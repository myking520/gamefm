package com.myking520.github.db.common;

import java.util.HashMap;
import java.util.Map;

import com.myking520.github.db.common.writer.POWriter;

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
public class VORWFacotory extends ClassLoader {
	private static VORWFacotory vorwfacotory = new VORWFacotory();
	private static Map<Class<? extends IVO>, IVORW> vorwClases = new HashMap<Class<? extends IVO>,IVORW>();

	private VORWFacotory() {

	}

	public static IVORW getVORW(Class<? extends IVO> voclaz) {
		IVORW vorw= vorwClases.get(voclaz);
		if (vorw == null) {
			try {
				POWriter pw = new POWriter(voclaz);
				byte[] bytes = pw.toClassBytes();
				Class<IVORW> claz = (Class<IVORW>) vorwfacotory.defineClass(null, bytes, 0, bytes.length);
				vorw= claz.newInstance();
				vorwClases.put(voclaz, vorw);
			} catch (Exception e) {
				throw new RuntimeException("初始失败", e);
			}
		}
		return vorw.newIVORW();
	}

	/**
	 * 读写对象
	 * 
	 * @param vo
	 * @return
	 */
	public static IVORW getVORW(IVO vo) {
		Class<IVO> voclaz = (Class<IVO>) vo.getClass();
		return getVORW(voclaz);
	}
}
