package com.github.chenjianjx.srb4jfullsample.intf.bo.basic;

import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.ErrorResult;
import com.github.chenjianjx.srb4jfullsample.intf.fo.basic.FoResponse;

import java.util.Map;

/**
 * the generic response object for back office interfaces.
 * Since {@link FoResponse}'s data structure can also work on BO, then why not just extend it?
 *
 * @author chenjianjx@gmail.com
 */
public class BoResponse<T> extends FoResponse<T> {

    public static <T> BoResponse<T> success(T data) {
        BoResponse<T> r = new BoResponse<T>();
        r.setData(data);
        return r;
    }

    public static <T> BoResponse<T> userErrResponse(String errorCode, String nonFieldUserError, Map<String, String> fieldUserErrors) {
        BoResponse<T> response = new BoResponse<T>();
        ErrorResult err = new ErrorResult();
        err.setErrorCode(errorCode);
        err.setNonFieldUserError(nonFieldUserError);
        err.setFieldUserErrors(fieldUserErrors);
        response.setErr(err);
        return response;
    }

    /**
     * Give data in spite of error. This is normally for a recoverable error.
     */
    public static <T> BoResponse<T> errResponseWithData(String errorCode,
                                                        T data) {
        BoResponse<T> response = new BoResponse<T>();
        response.setData(data);
        ErrorResult err = new ErrorResult();
        err.setErrorCode(errorCode);
        response.setErr(err);
        return response;
    }
}
