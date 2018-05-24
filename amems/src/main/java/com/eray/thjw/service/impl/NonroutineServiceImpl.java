package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.NonroutineMapper;
import com.eray.thjw.po.BaseWorkOrder;
import com.eray.thjw.po.Nonroutine;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WOActionObj;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.po.TimeControlOptions;
import com.eray.thjw.productionplan.po.TimeControllWareItem;
import com.eray.thjw.productionplan.service.LoadingListService;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.productionplan.service.TimeControlOptionsService;
import com.eray.thjw.productionplan.service.TimeControllWareItemService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.NonroutineService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.WOBaseService;
import com.eray.thjw.service.WorkOrderService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
@Service
public class NonroutineServiceImpl implements NonroutineService {
    
	@Resource
	private NonroutineMapper nonroutineMapper;
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	@Resource
	private WOBaseService wOBaseService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private TimeControllWareItemService timeControllWareItemService;
	
	@Resource
	private LoadingListService loadingListService; //装机清单Service
	
	@Resource
	private WorkOrderService workOrderService; //时控件service提交计划
	
	@Resource
	private TimeControlOptionsService timeControlOptionsService; //时控件service提交计划
	
	@Resource
	private PlaneDataService planeDataService; //时控件service提交计划
	
	@Override
	public int insertSelective(List<Nonroutine> nonroutine,String uuid,String gdbh,String jcid) throws RuntimeException {
		User user = ThreadVarUtil.getUser();
		int count=0;
		for (Nonroutine nonroutine2 : nonroutine) {
			nonroutine2.setId(uuid);
			nonroutine2.setGdjcid(jcid);
			nonroutine2.setGdbh(gdbh);
			if(user.getBmdm()==null){
				nonroutine2.setZddwid("");
			}else{
				nonroutine2.setZddwid(user.getBmdm());
				
			}
			nonroutine2.setZdrid(user.getId());
			nonroutine2.setDprtcode(user.getJgdm());
			nonroutine2.setZdjsrid(user.getId());
			if(null!=nonroutine2.getGdbh()&&!"".equals(nonroutine2.getGdbh())){
				if(nonroutine2.getGdlx()==1||nonroutine2.getGdlx()==2||nonroutine2.getGdlx()==3){
					count=nonroutineMapper.insertSelective(nonroutine2);         //增加非例行工单数据
					commonRecService.write(uuid, TableEnum.B_G_007, user.getId(), UpdateTypeEnum.SAVE,uuid);    //增加操作日志
				}else{
					count=nonroutineMapper.insertSelective2(nonroutine2);         //增加EO工单数据
					commonRecService.write(uuid, TableEnum.B_G_010, user.getId(), UpdateTypeEnum.SAVE,uuid);    //增加操作日志
				}
			}
		}
		return count;
	}

