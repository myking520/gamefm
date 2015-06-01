package com.myking520.github.db;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.Test;

import com.myking520.github.db.jdop.IDO;
import com.myking520.github.db.jdop.redis.JedisUtil;
import com.myking520.github.db.jdop.redis.RedisTBOP;
import com.myking520.github.db.jdop.redis.impl.IntMaxId;

public class ResisTBOPTest {
	@Test
	public void save() {
		JedisUtil.Init("127.0.0.1", 6379, null);
		RedisTBOP tbpo = new RedisTBOP();
		tbpo.setTableName("test");
		tbpo.setFkNames(new String[] { "a", "b", "c" });
		tbpo.setMaxidProcess(new IntMaxId());
		DO d = new DO();
		tbpo.save(d);
		IDO ido=tbpo.findByPK(100);		
		System.out.println(ido);
	}

	public static class DO implements IDO ,Serializable {
		private String name = "bbbbbbb";

		@Override
		public Serializable getPrimaryKey() {
			return 100;
		}

		@Override
		public Object[] getFKs() {
			return new Object[] { 1, 2, 3 };
		}

		@Override
		public byte[] getData() {
			try {
				ByteArrayOutputStream bo = new ByteArrayOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(bo);
				out.writeObject(this);
				return bo.toByteArray();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}
}
