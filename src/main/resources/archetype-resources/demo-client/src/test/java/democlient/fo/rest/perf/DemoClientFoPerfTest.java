package ${groupId}.democlient.fo.rest.perf;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.freebencher.FbJobResult;
import org.freebencher.FbTarget;
import org.freebencher.Freebencher;
import org.junit.Test;
import ${groupId}.democlient.fo.rest.auth.DemoClientFoAuthTest;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class DemoClientFoPerfTest {

	@Test
	public void testLogin() {

		System.out.println("Preparing data...");
		final List<String> usernameList = new ArrayList<String>();
		final int numOfUserNames = 100;
		final String commonPassword = "abc123";
		for (int i = 0; i < numOfUserNames; i++) {
			String username = RandomStringUtils.randomAlphanumeric(10)
					+ "@nonexist.com";
			DemoClientFoAuthTest.doRegister(username, commonPassword);
			usernameList.add(username);
		}
		System.out.println("Data prepartion done.");

		FbJobResult result = Freebencher.benchmark(new FbTarget() {
			@Override
			public boolean invoke() {
				String username = usernameList.get(RandomUtils
						.nextInt(numOfUserNames));
				//Note: sharing a single rest client
				DemoClientFoAuthTest.doLogin(username, commonPassword);
				return true;
			}
		}, 5, // concurrency,
				50 // number of tests to run
				);

		System.out.println(result.report());
	}

	@Test
	public void testRegister() {
		FbJobResult result = Freebencher.benchmark(new FbTarget() {
			@Override
			public boolean invoke() {
				//Note: sharing a single rest client 
				DemoClientFoAuthTest.doRegister();
				return true;
			}
		}, 5, 250);
		System.out.println(result.report());
	}

}
