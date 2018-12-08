package com.github.chenjianjx.srb4jfullsample.intf.fo.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A response object containing OAuth2 auth token fields
 * 
 * @author chenjianjx@gmail.com
 *
 */
@ApiModel(value = "AuthTokenResult", description = "It's compatible with OAuth2 Token Response")
public class FoAuthTokenResult {

	@ApiModelProperty("access_token")
	@JsonProperty("access_token")
	private String accessToken;

	@ApiModelProperty("refresh_token")
	@JsonProperty("refresh_token")
	private String refreshToken;

	@ApiModelProperty("expires_in")
	@JsonProperty("expires_in")
	private long expiresIn;

	@ApiModelProperty("token_type")
	@JsonProperty("token_type")
	private String tokenType;

	@ApiModelProperty("user_principal")
	@JsonProperty("user_principal")
	private String userPrincipal;

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public void defaultTokenType() {
		this.setTokenType("Bearer");

	}

	public String getUserPrincipal() {
		return userPrincipal;
	}

	public void setUserPrincipal(String userPrincipal) {
		this.userPrincipal = userPrincipal;
	}

}
