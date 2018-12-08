package com.github.chenjianjx.srb4jfullsample.intf.fo.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@ApiModel("RegisterRequest")
public class FoRegisterRequest {

	/**
	 * the new value to set
	 */
	@NotNull(message = "Please input email.")
	// Please validate the pattern of email in implementation
	@ApiModelProperty(value = "email", required = true)
	private String email;

	@NotNull(message = "Please input password.")
	@Pattern(regexp = FoConstants.PASSWORD_PATTERN, message = FoConstants.PASSWORD_ERR_TIP)
	@ApiModelProperty(value = "password", required = true)
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
