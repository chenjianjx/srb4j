package ${package}.democlient.fo.rest.fordoc;

import static ${package}.democlient.util.DemoClientUtils.fromJson;
import static ${package}.democlient.util.DemoClientUtils.toJson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.junit.Test;

import ${package}.democlient.util.DemoClientUtils;
import ${package}.restclient.model.NewPostRequest;
import ${package}.restclient.model.Post;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

/**
 * The code's target is for it to be copied to documents. So there should be as
 * little abstraction as possible.
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class DemoClientForDocTest {

	@Test
	public void allInOneTest() throws Exception {
		String accessToken = null;

		// do login
		HttpResponse<JsonNode> loginResponse = Unirest
				.post("http://localhost:8080/fo/rest/token/new/local")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.field("grant_type", "password")
				.field("username", "chenjianjx@gmail.com")
				.field("password", "abc123").asJson();

		if (loginResponse.getStatus() == 200) {
			JSONObject token = loginResponse.getBody().getObject();
			System.out.println(token.get("access_token")); //You can save the token for later use
			System.out.println(token.get("refresh_token"));
			System.out.println(token.get("expires_in"));
			System.out.println(token.get("user_principal")); // "the full user name"
			accessToken = (String) token.get("access_token");
		} else {
			System.out.println(loginResponse.getStatus());
			System.out.println(loginResponse.getStatusText());
			JSONObject error = loginResponse.getBody().getObject();
			System.out.println(error.get("error")); // "the error code"
			System.out.println(error.get("error_description")); // "the error description for developers"
			System.out.println(error.get("error_description_for_user")); // "user-friendly error desc for users"
			System.out.println(error.get("exception_id")); // "the server side developer can use this id to do troubleshooting"
		}

		// call a business web service
		NewPostRequest bizRequest = new NewPostRequest();
		bizRequest.setContent("my-first-post");
		HttpResponse<String> bizResponse = Unirest
				.post("http://localhost:8080/fo/rest/bbs/posts/new")
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + accessToken)
				.body(toJson(bizRequest)).asString();

		if (bizResponse.getStatus() == 200) {
			Post post = fromJson(bizResponse.getBody(), Post.class);
			System.out.println(post);
		}

		else if (Arrays.asList(400, 401, 403).contains(bizResponse.getStatus())) { // "token error"
			String authHeader = bizResponse.getHeaders()
					.get("WWW-Authenticate").get(0);// "See https://tools.ietf.org/html/rfc6750#page-7"			
			System.out.println(bizResponse.getStatus());
			System.out.println(authHeader); //You can also further parse auth header if needed. Search "decodeOAuthHeader" in this repository.  
			//You should then redirect the user to login UI
		}

		else if (bizResponse.getStatus() == 460) { // "biz error"
			JSONObject error = new JSONObject(bizResponse.getBody());
			System.out.println(error.get("error")); // "the error code"
			System.out.println(error.get("error_description")); // "the error description for developers"
			System.out.println(error.get("error_description_for_user")); // "user-friendly error desc for users"
			System.out.println(error.get("exception_id")); // "the server side developer can use this id to do troubleshooting"
		} else {
			System.out.println(bizResponse.getStatus());
			System.out.println(bizResponse.getBody());
		}

		// logout
		Unirest.post("http://localhost:8080/fo/rest/bbs/posts/delete")
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + accessToken).asJson();

	}

	private static Map<String, String> decodeOAuthHeader(String header) {
		Map<String, String> headerValues = new HashMap<String, String>();
		if (header != null) {
			Matcher m = Pattern.compile("\\s*(\\w*)\\s+(.*)").matcher(header);
			if (m.matches()) {
				if ("Bearer".equalsIgnoreCase(m.group(1))) {
					for (String nvp : m.group(2).split("\\s*,\\s*")) {
						m = Pattern.compile("(\\S*)\\s*\\=\\s*\"([^\"]*)\"")
								.matcher(nvp);
						if (m.matches()) {
							String name = DemoClientUtils.urlDecode(m.group(1));
							String value = DemoClientUtils
									.urlDecode(m.group(2));
							headerValues.put(name, value);
						}
					}
				}
			}
		}
		return headerValues;
	}

}