package com.eray.thjw.aerialmaterial.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.QualityReview;

public interface QualityReviewService {
	/**
	 * 新增
	 * @param record
	 * @throws Exception 
	 */
	void insertRecord(QualityReview record) throws Exception;
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
