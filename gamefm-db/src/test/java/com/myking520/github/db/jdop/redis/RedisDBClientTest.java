package com.myking520.github.db.jdop.redis;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.myking520.github.db.annotations.CObj;
import com.myking520.github.db.jdop.IDO;
import com.myking520.github.db.jdop.impl.DefaultData;
import com.myking520.github.db.jdop.redis.impl.RedisDBClient;

/**
 * Copyright (c) 2015, kongguoan All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
public class RedisDBClientTest {
	private RedisDBClient client = new RedisDBClient();
	private static RedisTableInfo table1 = null;

	@BeforeClass
	public static void init() {
		JedisUtil.Init("127.0.0.1", 6379, null);
		table1 = new RedisTableInfo();
		table1.setFkNames(new String[] { "fk1", "fk2" });
		table1.setTableName("tbTest");
		table1.setDoCreater(new RedisDO());

	}

	@Test
	public void save() {
		client.save(this.random(10));
	}

	@Test
	public void findByPK() {
		DefaultData defaultdata = new DefaultData(table1, null);
		Object obj = client.findByPK(1, defaultdata);
		System.out.println(obj);
	}

	@Test
	public void findByFK() {
		DefaultData defaultdata = new DefaultData(table1, null);
		List<CObj> lt = client.findByFK(defaultdata, 2, "fk2");
		System.out.println(lt);
	}

	@Test
	public void getAll() {
		DefaultData defaultdata = new DefaultData(table1, null);
		List<CObj> lt = client.getAll(defaultdata);
		System.out.println(lt);
	}

	@Test
	public void delete() {
		DefaultData defaultdata = new DefaultData(table1, null);
		List<CObj> lt = client.getAll(defaultdata);
		IDO ido = defaultdata.getTableInfo().getIDOCreater();
		ido.setSouce(lt.get(0));
		defaultdata = new DefaultData(table1, ido);
		client.delete(defaultdata);
	}

	@Test
	public void updateFK() {
		DefaultData defaultdata = new DefaultData(table1, null);
		List<CObj> lt = client.getAll(defaultdata);
		CObj cobj = lt.get(0);
		int old = cobj.getMid();
		cobj.setMid(33333);
		IDO ido = defaultdata.getTableInfo().getIDOCreater();
		ido.setSouce(cobj);
		defaultdata = new DefaultData(table1, ido);
		client.updateFK(defaultdata, "fk2", old);
	}

	private DefaultData[] random(int num) {
		DefaultData[] defaultdatas = new DefaultData[num];
		for (int i = 0; i < num; i++) {
			IDO ido = table1.getIDOCreater().newDO();
			CObj cobj = new CObj();
			cobj.setFid(1);
			cobj.setMid(2);
			cobj.setName("c1");
			cobj.setId(i);
			ido.setSouce(cobj);
			defaultdatas[i] = new DefaultData(table1, ido);
		}
		return defaultdatas;
	}
}
