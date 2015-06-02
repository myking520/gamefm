package com.myking520.github.db.annotations;

import java.util.List;

import com.myking520.github.db.common.annotation.Column;
import com.myking520.github.db.jdop.annotations.Entity;

@Entity(name = "father")
public class FObj {
	@Column(index = 1, name = "id")
	private int id;
	@Column(index = 2, name = "name")
	private String name;
	
	private List<CObj> cobj;
}
