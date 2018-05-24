package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Affected;


/**
 * @author liub
 * @description 
 * @develop date 2016.07.29
 */
public interface AffectedService {

	public void save(List<Affected> affected,String id,String list,String list1,String czls,String technicalfileId) throws BusinessException;
	
	/**
	 * @author sunji
	 * @description 根据评估单id工作内容数据
	 * @param djxmid
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryListByDjxmbhid(String djxmid) throws BusinessException;
	/**
	 * @author sunji
	 * @description 根据多个评估单id工作内容数据
	 * @param techncialfileids
	 * @return List<Map<String, Object>>
	 */
	public List<Affected> queryListByTechnicalfileids(List<String> techncialfileIds) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 根据多个评估单id查询执行对象数据
	 * @param pgdIds
	 * @return List<Affected>
	 */
	public List<Affected> queryListByPgdIds(List<String> pgdIds)throws BusinessException;
	
}
