package com.myking520.github.db.common.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

import com.myking520.github.db.common.DataConstant;
import com.myking520.github.db.common.DataObjField;
import com.myking520.github.db.common.DataObjInfo;

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
public class VOReader extends ClassVisitor {
	private DataObjInfo dbObjInfo;

	public VOReader(int api) {
		super(api);
	}

	public DataObjInfo getDbObjInfo() {
		return dbObjInfo;
	}

	private String superName;

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
		this.superName = superName;
		this.dbObjInfo = new DataObjInfo(version, access, name, signature, superName, interfaces);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor fv = super.visitField(access, name, desc, signature, value);
		DataObjField dbObjField = new DataObjField(access, name, desc, signature, value);
		FieldReader fr = new FieldReader(super.api, fv, dbObjInfo, dbObjField);
		return fr;
	}

	@Override
	public void visitEnd() {
		super.visitEnd();
		List<DataObjInfo> parents = new ArrayList<DataObjInfo>();
		if (!this.superName.equals(DataConstant.OBJECT_INTERNALNAME)) {
			VOReader voReader = new VOReader(DataConstant.API);
			try {
				ClassReader cr = new ClassReader(Type.getObjectType(superName).getClassName());
				cr.accept(voReader, 0);
				parents.add(voReader.dbObjInfo);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		for (int i = 0; i < parents.size(); i++) {
			DataObjInfo dataObjInfo = parents.get(i);
			for (int i0 = 0; i0 < dataObjInfo.getDbObjFields().size(); i0++) {
				DataObjField df = dataObjInfo.getDbObjFields().get(i0);
				this.dbObjInfo.addDBObjField(df);
			}
		}
	}

}
