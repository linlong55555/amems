package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.NonWOCardMapper;
import com.eray.thjw.po.NonWOCard;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.NonWOCardService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class NonWOCardServiceImpl implements NonWOCardService {
	@Resource
	private NonWOCardMapper nonWOCardMapper;
	@Resource
	private CommonRecService commonRecService;
	@Override
	public int insertSelective(WorkOrder workorder)throws RuntimeException {
		int count =0;
		List<NonWOCard> list=workorder.getBaseWorkOrder().getNonwocardList();
		if (list!=null && !list.isEmpty()) {
				User user = ThreadVarUtil.getUser();
				for (NonWOCard nonWOCard : list) {
					UUID uuid = UUID.randomUUID();
					String id = uuid.toString();
					nonWOCard.setId(id);
					nonWOCard.setMainid(workorder.getGdjcid());
					nonWOCard.setWhdwid(user.getBmdm());
					nonWOCard.setWhrid(user.getId());
					nonWOCard.setZt(1);
					nonWOCard.setWhsj(new Date());
					nonWOCard.setDprtcode(user.getJgdm());
					count=nonWOCardMapper.insertSelective(nonWOCard);
					commonRecService.write(id, TableEnum.B_G_00605, user.getId(), workorder.getCzls(), workorder.getLogOperationEnum(), UpdateTypeEnum.SAVE,workorder.getId());
				}
		}		
		return count;
	}
	
	@Override
	public int doByWOCard(WorkOrder workorder) {
		List<NonWOCard> nonwocardList=workorder.getBaseWorkOrder().getNonwocardList();
		int count =0;
		List<String> newXggdidlist=new ArrayList<String>();              //新的集合
		List<String> oldXggdlist=new ArrayList<String>();              //旧的集合
		
		NonWOCard nw=new NonWOCard();
		nw.setMainid(workorder.getGdjcid());
		List<NonWOCard>  NWXggdidlist =nonWOCardMapper.selectByNonWOCardList(nw);
		for (NonWOCard nonWOCard : NWXggdidlist) {
			oldXggdlist.add(nonWOCard.getId());
		}
		User user = ThreadVarUtil.getUser();
		for (NonWOCard nonwocardList2 : nonwocardList) {
			newXggdidlist.add(nonwocardList2.getId());
			if(!oldXggdlist.contains(nonwocardList2.getId())){
				UUID uuid = UUID.randomUUID();
				String id = uuid.toString();
				nonwocardList2.setId(id);
				nonwocardList2.setMainid(workorder.getGdjcid());
				nonwocardList2.setWhdwid(user.getBmdm());
				nonwocardList2.setWhrid(user.getId());
				nonwocardList2.setZt(1);
			    count=nonWOCardMapper.insertSelective(nonwocardList2);
			    commonRecService.write(id, TableEnum.B_G_00605, user.getId(), workorder.getCzls(), workorder.getLogOperationEnum(), UpdateTypeEnum.SAVE,workorder.getId());
			}
		}
		for (NonWOCard nonWOCard : NWXggdidlist) {
			if(!newXggdidlist.contains(nonWOCard.getId())){
				commonRecService.write(nonWOCard.getId(), TableEnum.B_G_00605, user.getId(), workorder.getCzls(), workorder.getLogOperationEnum(), UpdateTypeEnum.DELETE,workorder.getId());
				count=nonWOCardMapper.deleteById(nonWOCard.getId());
			}
		}
		return count;
	}
	@Override
	public List<NonWOCard> selectByNonWOCardList(NonWOCard record) {
		return nonWOCardMapper.selectByNonWOCardList(record);
	}
	/**
	 * @author sunji
	 * @description 根据基础id查询相关工单（卡）
	 * @develop date 2016.08.15
	 */
	public List<NonWOCard> selectedXggkList(NonWOCard nonWOCard)
			throws RuntimeException {
		return nonWOCardMapper.selectedXggkList(nonWOCard);
	}

	@Override
	public int deleteById(String id) {
		return nonWOCardMapper.deleteById(id);
	}
}
