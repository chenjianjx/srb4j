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

public class FoRandomCodeLoginRequest {

	@NotNull(message = "Please input email.")
	@ApiModelProperty(required = true)
	private String email;

	@NotNull(message = "Please input the random code you received.")
	@ApiModelProperty(required = true)
	private String randomCode;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRandomCode() {
		return randomCode;
	}

	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
