package org.srb4j.intf.fo.misc;

import org.srb4j.intf.fo.auth.FoAccessToken;

/**
 * non user-oriented interfaces. Sometimes you need these
 * 
 * @author chenjianjx@gmail.com
 *
 */
public interface MiscBiz {

	/**
	 * 
	 * 
	 * @param tokenStr
	 * @return
	 */
	public FoAccessToken getAccessTokenByTokenStr(String tokenStr);

}
