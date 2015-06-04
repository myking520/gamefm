package com.myking520.github.db.jdop;

public interface IQueryCallback {
	public <O> O excute(String query,Object params,IDBConnection connnection);
}
