package com.eray.thjw.project2.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.po.TechnicalfileOrder;

import enu.LogOperationEnum;
import enu.UpdateTypeEnum;

public interface TechnicalfileOrderService {

	/**
	 * 
	 * @Description 添加技术指令表
	 * @CreateTime 2017-8-16 下午5:12:22
	 * @CreateBy 孙霁
	 * @param technicalfileOrder
	 * @param mainid
	 * @param czls
	 * @param zdid
	 * @param user
	 * @return
	 * @throws BusinessException
	 */
	public TechnicalfileOrder insert(TechnicalfileOrder technicalfileOrder,String mainid,String czls,String zdid, 
			User user, LogOperationEnum cz)throws BusinessException ;
	/**
	 * 
	 * @Description 修改技术指令表
	 * @CreateTime 2017-8-16 下午5:12:08
	 * @CreateBy 孙霁
	 * @param technicalfileOrder
	 * @param mainid
	 * @param czls
	 * @param zdid
	 * @param user
	 * @throws BusinessException
	 */
	public void update(TechnicalfileOrder technicalfileOrder,String mainid,String czls,String zdid,
			User user, LogOperationEnum cz)throws BusinessException ;
	/**
	 * 
	 * @Description 删除技术指令表
	 * @CreateTime 2017-8-16 下午5:11:16
	 * @CreateBy 孙霁
	 * @param technicalfileOrderId
	 * @param czls
	 * @param zdid
	 * @param user
	 * @throws BusinessException
	 */
	public void delete(String technicalfileOrderId,String czls,String zdid,
			User user, LogOperationEnum cz)throws BusinessException ;
	
	/**
	 * 
	 * @Description 根据适航指令ids获取数据
	 * @CreateTime 2017-8-16 下午5:12:38
	 * @CreateBy 孙霁
	 * @param arids
	 * @return
	 */
	public List<TechnicalfileOrder> queryAllByMainids(List<String> arids);
	/**
	 * 
	 * @Description 根据组适航性资料id删除数据
	 * @CreateTime 2017-8-12 下午2:43:31
	 * @CreateBy 孙霁
	 * @param technicalfileOrderId
	 * @param mainid
	 * @param czls
	 * @param zdid
	 * @param dprtcode
	 * @throws BusinessException
	 */
	public void deleteByMainid(String mainid,String czls,String zdid, User user, LogOperationEnum cz)throws BusinessException ;
	
	/**
	 * 
	 * @Description 对适用性机型 进行增删改操作
	 * @CreateTime 2017-8-16 上午11:11:41
	 * @CreateBy 孙霁
	 * @param TechnicalfileOrderList
	 * @throws BusinessException
	 */
	public void updateTechnicalfileOrder(Airworthiness airworthiness
			,String czls,String zdid,User user, LogOperationEnum cz)throws BusinessException ;
	/**
	 * 
	 * @Description 修改评估人
	 * @CreateTime 2017年11月20日 上午10:30:37
	 * @CreateBy 林龙
	 * @param jswjid
	 * @param jx
	 * @param pgrid
	 */
	public void updatetBypgr(String jswjid, String jx, String pgrid);
}
