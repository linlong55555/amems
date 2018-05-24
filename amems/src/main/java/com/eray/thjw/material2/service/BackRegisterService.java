package com.eray.thjw.material2.service;

import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.BackRegister;
import com.eray.thjw.material2.po.MeasurementToolsDetails;

/**
 * 
 * @Description 退料登记Service
 * @CreateTime 2018年3月5日 上午9:47:50
 * @CreateBy 林龙
 */
public interface BackRegisterService {
	
	/**
	 * @Description 退料登记分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param measurementToolsDetails 计量工具
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllPageList(BackRegister backRegister)throws BusinessException ;

	/**
	 * 
	 * @Description 查询退料登记详情
	 * @CreateTime 2018年3月5日 上午11:51:06
	 * @CreateBy 林龙
	 * @param backRegister
	 * @return
	 * @throws BusinessException
	 */
	public BackRegister getInfoById(BackRegister backRegister)throws BusinessException;
	
	
}
