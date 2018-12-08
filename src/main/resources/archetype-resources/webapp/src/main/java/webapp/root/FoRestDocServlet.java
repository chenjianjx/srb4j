package com.github.chenjianjx.srb4jfullsample.webapp.root;

import com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.support.FoRestUtils;
import com.github.chenjianjx.srb4jfullsample.webapp.system.WebAppEnvProp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.swagger2html.Swagger2Html;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;

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
		String contextPath = servletConfig.getServletContext().getContextPath();
		swaggerUrl = FoRestUtils.getResourceBasePath(props, contextPath) + "/swagger.json";
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {

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
