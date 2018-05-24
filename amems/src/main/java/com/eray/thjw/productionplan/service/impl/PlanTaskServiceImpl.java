package com.eray.thjw.productionplan.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.po.HcTaskInfo;
import com.eray.thjw.aerialmaterial.po.HcTaskRelOrder;
import com.eray.thjw.dao.EoBillMapper;
import com.eray.thjw.dao.NonroutineMapper;
import com.eray.thjw.dao.PlaneModelDataMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.CheckTaskBill;
import com.eray.thjw.po.Engineering;
import com.eray.thjw.po.EoBill;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.Nonroutine;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.productionplan.dao.CheckBillMapper;
import com.eray.thjw.productionplan.dao.PlanTaskMapper;
import com.eray.thjw.productionplan.dao.PlanTaskSnapMapper;
import com.eray.thjw.productionplan.dao.PlaneDataMapper;
import com.eray.thjw.productionplan.po.PlanTask;
import com.eray.thjw.productionplan.po.PlanTaskExt;
import com.eray.thjw.productionplan.po.PlanTaskHistory;
import com.eray.thjw.productionplan.po.PlanTaskSnap;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.service.CheckTaskBillService;
import com.eray.thjw.productionplan.service.PlanTaskService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.EngineeringService;
import com.eray.thjw.service.WorkOrderService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.BinaryEnum;
import enu.CompleteSourceEnum;
import enu.LogOperationEnum;
import enu.PlanTaskDispalyState;
import enu.PlanTaskState;
import enu.PlanTaskSubType;
import enu.PlanTaskType;
import enu.SortEnum;
import enu.TableEnum;
import enu.ThresholdEnum;
import enu.UpdateTypeEnum;

/**
 * 计划任务服务实现
 * @author zhuchao
 *
 */

@Service
public class PlanTaskServiceImpl implements PlanTaskService {

	@Resource
	private WorkOrderService workOrderService;
	
	@Resource
	private PlanTaskMapper planTaskMapper;
	
	@Resource
	private PlanTaskSnapMapper planTaskSnapMapper;
	
	
	@Resource
	private NonroutineMapper nonroutineMapper;
	
	@Resource
	private EoBillMapper eoBillMapper;
	
	@Resource
	private CheckBillMapper checkBillMapper;
	
	@Resource
	private CheckTaskBillService checkTaskBillService;
	
	@Resource
	private PlaneDataMapper planeDataMapper;
	
	@Resource
	private PlaneModelDataMapper planeModelDataMapper;
	
	@Resource
	private HCMainDataMapper hcMainDataMapper;
	
	@Resource
	private CommonRecService commonRecService; 
	
	@Resource
	private CommonService commonService; 
	
	@Resource
	private EngineeringService engineeringService;
	
	@Resource
	private AttachmentMapper attachmentMapper;
	
	private  static final String SYDAYS = "sydays";
	
	@Resource
	private MonitorsettingsService monitorsettingsService;
	
	
	
	@Override
	public PlanTask selectByPrimaryKey(String id) throws BusinessException {
		try {
			return planTaskMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			throw new BusinessException("查询计划任务失败",e);
		}
	}

