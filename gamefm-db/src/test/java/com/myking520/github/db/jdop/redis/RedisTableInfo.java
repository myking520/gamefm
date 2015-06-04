package com.myking520.github.db.jdop.redis;

import com.myking520.github.db.jdop.IDO;
import com.myking520.github.db.jdop.IPKFKGetter;
import com.myking520.github.db.jdop.redis.impl.IntMaxId;

public class RedisTableInfo implements IRedisTableInfo {
	private String tableName;
	private String[] fkNames;
	private IDO doCreater;
	public void setDoCreater(IDO doCreater) {
		this.doCreater = doCreater;
	}

	@Override
	public IMaxID getMaxIDProcess() {
		return new IntMaxId();
	}

	@Override
	public byte[] getTableName4Bytes() {
		return tableName.getBytes(IMaxID.CHARSET);
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public void setFkNames(String[] fkNames) {
		this.fkNames = fkNames;
	}

	@Override
	public String[] getFKNames() {
		return fkNames;
	}

	@Override
	public IPKFKGetter getPKFKGetter() {
		return null;
	}
 
}
