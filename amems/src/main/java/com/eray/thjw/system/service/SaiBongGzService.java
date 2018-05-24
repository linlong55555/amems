package com.eray.thjw.system.service;

import java.util.List;

import com.eray.component.saibong.po.SaibongGz;
import com.eray.thjw.exception.BusinessException;

/**
 * 采番管理
 * @author l.l
 *
 */
public interface SaiBongGzService {

	/**
	 * 查询采番信息
	 * @param baseEntity
	 * @return
	 * @throws BusinessException 
	 */
	public List<SaibongGz> queryAllList(SaibongGz saibongGz) throws BusinessException;

	public void update(SaibongGz saibongGz)throws BusinessException;

	public String previewSaibong(SaibongGz saibongGz)throws BusinessException;
	/**
	 * 
	 * @Description 获取流水长度
	 * @CreateTime 2017年12月5日 下午4:03:53
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @param key
	 * @return
	 * @throws BusinessException 
	 */
	int getLength4Lscd(String dprtcode,String key) throws BusinessException;
}
