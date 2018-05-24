package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToAchievement;

public interface PersonnelToAchievementMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonnelToAchievement record);

    int insertSelective(PersonnelToAchievement record);

    PersonnelToAchievement selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonnelToAchievement record);

    int updateByPrimaryKey(PersonnelToAchievement record);
    
    /**
     * 删除列表中不存在的数据
     * @param personnel
     * @return
     */
    int deleteNotExist(MaintenancePersonnel personnel);
    
    /**
     * 查询学术成就
     * @param id
     * @return
     */
    List<PersonnelToAchievement> queryScholarships(String id);
    
    /**
     * 查询嘉奖记录
     * @param id
     * @return
     */
    List<PersonnelToAchievement> queryCitations(String id);
    
    /**
     * @Description 根据组织机构查询学术成就
     * @CreateTime 2018年1月12日 上午11:44:42
     * @CreateBy 韩武
     * @param dprtcode
     * @return
     */
    List<PersonnelToAchievement> queryScholarshipsByDprtcode(String dprtcode);
    
    /**
     * @Description 根据组织机构查询嘉奖记录
     * @CreateTime 2018年1月12日 上午11:44:57
     * @CreateBy 韩武
     * @param dprtcode
     * @return
     */
    List<PersonnelToAchievement> queryCitationsByDprtcode(String dprtcode);
}