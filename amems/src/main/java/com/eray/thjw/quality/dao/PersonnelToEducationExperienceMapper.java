package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToEducationExperience;

public interface PersonnelToEducationExperienceMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonnelToEducationExperience record);

    int insertSelective(PersonnelToEducationExperience record);

    PersonnelToEducationExperience selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonnelToEducationExperience record);

    int updateByPrimaryKey(PersonnelToEducationExperience record);
    
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
    List<PersonnelToEducationExperience> query(String id);
    
    /**
     * @Description 根据组织机构查询
     * @CreateTime 2018年1月12日 上午10:47:14
     * @CreateBy 韩武
     * @param dprtcode
     * @return
     */
    List<PersonnelToEducationExperience> queryByDprtcode(String dprtcode);
}