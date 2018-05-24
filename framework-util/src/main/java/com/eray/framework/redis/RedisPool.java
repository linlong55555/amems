package com.eray.framework.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.eray.framework.common.SysConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Description Redis Pool 连接池
 * @CreateTime 2017年12月21日 上午9:47:47
 * @CreateBy 胡黄驰
 */
public class RedisPool {
	private static RedisPool instance;
	private static JedisPool jedisPool;

	private RedisPool() {
		GenericObjectPoolConfig jedisPoolConfig = new JedisPoolConfig().getPoolConfig();
		jedisPool = new JedisPool(jedisPoolConfig, SysConfig.REDIS_IP, SysConfig.REDIS_PORT);
	}

	public static synchronized RedisPool getInstance() {
		if (instance == null) {
			instance = new RedisPool();
		}
		return instance;
	}

	public Jedis getJedisResource() {
		return jedisPool.getResource();
	}

	public Jedis getJedisResourceWithAuth() {
		return auth(jedisPool.getResource());
	}

	private Jedis auth(Jedis jedis) {
		jedis.auth(SysConfig.REDIS_AUTH);
		return jedis;
	}
}
