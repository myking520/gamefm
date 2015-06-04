package com.myking520.github.db.common.writer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.myking520.github.db.common.DataConstant;
import com.myking520.github.db.common.DataObjField;
import com.myking520.github.db.common.DataObjInfo;
import com.myking520.github.db.common.reader.VOReader;
import com.myking520.github.db.common.writer.datahodlerfield.BooleanRW;
import com.myking520.github.db.common.writer.datahodlerfield.IntLsRW;
import com.myking520.github.db.common.writer.datahodlerfield.IntRW;
import com.myking520.github.db.common.writer.datahodlerfield.LongRW;
import com.myking520.github.db.common.writer.datahodlerfield.StringWR;

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
public class POWriter implements Opcodes {
	private ClassWriter cw;
	private DataObjInfo dbObjInfo;
	private MethodVisitor mv = null;
	private static Map<String, IDataHolderFieldRW> dataHolderFieldRWs = new HashMap<String, IDataHolderFieldRW>();
	{
		dataHolderFieldRWs.put(Type.INT_TYPE.getDescriptor(), new IntRW());
		dataHolderFieldRWs.put(Type.LONG_TYPE.getDescriptor(), new LongRW());
		dataHolderFieldRWs.put(Type.getDescriptor(String.class), new StringWR());
		dataHolderFieldRWs.put(Type.getDescriptor(int[].class), new IntLsRW());
		dataHolderFieldRWs.put(Type.BOOLEAN_TYPE.getDescriptor(), new BooleanRW());
	}

	public POWriter(DataObjInfo dbObjInfo) {
		super();
		this.dbObjInfo = dbObjInfo;
	}

	private void build() {
		cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		this.buildClass();
		this.buildField();
		this.buildInit();
		this.buildMethodReadFromVO();
		this.buildMethodWrite2VO();
		this.buildMethodwrite2Dataholder();
		this.buildMethodReadFromDataholder();
		this.buildMethodClone();
		this.buildMethodnewIVORW();
		cw.visitEnd();
	}

	private void buildMethodnewIVORW() {
		mv = cw.visitMethod(ACC_PUBLIC, "newIVORW", "()"+ DataConstant.IVORW_DESCRIPTOR, null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitTypeInsn(NEW, dbObjInfo.getDynName());
		mv.visitInsn(DUP);
		mv.visitMethodInsn(INVOKESPECIAL, dbObjInfo.getDynName(), "<init>", "()V", false);
		mv.visitInsn(ARETURN);
		Label l1 = new Label();
		mv.visitLabel(l1);
		mv.visitLocalVariable("this", this.dbObjInfo.getDynDesc(), null, l0, l1, 0);
		mv.visitMaxs(2, 1);
		mv.visitEnd();
	}

	private void buildClass() {
		cw.visit(V1_5, ACC_PUBLIC + ACC_SUPER, dbObjInfo.getDynName(), null, DataConstant.OBJECT_INTERNALNAME,
				new String[] { DataConstant.IVORW_INTERNALNAME });
	}

	private void buildField() {
		List<DataObjField> fields = dbObjInfo.getDbObjFields();
		for (int i = 0; i < fields.size(); i++) {
			DataObjField dbObjField = fields.get(i);
			FieldVisitor fv = cw.visitField(dbObjField.getAccess(), dbObjField.getName(), dbObjField.getDesc(),
					dbObjField.getSignature(), dbObjField.getValue());
			fv.visitEnd();
		}
	}

	private void buildInit() {
		mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, DataConstant.OBJECT_INTERNALNAME, "<init>", "()V", false);
		mv.visitInsn(RETURN);
		Label l1 = new Label();
		mv.visitLabel(l1);
		mv.visitLocalVariable("this", this.dbObjInfo.getDynDesc(), null, l0, l1, 0);
		mv.visitMaxs(1, 1);
		mv.visitEnd();
	}

