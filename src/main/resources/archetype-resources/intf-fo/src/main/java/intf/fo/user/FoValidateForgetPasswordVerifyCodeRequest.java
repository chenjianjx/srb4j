package com.github.chenjianjx.srb4jfullsample.intf.fo.user;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

/**
 *
 * 
 * @author chenjianjx@gmail.com
 *
 */

public class FoValidateForgetPasswordVerifyCodeRequest {

	@NotNull(message = "Please input email.")
	@ApiModelProperty(required = true)
	private String email;

	@NotNull(message = "Please input the verification code you received.")
	@ApiModelProperty(required = true)
	private String verifyCode;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
