package org.srb4j.impl.fo.user;

import static org.srb4j.intf.fo.basic.FoConstants.NULL_REQUEST_BEAN_TIP;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.srb4j.impl.biz.auth.AuthService;
import org.srb4j.impl.biz.user.User;
import org.srb4j.impl.biz.user.UserRepo;
import org.srb4j.impl.fo.common.FoManagerImplBase;
import org.srb4j.impl.util.infrahelp.beanvalidae.MyValidator;
import org.srb4j.intf.fo.auth.FoChangePasswordRequest;
import org.srb4j.intf.fo.auth.FoUserManager;
import org.srb4j.intf.fo.basic.FoConstants;
import org.srb4j.intf.fo.basic.FoResponse;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Service("foUserManager")
public class FoUserManagerImpl extends FoManagerImplBase implements
		FoUserManager {

	@Resource
	MyValidator myValidator;

	@Resource
	AuthService authService;

	@Resource
	UserRepo userRepo;

	@Override
	public FoResponse<Void> changePassword(Long currentUserId,
			FoChangePasswordRequest request) {

		String error = myValidator.validateBeanFastFail(request,
				NULL_REQUEST_BEAN_TIP);
		if (error != null) {
			return FoResponse.userErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST, error);
		}

		User currentUser = getCurrentUserConsideringInvalidId(currentUserId);
		if (currentUser == null) {
			return buildNotLoginErr();
		}

		// now compare password
		String encodedCurrentPassword = authService
				.encodePasswordOrRandomCode(request.getCurrentPassword());

		if (!encodedCurrentPassword.equals(currentUser.getPassword())) {
			return FoResponse.userErrResponse(
					FoConstants.FEC_OAUTH2_INVALID_REQUEST,
					"The current password you input is wrong");
		}

		String newPassword = authService.encodePasswordOrRandomCode(request
				.getNewPassword());

		User user = userRepo.getUserById(currentUserId);
		user.setPassword(newPassword);
		user.setUpdatedBy(user.getPrincipal());
		userRepo.updateUser(user);

		return FoResponse.success(null);

	}
}
