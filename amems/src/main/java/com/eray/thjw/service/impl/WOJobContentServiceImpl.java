package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.WOJobContentMapper;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WOJobContent;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.WOJobContentService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class WOJobContentServiceImpl implements WOJobContentService {
    
	@Resource
	private WOJobContentMapper wOJobContentMapper;
	
   @Resource
    private CommonRecService commonRecService;
	@Override
	public int insertSelective(WorkOrder wrokorder)throws RuntimeException {
		int count=0;
		List<WOJobContent> list=wrokorder.getBaseWorkOrder().getWoJobContent();
		if (list!=null && !list.isEmpty()) {
				User user = ThreadVarUtil.getUser();
				for (WOJobContent woJobContent : list) {
					UUID uuid = UUID.randomUUID();
					String id = uuid.toString();
					woJobContent.setId(id);
					woJobContent.setMainid(wrokorder.getGdjcid());
					woJobContent.setWhdwid(user.getBmdm());
					woJobContent.setWhrid(user.getId());
					woJobContent.setDprtcode(user.getJgdm());
					count=wOJobContentMapper.insertSelective(woJobContent);
					commonRecService.write(id, TableEnum.B_G_00602, user.getId(), wrokorder.getCzls(), wrokorder.getLogOperationEnum(), UpdateTypeEnum.SAVE,wrokorder.getId());
				}		
		}		
		return count;
	}
	
	@Override
	public int doByJobContent(WorkOrder workorder) {
		List<WOJobContent> woJobContentList=workorder.getBaseWorkOrder().getWoJobContent();
		
		int count=0;
		List<String> newGznridlist=new ArrayList<String>();              //新的集合
		List<String> oldGznridslist=new ArrayList<String>();              //旧的集合
		
		WOJobContent wjc=new WOJobContent();
		wjc.setMainid(workorder.getGdjcid());
		List<WOJobContent>  oldGznr=wOJobContentMapper.selectByWOJobContentList(wjc);
		for (WOJobContent woJobContent : oldGznr) {
			oldGznridslist.add(woJobContent.getId());
		}
		User user = ThreadVarUtil.getUser();
		for (WOJobContent woJobContentlist : woJobContentList) {
			newGznridlist.add(woJobContentlist.getId());
			if(!oldGznridslist.contains(woJobContentlist.getId())){
				UUID uuid = UUID.randomUUID();
				String id = uuid.toString();
				woJobContentlist.setId(id);
				woJobContentlist.setMainid(workorder.getGdjcid());
				woJobContentlist.setWhdwid(user.getBmdm());
				woJobContentlist.setWhrid(user.getId());
				woJobContentlist.setDprtcode(user.getJgdm());
				count=wOJobContentMapper.insertSelective(woJobContentlist);
				commonRecService.write(id, TableEnum.B_G_00602, user.getId(), workorder.getCzls(), workorder.getLogOperationEnum(), UpdateTypeEnum.SAVE,workorder.getId());
			}else{
				count=wOJobContentMapper.updateByJobContent(woJobContentlist);
				commonRecService.write(woJobContentlist.getId(), TableEnum.B_G_00602, user.getId(), workorder.getCzls(), workorder.getLogOperationEnum(), UpdateTypeEnum.UPDATE,workorder.getId());
			}
		}
		for (WOJobContent woJobContent : oldGznr) {
			if(!newGznridlist.contains(woJobContent.getId())){
				commonRecService.write(woJobContent.getId(), TableEnum.B_G_00602, user.getId(), workorder.getCzls(), workorder.getLogOperationEnum(), UpdateTypeEnum.DELETE,workorder.getId());
				count=wOJobContentMapper.deleteById(woJobContent.getId());
			}
		}
		return count;
	}
	@Override
	public List<WOJobContent> selectByWOJobContentList(WOJobContent record) {
		return wOJobContentMapper.selectByWOJobContentList(record);
	}

	/**
   	 * @author sunji
   	 * @description 根据基础id查询工作内容
   	 * @develop date 2016.08.15
   	 */
	public List<WOJobContent> selectedGznrList(WOJobContent wOJobContent)
			throws RuntimeException {
		return wOJobContentMapper.selectedGznrList(wOJobContent);
	}
	@Override
	public int deleteById(String id) {
		return wOJobContentMapper.deleteById(id);
	}

	@Override
	public int updateByJobContent(WOJobContent wOJobContent) {
		return wOJobContentMapper.updateByJobContent(wOJobContent);
	}
}
