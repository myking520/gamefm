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
