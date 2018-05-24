package com.eray.thjw.productionplan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.dao.CheckTaskBillMapper;
import com.eray.thjw.po.CheckTaskBill;
import com.eray.thjw.po.FixedCheckItem;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.productionplan.po.PlanTaskExt;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.productionplan.service.CheckTaskBillService;
import com.eray.thjw.productionplan.service.CheckingContentSerivce;
import com.eray.thjw.productionplan.service.CheckingMonitoringSerivce;
import com.eray.thjw.productionplan.service.PlanTaskService;
import com.eray.thjw.productionplan.service.ScheduledTaskSerivce;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.FixedCheckItemService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.WorkOrderService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.StatusEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class CheckTaskBillServiceImpl implements CheckTaskBillService {
	
	/**
	 * @author ll
	 * @description b_g_014 定检任务主表
	 * @develop date 2016.08.24
	 */
	@Autowired
	private CheckTaskBillMapper checkTaskBillMapper;
	
	@Autowired
	private PlaneModelDataService planeModelDataService;
	/**
	 * @author ll
	 * @description 定检项目主表Service
	 * @develop date 2016.08.24
	 */
	@Autowired
	private FixedCheckItemService fixedCheckItemService;
	
	/**
	 * @author ll
	 * @description 日志service
	 * @develop date 2016.08.24
	 */
	@Autowired
	private CommonRecService commonRecService;
	
	/**
	 * @author ll
	 * @description 采番编号
	 * @develop date 2016.08.24
	 */
	@Autowired
	private SaibongUtilService saibongUtilService;     
	
	@Autowired
	private CheckingMonitoringSerivce checkingMonitoringSerivce;//b_g_01401 定检任务监控项目 service
	
	@Autowired
	private CheckingContentSerivce checkingContentSerivce;//b_g_01402  定检任务工作内容service
	
	@Autowired
	private ScheduledTaskSerivce scheduledTaskSerivce;//b_s_009 计划任务 service
	
	@Resource
	private WorkOrderService workOrderService;      //
	
	@Resource
	private PlanTaskService planTaskService;      //
	
	@Override
	public void doEnd(CheckTaskBill record) throws RuntimeException {
		 //TODO 指定结束定检任务单，结束其下的所有的定检工单
		String czls = UUID.randomUUID().toString();    //历史记录流水号
		
		PlanTaskExt pte=new PlanTaskExt();
		pte.setXggdid(record.getId());
	    pte.setCzls(czls);
	    pte.setZdjsyy(record.getZdjsyy());
	    pte.setLogOperationEnum(LogOperationEnum.GUANBI);
	    try {
			planTaskService.doEndByWorkOrder(pte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*User use=ThreadVarUtil.getUser();
		checkTaskBillMapper.updateByPrimaryKeySelective(record);

		commonRecService.write(record.getId(), TableEnum.B_G_014, use.getId(), czls, LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,null);

		
		WorkOrder workorder=new WorkOrder();
		workorder.setDjrwdid(record.getId());
		workorder.setZdjsrid(record.getZdjsrid());
		workorder.setZdjsyy(record.getZdjsyy());
		workorder.setZt(9);
		List<String>list=new ArrayList<String>();
		list.add(record.getId());
	    workOrderService.doEnd(workorder);

	    commonRecService.write("djrwdid", list, TableEnum.B_G_016, use.getId(), czls,LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,null);*/
	}

	/**
	 * save b_g_014 定检任务主表
	 * @param scheduledCheckItem
	 * @throws Exception
	 */
	@Override
	public void save(ScheduledCheckItem scheduledCheckItem)throws Exception {
		String last4=null;
		User user = ThreadVarUtil.getUser();
		CheckTaskBill checkTaskBill=new CheckTaskBill();
		
		List<String> list = new ArrayList<String>();
		list.add(scheduledCheckItem.getFjzch());
		planeModelDataService.existsAircraft4Expt(user.getId(), user.getUserType(), scheduledCheckItem.getDprtcode(), list);
		
		//新增
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();	
		checkTaskBill.setId(id);
		checkTaskBill.setFjzch(scheduledCheckItem.getFjzch());
		checkTaskBill.setBjh(scheduledCheckItem.getJh());
		checkTaskBill.setXlh(scheduledCheckItem.getXlh());
		FixedCheckItem fixedCheckItem=fixedCheckItemService.selectByPrimaryKey(scheduledCheckItem.getDjxmid());
		String fjzch=scheduledCheckItem.getFjzch();                    //获取飞机注册号的后四位
		
		if(fjzch==null){
			last4="";
		}else{
			last4=fjzch.substring(fjzch.length()-4<=0?0:fjzch.length()-4,fjzch.length());
		}
		try {
			last4 = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.DJRW.getName(), last4);
		} catch (SaibongException e) {
			throw new RuntimeException(e);
		}
		
		//数据来源：b_g_012
		checkTaskBill.setDjxmid(scheduledCheckItem.getDjxmid());
		checkTaskBill.setDjrwbh(last4);
		checkTaskBill.setWxfabh(fixedCheckItem.getWxfabh());
		checkTaskBill.setDjbh(fixedCheckItem.getDjbh());
		checkTaskBill.setBb(fixedCheckItem.getBb());
		if(scheduledCheckItem.getJkbz()!=null){
			checkTaskBill.setJkbz(scheduledCheckItem.getJkbz());
		}else{
			checkTaskBill.setJkbz("");
		}
	
		checkTaskBill.setZdsj(new Date());
		checkTaskBill.setZwms(fixedCheckItem.getZwms());
		if(fixedCheckItem.getYwms()!=null){
			checkTaskBill.setYwms(fixedCheckItem.getYwms());
		}else{
			checkTaskBill.setYwms("");
		}
		
		checkTaskBill.setZyxs(fixedCheckItem.getZyxs());
		checkTaskBill.setJhgsRs(fixedCheckItem.getJhgsRs());
		checkTaskBill.setJhgsXss(fixedCheckItem.getJhgsXss());
		if(fixedCheckItem.getBz()!=null){
			checkTaskBill.setBz(fixedCheckItem.getBz());
		}else{
			checkTaskBill.setBz("");
		}
	
		if(user.getBmdm()==null){
			checkTaskBill.setZdbmid("");
		}else{
			checkTaskBill.setZdbmid(user.getBmdm());
		}
		checkTaskBill.setZdrid(user.getId());
		checkTaskBill.setZt(StatusEnum.EFFECT.getId());
		checkTaskBill.setDprtcode(scheduledCheckItem.getDprtcode());
		
		
		checkTaskBillMapper.save(checkTaskBill);//保存
		
		
		String czls = UUID.randomUUID().toString();
		commonRecService.write(id, TableEnum.B_G_014, user.getId(),czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,scheduledCheckItem.getId());
		
		checkingMonitoringSerivce.save(id,scheduledCheckItem,czls);//save b_g_01401  定检任务监控项目
		
		checkingContentSerivce.save(fixedCheckItem.getZwms(),id,scheduledCheckItem,czls);//save b_g_01402  定检任务工作内容
		
		scheduledTaskSerivce.save(id,last4,scheduledCheckItem,czls); //save b_s_009 计划任务
		
	}
	
	/**
	 * @author liub
	 * @description 检查定检任务主表是否存在数据
	 * @param Map<String, Object>
	 * @return
	 * @develop date 2016.09.26
	 */
	public List<CheckTaskBill> checkIsExist(Map<String, Object> paramMap) throws RuntimeException{
		return checkTaskBillMapper.checkIsExist(paramMap);
	}

	
	/**
	 * @author meizhiliang
	 * @description 分页查询定检任务单的列表
	 * @develop date 2016.09.26
	 */
	@Override
	public List<CheckTaskBill> selectCheckTaskBill(CheckTaskBill record) {
		return checkTaskBillMapper.selectCheckTaskBill(record);
	}

	@Override
	public CheckTaskBill selectByPrimaryKey(String id) {
		return checkTaskBillMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(CheckTaskBill checkTaskBill) {
		checkTaskBillMapper.updateByPrimaryKeySelective(checkTaskBill);
	}

	@Override
	public void updateByPrimaryKeySelective1(CheckTaskBill checkTaskBill) {
		checkTaskBillMapper.updateByPrimaryKeySelective1(checkTaskBill);
	}

	@Override
	public void doEndByPlanTask(CheckTaskBill record) throws RuntimeException {
		User use=ThreadVarUtil.getUser();
		String czls="";
		if(record.getCzls()==null||"".equals(record.getCzls())){
			czls=UUID.randomUUID().toString();    //历史记录流水号
		}else{
			czls=record.getCzls();
		}
		checkTaskBillMapper.updateByPrimaryKeySelective(record);
		commonRecService.write(record.getId(), TableEnum.B_G_014, use.getId(),czls,LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,null);
		WorkOrder workorder=new WorkOrder();
		workorder.setDjrwdid(record.getId());
		workorder.setZdjsrid(record.getZdjsrid());
		workorder.setZdjsyy(record.getZdjsyy());
		workorder.setZt(9);
		List<String>list=new ArrayList<String>();
		list.add(record.getId());
	    workOrderService.doEnd(workorder);
	    commonRecService.write("djrwdid",list, TableEnum.B_G_016, use.getId(),czls,LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,null);
	}

}
