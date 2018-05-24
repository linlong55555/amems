package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToCreditRecord;

public interface PersonnelToCreditRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonnelToCreditRecord record);

    int insertSelective(PersonnelToCreditRecord record);

    PersonnelToCreditRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonnelToCreditRecord record);

    int updateByPrimaryKey(PersonnelToCreditRecord record);
    
    /**
     * 删除列表中不存在的数据
     * @param personnel
     * @return
     */
    int deleteNotExist(MaintenancePersonnel personnel);
    
    /**
     * 查询事故征候情况
     * @param id
     * @return
     */
    List<PersonnelToCreditRecord> queryIncidentSituations(String id);
    
    /**
     * 查询负有责任的不安全事件
     * @param id
     * @return
     */
    List<PersonnelToCreditRecord> queryUnsafeIncidents(String id);
    
    /**
     * 查询不诚信行为
     * @param id
     * @return
     */
    List<PersonnelToCreditRecord> queryDishonestBehaviors(String id);
    
    /**
     * @Description 根据组织机构查询事故征候情况
     * @CreateTime 2018年1月12日 下午12:00:35
     * @CreateBy 韩武
     * @param dprtcode
     * @return
     */
    List<PersonnelToCreditRecord> queryIncidentSituationsByDprtcode(String dprtcode);
    
    /**
     * @Description 根据组织机构查询负有责任的不安全事件
     * @CreateTime 2018年1月12日 下午12:00:51
     * @CreateBy 韩武
     * @param dprtcode
     * @return
     */
    List<PersonnelToCreditRecord> queryUnsafeIncidentsByDprtcode(String dprtcode);
    
    /**
     * @Description 根据组织机构查询不诚信行为
     * @CreateTime 2018年1月12日 下午12:01:12
     * @CreateBy 韩武
     * @param dprtcode
     * @return
     */
    List<PersonnelToCreditRecord> queryDishonestBehaviorsByDprtcode(String dprtcode);
}