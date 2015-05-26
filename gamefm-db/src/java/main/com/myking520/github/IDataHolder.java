package com.myking520.github;

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
public interface IDataHolder {
	/**
	 * @param name
	 *            字段名称
	 * @param index
	 *            索引
	 * @return 返回int值
	 */
	public int getInt(String name, int index);

	/**
	 * @param name
	 *            字段名称
	 * @param index
	 *            索引
	 * @param value
	 *            值
	 */
	public void putInt(String name, int index, int value);

	/**
	 * @param name
	 *            字段名称
	 * @param index
	 *            索引
	 * @return
	 */
	public long getLong(String name, int index);

	/**
	 * @param name
	 *            字段名称
	 * @param index
	 *            索引
	 * @param value
	 *            值
	 */
	public void putLong(String name, int index, long value);

	/**
	 * @param name
	 *            字段名称
	 * @param index
	 *            索引
	 * @return
	 */
	public String getString(String name, int index);

	/**
	 * @param name
	 *            字段名称
	 * @param index
	 *            索引
	 * @param value
	 *            值
	 */
	public void putString(String name, int index, String value);

	/**
	 * @param name
	 *            字段名称
	 * @param index
	 *            索引
	 * @param value
	 *            值
	 */
	public void putIntList(String name, int index, int[] value);

	/**
	 * @param name
	 *            字段名称
	 * @param index
	 *            索引
	 * @return
	 */
	public int[] getIntList(String name, int index);

	/**
	 * @param name
	 *            字段名称
	 * @param index
	 *            索引
	 * @param value
	 *            值
	 */
	public void putBytes(String name, int index, byte[] value);

	/**
	 * 
	 * @param name
	 *            字段名称
	 * @param index
	 *            索引
	 * @return
	 */
	public byte[] getBytes(String name, int index);
}
