package com.eray.framework.saibong;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.eray.framework.common.SysContextFactory;
import com.eray.framework.enu.HexEnum;
import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.po.SaibongRule;
import com.eray.framework.saibong.po.SaibongSn;
import com.eray.framework.saibong.service.SaibongService;
import com.eray.framework.util.FormatUtils;

/**
 * @Description 执行采番的对象RDS
 * @CreateTime 2018-1-8 上午9:58:40
 * @CreateBy 刘兵
 */
public class SNGeneratorByRDS extends SNGenerator {//依赖RDS的番号生成器{
	
	SNGeneratorByRDS(Object entity){
		super(entity);
	}
	
	/**
	 * @Description 获取番号 参照SNGeneratorByRedis的逻辑实现，区别：不是jedis而是jdbc
	 * @CreateTime 2018-1-8 上午9:50:55
	 * @CreateBy 刘兵
	 * @return 番号
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String generateSN(String dprtcode, String key) throws SaibongException{
		if(StringUtils.isBlank(dprtcode) || StringUtils.isBlank(key)) {
			throw new SaibongException("采番异常，参数错误");
		}
		SaibongService saibongService = SysContextFactory.getBean(SaibongService.class);
		SaibongRule saibongRule = saibongService.getSaibongRuleByDprtAndKey(dprtcode, key);
		if(null == saibongRule){
			saibongRule = saibongService.getSaibongRuleByDprtAndKey("-1", key);
		}
		//采番规则解析
		Map<String, Object> ruleMap = super.analysisRule(saibongRule.getGznr());
		if(ruleMap == null){
			throw new SaibongException("采番异常，无采蕃规则");
		}
		//判断snno对应的流水值是否为空,为空默认key+"："
		String snno = StringUtils.isNotBlank((String)ruleMap.get("snno"))?ruleMap.get("snno").toString():key+":";
		SaibongSn saibongSn = saibongService.getSaibongSnByDprtAndKeyAndDjh(dprtcode, key, snno);
		int sn_num = 1;//当前流水号
		String sn_str = null;//流水号字符串
		//获取流水号长度
		int len = ((Map<String, Object>)ruleMap.get("numbertype")).get("len") != null?FormatUtils.ObjectToInt(((Map<String, Object>)ruleMap.get("numbertype")).get("len")):5;
		//规则下已存在采番，递增采番
		if(null != saibongSn){
			sn_num = saibongSn.getDqlsh() + 1;
			//流水号哈希类型转换
			sn_str = HexEnum.H.toString().equalsIgnoreCase((String)((Map<String, Object>) ruleMap.get("numbertype")).get("hex"))?Integer.toHexString(sn_num):sn_num + "";
			if(sn_str.length() > len){
				sn_num = 1;
				sn_str = "1";
			}
			saibongSn.setDqlsh(sn_num);
			saibongService.updateDqlsh(saibongSn);
		}else{
			sn_str = "1";
			//新增采番流水
			saibongSn = new SaibongSn();
			saibongSn.setCfkey(key);
			saibongSn.setDprtcode(dprtcode);
			saibongSn.setDjh(snno);
			saibongSn.setDqlsh(sn_num);
			saibongSn.setFssj(new Date());
			saibongService.save(saibongSn);
		}
		//使用ruleMap.get("numbertype").get("fill")和填补位置ruleMap.get("numbertype").get("posi")，来补充字符串
		String fill = (String)((Map<String, Object>)ruleMap.get("numbertype")).get("fill");
		String posi = (String)((Map<String, Object>)ruleMap.get("numbertype")).get("posi");
		ruleMap.put("sn_str", format(sn_str, len, fill, posi));
		return super.appendFh(ruleMap);
	}

	/**
	 * @Description 加载采番规则
	 * @CreateTime 2018年1月18日 上午11:31:51
	 * @CreateBy 徐勇
	 */
	@Override
	void loadRules() {
		// RDS无需实现
	}
	
	
}
