package com.eray.thjw.flightdata.service;

import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Pagination;

/**
 * b_h_01001 部件拆机记录服务接口
 * @author zhuchao
 *
 */
public interface PartsDisassemblyRecordService {

	/**
	 * 按条件查询一页部件信息
	 * @param param
	 * @param pagination
	 * @return
	 */
	public Map<String, Object> queryPartsInfoPage(Map<String, Object> param , Pagination pagination) throws RuntimeException;
	
	/**
	 * 根据部件号+序列号查询部件信息以及拆装记录和子部件列表
	 * @param param
	 * @return
	 * @throws RuntimeException
	 */
	public Map<String, Object> queryPartsInfo(Map<String, Object> param) throws RuntimeException;

	/**
	 * 根据部件号+序列号查询部件信息以及拆装记录和子部件列表
	 * @param jh 
	 * @param xlh
	 * @param dprtcode
	 * @throws RuntimeException
	 * @throws BusinessException 
	 */
	public Map<String, Object> queryPartsInfo(String jh, String xlh,String dprtcode) throws RuntimeException, BusinessException;
	

}
