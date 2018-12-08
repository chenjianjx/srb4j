package com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.user;

import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.ErrorResult;
import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoResponse;
import com.github.chenjianjx.srb4jfullsample.intf.fo.user.FoChangePasswordRequest;
import com.github.chenjianjx.srb4jfullsample.intf.fo.user.FoGenForgetPasswordVerifyCodeRequest;
import com.github.chenjianjx.srb4jfullsample.intf.fo.user.FoResetPasswordRequest;
import com.github.chenjianjx.srb4jfullsample.intf.fo.user.FoUser;
import com.github.chenjianjx.srb4jfullsample.intf.fo.user.FoUserManager;
import com.github.chenjianjx.srb4jfullsample.intf.fo.user.FoValidateForgetPasswordVerifyCodeRequest;
import com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.support.FoResourceBase;
import com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.support.FoRestUtils;
import com.github.chenjianjx.srb4jfullsample.webapp.system.WebAppEnvProp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.ACCESS_TOKEN_HEADER_DEFAULT;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.ACCESS_TOKEN_HEADER_KEY;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.BIZ_ERR_TIP;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.EMAIL_VERIFICATION_DIGEST_PARAM_NAME;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.FO_SC_BIZ_ERROR;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.OAUTH2_ACCESS_TOKEN_NAME_TIP;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.OK_TIP;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.PATH_EMAIL_VERIFICATION_PROCESS_VERIFY;
import static javax.servlet.http.HttpServletResponse.SC_OK;

/**
 * rest resource for authentication
 *
 * @author chenjianjx@gmail.com
 */
