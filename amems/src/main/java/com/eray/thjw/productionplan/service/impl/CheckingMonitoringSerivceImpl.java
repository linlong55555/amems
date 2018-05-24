package com.eray.thjw.productionplan.service.impl;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.CheckingMonitoringMapper;
import com.eray.thjw.productionplan.po.CheckingMonitoring;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.productionplan.po.ScheduledCheckMonitorItem;
import com.eray.thjw.productionplan.service.CheckingMonitoringSerivce;
import com.eray.thjw.productionplan.service.ScheduledCheckMonitorItemService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;

import enu.CheckingEnum;
import enu.LogOperationEnum;
import enu.ScheduledEnum;
import enu.TableEnum;
import enu.TimeOperationEnum;
import enu.TimeSeparatorEnum;
import enu.UpdateTypeEnum;

@Service
public class CheckingMonitoringSerivceImpl implements CheckingMonitoringSerivce {
	
	private static final Logger logger = LoggerFactory.getLogger(CheckingMonitoringSerivceImpl.class);
	
	@Resource
	private CheckingMonitoringMapper checkingMonitoringMapper;
	
	@Autowired
	private CommonRecService commonRecService;//日志service
	
	@Autowired
	private ScheduledCheckMonitorItemService scheduledCheckMonitorItemService;//计划
	
