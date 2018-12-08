package com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.staffuser;


import com.github.chenjianjx.srb4jfullsample.intf.bo.basic.BoConstants;
import com.github.chenjianjx.srb4jfullsample.intf.bo.basic.BoResponse;
import com.github.chenjianjx.srb4jfullsample.intf.bo.staffuser.BoChangePasswordRequest;
import com.github.chenjianjx.srb4jfullsample.intf.bo.staffuser.BoStaffUserManager;
import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.ErrorResult;
import com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoMvcModel;
import com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoPortalAlert.BoAlertType;
import com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoSessionHelper;
import com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoSessionStaffUser;
import com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoUrlHelper;
import org.apache.commons.lang3.StringUtils;
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
import java.util.LinkedHashMap;
import java.util.Map;

import static com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoResourcePaths.CHANGE_PASSWORD;
import static com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoResourcePaths.DASHBOARD;
import static com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support.BoSessionHelper.getSessionStaffUserId;

@Path("/")
@Controller
@Produces(MediaType.TEXT_HTML)
public class BoStaffUserController {


    private static final String CHANGE_PASSWORD_FORM_VIEW = "/staffuser/changePassword";

    @Resource
    BoStaffUserManager boStaffUserManager;

    @GET
    @Path(CHANGE_PASSWORD)
    public Viewable changePasswordForm(@Context HttpServletRequest servletRequest) {
        BoSessionStaffUser sessionStaffUser = BoSessionHelper.getStaffUser(servletRequest.getSession());

        Map<String, Object> model = new LinkedHashMap<>();
        model.put("username", sessionStaffUser.getUsername());

        if (sessionStaffUser.isMustChangePassword() && sessionStaffUser.getChangePasswordReason() == BoChangePasswordReason.FIRST_TIME_LOGIN) {
            model.put("changeReason", "You have to change the password for the first time login");
        }

        return new Viewable(CHANGE_PASSWORD_FORM_VIEW, model);
    }

    @POST
    @Path(CHANGE_PASSWORD)
    public Object changePassword(@FormParam("currentPassword") String currentPassword,
                                 @FormParam("newPassword") String newPassword,
                                 @FormParam("confirmPassword") String confirmPassword,
                                 @Context HttpServletRequest servletRequest) throws MalformedURLException, URISyntaxException {

        BoSessionStaffUser sessionStaffUser = BoSessionHelper.getStaffUser(servletRequest.getSession());

        BoMvcModel model = BoMvcModel.newInstance(servletRequest);
        model.put("currentPassword", currentPassword);
        model.put("newPassword", newPassword);
        model.put("confirmPassword", confirmPassword);

        if (!StringUtils.equals(newPassword, confirmPassword)) {
            ErrorResult err = BoResponse.userErrResponse(BoConstants.FEC_INVALID_INPUT, "The new passwords don't match", null).getErr();
            model.setError(err);
            return new Viewable(CHANGE_PASSWORD_FORM_VIEW, model);
        }

        BoChangePasswordRequest request = new BoChangePasswordRequest();
        request.setCurrentPassword(currentPassword);
        request.setNewPassword(newPassword);

        BoResponse<Void> response = boStaffUserManager.changePassword(getSessionStaffUserId(servletRequest.getSession()), request);

        if (response.isSuccessful()) {

            HttpSession session = servletRequest.getSession(true);

            //reset the session staff user
            BoSessionStaffUser newSessionStaffUser = new BoSessionStaffUser();
            newSessionStaffUser.setUserId(sessionStaffUser.getUserId());
            newSessionStaffUser.setUsername(sessionStaffUser.getUsername());
            BoSessionHelper.setStaffUser(session, newSessionStaffUser);

            BoSessionHelper.setFlashScopeAlert(session, BoAlertType.success, "Password changed.");

            return Response.seeOther(BoUrlHelper.path2URI(DASHBOARD)).build();
        } else {
            ErrorResult err = response.getErr();
            if (StringUtils.isBlank(confirmPassword)) {
                Map<String, String> fieldErrors = err.getFieldUserErrors() == null ? new LinkedHashMap<>() : new LinkedHashMap<>(err.getFieldUserErrors());
                fieldErrors.put("confirmPassword", "Please repeat the new password");
                err.setFieldUserErrors(fieldErrors);
            }

            model.setError(err);
            return new Viewable(CHANGE_PASSWORD_FORM_VIEW, model);
        }

    }

}
