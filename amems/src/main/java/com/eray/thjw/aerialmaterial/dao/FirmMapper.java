package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Firm;

public interface FirmMapper {
	int deleteByPrimaryKey(String id);

	int insert(Firm record);

	int insertSelective(Firm record);

	Firm selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Firm record);

	int updateByPrimaryKey(Firm record);

	/**
	 * @author liub
	 * @description 检查厂商是否存在
	 * @param record
	 * @return List<Firm>
	 */
	List<Firm> checkFirm(Firm record);

	/**
	 * @author liub
	 * @description 根据查询条件分页查询厂商信息
	 * @param record
	 * @return List<Firm>
	 */
	public List<Firm> queryAllPageList(Firm record);
	
	/**
	 * 
	 * @Description 根据查询条件分页查询厂商信息pop
	 * @CreateTime 2017年11月10日 上午10:14:35
	 * @CreateBy 朱超
	 * @param record
	 * @return
	 */
	public List<Firm> queryPageListPop(Firm record);
	

	/**
	 * @author liub
	 * @description 根据查询条件查询供应商信息
	 * @param record
	 * @return List<Firm>
	 */
	public List<Firm> queryFirmList(Firm record);

	/**
	 * @author liub
	 * @description 根据查询条件分页查询供应商信息(弹窗)
	 * @param record
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryWinAllPageList(Firm record);

	/**
	 * @author liub
	 * @description 作废
	 * @param record
	 */
	void cancel(Firm record);

	/**
	 * 批量插入供应商
	 * 
	 * @return
	 */
	int batchInsert(List<Firm> persons);

	/**
	 * 批量修改供应商
	 * 
	 * @return
	 */
	int batchUpdate(List<Firm> persons);

	/**
	 * 
	 * @Description 获取该组织机构下有效的外委供应商
	 * @CreateTime 2017年9月27日 上午11:24:02
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	List<Firm> getwwFirmList(String dprtcode);
	/**
	 * 
	 * @Description 获取该组织机构下状态为有效的所有供应商
	 * @CreateTime 2017年11月30日 下午2:20:59
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 */
	List<Firm> queryAllFirmList(Firm record);
}