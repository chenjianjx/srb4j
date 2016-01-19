package ${package}.webapp.fo.rest.bbs;

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static ${package}.intf.fo.basic.FoConstants.ACCESS_TOKEN_HEADER_DEFAULT;
import static ${package}.intf.fo.basic.FoConstants.ACCESS_TOKEN_HEADER_KEY;
import static ${package}.intf.fo.basic.FoConstants.BIZ_ERR_TIP;
import static ${package}.intf.fo.basic.FoConstants.FO_SC_BIZ_ERROR;
import static ${package}.intf.fo.basic.FoConstants.OAUTH2_ACCESS_TOKEN_NAME_TIP;
import static ${package}.intf.fo.basic.FoConstants.OAUTH2_ALLOW_ANONYMOUS_TIP;
import static ${package}.intf.fo.basic.FoConstants.OK_TIP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;
import ${package}.intf.fo.basic.FoErrorResult;
import ${package}.intf.fo.basic.FoResponse;
import ${package}.intf.fo.bbs.FoBbsManager;
import ${package}.intf.fo.bbs.FoNewPostRequest;
import ${package}.intf.fo.bbs.FoPost;
import ${package}.intf.fo.bbs.FoUpdatePostRequest;
import ${package}.webapp.fo.rest.support.FoResourceBase;
import ${package}.webapp.fo.rest.support.FoRestUtils;

/**
 * An exemplary rest resource.
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Service
@Path("/bbs")
@Api(value = "/bbs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FoBbsResource extends FoResourceBase {

	@Resource
	FoBbsManager foBbsManager;

	@POST
	@Path("/posts/new")
	@ApiOperation(value = "new post")
	@ApiImplicitParams({ @ApiImplicitParam(name = ACCESS_TOKEN_HEADER_KEY, value = OAUTH2_ACCESS_TOKEN_NAME_TIP, required = true, dataType = "string", paramType = "header", defaultValue = ACCESS_TOKEN_HEADER_DEFAULT) })
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = OK_TIP, response = FoPost.class),
			@ApiResponse(code = FO_SC_BIZ_ERROR, message = BIZ_ERR_TIP, response = FoErrorResult.class) })
	public Response newPost(@Context ContainerRequestContext context,
			FoNewPostRequest request) {
		FoResponse<FoPost> foResponse = foBbsManager.newPost(
				getUserId(context), request);
		return FoRestUtils.fromFoResponse(foResponse, context);
	}

	@POST
	@Path("/posts/update")
	@ApiOperation(value = "update post")
	@ApiImplicitParams({ @ApiImplicitParam(name = ACCESS_TOKEN_HEADER_KEY, value = OAUTH2_ACCESS_TOKEN_NAME_TIP, required = true, dataType = "string", paramType = "header", defaultValue = ACCESS_TOKEN_HEADER_DEFAULT) })
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = OK_TIP, response = FoPost.class),
			@ApiResponse(code = FO_SC_BIZ_ERROR, message = BIZ_ERR_TIP, response = FoErrorResult.class) })
	public Response updatePost(@Context ContainerRequestContext context,
			FoUpdatePostRequest request) {
		FoResponse<FoPost> foResponse = foBbsManager.updatePost(
				getUserId(context), request);
		return FoRestUtils.fromFoResponse(foResponse, context);
	}

	@POST
	@Path("/posts/delete")
	@ApiOperation(value = "delete post")
	@ApiImplicitParams({ @ApiImplicitParam(name = ACCESS_TOKEN_HEADER_KEY, value = OAUTH2_ACCESS_TOKEN_NAME_TIP, required = true, dataType = "string", paramType = "header", defaultValue = ACCESS_TOKEN_HEADER_DEFAULT) })
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = OK_TIP, response = Void.class),
			@ApiResponse(code = FO_SC_BIZ_ERROR, message = BIZ_ERR_TIP, response = FoErrorResult.class) })
	public Response deletePost(@Context ContainerRequestContext context,
			@PathParam("id") Long postId) {
		FoResponse<Void> foResponse = foBbsManager.deletePostById(
				getUserId(context), postId);
		return FoRestUtils.fromFoResponse(foResponse, context);
	}

	@GET
	@Path("/posts")
	@ApiOperation(value = "get all posts")
	@ApiImplicitParams({ @ApiImplicitParam(name = ACCESS_TOKEN_HEADER_KEY, value = OAUTH2_ALLOW_ANONYMOUS_TIP, required = false, dataType = "string", paramType = "header") })
	@ApiResponses(value = {
			@ApiResponse(code = SC_OK, message = OK_TIP, response = FoPost.class, responseContainer = "List"),
			@ApiResponse(code = FO_SC_BIZ_ERROR, message = BIZ_ERR_TIP, response = FoErrorResult.class) })
	public Response getAllPosts(@Context ContainerRequestContext context) {
		FoResponse<List<FoPost>> foResponse = foBbsManager
				.getAllPosts(getUserId(context));
		return FoRestUtils.fromFoResponse(foResponse, context);
	}

}
