package ${groupId}.democlient.fo.rest.auth;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import ${groupId}.restclient.model.AuthTokenResult;
import ${groupId}.restclient.model.ErrorResult;

/**
 * The response object of visiting OAuth2 token point (login/register)
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class DemoClientFoAuthTokenResponse {
	public AuthTokenResult tokenResult;
	public ErrorResult errorResult;
	public int httpCode;

	public boolean isSuccessful() {
		return httpCode == 200;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public static DemoClientFoAuthTokenResponse parseRestResponse(
			Response restResponse) {
		DemoClientFoAuthTokenResponse clientResponse = new DemoClientFoAuthTokenResponse();
		clientResponse.httpCode = restResponse.getStatus();
		if (restResponse.getStatus() == 200) {
			clientResponse.tokenResult = restResponse
					.readEntity(AuthTokenResult.class);
			return clientResponse;
		} else {
			clientResponse.errorResult = restResponse
					.readEntity(ErrorResult.class);
			return clientResponse;
		}
	}

}
