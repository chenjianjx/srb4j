package org.srb4j.impl.support.aop.bizlayer;

import java.util.GregorianCalendar;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.srb4j.impl.biz.common.EntityBase;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */

@Intercepts({ @Signature(type = Executor.class, method = "update", args = {
		MappedStatement.class, Object.class }) })
public class RepoInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement stmt = (MappedStatement) invocation.getArgs()[0];
		Object param = invocation.getArgs()[1];
		if (stmt == null) {
			return invocation.proceed();
		}

		if (stmt.getSqlCommandType().equals(SqlCommandType.INSERT)) {
			if (param != null && param instanceof EntityBase) {
				EntityBase e = (EntityBase) param;
				if (e.getCreatedAt() == null) {
					e.setCreatedAt(new GregorianCalendar());
				}
			}
		}

		if (stmt.getSqlCommandType().equals(SqlCommandType.UPDATE)) {
			if (param != null && param instanceof EntityBase) {
				EntityBase e = (EntityBase) param;
				if (e.getUpdatedAt() == null) {
					e.setUpdatedAt(new GregorianCalendar());
				}
			}
		}

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}
}