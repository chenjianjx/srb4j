package com.github.chenjianjx.srb4jfullsample.impl.biz.user;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.github.chenjianjx.srb4jfullsample.impl.biz.common.EntityBase;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class User extends EntityBase implements Serializable {

	public static final String SOURCE_GOOGLE = "google";
	public static final String SOURCE_FACEBOOK = "facebook";
	public static final String SOURCE_LOCAL = "local";

	public static final List<String> SOCIAL_ACCOUNT_SOURCES = Arrays.asList(
			SOURCE_GOOGLE, SOURCE_FACEBOOK);

	/**
	 * 
	 */
	private static final long serialVersionUID = -4893003757160145896L;

	private String email;

	/**
	 * has the email been verified ?
	 */
	private boolean emailVerified;

	/**
	 * where the user is from
	 */
	private String source;

	/**
	 * encrypted password
	 */
	private String password;

	public static boolean isValidSocialAccountSource(String source) {
		return SOCIAL_ACCOUNT_SOURCES.contains(source);
	}

	public static String decidePrincipalFromLocal(String email) {
		return decidePrincipal(SOURCE_LOCAL, email);
	}

	public static String decidePrincipal(String source, String userNameInSource) {
		return source + ":" + userNameInSource;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * unique. the format is "source:username", such as "facebook:chenjianjx",
	 * or "local:test@test.com"
	 */
	public String getPrincipal() {
		return decidePrincipal(source, email);
	}

	/**
	 * you don't call this method. this is for some reflection-based
	 * framework to call.
	 * 
	 * @param principal
	 */
	public void setPrincipal(String principal) {
		// do nothing
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public boolean isLocal() {
		return SOURCE_LOCAL.equals(this.source);
	}
}
