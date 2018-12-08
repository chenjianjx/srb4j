package com.github.chenjianjx.srb4jfullsample.intf.bo.frontuser;

import com.github.chenjianjx.srb4jfullsample.intf.bo.basic.BoResponse;
import com.github.chenjianjx.srb4jfullsample.intf.fo.user.FoUser;

import java.util.List;

/**
 * managing front users
 */
public interface BoFrontUserManager {

    BoResponse<List<FoUser>> getAllUsers(Long currentStaffUserId);
}
