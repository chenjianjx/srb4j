package com.github.chenjianjx.srb4jfullsample.impl.itcase.util.infrahelp.beanvalidae;

import com.github.chenjianjx.srb4jfullsample.impl.itcase.BaseITCase;
import com.github.chenjianjx.srb4jfullsample.impl.support.beanvalidate.MyValidator;
import com.github.chenjianjx.srb4jfullsample.impl.support.beanvalidate.ValidationError;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext-test.xml" })
public class MyBeanValidatorITCase extends BaseITCase {

	@Resource
	MyValidator myBeanValidator;

	@Test
	public void validateBean_NullBean() {
		ValidationError errorResult = myBeanValidator.validateBean(null, "bean-null");
		Assert.assertTrue(errorResult.hasErrors());
		Assert.assertTrue(errorResult.getNonFieldError().equals("bean-null"));
	}

	@Test
	public void validateBean_InvalidProp() {
		Mb mb = new Mb();
		ValidationError errorResult = myBeanValidator.validateBean(mb, "bean-null");
		Assert.assertEquals(2, errorResult.getFieldErrors().size());
		Assert.assertEquals("email-null", errorResult.getFieldErrors().get("email"));
		Assert.assertEquals("password-null", errorResult.getFieldErrors().get("password"));
	}

	@Test
	public void validateBean_NoErrors() {
		Mb mb = new Mb();
		mb.email = "hi@hi.com";
		mb.password = "heyya";
		ValidationError errorResult = myBeanValidator.validateBean(mb, "bean-null");
		Assert.assertFalse(errorResult.hasErrors());

	}

	private static class Mb {

		@NotNull(message = "email-null")
		private String email;

		@NotNull(message = "password-null")
		private String password;
	}

}
