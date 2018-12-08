package com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.support;

import com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.FrPackageAnchor;
import com.github.chenjianjx.srb4jfullsample.webapp.system.WebAppEnvProp;
import io.swagger.jaxrs.config.BeanConfig;
import org.apache.commons.io.IOUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class FoSwaggerJaxrsConfig extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6022464230486259162L;

	@Override
	public void init(ServletConfig servletConfig)
			throws javax.servlet.ServletException {

		super.init(servletConfig);

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		WebAppEnvProp props = ctx.getBean(WebAppEnvProp.class);

		String contextPath = servletConfig.getServletContext().getContextPath();
		String basePath = FoRestUtils.getResourceBasePath(props, contextPath);
		BeanConfig swaggerConfig = new BeanConfig();

		String scheme = null;
		String host = null;

		String[] parts = basePath.split("://");
		if (parts.length > 1) {
			int pos = parts[1].indexOf("/");
			if (pos >= 0) {
				scheme = parts[0];
				basePath = parts[1].substring(pos);
				host = parts[1].substring(0, pos);
			} else {
				scheme = parts[0];
				basePath = null;
				host = parts[1];
			}
		}

		String title = "srb4jfullsample";
		String email = "srb4jfullsample@gmail.com";
		String desc = getDesc();

		swaggerConfig.setSchemes(new String[] { scheme });
		swaggerConfig.setTitle(title);
		swaggerConfig.setVersion("1.0-SNAPSHOT");
		swaggerConfig.setDescription(desc);

		swaggerConfig.setContact(email);
		swaggerConfig.setHost(host);
		swaggerConfig.setBasePath(basePath);
		swaggerConfig.setPrettyPrint(true);
		swaggerConfig.setResourcePackage(FrPackageAnchor.class.getPackage()
				.getName());
		swaggerConfig.setScan(true);

	}


	private String getDesc() {
		try {
			return IOUtils.toString(FoSwaggerJaxrsConfig.class
					.getResourceAsStream("/swagger/desc.markdown"), "utf8");
		} catch (IOException e) {
			return null;
		}
	}

}
