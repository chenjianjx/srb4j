package ${package}.intf.bo.frontuser;

import ${package}.intf.bo.basic.BoResponse;
import ${package}.intf.fo.user.FoUser;

import java.util.List;

/**
 * managing front users
 */
public interface BoFrontUserManager {

    BoResponse<List<FoUser>> getAllUsers(Long currentStaffUserId);
}
