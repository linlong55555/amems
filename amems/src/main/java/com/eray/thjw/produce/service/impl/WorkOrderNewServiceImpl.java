package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.ComponentUseMapper;
import com.eray.thjw.produce.dao.InstallationListEditableMapper;
import com.eray.thjw.produce.dao.MonitoringPlanMapper;
import com.eray.thjw.produce.dao.MonitoringWorkpackageMapper;
import com.eray.thjw.produce.dao.WorkOrderIORecordMapper;
import com.eray.thjw.produce.dao.Workorder145Mapper;
import com.eray.thjw.produce.dao.WorkorderMapper;
import com.eray.thjw.produce.dao.WorkpackageMapper;
import com.eray.thjw.produce.po.ComponentUse;
import com.eray.thjw.produce.po.InstallAndRemove;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.po.MonitoringPlan;
import com.eray.thjw.produce.po.WorkOrderIORecord;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.po.Workorder145;
import com.eray.thjw.produce.po.Workpackage;
import com.eray.thjw.produce.service.InstallationListEffectService;
import com.eray.thjw.produce.service.InstallationListTempService;
import com.eray.thjw.produce.service.MonitorDataService;
import com.eray.thjw.produce.service.WorkOrderNewService;
import com.eray.thjw.produce.service.WorkOrderWorkerService;
import com.eray.thjw.produce.service.WorkpackageService;
import com.eray.thjw.project2.dao.InstructionsourceMapper;
import com.eray.thjw.project2.dao.MaintenanceProjectMapper;
import com.eray.thjw.project2.dao.ReferenceMapper;
import com.eray.thjw.project2.po.Instructionsource;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.service.CoverPlateService;
import com.eray.thjw.project2.service.InstructionsourceService;
import com.eray.thjw.project2.service.MaterialToolService;
import com.eray.thjw.project2.service.ReferenceService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.project2.service.WorkContentService;
import com.eray.thjw.project2.service.WorkHourService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.common.WhetherEnum;
import enu.produce.InstalledStatusEnum;
import enu.produce.RetentionTypeEnum;
import enu.produce.WorkorderStatusEnum;
import enu.produce.WorkorderTypeEnum;
import enu.project2.EngineeringOrderStatusEnum;
import enu.project2.MaintenanceProjectTypeEnum;
import enu.project2.MonitorProjectEnum;
import enu.project2.ProjectBusinessEnum;
import enu.project2.SendOrderEnum;

/**
 * @Description 135工单Service实现
 * @CreateTime 2017-9-29 上午10:32:55
 * @CreateBy 雷伟
 */
@Service
public class WorkOrderNewServiceImpl implements WorkOrderNewService {

	@Resource
	private WorkorderMapper workorderMapper;
	@Resource
	private Workorder145Mapper workorder145Mapper;
	@Resource
	private WorkpackageMapper workpackageMapper;
	@Resource
	private AttachmentService attachmentService;
	@Resource
	private CommonService commonService;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private PlaneModelDataService planeModelDataService;
	@Resource
	private CoverPlateService coverPlateService;
	@Resource
	private WorkHourService workHourService;
	@Resource
	private ReferenceService referenceService;
	@Resource
	private MaterialToolService materialToolService;
	@Resource
	private WorkContentService workContentService;
	@Resource
	private WorkpackageService workpackageService;
	@Resource
	private ReferenceMapper referenceMapper;
	@Resource
	private MonitoringPlanMapper monitoringPlanMapper;
	@Resource
	private MaintenanceProjectMapper maintenanceProjectMapper;
	@Resource
	private InstructionsourceService instructionsourceService;
	@Resource
	private TodorsService todorsService;
	@Resource
	private InstallationListTempService installationListTempService;
	@Resource
	private InstructionsourceMapper instructionsourceMapper;
	@Resource
	private WorkOrderIORecordMapper workOrderIORecordMapper;
	
	@Resource
	private InstallationListEffectService installationListEffectService;

	@Resource
	private InstallationListEditableMapper installationListEditableMapper;
	
	@Resource
	private ComponentUseMapper componentUseMapper;
	
	@Resource
	private MonitorDataService monitorDataService;
	
	@Resource
	private MonitoringWorkpackageMapper monitoringWorkpackageMapper;
	
	@Resource
	private WorkOrderWorkerService workOrderWorkerService;
	
	/**
	 * 
	 * @Description 选择不在工包和预组包中的工单
	 * @CreateTime 2017年9月29日 下午2:01:38
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @param fjzch
	 * @param gbid
	 * @return
	 */
	@Override
	public Map<String, Object> getWorkorderList(Workorder record) {
		PageHelper.startPage(record.getPagination());
		return PageUtil.pack4PageHelper(workorderMapper.getWorkorderList(record), record.getPagination());
	}

	/**
	 * 
	 * @Description 工包添加工单
	 * @CreateTime 2017年9月29日 下午5:00:20
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateGbid(Workorder record) throws BusinessException {
		// 验证工单当前状态是否为1或2且工包id为空
		validationWorkorder((List<String>) record.getParamsMap().get("idList"));
		workorderMapper.updateGbid(record);
		workpackageService.doAutoIssuedWorkorder(record.getGbid(),(List<String>) record.getParamsMap().get("idList"));
	}

	/**
	 * 
	 * @Description 验证工单当前状态是否为1或2且工包id为空
	 * @CreateTime 2017年9月29日 下午5:50:31
	 * @CreateBy 岳彬彬
	 * @param list
	 * @throws BusinessException
	 */
	private void validationWorkorder(List<String> list) throws BusinessException {
		int count = workorderMapper.getCountByztAndGdid(list);
		if (list.size() != count) {
			throw new BusinessException("工单数据已变更，请刷新页面!");
		}
	}

	/**
	 * @Description 工单135列表查询
	 * @CreateTime 2017-9-30 下午1:34:35
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 */
	@Override
	public Map<String, Object> queryAllPageList(Workorder workorder) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = workorder.getId();
		workorder.setId(""); //跑到第一行
		User user = ThreadVarUtil.getUser();
		workorder.getParamsMap().put("userId", user.getId());
		workorder.getParamsMap().put("userType", user.getUserType());
		PageHelper.startPage(workorder.getPagination());
		List<Workorder> eoList = workorderMapper.queryAllPageList(workorder);

