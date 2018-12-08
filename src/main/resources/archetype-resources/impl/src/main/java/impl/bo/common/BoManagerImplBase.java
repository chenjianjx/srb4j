package ${package}.impl.bo.common;

import ${package}.impl.biz.staff.StaffUser;
import ${package}.impl.biz.staff.StaffUserRepo;
import ${package}.intf.bo.basic.BoConstants;
import ${package}.intf.bo.basic.BoResponse;

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