	/**
	 * save b_g_01401 定检任务监控项目
	 */
	@Override
	public void save(String id,ScheduledCheckItem scheduledCheckItem,String czls) throws Exception{
		User user = ThreadVarUtil.getUser();
		
		CheckingMonitoring checkingMonitoring=new CheckingMonitoring();
		
		ScheduledCheckMonitorItem scheduledCheckMonitorItem=new ScheduledCheckMonitorItem(); 
		scheduledCheckMonitorItem.setFjzch(scheduledCheckItem.getFjzch());
		scheduledCheckMonitorItem.setMainid(scheduledCheckItem.getId());
		List<ScheduledCheckMonitorItem> list=scheduledCheckMonitorItemService.queryAllList(null);
		
		StringBuffer jkxmbh=new StringBuffer();
		StringBuffer jkflbh=new StringBuffer();
		StringBuffer jkz=new StringBuffer();
		StringBuffer zqz=new StringBuffer();
		StringBuffer dw=new StringBuffer();
		StringBuffer csbj=new StringBuffer();
		String[] jkxmbhs=new String[0];
		String[] jkflbhs=new String[0];
		String[] jkzs=new String[0];
		String[] zqzs=new String[0];
		String[] dws=new String[0];
		String[] csbjs=new String[0];
		//获取监控项目编号，分类编号，计划值
		for (ScheduledCheckMonitorItem scheduledCheckMonitorItem2 : list) {
			if(scheduledCheckMonitorItem2.getMainid().equals(scheduledCheckItem.getId())){
				jkxmbh.append(scheduledCheckMonitorItem2.getJklbh()+",");
				jkflbh.append(scheduledCheckMonitorItem2.getJkflbh()+",");
				jkz.append(scheduledCheckMonitorItem2.getJhz()+",");	
				zqz.append(scheduledCheckMonitorItem2.getZqz()+",");	
				dw.append(scheduledCheckMonitorItem2.getDw()+",");	
				csbj.append(scheduledCheckMonitorItem2.getCsbj()+",");
			}
		}
		
		jkxmbhs = (jkxmbh.toString()).split(",");//监控项目编号
		jkflbhs = (jkflbh.toString()).split(",");//分类编号
		jkzs = (jkz.toString()).split(",");//分类编号
		zqzs = (zqz.toString()).split(",");//周期值
		dws = (dw.toString()).split(",");//单位
		csbjs = (csbj.toString()).split(",");//初始标记
		
		//新增
		UUID uuid = UUID.randomUUID();
		String ids = uuid.toString();	
		checkingMonitoring.setId(ids);
		checkingMonitoring.setMainid(id);
		checkingMonitoring.setFjzch(scheduledCheckItem.getFjzch());
		checkingMonitoring.setZjqdid(scheduledCheckItem.getZjqdid());
		checkingMonitoring.setBjh(scheduledCheckItem.getJh());
		checkingMonitoring.setBjxlh(scheduledCheckItem.getXlh());
		if(jkxmbhs.length>=1){
			//监控项目编号
			if(jkxmbhs[0]!=null){
				checkingMonitoring.setJkxmbhF(jkxmbhs[0]);
			}
		}else{
			checkingMonitoring.setJkxmbhF("");
		}
	
		if(jkxmbhs.length>=2){
			if(jkxmbhs[1]!=null){
				checkingMonitoring.setJkxmbhS(jkxmbhs[1]);
			}
		}else{
			checkingMonitoring.setJkxmbhS("");
		}
		
		if(jkxmbhs.length==3){
			if(jkxmbhs[2]!=null){
				checkingMonitoring.setJkxmbhT(jkxmbhs[2]);
			}
		}else{
			checkingMonitoring.setJkxmbhT("");
		}
		
		//分类编号
		if(jkflbhs.length>=1){
			if(jkflbhs[0]!=null){
				checkingMonitoring.setJkflbhF(jkflbhs[0]);
			}
		}else{
			checkingMonitoring.setJkflbhF("");
		}
		
		if(jkflbhs.length>=2){
			if(jkflbhs[1]!=null){
				checkingMonitoring.setJkflbhS(jkflbhs[1]);
			}
		}else{
			checkingMonitoring.setJkflbhS("");
		}
		if(jkflbhs.length==3){
			if(jkflbhs[2]!=null){
				checkingMonitoring.setJkflbhT(jkflbhs[2]);
			}
		}else{
			checkingMonitoring.setJkflbhT("");
		}
		
		
		//监控值
		if(jkzs.length>=1){
			if(jkzs[0]!=null){
				String str1=zqzs[0];
		        BigDecimal bd=new BigDecimal(str1);
		        
		        if(csbjs[0].equals("0")){
		        	checkingMonitoring.setJkzF(jihua(Integer.valueOf(dws[0]), bd, jkzs[0]));
		        }else{
		        	checkingMonitoring.setJkzF(jkzs[0]);
		        }
			}
		}else{
			checkingMonitoring.setJkzF("");
		}
		if(jkzs.length>=2){
			if(jkzs[1]!=null){
				String str1=zqzs[1];
		        BigDecimal bd=new BigDecimal(str1);
		        if(csbjs[1].equals("0")){
		        	checkingMonitoring.setJkzS(jihua(Integer.valueOf(dws[1]), bd, jkzs[1]));
		        }else{
		        	checkingMonitoring.setJkzS(jkzs[1]);
		        }
			}
		}else{
			checkingMonitoring.setJkzS("");
		}
		
		if(jkzs.length==3){
			if(jkzs[2]!=null){
				String str1=zqzs[2];
		        BigDecimal bd=new BigDecimal(str1);
		        if(csbjs[2].equals("0")){
		        	checkingMonitoring.setJkzT(jihua(Integer.valueOf(dws[2]), bd, jkzs[2]));
		        }else{
		        	checkingMonitoring.setJkzT(jkzs[2]);
		        }
			}
		}else{
			checkingMonitoring.setJkzT("");
		}
		if(user.getBmdm()==null){
			checkingMonitoring.setWhbmid("");
		}else{
			checkingMonitoring.setWhbmid(user.getBmdm());
		}
		checkingMonitoring.setWhrid(user.getId());
		checkingMonitoring.setWhsj("");
		checkingMonitoring.setZt(CheckingEnum.EFFECT.getId());
		checkingMonitoring.setDprtcode(scheduledCheckItem.getDprtcode());
		
		checkingMonitoringMapper.save(checkingMonitoring);
		commonRecService.write(ids, TableEnum.B_G_01401, user.getId(),czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,scheduledCheckItem.getId());
	}

	
	/**
	 * 计算计划值
	 */
	public String jihua(int dw,BigDecimal zqz,String jhz)
			throws Exception {
		String jh=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(dw==ScheduledEnum.DAY.getId()){
			Date date = sdf.parse(jhz);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int b=zqz.intValue(); 
			cal.add(Calendar.DATE, b);
			date =cal.getTime();
			jh=new SimpleDateFormat("yyyy-MM-dd").format(date);
			
		}else if(dw==ScheduledEnum.MOON.getId()){
			Date date = sdf.parse(jhz);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int b=zqz.intValue(); 
			cal.add(Calendar.MONTH, b);
			date =cal.getTime();
			jh=new SimpleDateFormat("yyyy-MM-dd").format(date);
		}else if(dw==ScheduledEnum.YEAR.getId()){
			Date date = sdf.parse(jhz);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int b=zqz.intValue(); 
			cal.add(Calendar.YEAR, b);
			date =cal.getTime();
			jh=new SimpleDateFormat("yyyy-MM-dd").format(date);
		}else if(dw==ScheduledEnum.TIME.getId()){
			if(jhz==null){
				jhz="0";
			}
			jh=StringAndDate_Util.operateTime(jhz, zqz.toString(), TimeSeparatorEnum.COLON, TimeOperationEnum.ADD);

		}else if(dw==ScheduledEnum.LOOP.getId()){
			
			if(jhz==null){
				jhz="0";
			}
			BigDecimal ss=new BigDecimal(jhz);
			BigDecimal sss=ss.add(zqz);
			jh=sss.toString();
		}
				
		if(jh==null){
			jh="";
		}
		
	return jh;
	}
	
	
	@Override
	public CheckingMonitoring selectByPrimaryKey(String mainid) {
		return checkingMonitoringMapper.selectByPrimaryKey(mainid);
	}




}
