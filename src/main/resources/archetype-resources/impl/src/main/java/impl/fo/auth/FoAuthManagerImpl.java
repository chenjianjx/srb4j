package ${package}.impl.fo.auth;

import static ${package}.intf.fo.basic.FoConstants.NULL_REQUEST_BEAN_TIP;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ${package}.impl.biz.auth.AccessToken;
import ${package}.impl.biz.auth.AccessTokenRepo;
import ${package}.impl.biz.auth.AuthService;
import ${package}.impl.biz.auth.RandomLoginCode;
import ${package}.impl.biz.auth.RandomLoginCodeRepo;
import ${package}.impl.biz.client.Client;
import ${package}.impl.biz.user.User;
import ${package}.impl.biz.user.UserRepo;
import ${package}.impl.fo.auth.socialsite.FoSocialSiteAuthHelper;
import ${package}.impl.fo.common.FoManagerImplBase;
import ${package}.impl.util.infrahelp.beanvalidae.MyValidator;
import ${package}.impl.util.tools.lang.MyDuplet;
import ${package}.intf.fo.auth.FoAuthManager;
import ${package}.intf.fo.auth.FoAuthTokenResult;
import ${package}.intf.fo.auth.FoGenRandomLoginCodeRequest;
import ${package}.intf.fo.auth.FoLocalLoginRequest;
import ${package}.intf.fo.auth.FoRandomCodeLoginRequest;
import ${package}.intf.fo.auth.FoRefreshTokenRequest;
import ${package}.intf.fo.auth.FoRegisterRequest;
import ${package}.intf.fo.auth.FoSocialAuthCodeLoginRequest;
import ${package}.intf.fo.auth.FoSocialLoginByTokenRequest;
import ${package}.intf.fo.basic.FoConstants;
import ${package}.intf.fo.basic.FoResponse;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Service("foAuthManager")
public class FoAuthManagerImpl extends FoManagerImplBase implements
		FoAuthManager {

	private static final Logger logger = LoggerFactory
			.getLogger(FoAuthManagerImpl.class);

	@Resource
	AccessTokenRepo accessTokenRepo;

	@Resource
	UserRepo userRepo;

	@Resource
	RandomLoginCodeRepo randomCodeRepo;

	@Resource
	MyValidator myValidator;

	@Resource
	AuthService authService;

	@Resource
	FoSocialSiteAuthHelper.Factory socialSiteAuthHelperFactory;

	@Override
	public FoResponse<FoAuthTokenResult> localOauth2Login(
			FoLocalLoginRequest request) {
		String error = myValidator.validateBeanFastFail(request,
				NULL_REQUEST_BEAN_TIP);
		if (error != null) {
			return FoResponse.userErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST, error);
		}

		String principal = User.decidePrincipalFromLocal(request.getEmail());
		User user = userRepo.getUserByPrincipal(principal);

		if (user == null) {
			return FoResponse.userErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST, "invalid email");
		}

		// now compare password
		String encodedPassword = authService.encodePasswordOrRandomCode(request
				.getPassword());

		if (!encodedPassword.equals(user.getPassword())) {
			return FoResponse.userErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST, "invalid password");
		}

		// ok, do the token
		return buildAuthTokenResponse(user, request.isLongSession());
	}

	@Override
	public FoResponse<FoAuthTokenResult> localRandomCodeLogin(
			FoRandomCodeLoginRequest request) {

		String error = myValidator.validateBeanFastFail(request,
				NULL_REQUEST_BEAN_TIP);
		if (error != null) {
			return FoResponse.userErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST, error);
		}

		String principal = User.decidePrincipalFromLocal(request.getEmail());
		User user = userRepo.getUserByPrincipal(principal);

		if (user == null) {
			return FoResponse.userErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST, "Invalid email.");
		}

		// now compare the codes
		String encodedCode = authService.encodePasswordOrRandomCode(request
				.getRandomCode());
		RandomLoginCode rlc = randomCodeRepo.getByUserId(user.getId());
		if (rlc == null || !encodedCode.equals(rlc.getCodeStr())) {
			return FoResponse.userErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST,
					"Invalid random code.");
		}

		if (rlc.hasExpired()) {
			return FoResponse.userErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST,
					"Random code expired.");
		}

		// ok, do the token
		FoResponse<FoAuthTokenResult> response = buildAuthTokenResponse(user,
				false);

		// finally delete the code
		randomCodeRepo.deleteByUserId(user.getId());
		return response;
	}

	private FoResponse<FoAuthTokenResult> buildAuthTokenResponse(User user,
			boolean longSession) {
		AccessToken at = authService
				.genNewAccessTokenForUser(user, longSession);
		return buildAuthTokenResponse(at, user);

	}

	private FoResponse<FoAuthTokenResult> buildAuthTokenResponse(
			AccessToken accessToken, User user) {
		FoAuthTokenResult result = new FoAuthTokenResult();
		result.setAccessToken(accessToken.getTokenStr());
		result.setRefreshToken(accessToken.getRefreshTokenStr());
		result.setExpiresIn(accessToken.getLifespan());
		result.defaultTokenType();
		result.setUserPrincipal(user.getPrincipal());
		return FoResponse.success(result);
	}

	@Override
	public FoResponse<FoAuthTokenResult> localRegister(FoRegisterRequest request) {

		String error = myValidator.validateBeanFastFail(request,
				NULL_REQUEST_BEAN_TIP);
		if (error != null) {
			return FoResponse.userErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST, error);
		}

		String email = request.getEmail();
		if (!myValidator.isEmailValid(email)) {
			return FoResponse.userErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST, "Invalid Email");
		}

		// validate user existence

		String principalName = User.decidePrincipalFromLocal(email);
		User existingUser = userRepo.getUserByPrincipal(principalName);

		if (existingUser != null) {
			String err = "This email already exists";
			return FoResponse.userErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST, err);
		}

		// save it
		String source = User.SOURCE_LOCAL;
		String encodedPassword = authService.encodePasswordOrRandomCode(request
				.getPassword());
		User user1 = new User();
		user1.setEmail(email);
		user1.setPassword(encodedPassword);
		user1.setSource(source);
		user1.setCreatedBy(user1.getPrincipal());
		userRepo.saveNewUser(user1);

		User user = user1;
		return buildAuthTokenResponse(user, false);
	}

	@Override
	public FoResponse<Void> oauth2Logout(Long currentUserId, String tokenStr) {
		if (currentUserId == null) {
			// not login yet? say nothing harsh
			return FoResponse.success(null);
		}
		tokenStr = StringUtils.trimToNull(tokenStr);
		if (tokenStr == null) {
			// say nothing harsh
			return FoResponse.success(null);
		}

		AccessToken at = accessTokenRepo.getByTokenStr(tokenStr);
		if (at == null) {
			// invalid token? say nothing harsh
			return FoResponse.success(null);
		}

		if (at.getUserId() != currentUserId) {
			// not his or her token? say nothing harsh
			return FoResponse.success(null);
		}
		accessTokenRepo.deleteByTokenStr(tokenStr);
		return FoResponse.success(null);
	}

	@Override
	public FoResponse<Void> generateRandomLoginCode(
			FoGenRandomLoginCodeRequest request) {
		String error = myValidator.validateBeanFastFail(request,
				NULL_REQUEST_BEAN_TIP);
		if (error != null) {
			return FoResponse.userErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST, error);
		}

		String principalName = User
				.decidePrincipalFromLocal(request.getEmail());
		User user = userRepo.getUserByPrincipal(principalName);

		if (user == null) {
			return FoResponse.userErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST, "Invalid email.");
		}

		String randomCodeStr = authService.generateRandomLoginCode();
		RandomLoginCode randomCodeObj = authService.saveNewRandomCodeForUser(
				user, randomCodeStr);

		// send the email
		try {
			authService.sendEmailForRandomLoginCodeAsync(user, randomCodeObj,
					randomCodeStr);
		} catch (Exception e) {
			logger.error("fail to send random login codel asyncly  for user "
					+ user.getPrincipal(), e);
		}

		return FoResponse.success(null);
	}

	@Override
	public FoResponse<FoAuthTokenResult> socialLoginByToken(
			FoSocialLoginByTokenRequest request) {

		String error = myValidator.validateBeanFastFail(request,
				NULL_REQUEST_BEAN_TIP);
		if (error != null) {
			return FoResponse.devErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST, error, null);
		}
		String source = request.getSource();
		if (!User.isValidSocialAccountSource(source)) {
			return FoResponse.devErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST,
					"unsupported source: " + source, null);
		}

		boolean longSession = request.isLongSession();

		String socialToken = request.getToken();
		FoSocialSiteAuthHelper helper = this.socialSiteAuthHelperFactory
				.getHelper(source);
		MyDuplet<String, FoResponse<FoAuthTokenResult>> emailOrErrResp = helper
				.getEmailFromToken(socialToken, request.getClientType());
		return handleSocialSiteEmailResult(source, longSession, emailOrErrResp);
	}

	@Override
	public FoResponse<FoAuthTokenResult> socialLoginByAuthCode(
			FoSocialAuthCodeLoginRequest request) {

		String error = myValidator.validateBeanFastFail(request,
				NULL_REQUEST_BEAN_TIP);
		if (error != null) {
			return FoResponse.devErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST, error, null);
		}
		String source = request.getSource();
		if (!User.isValidSocialAccountSource(source)) {
			return FoResponse.devErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST,
					"unsupported source: " + source, null);
		}

		String clientType = request.getClientType();
		if (!Client.isValidClientType(clientType)) {
			return FoResponse.devErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST,
					"unsupported client type: " + clientType, null);
		}

		String redirectUri = request.getRedirectUri();

		boolean longSession = request.isLongSession();

		String authCode = request.getAuthCode();
		FoSocialSiteAuthHelper helper = this.socialSiteAuthHelperFactory
				.getHelper(source);
		MyDuplet<String, FoResponse<FoAuthTokenResult>> emailOrErrResp = helper
				.getEmailFromCode(authCode, clientType, redirectUri);
		return handleSocialSiteEmailResult(source, longSession, emailOrErrResp);
	}

	private FoResponse<FoAuthTokenResult> handleSocialSiteEmailResult(
			String source, boolean longSession,
			MyDuplet<String, FoResponse<FoAuthTokenResult>> emailOrErrResp) {
		if (emailOrErrResp.right != null) {
			return emailOrErrResp.right;
		}

		String email = emailOrErrResp.left;
		String principal = User.decidePrincipal(source, email);
		User existingUser = userRepo.getUserByPrincipal(principal);

		if (existingUser == null) {
			existingUser = new User();
			existingUser.setEmail(email);
			existingUser.setPassword(null);
			existingUser.setSource(source);
			existingUser.setCreatedBy(existingUser.getPrincipal());
			userRepo.saveNewUser(existingUser);

		}
		// ok, do the token
		return buildAuthTokenResponse(existingUser, longSession);
	}

	@Override
	public FoResponse<FoAuthTokenResult> oauth2RefreshToken(
			FoRefreshTokenRequest request) {

		String error = myValidator.validateBeanFastFail(request,
				NULL_REQUEST_BEAN_TIP);
		if (error != null) {
			return FoResponse.devErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST, error, null);
		}

		String refreshToken = request.getRefreshToken();

		AccessToken at = accessTokenRepo.getByRefreshTokenStr(refreshToken);

		if (at == null) {
			return FoResponse.devErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST,
					"invalid refresh token", null);
		}

		User user = userRepo.getUserById(at.getUserId());

		at.setTokenStr(authService.generateAccessTokenStr());
		at.setRefreshTokenStr(authService.generateRefreshTokenStr());
		at.setExpiresAt(authService.calExpiresAt(at.getLifespan()));
		accessTokenRepo.updateAccessToken(at);

		// ok, do the token
		return buildAuthTokenResponse(at, user);

	}

}
