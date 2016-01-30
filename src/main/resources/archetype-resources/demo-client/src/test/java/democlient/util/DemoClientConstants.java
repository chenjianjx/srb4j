package ${package}.democlient.util;


/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public interface DemoClientConstants {

	public static final String BACKEND_URL = "http://localhost:8080";

	public static final String BACKEND_FO_REST_URL = BACKEND_URL + "/fo/rest";
	
	
	public static final String SOCIAL_LOGIN_BY_TOKEN_URL_PREFIX = "/token/new/social/by-token/";
	public static final String PROCTED_RESOURCE_URL = "/token/test/protected-resource";

	public static final String LOCAL_LOGIN_URL = "/token/new/local";
	public static final String LOCAL_REG_URL = "/token/new/by-register/local";
 

	public static final String LOGOUT_URL = "/token/delete";
	public static final String GEN_RANDOM_CODE_URL = "/token/random-code/new/local";

	public static final String RANDOM_CODE_LOGIN_URL = "/token/new/by-random-code/local";
	public static final String REFRESH_TOKEN_URL = "/token/refresh";
	
	public static final String BACKEND_BO_PORTAL_URL = BACKEND_URL + "/bo/portal";
	public static final String BO_LOGIN_PATH = "/login";
}
