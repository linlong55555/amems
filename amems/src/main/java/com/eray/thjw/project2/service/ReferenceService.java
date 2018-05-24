package com.eray.thjw.project2.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.Reference;

import enu.LogOperationEnum;


/**
 * @Description 相关参考文件service
 * @CreateTime 2017-8-19 下午4:41:25
 * @CreateBy 刘兵
 */
public interface ReferenceService {

	/**
	 * 
	 * @Description  新增参考文件
	 * @CreateTime 2017年8月19日 下午4:51:36
	 * @CreateBy 林龙
	 * @param referenceList 参考文件list集合
	 * @param mainid 技术评估单id
	 * @param czls 流水号
	 * @throws BusinessException
	 */
	public void insertReference(List<Reference> referenceList, String mainid, String czls)throws BusinessException;
	
	/**
	 * @Description 保存多个相关参考文件
	 * @CreateTime 2017-8-19 下午3:09:13
	 * @CreateBy 刘兵
	 * @param referenceList 相关参考文件集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	void saveReferenceList(List<Reference> referenceList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration);
	
	/**
	 * @Description 编辑多个相关参考文件
	 * @CreateTime 2017-8-19 下午4:15:15
	 * @CreateBy 刘兵
	 * @param referenceList 相关参考文件集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	void updateReferenceList(List<Reference> referenceList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration);
	
	/**
	 * @Description 根据ywid删除相关参考文件
	 * @CreateTime 2017-9-13 下午5:16:13
	 * @CreateBy 刘兵
	 * @param ywid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	void deleteByYwid(String ywid, String czls, String zdid, LogOperationEnum logopration);
	
	/**
	 * @Description 根据条件查询相关参考文件列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param reference 相关参考文件
	 * @return List<Reference> 相关参考文件集合
	 */
	List<Reference> queryAllList(Reference reference);

	/**
	 * 
	 * @Description 根据ywid 技术评估单id删除 相关文件信息集合
	 * @CreateTime 2017年8月24日 下午3:35:10
	 * @CreateBy 林龙
	 * @param ywid 技术评估单id
	 * @param czls 流水号
	 */
	public void deleteReference(String ywid, String czls);
}
