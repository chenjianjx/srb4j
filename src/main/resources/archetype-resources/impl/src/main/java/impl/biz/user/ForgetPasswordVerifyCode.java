package com.github.chenjianjx.srb4jfullsample.impl.biz.user;

import com.github.chenjianjx.srb4jfullsample.impl.biz.common.EntityBase;

import java.util.Calendar;

/**
 * Verification code used in "forget password flow"
 *
 * @author chenjianjx@gmail.com
 *
 */
public class ForgetPasswordVerifyCode extends EntityBase {

	/**
	 * encoded code string
	 */
	private String codeStr;

	private long userId;

	private Calendar expiresAt;

	public String getCodeStr() {
		return codeStr;
	}

	public void setCodeStr(String codeStr) {
		this.codeStr = codeStr;
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
