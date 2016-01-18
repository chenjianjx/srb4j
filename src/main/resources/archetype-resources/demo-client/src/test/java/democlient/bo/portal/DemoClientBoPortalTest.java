package ${groupId}.${rootArtifactId}.democlient.bo.portal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;
import ${groupId}.${rootArtifactId}.democlient.util.DemoClientConstants;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class DemoClientBoPortalTest {

	private static final String BBSADMIN_EMAIL = "bbsadmin@nonexist.com";
	private static Client mockClient = ClientBuilder.newClient();

	@Test
	public void loginFormTest() {
		Response response = mockClient
				.target(DemoClientConstants.BACKEND_BO_PORTAL_URL)
				.path(DemoClientConstants.BO_LOGIN_PATH).request().get();
		Assert.assertEquals(200, response.getStatus());
	}

	@Test
	public void loginTest() {

		Form form = new Form();
		form.param("email", BBSADMIN_EMAIL);
		form.param("password", "abc123456");

		Response response = mockClient
				.target(DemoClientConstants.BACKEND_BO_PORTAL_URL)
				.path(DemoClientConstants.BO_LOGIN_PATH).request()
				.post(Entity.form(form));
		Assert.assertEquals(200, response.getStatus());
		Assert.assertTrue(response.readEntity(String.class).contains("Back Office Menu"));
	}

	
	@Test
	public void loginTest_WrongPassword() {

		Form form = new Form();
		form.param("email", BBSADMIN_EMAIL);
		form.param("password", "wrong");

		Response response = mockClient
				.target(DemoClientConstants.BACKEND_BO_PORTAL_URL)
				.path(DemoClientConstants.BO_LOGIN_PATH).request()
				.post(Entity.form(form));
		Assert.assertEquals(200, response.getStatus());
		Assert.assertTrue(response.readEntity(String.class).contains("Invalid Password"));
	}
	
}
