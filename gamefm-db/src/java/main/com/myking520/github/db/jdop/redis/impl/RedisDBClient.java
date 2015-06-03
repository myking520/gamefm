package com.myking520.github.db.jdop.redis.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;

import com.myking520.github.db.jdop.IDBClient;
import com.myking520.github.db.jdop.IData;
import com.myking520.github.db.jdop.redis.IMaxID;
import com.myking520.github.db.jdop.redis.IRedisDO;
import com.myking520.github.db.jdop.redis.IRedisTableInfo;
import com.myking520.github.db.jdop.redis.JedisUtil;

/**
 * 
 * 
 Copyright (c) 2015, kongguoan All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
public class RedisDBClient implements IDBClient {
	private final static Logger logger = LoggerFactory.getLogger(RedisDBClient.class);

	@Override
	public void save(IData... datas) {
		ShardedJedis sj = null;
		boolean error = false;
		IRedisTableInfo tinfo = (IRedisTableInfo) datas[0].getTableInfo();
		try {
			sj = JedisUtil.getSharedJedis();
			for (int i = 0; i < datas.length; i++) {
				IData data = datas[i];
				IRedisDO ido = (IRedisDO) data.getDO();
				Map<byte[], byte[]> dataMap = new HashMap<byte[], byte[]>();
				byte[] dump = new byte[0];
				Object pk = ido.getPrimaryKey();
				Object[] fks = ido.getFKs();
				tinfo.getMaxIDProcess().saveMaxID(tinfo.getTableName4Bytes(), pk, sj);
				byte[] pkBytes = pk.toString().getBytes(IMaxID.CHARSET);
				dataMap.put(pkBytes, ido.getData());
				sj.hmset(tinfo.getTableName4Bytes(), dataMap);
				if (fks != null && fks.length > 0) {
					for (int i1 = 0; i1 < tinfo.getFKNames().length; i1++) {
						String fkName = tinfo.getFKNames()[i1];
						if (fkName == null)
							continue;
						if (fks[i1] == null) {
							continue;
						}
						fkName = tinfo.getTableName() + fkName;
						String fkvl = fkName + fks[i1].toString();
						Map<byte[], byte[]> pkFiled = new HashMap<byte[], byte[]>();
						pkFiled.put(pkBytes, dump);
						sj.hmset(fkvl.getBytes(IMaxID.CHARSET), pkFiled);
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			error = true;
		} finally {
			if (sj != null) {
				if (error) {
					JedisUtil.returnBrokenJedis(sj);
				} else {
					JedisUtil.returnSharedJedis(sj);
				}
			}
		}
	}

	@Override
	public <O> void update(IData... u) {
		this.save(u);
	}

	@Override
	public <O> void delete(IData... datas) {
		ShardedJedis sj = null;
		boolean error = false;
		IRedisTableInfo tinfo = (IRedisTableInfo) datas[0].getTableInfo();
		try {
			sj = JedisUtil.getSharedJedis();
			for (int i = 0; i < datas.length; i++) {
				IData data = datas[i];
				IRedisDO ido = (IRedisDO) data.getDO();
				sj.hdel(tinfo.getTableName4Bytes(), ido.getPrimaryKey().toString().getBytes(IMaxID.CHARSET));
				Object[] fkValues = ido.getFKs();
				if (tinfo.getFKNames() != null) {
					for (int i1 = 0; i1 < tinfo.getFKNames().length; i1++) {
						String fkName = tinfo.getFKNames()[i1];
						if (fkName == null)
							continue;
						if (fkValues[i1] == null) {
							continue;
						}
						fkName = tinfo.getTableName() + fkName;
						String fkvl = fkName + fkValues[i1].toString();
						sj.hdel(fkvl.getBytes(IMaxID.CHARSET), ido.getPrimaryKey().toString().getBytes(IMaxID.CHARSET));
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			error = true;
		} finally {
			if (sj != null) {
				if (error) {
					JedisUtil.returnBrokenJedis(sj);
				} else {
					JedisUtil.returnSharedJedis(sj);
				}
			}
		}
	}

	@Override
	public <O> List<O> getAll(IData ido) {
		ShardedJedis sj = null;
		List<O> ret = new ArrayList<O>();
		boolean error = false;
		IRedisTableInfo tinfo = (IRedisTableInfo) ido.getTableInfo();
		IRedisDO redisDO = (IRedisDO) tinfo.getIDOCreater();
		try {
			sj = JedisUtil.getSharedJedis();
			Collection<byte[]> datas = sj.hvals(tinfo.getTableName4Bytes());
			for (byte[] data : datas) {
				if (data == null)
					continue;
				redisDO = redisDO.newDO();
				redisDO.createObject(data);
				ret.add(redisDO.getSource());
			}
		} catch (Exception e) {
			logger.error("", e);
			error = true;
		} finally {
			if (sj != null) {
				if (error) {
					JedisUtil.returnBrokenJedis(sj);
				} else {
					JedisUtil.returnSharedJedis(sj);
				}
			}
		}
		return ret;
	}

	@Override
	public <O> O findByPK(Serializable pk, IData ido) {
		ShardedJedis sj = null;
		boolean error = false;
		IRedisTableInfo tinfo = (IRedisTableInfo) ido.getTableInfo();
		IRedisDO rdido = (IRedisDO) tinfo.getIDOCreater().newDO();
		O ret = null;
		try {
			sj = JedisUtil.getSharedJedis();
			byte[] bytes = sj.hget(tinfo.getTableName4Bytes(), pk.toString().getBytes(IMaxID.CHARSET));
			if (bytes != null) {
				rdido.createObject(bytes);
			}
			return rdido.getSource();
		} catch (Exception e) {
			logger.error("", e);
			error = true;
		} finally {
			if (sj != null) {
				if (error) {
					JedisUtil.returnBrokenJedis(sj);
				} else {
					JedisUtil.returnSharedJedis(sj);
				}
			}
		}
		return ret;
	}

	@Override
	public <O> void updateFK(IData data, String fk, Object oldValue) {
		ShardedJedis sj = null;
		boolean error = false;
		try {
			sj = JedisUtil.getSharedJedis();
			
		} catch (Exception e) {
			logger.error("", e);
			error = true;
		} finally {
			if (sj != null) {
				if (error) {
					JedisUtil.returnBrokenJedis(sj);
				} else {
					JedisUtil.returnSharedJedis(sj);
				}
			}
		}
	}

	@Override
	public <O> List<O> findByFK(IData ido, Serializable fkv, String fkName) {
		ShardedJedis sj = null;
		List<O> ret = new ArrayList<O>();
		boolean error = false;
		IRedisTableInfo tinfo = (IRedisTableInfo) ido.getTableInfo();
		IRedisDO rdido = (IRedisDO) tinfo.getIDOCreater();
		String fk = tinfo.getTableName() + fkName + fkv;
		try {
			sj = JedisUtil.getSharedJedis();
			Set<byte[]> pks = sj.hkeys(fk.getBytes(IMaxID.CHARSET));
			byte[][] pkAry = new byte[pks.size()][];
			pkAry = pks.toArray(pkAry);
			if (pkAry.length > 0) {
				List<byte[]> datas = sj.hmget(tinfo.getTableName4Bytes(), pkAry);
				int len = datas.size();
				for (int i = 0; i < len; i++) {
					byte[] data = datas.get(i);
					if (data != null) {
						rdido = (IRedisDO) rdido.newDO();
						rdido.createObject(data);
						ret.add(rdido.getSource());
					} else {
						logger.error("没有数据" + fkv);
					}

				}
			}
		} catch (Exception e) {
			logger.error("", e);
			error = true;
		} finally {
			if (sj != null) {
				if (error) {
					JedisUtil.returnBrokenJedis(sj);
				} else {
					JedisUtil.returnSharedJedis(sj);
				}
			}
		}
		return ret;
	}

}
