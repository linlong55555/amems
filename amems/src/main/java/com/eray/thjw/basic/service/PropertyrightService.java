package com.eray.thjw.basic.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.basic.po.Propertyright;
import com.eray.thjw.basic.po.ZoneStation;
import com.eray.thjw.exception.BusinessException;

/**
 * 
 * @Description 产权数据管理业务逻辑接口
 * @CreateTime 2018-2-5 上午10:53:30
 * @CreateBy 孙霁
 */
public interface PropertyrightService {


	/**
	 * 
	 * @Description 根据条件查询数据
	 * @CreateTime 2018-2-5 上午10:55:01
	 * @CreateBy 孙霁
	 * @return
	 * @throws BusinessException
	 */
	public Map<String , Object> queryAll(Propertyright propertyright)throws BusinessException ;
	
	
	/**
	 * 
	 * @Description 新增
	 * @CreateTime 2018-2-5 上午11:33:36
	 * @CreateBy 孙霁
	 * @param propertyright
	 * @return
	 * @throws BusinessException
	 */
	public String insert(Propertyright propertyright)throws BusinessException ;
	
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2018-2-5 上午11:34:01
	 * @CreateBy 孙霁
	 * @param propertyright
	 * @return
	 * @throws BusinessException
	 */
	public String update(Propertyright propertyright)throws BusinessException ;
	
	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2018-2-5 上午11:34:17
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public String delete(String id)throws BusinessException ;
	
	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2018-2-5 下午4:39:25
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public Propertyright selectById(String id)throws BusinessException ;
	
}
