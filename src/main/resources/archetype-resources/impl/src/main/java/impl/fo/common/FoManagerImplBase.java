package ${package}.impl.fo.common;

import ${package}.impl.biz.user.User;
import ${package}.impl.biz.user.UserRepo;
import ${package}.intf.fo.basic.FoConstants;
import ${package}.intf.fo.basic.FoResponse;

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
