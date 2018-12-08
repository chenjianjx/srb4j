package com.github.chenjianjx.srb4jfullsample.intf.fo.basic;

import io.swagger.annotations.ApiModelProperty;

import java.util.Calendar;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A mirror object of a biz entity, used on application layer or above. It is
 * suggested that the spelling of the properties names are exactly the same with
 * their biz-layer counterparts, so that property copy based on reflection can
 * be done.
 * 
 * @author chenjianjx@gmail.com
 *
 */
public abstract class FoEntityBase {

	@ApiModelProperty(value = "the record's id in backend's db", required = true)
	protected Long id;

	@ApiModelProperty(value = "create time. The system miliseconds since Jan.1 1970 GMT", required = true)
	protected Calendar createdAt;

	@ApiModelProperty(value = "last update time. The system miliseconds since Jan.1 1970 GMT", required = true)
	protected Calendar updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	public Calendar getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
