package ${groupId}.impl.itcase.support.aop.applayer;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import ${groupId}.impl.itcase.support.MySpringJunit4ClassRunner;
import ${groupId}.impl.itcase.support.aop.applayer.TestManager.TestRequest;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@RunWith(MySpringJunit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml" })
public class ManagerAspectITCase {

	@Resource
	TestManager testManager;

	@Test
	public void testIt() throws Exception {
		TestRequest request = new TestRequest();
		Assert.assertEquals("withSpace", testManager.echoRequest(123l, request)
				.getWithSpace());
	}

}
