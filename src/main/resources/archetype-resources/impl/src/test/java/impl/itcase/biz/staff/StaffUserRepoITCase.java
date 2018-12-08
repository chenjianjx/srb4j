package com.github.chenjianjx.srb4jfullsample.impl.itcase.biz.staff;


import com.github.chenjianjx.srb4jfullsample.impl.biz.staff.StaffUser;
import com.github.chenjianjx.srb4jfullsample.impl.biz.staff.StaffUserRepo;
import com.github.chenjianjx.srb4jfullsample.impl.itcase.BaseITCase;
import com.github.chenjianjx.srb4jfullsample.utils.lang.MyLangUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.GregorianCalendar;

/**
 * @author chenjianjx@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext-test.xml"})
public class StaffUserRepoITCase extends BaseITCase {

    @Resource
    StaffUserRepo repo;

    @Test
    public void crudTest() throws Exception {

        StaffUser forInsert = new StaffUser();

        forInsert.setUsername("someName");
        forInsert.setPassword("somePassword");
        forInsert.setLastLoginDate(MyLangUtils.newCalendar(1l));


        forInsert.setCreatedBy("someCreatedBy");
        forInsert.setUpdatedAt(new GregorianCalendar());
        forInsert.setUpdatedBy("someUpdatedBy");

        //save
        long userId = repo.saveNewStaffUser(forInsert);
        Assert.assertTrue(userId > 0);


        //retrieve it
        StaffUser postInsert = repo.getStaffUserById(userId);
        System.out.println("postInsert: " + postInsert);

        Assert.assertEquals(userId, postInsert.getId());
        Assert.assertEquals("someName", postInsert.getUsername());
        Assert.assertEquals("somePassword", postInsert.getPassword());
        Assert.assertEquals(MyLangUtils.newCalendar(1l), postInsert.getLastLoginDate());

        Assert.assertNotNull(postInsert.getCreatedAt());
        Assert.assertEquals("someCreatedBy", postInsert.getCreatedBy());
        Assert.assertNull(postInsert.getUpdatedAt());  //ignored during inserting
        Assert.assertNull(postInsert.getUpdatedBy());  //ignored during inserting

        Thread.sleep(10l);


        //update
        StaffUser forUpdate = repo.getStaffUserById(userId);
        forUpdate.setUsername("newName");
        forUpdate.setPassword("newPassword");
        forUpdate.setLastLoginDate(MyLangUtils.newCalendar(2l));

        forUpdate.setCreatedAt(null);
        forUpdate.setCreatedBy("newCreatedBy");
        forUpdate.setUpdatedBy("someUpdatedBy");
        repo.updateStaffUser(forUpdate);

        StaffUser postUpdate = repo.getStaffUserById(userId);
        System.out.println("postUpdate: " + postUpdate);

        Assert.assertEquals(userId, postUpdate.getId());
        Assert.assertEquals("someName", postUpdate.getUsername()); //username cannot be changed
        Assert.assertEquals("newPassword", postUpdate.getPassword()); //email cannot be changed
        Assert.assertEquals(MyLangUtils.newCalendar(2l), postUpdate.getLastLoginDate());

        Assert.assertEquals(postInsert.getCreatedAt(), postUpdate.getCreatedAt()); //should not change
        Assert.assertEquals("someCreatedBy", postUpdate.getCreatedBy()); //should not change
        Assert.assertTrue(postUpdate.getUpdatedAt().after(postUpdate.getCreatedAt()));
        Assert.assertEquals("someUpdatedBy", postUpdate.getUpdatedBy());

    }


    @Test(expected = DuplicateKeyException.class)
    public void duplicateUserTest() throws Exception {
        StaffUser u1 = buildStaffUser("someUser");
        StaffUser u2 = buildStaffUser("someUser");
        repo.saveNewStaffUser(u1);
        repo.saveNewStaffUser(u2);
    }

    private StaffUser buildStaffUser(String username) {
        StaffUser u = new StaffUser();
        u.setUsername(username);
        u.setPassword("somePassword");
        u.setLastLoginDate(null);
        u.setCreatedBy("someone");
        return u;
    }

}
