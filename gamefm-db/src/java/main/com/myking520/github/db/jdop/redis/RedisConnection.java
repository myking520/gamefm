package com.myking520.github.db.jdop.redis;

import redis.clients.jedis.ShardedJedis;

import com.myking520.github.db.jdop.IDBConnection;

public class RedisConnection implements IDBConnection {
	private ShardedJedis shardedJedis;
	public RedisConnection(ShardedJedis shardedJedis) {
		super();
		this.shardedJedis = shardedJedis;
	}
	@Override
	public ShardedJedis getConnection() {
		return shardedJedis;
	}

}
