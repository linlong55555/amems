package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.FlightSheet;

/**
 * 
 * @Description  飞机记录本service
 * @CreateTime 2017年10月9日 下午4:49:37
 * @CreateBy 林龙
 */
public interface FlightSheetService {
	
	/**
	 * 
	 * @Description  飞机记录本列表加载
	 * @CreateTime 2017年10月9日 下午4:49:21
	 * @CreateBy 林龙
	 * @param flightSheet 飞机记录本
	 * @return map 
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllList(FlightSheet flightSheet)throws BusinessException ;
	
	/**
	 * @Description 保存飞行记录本
	 * @CreateTime 2017年10月24日 下午3:50:18
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @return
	 * @throws BusinessException
	 */
	String doSave(FlightSheet flightSheet) throws BusinessException;
	
	/**
	 * @Description 查询飞行记录本详情
	 * @CreateTime 2017年10月26日 下午5:07:04
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @return
	 */
	FlightSheet queryDetail(FlightSheet flightSheet);
	
	/**
	 * @Description 查询飞行记录本详情（上一页、下一页）
	 * @CreateTime 2017年12月5日 下午4:19:36
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @return
	 */
	FlightSheet queryDetailWithPage(FlightSheet flightSheet);
	
	/**
	 * @Description 加载飞行前数据
	 * @CreateTime 2017年10月28日 下午12:49:50
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @return
	 */
	FlightSheet loadPreflightData(FlightSheet flightSheet);
	
	/**
	 * @Description 提交飞行记录本
	 * @CreateTime 2017年11月14日 上午10:09:49
	 * @CreateBy 徐勇
	 * @param flightSheet 飞行记录本
	 * @throws BusinessException 
	 */
	public void doSubmit(FlightSheet flightSheet) throws BusinessException;
	
	/**
	 * @Description 删除飞行记录本数据
	 * @CreateTime 2017年10月28日 下午4:51:07
	 * @CreateBy 韩武
	 * @param flightSheet
	 */
	void doDelete(FlightSheet flightSheet) throws BusinessException;
	
	/**
	 * @Description 修订飞行记录本
	 * @CreateTime 2017年11月22日 上午11:00:03
	 * @CreateBy 徐勇
	 * @param flightSheet 飞行记录本数据
	 * @throws BusinessException 
	 */
	public void doRevise(FlightSheet flightSheet) throws BusinessException;
	
	/**
	 * @Description 作废飞行记录本
	 * @CreateTime 2017年11月15日 下午1:44:29
	 * @CreateBy 徐勇
	 * @param id 飞行记录本ID
	 * @throws BusinessException 
	 */
	public void doDiscard(String id) throws BusinessException;

	public List<FlightSheet> getHbhFxrqFlightRecord(FlightSheet sheet);
}
