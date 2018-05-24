package com.eray.thjw.system.dao;

import java.util.List;

import com.eray.thjw.system.po.SysLog;

public interface SysLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

	int queryCount(SysLog sysLog);

	List<SysLog> queryAllPageList(SysLog sysLog);
}