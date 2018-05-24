package com.eray.thjw.dao;


import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Affected;


/**
 * @author liub
 * @description 
 * @develop date 2016.07.29
 */
public interface AffectedMapper {

	public void save(Affected affected) throws BusinessException;
	
	public void update(Affected affected) throws BusinessException;
	
	/**
	 * @author sunji
	 * @description 根据评估单id工作内容数据
	 * @param djxmid
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.29
	 */
	public List<Affected> queryListByDjxmbhid(String djxmid) throws BusinessException;
	
	/**
	 * @author sunji
	 * @description 根据评估单id工作内容数据
	 * @param djxmid
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.29
	 */
	public List<Affected> queryListByTechnicalfileids(List<String> techncialfileIds) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 根据多个评估单id查询执行对象数据
	 * @param pgdIds
	 * @return List<Affected>
	 */
	public List<Affected> queryListByPgdIds(List<String> pgdIds) throws BusinessException;
	
}
