package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToTechnicalExperience;

public interface PersonnelToTechnicalExperienceMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonnelToTechnicalExperience record);

    int insertSelective(PersonnelToTechnicalExperience record);

    PersonnelToTechnicalExperience selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonnelToTechnicalExperience record);

    int updateByPrimaryKey(PersonnelToTechnicalExperience record);
    
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
    List<PersonnelToTechnicalExperience> query(String id);
    
    /**
     * @Description 根据组织机构查询
     * @CreateTime 2018年1月12日 上午11:21:33
     * @CreateBy 韩武
     * @param id
     * @return
     */
    List<PersonnelToTechnicalExperience> queryByDprtcode(String id);
}