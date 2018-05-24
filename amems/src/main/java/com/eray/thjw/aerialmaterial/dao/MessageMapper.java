package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.Message;

public interface MessageMapper {
    int deleteByPrimaryKey(String id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
    
    /**
	 * @author liub
	 * @description  根据条件查询留言信息
	 * @param record
	 * @return List<Message>
	 * @develop date 2016.11.13
	 */
	public List<Message> queryMessageList(Message record);
	
	/**
	 * @author liub
	 * @description  根据单据id集合查询留言信息
	 * @param message
	 * @return List<Message>
	 * @develop date 2016.11.16
	 */
	public List<Message> queryMessageListByDjIdList(List<String> djidList);
}