package com.myking520.github.db.annotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.myking520.github.db.common.IVO;
import com.myking520.github.db.common.annotation.Column;
import com.myking520.github.db.jdop.annotations.Entity;
import com.myking520.github.db.jdop.annotations.PK;

@Entity(name = "children")
public class CObj implements IVO{
	@PK
	@Column(index = 1, name = "id")
	private int id;
	@Column(index = 2, name = "name")
	private String name;
	@Column(index = 3, name = "fid")
	private int fid;
	@Column(index = 4, name = "mid")
	private int mid;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}
}
