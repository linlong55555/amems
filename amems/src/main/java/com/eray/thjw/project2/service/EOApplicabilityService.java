package com.eray.thjw.project2.service;

import java.util.Date;
import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.EOApplicability;
import com.eray.thjw.project2.po.EngineeringOrder;

import enu.LogOperationEnum;

/** 
 * @Description EO适用性设置
 * @CreateTime 2017-8-28 上午10:45:02
 * @CreateBy 雷伟	
 */
public interface EOApplicabilityService {
	/**
	 * @Description 新增EO适用性设置
	 * @CreateTime 2017-8-23 上午9:36:02
	 * @CreateBy 雷伟
	 * @param syxszList 数据集合
	 * @param user 当前用户
	 * @param eoId 业务ID
	 * @param currentDate 当前时间
	 * @param czls 流水号
	 * @param operation 操作类型
	 */
	void savePulicationAffectedList(List<EOApplicability> syxszList,User user, String eoId,Date currentDate,String czls,LogOperationEnum operation);

	void deleteByMainid(EngineeringOrder engineeringOrder, User user,Date currentDate, String czls, LogOperationEnum operation);

	/**
	 * @Description 飞机注册 添加EO执行对象
	 * @CreateTime 2017年10月16日 上午10:57:09
	 * @CreateBy 徐勇
	 * @param dprtcode 飞机所属组织机构
	 * @param fjjx 飞机机型
	 * @param fjzch 飞机注册号
	 * @param xlh 飞机序列号
	 */
	public void addExecObject(String dprtcode, String fjjx, String fjzch, String xlh);
	
	/**
	 * @Description 添加EO执行对象-部件
	 * @CreateTime 2017年10月16日 上午10:57:09
	 * @CreateBy 徐勇
	 * @param dprtcode 飞机所属组织机构
	 * @param fjjx 飞机机型
	 * @param fjzch 飞机注册号
	 * @param xlh 飞机序列号
	 */
	public void addExecObject4Component(String dprtcode, String fjjx, String bjh, String xlh);

	/**
	 * @Description 获取EO适用性
	 * @CreateTime 2017-10-19 下午12:02:37
	 * @CreateBy 雷伟
	 * @param id
	 * @return
	 */
	List<EOApplicability> getEOApplicabilityByMainId(String mainId);

	EOApplicability selectById(String zxdxId);

	String doZxdxClose(EOApplicability eoApplicability) throws BusinessException;

	String doZxdxConfirm(String zxdxId) throws BusinessException;
}
