package org.srb4j.impl.bo.bbs;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.srb4j.impl.biz.bbs.Post;
import org.srb4j.impl.biz.bbs.PostRepo;
import org.srb4j.impl.biz.user.User;
import org.srb4j.impl.biz.user.UserRepo;
import org.srb4j.impl.fo.bbs.FoBbsManagerSupport;
import org.srb4j.impl.fo.common.FoManagerImplBase;
import org.srb4j.impl.util.tools.lang.MyLangUtils;
import org.srb4j.intf.bo.bbs.BoBbsManager;
import org.srb4j.intf.bo.bbs.BoPost;
import org.srb4j.intf.fo.basic.FoConstants;
import org.srb4j.intf.fo.basic.FoResponse;
import org.srb4j.intf.fo.bbs.FoPost;

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
