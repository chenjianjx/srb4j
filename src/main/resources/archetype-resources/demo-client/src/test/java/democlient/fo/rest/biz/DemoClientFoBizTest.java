package ${package}.democlient.fo.rest.biz;

import static ${package}.democlient.util.DemoClientConstants.BACKEND_FO_REST_URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;
import ${package}.democlient.fo.rest.auth.DemoClientFoAuthTest;
import ${package}.democlient.util.DemoClientUtils;
import ${package}.restclient.model.NewPostRequest;
import ${package}.restclient.model.Post;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class DemoClientFoBizTest {
	private static final String BIZ_RESOURCE_URL = "/bbs/posts/new";
	private Client restClient = ClientBuilder.newClient();

	@Test
	public void bizTest() {

		// get a token first
		DemoClientFoAuthTest auth = new DemoClientFoAuthTest();
		String token = auth.doRegister();

		// access resource
		Builder restRequest = restClient.target(BACKEND_FO_REST_URL)
				.path(BIZ_RESOURCE_URL).request();

		restRequest = restRequest.header("Authorization", "Bearer " + token);
		NewPostRequest bizRequest = new NewPostRequest();
		bizRequest.setContent("Hi, welcome");
		Response restResponse = restRequest.post(Entity.entity(bizRequest,
				MediaType.APPLICATION_JSON));
		// parse the response
		DemoClientFoBizResponse<Post> bizResponse = DemoClientFoBizResponse
				.parseRestResponse(restResponse, Post.class);

		// deal with the response
		Assert.assertTrue(bizResponse.isSuccessful());
		Post post = bizResponse.data;
		Assert.assertNotNull(post);
		System.out.println("Successful. The data are: ");
		System.out.println(post);
		return;
	}

	@Test
	public void bizTest_WrongToken() {

		// access resource
		Builder restRequest = restClient.target(BACKEND_FO_REST_URL)
				.path(BIZ_RESOURCE_URL).request();

		restRequest = restRequest.header("Authorization", "Bearer "
				+ "some-wrong-token");
		NewPostRequest bizRequest = new NewPostRequest();
		bizRequest.setContent("Hi, welcome");
		Response restResponse = restRequest.post(Entity.entity(bizRequest,
				MediaType.APPLICATION_JSON));

		// parse the response
		DemoClientFoBizResponse<Post> bizResponse = DemoClientFoBizResponse
				.parseRestResponse(restResponse, Post.class);

		// deal with the response
		DemoClientUtils.printErrorResult(bizResponse.oauth2Error);
		Assert.assertTrue(bizResponse.isOauth2Error());
		Assert.assertTrue(bizResponse.isOauth2InvalidToken());

	}

	@Test
	public void bizTest_InvalidBizData() {

		// get a token first
		DemoClientFoAuthTest auth = new DemoClientFoAuthTest();
		String token = auth.doRegister();

		// access resource
		Builder restRequest = restClient.target(BACKEND_FO_REST_URL)
				.path(BIZ_RESOURCE_URL).request();

		restRequest = restRequest.header("Authorization", "Bearer " + token);
		NewPostRequest bizRequest = new NewPostRequest();
		bizRequest.setContent(null); // null content is not allowed by the
										// backend
		Response restResponse = restRequest.post(Entity.entity(bizRequest,
				MediaType.APPLICATION_JSON));
		// parse the response
		DemoClientFoBizResponse<Post> bizResponse = DemoClientFoBizResponse
				.parseRestResponse(restResponse, Post.class);

		Assert.assertTrue(bizResponse.isBizError());
		System.out.println("Biz Error");
		DemoClientUtils.printErrorResult(bizResponse.bizError);

	}
}
