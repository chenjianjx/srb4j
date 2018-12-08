package com.github.chenjianjx.srb4jfullsample.intf.fo.auth;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * 
 * @author chenjianjx@gmail.com
 *
 */

public class FoLocalLoginRequest {

	@NotNull(message = "Please input email.")
	@ApiModelProperty(required = true)
	private String email;

	@NotNull(message = "Please input password.")
	@ApiModelProperty(required = true)
	private String password;

	private Boolean longSession;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getLongSession() {
		return longSession;
	}
	
	@JsonIgnore
	public boolean isLongSession(){
		return longSession != null && longSession;
	}

	public void setLongSession(Boolean longSession) {
		this.longSession = longSession;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
