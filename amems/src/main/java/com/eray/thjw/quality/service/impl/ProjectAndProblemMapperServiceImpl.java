package com.eray.thjw.quality.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.quality.dao.ProjectAndProblemMapper;
import com.eray.thjw.quality.po.ProjectAndProblem;
import com.eray.thjw.quality.service.ProjectAndProblemMapperService;

/**
 * 
 * @Description 流程记录serviceimpl
 * @CreateTime 2017年9月25日 下午1:57:31
 * @CreateBy 岳彬彬
 */
@Service("projectAndProblemMapperService")
public class ProjectAndProblemMapperServiceImpl implements ProjectAndProblemMapperService  {

	@Resource
	private ProjectAndProblemMapper projectAndProblemMapper;
	
	/**
	 * 
	 * @Description 新增流程记录
	 * @CreateTime 2018年1月11日 上午10:09:17
	 * @CreateBy 岳彬彬
	 * @param shwtdid
	 * @param shxmdid
	 */
	@Override
	public void addRecord(String shwtdid,String shxmdid) {
		ProjectAndProblem record = new ProjectAndProblem();
		record.setShwtdid(shwtdid);
		record.setShxmdid(shxmdid);
		projectAndProblemMapper.insert(record);
	}
	
	/**
	 * 
	 * @Description 根据审核问题单id删除记录
	 * @CreateTime 2018年1月11日 下午2:32:19
	 * @CreateBy 岳彬彬
	 * @param shwtdid
	 */
	@Override
	public void deleteRecord(String shwtdid) {
		projectAndProblemMapper.deleteByShwtdid(shwtdid);	
	}
	
}