package com.eray.thjw.aerialmaterial.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.MessageMapper;
import com.eray.thjw.aerialmaterial.dao.MessageRecipientsMapper;
import com.eray.thjw.aerialmaterial.po.Message;
import com.eray.thjw.aerialmaterial.po.MessageRecipients;
import com.eray.thjw.aerialmaterial.service.MessageService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.util.ThreadVarUtil;

import enu.common.EffectiveEnum;

/**
 * @author liub
 * @description 留言service,用于业务逻辑处理
 * @develop date 2016.11.13
 */
@Service
public class MessageServiceImpl implements MessageService {
	
	/**
	 * @author liub
	 * @description 留言消息mapper
	 * @develop date 2016.11.13
	 */
	@Autowired
	private MessageMapper messageMapper;
	/**
	 * @author liub
	 * @description 留言-接收人mapper
	 * @develop date 2016.11.13
	 */
	@Resource
	private MessageRecipientsMapper messageRecipientsMapper;
	
	
	/**
	 * @author liub
	 * @description 保存留言
	 * @param message
	 * @develop date 2016.11.13
	 * @throws BusinessException 
	 */
	@Override
	public void add(Message message) throws RuntimeException{
		User user = ThreadVarUtil.getUser();
		//保存留言
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		message.setId(id);
		message.setYswjm(user.getId());
		message.setDprtcode(user.getBmdm());
		message.setYxzt(EffectiveEnum.YES.getId());
		messageMapper.insertSelective(message);
		//保存接收人
		List<MessageRecipients> messageRecipientsList = message.getMessageRecipientsList();
		for (MessageRecipients messageRecipients : messageRecipientsList) {
			UUID uuid2 = UUID.randomUUID();//获取随机的uuid
			String mrId = uuid2.toString();
			messageRecipients.setId(mrId);
			messageRecipients.setMainid(id);
			messageRecipients.setZt(EffectiveEnum.NO.getId());
			messageRecipientsMapper.insertSelective(messageRecipients);
		}
	}
	
	/**
	 * @author liub
	 * @description  删除留言
	 * @param id
	 * @return
	 * @develop date 2016.11.12
	 */
	@Override
	public void delete(String id) throws RuntimeException{
		messageMapper.deleteByPrimaryKey(id);
		messageRecipientsMapper.deleteByMainId(id);
	}
	
	/**
	 * @author liub
	 * @description  根据单据id、提醒人读取留言
	 * @param djid
	 * @return
	 * @develop date 2016.11.15
	 */
	@Override
	public void updateViewStatusByDjIdUId(String djid) throws RuntimeException{
		User user = ThreadVarUtil.getUser();
		messageRecipientsMapper.updateViewStatusByDjIdUId(djid,user.getId());
	}
	
	/**
	 * @author liub
	 * @description  根据条件查询留言信息
	 * @param message
	 * @return List<Message>
	 * @develop date 2016.11.13
	 */
	@Override
	public List<Message> queryMessageList(Message message) throws RuntimeException{
		return messageMapper.queryMessageList(message);
	}
	
	/**
	 * @author liub
	 * @description  根据单据id集合查询留言信息
	 * @param message
	 * @return List<Message>
	 * @develop date 2016.11.16
	 */
	@Override
	public List<Message> queryMessageListByDjIdList(List<String> djidList) throws RuntimeException{
		return messageMapper.queryMessageListByDjIdList(djidList);
	}
	
}
