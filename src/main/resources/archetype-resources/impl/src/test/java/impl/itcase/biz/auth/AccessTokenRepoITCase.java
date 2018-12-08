package com.github.chenjianjx.srb4jfullsample.impl.itcase.biz.auth;

import com.github.chenjianjx.srb4jfullsample.impl.biz.auth.AccessToken;
import com.github.chenjianjx.srb4jfullsample.impl.biz.auth.AccessTokenRepo;
import com.github.chenjianjx.srb4jfullsample.impl.itcase.BaseITCase;
import com.github.chenjianjx.srb4jfullsample.utils.lang.MyLangUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author chenjianjx@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext-test.xml"})
public class AccessTokenRepoITCase extends BaseITCase {

    @Resource
    AccessTokenRepo repo;

    @Test
    public void crudTest() throws Exception {

        AccessToken forInsert = new AccessToken();

        forInsert.setTokenStr("someToken");
        forInsert.setRefreshTokenStr("someRefreshToken");
        forInsert.setUserId(1l);
        forInsert.setLifespan(3600l);
        forInsert.setExpiresAt(MyLangUtils.newCalendar(1));

        forInsert.setCreatedBy("someCreatedBy");
        forInsert.setUpdatedAt(new GregorianCalendar());
        forInsert.setUpdatedBy("someUpdatedBy");

        //save
        long tokenId = repo.saveNewToken(forInsert);
        Assert.assertTrue(tokenId > 0);


        //retrieve it
        AccessToken postInsert = repo.getByTokenStr("someToken");
        System.out.println("postInsert: " + postInsert);

        Assert.assertEquals(tokenId, postInsert.getId());
        Assert.assertEquals("someToken", postInsert.getTokenStr());
        Assert.assertEquals("someRefreshToken", postInsert.getRefreshTokenStr());
        Assert.assertEquals(1l, postInsert.getUserId());
        Assert.assertEquals(3600l, postInsert.getLifespan());
        Assert.assertEquals(MyLangUtils.newCalendar(1), postInsert.getExpiresAt());

        Assert.assertNotNull(postInsert.getCreatedAt());
        Assert.assertEquals("someCreatedBy", postInsert.getCreatedBy());
        Assert.assertNull(postInsert.getUpdatedAt());  //ignored during inserting
        Assert.assertNull(postInsert.getUpdatedBy());  //ignored during inserting

        Thread.sleep(10l);


        //update
        AccessToken forUpdate = repo.getByTokenStr("someToken");

        forUpdate.setTokenStr("newToken");
        forUpdate.setRefreshTokenStr("newRefreshToken");
        forUpdate.setUserId(2l);
        forUpdate.setLifespan(7200l);
        forUpdate.setExpiresAt(MyLangUtils.newCalendar(2));

        forUpdate.setCreatedAt(null);
        forUpdate.setCreatedBy("newCreatedBy");
        forUpdate.setUpdatedBy("someUpdatedBy");
        repo.updateAccessToken(forUpdate);

        AccessToken postUpdate = repo.getByTokenStr("newToken");
        System.out.println("postUpdate: " + postUpdate);

        Assert.assertEquals(tokenId, postUpdate.getId());
        Assert.assertEquals("newToken", postUpdate.getTokenStr());
        Assert.assertEquals("newRefreshToken", postUpdate.getRefreshTokenStr());
        Assert.assertEquals(1l, postUpdate.getUserId()); //user id cannot be changed
        Assert.assertEquals(7200l, postUpdate.getLifespan());
        Assert.assertEquals(MyLangUtils.newCalendar(2), postUpdate.getExpiresAt());

        Assert.assertEquals(postInsert.getCreatedAt(), postUpdate.getCreatedAt()); //should not change
        Assert.assertEquals("someCreatedBy", postUpdate.getCreatedBy()); //should not change
        Assert.assertTrue(postUpdate.getUpdatedAt().after(postUpdate.getCreatedAt()));
        Assert.assertEquals("someUpdatedBy", postUpdate.getUpdatedBy());

        //delete
        repo.deleteByTokenStr("newToken");
        Assert.assertNull(repo.getByTokenStr("newToken"));
    }


    @Test(expected = DuplicateKeyException.class)
    public void duplicateTokenStrTest() throws Exception {
        AccessToken t1 = buildToken("token", RandomStringUtils.randomAlphanumeric(3));
        AccessToken t2 = buildToken("token", RandomStringUtils.randomAlphanumeric(4));
        repo.saveNewToken(t1);
        repo.saveNewToken(t2);
    }

    @Test(expected = DuplicateKeyException.class)
    public void duplicateRefreshTokenStrTest() throws Exception {
        AccessToken t1 = buildToken(RandomStringUtils.randomAlphanumeric(3), "refreshToken");
        AccessToken t2 = buildToken(RandomStringUtils.randomAlphanumeric(4), "refreshToken");
        repo.saveNewToken(t1);
        repo.saveNewToken(t2);
    }

    private AccessToken buildToken(String tokenStr, String refreshTokenStr) {
        AccessToken token = new AccessToken();
        token.setLifespan(1l);
        token.setExpiresAt(Calendar.getInstance());
        token.setUserId(1l);
        token.setTokenStr(tokenStr);
        token.setRefreshTokenStr(refreshTokenStr);
        token.setCreatedBy("someone");
        return token;
    }
}
