package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToTechnicalGradeRecord;

public interface PersonnelToTechnicalGradeRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonnelToTechnicalGradeRecord record);

    int insertSelective(PersonnelToTechnicalGradeRecord record);

    PersonnelToTechnicalGradeRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonnelToTechnicalGradeRecord record);

    int updateByPrimaryKey(PersonnelToTechnicalGradeRecord record);
    
    /**
     * 删除列表中不存在的数据
     * @param personnel
     * @return
     */
    int deleteNotExist(MaintenancePersonnel personnel);
    
    /**
     * 查询
     * @param id
     * @return
     */
    List<PersonnelToTechnicalGradeRecord> query(String id);
    
    /**
     * @Description 根据组织机构查询
     * @CreateTime 2018年1月12日 上午11:31:35
     * @CreateBy 韩武
     * @param dprtcode
     * @return
     */
    List<PersonnelToTechnicalGradeRecord> queryByDprtcode(String dprtcode);
}