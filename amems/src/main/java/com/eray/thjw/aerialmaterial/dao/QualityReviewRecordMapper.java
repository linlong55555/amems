package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.QualityReviewRecord;

public interface QualityReviewRecordMapper {
	/**
	 * 新增
	 * @param record
	 */
	void insertRecord(QualityReviewRecord record);
	/**
	 * 根据mainid获取列表
	 * @param mainid
	 * @return
	 */
	List<QualityReviewRecord>selectRecordList(String mainid);
}