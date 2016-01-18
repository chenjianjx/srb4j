package ${groupId}.${rootArtifactId}.intf.fo.auth;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class FoSocialLoginRequest {

	@NotNull(message = "social source not specified")
	private String source;

	@NotNull(message = "social site token cannot be empty")
	private String token;

	private Boolean longSession;
 
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getLongSession() {
		return longSession;
	}

	@JsonIgnore
	public boolean isLongSession() {
		return longSession != null && longSession;
	}

	public void setLongSession(Boolean longSession) {
		this.longSession = longSession;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
