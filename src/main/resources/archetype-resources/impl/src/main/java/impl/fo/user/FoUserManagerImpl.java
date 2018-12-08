package com.github.chenjianjx.srb4jfullsample.impl.fo.user;

import com.github.chenjianjx.srb4jfullsample.impl.biz.user.*;
import com.github.chenjianjx.srb4jfullsample.impl.fo.common.FoManagerImplBase;
import com.github.chenjianjx.srb4jfullsample.impl.support.beanvalidate.MyValidator;
import com.github.chenjianjx.srb4jfullsample.impl.support.beanvalidate.ValidationError;
import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants;
import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoResponse;
import com.github.chenjianjx.srb4jfullsample.intf.fo.user.*;
import com.github.chenjianjx.srb4jfullsample.utils.lang.MyCodecUtils;
import com.github.chenjianjx.srb4jfullsample.utils.lang.MyLangUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.github.chenjianjx.srb4jfullsample.impl.common.ImplHelper.generateRandomDigitCode;
import static com.github.chenjianjx.srb4jfullsample.impl.common.ImplHelper.pleaseSocialLoginTip;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.NULL_REQUEST_BEAN_TIP;

/**
 * @author chenjianjx@gmail.com
 */
@Service("foUserManager")
public class FoUserManagerImpl extends FoManagerImplBase implements
        FoUserManager {

    private static final Logger logger = LoggerFactory.getLogger(FoUserManagerImpl.class);

    @Resource
    MyValidator myValidator;


    @Resource
    UserRepo userRepo;

    @Resource
    UserService userService;

    @Resource
    EmailVerificationDigestRepo emailVerificationDigestRepo;

    @Resource
    ForgetPasswordVerifyCodeRepo forgetPasswordVerifyCodeRepo;

    @Override
    public FoResponse<Void> changePassword(Long currentUserId,
                                           FoChangePasswordRequest request) {

        ValidationError error = myValidator.validateBean(request, NULL_REQUEST_BEAN_TIP);
        if (error.hasErrors()) {
            return FoResponse.userErrResponse(
                    FoConstants.FEC_INVALID_INPUT, error.getNonFieldError(), error.getFieldErrors());
        }

        User currentUser = getCurrentUserConsideringInvalidId(currentUserId);
        if (currentUser == null) {
            return buildNotLoginErr();
        }

        if (!currentUser.isLocal()) {
            return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT,
                    String.format("You are a %s user and cannot change password", currentUser.getSource()), null);
        }

        if (!MyCodecUtils.isPasswordDjangoMatches(request.getCurrentPassword(), currentUser.getPassword())) {
            return FoResponse.userErrResponse(
                    FoConstants.FEC_INVALID_INPUT,
                    "The current password you input is wrong", null);
        }

        String newPassword = MyCodecUtils.encodePasswordLikeDjango(request.getNewPassword());

        User user = userRepo.getUserById(currentUserId);
        user.setPassword(newPassword);
        user.setUpdatedBy(user.getPrincipal());
        userRepo.updateUser(user);

        return FoResponse.success(null);

    }

    @Override
    public FoResponse<Void> startEmailVerification(Long currentUserId, String verificationUrlBase, String digestParamName) {

        User currentUser = getCurrentUserConsideringInvalidId(currentUserId);
        if (currentUser == null) {
            return buildNotLoginErr();
        }

        User user = currentUser;
        if (user.isEmailVerified()) {
            return FoResponse.userErrResponse(
                    FoConstants.FEC_ILLEGAL_STATUS, "Your email is already verified.", null);
        }

        EmailVerificationDigest digest = userService.saveNewEmailVerificationDigestForUser(user);

        // send the email
        try {
            userService.sendEmailForEmailVerificationAsync(user, digest, verificationUrlBase, digestParamName);
        } catch (Exception e) {
            logger.error("fail to send email verification link asyncly  for user "
                    + user.getPrincipal(), e);
        }

        return FoResponse.success(null);
    }

    @Override
    public FoResponse<Void> verifyEmail(String digestStr) {
        digestStr = StringUtils.trimToNull(digestStr);
        if (digestStr == null) {
            return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT, "Invalid request", null);
        }

        EmailVerificationDigest digest = emailVerificationDigestRepo.getByDigestStr(digestStr);
        if (digest == null) {
            return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT, "Invalid request", null);
        }

        User user = userRepo.getUserById(digest.getUserId());

        if (user == null) {
            return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT, "Invalid request", null);
        }

        if (user.isEmailVerified()) {
            //verified already? Just say congratulations
            return FoResponse.success(null);
        }

        if (digest.hasExpired()) {
            return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT, "The link has expired.", null);
        }

        user.setEmailVerified(true);
        userRepo.updateUser(user);
        return FoResponse.success(null);
    }

    @Override
    public FoResponse<FoUser> getCurrentUser(Long currentUserId) {
        User currentUser = getCurrentUserConsideringInvalidId(currentUserId);
        if (currentUser == null) {
            return buildNotLoginErr();
        }
        FoUser foUser = MyLangUtils.copyPropertiesToNewObject(FoUser.class, currentUser);
        return FoResponse.success(foUser);
    }

    @Override
    public FoResponse<Void> startForgetPasswordFlow(FoGenForgetPasswordVerifyCodeRequest request) {
        ValidationError error = myValidator.validateBean(request, FoConstants.NULL_REQUEST_BEAN_TIP);
        if (error.hasErrors()) {
            return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT, error.getNonFieldError(), error.getFieldErrors());
        }

        User user = userRepo.getUserByEmail(request.getEmail());
        if (user == null) {
            return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT, "User not found", null);
        }

        if (!user.isLocal()) {
            return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT, pleaseSocialLoginTip(user.getSource()), null);
        }


        String codeStr = generateRandomDigitCode();
        ForgetPasswordVerifyCode codeObj = userService.saveNewForgetPasswordVerifyCodeForUser(user, codeStr);

        // send the email
        try {
            userService.sendEmailForForgetPasswordVerifyCodeAsync(user, codeObj,
                    codeStr);
        } catch (Exception e) {
            logger.error("fail to send forget-password-verification-code asynchronously  for user "
                    + user.getPrincipal(), e);
        }

        return FoResponse.success(null);
    }

    @Override
    public FoResponse<Void> validateForgetPasswordVerifyCode(FoValidateForgetPasswordVerifyCodeRequest request) {
        ValidationError error = myValidator.validateBean(request, NULL_REQUEST_BEAN_TIP);
        if (error.hasErrors()) {
            return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT, error.getNonFieldError(), error.getFieldErrors());
        }
        return bizValidateForgetPasswordVerifyCode(request);
    }


    @Override
    public FoResponse<Void> resetPassword(FoResetPasswordRequest request) {

        ValidationError error = myValidator.validateBean(request, NULL_REQUEST_BEAN_TIP);
        if (error.hasErrors()) {
            return FoResponse.userErrResponse(
                    FoConstants.FEC_INVALID_INPUT, error.getNonFieldError(), error.getFieldErrors());
        }

        FoResponse<Void> verifyCodeValidationResponse = bizValidateForgetPasswordVerifyCode(request);
        if (!verifyCodeValidationResponse.isSuccessful()) {
            throw new IllegalStateException("Must have passed the verification code validation");
        }

        String newPassword = MyCodecUtils.encodePasswordLikeDjango(request.getNewPassword());

        String principal = User.decidePrincipalFromLocal(request.getEmail());
        User user = userRepo.getUserByPrincipal(principal);
        user.setPassword(newPassword);
        user.setUpdatedBy(user.getPrincipal());
        userRepo.updateUser(user);

        return FoResponse.success(null);
    }


    private FoResponse<Void> bizValidateForgetPasswordVerifyCode(FoValidateForgetPasswordVerifyCodeRequest request) {
        String principal = User.decidePrincipalFromLocal(request.getEmail());
        User user = userRepo.getUserByPrincipal(principal);

        if (user == null) {
            return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT, "User not found", null);
        }

        // now compare the codes
        ForgetPasswordVerifyCode verifyCodeFromDb = forgetPasswordVerifyCodeRepo.getByUserId(user.getId());
        if (verifyCodeFromDb == null || !MyCodecUtils.isPasswordDjangoMatches(request.getVerifyCode(), verifyCodeFromDb.getCodeStr())) {
            return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT, "Invalid verification code.", null);
        }

        if (verifyCodeFromDb.hasExpired()) {
            return FoResponse.userErrResponse(FoConstants.FEC_INVALID_INPUT, "Verification code expired.", null);
        }

        return FoResponse.success(null);
    }

}
