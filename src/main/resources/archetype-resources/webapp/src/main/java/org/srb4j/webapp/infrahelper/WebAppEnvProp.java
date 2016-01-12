package ${groupId}.webapp.infrahelper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * the webapp's env-specific properties
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Component
public class WebAppEnvProp {

	private String schemeAndHost;

	private boolean enableSwagger;

	/**
	 * will end with "/"
	 * @param schemeAndHost
	 */
	@Value("${schemeAndHost}")
	public void setSchemeAndHost(String schemeAndHost) {
		schemeAndHost = StringUtils.trimToNull(schemeAndHost);
		if (schemeAndHost == null) {
			throw new IllegalStateException(
					"Pleaset set schemeAndHost property");
		}
		if (!schemeAndHost.endsWith("/")) {
			schemeAndHost = schemeAndHost + "/";
		}

		this.schemeAndHost = schemeAndHost;
	}

	@Value("${enableSwagger}")
	public void setEnableSwagger(String enableSwaggerStr) {
		enableSwaggerStr = StringUtils.trimToNull(enableSwaggerStr);
		if (enableSwaggerStr == null) {
			throw new IllegalStateException(
					"Pleaset set enableSwagger property");
		}

		enableSwagger = Boolean.valueOf(enableSwaggerStr);
	}

	public String getSchemeAndHost() {
		return schemeAndHost;
	}

	public boolean isEnableSwagger() {
		return enableSwagger;
	}

}
