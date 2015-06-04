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

import com.myking520.github.db.common.IVORW;
import com.myking520.github.db.common.VORWFacotory;
import com.myking520.github.db.jdop.IDBClient;
import com.myking520.github.db.jdop.IData;
import com.myking520.github.db.jdop.IPKFKGetter;
import com.myking520.github.db.jdop.IQueryCallback;
import com.myking520.github.db.jdop.redis.IMaxID;
import com.myking520.github.db.jdop.redis.IRedisTableInfo;
import com.myking520.github.db.jdop.redis.JedisUtil;
import com.myking520.github.db.jdop.redis.RedisConnection;

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
	private byte[] EMPY = new byte[0];

	@Override
	public void save(IData... datas) {
		ShardedJedis sj = null;
		boolean error = false;
		IRedisTableInfo tinfo = (IRedisTableInfo) datas[0].getTableInfo();
		IPKFKGetter pkfkgetter = tinfo.getPKFKGetter();
		try {
			sj = JedisUtil.getSharedJedis();
			for (int i = 0; i < datas.length; i++) {
				IData data = datas[i];
				Map<byte[], byte[]> dataMap = new HashMap<byte[], byte[]>();
				Object pk = data.getPrimaryKey();
				Object[] fks = data.getFKs();
				tinfo.getMaxIDProcess().saveMaxID(tinfo.getTableName4Bytes(), pk, sj);
				byte[] pkvalue = pk.toString().getBytes(IMaxID.CHARSET);
				IVORW vorw = VORWFacotory.getVORW2(data.getSource());
				RedisDataHodler redisDataHodler = new RedisDataHodler();
				vorw.readFromVO(data.getSource());
				vorw.write2Dataholder(redisDataHodler);
				dataMap.put(pkvalue, redisDataHodler.toBytes());
				sj.hmset(tinfo.getTableName4Bytes(), dataMap);
				if (fks != null && fks.length > 0) {
					for (int i1 = 0; i1 < tinfo.getFKNames().length; i1++) {
						String fkName = tinfo.getFKNames()[i1];
						if (fkName == null)
							continue;
						if (fks[i1] == null) {
							continue;
						}
						String fkTableName = this.getFkTableName(tinfo.getTableName(), fkName, fks[i1]);
						this.saveFK(fkTableName, pkvalue, sj);
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

	private void saveFK(String fkTableName, byte[] pkvalue, ShardedJedis sj) {
		Map<byte[], byte[]> pkFiled = new HashMap<byte[], byte[]>();
		pkFiled.put(pkvalue, EMPY);
		sj.hmset(fkTableName.getBytes(IMaxID.CHARSET), pkFiled);
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

				sj.hdel(tinfo.getTableName4Bytes(), data.getPrimaryKey().toString().getBytes(IMaxID.CHARSET));
				Object[] fkValues = data.getFKs();
				if (tinfo.getFKNames() != null) {
					for (int i1 = 0; i1 < tinfo.getFKNames().length; i1++) {
						String fkName = tinfo.getFKNames()[i1];
						if (fkName == null)
							continue;
						if (fkValues[i1] == null) {
							continue;
						}
						this.delFk(tinfo.getTableName(), fkName, fkValues[i1], data.getPrimaryKey(), sj);
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

	private String getFkTableName(String tableName, String fkName, Object fkValue) {
		return tableName + ":" + fkName + ":" + fkValue;
	}

	private void delFk(String tableName, String fkName, Object fkValue, Serializable pk, ShardedJedis sj) {
		String fktable = this.getFkTableName(tableName, fkName, fkValue);
		sj.hdel(fktable.getBytes(IMaxID.CHARSET), pk.toString().getBytes(IMaxID.CHARSET));
	}

	@Override
	public <O> void updateFK(IData data, String fk, Object oldValue) {
		ShardedJedis sj = null;
		boolean error = false;
		IRedisTableInfo tinfo = (IRedisTableInfo) data.getTableInfo();
		int index = -1;
		for (int i = 0; i < tinfo.getFKNames().length; i++) {
			String fkname = tinfo.getFKNames()[i];
			if (fkname.equals(fk)) {
				index = i;
			}
		}
		if (index == -1) {
			throw new RuntimeException(tinfo.getTableName() + "没有找到FK" + "->" + fk);
		}
		try {
			sj = JedisUtil.getSharedJedis();
			this.delFk(tinfo.getTableName(), fk, oldValue, data.getPrimaryKey(), sj);
			String fkTableName = this.getFkTableName(tinfo.getTableName(), fk, data.getFKs()[index]);
			this.saveFK(fkTableName, data.getPrimaryKey().toString().getBytes(IMaxID.CHARSET), sj);
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
		try {
			sj = JedisUtil.getSharedJedis();
			Collection<byte[]> datas = sj.hvals(tinfo.getTableName4Bytes());
			for (byte[] data : datas) {
				if (data == null)
					continue;
				// TODO 返回对象
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
		O ret = null;
		try {
			sj = JedisUtil.getSharedJedis();
			byte[] bytes = sj.hget(tinfo.getTableName4Bytes(), pk.toString().getBytes(IMaxID.CHARSET));
			if (bytes != null) {
				// TODO 返回对象
			}
			return null;
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
	public <O> List<O> findByFK(IData ido, Serializable fkv, String fkName) {
		ShardedJedis sj = null;
		List<O> ret = new ArrayList<O>();
		boolean error = false;
		IRedisTableInfo tinfo = (IRedisTableInfo) ido.getTableInfo();
		String fkTableName = this.getFkTableName(tinfo.getTableName(), fkName, fkv);
		try {
			sj = JedisUtil.getSharedJedis();
			Set<byte[]> pks = sj.hkeys(fkTableName.getBytes(IMaxID.CHARSET));
			byte[][] pkAry = new byte[pks.size()][];
			pkAry = pks.toArray(pkAry);
			if (pkAry.length > 0) {
				List<byte[]> datas = sj.hmget(tinfo.getTableName4Bytes(), pkAry);
				int len = datas.size();
				for (int i = 0; i < len; i++) {
					byte[] data = datas.get(i);
					if (data != null) {
						// TODO 返回对象
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

	@Override
	public <O> O query(String query, Object params, IQueryCallback callback) {
		ShardedJedis sj = null;
		boolean error = false;
		try {
			RedisConnection rc = new RedisConnection(sj);
			return callback.excute(query, params, rc);
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
		return null;
	}

}
