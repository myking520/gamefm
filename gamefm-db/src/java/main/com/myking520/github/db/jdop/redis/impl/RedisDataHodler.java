package com.myking520.github.db.jdop.redis.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.myking520.github.db.common.IDataHolder;

public class RedisDataHodler implements IDataHolder {
	private Object souce;
	@Override
	public int getInt(String name, int index) {
		return 0;
	}
	@Override
	public void putInt(String name, int index, int value) {

	}
	@Override
	public long getLong(String name, int index) {
		return 0;
	}
	@Override
	public void putLong(String name, int index, long value) {

	}
	@Override
	public String getString(String name, int index) {
		return null;
	}
	@Override
	public void putString(String name, int index, String value) {

	}
	@Override
	public void putIntList(String name, int index, int[] value) {

	}
	@Override
	public int[] getIntList(String name, int index) {
		return null;
	}
	@Override
	public void putBytes(String name, int index, byte[] value) {

	}
	@Override
	public byte[] getBytes(String name, int index) {
		return null;
	}
	public byte[] toBytes(){
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream oout = new ObjectOutputStream(bout);
			oout.writeObject(this.souce);
			return  bout.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
