package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToBusinessAssessment;

public interface PersonnelToBusinessAssessmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonnelToBusinessAssessment record);

    int insertSelective(PersonnelToBusinessAssessment record);

    PersonnelToBusinessAssessment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonnelToBusinessAssessment record);

    int updateByPrimaryKey(PersonnelToBusinessAssessment record);
    
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
    List<PersonnelToBusinessAssessment> query(String id);
    
    /**
     * @Description 根据组织机构查询
     * @CreateTime 2018年1月12日 上午11:42:06
     * @CreateBy 韩武
     * @param dprtcode
     * @return
     */
    List<PersonnelToBusinessAssessment> queryByDprtcode(String dprtcode);
}