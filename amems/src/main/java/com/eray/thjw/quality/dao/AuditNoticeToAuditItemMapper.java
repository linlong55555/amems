package com.eray.thjw.quality.dao;

import com.eray.thjw.quality.po.AuditNoticeToAuditItem;

public interface AuditNoticeToAuditItemMapper {
	
    int insert(AuditNoticeToAuditItem record);

    int insertSelective(AuditNoticeToAuditItem record);
    
    int deleteByShxmdid(String shxmdid);
}