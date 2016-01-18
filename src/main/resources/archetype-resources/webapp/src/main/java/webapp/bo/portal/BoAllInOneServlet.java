package ${package}.webapp.bo.portal;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ${package}.intf.bo.auth.BoAuthManager;
import ${package}.intf.bo.auth.BoLocalLoginRequest;
import ${package}.intf.bo.auth.BoLoginResult;
import ${package}.intf.bo.bbs.BoBbsManager;
import ${package}.intf.bo.bbs.BoPost;
import ${package}.intf.fo.basic.FoResponse;

/**
 * a sample servlet for back office portal to show how bo-portal talks to
 * bo-intf
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class BoAllInOneServlet extends HttpServlet {

	private static final long serialVersionUID = 3548138578763186431L;

	private static final String SESSION_KEY_BO_USER_ID = "sessionBoUserId";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getRequestURI();

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());

		BoAuthManager boAuthManager = ctx.getBean(BoAuthManager.class);
		BoBbsManager boBbsManager = ctx.getBean(BoBbsManager.class);

		if (action.endsWith("loginForm")) {
			gotoLoginPage(req, resp);
			return;
		}

		if (action.endsWith("login")) {
			BoLocalLoginRequest loginRequest = new BoLocalLoginRequest();
			loginRequest.setEmail(req.getParameter("email"));
			loginRequest.setPassword(req.getParameter("password"));
			FoResponse<BoLoginResult> loginResponse = boAuthManager
					.localLogin(loginRequest);

			if (loginResponse.isSuccessful()) {
				req.getSession().setAttribute(SESSION_KEY_BO_USER_ID,
						loginResponse.getData().getUserId());
				gotoMenuPage(req, resp);
				return;
			} else {
				req.setAttribute("err", loginResponse.getErr());
				gotoLoginPage(req, resp);
				return;
			}
		}

		if (action.endsWith("logout")) {
			req.getSession().removeAttribute(SESSION_KEY_BO_USER_ID);
			gotoLoginPage(req, resp);
			return;
		}

		Long currentUserId = (Long) req.getSession().getAttribute(
				SESSION_KEY_BO_USER_ID);
		if (currentUserId == null) {
			gotoLoginPage(req, resp);
			return;
		}

		if (action.endsWith("bbs")) {
			FoResponse<List<BoPost>> postListResult = boBbsManager
					.getAllPostsForBbsAdmin(currentUserId);
			req.setAttribute("postListResult", postListResult);
			gotoBbsPage(req, resp);
			return;
		}
		gotoMenuPage(req, resp);
	}

	private void gotoBbsPage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/bo-portal/bbs.jsp").forward(req, resp);
	}

	private void gotoLoginPage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/bo-portal/loginForm.jsp").forward(req, resp);
	}

	private void gotoMenuPage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/bo-portal/menu.jsp").forward(req, resp);
	}

}
