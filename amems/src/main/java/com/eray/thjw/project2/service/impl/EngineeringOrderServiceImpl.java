package com.eray.thjw.project2.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.OutFieldStockMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.InstallationListEffectiveMapper;
import com.eray.thjw.produce.po.InstallationListEffective;
import com.eray.thjw.produce.po.MonitoringObject;
import com.eray.thjw.produce.po.MonitoringPlan;
import com.eray.thjw.produce.service.MonitorDataService;
import com.eray.thjw.project2.dao.DistributionDepartmentMapper;
import com.eray.thjw.project2.dao.EOApplicabilityMapper;
import com.eray.thjw.project2.dao.EOManhourParkingTimeMapper;
import com.eray.thjw.project2.dao.EOMonitorIemSetMapper;
import com.eray.thjw.project2.dao.EOPulicationAffectedMapper;
import com.eray.thjw.project2.dao.EngineeringOrderMapper;
import com.eray.thjw.project2.dao.EngineeringOrderSubMapper;
import com.eray.thjw.project2.dao.InstructionsourceMapper;
import com.eray.thjw.project2.dao.PersonnelReceiptMapper;
import com.eray.thjw.project2.dao.ReferenceMapper;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.po.DistributionDepartment;
import com.eray.thjw.project2.po.EOApplicability;
import com.eray.thjw.project2.po.EOMonitorIemSet;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.project2.po.EngineeringOrderSub;
import com.eray.thjw.project2.po.Instructionsource;
import com.eray.thjw.project2.po.MaintenanceScheme;
import com.eray.thjw.project2.po.MaterialTool;
import com.eray.thjw.project2.po.PersonnelReceipt;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.po.WorkCard;
import com.eray.thjw.project2.service.CoverPlateService;
import com.eray.thjw.project2.service.EOApplicabilityService;
import com.eray.thjw.project2.service.EOManhourParkingTimeService;
import com.eray.thjw.project2.service.EOMonitorIemSetService;
import com.eray.thjw.project2.service.EOPulicationAffectedService;
import com.eray.thjw.project2.service.EngineeringOrderService;
import com.eray.thjw.project2.service.InstructionsourceService;
import com.eray.thjw.project2.service.MaterialToolService;
import com.eray.thjw.project2.service.ReferenceService;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.project2.service.WorkContentService;
import com.eray.thjw.project2.service.WorkHourService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.common.RevMarkEnum;
import enu.common.WhetherEnum;
import enu.produce.MaintenanceTypeEnum;
import enu.produce.NodeEnum;
import enu.project2.CompnentTypeEnum;
import enu.project2.DistributionDepartmentTypeEnum;
import enu.project2.EngineeringOrderStatusEnum;
import enu.project2.ExecutionEnum;
import enu.project2.MonitorProjectEnum;
import enu.project2.MonitorProjectUnitEnum;
import enu.project2.ProjectBusinessEnum;
import enu.project2.SendOrderEnum;
import enu.project2.TodoEnum;
import enu.project2.TodoStatusEnum;

/** 
 * @Description EO接口实现类
 * @CreateTime 2017-8-22 上午9:29:03
 * @CreateBy 雷伟	
 */
@Service
public class EngineeringOrderServiceImpl implements EngineeringOrderService {

	@Resource
	private EngineeringOrderMapper engineeringOrderMapper;
	@Resource
	private TodoService todoService;
	@Resource
	private EngineeringOrderSubMapper engineeringOrderSubMapper;
	@Resource
	private EOApplicabilityMapper eoApplicabilityMapper;
	@Resource
	private DistributionDepartmentMapper distributionDepartmentMapper;
	@Resource
	private PersonnelReceiptMapper personnelReceiptMapper;
	@Resource
	private EOMonitorIemSetMapper eoMonitorIemSetMapper;
	@Resource
	private EOPulicationAffectedMapper eoPulicationAffectedMapper;
	@Resource
	private EOManhourParkingTimeMapper eoManhourParkingTimeMapper;
	@Resource
	private StockMapper stockMapper;
	@Resource
	private OutFieldStockMapper outFieldStockMapper;
	@Resource
	private InstallationListEffectiveMapper installationListEffectiveMapper;
	@Resource
	private InstructionsourceMapper instructionsourceMapper;
	@Resource
	private ReferenceMapper referenceMapper;
	@Resource
	private SaibongUtilService saibongUtilService;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private AttachmentService attachmentService;
	@Resource
	private WorkContentService workContentService;
	@Resource
	private WorkHourService workHourService;
	@Resource
	private ReferenceService referenceService;
	@Resource
	private MaterialToolService materialToolService;
	@Resource
	private InstructionsourceService instructionsourceService;
	@Resource
	private EOPulicationAffectedService eoPulicationAffectedService;
	@Resource
	private EOManhourParkingTimeService eoManhourParkingTimeService;
	@Resource
	private EOMonitorIemSetService eoMonitorIemSetService;
	@Resource
	private EOApplicabilityService eoApplicabilityService;
	@Resource
	private CoverPlateService coverPlateService;
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	@Resource
	private CommonService commonService;
	@Resource
	private TodorsService todorsService;
	@Resource
	private MonitorDataService monitorDataService;
	
	/**
	 * @Description EO分页列表查询
	 * @CreateTime 2017-8-22 下午9:44:27
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 */
	@Override
	public Map<String, Object> queryAllPageList(EngineeringOrder engineeringOrder) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = engineeringOrder.getId();
		engineeringOrder.setId("");
		PageHelper.startPage(engineeringOrder.getPagination());
		List<EngineeringOrder> eoList = engineeringOrderMapper.queryAllPageList(engineeringOrder);
		
