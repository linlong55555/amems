package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.QualityReview;

public interface QualityReviewMapper {
	/**
	 * 新增
	 * @param record
	 */
	void insertRecord(QualityReview record);
	/**
	 * 查询列表
	 * @param record
	 * @return
	 */
	List<QualityReview> selectQualityReviewList(QualityReview record);
	/**
	 * 根据id获取信息
	 * @param id
	 * @return
	 */
	QualityReview selectQualityReviewById(String id);
	/**
	 * 更新
	 * @param record
	 */
	void updateQualityReviewById(QualityReview record);
}