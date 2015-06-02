package com.myking520.github.db.jdop.redis;

import java.io.Serializable;

import com.myking520.github.db.annotations.CObj;
import com.myking520.github.db.common.IVO;
import com.myking520.github.db.jdop.IDO;

public class RedisDO implements IDO {
	private Object[] fks;
	private CObj souce;
	@Override
	public Serializable getPrimaryKey() {
		return this.souce.getId();
	}
	@Override
	public Object[] getFKs() {
		return fks;
	}
	public byte[] getData(){
		return new byte[0];
	}
	public void setSouce(Object souce) {
		this.souce=(CObj) souce;
		this.fks=new Object[]{this.souce.getMid(),this.souce.getMid()};
	}
	@Override
	public IDO newDO() {
		return new RedisDO();
	}
}
