package com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.support;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

/**
 * <a href=
 * "http://stackoverflow.com/questions/5514087/jersey-rest-default-character-encoding/20569571"
 * >make sure utf8 is used in the response</a>
 * 
 * 
 *
 */
@Provider
@Component
public class FoCharsetResponseFilter implements ContainerResponseFilter {
	@Override
	public void filter(ContainerRequestContext request,
			ContainerResponseContext response) {
		MediaType type = response.getMediaType();
		if (type != null) {
			String contentType = type.toString();
			if (!contentType.contains("charset")) {
				contentType = contentType + ";charset=utf-8";
				response.getHeaders().putSingle("Content-Type", contentType);
			}
		}
	}
}