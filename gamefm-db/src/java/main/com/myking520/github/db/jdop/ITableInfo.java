package com.myking520.github.db.jdop;

public interface ITableInfo {
	/**
	 * @return 表名
	 */
	public String getTableName();
	/**
	 * @return 关系字段名
	 */
	public String[] getFKNames();
	/**
	 * @return
	 */
	public IDO getIDOCreater();
}
