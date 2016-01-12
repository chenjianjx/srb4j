package ${groupId}.impl.fo.common;

import javax.annotation.Resource;

import ${groupId}.impl.biz.user.User;
import ${groupId}.impl.biz.user.UserRepo;
import ${groupId}.intf.fo.basic.FoConstants;
import ${groupId}.intf.fo.basic.FoResponse;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class FoManagerImplBase {

	@Resource
	UserRepo userRepo;

	/**
	 * if the currentUserId is invalid ,return null
	 * 
	 * @param currentUserId
	 * @return
	 */
	protected User getCurrentUserConsideringInvalidId(Long currentUserId) {
		if (currentUserId == null || currentUserId <= 0) {
			return null;
		}
		User currentUser = userRepo.getUserById(currentUserId);
		return currentUser;
	}

	public static <T> FoResponse<T> buildNotLoginErr() {
		return FoResponse.userErrResponse(FoConstants.FEC_NOT_LOGIN_YET,
				"You have not login yet");
	}
}
