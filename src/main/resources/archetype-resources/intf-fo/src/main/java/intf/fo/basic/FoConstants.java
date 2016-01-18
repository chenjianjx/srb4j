package ${package}.intf.fo.basic;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public interface FoConstants {

	/* some verbiage only for for documentation purpose. */
	public static final String BIZ_ERR_TIP = "biz error";
	public static final String OAUTH2_TOKEN_ENDPOINT_ERR_TIP = "oauth2 token endpoint error";
	public static final String OK_TIP = "success";
	public static final String ACCESS_TOKEN_HEADER_KEY = "Authorization";
	public static final String ACCESS_TOKEN_HEADER_DEFAULT = "Bearer xxx";
	public static final String OAUTH2_TOKEN_ENDPOINT_TIP = "OAuth2 Token Endpoint.";
	public static final String OAUTH2_FLOW_TIP = "It conforms to standard OAuth 2.0 protocol with grant_type = password without validating client credentials.";
	public static final String OAUTH2_GRANT_TYPE_TIP = "OAuth2 grant type. It has to be 'password'";
	public static final String OAUTH2_ACCESS_TOKEN_NAME_TIP = "OAuth2 access token such as '"
			+ ACCESS_TOKEN_HEADER_DEFAULT + "'";
	public static final String OAUTH2_ALLOW_ANONYMOUS_TIP = OAUTH2_ACCESS_TOKEN_NAME_TIP
			+ ". Allow anonymous access";
	public static final String LONG_SESSION_TIP = "if true, the token will be available for a relatively long time";

	/* the names of error fields in response */
	public static final String ERROR_FN = "error";

	public static final String SUCC_FN = "successful";

	public static final String EXCEPTION_ID_FN = "exception_id";

	public static final String ERROR_DESC_FOR_USER_FN = "error_description_for_user";

	public static final String ERROR_DESC_FN = "error_description";

	/* Other fields */
	public static final String LONG_SESSION_PARAM = "long_session";

	/* biz error http code */
	public static final int FO_SC_BIZ_ERROR = 460;

	/* the oauth error codes */
	public static final String FEC_OAUTH2_INVALID_REQUEST = "invalid_request";

	public static final String FEC_OAUTH2_INVALID_GRANT = "invalid_grant";

	/* the biz error codes */
	public static final String FEC_NO_RECORD = "NO_RECORD";

	public static final String FEC_INVALID_INPUT = "INVALID_INPUT";

	public static final String FEC_RECORD_ALREADY_EXISTS = "RECORD_ALREADY_EXISTS";

	/**
	 * won't be visible to restful users because an oauth2 response will be sent
	 * instead, using oauth2 error codes
	 */
	public static final String FEC_NOT_LOGIN_YET = "NOT_LOGIN_YET";

	public static final String FEC_NO_PERMISSION = "NO_PERMISSION";

	public static final String FEC_REQUIRE_PASSWORD_RESET = "REQUIRE_PASSWORD_RESET";

	public static final String FEC_UNKNOWN_SERVER_ERROR = "UNKNOWN_SERVER_ERROR";

	/* Other Auth Constants */
	public static final int NORMAL_ACCESS_TOKEN_LIFESPAN = 3600;
	public static final int LONG_SESSION_ACCESS_TOKEN_LIFESPAN = 3600 * 24 * 14;
	public static final int RANDOM_LOGIN_CODE_LIFESPAN = 3600 / 2;

	/* some validation constants */
	public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-zA-Z]).{6,20})";
	public static final String PASSWORD_ERR_TIP = "Password should contain at least 1 digit and 1 English letter, 6-20 characters long and should only digits, English letters and punctuations.";
	public static final String NULL_REQUEST_BEAN_TIP = "Please input the required fields.";

}
