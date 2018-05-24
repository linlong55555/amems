package com.eray.thjw.training.dao;

import java.util.List;

import com.eray.thjw.training.po.FacultyCourse;


public interface FacultyCourseMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(FacultyCourse record);

    int insertSelective(FacultyCourse record);

    FacultyCourse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FacultyCourse record);

    int updateByPrimaryKey(FacultyCourse record);
    
	/**
	 * 
	 * @Description 根据参数查询授权课程信息
	 * @CreateTime 2017年9月25日 下午4:25:25
	 * @CreateBy 胡才秋
	 * @param facultyCourse
	 * @return
	 */
	List<FacultyCourse> queryPageByParam(FacultyCourse facultyCourse);
	
	/**
	 * 
	 * @Description 根据主ID删除授课信息
	 * @CreateTime 2017年10月9日 下午5:56:25
	 * @CreateBy 胡才秋
	 * @param facultyCourse
	 * @return
	 */
	int updateByMainId(FacultyCourse facultyCourse);
}