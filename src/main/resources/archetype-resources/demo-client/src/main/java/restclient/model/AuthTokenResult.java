package ${groupId}.${rootArtifactId}.restclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;



/**
 * It&#39;s compatible with OAuth2 Token Response
 **/

@ApiModel(description = "It's compatible with OAuth2 Token Response")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-01-08T20:25:00.614+08:00")
public class AuthTokenResult   {
  
  private String accessToken = null;
  private String refreshToken = null;
  private Long expiresIn = null;
  private String tokenType = null;

  
  /**
   * access_token
   **/
  
  @ApiModelProperty(value = "access_token")
  @JsonProperty("access_token")
  public String getAccessToken() {
    return accessToken;
  }
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  
  /**
   * refresh_token
   **/
  
  @ApiModelProperty(value = "refresh_token")
  @JsonProperty("refresh_token")
  public String getRefreshToken() {
    return refreshToken;
  }
  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  
  /**
   * expires_in
   **/
  
  @ApiModelProperty(value = "expires_in")
  @JsonProperty("expires_in")
  public Long getExpiresIn() {
    return expiresIn;
  }
  public void setExpiresIn(Long expiresIn) {
    this.expiresIn = expiresIn;
  }

  
  /**
   * token_type
   **/
  
  @ApiModelProperty(value = "token_type")
  @JsonProperty("token_type")
  public String getTokenType() {
    return tokenType;
  }
  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthTokenResult authTokenResult = (AuthTokenResult) o;
    return Objects.equals(accessToken, authTokenResult.accessToken) &&
        Objects.equals(refreshToken, authTokenResult.refreshToken) &&
        Objects.equals(expiresIn, authTokenResult.expiresIn) &&
        Objects.equals(tokenType, authTokenResult.tokenType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accessToken, refreshToken, expiresIn, tokenType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthTokenResult {\n");
    
    sb.append("    accessToken: ").append(toIndentedString(accessToken)).append("\n");
    sb.append("    refreshToken: ").append(toIndentedString(refreshToken)).append("\n");
    sb.append("    expiresIn: ").append(toIndentedString(expiresIn)).append("\n");
    sb.append("    tokenType: ").append(toIndentedString(tokenType)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

