package ${package}.webapp.fo.rest.support;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import ${package}.webapp.fo.rest.FrPackageAnchor;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author chenjianjx@gmail.com
 */
public class FoRestApplication extends ResourceConfig {

    public FoRestApplication() {
        register(JacksonJaxbJsonProvider.class);
        packages("io.swagger.jaxrs.listing", FrPackageAnchor.class.getPackage().getName());
    }
}
