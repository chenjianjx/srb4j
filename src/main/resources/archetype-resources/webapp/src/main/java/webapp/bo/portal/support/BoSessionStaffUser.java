package com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support;


import com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.staffuser.BoChangePasswordReason;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * store this object in session.
 * Note:  Don't access this object using session.getAttribute(), instead please always use {@link BoSessionHelper},
 * which will make sure valid data is stored and retrieved
 */
public class BoSessionStaffUser implements Serializable {

    private long userId;
    private String username;
    private boolean mustChangePassword;
    private BoChangePasswordReason changePasswordReason;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isMustChangePassword() {
        return mustChangePassword;
    }

    public void setMustChangePassword(boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }

    public BoChangePasswordReason getChangePasswordReason() {
        return changePasswordReason;
    }

    public void setChangePasswordReason(BoChangePasswordReason changePasswordReason) {
        this.changePasswordReason = changePasswordReason;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
