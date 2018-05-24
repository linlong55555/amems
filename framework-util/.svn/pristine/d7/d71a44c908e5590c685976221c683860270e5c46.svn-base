package com.eray.framework.redis;

import com.eray.framework.common.SysConfig;
import com.eray.framework.exception.CustomRedisException;

/**
 * @Description JedisOperations工厂
 * @CreateTime 2018-1-5 下午3:02:11
 * @CreateBy 刘兵
 */
public class JedisFactory{
	
	/**
	 * @Description Redis是否使用
	 * @CreateTime 2018年1月16日 上午9:44:35
	 * @CreateBy 徐勇
	 * @return
	 */
	public static boolean isUsed(){
		return SysConfig.REDIS_ENABLED;
	}
	
	private static JedisOperations jedisOperations;
	
	public static synchronized JedisOperations getInstance() throws CustomRedisException {
		if(!isUsed()){
			throw new CustomRedisException("Redis未启用");
		}
		if (jedisOperations == null) {
			jedisOperations = new JedisOperations();
		}
		return jedisOperations;
	}
	
}
