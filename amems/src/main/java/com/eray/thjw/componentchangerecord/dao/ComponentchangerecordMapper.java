package com.eray.thjw.componentchangerecord.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.flightdata.po.PartsDisassemblyRecord;

public interface ComponentchangerecordMapper {

		/**
		 * @author sunji
		 * @description 根据查询条件分页查询维修记录
		 * @param PartsDisassemblyRecord
		 * @return List<PartsDisassemblyRecord>
		 * @develop date 2016.08.15
		 */
	    List<PartsDisassemblyRecord> queryPartsDisassemblyRecordList(PartsDisassemblyRecord record);
	    /**
	     * @author sunji
	     * @description 查看装上记录
	     * @param PartsDisassemblyRecord
	     * @return map
	     * @develop date 2016.08.15
	     */
	    List<Map<String,Object>> queryMountByid(PartsDisassemblyRecord record);
	    /**
	     * @author sunji
	     * @description 查看拆下记录
	     * @param PartsDisassemblyRecord
	     * @return map
	     * @develop date 2016.08.15
	     */
	    List<Map<String,Object>> queryStrikeByid(PartsDisassemblyRecord record);
}
