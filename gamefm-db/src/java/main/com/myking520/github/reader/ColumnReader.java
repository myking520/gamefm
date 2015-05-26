package com.myking520.github.reader;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Type;

import com.myking520.github.DataConstant;
import com.myking520.github.DataObjField;
import com.myking520.github.DataObjInfo;

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
public class ColumnReader extends AnnotationVisitor {
	private DataObjField dbObjField;
	private DataObjInfo dbObjInfo;

	public ColumnReader(int api, AnnotationVisitor av, DataObjField dbObjField, DataObjInfo dbObjInfo) {
		super(api, av);
		this.dbObjField = dbObjField;
		this.dbObjInfo = dbObjInfo;
	}

	@Override
	public void visit(String name, Object value) {
		super.visit(name, value);
		if (DataConstant.COLUMND_SERDESERCLASS.equals(name)) {
			dbObjField.setSerDeSerClass((Type) value);
		} else if (DataConstant.COLUMND_INDEX.equals(name)) {
			Integer ivalue = (Integer) value;
			if (ivalue < 1) {
				throw new RuntimeException("字段索引错误。只能从1开始[" + dbObjInfo.getName() + "." + dbObjField.getName() + "]");
			}
			dbObjField.setDbindex(ivalue);
		} else if (DataConstant.COLUMND_CLONEMECLASS.equals(name)) {
			dbObjField.setCloneMeClass((Type) value);
		}
	}

	@Override
	public void visitEnd() {
		super.visitEnd();
		if (dbObjField.getDbindex() > 0)
			dbObjInfo.addDBObjField(dbObjField);
	}

}
