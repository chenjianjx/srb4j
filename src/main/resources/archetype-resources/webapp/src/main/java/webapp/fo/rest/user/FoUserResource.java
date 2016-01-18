package ${groupId}.${rootArtifactId}.webapp.fo.rest.user;

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static ${groupId}.${rootArtifactId}.intf.fo.basic.FoConstants.ACCESS_TOKEN_HEADER_DEFAULT;
import static ${groupId}.${rootArtifactId}.intf.fo.basic.FoConstants.ACCESS_TOKEN_HEADER_KEY;
import static ${groupId}.${rootArtifactId}.intf.fo.basic.FoConstants.BIZ_ERR_TIP;
import static ${groupId}.${rootArtifactId}.intf.fo.basic.FoConstants.FO_SC_BIZ_ERROR;
import static ${groupId}.${rootArtifactId}.intf.fo.basic.FoConstants.OAUTH2_ACCESS_TOKEN_NAME_TIP;
import static ${groupId}.${rootArtifactId}.intf.fo.basic.FoConstants.OK_TIP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;
import ${groupId}.${rootArtifactId}.intf.fo.auth.FoChangePasswordRequest;
import ${groupId}.${rootArtifactId}.intf.fo.auth.FoUserManager;
import ${groupId}.${rootArtifactId}.intf.fo.basic.FoErrorResult;
import ${groupId}.${rootArtifactId}.intf.fo.basic.FoResponse;
import ${groupId}.${rootArtifactId}.webapp.fo.rest.support.FoResourceBase;
import ${groupId}.${rootArtifactId}.webapp.fo.rest.support.FoRestUtils;

/**
 * rest resource for authentication
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Service
@Path("/user")
@Api(value = "/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FoUserResource extends FoResourceBase {

	@Resource
	FoUserManager userManager;

	@POST
	@Path("/password/update/local")
	@ApiOperation(value = "change password for local accounts")
	@ApiImplicitParams({ @ApiImplicitParam(name = ACCESS_TOKEN_HEADER_KEY, value = OAUTH2_ACCESS_TOKEN_NAME_TIP, required = true, dataType = "string", paramType = "header", defaultValue = ACCESS_TOKEN_HEADER_DEFAULT) })
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = OK_TIP, response = Void.class),
			@ApiResponse(code = FO_SC_BIZ_ERROR, message = BIZ_ERR_TIP, response = FoErrorResult.class) })
	public Response changePassword(@Context ContainerRequestContext context,
			FoChangePasswordRequest request) {
		FoResponse<Void> foResponse = userManager.changePassword(
				getUserId(context), request);
		return FoRestUtils.fromFoResponse(foResponse, context);
	}
}
