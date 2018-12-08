package com.github.chenjianjx.srb4jfullsample.impl.bo.staffuser;

import com.github.chenjianjx.srb4jfullsample.impl.biz.staff.StaffUser;
import com.github.chenjianjx.srb4jfullsample.impl.biz.staff.StaffUserRepo;
import com.github.chenjianjx.srb4jfullsample.impl.bo.common.BoManagerImplBase;
import com.github.chenjianjx.srb4jfullsample.impl.support.beanvalidate.MyValidator;
import com.github.chenjianjx.srb4jfullsample.impl.support.beanvalidate.ValidationError;
import com.github.chenjianjx.srb4jfullsample.intf.bo.basic.BoConstants;
import com.github.chenjianjx.srb4jfullsample.intf.bo.basic.BoResponse;
import com.github.chenjianjx.srb4jfullsample.intf.bo.staffuser.BoChangePasswordRequest;
import com.github.chenjianjx.srb4jfullsample.intf.bo.staffuser.BoStaffUserManager;
import com.github.chenjianjx.srb4jfullsample.utils.lang.MyCodecUtils;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * @author chenjianjx@gmail.com
 */
@Service("boStaffUserManager")
public class BoStaffUserManagerImpl extends BoManagerImplBase implements BoStaffUserManager {

    @Resource
    MyValidator myValidator;

    @Resource
    StaffUserRepo staffUserRepo;


    @Override
    public BoResponse<Void> changePassword(Long currentStaffUserId, BoChangePasswordRequest request) {
        ValidationError error = myValidator.validateBean(request, BoConstants.NULL_REQUEST_BEAN_TIP);
        if (error.hasErrors()) {
            return BoResponse.userErrResponse(BoConstants.FEC_INVALID_INPUT, error.getNonFieldError(), error.getFieldErrors());
        }
        StaffUser currentStaffUser = getCurrentStaffUserConsideringInvalidId(currentStaffUserId);
        if (currentStaffUser == null) {
            return buildNotLoginErr();
        }

        if (request.getCurrentPassword().equals(request.getNewPassword())) {
            return BoResponse.userErrResponse(BoConstants.FEC_INVALID_INPUT, null,
                    ImmutableMap.of("newPassword", "New password cannot be the same as the old one"));
        }

        // now compare password
        if (!MyCodecUtils.isPasswordDjangoMatches(request.getCurrentPassword(), currentStaffUser.getPassword())) {
            return BoResponse.userErrResponse(
                    BoConstants.FEC_INVALID_INPUT, null,
                    ImmutableMap.of("currentPassword",  "Current password is wrong"));
        }

        String encodedNewPassword = MyCodecUtils.encodePasswordLikeDjango(request.getNewPassword());
        StaffUser newStaffUser = staffUserRepo.getStaffUserById(currentStaffUserId);
        newStaffUser.setPassword(encodedNewPassword);
        newStaffUser.setUpdatedBy(newStaffUser.getUsername());
        newStaffUser.setLastLoginDate(Calendar.getInstance()); //consider logged in
        staffUserRepo.updateStaffUser(newStaffUser);

        return BoResponse.success(null);

    }


}
