package com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.BpPackageAnchor;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

/**
 * @author chenjianjx@gmail.com
 */
public class BoPortalApplication extends ResourceConfig {

    public static final String JSP_DIR = "/WEB-INF/jsp/bo-portal";

    public BoPortalApplication() {
        register(JacksonJaxbJsonProvider.class);

        property(JspMvcFeature.TEMPLATE_BASE_PATH, JSP_DIR);
        register(JspMvcFeature.class);

        packages(BpPackageAnchor.class.getPackage().getName());
    }
}
