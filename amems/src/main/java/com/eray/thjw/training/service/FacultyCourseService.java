package com.eray.thjw.training.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.training.po.FacultyCourse;

public interface FacultyCourseService {

	List<FacultyCourse> queryList(FacultyCourse facultyCourse)throws BusinessException;
	
	List<FacultyCourse> queryPageList(FacultyCourse facultyCourse)throws BusinessException;
	
	int add(FacultyCourse facultyCourse)throws BusinessException;
	
	int update(FacultyCourse facultyCourse)throws BusinessException;

	int deleteByMainId(FacultyCourse facultyCourse) throws BusinessException;
}
