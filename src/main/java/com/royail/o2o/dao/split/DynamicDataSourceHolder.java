package com.royail.o2o.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicDataSourceHolder {

	private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
	
	private static ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	
	public static final String DB_MASTER = "master" ;
	
	public static final String DB_SLAVE = "slave";
	
	/**
	 *根据数据源的键  获取相应值
	 * @return  数据源的值
	 */
	public static String  getDbType() {
		String  db = contextHolder.get();
		if(null == db){
			db = DB_MASTER;
		}
		return db;
	}
	
	
	/**
	 * 设置数据源的类型
	 * @param string 数据源的键
	 */
	public static void  setDbType(String string){
		logger.debug("所使用的数据源为： "+string);
		contextHolder.set(string);
	}
	
	
	
	/**
	 * 清楚连接数据源的类型
	 */
	public static void  clearDbType(){
		contextHolder.remove();
	}
	
	
}
