package com.github.chenjianjx.srb4jfullsample.intf.fo.user;


import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoResponse;

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
	FoResponse<Void> changePassword(Long currentUserId, FoChangePasswordRequest request);

	/**
	 * start email verification.  An email containing the email verification link will be sent
     */
	FoResponse<Void> startEmailVerification(Long currentUserId, String verificationUrlBase, String digestParamName);

	/**
	 * verify the email verification link
	 * @param digest the key information in the verification link
     */
	FoResponse<Void> verifyEmail(String digest);


    FoResponse<FoUser> getCurrentUser(Long currentUserId);


	/**
	 * generate verification code for local user's "forget password flow" and send it to the user's email address
	 *
	 *
	 * @param request
	 * @return
	 */
	FoResponse<Void> startForgetPasswordFlow(FoGenForgetPasswordVerifyCodeRequest request);


	/**
	 * validate the verification code for "forget password flow"
	 *
	 */
	FoResponse<Void> validateForgetPasswordVerifyCode(FoValidateForgetPasswordVerifyCodeRequest request);

	/**
	 * The final step of "forget-password"
	 *
	 */
	FoResponse<Void> resetPassword(FoResetPasswordRequest request);
}
