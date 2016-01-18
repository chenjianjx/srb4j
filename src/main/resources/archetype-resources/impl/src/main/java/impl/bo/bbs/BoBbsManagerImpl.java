package ${groupId}.${rootArtifactId}.impl.bo.bbs;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import ${groupId}.${rootArtifactId}.impl.biz.bbs.Post;
import ${groupId}.${rootArtifactId}.impl.biz.bbs.PostRepo;
import ${groupId}.${rootArtifactId}.impl.biz.user.User;
import ${groupId}.${rootArtifactId}.impl.biz.user.UserRepo;
import ${groupId}.${rootArtifactId}.impl.fo.bbs.FoBbsManagerSupport;
import ${groupId}.${rootArtifactId}.impl.fo.common.FoManagerImplBase;
import ${groupId}.${rootArtifactId}.impl.util.tools.lang.MyLangUtils;
import ${groupId}.${rootArtifactId}.intf.bo.bbs.BoBbsManager;
import ${groupId}.${rootArtifactId}.intf.bo.bbs.BoPost;
import ${groupId}.${rootArtifactId}.intf.fo.basic.FoConstants;
import ${groupId}.${rootArtifactId}.intf.fo.basic.FoResponse;
import ${groupId}.${rootArtifactId}.intf.fo.bbs.FoPost;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Service("boBbsManager")
@SuppressWarnings({ "deprecation" })
public class BoBbsManagerImpl extends FoManagerImplBase implements BoBbsManager {

	@Resource
	PostRepo postRepo;

	@Resource
	UserRepo userRepo;

	@Resource
	FoBbsManagerSupport foBbsManagerSupport;

	@Override
	public FoResponse<List<BoPost>> getAllPostsForBbsAdmin(Long currentUserId) {

		User currentUser = getCurrentUserConsideringInvalidId(currentUserId);
		if (currentUser == null) {
			return buildNotLoginErr();
		}
		if (!foBbsManagerSupport
				.canAdminBbs_ThisMethodShouldBeChanged(currentUser)) {
			return FoResponse.userErrResponse(FoConstants.FEC_NO_PERMISSION,
					"You cannot do bbs admin work");
		}

		List<Post> bizList = postRepo.getAllPosts();
		List<FoPost> foList = foBbsManagerSupport.bizPosts2FoPosts(bizList,
				currentUser);
		List<BoPost> boList = MyLangUtils.copyPropertiesToNewCollections(
				BoPost.class, foList);

		// performance is bad. Don't go this approach for real product system
		for (BoPost boPost : boList) {
			User user = userRepo.getUserById(boPost.getUserId());
			boPost.setUserPrincipal(user.getPrincipal());
		}
		return FoResponse.success(boList);

	}

}
