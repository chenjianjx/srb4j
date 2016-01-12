package ${groupId}.webapp.fo.rest.auth;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static ${groupId}.intf.fo.basic.FoConstants.ACCESS_TOKEN_HEADER_DEFAULT;
import static ${groupId}.intf.fo.basic.FoConstants.ACCESS_TOKEN_HEADER_KEY;
import static ${groupId}.intf.fo.basic.FoConstants.BIZ_ERR_TIP;
import static ${groupId}.intf.fo.basic.FoConstants.FO_SC_BIZ_ERROR;
import static ${groupId}.intf.fo.basic.FoConstants.LONG_SESSION_PARAM;
import static ${groupId}.intf.fo.basic.FoConstants.LONG_SESSION_TIP;
import static ${groupId}.intf.fo.basic.FoConstants.OAUTH2_ACCESS_TOKEN_NAME_TIP;
import static ${groupId}.intf.fo.basic.FoConstants.OAUTH2_FLOW_TIP;
import static ${groupId}.intf.fo.basic.FoConstants.OAUTH2_GRANT_TYPE_TIP;
import static ${groupId}.intf.fo.basic.FoConstants.OAUTH2_TOKEN_ENDPOINT_ERR_TIP;
import static ${groupId}.intf.fo.basic.FoConstants.OAUTH2_TOKEN_ENDPOINT_TIP;
import static ${groupId}.intf.fo.basic.FoConstants.OK_TIP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.as.request.OAuthUnauthenticatedTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.stereotype.Service;
import ${groupId}.intf.fo.auth.FoAuthManager;
import ${groupId}.intf.fo.auth.FoAuthTokenResult;
import ${groupId}.intf.fo.auth.FoGenRandomLoginCodeRequest;
import ${groupId}.intf.fo.auth.FoLocalLoginRequest;
import ${groupId}.intf.fo.auth.FoSocialLoginRequest;
import ${groupId}.intf.fo.auth.FoRandomCodeLoginRequest;
import ${groupId}.intf.fo.auth.FoRefreshTokenRequest;
import ${groupId}.intf.fo.auth.FoRegisterRequest;
import ${groupId}.intf.fo.basic.FoConstants;
import ${groupId}.intf.fo.basic.FoErrorResult;
import ${groupId}.intf.fo.basic.FoResponse;
import ${groupId}.webapp.fo.rest.support.FoResourceBase;
import ${groupId}.webapp.fo.rest.support.FoRestUtils;
import ${groupId}.webapp.infrahelper.rest.OAuth2RequestWrapper;

