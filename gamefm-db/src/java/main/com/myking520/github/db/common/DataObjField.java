package com.myking520.github.db.common;

import org.objectweb.asm.Type;


/**
Copyright (c) 2015, kongguoan
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 */
public class DataObjField {
	private int access;
	private String name;
	private String desc;
	private String signature;
	private Object value;
	// 索引
	private int dbindex;
	// 序列化反序列化方式
	private Type serDeSerClass;
	// clone方式
	private Type cloneMeClass;
	private String methodName;

	public DataObjField(int access, String name, String desc, String signature, Object value) {
		super();
		this.access = access;
		this.name = name;
		this.desc = desc;
		this.signature = signature;
		this.value = value;
		char upc = Character.toUpperCase(name.charAt(0));
		this.methodName = name.replaceFirst(String.valueOf(name.charAt(0)), String.valueOf(upc));
	}

	public int getAccess() {
		return access;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public String getSignature() {
		return signature;
	}

	public Object getValue() {
		return value;
	}

	public int getDbindex() {
		return dbindex;
	}

	public void setDbindex(int dbindex) {
		this.dbindex = dbindex;
	}

	public Type getSerDeSerClass() {
		return serDeSerClass;
	}

	public void setSerDeSerClass(Type serDeSerClass) {
		this.serDeSerClass = serDeSerClass;
	}

	public Type getCloneMeClass() {
		return cloneMeClass;
	}

	public void setCloneMeClass(Type cloneMeClass) {
		this.cloneMeClass = cloneMeClass;
	}

	public String getMethodName() {
		return this.methodName;
	}

}
