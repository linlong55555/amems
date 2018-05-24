package com.eray.thjw.basic.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.basic.po.CoverPlateInformation;
import com.eray.thjw.basic.po.ZoneStation;
import com.eray.thjw.exception.BusinessException;

/**
 * @author liub
 * @description 盖板Service
 */
public interface CoverPlateInformationService {
	
	/**
	 * 根据飞机机型查询盖板多选下拉框
	 * @param coverPlateInformation
	 * @return List<CoverPlateInformation>
	 */
	List<CoverPlateInformation> queryMultiselectByFjjx(CoverPlateInformation coverPlateInformation);
	
	/**
	 * @author liub
	 * @description 根据飞机机型查询盖板(弹窗)
	 * @param coverPlateInformation
	 * @return List<CoverPlateInformation>
	 */
	List<CoverPlateInformation> queryWinByFjjx(CoverPlateInformation coverPlateInformation);
	
	/**
	 * 
	 * @Description 经过查询得到盖板列表
	 * @CreateTime 2017年8月31日 上午11:07:37
	 * @CreateBy 李高升
	 * @param coverPlateInformation
	 * @return
	 */
	Map<String, Object> queryCoverPlateList(CoverPlateInformation coverPlateInformation);

	/**
	 * 
	 * @Description 增加盖板 
	 * @CreateTime 2017年8月31日 上午11:39:43
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @throws BusinessException 
	 */
	void addCoverPlate(CoverPlateInformation coverPlateInformation) throws BusinessException;
	
	/**
	 * 
	 * @Description 增加或修改盖板 
	 * @CreateTime 2017年9月14日 下午午13:39:43
	 * @CreateBy 胡才秋
	 * @param zoneStation
	 * @throws BusinessException 
	 */
	boolean saveorUpdate(CoverPlateInformation coverPlateInformation) throws BusinessException;
	
	/**
	 * 
	 * @Description 通过id获取盖板参数 
	 * @CreateTime 2017年9月14日 下午午13:39:43
	 * @CreateBy 胡才秋
	 * @param zoneStation
	 * @throws BusinessException 
	 */
	CoverPlateInformation findDataById(String id) throws BusinessException;
}
