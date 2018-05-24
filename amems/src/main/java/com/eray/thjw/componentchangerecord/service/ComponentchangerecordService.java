package com.eray.thjw.componentchangerecord.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.PartsDisassemblyRecord;

public interface ComponentchangerecordService {
	/**
	 * @author sunji
	 * @description 根据查询条件分页查询维修记录
	 * @param PartsDisassemblyRecord
	 * @return List<PartsDisassemblyRecord>
	 * @develop date 2016.08.15
	 */
	 public List<PartsDisassemblyRecord> queryPartsDisassemblyRecordList(PartsDisassemblyRecord partsDisassemblyRecord)throws BusinessException;
}
