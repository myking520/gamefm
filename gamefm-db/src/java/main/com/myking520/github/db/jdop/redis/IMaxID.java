package com.myking520.github.db.jdop.redis;

import java.nio.charset.Charset;

import redis.clients.jedis.ShardedJedis;

public interface IMaxID {
	public final static String MAXKEY = "maxKey";
	public static final Charset CHARSET = Charset.forName("UTF-8");
	public final static byte[] MAXKEYBYTES = MAXKEY.getBytes(CHARSET);
	public Number getMaxID(byte[] tableName, ShardedJedis sj);
	public void saveMaxID(byte[] tableName, Object value, ShardedJedis sj);
}
