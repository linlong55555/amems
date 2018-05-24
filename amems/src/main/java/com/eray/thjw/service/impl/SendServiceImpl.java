package com.eray.thjw.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.SendMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.Send;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.SendService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class SendServiceImpl implements SendService{

	@Autowired
	private SendMapper sendMapper;
	@Autowired
	private CommonRecService commonRecService;
	
	@Override
	public List<Send> querySendAll(Send record) {
		return sendMapper.querySendAll(record);
	}

	@Override
	public int insert(Send record) {
		return sendMapper.insert(record);
	}

	@Override
	public int insertSelective(Send record) {
		return sendMapper.insertSelective(record);
	}
	
	@Override
	public int deleteSend(String mainid) {
		return sendMapper.deleteSend(mainid);
	}

	/**
	 * @author sunji
	 * @description 根据条件查询
	 * @param request,model
	 * @return list
	 * @develop date 2016.08.05
	 */
	public List<Send> queryAll(Send send) throws BusinessException {
		return sendMapper.queryAll(send);
	}

	@Override
	public List<Send> selectByPrimaryKey(Send send) {
		return sendMapper.selectByPrimaryKey(send);
	}

	@Override
	public int delete(Send send) {
		return sendMapper.delete(send);
	}

	@Override
	public void updateByPrimaryKeySelective(Send send) {
		sendMapper.updateByPrimaryKeySelective(send);
	}

	@Override
	public void updateByMainid(Send send) {
		sendMapper.updateByMainid(send);
	}

	/**
	 * @author sunji
	 * @description 根据mainid和jsrid修改接收状态
	 * @param request,model
	 * @return void
	 * @develop date 2016.08.05
	 */
	public void updateJszt(Send send) {
		User user = ThreadVarUtil.getUser();
		String uuid=UUID.randomUUID().toString(); 
		sendMapper.updateJszt(send);
		
		Annunciate annunciate=new Annunciate();
		annunciate.setId(send.getMainid());
		commonRecService.write(annunciate.getId(), TableEnum.B_G_002, user.getId(), uuid, annunciate.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,annunciate.getId());
		String str="mainid ='"+ send.getMainid()+ "' and jsrid='"+send.getJsrid()+"'";
		commonRecService.writeByWhere(str, TableEnum.B_G_00201, user.getId(), uuid,send.getLogOperationEnum().EDIT,UpdateTypeEnum.UPDATE,annunciate.getId());
	}



	
	
}