		if (((Page) eoList).getTotal() > 0) {
			// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
			if (StringUtils.isNotBlank(id)) {
				// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
				if (!PageUtil.hasRecord(eoList, id)) {
					// 在查询条件中增加ID
					Workorder newRecord = new Workorder();
					newRecord.setId(id);
					List<Workorder> newRecordList = workorderMapper.queryAllPageList(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						eoList.add(0, newRecordList.get(0));
					}
				}
			}
			getOtherInfo(eoList);// 获取关联其他信息
			resultMap = PageUtil.pack4PageHelper(eoList, workorder.getPagination());
			return resultMap;

		} else {
			List<Workorder> newRecordList = new ArrayList<Workorder>();
			if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				// 在查询条件中增加ID
				Workorder newRecord = new Workorder();
				newRecord.setId(id);
				newRecordList = workorderMapper.queryAllPageList(newRecord);
				if (newRecordList != null && newRecordList.size() == 1) {
					resultMap = PageUtil.pack(1, newRecordList, workorder.getPagination());
					return resultMap;
				}

			}
			getOtherInfo(eoList);// 获取关联其他信息
			resultMap = PageUtil.pack(0, newRecordList, workorder.getPagination());
			return resultMap;
		}
	}
	
	/**
	 * @Description 工单135信息查询 (弹窗)
	 * @CreateTime 2017-12-28 上午11:27:24
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return 工单列表
	 */
	@Override
	public Map<String, Object> queryAllPageListWin(Workorder workorder) {
		User user = ThreadVarUtil.getUser();
		workorder.getParamsMap().put("userId", user.getId());
		workorder.getParamsMap().put("userType", user.getUserType());
		PageHelper.startPage(workorder.getPagination());
		List<Workorder> list = workorderMapper.queryAllPageListWin(workorder);
		return PageUtil.pack4PageHelper(list, workorder.getPagination());
	}

	private void getOtherInfo(List<Workorder> eoList) {
		if(null != eoList){
			for (Workorder workorder : eoList) {
				this.getCompleteLimit(workorder); //设置完成时限
			}
		}
	}
	/**
	 * 
	 * @Description 工包明细列表处理附件
	 * @CreateTime 2017年9月30日 下午2:33:46
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public void doAttachment(Workorder record) throws BusinessException {
		String czls = UUID.randomUUID().toString();
		attachmentService.eidtAttachment(record.getAttachments(), record.getId(), czls, record.getId(),
				record.getDprtcode(), LogOperationEnum.EDIT);

	}

	/**
	 * @Description 新增工单
	 * @CreateTime 2017-10-10 下午8:50:14
	 * @CreateBy 雷伟
	 * @param workorder 工单135
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String save(Workorder workorder) throws BusinessException {
		/**准备数据*/
		workorder.setId(UUID.randomUUID().toString());//工单ID
		if (null != workorder.getWorkContentAttachment()) {
			workorder.setGznrfjid(UUID.randomUUID().toString());//工作内容附件ID
		}
		User user = ThreadVarUtil.getUser();//当前登录用户
		Date currentDate = commonService.getSysdate();//当前时间(取数据库时间)
		String czls = UUID.randomUUID().toString();//操作流水
		LogOperationEnum operation = LogOperationEnum.SAVE_WO;//确定状态
		if (null != workorder.getParamsMap() && null != workorder.getParamsMap().get("operationType")) {
			if (WorkorderStatusEnum.SAVE.getId() == workorder.getParamsMap().get("operationType")) {
				workorder.setZt(WorkorderStatusEnum.SAVE.getId());
			}else {
				workorder.setZt(WorkorderStatusEnum.SUBMIT.getId());
				operation = LogOperationEnum.SUBMIT_WO;
			}
		}

		/**检查用户飞机注册号的权限*/
		
		if(StringUtils.isNotBlank(workorder.getFjzch())){
			List<String> fjzchList = new ArrayList<String>();
			fjzchList.add(workorder.getFjzch());
			planeModelDataService.existsAircraft4Expt(user.getId(), user.getUserType(), user.getJgdm(), fjzchList);
		}
		
		//当页面没有填写缺陷保留编号时
		if(workorder.getGdbh() == null || "".equals(workorder.getGdbh())){ 
			String gddh=setGdbh(workorder,user); //根据缺陷获取最新缺陷保留单号
			workorder.setGdbh(gddh);
		}else{
			//验证验证缺陷保留单编号 唯一
			this.validateUniqueness(workorder);
		}

		/**保存数据*/
		this.saveWOMain(workorder,currentDate,czls,operation);//保存WO主表数据
		
		this.saveWO4Others(workorder,currentDate,czls,operation);//保存WO关联表数据
		
		/**Start: 自动下发工单*/
		if(null != workorder.getParamsMap() && WorkorderStatusEnum.SUBMIT.getId() == workorder.getParamsMap().get("operationType")){
			if(workorder.getGbid() != null ){
				List<String> list = new ArrayList<String>();
				list.add(workorder.getId());
				workpackageService.doAutoIssuedWorkorder(workorder.getGbid(), list);
			}
		}
		/**End: 自动下发工单*/

		/**保留处理*/
		if (RetentionTypeEnum.GZ.getId() == workorder.getBlLx()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", workorder.getBlId());
			map.put("gdid",workorder.getId());
			workorderMapper.updateGZBLByGdid(map);//b_s2_013 故障保留
		}else if (RetentionTypeEnum.QX.getId() == workorder.getBlLx()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", workorder.getBlId());
			map.put("gdid",workorder.getId());
			workorderMapper.updateQXBLByGdid(map); //b_s2_012 缺陷保留
		}

		return workorder.getId();
	}

	/**
	 * @Description 保存工单关联表数据
	 * @CreateTime 2017-10-10 下午9:23:18
	 * @CreateBy 雷伟
	 * @param workorder 工单135
	 * @param currentDate 当前时间（取数据库时间）
	 * @param czls 操作流水
	 * @param operation operation 
	 */
	private void saveWO4Others(Workorder workorder, Date currentDate,String czls, LogOperationEnum operation) {
		User user = ThreadVarUtil.getUser();//当前登录用户
		//区域、接近、飞机站位
		coverPlateService.saveCoverPlateList(workorder.getCoverPlateList(), ProjectBusinessEnum.WORKORDER.getId(), workorder.getId(), czls, workorder.getId(), user.getJgdm(), operation);
		//工种工时 
		workHourService.saveWorkHourList(workorder.getWorkHourList(), ProjectBusinessEnum.WORKORDER.getId(), workorder.getId(), czls, workorder.getId(), user.getJgdm(), operation);
		//相关参考文件
		referenceService.saveReferenceList(workorder.getReferenceList(), ProjectBusinessEnum.WORKORDER.getId(), workorder.getId(), czls, workorder.getId(), user.getJgdm(), operation);
		//器材、工具
		materialToolService.saveMaterialToolList(workorder.getMaterialToolList(), ProjectBusinessEnum.WORKORDER.getId(),  workorder.getId(), czls,  workorder.getId(), user.getJgdm(), operation);
		//工作内容
		workContentService.saveWorkContentList(workorder.getWorkContentList(),ProjectBusinessEnum.WORKORDER.getId(), workorder.getId(), czls,  workorder.getId(), user.getJgdm(), operation);
		//工作内容附件
		if (null != workorder.getWorkContentAttachment()) {
			attachmentService.addAttachment(workorder.getWorkContentAttachment(),workorder.getGznrfjid(), czls, workorder.getId(), user.getJgdm(), operation);
		}
		//保存下达指令
		instructionsourceService.saveInstructionSourceList(workorder.getIsList(), ProjectBusinessEnum.NRC.getId(), workorder.getId(), workorder.getDprtcode());
		//新增待办事宜处理结果
		if (null != workorder.getIsList()) {
			List<String> lyidList = new ArrayList<String>();
			for(Instructionsource instructionsource : workorder.getIsList()){
				lyidList.add(instructionsource.getPgdid());
			}
			// 新增待办事宜处理结果
			todorsService.insertTodorsList(workorder.getJx(), lyidList, SendOrderEnum.NRC.getId(), workorder.getWhrid()
					, workorder.getId(), workorder.getGdbh(), "", workorder.getGdbt(),workorder.getZt());
		}
		//附件列表
		attachmentService.eidtAttachment(workorder.getAttachments(), workorder.getId(), czls, workorder.getId(), user.getJgdm(), operation);
	}

	/**
	 * @Description  保存工单主表数据
	 * @CreateTime 2017-10-10 下午9:23:18
	 * @CreateBy 雷伟
	 * @param workorder 工单135
	 * @param currentDate 当前时间（取数据库时间）
	 * @param czls 操作流水
	 * @param operation operation 
	 * @throws BusinessException 
	 */
	private void saveWOMain(Workorder workorder, Date currentDate, String czls,LogOperationEnum operation) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登录用户
		workorder.setGdsbid(UUID.randomUUID().toString());//工单识别ID
		
		workorder.setDprtcode(user.getJgdm());//组织编码
		workorder.setWhdwid(user.getBmdm());//维护人单位ID
		workorder.setWhrid(user.getId());//维护人ID
		workorder.setWhsj(currentDate);//维护时间
		workorder.setZdbmid(user.getBmdm());//制单部门ID
		workorder.setZdrid(user.getId());//制单人id
		workorder.setZdrq(currentDate);//制单日期
		//关闭人不为空时设置关闭时间
		if(StringUtils.isNotBlank(workorder.getGbrid())){
			workorder.setGbrq(currentDate);
		}
		workorder.setXmblbs(WhetherEnum.NO.getId());//项目保留标识标识：0否、1是
		workorder.setWgbs(WhetherEnum.NO.getId());//完工标识：0未完工、1已完工
		workorderMapper.insert(workorder);
		//保存工作者
		workOrderWorkerService.save(workorder, czls, operation);
		//保存历史记录信息
		commonRecService.write(workorder.getId(), TableEnum.B_S2_008, user.getId(), czls, operation, UpdateTypeEnum.SAVE, workorder.getId());
	}
	
	/**
	 * 
	 * @Description 生成编号
	 * @CreateTime 2017年9月27日 下午1:48:51
	 * @CreateBy 林龙
	 * @param Workorder  
	 * @param user 当前用户
	 * @return
	 * @throws BusinessException 
	 */
	private String setGdbh(Workorder workorder,User user) throws BusinessException {
		Workorder fai = new Workorder(); 		  	//new 编号
		if (StringUtils.isNotEmpty(workorder.getGbid())) {
			Workpackage workPage = workpackageMapper.selectByPrimaryKey(workorder.getGbid()); //获得135的工包
			if (workPage != null && StringUtils.isNotEmpty(workPage.getGbbh())) {
				workorder.setGbbh(workPage.getGbbh());
			}
		}
		boolean b = true;
		String gddh = null;
		while(b){ 
			try {
				gddh = SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.GD135.getName(),workorder);
			} catch (SaibongException e) {
				throw new BusinessException(e);
			}
			fai.setGdbh(gddh);
			fai.setDprtcode(user.getJgdm());
			//根据技术文件对象查询数量
			int i = workorderMapper.queryCount(fai); 
			if(i <= 0){
				b = false;
			}
		}
		return gddh;
	}

	/**
	 * @Description 校验数据唯一性
	 * @CreateTime 2017-10-10 下午9:10:57
	 * @CreateBy 雷伟
	 * @param workorder
	 * @throws BusinessException 
	 */
	private void validateUniqueness(Workorder workorder) throws BusinessException {
		//机构代码+工单编号唯一
		int iCount = workorderMapper.getCount4CheckExist(workorder.getGdbh(), (String)workorder.getParamsMap().get("dprtcode"));
		if (iCount > 0) {
			throw new BusinessException("工单编号已存在!");
		}
	}

	/**
	 * 
	 * @Description 工单列表加载
	 * @CreateTime 2017年10月9日 下午9:10:43
	 * @CreateBy 林龙
	 * @param workorder 工单
	 * @return
	 */
	@Override
	public Map<String, Object> queryAllList(Workorder workorder)throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = workorder.getId();
		workorder.setId(""); //跑到第一行
		User user = ThreadVarUtil.getUser();//当前登陆人
		workorder.getParamsMap().put("userId", user.getId());
		workorder.getParamsMap().put("userType", user.getUserType());//验证飞机注册号权限权限
		PageHelper.startPage(workorder.getPagination());
		List<Workorder> eoList = workorderMapper.queryAllList(workorder);

		if (((Page) eoList).getTotal() > 0) {
			// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
			if (StringUtils.isNotBlank(id)) {
				// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
				if (!PageUtil.hasRecord(eoList, id)) {
					// 在查询条件中增加ID
					Workorder newRecord = new Workorder();
					newRecord.setId(id);
					List<Workorder> newRecordList = workorderMapper.queryAllList(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						eoList.add(0, newRecordList.get(0));
					}
				}
			}
			resultMap = PageUtil.pack4PageHelper(eoList, workorder.getPagination());
			return resultMap;

		} else {
			List<Workorder> newRecordList = new ArrayList<Workorder>();
			if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				// 在查询条件中增加ID
				Workorder newRecord = new Workorder();
				newRecord.setId(id);
				newRecordList = workorderMapper.queryAllList(newRecord);
				if (newRecordList != null && newRecordList.size() == 1) {
					resultMap = PageUtil.pack(1, newRecordList, workorder.getPagination());
					return resultMap;
				}

			}
			resultMap = PageUtil.pack(0, newRecordList, workorder.getPagination());
			return resultMap;
		}
	}

	/**
	 * @Description 根据条件查询维修历史清单列表
	 * @CreateTime 2017-10-09上午11:06:29
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Workorder> 工单数据集合
	 */
	@Override
	public List<Workorder> queryAllTaskhistory(Workorder workorder){
		return workorderMapper.queryAllTaskhistory(workorder);
	}
	
	/**
	 * @Description 根据条件查询NRC工单列表
	 * @CreateTime 2017-10-13 上午9:58:19
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Workorder> 工单数据集合
	 */
	@Override
	public List<Workorder> queryNRCWorkOrderList(Workorder workorder){
		return workorderMapper.queryNRCWorkOrderList(workorder);
	}

	/**
	 * @Description 根据id查询工单信息
	 * @CreateTime 2017年10月10日 上午9:49:26
	 * @CreateBy 林龙
	 * @param gdid 工单id
	 */
	@Override
	public Workorder selectByPrimaryKey(String gdid) {
		return workorderMapper.selectByPrimaryKey(gdid);
	}

	/**
	 * @Description 根据id查询工单信息
	 * @CreateTime 2017年10月10日 上午9:49:26
	 * @CreateBy 雷伟
	 * @param gdid 工单id
	 */
	@Override
	public Workorder selectWOById(String gdid) {
		return workorderMapper.selectWOById(gdid);
	}

	/**
	 * @Description 编辑工单
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param workorder 工单
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String update(Workorder workorder) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登录用户
		Date currentDate = commonService.getSysdate();//当前时间
		String czls = UUID.randomUUID().toString();//操作流水
		LogOperationEnum operation = LogOperationEnum.EDIT;//操作类型

		Workorder woObj = workorderMapper.selectByPrimaryKey(workorder.getId()); //WO信息

		/**检查用户飞机注册号的权限*/
		if(StringUtils.isNotBlank(workorder.getFjzch())){
			List<String> fjzchList = new ArrayList<String>();
			fjzchList.add(workorder.getFjzch());
			planeModelDataService.existsAircraft4Expt(user.getId(), user.getUserType(),woObj.getDprtcode(),fjzchList);
		}	
		
		//确定状态
		if(null != workorder.getParamsMap() && null != workorder.getParamsMap().get("operationType")){
			if(WorkorderStatusEnum.SUBMIT.getId() == workorder.getParamsMap().get("operationType")){
				workorder.setZt(WorkorderStatusEnum.SUBMIT.getId());
				/**校验状态,防止重复*/
				this.validateResubmit(woObj,LogOperationEnum.EDIT.getId());
				operation = LogOperationEnum.EDIT;
			}else if(WorkorderStatusEnum.SAVE.getId() == workorder.getParamsMap().get("operationType")){
				operation = LogOperationEnum.EDIT;
			}
		}

		/**修改数据*/
		this.updateWO4Others(workorder,currentDate,czls,operation);//修改WO关联表数据
		this.updateWOMain(workorder,currentDate,czls,operation);//修改WO主表数据(需先更新关联表，再更新主表，因为要会写工作内容附件ID)
		/** 下发状态的工单需要同步到145工单 */
		if(null != workorder.getParamsMap() && WorkorderStatusEnum.TAKEEFFECT.getId() == workorder.getParamsMap().get("operationType")){
			doSync(workorder,currentDate,czls,operation);
		}
		/**Start: 自动下发工单*/
		if(null != workorder.getParamsMap() && WorkorderStatusEnum.SUBMIT.getId() == workorder.getParamsMap().get("operationType")){
			if(workorder.getGbid() != null ){
				List<String> list = new ArrayList<String>();
				list.add(workorder.getId());
				workpackageService.doAutoIssuedWorkorder(workorder.getGbid(), list);
			}
		}
		/**End: 自动下发工单*/
		return workorder.getId();
	}

	/**
	 * @Description 修改工单135表数据
	 * @CreateTime 2017-10-11 下午7:56:06
	 * @CreateBy 雷伟
	 * @param workorder WO
	 * @param currentDate 当前时间
	 * @param czls 操作流水号
	 * @param operation 操作类型
	 */
	public void updateWOMain(Workorder workorder, Date currentDate,String czls, LogOperationEnum operation) {
		User user = ThreadVarUtil.getUser();//当前登录用户
		workorder.setWhdwid(user.getBmdm());//维护人单位ID
		workorder.setWhrid(user.getId());//维护人ID
		workorder.setWhsj(currentDate);//维护时间
		workorderMapper.updateByPrimaryKeySelective(workorder);

		//保存历史记录信息
		commonRecService.write(workorder.getId(), TableEnum.B_S2_008, user.getId(), czls, operation, UpdateTypeEnum.UPDATE, workorder.getId());
	}

	/**
	 * @Description 更新关联表数据
	 * @CreateTime 2017-10-11 下午7:53:31
	 * @CreateBy 雷伟
	 * @param workorder WO
	 * @param currentDate 当前时间
	 * @param czls 操作流水号
	 * @param operation 操作类型
	 */
	private void updateWO4Others(Workorder workorder, Date currentDate,String czls, LogOperationEnum operation) {
		//区域、接近、飞机站位
		coverPlateService.updateCoverPlateList(workorder.getCoverPlateList(), ProjectBusinessEnum.WORKORDER.getId(), workorder.getId(), czls, workorder.getId(),  workorder.getDprtcode(), operation);
		//工种工时 
		workHourService.updateWorkHourList(workorder.getWorkHourList(), ProjectBusinessEnum.WORKORDER.getId(), workorder.getId(), czls, workorder.getId(), workorder.getDprtcode(), operation);
		//相关参考文件
		referenceService.updateReferenceList(workorder.getReferenceList(), ProjectBusinessEnum.WORKORDER.getId(), workorder.getId(), czls, workorder.getId(), workorder.getDprtcode(), operation);
		//器材、工具
		materialToolService.updateMaterialToolList(workorder.getMaterialToolList(), ProjectBusinessEnum.WORKORDER.getId(),  workorder.getId(), czls,  workorder.getId(), workorder.getDprtcode(), operation);
		//工作内容
		workContentService.updateWorkContentList(workorder.getWorkContentList(),ProjectBusinessEnum.WORKORDER.getId(), workorder.getId(), czls,  workorder.getId(), workorder.getDprtcode(), operation);
		//工作内容附件
		Workorder woObj = workorderMapper.selectByPrimaryKey(workorder.getId()); //WO信息
		if(null != woObj.getGznrfjid() && !"".equals(woObj.getGznrfjid())){
			attachmentService.delFiles(woObj.getGznrfjid(), czls, workorder.getId(), LogOperationEnum.EDIT);
			if(workorder.getWorkContentAttachment() == null){
				//删除工卡附件
				workorder.setGznrfjid("");
			}else{
				//编辑工卡附件
				attachmentService.addAttachment(workorder.getWorkContentAttachment(), woObj.getGznrfjid(), czls, workorder.getId(),  workorder.getDprtcode(), operation);
			}
		}else{
			if(workorder.getWorkContentAttachment() != null){
				workorder.setGznrfjid(UUID.randomUUID().toString());
				attachmentService.addAttachment(workorder.getWorkContentAttachment(), workorder.getGznrfjid(), czls, workorder.getId(),  workorder.getDprtcode(), operation);
			}
		}
		//保存下达指令
		instructionsourceService.updateInstructionSourceList(workorder.getIsList(), ProjectBusinessEnum.NRC.getId(), workorder.getId(), workorder.getDprtcode());
		// 删除旧的待办事宜待处理结果
		todorsService.deleteTodorsByYwid(workorder.getId());
		//新增待办事宜处理结果
		if (null != workorder.getIsList()) {
			List<String> lyidList = new ArrayList<String>();
			for(Instructionsource instructionsource : workorder.getIsList()){
				lyidList.add(instructionsource.getPgdid());
			}
			// 新增待办事宜处理结果
			todorsService.insertTodorsList(workorder.getJx(), lyidList, SendOrderEnum.NRC.getId(),ThreadVarUtil.getUser().getId()
					, workorder.getId(), workorder.getGdbh(), "", workorder.getGdbt(),workorder.getZt());
		}
		//附件列表
		attachmentService.eidtAttachment(workorder.getAttachments(), workorder.getId(), czls, workorder.getId(), workorder.getDprtcode(), operation);
	}
	/**
	 * 
	 * @Description 135同步到145
	 * @CreateTime 2017年12月28日 上午11:08:25
	 * @CreateBy 岳彬彬
	 * @param workorder
	 * @param currentDate
	 * @param czls
	 * @param operation
	 */
	private void doSync(Workorder workorder, Date currentDate,String czls, LogOperationEnum operation){
		Workpackage wp = workpackageMapper.selectByPrimaryKey(workorder.getGbid());
		if(null != wp && wp.getJhWwbs() == 0){//内部执行单位
			Workorder145 wo145 = workorder145Mapper.getByGdsbid(workorder.getGdsbid());
			if(null != wo145){
				//同步主表
				workorder145Mapper.updateByGdsbid(workorder);
				commonRecService.write(wo145.getId(), TableEnum.B_S2_014, ThreadVarUtil.getUser().getId(), czls, operation, UpdateTypeEnum.UPDATE, wo145.getId());
				//同步从表
				//区域、接近、飞机站位
				coverPlateService.updateCoverPlateList(workorder.getCoverPlateList(), ProjectBusinessEnum.WORKORDER.getId(), wo145.getId(), czls, wo145.getId(),  workorder.getDprtcode(), operation);
				//工种工时 
				workHourService.updateWorkHourList(workorder.getWorkHourList(), ProjectBusinessEnum.WORKORDER.getId(), wo145.getId(), czls, wo145.getId(), workorder.getDprtcode(), operation);
				//相关参考文件
				referenceService.updateReferenceList(workorder.getReferenceList(), ProjectBusinessEnum.WORKORDER.getId(), wo145.getId(), czls, wo145.getId(), workorder.getDprtcode(), operation);
				//器材、工具
				materialToolService.updateMaterialToolList(workorder.getMaterialToolList(), ProjectBusinessEnum.WORKORDER.getId(),  wo145.getId(), czls,  wo145.getId(), workorder.getDprtcode(), operation);
				//工作内容
				workContentService.updateWorkContentList(workorder.getWorkContentList(),ProjectBusinessEnum.WORKORDER.getId(), wo145.getId(), czls,  wo145.getId(), workorder.getDprtcode(), operation);
				//工作内容附件id2边是一致的，所以不需要处理
				//工单附件
				attachmentService.eidtAttachment(workorder.getAttachments(), wo145.getId(), czls, wo145.getId(), workorder.getDprtcode(), operation);
			}
		}
	}
	/**
	 * @Description 校验是否重复提交
	 * @CreateTime 2017-8-29 上午10:30:56
	 * @CreateBy 雷伟
	 * @param woObj 数据库工单对象
	 * @param operationId 操作类型ID
	 * @throws BusinessException 
	 */
	private void validateResubmit(Workorder woObj, Integer operationId) throws BusinessException {
		if (LogOperationEnum.EDIT.getId() == operationId) {
			//编辑前提：状态不等于9或10
			if(woObj.getZt() == WorkorderStatusEnum.CLOSETOEND.getId() || woObj.getZt() == WorkorderStatusEnum.CLOSETOFINISH.getId()) {
				throw new BusinessException("该工单已更新，请刷新后再进行操作!");
			}
		} else if (LogOperationEnum.DELETE.getId() == operationId) {
			//删除前提：状态是保存
			if(!(woObj.getZt() == EngineeringOrderStatusEnum.SAVE.getId())) {
				throw new BusinessException("该工单已更新，请刷新后再进行操作!");
			}
		} else if (LogOperationEnum.FEEDBACK.getId() == operationId) {
			//反馈前提：NRC工单状态为2或7，其他工单状态是7
			if(WorkorderTypeEnum.NRC.getId() == woObj.getGdlx()){
				if (EngineeringOrderStatusEnum.TAKEEFFECT.getId() != woObj.getZt() && EngineeringOrderStatusEnum.SUBMIT.getId() != woObj.getZt()) {
					throw new BusinessException("该工单已更新，请刷新后再进行操作!");
				}
			}else{
				if(!(woObj.getZt() == EngineeringOrderStatusEnum.TAKEEFFECT.getId())) {
					throw new BusinessException("该工单已更新，请刷新后再进行操作!");
				}
			}
		} else if (LogOperationEnum.WANCHEN.getId() == operationId) {
			//正常完成前提：NRC工单状态为2或7，其他工单状态是7 且 工单项目保留标识 = 0
			if(WorkorderTypeEnum.NRC.getId() == woObj.getGdlx()){
				if (!(EngineeringOrderStatusEnum.TAKEEFFECT.getId() == woObj.getZt() || EngineeringOrderStatusEnum.SUBMIT.getId() == woObj.getZt() && woObj.getXmblbs() == 0)) {
					throw new BusinessException("该工单已更新，请刷新后再进行操作!");
				}
			}else{
				if(!(woObj.getZt() == EngineeringOrderStatusEnum.TAKEEFFECT.getId() && woObj.getXmblbs() == 0)) {
					throw new BusinessException("该工单已更新，请刷新后再进行操作!");
				}
			}	
		} else if (LogOperationEnum.GUANBI.getId() == operationId) {
			//指定结束前提：状态是7生效 或状态=2
			if(!(woObj.getZt() == EngineeringOrderStatusEnum.TAKEEFFECT.getId() || woObj.getZt() == EngineeringOrderStatusEnum.SUBMIT.getId() )) {
				throw new BusinessException("该工单已更新，请刷新后再进行操作!");
			}
		} else if (LogOperationEnum.REVISE.getId() == operationId) {
			//修订前提：状态是9关闭 或状态=10 指定结束
			if(!(woObj.getZt() == EngineeringOrderStatusEnum.CLOSETOEND.getId() || woObj.getZt() == EngineeringOrderStatusEnum.CLOSETOFINISH.getId() )) {
				throw new BusinessException("该工单已更新，请刷新后再进行操作!");
			}
		}
	}


	/**
	 * @Description 删除工单
	 * @CreateTime 2017-10-12 下午2:54:11
	 * @CreateBy 雷伟
	 * @param woId 工单ID
	 * @throws BusinessException
	 */
	@Override
	public void doDelete(String woId) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		String czls = UUID.randomUUID().toString();//操作流水
		LogOperationEnum operation = LogOperationEnum.DELETE;//操作类型

		Workorder woObj = workorderMapper.selectByPrimaryKey(woId); //WO信息

		if(null == woObj){
			throw new BusinessException("该工单已更新，请刷新后再进行操作!");
		}

		//检查用户机型的权限
		/*List<String> jxList = new ArrayList<String>();
		jxList.add(workorder.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(),woObj.getDprtcode(), jxList);*/

		/**校验状态,防止重复提交*/
		this.validateResubmit(woObj,operation.getId()); //删除

		//删除EO关联表数据
		this.deleteWO4Others(woObj,currentDate,czls,operation);
		//删除EO从表数据
		this.deleteWOMain(woObj,currentDate,czls,operation);
	}

	/**
	 * @Description 删除工单表信息
	 * @CreateTime 2017-10-12 下午3:03:37
	 * @CreateBy 雷伟
	 * @param workorder 工单135
	 * @param currentDate 当前时间（取数据库时间）
	 * @param czls 操作流水
	 * @param operation operation 
	 */
	private void deleteWOMain(Workorder woObj, Date currentDate, String czls,LogOperationEnum operation) {
		User user = ThreadVarUtil.getUser();//当前登录用户
		//保存历史记录信息
		commonRecService.write(woObj.getId(), TableEnum.B_S2_008, user.getId(), czls, operation, UpdateTypeEnum.DELETE, woObj.getId());
		workorderMapper.deleteByPrimaryKey(woObj.getId());
		// 删除待办事宜待处理结果
		todorsService.deleteTodorsByYwid(woObj.getId());
		// 删除下达指令
		instructionsourceMapper.deleteInstructionSourceByZlid(woObj.getId());
	}

	/**
	 * @Description 删除工单关联表信息
	 * @CreateTime 2017-10-12 下午3:03:42
	 * @CreateBy 雷伟
	 * @param workorder 工单135
	 * @param currentDate 当前时间（取数据库时间）
	 * @param czls 操作流水
	 * @param operation operation 
	 */
	private void deleteWO4Others(Workorder woObj, Date currentDate,String czls, LogOperationEnum operation) {
		//区域、接近、飞机站位
		coverPlateService.deleteByYwid(woObj.getId(), czls, woObj.getId(), operation);
		//工种工时 
		workHourService.deleteByYwid(woObj.getId(), czls, woObj.getId(), LogOperationEnum.DELETE);
		//相关参考文件
		commonRecService.write(woObj.getId(), TableEnum.B_G2_999, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,woObj.getId());
	    referenceMapper.deleteByYwid(woObj.getId());
		//器材、工具
	    materialToolService.deleteByYwid(woObj.getId(), czls, woObj.getId(), LogOperationEnum.DELETE);
		//工作内容
	    workContentService.deleteByYwid(woObj.getId(), czls, woObj.getId(), LogOperationEnum.DELETE);
	}

	/**
	 * @Description 工单反馈
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param workorder 工单
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String doFeedback(Workorder workorder) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登录用户
		Date currentDate = commonService.getSysdate();//当前时间
		String czls = UUID.randomUUID().toString();//操作流水
		LogOperationEnum operation = LogOperationEnum.FEEDBACK;//操作类型

		Workorder woObj = workorderMapper.selectByPrimaryKey(workorder.getId()); //WO信息

		//检查用户机型的权限
		/*List<String> jxList = new ArrayList<String>();
		jxList.add(workorder.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(),woObj.getDprtcode(), jxList);*/

		if(null == woObj){
			throw new BusinessException("该工单已更新，请刷新后再进行操作!");
		}
		
		/**校验状态,防止重复提交*/
		this.validateResubmit(woObj,operation.getId()); //完工反馈

		/**修改数据*/
		workorder.setWhdwid(user.getBmdm());//维护人单位ID
		workorder.setWhrid(user.getId());//维护人ID
		workorder.setWhsj(currentDate);//维护时间
		workorder.setWgfkrdwid(user.getBmdm());//完工反馈人单位ID
		workorder.setWgfkrid(user.getId());//完工反馈人ID
		workorder.setWgfksj(currentDate);//完工反馈时间
		workorderMapper.updateByPrimaryKeySelective(workorder);
		commonRecService.write(workorder.getId(), TableEnum.B_S2_008, user.getId(), czls, operation, UpdateTypeEnum.UPDATE, workorder.getId());//保存历史记录信息
		
		//保存工作者
		workOrderWorkerService.save(workorder, czls, operation);
		
		//根据工单同步修改FLB完成工作
		this.workorderMapper.updateFlbWorkByWorkorder(workorder.getId());
		//工单信息修改后 工作者同步更新FLB信息
		workOrderWorkerService.updateFlbWorkerByWorkorderWorker(workorder.getId());
		//拆装件
		doReplacementRecord(workorder,user,czls,operation);
		attachmentService.eidtAttachment(workorder.getAttachments(), workorder.getId(), czls, workorder.getId(), workorder.getDprtcode(), operation);//附件列表
		
		
		return workorder.getId();
	}
	/**
	 * 
	 * @Description 处理拆换件记录
	 * @CreateTime 2017年10月30日 下午4:30:58
	 * @CreateBy 岳彬彬
	 * @param workorder
	 * @param user
	 * @param czls
	 * @param operation
	 * @throws BusinessException
	 */
	private void doReplacementRecord(Workorder workorder,User user,String czls,LogOperationEnum operation) throws BusinessException{
		deleteNotExist(workorder,czls,user);
		if(workorder.getWoIORecordList()!=null){
			List<WorkOrderIORecord> list = workorder.getWoIORecordList();
			for (WorkOrderIORecord workOrderIORecord : list) {
				if(null != workOrderIORecord.getZsj() && StringUtils.isNotBlank(workOrderIORecord.getZsj().getBjh())){
					//验证装上件初始化信息
					if(StringUtils.isBlank(workOrderIORecord.getZsj().getJldw())){
						throw new BusinessException("该工单拆换件记录的装上件初始化信息的单位没有填写，请填写后再进行操作!");
					}
					if(StringUtils.isNotBlank(workOrderIORecord.getZsj().getXlh()) && (null == workOrderIORecord.getZsj().getInitDatas() || 0 == workOrderIORecord.getZsj().getInitDatas().size())){
						throw new BusinessException("该工单拆换件记录的装上件初始化信息的初始化日期没有填写，请填写后再进行操作!");
					}
					String zsjid = installationListTempService.save(workOrderIORecord.getZsj(), workOrderIORecord.getZsZjqdid());
					workOrderIORecord.getZsj().setCzls(workOrderIORecord.getCzls());
					workOrderIORecord.getZsj().setLogOperationEnum(workOrderIORecord.getLogOperationEnum());
					workOrderIORecord.setZsZjqdid(zsjid);					
				}
				workOrderIORecord.setMainid(workorder.getId());
				workOrderIORecord.setCzls(workorder.getCzls());
				workOrderIORecord.setLogOperationEnum(workorder.getLogOperationEnum());
				workOrderIORecord.setWhrid(workorder.getId());
				workOrderIORecord.setWhsj(new Date());
				workOrderIORecord.setWhdwid(workorder.getWhdwid());
				workOrderIORecord.setZt(EffectiveEnum.YES.getId());
				
				if (StringUtils.isBlank(workOrderIORecord.getId())){	// 新增拆装记录
					workOrderIORecord.setId(UUID.randomUUID().toString());
					workOrderIORecordMapper.insertSelective(workOrderIORecord);
					// 保存历史记录信息
					commonRecService.write(workOrderIORecord.getId(), TableEnum.B_S2_00801, user.getId(), 
							czls , operation, UpdateTypeEnum.SAVE, workorder.getId());
					
				} else {	// 修改拆装记录
					workOrderIORecordMapper.updateByWorkOrder(workOrderIORecord);
					//同步修改FLB中相应数据
					this.workOrderIORecordMapper.updateFlbIOByWorkorderIO(workOrderIORecord.getId());
					// 保存历史记录信息
					commonRecService.write(workorder.getId(), TableEnum.B_S2_00801, user.getId(), 
							czls , operation, UpdateTypeEnum.UPDATE, workorder.getId());
					if(workOrderIORecord.getZsj() ==null){
						WorkOrderIORecord record = workOrderIORecordMapper.selectByPrimaryKey(workOrderIORecord.getId());
						if(null != record.getZsZjqdid() && !"".equals(record.getZsZjqdid())){
							installationListTempService.delete(record.getZsZjqdid());
						}
					}
				}
			}
			
		}
	}
	
	private void deleteNotExist(Workorder record,String czls, User user){
		// 插入日志
		StringBuilder sql = new StringBuilder("b.mainid = '"+record.getId()+"' ");
		if(null != record.getWoIORecordList() && !record.getWoIORecordList().isEmpty()){
			sql.append("and b.id not in (");
			for (WorkOrderIORecord disassembly : record.getWoIORecordList()) {
				sql.append("'").append(null==disassembly.getId() || "".equals(disassembly.getId())?"0":disassembly.getId()).append("',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(")");
		}
		commonRecService.writeByWhere(sql.toString(), TableEnum.B_S2_00801, user.getId(), record.getCzls(),
				LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, record.getId());
		//同步删除FLB中相应数据
		this.workOrderIORecordMapper.deleteFlbIOByWONotExist(record);
		// 删除数据
		workOrderIORecordMapper.deleteNotExist(record);
	}
	/**
	 * @Description 工单完工关闭
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param workorder 工单
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String doWGClose(Workorder workorder) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登录用户
		Date currentDate = commonService.getSysdate();//当前时间
		String czls = UUID.randomUUID().toString();//操作流水
		LogOperationEnum operation = LogOperationEnum.WANCHEN;//操作类型

		Workorder woObj = workorderMapper.selectByPrimaryKey(workorder.getId()); //WO信息

		//检查用户机型的权限
		/*List<String> jxList = new ArrayList<String>();
		jxList.add(workorder.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(),woObj.getDprtcode(), jxList);*/

		if(null == woObj){
			throw new BusinessException("该工单已更新，请刷新后再进行操作!");
		}
		
		/**如果是1(RTN)工单,判断是否可关闭*/
		if(null != woObj.getGdlx() && woObj.getGdlx() == WorkorderTypeEnum.RTN.getId()){
			this.checkRTNIsCanClose(woObj);
		}
		
		/**校验状态,防止重复提交*/
		this.validateResubmit(woObj,operation.getId()); //正常完成
		
		/**修改数据*/
		workorder.setWhdwid(user.getBmdm());//维护人单位ID
		workorder.setWhrid(user.getId());//维护人ID
		workorder.setWhsj(currentDate);//维护时间
		workorder.setGbrid(user.getId());//关闭人ID
		workorder.setGbrq(currentDate);//关闭人ID
		workorder.setWgbs(WhetherEnum.YES.getId());//完工标识：1已完工
		workorderMapper.updateByPrimaryKeySelective(workorder);
		commonRecService.write(workorder.getId(), TableEnum.B_S2_008, user.getId(), czls, operation, UpdateTypeEnum.CLOSE, workorder.getId());//保存历史记录信息
		//保存工作者
		workOrderWorkerService.save(workorder, czls, operation);
		//更改下达指令状态
		instructionsourceMapper.updateYwdjztByZlid(WorkorderStatusEnum.CLOSETOFINISH.getId(), workorder.getId());
		//拆装件,暂缺......
		doReplacementRecord(workorder,user,czls,operation);
		attachmentService.eidtAttachment(workorder.getAttachments(), workorder.getId(), czls, workorder.getId(), workorder.getDprtcode(), operation);//附件列表
		
		//根据工单同步修改FLB完成工作
		this.workorderMapper.updateFlbWorkByWorkorder(workorder.getId());
		//工单信息修改后 工作者同步更新FLB信息
		workOrderWorkerService.updateFlbWorkerByWorkorderWorker(workorder.getId());
		
		/**工单关闭逻辑**/
		try{
			this.processInstallList(workorder);
			this.monitorDataService.doWorkOrderFinish(workorder.getId());
		}catch(BusinessException e){
			throw new BusinessException("工单关闭失败:"+e.getMessage());
		}
		return workorder.getId();
	}

	/**
	 * @Description 检查RTN工单是否可以关闭
	 * 逻辑：
		        关闭时，判断，工单类型是不是等于1（RTN）
		        如果是1（RTN），那么根据b_s2_008表的来源任务id，到b_g2_012去确定是否是定检包，
		        如果是定检包，那么根据选择关闭的这条工单中的工单识别id，找b_s2_008表定检包工单识别id的记录是否都关闭（9,10）
		        如果有一条没关闭，工单将不能被关闭！
	 * @CreateTime 2017-10-20 下午2:08:13
	 * @CreateBy 雷伟
	 * @param woObj 工单135对象
	 * @throws BusinessException 
	 */
	private void checkRTNIsCanClose(Workorder woObj) throws BusinessException {
		MaintenanceProject wxxmObj = maintenanceProjectMapper.selectByPrimaryKey(woObj.getLyrwid()); //根据来源任务ID,找来维修项目
		if(null != wxxmObj && null != wxxmObj.getWxxmlx() && wxxmObj.getWxxmlx() == MaintenanceProjectTypeEnum.FIXEDPACKAGE.getId()){//判断是否是定检包
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.clear();
			paramMap.put("djbgdid", woObj.getGdsbid());
			List<Workorder> woList = workorderMapper.selectByParam(paramMap);
			if(null != woList){
				for(Workorder workorder : woList) {
					if(null != workorder.getZt() && 
							!(workorder.getZt() == WorkorderStatusEnum.CLOSETOEND.getId() ||workorder.getZt() == WorkorderStatusEnum.CLOSETOFINISH.getId())){
						throw new BusinessException("定检包下工单未全部关闭，不能关闭该工单!");
					}
				}
			}
		}
	}

	/**
	 * @Description 工单关闭,指定结束
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param workorder 工单
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String doZDClose(Workorder workorder) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登录用户
		Date currentDate = commonService.getSysdate();//当前时间
		String czls = UUID.randomUUID().toString();//操作流水
		LogOperationEnum operation = LogOperationEnum.GUANBI;//操作类型

		Workorder woObj = workorderMapper.selectByPrimaryKey(workorder.getId()); //WO信息

		//检查用户机型的权限
		/*List<String> jxList = new ArrayList<String>();
		jxList.add(workorder.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(),woObj.getDprtcode(), jxList);*/

		if(null == woObj){
			throw new BusinessException("该工单已更新，请刷新后再进行操作!");
		}
		
		/**如果是1(RTN)工单,判断是否可关闭*/
		if(null != woObj.getGdlx() && woObj.getGdlx() == WorkorderTypeEnum.RTN.getId()){
			this.checkRTNIsCanClose(woObj);
		}
		
		/**校验状态,防止重复提交*/
		this.validateResubmit(woObj,operation.getId()); //正常完成
		
		/**修改数据*/
		workorder.setWhdwid(user.getBmdm());//维护人单位ID
		workorder.setWhrid(user.getId());//维护人ID
		workorder.setWhsj(currentDate);//维护时间
		workorder.setGbrid(user.getId());//关闭人ID
		workorder.setGbrq(currentDate);//关闭人ID