	/**
	 * 生成时控件工单
	 * ll
	 */
	@Override
	public void save(TimeControllWareItem timeControllWareItem) throws Exception {
		
		List<TimeControllWareItem> timeControllWareItem1=timeControllWareItemService.queryAllPageList1(timeControllWareItem);
		PlaneData planeData=new PlaneData();
		
		User user = ThreadVarUtil.getUser();
		List<String> list4 = new ArrayList<String>();
		list4.add(timeControllWareItem.getFjzch());
		planeModelDataService.existsAircraft4Expt(user.getId(), user.getUserType(), timeControllWareItem.getDprtcode(), list4);
		
		
		planeData.setFjzch(timeControllWareItem1.get(0).getFjzch());
		planeData.setDprtcode(timeControllWareItem1.get(0).getDprtcode());
		planeData=planeDataService.selectByPrimaryKey(planeData);
		WorkOrder workOrder=new WorkOrder();
		workOrder.setGddlx(2);//时控件类型
		workOrder.setGdlx(1);
		workOrder.setJd(planeData.getJd());
		workOrder.setZjh(timeControllWareItem1.get(0).getZjh());
		workOrder.setZt(0);
		//workOrder.setGzzid(gzzid);
		workOrder.setDprtcode(timeControllWareItem1.get(0).getDprtcode());
		if(timeControllWareItem1.get(0).getJkbz()==null){
			workOrder.setJkbz("");
		}else{
			workOrder.setJkbz(timeControllWareItem1.get(0).getJkbz());}
		
		BaseWorkOrder baseWorkOrder=new BaseWorkOrder();
		
		WOActionObj wOActionObjList=new WOActionObj();
		
		wOActionObjList.setZxfl("ZJJ");
		wOActionObjList.setFjzch(timeControllWareItem1.get(0).getFjzch());
		wOActionObjList.setZjqdid(timeControllWareItem1.get(0).getId());
		wOActionObjList.setBjh(timeControllWareItem1.get(0).getJh());
		wOActionObjList.setBjxlh(timeControllWareItem1.get(0).getXlh());
		
		
		TimeControlOptions timeControlOptions=new TimeControlOptions();
		timeControlOptions.setZjqdid(timeControllWareItem.getId());
		List<TimeControlOptions> list=timeControlOptionsService.queryAllList(timeControlOptions);
		
		StringBuffer jkxmbh=new StringBuffer();
		StringBuffer jkflbh=new StringBuffer();
		StringBuffer jkz=new StringBuffer();
		String[] jkxmbhs=new String[0];
		String[] jkflbhs=new String[0];
		String[] jkzs=new String[0];
		StringBuffer str=new StringBuffer();
		for (TimeControlOptions timeControlOptions2 : list) {
			str.append(timeControlOptions2.getMs()+" 规定上限:"+timeControlOptions2.getGdsx()+"装机前已用:"+timeControlOptions2.getZjyy()+"部件预拆:"+timeControlOptions2.getBjyc()+"\r\n");//工单备注
				jkxmbh.append(timeControlOptions2.getJklbh()+",");
				jkflbh.append(timeControlOptions2.getJkflbh()+",");
				if(jkxmbh.toString().equals("calendar")){
					jkz.append(timeControlOptions2.getBjyc()+",");
				}else{
					jkz.append(timeControlOptions2.getGdsx()+",");
				}	
		}
		
		workOrder.setBz(str.toString());
		jkxmbhs = (jkxmbh.toString()).split(",");//监控项目编号
		jkflbhs = (jkflbh.toString()).split(",");//分类编号
		jkzs = (jkz.toString()).split(",");//分类编号
		
		if(jkxmbhs.length>=1){
			//监控项目编号
			if(jkxmbhs[0]!=null){
				wOActionObjList.setJkxmbhF(jkxmbhs[0]);
			}
		}else{
			wOActionObjList.setJkxmbhF("");
		}
		
		if(jkxmbhs.length>=2){
			if(jkxmbhs[1]!=null){
				wOActionObjList.setJkxmbhS(jkxmbhs[1]);
			}
		}else{
			wOActionObjList.setJkxmbhS("");
		}
		if(jkxmbhs.length==3){
			if(jkxmbhs[2]!=null){
				wOActionObjList.setJkxmbhT(jkxmbhs[2]);
			}
		}else{
			wOActionObjList.setJkxmbhT("");
		}
		
		//分类编号
		if(jkflbhs.length>=1){
			if(jkflbhs[0]!=null){
				wOActionObjList.setJkflbhF(jkflbhs[0]);
			}
		}else{
			wOActionObjList.setJkflbhF("");
		}
		
		if(jkflbhs.length>=2){
			if(jkflbhs[1]!=null){
				wOActionObjList.setJkflbhS(jkflbhs[1]);
			}
		}else{
			wOActionObjList.setJkflbhS("");
		}
		if(jkflbhs.length==3){
			if(jkflbhs[2]!=null){
				wOActionObjList.setJkflbhT(jkflbhs[2]);
			}
		}else{
			wOActionObjList.setJkflbhT("");
		}
		
		//监控值
		if(jkzs.length>=1){
			if(jkzs[0]!=null){
				wOActionObjList.setJkzF(jkzs[0]);
			}
		}else{
			wOActionObjList.setJkzF("");
		}
		if(jkzs.length>=2){
			if(jkzs[1]!=null){
				wOActionObjList.setJkzS(jkzs[1]);
			}
		}else{
			wOActionObjList.setJkzS("");
		}
		
		if(jkzs.length==3){
			if(jkzs[2]!=null){
				wOActionObjList.setJkzT(jkzs[2]);
			}
		}else{
			wOActionObjList.setJkzT("");
		}
		
		baseWorkOrder.setwOActionObj(wOActionObjList);//增加执行对象数据
		workOrder.setBaseWorkOrder(baseWorkOrder);//增加工单基础表
		String czls = UUID.randomUUID().toString();
		workOrder.setCzls(czls);
		String Gdid=workOrderService.insertSelective(workOrder);//增加时控件工单 返回工单表的id
		
		LoadingList loadingList=new LoadingList();
		loadingList.setId(timeControllWareItem1.get(0).getId());
		loadingList.setGdid(Gdid);
		
		loadingListService.updatePlaneEditable(loadingList);       //b_s_003.工单id = b_g_007.id
	}

	@Override
	public Nonroutine selectByPrimaryKey(Nonroutine nonroutine) {
		return nonroutineMapper.selectByPrimaryKey(nonroutine);
	}

	@Override
	public Nonroutine selectByEOWork(Nonroutine nonroutine) {
		return nonroutineMapper.selectByEOWork(nonroutine);
	}

	@Override
	public Nonroutine selectByDingJianWork(Nonroutine nonroutine) {
		return nonroutineMapper.selectByDingJianWork(nonroutine);
	}

	@Override
	public int updateByPrimaryKey(Nonroutine record) {
		return nonroutineMapper.updateByPrimaryKey(record);
	}

	@Override
	public void updateByPrimaryKeySelective(Nonroutine nonroutine) {
		 nonroutineMapper.updateByPrimaryKeySelective(nonroutine);
	}

	@Override
	public int updateByPrimaryKey2(Nonroutine record) {
		return nonroutineMapper.updateByPrimaryKey2(record);
	}

	@Override
	public int updateByPrimaryKey3(Nonroutine record) {
		return nonroutineMapper.updateByPrimaryKey3(record);
	}

	@Override
	public int updateByPrimaryKey4(Nonroutine record) {
		return nonroutineMapper.updateByPrimaryKey4(record);
	}

}
