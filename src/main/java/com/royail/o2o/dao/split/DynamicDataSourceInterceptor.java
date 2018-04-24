package com.royail.o2o.dao.split;

import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Intercepts({@Signature(type =Executor.class, method="update",args={MappedStatement.class,Object.class}),
			@Signature(type =Executor.class, method="query",args={MappedStatement.class,Object.class,RowBounds.class,ResultHandler.class})	})
public class DynamicDataSourceInterceptor implements Interceptor {
	private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);
	private static final String SQL_REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		String lookupKey = DynamicDataSourceHolder.DB_MASTER;
		boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
		Object[] objects = invocation.getArgs();
		MappedStatement mStatement = (MappedStatement) objects[0];

		if (synchronizationActive != true) {
			// 读方法
			if (mStatement.getSqlCommandType().equals(SqlCommandType.SELECT)) {
				// selectKey 为自增id查询主键 （select Last_insert_id）方法，使用主库
				if (mStatement.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
					lookupKey = DynamicDataSourceHolder.DB_MASTER;
				} else {
					BoundSql boundsql = mStatement.getSqlSource().getBoundSql(objects[1]);
					String sql = boundsql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");

					if (sql.matches(SQL_REGEX)) {
						lookupKey = DynamicDataSourceHolder.DB_MASTER;
					} else {
						lookupKey = DynamicDataSourceHolder.DB_SLAVE;
					}
				}
			}
		} else {
			lookupKey = DynamicDataSourceHolder.DB_MASTER;
		}
		logger.debug("设置方法[{}] use[{}]Strategy,SqlCommanType[{}]..",mStatement.getId(),lookupKey,mStatement.getSqlCommandType().name());
		DynamicDataSourceHolder.setDbType(lookupKey);
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub

	}

}
