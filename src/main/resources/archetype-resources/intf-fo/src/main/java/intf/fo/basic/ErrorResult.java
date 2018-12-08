package com.github.chenjianjx.srb4jfullsample.intf.fo.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.ERROR_DESC_FN;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.ERROR_DESC_FOR_USER_FN;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.ERROR_FN;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.EXCEPTION_ID_FN;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.FIELD_ERRORS_FOR_USER_FN;
import static com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoConstants.NON_FIELD_ERROR_FOR_USER_FN;

/**
 * the error object for front office interfaces. Its error fields are compatible
 * with OAuth2 fields (e.g. "error" and "error_description")
 *
 * @author chenjianjx@gmail.com
 */
@ApiModel("ErrorResult")
public class ErrorResult {

    @ApiModelProperty(name = ERROR_FN, required = false, value = "Error code. Compatible with OAuth2")
    @JsonProperty(ERROR_FN)
    private String errorCode;

    @ApiModelProperty(name = ERROR_DESC_FN, required = false, value = "Error message for client developers to read. Not for users. Compatible with OAuth2")
    @JsonProperty(ERROR_DESC_FN)
    private String devErrMsg;


    @ApiModelProperty(name = EXCEPTION_ID_FN, value = "exception Id. Please send this to the backend developer for troubleshooting", required = false)
    @JsonProperty(EXCEPTION_ID_FN)
    private String exceptionId;

    @ApiModelProperty(name = NON_FIELD_ERROR_FOR_USER_FN, required = false, value = "Imagine there is a form, this is the error shown on top of the form, unrelated to any specific field ")
    @JsonProperty(NON_FIELD_ERROR_FOR_USER_FN)
    private String nonFieldUserError;

    @ApiModelProperty(name = FIELD_ERRORS_FOR_USER_FN, required = false, value = "Imagine there is a form, this is the error shown beside the input fields ")
    @JsonProperty(FIELD_ERRORS_FOR_USER_FN)
    private Map<String, String> fieldUserErrors;

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

    @ApiModelProperty(name = ERROR_DESC_FOR_USER_FN, required = false, value = "Error message that can be shown to users. It's derived from " + NON_FIELD_ERROR_FOR_USER_FN + " and " + FIELD_ERRORS_FOR_USER_FN)
    @JsonProperty(ERROR_DESC_FOR_USER_FN)
    public String getUserErrMsg() {
        if (nonFieldUserError != null) {
            return nonFieldUserError;
        }

        if (fieldUserErrors != null && fieldUserErrors.size() > 0) {
            return fieldUserErrors.values().stream().findFirst().get();
        }

        return null;
    }


    public String getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(String exceptionId) {
        this.exceptionId = exceptionId;
    }


    public String getNonFieldUserError() {
        return nonFieldUserError;
    }

    public void setNonFieldUserError(String nonFieldUserError) {
        this.nonFieldUserError = nonFieldUserError;
    }

    public Map<String, String> getFieldUserErrors() {
        return fieldUserErrors;
    }

    public void setFieldUserErrors(Map<String, String> fieldUserErrors) {
        this.fieldUserErrors = fieldUserErrors;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
