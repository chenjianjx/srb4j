package ${groupId}.impl.pso.common;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.caucho.hessian.server.HessianServlet;

/**
 * All hessian endpoints should extends this servlet
 * 
 * @author chenjianjx@gmail.com
 *
 */
public abstract class PsoAbstractHessianServlet extends HessianServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4859005050754292111L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		AutowireCapableBeanFactory beanFactory = PsoRpcServer.contextDefinedInWebappProject
				.getAutowireCapableBeanFactory();
		beanFactory.autowireBean(this);
	}
}
