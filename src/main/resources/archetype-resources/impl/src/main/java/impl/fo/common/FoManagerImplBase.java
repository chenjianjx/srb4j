package com.github.chenjianjx.srb4jfullsample.impl.fo.common;

import com.github.chenjianjx.srb4jfullsample.impl.biz.user.User;
import com.github.chenjianjx.srb4jfullsample.impl.biz.user.UserRepo;
import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants;
import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoResponse;

import javax.annotation.Resource;

/**
 * @author chenjianjx@gmail.com
 */
public class FoManagerImplBase {

    @Resource
    UserRepo userRepo;

    /**
     * if the currentUserId is invalid ,return null
     *
     * @param currentUserId
     * @return
     */
    protected User getCurrentUserConsideringInvalidId(Long currentUserId) {
        if (currentUserId == null || currentUserId <= 0) {
            return null;
        }
        User currentUser = userRepo.getUserById(currentUserId);
        return currentUser;
    }

    public static <T> FoResponse<T> buildNotLoginErr() {
        return FoResponse.userErrResponse(FoConstants.FEC_NOT_LOGIN_YET, "You have not login yet", null);
    }
}
