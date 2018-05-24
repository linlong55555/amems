package com.eray.thjw.productionplan.service.impl;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.JobCardMapper;
import com.eray.thjw.po.BaseWorkOrder;
import com.eray.thjw.po.JobCard;
import com.eray.thjw.po.WOActionObj;
import com.eray.thjw.po.WOAirMaterial;
import com.eray.thjw.po.WOJobContent;
import com.eray.thjw.po.WOJobEnclosure;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.productionplan.dao.CheckBillMapper;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.productionplan.po.ScheduledCheckMonitorItem;
import com.eray.thjw.productionplan.service.CheckBillSerivce;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.productionplan.service.ScheduledCheckMonitorItemService;
import com.eray.thjw.service.WOAirMaterialService;
import com.eray.thjw.service.WOJobContentService;
import com.eray.thjw.service.WOJobEnclosureService;
import com.eray.thjw.service.WorkOrderService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.CheckBillEnum;

@Service
public class CheckBillSerivceImpl implements CheckBillSerivce {
	
	
	@Resource
	private CheckBillMapper checkBillMapper;
	
	@Autowired
	private JobCardMapper jobCardMapper;//定检工作内容
	
	@Autowired
	private WorkOrderService workOrderService;//工单基础表实体  b_g_006 service
	
	@Autowired
	private WOAirMaterialService wOAirMaterialService;//工单基础表实体  b_g_006 service
	
	@Autowired
	private WOJobContentService wOJobContentService;//工单基础表实体  b_g_006 service
	
	@Autowired
	private WOJobEnclosureService wOJobEnclosureService;//工单基础表实体  b_g_006 service
	
	@Autowired
	private ScheduledCheckMonitorItemService scheduledCheckMonitorItemService;//工单基础表实体  b_g_006 service
	
