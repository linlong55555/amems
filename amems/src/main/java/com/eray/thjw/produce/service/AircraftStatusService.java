package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.AircraftinfoStatus;
import com.eray.thjw.produce.po.Workorder;

/**
 * 
 * @Description 飞机监控service
 * @CreateTime 2017-10-23 下午2:06:34
 * @CreateBy 孙霁
 */
public interface AircraftStatusService{


	/**
	 * 
	 * @Description 查询飞机状态
	 * @CreateTime 2017-10-23 下午2:12:04
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @return
	 */
	public Aircraftinfo selectBystatus(Aircraftinfo aircraftinfo);
	
	/**
	 * 
	 * @Description 查询飞机状态主数据
	 * @CreateTime 2017-10-23 下午2:23:14
	 * @CreateBy 孙霁
	 * @param aircraftinfoStatus
	 * @return
	 */
	public List<AircraftinfoStatus> queryAll(AircraftinfoStatus aircraftinfoStatus);
	/**
	 * 
	 * @Description 查询未关闭的适航性资料
	 * @CreateTime 2017-10-23 下午2:23:14
	 * @CreateBy 孙霁
	 * @param aircraftinfoStatus
	 * @return
	 */
	public List<Map<String, Object>> queryAllAir(Aircraftinfo aircraftinfo);
	
	/**
	 * 
	 * @Description 加载执行历史
	 * @CreateTime 2017-10-27 下午2:55:08
	 * @CreateBy 孙霁
	 * @param workorder
	 * @return
	 */
	public List<Workorder> queryAllPerformHistory(Workorder workorder);
	
	/**
	 * @Description 装机清单生效时保存飞机状态数据，调用该方法前需要验证是否为一级部件
	 * @CreateTime 2017年10月30日 下午1:46:53
	 * @CreateBy 徐勇
	 * @param list 飞机状态数据
	 */
	public void save4installComponent(List<AircraftinfoStatus> list);
	
	public void removeByZjqdid(String zjqdid);
}
