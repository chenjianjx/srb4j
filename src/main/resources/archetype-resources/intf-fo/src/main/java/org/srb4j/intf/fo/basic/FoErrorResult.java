package org.srb4j.intf.fo.basic;

import static org.srb4j.intf.fo.basic.FoConstants.ERROR_DESC_FN;
import static org.srb4j.intf.fo.basic.FoConstants.ERROR_DESC_FOR_USER_FN;
import static org.srb4j.intf.fo.basic.FoConstants.ERROR_FN;
import static org.srb4j.intf.fo.basic.FoConstants.EXCEPTION_ID_FN;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * the error object for front office interfaces. Its error fields are compatible
 * with OAuth2 fields (e.g. "error" and "error_description")
 * 
 * @author chenjianjx@gmail.com
 *
 * 
 */
@ApiModel("ErrorResult")
public class FoErrorResult {

	@ApiModelProperty(name = ERROR_FN, required = false, value = "Error code. Compatible with OAuth2")
	@JsonProperty(ERROR_FN)
	private String errorCode;

	@ApiModelProperty(name = ERROR_DESC_FN, required = false, value = "Error message for client developers to read. Not for users. Compatible with OAuth2")
	@JsonProperty(ERROR_DESC_FN)
	private String devErrMsg;

	@ApiModelProperty(name = ERROR_DESC_FOR_USER_FN, required = false, value = "Error message that can be shown to users.")
	@JsonProperty(ERROR_DESC_FOR_USER_FN)
	private String userErrMsg;

	@ApiModelProperty(name = EXCEPTION_ID_FN, value = "exception Id. Please send this to the backend developer for troubleshooting", required = false)
	@JsonProperty(EXCEPTION_ID_FN)
	private String exceptionId;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String error) {
		this.errorCode = error;
	}

	public String getDevErrMsg() {
		return devErrMsg;
	}

	public void setDevErrMsg(String devMsg) {
		this.devErrMsg = devMsg;
	}

	public String getUserErrMsg() {
		return userErrMsg;
	}

	public void setUserErrMsg(String userMsg) {
		this.userErrMsg = userMsg;
	}

	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
