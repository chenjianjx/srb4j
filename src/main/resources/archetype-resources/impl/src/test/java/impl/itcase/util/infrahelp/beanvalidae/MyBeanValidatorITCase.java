package ${groupId}.${rootArtifactId}.impl.itcase.util.infrahelp.beanvalidae;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import ${groupId}.${rootArtifactId}.impl.itcase.support.MySpringJunit4ClassRunner;
import ${groupId}.${rootArtifactId}.impl.util.infrahelp.beanvalidae.MyValidator;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@RunWith(MySpringJunit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml" })
public class MyBeanValidatorITCase {

	@Resource
	MyValidator myBeanValidator;

	@Test
	public void validateBean_NullBean() {
		List<String> errors = myBeanValidator.validateBean(null, "bean-null");
		Assert.assertEquals(1, errors.size());
		Assert.assertTrue(errors.contains("bean-null"));
	}

	@Test
	public void validateBean_InvalidProp() {
		Mb mb = new Mb();
		List<String> errors = myBeanValidator.validateBean(mb, "bean-null");
		Assert.assertEquals(2, errors.size());
		Assert.assertTrue(errors.contains("email-null"));
		Assert.assertTrue(errors.contains("password-null"));
	}

	@Test
	public void validateBean_NoErrors() {
		Mb mb = new Mb();
		mb.email = "hi@hi.com";
		mb.password = "heyya";
		List<String> errors = myBeanValidator.validateBean(mb, "bean-null");
		Assert.assertEquals(0, errors.size());

	}

	private static class Mb {

		@NotNull(message = "email-null")
		private String email;

		@NotNull(message = "password-null")
		private String password;
	}

}
