package com.github.chenjianjx.srb4jfullsample.impl.support.aop.applayer;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import com.github.chenjianjx.srb4jfullsample.utils.lang.MyLangUtils;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Component
@Aspect
public class ManagerAspect {

	@Before("bean(*Manager)  && execution(public * *(..))")
	public void doAdvice(JoinPoint joinPoint) {	 
		Object[] args = joinPoint.getArgs();
		if (args == null) {
			return;
		}
		for (Object arg : args) {
			if (arg == null) {
				continue;
			}
			// if it is a request object
			if (arg.getClass().getSimpleName().endsWith("Request")) {
				MyLangUtils.trimRootLevelStringFields(arg);
			}
		}
	}
}
