package com.eray.thjw.material2.service;

import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.OutboundOrder;
import com.eray.thjw.produce.po.DefectKeep;


/**
 * 
 * @Description 出库service
 * @CreateTime 2018年3月15日 下午3:19:14
 * @CreateBy 林龙
 */
public interface OutboundOrderService {

	/**
	 * 
	 * @Description 保存出库单
	 * @CreateTime 2018年3月15日 下午3:21:28
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
	String save(OutboundOrder outboundOrder)throws BusinessException;

	/**
	 * 
	 * @Description 修改出库单
	 * @CreateTime 2018年3月15日 下午3:21:53
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
	String update(OutboundOrder outboundOrder)throws BusinessException;

	/**
	 * 
	 * @Description 新增提交出库单
	 * @CreateTime 2018年3月15日 下午3:22:53
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
	String saveSubmit(OutboundOrder outboundOrder)throws BusinessException;

	/**
	 * 
	 * @Description 修改提交出库单
	 * @CreateTime 2018年3月15日 下午3:23:43
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
	String updateSubmit(OutboundOrder outboundOrder)throws BusinessException;
	
	/**
	 * 
	 * @Description 修改提交出库单
	 * @CreateTime 2018年3月15日 下午3:23:43
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
	void delete(OutboundOrder outboundOrder)throws BusinessException;

	/**
	 * 
	 * @Description 出库列表
	 * @CreateTime 2018年3月16日 上午11:43:33
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> queryAllPageList(OutboundOrder outboundOrder)throws BusinessException;

	/**
	 * 
	 * @Description 查询出库单数据
	 * @CreateTime 2018年3月16日 下午2:51:53
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
	OutboundOrder getByStockoutId(OutboundOrder outboundOrder)throws BusinessException;

	/**
	 * 
	 * @Description 撤销出库单
	 * @CreateTime 2018年3月20日 下午12:14:50
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @throws BusinessException
	 */
	String updaterevoke(OutboundOrder outboundOrder)throws BusinessException;
	
	
}