//		workorder.setWgbs(WhetherEnum.NO.getId());//完工标识：1已完工
		workorderMapper.updateByPrimaryKeySelective(workorder);
		commonRecService.write(workorder.getId(), TableEnum.B_S2_008, user.getId(), czls, operation, UpdateTypeEnum.CLOSE, workorder.getId());//保存历史记录信息
		//更改下达指令状态
		instructionsourceMapper.updateYwdjztByZlid(WorkorderStatusEnum.CLOSETOEND.getId(), workorder.getId());
		//拆装件
//		doReplacementRecord(workorder,user,czls,operation);
		return workorder.getId();
	}
	
	/**
	 * @Description 飞行记录本修订 指定结束 工单
	 * @CreateTime 2017年11月27日 上午9:46:26
	 * @CreateBy 徐勇
	 * @param workOrderId 工单ID
	 */
	public void doZDClose4FLB(String workOrderId){
		Workorder workorder = new Workorder();
		User user = ThreadVarUtil.getUser();//当前登录用户
		workorder.setWhdwid(user.getBmdm());//维护人单位ID
		workorder.setWhrid(user.getId());//维护人ID
		Date currentDate = commonService.getSysdate();//当前时间
		workorder.setWhsj(currentDate);//维护时间
		workorder.setGbrid(user.getId());//关闭人ID
		workorder.setGbrq(currentDate);//关闭人ID
		workorder.setZt(WorkorderStatusEnum.CLOSETOEND.getId());
		workorderMapper.updateByPrimaryKeySelective(workorder);
		commonRecService.write(workorder.getId(), TableEnum.B_S2_008, user.getId(), UUID.randomUUID().toString(), LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE, workorder.getId());//保存历史记录信息
	}

	/**
	 * @Description 工单航材明细
	 * @CreateTime 2017年9月29日 下午2:21:37
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> getGDHCToolDetail(Workorder workorder) throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gdid", workorder.getId());
		map.put("dprtcode", workorder.getDprtcode());
		map.put("ckidList", null);
		List<Map<String, Object>>  hcList = workorderMapper.getGDHCToolDetail(map);
		map.clear();
		map.put("hcList", hcList);
		return map;
	}

	/**
	 * @Description 工单执行历史
	 * @CreateTime 2017年9月29日 下午2:21:37
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> getGDZxhistoryInfo(Workorder workorder) throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fjzch", workorder.getFjzch());
		map.put("jksjid", workorder.getJksjid());
		map.put("dprtcode", workorder.getDprtcode());
		List<Map<String, Object>>  exeList = workorderMapper.getGDZxhistoryInfo(map);
		map.clear();
		map.put("exeList", exeList);
		return map;
	}
	
	/**
	 * @Description 设置完成时限
	 * @CreateTime 2017-10-11 下午4:38:29
	 * @CreateBy 雷伟
	 * @param wo
	 */
	public Workorder getCompleteLimit(Workorder wo) {
		if(null != wo && null != wo.getJksjid()){
			MonitoringPlan obj = monitoringPlanMapper.getCompleteLimit(wo.getJksjid());
			wo.setMonitoringPlan(obj);
		}
		return wo;
	}
	
	/**
	 * @Description 查询工包明细(工单清单列表)
	 * @CreateTime 2017-10-17 下午9:10:03
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Workorder> 工单集合
	 */
	@Override
	public List<Workorder> queryWorkOrderList(Workorder workorder) {
		return workorderMapper.queryWorkOrderList(workorder);
	}
	
	/**
	 * @Description 据查询条件分页查询工包明细(工单清单列表)
	 * @CreateTime 2017-10-20 上午11:36:49
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return Map<String, Object> 页面数据
	 */
	@Override
	public Map<String, Object> queryAllPageWorkOrderList(Workorder workorder) {
		PageHelper.startPage(workorder.getPagination());
		List<Workorder> list = workorderMapper.queryWorkOrder135List(workorder);
		Map<String, Object> pack4PageHelper = PageUtil.pack4PageHelper(list, workorder.getPagination());
		pack4PageHelper.put("rows", workorderToMap(list));
		return pack4PageHelper;
	}
	
	/**
	 * @Description 维修监控对象转map
	 * @CreateTime 2018-4-11 上午9:58:54
	 * @CreateBy 刘兵
	 * @param list
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> workorderToMap(List<Workorder> list){
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (Workorder workorder : list) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", workorder.getId());
			resultMap.put("dprtcode", workorder.getDprtcode());
			resultMap.put("zt", workorder.getZt());
			resultMap.put("wgbs", workorder.getWgbs());
			resultMap.put("jksjid", workorder.getJksjid());
			resultMap.put("fjzch", workorder.getFjzch());
			resultMap.put("gdbh", workorder.getGdbh());
			resultMap.put("gdlx", workorder.getGdlx());
			resultMap.put("gdsbid", workorder.getGdsbid());
			resultMap.put("zjh", workorder.getZjh());
			resultMap.put("zy", workorder.getZy());
			resultMap.put("gzlb", workorder.getGzlb());
			resultMap.put("lyrwh", workorder.getLyrwh());
			resultMap.put("lyrwid", workorder.getLyrwid());
			resultMap.put("gdbt", workorder.getGdbt());
			resultMap.put("gkid", workorder.getGkid());
			resultMap.put("jhZd", workorder.getJhZd());
			resultMap.put("gkbh", workorder.getGkbh());
			resultMap.put("gznrfjid", workorder.getGznrfjid());
			resultMap.put("whsj", workorder.getWhsj());
			resultMap.put("gznrfjid", workorder.getGznrfjid());
			resultMap.put("paramsMap", workorder.getParamsMap());
			resultList.add(resultMap);
		}
		return resultList;
	}
	
	/**
	 * @Description 根据多个id查询工单
	 * @CreateTime 2017年12月12日 下午2:36:47
	 * @CreateBy 徐勇
	 * @param ids
	 * @return 
	 */
	public List<Workorder> queryByIds(List<String> ids){
		if(ids != null && ids.size() > 0){
			return this.workorderMapper.selectByIdList(ids);
		}
		return new ArrayList<Workorder>(0);
	}
	
	/**
	 * @Description FLB中撤销工单和工单拆装记录
	 * @CreateTime 2017年12月12日 下午2:14:59
	 * @CreateBy 徐勇
	 * @param workOrderIds 待撤销的flb工单id
	 * @param ioIds 待撤销的flb中手工拆装记录
	 * @param workOrderList flb涉及的所有工单
	 * @param installRemoveUndoList 补充的待撤销拆装，可为null
	 * @throws BusinessException
	 */
	public void doWOUndo4FLB(List<String> workOrderIds, List<String> ioIds, List<Workorder> workOrderList, List<InstallAndRemove> installRemoveUndoList) throws BusinessException{
		if(installRemoveUndoList == null){
			installRemoveUndoList = new ArrayList<InstallAndRemove>();//待撤销拆装记录
		}
		if(ioIds != null && ioIds.size() > 0){
			List<WorkOrderIORecord> ioList = this.workOrderIORecordMapper.selectByIds(ioIds);
			//当相应工单是完工关闭状态时，处理相应的拆装记录撤销
			for (WorkOrderIORecord workOrderIORecord : ioList) {
				for (Workorder workorder : workOrderList) {
					if(workorder.getId().equals(workOrderIORecord.getMainid()) && workorder.getZt() == WorkorderStatusEnum.CLOSETOFINISH.getId()){
						if(StringUtils.isNotBlank(workOrderIORecord.getCxZjqdid())){
							installRemoveUndoList.add(new InstallAndRemove(workOrderIORecord.getCxZjqdid(), workOrderIORecord.getCxSj(), InstalledStatusEnum.REMOVED.getId(), workOrderIORecord.getId(), workOrderIORecord.getCxWckcid(), workOrderIORecord.getCxKclvid()));
						}
						if(StringUtils.isNotBlank(workOrderIORecord.getZsZjqdid())){
							installRemoveUndoList.add(new InstallAndRemove(workOrderIORecord.getZsZjqdid(), workOrderIORecord.getZsSj(), InstalledStatusEnum.INSTALLED.getId(), workOrderIORecord.getId(), workOrderIORecord.getZsWckcid(), workOrderIORecord.getZsKclvid()));
						}
					}
				}
			}
		}
		if(installRemoveUndoList.size() > 0){
			this.doInstallRemoveUndo(installRemoveUndoList);
		}
		if(ioIds != null && ioIds.size() > 0){
			//删除相应的工单拆装数据
			this.workOrderIORecordMapper.deleteByIds(ioIds);
			//删除FLB中引用的相关数据
			this.workOrderIORecordMapper.deleteHandFlbIOByIds(ioIds);
		}
		
		if(workOrderIds != null && workOrderIds.size() > 0){
			//指定结束 需要撤销的flb工单
			User user = ThreadVarUtil.getUser();
			Date currentDate = commonService.getSysdate();//当前时间(取数据库时间)
			for (String workOrderId : workOrderIds) {
				Workorder workorder = new Workorder();
				workorder.setId(workOrderId);
				workorder.setWhdwid(user.getBmdm());//维护人单位ID
				workorder.setWhrid(user.getId());//维护人ID
				workorder.setWhsj(currentDate);//维护时间
				workorder.setGbrid(user.getId());//关闭人ID
				workorder.setGbrq(currentDate);//关闭人ID
				workorder.setZt(WorkorderStatusEnum.CLOSETOEND.getId());
				workorderMapper.updateByPrimaryKeySelective(workorder);
				commonRecService.write(workorder.getId(), TableEnum.B_S2_008, ThreadVarUtil.getUser().getId(), 
						UUID.randomUUID().toString() , LogOperationEnum.GUANBI, UpdateTypeEnum.SAVE, workorder.getId());
			}
		}
		
	}
	
	/**
	 * @Description FLB提交/修订 处理工单修订和拆装记录修订
	 * @CreateTime 2017年12月12日 下午3:57:23
	 * @CreateBy 徐勇
	 * @param flbWorkOrderUndoIds 待撤销的flb工单id
	 * @param workOrderIOUndoIds 待撤销的工单拆装记录id
	 * @param addWOList 待新增的flb工单
	 * @param editWOList 待修改的工单
	 * @param addIOList 待新增的工单拆装记录
	 * @param editIOList 待修改的工单拆装记录
	 * @param installRemoveUndoList 待修改的拆装记录涉及的 拆装撤销
	 * @param installRemoveList 待修改的拆装记录涉及的 拆装生效
	 * @param workOrderIds FLB涉及的所有工单id
	 * @throws BusinessException 
	 */
	public void doWOUpdate4FLB(List<String> flbWorkOrderUndoIds, List<String> workOrderIOUndoIds, 
			List<Workorder> addWOList, List<Workorder> editWOList, 
			List<WorkOrderIORecord> addIOList, List<WorkOrderIORecord> editIOList,
			List<InstallAndRemove> installRemoveUndoList, List<InstallAndRemove> installRemoveList, List<String> workOrderIds) throws BusinessException{
		
		String czls = UUID.randomUUID().toString();//操作流水
		Date currentDate = commonService.getSysdate();//当前时间(取数据库时间)
		
		//查询业务涉及的所有工单
		List<Workorder> woList = this.queryByIds(workOrderIds);
		
		if(installRemoveList == null){
			installRemoveList = new ArrayList<InstallAndRemove>();
		}
		if(installRemoveUndoList == null){
			installRemoveList = new ArrayList<InstallAndRemove>();
		}
		
		//处理新增的拆装记录
		this.processAddIOList(addIOList, woList, installRemoveList, czls);
		
		//处理拆装记录修改，当工单不是完工关闭，从待生效拆装集合中移除数据
		this.processEditIOList(editIOList, woList, installRemoveList, installRemoveUndoList, czls);
		
		//处理工单新增
		for (Workorder workorder : addWOList) {
			LogOperationEnum operation = LogOperationEnum.SAVE_WO;//确定状态
			this.saveWOMain(workorder,currentDate,czls,operation);//保存WO主表数据
		}
		
		//处理待修改工单
		List<Workorder> toModifyMonitorList = new ArrayList<Workorder>();
		if(editWOList != null && editWOList.size() > 0){
			for (Workorder workorder : editWOList) {
				//修改工单信息
				this.workorderMapper.updateByPrimaryKeySelective(workorder);
				//修改工作者
				workOrderWorkerService.save(workorder, czls, LogOperationEnum.EDIT);
				//工单信息修改后 同步更新FLB信息
				this.workorderMapper.updateFlbWorkByWorkorder(workorder.getId());
				//工单信息修改后 工作者同步更新FLB信息
				//workOrderWorkerService.updateFlbWorkerByWorkorderWorker(workorder.getId());
				
				Workorder wo = getExistsWO(workorder.getId(), woList);
				if(wo != null && wo.getZt() == WorkorderStatusEnum.CLOSETOFINISH.getId()){
					if(StringUtils.isNotBlank(wo.getJksjid())){
						if(workorder.getSjJsrq() == null){
							throw new BusinessException("工单"+wo.getGdbh()+"完工时间不能为空");
						}
						workorder.setJksjid(wo.getJksjid());
						toModifyMonitorList.add(workorder);
					}
					commonRecService.write(wo.getId(), TableEnum.B_S2_008, ThreadVarUtil.getUser().getId(), 
							czls , LogOperationEnum.REVISE, UpdateTypeEnum.UPDATE, wo.getId());
				}else{
					commonRecService.write(wo.getId(), TableEnum.B_S2_008, ThreadVarUtil.getUser().getId(), 
							czls , LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, wo.getId());
				}
			}
		}
		
		//处理flb工单、待撤销拆装记录
		this.doWOUndo4FLB(flbWorkOrderUndoIds, workOrderIOUndoIds, woList, installRemoveUndoList);
		
		//处理拆装生效到装机清单
		if(installRemoveList.size() > 0){
			this.doInstallRemove(installRemoveList);
		}
		
		for (Workorder workorder : toModifyMonitorList) {
			this.monitorDataService.doMOModifyJssj(workorder.getJksjid(), workorder.getSjJsrq());
		}
	}
	
	/**
	 * @Description FLB提交/修订------处理新增的拆装记录
	 * @CreateTime 2017年12月12日 下午4:30:39
	 * @CreateBy 徐勇
	 * @param addIOList 待新增的拆装记录
	 * @param woList flb涉及的所有工单
	 * @param installRemoveList 新增拆装记录，将要生效的拆装动作放入
	 * @param czls 操作流水
	 */
	private void processAddIOList(List<WorkOrderIORecord> addIOList, List<Workorder> woList, List<InstallAndRemove> installRemoveList, String czls){
		if(addIOList == null || addIOList.size() == 0){
			return;
		}
		User user = ThreadVarUtil.getUser();
		for (WorkOrderIORecord workOrderIORecord : addIOList){
			workOrderIORecordMapper.insertSelective(workOrderIORecord);
			Workorder wo = getExistsWO(workOrderIORecord.getMainid(), woList);
			if(wo == null){
				if(StringUtils.isNotBlank(workOrderIORecord.getCxZjqdid())){
					installRemoveList.add(new InstallAndRemove(workOrderIORecord.getCxZjqdid(), workOrderIORecord.getCxSj(), InstalledStatusEnum.REMOVED.getId(), workOrderIORecord.getId(), workOrderIORecord.getCxWckcid(), workOrderIORecord.getCxKclvid()));
				}
				
				if(StringUtils.isNotBlank(workOrderIORecord.getZsZjqdid())){
					installRemoveList.add(new InstallAndRemove(workOrderIORecord.getZsZjqdid(), workOrderIORecord.getZsSj(), InstalledStatusEnum.INSTALLED.getId(), workOrderIORecord.getId()));
				}
				commonRecService.write(workOrderIORecord.getId(), TableEnum.B_S2_00801, user.getId(), czls , LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, workOrderIORecord.getMainid());
			}else{
				if(wo.getZt() == WorkorderStatusEnum.CLOSETOFINISH.getId()){
					if(StringUtils.isNotBlank(workOrderIORecord.getCxZjqdid())){
						installRemoveList.add(new InstallAndRemove(workOrderIORecord.getCxZjqdid(), workOrderIORecord.getCxSj(), InstalledStatusEnum.REMOVED.getId(), workOrderIORecord.getId(), workOrderIORecord.getCxWckcid(), workOrderIORecord.getCxKclvid()));
					}
					if(StringUtils.isNotBlank(workOrderIORecord.getZsZjqdid())){
						installRemoveList.add(new InstallAndRemove(workOrderIORecord.getZsZjqdid(), workOrderIORecord.getZsSj(), InstalledStatusEnum.INSTALLED.getId(), workOrderIORecord.getId()));
					}
					commonRecService.write(workOrderIORecord.getId(), TableEnum.B_S2_00801, user.getId(), czls , LogOperationEnum.REVISE, UpdateTypeEnum.SAVE, workOrderIORecord.getMainid());
				}else{
					commonRecService.write(workOrderIORecord.getId(), TableEnum.B_S2_00801, user.getId(), czls , LogOperationEnum.EDIT, UpdateTypeEnum.SAVE, workOrderIORecord.getMainid());
				}
			}
		}
	}
	
	/**
	 * @Description FLB提交/修订------处理修改的拆装记录
	 * @CreateTime 2017年12月12日 下午4:34:30
	 * @CreateBy 徐勇
	 * @param editIOList 修改的记录
	 * @param woList flb涉及的所有工单
	 * @param czls 操作流水
	 */
	private void processEditIOList(List<WorkOrderIORecord>editIOList, List<Workorder> woList, List<InstallAndRemove> installRemoveList, List<InstallAndRemove> installRemoveUndoList, String czls){
		if(editIOList == null || editIOList.size() == 0){
			return;
		}
		User user = ThreadVarUtil.getUser();
		for (WorkOrderIORecord workOrderIORecord : editIOList){
			workOrderIORecordMapper.updateByWorkOrder(workOrderIORecord);
			//同步修改FLB中相应数据
			this.workOrderIORecordMapper.updateFlbIOByWorkorderIO(workOrderIORecord.getId());
			Workorder wo = getExistsWO(workOrderIORecord.getMainid(), woList);
			// 保存历史记录信息
			if(wo != null && wo.getZt() == WorkorderStatusEnum.CLOSETOFINISH.getId()){
				commonRecService.write(workOrderIORecord.getId(), TableEnum.B_S2_00801, user.getId(), czls , LogOperationEnum.REVISE, UpdateTypeEnum.UPDATE, workOrderIORecord.getMainid());
			}else{
				Iterator<InstallAndRemove> iter = installRemoveList.iterator();
		        while (iter.hasNext()) {
		        	InstallAndRemove item = iter.next();
		            if (item.getWorkIOId().equals(workOrderIORecord.getId())) {
		                iter.remove();
		            }
		        }
		        Iterator<InstallAndRemove> iter1 = installRemoveUndoList.iterator();
		        while (iter1.hasNext()) {
		        	InstallAndRemove item = iter1.next();
		            if (item.getWorkIOId().equals(workOrderIORecord.getId())) {
		                iter1.remove();
		            }
		        }
				commonRecService.write(workOrderIORecord.getId(), TableEnum.B_S2_00801, user.getId(), czls , LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, workOrderIORecord.getMainid());
			}
		}
	}
	
	
	/**
	 * @Description 获取集合中存在的工单
	 * @CreateTime 2017年12月12日 下午4:16:42
	 * @CreateBy 徐勇
	 * @param id
	 * @param woList
	 * @return
	 */
	private Workorder getExistsWO(String id, List<Workorder> woList){
		for (Workorder workorder : woList) {
			if(workorder.getId().equals(id)){
				return workorder;
			}
		}
		return null;
	}
	
	/**
	 * @Description 处理拆装件同步到装机清单
	 * @CreateTime 2017年11月20日 上午10:18:31
	 * @CreateBy 徐勇
	 * @param id 工单ID
	 * @throws BusinessException
	 */
	private void processInstallList(Workorder workorder) throws BusinessException{
		List<WorkOrderIORecord> ioList = this.workOrderIORecordMapper.selectWithCxZsByMainid(workorder.getId());
		List<InstallAndRemove> installAndRemoveList = new ArrayList<InstallAndRemove>();
		for (WorkOrderIORecord workOrderIORecord : ioList) {
			if(StringUtils.isNotBlank(workOrderIORecord.getCxZjqdid())){
				installAndRemoveList.add(new InstallAndRemove(workOrderIORecord.getCxZjqdid(), workOrderIORecord.getCxSj(), InstalledStatusEnum.REMOVED.getId(), workOrderIORecord.getId()));
			}
			if(StringUtils.isNotBlank(workOrderIORecord.getZsZjqdid())){
				installAndRemoveList.add(new InstallAndRemove(workOrderIORecord.getZsZjqdid(), workOrderIORecord.getZsSj(), InstalledStatusEnum.INSTALLED.getId(), workOrderIORecord.getId()));
			}
		}
		if(installAndRemoveList.size() > 0){
			this.doInstallRemove(workorder.getId(), installAndRemoveList);
		}
	}
	
	
	/**
	 * 
	 * @Description 移除工包中的工单
	 * @CreateTime 2017年11月15日 上午9:35:51
	 * @CreateBy 岳彬彬
	 * @param id
	 */
	@Override
	public void update4RemoveWO(String id) {
		workorderMapper.update4RemoveWO(id);		
	}

	/**
	 * 
	 * @Description 工单修订
	 * @CreateTime 2017-11-24 下午3:21:14
	 * @CreateBy 孙霁
	 * @param workorder
	 */
	@Override
	public String doWGRevision(Workorder workorder)throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登录用户
		Date currentDate = commonService.getSysdate();//当前时间
		String czls = UUID.randomUUID().toString();//操作流水
		LogOperationEnum operation = LogOperationEnum.EDIT;//操作类型

		Workorder woObj = workorderMapper.selectByPrimaryKey(workorder.getId()); //WO信息

		/**校验状态,防止重复*/
		this.validateResubmit(woObj,LogOperationEnum.REVISE.getId());
		operation = LogOperationEnum.REVISE;
		/**修改数据*/
		this.updateWO4Others(workorder,currentDate,czls,operation);//修改WO关联表数据
		this.updateWOMain(workorder,currentDate,czls,operation);//修改WO主表数据(需先更新关联表，再更新主表，因为要会写工作内容附件ID)
		//根据工单同步修改FLB完成工作
		this.workorderMapper.updateFlbWorkByWorkorder(workorder.getId());
		//工单信息修改后 工作者同步更新FLB信息
		workOrderWorkerService.updateFlbWorkerByWorkorderWorker(workorder.getId());
		
		//保存工作者
		workOrderWorkerService.save(workorder, czls, operation);
				
		//查询原工单的拆装件
		List<WorkOrderIORecord> oldRecordList = this.workOrderIORecordMapper.selectWithCxZsByMainid(workorder.getId());
		
		//拆装件更新
		this.doReplacementRecord(workorder,user,czls,operation);
		
		List<WorkOrderIORecord> newRecordList = workorder.getWoIORecordList();
		//比对原拆装件和现拆装件
		List<InstallAndRemove> installedRemovedList = new ArrayList<InstallAndRemove>();//待生效的装机清单
		List<InstallAndRemove> installedRemovedUndoList = new ArrayList<InstallAndRemove>();//待撤销的装机清单
		for (WorkOrderIORecord record : oldRecordList) {
			if(!isExistsIORecord(record.getId(), newRecordList)){
				if(StringUtils.isNotBlank(record.getCxZjqdid())){
					installedRemovedUndoList.add(new InstallAndRemove(record.getCxZjqdid(), record.getCxSj(), InstalledStatusEnum.REMOVED.getId(), record.getId(), record.getCxWckcid(), record.getCxKclvid()));
				}
				if(StringUtils.isNotBlank(record.getZsZjqdid())){
					installedRemovedUndoList.add(new InstallAndRemove(record.getZsZjqdid(), record.getZsSj(), InstalledStatusEnum.INSTALLED.getId(), record.getId(), record.getZsWckcid(), record.getZsKclvid()));
				}
			}
		}
		if(newRecordList != null){
			for (WorkOrderIORecord record : newRecordList) {
				if(!isExistsIORecord(record.getId(), oldRecordList)){//新增拆装记录
					if(record.getZsj()!=null){
						record.setZsZjqdid(record.getZsj().getId());
						installedRemovedList.add(new InstallAndRemove(record.getZsZjqdid(), record.getZsSj(), InstalledStatusEnum.INSTALLED.getId(), record.getId(), record.getZsWckcid(), record.getZsKclvid()));
					}
					if(StringUtils.isNotBlank(record.getCxZjqdid())){
						installedRemovedList.add(new InstallAndRemove(record.getCxZjqdid(), record.getCxSj(), InstalledStatusEnum.REMOVED.getId(), record.getId(), record.getCxWckcid(), record.getCxKclvid()));
					}
				}else{//判断是否有修改
					for (WorkOrderIORecord oldRecord : oldRecordList) {
						if(oldRecord.getId().equals(record.getId())){
							//拆下是否修改
							if(StringUtils.isNotBlank(oldRecord.getCxZjqdid()) && !oldRecord.getCxZjqdid().equals(record.getCxZjqdid())){
								installedRemovedUndoList.add(new InstallAndRemove(oldRecord.getCxZjqdid(), oldRecord.getCxSj(), InstalledStatusEnum.REMOVED.getId(), oldRecord.getId(), oldRecord.getCxWckcid(), oldRecord.getCxKclvid()));
							}
							if(StringUtils.isNotBlank(record.getCxZjqdid()) ){
								if(!record.getCxZjqdid().equals(oldRecord.getCxZjqdid())){
									installedRemovedList.add(new InstallAndRemove(record.getCxZjqdid(), record.getCxSj(), InstalledStatusEnum.REMOVED.getId(), record.getId(), record.getCxWckcid(), record.getCxKclvid()));
								}else{
									installedRemovedList.add(new InstallAndRemove(record.getCxZjqdid(), record.getCxSj(), InstalledStatusEnum.REMOVED.getId(), true, record.getId(), record.getCxWckcid(), record.getCxKclvid()));
								}
							}
							
							if(record.getZsj()!=null){
								record.setZsZjqdid(record.getZsj().getId());
							}
							//装上是否有修改
							if(StringUtils.isNotBlank(oldRecord.getZsZjqdid()) && !oldRecord.getZsZjqdid().equals(record.getZsZjqdid())){
								installedRemovedUndoList.add(new InstallAndRemove(oldRecord.getZsZjqdid(), oldRecord.getZsSj(), InstalledStatusEnum.INSTALLED.getId(), oldRecord.getId(), oldRecord.getZsWckcid(), oldRecord.getZsKclvid()));
							}
							if(StringUtils.isNotBlank(record.getZsZjqdid())){
								if(!record.getZsZjqdid().equals(oldRecord.getZsZjqdid())){
									installedRemovedList.add(new InstallAndRemove(record.getZsZjqdid(), record.getZsSj(), InstalledStatusEnum.INSTALLED.getId(), record.getId(), record.getZsWckcid(), record.getZsKclvid()));
								}else{
									installedRemovedList.add(new InstallAndRemove(record.getZsZjqdid(), record.getZsSj(), InstalledStatusEnum.INSTALLED.getId(), true, record.getId(), record.getZsWckcid(), record.getZsKclvid()));
								}
							}
							break;
						}
					}
				}
			}
		}
		
		//处理删除/修改数据的拆装件的撤销
		if(installedRemovedUndoList.size() > 0){
			this.doInstallRemoveUndo(installedRemovedUndoList);
		}
		//处理新增/修改拆装件的生效
		if(installedRemovedList.size() > 0){
			this.doInstallRemove(workorder.getId(), installedRemovedList);
		}
		
		//判断工单完工时间 是否发生变化
		if(StringUtils.isNotBlank(woObj.getJksjid())){
			//工单完工时间发生变化，需要处理工单对应的监控数据
			this.monitorDataService.doMOModifyJssj(woObj.getJksjid(), workorder.getSjJsrq());
		}
		
		return workorder.getId();
	}
	
	/**
	 * @Description 是否存在该拆装记录
	 * @CreateTime 2017年11月28日 下午2:13:30
	 * @CreateBy 徐勇
	 * @param id
	 * @param recordList
	 * @return
	 */
	private boolean isExistsIORecord(String id, List<WorkOrderIORecord> recordList){
		if(recordList == null){
			return false;
		}
		for (WorkOrderIORecord record : recordList) {
			if(id.equals(record.getId())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @Description 处理装上拆下件的撤销
	 * @CreateTime 2017年11月27日 下午2:56:28
	 * @CreateBy 徐勇
	 * @param installedRemovedList 待撤销的拆装记录
	 * @throws BusinessException 
	 */
	private void doInstallRemoveUndo( List<InstallAndRemove> installedRemovedList) throws BusinessException{
		//排序，按拆装时间降序，先装后拆
		Collections.sort(installedRemovedList, new Comparator<InstallAndRemove>() {
			public int compare(InstallAndRemove a, InstallAndRemove b) {
				int timeCompare = b.getOperTime() == null ? -1 : b.getOperTime().compareTo(a.getOperTime());
				if (timeCompare == 0) {
					return a.getInstalledStatus().compareTo(b.getInstalledStatus());
				}
				return timeCompare;
			}
		});
		for (InstallAndRemove installAndRemove : installedRemovedList) {
			if(installAndRemove.getInstalledStatus() == InstalledStatusEnum.INSTALLED.getId()){
				//装上件处理 撤销
				this.installationListEffectService.doInstalledUndo(installAndRemove.getId(), installAndRemove.getId(), installAndRemove.getKcid(), installAndRemove.getKcllid());
			}else{
				//拆下件处理 撤销
				//查询拆下件不带拆下时间的部件使用
				ComponentUse componentUse = this.componentUseMapper.selectByZjqdidWithoutCxsj(installAndRemove.getId());
				this.installationListEffectService.doRemovedUndo(installAndRemove.getId(), installAndRemove.getId(), installAndRemove.getKcid(), installAndRemove.getKcllid(), componentUse);
			}
		}
	}
	
	/**
	 * @Description 处理装上拆下件的生效
	 * @CreateTime 2017年11月27日 下午2:58:56
	 * @CreateBy 徐勇
	 * @param workOrderId 工单id
	 * @param installedRemovedList 待生效的拆装记录
	 * @throws BusinessException 
	 */
	private void doInstallRemove(String workOrderId, List<InstallAndRemove> installedRemovedList) throws BusinessException{
		//排序，按拆装时间升序，先拆后装
		Collections.sort(installedRemovedList, new Comparator<InstallAndRemove>() {
			public int compare(InstallAndRemove a, InstallAndRemove b) {
				int timeCompare = a.getOperTime() == null ? -1 : a.getOperTime().compareTo(b.getOperTime());
				if (timeCompare == 0) {
					return b.getInstalledStatus().compareTo(a.getInstalledStatus());
				}
				return timeCompare;
			}
		});
		
		//获取工单拆下件的在机使用量
		List<ComponentUse> cxjComponentUseList = this.componentUseMapper.selectWorkOrderCompnentUsageList(workOrderId);
		Map<String, ComponentUse> cxjCompnentUsageMap = new HashMap<String, ComponentUse>(cxjComponentUseList.size());
		for (ComponentUse compnentUsage : cxjComponentUseList) {
			cxjCompnentUsageMap.put(compnentUsage.getZjqdid(), compnentUsage);
		}
		
		//获取工单装上件的在机使用量
		List<ComponentUse> zsjComponentUseList = this.componentUseMapper.selectWorkOrderZsjCompnentUsageList(workOrderId);
		Map<String, ComponentUse> zsjCompnentUsageMap = new HashMap<String, ComponentUse>(zsjComponentUseList.size());
		for (ComponentUse compnentUsage : zsjComponentUseList) {
			zsjCompnentUsageMap.put(compnentUsage.getZjqdid(), compnentUsage);
		}
		
		//查询装上件
		List<InstallationListEditable> installList = this.installationListEditableMapper.selectWorkOrderZsj(workOrderId);
		Map<String, InstallationListEditable> installMap = new HashMap<String, InstallationListEditable>(installList.size());
		for (InstallationListEditable installationListEditable : installList) {
			installMap.put(installationListEditable.getId(), installationListEditable);
		}
		//查询拆下件
		List<InstallationListEditable> removeList = this.installationListEditableMapper.selectWorkOrderCxj(workOrderId);
		Map<String, InstallationListEditable> removeMap = new HashMap<String, InstallationListEditable>(installList.size());
		for (InstallationListEditable installationListEditable : removeList) {
			removeMap.put(installationListEditable.getId(), installationListEditable);
		}
		
		for (InstallAndRemove installAndRemove : installedRemovedList) {
			if(installAndRemove.isEdit()){
				if(installAndRemove.getInstalledStatus() == InstalledStatusEnum.INSTALLED.getId()){
					//装上件处理
					if(installMap.containsKey(installAndRemove.getId())){
						this.installationListEffectService.doInstalledEffectFromTemp4Edit(installMap.get(installAndRemove.getId()), zsjCompnentUsageMap.get(installAndRemove.getId()));
					}
				}else{
					//拆下件处理
					if(removeMap.containsKey(installAndRemove.getId())){
						this.installationListEffectService.doRemovedEdit(removeMap.get(installAndRemove.getId()), cxjCompnentUsageMap.get(installAndRemove.getId()));
					}
				}
			}else{
				if(installAndRemove.getInstalledStatus() == InstalledStatusEnum.INSTALLED.getId()){
					//装上件处理
					if(installMap.containsKey(installAndRemove.getId())){
						this.installationListEffectService.doInstalledEffectFromTemp(installMap.get(installAndRemove.getId()), zsjCompnentUsageMap.get(installAndRemove.getId()), installAndRemove.getWorkIOId());
					}
				}else{
					//拆下件处理
					if(removeMap.containsKey(installAndRemove.getId())){
						this.installationListEffectService.doRemovedEffect(removeMap.get(installAndRemove.getId()), cxjCompnentUsageMap.get(installAndRemove.getId()), installAndRemove.getWorkIOId());
					}
				}
			}
		}
	}
	
	/**
	 * @Description 处理装上拆下件的生效
	 * @CreateTime 2017年11月27日 下午2:58:56
	 * @CreateBy 徐勇
	 * @param workOrderId 工单id
	 * @param installedRemovedList 待生效的拆装记录
	 * @throws BusinessException 
	 */
	private void doInstallRemove(List<InstallAndRemove> installedRemovedList) throws BusinessException{
		//排序，按拆装时间升序，先拆后装
		Collections.sort(installedRemovedList, new Comparator<InstallAndRemove>() {
			public int compare(InstallAndRemove a, InstallAndRemove b) {
				int timeCompare = a.getOperTime() == null ? -1 : a.getOperTime().compareTo(b.getOperTime());
				if (timeCompare == 0) {
					return b.getInstalledStatus().compareTo(a.getInstalledStatus());
				}
				return timeCompare;
			}
		});
		
		List<String> ioIdList = new ArrayList<String>();
		for (InstallAndRemove installAndRemove : installedRemovedList) {
			ioIdList.add(installAndRemove.getWorkIOId());
		}
		
		
		//获取工单拆下件的在机使用量
		List<ComponentUse> cxjComponentUseList = this.componentUseMapper.selectWorkOrderIOCompnentUsageList(ioIdList);
		Map<String, ComponentUse> cxjCompnentUsageMap = new HashMap<String, ComponentUse>(cxjComponentUseList.size());
		for (ComponentUse compnentUsage : cxjComponentUseList) {
			cxjCompnentUsageMap.put(compnentUsage.getZjqdid(), compnentUsage);
		}
		
		//获取工单装上件的在机使用量
		List<ComponentUse> zsjComponentUseList = this.componentUseMapper.selectWorkOrderIOZsjCompnentUsageList(ioIdList);
		Map<String, ComponentUse> zsjCompnentUsageMap = new HashMap<String, ComponentUse>(zsjComponentUseList.size());
		for (ComponentUse compnentUsage : zsjComponentUseList) {
			zsjCompnentUsageMap.put(compnentUsage.getZjqdid(), compnentUsage);
		}
		
		//查询装上件
		List<InstallationListEditable> installList = this.installationListEditableMapper.selectWorkOrderIOZsj(ioIdList);
		Map<String, InstallationListEditable> installMap = new HashMap<String, InstallationListEditable>(installList.size());
		for (InstallationListEditable installationListEditable : installList) {
			installMap.put(installationListEditable.getId(), installationListEditable);
		}
		//查询拆下件
		List<InstallationListEditable> removeList = this.installationListEditableMapper.selectWorkOrderIOCxj(ioIdList);
		Map<String, InstallationListEditable> removeMap = new HashMap<String, InstallationListEditable>(installList.size());
		for (InstallationListEditable installationListEditable : removeList) {
			removeMap.put(installationListEditable.getId(), installationListEditable);
		}
		
		for (InstallAndRemove installAndRemove : installedRemovedList) {
			if(installAndRemove.isEdit()){
				if(installAndRemove.getInstalledStatus() == InstalledStatusEnum.INSTALLED.getId()){
					//装上件处理
					this.installationListEffectService.doInstalledEffectFromTemp4Edit(installMap.get(installAndRemove.getId()), zsjCompnentUsageMap.get(installAndRemove.getId()));
				}else{
					//拆下件处理
					this.installationListEffectService.doRemovedEdit(removeMap.get(installAndRemove.getId()), cxjCompnentUsageMap.get(installAndRemove.getId()));
				}
			}else{
				if(installAndRemove.getInstalledStatus() == InstalledStatusEnum.INSTALLED.getId()){
					//装上件处理
					this.installationListEffectService.doInstalledEffectFromTemp(installMap.get(installAndRemove.getId()), zsjCompnentUsageMap.get(installAndRemove.getId()), installAndRemove.getWorkIOId());
				}else{
					//拆下件处理
					this.installationListEffectService.doRemovedEffect(removeMap.get(installAndRemove.getId()), cxjCompnentUsageMap.get(installAndRemove.getId()), installAndRemove.getWorkIOId());
				}
			}
		}
	}
	
	/**
	 * @Description 根据工单识别id查询工单列表
	 * @CreateTime 2017-11-25 下午2:03:39
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Workorder> 工单集合
	 */
	@Override
	public List<Workorder> queryByGdsbid(Workorder workorder){
		return workorderMapper.queryByGdsbid(workorder);
	}
	
	/**
	 * @Description 查询工包明细维修计划135(工单数，已反馈，已关闭)数量
	 * @CreateTime 2017-11-27 下午3:01:33
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return Map<String, Object> 工单数量集合
	 */
	@Override
	public Map<String, Object> queryCount4WorkOrder(Workorder workorder) {
		return workorderMapper.queryCount4WorkOrder(workorder);
	}
	
	/**
	 * 
	 * @Description 验证该工单是否能下发
	 * @CreateTime 2017年12月12日 下午2:42:08
	 * @CreateBy 岳彬彬
	 * @param id
	 * @throws BusinessException
	 */
	@Override
	public void doValidation4Exist(String id) throws BusinessException {
		//1、验证状态
		Workorder wo = workorderMapper.selectByPrimaryKey(id);
		if(WorkorderStatusEnum.SUBMIT.getId()!= wo.getZt()){
			throw new BusinessException("该工单已更新，请刷新后再进行操作!");
		}
		//2、验证是否存在b_s2_009预组包中
		int count =monitoringWorkpackageMapper.getCountByJksjgdid(id);
		if(count>0){
			throw new BusinessException("该工单已更新，请刷新后再进行操作!");
		}
	}
	/**
	 * 
	 * @Description MCC/其他工单下发,选择已下发的工包
	 * @CreateTime 2017年12月13日 上午10:33:05
	 * @CreateBy 岳彬彬
	 * @param workorder
	 */
	@Override
	public void doIssued(Workorder workorder) {
		if(null != workorder.getGbid() && !"".equals(workorder.getGbid())){
			//修改工单，将工包id写入到工单中
			workorderMapper.updateByPrimaryKeySelective(workorder);
			List<String> list = new ArrayList<String>();
			list.add(workorder.getId());
			//下发
			workpackageService.doAutoIssuedWorkorder(workorder.getGbid(), list);
		}	
	}
	
	/**
	 * 
	 * @Description 处理MCC/其他工单下发，工包的新增
	 * @CreateTime 2017年12月13日 上午10:44:36
	 * @CreateBy 岳彬彬
	 * @param workpackage
	 * @throws BusinessException 
	 */
	@Override
	public void doWoIssued(Workpackage workpackage) throws BusinessException {
		//新增工包
		String gbid = workpackageService.addRecord(workpackage);
		//修改工单，将工包id写入到工单中
		Workorder wo = new Workorder();
		wo.setId((String) workpackage.getParamsMap().get("woId"));
		wo.setGbid(gbid);
		workorderMapper.updateByPrimaryKeySelective(wo);
		//下发工包
		workpackageService.update4Issued(workpackage);
	}
	
	/**
	 * @Description 据查询条件分页查询工包明细(预组包工单清单列表)(导出)
	 * @CreateTime 2017-12-22 下午1:09:33
	 * @CreateBy 刘兵
	 * @param paramObj 当前工单参数
	 * @return List<Workorder>
	 */
	@Override
	public List<Workorder> exportExcelYzbWO(Workorder paramObj) {
		List<Workorder> list = workorderMapper.queryWorkOrderList(paramObj);
		for (Workorder workorder : list) {
			String lyfl = WorkorderTypeEnum.getName(workorder.getGdlx());
			if(1 == workorder.getGdlx() && null != workorder.getParamsMap().get("gdzlx")){
				lyfl = MaintenanceProjectTypeEnum.getName(Integer.valueOf(workorder.getParamsMap().get("gdzlx").toString()));
			}
			workorder.getParamsMap().put("lyfl", lyfl);
			formatFinlishTime(workorder);
		}
		return list;
	}
	
	/**
	 * @Description 据查询条件分页查询工包明细(工单清单列表)(导出)
	 * @CreateTime 2017-12-22 下午1:09:33
	 * @CreateBy 刘兵
	 * @param paramObj 当前工单参数
	 * @return List<Workorder>
	 */
	@Override
	public List<Workorder> exportExcelWO(Workorder paramObj) {
		List<Workorder> list = workorderMapper.queryWorkOrder135List(paramObj);
		List<Workorder> workorderList = new ArrayList<Workorder>();//工单集合
		Map<String, List<Workorder>> childrenMap = new HashMap<String, List<Workorder>>();//定检包下的子工单集合
		for (Workorder workorder : list) {
			String lyfl = WorkorderTypeEnum.getName(workorder.getGdlx());
			if(1 == workorder.getGdlx() && null != workorder.getParamsMap().get("gdzlx")){
				lyfl = MaintenanceProjectTypeEnum.getName(Integer.valueOf(workorder.getParamsMap().get("gdzlx").toString()));
			}
			workorder.getParamsMap().put("lyfl", lyfl);
			formatFinlishTime(workorder);
			//如果是定检包下的工单,则加入子工单集合,否则加入返回的工单集合
			if(null != workorder.getDjbgdid()){
				List<Workorder> chlidrenList = childrenMap.get(workorder.getDjbgdid());
				if(null == chlidrenList){
					chlidrenList = new ArrayList<Workorder>();
					childrenMap.put(workorder.getDjbgdid(), chlidrenList);
				}
				chlidrenList.add(workorder);
			}else{
				workorderList.add(workorder);
			}
		}
		if(childrenMap.size() > 0){
			workorderList = addWorkOrderList(workorderList, childrenMap);
		}
		return workorderList;
	}
	
	/**
	 * @Description 添加子工单集合
	 * @CreateTime 2017-12-22 下午3:12:31
	 * @CreateBy 刘兵
	 * @param fList 工单集合
	 * @param childrenMap 子工单集合
	 */
	private List<Workorder> addWorkOrderList(List<Workorder> fList, Map<String, List<Workorder>> childrenMap){
		List<Workorder> workorderList = new ArrayList<Workorder>();//工单集合
		for (Workorder workorder : fList) {
			workorderList.add(workorder);
			if(null != childrenMap.get(workorder.getGdsbid())){
				workorderList.addAll(childrenMap.get(workorder.getGdsbid()));
			}
		}
		return workorderList;
	}
	
	/**
	 * @Description 格式化完成时限
	 * @CreateTime 2017-12-19 下午4:13:28
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @throws BusinessException
	 */
	private void formatFinlishTime(Workorder workorder){
		if(null != workorder.getParamsMap().get("jhsjsj")){
			StringBuffer wcsx = new StringBuffer();
			String[] arr1 = String.valueOf(workorder.getParamsMap().get("jhsjsj")).split(",");
			for (int i  = 0 ; i < arr1.length ; i++) {
				String[] arr2 = arr1[i].split("#_#", -1);
				wcsx.append(formatJkz(arr2[0], arr2[1], " ", "", true));
				if(i != arr1.length - 1){
					wcsx.append("/");
				}
			}
			workorder.getParamsMap().put("wcsx", wcsx);
		}
	}
	
	/**
	 * @Description 格式化监控值
	 * @CreateTime 2017-12-20 上午9:50:09
	 * @CreateBy 刘兵
	 * @param jklbh 监控类编号
	 * @param value 监控值
	 * @param backupValue 备用值
	 * @param dw 单位
	 * @param addDw 是否增加单位
	 * @return 监控值
	 */
	private String formatJkz(String jklbh, String value, String backupValue, String dw, boolean addDw){
		StringBuffer jkz = new StringBuffer();
		if(StringUtils.isNotBlank(value)){
			//判断是否是时间格式
			if(MonitorProjectEnum.isTime(jklbh)){
				value = StringAndDate_Util.convertToHour(value);
			}
		}else{
			value = backupValue;
		}
		jkz.append(value);
		if(addDw && !" ".equals(value)){
			jkz.append(MonitorProjectEnum.getUnit(jklbh, dw));
		}
		return jkz.toString();
	}

}
