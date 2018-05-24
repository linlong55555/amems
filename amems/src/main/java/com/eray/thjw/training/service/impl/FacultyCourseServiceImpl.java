package com.eray.thjw.training.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.training.dao.FacultyCourseMapper;
import com.eray.thjw.training.po.FacultyCourse;
import com.eray.thjw.training.service.FacultyCourseService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

import enu.common.EffectiveEnum;

@Service
public class FacultyCourseServiceImpl implements FacultyCourseService{
	
	@Resource
	private FacultyCourseMapper facultyCourseMapper;
	

	@Override
	public List<FacultyCourse> queryList(FacultyCourse facultyCourse) throws BusinessException {
		List<FacultyCourse> list = facultyCourseMapper.queryPageByParam(facultyCourse);
		return list;
	}
	
	@Override
	public List<FacultyCourse> queryPageList(FacultyCourse facultyCourse) throws BusinessException {
		//PageHelper.startPage(facultyCourse.getPagination());
		List<FacultyCourse> list = facultyCourseMapper.queryPageByParam(facultyCourse);
		return list;
		/*if(list !=null && list.size()>0) {
			return PageUtil.pack4PageHelper(list, facultyCourse.getPagination());
		}else {
			list = new ArrayList<FacultyCourse>();
			return PageUtil.pack(0, list, facultyCourse.getPagination());
		}*/
	}

	
	
	@Override
	public int add(FacultyCourse facultyCourse) throws BusinessException {
		return facultyCourseMapper.insert(facultyCourse);
	}

	@Override
	public int update(FacultyCourse facultyCourse) throws BusinessException {
		return facultyCourseMapper.updateByPrimaryKeySelective(facultyCourse);
	}
	
	@Override
	public int deleteByMainId(FacultyCourse facultyCourse) throws BusinessException {
		facultyCourse.setZt(EffectiveEnum.NO.getId());
		return facultyCourseMapper.updateByMainId(facultyCourse);
		
	}

}
