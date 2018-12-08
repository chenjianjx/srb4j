package com.github.chenjianjx.srb4jfullsample.webapp.bo.portal.support;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public final class BoPortalAlert {

    public enum BoAlertType {
        success, danger, warning, info
    }


    private BoAlertType type;
    private String html;

    public BoPortalAlert() {
    }

    public BoPortalAlert(BoAlertType type, String html) {
        this.type = type;
        this.html = html;
    }

    public BoAlertType getType() {
        return type;
    }

    public void setType(BoAlertType type) {
        this.type = type;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
