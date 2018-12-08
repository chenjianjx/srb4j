package com.github.chenjianjx.srb4jfullsample.impl.bo.frontuser;

import com.github.chenjianjx.srb4jfullsample.impl.biz.staff.StaffUser;
import com.github.chenjianjx.srb4jfullsample.impl.biz.user.User;
import com.github.chenjianjx.srb4jfullsample.impl.biz.user.UserRepo;
import com.github.chenjianjx.srb4jfullsample.impl.bo.common.BoManagerImplBase;
import com.github.chenjianjx.srb4jfullsample.utils.lang.MyLangUtils;
import com.github.chenjianjx.srb4jfullsample.intf.bo.basic.BoResponse;
import com.github.chenjianjx.srb4jfullsample.intf.bo.frontuser.BoFrontUserManager;
import com.github.chenjianjx.srb4jfullsample.intf.fo.user.FoUser;
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
