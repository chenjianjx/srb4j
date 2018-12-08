package com.github.chenjianjx.srb4jfullsample.intf.fo.auth;

import java.util.Calendar;

import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoEntityBase;

/**
 * 
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class FoAccessToken extends FoEntityBase {

	private String tokenStr;

	private Long userId;

	/**
	 * units: second. This doesn't serve as the criteria for expiry
	 */
	private Long lifespan;

	private Calendar expiresAt;

	public String getTokenStr() {
		return tokenStr;
	}

	public void setTokenStr(String tokenStr) {
		this.tokenStr = tokenStr;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLifespan() {
		return lifespan;
	}

	public void setLifespan(Long lifespan) {
		this.lifespan = lifespan;
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
