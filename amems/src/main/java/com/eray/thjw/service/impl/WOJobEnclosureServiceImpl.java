package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.WOJobEnclosureMapper;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WOJobEnclosure;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.WOJobEnclosureService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class WOJobEnclosureServiceImpl implements WOJobEnclosureService {

	 @Resource
	 private WOJobEnclosureMapper wOJobEnclosureMapper;
	
	 @Resource
	 private CommonRecService commonRecService;
	 
	 @Override
	public int insertSelective(WorkOrder wrokorder) {
		 int count =0;
		 List<WOJobEnclosure> list=wrokorder.getBaseWorkOrder().getWoJobenclosure();
		 if (list!=null && !list.isEmpty()) {
			    User user = ThreadVarUtil.getUser();
			    for (WOJobEnclosure woJobEnclosure2 : list) {
		    		UUID uuid = UUID.randomUUID();      //获取随机的uuid
		    		String id = uuid.toString();
		    		woJobEnclosure2.setId(id);
		    		woJobEnclosure2.setMainid(wrokorder.getGdjcid());
		    		woJobEnclosure2.setCzdwid(user.getBmdm());
		    		woJobEnclosure2.setCzrid(user.getId());
		    		woJobEnclosure2.setDprtcode(user.getJgdm());
		    		count =wOJobEnclosureMapper.insertSelective(woJobEnclosure2);
		    		commonRecService.write(id, TableEnum.B_G_00603, user.getId(), wrokorder.getCzls(), wrokorder.getLogOperationEnum(), UpdateTypeEnum.SAVE,wrokorder.getId());
			    }
		 }	    
		return count;
	}
		@Override
		public int doByWOJobEnclosure(WorkOrder workorder) {
			
			List<WOJobEnclosure> record=workorder.getBaseWorkOrder().getWoJobenclosure();
			int count=0;
			List<String> newFjlist=new ArrayList<String>();              //新的集合
			List<String> oldFjlist=new ArrayList<String>();              //旧的集合
			
			WOJobEnclosure woje=new WOJobEnclosure();
			woje.setMainid(workorder.getGdjcid());
			List<WOJobEnclosure>  oldWOFjlist =wOJobEnclosureMapper.selectedFjList(woje);
			for (WOJobEnclosure woJobEnclosure : oldWOFjlist) {
				oldFjlist.add(woJobEnclosure.getId());
			}
			User user = ThreadVarUtil.getUser();
			for (WOJobEnclosure woJobEnclosure2 : record) {
				newFjlist.add(woJobEnclosure2.getId());
				if(!oldFjlist.contains(woJobEnclosure2.getId())){
					UUID uuid = UUID.randomUUID();
					String id = uuid.toString();
					woJobEnclosure2.setId(id);
					woJobEnclosure2.setMainid(workorder.getGdjcid());
		    		woJobEnclosure2.setCzdwid(user.getBmdm());
		    		woJobEnclosure2.setCzrid(user.getId());
		    		woJobEnclosure2.setDprtcode(user.getJgdm());
		    		count =wOJobEnclosureMapper.insertSelective(woJobEnclosure2);
		    		commonRecService.write(id, TableEnum.B_G_00603, user.getId(), workorder.getCzls(), workorder.getLogOperationEnum(), UpdateTypeEnum.SAVE,workorder.getId());
				}
			}
			for (WOJobEnclosure woJobEnclosure : oldWOFjlist) {
				if(!newFjlist.contains(woJobEnclosure.getId())){
					commonRecService.write(woJobEnclosure.getId(), TableEnum.B_G_00603, user.getId(), workorder.getCzls(), workorder.getLogOperationEnum(), UpdateTypeEnum.DELETE,workorder.getId());
					count=wOJobEnclosureMapper.deleteById(woJobEnclosure.getId());
				}
			}
			return count;
		}

	@Override
	public List<WOJobEnclosure> selectedFjList(WOJobEnclosure wOJobEnclosure) {
		return wOJobEnclosureMapper.selectedFjList(wOJobEnclosure);
	}

	@Override
	public int updateByWOJobEnclosure(WOJobEnclosure record) {
		return wOJobEnclosureMapper.updateByWOJobEnclosure(record);
	}

	@Override
	public int deleteById(String id) {
		return wOJobEnclosureMapper.deleteById(id);
	}
	@Override
	public WOJobEnclosure selectByPrimaryKey(String id) {
		return wOJobEnclosureMapper.selectByPrimaryKey(id);
	}

}
