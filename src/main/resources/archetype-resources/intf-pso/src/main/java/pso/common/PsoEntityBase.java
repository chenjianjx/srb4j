package ${package}.pso.common;

import java.util.Calendar;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


#if( $includePso == "true" ||  $includePso == "y" ||  $includePso == "yes")
/**
 * A mirror object of a biz entity, used in partner system oriented services. It is
 * suggested that the spelling of the properties names are exactly the same with
 * their biz-layer counterparts, so that property copy based on reflection can
 * be done.
 * 
 * @author chenjianjx@gmail.com
 *
 */
	
#end	
public abstract class PsoEntityBase {

#if( $includePso == "true" ||  $includePso == "y" ||  $includePso == "yes")	
	 
	protected Long id;

	 
	protected Calendar createdAt;

	 
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
	
#end	
}
