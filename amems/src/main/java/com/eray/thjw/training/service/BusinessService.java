package com.eray.thjw.training.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.training.po.Business;

/**
 * 岗位service
 * @author ll
 *
 */
public interface BusinessService {
	
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
	public void insertSelective(Business business)throws BusinessException;

    /**
     * 根据id查询
     * @param id
     * @return
     */
	public Business selectByPrimaryKey(String id)throws BusinessException;

    /**
     * 修改
     * @param business
     * @return
     */
	public void updateByPrimaryKeySelective(Business business)throws BusinessException;
	
	/**
	 * 作废
	 * @param business
	 * @return
	 */
	public void invalid(Business business)throws BusinessException;

    /**
     * 分页查询
     * @param business
     * @return
     */
	Map<String, Object> queryAllPageList(Business business)throws BusinessException;
	
	/**
	 * @Description 查询课程岗位关系
	 * @CreateTime 2018-2-1 上午10:43:59
	 * @CreateBy 刘兵
	 * @param business 岗位
	 * @return List<Business> 岗位集合
	 */
	List<Business> queryByKc(Business business);
	
	/** sunji
	 * 查询所有状态为有效的数据（不分页）
	 * @return List<Business>
	 * @throws Exception 
	 */
	List<Business> queryAllBusiness(Business business)throws BusinessException;
	/**
	 * 
	 * @Description 根据岗位id和维修人员档案id查询数据
	 * @CreateTime 2018-3-27 下午2:16:32
	 * @CreateBy 孙霁
	 * @param business
	 * @return
	 * @throws BusinessException
	 */
	List<Business> queryAllResults(Business business)throws BusinessException;
	
	
}