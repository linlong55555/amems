package com.eray.framework.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @Description Jedis连接池设置
 * @CreateTime 2017年12月21日 上午10:56:30
 * @CreateBy 胡黄驰
 */
public class JedisPoolConfig {
	public GenericObjectPoolConfig getPoolConfig() {
		GenericObjectPoolConfig jedisPoolConfig = new GenericObjectPoolConfig();
		// 设置最大连接数为默认值的5倍
		jedisPoolConfig.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL * 5);
		// 设置最大空闲连接数为默认值的3倍
		jedisPoolConfig.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE * 3);
		// 设置最小空闲连接数为默认值的2倍
		jedisPoolConfig.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE * 2);
		// 设置开启jmx功能
		jedisPoolConfig.setJmxEnabled(true);
		// 设置连接池没有连接后客户端的最大等待时间（单位为毫秒）
		jedisPoolConfig.setMaxWaitMillis(3000);
		
		return jedisPoolConfig;
	}
}
