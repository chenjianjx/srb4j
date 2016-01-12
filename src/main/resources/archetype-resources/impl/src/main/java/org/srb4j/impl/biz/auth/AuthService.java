package org.srb4j.impl.biz.auth;

import static org.srb4j.impl.util.tools.lang.MyLangUtils.toUtf8Bytes;
import static org.srb4j.intf.fo.basic.FoConstants.RANDOM_LOGIN_CODE_LIFESPAN;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Sha2Crypt;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.srb4j.impl.biz.user.User;
import org.srb4j.impl.support.config.AppProperties;
import org.srb4j.impl.support.mail.MailEngine;
import org.srb4j.impl.util.tools.lang.MyLangUtils;
import org.srb4j.intf.fo.basic.FoConstants;

/**
 * 
 * @author chenjianjx@gmail.com
 *
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
	 * 
	 * @param user
	 * @param randomCodeStr clear text code
	 * @return
	 */
	public RandomLoginCode saveNewRandomCodeForUser(User user, String randomCodeStr) {
		RandomLoginCode newCode = new RandomLoginCode();		
		newCode.setCodeStr(encodePasswordOrRandomCode(randomCodeStr));
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
	
	
	 

	public String encodePasswordOrRandomCode(String raw) {
		Sha2Crypt.sha256Crypt(MyLangUtils.toUtf8Bytes(raw));
		return DigestUtils.sha1Hex(raw);
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

	public String generateRandomLoginCode() {
		SecureRandom random = new SecureRandom();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			int randNum = random.nextInt(10);
			sb.append(randNum);
		}
		return sb.toString();
	}

	public void setRandomCodeRepo(RandomLoginCodeRepo randomCodeRepo) {
		this.randomCodeRepo = randomCodeRepo;
	}

	public void setAccessTokenRepo(AccessTokenRepo accessTokenRepo) {
		this.accessTokenRepo = accessTokenRepo;
	}

	/**
	 * 
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
