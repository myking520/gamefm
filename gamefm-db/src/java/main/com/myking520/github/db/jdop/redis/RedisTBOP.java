package com.myking520.github.db.jdop.redis;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;

import com.myking520.github.db.jdop.IDO;
import com.myking520.github.db.jdop.ITBOP;

public class RedisTBOP<O extends IDO> implements ITBOP<O> {
	public static final Charset CHARSET = Charset.forName("UTF-8");
	private final static Logger logger = LoggerFactory.getLogger(RedisTBOP.class);
	private Map<Object, Object> saves = new ConcurrentHashMap<Object, Object>();
	private Map<Object, Object> updates = new ConcurrentHashMap<Object, Object>();
	private Map<Object, Object> deletes = new ConcurrentHashMap<Object, Object>();
	private String tableName;
	private byte[] tableNamebytes;
	private IMaxID maxidProcess;
	public void setMaxidProcess(IMaxID maxidProcess) {
		this.maxidProcess = maxidProcess;
	}

	private String[] fkNames;

	public void setFkNames(String[] fkNames) {
		this.fkNames = fkNames;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
		this.tableNamebytes=tableName.getBytes(CHARSET);
	}

	private void saveOrUpdate(O... o) {
		if (o == null || o.length < 1) {
			return;
		}
		ShardedJedis shardedjedis = null;
		try {
			shardedjedis = JedisUtil.getSharedJedis();
			for (int i = 0; i < o.length; i++) {
				O s = o[i];
				Object pk = s.getPrimaryKey();
				try {
					Map<byte[], byte[]> dataMap = new HashMap<byte[], byte[]>();
					Object[] fks = s.getFKs();
					maxidProcess.saveMaxID(tableNamebytes, pk, shardedjedis);
					byte[] pkBytes = pk.toString().getBytes(CHARSET);
					dataMap.put(pkBytes, s.getData());
					shardedjedis.hmset(this.tableNamebytes, dataMap);
					Map<byte[], byte[]> pkmap = new HashMap<byte[], byte[]>();
					pkmap.put(pkBytes, new byte[0]);
					for (int i0 = 0; i0 < fks.length; i0++) {
						Object fkv = fks[i0];
						if (fkv == null) {
							continue;
						}
						String fkname = this.fkNames[i0];
						if (fkname == null) {
							continue;
						}
						String fk = fkname + fkv;
						shardedjedis.hmset(fk.toString().getBytes(CHARSET), pkmap);
					}
				} catch (Exception e) {
					logger.error("", e);
				} finally {
					if (shardedjedis != null)
						JedisUtil.returnSharedJedis(shardedjedis);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (shardedjedis != null) {
				JedisUtil.returnBrokenJedis(shardedjedis);
			}
		}
	}

	@Override
	public void save(O... o) {
		this.saveOrUpdate(o);
	}

	@Override
	public void update(O... o) {
		this.saveOrUpdate(o);
	}

	@Override
	public void delete(O... o) {

	}

	@Override
	public O findByPK(Serializable pk) {
		ShardedJedis shardedjedis = null;
		try {
			shardedjedis = JedisUtil.getSharedJedis();
			byte[] bytes = shardedjedis.hget(tableName.getBytes(CHARSET), pk.toString().getBytes(CHARSET));
			if (bytes != null) {
				System.out.println(bytes);
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (shardedjedis != null)
				JedisUtil.returnSharedJedis(shardedjedis);
		}
		return null;
	}

	@Override
	public List<O> findByFK(Serializable fk, String pkName) {
		List<O> ret = new ArrayList<O>();
		String fkv = pkName + fk;
		ShardedJedis shardedjedis = null;
		try {
			shardedjedis = JedisUtil.getSharedJedis();
			Set<byte[]> pks = shardedjedis.hkeys(fkv.getBytes(CHARSET));
			byte[][] pkAry = new byte[pks.size()][];
			pkAry = pks.toArray(pkAry);
			if (pkAry.length > 0) {
				List<byte[]> datas = shardedjedis.hmget(tableName.getBytes(CHARSET), pkAry);
				int len = datas.size();
				for (int i = 0; i < len; i++) {
					byte[] data = datas.get(i);
					if (data != null) {
						// TODO 反序列化
					} else {
						// TODO 数据丢失处理
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (shardedjedis != null)
				JedisUtil.returnSharedJedis(shardedjedis);
		}
		return ret;
	}

}
