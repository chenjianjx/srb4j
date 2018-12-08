package com.github.chenjianjx.srb4jfullsample.intf.fo.user;

import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * 
 * @author chenjianjx@gmail.com
 *
 */

@ApiModel("ChangePasswordRequest")
public class FoChangePasswordRequest {

	@NotNull(message = "Please input current password.")
	@ApiModelProperty(value = "current password", required = true)
	private String currentPassword;

	@NotNull(message = "Please input the new password.")
	@Pattern(regexp = FoConstants.PASSWORD_PATTERN, message = FoConstants.NEW_PASSWORD_ERR_TIP)
	@ApiModelProperty(value = "new password", required = true)
	private String newPassword;

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String password) {
		this.newPassword = password;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
