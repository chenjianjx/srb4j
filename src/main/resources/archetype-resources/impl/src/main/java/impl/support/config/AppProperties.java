package com.github.chenjianjx.srb4jfullsample.impl.support.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * some of the env-specific values defined in app.properties
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Component
public class AppProperties {

	private String orgSupportDesk;
	private String orgSupportEmail;

	public String getOrgSupportDesk() {
		return orgSupportDesk;
	}

	public String getOrgSupportEmail() {
		return orgSupportEmail;
	}

	@Value("${orgSupportDesk}")
	public void setOrgSupportDesk(String orgSupportDesk) {
		this.orgSupportDesk = trimToNull(orgSupportDesk, "Unknown Support Desk");
	}

	@Value("${orgSupportEmail}")
	public void setOrgSupportEmail(String orgSupportEmail) {
		this.orgSupportEmail = trimToNull(orgSupportEmail,
				"unkown@support.email");
	}

	private String trimToNull(String value, String defalutValue) {
		value = StringUtils.trimToNull(value);
		if (value == null) {
			value = defalutValue;
		}
		return value;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
