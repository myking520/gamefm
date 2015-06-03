package com.myking520.github.db.jdop;

import java.io.Serializable;
import java.util.List;


public interface IJDOP {
	public <O>  void save(O... o);
	public  <O> void update(O... u);
	public  <O> void delete(O... d);
	public <O>  List<O> getAll(Class claz);
	public  <O>O findByPK(Serializable pk,Class claz);
	public <O> void updateFK(Class claz,String fk,Object newValue,Object oldValue);
	public <O>  List<O> findByFK(Serializable rk,String pkName,Class claz);
}
