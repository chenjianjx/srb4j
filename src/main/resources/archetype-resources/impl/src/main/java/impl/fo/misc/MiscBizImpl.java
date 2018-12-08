package com.github.chenjianjx.srb4jfullsample.impl.fo.misc;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.github.chenjianjx.srb4jfullsample.impl.biz.auth.AccessToken;
import com.github.chenjianjx.srb4jfullsample.impl.biz.auth.AccessTokenRepo;
import com.github.chenjianjx.srb4jfullsample.utils.lang.MyLangUtils;
import com.github.chenjianjx.srb4jfullsample.intf.fo.auth.FoAccessToken;
import com.github.chenjianjx.srb4jfullsample.intf.fo.misc.MiscBiz;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Service("miscBiz")
public class MiscBizImpl implements MiscBiz {

	@Resource
	AccessTokenRepo accessTokenRepo;

	@Override
	public FoAccessToken getAccessTokenByTokenStr(String tokenStr) {
		if (tokenStr == null) {
			return null;
		}
		AccessToken at = accessTokenRepo.getByTokenStr(tokenStr);
		if(at == null){
			return null;
		}
		FoAccessToken fat = new FoAccessToken();
		MyLangUtils.copyProperties(fat, at);
		return fat;
	}

}
