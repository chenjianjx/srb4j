package com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.home;

import com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoMvcModel;
import com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoResourcePaths;
import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;


@Controller
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class BoHomeController {

    @GET
    @Path(BoResourcePaths.DASHBOARD)
    public Viewable dashboard(@Context HttpServletRequest servletRequest) {
        BoMvcModel model = BoMvcModel.newInstance(servletRequest);
        return new Viewable("/home/dashboard", model);
    }
}