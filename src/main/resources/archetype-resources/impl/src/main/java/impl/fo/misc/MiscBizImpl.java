package ${package}.impl.fo.misc;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import ${package}.impl.biz.auth.AccessToken;
import ${package}.impl.biz.auth.AccessTokenRepo;
import ${package}.impl.util.tools.lang.MyLangUtils;
import ${package}.intf.fo.auth.FoAccessToken;
import ${package}.intf.fo.misc.MiscBiz;

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
