package ${groupId}.${rootArtifactId}.intf.fo.bbs;

import io.swagger.annotations.ApiModel;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * create a post
 * 
 * @author chenjianjx@gmail.com
 *
 */
@ApiModel("NewPostRequest")
public class FoNewPostRequest extends FoSavePostRequest {


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
