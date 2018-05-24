package com.eray.thjw.monitorsettings.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Monitorsettings;

public interface MonitorsettingsService {

	/**
	 * 根据组织机构查询系统阀值设置信息的实现方法
	 */
	public List<Monitorsettings> queryThresholdByDprtcode(String dprtcode) throws BusinessException;
	
	/**
	 * 系统阀值设置修改信息的实现方法
	 */
	public void update(Monitorsettings monitorsettings) throws BusinessException;
	/**
	 * 获取监控预警阈值
	 * @param key 预警编码
	 * @param dprtcode 组织机构
	 */
	public Monitorsettings getByKeyDprtcode(String key, String dprtcode) throws BusinessException ;

	/**
	 * 获取指定关键字在各个机构监控预警阈值
	 * @param key 预警编码
	 * @param dprtcodes 组织机构列表
	 * @return
	 */
	public Map<String,Monitorsettings> getByKeyInDprtcodes(String key, List<String> dprtcodes);

	/**
	 * @Description 获取适航性资料，子类型
	 * @CreateTime 2018-4-12 下午3:04:56
	 * @CreateBy 雷伟
	 * @param dprtcode
	 * @return
	 */
	public List<Monitorsettings> queryShxzlSubTypeByDprtcode(String dprtcode) throws BusinessException ;
}
