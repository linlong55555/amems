package com.eray.thjw.dao;

import com.eray.thjw.po.RevisionNoticeBookLog;

public interface RevisionNoticeBookLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(RevisionNoticeBookLog record);

    int insertSelective(RevisionNoticeBookLog record);

    RevisionNoticeBookLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RevisionNoticeBookLog record);

    int updateByPrimaryKey(RevisionNoticeBookLog record);
}