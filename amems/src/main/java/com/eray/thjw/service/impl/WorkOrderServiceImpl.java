package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.dao.CheckTaskBillMapper;
import com.eray.thjw.dao.WorkOrderMapper;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.BaseWorkOrder;
import com.eray.thjw.po.CheckTaskBill;
import com.eray.thjw.po.JobCard;
import com.eray.thjw.po.NonWOCard;
import com.eray.thjw.po.OrderSource;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WOActionObj;
import com.eray.thjw.po.WOAirMaterial;
import com.eray.thjw.po.WOJobContent;
import com.eray.thjw.po.WOJobEnclosure;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.productionplan.po.PlanTaskExt;
import com.eray.thjw.productionplan.service.PlanTaskService;
import com.eray.thjw.service.BaseWorkOrderService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.JobCardService;
import com.eray.thjw.service.NonWOCardService;
import com.eray.thjw.service.OrderSourceService;
import com.eray.thjw.service.PlaneBaseService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.WOActionObjService;
import com.eray.thjw.service.WOAirMaterialService;
import com.eray.thjw.service.WOJobContentService;
import com.eray.thjw.service.WOJobEnclosureService;
import com.eray.thjw.service.WorkOrderService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.MaxWorkOrderTypeEnum;
import enu.MinWorkOrderTypeEnum;
import enu.MonitorItemEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.WorkOrderStateEnum;
import enu.WorkOrderTypeEnum;
import enu.workorder.WorkOrderEnum;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

	@Resource
	private SaibongUtilService saibongUtilService;     
	@Autowired
	private CheckTaskBillMapper checkTaskBillMapper;
	@Resource
	private CommonRecService commonRecService;                   //REC表服务
	
	@Resource
	private BaseWorkOrderService baseWorkOrderService;                   //基础工单服务
	@Resource
	private OrderSourceService orderSourceService;                              //指令来源服务
	@Resource
	private NonWOCardService nonWOCardService;                              //相关工单服务
	@Resource
	private WOAirMaterialService wOAirMaterialService;                          //工单航材耗材服务
	@Resource
	private WOJobContentService wOJobContentService;                          //工单共走内容服务
	@Resource
	private WOJobEnclosureService wOJobEnclosureService;                    //工单附件的服务
	@Resource
	private  WOActionObjService wOActionObjService;                               //工单的执行对象
	@Resource
	private WorkOrderMapper workOrderMapper;        
	@Resource
	private PlanTaskService planTaskService;  
	@Resource
	private PlaneModelDataService planeModelDataService;
	@Resource
	private PlaneBaseService planeBaseService;
	@Resource
	private JobCardService jobCardService;
	/**
	 * @author meizhiliang
	 * 关联工单查询的全部工单
	 * @param WorkOrder record
	 */
	@Override
	public Map<String, Object> selectAllWorkOrder(WorkOrder record) {
		//查询的关联工单不包含已经选择过的工单
		if(null!=record.getGdids()&&record.getGdids().isEmpty()){
			record.setGdids(null);
		}  
		PageHelper.startPage(record.getPagination());
		List<WorkOrder> list = workOrderMapper.selectAllWorkOrder(record);
		return PageUtil.pack4PageHelper(list, record.getPagination());
		
	}
	/**
	 * @author meizhiliang
	 * 新增工单操作包括三种工单
	 * @param WorkOrder record
	 */
	@Override
	public String insertSelective(WorkOrder wrokorder) {
		
		User user = ThreadVarUtil.getUser();
		String czls = null;                     //声明操作流水号

		String id = UUID.randomUUID().toString();		  //工单id
		String gdjcid = UUID.randomUUID().toString();    //基础id
		String gdbh=null;
		try {
			if(WorkOrderEnum.EO_TYPE.getId().equals(wrokorder.getGddlx())){
				gdbh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.EOGD.getName(), wrokorder.getGczlbh());
			}else if(WorkOrderEnum.NON_TYPE.getId().equals(wrokorder.getGddlx())){
				gdbh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.FLX.getName());
			}else if(WorkOrderEnum.DJ_TYPE.getId().equals(wrokorder.getGddlx())){
				String fjzch = wrokorder.getFjzch();
				String djh = fjzch.substring(fjzch.length()-4<= 0?0:fjzch.length()-4,fjzch.length());
				gdbh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.LXGD.getName(), djh);
			}
		} catch (SaibongException e) {
			throw new RuntimeException(e);
		}
		wrokorder.setId(id);
		wrokorder.setGdjcid(gdjcid);
		wrokorder.setGdbh(gdbh);
		wrokorder.setZdrid(user.getId());
		wrokorder.setZddwid(user.getBmdm());
		wrokorder.setDprtcode(user.getJgdm());
		//取得CZLS的的值
		if(wrokorder.getCzls()==null||"".equals(wrokorder.getCzls())){
			czls=UUID.randomUUID().toString();    
			wrokorder.setCzls(czls);              
		}else{
			czls=wrokorder.getCzls();
		}
		//取得日志的操作类型
		if(WorkOrderStateEnum.SAVE_WO.getId().equals(wrokorder.getZt())){
			wrokorder.setLogOperationEnum(LogOperationEnum.SAVE_WO);
		}else{
			wrokorder.setLogOperationEnum(LogOperationEnum.SUBMIT_WO);
		}
		//新增工单
		try{
			workOrderMapper.insertSelective(wrokorder);   
		}catch(Exception e){
			e.printStackTrace();
		}
		//新增工单的日志                                                        
		if(WorkOrderEnum.NON_TYPE.getId().equals(wrokorder.getGddlx())){
			if(WorkOrderStateEnum.SAVE_WO.getId().equals(wrokorder.getZt())){
				commonRecService.write(id, TableEnum.B_G_007, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,id);
			}else{
				commonRecService.write(id, TableEnum.B_G_007, user.getId(), czls, LogOperationEnum.SUBMIT_WO, UpdateTypeEnum.SAVE,id);
			}
		}else if(WorkOrderEnum.EO_TYPE.getId().equals(wrokorder.getGddlx())){
			if(WorkOrderStateEnum.SAVE_WO.getId().equals(wrokorder.getZt())){
				commonRecService.write(id, TableEnum.B_G_010, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,id);
			}else{
				commonRecService.write(id, TableEnum.B_G_010, user.getId(), czls, LogOperationEnum.SUBMIT_WO, UpdateTypeEnum.SAVE,id);
			}
		}else{
			if(WorkOrderStateEnum.SAVE_WO.getId().equals(wrokorder.getZt())){
				commonRecService.write(id, TableEnum.B_G_016, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,id);
			}else{
				commonRecService.write(id, TableEnum.B_G_016, user.getId(), czls, LogOperationEnum.SUBMIT_WO, UpdateTypeEnum.SAVE,id);
			}
		}
		//增加工单的附表
		if(wrokorder.getBaseWorkOrder()!=null){
			baseWorkOrderService.insertSelective(wrokorder);           //插入基础工单
			orderSourceService.insertOrderSource(wrokorder);           //插入下达指令表数据
			nonWOCardService.insertSelective(wrokorder);               //插入工单相关工卡的数据
			wOAirMaterialService.insertSelective(wrokorder);           //插入工单航材耗材工具信息
			wOJobContentService.insertSelective(wrokorder);            //插入工作内容的数据
			wOJobEnclosureService.insertSelective(wrokorder);          //给工单的附件表插入数据
			wOActionObjService.insertSelective(wrokorder);             //给执行对象的表插入数据
		}
	    return id;
	}
	
	/**
	 * @author meizhiliang
	 * 修改工单操作包括三种类型工单
	 * @param WorkOrder record
	 */
	@Override
	public int updateByPrimaryKeySelective(WorkOrder wrokorder) {
		int count=0;
		//判断更新的单据类型
		if(WorkOrderEnum.DJRW_TYPE.getId().equals(wrokorder.getGddlx())){       
			count=UpdateCheckTaskBill(wrokorder);
		}else{								//更新工单
			WorkOrder temp_wo=selectByWorkOrder(wrokorder);
			Integer wo_zt = temp_wo.getZt();   //取得数据库中工单的当前状态
			if(WorkOrderStateEnum.SAVE_WO.getId().equals(wrokorder.getZt())){
				if(WorkOrderStateEnum.SAVE_WO.getId().equals(wo_zt)||WorkOrderStateEnum.SHENHEBOHUI_WO.getId().equals(wo_zt)||WorkOrderStateEnum.SHENPIBOHUI_WO.getId().equals(wo_zt)){
					count=doUpdateWorkOrder(wrokorder);
				}else{
					count=-1;
				}
			}else{
				if(WorkOrderStateEnum.SAVE_WO.getId().equals(wo_zt)||WorkOrderStateEnum.SHENHEBOHUI_WO.getId().equals(wo_zt)||WorkOrderStateEnum.SHENPIBOHUI_WO.getId().equals(wo_zt)
				   ||WorkOrderStateEnum.SUBMIT_WO.getId().equals(wo_zt)||WorkOrderStateEnum.YISHENHE_WO.getId().equals(wo_zt)||WorkOrderStateEnum.YIPIZHUN_WO.getId().equals(wo_zt)){
					count=doUpdateWorkOrder(wrokorder);
				}else{
					count=-1;
			    }
			}
		}
		return count;
	}
	
	/**
	 * 更新工单
	 * @param wrokorder
	 * @author meizhiliang
	 */
	private int doUpdateWorkOrder(WorkOrder wrokorder){
		int count=0;
		User user = ThreadVarUtil.getUser();
		String czls = "" ; 
		//取得日志的操作流水
		if(wrokorder.getCzls()==null||"".equals(wrokorder.getCzls())){
			czls=UUID.randomUUID().toString();     
			wrokorder.setCzls(czls);               
		}else{
			czls=wrokorder.getCzls();
		}
		//取得日志的操作类型
		if(wrokorder.getZt()==null ||WorkOrderStateEnum.SAVE_WO.getId().equals(wrokorder.getZt())){
			wrokorder.setLogOperationEnum(LogOperationEnum.EDIT);
		}else{
			wrokorder.setLogOperationEnum(LogOperationEnum.SUBMIT_WO);
		}
		count=workOrderMapper.updateByPrimaryKeySelective(wrokorder);              //更新工单
		//写入工单更新的日志
		if(WorkOrderEnum.NON_TYPE.getId().equals(wrokorder.getGddlx())){
			commonRecService.write(wrokorder.getId(), TableEnum.B_G_007, user.getId(), czls, wrokorder.getLogOperationEnum(), UpdateTypeEnum.UPDATE,wrokorder.getId());
		}else if(WorkOrderEnum.EO_TYPE.getId().equals(wrokorder.getGddlx())){
			commonRecService.write(wrokorder.getId(), TableEnum.B_G_010, user.getId(), czls, wrokorder.getLogOperationEnum(), UpdateTypeEnum.UPDATE,wrokorder.getId());
		}else{
			commonRecService.write(wrokorder.getId(), TableEnum.B_G_016, user.getId(), czls, wrokorder.getLogOperationEnum(), UpdateTypeEnum.UPDATE,wrokorder.getId());
		}
		//写入工单的附表
		if(wrokorder.getBaseWorkOrder()!=null){
			count+=orderSourceService.doByOrder(wrokorder);
			count+=nonWOCardService.doByWOCard(wrokorder);
			count+=wOAirMaterialService.doByAirMaterial(wrokorder);     
			count+=wOJobContentService.doByJobContent(wrokorder);     
			count+=wOActionObjService.doByActionObj(wrokorder);        
			count+=wOJobEnclosureService.doByWOJobEnclosure(wrokorder);   
		}
		return count;
	}
	/**
	 * @author linglong
	 * @param  WorkOrder wrokorder
	 * 更新工单的监控备注
	 */
	public void updateByPrimaryKeys(WorkOrder wrokorder) {
		doUpdateWorkOrder(wrokorder);
	}
	@Override
	public List<WorkOrder> queryRelatedJobCardAll(WorkOrder record) {
		return workOrderMapper.queryRelatedJobCardAll(record);
	}

	@Override
	public List<WorkOrder> selectWorkOrderList(WorkOrder record) {
		return workOrderMapper.selectWorkOrderList(record);
	}

	@Override
	public List<WorkOrder> selectRealtedJobCardByjx(WorkOrder record) {
		return workOrderMapper.selectRealtedJobCardByjx(record);
	}

	@Override
	public WorkOrder selectByWorkOrder(WorkOrder record) {
		return workOrderMapper.selectByWorkOrder(record);
	}
	/**
	 * @author meizhiliang
	 * @discription 工单的审核
	 */
	@Override
	public int updateAudit(WorkOrder record) {
		int count=0;
		Integer wo_zt = selectByWorkOrder(record).getZt();   //取得数据库中工单的当前状态
		if(WorkOrderStateEnum.SUBMIT_WO.getId().equals(wo_zt)){
			User user = ThreadVarUtil.getUser();
			String czls = UUID.randomUUID().toString();  //日志的操作流水
			record.setShbmid(user.getBmdm());
			record.setShrid(user.getId());
			count=workOrderMapper.updateAudit(record);
			//增加日志REC
			if(WorkOrderEnum.NON_TYPE.getId().equals(record.getGddlx())){
				if(WorkOrderStateEnum.YISHENHE_WO.getId().equals(record.getZt())){
					commonRecService.write(record.getId(), TableEnum.B_G_007, user.getId(), czls, LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE,record.getId());
				}else{
					commonRecService.write(record.getId(), TableEnum.B_G_007, user.getId(), czls, LogOperationEnum.SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE,record.getId());
				}
			}else if(WorkOrderEnum.EO_TYPE.getId().equals(record.getGddlx())){
				if(WorkOrderStateEnum.YISHENHE_WO.getId().equals(record.getZt())){
					commonRecService.write(record.getId(), TableEnum.B_G_010, user.getId(), czls, LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE,record.getId());
				}else{
					commonRecService.write(record.getId(), TableEnum.B_G_010, user.getId(), czls, LogOperationEnum.SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE,record.getId());
				}
			}else{
				if(WorkOrderStateEnum.YISHENHE_WO.getId().equals(record.getZt())){
					commonRecService.write(record.getId(), TableEnum.B_G_016, user.getId(), czls, LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE,record.getId());
				}else{
					commonRecService.write(record.getId(), TableEnum.B_G_016, user.getId(), czls, LogOperationEnum.SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE,record.getId());
				}
			}
		}else{
			count=-1;
		}
		return count;
	}
	/**
	 * @author meizhiliang
	 * @discription 工单的批准
	 */
	@Override
	public int updateReply(WorkOrder record) {
		int count=0;
		Integer wo_zt = selectByWorkOrder(record).getZt();   //取得数据库中工单的当前状态
		if(WorkOrderStateEnum.YISHENHE_WO.getId().equals(wo_zt)){
			User user = ThreadVarUtil.getUser();
			String czls = UUID.randomUUID().toString();  //日志的操作流水
			record.setPfbmid(user.getBmdm());
			record.setPfrid(user.getId());
			count=workOrderMapper.updateReply(record);
			if(WorkOrderEnum.NON_TYPE.getId().equals(record.getGddlx())){
				if(WorkOrderStateEnum.YIPIZHUN_WO.getId().equals(record.getZt())){     //批准通过
					commonRecService.write(record.getId(), TableEnum.B_G_007, user.getId(), czls, LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE,record.getId());
				}else if(WorkOrderStateEnum.SHENPIBOHUI_WO.getId().equals(record.getZt())){  //批准驳回
					commonRecService.write(record.getId(), TableEnum.B_G_007, user.getId(), czls, LogOperationEnum.SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE,record.getId());
				}else{
					commonRecService.write(record.getId(), TableEnum.B_G_007, user.getId(), czls, LogOperationEnum.SHOUTDOWN_WO, UpdateTypeEnum.UPDATE,record.getId());
				}
			}else if(WorkOrderEnum.EO_TYPE.getId().equals(record.getGddlx())){
				if(WorkOrderStateEnum.YIPIZHUN_WO.getId().equals(record.getZt())){
					commonRecService.write(record.getId(), TableEnum.B_G_010, user.getId(), czls, LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE,record.getId());
				}else if(WorkOrderStateEnum.SHENPIBOHUI_WO.getId().equals(record.getZt())){
					commonRecService.write(record.getId(), TableEnum.B_G_010, user.getId(), czls, LogOperationEnum.SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE,record.getId());
				}else{
					commonRecService.write(record.getId(), TableEnum.B_G_010, user.getId(), czls, LogOperationEnum.SHOUTDOWN_WO, UpdateTypeEnum.UPDATE,record.getId());
				}
			}else{
				if(WorkOrderStateEnum.YIPIZHUN_WO.getId().equals(record.getZt())){
					commonRecService.write(record.getId(), TableEnum.B_G_016, user.getId(), czls, LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE,record.getId());
				}else if(WorkOrderStateEnum.YIPIZHUN_WO.getId().equals(record.getZt())){
					commonRecService.write(record.getId(), TableEnum.B_G_016, user.getId(), czls, LogOperationEnum.SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE,record.getId());
				}else{
					commonRecService.write(record.getId(), TableEnum.B_G_016, user.getId(), czls, LogOperationEnum.SHOUTDOWN_WO, UpdateTypeEnum.UPDATE,record.getId());
				}
			}
		}else{
			count=-1;
		}
		return count;
	}
	/**
	 * @author meizhiliang
	 * @discription 工单的指定结束
	 */
	@Override
	public int updateOver(WorkOrder record) {
		int count=0;
		Integer wo_zt = selectByWorkOrder(record).getZt();   //取得数据库中工单的当前状态
		if(WorkOrderStateEnum.YIPIZHUN_WO.getId().equals(wo_zt)){
			String czls = "";  //日志的操作流水
			User user = ThreadVarUtil.getUser();
			if(record.getCzls()==null||"".equals(record.getCzls())){
				czls=UUID.randomUUID().toString();      //日志的操作流水
				record.setCzls(czls);                //给日志表的操作流水赋值
			}else{
				czls=record.getCzls();
			}
			if(WorkOrderEnum.DJ_TYPE.equals(record.getGddlx())){               //当工单为定检工单时   
				record.setZdjsrid(user.getId());
				workOrderMapper.updateOver(record);
				commonRecService.write(record.getId(), TableEnum.B_G_016, user.getId(), czls, LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,record.getId());
			}else{
				PlanTaskExt pte=new PlanTaskExt();
				pte.setXggdid(record.getId());
				pte.setCzls(czls);
				pte.setZdjsyy(record.getZdjsyy());
				pte.setLogOperationEnum(LogOperationEnum.GUANBI);
				try {
					String returnCode=planTaskService.doEndByWorkOrder(pte);
					//判断是否存在计划任务
					if(returnCode.equals("01")){  
						record.setZdjsrid(user.getId());
						workOrderMapper.updateOver(record);
						if(WorkOrderEnum.NON_TYPE.getId().equals(record.getGddlx())){
							commonRecService.write(record.getId(), TableEnum.B_G_007, user.getId(), czls, LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,record.getId());
						}else{
							commonRecService.write(record.getId(), TableEnum.B_G_010, user.getId(), czls, LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,record.getId());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else{
			count=-1;
		}
		return count;
	}
	/**
	 * @author meizhiliang
	 * @discription 作废工单
	 */
	@Override
	public int updateCancel(WorkOrder record) {
		int count=0;
		Integer wo_zt = selectByWorkOrder(record).getZt();   //取得数据库中工单的当前状态
		if(WorkOrderStateEnum.SAVE_WO.getId().equals(wo_zt)||WorkOrderStateEnum.SHENHEBOHUI_WO.getId().equals(wo_zt)||WorkOrderStateEnum.SHENPIBOHUI_WO.getId().equals(wo_zt)){
			User user = ThreadVarUtil.getUser();
			String czls = UUID.randomUUID().toString();  //日志的操作流水
			count =workOrderMapper.updateCancel(record); 
			if(WorkOrderEnum.NON_TYPE.getId().equals(record.getGddlx())){
				commonRecService.write(record.getId(), TableEnum.B_G_007, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,record.getId());
			}else if(WorkOrderEnum.EO_TYPE.getId().equals(record.getGddlx())){
				commonRecService.write(record.getId(), TableEnum.B_G_010, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,record.getId());
			}else{
				commonRecService.write(record.getId(), TableEnum.B_G_016, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,record.getId());
			}	
		}else{
			count=-1;
		}
		return count;
	}
	
	
	@Override
	public ModelAndView intoEditByWorkOrder(String id, Integer gddlx) {
		ModelAndView view = null;
		Map<String, Object> model = initializtionWo(id,gddlx);
		WorkOrder wo = (WorkOrder)model.get("woResult") ;
		
		if(WorkOrderEnum.NON_TYPE.getId().equals(gddlx)){        //非例行工单
			if(!WorkOrderTypeEnum.TIMECONTORL_WO.getId().equals(wo.getGdlx())){
				view=new ModelAndView("project/workorder/edit_non", model);
			}else{
				if(WorkOrderStateEnum.SAVE_WO.getId().equals(wo.getZt())||WorkOrderStateEnum.SHENHEBOHUI_WO.getId().equals(wo.getZt())||WorkOrderStateEnum.SHENPIBOHUI_WO.getId().equals(wo.getZt())){
					view=new ModelAndView("project/workorder/edit_non", model);
				}else{
					view=new ModelAndView("redirect:/project/workorder/Looked?id="+id+"&gddlx="+gddlx);
				}
			}
		}else if(WorkOrderEnum.EO_TYPE.getId().equals(gddlx)){    //EO工单
			view=new ModelAndView("project/workorder/edit_eo", model);
		}else{              //其他单据类型
			view=new ModelAndView("redirect:/project/checklist/Looked?id="+id);
		}
		return view;
	}
	/**
	 * @author meizhiliang
	 * @discription 工单初始化
	 */
	@Override
	public Map<String, Object> initializtionWo(String id, Integer gddlx) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("maxWorkOrderTypeEnum", MaxWorkOrderTypeEnum.enumToListMap());        //将工单小类型枚举作为对象传到前台
		model.put("minWorkOrderTypeEnum", MinWorkOrderTypeEnum.enumToListMap());        //将工单大类型枚举作为对象传到前台
		WorkOrder wo=new WorkOrder();
		wo.setId(id);
		wo.setGddlx(gddlx);
		WorkOrder woResult = selectByWorkOrder(wo);          
		if(woResult!=null){
			model.put("baseList", planeBaseService.findBaseByDprtcode(woResult.getDprtcode()));
			List <OrderSource> orderSource=null;                                                                                   //获取工单获取指令来源
			OrderSource os=new OrderSource(); 
		    os.setZlid(woResult.getId());
			if(WorkOrderEnum.EO_TYPE.getId().equals(gddlx)){
				os.setZlxl(WorkOrderEnum.ENGINEER_EO_TYPE.getId());
				orderSource=orderSourceService.selectByOrder(os);
			}else{
				os.setZlxl(WorkOrderTypeEnum.NONROUTINE_WO.getId());
				orderSource=orderSourceService.selectByOrder(os);
			}
			WOAirMaterial wam=new WOAirMaterial();                                                                                      //工单的航材耗材
			wam.setMainid(woResult.getGdjcid());
			List <WOAirMaterial> woAirMaterial=wOAirMaterialService.selectWOAirMaterialList(wam);
			
			WOJobContent wjc=new WOJobContent();                                                                                          //工单的工作内容
	        wjc.setMainid(woResult.getGdjcid());
			List <WOJobContent> wOJobContent=wOJobContentService.selectByWOJobContentList(wjc);
			
			WOActionObj wao2=new WOActionObj();                                                                                           //将执行对象传到前台
			wao2.setMainid(woResult.getGdjcid());
			wao2.setDprtcode(woResult.getDprtcode());
			WOActionObj wOActionObj=wOActionObjService.selectByActionObj(wao2);
			if(woResult.getGddlx()!=null){
				//当工单为定检工单和时控件工单时 需要动态加载监控项
				if(WorkOrderEnum.DJ_TYPE.getId().equals(woResult.getGddlx())||WorkOrderTypeEnum.TIMECONTORL_WO.getId().equals(woResult.getGdlx())){
					if(null!=wOActionObj){
						if(wOActionObj.getJkxmbhF()!=null){
							wOActionObj.setJkxmbhF(MonitorItemEnum.getName(wOActionObj.getJkxmbhF()));
						}
						if(wOActionObj.getJkxmbhS()!=null){
							wOActionObj.setJkxmbhS(MonitorItemEnum.getName(wOActionObj.getJkxmbhS()));
						}
						if(wOActionObj.getJkxmbhT()!=null){
							wOActionObj.setJkxmbhT(MonitorItemEnum.getName(wOActionObj.getJkxmbhT()));
						}
					}
				}
			}
			NonWOCard nwc=new NonWOCard();               //工单的相关工单
			//如果为定检工单则需要关联到相关工卡的相关工单
			if(WorkOrderEnum.DJ_TYPE.getId().equals(gddlx)){               
				JobCard jobcard = jobCardService.getByPrimaryKey(woResult.getDjgkid());
				nwc.setMainid(jobcard.getGdjcid());
			}else{
				nwc.setMainid(woResult.getGdjcid());
			}
			List<NonWOCard> nonWOCardd=nonWOCardService.selectByNonWOCardList(nwc);
			
			WOJobEnclosure wje=new WOJobEnclosure();                                                                                           //工单的附件
			wje.setMainid(woResult.getGdjcid());
			List<WOJobEnclosure> woJobEnclosure=wOJobEnclosureService.selectedFjList(wje);
			
			model.put("woResult", woResult);
			model.put("orderSource", orderSource);
			model.put("woAirMaterial", woAirMaterial);
			model.put("wOJobContent", wOJobContent);
			model.put("wOActionObj", wOActionObj);
			model.put("nonWOCardd", nonWOCardd);
			model.put("woJobEnclosure", woJobEnclosure);
		}
		return model;
	}

	@Override
	public List<WorkOrder> queryAll(Map<String ,Object> map) {
		return workOrderMapper.queryAll(map);
	}
	
	/**
	 * 开窗分页查询 相关工单
	 * @author xu.yong
	 * @param record
	 * @return
	 */
	public Map<String, Object> queryGlgdPage(BaseEntity record) {
		
		PageHelper.startPage(record.getPagination());
		List<WorkOrder> list = this.workOrderMapper.queryGlgdPage(record);
		return PageUtil.pack4PageHelper(list, record.getPagination());
	}

	@Override
	public void doEnd(WorkOrder workorder) {
		workOrderMapper.doEnd(workorder);
	}

	/**
	 * 查询所有EO工单（不分页）
	 * @param gczlmxId
	 * @author sunji
	 * @return
	 */
	public List<WorkOrder> queryAllByDetailEngineeringId() {
		return workOrderMapper.queryAllByDetailEngineeringId();
	}
	/**
	 * @author meizhiliang
	 * @discription 批量审核工单
	 */
	@Override
	public String doUpdateBatchReview(WorkOrder record) {

		List<String> auditIdList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		List<WorkOrder> fItemListNon = workOrderMapper.queryByIdListNon(record.getIds());
		List<WorkOrder> fItemListEo = workOrderMapper.queryByIdListEo(record.getIds());
		for (WorkOrder item :fItemListEo) {
			if(WorkOrderStateEnum.SUBMIT_WO.getId().equals(item.getZt())){
				auditIdList.add(item.getId());
				buffer.append("工单【").append(item.getGdbh()).append("】审核通过!<br>");
			}else{
				buffer.append("工单【").append(item.getGdbh()).append("】数据已更新,未能审核成功!<br>");
			}
		}
		for (WorkOrder item : fItemListNon) {
			if(WorkOrderStateEnum.SUBMIT_WO.getId().equals(item.getZt())){
				auditIdList.add(item.getId());
				buffer.append("工单【").append(item.getGdbh()).append("】审核通过!<br>");
			}else{
				buffer.append("工单【").append(item.getGdbh()).append("】数据已更新,未能审核成功!<br>");
			}
		}
		String czls = UUID.randomUUID().toString();  //日志的操作流水
		User user = ThreadVarUtil.getUser();
		record.setIds(auditIdList);
		record.setShbmid(user.getBmdm());
		record.setShrid(user.getId());
		record.setZt(WorkOrderStateEnum.YISHENHE_WO.getId());
		record.setCzls(czls);
		record.setCzlx("review");
		if(record.getIds().size()>0){
			workOrderMapper.updateBatchReviewAndApproveNon(record);
			commonRecService.write("id", record.getIds(), TableEnum.B_G_007, user.getId(), czls, LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE,null);
			workOrderMapper.updateBatchReviewAndApproveEO(record);
			commonRecService.write("id", record.getIds(), TableEnum.B_G_010, user.getId(), czls, LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE,null);
		}
		return buffer.toString();
	}
	/**
	 * @author meizhiliang
	 * @discription 批量批准工单
	 */
	@Override
	public String doUpdateBatchApprove(WorkOrder record) {
		List<String> approveIdList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		List<WorkOrder> fItemListEo = workOrderMapper.queryByIdListEo(record.getIds());
		List<WorkOrder> fItemListNon = workOrderMapper.queryByIdListNon(record.getIds());
		for (WorkOrder item : fItemListEo) {
			if(WorkOrderStateEnum.YISHENHE_WO.getId().equals(item.getZt())){
				approveIdList.add(item.getId());
				buffer.append("工单【").append(item.getGdbh()).append("】批准通过!<br>");
			}else{
				buffer.append("工单【").append(item.getGdbh()).append("】数据已更新,未能审核成功!<br>");
			}
		}
		for (WorkOrder item : fItemListNon) {
			if(WorkOrderStateEnum.YISHENHE_WO.getId().equals(item.getZt())){
				approveIdList.add(item.getId());
				buffer.append("工单【").append(item.getGdbh()).append("】批准通过!<br>");
			}else{
				buffer.append("工单【").append(item.getGdbh()).append("】数据已更新,未能审核成功!<br>");
			}
		}
		String czls = UUID.randomUUID().toString();  //日志的操作流水
		User user = ThreadVarUtil.getUser();
		record.setIds(approveIdList);
		record.setPfbmid(user.getBmdm());
		record.setPfrid(user.getId());
		record.setZt(WorkOrderStateEnum.YIPIZHUN_WO.getId());
		record.setCzls(czls);
		if(record.getIds().size()>0){
			workOrderMapper.updateBatchReviewAndApproveNon(record);
			commonRecService.write("id", record.getIds(), TableEnum.B_G_007, user.getId(), czls, LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE,null);
			workOrderMapper.updateBatchReviewAndApproveEO(record);
			commonRecService.write("id", record.getIds(), TableEnum.B_G_010, user.getId(), czls, LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE,null);
		}
		return buffer.toString();
	}

	@Override
	public int UpdateCheckTaskBill(WorkOrder workorder) {
		String czls = UUID.randomUUID().toString();  //日志的操作流水
		User user = ThreadVarUtil.getUser();
		CheckTaskBill ctb=new CheckTaskBill();
		ctb.setId(workorder.getId());
		checkTaskBillMapper.updateByPrimaryKeySelective(ctb);
		commonRecService.write(ctb.getId(), TableEnum.B_G_014, user.getId(),czls,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,workorder.getId());
		return 0;
	}

	@Override
	public List<WorkOrder> selectCopyList(WorkOrder record) {
		List<WorkOrder> list =workOrderMapper.selectCopyList(record);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public WorkOrder selectByCopy(WorkOrder workorder) {
		//获取工单及相应附表的所有内容
	    Map<String, Object> model = initializtionWo(workorder.getId(),workorder.getGddlx());
		BaseWorkOrder bwo=new BaseWorkOrder();
		WorkOrder woResult=(WorkOrder) model.get("woResult");
		//重新组合工单对象
		bwo.setOrderSourceList((List<OrderSource>)model.get("orderSource"));
		bwo.setwOActionObj((WOActionObj) model.get("wOActionObj"));
		bwo.setWoJobContent((List<WOJobContent>)model.get("wOJobContent"));
		bwo.setWoAirMaterial((List<WOAirMaterial>) model.get("woAirMaterial"));
		bwo.setWoJobenclosure((List<WOJobEnclosure>) model.get("woJobEnclosure"));
		bwo.setNonwocardList((List<NonWOCard>) model.get("nonWOCardd"));
		woResult.setBaseWorkOrder(bwo);
		
		return woResult;
	}
	
	/**
	 * @author liub
	 * @description 根据工程指令id查询EO工单数据
	 * @param gczlid
	 * @develop date 2017.03.17
	*/
	@Override
	public List<WorkOrder> queryByGczlId(String gczlid){
		return workOrderMapper.queryByGczlId(gczlid);
	}
	
	/**
	 * @author liub
	 * @description 根据评估单id、工单类型查询非例行工单数据
	 * @param pgdid,gdlx
	 * @return List<WorkOrder>
	 * @develop date 2017.03.20
	*/
	@Override
	public List<WorkOrder> queryByPgdIdAndGdlx(String pgdid,String gdlx){
		return workOrderMapper.queryByPgdIdAndGdlx(pgdid,gdlx);
	}
}
