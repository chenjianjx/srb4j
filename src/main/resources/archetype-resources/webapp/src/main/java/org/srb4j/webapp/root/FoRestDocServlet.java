package org.srb4j.webapp.root;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.srb4j.webapp.fo.rest.support.FoSwaggerJaxrsConfig;
import org.srb4j.webapp.infrahelper.WebAppEnvProp;
import org.swagger2html.Swagger2Html;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class FoRestDocServlet extends HttpServlet {

	private String restDoc;
	private static final long serialVersionUID = 564401476689261001L;
	private String swaggerUrl;
	private WebAppEnvProp props;

	private static final Logger logger = LoggerFactory
			.getLogger(FoRestDocServlet.class);

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		props = ctx.getBean(WebAppEnvProp.class);
		swaggerUrl = FoSwaggerJaxrsConfig.getResourceBasePath(servletConfig,
				props) + "/swagger.json";
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {

		if (!props.isEnableSwagger()) {
			resp.getWriter()
					.println(
							"The doc has been disabled because swagger has been disabled.");
			return;
		}

		if (restDoc == null) {
			restDoc = genDoc(request);
		}
		if (restDoc == null) {
			resp.getWriter().println(
					"Fail to load the document from " + swaggerUrl);
			return;
		}
		resp.setContentType("text/html");
		resp.getWriter().println(restDoc);

	}

	private String genDoc(HttpServletRequest request) throws IOException {
		try {
			StringWriter sw = new StringWriter();
			Swagger2Html s2h = new Swagger2Html();
			s2h.toHtml(swaggerUrl, sw);
			String doc = sw.toString();
			return doc;
		} catch (Exception e) {
			logger.error("Fail to load the document from " + swaggerUrl, e);
			return null;
		}
	}
}
