package com.myking520.github.db.jdop.redis;

import com.myking520.github.db.jdop.ITableInfo;

public interface IRedisTableInfo extends ITableInfo{
	public IMaxID getMaxIDProcess();
	public byte[] getTableName4Bytes();
}
