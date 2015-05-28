package com.myking520.github.db.common;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.myking520.github.db.common.annotation.Column;
import com.myking520.github.db.common.cloneme.CloneMeFactory;
import com.myking520.github.db.common.column.ICloneMe;
import com.myking520.github.db.common.column.ISer;
import com.myking520.github.db.common.serDeser.SerDserFacotry;

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
public final class DataConstant {
	public final static String COLUMNDESCRIPTOR = Type.getType(Column.class).getDescriptor();
	public final static String COLUMND_SERDESERCLASS = "serDeSerClass";
	public final static String COLUMND_INDEX = "index";
	public final static String COLUMND_CLONEMECLASS = "cloneMeClass";
	public final static String IVORW_INTERNALNAME = Type.getType(IVORW.class).getInternalName();
	public final static String OBJECT_INTERNALNAME = Type.getInternalName(Object.class);
	public final static String IVO_DESCRIPTOR = Type.getDescriptor(IVO.class);
	public final static String IVORW_DESCRIPTOR = Type.getDescriptor(IVORW.class);
	public final static String IDATAHOLDER_DESCRIPTOR = Type.getDescriptor(IDataHolder.class);
	public final static String IDATAHOLDER_INTERNALNAME = Type.getInternalName(IDataHolder.class);
	public final static String READFROMVO_DESC = "(" + IVO_DESCRIPTOR + ")V";
	public final static String WRITE2DATAHOLDER_DESC = "(" + IDATAHOLDER_DESCRIPTOR + ")V";
	public final static String SERDSERFACOTRY_DESC = Type.getDescriptor(SerDserFacotry.class);
	public final static String SERDSERFACOTRY_INTERNALNAME = Type.getInternalName(SerDserFacotry.class);
	public final static String ISER_INTERNALNAME = Type.getInternalName(ISer.class);
	public final static String ISER_DESC = Type.getDescriptor(ISer.class);
	public final static String CLONEMEFACTORY_DESC = Type.getDescriptor(CloneMeFactory.class);
	public final static String CLONEMEFACTORY_INTERNALNAME = Type.getInternalName(CloneMeFactory.class);
	public final static String ICLONEME_DESC = Type.getDescriptor(ICloneMe.class);
	public final static String ICloneMe_INTERNALNAME = Type.getInternalName(ICloneMe.class);

	public final static String DYNSIGN = "kga";
	public final static int API = Opcodes.ASM5;

	private DataConstant() {
	}
}