	private void buildMethodReadFromVO() {
		mv = cw.visitMethod(ACC_PUBLIC, "readFromVO", DataConstant.READFROMVO_DESC, null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitTypeInsn(CHECKCAST, dbObjInfo.getName());
		mv.visitVarInsn(ASTORE, 2);
		Label l1 = new Label();
		mv.visitLabel(l1);
		List<DataObjField> fields = this.dbObjInfo.getDbObjFields();
		for (int i = 0; i < fields.size(); i++) {
			Label l4 = new Label();
			mv.visitLabel(l4);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 2);
			DataObjField dbf = fields.get(i);
			if (dbf.getDesc().equals(Type.BOOLEAN_TYPE.getDescriptor())) {
				mv.visitMethodInsn(INVOKEVIRTUAL, dbObjInfo.getName(), "is" + dbf.getMethodName(),
						"()" + dbf.getDesc(), false);
			} else {
				mv.visitMethodInsn(INVOKEVIRTUAL, dbObjInfo.getName(), "get" + dbf.getMethodName(),
						"()" + dbf.getDesc(), false);
			}
			mv.visitFieldInsn(PUTFIELD, dbObjInfo.getDynName(), dbf.getName(), dbf.getDesc());
		}
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitInsn(RETURN);
		Label l3 = new Label();
		mv.visitLabel(l3);
		mv.visitLocalVariable("this", dbObjInfo.getDynDesc(), null, l0, l3, 0);
		mv.visitLocalVariable("voIn", DataConstant.IVO_DESCRIPTOR, null, l0, l3, 1);
		mv.visitLocalVariable("vo", dbObjInfo.getDesc(), null, l1, l3, 2);
		mv.visitMaxs(2, 3);
		mv.visitEnd();
	}

	private boolean invokestaticm(MethodVisitor mv, String desc) {
		if (desc.equals(Type.INT_TYPE.getDescriptor())) {
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
			return true;
		} else if (desc.equals(Type.LONG_TYPE.getDescriptor())) {
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
			return true;
		} else if (desc.equals(Type.SHORT_TYPE.getDescriptor())) {
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
			return true;
		} else if (desc.equals(Type.BOOLEAN_TYPE.getDescriptor())) {
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
			return true;
		}
		return false;
	}

	private void buildMethodWrite2VO() {
		mv = cw.visitMethod(ACC_PUBLIC, "write2VO", DataConstant.READFROMVO_DESC, null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitTypeInsn(CHECKCAST, dbObjInfo.getName());
		mv.visitVarInsn(ASTORE, 2);
		Label l1 = new Label();
		mv.visitLabel(l1);
		List<DataObjField> fields = this.dbObjInfo.getDbObjFields();
		for (int i = 0; i < fields.size(); i++) {
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ALOAD, 0);
			DataObjField dbf = fields.get(i);
			mv.visitFieldInsn(GETFIELD, dbObjInfo.getDynName(), dbf.getName(), dbf.getDesc());
			mv.visitMethodInsn(INVOKEVIRTUAL, dbObjInfo.getName(), "set" + dbf.getMethodName(), "(" + dbf.getDesc()
					+ ")V", false);
		}

		Label l4 = new Label();
		mv.visitLabel(l4);
		mv.visitInsn(RETURN);
		Label l5 = new Label();
		mv.visitLabel(l5);
		mv.visitLocalVariable("this", this.dbObjInfo.getDynDesc(), null, l0, l5, 0);
		mv.visitLocalVariable("voIn", DataConstant.IVO_DESCRIPTOR, null, l0, l5, 1);
		mv.visitLocalVariable("vo", this.dbObjInfo.getDesc(), null, l1, l5, 2);
		mv.visitMaxs(1, 1);
		mv.visitEnd();
	}

