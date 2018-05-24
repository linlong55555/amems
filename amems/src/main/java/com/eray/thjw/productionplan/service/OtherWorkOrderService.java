package com.eray.thjw.productionplan.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.po.OtherWorkOrder;


/**
 * 其他工单监控
 * @author linlong
 *
 */
public interface OtherWorkOrderService {
	
	public List<OtherWorkOrder> queryAllList(OtherWorkOrder otherWorkOrder) throws Exception;
	
	OtherWorkOrder canshu(OtherWorkOrder otherWorkOrder2,PlaneModelData planeModelData) throws Exception;///格式化计划，剩余，剩余天数
	
	 String shengyu1(OtherWorkOrder otherWorkOrder2) throws Exception;//格式化剩余：监控1
	 
	 String shengyu2(OtherWorkOrder otherWorkOrder2) throws Exception;//格式化剩余：监控2
	 
	 String shengyu3(OtherWorkOrder otherWorkOrder2) throws Exception;//格式化剩余：监控3

	 public Map<String, Object> checkUpdMt(User userFromSession, String ids) throws Exception; //验证

	public Map<String, Object> subjh(OtherWorkOrder otherWorkOrder)throws BusinessException ;

	public Map<String, Object> saveJkbz(OtherWorkOrder otherWorkOrder)throws Exception;
	
}