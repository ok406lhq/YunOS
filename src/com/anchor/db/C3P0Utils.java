package com.anchor.db;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * c3p0连接MySQL数据库工具包
 * 
 */
public class C3P0Utils {
	// 定义全局变量
	private static ComboPooledDataSource datasources;
	// 获得数据源
	public static DataSource getDataSource()
	{
		if (datasources == null){
			datasources = new ComboPooledDataSource("dbsimp");
		}
		return datasources;
	}
}