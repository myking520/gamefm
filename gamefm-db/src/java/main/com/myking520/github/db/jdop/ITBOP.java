package com.myking520.github.db.jdop;

import java.io.Serializable;
import java.util.List;

public interface ITBOP<O extends IDO> {
	public  void save(O... o);
	public  void update(O... o);
	public  void delete(O... o);
	public  O findByPK(Serializable pk);
	public  List<O> findByFK(Serializable rk,String pkName);
	public String getTableName();
}
