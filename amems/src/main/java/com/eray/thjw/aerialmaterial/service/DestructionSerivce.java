package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Destruction;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.exception.BusinessException;

public interface DestructionSerivce {

	/**
	 * @author sunji
	 * @description  根据条件分页销毁记录单
	 * @param stock
	 * @return Map<String, Object>
	 */
	public List<Destruction> queryDestructionList(Destruction record) throws BusinessException;
	/**
	 * @author sunji
	 * @description  添加销毁记录单(保存)
	 * @param Destruction
	 * @return Map<String, Object>
	 */
	public Map<String,Object> save(Destruction record) throws BusinessException;
	/**
	 * @author sunji
	 * @description  添加销毁记录单(提交)
	 * @param Destruction
	 * @return Map<String, Object>
	 */
	public Map<String,Object> submit(Destruction record) throws BusinessException;
	/**
	 * @author sunji
	 * @description  修改销毁记录单(保存)
	 * @param Destruction
	 * @return Map<String, Object>
	 */
	public Map<String,Object> edit(Destruction record) throws BusinessException;
	/**
	 * @author sunji
	 * @description  修改销毁记录单(提交)
	 * @param Destruction
	 * @return Map<String, Object>
	 */
	public Map<String,Object> update(Destruction record) throws BusinessException;
	/**
	 * @author sunji
	 * @description  删除销毁单
	 * @param Destruction
	 * @return Map<String, Object>
	 */
	public Map<String,Object> deleteDestruction(String id) throws BusinessException;
	/**
	 * @author sunji
	 * @description  撤销销毁单
	 * @param Destruction
	 * @return Map<String, Object>
	 */
	public Map<String,Object> updateUndoDestruction(String id) throws BusinessException;
	/**
	 * @author sunji
	 * @description  根据id查询单个
	 * @param Destructions
	 * @return Map<String, Object>
	 */
	public Destruction selectByPrimaryKey(String id) throws BusinessException;
}
