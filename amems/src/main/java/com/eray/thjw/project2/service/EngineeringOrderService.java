package com.eray.thjw.project2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.project2.po.MaintenanceScheme;
import com.eray.thjw.project2.po.WorkCard;

/**
 * @Description EO接口
 * @CreateTime 2017-8-22 上午9:23:33
 * @CreateBy 雷伟
 */
public interface EngineeringOrderService {

	/**
	 * @Description EO分页列表查询
	 * @CreateTime 2017-8-22 下午9:44:27
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 */
	Map<String, Object> queryAllPageList(EngineeringOrder engineeringOrder);
	
	/**
	 * @Description 新增EO
	 * @CreateTime 2017-8-22 上午9:24:10
	 * @CreateBy 雷伟
	 * @param engineeringOrder EO
	 * @return String EO id
	 * @throws BusinessException
	 */
	String save(EngineeringOrder engineeringOrder) throws BusinessException;
	
	/**
	 * @Description 查询维修项目获取可关联EO列表
	 * @CreateTime 2017年8月23日 下午3:41:27
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	Map<String, Object> queryAssociateList(MaintenanceScheme scheme);
	/**
	 * 
	 * @Description 获取部件所在位置信息
	 * @CreateTime 2017年8月24日 上午10:44:30
	 * @CreateBy 岳彬彬
	 * @param bjid
	 * @param dprtcode
	 * @param fjjx
	 * @return
	 */
	Map<String,Object> getBj(String bjid,String dprtcode,String fjjx,String bjh);
	/**
	 * @Description 根据EOId查询EO及相关信息
	 * @CreateTime 2017-8-24 上午11:02:03
	 * @CreateBy 雷伟
	 * @param eoId
	 * @return EO信息
	 */
	EngineeringOrder selectById(String eoId);

	/**
	 * @Description 编辑EO
	 * @CreateTime 2017-8-25 下午5:30:40
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 * @throws BusinessException 
	 */
	String update(EngineeringOrder engineeringOrder) throws BusinessException;

	/**
	 * @Description EO审核
	 * @CreateTime 2017-8-25 下午9:32:02
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 */
	String doAudit(EngineeringOrder engineeringOrder) throws BusinessException;

	/**
	 * @Description EO批准
	 * @CreateTime 2017-8-25 下午9:31:38
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 * @throws BusinessException
	 */
	String doApprove(EngineeringOrder engineeringOrder) throws BusinessException;

	/**
	 * @Description EO提交
	 * @CreateTime 2017-8-25 下午9:31:38
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 * @throws BusinessException
	 */
	String doSubmit(EngineeringOrder engineeringOrder) throws BusinessException;

	/**
	 * @Description EO关闭
	 * @CreateTime 2017-8-25 下午9:31:38
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 * @throws BusinessException
	 */
	String doClose(EngineeringOrder engineeringOrder) throws BusinessException;

	/**
	 * @Description 改版EO
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param engineeringOrder EO
	 * @return
	 * @throws BusinessException
	 */
	String doRevision(EngineeringOrder engineeringOrder) throws BusinessException;

	/**
	 * @Description  根据EOId查询当前工卡的历史版本集合
	 * @CreateTime 2017-8-31 上午9:06:37
	 * @CreateBy 雷伟
	 * @param id EOID
	 * @return
	 * @throws BusinessException
	 */
	List<WorkCard> queryHistoryListById(String id);

	/**
	 * @Description 删除EO
	 * @CreateTime 2017-9-13 下午4:41:02
	 * @CreateBy 雷伟
	 * @param id
	 */
	void doDelete(String id) throws BusinessException;

	/**
	 * @Description EO执行对象
	 * @CreateTime 2017-10-16 下午4:48:02
	 * @CreateBy 雷伟
	 * @param eoId
	 * @return
	 */
	List<Map<String, Object>> getEOExecutionObjs(String eoId);
	
	
	EngineeringOrder getByEobh(EngineeringOrder engineeringOrder) throws BusinessException;
	
	
	List<EngineeringOrder> exportEoList(EngineeringOrder engineeringOrder) throws BusinessException;

	/**
	 * @description EO圈阅
	 * @CreateTime 2018-2-26 下午3:42:12
	 * @CreateBy 雷伟
	 * @param eoId
	 * @throws BusinessException 
	 */
	void view4IsCirculuation(String eoId) throws BusinessException;
	
}
