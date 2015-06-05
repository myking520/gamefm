package com.myking520.github.db.jdop.redis;

import java.io.Serializable;

import com.myking520.github.db.annotations.CObj;
import com.myking520.github.db.jdop.IDOPS;

public class COBJPK implements IDOPS {

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

	@Override
	public <O> O newObject() {
		return  (O) new CObj();
	}

}
