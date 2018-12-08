package com.github.chenjianjx.srb4jfullsample.webapp.infrahelper.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Inject some request parameter here to work around oltu
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class OAuth2RequestWrapper extends FormRequestWrapper {

	private static final String DEFALUT_CLIENT_ID = "default-client";

	public OAuth2RequestWrapper(HttpServletRequest request,
			MultivaluedMap<String, String> form) {
		super(request, form);
	}

	@Override
	public String getParameter(String name) {

		if ("client_id".equals(name)) {
			return DEFALUT_CLIENT_ID;
		}
		return super.getParameter(name);
	}
}
