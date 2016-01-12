package ${groupId}.intf.fo.auth;

import ${groupId}.intf.fo.basic.FoResponse;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public interface FoUserManager {
	/**
	 * for local user to change password
	 * 
	 * @param currentUserId
	 * @param request
	 * @return
	 */
	public FoResponse<Void> changePassword(Long currentUserId,
			FoChangePasswordRequest request);

}
