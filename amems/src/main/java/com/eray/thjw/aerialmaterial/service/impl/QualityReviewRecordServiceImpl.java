package com.eray.thjw.aerialmaterial.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.QualityReviewRecordMapper;
import com.eray.thjw.aerialmaterial.po.QualityReviewRecord;
import com.eray.thjw.aerialmaterial.service.QualityReviewRecordService;

@Service
public class QualityReviewRecordServiceImpl implements QualityReviewRecordService {
	@Autowired
	private QualityReviewRecordMapper qualityReviewRecordMapper;

	@Override
	public void insertRecord(QualityReviewRecord record) {
		
		qualityReviewRecordMapper.insertRecord(record);
	}

	@Override
	public List<QualityReviewRecord> selectRecordList(String mainid) {
		
		return qualityReviewRecordMapper.selectRecordList(mainid);
	}
	
	
}
