package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.ScheduledCheckCard;

public interface ScheduledCheckCardMapper {
    int deleteByPrimaryKey(String id) throws RuntimeException;

    int insert(ScheduledCheckCard record) throws RuntimeException;

    int insertSelective(ScheduledCheckCard record)throws RuntimeException;

    ScheduledCheckCard selectByPrimaryKey(String id)throws RuntimeException;

    int updateByPrimaryKeySelective(ScheduledCheckCard record)throws RuntimeException;

    int updateByPrimaryKey(ScheduledCheckCard record)throws RuntimeException;

	List<ScheduledCheckCard> queryPage(ScheduledCheckCard card)throws RuntimeException;
}