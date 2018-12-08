package com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support;

import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.ErrorResult;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

import static com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoPortalConstants.BO_PORTAL_URL_BASE;
import static com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoPortalConstants.MVC_KEY_ERR;
import static com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoPortalConstants.MVC_KEY_RELATIVE_URL;

/**
 * Please always use this class for the mvc model, instead of a plain Map
 */
public class BoMvcModel extends LinkedHashMap<String, Object> {


    private BoMvcModel() {

    }

    public static BoMvcModel newInstance(HttpServletRequest servletRequest) {
        BoMvcModel model = new BoMvcModel();
        model.put(MVC_KEY_RELATIVE_URL, servletRequest.getRequestURI().substring(BO_PORTAL_URL_BASE.length()));
        return model;
    }

    public BoMvcModel setError(ErrorResult err) {
        this.put(MVC_KEY_ERR, err);
        return this;
    }

}
