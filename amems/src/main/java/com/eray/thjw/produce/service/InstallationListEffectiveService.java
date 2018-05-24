package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.po.InstallationListEffective;

/** 
 * @Description 装机清单-生效区service
 * @CreateTime 2017年10月11日 下午2:21:03
 * @CreateBy 韩武	
 */
public interface InstallationListEffectiveService {
	
	/**
	 * @Description 查询装机清单-生效区的分页数据
	 * @CreateTime 2017年10月11日 下午2:22:35
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	Map<String, Object> queryPageableList(InstallationListEffective record);
	
	/**
	 * @Description 查询装机清单-生效区的数据集合
	 * @CreateTime 2017年10月27日 上午10:44:51
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	List<InstallationListEffective> queryList(InstallationListEffective record);
	
	/**
	 * @Description 查询装机清单详情
	 * @CreateTime 2017年11月27日 下午5:09:11
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	InstallationListEffective queryDetail(InstallationListEffective record);
	
	/**
	 * @Description 获取导出数据
	 * @CreateTime 2017年12月18日 上午11:06:03
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	List<InstallationListEditable> getExportList(InstallationListEditable record);
}
