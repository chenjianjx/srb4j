package ${groupId}.impl.bo.bbs;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import ${groupId}.impl.biz.bbs.Post;
import ${groupId}.impl.biz.bbs.PostRepo;
import ${groupId}.impl.biz.user.User;
import ${groupId}.impl.biz.user.UserRepo;
import ${groupId}.impl.fo.bbs.FoBbsManagerSupport;
import ${groupId}.impl.fo.common.FoManagerImplBase;
import ${groupId}.impl.util.tools.lang.MyLangUtils;
import ${groupId}.intf.bo.bbs.BoBbsManager;
import ${groupId}.intf.bo.bbs.BoPost;
import ${groupId}.intf.fo.basic.FoConstants;
import ${groupId}.intf.fo.basic.FoResponse;
import ${groupId}.intf.fo.bbs.FoPost;

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
