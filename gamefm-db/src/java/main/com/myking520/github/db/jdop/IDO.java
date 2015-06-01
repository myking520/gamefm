package com.myking520.github.db.jdop;

import java.io.Serializable;

public interface IDO {
	/**
	 * @return 主键
	 */
	public Serializable getPrimaryKey();
	/**
	 * @return 关系字段值
	 */
	public Object[] getFKs();
	public byte[] getData();
}
