package com.eray.thjw.quality.dao;

import com.eray.thjw.quality.po.NoticeToItemlistquery;

public interface NoticeToItemlistqueryMapper {
    int insert(NoticeToItemlistquery record);

    int insertSelective(NoticeToItemlistquery record);
    
    int delete(String shtzdid);
}