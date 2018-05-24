package com.eray.thjw.system.dao;

import java.util.Date;
import java.util.List;

import com.eray.thjw.system.po.Message;

public interface SystemMessageMapper {
    int deleteByPrimaryKey(String id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
    
    /**
     * 查询 在有效期的公告
     * @return
     */
    List<Message> selectEffective(String dprtcode);
    
    List<Message>selectMessageList(Message record);
    
    void insertMessage(Message record);
    
    void updateMessageByPrimaryKey(Message record);
    
    void updateZtByPrimaryKey(Message record);
    
    void updateToClose(Message record);
    
    Date getYxqKsById(Message record);
}