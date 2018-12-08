package com.github.chenjianjx.srb4jfullsample.intf.fo.basic;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public interface FoConstants {

	/* some verbiage only for for documentation purpose. */
	String BIZ_ERR_TIP = "biz error";
	String OAUTH2_TOKEN_ENDPOINT_ERR_TIP = "oauth2 token endpoint error";
	String OK_TIP = "success";
	String ACCESS_TOKEN_HEADER_KEY = "Authorization";
	String ACCESS_TOKEN_HEADER_DEFAULT = "Bearer xxx";
	String OAUTH2_TOKEN_ENDPOINT_TIP = "OAuth2 Token Endpoint.";
	String OAUTH2_FLOW_TIP = "It conforms to standard OAuth 2.0 protocol with grant_type = password without validating client credentials.";
	String OAUTH2_GRANT_TYPE_TIP = "OAuth2 grant type. It has to be 'password'";
	String OAUTH2_ACCESS_TOKEN_NAME_TIP = "OAuth2 access token such as '"
			+ ACCESS_TOKEN_HEADER_DEFAULT + "'";
	String OAUTH2_ALLOW_ANONYMOUS_TIP = OAUTH2_ACCESS_TOKEN_NAME_TIP
			+ ". Allow anonymous access";
	String LONG_SESSION_TIP = "if true, the token will be available for a relatively long time";

	/* the names of error fields in response */
	String ERROR_FN = "error";

	String EXCEPTION_ID_FN = "exception_id";

	String ERROR_DESC_FOR_USER_FN = "error_description_for_user";

	String NON_FIELD_ERROR_FOR_USER_FN ="non_field_error_for_user";

	String FIELD_ERRORS_FOR_USER_FN = "field_errors_for_user";

	String ERROR_DESC_FN = "error_description";

	/* Other fields */
	String LONG_SESSION_PARAM = "long_session";	
	String SOCIAL_SITE_SOURCE_PARAM = "source";
	String CLIENT_TYPE_PARAM = "clientType";
	String REDIRECT_URI_PARAM = "redirectUri";
	
	/*social login redirect uris*/
	String GOOGLE_REDIRECT_URI_POSTMESSAGE = "postmessage";
	String GOOGLE_REDIRECT_URI_OOB = "urn:ietf:wg:oauth:2.0:oob";
	String FACEBOOK_REDIRECT_URI_LOGIN_SUCCESS = "https://www.facebook.com/connect/login_success.html";
	

	/* biz error http code */
	int FO_SC_BIZ_ERROR = 460;

	/* the oauth error codes */
	String FEC_OAUTH2_INVALID_REQUEST = "invalid_request";

	String FEC_OAUTH2_INVALID_GRANT = "invalid_grant";

	/* the biz error codes */
	String FEC_NO_RECORD = "NO_RECORD";

	String FEC_INVALID_INPUT = "INVALID_INPUT";

	String FEC_ILLEGAL_STATUS = "ILLEGAL_STATUS";

	String FEC_RECORD_ALREADY_EXISTS = "RECORD_ALREADY_EXISTS";
	
	String FEC_ERR_BUT_CAN_RETRY = "ERR_BUT_CAN_RETRY";

	/**
	 * won't be visible to restful users because an oauth2 response will be sent
	 * instead, using oauth2 error codes
	 */
	String FEC_NOT_LOGIN_YET = "NOT_LOGIN_YET";

	String FEC_NO_PERMISSION = "NO_PERMISSION";

	String FEC_REQUIRE_PASSWORD_RESET = "REQUIRE_PASSWORD_RESET";

	String FEC_UNKNOWN_SERVER_ERROR = "UNKNOWN_SERVER_ERROR";

	/* Other Auth Constants */
	int NORMAL_ACCESS_TOKEN_LIFESPAN = 3600;
	int LONG_SESSION_ACCESS_TOKEN_LIFESPAN = 3600 * 24 * 14;
	int RANDOM_LOGIN_CODE_LIFESPAN = 3600 / 2;


	/* some validation constants */
	String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-zA-Z]).{6,20})";	
	String PASSWORD_ERR_TIP = "Password should contain at least 1 digit and 1 English letter, and be 6-20 characters long.";
	String NEW_PASSWORD_ERR_TIP = "New password should contain at least 1 digit and 1 English letter, and be 6-20 characters long.";
	String NULL_REQUEST_BEAN_TIP = "Please input the required fields.";
	String SOCIAL_LOGIN_CLIENT_TYPE_TIP = "The client type, including 'desktop', 'web' and 'mobile'.";
	String SOCIAL_LOGIN_SOURCE_TIP = "Currently it supports: 'google' and 'facebook' .";

	/*email verification related*/
	String PATH_EMAIL_VERIFICATION_PROCESS_VERIFY = "/email-verification-process/verify"; //please prepend "/user" for the whole path
	String EMAIL_VERIFICATION_DIGEST_PARAM_NAME = "d";
	int EMAIL_VERIFICATION_DIGEST_LIFESPAN = 3600 * 24 * 14;


	/**forget password related*/
	int FORGET_PASSWORD_VERIFY_CODE_LIFESPAN = 60 * 10;
}
