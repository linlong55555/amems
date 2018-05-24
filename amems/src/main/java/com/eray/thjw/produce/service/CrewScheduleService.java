package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.produce.po.CrewSchedule;
import com.eray.thjw.productionplan.po.PlaneData;

public interface CrewScheduleService {

	public List<CrewSchedule> queryAllPageList(CrewSchedule crewSchedule) throws Exception;

	CrewSchedule queryCrewScheduleById(String id) throws Exception;

	void updateCrewScheduleById(CrewSchedule crewSchedule) throws Exception;

	/**
	 * 查询飞机排班数据
	 * 
	 * @param pd
	 * @return
	 */
	List<PlaneData> queryPlaneScheduling(PlaneData pd);
	
	/**
	 * 保存飞机排班数据
	 * @param crewScheduleList
	 */
	void savePlaneScheduling(List<CrewSchedule> crewScheduleList);
	
	/**
	 * 取消飞机排班数据
	 * @param crewSchedule
	 */
	void doCancelPlaneScheduling(CrewSchedule crewSchedule, boolean writeRec);
	
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
	
}
