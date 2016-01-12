package ${groupId}.democlient.pso;

import java.net.MalformedURLException;
import java.util.List;

import org.junit.Test;
import ${groupId}.democlient.util.DemoClientConstants;
import ${groupId}.pso.celebritysystem.bbs.CsBbsRpc;
import ${groupId}.pso.celebritysystem.bbs.CsPost;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class DemoClientPsoRpcTest {

	@Test
	public void invokePso() throws MalformedURLException {
		HessianProxyFactory factory = new HessianProxyFactory();
		CsBbsRpc rpc = (CsBbsRpc) factory.create(CsBbsRpc.class,
				DemoClientConstants.PSO_TEST_URL);
		List<CsPost> result = rpc.getPostsByCelebrity("tom");
		System.out.println("Posts: " + result);
	}

}
