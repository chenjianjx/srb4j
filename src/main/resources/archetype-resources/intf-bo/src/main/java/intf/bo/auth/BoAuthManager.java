package ${package}.intf.bo.auth;

import ${package}.intf.fo.basic.FoResponse;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public interface BoAuthManager {

	/**
	 * login if the user is not an social site user such as google/facebook. The user can login with their
	 * email
	 * 
	 */
	public FoResponse<BoLoginResult> localLogin(BoLocalLoginRequest request);

}
