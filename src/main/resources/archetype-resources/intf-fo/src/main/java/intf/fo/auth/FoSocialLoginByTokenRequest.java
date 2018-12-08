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
public class FoSocialLoginByTokenRequest {

	@NotNull(message = "social source not specified")
	@ApiModelProperty(required = true)
	private String source;

	@NotNull(message = "social site token cannot be empty")
	@ApiModelProperty(required = true)
	private String token;

	private String clientType;

	private Boolean longSession;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getLongSession() {
		return longSession;
	}

	@JsonIgnore
	public boolean isLongSession() {
		return longSession != null && longSession;
	}

	public void setLongSession(Boolean longSession) {
		this.longSession = longSession;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
