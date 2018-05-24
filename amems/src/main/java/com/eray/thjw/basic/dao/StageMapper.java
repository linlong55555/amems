package com.eray.thjw.basic.dao;

import java.util.List;

import com.eray.thjw.basic.po.Stage;

/** 
 * @Description stage Mapper接口
 * @CreateTime 2017-9-14 上午11:51:14
 * @CreateBy 甘清	
 */
public interface StageMapper {
	/**
	 * @Description 根据条件获得符合条件的stage信息列表
	 * @CreateTime 2017-9-14 下午4:42:05
	 * @CreateBy 甘清
	 * @param stage 查询条件
	 * @return List<Stage>
	 */
	List<Stage> getStageList(Stage stage);
	
	/**
	 * @Description 新增一个stage信息
	 * @CreateTime 2017-9-15 上午11:19:39
	 * @CreateBy 甘清
	 * @param stage 前端stage参数
	 */
	void addStage(Stage stage);
	
	/**
	 * @Description 更新一个stage信息
	 * @CreateTime 2017-9-15 上午11:21:41
	 * @CreateBy 甘清
	 * @param stage 前端参数信息
	 */
	void updateStage(Stage stage);
	
	/**
	 * @Description 检查stage是否已经存在
	 * @CreateTime 2017-9-15 下午12:22:17
	 * @CreateBy 甘清
	 * @param stage
	 * @return 
	 */
	List<Stage> checkStage(Stage stage);
	
	/**
	 * @Description 根据ID & 组织机构码获得stage信息
	 * @CreateTime 2017-9-15 下午4:04:30
	 * @CreateBy 甘清
	 * @param stage 查询参数
	 * @return Stage
	 */
	Stage getStageById(Stage stage);
	
	/**
	 * @Description 删除阶段
	 * @CreateTime 2017-9-24 下午8:09:29
	 * @CreateBy 甘清
	 * @param stage 前端参数
	 */
	void deleteStage(Stage stage);
	
	/**
	 * @Description 根据机构代码获取阶段集合
	 * @CreateTime 2017-9-19 下午2:11:08
	 * @CreateBy 刘兵
	 * @param dprtcode 机构代码
	 * @return List<Stage> 阶段集合
	 */
	List<Stage> getStageListByDrpt(String dprtcode);

	/**
	 * @Description 根据机构代码+状态=1获取阶段集合：导入专用
	 * @CreateTime 2017-12-11 下午2:08:56
	 * @CreateBy 雷伟
	 * @param param
	 * @return
	 */
	List<Stage> getAllStages(Stage param);

}
