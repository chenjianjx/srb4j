package com.github.chenjianjx.srb4jfullsample.impl.itcase.biz.user;

import com.github.chenjianjx.srb4jfullsample.impl.biz.user.User;
import com.github.chenjianjx.srb4jfullsample.impl.biz.user.UserRepo;
import com.github.chenjianjx.srb4jfullsample.impl.itcase.BaseITCase;
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
public class UserRepoITCase extends BaseITCase {

    @Resource
    UserRepo repo;

    @Test
    public void crudTest() throws Exception {


        User forInsert = new User();

        forInsert.setSource("someSource");
        forInsert.setPassword("somePassword");
        forInsert.setEmail("someEmail@test.com");
        forInsert.setEmailVerified(true);

        forInsert.setCreatedBy("someCreatedBy");
        forInsert.setUpdatedAt(new GregorianCalendar());
        forInsert.setUpdatedBy("someUpdatedBy");

        //save
        long userId = repo.saveNewUser(forInsert);
        Assert.assertTrue(userId > 0);


        //retrieve it
        User postInsert = repo.getUserById(userId);
        System.out.println("postInsert: " + postInsert);

        Assert.assertEquals(userId, postInsert.getId());
        Assert.assertEquals("someSource", postInsert.getSource());
        Assert.assertEquals("someEmail@test.com", postInsert.getEmail());
        Assert.assertEquals("somePassword", postInsert.getPassword());
        Assert.assertTrue(postInsert.isEmailVerified());

        Assert.assertNotNull(postInsert.getCreatedAt());
        Assert.assertEquals("someCreatedBy", postInsert.getCreatedBy());
        Assert.assertNull(postInsert.getUpdatedAt());  //ignored during inserting
        Assert.assertNull(postInsert.getUpdatedBy());  //ignored during inserting

        Thread.sleep(10l);

        //update
        User forUpdate = repo.getUserById(userId);
        forUpdate.setSource("newSource");
        forUpdate.setPassword("newPassword");
        forUpdate.setEmail("newEmail@test.com");
        forUpdate.setEmailVerified(false);
        forUpdate.setCreatedAt(null);
        forUpdate.setCreatedBy("newCreatedBy");
        forUpdate.setUpdatedBy("someUpdatedBy");
        repo.updateUser(forUpdate);

        User postUpdate = repo.getUserById(userId);
        System.out.println("postUpdate: " + postUpdate);

        Assert.assertEquals(userId, postUpdate.getId());
        Assert.assertEquals("someSource", postUpdate.getSource()); //source cannot be changed
        Assert.assertEquals("someEmail@test.com", postUpdate.getEmail()); //email cannot be changed
        Assert.assertFalse(postUpdate.isEmailVerified());
        Assert.assertEquals("newPassword", postUpdate.getPassword());
        Assert.assertEquals(postInsert.getCreatedAt(), postUpdate.getCreatedAt()); //should not change
        Assert.assertEquals("someCreatedBy", postUpdate.getCreatedBy()); //should not change
        Assert.assertTrue(postUpdate.getUpdatedAt().after(postUpdate.getCreatedAt()));
        Assert.assertEquals("someUpdatedBy", postUpdate.getUpdatedBy());

        Thread.sleep(10l);

        //update again
        User forUpdate2 = repo.getUserById(userId);
        forUpdate2.setUpdatedBy("newUpdatedBy");
        repo.updateUser(forUpdate2);
        User postUpdate2 = repo.getUserById(userId);
        Assert.assertEquals("newUpdatedBy", postUpdate2.getUpdatedBy());
        Assert.assertTrue(postUpdate2.getUpdatedAt().after(postUpdate.getUpdatedAt()));
    }


    @Test(expected = DuplicateKeyException.class)
    public void duplicateEmailTest() throws Exception {
        User u1 = buildUser("someEmail");
        User u2 = buildUser("someEmail");
        repo.saveNewUser(u1);
        repo.saveNewUser(u2);
    }


    private User buildUser(String email) {
        User u = new User();
        u.setEmail(email);
        u.setSource("someSource");
        u.setPassword("somePassword");
        u.setCreatedBy("someone");
        return u;
    }


}
