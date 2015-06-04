package com.myking520.github.db.jdop;

import java.io.Serializable;
import java.util.List;

public interface IDBClient {
	public void save(IData ... u);
	public  <O> void update(IData ... u);
	public  <O> void delete(IData ... d);
	public <O>  List<O> getAll(IData ido );
	public  <O>O findByPK(Serializable pk,IData ido);
	public <O> void updateFK(IData ido,String fk,Object oldValue);
	public <O>  List<O> findByFK(IData ido,Serializable rk,String pkName);
	public <O> O query(String query,Object params,IQueryCallback callback);
}
