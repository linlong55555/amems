package com.eray.thjw.produce.service;

import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.InstallationListEffective;

/** 
 * @Description 
 * @CreateTime 2017-10-16 下午2:32:24
 * @CreateBy 孙霁	
 */
public interface ComponentiohistoryService {

	/**
	 * 
	 * @Description 查询部件拆装记录列表数据
	 * @CreateTime 2017-10-16 下午2:35:08
	 * @CreateBy 孙霁
	 * @param installationListEffective
	 * @return  Map<String, Object>
	 */
	public Map<String, Object> queryAll(InstallationListEffective installationListEffective);
	
	/**
	 * 
	 * @Description 删除拆装记录的导入数据
	 * @CreateTime 2017-12-5 上午11:14:43
	 * @CreateBy 孙霁
	 * @param id
	 * @throws BusinessException
	 */
	public void deleteRecord(String id) throws BusinessException ;
}
