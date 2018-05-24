package com.eray.thjw.aerialmaterial.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.Message;
import com.eray.thjw.exception.BusinessException;


public interface MessageService {
	
	/**
	 * @author liub
	 * @description 保存留言
	 * @param message
	 * @develop date 2016.11.13
	 * @throws BusinessException 
	 */
	public void add(Message message) throws RuntimeException;
	
	/**
	 * @author liub
	 * @description  删除留言
	 * @param id
	 * @return
	 * @develop date 2016.11.12
	 */
	public void delete(String id) throws RuntimeException;
	
	/**
	 * @author liub
	 * @description  根据单据id、提醒人读取留言
	 * @param djid
	 * @return
	 * @develop date 2016.11.15
	 */
	public void updateViewStatusByDjIdUId(String djid) throws RuntimeException;
	
	/**
	 * @author liub
	 * @description  根据条件查询留言信息
	 * @param message
	 * @return List<Message>
	 * @develop date 2016.11.13
	 */
	public List<Message> queryMessageList(Message message) throws RuntimeException;
	
	/**
	 * @author liub
	 * @description  根据单据id集合查询留言信息
	 * @param message
	 * @return List<Message>
	 * @develop date 2016.11.16
	 */
	public List<Message> queryMessageListByDjIdList(List<String> djidList) throws RuntimeException;
	
}
