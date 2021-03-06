package com.myking520.github.db.common.cloneme;

import java.util.HashMap;
import java.util.Map;

import com.myking520.github.db.common.column.ICloneMe;

/**
 * 字段克隆<p>
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
public class CloneMeFactory {
	private CloneMeFactory() {

	}

	private static Map<String, ICloneMe> clonefs = new HashMap<String, ICloneMe>();

	/**
	 * 添加到clone工厂
	 * 
	 * @param cloneMe
	 */
	public static void addCloneMe(ICloneMe cloneMe) {
		clonefs.put(cloneMe.getClass().getName(), cloneMe);
	}

	public static ICloneMe getCloneMe(String cloneMe) {
		ICloneMe cm = clonefs.get(cloneMe);
		if (cm == null) {
			try {
				cm = (ICloneMe) Class.forName(cloneMe).newInstance();
				clonefs.put(cloneMe, cm);
				return cm;
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("没有找到cloneMe->" + cloneMe);
			}
		}
		return cm;
	}

}
