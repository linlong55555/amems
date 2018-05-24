package com.eray.thjw.material2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.Destroy;

/**
 * 
 * @Description 销毁
 * @CreateTime 2018年3月27日 下午4:24:45
 * @CreateBy 岳彬彬
 */
public interface MaterialSDestroyService {
	/**
	 * 
	 * @Description 待销毁列表
	 * @CreateTime 2018年3月27日 下午5:30:40
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> getToDestroyList(Destroy record) throws BusinessException;
	/**
	 * 
	 * @Description 销毁
	 * @CreateTime 2018年3月27日 下午5:30:49
	 * @CreateBy 岳彬彬
	 * @param list  id集合
	 */
	void update4DestroyRecrod(List<String> list) throws BusinessException;
	/**
	 * 
	 * @Description 已销毁列表
	 * @CreateTime 2018年3月28日 下午2:07:57
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> getDestroyList(Destroy record) throws BusinessException;
	/**
	 * 
	 * @Description 撤销
	 * @CreateTime 2018年3月28日 下午3:12:19
	 * @CreateBy 岳彬彬
	 * @param list
	 * @throws BusinessException
	 */
	void update4RevokeRecrod(List<String> list) throws BusinessException;
}
