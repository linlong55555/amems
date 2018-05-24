package com.eray.thjw.aerialmaterial.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.GodownEntryDetail;

/**
 * 入库单附表
 * @author ll
 */
public interface GodownEntryDetailService {


	/**
	 * 根据mainid查询航材信息
	 * @param id
	 * @return
	 * @throws RuntimeException
	 */
	public List<GodownEntryDetail> queryGetreceiptSheetDetails(String id)throws RuntimeException;

	public void updateByPrimaryKeySelective(GodownEntryDetail godownEntryDetail);

	public void insertSelective(GodownEntryDetail godownEntryDetail);

	public GodownEntryDetail selectByPrimaryKey(String id);

	public GodownEntryDetail selectByPrimary(
			GodownEntryDetail godownEntryDetail1);

	
}
