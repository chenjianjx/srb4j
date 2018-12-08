package com.github.chenjianjx.srb4jfullsample.intf.fo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@ApiModel(value = "GenForgetPasswordVerifyRequest", description = "generate verification code for 'forget password flow'")
public class FoGenForgetPasswordVerifyCodeRequest {

	@NotNull(message = "please input email")
	@ApiModelProperty(required = true)
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
