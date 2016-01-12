package ${groupId}.intf.fo.bbs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * create a post
 * 
 * @author chenjianjx@gmail.com
 *
 */
@ApiModel("UpdatePostRequest")
public class FoUpdatePostRequest extends FoSavePostRequest {

	@ApiModelProperty(value = "the postId.")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long postId) {
		this.id = postId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
