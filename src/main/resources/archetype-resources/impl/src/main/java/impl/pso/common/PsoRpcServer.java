package ${package}.impl.pso.common;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import ${package}.impl.pso.celebritysystem.bbs.CsBbsRpcServlet;

#if( $includePso == "true" ||  $includePso == "y" ||  $includePso == "yes")
/**
 * The class to initialize an embedded server to host rpc endpoints
 * 
 * @author chenjianjx@gmail.com
 *
 */	
@Component
#end
public class PsoRpcServer implements ApplicationContextAware {

#if( $includePso == "true" ||  $includePso == "y" ||  $includePso == "yes")
	
	public static ApplicationContext contextDefinedInWebappProject;

	@Resource
	PsoConfig psoConfig;

	@PostConstruct
	public void init() throws Exception {
		if (psoConfig.getHost() == null || psoConfig.getPort() <= 0) {
			throw new IllegalArgumentException(
					"You have included the partner system oriented rpc module, but you didn't set up the hostname and port well. "
							+ "Please set up 'psoRpcServerHost' and 'psoRpcServerHostPort' in the properties file. "
							+ "VERY IMPORTANT: The host and port should only be accessble from intranet");
		}
		InetSocketAddress address = new InetSocketAddress(psoConfig.getHost(),
				psoConfig.getPort());
		Server server = new Server(address);

		ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");
		context.addServlet(CsBbsRpcServlet.class, "/pso/cs/bbs");
		server.setHandler(context);
		server.start();
	}
#end		

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
	
#if( $includePso == "true" ||  $includePso == "y" ||  $includePso == "yes")	
		contextDefinedInWebappProject = context;
#end
	}


}
