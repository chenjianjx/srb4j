package ${package}.impl.bo.frontuser;

import ${package}.impl.biz.staff.StaffUser;
import ${package}.impl.biz.user.User;
import ${package}.impl.biz.user.UserRepo;
import ${package}.impl.bo.common.BoManagerImplBase;
import ${package}.utils.lang.MyLangUtils;
import ${package}.intf.bo.basic.BoResponse;
import ${package}.intf.bo.frontuser.BoFrontUserManager;
import ${package}.intf.fo.user.FoUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenjianjx@gmail.com
 */
@Service("boFrontUserManager")
public class BoFrontUserManagerImpl extends BoManagerImplBase implements BoFrontUserManager {

    @Resource
    UserRepo userRepo;


    @Override
    public BoResponse<List<FoUser>> getAllUsers(Long currentStaffUserId) {
        StaffUser currentStaffUser = getCurrentStaffUserConsideringInvalidId(currentStaffUserId);
        if (currentStaffUser == null) {
            return buildNotLoginErr();
        }

        List<User> bizUsers = userRepo.getAllUsers();
        List<FoUser> foUsers = MyLangUtils.copyPropertiesToNewCollections(FoUser.class, bizUsers);
        return BoResponse.success(foUsers);
    }


}
