package com.eray.framework.saibong;

import com.eray.framework.exception.SaibongException;
import com.eray.framework.redis.JedisFactory;

/**
 * @Description 采番工厂
 * @CreateTime 2018-1-8 上午9:54:19
 * @CreateBy 刘兵
 */
public class SNGeneratorFactory{
	
	/**
	 * @Description 获取执行采番的对象
	 * @CreateTime 2018-1-8 上午9:56:21
	 * @CreateBy 刘兵
	 * @param entity 参数对象
	 * @return Generator 执行采番的对象
	 * @throws Exception 
	 */
	public static String generate(String dprtcode, String key) throws SaibongException{
		return generate(dprtcode, key, null);
	}
		
	public static String generate(String dprtcode, String key, Object entity) throws SaibongException{
		try{
			return creator(entity).generateSN(dprtcode, key);
		}catch (Exception e) {
			throw new SaibongException(e);
		}
	}
	
	/**
	 * @Description 获取执行采番的对象
	 * @CreateTime 2018-1-8 上午9:56:21
	 * @CreateBy 刘兵
	 * @param entity 参数对象
	 * @return Generator 执行采番的对象
	 */
	private static SNGenerator creator(Object entity){
		if(JedisFactory.isUsed()){
			return new SNGeneratorByRedis(entity);
		}else{
			return new SNGeneratorByRDS(entity);
		}
	}
	
	public static void loadRules() throws Exception{
		creator(null).loadRules();
	}
	
}
