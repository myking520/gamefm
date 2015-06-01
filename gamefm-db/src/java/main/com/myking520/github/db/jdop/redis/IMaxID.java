package com.myking520.github.db.jdop.redis;

import redis.clients.jedis.ShardedJedis;

public interface IMaxID {
public final static byte[]	MAXKEY="maxKey".getBytes(RedisTBOP.CHARSET);
	public Number getMaxID(byte[] tableName,ShardedJedis sj);
	public void saveMaxID(byte[] tableName,Object value, ShardedJedis sj);
}
