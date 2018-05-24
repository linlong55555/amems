package com.eray.thjw.basic.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.basic.po.Stage;
import com.eray.thjw.exception.BusinessException;

/** 
 * @Description stage service接口
 * @CreateTime 2017-9-14 上午11:47:08
 * @CreateBy 甘清	
 */
public interface StageService {
	
	/**
	 * @Description  根据条件获得stage集合
	 * @CreateTime 2017-9-14 下午3:19:41
	 * @CreateBy 甘清
	 * @param stage 搜索条件
	 * @return Map<String, Object>
	 */
	Map<String, Object> getStages(Stage stage) throws BusinessException;
	
	/**
	 * @Description 新增stage信息
	 * @CreateTime 2017-9-15 上午11:13:46
	 * @CreateBy 甘清
	 * @param stage 前端stage参数
	 * @return Stage
	 */
	Stage addStage(Stage stage) throws BusinessException;
	
	/**
	 * @Description 更新阶段信息
	 * @CreateTime 2017-9-15 上午11:53:00
	 * @CreateBy 甘清
	 * @param stage 前端阶段参数
	 */
	void updateStage(Stage stage) throws BusinessException;
	
	/**
	 * @Description 根据ID & 组织机构码 获得stage的详细信息
	 * @CreateTime 2017-9-15 下午3:59:05
	 * @CreateBy 甘清
	 * @param stage 获得stage的信息
	 * @return Stage
	 */
	Stage getStageById(Stage stage);
	
	/**
	 * @Description 根据机构代码获取阶段集合
	 * @CreateTime 2017-9-19 下午2:11:08
	 * @CreateBy 刘兵
	 * @param dprtcode 机构代码
	 * @return List<Stage> 阶段集合
	 */
	List<Stage> getStageListByDrpt(String dprtcode);
	
	/**
	 * @Description 删除阶段
	 * @CreateTime 2017-9-24 下午8:09:29
	 * @CreateBy 甘清
	 * @param id 前端参数stage ID
	 */
	void updateStageStatus(String id) throws BusinessException;

}
