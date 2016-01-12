package org.srb4j.restclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.client.StringUtil;
import java.util.Objects;





@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-01-08T20:25:00.614+08:00")
public class ErrorResult   {
  
  private String error = null;
  private String errorDescription = null;
  private String errorDescriptionForUser = null;
  private String exceptionId = null;

  
  /**
   * Error code. Compatible with OAuth2
   **/
  
  @ApiModelProperty(value = "Error code. Compatible with OAuth2")
  @JsonProperty("error")
  public String getError() {
    return error;
  }
  public void setError(String error) {
    this.error = error;
  }

  
  /**
   * Error message for client developers to read. Not for users. Compatible with OAuth2
   **/
  
  @ApiModelProperty(value = "Error message for client developers to read. Not for users. Compatible with OAuth2")
  @JsonProperty("error_description")
  public String getErrorDescription() {
    return errorDescription;
  }
  public void setErrorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
  }

  
  /**
   * Error message that can be shown to users.
   **/
  
  @ApiModelProperty(value = "Error message that can be shown to users.")
  @JsonProperty("error_description_for_user")
  public String getErrorDescriptionForUser() {
    return errorDescriptionForUser;
  }
  public void setErrorDescriptionForUser(String errorDescriptionForUser) {
    this.errorDescriptionForUser = errorDescriptionForUser;
  }

  
  /**
   * exception Id. Please send this to the backend developer for troubleshooting
   **/
  
  @ApiModelProperty(value = "exception Id. Please send this to the backend developer for troubleshooting")
  @JsonProperty("exception_id")
  public String getExceptionId() {
    return exceptionId;
  }
  public void setExceptionId(String exceptionId) {
    this.exceptionId = exceptionId;
  }

  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResult errorResult = (ErrorResult) o;
    return Objects.equals(error, errorResult.error) &&
        Objects.equals(errorDescription, errorResult.errorDescription) &&
        Objects.equals(errorDescriptionForUser, errorResult.errorDescriptionForUser) &&
        Objects.equals(exceptionId, errorResult.exceptionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, errorDescription, errorDescriptionForUser, exceptionId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResult {\n");
    
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    errorDescription: ").append(toIndentedString(errorDescription)).append("\n");
    sb.append("    errorDescriptionForUser: ").append(toIndentedString(errorDescriptionForUser)).append("\n");
    sb.append("    exceptionId: ").append(toIndentedString(exceptionId)).append("\n");
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

