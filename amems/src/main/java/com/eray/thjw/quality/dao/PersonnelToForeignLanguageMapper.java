package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToForeignLanguage;

public interface PersonnelToForeignLanguageMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonnelToForeignLanguage record);

    int insertSelective(PersonnelToForeignLanguage record);

    PersonnelToForeignLanguage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonnelToForeignLanguage record);

    int updateByPrimaryKey(PersonnelToForeignLanguage record);
    
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
    List<PersonnelToForeignLanguage> query(String id);
    
    /**
     * @Description 根据组织机构查询
     * @CreateTime 2018年1月12日 上午11:10:09
     * @CreateBy 韩武
     * @param dprtcode
     * @return
     */
    List<PersonnelToForeignLanguage> queryByDprtcode(String dprtcode);
}