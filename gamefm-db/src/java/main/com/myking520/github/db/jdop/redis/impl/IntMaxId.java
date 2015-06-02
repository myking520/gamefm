package com.myking520.github.db.jdop.redis.impl;

import redis.clients.jedis.ShardedJedis;

import com.myking520.github.db.jdop.redis.IMaxID;

public class IntMaxId implements IMaxID {
	@Override
	public Number getMaxID(byte[] tableName, ShardedJedis sj) {
		byte[] maxKey = sj.hget(MAXKEYBYTES, tableName);
		String maxStr = new String(maxKey, IMaxID.CHARSET);	
		return Integer.valueOf(maxStr);
	}
	@Override
	public void saveMaxID(byte[] tableName, Object value, ShardedJedis sj) {
		int va=(int) value;
		sj.hset(MAXKEYBYTES, tableName, (String.valueOf(va)).getBytes(IMaxID.CHARSET));
	}

}
