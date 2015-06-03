package com.myking520.github.db.jdop.redis;

import com.myking520.github.db.jdop.IDO;

public interface IRedisDO extends IDO{
	public byte[] getData();
	public void createObject(byte[] bytes);
}
