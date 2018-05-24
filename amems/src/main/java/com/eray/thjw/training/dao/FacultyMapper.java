package com.eray.thjw.training.dao;

import java.util.List;

import com.eray.thjw.training.po.Faculty;


public interface FacultyMapper {
    int deleteByPrimaryKey(String id);

    int insert(Faculty record);

    int insertSelective(Faculty record);

    Faculty selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Faculty record);

    int updateByPrimaryKey(Faculty record);
    
    List<Faculty> queryAll(Faculty record);

	List<Faculty> queryAllopenlist(Faculty faculty);
	
	List<Faculty> getJsByDprtcode(String dprtcode);
	
	String hasCourseAuth(String jsbh,String kcbh,String dprtcoe,String jx);
	

}