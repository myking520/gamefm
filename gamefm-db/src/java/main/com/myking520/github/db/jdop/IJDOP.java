package com.myking520.github.db.jdop;

import java.util.List;

public interface IJDOP {
	/**
	 * 查找
	 * @param query
	 * @param clasz
	 * @param param
	 * @return
	 */
	public <O>List<O> find(String query,O clasz,Object ... param);
	/**
	 * 删除
	 * @param query
	 * @param param
	 */
	public void delete(String query,Object... param);
	/**删除
	 * @param o
	 */
	public <O> void delete(O o);
}
