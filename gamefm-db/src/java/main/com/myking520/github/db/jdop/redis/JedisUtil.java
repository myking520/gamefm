package com.myking520.github.db.jdop.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class JedisUtil {

	private static ShardedJedisPool jedisPool = null;

	public static void Init(String host,int port,String password){
		if(password!=null&&password.trim().length() == 0)
			password = null;
		init(host, port, password, null);
	}
	
	public static void Init(String host,int port,String pwd,JedisPoolConfig config){
		init(host, port, pwd, config);
	}
	private static void init(String host,int port,String pwd,JedisPoolConfig config){
		List<JedisShardInfo> jedisShardInfos = new ArrayList<JedisShardInfo>();
		JedisShardInfo jedisShardInfo = new JedisShardInfo(host,port);
		jedisShardInfo.setTimeout(10000);
		jedisShardInfo.setPassword(pwd);
		jedisShardInfos.add(jedisShardInfo);
		jedisPool = new ShardedJedisPool(config==null ? createDefJedisConfig() : config, jedisShardInfos);
	}
	
	private static JedisPoolConfig createDefJedisConfig() {
		JedisPoolConfig jedisConfig = new JedisPoolConfig();
		jedisConfig.setMaxIdle(2000);
		jedisConfig.setMaxWaitMillis(10000L);
		jedisConfig.setTestOnBorrow(true);
		return jedisConfig;
	}
	
	public static ShardedJedis getSharedJedis(){
		return jedisPool.getResource();
	}
	
	public static void returnBrokenJedis(ShardedJedis sj){
		jedisPool.returnBrokenResource(sj);
	}
	
	
	
	public static void returnSharedJedis(ShardedJedis sj){
	
		jedisPool.returnResource(sj);
	}
}
