package ${package}.webapp.fo.rest.support;

import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 * This class is introduced to avoid problems as mentioned <a href=
 * "http://stackoverflow.com/questions/5833147/jersey-jackson-exception-problem-with-exceptionmapper"
 * >here</a>
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class FoRestApplication extends ResourceConfig {

	public FoRestApplication() {
		register(JacksonJaxbJsonProvider.class);
	}
}
