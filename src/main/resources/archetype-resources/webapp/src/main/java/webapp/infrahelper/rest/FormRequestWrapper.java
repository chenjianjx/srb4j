package com.github.chenjianjx.srb4jfullsample.webapp.infrahelper.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;

/**
 * Sometimes we need to obtain the original {@link HttpServletRequest} object
 * but Jersey may have already consumed it and make all the parameters null.
 * This class is the workaround solution to restore the parameters. It also does
 * some trimming work.
 * 
 * @see <a
 *      href="http://stackoverflow.com/questions/22376810/unable-to-retrive-post-data-using-context-httpservletrequest-when-passed-to-oa">the
 *      solution on stackoverflow</a>
 * 
 * 
 * 
 *
 */
public class FormRequestWrapper extends HttpServletRequestWrapper {

	private MultivaluedMap<String, String> form;

	public FormRequestWrapper(HttpServletRequest request,
			MultivaluedMap<String, String> form) {
		super(request);
		this.form = form;
	}

	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (value == null) {
			value = form.getFirst(name);
		}
		return StringUtils.trimToNull(value);
	}
}
