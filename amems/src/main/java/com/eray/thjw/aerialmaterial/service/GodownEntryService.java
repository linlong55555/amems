package com.eray.thjw.aerialmaterial.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.GodownEntry;
import com.eray.thjw.aerialmaterial.po.GodownEntryDetail;
import com.eray.thjw.exception.BusinessException;
import com.google.gson.JsonElement;

/**
 * 入库
 * @author ll
 */
public interface GodownEntryService {

	/**
	 * 入库分页
	 * @param borrowApply
	 * @return
	 */
	public List<GodownEntry> queryAllPageList(GodownEntry godownEntry)throws RuntimeException;

	/**
	 * 根据id查询入库单
	 * @param id
	 * @return
	 * @throws RuntimeException
	 */
	public GodownEntry queryGetByid(String id)throws RuntimeException;

	/**
	 * 修改
	 * @param godownEntry
	 * @param GodownEntryDetailList
	 * @return
	 * @throws BusinessException
	 */
	String edit(GodownEntry godownEntry,List<GodownEntryDetail> GodownEntryDetailList)throws BusinessException;

	public String editSave(GodownEntry godownEntry) throws RuntimeException;

	public void editSubmit(GodownEntry godownEntry)  throws RuntimeException;

	void editSubmit(GodownEntry godownEntry,List<GodownEntryDetail> GodownEntryDetailList)throws RuntimeException;

	/**
	 * 撤销
	 * @param id
	 */
	public void cancel(String id)throws BusinessException;

	
}
