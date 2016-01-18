package ${package}.impl.pso.common;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class PsoConfig {

#if( $includePso == "true" ||  $includePso == "y" ||  $includePso == "yes")	

	private String host;

	private int port;

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public void setHost(String host) {
		this.host = StringUtils.trimToNull(host);
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
#end	
}
