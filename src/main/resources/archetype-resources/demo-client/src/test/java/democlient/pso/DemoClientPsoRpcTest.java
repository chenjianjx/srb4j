package ${package}.democlient.pso;

import java.net.MalformedURLException;
import java.util.List;

import org.junit.Test;
import ${package}.democlient.util.DemoClientConstants;
import ${package}.pso.celebritysystem.bbs.CsBbsRpc;
import ${package}.pso.celebritysystem.bbs.CsPost;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
public class DemoClientPsoRpcTest {
#if( $includePso == "true" ||  $includePso == "y" ||  $includePso == "yes")
	@Test
	public void invokePso() throws MalformedURLException {
		HessianProxyFactory factory = new HessianProxyFactory();
		CsBbsRpc rpc = (CsBbsRpc) factory.create(CsBbsRpc.class,
				DemoClientConstants.PSO_TEST_URL);
		List<CsPost> result = rpc.getPostsByCelebrity("tom");
		System.out.println("Posts: " + result);
	}
#end
}
