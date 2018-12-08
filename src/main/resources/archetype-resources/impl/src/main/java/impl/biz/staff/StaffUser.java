package com.github.chenjianjx.srb4jfullsample.impl.biz.staff;

import com.github.chenjianjx.srb4jfullsample.impl.biz.common.EntityBase;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author chenjianjx@gmail.com
 */
public class StaffUser extends EntityBase implements Serializable {


    private String username;

    /**
     * encrypted password
     */
    private String password;

    private Calendar lastLoginDate;

    public boolean isLoggedInOnce() {
        return lastLoginDate != null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Calendar getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Calendar lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}
