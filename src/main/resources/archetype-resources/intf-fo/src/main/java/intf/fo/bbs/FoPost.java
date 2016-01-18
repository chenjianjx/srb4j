package ${groupId}.${rootArtifactId}.intf.fo.bbs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import ${groupId}.${rootArtifactId}.intf.fo.basic.FoEntityBase;

/**
 * an exemplary fo entity
 * 
 * @author chenjianjx@gmail.com
 *
 */
@ApiModel(value = "Post", description = "Exemplary Only")
public class FoPost extends FoEntityBase {

	@ApiModelProperty(value = "the author's userId", required = true)
	protected Long userId;

	@ApiModelProperty(value = "the content of this post", required = true)
	protected String content;

	@ApiModelProperty(value = "can this post be deleted by current user?", required = false)
	protected Boolean canDelete = Boolean.FALSE;

	@ApiModelProperty(value = "can this post be updated by current user?", required = false)
	protected Boolean canUpdate = Boolean.FALSE;

	public Boolean getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}

	public Boolean getCanUpdate() {
		return canUpdate;
	}

	public void setCanUpdate(Boolean canUpdate) {
		this.canUpdate = canUpdate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
