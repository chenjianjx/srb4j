package com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.support;

import static com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.support.FoResourceBase.RS_ACCESS_TOKEN;
import static com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.support.FoResourceBase.RS_CURRENT_USER_ID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.error.OAuthError.ResourceResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.stereotype.Component;
import com.github.chenjianjx.srb4jfullsample.intf.fo.auth.FoAccessToken;
import com.github.chenjianjx.srb4jfullsample.intf.fo.auth.FoAuthManager;
import com.github.chenjianjx.srb4jfullsample.intf.fo.misc.MiscBiz;

/**
 * A filter that serves as an OAuth2 resource end point
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Provider
@PreMatching
@Component
public class FoAuthFilter implements ContainerRequestFilter {

	@Context
	private javax.inject.Provider<HttpServletRequest> servletRequestProvider;

	@Resource
	FoAuthManager foAuthManager;

	@Resource
	MiscBiz miscBiz;

	@Override
	public void filter(ContainerRequestContext requestContext) {
		try {
			doFilter(requestContext);
		} catch (OAuthSystemException e) {
			throw new RuntimeException(e);
		}

	}

	private void doFilter(ContainerRequestContext requestContext)
			throws OAuthSystemException {

		HttpServletRequest servletRequest = servletRequestProvider.get();
		String authzHeader = servletRequest
				.getHeader(OAuth.HeaderType.AUTHORIZATION);
		if (StringUtils.isBlank(authzHeader)) {
			/**
			 * no token. maybe the client is trying to access public resources.
			 * If it turns out the resource requires login, the resource will
			 * throw an error response with error code = not login yet, which in
			 * return will be translated to oauth2 error response
			 */
			return;
		}

		String realm = requestContext.getUriInfo().getBaseUri().toString();

		OAuthAccessResourceRequest oauthRequest = null;
		try {
			oauthRequest = new OAuthAccessResourceRequest(servletRequest,
					ParameterStyle.HEADER);
		} catch (OAuthProblemException e) {

			// Check if the error code has been set
			String errorCode = e.getError();

			if (OAuthUtils.isEmpty(errorCode)) {
				errorCode = ResourceResponse.INVALID_REQUEST;
			}

			OAuthResponse oltuResponse = OAuthRSResponse
					.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.setRealm(realm).setError(e.getError())
					.setErrorDescription(e.getDescription())
					.setErrorUri(e.getUri()).buildHeaderMessage();

			requestContext.abortWith(toRestResponse(oltuResponse));
			return;

		}

		// Get the access token
		String accessToken = oauthRequest.getAccessToken();

		// get the token from db
		FoAccessToken fat = miscBiz.getAccessTokenByTokenStr(accessToken);

		// check token
		if (fat == null) {// invalid token
			OAuthResponse oltuResponse = OAuthRSResponse
					.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
					.setRealm(realm)
					.setError(OAuthError.ResourceResponse.INVALID_TOKEN)
					.buildHeaderMessage();
			requestContext.abortWith(toRestResponse(oltuResponse));
			return;
		}

		if (fat.hasExpired()) {
			OAuthResponse oltuResponse = OAuthRSResponse
					.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
					.setRealm(realm)
					.setError(OAuthError.ResourceResponse.EXPIRED_TOKEN)
					.buildHeaderMessage();
			requestContext.abortWith(toRestResponse(oltuResponse));
			return;
		}

		// put sth into context
		requestContext.setProperty(RS_CURRENT_USER_ID, fat.getUserId());
		requestContext.setProperty(RS_ACCESS_TOKEN, fat.getTokenStr());
	}

	private Response toRestResponse(OAuthResponse oauthResponse) {
		return Response
				.status(oauthResponse.getResponseStatus())
				.header(OAuth.HeaderType.WWW_AUTHENTICATE,
						oauthResponse
								.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE))
				.entity(oauthResponse.getBody()).build();
	}

}
