package com.myking520.github.db.jdop.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myking520.github.db.jdop.IDBClient;
import com.myking520.github.db.jdop.IDO;
import com.myking520.github.db.jdop.IData;
import com.myking520.github.db.jdop.IJDOP;
import com.myking520.github.db.jdop.ITableInfo;

public class DefaultJDOP implements IJDOP {
	private Map<Class, ITableInfo> tableInfos = new HashMap<Class, ITableInfo>();
	private IDBClient dbClient;

	@Override
	public <O> void save(O... o) {
		dbClient.save(this.getDatas(o));
	}

	private <O> IData[] getDatas(O... o) {
		ITableInfo tinfo = this.getTableInfo(o[0]);
		IData[] datas = new IData[o.length];
		for (int i = 0; i < o.length; i++) {
			IDO doo = tinfo.getIDOCreater().newDO();
			doo.setSouce(o);
			DefaultData data = new DefaultData(tinfo, doo);
			datas[i] = data;
		}
		return datas;
	}

	private ITableInfo getTableInfo(Object o) {
		ITableInfo tinfo = tableInfos.get(o.getClass());
		if (tinfo == null) {
			throw new RuntimeException("不能保存的对象" + o.getClass());
		}
		return tinfo;
	}

	private ITableInfo getTableInfo(Class claz) {
		ITableInfo tinfo = tableInfos.get(claz);
		if (tinfo == null) {
			throw new RuntimeException("不能保存的对象" + claz);
		}
		return tinfo;
	}

	@Override
	public <O> void update(O... o) {
		dbClient.update(this.getDatas(o));
	}

	@Override
	public <O> void delete(O... o) {
		dbClient.delete(this.getDatas(o));
	}

	@Override
	public <O> List<O> getAll(Class claz) {
		ITableInfo tinfo = this.getTableInfo(claz);
		IDO ido = tinfo.getIDOCreater();
		DefaultData data = new DefaultData(tinfo, ido);
		return dbClient.getAll(data);
	}

	@Override
	public <O> O findByPK(Serializable pk, Class claz) {
		ITableInfo tinfo = this.getTableInfo(claz);
		DefaultData data = new DefaultData(tinfo, null);
		return dbClient.findByPK(pk, data);
	}

	@Override
	public <O> void updateFK(Class claz, String fk, Object newValue, Object oldValue) {
		ITableInfo tinfo = this.getTableInfo(claz);
		DefaultData data = new DefaultData(tinfo, null);
		dbClient.updateFK(data, fk, oldValue);
	}

	@Override
	public <O> List<O> findByFK(Serializable rk, String pkName, Class claz) {
		ITableInfo tinfo = this.getTableInfo(claz);
		DefaultData data = new DefaultData(tinfo, null);
		return dbClient.findByFK(data, rk, pkName);
	}

}
