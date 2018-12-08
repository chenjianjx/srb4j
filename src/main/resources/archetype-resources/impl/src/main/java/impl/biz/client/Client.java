package com.github.chenjianjx.srb4jfullsample.impl.biz.client;

import java.util.Arrays;
import java.util.List;

/**
 * Representing the clients of this backend
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class Client {

	public static final String TYPE_DESKTOP = "desktop";
	public static final String TYPE_WEB = "web";
	public static final String TYPE_MOBILE = "mobile";

	public static final List<String> CLIENT_TYPES = Arrays.asList(TYPE_DESKTOP,
			TYPE_WEB, TYPE_MOBILE);

	public static boolean isValidClientType(String clientType) {
		return CLIENT_TYPES.contains(clientType);
	}
}
