package com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.support;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * 
 *
 */
@Provider
@Component
public class FoCorsFilter implements ContainerResponseFilter {

	/**
	 *
	 * Note: an element can be the wildcard (*)
	 */
	private List<String> corsAllowedOrigins;

	@Override
	public void filter(ContainerRequestContext request,
			ContainerResponseContext response) {

		String responseOriginHeader = decideResponseOriginHeader(corsAllowedOrigins, request.getHeaderString("Origin"));
		if (responseOriginHeader == null) {
			return;
		}

		MultivaluedMap<String, Object> headers = response.getHeaders();
		headers.add("Access-Control-Allow-Origin", responseOriginHeader);
		headers.add("Access-Control-Allow-Methods",
				"GET, POST, PUT, DELETE, OPTIONS, HEAD");
		headers.add(
				"Access-Control-Allow-Headers",
				"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");		
		headers.add("Access-Control-Expose-Headers", "WWW-Authenticate");
	}

	/**
	 * It can return null. If null value is returned, it means there should not be cors headers in the response
	 *
	 * @return
	 */
	String decideResponseOriginHeader(List<String> allowed, String origin) {
		if (allowed == null || allowed.isEmpty()) {
			return null;
		}
		if (allowed.contains("*")) {
			return "*";
		}
		if (allowed.contains(origin)) {
			return origin;
		}
		return null;
	}

	@Value("${corsAllowedOrigins}")
	public void setCorsAllowedOrigins(String corsAllowedOriginsStr) {
		List<String> corsAllowedOrigins = parseCorsAllowedOrigins(corsAllowedOriginsStr);
		this.corsAllowedOrigins = corsAllowedOrigins;
	}

	List<String> parseCorsAllowedOrigins(String corsAllowedOriginsStr) {
		List<String> resultList = new ArrayList<>();
		corsAllowedOriginsStr = StringUtils.trimToNull(corsAllowedOriginsStr);
		String[] originArray = StringUtils.split(corsAllowedOriginsStr, ";");
		if(originArray == null || originArray.length == 0){
			return resultList;
		}
		resultList = Arrays.asList(originArray).stream().map(o -> StringUtils.trimToNull(o)).filter(o -> o != null).collect(Collectors.toList());
		return resultList;
	}

}