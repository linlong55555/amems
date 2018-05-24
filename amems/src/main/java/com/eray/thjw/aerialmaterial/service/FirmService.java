package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Firm;
import com.eray.thjw.exception.BusinessException;

public interface FirmService {

	/**
	 * @author liub
	 * @description 增加厂商
	 * @param firm
	 * @throws BusinessException
	 */
	public String add(Firm firm) throws BusinessException;

	/**
	 * @author liub
	 * @description 修改厂商
	 * @param firm
	 * @throws BusinessException
	 */
	public void edit(Firm firm) throws BusinessException;

	/**
	 * @author liub
	 * @description 作废
	 * @param id
	 */
	public void cancel(String id);

	/**
	 * @author liub
	 * @description 根据查询条件分页查询厂商信息
	 * @param firm
	 * @return List<Firm>
	 */
	public List<Firm> queryAllPageList(Firm firm);
	
	/**
	 * 
	 * @Description 外委供应商弹窗
	 * @CreateTime 2017年11月10日 上午10:09:59
	 * @CreateBy 朱超
	 * @param firm
	 * @return
	 */
	public List<Firm> queryPageListPop(Firm firm);
	

	/**
	 * @author liub
	 * @description 根据查询条件分页查询供应商信息(弹窗)
	 * @param firm
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryWinAllPageList(Firm firm);

	/**
	 * @author liub
	 * @description 根据主键id查询厂商信息
	 * @param id
	 * @return Firm
	 */
	public Firm selectByPrimaryKey(String id);

	/**
	 * @author liub
	 * @description 根据查询条件查询供应商信息
	 * @param firm
	 * @return List<Firm>
	 */
	public List<Firm> queryFirmList(Firm firm);

	/**
	 * 
	 * @Description 获取组织机构下的有效的外委供应商
	 * @CreateTime 2017年9月27日 上午11:24:47
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	List<Firm> getwwFirmList(String dprtcode);
}
