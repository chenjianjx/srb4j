package ${package}.impl.itcase.support.aop.applayer;

import javax.annotation.Resource;

import ${package}.impl.itcase.BaseITCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import ${package}.impl.itcase.support.aop.applayer.TestManager.TestRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml" })
public class ManagerAspectITCase extends BaseITCase {

	@Resource
	TestManager testManager;

	@Test
	public void testIt() throws Exception {
		TestRequest request = new TestRequest();
		Assert.assertEquals("withSpace", testManager.echoRequest(123l, request)
				.getWithSpace());
	}

}
