package org.apache.hadoop.redis;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisAPITest {

	Jedis jedis ;
	@Before
	public void init(){
		jedis = new Jedis("192.168.199.128", 6379);
	}
	
	@Test
	public void testString(){
		byte[] value = jedis.get("foo".getBytes());
		System.out.println(new String(value));
	}
}
