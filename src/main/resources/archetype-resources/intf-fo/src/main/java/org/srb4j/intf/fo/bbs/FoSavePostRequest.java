package org.srb4j.intf.fo.bbs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * create or update a post
 * 
 * @author chenjianjx@gmail.com
 *
 */
@ApiModel("SavePostRequest")
public abstract class FoSavePostRequest {

	@NotNull(message = "Content cannot be null")
	@Size(min = 3, max = 140, message = "Content must be {min}-{max} characters")
	@ApiModelProperty(value = "the post's content", required = true)
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
