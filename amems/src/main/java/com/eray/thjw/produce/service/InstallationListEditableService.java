package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.po.PlanInit;



/**
 * @Description 装机清单-编辑区service
 * @CreateTime 2017年9月22日 下午4:05:23
 * @CreateBy 韩武
 */
public interface InstallationListEditableService {
	
	/**
	 * @Description 查询装机清单-编辑区的分页数据
	 * @CreateTime 2017年9月23日 上午9:59:30
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	Map<String, Object> queryPageableList(InstallationListEditable record);
	
	/**
	 * @Description 查询上级部件集合
	 * @CreateTime 2017年9月25日 下午4:56:40
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	List<InstallationListEditable> queryList(InstallationListEditable record);
	
	/**
	 * @Description 保存装机清单
	 * @CreateTime 2017年9月28日 下午3:55:21
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	String doSave(InstallationListEditable record) throws BusinessException;
	
	/**
	 * @Description 删除装机清单
	 * @CreateTime 2017年10月10日 上午10:03:14
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException
	 */
	void doDelete(InstallationListEditable record) throws BusinessException;
	
	/**
	 * @Description 查询装机清单详情
	 * @CreateTime 2017年10月9日 上午11:11:45
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	InstallationListEditable queryDetail(InstallationListEditable record);
	
	/**
	 * @Description 飞机注册时 保存装机清单编辑区
	 * @CreateTime 2017年10月16日 上午10:34:42
	 * @CreateBy 徐勇
	 * @param fjjx 机型 
	 * @param fjzch 飞机注册号
	 * @param dprtcode 组织机构
	 * @param planeInitList 飞机初始化信息
	 * @param czls 操作流水号
	 * @throws BusinessException 
	 */
	public void saveInstall4PlaneAdd(String fjjx, String fjzch, String dprtcode, List<PlanInit> planeInitList, String czls) throws BusinessException;
	
	/**
	 * @Description 飞机初始值修改时同步修改装机清单
	 * @CreateTime 2017年10月17日 下午2:54:09
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 */
	public void updateInit4PlaneEdit(String dprtcode, String fjzch);
	
	/**
	 * @Description 获取导出数据
	 * @CreateTime 2017年12月18日 上午10:47:55
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	List<InstallationListEditable> getExportList(InstallationListEditable record);

}
