package com.myking520.github.db.common.writer.datahodlerfield;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.myking520.github.db.common.DataConstant;
import com.myking520.github.db.common.DataObjField;
import com.myking520.github.db.common.DataObjInfo;
import com.myking520.github.db.common.writer.IDataHolderFieldRW;

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
public class LongRW implements IDataHolderFieldRW, Opcodes {

	@Override
	public void write(MethodVisitor mv, DataObjField dbObjField, DataObjInfo dbObjInfo) {
		mv.visitVarInsn(ALOAD, 1);
		mv.visitLdcInsn(dbObjField.getName());
		mv.visitIntInsn(BIPUSH, dbObjField.getDbindex());
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, dbObjInfo.getDynName(), dbObjField.getName(), "J");
		mv.visitMethodInsn(INVOKEINTERFACE, DataConstant.IDATAHOLDER_INTERNALNAME, "putLong",
				"(Ljava/lang/String;IJ)V", true);
	}

	@Override
	public void read(MethodVisitor mv, DataObjField dbObjField, DataObjInfo dbObjInfo) {
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitLdcInsn(dbObjField.getName());
		mv.visitIntInsn(BIPUSH, dbObjField.getDbindex());
		mv.visitMethodInsn(INVOKEINTERFACE, DataConstant.IDATAHOLDER_INTERNALNAME, "getLong", "(Ljava/lang/String;I)J",
				true);
		mv.visitFieldInsn(PUTFIELD, dbObjInfo.getDynName(), dbObjField.getName(), "J");
	}

}
