package com.eray.thjw.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.po.User;

/**
 * 首页块数据获取
 * @author xu.yong
 *
 */
public interface PortalBlockMapper {
	
	/**
	 * 查看留言
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> selectMsg(String userId);
	/**
	 * 指派给我的技术评估单（未评估）
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> selectBlock1(String userId);
	/**
	 * 指派给我的技术通告（未阅）
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> selectBlock2(@Param("userId")String userId ,@Param("jgbm")String jgbm);
	/**
	 * 指派给我的技术指令（未阅）
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> selectBlock3(String userId);
	/**
	 * 指派给我的修订通知书（未阅）
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> selectBlock4(String userId);
	/**
	 * 指派给我培训计划
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> selectBlock5(@Param("userId")String userId ,@Param("jgbm")String jgbm);
	/**
	 * 我的提订单
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> selectBlock6(String userId);
	/**
	 * MCC 135工单预警（完工未关闭）
	 * @param dprtcode
	 * @return
	 */
	List<Map<String, Object>> selectBlock7(User user);
	/**
	 * 
	 * @Description 145工单预警
	 * @CreateTime 2017年10月16日 下午1:49:19
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	List<Map<String, Object>> selectBlock8(User user);
	/***
	 * 
	 * @Description 待办工作
	 * @CreateTime 2017年10月16日 下午2:58:18
	 * @CreateBy 岳彬彬
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> selectBlock9(String userId);
	/**
	 * 
	 * @Description 传阅
	 * @CreateTime 2018年3月22日 下午2:03:58
	 * @CreateBy 林龙
	 * @return
	 */
	List<Map<String, Object>> selectBlock10(@Param("userId")String userId,@Param("bmId")String bmId);
	
	/**
	 * @Description 待办工作
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	List<Map<String, Object>> selectBlock11(@Param("userId")String userId,@Param("dprtcode")String dprtcode);
	/**
	 * @Description 飞机监控135
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	List<Map<String, Object>> selectBlock12(@Param("userId")String userId,@Param("dprtcode")String dprtcode);
	
	/**
	 * @Description 飞机监控145
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	List<Map<String, Object>> selectBlock13(@Param("userId")String userId,@Param("dprtcode")String dprtcode);
	
	/**
	 * @Description 个人执照/课程到期提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	List<Map<String, Object>> selectBlock14(@Param("userId")String userId,@Param("dprtcode")String dprtcode);
	
	/**
	 * @Description 执照到期提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	List<Map<String, Object>> selectBlock15(String dprtcode);
	
	/**
	 * @Description 工具/设备校验提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	List<Map<String, Object>> selectBlock16(String dprtcode);
	
	/**
	 * @Description 人员培训提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	List<Map<String, Object>> selectBlock17(String dprtcode);
	
	/**
	 * @Description 审核通知单
	 * @CreateTime 2018-4-19 上午10:27:12
	 * @CreateBy 刘兵
	 * @return
	 */
	List<Map<String, Object>> selectBlock18(@Param("shdxid")String shdxid, @Param("dprtcode")String dprtcode);
}