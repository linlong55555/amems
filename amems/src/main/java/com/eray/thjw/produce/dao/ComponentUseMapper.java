package com.eray.thjw.produce.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.ComponentUse;

public interface ComponentUseMapper {

	/**
	 * @Description 查询待同步装机清单部件的在机累计值
	 * @CreateTime 2017年10月19日 下午1:46:59
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @return
	 */
	List<ComponentUse> selectToBeSyncInstallCompnentUsageList(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);
	
	/**
	 * 
	 * @Description 查询飞行记录本涉及时间内在机部件的累计值
	 * @CreateTime 2017年11月14日 下午2:54:35
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param minKcsj 最小开车时间
	 * @param maxKcsj 最大开车时间
	 * @return
	 */
	List<ComponentUse> selectInstalledCompnentUsageList(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch, @Param("minKcsj") Date minKcsj, @Param("maxKcsj")Date maxKcsj);

	/**
	 * @Description 查询工单拆下件部件使用累计值
	 * @CreateTime 2017年11月20日 上午10:11:50
	 * @CreateBy 徐勇
	 * @param workOrderId 工单ID
	 * @return
	 */
	List<ComponentUse> selectWorkOrderCompnentUsageList(@Param("workOrderId")String workOrderId);
	
	/**
	 * @Description 查询工单装件部件使用累计值
	 * @CreateTime 2017年11月20日 上午10:11:50
	 * @CreateBy 徐勇
	 * @param workOrderId 工单ID
	 * @return
	 */
	List<ComponentUse> selectWorkOrderZsjCompnentUsageList(@Param("workOrderId")String workOrderId);
	/**
	 * @Description 查询工单拆装记录拆下件部件使用累计值
	 * @CreateTime 2017年11月20日 上午10:11:50
	 * @CreateBy 徐勇
	 * @param woIOIdList 工单拆装记录ID集合
	 * @return
	 */
	List<ComponentUse> selectWorkOrderIOCompnentUsageList(@Param("list")List<String> woIOIdList);
	
	/**
	 * @Description 查询工单拆装记录装件部件使用累计值
	 * @CreateTime 2017年11月20日 上午10:11:50
	 * @CreateBy 徐勇
	 * @param woIOIdList 工单拆装记录ID集合
	 * @return
	 */
	List<ComponentUse> selectWorkOrderIOZsjCompnentUsageList(@Param("list")List<String> woIOIdList);
	
	/**
	 * @Description 根据装机清单ID不带拆下时间查询部件累计使用量 
	 * @CreateTime 2017年12月8日 上午10:51:35
	 * @CreateBy 徐勇
	 * @param zjqdid
	 * @return
	 */
	ComponentUse selectByZjqdidWithoutCxsj(@Param("zjqdid")String zjqdid);
	
	/**
	 * @Description 根据装机清单ID查询部件使用累计值
	 * @CreateTime 2017年11月20日 下午2:15:31
	 * @CreateBy 徐勇
	 * @param zjqdid 装机清单ID
	 * @param jssj 计算时间
	 * @return
	 */
	ComponentUse selectByZjqdid(@Param("zjqdid")String zjqdid, @Param("jssj")Date jssj);
	
	/**
	 * @Description 根据飞机查询飞机及关键部件使用累计值
	 * @CreateTime 2017年11月20日 下午2:33:24
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param jssj 计算时间
	 * @return
	 */
	List<ComponentUse> selectByFjzch(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch, @Param("jssj")Date jssj);
}