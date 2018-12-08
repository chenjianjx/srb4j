package com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support;


import org.apache.commons.lang3.StringUtils;

import java.net.URI;

public class BoUrlHelper {

    public static String path2Url(String resourcePath) {
        String url = BoPortalConstants.BO_PORTAL_URL_BASE + "/" + StringUtils.stripStart(resourcePath, "/");
        return url;
    }

    public static URI path2URI(String resourcePath) {
        return URI.create(path2Url(resourcePath));
    }
}
