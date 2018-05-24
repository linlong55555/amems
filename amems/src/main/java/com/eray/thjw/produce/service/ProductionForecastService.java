package com.eray.thjw.produce.service;

import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.AircraftinfoStatus;
import com.eray.thjw.produce.po.MonitoringCurrent;
 
/**
 * 
 * @Description 计划预测服务接口
 * @CreateTime 2017年10月12日 上午9:35:51
 * @CreateBy 朱超
 */
public interface ProductionForecastService {
	
	/**
	 *  
	 * @Description 计算当前值
	 * @CreateTime 2017年10月12日 上午9:37:18
	 * @CreateBy 朱超
	 * @param aircraftinfoStatus
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> calculationCurrent(AircraftinfoStatus aircraftinfoStatus) throws BusinessException;

 
	/**
	 * 
	 * @Description 查询计划预测列表
	 * @CreateTime 2017年10月12日 下午4:08:40
	 * @CreateBy 朱超
	 * @param record
	 * @return
	 */
	public Map<String, Object> queryList(MonitoringCurrent record)throws BusinessException ;
 

}
