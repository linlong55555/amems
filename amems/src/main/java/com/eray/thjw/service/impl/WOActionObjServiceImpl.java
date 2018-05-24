package com.eray.thjw.service.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.WOActionObjMapper;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WOActionObj;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.WOActionObjService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
@Service
public class WOActionObjServiceImpl implements WOActionObjService {
    
	@Resource
	private WOActionObjMapper wOActionObjMapper;
	@Resource
	private CommonRecService commonRecService;

	@Override
	public int insertSelective(WorkOrder wrokorder)throws RuntimeException {
		int count = 0;
		WOActionObj wo= wrokorder.getBaseWorkOrder().getwOActionObj();
		 if (wo!=null) {
			User user = ThreadVarUtil.getUser();
			UUID uuid = UUID.randomUUID();
			String id = uuid.toString();
			wo.setId(id);
			wo.setMainid(wrokorder.getGdjcid());
			wo.setWhdwid(user.getBmdm());
			wo.setWhrid(user.getId());
			wo.setDprtcode(user.getJgdm());
			count =wOActionObjMapper.insertSelective(wo);
			commonRecService.write(id, TableEnum.B_G_00604, user.getId(), wrokorder.getCzls(), wrokorder.getLogOperationEnum(), UpdateTypeEnum.SAVE,wrokorder.getId());
		 }			
		return count;
	}

	@Override
	public int doByActionObj(WorkOrder workorder) {
		  
		 WOActionObj wOActionObj=workorder.getBaseWorkOrder().getwOActionObj();
		 int count = 0;
		 User user = ThreadVarUtil.getUser();
		 if (wOActionObj!=null ) {
		    count=wOActionObjMapper.updateByActionObj(wOActionObj);
			commonRecService.write(wOActionObj.getId(), TableEnum.B_G_00604, user.getId(), workorder.getCzls(), workorder.getLogOperationEnum(), UpdateTypeEnum.UPDATE,workorder.getId());
		 }			
		return count;
	}
	@Override
	public WOActionObj selectByActionObj(WOActionObj woobj)
			throws RuntimeException {
		return wOActionObjMapper.selectByActionObj(woobj);
	}

	@Override
	public int deleteByMainid(String mainid) {
		return wOActionObjMapper.deleteByMainid(mainid);
	}

	@Override
	public int updateByActionObj(WOActionObj woActionObj) {
		return wOActionObjMapper.updateByActionObj(woActionObj);
	}

}
