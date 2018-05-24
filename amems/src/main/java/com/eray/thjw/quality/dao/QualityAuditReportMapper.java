package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.AuditMembers;
import com.eray.thjw.quality.po.DistributeDepartment;
import com.eray.thjw.quality.po.ProcessRecord;
import com.eray.thjw.quality.po.QualityAuditReport;

public interface QualityAuditReportMapper {
	
	public List<QualityAuditReport> getQualityAuditReportList(QualityAuditReport report);
	
	public int deleteAuditReport(String id);
	
	public int deleteAuditMember(String ywid);
	
	public int deleteDistributeDepartment(String ywid);
		
	public int saveAuditReport(QualityAuditReport report);
	
	public int saveAuditMember(List<AuditMembers> members);
	
	public int saveDistributeDepartment(List<DistributeDepartment> department);
	
	public int saveFlowRecord(ProcessRecord flow);
	
	public int updateAuditReportById(QualityAuditReport report);
	
	public List<String> validatShbhUnique(String zlshbh, String dprtcode,String id);
	
	public QualityAuditReport selectByKey(String id);
	
	public QualityAuditReport selectByQualityReportNum(QualityAuditReport report);
	

}
