package com.eray.thjw.flightdata.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.flightdata.po.PartsDisassemblyRecord;

public interface PartsDisassemblyRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(PartsDisassemblyRecord record);

    int insertSelective(PartsDisassemblyRecord record);

    PartsDisassemblyRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PartsDisassemblyRecord record);

    int updateByPrimaryKey(PartsDisassemblyRecord record);

    /**
     * 查询部件信息列表
     * @param param
     * @return
     */
	List<Map<String, Object>> selectPartsInfoPage(Map<String, Object> param);

	 /**
     * 查询部件信息
     * @param param
     * @return
     */
	Map<String, Object> selectPartsInfo(Map<String, Object> param);
	
	/**
     * 查询子部件列表
     * @param param
     * @return
     */
	List<Map<String, Object>> selectSubcomponents(Map<String, Object> param);
	
	/**
     * 部件拆装记录(已查下)
     * @param param
     * @return
     */
	List<Map<String, Object>> selectDisassemblyRecords(Map<String, Object> param);
	
	/**
     * 部件拆装记录(未查下)
     * @param param
     * @return
     */
	List<Map<String, Object>> selectNotDisassemblyRecords(Map<String, Object> param);
}