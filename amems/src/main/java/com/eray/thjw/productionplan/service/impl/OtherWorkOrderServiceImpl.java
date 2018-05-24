package com.eray.thjw.productionplan.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.productionplan.dao.OtherWorkOrderMapper;
import com.eray.thjw.productionplan.dao.ScheduledTaskMapper;
import com.eray.thjw.productionplan.po.OtherWorkOrder;
import com.eray.thjw.productionplan.po.PlaneInitData;
import com.eray.thjw.productionplan.po.ScheduledTask;
import com.eray.thjw.productionplan.service.OtherWorkOrderService;
import com.eray.thjw.productionplan.service.PlaneInitDataService;
import com.eray.thjw.productionplan.service.ScheduledTaskSerivce;
import com.eray.thjw.productionplan.service.TimeControllWareItemService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.WorkOrderService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.TimeOperationEnum;
import enu.TimeSeparatorEnum;
import enu.UpdateTypeEnum;


@Service
public class OtherWorkOrderServiceImpl implements OtherWorkOrderService {
	DecimalFormat df = new DecimalFormat("0.00");	

	Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@Autowired
	private OtherWorkOrderMapper otherWorkOrderMapper;
	@Autowired
	private PlaneModelDataService planeModelDataService;
	
	@Autowired
	private TimeControllWareItemService timeControllWareItemService;//b_g_014 定检任务主表 service
	
	@Autowired
	private WorkOrderService workOrderService;//b_g_014 定检任务主表 service
	
	@Autowired
	private ScheduledTaskMapper scheduledTaskMapper;//saibong编号
	
	@Autowired
	private CommonRecService commonRecService;//saibong编号
	
	@Autowired
	private ScheduledTaskSerivce scheduledTaskSerivce;//saibong编号
	
	@Autowired
	private PlaneInitDataService planeInitDataService;//saibong编号
	
	@Override
	public List<OtherWorkOrder> queryAllList(OtherWorkOrder otherWorkOrder) throws Exception {
		return otherWorkOrderMapper.queryAllList(otherWorkOrder);
	}

	/**
	 * 格式化计划，剩余，剩余天数
	 */
	@Override
	public OtherWorkOrder canshu(OtherWorkOrder otherWorkOrder2,PlaneModelData planeModelData)
			throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		StringBuffer jihua=new StringBuffer();//计划值
		StringBuffer shengyu=new StringBuffer(); //剩余
		String shengyut=null;
		//监控1
		if(otherWorkOrder2.getMs_f()!=null&&!otherWorkOrder2.getMs_f().equals("")){
			
			jihua.append(otherWorkOrder2.getMs_f()+":"+otherWorkOrder2.getJkz_f()+",");
			shengyu.append(otherWorkOrder2.getMs_f()+":"+shengyu1(otherWorkOrder2)+",");
			resultMap.put(otherWorkOrder2.getJkxmbh_f(), shengyu1(otherWorkOrder2));
		}
		//监控2
		if(otherWorkOrder2.getMs_s()!=null&&!otherWorkOrder2.getMs_s().equals("")){
			
			jihua.append(otherWorkOrder2.getMs_s()+":"+otherWorkOrder2.getJkz_s()+",");
			shengyu.append(otherWorkOrder2.getMs_s()+":"+shengyu2(otherWorkOrder2)+",");
			resultMap.put(otherWorkOrder2.getJkxmbh_s(), shengyu2(otherWorkOrder2));
		}
		//监控3
		if(otherWorkOrder2.getMs_t()!=null&&!otherWorkOrder2.getMs_t().equals("")){
			
			jihua.append(otherWorkOrder2.getMs_t()+":"+otherWorkOrder2.getJkz_t()+",");
			shengyu.append(otherWorkOrder2.getMs_t()+":"+shengyu3(otherWorkOrder2)+",");
			resultMap.put(otherWorkOrder2.getJkxmbh_t(), shengyu3(otherWorkOrder2));
		}
		
		//计算剩余天数
		if(resultMap.size()!=0){
			shengyut=timeControllWareItemService.shengyutianshu(planeModelData,resultMap);
			if(shengyut==null){
				shengyut="";
			}
		}
		if(otherWorkOrder2.getGddl().equals("FLX")){
			if(otherWorkOrder2.getGdzl().equals("2")){
				otherWorkOrder2.setGdlx("非例行-附加工单");
			}else{
				otherWorkOrder2.setGdlx("非例行-排故工单");
			}
		}else{
			otherWorkOrder2.setGdlx("EO工单");
		}
		
