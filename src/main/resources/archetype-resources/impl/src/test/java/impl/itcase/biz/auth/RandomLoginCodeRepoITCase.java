package ${groupId}.${rootArtifactId}.impl.itcase.biz.auth;

import java.util.GregorianCalendar;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import ${groupId}.${rootArtifactId}.impl.biz.auth.RandomLoginCode;
import ${groupId}.${rootArtifactId}.impl.biz.auth.RandomLoginCodeRepo;
import ${groupId}.${rootArtifactId}.impl.itcase.support.MySpringJunit4ClassRunner;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@RunWith(MySpringJunit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml" })
public class RandomLoginCodeRepoITCase {

	@Resource
	RandomLoginCodeRepo repo;

	@Test
	public void crudTest() throws Exception {
		// insert
		long userId = System.currentTimeMillis();

		String codeStr = "test-code-" + System.currentTimeMillis();
		RandomLoginCode t = buildCode(userId, codeStr);
		repo.saveNewCode(t);
		Assert.assertNotNull(t.getId());
		Assert.assertNotNull(t.getCreatedAt());
		System.out.println(t);

		// get by userId
		t = repo.getByUserId(userId);
		Assert.assertNotNull(t);

		// delete by userId
		repo.deleteByUserId(userId);
		t = repo.getByUserId(userId);
		Assert.assertNull(t);
	}

	@Test(expected = DuplicateKeyException.class)
	public void duplicateTest() throws Exception {
		long userId = System.currentTimeMillis();
		RandomLoginCode t1 = buildCode(userId, "code1");
		RandomLoginCode t2 = buildCode(userId, "code2");
		repo.saveNewCode(t1);
		repo.saveNewCode(t2);
	}

	private RandomLoginCode buildCode(long userId, String codeStr) {
		RandomLoginCode t = new RandomLoginCode();
		t.setCodeStr(codeStr);
		t.setUserId(userId);
		t.setExpiresAt(new GregorianCalendar());
		t.setCreatedAt(new GregorianCalendar());
		t.setCreatedBy("some-man");
		return t;
	}

}
