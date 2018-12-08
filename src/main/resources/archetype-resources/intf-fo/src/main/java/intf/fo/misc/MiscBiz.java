package com.github.chenjianjx.srb4jfullsample.intf.fo.misc;

import com.github.chenjianjx.srb4jfullsample.intf.fo.auth.FoAccessToken;

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
