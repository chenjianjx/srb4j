package org.srb4j.impl.itcase.support;

import java.io.FileNotFoundException;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

/**
 * To add some behavior for spring junit runer. for example:
 * http://stackoverflow.com/questions/8272930/log4j-in-junit-test-case
 * 
 * @author chenjianjx@gmail.com
 *
 */
@SuppressWarnings("deprecation")
public class MySpringJunit4ClassRunner extends SpringJUnit4ClassRunner {
	static {
		try {
			Log4jConfigurer.initLogging("classpath:log4j-test.xml");
		} catch (FileNotFoundException ex) {
			System.err.println("Cannot Initialize log4j");
			ex.printStackTrace();
		}
	}

	public MySpringJunit4ClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}
}
