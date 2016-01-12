package org.srb4j.democlient.fo.rest.biz;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.srb4j.democlient.util.DemoClientUtils;
import org.srb4j.restclient.model.ErrorResult;

/**
 * The response object of visiting biz services ( OAuth2 resource point)
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class DemoClientFoBizResponse<T> {

	private static final Pattern OAUTH_HEADER = Pattern
			.compile("\\s*(\\w*)\\s+(.*)");

	private static final Pattern EQUATION = Pattern
			.compile("(\\S*)\\s*\\=\\s*\"([^\"]*)\"");

	public int httpCode;

	public T data;
	public ErrorResult bizError;
	public ErrorResult oauth2Error;

	private static final List<Integer> OAUTH2_HTTP_ERROR_CODES = Arrays.asList(
			400, 401, 403);

	private static final int BIZ_ERROR_CODE = 460;

	public boolean isSuccessful() {
		return httpCode == 200;
	}

	public boolean isOauth2Error() {
		return oauth2Error != null;
	}

	public boolean isBizError() {
		return bizError != null;
	}

	public boolean isOauth2InvalidRequest() {
		return isOauth2Error()
				&& "invalid_request".equals(oauth2Error.getError());
	}

	public boolean isOauth2InvalidToken() {
		return isOauth2Error()
				&& "invalid_token".equals(oauth2Error.getError());
	}

	public boolean isOauth2ExpiredToken() {
		return isOauth2Error()
				&& "expired_token".equals(oauth2Error.getError());
	}

	public static <T> DemoClientFoBizResponse<T> parseRestResponse(
			Response restResponse, Class<T> dataClass) {
		return doParseResponse(restResponse, dataClass);
	}

	public static <T> DemoClientFoBizResponse<T> parseRestResponse(
			Response restResponse, GenericType<T> dataType) {
		return doParseResponse(restResponse, dataType);
	}

	@SuppressWarnings("unchecked")
	private static <T> DemoClientFoBizResponse<T> doParseResponse(
			Response restResponse, Object typeOrClass) {
		DemoClientFoBizResponse<T> result = new DemoClientFoBizResponse<T>();
		result.httpCode = restResponse.getStatus();

		if (restResponse.getStatus() == 200) {
			result.data = (T) parseData(restResponse, typeOrClass);
			return result;
		}

		if (OAUTH2_HTTP_ERROR_CODES.contains(restResponse.getStatus())) {
			String wwwAuthHeader = restResponse
					.getHeaderString("www-authenticate");
			Map<String, String> headerValues = decodeOAuthHeader(wwwAuthHeader);
			result.oauth2Error = new ErrorResult();
			result.oauth2Error.setError(headerValues.get("error"));
			result.oauth2Error.setErrorDescription(headerValues
					.get("error_description"));
			result.oauth2Error.setErrorDescriptionForUser(headerValues
					.get("error_description_for_user"));
			result.oauth2Error.setExceptionId(headerValues.get("exception_id"));
			return result;

		}

		if (BIZ_ERROR_CODE == restResponse.getStatus()) {
			result.bizError = restResponse.readEntity(ErrorResult.class);
			return result;
		}

		throw new RuntimeException(
				"Failed parsing the restful response. Status code = "
						+ restResponse.getStatus());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object parseData(Response restResponse, Object typeOrClass) {
		if (typeOrClass instanceof Class) {
			return restResponse.readEntity((Class) typeOrClass);
		}

		if (typeOrClass instanceof GenericType) {
			return restResponse.readEntity((GenericType) typeOrClass);
		}

		throw new IllegalArgumentException("unsupported parameter: "
				+ typeOrClass);

	}

	private static Map<String, String> decodeOAuthHeader(String header) {
		Map<String, String> headerValues = new HashMap<String, String>();
		if (header != null) {
			Matcher m = OAUTH_HEADER.matcher(header);
			if (m.matches()) {
				if ("Bearer".equalsIgnoreCase(m.group(1))) {
					for (String nvp : m.group(2).split("\\s*,\\s*")) {
						m = EQUATION.matcher(nvp);
						if (m.matches()) {
							String name = DemoClientUtils.urlDecode(m.group(1));
							String value = DemoClientUtils
									.urlDecode(m.group(2));
							headerValues.put(name, value);
						}
					}
				}
			}
		}
		return headerValues;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
