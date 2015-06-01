package com.myking520.github.db.jdop.redis.impl;

import redis.clients.jedis.ShardedJedis;

import com.myking520.github.db.jdop.redis.IMaxID;
import com.myking520.github.db.jdop.redis.RedisTBOP;

public class IntMaxId implements IMaxID {
	@Override
	public Number getMaxID(byte[] tableName, ShardedJedis sj) {
		byte[] maxKey = sj.hget(MAXKEY, tableName);
		String maxStr = new String(maxKey, RedisTBOP.CHARSET);	
		return Integer.valueOf(maxStr);
	}
	@Override
	public void saveMaxID(byte[] tableName, Object value, ShardedJedis sj) {
		int va=(int) value;
		sj.hset(MAXKEY, tableName, (String.valueOf(va)).getBytes(RedisTBOP.CHARSET));
	}

}
