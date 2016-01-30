package ${package}.democlient.fo.rest.auth;

import static ${package}.democlient.util.DemoClientConstants.BACKEND_FO_REST_URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;

import ${package}.democlient.util.DemoClientConstants;
import ${package}.restclient.model.GenRandomLoginCodeRequest;
import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.apis.google.GoogleToken;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.AccessTokenExtractor;
import com.github.scribejava.core.extractors.JsonTokenExtractor;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.utils.OAuthEncoder;
import com.github.scribejava.core.utils.Preconditions;

/**
 * This program requires user interaction, but "mvn test" doesn't take user
 * input well. So I have to write a main program instead of a junit test case
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class DemoClientFoAuthUiMain {
	private static Client restClient = ClientBuilder.newClient();

	public static void main(String[] args) throws Exception {

		facebookLoginByTokenFlow();
		googleLoginByTokenFlow();

		/**
		 * for social login by code, see <a
		 * href="https://github.com/chenjianjx/srb4j-desktop-client">here</a>
		 */

		randomCodeLoginFlow();

	}

	public static void randomCodeLoginFlow() throws IOException {
		// register user account first

		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(
				System.in));
		System.out
				.print("Have you got an email address that has been registered to the system and can receive emails?  \n (yes or no)>>");
		String hasEmail = consoleInput.readLine();
		System.out.println();

		String email = null;
		if (!"yes".equals(hasEmail)) {
			System.out.print("Please input such an email to register: \n >> ");
			email = consoleInput.readLine();
			String pwd = "abc123";
			DemoClientFoAuthTest.doRegister(email, pwd);
			System.out.println("Registration done. Your password is " + pwd);
		} else {
			System.out.print("Please input that email: \n >> ");
			email = consoleInput.readLine();
		}

		// do gen
		GenRandomLoginCodeRequest genCodeRequest = new GenRandomLoginCodeRequest();
		genCodeRequest.setEmail(email);

		Response genCodeResponse = restClient
				.target(BACKEND_FO_REST_URL)
				.path(DemoClientConstants.GEN_RANDOM_CODE_URL)
				.request()
				.post(Entity.entity(genCodeRequest, MediaType.APPLICATION_JSON));

		Assert.assertEquals(200, genCodeResponse.getStatus());

		System.out
				.print("Please input the random code you got from your email. \n >>");
		String randomCode = consoleInput.readLine();

		WebTarget target = restClient.target(BACKEND_FO_REST_URL).path(
				DemoClientConstants.RANDOM_CODE_LOGIN_URL);
		Form form = new Form();
		form.param("grant_type", "password");
		form.param("username", email);
		form.param("password", randomCode);
		form.param("long_session", "false");

		Response randomCodeLoginRestResponse = target
				.request(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.form(form));

		DemoClientFoAuthTokenResponse clientResponse = DemoClientFoAuthTokenResponse
				.parseRestResponse(randomCodeLoginRestResponse);

		DemoClientFoAuthTest.assertAuthResponseSuccessful(clientResponse);

	}

	private static void googleLoginByTokenFlow() throws IOException {
		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(
				System.in));

		System.out.print("Please input your google client id: \n >>");
		String googleClientId = consoleInput.readLine();

		System.out.print("Please input your google client screct: \r\n >>");
		String googleClientSecret = consoleInput.readLine();

		final OAuth20Service service = new ServiceBuilder()
				.apiKey(googleClientId).apiSecret(googleClientSecret)
				.scope("email")
				// replace with desired scope
				.callback("urn:ietf:wg:oauth:2.0:oob")
				.build(GoogleApi20.instance());

		final String authorizationUrl = service.getAuthorizationUrl();
		System.out
				.println("Please copy the following url and visit it on your browser. Copy the code on the page and paste it here");
		System.out.println(authorizationUrl);
		System.out.print(">>");
		final Verifier verifier = new Verifier(consoleInput.readLine());
		System.out.println();

		final GoogleToken accessToken = (GoogleToken) service
				.getAccessToken(verifier);
		String googleIdToken = accessToken.getOpenIdToken();
		System.out.println("the idToken is: " + googleIdToken);

		WebTarget target = restClient.target(BACKEND_FO_REST_URL)
				.path(DemoClientConstants.SOCIAL_LOGIN_BY_TOKEN_URL_PREFIX
						+ "google");
		Form form = new Form();
		form.param("grant_type", "password");
		form.param("username", googleIdToken);
		form.param("password", "anything");
		form.param("long_session", "false");

		Response restResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.form(form));

		DemoClientFoAuthTokenResponse clientResponse = DemoClientFoAuthTokenResponse
				.parseRestResponse(restResponse);

		DemoClientFoAuthTest.assertAuthResponseSuccessful(clientResponse);

	}

	private static void facebookLoginByTokenFlow() throws IOException {
		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(
				System.in));

		System.out.print("Please input your facebook app id: \n >>");
		String clientId = consoleInput.readLine();

		System.out.print("Please input your facebook app screct: \r\n >>");
		String clientSecret = consoleInput.readLine();

		final OAuth20Service service = new ServiceBuilder()
				.apiKey(clientId)
				.apiSecret(clientSecret)
				.scope("email")
				// replace with desired scope
				.callback("https://www.facebook.com/connect/login_success.html")
				.build(new DemoClientFacebookApi());

		final String authorizationUrl = service.getAuthorizationUrl();
		System.out
				.println("Now open your browser and show the 'inspect' window to monitor the url redirection");
		System.out
				.println("Copy the following url and visit it on your browser. And then extract the code accoring to\n https://raw.githubusercontent.com/chenjianjx/srb4j/master/src/main/resources/archetype-resources/demo-client/doc/fo/extract-facebook-code.png: ");
		System.out.println(authorizationUrl);
		System.out.print(">>");
		Verifier verifier = new Verifier(consoleInput.readLine());
		Token accessToken = service.getAccessToken(verifier);

		System.out.println("the access token is: " + accessToken.getToken());

		WebTarget target = restClient.target(BACKEND_FO_REST_URL).path(
				DemoClientConstants.SOCIAL_LOGIN_BY_TOKEN_URL_PREFIX
						+ "facebook");
		Form form = new Form();
		form.param("grant_type", "password");
		form.param("username", accessToken.getToken());
		form.param("password", "anything");
		form.param("long_session", "false");

		Response restResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.form(form));

		DemoClientFoAuthTokenResponse clientResponse = DemoClientFoAuthTokenResponse
				.parseRestResponse(restResponse);

		DemoClientFoAuthTest.assertAuthResponseSuccessful(clientResponse);

	}

	public static class DemoClientFacebookApi extends DefaultApi20 {

		private static final String AUTHORIZE_URL = "https://www.facebook.com/v2.5/dialog/oauth?client_id=%s&redirect_uri=%s";

		@Override
		public String getAccessTokenEndpoint() {
			return "https://graph.facebook.com/v2.5/oauth/access_token";
		}

		@Override
		public String getAuthorizationUrl(final OAuthConfig config) {
			Preconditions
					.checkValidUrl(config.getCallback(),
							"Must provide a valid url as callback. Facebook does not support OOB");
			final StringBuilder sb = new StringBuilder(String.format(
					AUTHORIZE_URL, config.getApiKey(),
					OAuthEncoder.encode(config.getCallback())));
			if (config.hasScope()) {
				sb.append('&').append(OAuthConstants.SCOPE).append('=')
						.append(OAuthEncoder.encode(config.getScope()));
			}

			final String state = config.getState();
			if (state != null) {
				sb.append('&').append(OAuthConstants.STATE).append('=')
						.append(OAuthEncoder.encode(state));
			}
			return sb.toString();
		}

		@Override
		public AccessTokenExtractor getAccessTokenExtractor() {
			return new JsonTokenExtractor();
		}

	}

}