@Controller
@Path("/user")
@Api(value = "/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FoUserResource extends FoResourceBase {

    @Resource
    FoUserManager userManager;

    @Resource
    WebAppEnvProp webAppEnvProp;

    @Context
    javax.inject.Provider<HttpServletRequest> servletRequestProvider;

    @GET
    @Path("/profile/me")
    @ApiOperation(value = "Get the profile of the current user")
    @ApiImplicitParams({@ApiImplicitParam(name = ACCESS_TOKEN_HEADER_KEY, value = OAUTH2_ACCESS_TOKEN_NAME_TIP, required = true, dataType = "string", paramType = "header", defaultValue = ACCESS_TOKEN_HEADER_DEFAULT)})
    @ApiResponses(value = {
            @ApiResponse(code = SC_OK, message = OK_TIP, response = FoUser.class),
            @ApiResponse(code = FO_SC_BIZ_ERROR, message = BIZ_ERR_TIP, response = ErrorResult.class)})
    public Response changePassword(@Context ContainerRequestContext context) {
        FoResponse<FoUser> foResponse = userManager.getCurrentUser(getUserId(context));
        return FoRestUtils.fromFoResponse(foResponse, context);
    }


    @POST
    @Path("/password/update/local")
    @ApiOperation(value = "change password for local accounts")
    @ApiImplicitParams({@ApiImplicitParam(name = ACCESS_TOKEN_HEADER_KEY, value = OAUTH2_ACCESS_TOKEN_NAME_TIP, required = true, dataType = "string", paramType = "header", defaultValue = ACCESS_TOKEN_HEADER_DEFAULT)})
    @ApiResponses(value = {
            @ApiResponse(code = SC_OK, message = OK_TIP, response = Void.class),
            @ApiResponse(code = FO_SC_BIZ_ERROR, message = BIZ_ERR_TIP, response = ErrorResult.class)})
    public Response changePassword(@Context ContainerRequestContext context,
                                   FoChangePasswordRequest request) {
        FoResponse<Void> foResponse = userManager.changePassword(
                getUserId(context), request);
        return FoRestUtils.fromFoResponse(foResponse, context);
    }


    /*Email verification flow*/

    @POST
    @Path("/email-verification-process/new")
    @ApiOperation(value = "Start an email verification process. After calling this the user will receive an email containing the verificaiton link")
    @ApiImplicitParams({@ApiImplicitParam(name = ACCESS_TOKEN_HEADER_KEY, value = OAUTH2_ACCESS_TOKEN_NAME_TIP, required = true, dataType = "string", paramType = "header", defaultValue = ACCESS_TOKEN_HEADER_DEFAULT)})
    @ApiResponses(value = {
            @ApiResponse(code = SC_OK, message = OK_TIP, response = Void.class),
            @ApiResponse(code = FO_SC_BIZ_ERROR, message = BIZ_ERR_TIP, response = ErrorResult.class)})
    public Response startEmailVerification(@Context ContainerRequestContext context) {
        String contextPath = servletRequestProvider.get().getContextPath();
        String emailVerifyResourceUrl =
                FoRestUtils.getResourceBasePath(webAppEnvProp, contextPath) + "/user" + PATH_EMAIL_VERIFICATION_PROCESS_VERIFY;
        FoResponse<Void> foResponse = userManager.startEmailVerification(getUserId(context), emailVerifyResourceUrl, EMAIL_VERIFICATION_DIGEST_PARAM_NAME);
        return FoRestUtils.fromFoResponse(foResponse, context);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path(PATH_EMAIL_VERIFICATION_PROCESS_VERIFY)
    @ApiOperation(value = "Do email verification. Note this is not really a typical restful call. It always returns plain text")
    @ApiResponses(value = {
            @ApiResponse(code = SC_OK, message = OK_TIP, response = String.class),
            @ApiResponse(code = FO_SC_BIZ_ERROR, message = BIZ_ERR_TIP, response = String.class)})
    public Response verifyEmail(@Context ContainerRequestContext context, @QueryParam(EMAIL_VERIFICATION_DIGEST_PARAM_NAME) String digest) {
        FoResponse<Void> foResponse = userManager.verifyEmail(digest);
        if (foResponse.isSuccessful()) {
            return Response.status(SC_OK).entity("Email address verified.").build();
        } else {
            ErrorResult errResult = foResponse.getErr();
            String userErrorMessage = errResult == null ? "Email address not verified" : errResult.getUserErrMsg();
            return Response.status(FO_SC_BIZ_ERROR).entity(userErrorMessage).build();
        }
    }

    /*Forget password flow*/

    @POST
    @Path("/password/forget-password-verify-code/new")
    @ApiOperation(value = "Start a 'forget-password' process. After calling this the user will receive an email containing a verification code for him to reset password")
    @ApiResponses(value = {
            @ApiResponse(code = SC_OK, message = OK_TIP, response = Void.class),
            @ApiResponse(code = FO_SC_BIZ_ERROR, message = BIZ_ERR_TIP, response = ErrorResult.class)})
    public Response startForgetPasswordFlow(@Context ContainerRequestContext context, FoGenForgetPasswordVerifyCodeRequest request) {
        FoResponse<Void> foResponse = userManager.startForgetPasswordFlow(request);
        return FoRestUtils.fromFoResponse(foResponse, context);
    }


    @POST
    @Path("/password/forget-password-verify-code/validate")
    @ApiOperation(value = "Call this endpoint to validate the verification code user received in their email")
    @ApiResponses(value = {
            @ApiResponse(code = SC_OK, message = OK_TIP, response = Void.class),
            @ApiResponse(code = FO_SC_BIZ_ERROR, message = BIZ_ERR_TIP, response = ErrorResult.class)})
    public Response validateForgetPasswordVerifyCode(@Context ContainerRequestContext context, FoValidateForgetPasswordVerifyCodeRequest request) {
        FoResponse<Void> foResponse = userManager.validateForgetPasswordVerifyCode(request);
        return FoRestUtils.fromFoResponse(foResponse, context);
    }

    @POST
    @Path("/password/forget-password-flow/update")
    @ApiOperation(value = "The final step of the forget-password-flow:  reset password")
    @ApiResponses(value = {
            @ApiResponse(code = SC_OK, message = OK_TIP, response = Void.class),
            @ApiResponse(code = FO_SC_BIZ_ERROR, message = BIZ_ERR_TIP, response = ErrorResult.class)})
    public Response resetPassword(@Context ContainerRequestContext context, FoResetPasswordRequest request) {
        FoResponse<Void> foResponse = userManager.resetPassword(request);
        return FoRestUtils.fromFoResponse(foResponse, context);
    }
}
