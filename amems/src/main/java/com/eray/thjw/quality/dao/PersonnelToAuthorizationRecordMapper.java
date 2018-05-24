package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToAuthorizationRecord;

public interface PersonnelToAuthorizationRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonnelToAuthorizationRecord record);

    int insertSelective(PersonnelToAuthorizationRecord record);

    PersonnelToAuthorizationRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonnelToAuthorizationRecord record);

    int updateByPrimaryKey(PersonnelToAuthorizationRecord record);
    
    /**
     * 删除列表中不存在的数据
     * @param personnel
     * @return
     */
    int deleteNotExist(MaintenancePersonnel personnel);
    
    /**
     * 查询维修执照
     * @param id
     * @return
     */
    List<PersonnelToAuthorizationRecord> queryMaintenanceLicenses(String id);
    
    /**
     * 查询机型执照
     * @param id
     * @return
     */
    List<PersonnelToAuthorizationRecord> queryTypeLicenses(String id);
    
    /**
     * 
     * @Description 查询档案-授权记录
     * @CreateTime 2017年9月19日 下午2:09:08
     * @CreateBy 朱超
     * @param record
     * @return
     */
	List<PersonnelToAuthorizationRecord> queryList(PersonnelToAuthorizationRecord record);
	
	/**
	 * @Description 根据组织机构查询维修执照
	 * @CreateTime 2018年1月12日 上午11:27:22
	 * @CreateBy 韩武
	 * @param dprtcode
	 * @return
	 */
	List<PersonnelToAuthorizationRecord> queryMaintenanceLicensesByDprtcode(String dprtcode);
	
	/**
	 * @Description 根据组织机构查询机型执照
	 * @CreateTime 2018年1月12日 上午11:27:22
	 * @CreateBy 韩武
	 * @param dprtcode
	 * @return
	 */
	List<PersonnelToAuthorizationRecord> queryTypeLicensesByDprtcode(String dprtcode);
}