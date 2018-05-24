package com.eray.thjw.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.BaseWorkOrderMapper;
import com.eray.thjw.po.BaseWorkOrder;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.service.BaseWorkOrderService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class BaseWorkOrderServiceImpl implements BaseWorkOrderService {

	@Resource
	private BaseWorkOrderMapper baseWorkOrderMapper;
	
	@Resource
	private CommonRecService commonRecService;                   //REC表服务
	
	@Override
	public int insertSelective(WorkOrder workorder) throws RuntimeException {
		User user = ThreadVarUtil.getUser();
		BaseWorkOrder record=workorder.getBaseWorkOrder();
		record.setId(workorder.getGdjcid());
		record.setGdbh(workorder.getGdbh());
		record.setZddwid(user.getBmdm());
		record.setDprtcode(user.getJgdm());
		record.setZdrid(user.getId());
		int count=baseWorkOrderMapper.insertSelective(record);
		
		commonRecService.write(workorder.getGdjcid(), TableEnum.B_G_006, user.getId(), workorder.getCzls(), workorder.getLogOperationEnum(), UpdateTypeEnum.SAVE,workorder.getId());
		return count;
	}

	@Override
	public BaseWorkOrder selectKey(String gdbh) throws RuntimeException {
		
		return baseWorkOrderMapper.selectKey(gdbh);
	}

	

}
