package com.github.chenjianjx.srb4jfullsample.impl.itcase.biz.user;


import com.github.chenjianjx.srb4jfullsample.impl.biz.user.EmailVerificationDigest;
import com.github.chenjianjx.srb4jfullsample.impl.biz.user.EmailVerificationDigestRepo;
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
 * 
 * @author chenjianjx@gmail.com
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml" })
public class EmailVerificationDigestRepoITCase extends BaseITCase {

	@Resource
	EmailVerificationDigestRepo repo;

	@Test
	public void crudTest() throws Exception {
		EmailVerificationDigest forInsert = new EmailVerificationDigest();

		forInsert.setDigestStr("someDigest");
		forInsert.setUserId(1l);
		forInsert.setExpiresAt(MyLangUtils.newCalendar(1l));

		forInsert.setCreatedBy("someCreatedBy");
		forInsert.setUpdatedAt(new GregorianCalendar());
		forInsert.setUpdatedBy("someUpdatedBy");

		//save
		long digestId = repo.saveNewDigest(forInsert);
		Assert.assertTrue(digestId > 0);


		//retrieve it
		EmailVerificationDigest postInsert = repo.getByUserId(1l);
		System.out.println("postInsert: " + postInsert);

		Assert.assertEquals(digestId, postInsert.getId());
		Assert.assertEquals("someDigest", postInsert.getDigestStr());
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
	public void duplicateTest_userDuplicated() throws Exception {
		long userId = System.currentTimeMillis();
		EmailVerificationDigest t1 = buildDigest(userId, "digest1");
		EmailVerificationDigest t2 = buildDigest(userId, "digest2");
		repo.saveNewDigest(t1);
		repo.saveNewDigest(t2);
	}

	@Test(expected = DuplicateKeyException.class)
	public void duplicateTest_digestDuplicated() throws Exception {
		long userId1 = System.currentTimeMillis();
		Thread.sleep(5);
		long userId2 = System.currentTimeMillis();
		EmailVerificationDigest t1 = buildDigest(userId1, "digest");
		EmailVerificationDigest t2 = buildDigest(userId2, "digest");
		repo.saveNewDigest(t1);
		repo.saveNewDigest(t2);
	}

	private EmailVerificationDigest buildDigest(long userId, String digestStr) {
		EmailVerificationDigest t = new EmailVerificationDigest();
		t.setDigestStr(digestStr);
		t.setUserId(userId);
		t.setExpiresAt(new GregorianCalendar());
		t.setCreatedAt(new GregorianCalendar());
		t.setCreatedBy("some-man");
		return t;
	}

}