		if (((Page) eoList).getTotal() > 0) {
			// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
			if (StringUtils.isNotBlank(id)) {
				// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
				if (!PageUtil.hasRecord(eoList, id)) {
					// 在查询条件中增加ID
					EngineeringOrder newRecord = new EngineeringOrder();
					newRecord.setId(id);
					List<EngineeringOrder> newRecordList = engineeringOrderMapper.queryAllPageList(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						eoList.add(0, newRecordList.get(0));
					}
				}
			}
			getOtherInfo(eoList);// 获取关联其他信息
			resultMap = PageUtil.pack4PageHelper(eoList, engineeringOrder.getPagination());
			return resultMap;

		} else {
			List<EngineeringOrder> newRecordList = new ArrayList<EngineeringOrder>();
			if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				// 在查询条件中增加ID
				EngineeringOrder newRecord = new EngineeringOrder();
				newRecord.setId(id);
				newRecordList = engineeringOrderMapper.queryAllPageList(newRecord);
				if (newRecordList != null && newRecordList.size() == 1) {
					resultMap = PageUtil.pack(1, newRecordList, engineeringOrder.getPagination());
					return resultMap;
				}

			}
			getOtherInfo(eoList);// 获取关联其他信息
			resultMap = PageUtil.pack(0, newRecordList, engineeringOrder.getPagination());
			return resultMap;
		}
	}
	
	/**
	 * @Description 获取其他关联信息
	 * @CreateTime 2017-8-28 上午11:20:47
	 * @CreateBy 雷伟
	 * @param eoList
	 */
	private void getOtherInfo(List<EngineeringOrder> eoList) {
		// 指令id集合
		List<String> zlidList = new ArrayList<String>();
		
		for (EngineeringOrder engineeringOrder : eoList) {
			zlidList.add(engineeringOrder.getId());
		}
		
		if (zlidList == null || zlidList.size() <= 0) {
			return;
		}
		
		//根据指令ID,获取指令及关联的来源文件
		List<Instructionsource> zlList = instructionsourceMapper.getRIByZlidList(zlidList);
		//根据指令ID,获取适用性
		List<EOApplicability> syxList = eoApplicabilityMapper.getSyxByZlidList(zlidList);
		
		if (null != zlList && zlList.size() > 0) {
			for (EngineeringOrder eo : eoList) {
				List<Instructionsource> isList = new ArrayList<Instructionsource>(); //技术评估单
				List<Airworthiness> wjList = new ArrayList<Airworthiness>(); //来源文件
				for (Instructionsource instructionsource : zlList) {
					if (null != instructionsource && instructionsource.getZlid().equals(eo.getId())) {
						if(null != instructionsource.getPgdh()){
							isList.add(instructionsource);
						}
						/*if(null != instructionsource.getLywj() && null != instructionsource.getLywj().getJswjbh()){*/
							wjList.add(instructionsource.getLywj());
						/*}*/
					}
				}
				if (null != isList && isList.size() > 0) {
					eo.setIsList(isList);
				}
				/*if (null != wjList && wjList.size() > 0) {*/
					eo.setLywjList(wjList);
				/*}*/
				
				//适用性 (只要适用性表中，有一条记录有确认状态=0，那么就要标黄)
				for (EOApplicability eoApplicability : syxList) {
					if(eoApplicability.getMainid().equals(eo.getId()) && eoApplicability.getZt() > 0){
						eo.setShowYellow(true);
					}
				}
			}
		}
	}

	/**
	 * @Description 新增EO
	 * @CreateTime 2017-8-22 上午9:31:45
	 * @CreateBy 雷伟
	 * @param engineeringOrder EO
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String save(EngineeringOrder engineeringOrder) throws BusinessException {
		/**1.准备数据*/
		//EO主键ID
		String eoId = UUID.randomUUID().toString();
		engineeringOrder.setId(eoId);
		//工作内容附件ID
		String gznlfjId = UUID.randomUUID().toString();
		if (null != engineeringOrder.getWorkContentAttachment()) {
			engineeringOrder.setGznrfjid(gznlfjId);
		}
		//当前登录用户
		User user = ThreadVarUtil.getUser();
		//当前时间
		Date currentDate = commonService.getSysdate();
		//操作流水
		String czls = UUID.randomUUID().toString();
		//确定状态
		LogOperationEnum operation = LogOperationEnum.SAVE_WO;
		if(null != engineeringOrder.getParamsMap() && null != engineeringOrder.getParamsMap().get("operationType")){
			if(EngineeringOrderStatusEnum.SAVE.getId() == engineeringOrder.getParamsMap().get("operationType")){
				engineeringOrder.setZt(EngineeringOrderStatusEnum.SAVE.getId());
			}else{
				engineeringOrder.setZt(EngineeringOrderStatusEnum.SUBMIT.getId());
				operation = LogOperationEnum.SUBMIT_WO;
				
				//关闭待办
				Todo tododbyw=new Todo();
				tododbyw.setDbywid(engineeringOrder.getId());
				List<Integer> jdlist=new ArrayList<Integer>();
				jdlist.add(5);
				tododbyw.getParamsMap().put("jdlist", jdlist);
				tododbyw.setZt(TodoStatusEnum.DCL.getId());
				tododbyw.setBlrid(user.getId());
				tododbyw.setFksj(currentDate);
				todoService.updateByDbyw(tododbyw);
				
				//拼接说明
				StringBuffer strSm = new StringBuffer();
				strSm.append("请审核");
				strSm.append(engineeringOrder.getJx());
				strSm.append("机型的");
				strSm.append(engineeringOrder.getEobh());
				strSm.append(" R");
				strSm.append(engineeringOrder.getBb());
				strSm.append("工程指令EO。");
				todoService.insertSelectiveTechnical(engineeringOrder,strSm.toString(),"project2:eo:main:04",NodeEnum.NODE2.getId(),null,TodoEnum.EO.getId());
			}
		}
		//如果不输入EO编号,系统自动生成
		if (engineeringOrder.getEobh() == null || "".equals(engineeringOrder.getEobh())) {
			try {
				String eobh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.GCLZ.getName(),engineeringOrder.getZjh());
				engineeringOrder.setEobh(eobh);
			} catch (SaibongException e) {
				throw new RuntimeException(e);
			}
		}
		
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(engineeringOrder.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), user.getJgdm(), jxList);
		
		/**2.校验数据*/
		this.validate(engineeringOrder,LogOperationEnum.SAVE_WO.getId());
		
		/**3.保存数据*/
		//保存EO主表数据
		this.saveEOMain(engineeringOrder,eoId,user,currentDate,czls,operation);
		//保存EO从表表数据
		this.saveEOSub(engineeringOrder,eoId,user,currentDate,czls,operation);
		//保存EO关联表数据
		this.saveEO4Others(engineeringOrder,eoId,user,currentDate,czls,operation);
		
		/**4.处理待办**/
		this.doPgdDai(engineeringOrder,UpdateTypeEnum.SAVE.getId());
		
		return eoId;
	}

	/**
	 * @Description 处理技术评估单待办
	 * @CreateTime 2017-9-14 下午1:24:33
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @param optionId 操作类型ID
	 */
	private void doPgdDai(EngineeringOrder engineeringOrder,Integer optionId) {
		User user = ThreadVarUtil.getUser();
		
		List<String> lyidList = new ArrayList<String>();
		if(null != engineeringOrder.getIsList()){
			for(Instructionsource obj : engineeringOrder.getIsList()) {
				lyidList.add(obj.getPgdid());
			}
		}
		
		if (UpdateTypeEnum.SAVE.getId() == optionId) {//新增代办
			todorsService.insertTodorsList(engineeringOrder.getJx(), lyidList, SendOrderEnum.EO.getId(), user.getId(),
					engineeringOrder.getId(), engineeringOrder.getEobh(), engineeringOrder.getBb().toString(), 
					engineeringOrder.getEozt(), engineeringOrder.getZt());
			
		}else if (UpdateTypeEnum.UPDATE.getId() == optionId) {
			todorsService.deleteTodorsByYwid(engineeringOrder.getId());
			todorsService.insertTodorsList(engineeringOrder.getJx(), lyidList, SendOrderEnum.EO.getId(), user.getId(),
					engineeringOrder.getId(), engineeringOrder.getEobh(), engineeringOrder.getBb().toString(), 
					engineeringOrder.getEozt(), engineeringOrder.getZt());
			
		}else if (UpdateTypeEnum.DELETE.getId() == optionId) {
			todorsService.deleteTodorsByYwid(engineeringOrder.getId());//删除
			
		}else if (UpdateTypeEnum.AUDIT.getId() == optionId) {
			todorsService.updateYwdjztByYwid(engineeringOrder.getZt(), engineeringOrder.getId());//审核
			
		}else if (UpdateTypeEnum.APPROVE.getId() == optionId) {
			todorsService.updateYwdjztByYwid(engineeringOrder.getZt(), engineeringOrder.getId());//批准
			
		}else if (UpdateTypeEnum.CLOSE.getId() == optionId) {
			todorsService.updateYwdjztByYwid(engineeringOrder.getZt(), engineeringOrder.getId());//关闭
		}
	}

	/**
	 * @Description 数据校验
	 * @CreateTime 2017-8-22 下午3:54:50
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @param operationType 操作类型
	 * @throws BusinessException 
	 */
	private void validate(EngineeringOrder engineeringOrder,Integer operationType) throws BusinessException {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		//新增:校验编号是否已存在
		if (LogOperationEnum.SAVE_WO.getId() == operationType) {
			paramMap.clear();
			paramMap.put("eobh", engineeringOrder.getEobh());
			paramMap.put("dprtcode", engineeringOrder.getParamsMap().get("dprtcode"));
			List<EngineeringOrder> eos = engineeringOrderMapper.selectByParam(paramMap);
			if (null != eos && eos.size() > 0) {
				throw new BusinessException("EO编号已存在,请去改版");
			}
		}
		
		//新增、改版
		if (LogOperationEnum.SAVE_WO.getId() == operationType || LogOperationEnum.REVISION.getId() == operationType) {
			//校验编号+版本号是否唯一
			paramMap.clear();
			paramMap.put("eobh", engineeringOrder.getEobh());
			paramMap.put("bb", engineeringOrder.getBb());
			paramMap.put("dprtcode", engineeringOrder.getParamsMap().get("dprtcode"));
			List<EngineeringOrder> eos = engineeringOrderMapper.selectByParam(paramMap);
			if (null != eos && eos.size() > 0) {
				throw new BusinessException("EO编号+版本号重复");
			}
		}
		
		//改版 
		if (LogOperationEnum.REVISION.getId() == operationType) {
			paramMap.clear();
			paramMap.put("eobh", engineeringOrder.getEobh());
			paramMap.put("dprtcode", engineeringOrder.getParamsMap().get("dprtcode"));
			Integer maxbb = engineeringOrderMapper.selectMaxbbByParam(paramMap);
			if (null != maxbb && engineeringOrder.getBb().longValue() < maxbb) {
				throw new BusinessException("版本不可以小于相同编号的最大版本");
			}
		}
	}

	/**
	 * @Description 保存EO关联表数据
	 * @CreateTime 2017-8-22 上午9:50:07
	 * @CreateBy 雷伟
	 * @param engineeringOrder EO
	 * @param eoId EOID
	 * @param user 当前用户
	 * @param currentDate 当前时间
	 * @param czls 操作流水号
	 * @param operation 操作类型
	 */
	private void saveEO4Others(EngineeringOrder engineeringOrder, String eoId,User user, Date currentDate,String czls,LogOperationEnum operation) {
		//技术评估单数据(下达指令)
		instructionsourceService.saveInstructionSourceList(this.getInstructionSourceList(engineeringOrder), ProjectBusinessEnum.EO.getId(), eoId, user.getJgdm());
		//参考文件
		referenceService.saveReferenceList(engineeringOrder.getCkwjList(), ProjectBusinessEnum.EO.getId(), eoId, czls, eoId, user.getJgdm(), operation);
		//工种工时
		workHourService.saveWorkHourList(engineeringOrder.getGzgsList(), ProjectBusinessEnum.EO.getId(), eoId, czls, eoId, user.getJgdm(), operation);
		//分发部门
		this.saveDistributeDepartment(engineeringOrder.getDepartmentIds(),ProjectBusinessEnum.EO.getId(),DistributionDepartmentTypeEnum.department.getId(),eoId,user,czls,currentDate);
		//附件列表
		if(operation.getId() == LogOperationEnum.REVISION.getId()){//改版
			attachmentService.saveAttachment4Modify(engineeringOrder.getAttachments(),engineeringOrder.getfBbid(), eoId, czls, eoId, user.getJgdm(), operation);
		}else{
			attachmentService.eidtAttachment(engineeringOrder.getAttachments(), eoId, czls, eoId, user.getJgdm(), operation);
		}
		//器材清单 ~ 工具设备
		List<MaterialTool> toolsList = new ArrayList<MaterialTool>();
		toolsList.addAll(engineeringOrder.getQcqdList());
		toolsList.addAll(engineeringOrder.getGjsbList());
		materialToolService.saveMaterialToolList(toolsList, ProjectBusinessEnum.EO.getId(), eoId, czls, eoId, user.getJgdm(), operation);
		//工作内容附件
		if (null != engineeringOrder.getWorkContentAttachment()) {
			attachmentService.addAttachment(engineeringOrder.getWorkContentAttachment(),engineeringOrder.getGznrfjid(), czls, eoId, user.getJgdm(), operation);
		}
		//工作内容数据
		workContentService.saveWorkContentList(engineeringOrder.getGznrList(),ProjectBusinessEnum.EO.getId(), eoId, czls, eoId, user.getJgdm(), operation);
		//区域站位
		coverPlateService.saveCoverPlateList(engineeringOrder.getQyzwList(), ProjectBusinessEnum.EO.getId(), eoId, czls, eoId, user.getJgdm(), operation);
		//适用列表
		eoApplicabilityService.savePulicationAffectedList(engineeringOrder.getSyxszList(), user, eoId, currentDate, czls, operation);
		//受影响出版物
		eoPulicationAffectedService.savePulicationAffectedList(engineeringOrder.getSyxcbwList(),user,eoId,currentDate,czls,operation);
		//工时停场时间 
		eoManhourParkingTimeService.saveManhourParkingTimeList(engineeringOrder.getGstcshList(),user,eoId,currentDate,czls,operation);
		//监控项信息
		eoMonitorIemSetService.saveMonitorIemSetList(engineeringOrder.getJkxszList(),user,eoId,currentDate,czls,operation);
	}

	private List<Instructionsource> getInstructionSourceList(EngineeringOrder engineeringOrder) {
		if(null != engineeringOrder && null !=engineeringOrder.getIsList() && engineeringOrder.getIsList().size() > 0){
			for (int i = 0; i < engineeringOrder.getIsList().size(); i++) {
				engineeringOrder.getIsList().get(i).setZlbh(engineeringOrder.getEobh());
				engineeringOrder.getIsList().get(i).setZlbb(engineeringOrder.getBb()+"");
				engineeringOrder.getIsList().get(i).setYwzt(engineeringOrder.getEozt());
				engineeringOrder.getIsList().get(i).setYwdjzt(engineeringOrder.getZt());
			}
		}
		return engineeringOrder.getIsList();
	}

	/**
	 * @Description 新增分发部门
	 * @CreateTime 2017-8-23 上午9:15:00
	 * @CreateBy 雷伟
	 * @param departments 分发的部门ID
	 * @param ffType 分发的类型
	 * @param eoId 业务ID
	 * @param user 当前用户
	 * @param czls 流水号
	 * @param currentDate 当前时间
	 */
	private void saveDistributeDepartment(String departments, Integer ywType,Integer ffType,String eoId, User user, String czls,Date currentDate) {
		if (null == departments || StringUtils.isEmpty(departments)) {
			return;
		}
		
		DistributionDepartment ddepartment = null;
		List<String> ddepartmentIdList = new ArrayList<String>();
		List<DistributionDepartment> list = new ArrayList<DistributionDepartment>();
		if (departments.indexOf(",") > 0) {
			String[] deptIds = departments.split(",");
			for (String deptId : deptIds) {
				ddepartment = new DistributionDepartment();
				ddepartment.setId(UUID.randomUUID().toString());
				ddepartment.setDprtcode(user.getJgdm());
				ddepartment.setYwlx(ywType);// 业务类型
				ddepartment.setYwid(eoId);// 业务id
				ddepartment.setLx(ffType);//分发类型
				ddepartment.setDxid(deptId);// 对象id
				ddepartment.setIsJs(WhetherEnum.NO.getId());// 接收标识:否
				ddepartment.setZt(EffectiveEnum.YES.getId());// 状态
				ddepartment.setWhrid(user.getId());
				ddepartment.setWhdwid(user.getBmdm());
				ddepartment.setWhsj(currentDate);
				ddepartmentIdList.add(ddepartment.getId());
				list.add(ddepartment);
			}
		} else {
			ddepartment = new DistributionDepartment();
			ddepartment.setId(UUID.randomUUID().toString());
			ddepartment.setDprtcode(user.getJgdm());
			ddepartment.setYwlx(ywType);// 业务类型
			ddepartment.setYwid(eoId);// 业务id
			ddepartment.setLx(ffType);//分发类型
			ddepartment.setDxid(departments);// 对象id
			ddepartment.setIsJs(WhetherEnum.NO.getId());// 接收标识:否
			ddepartment.setZt(EffectiveEnum.YES.getId());// 状态：1
			ddepartment.setWhrid(user.getId());
			ddepartment.setWhdwid(user.getBmdm());
			ddepartment.setWhsj(currentDate);
			ddepartmentIdList.add(ddepartment.getId());
			list.add(ddepartment);
		}
		distributionDepartmentMapper.insertDistributionDepartment(list);
		commonRecService.write("id", ddepartmentIdList, TableEnum.B_G2_998, user.getId(), czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, eoId);
	}

	/**
	 * @Description 保存EO从表数据
	 * @CreateTime 2017-8-22 上午9:50:07
	 * @CreateBy 雷伟
	 * @param engineeringOrder EO
	 * @param eoId EOID
	 * @param user 当前用户
	 * @param currentDate 当前时间
	 * @param czls 操作流水号
	 * @param operation 操作类型
	 */
	private void saveEOSub(EngineeringOrder engineeringOrder, String eoId,User user, Date currentDate,String czls,LogOperationEnum operation) {
		EngineeringOrderSub eoSub = engineeringOrder.getEoSub();
		eoSub.setId(UUID.randomUUID().toString());
		eoSub.setMainid(eoId);
		engineeringOrderSubMapper.insert(eoSub);
		//保存历史记录信息
		commonRecService.write(eoSub.getId(), TableEnum.B_G2_01000, user.getId(), czls, operation, UpdateTypeEnum.SAVE, eoSub.getId());
	}

	/**
	 * @Description 保存EO主表数据
	 * @CreateTime 2017-8-22 上午9:49:19
	 * @CreateBy 雷伟
	 * @param engineeringOrder EO
	 * @param eoId EOID
	 * @param user 当前用户
	 * @param currentDate 当前时间
	 * @param czls 操作流水号
	 *  @param operation 操作类型
	 */
	private void saveEOMain(EngineeringOrder engineeringOrder, String eoId,User user, Date currentDate,String czls,LogOperationEnum operation) {
		engineeringOrder.setId(eoId);
		engineeringOrder.setDprtcode(user.getJgdm());
		engineeringOrder.setZxbs(RevMarkEnum.INITIAL.getId());
		engineeringOrder.setWhbmid(user.getBmdm());
		engineeringOrder.setWhrid(user.getId());
		engineeringOrder.setWhsj(currentDate);
		engineeringOrderMapper.insert(engineeringOrder);
		//保存历史记录信息
		commonRecService.write(engineeringOrder.getId(), TableEnum.B_G2_010, user.getId(), czls, operation, UpdateTypeEnum.SAVE, engineeringOrder.getId());
	}
	/**
	 * @Description 查询维修项目获取可关联EO列表
	 * @CreateTime 2017年8月23日 下午3:41:27
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	@Override
	public Map<String, Object> queryAssociateList(MaintenanceScheme scheme) {
		PageHelper.startPage(scheme.getPagination());
		List<EngineeringOrder> list = engineeringOrderMapper.queryAssociateList(scheme);
		return PageUtil.pack4PageHelper(list, scheme.getPagination());
	}
	/**
	 * @Description 获取部件所在位置信息
	 * @CreateTime 2017年8月24日 上午10:45:21
	 * @CreateBy 岳彬彬
	 * @param bjid
	 * @param dprtcode
	 */
	@Override
	public Map<String, Object> getBj(String bjid, String dprtcode,String fjjx,String bjh) {
		List<Stock> stockList = stockMapper.getStockList(bjid, dprtcode);
		List<OutFieldStock> outStockList = outFieldStockMapper.getOutFieldStockList(bjid, dprtcode);
		List<InstallationListEffective> loadingList = installationListEffectiveMapper.getLoadinglistByfjzch(dprtcode, fjjx,bjh);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("stockList", stockList);
		map.put("outStockList", outStockList);
		map.put("loadingList", loadingList);
		return map;
	}
	/**
	 * @Description 根据EOId查询EO及相关信息
	 * @CreateTime 2017-8-24 上午11:02:03
	 * @CreateBy 雷伟
	 * @param id
	 * @return EO信息
	 */
	@Override
	public EngineeringOrder selectById(String id) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		
		EngineeringOrder eo = engineeringOrderMapper.selectById(paramMap); //主表信息
		if (null != eo && null != eo.getId()) {
			paramMap.clear();
			paramMap.put("mainid", eo.getId());
			EngineeringOrderSub eoSub = engineeringOrderSubMapper.selectById(paramMap); //从表信息
			eo.setEoSub(eoSub);
		}
		
		//查询当前版本前版本对象
		paramMap.clear();
		paramMap.put("id", eo.getfBbid());
		EngineeringOrder fBBObj = engineeringOrderMapper.selectById(paramMap);
		eo.setfBbObj(fBBObj);
		
		return eo;
	}

	/**
	 * @Description 编辑EO
	 * @CreateTime 2017-8-25 下午5:30:40
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 */
	@Override
	public String update(EngineeringOrder engineeringOrder) throws BusinessException {
		//当前登录用户
		User user = ThreadVarUtil.getUser();
		//当前时间
		Date currentDate = commonService.getSysdate();
		//操作流水
		String czls = UUID.randomUUID().toString();
		//操作类型
		LogOperationEnum operation = LogOperationEnum.EDIT;
		
		EngineeringOrder eoObj = engineeringOrderMapper.selectByPrimaryKey(engineeringOrder.getId()); //EO信息
		
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(eoObj.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(),eoObj.getDprtcode(), jxList);
		
		//确定状态
		if(null != engineeringOrder.getParamsMap() && null != engineeringOrder.getParamsMap().get("operationType")){
			if(EngineeringOrderStatusEnum.SAVE.getId() == engineeringOrder.getParamsMap().get("operationType")){
				if(null != eoObj 
						&& EngineeringOrderStatusEnum.AUDITDOWN.getId() != eoObj.getZt() 
						&& EngineeringOrderStatusEnum.APPROVALDOWN.getId() != eoObj.getZt()){//审核驳回、审批驳回,再次保存时不改状态
					engineeringOrder.setZt(EngineeringOrderStatusEnum.SAVE.getId());
				}
			}else{
				engineeringOrder.setZt(EngineeringOrderStatusEnum.SUBMIT.getId());
				/**校验状态,防止重复提交*/
				this.validateResubmit(engineeringOrder.getId(),LogOperationEnum.SUBMIT_WO.getId());
				operation = LogOperationEnum.SUBMIT_WO;
				
				//关闭待办
				Todo tododbyw=new Todo();
				tododbyw.setDbywid(engineeringOrder.getId());
				List<Integer> jdlist=new ArrayList<Integer>();
				jdlist.add(5);
				tododbyw.getParamsMap().put("jdlist", jdlist);
				tododbyw.setZt(TodoStatusEnum.DCL.getId());
				tododbyw.setBlrid(user.getId());
				tododbyw.setFksj(currentDate);
				todoService.updateByDbyw(tododbyw);
				
				//拼接说明
				StringBuffer strSm = new StringBuffer();
				strSm.append("请审核");
				strSm.append(engineeringOrder.getJx());
				strSm.append("机型的");
				strSm.append(engineeringOrder.getEobh());
				strSm.append(" R");
				strSm.append(engineeringOrder.getBb());
				strSm.append("工程指令EO。");
				todoService.insertSelectiveTechnical(engineeringOrder,strSm.toString(),"project2:eo:main:04",NodeEnum.NODE2.getId(),null,TodoEnum.EO.getId());
			}
		}
		
		/**3.编辑数据*/
		//编辑EO从表表数据
		this.updateEOSub(engineeringOrder,user,currentDate,czls,operation);
		//编辑EO关联表数据
		this.updateEO4Others(engineeringOrder,user,currentDate,czls,operation);
		//编辑EO主表数据(需先更新关联表，再更新主表，因为要会写工作内容附件ID)
		this.updateEOMain(engineeringOrder,user,currentDate,czls,operation);
		
		/**4.处理待办**/
		this.doPgdDai(engineeringOrder,UpdateTypeEnum.UPDATE.getId());
		
		return engineeringOrder.getId();
	}

	/**
	 * @Description 更新关联表数据
	 * @CreateTime 2017-8-25 下午5:50:51
	 * @CreateBy 雷伟
	 * @param engineeringOrder EO
	 * @param eoId EOID
	 * @param user 当前用户
	 * @param currentDate 当前时间
	 * @param czls 操作流水号
	 * @param operation 操作类型
	 */
	private void updateEO4Others(EngineeringOrder engineeringOrder, User user,Date currentDate, String czls,LogOperationEnum operation) {
		String eoId = engineeringOrder.getId();
		//技术评估单数据(下达指令)
		instructionsourceService.updateInstructionSourceList(this.getInstructionSourceList(engineeringOrder), ProjectBusinessEnum.EO.getId(), eoId, engineeringOrder.getDprtcode());
		//参考文件
		referenceService.updateReferenceList(engineeringOrder.getCkwjList(), ProjectBusinessEnum.EO.getId(), eoId, czls, eoId,  engineeringOrder.getDprtcode(), operation);
		//工种工时
		workHourService.updateWorkHourList(engineeringOrder.getGzgsList(), ProjectBusinessEnum.EO.getId(), eoId, czls, eoId,  engineeringOrder.getDprtcode(), operation);
		//分发部门：先删再保存
		distributionDepartmentMapper.deleteByYwid(eoId);
		this.saveDistributeDepartment(engineeringOrder.getDepartmentIds(),ProjectBusinessEnum.EO.getId(),DistributionDepartmentTypeEnum.department.getId(),eoId,user,czls,currentDate);
		//附件列表
		attachmentService.eidtAttachment(engineeringOrder.getAttachments(), eoId, czls, eoId,  engineeringOrder.getDprtcode(), operation);
		//器材清单 ~ 工具设备
		List<MaterialTool> toolsList = new ArrayList<MaterialTool>();
		toolsList.addAll(engineeringOrder.getQcqdList());
		toolsList.addAll(engineeringOrder.getGjsbList());
		materialToolService.updateMaterialToolList(toolsList, ProjectBusinessEnum.EO.getId(), eoId, czls, eoId, engineeringOrder.getDprtcode(), operation);
		
		//编辑工作内容附件
		EngineeringOrder oldEO = engineeringOrderMapper.selectByPrimaryKey(eoId); //EO信息
		if(null != oldEO.getGznrfjid() && !"".equals(oldEO.getGznrfjid())){
			if(engineeringOrder.getWorkContentAttachment() == null){
				//删除工卡附件
				attachmentService.delFiles(oldEO.getGznrfjid(), czls, eoId, LogOperationEnum.EDIT);
				engineeringOrder.setGznrfjid("");
			}else{
				//编辑工卡附件
				attachmentService.editAttachment(engineeringOrder.getWorkContentAttachment(), engineeringOrder.getGznrfjid(), czls, eoId,  engineeringOrder.getDprtcode(), operation);
			}
		}else{
			if(engineeringOrder.getWorkContentAttachment() != null){
				engineeringOrder.setGznrfjid(UUID.randomUUID().toString());
				attachmentService.addAttachment(engineeringOrder.getWorkContentAttachment(), engineeringOrder.getGznrfjid(), czls, eoId,  engineeringOrder.getDprtcode(), operation);
			}
		}
		
		//工作内容数据
		workContentService.updateWorkContentList(engineeringOrder.getGznrList(),ProjectBusinessEnum.EO.getId(), eoId, czls, eoId,  engineeringOrder.getDprtcode(), operation);
		//区域站位
		coverPlateService.updateCoverPlateList(engineeringOrder.getQyzwList(), ProjectBusinessEnum.EO.getId(), eoId, czls, eoId,  engineeringOrder.getDprtcode(), operation);
		//适用列表:先删再保存
		eoApplicabilityMapper.deleteByMainid(eoId);
		eoApplicabilityService.savePulicationAffectedList(engineeringOrder.getSyxszList(), user, eoId, currentDate, czls, operation);
		//受影响出版物：先删再保存
		eoPulicationAffectedMapper.deleteByMainid(eoId);
		eoPulicationAffectedService.savePulicationAffectedList(engineeringOrder.getSyxcbwList(),user,eoId,currentDate,czls,operation);
		//工时停场时间 ：先删再保存
		eoManhourParkingTimeMapper.deleteByMainid(eoId);
		eoManhourParkingTimeService.saveManhourParkingTimeList(engineeringOrder.getGstcshList(),user,eoId,currentDate,czls,operation);
		//监控项信息：先删再保存
		eoMonitorIemSetMapper.deleteByMainid(eoId);
		eoMonitorIemSetService.saveMonitorIemSetList(engineeringOrder.getJkxszList(),user,eoId,currentDate,czls,operation);
	}

	/**
	 * @Description 编辑EO从表表数据
	 * @CreateTime 2017-8-25 下午5:45:28
	 * @CreateBy 雷伟
	 * @param engineeringOrder EO
	 * @param user 当前用户
	 * @param currentDate 当前时间
	 * @param czls 操作流水号
	 * @param Operation 操作状态
	 */
	private void updateEOSub(EngineeringOrder engineeringOrder, User user,Date currentDate, String czls,LogOperationEnum Operation) {
		EngineeringOrderSub eoSub = engineeringOrder.getEoSub();
		engineeringOrderSubMapper.updateByPrimaryKeySelective(eoSub);
		//保存历史记录信息
		commonRecService.write(eoSub.getId(), TableEnum.B_G2_01000, user.getId(), czls, Operation, UpdateTypeEnum.UPDATE, eoSub.getId());
	}

	/**
	 * @Description 编辑EO主表数据
	 * @CreateTime 2017-8-25 下午5:37:15
	 * @CreateBy 雷伟
	 * @param engineeringOrder EO
	 * @param user 当前用户
	 * @param currentDate 当前时间
	 * @param czls 操作流水号
	 * @param Operation 操作状态
	 */
	private void updateEOMain(EngineeringOrder engineeringOrder, User user,Date currentDate, String czls,LogOperationEnum Operation) {
		engineeringOrder.setZxbs(RevMarkEnum.INITIAL.getId());
		engineeringOrder.setWhbmid(user.getBmdm());
		engineeringOrder.setWhrid(user.getId());
		engineeringOrder.setWhsj(currentDate);
		engineeringOrderMapper.updateByPrimaryKeySelective(engineeringOrder);
		//保存历史记录信息
		commonRecService.write(engineeringOrder.getId(), TableEnum.B_G2_010, user.getId(), czls, Operation, UpdateTypeEnum.UPDATE, engineeringOrder.getId());
	}

	/**
	 * @Description EO审核
	 * @CreateTime 2017-8-25 下午9:31:38
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String doAudit(EngineeringOrder engineeringOrder) throws BusinessException {
		User user = ThreadVarUtil.getUser(); //当前用户
		Date currentDate = commonService.getSysdate();////当前时间
		
		EngineeringOrder eo = engineeringOrderMapper.selectByPrimaryKey(engineeringOrder.getId()); //EO信息
		
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(eo.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(),eo.getDprtcode(), jxList);
				
		/**校验状态,防止重复提交*/
		this.validateResubmit(engineeringOrder.getId(),LogOperationEnum.YISHENHE_WO.getId());	
		
		eo.setShrid(user.getId()); //审核人ID
		eo.setShbmid(user.getBmdm()); //审核人部门ID
		eo.setShsj(currentDate);//审核时间
		eo.setShyj(engineeringOrder.getShyj());//审核意见
		eo.setShjl(engineeringOrder.getShjl());//审核结论
		if (EngineeringOrderStatusEnum.AUDITED .getId() == engineeringOrder.getShjl().intValue()) {
			eo.setZt(EngineeringOrderStatusEnum.AUDITED.getId()); //状态
			engineeringOrderMapper.updateByPrimaryKeySelective(eo); //编辑EO
			
			//保存历史记录信息
			commonRecService.write(engineeringOrder.getId(), TableEnum.B_G2_010, user.getId(), UUID.randomUUID().toString(), LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE, engineeringOrder.getId());
			EngineeringOrder engineeringOrder1=engineeringOrderMapper.selectByPrimaryKey(engineeringOrder.getId());
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(engineeringOrder1.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(2);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append("请审批");
			strSm.append(engineeringOrder1.getJx());
			strSm.append("机型的");
			strSm.append(engineeringOrder1.getEobh());
			strSm.append(" R");
			strSm.append(engineeringOrder1.getBb());
			strSm.append("工程指令EO。");
			todoService.insertSelectiveTechnical(engineeringOrder1,strSm.toString(),"project2:eo:main:05",NodeEnum.NODE3.getId(),null,TodoEnum.EO.getId());
		}else {
			eo.setZt(EngineeringOrderStatusEnum.AUDITDOWN.getId()); //状态
			engineeringOrderMapper.updateByPrimaryKeySelective(eo); //编辑EO
			
			//保存历史记录信息
			commonRecService.write(engineeringOrder.getId(), TableEnum.B_G2_010, user.getId(), UUID.randomUUID().toString(), LogOperationEnum.SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE, engineeringOrder.getId());
			EngineeringOrder engineeringOrder1=engineeringOrderMapper.selectByPrimaryKey(engineeringOrder.getId());
			
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(engineeringOrder1.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(2);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append(engineeringOrder1.getJx());
			strSm.append("机型的");
			strSm.append(engineeringOrder1.getEobh());
			strSm.append(" R");
			strSm.append(engineeringOrder1.getBb());
			strSm.append("工程指令EO已经驳回，请重新提交。");
			todoService.insertSelectiveTechnical(engineeringOrder1,strSm.toString(),"project2:eo:main:02",NodeEnum.NODE5.getId(),engineeringOrder1.getWhrid(),TodoEnum.EO.getId());
		}
		
		/**4.处理待办**/
		this.doPgdDai(eo,UpdateTypeEnum.AUDIT.getId());
		
		/**更新下达指令关联的状态**/
		this.doInstructionsource(eo);
		
		return eo.getId();
	}

	/**更新下达指令关联的状态**/
	private void doInstructionsource(EngineeringOrder eo) {
		List<String> zlidList = new ArrayList<String>();
		zlidList.add(eo.getId());
		List<Instructionsource> isList = instructionsourceMapper.getListByZlidList(zlidList);
		eo.setIsList(isList);
		instructionsourceService.updateInstructionSourceList(this.getInstructionSourceList(eo), ProjectBusinessEnum.EO.getId(), eo.getId(), eo.getDprtcode());
	}

	/**
	 * @Description EO批准
	 * @CreateTime 2017-8-25 下午9:31:38
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String doApprove(EngineeringOrder engineeringOrder) throws BusinessException {
		User user = ThreadVarUtil.getUser(); //当前用户
		Date currentDate = commonService.getSysdate();////当前时间
		
		EngineeringOrder eo = engineeringOrderMapper.selectByPrimaryKey(engineeringOrder.getId()); //EO信息
		
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(eo.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(),eo.getDprtcode(), jxList);
				
		/**校验状态,防止重复提交*/
		this.validateResubmit(engineeringOrder.getId(),LogOperationEnum.YIPIZHUN_WO.getId());
		
		eo.setPfrid(user.getId()); //批核人ID
		eo.setPfbmid(user.getBmdm()); //批核人部门ID
		eo.setPfsj(currentDate);//批核时间
		eo.setPfyj(engineeringOrder.getPfyj());//批核意见
		eo.setPfjl(engineeringOrder.getPfjl());//批核结论
		if (EngineeringOrderStatusEnum.APPROVED.getId() == engineeringOrder.getPfjl().intValue()) {
			eo.setZt(EngineeringOrderStatusEnum.APPROVED.getId()); //状态
			engineeringOrderMapper.updateByPrimaryKeySelective(eo); //编辑EO
			//EO生效
			this.updateEOEffective(eo);
			
			//保存历史记录信息
			commonRecService.write(engineeringOrder.getId(), TableEnum.B_G2_010, user.getId(), UUID.randomUUID().toString(), LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE, engineeringOrder.getId());
		
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(engineeringOrder.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(3);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			
		}else {
			eo.setZt(EngineeringOrderStatusEnum.APPROVALDOWN.getId()); //状态
			engineeringOrderMapper.updateByPrimaryKeySelective(eo); //编辑EO
			//保存历史记录信息
			commonRecService.write(engineeringOrder.getId(), TableEnum.B_G2_010, user.getId(), UUID.randomUUID().toString(), LogOperationEnum.SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE, engineeringOrder.getId());
			EngineeringOrder engineeringOrder1=engineeringOrderMapper.selectByPrimaryKey(engineeringOrder.getId());
			
			
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(engineeringOrder1.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(3);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append(engineeringOrder1.getJx());
			strSm.append("机型的");
			strSm.append(engineeringOrder1.getEobh());
			strSm.append(" R");
			strSm.append(engineeringOrder1.getBb());
			strSm.append("工程指令EO已经驳回，请重新提交。");
			todoService.insertSelectiveTechnical(engineeringOrder1,strSm.toString(),"project2:eo:main:02",NodeEnum.NODE5.getId(),engineeringOrder1.getWhrid(),TodoEnum.EO.getId());
		}
		
		/**4.处理待办**/
		this.doPgdDai(eo,UpdateTypeEnum.APPROVE.getId());
		
		/**更新下达指令关联的状态**/
		this.doInstructionsource(eo);
		
		return eo.getId();
	}

	/**
	 * @Description 更新EO生效
	 * @CreateTime 2017-8-26 上午10:12:17
	 * @CreateBy 雷伟
	 * @param eo
	 */
	private void updateEOEffective(EngineeringOrder engineeringOrder) {
		//设置EO生效,更新EO状态=7生效，最新标识=2新版本
		EngineeringOrder eo = new EngineeringOrder();
		eo.setId(engineeringOrder.getId());
		eo.setZxbs(RevMarkEnum.LATEST_VERSION.getId());
		eo.setZt(EngineeringOrderStatusEnum.TAKEEFFECT.getId());
		engineeringOrderMapper.updateByPrimaryKeySelective(eo);
		
		//更新前版本的EO最新标识=3老版本
		if (StringUtils.isNotBlank(engineeringOrder.getfBbid())) {
			EngineeringOrder previousVersion = new EngineeringOrder();
			previousVersion.setId(engineeringOrder.getfBbid());
			previousVersion.setZxbs(RevMarkEnum.OLD_VERSION.getId());
			engineeringOrderMapper.updateByPrimaryKeySelective(previousVersion);
		}
		
		//更新EO监控对象
		if(null != engineeringOrder.getIsXfsc() && BigDecimal.ONE.compareTo(engineeringOrder.getIsXfsc()) == 0){
			this.updateMonitorData(engineeringOrder);
		}
		
	}
	
	/**
	 * 
	 * @Description 更新监控数据
	 * @CreateTime 2017年10月9日 上午11:08:30
	 * @CreateBy 徐勇
	 * @param engineeringOrder EO对象
	 */
	private void updateMonitorData(EngineeringOrder engineeringOrder){
		
		//根据EO的适用类别按不同方式查询监控数据
		List<MonitoringObject> moList = null;
		if(engineeringOrder.getSylb().intValue() == CompnentTypeEnum.FUSELAGE.getId().intValue()){
			//适用类别为飞机
			moList = this.monitorDataService.queryEOPlaneNeedMonitorDataList(engineeringOrder.getId());
		}else{
			//适用类别为其它
			moList = this.monitorDataService.queryEOPartNeedMonitorDataList(engineeringOrder.getId(), engineeringOrder.getDprtcode(), engineeringOrder.getJx());
		}
		if(moList.isEmpty()){
			return;
		}
		
		//查询EO的监控设置
		EOMonitorIemSet eoMonitorIemSetParam = new EOMonitorIemSet();
		eoMonitorIemSetParam.setMainid(engineeringOrder.getId());
		List<EOMonitorIemSet> monitorItemList = this.eoMonitorIemSetMapper.queryAllList(eoMonitorIemSetParam);
		//进行map转换，key值为项次
		Map<Integer, List<EOMonitorIemSet>> monitorItemMap = new HashMap<Integer, List<EOMonitorIemSet>>();
		boolean hasCalMonitorItem = false;//是否包含日历监控项
		for (EOMonitorIemSet eoMonitorIemSet : monitorItemList) {
			if(monitorItemMap.containsKey(eoMonitorIemSet.getXc().intValue())){
				monitorItemMap.get(eoMonitorIemSet.getXc().intValue()).add(eoMonitorIemSet);
			}else{
				List<EOMonitorIemSet> tempMonitorItemList = new ArrayList<EOMonitorIemSet>();
				tempMonitorItemList.add(eoMonitorIemSet);
				monitorItemMap.put(eoMonitorIemSet.getXc().intValue(), tempMonitorItemList);
			}
			if(engineeringOrder.getZxfs().intValue() == ExecutionEnum.REPEAT.getId().intValue() && MonitorProjectEnum.isCalendar(eoMonitorIemSet.getJklbh())){
				hasCalMonitorItem = true;
			}
		}
		
		//判断EO是否有日历监控项，有日历监控项则查询日历初始值
		//查询 机型下飞机/在机序列件的 日历初始值，map中key值为 飞机注册号/装机清单ID
		Map<String, String> calInitMap = null;
		if(hasCalMonitorItem){
//			calInitMap = this.monitorDataService.queryAllCalInitByFjjx(engineeringOrder.getDprtcode(), engineeringOrder.getJx());
			//日历初始值和执行对象有初始化日期没有关系了，这里不需要再查询 
			calInitMap = new HashMap<String, String>(0);
		}
				
		List<MonitoringObject> cMOList = new ArrayList<MonitoringObject>();//待新增的监控数据
		List<MonitoringPlan> cMPlanList = new ArrayList<MonitoringPlan>();//待新增的计划数据（待新增及待修改的监控数据的计划数据）
		
		//组装当前监控数据 及 计划、上次执行数据
		for (MonitoringObject monitoringObject : moList) {
			this.buildMonitorData(cMOList, cMPlanList, monitoringObject, monitorItemMap, calInitMap, engineeringOrder, ThreadVarUtil.getUser());
		}
		
		//批量新增监控数据（处理待新增监控数据）
		this.monitorDataService.saveMonitorObjectBatch(cMOList);
		//批量新增计划数据（处理待新增计划数据）
		this.monitorDataService.saveMonitorPlanBatch(cMPlanList);
		//根据待新增监控数据 同步到 当前监控数据中
		this.monitorDataService.saveCurrentMonitor4BatchSync(cMOList);
	}
	
	/**
	 * @Description 构建监控数据、监控计划
	 * @CreateTime 2017年10月10日 下午8:22:56
	 * @CreateBy 徐勇
	 * @param cMOList 待新增监控数据
	 * @param uMOList 待更新监控数据
	 * @param cMPlanList 待新增监控计划
	 * @param monitoringObject 新监控对象
	 * @param notExeMO 原监控对象
	 * @param mpMap 维修项目清单
	 * @param calInitMap 飞机/在机序列件日历初始值
	 * @param scheme 维修方案
	 * @param user 登陆用户令牌
	 */
	private void buildMonitorData(List<MonitoringObject> cMOList, List<MonitoringPlan> cMPlanList,
			MonitoringObject monitoringObject, Map<Integer, List<EOMonitorIemSet>> monitorItemMap,
			Map<String, String> calInitMap, EngineeringOrder engineeringOrder, User user) {
	
		String jksjid = UUID.randomUUID().toString();//监控数据ID
		//生成监控对象
		monitoringObject.setId(jksjid);
		monitoringObject.setWhbmid(user.getBmdm());
		monitoringObject.setWhrid(user.getId());
		monitoringObject.setLylx(MaintenanceTypeEnum.EO.getId());
		cMOList.add(monitoringObject);
		
		//生成计划数据
		//根据项次获取监控项
		List<EOMonitorIemSet> monitorItemList = monitorItemMap.get(monitoringObject.getEoxc());
		MonitoringPlan monitoringPlan = null;
		for (EOMonitorIemSet eoMonitorIemSet : monitorItemList) {
			monitoringPlan = new MonitoringPlan(eoMonitorIemSet);
			monitoringPlan.setId(UUID.randomUUID().toString());
			monitoringPlan.setJksjid(jksjid);
			monitoringPlan.setWhdwid(user.getBmdm());
			monitoringPlan.setWhrid(user.getId());
			//EO执行方式=单次或分段 计划值为EO监控项中的首次值
			if(engineeringOrder.getZxfs().intValue() != ExecutionEnum.REPEAT.getId().intValue()){
				monitoringPlan.setJhz(eoMonitorIemSet.getScz());
			}else{//EO执行方式=重复
				if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
					//日历计划值为初始+首次; 当监控对象为飞机或在机件（飞机注册号不为空）时，取日历初始值+首次， 库存及外场件（飞机注册和装机清单ID均为空） 时计划值为空
//					String strCalInit = null;
//					if(StringUtils.isNotBlank(monitoringObject.getFjzch())){
//						if(StringUtils.isNotBlank(monitoringObject.getZjqdid())){
//							strCalInit = calInitMap.get(monitoringObject.getZjqdid());
//						}else{
//							strCalInit = calInitMap.get(monitoringObject.getFjzch());
//						}
//					}
//					
//					//异常处理，当日历初始值或首次值为空 则 计划值为空
//					if(StringUtils.isNotBlank(strCalInit) && StringUtils.isNotBlank(eoMonitorIemSet.getScz())){
//						try {
//							monitoringPlan.setJhz(DateUtil.getOffsetDate(strCalInit, Integer.parseInt(monitoringPlan.getScz()), this.monitorDataService.getOffsetUnit(monitoringPlan.getDwScz())));
//						} catch (ParseException e) {}
//					}
					//日历计划值就是日历
					monitoringPlan.setJhz(monitoringPlan.getScz());
					if(StringUtils.isNotBlank(monitoringPlan.getScz())){
						monitoringPlan.setScz("0");
						monitoringPlan.setDwScz(MonitorProjectUnitEnum.DAY.getCode());
					}else{
						monitoringPlan.setScz(null);
						monitoringPlan.setDwScz(null);
					}
					
				}else{//非日历
					monitoringPlan.setJhz(eoMonitorIemSet.getScz());
				}
			}
			
			cMPlanList.add(monitoringPlan);
		}
	}
	

	/**
	 * @Description EO提交
	 * @CreateTime 2017-8-25 下午9:31:38
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String doSubmit(EngineeringOrder engineeringOrder) throws BusinessException {
		/**校验状态,防止重复提交*/
		this.validateResubmit(engineeringOrder.getId(),LogOperationEnum.SUBMIT_WO.getId());
		
		EngineeringOrder eo = engineeringOrderMapper.selectByPrimaryKey(engineeringOrder.getId()); //EO信息
		eo.setZt(EngineeringOrderStatusEnum.SUBMIT.getId()); //状态
		engineeringOrderMapper.updateByPrimaryKeySelective(eo); //编辑EO
		
		return eo.getId();
	}

	/**
	 * @Description  EO关闭
	 * @CreateTime 2017-8-25 下午9:31:38
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String doClose(EngineeringOrder engineeringOrder) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		
		EngineeringOrder eo = engineeringOrderMapper.selectByPrimaryKey(engineeringOrder.getId()); //EO信息
		
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(eo.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(),eo.getDprtcode(), jxList);
		
		/**校验状态,防止重复提交*/
		this.validateResubmit(engineeringOrder.getId(),LogOperationEnum.Close.getId());
		
		/**生效状态下关闭：校验执行对象是否都已经关闭**/
		if (EngineeringOrderStatusEnum.TAKEEFFECT.getId()==eo.getZt()) {
			int notCompleteNums = eoApplicabilityMapper.getNotCompleteNumsByMainId(engineeringOrder.getId()); //未完成数量
			if (notCompleteNums > 0) {
				throw new BusinessException("该EO执行对象没有全部关闭，不能关闭!");
			}
		}
		
		eo.setZt(engineeringOrder.getZt().intValue()); //状态
		eo.setGbyy(engineeringOrder.getGbyy()); //关闭原因
		eo.setGbrid(user.getId()); //关闭人ID
		eo.setGbrq(commonService.getSysdate()); //关闭时间
		eo.setZxbs(RevMarkEnum.LATEST_VERSION.getId()); //当前版本为新版本
		engineeringOrderMapper.updateByPrimaryKeySelective(eo); //编辑EO
		
		String czls = UUID.randomUUID().toString();
		//保存关闭附件
		attachmentService.eidtAttachment(engineeringOrder.getAttachments(), eo.getId(), czls, eo.getId(),  eo.getDprtcode(), LogOperationEnum.SUBMIT_WO);
		
		//更新前版本的EO最新标识=3老版本
		if (StringUtils.isNotBlank(eo.getfBbid())) {
			EngineeringOrder previousVersion = new EngineeringOrder();
			previousVersion.setId(eo.getfBbid());
			previousVersion.setZxbs(RevMarkEnum.OLD_VERSION.getId());
			engineeringOrderMapper.updateByPrimaryKeySelective(previousVersion);
		}
		
		//保存历史记录信息
		commonRecService.write(engineeringOrder.getId(), TableEnum.B_G2_010, user.getId(), czls, LogOperationEnum.Close, UpdateTypeEnum.UPDATE, engineeringOrder.getId());
		
		/**4.处理待办**/
		this.doPgdDai(eo,UpdateTypeEnum.CLOSE.getId());
		
		/**更新下达指令关联的状态**/
		this.doInstructionsource(eo);
		
		//删除待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(engineeringOrder.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(2);
		jdlist.add(3);
		jdlist.add(5);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(tododbyw);
		
		return eo.getId();
		
	}

	/**
	 * @Description 改版EO
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param engineeringOrder EO
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String doRevision(EngineeringOrder engineeringOrder) throws BusinessException {
		/**1.准备数据*/
		//EO主键ID
		String eoId = UUID.randomUUID().toString();
		//工作内容附件ID
		if (null != engineeringOrder.getWorkContentAttachment()) {
			String gznlfjId = UUID.randomUUID().toString();
			engineeringOrder.setGznrfjid(gznlfjId);
		}
		//当前登录用户
		User user = ThreadVarUtil.getUser();
		//当前时间
		Date currentDate = commonService.getSysdate(); //取数据库时间
		//操作流水
		String czls = UUID.randomUUID().toString();
		//确定状态
		if(null != engineeringOrder.getParamsMap() && null != engineeringOrder.getParamsMap().get("operationType")){
			if(EngineeringOrderStatusEnum.SAVE.getId() == engineeringOrder.getParamsMap().get("operationType")){
				engineeringOrder.setZt(EngineeringOrderStatusEnum.SAVE.getId());
			}else{
				engineeringOrder.setZt(EngineeringOrderStatusEnum.SUBMIT.getId());
				
			}
		}
		//前版本ID
		engineeringOrder.setfBbid(engineeringOrder.getId());
		//操作类型
		LogOperationEnum operation = LogOperationEnum.REVISION;
		
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(engineeringOrder.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(),engineeringOrder.getDprtcode(), jxList);
		
		/**2.1校验状态,防止重复提交*/
		this.validateResubmit(engineeringOrder.getId(),operation.getId());	
		/**2.2校验数据*/
		this.validate(engineeringOrder,operation.getId());
		
		/**3.更新前版本的后版本id=改版后的EOid*/
		EngineeringOrder previousVersion = new EngineeringOrder();
		previousVersion.setId(engineeringOrder.getfBbid());
		previousVersion.setbBbid(eoId);
		engineeringOrderMapper.updateByPrimaryKeySelective(previousVersion);
		
		/**4.保存数据*/
		//保存EO主表数据
		this.saveEOMain(engineeringOrder,eoId,user,currentDate,czls,operation);
		//保存EO从表表数据
		this.saveEOSub(engineeringOrder,eoId,user,currentDate,czls,operation);
		//保存EO关联表数据
		this.saveEO4Others(engineeringOrder,eoId,user,currentDate,czls,operation);
		
		/**4.处理待办**/
		this.doPgdDai(engineeringOrder,UpdateTypeEnum.SAVE.getId());
		
		//为提交
		if(engineeringOrder.getZt().equals(EngineeringOrderStatusEnum.SUBMIT.getId())){
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(engineeringOrder.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(5);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append("请审核");
			strSm.append(engineeringOrder.getJx());
			strSm.append("机型的");
			strSm.append(engineeringOrder.getEobh());
			strSm.append(" R");
			strSm.append(engineeringOrder.getBb());
			strSm.append("工程指令EO。");
			todoService.insertSelectiveTechnical(engineeringOrder,strSm.toString(),"project2:eo:main:04",NodeEnum.NODE2.getId(),null,TodoEnum.EO.getId());
		}
		
		
		return eoId;
	}

	/**
	 * @Description 校验是否重复提交
	 * @CreateTime 2017-8-29 上午10:30:56
	 * @CreateBy 雷伟
	 * @param objId 操作的对象ID
	 * @param operationId 操作类型ID
	 * @throws BusinessException 
	 */
	private void validateResubmit(String objId, Integer operationId) throws BusinessException {
		EngineeringOrder eoObj = engineeringOrderMapper.selectByPrimaryKey(objId); //EO信息

		if (LogOperationEnum.REVISION.getId() == operationId) {
			//改版前提: 状态是关闭的 + 最新标识等于2 + 后版本为空
			if(!((eoObj.getZt() == EngineeringOrderStatusEnum.CLOSETOEND.getId() || eoObj.getZt() == EngineeringOrderStatusEnum.CLOSETOFINISH.getId()) 
					&& eoObj.getZxbs() == RevMarkEnum.LATEST_VERSION.getId()
					&& ("".equals(eoObj.getbBbid()) || eoObj.getbBbid() == null))) {
				throw new BusinessException("该EO已更新，请刷新后再进行操作!");
			}
		} else if (LogOperationEnum.SUBMIT_WO.getId() == operationId) {
			//提交前提：状态是保存或审批驳回或批准驳回
			if(!(eoObj.getZt() == EngineeringOrderStatusEnum.SAVE.getId() || 
					eoObj.getZt() == EngineeringOrderStatusEnum.AUDITDOWN.getId() || 
					eoObj.getZt() == EngineeringOrderStatusEnum.APPROVALDOWN.getId())) {
				throw new BusinessException("该EO已更新，请刷新后再进行操作!");
			}
		} else if (LogOperationEnum.YISHENHE_WO.getId() == operationId) {
			//审核前提：状态是提交
			if(!(eoObj.getZt() == EngineeringOrderStatusEnum.SUBMIT.getId())) {
				throw new BusinessException("该EO已更新，请刷新后再进行操作!");
			}
		} else if (LogOperationEnum.YIPIZHUN_WO.getId() == operationId) {
			//批准前提：状态是已审核
			if(!(eoObj.getZt() == EngineeringOrderStatusEnum.AUDITED.getId())) {
				throw new BusinessException("该EO已更新，请刷新后再进行操作!");
			}
		} else if (LogOperationEnum.Close.getId() == operationId) {//关闭
			//关闭前提：状态是提交 或 已审核 或 已批准 或 生效
			if(!(eoObj.getZt() == EngineeringOrderStatusEnum.SUBMIT.getId() ||
					eoObj.getZt() == EngineeringOrderStatusEnum.AUDITED.getId() ||
					eoObj.getZt() == EngineeringOrderStatusEnum.APPROVED.getId() ||
					eoObj.getZt() == EngineeringOrderStatusEnum.TAKEEFFECT.getId())) {
				throw new BusinessException("该EO已更新，请刷新后再进行操作!");
			}
		} else if (LogOperationEnum.DELETE.getId() == operationId) {
			//删除前提：状态是保存或审批驳回或批准驳回
			if(!(eoObj.getZt() == EngineeringOrderStatusEnum.SAVE.getId() || 
					eoObj.getZt() == EngineeringOrderStatusEnum.AUDITDOWN.getId() || 
					eoObj.getZt() == EngineeringOrderStatusEnum.APPROVALDOWN.getId())) {
				throw new BusinessException("该EO已更新，请刷新后再进行操作!");
			}
		}
	}

	/**
	 * @Description  根据EOId查询当前工卡的历史版本集合
	 * @CreateTime 2017-8-31 上午9:06:37
	 * @CreateBy 雷伟
	 * @param id EOID
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<WorkCard> queryHistoryListById(String id) {
		return engineeringOrderMapper.queryHistoryListById(id);
	}

	/**
	 * @Description 删除EO
	 * @CreateTime 2017-9-13 下午4:41:02
	 * @CreateBy 雷伟
	 * @param id
	 */
	@Override
	public void doDelete(String eoId) throws BusinessException {
		//当前登录用户
		User user = ThreadVarUtil.getUser();
		//当前时间
		Date currentDate = commonService.getSysdate();
		//操作流水
		String czls = UUID.randomUUID().toString();
		//操作类型
		LogOperationEnum operation = LogOperationEnum.DELETE;
		
		EngineeringOrder engineeringOrder = engineeringOrderMapper.selectByPrimaryKey(eoId); //EO信息
		
		if(null == engineeringOrder){
			throw new BusinessException("该EO已更新，请刷新后再进行操作!");
		}
		
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(engineeringOrder.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(),engineeringOrder.getDprtcode(), jxList);
				
		/**校验状态,防止重复提交*/
		this.validateResubmit(engineeringOrder.getId(),operation.getId()); //删除
		
		
		/**3.更新前版本的后版本id=null*/
		EngineeringOrder previousVersion = engineeringOrderMapper.selectByPrimaryKey(engineeringOrder.getfBbid());
		if(null != previousVersion){
			previousVersion.setbBbid(null);
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("bBbid", "ND");
			previousVersion.setParamsMap(paramsMap);
			engineeringOrderMapper.updateByPrimaryKeySelective(previousVersion);
		}
		
		//删除EO关联表数据
		this.deleteEO4Others(engineeringOrder,user,currentDate,czls,operation);
		//删除EO从表数据
		this.deleteEOSub(engineeringOrder,user,currentDate,czls,operation);
		//删除EO主表数据
		this.deleteEOMain(engineeringOrder,user,currentDate,czls,operation);
		
		/**4.处理待办**/
		this.doPgdDai(engineeringOrder,UpdateTypeEnum.DELETE.getId());
		
		//删除待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(eoId);
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(2);
		jdlist.add(3);
		jdlist.add(5);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(tododbyw);
	}
	
	/**
	 * @Description 物理删除EO主表数据
	 * @CreateTime 2017-9-13 下午5:13:02
	 * @CreateBy 雷伟
	 * @param engineeringOrder eo对象
	 * @param eoId EOID
	 * @param user 当前用户
	 * @param currentDate 当前时间
	 * @param czls 操作流水
	 * @param operation 操作类型
	 */
	private void deleteEOMain(EngineeringOrder engineeringOrder, User user,Date currentDate, String czls, LogOperationEnum operation) {
		commonRecService.write(engineeringOrder.getId(), TableEnum.B_G2_010, user.getId(), czls, operation, UpdateTypeEnum.DELETE, engineeringOrder.getId());
		engineeringOrderMapper.deleteByPrimaryKey(engineeringOrder.getId());
	}
	
	/**
	 * @Description 物理删除EO从表数据
	 * @CreateTime 2017-9-13 下午5:13:02
	 * @CreateBy 雷伟
	 * @param engineeringOrder eo对象
	 * @param eoId EOID
	 * @param user 当前用户
	 * @param currentDate 当前时间
	 * @param czls 操作流水
	 * @param operation 操作类型
	 */
	private void deleteEOSub(EngineeringOrder engineeringOrder, User user,Date currentDate, String czls, LogOperationEnum operation) {
		//保存历史记录信息
		commonRecService.write(engineeringOrder.getId(), TableEnum.B_G2_01000, user.getId(), czls, operation, UpdateTypeEnum.DELETE, engineeringOrder.getId());
		engineeringOrderSubMapper.deleteByMainId(engineeringOrder.getId());
	}

	/**
	 * @Description 物理删除EO关联表数据
	 * @CreateTime 2017-9-13 下午5:13:02
	 * @CreateBy 雷伟
	 * @param engineeringOrder eo对象
	 * @param eoId EOID
	 * @param user 当前用户
	 * @param currentDate 当前时间
	 * @param czls 操作流水
	 * @param operation 操作类型
	 */
	private void deleteEO4Others(EngineeringOrder engineeringOrder, User user, Date currentDate, String czls,LogOperationEnum operation) {
		//技术评估单数据(下达指令)
		instructionsourceService.deleteInstructionSourceByZlid(engineeringOrder.getId());
		//参考文件
	    commonRecService.write(engineeringOrder.getId(), TableEnum.B_G2_999, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,engineeringOrder.getId());
	    referenceMapper.deleteByYwid(engineeringOrder.getId());
		//工种工时
	    workHourService.deleteByYwid(engineeringOrder.getId(), czls, engineeringOrder.getId(), LogOperationEnum.DELETE);
	    //分发部门
	    commonRecService.writeByWhere("b.ywid = '".concat(engineeringOrder.getId()).concat("' ").concat("and b.zt=1"),TableEnum.B_G2_998, user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, engineeringOrder.getId());
	    distributionDepartmentMapper.deleteByYwid(engineeringOrder.getId());
		//附件列表
	    attachmentService.delFiles(engineeringOrder.getId(), czls, engineeringOrder.getId(), LogOperationEnum.DELETE);
		//器材清单 ~ 工具设备
	    materialToolService.deleteByYwid(engineeringOrder.getId(), czls, engineeringOrder.getId(), LogOperationEnum.DELETE);
		//工作内容附件
	    if(null != engineeringOrder.getGznrfjid() && !"".equals(engineeringOrder.getGznrfjid())){
			//删除工作内容附件
			attachmentService.delFiles(engineeringOrder.getGznrfjid(), czls, engineeringOrder.getId(), LogOperationEnum.DELETE);
		}
		//工作内容数据
	    workContentService.deleteByYwid(engineeringOrder.getId(), czls, engineeringOrder.getId(), LogOperationEnum.DELETE);
		//区域站位
		coverPlateService.deleteByYwid(engineeringOrder.getId(), czls, engineeringOrder.getId(), operation);
		//适用列表
		eoApplicabilityService.deleteByMainid(engineeringOrder, user, currentDate, czls, operation);
		//受影响出版物
		eoPulicationAffectedService.deleteByMainid(engineeringOrder,user,currentDate,czls,operation);
		//工时停场时间 
		eoManhourParkingTimeService.deleteByMainid(engineeringOrder,user,currentDate,czls,operation);
		//监控项信息
		eoMonitorIemSetService.deleteByMainid(engineeringOrder,user,currentDate,czls,operation);
	}

	/**
	 * @Description EO执行对象
	 * @CreateTime 2017年9月29日 下午2:21:37
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<Map<String, Object>> getEOExecutionObjs(String id) {
		return engineeringOrderMapper.getEOExecutionObjs(id);
	}
	
	@Override
	public EngineeringOrder getByEobh(EngineeringOrder engineeringOrder)
			throws BusinessException {
		// TODO Auto-generated method stub
		return engineeringOrderMapper.selectByEobh(engineeringOrder);
	}

	@Override
	public List<EngineeringOrder> exportEoList(EngineeringOrder engineeringOrder)
			throws BusinessException {
		String br = "\r\n";
		StringBuilder  builder=new StringBuilder("");
		List<Instructionsource> instructionsourceList=null;
		List<Airworthiness> airworthinessList=null;
		List<EngineeringOrder> list = engineeringOrderMapper.queryAllPageList(engineeringOrder);
		getOtherInfo(list);
		if (list != null) {
			for (EngineeringOrder order : list) {
				//拼接评估单
				instructionsourceList=order.getIsList();
				if(instructionsourceList!=null){
					for(Instructionsource instructionsource:instructionsourceList){
						if(instructionsource==null)continue;
						builder.append(instructionsource.getPgdh()).append(" R")
						.append(instructionsource.getBb()).append(br);
					}
					order.getParamsMap().put("evaluates", builder.toString());
					builder=new StringBuilder("");
				}
				//拼接来源文件
				airworthinessList=order.getLywjList();
				if(airworthinessList!=null){
					for(Airworthiness airworthiness:airworthinessList){
						if(airworthiness==null)continue;
						builder.append(airworthiness.getJswjbh()).append(" R")
						.append(airworthiness.getBb()).append(br);
					}
					order.getParamsMap().put("originals", builder.toString());
					builder=new StringBuilder("");
				}
				   
			}
		}
	    return list;
	}

	/**
	 * @description EO圈阅
	 * @CreateTime 2018-2-26 下午3:42:12
	 * @CreateBy 雷伟
	 * @param eoId
	 */
	@Override
	public void view4IsCirculuation(String id) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		
		if(StringUtils.isBlank(user.getBmdm())){
			return;
		}
		// 查询当前该数据对应的分发部门信息
		DistributionDepartment dDepartment = distributionDepartmentMapper.getDepartmentByYwidAndDxid4Eo(id,
				user.getBmdm());
		// 当前登录用户所在部门与该技术通告部门是同一个部门且未圈阅且是批准状态
		if(dDepartment == null){
			return;
		}
		if (dDepartment.getIsJs() == 0) {
			dDepartment.setWhrid(user.getId());
			distributionDepartmentMapper.updateisJsByid(dDepartment);// 更新标识为已阅
			PersonnelReceipt personnelReceipt = new PersonnelReceipt();// 人员接收表
			personnelReceipt.setId(UUID.randomUUID().toString());
			personnelReceipt.setDprtcode(dDepartment.getDprtcode());
			personnelReceipt.setYwlx(ProjectBusinessEnum.EO.getId());// 业务类型
			personnelReceipt.setYwid(id);// 业务id
			personnelReceipt.setJsbmid(dDepartment.getDxid());// 接收部门
			personnelReceipt.setJssj(new Date());// 制单时间
			personnelReceipt.setJsrid(user.getId());// 制单人
			personnelReceiptMapper.insertSelective(personnelReceipt);
			String czls = UUID.randomUUID().toString();
			commonRecService.write(dDepartment.getId(), TableEnum.B_G2_998, user.getId(), czls, LogOperationEnum.EDIT,
					UpdateTypeEnum.UPDATE, id);
			commonRecService.write(id, TableEnum.B_G2_010, user.getId(), czls, LogOperationEnum.EDIT,
					UpdateTypeEnum.UPDATE, id);
		}else{
			PersonnelReceipt pr = personnelReceiptMapper.getPersonnelReceipt4Validation(id, user.getId());
			if (pr == null && dDepartment != null) {
				pr = new PersonnelReceipt();
				pr.setId(UUID.randomUUID().toString());
				pr.setDprtcode(dDepartment.getDprtcode());
				pr.setYwlx(ProjectBusinessEnum.EO.getId());// 业务类型
				pr.setYwid(id);// 业务id
				pr.setJsbmid(dDepartment.getDxid());// 接收部门
				pr.setJssj(new Date());// 制单时间
				pr.setJsrid(user.getId());// 制单人
				personnelReceiptMapper.insertSelective(pr);
			}
		}
	}
}