	private void checkcast(MethodVisitor mv, DataObjField dbObjField) {
		String desc = dbObjField.getDesc();
		if (desc.equals(Type.INT_TYPE.getDescriptor())) {
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
		} else if (desc.equals(Type.LONG_TYPE.getDescriptor())) {
			mv.visitTypeInsn(CHECKCAST, "java/lang/Long");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false);
		} else if (desc.equals(Type.SHORT_TYPE.getDescriptor())) {
			mv.visitTypeInsn(CHECKCAST, "java/lang/Short");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S", false);
		} else if (desc.equals(Type.BOOLEAN_TYPE.getDescriptor())) {
			mv.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
		} else {
			mv.visitTypeInsn(CHECKCAST, Type.getType(desc).getInternalName());
		}
	}

	private void buildMethodwrite2Dataholder() {
		mv = cw.visitMethod(ACC_PUBLIC, "write2Dataholder", DataConstant.WRITE2DATAHOLDER_DESC, null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		for (int i = 0; i < this.dbObjInfo.getDbObjFields().size(); i++) {
			DataObjField dataObjField = this.dbObjInfo.getDbObjFields().get(i);
			Label l1 = new Label();
			mv.visitLabel(l1);
			if (dataObjField.getSerDeSerClass() != null) {
				mv.visitLdcInsn(dataObjField.getSerDeSerClass().getClassName());
				mv.visitMethodInsn(INVOKESTATIC, DataConstant.SERDSERFACOTRY_INTERNALNAME, "getISer",
						"(Ljava/lang/String;)" + DataConstant.ISER_DESC + "", false);
				mv.visitVarInsn(ALOAD, 0);
				mv.visitFieldInsn(GETFIELD, this.dbObjInfo.getDynName(), dataObjField.getName(), dataObjField.getDesc());
				this.invokestaticm(mv, dataObjField.getDesc());
				mv.visitLdcInsn(dataObjField.getName());
				mv.visitIntInsn(BIPUSH, dataObjField.getDbindex());
				mv.visitVarInsn(ALOAD, 1);
				mv.visitMethodInsn(INVOKEINTERFACE, DataConstant.ISER_INTERNALNAME, "ser",
						"(Ljava/lang/Object;Ljava/lang/String;I" + DataConstant.IDATAHOLDER_DESCRIPTOR + ")V", true);
				continue;
			}
			IDataHolderFieldRW ioBytes = dataHolderFieldRWs.get(dataObjField.getDesc());
			if (ioBytes == null) {
				throw new RuntimeException("默认未实现的类型" + this.dbObjInfo.getName() + "." + dataObjField.getName() + "-"
						+ dataObjField.getDesc() + "请自己实现ISer");
			}
			ioBytes.write(mv, dataObjField, dbObjInfo);
		}
		Label l1 = new Label();
		mv.visitLabel(l1);
		mv.visitInsn(RETURN);
		Label l3 = new Label();
		mv.visitLabel(l3);
		mv.visitLocalVariable("this", this.dbObjInfo.getDynDesc(), null, l0, l3, 0);
		mv.visitLocalVariable("dataHolder", DataConstant.IDATAHOLDER_DESCRIPTOR, null, l0, l3, 1);
		mv.visitMaxs(4, 2);
		mv.visitEnd();
	}

	private void buildMethodReadFromDataholder() {
		mv = cw.visitMethod(ACC_PUBLIC, "readFromDataholder", DataConstant.WRITE2DATAHOLDER_DESC, null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);

		for (int i = 0; i < this.dbObjInfo.getDbObjFields().size(); i++) {
			DataObjField dataObjField = this.dbObjInfo.getDbObjFields().get(i);
			Label l1 = new Label();
			mv.visitLabel(l1);
			if (dataObjField.getSerDeSerClass() != null) {
				mv.visitVarInsn(ALOAD, 0);
				mv.visitLdcInsn(dataObjField.getSerDeSerClass().getClassName());
				mv.visitMethodInsn(INVOKESTATIC, DataConstant.SERDSERFACOTRY_INTERNALNAME, "getISer",
						"(Ljava/lang/String;)" + DataConstant.ISER_DESC + "", false);
				mv.visitLdcInsn(dataObjField.getName());
				mv.visitIntInsn(BIPUSH, dataObjField.getDbindex());
				mv.visitVarInsn(ALOAD, 1);
				mv.visitMethodInsn(INVOKEINTERFACE, DataConstant.ISER_INTERNALNAME, "deSer", "(Ljava/lang/String;I"
						+ DataConstant.IDATAHOLDER_DESCRIPTOR + ")Ljava/lang/Object;", true);
				this.checkcast(mv, dataObjField);
				mv.visitFieldInsn(PUTFIELD, this.dbObjInfo.getDynName(), dataObjField.getName(), dataObjField.getDesc());
				continue;
			}
			IDataHolderFieldRW ioBytes = dataHolderFieldRWs.get(dataObjField.getDesc());
			if (ioBytes == null) {
				throw new RuntimeException("默认未实现的类型" + this.dbObjInfo.getName() + "." + dataObjField.getName() + "-"
						+ dataObjField.getDesc() + "请自己实现ISer");
			}
			ioBytes.read(mv, dataObjField, dbObjInfo);
		}
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitInsn(RETURN);
		Label l3 = new Label();
		mv.visitLabel(l3);
		mv.visitLocalVariable("this", this.dbObjInfo.getDynDesc(), null, l0, l3, 0);
		mv.visitLocalVariable("dataHoder", DataConstant.IDATAHOLDER_DESCRIPTOR, null, l0, l3, 1);
		mv.visitMaxs(5, 2);
		mv.visitEnd();
	}

	private void buildMethodClone() {
		mv = cw.visitMethod(ACC_PUBLIC, "clone", "()" + DataConstant.IVORW_DESCRIPTOR, null,
				new String[] { "java/lang/CloneNotSupportedException" });
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "clone", "()Ljava/lang/Object;", false);
		mv.visitTypeInsn(CHECKCAST, this.dbObjInfo.getDynName());
		mv.visitVarInsn(ASTORE, 1);
		Label l1 = new Label();
		mv.visitLabel(l1);
		for (int i = 0; i < this.dbObjInfo.getNeedCloneMes().size(); i++) {
			DataObjField dataObjField = this.dbObjInfo.getNeedCloneMes().get(i);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitLdcInsn(dataObjField.getCloneMeClass().getClassName());
			mv.visitMethodInsn(INVOKESTATIC, DataConstant.CLONEMEFACTORY_INTERNALNAME, "getCloneMe",
					"(Ljava/lang/String;)" + DataConstant.ICLONEME_DESC, false);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, dbObjInfo.getDynName(), dataObjField.getName(), dataObjField.getDesc());
			this.invokestaticm(mv, dataObjField.getDesc());
			mv.visitLdcInsn(dataObjField.getName());
			mv.visitIntInsn(SIPUSH, dataObjField.getDbindex());
			mv.visitMethodInsn(INVOKEINTERFACE, DataConstant.ICloneMe_INTERNALNAME, "cloneMe",
					"(Ljava/lang/Object;Ljava/lang/String;I)Ljava/lang/Object;", true);
			this.checkcast(mv, dataObjField);
			mv.visitFieldInsn(PUTFIELD, dbObjInfo.getDynName(), dataObjField.getName(), dataObjField.getDesc());
		}
		Label l3 = new Label();
		mv.visitLabel(l3);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitInsn(ARETURN);
		Label l4 = new Label();
		mv.visitLabel(l4);
		mv.visitLocalVariable("this", dbObjInfo.getDynDesc(), null, l0, l4, 0);
		mv.visitLocalVariable("po", dbObjInfo.getDynDesc(), null, l1, l4, 1);
		mv.visitMaxs(5, 2);
		mv.visitEnd();
	}

	private NclassLoad nl = new NclassLoad();

	private static class NclassLoad extends ClassLoader {
		public Class define(byte[] bytes) {
			return this.defineClass(null, bytes, 0, bytes.length, null);
		}
	}

	public byte[] toClassBytes() {
		this.build();
		return this.cw.toByteArray();
	}

	public void writeClass2File() throws IOException, InstantiationException, IllegalAccessException,
			CloneNotSupportedException {
		// FileOutputStream fos;
		// Class clas = nl.define(cw.toByteArray());
		// fos = new FileOutputStream(clas.getSimpleName() + ".class");
		// byte[] bytes = cw.toByteArray();
		// fos.write(bytes);
		// fos.close();
	}

	public POWriter(Class<?> class1) throws IOException {
		VOReader voReader = new VOReader(DataConstant.API);
		ClassReader cr = new ClassReader(class1.getName());
		cr.accept(voReader, 0);
		this.dbObjInfo = voReader.getDbObjInfo();
	}

}
