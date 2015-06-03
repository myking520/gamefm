package com.myking520.github.db.jdop.impl;

import com.myking520.github.db.jdop.IDO;
import com.myking520.github.db.jdop.IData;
import com.myking520.github.db.jdop.ITableInfo;

public class DefaultData implements IData {
	private ITableInfo tinfo;
	private IDO ido;
	
	public DefaultData(ITableInfo tinfo, IDO ido) {
		super();
		this.tinfo = tinfo;
		this.ido = ido;
	}

	@Override
	public ITableInfo getTableInfo() {
		return tinfo;
	}

	@Override
	public IDO getDO() {
		return ido;
	}

}
