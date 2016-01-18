package ${groupId}.${rootArtifactId}.impl.fo.misc;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import ${groupId}.${rootArtifactId}.impl.biz.auth.AccessToken;
import ${groupId}.${rootArtifactId}.impl.biz.auth.AccessTokenRepo;
import ${groupId}.${rootArtifactId}.impl.util.tools.lang.MyLangUtils;
import ${groupId}.${rootArtifactId}.intf.fo.auth.FoAccessToken;
import ${groupId}.${rootArtifactId}.intf.fo.misc.MiscBiz;

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
