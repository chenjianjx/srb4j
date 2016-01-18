package ${groupId}.${rootArtifactId}.impl.fo.bbs;

import static ${groupId}.${rootArtifactId}.intf.fo.basic.FoConstants.NULL_REQUEST_BEAN_TIP;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import ${groupId}.${rootArtifactId}.impl.biz.bbs.Post;
import ${groupId}.${rootArtifactId}.impl.biz.bbs.PostRepo;
import ${groupId}.${rootArtifactId}.impl.biz.user.User;
import ${groupId}.${rootArtifactId}.impl.fo.common.FoManagerImplBase;
import ${groupId}.${rootArtifactId}.impl.util.infrahelp.beanvalidae.MyValidator;
import ${groupId}.${rootArtifactId}.intf.fo.basic.FoConstants;
import ${groupId}.${rootArtifactId}.intf.fo.basic.FoResponse;
import ${groupId}.${rootArtifactId}.intf.fo.bbs.FoBbsManager;
import ${groupId}.${rootArtifactId}.intf.fo.bbs.FoNewPostRequest;
import ${groupId}.${rootArtifactId}.intf.fo.bbs.FoPost;
import ${groupId}.${rootArtifactId}.intf.fo.bbs.FoUpdatePostRequest;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Service("foBbsManager")
@SuppressWarnings({ "deprecation" })
public class FoBbsManagerImpl extends FoManagerImplBase implements FoBbsManager {

	@Resource
	MyValidator myValidator;

	@Resource
	PostRepo postRepo;

	@Resource
	FoBbsManagerSupport foBbsManagerSupport;

	@Override
	public FoResponse<FoPost> newPost(Long currentUserId,
			FoNewPostRequest request) {

		String error = myValidator.validateBeanFastFail(request,
				NULL_REQUEST_BEAN_TIP);
		if (error != null) {
			return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT,
					error);
		}

		User currentUser = getCurrentUserConsideringInvalidId(currentUserId);
		if (currentUser == null) {
			return buildNotLoginErr();
		}

		Post bizPost = null;
		bizPost = new Post();
		bizPost.setContent(request.getContent());
		bizPost.setUserId(currentUserId);
		bizPost.setCreatedBy(currentUser.getPrincipal());
		postRepo.saveNewPost(bizPost);

		FoPost foPost = foBbsManagerSupport.bizPosts2FoPosts(
				Arrays.asList(bizPost), currentUser).get(0);

		return FoResponse.success(foPost);
	}

	@Override
	public FoResponse<FoPost> updatePost(Long currentUserId,
			FoUpdatePostRequest request) {

		String error = myValidator.validateBeanFastFail(request,
				NULL_REQUEST_BEAN_TIP);
		if (error != null) {
			return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT,
					error);
		}

		User currentUser = getCurrentUserConsideringInvalidId(currentUserId);
		if (currentUser == null) {
			return buildNotLoginErr();
		}

		Post bizPost = postRepo.getPostById(request.getId());
		if (bizPost == null) {
			return FoResponse.userErrResponse(FoConstants.FEC_NO_RECORD,
					"Invalid Post ID : " + request.getId());
		}

		if (currentUserId != bizPost.getId()) {
			return FoResponse.userErrResponse(
					FoConstants.FEC_NO_PERMISSION,
					"You have no permission to update this post."
							+ bizPost.getId());
		}

		bizPost.setContent(request.getContent());
		postRepo.updatePost(bizPost);

		FoPost foPost = foBbsManagerSupport.bizPosts2FoPosts(
				Arrays.asList(bizPost), currentUser).get(0);
		return FoResponse.success(foPost);
	}

	/**
	 *    
	 */
	@Override
	public FoResponse<List<FoPost>> getAllPosts(Long currentUserId) {
		User currentUser = getCurrentUserConsideringInvalidId(currentUserId);

		List<Post> bizList = postRepo.getAllPosts();
		List<FoPost> foList = foBbsManagerSupport.bizPosts2FoPosts(bizList,
				currentUser);
		return FoResponse.success(foList);
	}

	@Override
	public FoResponse<Void> deletePostById(Long currentUserId, Long postId) {
		User currentUser = getCurrentUserConsideringInvalidId(currentUserId);
		if (currentUser == null) {
			return buildNotLoginErr();
		}

		Post biz = postRepo.getPostById(postId);
		if (biz == null) {
			return FoResponse.userErrResponse(FoConstants.FEC_NO_RECORD,
					"Invalid Post ID : " + postId);
		}

		if (currentUserId != biz.getId()
				&& !foBbsManagerSupport
						.canAdminBbs_ThisMethodShouldBeChanged(currentUser)) {
			return FoResponse.userErrResponse(FoConstants.FEC_NO_PERMISSION,
					"You have no permission to delete this post." + postId);
		}

		postRepo.deletePostById(postId);
		return FoResponse.success(null);
	}

}
