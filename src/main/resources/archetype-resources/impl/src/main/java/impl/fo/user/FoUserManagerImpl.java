package ${package}.impl.fo.user;

import static ${package}.intf.fo.basic.FoConstants.NULL_REQUEST_BEAN_TIP;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import ${package}.impl.biz.auth.AuthService;
import ${package}.impl.biz.user.User;
import ${package}.impl.biz.user.UserRepo;
import ${package}.impl.fo.common.FoManagerImplBase;
import ${package}.impl.util.infrahelp.beanvalidae.MyValidator;
import ${package}.intf.fo.auth.FoChangePasswordRequest;
import ${package}.intf.fo.auth.FoUserManager;
import ${package}.intf.fo.basic.FoConstants;
import ${package}.intf.fo.basic.FoResponse;

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
