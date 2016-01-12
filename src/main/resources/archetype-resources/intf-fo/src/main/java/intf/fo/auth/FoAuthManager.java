package ${groupId}.intf.fo.auth;

import ${groupId}.intf.fo.basic.FoResponse;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public interface FoAuthManager {

	/**
	 * login under OAuth2 protocol if the user is not an social account user. The user
	 * can login with their email
	 * 
	 */
	public FoResponse<FoAuthTokenResult> localOauth2Login(FoLocalLoginRequest request);

	/**
	 * let local user login with random code. Will return OAuth2 tokens.
	 * 
	 */
	public FoResponse<FoAuthTokenResult> localRandomCodeLogin(
			FoRandomCodeLoginRequest request);

	/**
	 * login using social site's token. Will return OAuth2 tokens.
	 * 
	 * @param request
	 * @return
	 */
	public FoResponse<FoAuthTokenResult> socialLogin(FoSocialLoginRequest request);

	/**
	 * refresh token
	 * 
	 * @return
	 */
	public FoResponse<FoAuthTokenResult> oauth2RefreshToken(FoRefreshTokenRequest request);

	/**
	 * register as a local user, not an social site user such as google/facebook. After registration, an
	 * Oauth2 token will be issued
	 */
	public FoResponse<FoAuthTokenResult> localRegister(FoRegisterRequest request);

	/**
	 * log out
	 * 
	 * @param accessToken
	 * @return
	 */
	public FoResponse<Void> oauth2Logout(Long currentUserId,
			String accessToken);


	/**
	 * generate random login code for local user
	 * 
	 * 
	 * @param request
	 * @return
	 */
	public FoResponse<Void> generateRandomLoginCode(
			FoGenRandomLoginCodeRequest request);

}
