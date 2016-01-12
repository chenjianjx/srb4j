package org.srb4j.impl.fo.misc;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.srb4j.impl.biz.auth.AccessToken;
import org.srb4j.impl.biz.auth.AccessTokenRepo;
import org.srb4j.impl.util.tools.lang.MyLangUtils;
import org.srb4j.intf.fo.auth.FoAccessToken;
import org.srb4j.intf.fo.misc.MiscBiz;

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
