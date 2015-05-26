package com.myking520.github.reader;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;

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
public class FieldReader extends FieldVisitor {
	private DataObjField dbObjField;
	private DataObjInfo dbObjInfo;

	public FieldReader(int api, FieldVisitor fv, DataObjInfo dbObjInfo, DataObjField dbObjField) {
		super(api, fv);
		this.dbObjField = dbObjField;
		this.dbObjInfo = dbObjInfo;
	}

	@Override
	public void visitAttribute(Attribute attr) {
		// TODO Auto-generated method stub
		super.visitAttribute(attr);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		AnnotationVisitor av = super.visitAnnotation(desc, visible);
		if (desc.equals(DataConstant.COLUMNDESCRIPTOR)) {
			av = new ColumnReader(super.api, av, dbObjField, dbObjInfo);
		}
		return av;
	}
}
