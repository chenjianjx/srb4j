package com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.support;

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.FEC_NOT_LOGIN_YET;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.FO_SC_BIZ_ERROR;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

import com.github.chenjianjx.srb4jfullsample.webapp.system.WebAppEnvProp;
import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError.ResourceResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoResponse;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class FoRestUtils {

	private static final String EMPTY_JSON = "{}";

	/**
	 * convert app layer response to jax-rs response
	 * 
	 * @param foResponse
	 * @return
	 */
	public static <T> Response fromFoResponse(FoResponse<T> foResponse,
			ContainerRequestContext context) {
		if (!foResponse.isSuccessful()
				&& FEC_NOT_LOGIN_YET.equals(foResponse.getErr().getErrorCode())) {
			return buildNotLoginRestResponse(context);
		}

		return fromFoResponse(foResponse, FO_SC_BIZ_ERROR);
	}

	private static <T> Response buildNotLoginRestResponse(
			ContainerRequestContext context) {
		try {
			String realm = context.getUriInfo().getBaseUri().toString();
			OAuthResponse oltuResponse = OAuthRSResponse
					.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.setRealm(realm).setError(ResourceResponse.INVALID_REQUEST)
					.setErrorDescription("Missing auth token")
					.buildHeaderMessage();
			return Response
					.status(oltuResponse.getResponseStatus())
					.header(OAuth.HeaderType.WWW_AUTHENTICATE,
							oltuResponse
									.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE))
					.entity(oltuResponse.getBody()).build();
		} catch (OAuthSystemException e) {
			throw new RuntimeException(e);
		}
	}

 

	public static <T> Response fromFoResponse(FoResponse<T> foResponse,
			int statusCodeIfErr) {
		if (foResponse == null) {
			return null;
		}
		if (foResponse.isSuccessful()) {
			T data = foResponse.getData();
			if (data == null) {
				return Response.status(SC_OK).entity(EMPTY_JSON).build();
			} else {
				return Response.status(SC_OK).entity(data).build();
			}

		} else {
			return Response.status(statusCodeIfErr).entity(foResponse.getErr())
					.build();
		}
	}

	public static String getResourceBasePath(WebAppEnvProp props, String contextPath) {
		String schemeAndHost = props.getSchemeAndHost();
		String afterHost = StringUtils.replace(contextPath + "/fo/rest", "//",
				"/");
		if (afterHost.startsWith("/")) {
			afterHost = afterHost.substring(1);
		}
		String url = schemeAndHost + afterHost;
		return url;
	}
}
