package com.myking520.github.db.jdop.redis;

import com.myking520.github.db.jdop.IDOPS;
import com.myking520.github.db.jdop.redis.impl.IntMaxId;

public class RedisTableInfo implements IRedisTableInfo {
	private String tableName;
	private String[] fkNames;
	private IDOPS dops;
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
	public IDOPS getDOPS() {
		return dops;
	}

	public void setDOPS(IDOPS ipkfkgetter) {
		this.dops = ipkfkgetter;
	}


}
