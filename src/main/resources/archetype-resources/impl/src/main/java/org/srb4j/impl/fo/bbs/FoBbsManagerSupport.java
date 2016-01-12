package org.srb4j.impl.fo.bbs;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.srb4j.impl.biz.bbs.Post;
import org.srb4j.impl.biz.bbs.PostRepo;
import org.srb4j.impl.biz.user.User;
import org.srb4j.impl.biz.user.UserRepo;
import org.srb4j.impl.util.tools.lang.MyLangUtils;
import org.srb4j.intf.fo.auth.FoAuthManager;
import org.srb4j.intf.fo.auth.FoAuthTokenResult;
import org.srb4j.intf.fo.auth.FoRegisterRequest;
import org.srb4j.intf.fo.basic.FoResponse;
import org.srb4j.intf.fo.bbs.FoPost;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Service
public class FoBbsManagerSupport {

	@Resource
	FoAuthManager foAuthManager;

	@Resource
	UserRepo userRepo;

	@Resource
	PostRepo postRepo;

	public List<FoPost> bizPosts2FoPosts(List<Post> bizList, User currentUser) {
		List<FoPost> foList = MyLangUtils.copyPropertiesToNewCollections(
				FoPost.class, bizList);
		
		
		if(currentUser == null){
			return foList;
		}
		
		for (FoPost fo : foList) {
			if (canAdminBbs_ThisMethodShouldBeChanged(currentUser)) {
				fo.setCanDelete(true);
				fo.setCanUpdate(false);
				continue;
			}

			if (fo.getUserId() == currentUser.getId()) {
				fo.setCanDelete(true);
				fo.setCanUpdate(true);
			}
		}
		return foList;

	}

	/**
	 * a temporary method for you to decide whether a person have permission to
	 * do admin work. This is a workaround when you haven't got an authorization
	 * framework yet and it is only used for demonstration. Please delete this
	 * method the moment you've got your own authorization implementation.
	 * 
	 * @param user
	 * @return
	 * @deprecated Please delete this method the moment you've got your own
	 *             authorization implementation
	 */
	public boolean canAdminBbs_ThisMethodShouldBeChanged(User user) {
		String fakeAdminPrincipal = User
				.decidePrincipalFromLocal(FAKE_ADMIN_EMAIL_THIS_CONSTANT_SHOULD_BE_DELETED);
		if (user.getPrincipal().equals(fakeAdminPrincipal)) {
			return true;
		}
		return false;
	}

	@Deprecated
	private static final String FAKE_ADMIN_EMAIL_THIS_CONSTANT_SHOULD_BE_DELETED = "bbsadmin@nonexist.com";

	@Deprecated
	@PostConstruct
	public void init_ThisMethodShouldBeDeleted() {
		// make a fake user
		String fakeAdminPrincipal = User
				.decidePrincipalFromLocal(FAKE_ADMIN_EMAIL_THIS_CONSTANT_SHOULD_BE_DELETED);
		User fakeAdmin = userRepo.getUserByPrincipal(fakeAdminPrincipal);
		if (fakeAdmin == null) {
			FoRegisterRequest request = new FoRegisterRequest();
			request.setEmail(FAKE_ADMIN_EMAIL_THIS_CONSTANT_SHOULD_BE_DELETED);
			request.setPassword("abc123456");
			FoResponse<FoAuthTokenResult> response = foAuthManager.localRegister(request);
			if (!response.isSuccessful()) {
				System.err
						.println("init_ThisMethodShouldBeDeleted(): Cannot create a fake bbs admin user. You cannot login to back office. The cause is:");
				System.err.println(response);
				return;
			} else {
				System.out.println("init_ThisMethodShouldBeDeleted(): A fake bbs admin user has been created. It is " + FAKE_ADMIN_EMAIL_THIS_CONSTANT_SHOULD_BE_DELETED);
				fakeAdmin = userRepo.getUserByPrincipal(fakeAdminPrincipal);
			}
		}
		// create several posts
		if (postRepo.getAllPosts().isEmpty()) {
			for (int i = 0; i < 2; i++) {
				Post bizPost = new Post();
				bizPost.setContent("tom cruise like "
						+ RandomStringUtils.randomAlphanumeric(100));
				bizPost.setUserId(fakeAdmin.getId());
				bizPost.setCreatedBy(fakeAdmin.getPrincipal());				
				postRepo.saveNewPost(bizPost);
				System.out.println("init_ThisMethodShouldBeDeleted(): A fake bbs post has been created. Its ID is " + bizPost.getId());
			}
		}

	}

}
