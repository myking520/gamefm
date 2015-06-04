package com.myking520.github.db.jdop.redis;

import java.io.Serializable;

import com.myking520.github.db.annotations.CObj;
import com.myking520.github.db.jdop.IPKFKGetter;

public class COBJPK implements IPKFKGetter {

	@Override
	public Serializable getPK(Object obj) {
		CObj cobj=(CObj) obj;
		return cobj.getId();
	}

	@Override
	public Object[] getFK(Object obj) {
		CObj cobj=(CObj) obj;
		return 		new Object[]{cobj.getFid(),cobj.getMid()};
	}

}
