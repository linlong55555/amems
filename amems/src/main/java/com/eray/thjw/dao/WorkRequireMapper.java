package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.WorkRequire;
import com.eray.thjw.quality.po.PostApplicationPXPG;

public interface WorkRequireMapper {
	/**
	 * @author liudeng
	 * @description 根据gwid查询数据
	 * @param id
	 * @return List<WorkRequire>
	 */
	List<WorkRequire> queryWorkRequiresAllByGwid(String id);
	
	
	/**
	 * 获取项次
	 */
	List<WorkRequire> queryXc(String mainid);
	
	
	/**
	 * 删除岗位需求信息
	 * @param workrequire
	 */
	int deleteWorkRequiresByPrimaryKey(String id);
	
	/**
	 * 保存岗位需求信息
	 * @param workrequire
	 * @return
	 */
	int saveWorkRequires(WorkRequire workrequire);
	
	

	/**
	 * 修改岗位需求信息
	 * @param workrequire
	 * @return
	 */
	int updateWorkRequiresByPrimaryKey(WorkRequire workrequire);
	
	
	/**
	 * 根据主键获取岗位需求信息
	 * @param workrequire
	 * @return
	 */
	List<WorkRequire> getWorkRequyireByKey(String key);
	
	/**
	 * @Description 查询岗位要求
	 * @CreateTime 2017-11-17 下午4:02:14
	 * @CreateBy 刘兵
	 * @param workRequire 岗位要求
	 * @return List<WorkRequire> 岗位要求集合
	 */
	List<WorkRequire> queryWorkRequireEval(WorkRequire workrequire);
	
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2017-12-4 下午3:04:37
	 * @CreateBy 孙霁
	 * @param workrequire
	 */
	int updateByPrimaryKeySelective(WorkRequire workrequire);
	
	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2017-12-4 下午3:05:24
	 * @CreateBy 孙霁
	 * @param workrequire
	 * @return
	 */
	int insertSelective(WorkRequire workrequire);
	
}
