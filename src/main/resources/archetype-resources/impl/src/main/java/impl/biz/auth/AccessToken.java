package com.github.chenjianjx.srb4jfullsample.impl.biz.auth;

import java.util.Calendar;

import com.github.chenjianjx.srb4jfullsample.impl.biz.common.EntityBase;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class AccessToken extends EntityBase {

	private String tokenStr;

	private long userId;

	/**
	 * unit: second. This doesn't serve as the criteria for expiry
	 */
	private long lifespan;

	private Calendar expiresAt;

	private String refreshTokenStr;

	public String getRefreshTokenStr() {
		return refreshTokenStr;
	}

	public void setRefreshTokenStr(String refreshTokenStr) {
		this.refreshTokenStr = refreshTokenStr;
	}

	public String getTokenStr() {
		return tokenStr;
	}

	public void setTokenStr(String tokenStr) {
		this.tokenStr = tokenStr;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getLifespan() {
		return lifespan;
	}

	public void setLifespan(long lifespan) {
		this.lifespan = lifespan;
	}

	public Calendar getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Calendar expiresAt) {
		this.expiresAt = expiresAt;
	}

}
