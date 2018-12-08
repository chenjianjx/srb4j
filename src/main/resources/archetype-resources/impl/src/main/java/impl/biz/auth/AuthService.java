package com.github.chenjianjx.srb4jfullsample.impl.biz.auth;

import com.github.chenjianjx.srb4jfullsample.impl.biz.user.User;
import com.github.chenjianjx.srb4jfullsample.impl.support.config.AppProperties;
import com.github.chenjianjx.srb4jfullsample.impl.support.mail.MailEngine;
import com.github.chenjianjx.srb4jfullsample.utils.lang.MyCodecUtils;
import com.github.chenjianjx.srb4jfullsample.utils.lang.MyLangUtils;
import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.github.chenjianjx.srb4jfullsample.utils.lang.MyLangUtils.toUtf8Bytes;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.RANDOM_LOGIN_CODE_LIFESPAN;

/**
 * @author chenjianjx@gmail.com
 */
@Service
public class AuthService {

    @Resource
    private RandomLoginCodeRepo randomCodeRepo;

    @Resource
    private AccessTokenRepo accessTokenRepo;

    @Resource
    private AppProperties appProperties;

    @Resource
    private MailEngine mailEngine;

    /**
     * @param user
     * @param randomCodeStr clear text code
     * @return
     */
    public RandomLoginCode saveNewRandomCodeForUser(User user, String randomCodeStr) {
        RandomLoginCode newCode = new RandomLoginCode();
        newCode.setCodeStr(MyCodecUtils.encodePasswordLikeDjango(randomCodeStr));
        newCode.setUserId(user.getId());
        newCode.setExpiresAt(MyLangUtils.newCalendar(System.currentTimeMillis()
                + RANDOM_LOGIN_CODE_LIFESPAN * 1000));
        newCode.setCreatedBy(user.getPrincipal());

        RandomLoginCode existingOne = randomCodeRepo.getByUserId(user.getId());
        if (existingOne != null) {
            randomCodeRepo.deleteByUserId(user.getId());
        }
        randomCodeRepo.saveNewCode(newCode);
        return newCode;
    }

    public AccessToken genNewAccessTokenForUser(User user, boolean longSession) {
        long lifespanInSeconds = longSession ? FoConstants.LONG_SESSION_ACCESS_TOKEN_LIFESPAN
                : FoConstants.NORMAL_ACCESS_TOKEN_LIFESPAN;
        Calendar expiresAt = calExpiresAt(lifespanInSeconds);
        AccessToken at = new AccessToken();
        at.setTokenStr(generateAccessTokenStr());
        at.setRefreshTokenStr(generateRefreshTokenStr());
        at.setUserId(user.getId());
        at.setLifespan(lifespanInSeconds);
        at.setExpiresAt(expiresAt);
        at.setCreatedBy(user.getPrincipal());

        accessTokenRepo.saveNewToken(at);
        return at;
    }

    public Calendar calExpiresAt(long lifespanInSeconds) {
        long expiryMilis = System.currentTimeMillis() + lifespanInSeconds
                * 1000;
        Calendar expiresAt = MyLangUtils.newCalendar(expiryMilis);
        return expiresAt;
    }


    public String generateAccessTokenStr() {
        String param = UUID.randomUUID().toString();
        return UUID.fromString(
                UUID.nameUUIDFromBytes(toUtf8Bytes(param)).toString())
                .toString();

    }

    public String generateRefreshTokenStr() {
        String param = UUID.randomUUID().toString();
        return UUID.fromString(
                UUID.nameUUIDFromBytes(toUtf8Bytes(param)).toString())
                .toString();

    }

    public void setRandomCodeRepo(RandomLoginCodeRepo randomCodeRepo) {
        this.randomCodeRepo = randomCodeRepo;
    }

    public void setAccessTokenRepo(AccessTokenRepo accessTokenRepo) {
        this.accessTokenRepo = accessTokenRepo;
    }

    /**
     * @param user
     * @param randomCodeObj
     * @param randomCodeStr clear text string
     */
    public void sendEmailForRandomLoginCodeAsync(User user,
                                                 RandomLoginCode randomCodeObj, String randomCodeStr) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(appProperties.getOrgSupportDesk() + "<"
                + appProperties.getOrgSupportEmail() + ">");
        msg.setSubject("Random Login Code");
        msg.setTo(user.getEmail());
        String templateName = "/template/auth/random-login-code-email.ftl";
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", user);
        model.put("randomCodeObj", randomCodeObj);
        model.put("randomCodeStr", randomCodeStr);
        mailEngine.sendMessageAsync(msg, templateName, model);
    }

    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public void setMailEngine(MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }
}
