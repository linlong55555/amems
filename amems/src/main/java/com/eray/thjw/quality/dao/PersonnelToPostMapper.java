package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToPost;

public interface PersonnelToPostMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonnelToPost record);

    int insertSelective(PersonnelToPost record);

    PersonnelToPost selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonnelToPost record);

    int updateByPrimaryKey(PersonnelToPost record);
    
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
    List<PersonnelToPost> query(String id);

	List<PersonnelToPost> selectMainidList(List<String> pgdidList);
	
	/**
	 * @Description 根据组织机构查询
	 * @CreateTime 2018年1月12日 上午11:18:53
	 * @CreateBy 韩武
	 * @param dprtcode
	 * @return
	 */
	List<PersonnelToPost> queryByDprtcode(String dprtcode);
}