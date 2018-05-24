package com.eray.thjw.productionplan.service.impl;


import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.FixedCheckWorkContentMapper;
import com.eray.thjw.dao.FixedCheckWorkPlaneMapper;
import com.eray.thjw.po.FixedCheckWorkContent;
import com.eray.thjw.po.FixedCheckWorkPlane;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.CheckingContentMapper;
import com.eray.thjw.productionplan.po.CheckingContent;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.productionplan.service.CheckBillSerivce;
import com.eray.thjw.productionplan.service.CheckingContentSerivce;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.WorkOrderService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.CheckingContentEnum;
import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class CheckingContentSerivceImpl implements CheckingContentSerivce {
	
	
	@Resource
	private CheckingContentMapper checkingContentMapper;
	
	@Autowired
	private CommonRecService commonRecService;//日志service
	
	@Resource
	private FixedCheckWorkContentMapper checkWorkContentMapper;
	
	@Autowired
	private CheckBillSerivce checkBillSerivce;//b_g_016 定检工单 service
	
	@Autowired
	private WorkOrderService workOrderService;//b_g_016 定检工单 service
	
	@Autowired
	private FixedCheckWorkPlaneMapper fixedCheckWorkPlaneMapper;//定检工作对应飞机关系表

	
	/**
	 * save b_g_01402  定检任务工作内容
	 */
	@Override
	public void save(String bz,String id,ScheduledCheckItem scheduledCheckItem,String czls) throws Exception{
		User user = ThreadVarUtil.getUser();
		
		CheckingContent checkingContent=new CheckingContent();
		
		List<FixedCheckWorkContent> wList = checkWorkContentMapper.queryListSelective(scheduledCheckItem.getDjxmid());//查询所有定检工作内容
		
		for (FixedCheckWorkContent workContent : wList) {
			//新增
			UUID uuid = UUID.randomUUID();
			String ids = uuid.toString();	
			checkingContent.setId(ids);
			checkingContent.setMainid(id);
			checkingContent.setNbxh(workContent.getNbxh());
			checkingContent.setZjh(workContent.getZjh());
			
			if(workContent.getWz()==null){
				checkingContent.setWz("");
			}else{
				checkingContent.setWz(workContent.getWz());
			}
			
			
			if(workContent.getCksc()==null){
				checkingContent.setCksc("");
			}else{
				checkingContent.setCksc(workContent.getCksc());
			}
		
			if(workContent.getCkgk()==null){
				checkingContent.setCkgk("");	
			}else{
				checkingContent.setCkgk(workContent.getCkgk());
			}
			
			if(workContent.getXmly()==null){
				checkingContent.setXmly("");
			}else{
				checkingContent.setXmly(workContent.getXmly());
			}
			if(workContent.getGzlx()==null){
				checkingContent.setGzlx("");
			}else{
				checkingContent.setGzlx(workContent.getGzlx());
			}
			if(workContent.getScmsZw()==null){
				checkingContent.setScmsZw("");
			}else{
				checkingContent.setScmsZw(workContent.getScmsZw());
			}
			if(workContent.getScmsYw()==null){
				checkingContent.setScmsYw("");
			}else{
				checkingContent.setScmsYw(workContent.getScmsYw());
			}
			if(workContent.getJclx()==null){
				checkingContent.setJclx("");
			}else{
				checkingContent.setJclx(workContent.getJclx());
			}
			
			if(workContent.getFjzch().equals("-")){
				List<FixedCheckWorkPlane> fixedCheckWorkPlaneList=fixedCheckWorkPlaneMapper.selectByMainid(workContent.getId());
				StringBuffer str=new StringBuffer();
				for (FixedCheckWorkPlane fixedCheckWorkPlane : fixedCheckWorkPlaneList) {
					str.append(fixedCheckWorkPlane.getFjzch()+",");
				}
				checkingContent.setFjzch(str.substring(0,str.length()-1));
			}else{
				checkingContent.setFjzch(workContent.getFjzch());
			}
			
			if(workContent.getGzzw()==null){
				checkingContent.setGzzw("");
			}else{
				checkingContent.setGzzw(workContent.getGzzw());
			}
			
			if(workContent.getWz()==null){
				checkingContent.setWz("");
			}else{
				checkingContent.setWz(workContent.getWz());
			}
			
			checkingContent.setIsBj(workContent.getIsBj());
			checkingContent.setIsMi(workContent.getIsMi());
			if(workContent.getBz()==null){
				checkingContent.setBz("");
			}else{
				checkingContent.setBz(workContent.getBz());
			}
			
			checkingContent.setZt(CheckingContentEnum.EFFECT.getId());
			if(user.getBmdm()==null){
				checkingContent.setWhbmid("");
			}else{
				checkingContent.setWhbmid(user.getBmdm());
			}
			checkingContent.setWhrid(user.getId());
			checkingContent.setWhsj("");
			checkingContent.setDprtcode(scheduledCheckItem.getDprtcode());
			
			
			checkingContentMapper.save(checkingContent);
			commonRecService.write(ids, TableEnum.B_G_01402, user.getId(),czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,null);
			checkBillSerivce.save( bz,id,workContent.getNbxh(),scheduledCheckItem,czls);//save b_g_016 定检工单
			
			
		}
		
	}
	@Override
	public List<CheckingContent> selectByPrimaryKey(String mainid) {
		return checkingContentMapper.selectByPrimaryKey(mainid);
	}
}
