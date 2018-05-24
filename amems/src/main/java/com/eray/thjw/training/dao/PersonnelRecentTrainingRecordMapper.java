package com.eray.thjw.training.dao;

import java.util.List;

import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.training.po.PersonnelRecentTrainingRecord;
import com.eray.thjw.training.po.PlanPerson;

public interface PersonnelRecentTrainingRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonnelRecentTrainingRecord record);

    int insertSelective(PersonnelRecentTrainingRecord record);

    PersonnelRecentTrainingRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonnelRecentTrainingRecord record);

    int updateByPrimaryKey(PersonnelRecentTrainingRecord record);


	void delete(String kcid, String wxrydaid);

	/**
	 * 根据课程id，计划id，维修人员id查询人员最近培训记录
	 * @param kcid
	 * @param wxrydaid
	 * @param pxjhid
	 * @return
	 */
	PersonnelRecentTrainingRecord selectByPrimary(String kcid, String wxrydaid,String pxjhid);

	PersonnelRecentTrainingRecord selectBykcwxr(String kcid, String wxrydaid);


	void deletel(PersonnelRecentTrainingRecord personnelRecentTrainingRecord);

	void deleteNotExist(
			PersonnelRecentTrainingRecord personnelRecentTrainingRecord);

	List<PersonnelRecentTrainingRecord> selectList(String id, String dprtcode);
}