		//任务单号
		if(otherWorkOrder2.getRwid()!=null){
			otherWorkOrder2.setRwdh(otherWorkOrder2.getRwdh()+"/"+otherWorkOrder2.getRwwhr()+"/"+otherWorkOrder2.getRwwhsj());
		}
		
		otherWorkOrder2.setJihua(jihua.toString());//计划
		otherWorkOrder2.setShengyu(shengyu.toString());//剩余
		otherWorkOrder2.setShengyut(shengyut);//剩余天数
		return otherWorkOrder2;
	}


	/**
	 * 格式化剩余：监控1
	 */
	@Override
	public String shengyu1(OtherWorkOrder otherWorkOrder2) throws Exception {
		String shengyu="";
		
		 if(otherWorkOrder2.getJkxmbh_f().equals("calendar")){
			 
			 shengyu=DateUtil.getConsumeDay(DateUtil.DEFAULT_FORMAT_DATE,otherWorkOrder2.getSj_f(), otherWorkOrder2.getJkz_f())+"";
		 }else if(otherWorkOrder2.getJkxmbh_f().equals("fuselage_flight_time")){
				
				shengyu= StringAndDate_Util.operateTime(otherWorkOrder2.getJkz_f(), otherWorkOrder2.getSj_f(), TimeSeparatorEnum.COLON, TimeOperationEnum.SUBTRACT);	
				if(!StringUtils.isBlank(otherWorkOrder2.getFjzch())){
					PlaneInitData pd=new PlaneInitData();
					pd.setFjzch(otherWorkOrder2.getFjzch());
					pd.setInitXmbh("init_time_jsfx");
					pd.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					PlaneInitData into=planeInitDataService.selectByKey(pd);
					String sjs="";
					if(into==null){
						sjs="0";
					}else{
						sjs=into.getInitValue();
					}
					shengyu= StringAndDate_Util.operateTime(shengyu, sjs, TimeSeparatorEnum.COLON, TimeOperationEnum.SUBTRACT);	
				}
				
		 }else if(otherWorkOrder2.getJkxmbh_f().equals("search_light_time")||otherWorkOrder2.getJkxmbh_f().equals("winch_time")){
			 shengyu= StringAndDate_Util.operateTime(otherWorkOrder2.getJkz_f(), otherWorkOrder2.getSj_f(), TimeSeparatorEnum.COLON, TimeOperationEnum.SUBTRACT);	
		 }else if(otherWorkOrder2.getJkxmbh_f().equals("landing_gear_cycle")||otherWorkOrder2.getJkxmbh_f().equals("special_first")||otherWorkOrder2.getJkxmbh_f().equals("special_second")){
			 if(otherWorkOrder2.getSj_t() == null){
				 otherWorkOrder2.setSj_t("0");
			 }
			 shengyu= String.valueOf(df.format(Double.valueOf(otherWorkOrder2.getJkz_f())-Double.valueOf(otherWorkOrder2.getSj_f())));
			 if(!StringUtils.isBlank(otherWorkOrder2.getFjzch())){
				 PlaneInitData pd=new PlaneInitData();
					pd.setFjzch(otherWorkOrder2.getFjzch());
					pd.setInitXmbh("init_loop_qlj");
					pd.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					PlaneInitData into=planeInitDataService.selectByKey(pd);
					String sjs="";
					if(into==null){
						sjs="0";
					}else{
						sjs=into.getInitValue();
					}
					shengyu= String.valueOf(df.format(Double.valueOf(shengyu)-Double.valueOf(sjs)));
			 }
		 } else if(otherWorkOrder2.getJkxmbh_f().equals("winch_cycle")||otherWorkOrder2.getJkxmbh_f().equals("ext_suspension_loop")||otherWorkOrder2.getJkxmbh_f().equals("N1")||otherWorkOrder2.getJkxmbh_f().equals("N2")
				||otherWorkOrder2.getJkxmbh_f().equals("N3")||otherWorkOrder2.getJkxmbh_f().equals("N4") ||otherWorkOrder2.getJkxmbh_f().equals("N5")||otherWorkOrder2.getJkxmbh_f().equals("N6")){
			 shengyu= String.valueOf(df.format(Double.valueOf(otherWorkOrder2.getJkz_f())-Double.valueOf(otherWorkOrder2.getSj_f())));
		 }
		return shengyu;
	}

	/**
	 * 格式化剩余：监控2
	 */
	@Override
	public String shengyu2(OtherWorkOrder otherWorkOrder2) throws Exception {
		String shengyu="";
	
		 if(otherWorkOrder2.getJkxmbh_s().equals("calendar")){
			 shengyu=DateUtil.getConsumeDay(DateUtil.DEFAULT_FORMAT_DATE,otherWorkOrder2.getSj_s(), otherWorkOrder2.getJkz_s())+"";
		 }else if(otherWorkOrder2.getJkxmbh_s().equals("fuselage_flight_time")){
			 shengyu= StringAndDate_Util.operateTime(otherWorkOrder2.getJkz_s(), otherWorkOrder2.getSj_s(), TimeSeparatorEnum.COLON, TimeOperationEnum.SUBTRACT);	
			 if(!StringUtils.isBlank(otherWorkOrder2.getFjzch())){
				 PlaneInitData pd=new PlaneInitData();
					pd.setFjzch(otherWorkOrder2.getFjzch());
					pd.setInitXmbh("init_time_jsfx");
					pd.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					PlaneInitData into=planeInitDataService.selectByKey(pd);
					String sjs="";
					if(into==null){
						sjs="0";
					}else{
						sjs=into.getInitValue();
					}
					shengyu= StringAndDate_Util.operateTime(shengyu, sjs, TimeSeparatorEnum.COLON, TimeOperationEnum.SUBTRACT);	
			 }
			 
		 }else if(otherWorkOrder2.getJkxmbh_s().equals("search_light_time")||otherWorkOrder2.getJkxmbh_s().equals("winch_time")){
			 
			 shengyu= StringAndDate_Util.operateTime(otherWorkOrder2.getJkz_s(), otherWorkOrder2.getSj_s(), TimeSeparatorEnum.COLON, TimeOperationEnum.SUBTRACT);	
		 }else if(otherWorkOrder2.getJkxmbh_s().equals("landing_gear_cycle")||
				 otherWorkOrder2.getJkxmbh_s().equals("special_sirst")||otherWorkOrder2.getJkxmbh_s().equals("special_second")){
			 if(otherWorkOrder2.getSj_t() == null){
				 otherWorkOrder2.setSj_t("0");
			 }
			 shengyu= String.valueOf(df.format(Double.valueOf(otherWorkOrder2.getJkz_s())-Double.valueOf(otherWorkOrder2.getSj_s())));
			 if(!StringUtils.isBlank(otherWorkOrder2.getFjzch())){
				 PlaneInitData pd=new PlaneInitData();
				 pd.setFjzch(otherWorkOrder2.getFjzch());
				 pd.setInitXmbh("init_loop_qlj");
				pd.setDprtcode(ThreadVarUtil.getUser().getJgdm());
				 PlaneInitData into=planeInitDataService.selectByKey(pd);
				 String sjs="";
				 if(into==null){
					 sjs="0";
				 }else{
					 sjs=into.getInitValue();
				 }
				 shengyu= String.valueOf(df.format(Double.valueOf(shengyu)-Double.valueOf(sjs)));
			 }
		 }else if(otherWorkOrder2.getJkxmbh_s().equals("winch_cycle")||otherWorkOrder2.getJkxmbh_s().equals("ext_suspension_loop")||otherWorkOrder2.getJkxmbh_s().equals("N1")||otherWorkOrder2.getJkxmbh_s().equals("N2")||
				 otherWorkOrder2.getJkxmbh_s().equals("N3")||otherWorkOrder2.getJkxmbh_s().equals("N4")||otherWorkOrder2.getJkxmbh_s().equals("N5")||otherWorkOrder2.getJkxmbh_s().equals("N6")){
			 
			 shengyu= String.valueOf(df.format(Double.valueOf(otherWorkOrder2.getJkz_s())-Double.valueOf(otherWorkOrder2.getSj_s())));
		 }
		
		return shengyu;
	}

	/**
	 * 格式化剩余：监控3
	 */
	@Override
	public String shengyu3(OtherWorkOrder otherWorkOrder2) throws Exception {
		String shengyu="";
		
		 if(otherWorkOrder2.getJkxmbh_t().equals("calendar")){
			 
			 shengyu=DateUtil.getConsumeDay(DateUtil.DEFAULT_FORMAT_DATE,otherWorkOrder2.getSj_t(), otherWorkOrder2.getJkz_t())+"";
		 }else if(otherWorkOrder2.getJkxmbh_t().equals("fuselage_flight_time")){
			 shengyu= StringAndDate_Util.operateTime(otherWorkOrder2.getJkz_t(), otherWorkOrder2.getSj_t(), TimeSeparatorEnum.COLON, TimeOperationEnum.SUBTRACT);	
			 if(!StringUtils.isBlank(otherWorkOrder2.getFjzch())){
				 PlaneInitData pd=new PlaneInitData();
				 pd.setFjzch(otherWorkOrder2.getFjzch());
				 pd.setInitXmbh("init_time_jsfx");
				pd.setDprtcode(ThreadVarUtil.getUser().getJgdm());
				 PlaneInitData into=planeInitDataService.selectByKey(pd);
				 String sjs="";
				 if(into==null){
					 sjs="0";
				 }else{
					 sjs=into.getInitValue();
				 }
				 shengyu= StringAndDate_Util.operateTime(shengyu, sjs, TimeSeparatorEnum.COLON, TimeOperationEnum.SUBTRACT);	
			 }
		 }else if(otherWorkOrder2.getJkxmbh_t().equals("search_light_time")||otherWorkOrder2.getJkxmbh_t().equals("winch_time")){
			 
			 shengyu= StringAndDate_Util.operateTime(otherWorkOrder2.getJkz_t(), otherWorkOrder2.getSj_t(), TimeSeparatorEnum.COLON, TimeOperationEnum.SUBTRACT);
		 }else if(otherWorkOrder2.getJkxmbh_t().equals("landing_gear_cycle")||otherWorkOrder2.getJkxmbh_t().equals("special_tirst")||otherWorkOrder2.getJkxmbh_t().equals("special_tecond")){
			 if(otherWorkOrder2.getSj_t() == null){
				 otherWorkOrder2.setSj_t("0");
			 }
			 shengyu= String.valueOf(df.format(Double.valueOf(otherWorkOrder2.getJkz_t())-Double.valueOf(otherWorkOrder2.getSj_t())));
			 if(!StringUtils.isBlank(otherWorkOrder2.getFjzch())){
				 PlaneInitData pd=new PlaneInitData();
				 pd.setFjzch(otherWorkOrder2.getFjzch());
				 pd.setInitXmbh("init_loop_qlj");
				pd.setDprtcode(ThreadVarUtil.getUser().getJgdm());
				 PlaneInitData into=planeInitDataService.selectByKey(pd);
				 String sjs="";
				 if(into==null){
					 sjs="0";
				 }else{
					 sjs=into.getInitValue();
				 }
				 shengyu= String.valueOf(df.format(Double.valueOf(shengyu)-Double.valueOf(sjs)));
			 }
		 }else if(otherWorkOrder2.getJkxmbh_t().equals("winch_cycle")||otherWorkOrder2.getJkxmbh_t().equals("ext_suspension_loop")
				 ||otherWorkOrder2.getJkxmbh_t().equals("N1")||otherWorkOrder2.getJkxmbh_t().equals("N2")||otherWorkOrder2.getJkxmbh_t().equals("N3")||otherWorkOrder2.getJkxmbh_t().equals("N4")||otherWorkOrder2.getJkxmbh_t().equals("N5")||otherWorkOrder2.getJkxmbh_t().equals("N6")){
			 
			 shengyu= String.valueOf(df.format(Double.valueOf(otherWorkOrder2.getJkz_t())-Double.valueOf(otherWorkOrder2.getSj_t())));
		 }
		
		return shengyu;
	}

	@Override
	public Map<String, Object> checkUpdMt(User userFromSession, String id) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		OtherWorkOrder otherWorkOrder=new OtherWorkOrder();
		otherWorkOrder.setId(id);
	
		List<OtherWorkOrder> list= otherWorkOrderMapper.queryAllList1(otherWorkOrder);
		
		if(list.get(0).getZt().equals("3")){
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "未审批完成,不可提交计划!");
			return returnMap;
		}

		if(list.get(0).getRwid()==null){
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "任务单已存在不可提交!");
			return returnMap;
		}
		returnMap.put("state", "success");
		
		return returnMap;
	}

	@Override
	public Map<String, Object> subjh(OtherWorkOrder otherWorkOrder)throws BusinessException  {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map

			List<OtherWorkOrder> list= otherWorkOrderMapper.queryAllList1(otherWorkOrder);
			
			User user = ThreadVarUtil.getUser();
			ScheduledTask scheduledTask=new ScheduledTask();
			WorkOrder workorder =new WorkOrder();
			Integer num=null;
			if(list.get(0).getGddl().equals("FLX")){
				num=2;
				scheduledTask.setRwlx(2);//flx
				scheduledTask.setRwzlx(Integer.valueOf(list.get(0).getGdzl()));
			}
			if(list.get(0).getGddl().equals("EO")){
				num=3;
				scheduledTask.setRwlx(3);//eo
				scheduledTask.setRwzlx(0);
			}
			workorder.setGddlx(num);
			workorder.setId(list.get(0).getGdid());
			WorkOrder workorder1=workOrderService.selectByWorkOrder(workorder);
			
			//新增
			UUID uuid = UUID.randomUUID();
			String ids = uuid.toString();	
			scheduledTask.setId(ids);
			scheduledTask.setRwdh(list.get(0).getGdbh());
		
		
			scheduledTask.setZt(2);//2提交
			scheduledTask.setXszt(1);//1待执行
			
			scheduledTask.setXggdid(list.get(0).getGdid());
			scheduledTask.setDprtcode(list.get(0).getDprtcode());
			
			if(workorder1.getZhut()==null){
				scheduledTask.setRwxx("");
			}else{
				scheduledTask.setRwxx(workorder1.getZhut());
			}
			if(list.get(0).getFjzch()==null){
				scheduledTask.setFjzch("");
			}else{
				
				List<String> list4 = new ArrayList<String>();
				list4.add(list.get(0).getFjzch());
				planeModelDataService.existsAircraft4Expt(user.getId(), user.getUserType(), list.get(0).getDprtcode(), list4);
				scheduledTask.setFjzch(list.get(0).getFjzch());
			}
			
			if(list.get(0).getBjh()==null){
				scheduledTask.setBjh("");
			}else{
				scheduledTask.setBjh(list.get(0).getBjh());
			}
			if(list.get(0).getBjxlh()==null){
				scheduledTask.setXlh("");
			}else{
				scheduledTask.setXlh(list.get(0).getBjxlh());
			}
		
			if(workorder1.getJhgsRs()==null){
				scheduledTask.setJhgsRs(BigDecimal.valueOf(0));
			}else{
				scheduledTask.setJhgsRs(workorder1.getJhgsRs());
			}
			
			if(workorder1.getJhgsXss()==null){
				scheduledTask.setJhgsXs(BigDecimal.valueOf(0));
			}else{
				scheduledTask.setJhgsXs(workorder1.getJhgsXss());
			}
			if(list.get(0).getJkbz()==null){
				scheduledTask.setBz("");
			}else{
				scheduledTask.setBz(list.get(0).getJkbz());
			}
			if(user.getBmdm()==null){
				scheduledTask.setWhdwid("");
			}else{
				scheduledTask.setWhdwid(user.getBmdm());
			}
			scheduledTask.setWhrid(user.getId());
			scheduledTask.setGsshZt(1);
			scheduledTaskMapper.save(scheduledTask);
			
			String czls = UUID.randomUUID().toString();
			commonRecService.write(ids, TableEnum.B_S_009, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,otherWorkOrder.getId());
			
			returnMap.put("state", "success");
			
		
		return returnMap;
	}

	@Override
	public Map<String, Object> saveJkbz(OtherWorkOrder otherWorkOrder)
			throws Exception {
		Integer num=null;
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		WorkOrder workorder =new WorkOrder();
		workorder.setId(otherWorkOrder.getId());
		workorder.setJkbz(otherWorkOrder.getJkbz());
		if(otherWorkOrder.getGddl().equals("FLX")){
			num=2;
		}
		if(otherWorkOrder.getGddl().equals("EO")){
			num=3;
		}
		workorder.setGddlx(num);
		workOrderService.updateByPrimaryKeys(workorder);
		
		try {
			OtherWorkOrder otherWorkOrder1=new OtherWorkOrder();
			otherWorkOrder1.setId(otherWorkOrder.getGdbh());
			List<OtherWorkOrder> list= otherWorkOrderMapper.queryAllList1(otherWorkOrder1);
			if(list.get(0).getRwid()!=null&&!list.get(0).getRwid().equals("")){
				 ScheduledTask scheduledTask=  scheduledTaskSerivce.queryKey(list.get(0).getRwid());
				 scheduledTask.setBz(otherWorkOrder.getJkbz());
				 scheduledTaskSerivce.update(scheduledTask);//更新BS009表备注
				 
			}
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		 returnMap.put("state", "success");
		 return returnMap;
	}



}
