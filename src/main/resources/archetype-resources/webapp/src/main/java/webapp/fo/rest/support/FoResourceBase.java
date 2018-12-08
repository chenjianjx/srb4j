package com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.support;

import javax.ws.rs.container.ContainerRequestContext;

/**
 * the parent class of all Fo restful resourses
 * 
 * @author chenjianjx@gmail.com
 *
 */
public abstract class FoResourceBase {

	public static final String RS_CURRENT_USER_ID = "RS_CURRENT_USER_ID";
	public static final String RS_ACCESS_TOKEN = "RS_ACCESS_TOKEN";
		

	protected Long getUserId(ContainerRequestContext context) {
		return (Long) context.getProperty(RS_CURRENT_USER_ID);
	}
	
	

}
