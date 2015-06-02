package com.myking520.github.db.jdop.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myking520.github.db.jdop.IDO;
import com.myking520.github.db.jdop.IJDOP;
import com.myking520.github.db.jdop.ITableInfo;

public class DefaultJDOP implements IJDOP {
	private Map<Class, ITableInfo> tableInfos = new HashMap<Class, ITableInfo>();
	private IJDOP jdop;
	@Override
	public <O> void save(O o) {

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
	public <O> void update(O... u) {
		ITableInfo tinfo = this.getTableInfo(u);
		IDO doo = tinfo.getIDOCreater().newDO();
		doo.setSouce(u);
		jdop.update(doo);
	}

	@Override
	public <O> void delete(O... d) {
		ITableInfo tinfo = this.getTableInfo(d);
		IDO doo = tinfo.getIDOCreater().newDO();
		doo.setSouce(d);
		jdop.delete(doo);
	}

	@Override
	public <O> List<O> getAll(Class claz) {
		ITableInfo tinfo = this.getTableInfo(o);
		IDO doo = tinfo.getIDOCreater().newDO();
//		doo.setSouce(o);
//		return (List<O>) jdop.getAll(doo);
//	}

	@Override
	public <O> O findByPK(Serializable pk, Class claz) {
		ITableInfo tinfo = this.getTableInfo(o);
		
		return null;
//		return jdop.findByFK(rk, pkName, o);
	}

	@Override
	public <O> void updateFK(Class claz, String fk, Object newValue, Object oldValue) {

	}

	@Override
	public <O> List<O> findByFK(Serializable rk, String pkName, Class claz) {
		return null;
	}


}
