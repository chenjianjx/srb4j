package com.github.chenjianjx.srb4jfullsample.impl.itcase.biz.user;


import com.github.chenjianjx.srb4jfullsample.impl.biz.user.ForgetPasswordVerifyCode;
import com.github.chenjianjx.srb4jfullsample.impl.biz.user.ForgetPasswordVerifyCodeRepo;
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
public class ForgetPasswordVerifyCodeRepoITCase extends BaseITCase {

    @Resource
    ForgetPasswordVerifyCodeRepo repo;

    @Test
    public void crudTest() throws Exception {

        ForgetPasswordVerifyCode forInsert = new ForgetPasswordVerifyCode();

        forInsert.setCodeStr("someCode");
        forInsert.setUserId(1l);
        forInsert.setExpiresAt(MyLangUtils.newCalendar(1l));

        forInsert.setCreatedBy("someCreatedBy");
        forInsert.setUpdatedAt(new GregorianCalendar());
        forInsert.setUpdatedBy("someUpdatedBy");

        //save
        long codeId = repo.saveNewCode(forInsert);
        Assert.assertTrue(codeId > 0);


        //retrieve it
        ForgetPasswordVerifyCode postInsert = repo.getByUserId(1l);
        System.out.println("postInsert: " + postInsert);

        Assert.assertEquals(codeId, postInsert.getId());
        Assert.assertEquals("someCode", postInsert.getCodeStr());
        Assert.assertEquals(1l, postInsert.getUserId());
        Assert.assertEquals(MyLangUtils.newCalendar(1l), postInsert.getExpiresAt());

        Assert.assertNotNull(postInsert.getCreatedAt());
        Assert.assertEquals("someCreatedBy", postInsert.getCreatedBy());
        Assert.assertNull(postInsert.getUpdatedAt());  //ignored during inserting
        Assert.assertNull(postInsert.getUpdatedBy());  //ignored during inserting

        //delete
        repo.deleteByUserId(1l);
        Assert.assertNull(repo.getByUserId(1l));
    }

    @Test(expected = DuplicateKeyException.class)
    public void duplicateUserTest() throws Exception {
        long userId = System.currentTimeMillis();
        ForgetPasswordVerifyCode t1 = buildCode(userId, "code1");
        ForgetPasswordVerifyCode t2 = buildCode(userId, "code2");
        repo.saveNewCode(t1);
        repo.saveNewCode(t2);
    }

    private ForgetPasswordVerifyCode buildCode(long userId, String codeStr) {
        ForgetPasswordVerifyCode t = new ForgetPasswordVerifyCode();
        t.setCodeStr(codeStr);
        t.setUserId(userId);
        t.setExpiresAt(new GregorianCalendar());
        t.setCreatedAt(new GregorianCalendar());
        t.setCreatedBy("some-man");
        return t;
    }

}
