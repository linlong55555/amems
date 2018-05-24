package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToWorkExperience;

public interface PersonnelToWorkExperienceMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonnelToWorkExperience record);

    int insertSelective(PersonnelToWorkExperience record);

    PersonnelToWorkExperience selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonnelToWorkExperience record);

    int updateByPrimaryKey(PersonnelToWorkExperience record);
    
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
    List<PersonnelToWorkExperience> query(String id);
    
    /**
     * @Description 根据组织机构查询
     * @CreateTime 2018年1月12日 上午11:16:13
     * @CreateBy 韩武
     * @param dprtcode
     * @return
     */
    List<PersonnelToWorkExperience> queryByDprtcode(String dprtcode);
}