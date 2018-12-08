package com.github.chenjianjx.srb4jfullsample.impl.biz.common;

import com.google.common.base.Function;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Calendar;

/**
 * Every entity bean will have these properties
 *
 * @author chenjianjx@gmail.com
 */
public abstract class EntityBase {

    protected long id;

    private String createdBy;
    private String updatedBy;

    private Calendar createdAt;
    private Calendar updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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

    public static class GetIdFunction<T extends EntityBase> implements
            Function<T, Long> {

        @Override
        public Long apply(T t) {
            return t.getId();
        }

    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
