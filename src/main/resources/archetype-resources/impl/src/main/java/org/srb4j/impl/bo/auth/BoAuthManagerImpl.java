package org.srb4j.impl.bo.auth;

import static org.srb4j.intf.fo.basic.FoConstants.NULL_REQUEST_BEAN_TIP;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.srb4j.impl.biz.auth.AuthService;
import org.srb4j.impl.biz.user.User;
import org.srb4j.impl.biz.user.UserRepo;
import org.srb4j.impl.util.infrahelp.beanvalidae.MyValidator;
import org.srb4j.intf.bo.auth.BoAuthManager;
import org.srb4j.intf.bo.auth.BoLocalLoginRequest;
import org.srb4j.intf.bo.auth.BoLoginResult;
import org.srb4j.intf.fo.basic.FoConstants;
import org.srb4j.intf.fo.basic.FoResponse;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Service("boAuthManager")
public class BoAuthManagerImpl implements BoAuthManager {

	@Resource
	MyValidator myValidator;

	@Resource
	UserRepo userRepo;

	@Resource
	AuthService authService;

	@Override
	public FoResponse<BoLoginResult> localLogin(BoLocalLoginRequest request) {

		String error = myValidator.validateBeanFastFail(request,
				NULL_REQUEST_BEAN_TIP);
		if (error != null) {
			return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT,
					error);
		}

		String principal = User.decidePrincipalFromLocal(request.getEmail());
		User user = userRepo.getUserByPrincipal(principal);

		if (user == null) {
			return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT,
					"Invalid Email");
		}

		// check if bo user
		if (!isBoUser_ThisMethodShouldBeChanged(user)) {
			return FoResponse.userErrResponse(FoConstants.FEC_NO_PERMISSION,
					"You are not a back office user");
		}

		// compare password
		String encodedPassword = authService.encodePasswordOrRandomCode(request
				.getPassword());

		if (!encodedPassword.equals(user.getPassword())) {
			return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT,
					"Invalid Password");
		}

		BoLoginResult result = new BoLoginResult();
		result.setUserId(user.getId());
		return FoResponse.success(result);
	}

	@Deprecated
	private boolean isBoUser_ThisMethodShouldBeChanged(User user) {
		return true;
	}

}
