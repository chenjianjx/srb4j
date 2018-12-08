package com.github.chenjianjx.srb4jfullsample.impl.biz.user;

import com.github.chenjianjx.srb4jfullsample.impl.biz.common.EntityBase;

import java.util.Calendar;

/**
 * The digest that will be used in an email verification link
 * @author chenjianjx@gmail.com
 *
 */
public class EmailVerificationDigest extends EntityBase {

	/**
	 * encoded code string
	 */
	private String digestStr;

	private long userId;

	private Calendar expiresAt;

	public String getDigestStr() {
		return digestStr;
	}

	public void setDigestStr(String digestStr) {
		this.digestStr = digestStr;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Calendar getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Calendar expiresAt) {
		this.expiresAt = expiresAt;
	}
	

	public boolean hasExpired() {
		Calendar now = Calendar.getInstance();
		return now.after(expiresAt);
	}

}
