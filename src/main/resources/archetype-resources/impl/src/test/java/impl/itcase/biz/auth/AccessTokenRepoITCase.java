package ${package}.impl.itcase.biz.auth;

import java.util.GregorianCalendar;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import ${package}.impl.biz.auth.AccessToken;
import ${package}.impl.biz.auth.AccessTokenRepo;
import ${package}.impl.itcase.support.MySpringJunit4ClassRunner;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@RunWith(MySpringJunit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml" })
public class AccessTokenRepoITCase {

	@Resource
	AccessTokenRepo repo;

	@Test
	public void crudTest() throws Exception {
		//insert
		AccessToken t = new AccessToken();
		String tokenStr = "test-token-" + System.currentTimeMillis();
		String refreshTokenStr = "test-refresh-token-" + System.currentTimeMillis();
		t.setTokenStr(tokenStr);
		t.setRefreshTokenStr(refreshTokenStr);
		t.setUserId(2l);
		t.setLifespan(3600);
		t.setExpiresAt(new GregorianCalendar());		
		t.setCreatedBy("some-man");
		repo.saveNewToken(t);
		Assert.assertNotNull(t.getId());
		Assert.assertNotNull(t.getCreatedAt());
		System.out.println(t);
		
		//get by token
		t = repo.getByTokenStr(tokenStr);
		Assert.assertNotNull(t);
		
		
		//get by refresh token
		t = repo.getByRefreshTokenStr(refreshTokenStr);
		Assert.assertNotNull(t);
		
		//update token
		t.setExpiresAt(new GregorianCalendar());
		repo.updateAccessToken(t);
		t = repo.getByTokenStr(tokenStr);
		System.out.println(t.getExpiresAt());
		
		//delete by token
		repo.deleteByTokenStr(tokenStr);
		t = repo.getByTokenStr(tokenStr);
		Assert.assertNull(t);
	}

}
