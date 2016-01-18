package ${groupId}.${rootArtifactId}.impl.util.spring;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * use this class to locate property files under ${user.home} or its sub
 * directories. On spring context xml, please set "userHomeFiles" instead of
 * "locations"
 * 
 * 
 * @author chenjianjx@gmail.com
 *
 */

public class UserHomeFilePropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {

	private File userHome = new File(System.getProperty("user.home"));

	public void setUserHomeFiles(String[] userHomeFiles) {

		if (userHomeFiles == null || userHomeFiles.length == 0) {
			return;
		}

		Resource[] resources = new Resource[userHomeFiles.length];

		for (int i = 0; i < userHomeFiles.length; i++) {

			String usf = StringUtils.trimToNull(userHomeFiles[i]);
			if (usf == null) {
				continue;
			}

			if (!usf.startsWith("/")) {
				usf = "/" + usf;
			}

			File file = new File(userHome, usf);

			if (!file.exists()) {
				throw new IllegalStateException(
						"The spring property file doesn't exist! File is "
								+ file.getAbsolutePath());
			}

			resources[i] = new FileSystemResource(file);
		}

		this.setLocations(resources);
	}
}