	@Autowired
	private PlaneDataService planeDataService;//工单基础表实体  b_g_006 service
	
	
	/**
	 * save b_g_016 定检工单
	 */
	@Override
	public void save(String bz,String id,String ids,ScheduledCheckItem scheduledCheckItem,String czls) throws Exception{
		PlaneData planeData=new PlaneData();
		planeData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
		planeData.setFjzch(scheduledCheckItem.getFjzch());
		planeData=planeDataService.selectByPrimaryKey(planeData);
		List<JobCard> wList = jobCardMapper.queryListByDjgznrid(scheduledCheckItem.getDjbh(),ids,scheduledCheckItem.getDprtcode());
		if(!wList.isEmpty()){
			for (JobCard jobCard : wList) {
				WorkOrder workOrder=new WorkOrder();
				
				//新增
				workOrder.setZy(jobCard.getZy());
				workOrder.setJhgsRs(jobCard.getJhgsRs());
				workOrder.setJhgsXss(jobCard.getJhgsXss());
				workOrder.setFjzch(scheduledCheckItem.getFjzch());
				if(bz==null){
					workOrder.setXfgdyy("");
				}else{
					workOrder.setXfgdyy(bz);
				}
				
				if(jobCard.getBz()==null){
					workOrder.setBz("");
				}else{
					workOrder.setBz(jobCard.getBz());
				}
				
				workOrder.setZt(CheckBillEnum.APPROVE.getId());
				if(scheduledCheckItem.getJkbz()==null){
					workOrder.setJkbz("");
				}else{
					workOrder.setJkbz(scheduledCheckItem.getJkbz());
				}
				workOrder.setGzzid(jobCard.getGzzId());
				workOrder.setCksc(jobCard.getCksc());
				workOrder.setCkgk(jobCard.getCkgk());
				workOrder.setGzzw(jobCard.getGzzw());
				workOrder.setBcwj(jobCard.getBcwj());
				workOrder.setGzdd(jobCard.getGzdd());
				workOrder.setNbxh(jobCard.getNbxh());
				workOrder.setJd(planeData.getJd());
				workOrder.setZjh(jobCard.getZjh());
				workOrder.setZhut(jobCard.getGzzt());
				workOrder.setDjrwdid(id);
				workOrder.setDjgkid(jobCard.getId());
				workOrder.setZdsj(new Date());
				workOrder.setGddlx(1);
				//String	gdbh=saibongService.SaiBongNumfind(SaiBongEnum.LXGD.getName(), ""); //
				//workOrder.setGdbh(jobCard.getGdjcid()); //根据工卡里的工单基础id查询
				
				BaseWorkOrder baseWorkOrder=new BaseWorkOrder();
				workOrder.setBaseWorkOrder(baseWorkOrder);//工单基础表
				
				//BaseWorkOrder baseWorkOrder1=baseWorkOrderService.selectKey(gdbh);//根据工单编号查询工单基础表信息
				WOAirMaterial wOAirMaterial=new WOAirMaterial();
				wOAirMaterial.setMainid(jobCard.getGdjcid());
				List<WOAirMaterial> woAirMateriallist=wOAirMaterialService.selectedHcList(wOAirMaterial);//根据工单基础表id查询航材耗材工具信息
				baseWorkOrder.setWoAirMaterial(woAirMateriallist);//插入工单航材耗材工具信息
				
				WOJobContent wOJobContent=new WOJobContent();
				wOJobContent.setMainid(jobCard.getGdjcid());
				List<WOJobContent> woJobContentlist=wOJobContentService.selectedGznrList(wOJobContent);//根据工单基础表id查询工作内容的数据
				baseWorkOrder.setWoJobContent(woJobContentlist);//插入工作内容的数据
				
				WOJobEnclosure wOJobEnclosure=new WOJobEnclosure();
				wOJobEnclosure.setMainid(jobCard.getGdjcid());
				List<WOJobEnclosure> woJobenclosurelist=wOJobEnclosureService.selectedFjList(wOJobEnclosure);//根据工单基础表id查询附件表插入数据
				baseWorkOrder.setWoJobenclosure(woJobenclosurelist);//给工单的附件表插入数据
				
				List<ScheduledCheckMonitorItem> list=scheduledCheckMonitorItemService.queryAllList(null);
				StringBuffer jkxmbh = new StringBuffer();
				StringBuffer jkflbh = new StringBuffer();
				StringBuffer jkz = new StringBuffer();
				String[] jkxmbhs=new String[0];
				String[] jkflbhs=new String[0];
				String[] jkzs=new String[0];
				//获取监控项目编号，分类编号，计划值
				for (ScheduledCheckMonitorItem scheduledCheckMonitorItem2 : list) {
					if(scheduledCheckMonitorItem2.getMainid().equals(scheduledCheckItem.getId())){
						jkxmbh.append(scheduledCheckMonitorItem2.getJklbh()+",");
						jkflbh.append(scheduledCheckMonitorItem2.getJkflbh()+",");
						jkz.append(scheduledCheckMonitorItem2.getJhz()+",");	
					}
				}
				jkxmbhs = (jkxmbh.toString()).split(",");//监控项目编号
				jkflbhs = (jkflbh.toString()).split(",");//分类编号
				jkzs = (jkz.toString()).split(",");//分类编号
				
				WOActionObj  wOActionObj=new WOActionObj();
				if(scheduledCheckItem.getBjlx()==1){
					wOActionObj.setZxfl("ZJJ");
				}else{
					wOActionObj.setZxfl("FJ");
				}
				
				wOActionObj.setFjzch(scheduledCheckItem.getFjzch());
				wOActionObj.setZjqdid(scheduledCheckItem.getZjqdid());
				wOActionObj.setBjh(scheduledCheckItem.getJh());
				wOActionObj.setBjxlh(scheduledCheckItem.getXlh());
				
				//监控项目编号
				if(jkxmbhs.length>=1){
					//监控项目编号
					if(jkxmbhs[0]!=null){
						wOActionObj.setJkxmbhF(jkxmbhs[0]);
					}
				}else{
					wOActionObj.setJkxmbhF("");
				}
				if(jkxmbhs.length>=2){
					if(jkxmbhs[1]!=null){
						wOActionObj.setJkxmbhS(jkxmbhs[1]);
					}
				}else{
					wOActionObj.setJkxmbhS("");
				}
				if(jkxmbhs.length==3){
					if(jkxmbhs[2]!=null){
						wOActionObj.setJkxmbhT(jkxmbhs[2]);
					}
				}else{
					wOActionObj.setJkxmbhT("");
				}
				
				if(jkflbhs.length>=1){
					if(jkflbhs[0]!=null){
						wOActionObj.setJkflbhF(jkflbhs[0]);
					}
				}else{
					wOActionObj.setJkflbhF("");
				}
				if(jkflbhs.length>=2){
					if(jkflbhs[1]!=null){
						wOActionObj.setJkflbhS(jkflbhs[1]);
					}
				}else{
					wOActionObj.setJkflbhS("");
				}
				if(jkflbhs.length==3){
					if(jkflbhs[2]!=null){
						wOActionObj.setJkflbhT(jkflbhs[2]);
					}
				}else{
					wOActionObj.setJkflbhT("");
				}
				//监控值
				if(jkzs.length>=1){
					if(jkzs[0]!=null){
						wOActionObj.setJkzF(jkzs[0]);
					}
				}else{
					wOActionObj.setJkzF("");
				}
				if(jkzs.length>=2){
					if(jkzs[1]!=null){
						wOActionObj.setJkzS(jkzs[1]);
					}
				}else{
					wOActionObj.setJkzS("");
				}
				if(jkzs.length==3){
					if(jkzs[2]!=null){
						wOActionObj.setJkzT(jkzs[2]);
					}
				}else{
					wOActionObj.setJkzT("");
				}
				baseWorkOrder.setwOActionObj(wOActionObj);//给执行对象的表插入数据
				workOrder.setCzls(czls);
				workOrderService.insertSelective(workOrder); //生成定检工单
			}
		}
		
	}




}
