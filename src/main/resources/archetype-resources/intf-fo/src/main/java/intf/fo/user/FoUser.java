package com.github.chenjianjx.srb4jfullsample.intf.fo.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoEntityBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "User")
public class FoUser extends FoEntityBase {

    @ApiModelProperty(required = true)
    private String principal;

    @ApiModelProperty(value = "source, like 'local' or 'google' ", required = true)
    private String source;

    @ApiModelProperty(required = true)
    private String email;

    @ApiModelProperty(required = true)
    private boolean emailVerified;

    @ApiModelProperty(required = true)
    @JsonProperty("canVerifyEmail")
    public boolean isCanVerifyEmail() {
        return !this.isEmailVerified();
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
}
