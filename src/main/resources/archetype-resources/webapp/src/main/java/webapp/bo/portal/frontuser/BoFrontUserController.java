package com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.frontuser;


import com.github.chenjianjx.srb4jfullsample.intf.bo.basic.BoResponse;
import com.github.chenjianjx.srb4jfullsample.intf.bo.frontuser.BoFrontUserManager;
import com.github.chenjianjx.srb4jfullsample.intf.fo.user.FoUser;
import com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoMvcModel;
import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoResourcePaths.FRONT_USER_LIST;
import static com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoSessionHelper.getSessionStaffUserId;

@Path("/")
@Controller
@Produces(MediaType.TEXT_HTML)
public class BoFrontUserController {


    private static final String USER_LIST_PAGE = "/frontuser/userList";

    @Resource
    BoFrontUserManager boFrontUserManager;

    @GET
    @Path(FRONT_USER_LIST)
    public Viewable frontUserList(@Context HttpServletRequest servletRequest) {
        BoResponse<List<FoUser>> response = boFrontUserManager.getAllUsers(getSessionStaffUserId(servletRequest.getSession()));

        BoMvcModel model = BoMvcModel.newInstance(servletRequest);
        model.put("users", response.getData());

        return new Viewable(USER_LIST_PAGE, model);
    }
}
