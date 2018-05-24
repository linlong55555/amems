package com.eray.thjw.system.dao;

import java.util.List;

import com.eray.component.saibong.po.SaibongGz;
import com.eray.thjw.exception.BusinessException;

/**
 * 采番管理
 * @author l.l
 *
 */
public interface SaiBongGzMapper {

	/**
	 * 查询采番信息
	 * @param baseEntity
	 * @return
	 * @throws BusinessException 
	 */
	public List<SaibongGz>  queryAllList(SaibongGz saibongGz) throws BusinessException;

	public void updateByPrimaryKeySelective(SaibongGz saibongGz)throws BusinessException;

	public void insertSelective(SaibongGz saibongGz)throws BusinessException;

	public SaibongGz queryKey(String cfkey, String dprtcode)throws BusinessException;

	public String selectPreviewTime(SaibongGz saibongGz);
	
	
}
