package com.myking520.github.db.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

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
public class DataObjInfo {
	private Map<String, DataObjField> dbObjFields = new LinkedHashMap<String, DataObjField>();
	private Map<Integer, DataObjField> dbObjFieldsIndex = new HashMap<Integer, DataObjField>();
	private List<DataObjField> needCloneMes = new ArrayList<DataObjField>();
	private List<DataObjField> sorted = null;
	private String dynName;
	private String name;
	private String desc;
	private String dynDesc;

	public void addDBObjField(DataObjField dbObjField) {
		DataObjField dbObjFieldo = dbObjFieldsIndex.get(dbObjField.getDbindex());
		if (dbObjFieldo != null) {
			throw new RuntimeException(this.name + "索引重复[" + dbObjField.getName() + "]->" + dbObjFieldo.getName());
		}
		dbObjFieldsIndex.put(dbObjField.getDbindex(), dbObjField);
		this.dbObjFields.put(dbObjField.getName(), dbObjField);
	}

	public DataObjInfo(int version, int access, String name, String signature, String superName, String[] interfaces) {
		this.name = name;
		Type type = Type.getObjectType(this.name);
		this.desc = type.getDescriptor();
		this.dynName = this.name.replaceAll("\\$", "/") + DataConstant.DYNSIGN;
		this.dynDesc = Type.getObjectType(this.dynName).getDescriptor();
	}

	public String getDynName() {
		return dynName;
	}

	/**
	 * 字段名
	 * 
	 * @param fname
	 * @return
	 */
	public DataObjField getDBObjField(String fname) {
		return this.dbObjFields.get(fname);
	}

	public String getName() {
		return name;
	}

	public String getDynDesc() {
		return dynDesc;
	}

	public String getDesc() {
		return desc;
	}

	public List<DataObjField> getNeedCloneMes() {
		return needCloneMes;
	}

	public List<DataObjField> getDbObjFields() {
		if (this.sorted == null) {
			this.sorted = new ArrayList<DataObjField>(dbObjFields.values());
			Collections.sort(this.sorted, new Comparator<DataObjField>() {

				@Override
				public int compare(DataObjField o1, DataObjField o2) {
					int r = o1.getDbindex() - o2.getDbindex();
					if (r == 0) {
						return 0;
					}
					return r > 0 ? 1 : -1;
				}

			});
			for (int i = 0; i < this.sorted.size(); i++) {
				DataObjField dbObjField = sorted.get(i);
				if (dbObjField.getCloneMeClass() != null) {
					needCloneMes.add(dbObjField);
				}
			}

		}
		return this.sorted;
	}

}
