package com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoResourcePaths.CHANGE_PASSWORD;
import static com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoResourcePaths.LOGIN;
import static com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoResourcePaths.LOGOUT;

public class BoSessionFilter implements Filter {

    private List<String> exempUrls = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        exempUrls.add(LOGIN);
        exempUrls.add(LOGOUT);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;


        String url = request.getRequestURI().substring(BoPortalConstants.BO_PORTAL_URL_BASE.length());
        if (!url.startsWith("/")) {
            url = "/" + url;
        }

        if (exempUrls.contains(url)) {
            chain.doFilter(req, resp);
            return;
        }

        BoSessionStaffUser sessionStaffUser = BoSessionHelper.getStaffUser(request.getSession());

        if (sessionStaffUser == null) {
            response.sendRedirect(BoUrlHelper.path2Url(LOGIN));
            return;
        }

        if (url.equals(CHANGE_PASSWORD)) {
            chain.doFilter(req, resp);
            return;
        }

        //user is there, but must change password
        if (sessionStaffUser.isMustChangePassword()) {
            response.sendRedirect(BoUrlHelper.path2Url(CHANGE_PASSWORD));
            return;
        }

        //everything is fine
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}
