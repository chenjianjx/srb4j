package com.github.chenjianjx.srb4jfullsample.intf.fo.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@ApiModel(value = "GenRandomLoginCodeRequest", description = "generate random login code")
public class FoGenRandomLoginCodeRequest {

	@NotNull(message = "Please input email")
	@ApiModelProperty(value = "the user's email", required = true)
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
