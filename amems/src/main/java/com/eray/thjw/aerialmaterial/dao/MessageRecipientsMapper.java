package com.eray.thjw.aerialmaterial.dao;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.aerialmaterial.po.MessageRecipients;

public interface MessageRecipientsMapper {
    int deleteByPrimaryKey(String id);

    int insert(MessageRecipients record);

    int insertSelective(MessageRecipients record);

    MessageRecipients selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MessageRecipients record);

    int updateByPrimaryKey(MessageRecipients record);
    
    /**
	 * @author liub
	 * @description  根据mainid删除接收人信息
	 * @param mainid
	 * @return int
	 * @develop date 2016.11.14
	 */
    int deleteByMainId(String mainid);
    
    /**
	 * @author liub
	 * @description  根据单据id、提醒人读取留言
	 * @param djid,jsrid
	 * @return int
	 * @develop date 2016.11.15
	 */
    int updateViewStatusByDjIdUId(@Param("djid")String djid,@Param("jsrid")String jsrid);
}