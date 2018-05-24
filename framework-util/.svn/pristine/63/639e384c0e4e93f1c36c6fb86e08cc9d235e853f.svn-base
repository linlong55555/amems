package com.eray.framework.redis;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.eray.framework.exception.CustomRedisException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * @Description jedis 操作集合
 * @CreateTime 2017年12月21日 上午11:29:00
 * @CreateBy 胡黄驰
 */
public class JedisOperations {
	
	/**
	 * @Description 获取jedis连接
	 * @CreateTime 2018年1月16日 上午9:50:11
	 * @CreateBy 徐勇
	 * @return
	 * @throws CustomRedisException
	 */
	private Jedis getJedis() throws CustomRedisException{
		try{
			return RedisPool.getInstance().getJedisResourceWithAuth();
		}catch (Exception e) {
			throw new CustomRedisException("Redis连接获取失败", e);
		}
	}
	
	/**
	 * @Description 关闭jedis连接，将连接释放到连接池
	 * @CreateTime 2018年1月16日 上午9:49:59
	 * @CreateBy 徐勇
	 * @param jedis
	 */
	private void closeJedis(Jedis jedis){
		if(jedis != null){
			jedis.close();
		}
	}
	
	/**
	 * @Description 根据key+field为数据增加增量
	 * @CreateTime 2018-1-5 下午4:36:19
	 * @CreateBy 刘兵
	 * @param key 键名
	 * @param field 
	 * @param value 增量
	 * @return result 结果
	 */
	public long hincrBy(final String key, String field, long value) throws CustomRedisException{
		if(StringUtils.isBlank(key) || StringUtils.isBlank(field)){
			throw new CustomRedisException("设置参数错误");
		}
		Jedis jedis = getJedis();
		try{
			return jedis.hincrBy(key, field, value);
		}catch (Exception e) {
			throw new CustomRedisException(e);
		}finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * @Description 根据key+field设置Hash的值
	 * @CreateTime 2018-1-5 下午4:36:19
	 * @CreateBy 刘兵
	 * @param key 键名
	 * @param field 
	 * @param value 
	 * @return result 结果
	 * @throws CustomRedisException 
	 */
	public long hset(final String key, String field, String value) throws CustomRedisException{
		if(StringUtils.isBlank(key) || StringUtils.isBlank(field)){
			throw new CustomRedisException("设置参数错误");
		}
		Jedis jedis = getJedis();
		try{
			return jedis.hset(key, field, value);
		}catch (Exception e) {
			throw new CustomRedisException(e);
		}finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * @Description 批量设置hash
	 * @CreateTime 2018年1月18日 下午1:55:48
	 * @CreateBy 徐勇
	 * @param key
	 * @param map
	 * @throws CustomRedisException
	 */
	public void hmset(final String key, Map<String, String> map) throws CustomRedisException{
		if(StringUtils.isBlank(key) || map == null || map.isEmpty()){
			throw new CustomRedisException("设置参数错误");
		}
		Jedis jedis = getJedis();
		try{
			Pipeline pipeline = jedis.pipelined();
			pipeline.hmset(key, map);
			pipeline.sync();
		}catch (Exception e) {
			throw new CustomRedisException(e);
		}finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * @Description 获取指定数据
	 * @CreateTime 2018-1-5 下午2:33:10
	 * @CreateBy 刘兵
	 * @param key 键名
	 * @param field 
	 * @return String
	 * @throws CustomRedisException 
	 */
	public String hget(final String key, String field) throws CustomRedisException{
		if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
			return null;
		}
		Jedis jedis = getJedis();
		try{
			return jedis.hget(key, field);
		}catch (Exception e) {
			throw new CustomRedisException(e);
		}finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * @Description 搜索具有匹配模式的Key
	 * @CreateTime 2018年1月18日 下午4:22:45
	 * @CreateBy 徐勇
	 * @param pattern 符合redis的匹配规范字符串
	 * @return
	 * @throws CustomRedisException
	 */
	public Set<String> keys(String pattern) throws CustomRedisException{
		if (StringUtils.isBlank(pattern) ) {
			return null;
		}
		Jedis jedis = getJedis();
		try{
			return jedis.keys(pattern);
		}catch (Exception e) {
			throw new CustomRedisException(e);
		}finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * @Description 根据字符串搜索符合右匹配的Key
	 * @CreateTime 2018年1月18日 下午4:23:24
	 * @CreateBy 徐勇
	 * @param str 待右匹配的字符串 
	 * @return
	 * @throws CustomRedisException
	 */
	public Set<String> keysRLike(String str) throws CustomRedisException{
		if (StringUtils.isBlank(str) ) {
			return null;
		}
		return keys(str+"*");
	}
	
	/**
	 * @Description 批量删除redis key
	 * @CreateTime 2018年1月18日 下午4:31:17
	 * @CreateBy 徐勇
	 * @param keys
	 * @throws CustomRedisException
	 */
	public void mdel(final String ... keys) throws CustomRedisException{
		if(keys == null || keys.length == 0){
			return;
		}
		Jedis jedis = getJedis();
		try{
			Pipeline pipeline = jedis.pipelined();
			pipeline.del(keys);
			pipeline.sync();
		}catch (Exception e) {
			throw new CustomRedisException(e);
		}finally {
			closeJedis(jedis);
		}
	}
	
}
