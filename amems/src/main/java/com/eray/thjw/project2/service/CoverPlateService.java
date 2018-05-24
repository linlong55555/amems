package com.eray.thjw.project2.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.CoverPlate;
import com.eray.thjw.project2.po.MaintenanceProject;

import enu.LogOperationEnum;

/**
 * @Description 接近/盖板service
 * @CreateTime 2017年8月16日 上午10:37:53
 * @CreateBy 韩武
 */
public interface CoverPlateService {
	
	/**
	 * @Description 保存接近/盖板-维修方案
	 * @CreateTime 2017年8月16日 上午10:42:25
	 * @CreateBy 韩武
	 * @param project 维修项目
	 * @throws BusinessException
	 */
	void save(MaintenanceProject project) throws BusinessException;
	
	/**
	 * @Description 保存多个接近/盖板
	 * @CreateTime 2017-8-18 下午7:05:37
	 * @CreateBy 刘兵
	 * @param coverPlateList 接近/盖板集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码WorkCard2Related
	 * @param logopration 操作类型
	 */
	void saveCoverPlateList(List<CoverPlate> coverPlateList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration);
	
	/**
	 * @Description 编辑多个接近/盖板
	 * @CreateTime 2017-8-18 下午7:05:37
	 * @CreateBy 刘兵
	 * @param coverPlateList 接近/盖板集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	void updateCoverPlateList(List<CoverPlate> coverPlateList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration);
	
	/**
	 * @Description 根据ywid删除接近/盖板
	 * @CreateTime 2017-9-13 下午5:16:13
	 * @CreateBy 刘兵
	 * @param ywid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	void deleteByYwid(String ywid, String czls, String zdid, LogOperationEnum logopration);
}
