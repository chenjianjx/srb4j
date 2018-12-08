package com.github.chenjianjx.srb4jfullsample.impl.itcase.support.aop.applayer;

import org.springframework.stereotype.Service;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Service
public class TestManager {

	public TestRequest echoRequest(Long userId, TestRequest request) {
		return request;
	}

	public static class TestRequest  {
		private String withSpace = "   withSpace   ";

		public String getWithSpace() {
			return withSpace;
		}

		public void setWithSpace(String withSpace) {
			this.withSpace = withSpace;
		}

	}

}
