package com.eray.thjw.produce.service;

import java.util.List;

import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.po.InstallationListEditable2Init;

import enu.LogOperationEnum;




/**
 * @Description 装机清单-编辑区-部件初始化service
 * @CreateTime 2017年9月28日 下午4:25:17
 * @CreateBy 韩武
 */
public interface InstallationListEditable2InitService {
	
	/**
	 * @Description 保存装机清单-编辑区-部件初始化数据
	 * @CreateTime 2017年9月28日 下午4:30:39
	 * @CreateBy 韩武
	 * @param record
	 */
	void save(InstallationListEditable record);
	
	/**
	 * @Description 批量新增装机清单-初始值 
	 * @CreateTime 2017年10月17日 上午11:43:58
	 * @CreateBy 徐勇
	 * @param list 装机清单-初始值
	 * @param czls 操作流水
	 * @param logOperationEnum 操作
	 * @param zbid 主表id,即装机清单
	 */
	public void add4Batch(List<InstallationListEditable2Init> list, String czls, LogOperationEnum logOperationEnum, String zbid);
	
	/**
	 * @Description 删除装机清单-编辑区-部件初始化数据
	 * @CreateTime 2017年9月28日 下午4:54:19
	 * @CreateBy 韩武
	 * @param record
	 */
	void delete(InstallationListEditable record);
}
