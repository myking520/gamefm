package com.myking520.github.db.jdop.impl.redis;

import java.util.List;

import com.myking520.github.db.jdop.IJDOP;

public class RedisJDOP implements IJDOP {
	public <O> List<O> find(String query, O clasz, Object... param) {
		return null;
	}

	@Override
	public void delete(String query, Object... param) {
		
	}

	@Override
	public <O> void delete(O o) {
		
	}

}
