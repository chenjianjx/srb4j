package com.github.chenjianjx.srb4jfullsample.intf.fo.auth;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * 
 * @author chenjianjx@gmail.com
 *
 */

public class FoRefreshTokenRequest {

	@NotNull(message = "Please specify refresh token.")
	@ApiModelProperty(required = true)
	private String refreshToken;

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
