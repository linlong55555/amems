package com.eray.thjw.productionplan.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.po.FixedCheckItem;
import com.eray.thjw.po.Nonroutine;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.ScheduledTaskMapper;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.productionplan.po.ScheduledTask;
import com.eray.thjw.productionplan.po.TimeControllWareItem;
import com.eray.thjw.productionplan.service.LoadingListService;
import com.eray.thjw.productionplan.service.ScheduledCheckItemService;
import com.eray.thjw.productionplan.service.ScheduledTaskSerivce;
import com.eray.thjw.productionplan.service.TimeControllWareItemService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.FixedCheckItemService;
import com.eray.thjw.service.NonroutineService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class ScheduledTaskSerivceImpl implements ScheduledTaskSerivce {
	
	@Resource
	private ScheduledTaskMapper scheduledTaskMapper;
	
	@Resource
	private PlaneModelDataService planeModelDataService;
	@Autowired
	private CommonRecService commonRecService;//日志service
	
	@Resource
	private LoadingListService loadingListService; //装机清单Service
	
	/**
	 * @author ll
	 * @description 定检项目主表Service
	 * @develop date 2016.08.24
	 */
	@Autowired
	private FixedCheckItemService fixedCheckItemService;
	
	/**
	 * @author ll
	 * @description 时控件提交计划Service
	 * @develop date 2016.08.24
	 */
	@Autowired
	private NonroutineService nonroutineService;
	
	@Resource
	private TimeControllWareItemService timeControllWareItemService;
	
	@Resource
	private ScheduledCheckItemService scheduledCheckItemService;
	
	/**
	 * save b_s_009 计划任务
	 */
	@Override
	public void save(String id,String rwdh,ScheduledCheckItem scheduledCheckItem,String czls) throws Exception{
		
		User user = ThreadVarUtil.getUser();
		
		ScheduledTask scheduledTask=new ScheduledTask();
		FixedCheckItem fixedCheckItem=fixedCheckItemService.selectByPrimaryKey(scheduledCheckItem.getDjxmid());
		//新增
		UUID uuid = UUID.randomUUID();
		String ids = uuid.toString();	
		scheduledTask.setId(ids);
		scheduledTask.setRwdh(rwdh);
		scheduledTask.setRwlx(1);//1定检执行任务
		scheduledTask.setRwzlx(0);//0无
		scheduledTask.setZt(2);//2提交
		scheduledTask.setXszt(1);//1待执行
		
		scheduledTask.setXggdid(id);
		scheduledTask.setDprtcode(scheduledCheckItem.getDprtcode());
		scheduledTask.setRwxx(fixedCheckItem.getZwms());
		scheduledTask.setFjzch(scheduledCheckItem.getFjzch());
		scheduledTask.setBjh(scheduledCheckItem.getJh());
		scheduledTask.setXlh(scheduledCheckItem.getXlh());
		scheduledTask.setJhgsRs(fixedCheckItem.getJhgsRs());
		scheduledTask.setJhgsXs(fixedCheckItem.getJhgsXss());
		scheduledTask.setGsshZt(1);
		if(scheduledCheckItem.getJkbz()==null){
			scheduledTask.setBz("");
		}else{
			scheduledTask.setBz(scheduledCheckItem.getJkbz());
		}
		if(user.getBmdm()==null){
			scheduledTask.setWhdwid("");
		}else{
			scheduledTask.setWhdwid(user.getBmdm());
		}
		scheduledTask.setWhrid(user.getId());
		scheduledTask.setWhsj("");
		
		scheduledTaskMapper.save(scheduledTask);
		
		commonRecService.write(ids, TableEnum.B_S_009, user.getId(),czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,scheduledCheckItem.getId());
		
		ScheduledCheckItem scheduledCheckItem1=new ScheduledCheckItem();
		scheduledCheckItem1.setId(scheduledCheckItem.getId());
		scheduledCheckItem1.setRwid(ids);
		scheduledCheckItemService.updatePlaneEditable(scheduledCheckItem1);       //回填b_s_00303 生效区-定检件定检项目 表中的任务id（rwid）字段
		
	}

	@Override
	public void saveskj(TimeControllWareItem timeControllWareItem) throws Exception {
		
		List<TimeControllWareItem> timeControllWareItem1=timeControllWareItemService.queryAllPageList1(timeControllWareItem);
		User user = ThreadVarUtil.getUser();
		List<String> list4 = new ArrayList<String>();
		list4.add(timeControllWareItem1.get(0).getFjzch());
		planeModelDataService.existsAircraft4Expt(user.getId(), user.getUserType(), timeControllWareItem1.get(0).getDprtcode(), list4);
		
	//	String last4=null;
		Nonroutine nonroutine1=new Nonroutine();
		nonroutine1.setId(timeControllWareItem1.get(0).getGdid());
		Nonroutine nonroutine=nonroutineService.selectByPrimaryKey(nonroutine1);//根据工单id查询工单全部信息
		
		ScheduledTask scheduledTask=new ScheduledTask();
		
		//新增
		UUID uuid = UUID.randomUUID();
		String ids = uuid.toString();	
		scheduledTask.setId(ids);
		scheduledTask.setRwdh(nonroutine.getGdbh());
		scheduledTask.setRwlx(2);//2非例行
		scheduledTask.setRwzlx(1);//1时控件
		scheduledTask.setZt(2);//2提交
		scheduledTask.setXszt(1);//1待执行
		
		scheduledTask.setXggdid(timeControllWareItem1.get(0).getGdid());
		scheduledTask.setDprtcode(timeControllWareItem1.get(0).getDprtcode());
		
		if(nonroutine.getZhut()==null){
			scheduledTask.setRwxx("");
		}else{
			scheduledTask.setRwxx(nonroutine.getZhut());
		}
		scheduledTask.setFjzch(timeControllWareItem1.get(0).getFjzch());
		scheduledTask.setBjh(timeControllWareItem1.get(0).getJh());
		scheduledTask.setXlh(timeControllWareItem1.get(0).getXlh());
		if(nonroutine.getJhgs_rs()==null){
			scheduledTask.setJhgsRs(BigDecimal.valueOf(0));
		}else{
			scheduledTask.setJhgsRs(nonroutine.getJhgs_rs());
		}
		
		if(nonroutine.getJhgs_xss()==null){
			scheduledTask.setJhgsXs(BigDecimal.valueOf(0));
		}else{
			scheduledTask.setJhgsXs(nonroutine.getJhgs_xss());
		}
		
		
		if(timeControllWareItem1.get(0).getJkbz()==null){
			scheduledTask.setBz("");
		}else{
			scheduledTask.setBz(timeControllWareItem1.get(0).getJkbz());
		}
		
		scheduledTask.setWhdwid(user.getBmdm());
		scheduledTask.setWhrid(user.getId());
		scheduledTask.setGsshZt(1);
		scheduledTaskMapper.save(scheduledTask);
		
		String czls = UUID.randomUUID().toString();
		commonRecService.write(ids, TableEnum.B_S_009, user.getId(),czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,timeControllWareItem.getId());
		
		LoadingList loadingList=new LoadingList();
		loadingList.setId(timeControllWareItem1.get(0).getId());
		loadingList.setRwid(ids);
		loadingListService.updatePlaneEditable(loadingList);       //b_s_003.计划id = b_g_009.id
		
	}

	@Override
	public List<ScheduledTask> queryAllPageList(ScheduledTask scheduledTask)
			throws RuntimeException {
		
		return scheduledTaskMapper.queryAllPageList(scheduledTask);
	}

	@Override
	public Map<String, Object> modify(ScheduledTask scheduledTask) throws RuntimeException {
		Map<String, Object>	resultMap=new HashMap<String, Object>();
		try {
			User user = ThreadVarUtil.getUser();
			scheduledTaskMapper.modify(scheduledTask);
			
			String czls = UUID.randomUUID().toString();
			commonRecService.write(scheduledTask.getId(), TableEnum.B_S_009, user.getId(),czls ,LogOperationEnum.AUDIT, UpdateTypeEnum.UPDATE,null);
			resultMap.put("id", scheduledTask.getId());
			resultMap.put("state", "Success");
		}catch (Exception e) {
			resultMap.put("state", e);
			
			}
			finally{
			}
		
		return resultMap;
	}

	@Override
	public Map<String, Object> subWorkersimplex(ScheduledTask scheduledTask)
			throws RuntimeException {
		Map<String, Object>	resultMap=new HashMap<String, Object>();
		try {
			User user = ThreadVarUtil.getUser();
			scheduledTask.setGdzt("2");
			scheduledTaskMapper.subWorkersimplex(scheduledTask);
			
			String czls = UUID.randomUUID().toString();
			commonRecService.write(scheduledTask.getId(), TableEnum.B_S_009, user.getId(),czls ,LogOperationEnum.SUBMIT_PRODUCTION_CONFIRM, UpdateTypeEnum.UPDATE,scheduledTask.getId());
			resultMap.put("id", scheduledTask.getId());
			resultMap.put("state", "Success");
		}catch (Exception e) {
			resultMap.put("state", e);
			
			}
			finally{
			}
		
		return resultMap;
	}

	@Override
	public ScheduledTask queryKey(String rwid) throws RuntimeException {
		
		return scheduledTaskMapper.queryKey(rwid);
	}

	@Override
	public void update(ScheduledTask scheduledTask) throws RuntimeException {
		User user = ThreadVarUtil.getUser();
		scheduledTaskMapper.update(scheduledTask);
		//commonRecService.write(scheduledTask.getId(), TableEnum.B_S_009, user.getId(), UpdateTypeEnum.UPDATE,null);//插入日志
	}




}
