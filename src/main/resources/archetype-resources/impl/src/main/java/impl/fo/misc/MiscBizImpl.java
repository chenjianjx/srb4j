package ${groupId}.impl.fo.misc;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import ${groupId}.impl.biz.auth.AccessToken;
import ${groupId}.impl.biz.auth.AccessTokenRepo;
import ${groupId}.impl.util.tools.lang.MyLangUtils;
import ${groupId}.intf.fo.auth.FoAccessToken;
import ${groupId}.intf.fo.misc.MiscBiz;

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
