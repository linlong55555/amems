package com.eray.framework.saibong;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.eray.framework.common.SysContextFactory;
import com.eray.framework.enu.HexEnum;
import com.eray.framework.exception.CustomRedisException;
import com.eray.framework.exception.SaibongException;
import com.eray.framework.redis.JedisFactory;
import com.eray.framework.redis.JedisOperations;
import com.eray.framework.saibong.po.SaibongRule;
import com.eray.framework.saibong.service.SaibongService;
import com.eray.framework.util.FormatUtils;

/**
 * @Description 执行采番的对象Redis
 * @CreateTime 2018-1-8 上午9:58:47
 * @CreateBy 刘兵
 */
public class SNGeneratorByRedis extends SNGenerator {//依赖redis的番号生成器
	
	SNGeneratorByRedis(Object entity){
		super(entity);
	}
	
	/**
	 * @Description 获取番号
	 * @CreateTime 2018-1-8 上午11:49:49
	 * @CreateBy 刘兵
	 * @param dprtcode 组织机构代码
	 * @param key 采蕃Key
	 * @return String 番号
	 * @throws CustomRedisException 
	 * @throws SaibongException 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String generateSN(String dprtcode, String key) throws CustomRedisException, SaibongException{//生成采番号
		if(StringUtils.isBlank(dprtcode) || StringUtils.isBlank(key)) {
			throw new SaibongException("采番异常，参数错误");
		}
		JedisOperations jeOper = JedisFactory.getInstance();
		String redisKey = this.getRedisRuleKey(dprtcode);
		//执行R命令：hget key field;//key："SNR" + ":" + 机构代码    field：采番key
		String rule = jeOper.hget(redisKey, key);
		if(StringUtils.isBlank(rule)){
			redisKey = this.getRedisRuleKey("-1");
			rule = jeOper.hget(redisKey, key);
		}
		//采番规则解析
		Map<String, Object> ruleMap = super.analysisRule(rule);
		if(ruleMap == null){
			throw new SaibongException("采番异常，无采蕃规则");
		}
		redisKey = this.getRedisSNKey(dprtcode, key);
		//判断snno对应的流水值是否为空
		String snno = StringUtils.isNotBlank((String)ruleMap.get("snno"))?ruleMap.get("snno").toString():key+":";
		//执行R命令:hincrby key field 1;//key："SN" + ":" + 机构代码 + ":" + 采番key   field：ruleMap.get("snno"),snno对应的流水值+1
		int sn_num = (int)jeOper.hincrBy(redisKey, snno, 1);
		//流水号哈希类型转换
		String sn_str = HexEnum.H.toString().equalsIgnoreCase((String)((Map<String, Object>) ruleMap.get("numbertype")).get("hex"))?Integer.toHexString(sn_num):sn_num + "";
		//获取流水号长度
		int len = ((Map<String, Object>)ruleMap.get("numbertype")).get("len") != null?FormatUtils.ObjectToInt(((Map<String, Object>)ruleMap.get("numbertype")).get("len")):5;
		if(sn_str.length() > len){
			//执行R命令重置为1
			sn_str = "1";
			jeOper.hset(redisKey, snno, sn_str);
		}
		//使用ruleMap.get("numbertype").get("fill")和填补位置ruleMap.get("numbertype").get("posi")，来补充字符串
		String fill = (String)((Map<String, Object>)ruleMap.get("numbertype")).get("fill");
		String posi = (String)((Map<String, Object>)ruleMap.get("numbertype")).get("posi");
		ruleMap.put("sn_str", format(sn_str, len, fill, posi));
		return super.appendFh(ruleMap);
	}
	
	/**
	 * @Description 获取采番规则redis key值
	 * @CreateTime 2018年1月18日 上午11:28:32
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @return 采番规则redis key值
	 */
	private String getRedisRuleKey(String dprtcode){
		return new StringBuffer("SNR:").append(dprtcode).toString();
	}
	
	/**
	 * @Description 获取采番当前流水redis key值
	 * @CreateTime 2018年1月18日 上午11:29:58
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param key 采番key值
	 * @return 采番当前流水redis key值
	 */
	private String getRedisSNKey(String dprtcode, String key){
		return new StringBuffer("SN:").append(dprtcode).append(":").append(key).toString();
	}

	/**
	 * @throws CustomRedisException 
	 * @Description 加载采番规则
	 * @CreateTime 2018年1月18日 上午11:31:51
	 * @CreateBy 徐勇
	 */
	@Override
	void loadRules() throws CustomRedisException {
		SaibongService saibongService = SysContextFactory.getBean(SaibongService.class);
		List<SaibongRule> saibongRuleList = saibongService.queryAllDeptSaibongRules();
		//清空所有采番
		Set<String> keys = JedisFactory.getInstance().keysRLike(getRedisRuleKey(""));
		if(keys != null && !keys.isEmpty()){
			JedisFactory.getInstance().mdel(keys.toArray(new String[0]));
		}
		
		if(saibongRuleList == null || saibongRuleList.isEmpty()){
			return;
		}
		String dprtcode = "";
		Map<String, String> map = new HashMap<String, String>();
		for (SaibongRule saibongRule : saibongRuleList) {
			if(dprtcode.equals(saibongRule.getDprtcode())){
				map.put(saibongRule.getCfkey(), saibongRule.getGznr());
			}else{
				if(!"".equals(dprtcode)){
					JedisFactory.getInstance().hmset(getRedisRuleKey(dprtcode), map);
				}
				dprtcode = saibongRule.getDprtcode();
				map.clear();
				map.put(saibongRule.getCfkey(), saibongRule.getGznr());
			}
		}
		if(!"".equals(dprtcode)){
			JedisFactory.getInstance().hmset(getRedisRuleKey(dprtcode), map);
		}
	}
	
}
