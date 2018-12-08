package com.github.chenjianjx.srb4jfullsample.impl.fo.auth.socialsite;

import com.github.chenjianjx.srb4jfullsample.impl.biz.client.Client;
import com.github.chenjianjx.srb4jfullsample.utils.lang.MyDuplet;
import com.github.chenjianjx.srb4jfullsample.intf.fo.auth.FoAuthTokenResult;
import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants;
import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoResponse;
import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Service
public class FoFacebookAuthHelper implements FoSocialSiteAuthHelper {

	private String facebookClientId;
	private String facebookClientSecret;

	@Value("${facebookClientId}")
	public void setFacebookClientId(String facebookClientId) {
		this.facebookClientId = StringUtils.trimToNull(facebookClientId);
	}

	@Value("${facebookClientSecret}")
	public void setFacebookClientSecret(String facebookClientSecret) {
		this.facebookClientSecret = StringUtils
				.trimToNull(facebookClientSecret);
	}

	@Override
	public MyDuplet<String, FoResponse<FoAuthTokenResult>> getEmailFromToken(
			String token, String clientType) {
		String accessToken = token;
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken,
				Version.VERSION_2_5);
		com.restfb.types.User user = facebookClient.fetchObject("me",
				com.restfb.types.User.class,
				Parameter.with("fields", "id,name,email"));
		if (user == null) {
			FoResponse<FoAuthTokenResult> errResp = FoResponse.devErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST,
					"invalid facebook access token", null);
			return MyDuplet.newInstance(null, errResp);
		}

		String email = user.getEmail();
		if (email == null) {
			FoResponse<FoAuthTokenResult> errResp = FoResponse
					.devErrResponse(
							FoConstants.FEC_OAUTH2_INVALID_REQUEST,
							"cannot extract email from this facebook access token. please check the scope used for facebook connect",
							null);
			return MyDuplet.newInstance(null, errResp);
		}
		return MyDuplet.newInstance(email, null);
	}

	@Override
	public MyDuplet<String, FoResponse<FoAuthTokenResult>> getEmailFromCode(
			String authCode, String clientType, String redirectUri) {

		if (Client.TYPE_DESKTOP.equals(clientType)) {
			if (redirectUri == null) {
				redirectUri = FoConstants.FACEBOOK_REDIRECT_URI_LOGIN_SUCCESS;
			}
		} else if (Client.TYPE_WEB.equals(clientType)) {
			if (redirectUri == null) {
				FoResponse<FoAuthTokenResult> errResp = FoResponse
						.devErrResponse(
								FoConstants.FEC_OAUTH2_INVALID_REQUEST,
								"redirect uri must not be empty for facebook + web ",
								null);
				return MyDuplet.newInstance(null, errResp);
			}
		} else {
			throw new IllegalArgumentException(
					"Currently we don't support google login with client type = "
							+ clientType);
		}

		// exchange the code for token
		final OAuth20Service service = new ServiceBuilder(facebookClientId)
				.apiSecret(facebookClientSecret)
				.scope("email").callback(redirectUri)
				.build(FacebookApi.instance());
		OAuth2AccessToken facebookTokenObj = null;
		try {
			facebookTokenObj = service.getAccessToken(authCode);
		} catch (IOException|InterruptedException|ExecutionException e) {
			throw new RuntimeException(e);
		}
		String token = facebookTokenObj.getAccessToken();
		// get email by token
		return this.getEmailFromToken(token, clientType);

	}

}
