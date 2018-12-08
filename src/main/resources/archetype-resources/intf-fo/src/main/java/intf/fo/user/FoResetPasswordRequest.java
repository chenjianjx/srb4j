package com.github.chenjianjx.srb4jfullsample.intf.fo.user;

import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Reset password means "change the password inside 'forget-password-flow' "
 * @author chenjianjx@gmail.com
 */

@ApiModel("ResetPasswordRequest")
public class FoResetPasswordRequest extends FoValidateForgetPasswordVerifyCodeRequest {


    @NotNull(message = "Please input the new password.")
    @Pattern(regexp = FoConstants.PASSWORD_PATTERN, message = FoConstants.NEW_PASSWORD_ERR_TIP)
    @ApiModelProperty(value = "new password", required = true)
    private String newPassword;


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
