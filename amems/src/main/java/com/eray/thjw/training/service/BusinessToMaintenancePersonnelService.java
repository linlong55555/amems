package com.eray.thjw.training.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.training.po.Business;
import com.eray.thjw.training.po.BusinessToMaintenancePersonnel;

/**
 * 岗位service
 * @author ll
 *
 */
public interface BusinessToMaintenancePersonnelService {
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public void deleteByPrimaryKey(String id)throws BusinessException;

    /**
     * 新增
     * @param business
     * @return
     */
	public void insertSelective(BusinessToMaintenancePersonnel businessPer)throws BusinessException;

    /**
     * 根据id查询
     * @param id
     * @return
     */
	public BusinessToMaintenancePersonnel selectByPrimaryKey(String id)throws BusinessException;

    /**
     * 修改
     * @param business
     * @return
     */
	public void updateByPrimaryKeySelective(BusinessToMaintenancePersonnel businessPer)throws BusinessException;

    /**
     * 分页查询
     * @param business
     * @return
     */
	Map<String, Object> queryAllPageList(BusinessToMaintenancePersonnel businessPer)throws BusinessException;
	/**
	 * 不分页查询
	 * @param business
	 * @return
	 */
	List<BusinessToMaintenancePersonnel> queryAllPage(BusinessToMaintenancePersonnel businessPer)throws BusinessException;
	 /**
     * 根据岗位id查询数据
     * @param business
     * @return
     */
	public List<BusinessToMaintenancePersonnel> queryByGwids(List<String> gwids)throws BusinessException;
	/**
	 * 根据维修档案id查询数据
	 * @param business
	 * @return
	 */
	public List<BusinessToMaintenancePersonnel> queryAllBywxrydaid(String wxrydaid)throws BusinessException;
	/**
	 * 根据ids进行批量添加
	 * @param businessToMaintenancePersonnel
	 * @return
	 */
	public void batchInsert(BusinessToMaintenancePersonnel businessToMaintenancePersonnel)throws BusinessException;
	/**
	 * 作废
	 * @param id
	 * @return
	 */
	public void deleteInfo(String id)throws BusinessException;

	public int selectByPrimarygwwxId(BusinessToMaintenancePersonnel businessToMaintenancePersonnel)throws BusinessException;

	public void deleteByPrimaryBt(
			BusinessToMaintenancePersonnel businessToMaintenancePersonnel);
	
	/**
	 * 
	 * @Description 根据条件查询岗位人员课程跟踪
	 * @CreateTime 2018-2-1 下午3:59:46
	 * @CreateBy 孙霁
	 * @param business
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAll(BusinessToMaintenancePersonnel businessToMaintenancePersonnel)throws BusinessException;
	/**
	 * 
	 * @Description 根据条件查询岗位人员课程跟踪(不分页)
	 * @CreateTime 2018-2-1 下午3:59:46
	 * @CreateBy 孙霁
	 * @param business
	 * @return
	 * @throws BusinessException
	 */
	public List<BusinessToMaintenancePersonnel> queryAllList(BusinessToMaintenancePersonnel businessToMaintenancePersonnel)throws BusinessException;
	
	
}