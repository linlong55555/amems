package com.eray.thjw.flightdata.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.po.FlightRecordSheet;
import com.eray.thjw.flightdata.po.LegacyTrouble;

public interface LegacyTroubleService {

	/**
	 * 分页查询下故障保留单
	 * @param legacytrouble
	 * @return
	 * @throws BusinessException 
	 */
	public Map<String, Object> queryList(LegacyTrouble legacytrouble) throws BusinessException;

	/**
	 * 新增一个故障保留单
	 * @param legacyTrouble
	 */
	public void add(LegacyTrouble legacyTrouble) throws BusinessException;
	/**
	 * 编辑一个故障保留单
	 * @param legacyTrouble
	 */
	public void edit(LegacyTrouble legacyTrouble) throws BusinessException;

	/**
	 * 作废一个故障保留单
	 * @param legacyTrouble
	 */
	public Map<String,Object> doCancel(LegacyTrouble legacyTrouble) throws BusinessException;

	/**
	 * 指定结束一个故障保留单
	 * @param legacyTrouble
	 */
	public void doEnd(LegacyTrouble legacyTrouble) throws BusinessException;

	/**
	 * 查询一个故障保留单
	 * @param id
	 * @return
	 */
	public LegacyTrouble load(String id) throws BusinessException;

	/**
	 * 生成工单
	 * @param legacyTrouble
	 */
	public void generateOrder(LegacyTrouble legacyTrouble) throws BusinessException;

	/**
	 * 查询附件列表
	 * @param attachment
	 * @return
	 */
	public List<Attachment> queryListAttachments(Attachment attachment) throws BusinessException;
	
	/**
	 * 根据飞行记录单查询故障保留单
	 * @param sheet
	 * @return
	 */
	public List<LegacyTrouble> queryListByFlightRecord(FlightRecordSheet sheet) throws BusinessException;

	/**
	 * 验证故障保留单号机构内唯一
	 * @param legacyTrouble
	 * @return
	 */
	public Boolean validatorGzbldh(LegacyTrouble legacyTrouble) throws BusinessException;

	/**
	 * 创建故障保留单（带验证）
	 * @param legacyTrouble
	 * @return
	 */
	public Map<String, Object> addValidate(LegacyTrouble legacyTrouble) throws BusinessException;

	/**
	 * 修改故障保留单（带验证）
	 * @param legacyTrouble
	 * @return
	 */
	public Map<String, Object> editValidate(LegacyTrouble legacyTrouble) throws BusinessException;

}
