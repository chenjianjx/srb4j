package com.github.chenjianjx.srb4jfullsample.impl.fo.auth.socialsite;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.chenjianjx.srb4jfullsample.impl.biz.user.User;
import com.github.chenjianjx.srb4jfullsample.utils.lang.MyDuplet;
import com.github.chenjianjx.srb4jfullsample.intf.fo.auth.FoAuthTokenResult;
import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoResponse;

/**
 * To get something from social site's token or auth code
 * 
 * @author chenjianjx
 *
 */
public interface FoSocialSiteAuthHelper {

	/**
	 * 
	 * @param token
	 * @param clientType
	 * @return left = email, right = error response if any
	 */
	MyDuplet<String, FoResponse<FoAuthTokenResult>> getEmailFromToken(
			String token, String clientType);

	/**
	 * 
	 * @param authCode
	 * @param clientType
	 * @param redirectUri
	 * @return left = email, right = error response if any
	 */
	MyDuplet<String, FoResponse<FoAuthTokenResult>> getEmailFromCode(
			String authCode, String clientType, String redirectUri);

	@Service
	public static final class Factory {
		@Resource
		FoGoogleAuthHelper googleAuthHelper;

		@Resource
		FoFacebookAuthHelper facebookAuthHelper;

		private Map<String, FoSocialSiteAuthHelper> helpers = new HashMap<String, FoSocialSiteAuthHelper>();

		@PostConstruct
		private void init() {
			helpers.put(User.SOURCE_GOOGLE, googleAuthHelper);
			helpers.put(User.SOURCE_FACEBOOK, facebookAuthHelper);
		}

		public FoSocialSiteAuthHelper getHelper(String source) {
			return helpers.get(source);
		}

	}
}
