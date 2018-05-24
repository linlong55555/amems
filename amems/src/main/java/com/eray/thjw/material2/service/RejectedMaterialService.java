package com.eray.thjw.material2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.DemandSafeguardDetail;
import com.eray.thjw.material2.po.RejectedMaterial;

/** 
 * @Description 
 * @CreateTime 2018-3-2 下午2:23:00
 * @CreateBy 孙霁	
 */
public interface RejectedMaterialService {
	
	/**
	 * 
	 * @Description 获取退料申请数据
	 * @CreateTime 2018-3-2 下午2:23:30
	 * @CreateBy 孙霁
	 * @param rejectedMaterial
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAll(RejectedMaterial rejectedMaterial)throws BusinessException ;

	/**
	 * 
	 * @Description 新增
	 * @CreateTime 2018-3-6 上午11:31:39
	 * @CreateBy 孙霁
	 * @param rejectedMaterial
	 * @return
	 * @throws BusinessException
	 */
	public String insert(RejectedMaterial rejectedMaterial)throws BusinessException ;
	
	/**
	 * 
	 * @Description 修改（修订）
	 * @CreateTime 2018-3-6 上午11:48:33
	 * @CreateBy 孙霁
	 * @param rejectedMaterial
	 * @return
	 * @throws BusinessException
	 */
	public String update(RejectedMaterial rejectedMaterial)throws BusinessException ;
	
	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2018-3-6 下午6:12:08
	 * @CreateBy 孙霁
	 * @param rejectedMaterial
	 * @throws BusinessException
	 */
	public RejectedMaterial selectById(String id)throws BusinessException ;
}
