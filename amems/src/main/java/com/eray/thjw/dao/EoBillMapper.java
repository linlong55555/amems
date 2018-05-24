package com.eray.thjw.dao;

import com.eray.thjw.po.EoBill;


public interface EoBillMapper {
    int deleteByPrimaryKey(String id);

    int insert(EoBill record);

    int insertSelective(EoBill record);

    EoBill selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EoBill record);

    int updateByPrimaryKey(EoBill record);

    /**
     * 指定结束
     * @param eoBill
     */
	public void doEnd(EoBill eoBill);
}