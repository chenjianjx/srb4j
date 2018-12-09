package ${package}.webapp.bo.portal.auth;


import ${package}.intf.bo.auth.BoAuthManager;
import ${package}.intf.bo.auth.BoLoginRequest;
import ${package}.intf.bo.auth.BoLoginResult;
import ${package}.intf.bo.basic.BoConstants;
import ${package}.intf.fo.basic.ErrorResult;
import ${package}.intf.fo.basic.FoResponse;
import ${package}.webapp.bo.portal.staffuser.BoChangePasswordReason;
import ${package}.webapp.bo.portal.support.BoMvcModel;
import ${package}.webapp.bo.portal.support.BoSessionHelper;
import ${package}.webapp.bo.portal.support.BoSessionStaffUser;
import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static ${package}.webapp.bo.portal.support.BoResourcePaths.CHANGE_PASSWORD;
import static ${package}.webapp.bo.portal.support.BoResourcePaths.DASHBOARD;
import static ${package}.webapp.bo.portal.support.BoResourcePaths.LOGIN;
import static ${package}.webapp.bo.portal.support.BoResourcePaths.LOGOUT;
import static ${package}.webapp.bo.portal.support.BoUrlHelper.path2URI;

@Path("/")
@Controller
@Produces(MediaType.TEXT_HTML)
public class BoAuthController {

    public static final String LOGIN_FORM_VIEW = "/auth/loginForm";
    @Resource
    BoAuthManager boAuthManager;

    @GET
    @Path(LOGIN)
    public Viewable loginForm(@Context HttpServletRequest servletRequest) {
        invalidateSession(servletRequest);
        return new Viewable(LOGIN_FORM_VIEW, BoMvcModel.newInstance(servletRequest));
    }

    @POST
    @Path(LOGIN)
    public Object login(@FormParam("username") String username, @FormParam("password") String password,
                        @Context HttpServletRequest servletRequest) throws MalformedURLException, URISyntaxException {
        invalidateSession(servletRequest);

        BoLoginRequest loginRequest = new BoLoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        FoResponse<BoLoginResult> loginResponse = boAuthManager.login(loginRequest);


        if (loginResponse.isSuccessful()) {
            BoLoginResult loginResult = loginResponse.getData();

            BoSessionStaffUser staffUser = new BoSessionStaffUser();
            staffUser.setUsername(loginResult.getUserName());
            staffUser.setUserId(loginResult.getUserId());
            BoSessionHelper.setStaffUser(servletRequest.getSession(true), staffUser);

            return Response.seeOther(path2URI(DASHBOARD)).build();
        } else {
            ErrorResult err = loginResponse.getErr();
            if (BoConstants.BEC_FIRST_LOGIN_MUST_CHANGE_PASSWORD.equals(err.getErrorCode())) {

                BoLoginResult loginResult = loginResponse.getData();

                BoSessionStaffUser staffUser = new BoSessionStaffUser();
                staffUser.setUsername(loginResult.getUserName());
                staffUser.setUserId(loginResult.getUserId());
                staffUser.setMustChangePassword(true);
                staffUser.setChangePasswordReason(BoChangePasswordReason.FIRST_TIME_LOGIN);
                BoSessionHelper.setStaffUser(servletRequest.getSession(true), staffUser);

                return Response.seeOther(path2URI(CHANGE_PASSWORD)).build();

            }

            BoMvcModel model = BoMvcModel.newInstance(servletRequest).setError(err);
            model.put("username", username);  //let the form render the value the user has input
            model.put("password", password);
            return new Viewable(LOGIN_FORM_VIEW, model);
        }
    }


    @GET
    @Path(LOGOUT)
    public Response logout(@Context HttpServletRequest servletRequest) {
        invalidateSession(servletRequest);
        return Response.seeOther(path2URI(DASHBOARD)).build();
    }

    private void invalidateSession(HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        if (session != null) {
            session.invalidate();
        }
    }
}
