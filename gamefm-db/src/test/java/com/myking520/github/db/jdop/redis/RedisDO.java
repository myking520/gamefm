package com.myking520.github.db.jdop.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.myking520.github.db.annotations.CObj;
import com.myking520.github.db.jdop.IDO;
import com.myking520.github.db.jdop.redis.IRedisDO;

public class RedisDO implements IRedisDO {
	private Object[] fks;
	private CObj souce;
	private byte[] bytes;

	@Override
	public Serializable getPrimaryKey() {
		return this.souce.getId();
	}

	@Override
	public Object[] getFKs() {
		return fks;
	}

	public byte[] getData() {
		return bytes;
	}

	public void setSouce(Object souce) {
		this.souce = (CObj) souce;
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream oout = new ObjectOutputStream(bout);
			oout.writeObject(this.souce);
			this.bytes = bout.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.fks = new Object[] { this.souce.getFid(), this.souce.getMid() };
	}
	
	@Override
	public void createObject(byte[] bytes) {
		ByteArrayInputStream binp = new ByteArrayInputStream(bytes);
		try {
			ObjectInputStream oinp = new ObjectInputStream(binp);
			this.souce = (CObj) oinp.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
