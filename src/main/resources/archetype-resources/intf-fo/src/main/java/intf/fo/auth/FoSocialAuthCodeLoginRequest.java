package com.github.chenjianjx.srb4jfullsample.intf.fo.auth;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class FoSocialAuthCodeLoginRequest {

	@NotNull(message = "social source not specified")
	@ApiModelProperty(required = true)
	private String source;

	@NotNull(message = "social site authorization code cannot be empty")
	@ApiModelProperty(required = true)
	private String authCode;

	private Boolean longSession;

	private String clientType;

	private String redirectUri;

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Boolean getLongSession() {
		return longSession;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	@JsonIgnore
	public boolean isLongSession() {
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
