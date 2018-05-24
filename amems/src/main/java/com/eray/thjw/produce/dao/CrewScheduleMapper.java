package com.eray.thjw.produce.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.produce.po.CrewSchedule;
import com.eray.thjw.productionplan.po.PlaneData;

public interface CrewScheduleMapper {

	List<CrewSchedule> queryAllPageList(CrewSchedule crewSchedule) throws Exception;

	CrewSchedule queryCrewScheduleById(String id) throws Exception;

	void updateCrewScheduleById(CrewSchedule crewSchedule) throws Exception;

	/**
	 * 查询飞机排班数据
	 * 
	 * @param pd
	 * @return
	 */
	List <PlaneData> queryPlaneScheduling(PlaneData pd);
	
	/**
	 * 保存飞机排班数据
	 * @param crewSchedule
	 * @return
	 */
	int savePlaneScheduling(CrewSchedule crewSchedule);
	
	/**
	 * 取消飞机排班数据
	 * @param crewSchedule
	 * @return
	 */
	int cancelPlaneScheduling(List<String> ids);
	
	/**
	 * 加载飞机排班数据
	 * @param crewSchedule
	 * @return
	 */
	List<CrewSchedule> loadPlaneScheduling(CrewSchedule crewSchedule);
	
	/**
	 * 查询飞机排班数据-飞机视图
	 * @param paramMap
	 * @return
	 */
	PlaneData queryPlaneSchedulingInPlaneView(Map<String, Object> paramMap);
	
	/**
	 * 获取要取消的飞机排班数据
	 * @param crewSchedule
	 * @return
	 */
	List<String> getCancelPlaneScheduling(CrewSchedule crewSchedule);
	/**
	 * 查询该天是否排班
	 * @param crewSchedule
	 * @return
	 */
	int selectByPbrq(CrewSchedule crewSchedule);
	/**
	 * 作废
	 * @param crewSchedule
	 */
	void deleteById(String id);
}