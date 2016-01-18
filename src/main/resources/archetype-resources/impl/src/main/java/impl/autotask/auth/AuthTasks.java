package ${package}.impl.autotask.auth;

import static org.apache.commons.lang.time.DateFormatUtils.format;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ${package}.impl.biz.auth.AccessTokenRepo;
import ${package}.impl.common.ImplConstants;
import ${package}.impl.util.tools.lang.MyLangUtils;

/**
 * auto tasks for auth module
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Component
public class AuthTasks {

	private static final Logger autoTaskLogger = LoggerFactory
			.getLogger(ImplConstants.AUTO_TASK_LOGGER);

	@Resource
	AccessTokenRepo accessTokenRepo;

	public AuthTasks() {

	}

	@PostConstruct
	public void init() {

		startStaleTokenScavenger();
	}

	private class StaleTokenScavengerTask extends TimerTask {

		//You have these hours to refres an expired token
		private static final int HOURS = 8;

		@Override
		public void run() {
			long someTimeAgo = System.currentTimeMillis() - 3600 * 1000 * HOURS;
			String dataStr = toDateStr(someTimeAgo);
			autoTaskLogger
					.info("Going to revoke stale acess tokens expired before "
							+ dataStr);
			int numOfRows = accessTokenRepo
					.deleteTokensExpiresBefore(new java.sql.Timestamp(
							someTimeAgo));
			autoTaskLogger.info(numOfRows + " access tokens revoked");
		}

		private String toDateStr(long oneHourAgo) {
			return format(MyLangUtils.newCalendar(oneHourAgo),
					"yyyy-MM-dd HH:mm:ss");
		}

	}

	private void startStaleTokenScavenger() {
		new Timer().schedule(new StaleTokenScavengerTask(), 60l * 1000,
				3600l * 1000);
	}

}
