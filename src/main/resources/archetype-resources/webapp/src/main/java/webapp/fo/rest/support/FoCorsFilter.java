package ${package}.webapp.fo.rest.support;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 *
 */
@Provider
@Component
public class FoCorsFilter implements ContainerResponseFilter {

	private String corsOriginHeader;

	@Override
	public void filter(ContainerRequestContext request,
			ContainerResponseContext response) {
		if (corsOriginHeader == null) {
			return;
		}

		MultivaluedMap<String, Object> headers = response.getHeaders();
		headers.add("Access-Control-Allow-Origin", corsOriginHeader);
		headers.add("Access-Control-Allow-Methods",
				"GET, POST, PUT, DELETE, OPTIONS, HEAD");
		headers.add(
				"Access-Control-Allow-Headers",
				"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");		
		headers.add("Access-Control-Expose-Headers", "WWW-Authenticate");
	}

	@Value("${corsOriginHeader}")
	public void setCorsOriginHeader(String corsOriginHeader) {
		this.corsOriginHeader = StringUtils.trimToNull(corsOriginHeader);
	}

}