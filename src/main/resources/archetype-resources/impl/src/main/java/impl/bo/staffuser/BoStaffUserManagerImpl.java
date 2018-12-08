package ${package}.impl.bo.staffuser;

import ${package}.impl.biz.staff.StaffUser;
import ${package}.impl.biz.staff.StaffUserRepo;
import ${package}.impl.bo.common.BoManagerImplBase;
import ${package}.impl.support.beanvalidate.MyValidator;
import ${package}.impl.support.beanvalidate.ValidationError;
import ${package}.intf.bo.basic.BoConstants;
import ${package}.intf.bo.basic.BoResponse;
import ${package}.intf.bo.staffuser.BoChangePasswordRequest;
import ${package}.intf.bo.staffuser.BoStaffUserManager;
import ${package}.utils.lang.MyCodecUtils;
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
