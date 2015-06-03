package com.myking520.github.db.jdop.redis;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.myking520.github.db.annotations.CObj;
import com.myking520.github.db.jdop.IDO;
import com.myking520.github.db.jdop.impl.DefaultData;
import com.myking520.github.db.jdop.redis.impl.RedisDBClient;

public class RedisDBClientTest {
	private RedisDBClient client=new RedisDBClient();
	private static RedisTableInfo table1=null;
	@BeforeClass
	public static void init(){
		JedisUtil.Init("127.0.0.1", 6379, null);
		table1=new RedisTableInfo();
		table1.setFkNames(new String[]{"fk1","fk2"});
		table1.setTableName("tbTest");
		table1.setDoCreater(new RedisDO());
		
	}
	@Test
	public void save(){
		client.save(this.random(10));
	}
	@Test
	public void findByPK(){
		DefaultData defaultdata=new DefaultData(table1,null);
		Object obj=client.findByPK(1, defaultdata);
		System.out.println(obj);
	}
	@Test
	public void findByFK(){
		DefaultData defaultdata=new DefaultData(table1,null);
		List<CObj> lt=client.findByFK(defaultdata, 2, "fk2");
		System.out.println(lt);
	}
	@Test
	public void getAll(){
		DefaultData defaultdata=new DefaultData(table1,null);
		List<CObj> lt=client.getAll(defaultdata);
		System.out.println(lt);
	}
	@Test
	public void delete(){
		DefaultData defaultdata=new DefaultData(table1,null);
		List<CObj> lt=client.getAll(defaultdata);
		IDO ido=defaultdata.getTableInfo().getIDOCreater();
		System.out.println(lt.get(0).getId());
		ido.setSouce(lt.get(0));
		 defaultdata=new DefaultData(table1	,ido);
		client.delete(defaultdata);
	}
	private DefaultData[] random(int num){
	
		DefaultData[] defaultdatas=new DefaultData[num];
		for(int i=0;i<num;i++){
			IDO ido=	table1.getIDOCreater().newDO();
			CObj cobj=new CObj();
			cobj.setFid(1);
			cobj.setMid(2);
			cobj.setName("c1");
			cobj.setId(i);
			ido.setSouce(cobj);
			defaultdatas[i]=new DefaultData(table1,ido);
		}
		return defaultdatas;
	}
}
