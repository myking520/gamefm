package com.myking520.github.db.jdop.impl;

import java.io.Serializable;

import com.myking520.github.db.jdop.IDO;
import com.myking520.github.db.jdop.IData;
import com.myking520.github.db.jdop.ITableInfo;

/**
 * Copyright (c) 2015, kongguoan All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
public class DefaultData implements IData {
	private ITableInfo tinfo;
	private Object souce;
	public DefaultData(ITableInfo tinfo, Object souce) {
		super();
		this.tinfo = tinfo;
		this.souce = souce;
	}
	public ITableInfo getTableInfo() {
		return tinfo;
	}
	public <O> O getSource() {
		return (O) souce;
	}
	public Serializable getPrimaryKey() {
		return this.tinfo.getDOPS().getPK(this.souce);
	}
	public Object[] getFKs() {
		return  this.tinfo.getDOPS().getFK(this.souce);
	}

}
