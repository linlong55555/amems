package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.WorkRequire;
import com.eray.thjw.quality.po.MaintenancePersonnel;


public interface WorkRequireService {
	/**
	 * @author liudeng
	 * @description 根据gwid查询数据
	 * @param id
	 * @return List<WorkRequire>
	 */
	List<WorkRequire> queryWorkRequiresAllByGwid(String id);
	
	
	/**
	 * 删除岗位需求信息
	 * @param workrequire
	 * @throws BusinessException
	 */
	void deleteWorkRequires(WorkRequire workrequire) throws BusinessException;
	
	/**
	 * 保存岗位需求信息
	 * @param workrequire
	 * @return
	 */
	String saveWorkRequires(WorkRequire workrequire) throws BusinessException;
	
	

	/**
	 * 修改岗位需求信息
	 * @param workrequire
	 * @return
	 */
	String updateWorkRequires(WorkRequire workrequire) throws BusinessException;
}
