package com.myking520.github.db.jdop.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
Copyright (c) 2015, kongguoan
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */
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
