package ${package}.intf.bo.staffuser;

import ${package}.intf.bo.basic.BoResponse;

/**
 * @author chenjianjx@gmail.com
 */
public interface BoStaffUserManager {

    String USER_NAME_REGEX = "[a-z][a-z0-9_]{2,9}";

    BoResponse<Void> changePassword(Long currentStaffUserId, BoChangePasswordRequest request);

}