	@Override
	public Map<String, Object> doEnd(PlanTaskExt planTask) throws BusinessException { 
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("isLegitimate", Boolean.TRUE);
			PlanTask row = planTaskMapper.selectByPrimaryKey(planTask.getId());
			//提交状态可以指定结束。
			if(row.getZt()==null || !row.getZt().equals(PlanTaskState.EXECUTING.getId())){
				result.put("isLegitimate", Boolean.FALSE);
				result.put("message", "状态已变更，维修计划不能指定结束。");
			}
			else{
			
				String czls = UUID.randomUUID().toString();
				//对计划任务进行指定结束
				Date date = commonService.getSysdate();
				User user = ThreadVarUtil.getUser();
				
				//上传附件
				List<Attachment> list = planTask.getAttachments();
				if (list!= null && !list.isEmpty()) {
					List<String> columnValueList = new ArrayList<String>();
					for (Attachment attachment : list) {
						attachment.setMainid(planTask.getId());
						attachment.setDprtcode(user.getJgdm());
						attachment.setId(UUID.randomUUID().toString());
						attachment.setCzrid(user.getId());
						attachment.setCzbmid(user.getBmdm());
						attachmentMapper.addFile(attachment);
						columnValueList.add(attachment.getId());
					}
					commonRecService.write("id", columnValueList, TableEnum.D_011, user.getId(),czls,LogOperationEnum.GUANBI, UpdateTypeEnum.SAVE,planTask.getId());
				}
				
				//从DB获取任务类型，相关工单ID
				PlanTask planTaskDb = planTaskMapper.selectByPrimaryKey(planTask.getId());
				PlanTaskExt planTaskSnapTemp = planTaskMapper.genPlanTaskSnap(planTaskDb);
				PlanTaskSnap planTaskSnap = new PlanTaskSnap (planTaskSnapTemp);
				planTaskSnap.setWhsj(date);
				planTaskSnap.setWhrid(user.getId());
				planTaskSnap.setWhdwid(user.getBmdm());
				planTaskSnap.setId(UUID.randomUUID().toString());
				
				planTaskSnapMapper.insertSelective(planTaskSnap);
				commonRecService.write(planTaskSnap.getId(), TableEnum.B_S_00901, user.getId(),czls ,LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,planTask.getId());
				
				planTask.setWhrid(user.getId());
				planTask.setWhdwid(user.getBmdm());
				planTask.setWhsj(date);
				planTask.setZt(PlanTaskState.CLOSE.getId());
				planTask.setZdjsrid(user.getId());
				planTask.setZdjsrq(date);
				planTaskMapper.updateByPrimaryKeySelective(planTask);
				
				//设置下游参数
				planTask.setRwlx(planTaskDb.getRwlx());
				planTask.setXggdid(planTaskDb.getXggdid());
				planTask.setRwzlx(planTaskDb.getRwzlx());
				
				//定检件，时控件类计划任务指定结束附件业务
				/* 时空件 更新 b_s_003 -GDID,RWID =NULL 
				 * 工单执行对象.zjqdid = b_s_003.ID 
				 * 
				 *定检件 更新 b_s_00303 -RWID =NULL
				 *b_g_01401.zjqdid = = b_s_00303.zjqdid 
				 *b_g_014 定检任务主表 .djxmid = b_s_00303.DJXMID 
				 * 
				 */
				if (StringUtils.isNotBlank(planTask.getXggdid())
						&& planTask.getRwlx() != null ) {
					if ( PlanTaskType.isEoBill(planTask.getRwlx())) {
						//对EO工单进行指定结束
						EoBill eoBill = new EoBill();
						eoBill.setZt(PlanTaskState.CLOSE.getId());
						eoBill.setZdjsrid(planTask.getZdjsrid());
						eoBill.setZdjsrq(date);
						eoBill.setZdjsyy(planTask.getZdjsyy());
						eoBill.setId(planTask.getXggdid());
						
						eoBillMapper.doEnd(eoBill);
						commonRecService.write(eoBill.getId(), TableEnum.B_G_010, user.getId(),czls,LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,planTask.getId());
						
						//如果是EO工单，更新工程指令的状态为完成。
						if (PlanTaskType.isEoBill(planTaskDb.getRwlx())) {
							// 获取对应工程指令
							Engineering engineering = engineeringService.findByEOId(planTaskDb.getXggdid());
							// 更新工程指令状态
							engineeringService.updateStatus(engineering.getId(), czls, LogOperationEnum.WANCHEN);
						}
					}
					else if ( PlanTaskType.isCheckBill(planTask.getRwlx())) {
						//对定检任务单进行指定结束
						CheckTaskBill checkTaskBill = new CheckTaskBill();
						checkTaskBill.setZt(PlanTaskState.CLOSE.getId());
						checkTaskBill.setZdjsrid(planTask.getZdjsrid());
						checkTaskBill.setZdjsrq(date);
						checkTaskBill.setZdjsyy(planTask.getZdjsyy());
						checkTaskBill.setId(planTask.getXggdid());
						
						checkTaskBillService.doEndByPlanTask(checkTaskBill);
						commonRecService.write(checkTaskBill.getId(), TableEnum.B_G_014, user.getId(),czls,LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,planTask.getId());
						planTaskMapper.doEndByCheckbill(planTask.getId());
					}
					else if ( PlanTaskType.isNonRoutine(planTask.getRwlx())) {
						//对非例行工单进行指定结束
						Nonroutine nonroutine = new Nonroutine();
						nonroutine.setZt(PlanTaskState.CLOSE.getId());
						nonroutine.setZdjsrid(planTask.getZdjsrid());
						nonroutine.setZdjsrq(date);
						nonroutine.setZdjsyy(planTask.getZdjsyy());
						nonroutine.setId(planTask.getXggdid());
						
						nonroutineMapper.doEnd(nonroutine);
						commonRecService.write(nonroutine.getId(), TableEnum.B_G_007, user.getId(),czls,LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,planTask.getId());
						
						//如果是时控件-不计日志
						if (PlanTaskSubType.isTimeControlPart(planTask.getRwzlx())) {
							planTaskMapper.doEndByTimecontrolpart(planTask.getId());
						}
					}
				}
				
				commonRecService.write(planTask.getId(), TableEnum.B_S_009, user.getId(),czls,LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,planTask.getId());
			}
			return result;
		} catch (Exception e) {
			 throw new BusinessException("计划任务指定结束失败",e);
		}
	}
	
	@Override
	public String doEndByWorkOrder(PlanTaskExt planTask) throws Exception { 
		
		//对计划任务进行指定结束
		String czls = planTask.getCzls();
		Date date = commonService.getSysdate();;
		User user = ThreadVarUtil.getUser();
			
		//从DB获取任务类型，相关工单ID
		PlanTask planTaskDb = planTaskMapper.selectByXggdid(planTask.getXggdid());
		if (planTaskDb!=null) {
			planTask.setRwlx(planTaskDb.getRwlx());
			planTask.setXggdid(planTaskDb.getXggdid());
			planTask.setRwzlx(planTaskDb.getRwzlx());
			planTask.setId(planTaskDb.getId());
			
			PlanTaskExt planTaskSnapTemp = planTaskMapper.genPlanTaskSnap(planTaskDb);
			PlanTaskSnap planTaskSnap = new PlanTaskSnap (planTaskSnapTemp);
			planTaskSnap.setWhsj(date);
			planTaskSnap.setWhrid(user.getId());
			planTaskSnap.setWhdwid(user.getBmdm());
			
			planTaskSnapMapper.insertSelective(planTaskSnap);
			commonRecService.write(planTaskSnap.getId(), TableEnum.B_S_00901, user.getId(),planTask.getCzls(),LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE, planTask.getId());
			
			planTask.setWhrid(user.getId());
			planTask.setWhdwid(user.getBmdm());
			planTask.setWhsj(date);
			 
			planTask.setZt(PlanTaskState.CLOSE.getId());
			planTask.setZdjsrid(user.getId());
			planTask.setZdjsrq(date);
			
			planTaskMapper.updateByPrimaryKeySelective(planTask);
			
			//定检件，时控件类计划任务指定结束附件业务
			/*时空件 更新 b_s_003 -GDID,RWID =NULL 
			 * 工单执行对象.zjqdid = b_s_003.ID 
			 * 
			 *定检件 更新 b_s_00303 -RWID =NULL
			 *b_g_01401.zjqdid = = b_s_00303.zjqdid 
			 *b_g_014 定检任务主表 .djxmid = b_s_00303.DJXMID 
			 * 
			 */
			if (StringUtils.isNotBlank(planTask.getXggdid())
					&& planTask.getRwlx() != null ) {
				 if ( PlanTaskType.isEoBill(planTask.getRwlx())) {
					//对EO工单进行指定结束
					EoBill eoBill = new EoBill();
					eoBill.setZt(PlanTaskState.CLOSE.getId());
					eoBill.setZdjsrid(planTask.getZdjsrid());
					eoBill.setZdjsrq(date);
					eoBill.setZdjsyy(planTask.getZdjsyy());
					eoBill.setId(planTask.getXggdid());
					eoBillMapper.doEnd(eoBill);
					commonRecService.write(eoBill.getId(), TableEnum.B_G_010, user.getId(),czls,LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE, planTask.getId());
					
					//如果是EO工单，更新工程指令的状态为完成。
//						PlanTask planTaskDb = planTaskMapper.selectByPrimaryKey(planTask.getId());
					if (PlanTaskType.isEoBill(planTaskDb.getRwlx())) {
						// 获取对应工程指令
						Engineering engineering = engineeringService.findByEOId(planTaskDb.getXggdid());
						// 更新工程指令状态
						engineeringService.updateStatus(engineering.getId(), czls, LogOperationEnum.WANCHEN);
					}
				}
				else if ( PlanTaskType.isCheckBill(planTask.getRwlx())) {
					//对定检任务单进行指定结束
					CheckTaskBill checkTaskBill = new CheckTaskBill();
					checkTaskBill.setZt(PlanTaskState.CLOSE.getId());
					checkTaskBill.setZdjsrid(planTask.getZdjsrid());
					checkTaskBill.setZdjsrq(date);
					checkTaskBill.setZdjsyy(planTask.getZdjsyy());
					checkTaskBill.setId(planTask.getXggdid());
					checkTaskBillService.doEndByPlanTask(checkTaskBill);
					commonRecService.write(checkTaskBill.getId(), TableEnum.B_G_014, user.getId(),czls,LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE, planTask.getId());
					planTaskMapper.doEndByCheckbill(planTask.getId());
				}
				else if (PlanTaskType.isNonRoutine(planTask.getRwlx())) {
					//对非例行工单进行指定结束
					Nonroutine nonroutine = new Nonroutine();
					nonroutine.setZt(PlanTaskState.CLOSE.getId());
					nonroutine.setZdjsrid(planTask.getZdjsrid());
					nonroutine.setZdjsrq(date);
					nonroutine.setZdjsyy(planTask.getZdjsyy());
					nonroutine.setId(planTask.getXggdid());
					nonroutineMapper.doEnd(nonroutine);
					commonRecService.write(nonroutine.getId(), TableEnum.B_G_007, user.getId(),czls,LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE, planTask.getId());
					
					//如果是时控件-不计日志
					if (PlanTaskSubType.isTimeControlPart(planTask.getRwzlx())) {
						planTaskMapper.doEndByTimecontrolpart(planTask.getId());
					}
				}
			}
			commonRecService.write(planTask.getId(), TableEnum.B_S_009, user.getId(),planTask.getCzls(),planTask.getLogOperationEnum(), UpdateTypeEnum.UPDATE, planTask.getId());

			return "00";
		}
		else{
			return "01";
		}
	}

	@Override
	public Map<String, List<PlanTask>> queryListPanel(PlanTask planTask) throws RuntimeException {
		//查询计划任务
		Map<String, List<PlanTask>> map = new TreeMap<String, List<PlanTask>>();
		List<PlanTask> list = planTaskMapper.queryListPanel(planTask) ;
		//将飞机注册号，计划任务组装成键值对
		for (PlanTask task : list) {
			List<PlanTask> listInPlain = null;
			//如果非装机件，飞机注册号固定：fzjj_fjzch
			if (StringUtils.isBlank(task.getFjzch())) {
				task.setFjzch("fzjj_fjzch");
			}
			if (map.containsKey(task.getFjzch())) {
				listInPlain = map.get(task.getFjzch());
				listInPlain.add(task);
			}
			else{
				listInPlain = new ArrayList<PlanTask>();
				listInPlain.add(task);
				map.put(task.getFjzch(),listInPlain);
			}
		}
		return map;
	}
 
	@Override
	public Map<String, String> queryTotalByGroup(PlanTask planTask) throws RuntimeException {
		 Map<String, String> map =  new HashMap<String, String>();
		 int fzjjs = 0;
		 int zjjs = 0;
		 //查询计划任务，分别统计出装机件，非装机件数量
		 String isLoadedParts = planTask.getIsLoadedParts();
		 //查询装机件计划数 
		 String fjzch = planTask.getFjzch();
		 String keyword = planTask.getKeyword();
		 planTask.setIsLoadedParts(BinaryEnum.YES.getId().toString());
		 zjjs = planTaskMapper.queryListCount(planTask);
		 
		 //查询非装机件计划数
		 planTask.setIsLoadedParts(BinaryEnum.NO.getId().toString());
		 planTask.setFjzch(null);
		 fzjjs = planTaskMapper.queryListCount(planTask);
		  
		 planTask.setIsLoadedParts(StringUtils.isNotBlank(isLoadedParts)?isLoadedParts:BinaryEnum.YES.getId().toString());
		 planTask.setFjzch(fjzch);
		 planTask.setKeyword(keyword);
		 map.put("fzjjs", String.valueOf(fzjjs));
		 map.put("zjjs", String.valueOf(zjjs));
		return map;
	}

	@Override
	public Map<String, Object> doComplete(PlanTask planTask) throws BusinessException {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("isLegitimate", Boolean.TRUE);
			PlanTask row = planTaskMapper.selectByPrimaryKey(planTask.getId());
			 
			//状态为1，2，显示状态为1或2可以执行完成操作
			if (PlanTaskState.canQuery(row.getZt()) && PlanTaskDispalyState.waitComplete(row.getXszt())) {
				planTask.setXszt(PlanTaskDispalyState.COMPLETE.getId());
				planTaskMapper.updateByPrimaryKeySelective(planTask);
				User user = ThreadVarUtil.getUser();
				String czls = UUID.randomUUID().toString();
				commonRecService.write(planTask.getId(), TableEnum.B_S_009, user.getId(),czls ,LogOperationEnum.WANGONG, UpdateTypeEnum.UPDATE,planTask.getId());
			}
			else{
				result.put("isLegitimate", Boolean.FALSE);
				result.put("message", "状态已变更，不能执行完成计划任务。");
			}
			return result;
		} catch (Exception e) {
			throw new BusinessException("完成计划任务失败",e);
		}
		
	}
	
	@Override
	public Map<String, Object> queryPlanTaskList(PlanTaskExt planTask) throws BusinessException {
		try {
			String id = planTask.getId();
			if (StringUtils.isNotEmpty(id)) {
				planTask.setId(null);
			}
			
			Map<String, Object> result = new HashMap<String, Object>();
			List<PlanTaskExt> list = colulatePlanTask(planTask);
			result = PageUtil.pack(list,id);
			result.put("total", list==null?0:list.size());
			result.put("rows", list);
			return result;
		} catch (Exception e) {
			 throw new BusinessException("查询计划任务失败",e);
		}
	}

	private List<PlanTaskExt> colulatePlanTask(PlanTaskExt planTask) throws Exception {
		Pagination pagination = planTask.getPagination();
		Boolean sortBydays = isSortBySyDays(pagination);
		//剩余天数采用内存排序，清除DB排序
		if (sortBydays) {
			pagination.setSort("");
		}
		List<PlanTaskExt> list = planTaskMapper.queryPlanTaskList(planTask);
		
		Monitorsettings monitorsettings = monitorsettingsService.getByKeyDprtcode(ThresholdEnum.SCJK.getName(),planTask.getDprtcode());
		
		if (!list.isEmpty()) {
			//查询机型日使用量
			PlaneModelData planeModelData =	planeModelDataMapper.selectPlanes(planTask.getFjzch(),planTask.getDprtcode());
			//查询飞机注册信息，
			PlaneData  planeData = null;
			if (StringUtils.isNotBlank(planTask.getFjzch())) {
				PlaneData param = new PlaneData(planTask.getFjzch(),planTask.getDprtcode());
				planeData = planeDataMapper.queryByFjzch(param);
			}
			//装机件-设置飞机初始数据
			for (PlanTaskExt task : list) {
				if (StringUtils.isNotBlank(planTask.getFjzch())) {
					task.setPlaneData(planeData);
				}
				task.setPlaneModelData(planeModelData);
				task.calculateRemainderdays();
				task.calculateSortVal();
				
				if (Integer.valueOf(task.getSydays())<=monitorsettings.getYjtsJb1() ) {
					//#ff9c9c
					task.setBgcolor("#ff9c9c");
				}
				else if (Integer.valueOf(task.getSydays())<=monitorsettings.getYjtsJb2() ) {
					//#fefe95
					task.setBgcolor("#fefe95");
				}
				
			}
			//如果排序字段是‘剩余天数’则内存排序，否则采用DB排序
			if (sortBydays) {
				
				if (pagination.getOrder().equals(SortEnum.DESC.getName())) {
					syDaysDesc(list);
				}
				else{
					syDaysAsc(list);
				}
			}
		}
		return list;
	}

	/**
	 * 按剩余天数排序
	 * @param planTask
	 * @return
	 */
	private boolean isSortBySyDays(Pagination pagination) {
		return pagination != null 
				&& StringUtils.isNotBlank(pagination.getSort())
				&& pagination.getSort().equals(SYDAYS);
	}

	/**
	 * 对剩余天数排逆序
	 * @param list
	 */
	private void syDaysDesc(List<PlanTaskExt> list) {
		Collections.sort(list, new Comparator<PlanTaskExt>() {
			@Override
			public int compare(PlanTaskExt o1, PlanTaskExt o2) {
				return o2.getSortVal().compareTo(o1.getSortVal());
			}
		});
	}

	/**
	 * 对剩余天数排升序
	 * @param list
	 */
	private void syDaysAsc(List<PlanTaskExt> list) {
		Collections.sort(list, new Comparator<PlanTaskExt>() {
			@Override
			public int compare(PlanTaskExt o1, PlanTaskExt o2) {
				return o1.getSortVal().compareTo(o2.getSortVal());
			}
		});
	}

	@Override
	public Map<String, Object> queryHistoryList(PlanTaskHistory planTask) throws BusinessException{
		try {
			Map<String,PlanTaskSnap> map = new HashMap<String,PlanTaskSnap>() ;
			PageHelper.startPage(planTask.getPagination());
			List<PlanTaskHistory> list = planTaskMapper.queryHistoryList(planTask);
			
			if (list!=null && !list.isEmpty()) {
				//获取需要组装快照的计划任务
				List<String> ids = new ArrayList<String>();
				for (PlanTaskHistory task : list) {
					ids.add(task.getId());
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("ids", ids);
				
				//根据ids 查询快照列表
				List<PlanTaskSnap> planTaskSnaps =  planTaskSnapMapper.queryList(params);
				for (PlanTaskSnap snap : planTaskSnaps) {
					if (!map.containsKey(snap.getMainid())) {
						map.put(snap.getMainid(),snap);
					}
				}
				//组装附件
				if(ids.size()>0){
					List<Attachment> attList=attachmentMapper.queryPlaneDataListAttachmentsByMainIds(ids);
					if(attList.size()>0){
						for (PlanTaskHistory planTaskHistory : list) {
							List<Attachment> attachments=new ArrayList<Attachment>();					
							for (Attachment attachment : attList) {
								if(planTaskHistory.getId()!=null&&planTaskHistory.getId().equals(attachment.getMainid())){
									attachments.add(attachment);
								}							
							}
							planTaskHistory.setAttachments(attachments);							
						}
						
					}
				}
				
				//将快照组装到计划任务
				for (PlanTaskHistory task : list) {
					if (map.containsKey(task.getId())) {
						task.setPlanTaskSnap(map.get(task.getId()));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, planTask.getPagination());
		} catch (RuntimeException e) {
			throw new BusinessException("查询计划任务历史失败",e);
		}
	}

	@Override
	public Map<String, Object> hcStatistics(HCMainData record) throws RuntimeException { 
		 
		Map<String, Object> result = new HashMap<String, Object>();
		//航材消耗件
		List<HCMainData> rows =  hcMainDataMapper.selectHcxhFir(record);
		if (rows!= null && !rows.isEmpty()) {
			//组装map 部件号-航材
			Map<String, HCMainData> hcMainDataMap = new HashMap<String, HCMainData>();
			for (HCMainData hcMainData : rows) {
				hcMainDataMap.put(hcMainData.getBjid(), hcMainData);
			}
			
			//任务自身工单信息
			List<HcTaskInfo> listSec =  hcMainDataMapper.selectHcxhSec(record);
			//组装map 部件号工单-工单信息
			Map<String, HcTaskInfo> hcTaskInfoMap = new HashMap<String, HcTaskInfo>();
			List<HcTaskInfo> hcTaskInfos = null;
			String key = null;
			if (listSec!=null && !listSec.isEmpty()) {
				for (HcTaskInfo hcTaskInfo : listSec) {
					 
					if (StringUtils.isNotBlank( hcTaskInfo.getBjid()) ) {
						//组装rwgdid-任务信息map
						key = hcTaskInfo.getBjid().concat(hcTaskInfo.getGdid()).concat(hcTaskInfo.getXh());
						hcTaskInfoMap.put(key, hcTaskInfo);
						
						//将任务工单信息组装到航材主单上
						if (hcMainDataMap.containsKey(hcTaskInfo.getBjid())) {
							hcTaskInfos = hcMainDataMap.get(hcTaskInfo.getBjid()).getHcTaskInfos();
							if(hcTaskInfos!=null){
								hcTaskInfos.add(hcTaskInfo);
							}
							else{
								hcTaskInfos = new ArrayList<HcTaskInfo>();
								hcTaskInfos.add(hcTaskInfo);
								hcMainDataMap.get(hcTaskInfo.getBjid()).setHcTaskInfos(hcTaskInfos);
							}
						}
					}
				}
				//任务：工单-主工单
				List<HcTaskRelOrder> listThi =  hcMainDataMapper.selectHcxhThi(record );
				if (listThi!=null && !listThi.isEmpty()) {
					//将主单-相关工单信息 组装到任务信息
					for (HcTaskRelOrder hcTaskRelOrder : listThi) {
						//将主单-相关工单信息 组装到任务信息
						if (hcTaskRelOrder.esMasterBill()) {
							hcTaskRelOrder.setZgdid(hcTaskRelOrder.getGdid());
							key = hcTaskRelOrder.getBjid().concat(hcTaskRelOrder.getRwgdid()).concat(hcTaskRelOrder.getXh());
							List<HcTaskRelOrder> relorders = hcTaskInfoMap.get(key).getHcTaskRelOrders();
							relorders.add(hcTaskRelOrder);
							hcTaskInfoMap.get(key).setHcTaskRelOrders(relorders);
						}
					}
					
					for (HcTaskRelOrder hcTaskRelOrder : listThi) {
						//将主单-相关工单信息 组装到任务信息
						if (!hcTaskRelOrder.esMasterBill()) {
							key = hcTaskRelOrder.getBjid().concat(hcTaskRelOrder.getRwgdid()).concat(hcTaskRelOrder.getXh());
							if(hcTaskInfoMap.get(key)!=null){
								List<HcTaskRelOrder> relorders = hcTaskInfoMap.get(key).getHcTaskRelOrders();
								relorders.add(hcTaskRelOrder);
								hcTaskInfoMap.get(key).setHcTaskRelOrders(relorders);
							}
						}
					}
				}
			}
			
			for (HCMainData hcMainData : rows) {
				List<HcTaskInfo>  tasks = hcMainData.getHcTaskInfos();
				if(tasks!=null && tasks.size()>0){
					for (HcTaskInfo task : tasks) {
						Collections.sort(task.getHcTaskRelOrders(), new Comparator<HcTaskRelOrder>() {
							@Override
							public int compare(HcTaskRelOrder o1, HcTaskRelOrder o2) {
								return o1.getZgdid().compareTo(o2.getZgdid());
							}
						});
					}
				}
			}
		}
		
		result.put("rows", rows);
		result.put("total", rows==null?0:rows.size());
		return result;
	
	}

	@Override
	public Map<String, Object> toolStatistics(HCMainData record) throws BusinessException{ 
		Map<String, Object> result = new HashMap<String, Object>();
		List<HCMainData> rows =  hcMainDataMapper.selectToolDemandFir(record);
		if (rows!= null && !rows.isEmpty()) {
			//组装部件号-航材map
			Map<String, HCMainData> hcMainDataMap = new HashMap<String, HCMainData>();
			for (HCMainData hcMainData : rows) {
				hcMainDataMap.put(hcMainData.getBjid(), hcMainData);
			}
			
			List<HcTaskInfo> listSec =  hcMainDataMapper.selectToolDemandSec(record);
			Map<String, HcTaskInfo> hcTaskInfoMap = new HashMap<String, HcTaskInfo>();
			List<HcTaskInfo> hcTaskInfos = null;
			if (listSec!=null && !listSec.isEmpty()) {
				
				//工具缺件修改：2017-03-22 <!-- 定检任务单，工单链接不正确-->
				String key = "";
				for (HcTaskInfo hcTaskInfo : listSec) {
					if (StringUtils.isNotBlank( hcTaskInfo.getBjid()) ) {
						//组装rwgdid-任务信息map
						key = hcTaskInfo.getBjid().concat(hcTaskInfo.getGdid()).concat(hcTaskInfo.getXh());
						hcTaskInfoMap.put( key, hcTaskInfo);
						
						//将任务信息组装到航材主单上
						if (hcMainDataMap.containsKey(hcTaskInfo.getBjid())) {
							hcTaskInfos = hcMainDataMap.get(hcTaskInfo.getBjid()).getHcTaskInfos();
							if(hcTaskInfos!=null){
								hcTaskInfos.add(hcTaskInfo);
							}
							else{
								hcTaskInfos = new ArrayList<HcTaskInfo>();
								hcTaskInfos.add(hcTaskInfo);
								hcMainDataMap.get(hcTaskInfo.getBjid()).setHcTaskInfos(hcTaskInfos);
							}
						}
					}
				}
				
				List<HcTaskRelOrder> listThi =  hcMainDataMapper.selectToolDemandThi(record );
				if (listThi!=null && !listThi.isEmpty()) {
					
					for (HcTaskRelOrder hcTaskRelOrder : listThi) {
						//将主单-相关工单信息 组装到任务信息
						if (hcTaskRelOrder.esMasterBill()) {
							hcTaskRelOrder.setZgdid(hcTaskRelOrder.getGdid());
							key = hcTaskRelOrder.getBjid().concat(hcTaskRelOrder.getRwgdid()).concat(hcTaskRelOrder.getXh());
							List<HcTaskRelOrder> relorders = hcTaskInfoMap.get(key).getHcTaskRelOrders();
							relorders.add(hcTaskRelOrder);
							hcTaskInfoMap.get(key).setHcTaskRelOrders(relorders);
						}
					}
					
					for (HcTaskRelOrder hcTaskRelOrder : listThi) {
						//将主单-相关工单信息 组装到任务信息
						if (!hcTaskRelOrder.esMasterBill()) {
							key = hcTaskRelOrder.getBjid().concat(hcTaskRelOrder.getRwgdid()).concat(hcTaskRelOrder.getXh());
							if(hcTaskInfoMap.get(key)!=null){
								List<HcTaskRelOrder> relorders = hcTaskInfoMap.get(key).getHcTaskRelOrders();
								relorders.add(hcTaskRelOrder);
								hcTaskInfoMap.get(key).setHcTaskRelOrders(relorders);
							}
						}
					}
					
				}
				//工具缺件修改：2017-03-22 <!-- 定检任务单，工单链接不正确-->
			}
			
			for (HCMainData hcMainData : rows) {
				List<HcTaskInfo>  tasks = hcMainData.getHcTaskInfos();
				if(tasks!=null && tasks.size()>0){
					for (HcTaskInfo task : tasks) {
						Collections.sort(task.getHcTaskRelOrders(), new Comparator<HcTaskRelOrder>() {
							@Override
							public int compare(HcTaskRelOrder o1, HcTaskRelOrder o2) {
								return o1.getZgdid().compareTo(o2.getZgdid());
							}
						});
					}
				}
			}
		}
		
		result.put("rows", rows);
		result.put("total", rows==null?0:rows.size());
		return result;
	
	}

	@Override
	public List<PlanTask> queryAllList(PlanTask planTask) throws BusinessException{
		try {
			return planTaskMapper.queryList(planTask);
		} catch (Exception e) {
			 throw new BusinessException("查询计划任务失败");
		}
	}

	@Override
	public void editJkRemark(PlanTask planTask)throws BusinessException {
		try {
			Date date = commonService.getSysdate();
			String czls = UUID.randomUUID().toString();      //日志的操作流水
			User user = ThreadVarUtil.getUser();
			
			//更新监控备注
			planTask.setWhrid(user.getId());
			planTask.setWhdwid(user.getBmdm());
			planTask.setWhsj(date);
			planTaskMapper.updateByPrimaryKeySelective(planTask);
			PlanTask planTaskDb = planTaskMapper.selectByPrimaryKey(planTask.getId());
			//更新工单监控备注
			WorkOrder record = new WorkOrder();
			record.setId(planTaskDb.getXggdid());
			record.setJkbz(planTaskDb.getBz());
			
			String gddlx = PlanTaskType.isCheckBill(planTaskDb.getRwlx())?"4":planTaskDb.getRwlx();
			record.setGddlx(Integer.valueOf(gddlx));
			record.setZdrid(user.getId());
			record.setCzls(czls);
			record.setLogOperationEnum(LogOperationEnum.EDIT);
			
			//调用工单接口
			workOrderService.updateByPrimaryKeySelective(record );
			commonRecService.write(planTask.getId(), TableEnum.B_S_009, user.getId(),czls,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,planTask.getId());
		} catch (Exception e) {
			 throw new BusinessException("修改监控备注失败",e);
		}
	}

	@Override
	public Map<String, Object> queryByFlightRecord(PlanTask planTask){
		List<PlanTask> rows = new ArrayList<PlanTask>();
		PageHelper.startPage(planTask.getPagination());
		if(planTask.getIsLoadedParts().equals("0")){	// 全部
			rows = planTaskMapper.queryAllList(planTask);
		} else if(planTask.getIsLoadedParts().equals("1")){	// 装机件任务
			rows = planTaskMapper.queryLoadedParts(planTask);
		} else if(planTask.getIsLoadedParts().equals("2")){	// 非装机件任务
			rows = planTaskMapper.queryNotLoadedParts(planTask);
		}
		return PageUtil.pack4PageHelper(rows, planTask.getPagination());
	}

	@Override
	public boolean alreadyCompleted(String id, String idSource) throws BusinessException {
		 Boolean alreadyCompleted = Boolean.FALSE;
		 PlanTask task = null;
		//1.定检任务根据ID查 
		if (CompleteSourceEnum.isPlanTask4Str(idSource)) {
			task = planTaskMapper.selectByPrimaryKey(id);
		}
		else {
			//idSource id来源 (1定检工单、2非例行工单任务、3EO工单任务 4、定检执行任务、5计划任务)
			String xggdid = null;
			
			//2.定检工单，先取定检任务id
			if(CompleteSourceEnum.isCheckBill4Str(idSource)){
				Map<String,Object> workOrder = planTaskMapper.selectDjWorkOrder(id);
				xggdid =workOrder.get("DJRWDID")!=null?workOrder.get("DJRWDID").toString():"";
			}//3.  2非例行工单任务、3EO工单任务 4、定检执行任务
			else if(CompleteSourceEnum.isNonRoutine4Str(idSource) || CompleteSourceEnum.isEoBill4Str(idSource) || CompleteSourceEnum.isCheckbillTask4Str(idSource)){
					xggdid = id;
			}
			task = planTaskMapper.selectByXggdid(xggdid); 
		}
		if (task != null) {
			alreadyCompleted = PlanTaskDispalyState.isComplete(task.getXszt());
		}
		return alreadyCompleted;
	}

	@Override
	public Map<String, Object> queryPanelSummary(PlanTask planTask)throws BusinessException {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			//查询装机件或非装机件数据（2选1）
			Map<String,List<PlanTask>> plantaskMap = this.queryListPanel(planTask);
			
			//查询装机件，或非装机件记录总数（两种情况）
			List<String>zts = new ArrayList<String>();
			zts.add(PlanTaskState.TO_BE_EXECUTED.getId().toString());
			zts.add(PlanTaskState.EXECUTING.getId().toString());
			planTask.setZts(zts);			
			Map<String,String> totalGroup = this.queryTotalByGroup(planTask);
			
			result.put("plantaskMap", plantaskMap);
			result.put("zjjs", totalGroup.get("zjjs"));
			result.put("fzjjs", totalGroup.get("fzjjs"));
			String isLoadedParts = planTask.getIsLoadedParts();
			result.put("isLoadedParts", StringUtils.isNotBlank(isLoadedParts)?isLoadedParts:BinaryEnum.YES.getId().toString() );
			result.put("keyword", planTask.getKeyword());
			result.put("fjzch", planTask.getFjzch());
			return result;
		} catch (Exception e) {
			throw new BusinessException("查询计划看板失败",e);
		}
		
	}
}
