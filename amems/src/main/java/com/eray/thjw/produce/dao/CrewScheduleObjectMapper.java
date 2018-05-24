package com.eray.thjw.produce.dao;

import java.util.List;

import com.eray.thjw.produce.po.CrewSchedule;
import com.eray.thjw.produce.po.CrewScheduleObject;

public interface CrewScheduleObjectMapper {

	List<String> selectIdByMainid(CrewScheduleObject crewScheduleObject) throws Exception;

	void deleteByMainid(CrewScheduleObject crewScheduleObject) throws Exception;

	void updatecrewshecduleObject(CrewScheduleObject crewScheduleObject) throws Exception;

	void updateZtByMainid(CrewScheduleObject crewScheduleObject) throws Exception;

	
	/**
	 * 保存飞机排班数据
	 * @param crewScheduleObject
	 */
	void savePlaneScheduling(CrewScheduleObject crewScheduleObject);
	
	/**
	 * 取消飞机排班数据
	 * @param crewSchedule
	 */
	void cancelPlaneScheduling(List<String> ids);
	
	/**
	 * 获取要取消的飞机排班数据
	 * @param crewSchedule
	 * @return
	 */
	List<String> getCancelPlaneScheduling(CrewSchedule crewSchedule);
	
	int getIdByMainid(CrewScheduleObject crewScheduleObject);
	
	List<String> getIdByZt(CrewScheduleObject crewScheduleObject);
	
	void updateZtByCrewScheduleObject(CrewScheduleObject crewScheduleObject);
	
	void deleteByZt(CrewScheduleObject crewScheduleObject);
	
	void updateZt(CrewScheduleObject crewScheduleObject);
	
	void deleteById(String id);
}