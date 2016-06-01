package ${package}.democlient.fo.rest.auth;

import static ${package}.democlient.util.DemoClientConstants.BACKEND_FO_REST_URL;

import java.io.File;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.junit.Assert;
import org.junit.Test;

import ${package}.democlient.util.DemoClientConstants;
import ${package}.democlient.util.DemoClientUtils;
import ${package}.restclient.model.AuthTokenResult;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class DemoClientFoAuthTest {

	private static Client restClient = DemoClientUtils.createRestClient();

	@Test
	public void testProtectedResourceWithoutLogin() {
		Response restResponse = restClient.target(BACKEND_FO_REST_URL)
				.path(DemoClientConstants.PROCTED_RESOURCE_URL)
				.request(MediaType.APPLICATION_JSON_TYPE).get();
		Assert.assertEquals(400, restResponse.getStatus());
		Assert.assertNotNull(restResponse.getHeaderString("WWW-Authenticate"));
	}

	@Test
	public void testLocalLogin() {

		// register one first
		String username = RandomStringUtils.randomAlphanumeric(10)
				+ "@nonexist.com";
		String password = "abc123";
		doRegister(username, password);

		// now login
		WebTarget target = restClient.target(BACKEND_FO_REST_URL).path(
				DemoClientConstants.LOCAL_LOGIN_URL);
		Form form = new Form();
		form.param("grant_type", "password");
		form.param("username", username);
		form.param("password", password);
		form.param("long_session", "true");

		Response restResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.form(form));

		DemoClientFoAuthTokenResponse clientResponse = DemoClientFoAuthTokenResponse
				.parseRestResponse(restResponse);

		assertAuthResponseSuccessful(clientResponse);

	}

	@Test
	public void testLocalLogin_NonExistingUser() {

		// now login
		WebTarget target = restClient.target(BACKEND_FO_REST_URL).path(
				DemoClientConstants.LOCAL_LOGIN_URL);
		Form form = new Form();
		form.param("grant_type", "password");
		form.param("username",
				"not-eixsting-user-" + System.currentTimeMillis());
		form.param("password", "some-password");

		Response restResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.form(form));

		DemoClientFoAuthTokenResponse clientResponse = DemoClientFoAuthTokenResponse
				.parseRestResponse(restResponse);

		Assert.assertFalse(clientResponse.isSuccessful());
		Assert.assertNull(clientResponse.tokenResult);
		Assert.assertNotNull(clientResponse.errorResult);
		DemoClientUtils.printErrorResult(clientResponse.errorResult);

		Assert.assertEquals("invalid_request",
				clientResponse.errorResult.getError());

	}

	@Test
	public void testLocalLogin_WrongPassword() {

		// register one first
		String username = RandomStringUtils.randomAlphanumeric(10)
				+ "@nonexist.com";
		String password = "abc123";
		doRegister(username, password);

		// now login
		WebTarget target = restClient.target(BACKEND_FO_REST_URL).path(
				DemoClientConstants.LOCAL_LOGIN_URL);
		Form form = new Form();
		form.param("grant_type", "password");
		form.param("username", username);
		form.param("password", "some-wrong-password");

		Response restResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.form(form));

		DemoClientFoAuthTokenResponse clientResponse = DemoClientFoAuthTokenResponse
				.parseRestResponse(restResponse);

		Assert.assertFalse(clientResponse.isSuccessful());
		Assert.assertNull(clientResponse.tokenResult);
		Assert.assertNotNull(clientResponse.errorResult);
		DemoClientUtils.printErrorResult(clientResponse.errorResult);

		Assert.assertEquals("invalid_request",
				clientResponse.errorResult.getError());

	}

	@Test
	public void testLocalLogin_UsingOAuth2Library()
			throws OAuthSystemException, OAuthProblemException {

		// register one first
		String username = RandomStringUtils.randomAlphanumeric(10)
				+ "@nonexist.com";
		String password = "abc123";
		doRegister(username, password);

		OAuthClientRequest request = OAuthClientRequest
				.tokenLocation(
						BACKEND_FO_REST_URL
								+ DemoClientConstants.LOCAL_LOGIN_URL)
				.setGrantType(GrantType.PASSWORD).setClientId(null)
				.setClientSecret(null).setUsername(username)
				.setPassword(password).setParameter("long_session", "false")
				.buildBodyMessage();

		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

		OAuthJSONAccessTokenResponse response = oAuthClient
				.accessToken(request);

		assertOauth2LibraryResponseSuccessful(response);

	}

	@Test
	public void testLocalLogin_WrongPassword_UsingOAuth2Library()
			throws OAuthSystemException {

		// register one first
		String username = RandomStringUtils.randomAlphanumeric(10)
				+ "@nonexist.com";
		String password = "abc123";
		doRegister(username, password);

		OAuthClientRequest request = OAuthClientRequest
				.tokenLocation(
						BACKEND_FO_REST_URL
								+ DemoClientConstants.LOCAL_LOGIN_URL)
				.setGrantType(GrantType.PASSWORD).setClientId(null)
				.setClientSecret(null).setUsername(username)
				.setPassword("wrong-password")
				.setParameter("long_session", "false").buildBodyMessage();

		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

		try {
			@SuppressWarnings("unused")
			OAuthJSONAccessTokenResponse response = oAuthClient
					.accessToken(request);
			Assert.fail();
		} catch (OAuthProblemException e) {
			System.out.println("error(the code): " + e.getError());
			System.out
					.println("error_description (error message  for client developer): "
							+ e.getDescription());
			// TODO: this will not work because oltu client throws away any
			// non-standard information. it sucks.
			System.out.println("error_description_for_user:"
					+ e.get("error_description_for_user"));

		}

	}

	@Test
	public void testRegister() {

		String username = RandomStringUtils.randomAlphanumeric(10)
				+ "@nonexist.com";
		String password = "abc123";
		WebTarget target = restClient.target(BACKEND_FO_REST_URL).path(
				DemoClientConstants.LOCAL_REG_URL);
		Form form = new Form();
		form.param("grant_type", "password");
		form.param("username", username);
		form.param("password", password);

		Response restResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.form(form));
		DemoClientFoAuthTokenResponse clientResponse = DemoClientFoAuthTokenResponse
				.parseRestResponse(restResponse);

		assertAuthResponseSuccessful(clientResponse);

	}

	@Test
	public void testLogout() {
		String accessToken = doRegister();
		Builder restRequest = restClient.target(BACKEND_FO_REST_URL)
				.path(DemoClientConstants.LOGOUT_URL).request();
		restRequest = restRequest.header("Authorization", "Bearer "
				+ accessToken);
		Response restResponse = restRequest.post(null);
		Assert.assertEquals(200, restResponse.getStatus());

		Assert.assertEquals(401, tryProctedResource(accessToken));
	}

	@Test
	public void testRefreshToken() {

		// register to get tokens
		String refreshToken = doRegisterAndReturnRefreshToken();

		// now do refresh
		WebTarget target = restClient.target(BACKEND_FO_REST_URL).path(
				DemoClientConstants.REFRESH_TOKEN_URL);
		Form form = new Form();
		form.param("grant_type", "refresh_token");
		form.param("refresh_token", refreshToken);

		Response restResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.form(form));

		DemoClientFoAuthTokenResponse clientResponse = DemoClientFoAuthTokenResponse
				.parseRestResponse(restResponse);

		assertAuthResponseSuccessful(clientResponse);

	}

	private void assertOauth2LibraryResponseSuccessful(
			OAuthJSONAccessTokenResponse response) {
		System.out.println("access token:" + response.getAccessToken());
		System.out.println("refresh token:" + response.getRefreshToken());
		System.out.println("expire in:" + response.getExpiresIn());

		Assert.assertNotNull(response.getAccessToken());
		Assert.assertNotNull(response.getRefreshToken());
		Assert.assertTrue(response.getExpiresIn() > 0);
		Assert.assertNotNull(response.getParam("user_principal"));
		Assert.assertEquals(200, tryProctedResource(response.getAccessToken()));
	}

	static void assertAuthResponseSuccessful(
			DemoClientFoAuthTokenResponse clientResponse) {

		if (!clientResponse.isSuccessful()) {
			saveErrorToFile(clientResponse);

		}

		Assert.assertTrue(clientResponse.isSuccessful());
		Assert.assertNotNull(clientResponse.tokenResult);
		Assert.assertNotNull(clientResponse.tokenResult.getAccessToken());
		Assert.assertNotNull(clientResponse.tokenResult.getRefreshToken());
		Assert.assertNotNull(clientResponse.tokenResult.getUserPrincipal());
		Assert.assertEquals(200,
				tryProctedResource(clientResponse.tokenResult.getAccessToken()));
	}

	private static void saveErrorToFile(
			DemoClientFoAuthTokenResponse clientResponse) {
		try {
			FileUtils.writeStringToFile(
					new File(System.getProperty("user.home") + "/"
							+ DemoClientFoAuthTest.class.getSimpleName()
							+ "-error.txt"),
					DemoClientUtils.toJson(clientResponse));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String doRegisterAndReturnRefreshToken() {
		String username = RandomStringUtils.randomAlphanumeric(10)
				+ "@nonexist.com";
		String password = "abc123";
		WebTarget target = restClient.target(BACKEND_FO_REST_URL).path(
				DemoClientConstants.LOCAL_REG_URL);
		Form form = new Form();
		form.param("grant_type", "password");
		form.param("username", username);
		form.param("password", password);

		Response restResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.form(form));

		if (restResponse.getStatus() != 200) {
			throw new IllegalArgumentException("registration failed. "
					+ restResponse.toString());
		}
		AuthTokenResult registerTokenResult = restResponse
				.readEntity(AuthTokenResult.class);
		registerTokenResult.getAccessToken();
		return registerTokenResult.getRefreshToken();
	}

	public static String doRegister() {
		String username = RandomStringUtils.randomAlphanumeric(10)
				+ "@nonexist.com";
		String password = "abc123";
		return doRegister(username, password);
	}

	private static int tryProctedResource(String accessToken) {
		Response response = restClient
				.target(BACKEND_FO_REST_URL)
				.path(DemoClientConstants.PROCTED_RESOURCE_URL)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization",
						"Bearer " + StringUtils.defaultString(accessToken))
				.get();
		return response.getStatus();
	}

	public static String doRegister(String username, String password) {
		WebTarget target = restClient.target(BACKEND_FO_REST_URL).path(
				DemoClientConstants.LOCAL_REG_URL);
		Form form = new Form();
		form.param("grant_type", "password");
		form.param("username", username);
		form.param("password", password);

		Response restResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.form(form));

		if (restResponse.getStatus() != 200) {
			throw new IllegalArgumentException("registration failed. "
					+ restResponse.toString());
		}
		String accessToken = restResponse.readEntity(AuthTokenResult.class)
				.getAccessToken();
		return accessToken;
	}

	public static String doLogin(String username, String password) {
		WebTarget target = restClient.target(BACKEND_FO_REST_URL).path(
				DemoClientConstants.LOCAL_LOGIN_URL);
		Form form = new Form();
		form.param("grant_type", "password");
		form.param("username", username);
		form.param("password", password);

		Response restResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.form(form));

		if (restResponse.getStatus() != 200) {
			throw new IllegalArgumentException("login failed. "
					+ restResponse.toString());
		}
		String accessToken = restResponse.readEntity(AuthTokenResult.class)
				.getAccessToken();
		return accessToken;
	}
}
