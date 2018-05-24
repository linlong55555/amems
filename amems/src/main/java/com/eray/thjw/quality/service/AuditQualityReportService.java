package com.eray.thjw.quality.service;

import java.util.List;
import java.util.Map;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.QualityAuditReport;

public interface AuditQualityReportService {

	
public Map<String,Object> getList(QualityAuditReport report) throws BusinessException;

public 	List<String> validatShbhUnique(String zlshbh,String dprtcode,String id) throws BusinessException;

public int save(QualityAuditReport report) throws BusinessException,SaibongException;

public int delete(String id) throws BusinessException;

public QualityAuditReport selectBykey(String key) throws BusinessException;

public QualityAuditReport selectByQualityNum(QualityAuditReport report) throws BusinessException;
	
}
