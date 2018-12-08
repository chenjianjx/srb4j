package com.github.chenjianjx.srb4jfullsample.impl.bo.common;

import com.github.chenjianjx.srb4jfullsample.impl.biz.staff.StaffUser;
import com.github.chenjianjx.srb4jfullsample.impl.biz.staff.StaffUserRepo;
import com.github.chenjianjx.srb4jfullsample.intf.bo.basic.BoConstants;
import com.github.chenjianjx.srb4jfullsample.intf.bo.basic.BoResponse;

import javax.annotation.Resource;

/**
 * @author chenjianjx@gmail.com
 */
public class BoManagerImplBase {

    @Resource
    StaffUserRepo staffUserRepo;

    /**
     * if the currentUserId is invalid ,return null
     *
     * @param currentUserId
     * @return
     */
    protected StaffUser getCurrentStaffUserConsideringInvalidId(Long currentUserId) {
        if (currentUserId == null || currentUserId <= 0) {
            return null;
        }
        StaffUser currentUser = staffUserRepo.getStaffUserById(currentUserId);
        return currentUser;
    }

    public static <T> BoResponse<T> buildNotLoginErr() {
        return BoResponse.userErrResponse(BoConstants.FEC_NOT_LOGIN_YET, "You have not login yet", null);
    }
}
