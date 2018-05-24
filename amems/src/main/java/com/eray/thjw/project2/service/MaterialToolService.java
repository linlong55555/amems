package com.eray.thjw.project2.service;

import java.util.List;

import com.eray.thjw.project2.po.MaterialTool;

import enu.LogOperationEnum;

/**
 * @Description 器材/工具service
 * @CreateTime 2017-8-19 下午2:57:09
 * @CreateBy 刘兵
 */
public interface MaterialToolService {
	
	/**
	 * @Description 保存多个器材/工具
	 * @CreateTime 2017-8-19 下午3:09:13
	 * @CreateBy 刘兵
	 * @param materialToolList 器材/工具集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	void saveMaterialToolList(List<MaterialTool> materialToolList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration);
	
	/**
	 * @Description 编辑多个器材/工具
	 * @CreateTime 2017-8-19 下午4:15:15
	 * @CreateBy 刘兵
	 * @param materialToolList 器材/工具集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	void updateMaterialToolList(List<MaterialTool> materialToolList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration);
	
	/**
	 * @Description 根据ywid删除器材/工具
	 * @CreateTime 2017-9-13 下午5:16:13
	 * @CreateBy 刘兵
	 * @param ywid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	void deleteByYwid(String ywid, String czls, String zdid, LogOperationEnum logopration);
	
	/**
	 * @Description 根据条件查询器材/工具列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryAllList(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询器材清单列表
	 * @CreateTime 2017-8-19 下午3:49:49
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryEquipmentList(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询航材工具需求清单(维修项目)
	 * @CreateTime 2017-10-25 下午3:03:06
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryToolList4Maintenance(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询航材工具需求清单(EO)
	 * @CreateTime 2017-10-25 下午3:03:06
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryToolList4EO(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询航材工具需求清单(工单)
	 * @CreateTime 2017-10-25 下午3:03:06
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryToolList4WorkOrder(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询航材工具需求清单(工包)
	 * @CreateTime 2017-10-28 下午1:11:56
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryToolList4Package(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询航材工具需求清单(选中的)
	 * @CreateTime 2017-10-27 上午10:41:26
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryAllToolList(MaterialTool materialTool);
	/**
	 * 
	 * @Description 根据条件查询航材工具需求清单(145工包)
	 * @CreateTime 2017年10月28日 下午4:07:35
	 * @CreateBy 岳彬彬
	 * @param materialTool
	 * @return
	 */
	List<MaterialTool> queryToolList4WP145(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询器材/工具任务信息列表(选中的)
	 * @CreateTime 2017-11-1 上午10:59:29
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具 
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryTaskInfoList(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询器材/工具任务信息列表(工包)
	 * @CreateTime 2017-11-1 上午10:59:29
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具 
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryTaskInfoList4Package(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询器材/工具任务信息列表(工单)
	 * @CreateTime 2017-11-1 上午10:59:29
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具 
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryTaskInfoList4WorkOrder(MaterialTool materialTool);
	/**
	 * @Description 根据条件查询器材/工具任务信息列表(145工包)
	 * @CreateTime 2017-11-1 上午10:59:29
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具 
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryTaskInfoList4WP145(MaterialTool materialTool);
}
