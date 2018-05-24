/**
 * Redis Pool 连接池
 */
package jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Description
 * @CreateTime 2017年12月21日 上午9:47:47
 * @CreateBy 胡黄驰
 */
public class RedisPool {
	private static RedisPool instance;
	private static JedisPool jedisPool;

	/* redis ip */
	private final static String STR_REDIS_IP_VALUE = "27.17.59.98";
	/* redis port */
	private final static int INT_REDIS_PORT_VALUE = 6480;
	/* redis auth */
	private final static String STR_REDIS_AUTH_VALUE = "erayredis";

	private RedisPool() {
		GenericObjectPoolConfig jedisPoolConfig = new JedisPoolConfig().getPoolConfig();
		jedisPool = new JedisPool(jedisPoolConfig, STR_REDIS_IP_VALUE, INT_REDIS_PORT_VALUE);
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
		jedis.auth(STR_REDIS_AUTH_VALUE);
		return jedis;
	}
}
