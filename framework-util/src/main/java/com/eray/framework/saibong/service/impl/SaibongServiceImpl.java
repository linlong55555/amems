package com.eray.framework.saibong.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.framework.saibong.dao.SaibongRuleMapper;
import com.eray.framework.saibong.dao.SaibongSnMapper;
import com.eray.framework.saibong.po.SaibongRule;
import com.eray.framework.saibong.po.SaibongSn;
import com.eray.framework.saibong.service.SaibongService;

@Service
public class SaibongServiceImpl implements SaibongService {

	@Resource
	private SaibongRuleMapper saibongRuleMapper;
	
	@Resource
	private SaibongSnMapper saibongSnMapper;

	/**
     * @Description 查询采番规则
     * @CreateTime 2018-1-10 下午4:00:52
     * @CreateBy 刘兵
     * @param dprtcode 机构代码
     * @param cfkey 采番key
     * @return SaibongRule 采番规则
     */
	public SaibongRule getSaibongRuleByDprtAndKey(String dprtcode, String cfkey) {
		return saibongRuleMapper.getSaibongRuleByDprtAndKey(dprtcode, cfkey);
	}
	
	/**
     * @Description 查询采番流水
     * @CreateTime 2018-1-10 下午4:00:52
     * @CreateBy 刘兵
     * @param dprtcode 机构代码
     * @param cfkey 采番key
     * @param djh djh
     * @return SaibongSn 采番流水
     */
	public SaibongSn getSaibongSnByDprtAndKeyAndDjh(String dprtcode, String cfkey, String djh) {
		return saibongSnMapper.getSaibongSnByDprtAndKeyAndDjh(dprtcode, cfkey, djh);
	}
	
	/**
     * @Description 保存采番流水
     * @CreateTime 2018-1-11 上午9:35:47
     * @CreateBy 刘兵
     * @param record 采番流水
     */
	public void save(SaibongSn record) {
		saibongSnMapper.insertSelective(record);
	}
	
	/**
     * @Description 修改当前流水号
     * @CreateTime 2018-1-11 上午9:35:47
     * @CreateBy 刘兵
     * @param record 采番流水
     */
	public void updateDqlsh(SaibongSn record) {
		saibongSnMapper.updateDqlsh(record);
	}
	
	/**
	 * @Description 查询所有组织机构的采番规则，无规则则使用通用规则代替
	 * @CreateTime 2018年1月18日 下午3:18:15
	 * @CreateBy 徐勇
	 * @return 所有组织机构的采番规则
	 */
	public List<SaibongRule> queryAllDeptSaibongRules(){
		return this.saibongRuleMapper.selectAllDeptSaibongRules();
	}
	
}
