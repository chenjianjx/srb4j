package ${groupId}.intf.fo.basic;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * the generic response object for front office interfaces. Its error fields are
 * compatible with OAuth2 fields (e.g. "error" and "error_description")
 * 
 * @author chenjianjx@gmail.com
 *
 * 
 */
public class FoResponse<T> {
	private FoErrorResult err;

	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccessful() {
		return err == null;
	}

	public FoErrorResult getErr() {
		return err;
	}

	public void setErr(FoErrorResult err) {
		this.err = err;
	}

	public static <T> FoResponse<T> success(T data) {
		FoResponse<T> r = new FoResponse<T>();
		r.setData(data);
		return r;
	}

	public static <T> FoResponse<T> devErrResponse(String errorCode,
			String devErrMsg, String exceptionId) {
		FoResponse<T> response = new FoResponse<T>();
		FoErrorResult err = new FoErrorResult();
		err.setErrorCode(errorCode);
		err.setDevErrMsg(devErrMsg);
		err.setExceptionId(exceptionId);
		response.setErr(err);
		return response;
	}

	public static <T> FoResponse<T> userErrResponse(String errorCode,
			String userErrMsg) {
		FoResponse<T> response = new FoResponse<T>();
		FoErrorResult err = new FoErrorResult();
		err.setErrorCode(errorCode);
		err.setUserErrMsg(userErrMsg);
		response.setErr(err);
		return response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
