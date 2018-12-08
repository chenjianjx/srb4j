package com.github.chenjianjx.srb4jfullsample.impl.biz.user;

import com.github.chenjianjx.srb4jfullsample.impl.support.config.AppProperties;
import com.github.chenjianjx.srb4jfullsample.impl.support.mail.MailEngine;
import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants;
import com.github.chenjianjx.srb4jfullsample.utils.lang.MyCodecUtils;
import com.github.chenjianjx.srb4jfullsample.utils.lang.MyLangUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.FORGET_PASSWORD_VERIFY_CODE_LIFESPAN;
import static com.github.chenjianjx.srb4jfullsample.utils.lang.MyLangUtils.toUtf8Bytes;

/**
 * Created by chenjianjx@gmail.com on 4/11/18.
 */
@Service
public class UserService {

    @Resource
    private EmailVerificationDigestRepo emailVerificationDigestRepo;


    @Resource
    private ForgetPasswordVerifyCodeRepo forgetPasswordVerifyCodeRepo;

    @Resource
    private AppProperties appProperties;

    @Resource
    private MailEngine mailEngine;


    /**
     * if such digest exists, delete it before saving the new one
     * @return
     */
    public EmailVerificationDigest saveNewEmailVerificationDigestForUser(User user) {
        EmailVerificationDigest newDigest = new EmailVerificationDigest();
        newDigest.setDigestStr(generateDigestString());
        newDigest.setUserId(user.getId());
        newDigest.setExpiresAt(MyLangUtils.newCalendar(System.currentTimeMillis()
                + FoConstants.EMAIL_VERIFICATION_DIGEST_LIFESPAN * 1000));
        newDigest.setCreatedBy(user.getPrincipal());

        EmailVerificationDigest existingOne = emailVerificationDigestRepo.getByUserId(user.getId());
        if (existingOne != null) {
            emailVerificationDigestRepo.deleteByUserId(user.getId());
        }
        emailVerificationDigestRepo.saveNewDigest(newDigest);
        return newDigest;
    }

    /**
     * @param user
     * @param forgetPasswordVerifyCodeStr clear text code
     * @return
     */
    public ForgetPasswordVerifyCode saveNewForgetPasswordVerifyCodeForUser(User user, String forgetPasswordVerifyCodeStr) {
        ForgetPasswordVerifyCode newCode = new ForgetPasswordVerifyCode();
        newCode.setCodeStr(MyCodecUtils.encodePasswordLikeDjango(forgetPasswordVerifyCodeStr));
        newCode.setUserId(user.getId());
        newCode.setExpiresAt(MyLangUtils.newCalendar(System.currentTimeMillis()
                + FORGET_PASSWORD_VERIFY_CODE_LIFESPAN * 1000));
        newCode.setCreatedBy(user.getPrincipal());

        ForgetPasswordVerifyCode existingOne = forgetPasswordVerifyCodeRepo.getByUserId(user.getId());
        if (existingOne != null) {
            forgetPasswordVerifyCodeRepo.deleteByUserId(user.getId());
        }
        forgetPasswordVerifyCodeRepo.saveNewCode(newCode);
        return newCode;
    }


    private String generateDigestString() {
        String param = UUID.randomUUID().toString();
        param = UUID.fromString(
                UUID.nameUUIDFromBytes(toUtf8Bytes(param)).toString())
                .toString();
        return param;
    }


    public void setEmailVerificationDigestRepo(EmailVerificationDigestRepo emailVerificationDigestRepo) {
        this.emailVerificationDigestRepo = emailVerificationDigestRepo;
    }

    public void sendEmailForEmailVerificationAsync(User user, EmailVerificationDigest digest, String verificationUrlBase, String digestParamName) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(appProperties.getOrgSupportDesk() + "<"
                + appProperties.getOrgSupportEmail() + ">");
        msg.setSubject("Verify Your Email Address");
        msg.setTo(user.getEmail());
        String templateName = "/template/user/email-address-verification-email.ftl";
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("verificationUrlBase", verificationUrlBase);
        model.put("digestParamName", digestParamName);
        model.put("digest", digest.getDigestStr());
        mailEngine.sendMessageAsync(msg, templateName, model);
    }

    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public void setMailEngine(MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }

    public void sendEmailForForgetPasswordVerifyCodeAsync(User user, ForgetPasswordVerifyCode codeObj, String codeStr) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(appProperties.getOrgSupportDesk() + "<"
                + appProperties.getOrgSupportEmail() + ">");
        msg.setSubject("Reset Password");
        msg.setTo(user.getEmail());
        String templateName = "/template/user/forget-password-verify-code-email.ftl";
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", user);
        model.put("verifyCodeObj", codeObj);
        model.put("verifyCodeStr", codeStr);
        mailEngine.sendMessageAsync(msg, templateName, model);
    }
}