/**
 * rest resource for authentication
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Service
@Path("/token")
@Api(value = "/token")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FoAuthTokenResource extends FoResourceBase {

	@Resource
	FoAuthManager foAuthManager;

	@POST
	@Path("/new/local")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = OAUTH2_TOKEN_ENDPOINT_TIP
			+ " login if the user's account is registered here instead of being an social account(google, facebook etc.)"
			+ "", notes = OAUTH2_FLOW_TIP)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "grant_type", value = OAUTH2_GRANT_TYPE_TIP, required = true, dataType = "string", paramType = "form", defaultValue = "password"),
			@ApiImplicitParam(name = "username", value = "The user's email", required = true, dataType = "string", paramType = "form"),
			@ApiImplicitParam(name = "password", value = "The user's password", required = true, dataType = "string", paramType = "form"),
			@ApiImplicitParam(name = LONG_SESSION_PARAM, value = LONG_SESSION_TIP, required = true, dataType = "boolean", paramType = "form") })
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = OK_TIP, response = FoAuthTokenResult.class),
			@ApiResponse(code = SC_BAD_REQUEST, message = OAUTH2_TOKEN_ENDPOINT_ERR_TIP, response = FoErrorResult.class) })
	public Response localLogin(@Context HttpServletRequest rawRequest,
			MultivaluedMap<String, String> form) throws OAuthSystemException {

		OAuth2RequestWrapper servletRequest = new OAuth2RequestWrapper(
				rawRequest, form);

		AppLayerAuthCommand appLayerAuth = new AppLayerAuthCommand() {

			@Override
			public FoResponse<FoAuthTokenResult> doAuth(
					HttpServletRequest servletRequest,
					OAuthUnauthenticatedTokenRequest oltuRequest) {
				boolean longSession = Boolean.TRUE.toString().equals(
						servletRequest.getParameter(LONG_SESSION_PARAM));
				FoLocalLoginRequest foRequest = new FoLocalLoginRequest();
				foRequest.setEmail(oltuRequest.getUsername());
				foRequest.setPassword(oltuRequest.getPassword());
				foRequest.setLongSession(longSession);
				FoResponse<FoAuthTokenResult> foResponse = foAuthManager
						.localOauth2Login(foRequest);
				return foResponse;
			}
		};

		return oauth2PasswordFlow(servletRequest, appLayerAuth);

	}

	@POST
	@Path("/new/google")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = OAUTH2_TOKEN_ENDPOINT_TIP
			+ " login with google's open-id token" + "", notes = OAUTH2_FLOW_TIP)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "grant_type", value = OAUTH2_GRANT_TYPE_TIP, required = true, dataType = "string", paramType = "form", defaultValue = "password"),
			@ApiImplicitParam(name = "username", value = "The open id token you obtained after logining into Google. The token should have the scope of 'email'", required = true, dataType = "string", paramType = "form"),
			@ApiImplicitParam(name = "password", value = "Anything but null", required = true, dataType = "string", paramType = "form"),
			@ApiImplicitParam(name = LONG_SESSION_PARAM, value = LONG_SESSION_TIP, required = true, dataType = "boolean", paramType = "form") })
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = OK_TIP, response = FoAuthTokenResult.class),
			@ApiResponse(code = SC_BAD_REQUEST, message = OAUTH2_TOKEN_ENDPOINT_ERR_TIP, response = FoErrorResult.class) })
	public Response googleLogin(@Context HttpServletRequest rawRequest,
			MultivaluedMap<String, String> form) throws OAuthSystemException {

		OAuth2RequestWrapper servletRequest = new OAuth2RequestWrapper(
				rawRequest, form);

		AppLayerAuthCommand appLayerAuth = new AppLayerAuthCommand() {

			@Override
			public FoResponse<FoAuthTokenResult> doAuth(
					HttpServletRequest servletRequest,
					OAuthUnauthenticatedTokenRequest oltuRequest) {
				boolean longSession = Boolean.TRUE.toString().equals(
						servletRequest.getParameter(LONG_SESSION_PARAM));
				FoSocialLoginRequest foRequest = new FoSocialLoginRequest();
				foRequest.setToken(oltuRequest.getUsername());
				foRequest.setLongSession(longSession);
				foRequest.setSource("google");
				FoResponse<FoAuthTokenResult> foResponse = foAuthManager
						.socialLogin(foRequest);
				return foResponse;
			}
		};

		return oauth2PasswordFlow(servletRequest, appLayerAuth);

	}
	
	
	@POST
	@Path("/new/facebook")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = OAUTH2_TOKEN_ENDPOINT_TIP
			+ " login with facebook's access token" + "", notes = OAUTH2_FLOW_TIP)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "grant_type", value = OAUTH2_GRANT_TYPE_TIP, required = true, dataType = "string", paramType = "form", defaultValue = "password"),
			@ApiImplicitParam(name = "username", value = "The access token you obtained after logining into Facebook. The token should have the scope of 'email'", required = true, dataType = "string", paramType = "form"),
			@ApiImplicitParam(name = "password", value = "Anything but null", required = true, dataType = "string", paramType = "form"),
			@ApiImplicitParam(name = LONG_SESSION_PARAM, value = LONG_SESSION_TIP, required = true, dataType = "boolean", paramType = "form") })
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = OK_TIP, response = FoAuthTokenResult.class),
			@ApiResponse(code = SC_BAD_REQUEST, message = OAUTH2_TOKEN_ENDPOINT_ERR_TIP, response = FoErrorResult.class) })
	public Response facebookLogin(@Context HttpServletRequest rawRequest,
			MultivaluedMap<String, String> form) throws OAuthSystemException {

		OAuth2RequestWrapper servletRequest = new OAuth2RequestWrapper(
				rawRequest, form);

		AppLayerAuthCommand appLayerAuth = new AppLayerAuthCommand() {

			@Override
			public FoResponse<FoAuthTokenResult> doAuth(
					HttpServletRequest servletRequest,
					OAuthUnauthenticatedTokenRequest oltuRequest) {
				boolean longSession = Boolean.TRUE.toString().equals(
						servletRequest.getParameter(LONG_SESSION_PARAM));
				FoSocialLoginRequest foRequest = new FoSocialLoginRequest();
				foRequest.setToken(oltuRequest.getUsername());
				foRequest.setLongSession(longSession);
				foRequest.setSource("facebook");
				FoResponse<FoAuthTokenResult> foResponse = foAuthManager
						.socialLogin(foRequest);
				return foResponse;
			}
		};

		return oauth2PasswordFlow(servletRequest, appLayerAuth);

	}


	@POST
	@Path("/new/by-random-code/local")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = OAUTH2_TOKEN_ENDPOINT_TIP
			+ " login with a random login code for local users" + "", notes = OAUTH2_FLOW_TIP)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "grant_type", value = OAUTH2_GRANT_TYPE_TIP, required = true, dataType = "string", paramType = "form", defaultValue = "password"),
			@ApiImplicitParam(name = "username", value = "The user's email", required = true, dataType = "string", paramType = "form"),
			@ApiImplicitParam(name = "password", value = "The random login code", required = true, dataType = "string", paramType = "form") })
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = OK_TIP, response = FoAuthTokenResult.class),
			@ApiResponse(code = SC_BAD_REQUEST, message = OAUTH2_TOKEN_ENDPOINT_ERR_TIP, response = FoErrorResult.class) })
	public Response localRandomCodeLogin(
			@Context HttpServletRequest rawRequest,
			MultivaluedMap<String, String> form) throws OAuthSystemException {

		OAuth2RequestWrapper servletRequest = new OAuth2RequestWrapper(
				rawRequest, form);

		AppLayerAuthCommand appLayerAuth = new AppLayerAuthCommand() {

			@Override
			public FoResponse<FoAuthTokenResult> doAuth(
					HttpServletRequest servletRequest,
					OAuthUnauthenticatedTokenRequest oltuRequest) {
				FoRandomCodeLoginRequest request = new FoRandomCodeLoginRequest();
				request.setEmail(oltuRequest.getUsername());
				request.setRandomCode(oltuRequest.getPassword());
				FoResponse<FoAuthTokenResult> foResponse = foAuthManager
						.localRandomCodeLogin(request);
				return foResponse;
			}
		};
		return oauth2PasswordFlow(servletRequest, appLayerAuth);
	}

	@POST
	@Path("/new/by-register/local")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = OAUTH2_TOKEN_ENDPOINT_TIP + " local user registers"
			+ "", notes = OAUTH2_FLOW_TIP)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "grant_type", value = OAUTH2_GRANT_TYPE_TIP, required = true, dataType = "string", paramType = "form", defaultValue = "password"),
			@ApiImplicitParam(name = "username", value = "email", required = true, dataType = "string", paramType = "form"),
			@ApiImplicitParam(name = "password", value = "password", required = true, dataType = "string", paramType = "form") })
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = OK_TIP, response = FoAuthTokenResult.class),
			@ApiResponse(code = SC_BAD_REQUEST, message = OAUTH2_TOKEN_ENDPOINT_ERR_TIP, response = FoErrorResult.class) })
	public Response localRegister(@Context HttpServletRequest rawRequest,
			MultivaluedMap<String, String> form) throws OAuthSystemException {

		OAuth2RequestWrapper servletRequest = new OAuth2RequestWrapper(
				rawRequest, form);

		AppLayerAuthCommand appLayerAuth = new AppLayerAuthCommand() {

			@Override
			public FoResponse<FoAuthTokenResult> doAuth(
					HttpServletRequest servletRequest,
					OAuthUnauthenticatedTokenRequest oltuRequest) {
				FoRegisterRequest request = new FoRegisterRequest();
				request.setEmail(oltuRequest.getUsername());
				request.setPassword(oltuRequest.getPassword());
				FoResponse<FoAuthTokenResult> foResponse = foAuthManager
						.localRegister(request);
				return foResponse;
			}
		};
		return oauth2PasswordFlow(servletRequest, appLayerAuth);
	}

	@GET
	@Path("/test/protected-resource")
	@ApiOperation(value = "A test resource only accessible by login-ed users. Feel free to delete it")
	@ApiImplicitParams({ @ApiImplicitParam(name = ACCESS_TOKEN_HEADER_KEY, value = OAUTH2_ACCESS_TOKEN_NAME_TIP, required = true, dataType = "string", paramType = "header", defaultValue = ACCESS_TOKEN_HEADER_DEFAULT) })
	@ApiResponses(value = { @ApiResponse(code = SC_OK, message = OK_TIP, response = Void.class) })
	public Response protectedResource(@Context ContainerRequestContext context) {
		Long userId = getUserId(context);
		if (userId == null) {
			FoResponse<Void> foResponse = FoResponse.userErrResponse(
					FoConstants.FEC_NOT_LOGIN_YET, "You have not login yet");
			return FoRestUtils.fromFoResponse(foResponse, context);
		} else {
			return FoRestUtils
					.fromFoResponse(FoResponse.success(null), context);
		}

	}

	@POST
	@Path("/refresh")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "OAuth2 Refresh Token", notes = "the refresh token will be invalid once used")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "grant_type", value = "must be 'refresh_token'", required = true, dataType = "string", paramType = "form", defaultValue = "refresh_token"),
			@ApiImplicitParam(name = "refresh_token", value = "The refresh token", required = true, dataType = "string", paramType = "form") })
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = OK_TIP, response = FoAuthTokenResult.class),
			@ApiResponse(code = SC_BAD_REQUEST, message = OAUTH2_TOKEN_ENDPOINT_ERR_TIP, response = FoErrorResult.class) })
	public Response refreshToken(@Context HttpServletRequest rawRequest,
			MultivaluedMap<String, String> form) throws OAuthSystemException {

		OAuth2RequestWrapper servletRequest = new OAuth2RequestWrapper(
				rawRequest, form);

		OAuthUnauthenticatedTokenRequest oltuRequest = null;
		try {
			// this constructor will validate required parameters
			oltuRequest = new OAuthUnauthenticatedTokenRequest(servletRequest);
		} catch (OAuthProblemException e) {
			OAuthResponse res = OAuthASResponse
					.errorResponse(FoConstants.FO_SC_BIZ_ERROR).error(e)
					.buildJSONMessage();
			return Response.status(res.getResponseStatus())
					.entity(res.getBody()).build();
		}
		OAuthResponse oltuResponse = null;

		/*
		 * the following fields will not be null since they have been validated
		 * by oltu
		 */
		String grantType = oltuRequest.getParam(OAuth.OAUTH_GRANT_TYPE);
		if (!grantType.equals(GrantType.REFRESH_TOKEN.toString())) {
			oltuResponse = OAuthASResponse
					.errorResponse(SC_BAD_REQUEST)
					.setError(OAuthError.TokenResponse.UNSUPPORTED_GRANT_TYPE)
					.setErrorDescription(
							"the grant_type has to be 'refresh_token' ")
					.buildJSONMessage();
			return toRestResponse(oltuResponse);
		}

		FoRefreshTokenRequest request = new FoRefreshTokenRequest();
		request.setRefreshToken(oltuRequest.getRefreshToken());
		FoResponse<FoAuthTokenResult> foResponse = foAuthManager
				.oauth2RefreshToken(request);
		// return restful response
		return authTokenResponseToRestResponse(foResponse);

	}

	@POST
	@Path("/delete")
	@ApiOperation(value = "logout")
	@ApiImplicitParams({ @ApiImplicitParam(name = ACCESS_TOKEN_HEADER_KEY, value = OAUTH2_ACCESS_TOKEN_NAME_TIP, required = true, dataType = "string", paramType = "header", defaultValue = ACCESS_TOKEN_HEADER_DEFAULT) })
	@ApiResponses(value = { @ApiResponse(code = SC_OK, message = OK_TIP, response = Void.class) })
	public Response logout(@Context ContainerRequestContext context) {
		String token = (String) context.getProperty(RS_ACCESS_TOKEN);
		FoResponse<Void> foResponse = foAuthManager.oauth2Logout(
				getUserId(context), token);
		return FoRestUtils.fromFoResponse(foResponse, context);
	}

	@POST
	@Path("/random-code/new/local")
	@ApiOperation(value = "generate a random login code")
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = OK_TIP, response = Void.class),
			@ApiResponse(code = FO_SC_BIZ_ERROR, message = BIZ_ERR_TIP, response = FoErrorResult.class) })
	public Response randomLoginCode(@Context ContainerRequestContext context,
			FoGenRandomLoginCodeRequest request) {
		FoResponse<Void> foResponse = foAuthManager
				.generateRandomLoginCode(request);
		return FoRestUtils.fromFoResponse(foResponse, context);
	}

	private Response toRestResponse(OAuthResponse oltuOauthResponse) {
		return Response.status(oltuOauthResponse.getResponseStatus())
				.entity(oltuOauthResponse.getBody()).build();
	}

	private Response oauth2PasswordFlow(OAuth2RequestWrapper servletRequest,
			AppLayerAuthCommand appLayerAuth) throws OAuthSystemException {
		/*
		 * No client credential validation here. If you need it for your oauth2
		 * flow, please refer to
		 * 
		 * @see <a href=
		 * "https://svn.apache.org/repos/asf/oltu/trunk/oauth-2.0/integration-tests/src/test/java/org/apache/oltu/oauth2/integration/endpoints/TokenEndpoint.java"
		 * >TokenEndpoint</a>
		 */
		OAuthUnauthenticatedTokenRequest oltuRequest = null;
		try {
			// this constructor will validate required parameters
			oltuRequest = new OAuthUnauthenticatedTokenRequest(servletRequest);
		} catch (OAuthProblemException e) {
			OAuthResponse res = OAuthASResponse
					.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
					.buildJSONMessage();
			return Response.status(res.getResponseStatus())
					.entity(res.getBody()).build();
		}
		OAuthResponse oltuResponse = null;

		/*
		 * the following fields will not be null since they have been validated
		 * by oltu
		 */
		String grantType = oltuRequest.getParam(OAuth.OAUTH_GRANT_TYPE);
		if (!grantType.equals(GrantType.PASSWORD.toString())) {
			oltuResponse = OAuthASResponse
					.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.setError(OAuthError.TokenResponse.UNSUPPORTED_GRANT_TYPE)
					.setErrorDescription("the grant_type has to be 'password' ")
					.buildJSONMessage();
			return toRestResponse(oltuResponse);
		}

		FoResponse<FoAuthTokenResult> foResponse = appLayerAuth.doAuth(
				servletRequest, oltuRequest);
		// return restful response
		return authTokenResponseToRestResponse(foResponse);
	}

	private static <T> Response authTokenResponseToRestResponse(
			FoResponse<T> foResponse) {
		if (foResponse.isSuccessful()) {
			return Response.status(SC_OK).entity(foResponse.getData()).build();
		} else {
			return Response.status(SC_BAD_REQUEST).entity(foResponse.getErr())
					.build();
		}
	}

	private static interface AppLayerAuthCommand {
		FoResponse<FoAuthTokenResult> doAuth(HttpServletRequest servletRequest,
				OAuthUnauthenticatedTokenRequest oltuRequest);
	}

}
