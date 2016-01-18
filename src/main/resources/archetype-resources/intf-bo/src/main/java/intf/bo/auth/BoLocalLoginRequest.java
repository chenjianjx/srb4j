package ${groupId}.${rootArtifactId}.intf.bo.auth;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * 
 * @author chenjianjx@gmail.com
 *
 */

public class BoLocalLoginRequest {

	@NotNull(message = "Please input email.")
	private String email;

	@NotNull(message = "Please input password.")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
