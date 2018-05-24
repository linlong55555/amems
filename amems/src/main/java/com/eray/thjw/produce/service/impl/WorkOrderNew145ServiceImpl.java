package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.baseinfo.dao.ProjectMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.AircraftinfoMapper;
import com.eray.thjw.produce.dao.WorkOrderIORecordMapper;
import com.eray.thjw.produce.dao.Workorder145IORecordMapper;
import com.eray.thjw.produce.dao.Workorder145Mapper;
import com.eray.thjw.produce.dao.WorkorderMapper;
import com.eray.thjw.produce.dao.Workpackage145Mapper;
import com.eray.thjw.produce.dao.WorkpackageMapper;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.po.Workorder145;
import com.eray.thjw.produce.po.Workorder145IORecord;
import com.eray.thjw.produce.po.Workpackage;
import com.eray.thjw.produce.po.Workpackage145;
import com.eray.thjw.produce.service.InstallationListTempService;
import com.eray.thjw.produce.service.WorkOrder145WorkerService;
import com.eray.thjw.produce.service.WorkOrderNew145Service;
import com.eray.thjw.produce.service.WorkOrderWorkerService;
import com.eray.thjw.produce.service.WorkorderProcessHoursService;
import com.eray.thjw.project2.dao.MaintenanceProjectMapper;
import com.eray.thjw.project2.dao.ReferenceMapper;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.service.CoverPlateService;
import com.eray.thjw.project2.service.MaterialToolService;
import com.eray.thjw.project2.service.ReferenceService;
import com.eray.thjw.project2.service.WorkContentService;
import com.eray.thjw.project2.service.WorkHourService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.system.service.SaiBongGzService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.common.WhetherEnum;
import enu.produce.RetentionTypeEnum;
import enu.produce.WorkorderDXFBSEnum;
import enu.produce.WorkorderStatusEnum;
import enu.produce.WorkorderTypeEnum;
import enu.produce.WorkpackageStatusEnum;
import enu.project2.EngineeringOrderStatusEnum;
import enu.project2.MaintenanceProjectTypeEnum;
import enu.project2.ProjectBusinessEnum;

/**
 * 
 * @Description 工单serviceimpl
 * @CreateTime 2017年10月10日 上午9:30:18
 * @CreateBy 林龙
 */
@Service
public class WorkOrderNew145ServiceImpl implements WorkOrderNew145Service {

	@Resource
	private Workpackage145Mapper workpackage145Mapper;
	@Resource
	private Workorder145Mapper workorder145Mapper;
	@Resource
	private WorkorderMapper workorderMapper;
	@Resource
	private AttachmentService attachmentService;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private CommonService commonService;
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
	private WorkorderProcessHoursService workorderProcessHoursService;
	@Resource
	private ReferenceMapper referenceMapper;
	@Resource
	private Workorder145IORecordMapper workorder145IORecordMapper;
	@Resource
	private MaintenanceProjectMapper maintenanceProjectMapper;
	@Resource
	private InstallationListTempService installationListTempService;
	@Resource
	private WorkOrderIORecordMapper workOrderIORecordMapper;
	@Resource
	private AttachmentMapper attachmentMapper;
//	@Resource
//	private SaibongUtilService saibongUtilService;
	@Resource
	private ProjectMapper projectMapper;
	@Resource
	private SaiBongGzService saiBongGzService;
	@Resource
	private WorkpackageMapper workpackageMapper;
	@Resource
	private WorkOrderWorkerService workOrderWorkerService;
	@Resource
	private WorkOrder145WorkerService workOrder145WorkerService;
	@Resource
	private AircraftinfoMapper aircraftinfoMapper;
	/**
	 * 
	 * @Description 工单列表加载
	 * @CreateTime 2017年10月9日 下午9:10:43
	 * @CreateBy 林龙
	 * @param workorder 工单
	 * @return
	 */
	@Override
	public Map<String, Object> queryAllList(Workorder145 workorder)throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = workorder.getId();
		workorder.setId(""); //跑到第一行
		User user = ThreadVarUtil.getUser();//当前登陆人
		workorder.getParamsMap().put("userId", user.getId());
		workorder.getParamsMap().put("userType", user.getUserType());//验证飞机注册号权限权限
		PageHelper.startPage(workorder.getPagination());
		List<Workorder145> eoList = workorder145Mapper.queryAllList(workorder);
		
		if (((Page) eoList).getTotal() > 0) {
			// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
			if (StringUtils.isNotBlank(id)) {
				// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
				if (!PageUtil.hasRecord(eoList, id)) {
					// 在查询条件中增加ID
					Workorder145 newRecord = new Workorder145();
					newRecord.setId(id);
					List<Workorder145> newRecordList = workorder145Mapper.queryAllList(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						eoList.add(0, newRecordList.get(0));
					}
				}
			}
			resultMap = PageUtil.pack4PageHelper(eoList, workorder.getPagination());
			return resultMap;

		} else {
			List<Workorder145> newRecordList = new ArrayList<Workorder145>();
			if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				// 在查询条件中增加ID
				Workorder145 newRecord = new Workorder145();
				newRecord.setId(id);
				newRecordList = workorder145Mapper.queryAllList(newRecord);
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
	 * 
	 * @Description 根据id查询工单信息
	 * @CreateTime 2017年10月10日 上午9:49:26
	 * @CreateBy 林龙
	 * @param gdid 工单id
	 */
	@Override
	public Workorder145 selectByPrimaryKey(String gdid) {
		return workorder145Mapper.selectByPrimaryKey(gdid);
	}

	@Override
	public void updateWOMain(Workorder145 workorder145, Date currentDate,String czls, LogOperationEnum operation) {
		User user = ThreadVarUtil.getUser();//当前登录用户
		workorder145.setWhdwid(user.getBmdm());//维护人单位ID
		workorder145.setWhrid(user.getId());//维护人ID
		workorder145.setWhsj(currentDate);//维护时间
		
		workorder145.setZdbmid(user.getBmdm());//制单部门ID
		workorder145.setZdrid(user.getId());//制单人id
		workorder145Mapper.updateByPrimaryKeySelective(workorder145);
		
		//保存历史记录信息
		commonRecService.write(workorder145.getId(), TableEnum.B_S2_014, user.getId(), czls, operation, UpdateTypeEnum.UPDATE, workorder145.getId());
		
	}
	/**
	 * 
	 * @Description 工包明细中工单附件处理
	 * @CreateTime 2017年10月20日 上午10:53:38
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public void doAttachment(Workorder145 record) throws BusinessException{
		String czls = UUID.randomUUID().toString();
		attachmentService.eidtAttachment(record.getAttachments(), record.getId(), czls, record.getId(),
				record.getDprtcode(), LogOperationEnum.EDIT);
	}
	/**
	 * 
	 * @Description 选择工单
	 * @CreateTime 2017年10月20日 上午11:05:48
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> getWorkorderList(Workorder145 record) throws BusinessException {
		PageHelper.startPage(record.getPagination());
		return PageUtil.pack4PageHelper(workorder145Mapper.getWOList(record), record.getPagination());
	}
	
	/**
	 * 
	 * @Description 工包添加工单
	 * @CreateTime 2017年10月20日 上午11:05:48
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateGbid(Workorder145 record) throws BusinessException {
		// 验证工单当前状态是否为1或2且工包id为空
		validationWorkorder((List<String>) record.getParamsMap().get("idList"));
		workorder145Mapper.updateGbid(record);
		//工包为7就要下发工单
		doAutoIssuedWorkorder(record.getGbid(),(List<String>) record.getParamsMap().get("idList"));
	}
	/**
	 * 
	 * @Description 验证工单当前状态是否为1或2且工包id为空
	 * @CreateTime 2017年10月20日 上午11:23:07
	 * @CreateBy 岳彬彬
	 * @param list
	 * @throws BusinessException
	 */
	private void validationWorkorder(List<String> list) throws BusinessException {
		int count = workorder145Mapper.getCountByztAndGdid(list);
		if (list.size() != count) {
			throw new BusinessException("工单数据已变更，请刷新页面!");
		}
	}
	/**
	 * 
	 * @Description 下发工单
	 * @CreateTime 2017年11月10日 上午11:07:28
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @param list
	 * @throws BusinessException 
	 */
	private void doAutoIssuedWorkorder(String gbid,List<String> list) throws BusinessException{
		Workpackage145 wp = workpackage145Mapper.selectByPrimaryKey(gbid);
		//工包是下发状态的工单，就要处理工包下工单
		if(wp!=null && wp.getZt()==WorkpackageStatusEnum.TAKEEFFECT.getId()){		
			List<Workorder145> woList = workorder145Mapper.selectByIdList(list);
			if(woList!=null && woList.size()>0){
				StringBuffer buffer = new StringBuffer();
				boolean flag = true;
				List<String> idList = new ArrayList<String>();
				for (Workorder145 workorder : woList) {
					if(workorder.getZt()==WorkorderStatusEnum.SUBMIT.getId()){
						idList.add(workorder.getId());
						if(workorder.getGzzid()==null||"".equals(workorder.getGzzid())){
							buffer.append("145工单【").append(workorder.getGdbh()).append("】没有选择主工种!,");
							flag = false;
						}
					}
				}
				if(idList.size()>0 && flag){
					User user = ThreadVarUtil.getUser();
					String czls = UUID.randomUUID().toString();
					Workorder145 wo = new Workorder145();
					wo.setGbid(gbid);
					wo.setWhrid(user.getId());
					wo.setWhdwid(user.getBmdm());
					wo.setXfrid(user.getId());
					wo.setXfrdwid(user.getBmdm());
					wo.setZt(WorkorderStatusEnum.TAKEEFFECT.getId());
					wo.setDxfbs(20);
					wo.getParamsMap().put("idList", idList);
					workorder145Mapper.update4IssuedById(wo);
					commonRecService.write("id", idList, TableEnum.B_S2_014, user.getId(), czls, LogOperationEnum.ISSUED, UpdateTypeEnum.UPDATE, null);
				}
				if(!flag){
					throw new BusinessException(buffer.toString().substring(0,buffer.toString().length()-1));
				}
			}		
		}
	}

	/**
	 * @Description 145工单主列表
	 * @CreateTime 2017-10-23 上午11:17:54
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 */
	@Override
	public Map<String, Object> queryAllPageList(Workorder145 workorder) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = workorder.getId();
		workorder.setId(""); //跑到第一行
		User user = ThreadVarUtil.getUser();//当前登陆人
		workorder.getParamsMap().put("userId", user.getId());
		workorder.getParamsMap().put("userType", user.getUserType());//验证飞机注册号权限权限
		PageHelper.startPage(workorder.getPagination());
		List<Workorder145> eoList = workorder145Mapper.queryAllPageList(workorder);
		
		if (((Page) eoList).getTotal() > 0) {
			// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
			if (StringUtils.isNotBlank(id)) {
				// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
				if (!PageUtil.hasRecord(eoList, id)) {
					// 在查询条件中增加ID
					Workorder145 newRecord = new Workorder145();
					newRecord.setId(id);
					List<Workorder145> newRecordList = workorder145Mapper.queryAllPageList(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						eoList.add(0, newRecordList.get(0));
					}
				}
			}
			resultMap = PageUtil.pack4PageHelper(eoList, workorder.getPagination());
			return resultMap;

		} else {
			List<Workorder145> newRecordList = new ArrayList<Workorder145>();
			if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				// 在查询条件中增加ID
				Workorder145 newRecord = new Workorder145();
				newRecord.setId(id);
				newRecordList = workorder145Mapper.queryAllPageList(newRecord);
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
	 * @Description 工单145信息查询 (弹窗)
	 * @CreateTime 2017-12-28 上午11:27:24
	 * @CreateBy 刘兵
	 * @param workorder 145工单
	 * @return 工单列表
	 */
	@Override
	public Map<String, Object> queryAllPageListWin(Workorder145 workorder) {
		PageHelper.startPage(workorder.getPagination());
		List<Workorder145> list = workorder145Mapper.queryAllPageListWin(workorder);
		return PageUtil.pack4PageHelper(list, workorder.getPagination());
	}

	/**
	 * @Description 新增工单145
	 * @CreateTime 2017-10-10 下午8:50:14
	 * @CreateBy 雷伟
	 * @param workorder 工单145
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String save(Workorder145 workorder) throws BusinessException {
		/**准备数据*/
		User user = ThreadVarUtil.getUser();//当前登录用户
//		if(null == workorder.getGdbh() || "".equals(workorder.getGdbh())){
			/*Workpackage145 wp145 = workpackage145Mapper.selectByPrimaryKey(workorder.getGbid());
			Project project = new Project();
			if(null == wp145.getXmid() || "".equals(wp145.getXmid())){
				throw new BusinessException("工包没有选择项目，请选择项目!");
			}
			project.setId(wp145.getXmid());
			project = projectMapper.getProjectById(project);*/
//			boolean b = true;
//			int count = 0;
//			int max = 0;
			/*if(WorkorderTypeEnum.NRC.getId() == workorder.getGdlx()){
				max =  saiBongGzService.getLength4Lscd(user.getJgdm(), SaiBongEnum.GDNRC.getName());
			}else{
				max =  saiBongGzService.getLength4Lscd(user.getJgdm(), SaiBongEnum.GDRTN.getName());
			}*/
/*			while (b) {
				String gdbh;
				try {
					if(count == max){
						throw new BusinessException("采番号已满，请手动输入工单号或联系管理员!");
					}
					if(WorkorderTypeEnum.NRC.getId() == workorder.getGdlx()){
						gdbh = saibongUtilService.generate(user.getJgdm(),SaiBongEnum.GDNRC.getName(), project.getXmbm().concat("-N"));
						if(null == gdbh || "".equals(gdbh)){
							gdbh = saibongUtilService.generate("-1",SaiBongEnum.GDNRC.getName(), project.getXmbm().concat("-N"));
						}
					}else{
						gdbh = saibongUtilService.generate(user.getJgdm(),SaiBongEnum.GDRTN.getName(), project.getXmbm());
						if(null == gdbh || "".equals(gdbh)){
							gdbh = saibongUtilService.generate("-1",SaiBongEnum.GDRTN.getName(), project.getXmbm());
						}
					}		
					workorder.setGdbh(gdbh);
					int bcount = workorder145Mapper.getCount4Validation(user.getJgdm(),gdbh);
					if (bcount == 0) {
						b = false;
					}else{
						count++;
					}
				} catch (SaibongException e) {
					throw new RuntimeException(e);
				}
			}*/
//		}
		workorder.setId(UUID.randomUUID().toString());//工单ID
		if (null != workorder.getWorkContentAttachment()) {
			workorder.setGznrfjid(UUID.randomUUID().toString());//工作内容附件ID
		}
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

		if(StringUtils.isNotBlank(workorder.getFjjx())){
			/**检查用户机型的权限*/
			List<String> jxList = new ArrayList<String>();
			jxList.add(workorder.getFjjx());
			planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), user.getJgdm(), jxList);
		}
		

		/**校验数据唯一性*/
		//this.validateUniqueness(workorder);

		/**保存数据*/
		this.saveWOMain(workorder,currentDate,czls,operation);//保存WO主表数据
		this.saveWO4Others(workorder,currentDate,czls,operation,true);//保存WO关联表数据
		
		/**Start: 自动下发工单*/
		if(null != workorder.getParamsMap() && WorkorderStatusEnum.SUBMIT.getId() == workorder.getParamsMap().get("operationType")){
			if(workorder.getGbid() != null ){
				List<String> list = new ArrayList<String>();
				list.add(workorder.getId());
				this.doAutoIssuedWorkorder(workorder.getGbid(), list);
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
		String id = workorder.getId();
		if(null != workorder.getParamsMap() && WorkorderStatusEnum.SUBMIT.getId() == workorder.getParamsMap().get("operationType")){
			//更新135工单数据
			updateWO135Record(workorder,currentDate,czls,operation,user);
		}
		return id;
	}
	
	
	/**
	 * @Description 校验数据唯一性
	 * @CreateTime 2017-10-10 下午9:10:57
	 * @CreateBy 雷伟
	 * @param workorder
	 * @throws BusinessException 
	 */
	private void validateUniqueness(Workorder145 workorder) throws BusinessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//机构代码+工单编号唯一
		paramMap.clear();
		paramMap.put("gdbh", workorder.getGdbh());
		paramMap.put("dprtcode", workorder.getParamsMap().get("dprtcode"));
		List<Workorder145> wos = workorder145Mapper.selectByParam(paramMap);
		if (null != wos && wos.size() > 0) {
			throw new BusinessException("工单编号已存在");
		}
	}
	
	/**
	 * @Description  保存工单主表数据
	 * @CreateTime 2017-10-10 下午9:23:18
	 * @CreateBy 雷伟
	 * @param workorder 工单145
	 * @param currentDate 当前时间（取数据库时间）
	 * @param czls 操作流水
	 * @param operation operation 
	 * @throws BusinessException 
	 */
	private void saveWOMain(Workorder145 workorder, Date currentDate, String czls,LogOperationEnum operation) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登录用户
		workorder.setGdsbid(UUID.randomUUID().toString());//工单识别ID
//		workorder.setGbbh(workorder.getWorkpackage145().getGbbh());  
		//当页面没有填写缺陷保留编号时
		if(workorder.getGdbh() == null || "".equals(workorder.getGdbh())){ 
			String gddh=setGdbh(workorder,user); //根据缺陷获取最新缺陷保留单号
			workorder.setGdbh(gddh);
		}else{
			//验证验证缺陷保留单编号 唯一
			this.validateUniqueness(workorder);
		}
		
		workorder.setDprtcode(user.getJgdm());//组织编码
		workorder.setWhdwid(user.getBmdm());//维护人单位ID
		workorder.setWhrid(user.getId());//维护人ID
		workorder.setWhsj(currentDate);//维护时间
		workorder.setZdbmid(user.getBmdm());//制单部门ID
		workorder.setZdrid(user.getId());//制单人id
		workorder.setXmblbs(WhetherEnum.NO.getId());//项目保留标识标识：0否、1是
		workorder.setWgbs(WhetherEnum.NO.getId());//完工标识：0未完工、1已完工
		workorder.setDxfbs(WorkorderDXFBSEnum.INITIAL.getId());//10初始状态
		workorder145Mapper.insert(workorder);
		//保存历史记录信息
		commonRecService.write(workorder.getId(), TableEnum.B_S2_014, user.getId(), czls, operation, UpdateTypeEnum.SAVE, workorder.getId());
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
	private String setGdbh(Workorder145 workorder,User user) throws BusinessException {
		Workorder145 fai = new Workorder145(); 		  	//new 编号
		String gddh = null;
		boolean b = true;
		while(b){ 
			try {
				gddh = SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.GD145.getName(),workorder);
			} catch (SaibongException e) {
				throw new BusinessException(e);
			}
			fai.setGdbh(gddh);
			fai.setDprtcode(user.getJgdm());
			//根据技术文件对象查询数量
			int i = workorder145Mapper.queryCount(fai); 
			if(i <= 0){
				b = false;
			}
		}
		return gddh;
	}
	
	/**
	 * @Description 保存工单关联表数据
	 * @CreateTime 2017-10-10 下午9:23:18
	 * @CreateBy 雷伟
	 * @param workorder 工单145
	 * @param currentDate 当前时间（取数据库时间）
	 * @param czls 操作流水
	 * @param operation operation 
	 * @param flag 是否保存工时工序
	 */
	private void saveWO4Others(Workorder145 workorder, Date currentDate,String czls, LogOperationEnum operation,boolean flag) {
		User user = ThreadVarUtil.getUser();//当前登录用户
		//工作者
		workOrderWorkerService.saveWorkorder145(workorder, czls, operation);
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
		//附件列表
		attachmentService.eidtAttachment(workorder.getAttachments(), workorder.getId(), czls, workorder.getId(), user.getJgdm(), operation);
		if(flag){//145工单有工时工序，135工单没有
			//工序工时
			workorderProcessHoursService.saveProcessHoursList(workorder.getProcessList(),user,workorder.getId(),currentDate,czls,operation);
		
		}
	}

	/**
	 * @Description 根据ID查询145工单
	 * @CreateTime 2017-10-23 下午4:28:08
	 * @CreateBy 雷伟
	 * @param gdid 工单ID
	 * @return
	 */
	@Override
	public Workorder145 selectWOById(String gdid) {
		Workorder145 wo145 = workorder145Mapper.selectWOById(gdid);
		//TODO
		if(null != wo145){
			if(validation4Wbbs(wo145.getGbid())){
				wo145.getParamsMap().put("exist", 1);
			}
		}
		return wo145;
	}


	/**
	 * @Description 编辑工单145
	 * @CreateTime 2017-10-10 下午8:50:14
	 * @CreateBy 雷伟
	 * @param workorder 工单145
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String update(Workorder145 workorder) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登录用户
		Date currentDate = commonService.getSysdate();//当前时间
		String czls = UUID.randomUUID().toString();//操作流水
		LogOperationEnum operation = LogOperationEnum.EDIT;//操作类型

		Workorder145 woObj = workorder145Mapper.selectByPrimaryKey(workorder.getId()); //WO信息

		//检查用户机型的权限
		if(StringUtils.isNotBlank(workorder.getFjjx())){
			List<String> jxList = new ArrayList<String>();
			jxList.add(workorder.getFjjx());
			planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(),woObj.getDprtcode(), jxList);
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
		this.updateWO145Main(workorder,currentDate,czls,operation);//修改WO主表数据(需先更新关联表，再更新主表，因为要会写工作内容附件ID)
		
		/**Start: 自动下发工单*/
		if(null != workorder.getParamsMap() && WorkorderStatusEnum.SUBMIT.getId() == workorder.getParamsMap().get("operationType")){
			if(workorder.getGbid() != null ){
				List<String> list = new ArrayList<String>();
				list.add(workorder.getId());
				this.doAutoIssuedWorkorder(workorder.getGbid(), list);
			}
		}
		/**End: 自动下发工单*/
		String id = workorder.getId();
		//处理135工单
		if(null != workorder.getParamsMap() && WorkorderStatusEnum.SUBMIT.getId() == workorder.getParamsMap().get("operationType")||WorkorderStatusEnum.TAKEEFFECT.getId() == workorder.getParamsMap().get("operationType")){
			//更新135工单数据
			updateWO135Record(workorder,currentDate,czls,operation, user);
		}
		return id;
	}
	
	/**
	 * @Description 校验是否重复提交
	 * @CreateTime 2017-8-29 上午10:30:56
	 * @CreateBy 雷伟
	 * @param woObj 数据库工单对象
	 * @param operationId 操作类型ID
	 * @throws BusinessException 
	 */
	private void validateResubmit(Workorder145 woObj, Integer operationId) throws BusinessException {
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
		}
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
	private void updateWO4Others(Workorder145 workorder, Date currentDate,String czls, LogOperationEnum operation) {
		User user = ThreadVarUtil.getUser();//当前登录用户
		
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
		Workorder145 woObj = workorder145Mapper.selectByPrimaryKey(workorder.getId()); //WO信息
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
		//附件列表
		attachmentService.eidtAttachment(workorder.getAttachments(), workorder.getId(), czls, workorder.getId(), workorder.getDprtcode(), operation);

		//工序工时:先删除再保存
		workorderProcessHoursService.deleteByMainid(workorder.getId());
		workorderProcessHoursService.saveProcessHoursList(workorder.getProcessList(),user,workorder.getId(),currentDate,czls,operation);
	}
	
	/**
	 * @Description 修改工单145表数据
	 * @CreateTime 2017-10-11 下午7:56:06
	 * @CreateBy 雷伟
	 * @param workorder WO
	 * @param currentDate 当前时间
	 * @param czls 操作流水号
	 * @param operation 操作类型
	 */
	public void updateWO145Main(Workorder145 workorder, Date currentDate,String czls, LogOperationEnum operation) {
		User user = ThreadVarUtil.getUser();//当前登录用户
		workorder.setWhdwid(user.getBmdm());//维护人单位ID
		workorder.setWhrid(user.getId());//维护人ID
		workorder.setWhsj(currentDate);//维护时间
		//下发工单修改了，为了同步到osms将待下发标识改为20
		if(WorkorderStatusEnum.TAKEEFFECT.getId() == workorder.getParamsMap().get("operationType")){
			workorder.setDxfbs(20);
		}
		workorder145Mapper.updateByPrimaryKeySelective(workorder);
		//保存历史记录信息
		commonRecService.write(workorder.getId(), TableEnum.B_S2_014, user.getId(), czls, operation, UpdateTypeEnum.UPDATE, workorder.getId());
	}

	/**
	 * @Description 145工单航材工具
	 * @CreateTime 2017-10-24 下午1:55:53
	 * @CreateBy 雷伟
	 * @param workorder 145工单
	 * @return
	 */
	@Override
	public Map<String, Object> getGDHCToolDetail(Workorder145 workorder) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gdid", workorder.getId());
		map.put("dprtcode", workorder.getDprtcode());
		map.put("kcidList", null);
		List<Map<String, Object>>  hcList = workorder145Mapper.getGDHCToolDetail(map);
		map.clear();
		map.put("hcList", hcList);
		return map;
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

		Workorder145 woObj = workorder145Mapper.selectByPrimaryKey(woId); //WO信息

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
	 * @Description 删除工单关联表信息
	 * @CreateTime 2017-10-12 下午3:03:42
	 * @CreateBy 雷伟
	 * @param workorder 工单135
	 * @param currentDate 当前时间（取数据库时间）
	 * @param czls 操作流水
	 * @param operation operation 
	 */
	private void deleteWO4Others(Workorder145 woObj, Date currentDate,String czls, LogOperationEnum operation) {
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
	 * @Description 删除工单表信息
	 * @CreateTime 2017-10-12 下午3:03:37
	 * @CreateBy 雷伟
	 * @param workorder 工单135
	 * @param currentDate 当前时间（取数据库时间）
	 * @param czls 操作流水
	 * @param operation operation 
	 */
	private void deleteWOMain(Workorder145 woObj, Date currentDate, String czls,LogOperationEnum operation) {
		User user = ThreadVarUtil.getUser();//当前登录用户
		//保存历史记录信息
		commonRecService.write(woObj.getId(), TableEnum.B_S2_014, user.getId(), czls, operation, UpdateTypeEnum.DELETE, woObj.getId());
		workorder145Mapper.deleteByPrimaryKey(woObj.getId());
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
	public String doWGClose(Workorder145 workorder) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登录用户
		Date currentDate = commonService.getSysdate();//当前时间
		String czls = UUID.randomUUID().toString();//操作流水
		LogOperationEnum operation = LogOperationEnum.WANCHEN;//操作类型

		Workorder145 woObj = workorder145Mapper.selectByPrimaryKey(workorder.getId()); //WO信息

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
		workorder145Mapper.updateByPrimaryKeySelective(workorder);
		commonRecService.write(workorder.getId(), TableEnum.B_S2_014, user.getId(), czls, operation, UpdateTypeEnum.CLOSE, workorder.getId());//保存历史记录信息
		
		//保存工作者
		workOrder145WorkerService.save(workorder, czls, operation);
		boolean flag = false;
		if(doExistFj(woObj.getFjzch(),woObj.getDprtcode())){
			flag = true;
		}
		//拆装件
		doReplacementRecord(workorder,user,czls,operation,flag);
		attachmentService.eidtAttachment(workorder.getAttachments(), workorder.getId(), czls, workorder.getId(), workorder.getDprtcode(), operation);//附件列表
		
		/**工单关闭逻辑：缺toDo**/
		//更新135工单数据
		workorder.setZt(null);
		updateWO135Record(workorder,currentDate,czls,operation, user);
		return workorder.getId();
	}

	/**
	 * @Description 检查RTN工单是否可以关闭
	 * 逻辑：
		        关闭时，判断，工单类型是不是等于1（RTN）
		        如果是1（RTN），那么根据b_s2_014表的来源任务id，到b_g2_012去确定是否是定检包，
		        如果是定检包，那么根据选择关闭的这条工单中的工单识别id，找b_s2_014表定检包工单识别id的记录是否都关闭（9,10）
		        如果有一条没关闭，工单将不能被关闭！
	 * @CreateTime 2017-10-20 下午2:08:13
	 * @CreateBy 雷伟
	 * @param woObj 工单135对象
	 * @throws BusinessException 
	 */
	private void checkRTNIsCanClose(Workorder145 woObj) throws BusinessException {
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
	public String doZDClose(Workorder145 workorder) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登录用户
		Date currentDate = commonService.getSysdate();//当前时间
		String czls = UUID.randomUUID().toString();//操作流水
		LogOperationEnum operation = LogOperationEnum.GUANBI;//操作类型

		Workorder145 woObj = workorder145Mapper.selectByPrimaryKey(workorder.getId()); //WO信息

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
		//处理拆装件
//		doReplacementRecord(workorder,user,czls,operation,false);
		workorder145Mapper.updateByPrimaryKeySelective(workorder);
		commonRecService.write(workorder.getId(), TableEnum.B_S2_014, user.getId(), czls, operation, UpdateTypeEnum.CLOSE, workorder.getId());//保存历史记录信息
		
		return workorder.getId();
	}

	/**
	 * @Description 修订工单
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 林龙
	 * @param workorder 工单
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String doXDClose(Workorder145 workorder) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登录用户
		Date currentDate = commonService.getSysdate();//当前时间
		String czls = UUID.randomUUID().toString();//操作流水
		LogOperationEnum operation = LogOperationEnum.REVISE;//操作类型
		
		/**修改数据*/
		workorder.setWhdwid(user.getBmdm());//维护人单位ID
		workorder.setWhrid(user.getId());//维护人ID
		workorder.setWhsj(currentDate);//维护时间
		workorder.setGbrid(user.getId());//关闭人ID
		workorder.setGbrq(currentDate);//关闭人ID
		workorder145Mapper.updateByPrimaryKeySelective(workorder);
		commonRecService.write(workorder.getId(), TableEnum.B_S2_014, user.getId(), czls, operation, UpdateTypeEnum.UPDATE, workorder.getId());//保存历史记录信息
		//保存工作者
		workOrder145WorkerService.save(workorder, czls, operation);
		
		//拆装件
		Workorder145 woObj = workorder145Mapper.selectByPrimaryKey(workorder.getId()); //WO信息
		boolean flag = false;
		if(doExistFj(woObj.getFjzch(),woObj.getDprtcode())){
			flag = true;
		}
		doReplacementRecord(workorder,user,czls,operation,flag);
		
		if(workorder.getZt()==WorkorderStatusEnum.CLOSETOFINISH.getId()){//当状态为完成时
			//附件
			attachmentService.eidtAttachment(workorder.getAttachments(), workorder.getId(), czls, workorder.getId(), workorder.getDprtcode(), operation);//附件列表
			
			/**工单关闭逻辑：缺toDo**/
			//更新135工单数据
			workorder.setZt(null);
			updateWO135Record(workorder,currentDate,czls,operation, user);
		}
		return workorder.getId();
	}
	
	
	/**
	 * @Description 工单反馈
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param workorder 工单145
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String doFeedback(Workorder145 workorder) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登录用户
		Date currentDate = commonService.getSysdate();//当前时间
		String czls = UUID.randomUUID().toString();//操作流水
		LogOperationEnum operation = LogOperationEnum.FEEDBACK;//操作类型

		Workorder145 woObj = workorder145Mapper.selectByPrimaryKey(workorder.getId()); //WO信息

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
		workorder145Mapper.updateByPrimaryKeySelective(workorder);
		commonRecService.write(workorder.getId(), TableEnum.B_S2_014, user.getId(), czls, operation, UpdateTypeEnum.UPDATE, workorder.getId());//保存历史记录信息
		//保存工作者
		workOrder145WorkerService.save(workorder, czls, operation);
		boolean flag = false;
		if(doExistFj(woObj.getFjzch(),woObj.getDprtcode())){
			flag = true;
		}
		//拆装件
		doReplacementRecord(workorder,user,czls,operation,flag);
		attachmentService.eidtAttachment(workorder.getAttachments(), workorder.getId(), czls, workorder.getId(), workorder.getDprtcode(), operation);//附件列表
		//更新135工单数据
		updateWO135Record(workorder,currentDate,czls,operation, user);
		return workorder.getId();
	}
	/**
	 * 
	 * @Description 处理拆装件
	 * @CreateTime 2017年11月1日 上午11:24:33
	 * @CreateBy 岳彬彬
	 * @param workorder
	 * @param user
	 * @param czls
	 * @param operation
	 * @throws BusinessException
	 */
	private void doReplacementRecord(Workorder145 workorder,User user,String czls,LogOperationEnum operation,boolean flag) throws BusinessException{
		deleteNotExist(workorder,czls,user,flag);
		if(workorder.getWoIORecordList()!=null){
			List<Workorder145IORecord> list = workorder.getWoIORecordList();
			for (Workorder145IORecord workOrderIORecord : list) {
				if(workOrderIORecord.getZsj()!=null && StringUtils.isNotBlank(workOrderIORecord.getZsj().getBjh())){
					if(null  == workOrderIORecord.getZsj().getJx() || StringUtils.isBlank(workOrderIORecord.getZsj().getJx())){
						workOrderIORecord.getZsj().setJx("-");
					}
					if(null  == workOrderIORecord.getZsj().getFjzch() || StringUtils.isBlank(workOrderIORecord.getZsj().getFjzch())){
						workOrderIORecord.getZsj().setFjzch("-");;
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
					workorder145IORecordMapper.insertSelective(workOrderIORecord);
					// 保存历史记录信息
					commonRecService.write(workOrderIORecord.getId(), TableEnum.B_S2_01401, user.getId(), 
							czls , operation, UpdateTypeEnum.SAVE, workorder.getId());
					if(flag){//完工反馈和完工关闭对135工单的拆换件记录有修改
						Workorder145 record = workorder145Mapper.selectByPrimaryKey(workorder.getId());
						Workorder wo = workorderMapper.getIdByGdsbid(record.getGdsbid());//135工单
						if(null != wo && WorkorderStatusEnum.CLOSETOEND.getId() != wo.getZt() && WorkorderStatusEnum.CLOSETOFINISH.getId() != wo.getZt()){
							workOrderIORecord.setMainid(wo.getId());
							workOrderIORecordMapper.insertByWoIO145(workOrderIORecord);
							// 保存历史记录信息
							commonRecService.write(workOrderIORecord.getId(), TableEnum.B_S2_00801, user.getId(), 
									czls , operation, UpdateTypeEnum.SAVE, workorder.getId());
						}					
					}									
				} 
				else {	// 修改拆装记录
					workorder145IORecordMapper.updateByWorkOrder145(workOrderIORecord);
					// 保存历史记录信息
					commonRecService.write(workorder.getId(), TableEnum.B_S2_01401, user.getId(), 
							czls , operation, UpdateTypeEnum.UPDATE, workorder.getId());
					if(workOrderIORecord.getZsj() ==null){
						Workorder145IORecord record = workorder145IORecordMapper.selectByPrimaryKey(workOrderIORecord.getId());
						if(null != record.getZsZjqdid() && !"".equals(record.getZsZjqdid())){
							installationListTempService.delete(record.getZsZjqdid());
						}
					}
					if(flag){
						Workorder145 record = workorder145Mapper.selectByPrimaryKey(workorder.getId());
						Workorder wo = workorderMapper.getIdByGdsbid(record.getGdsbid());//135工单
						if(null != wo && WorkorderStatusEnum.CLOSETOEND.getId() != wo.getZt() && WorkorderStatusEnum.CLOSETOFINISH.getId() != wo.getZt()){
							if(workOrderIORecord.getZsj() ==null){
								workOrderIORecord.setZsZjqdid(null);
							}
							workOrderIORecord.setMainid(wo.getId());
							int count = workOrderIORecordMapper.updateByWoIO145(workOrderIORecord);
							if(count == 0){//修改的时候不存在，就新增
								workOrderIORecordMapper.insertByWoIO145(workOrderIORecord);
							}	
							// 保存历史记录信息
							commonRecService.write(workorder.getId(), TableEnum.B_S2_00801, user.getId(), 
									czls , operation, UpdateTypeEnum.UPDATE, workorder.getId());
						}					
					}
					
				}
			}
			
		}
	}
	/**
	 * 
	 * @Description 删除不存在的记录
	 * @CreateTime 2017年11月1日 上午11:23:26
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param czls
	 * @param user
	 */
	private void deleteNotExist(Workorder145 record,String czls, User user,boolean flag){
		// 插入日志
		StringBuilder sql = new StringBuilder("b.mainid = '"+record.getId()+"' ");
		if(null != record.getWoIORecordList() && !record.getWoIORecordList().isEmpty()){
			sql.append("and b.id not in (");
			for (Workorder145IORecord disassembly : record.getWoIORecordList()) {
				sql.append("'").append(disassembly.getId()==null?"0":disassembly.getId()).append("',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(")");
		}
		commonRecService.writeByWhere(sql.toString(), TableEnum.B_S2_01401, user.getId(), record.getCzls(),
				LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, record.getId());
		// 删除数据
		workorder145IORecordMapper.deleteNotExist(record);
		if(flag){
			Workorder wo = workorderMapper.getIdByGdsbid(record.getGdsbid());//135工单
			if(null != wo && WorkorderStatusEnum.CLOSETOEND.getId() != wo.getZt() && WorkorderStatusEnum.CLOSETOFINISH.getId() != wo.getZt()){
				sql.replace(12,record.getId().length()+12,wo.getId());
				commonRecService.writeByWhere(sql.toString(), TableEnum.B_S2_00801, user.getId(), record.getCzls(),
						LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, record.getId());
				// 删除135数据
				String id = record.getId();
				record.setId(wo.getId());
				workOrderIORecordMapper.deleteNotExistWO135(record);
				record.setId(id);
			}		
		}	
	}
	/**
	 * 
	 * @Description 工种完工注水图数据
	 * @CreateTime 2017年10月23日 下午2:55:56
	 * @CreateBy 林龙
	 * @param workorder145
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryDiagramList(Workorder145 workorder145) {
		return	workorder145Mapper.queryDiagramList(workorder145);
	}

	/**
	 * 
	 * @Description 工种执行进度图数据
	 * @CreateTime 2017年10月24日 下午1:52:48
	 * @CreateBy 林龙
	 * @param workorder145
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryProgressList(Workorder145 workorder145) {
		return	workorder145Mapper.queryProgressList(workorder145);
	}
	
	/**
	 * 
	 * @Description 更新135工单数据
	 * @CreateTime 2017年11月8日 下午2:22:19
	 * @CreateBy 岳彬彬
	 * @param workorder
	 * @throws BusinessException 
	 */
	private void updateWO135Record(Workorder145 workorder,Date currentDate,String czls, LogOperationEnum operation,User user) throws BusinessException{
			Workorder145 record = workorder145Mapper.selectByPrimaryKey(workorder.getId());
			//验证工单所在工包是否是内部标识且工包存在于135中
			if(validation4Wbbs(record.getGbid())){
				workorder.setGdsbid(record.getGdsbid());
				if(workorder.getGdsbid()!=null&&!"".equals(workorder.getGdsbid())){
					Workorder wo = workorderMapper.getIdByGdsbid(workorder.getGdsbid());//135工单
					//如果工单存在于135
				if (wo != null && wo.getId() != null && !"".equals(wo.getId())) {
					// 135工单不为关闭或指定结束状态才能同步更新
					if (WorkorderStatusEnum.CLOSETOFINISH.getId() != wo.getZt() && WorkorderStatusEnum.CLOSETOEND.getId() != wo.getZt()) {
						// 135工单为下发不更新状态
						if (wo.getZt() == WorkorderStatusEnum.TAKEEFFECT.getId()) {
							workorder.setZt(null);
						}
						// 如果145工单的飞机注册号存在于d_007，就更新135工单
						if (!doExistFj(record.getFjzch(), record.getDprtcode())) {
						// 如果145工单的飞机注册号不存在于d_007，就更新部分135工单（拆换件不同步，飞机注册号、飞机机型、msn不同步）
							workorder.setFjzch(null);
						}
						workorderMapper.updateByGdsbid(workorder);// 更新工单
						commonRecService.write(wo.getId(), TableEnum.B_S2_008, user.getId(), czls,
								LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, wo.getId());
						updateWO1354Others(workorder, currentDate, wo.getId(), czls, operation, user);// 更新关联表
					}
				}else{//不存在就验证145工单飞机注册号是否存在于d_007
						// 存在就同步到135工单（新增工单），不存在就不同步
					if (doExistFj(record.getFjzch(), record.getDprtcode())) {
						workorder.setZt(WorkorderStatusEnum.TAKEEFFECT.getId());
						workorder.setXfrid(user.getId());
						workorder.setXfrdwid(user.getBmdm());
						workorder.setXfsj(new Date());
						workorder.setXmblbs(record.getXmblbs());// 防止先新增后提交导致项目保留标识没有带进来导致同步不一致
						workorder.setWgbs(record.getWgbs());// 防止先新增后提交导致完工标识没有带进来导致同步不一致
						workorder.setId(UUID.randomUUID().toString());
						if (workorder.getWorkContentAttachment() != null) {
							workorder.setGznrfjid(UUID.randomUUID().toString());
							workorder.getWorkContentAttachment().setMainid(workorder.getGznrfjid());
						}
						workorderMapper.insertByWO145(workorder);// 新增135工单
						commonRecService.write(workorder.getId(), TableEnum.B_S2_008, user.getId(), czls,
								LogOperationEnum.ISSUED, UpdateTypeEnum.SAVE, workorder.getId());
						this.saveWO4Others(workorder, currentDate, czls, operation, false);// 保存WO关联表数据
						// 处理附件
						addWorkorder135Attachment(workorder.getId(), record.getId(), record.getDprtcode(), user, czls);
					}	
				}
			}
		}
	}
	/**
	 * 
	 * @Description 同步135工单的关联表数据
	 * @CreateTime 2017年11月9日 上午9:33:06
	 * @CreateBy 岳彬彬
	 * @param workorder
	 * @param currentDate
	 * @param id
	 * @param czls
	 * @param operation
	 */
	private void updateWO1354Others(Workorder145 workorder, Date currentDate,String id ,String czls, LogOperationEnum operation,User user) {
		//工作者
		workOrderWorkerService.updateWorkorder145(workorder, czls, operation);
		//区域、接近、飞机站位
		coverPlateService.updateCoverPlateList(workorder.getCoverPlateList(), ProjectBusinessEnum.WORKORDER.getId(), id, czls, id,  workorder.getDprtcode(), operation);
		//工种工时 
		workHourService.updateWorkHourList(workorder.getWorkHourList(), ProjectBusinessEnum.WORKORDER.getId(), id, czls, id, workorder.getDprtcode(), operation);
		//相关参考文件
		referenceService.updateReferenceList(workorder.getReferenceList(), ProjectBusinessEnum.WORKORDER.getId(), id, czls, id, workorder.getDprtcode(), operation);
		//器材、工具
		materialToolService.updateMaterialToolList(workorder.getMaterialToolList(), ProjectBusinessEnum.WORKORDER.getId(),  id, czls,  id, workorder.getDprtcode(), operation);
		//工作内容
		workContentService.updateWorkContentList(workorder.getWorkContentList(),ProjectBusinessEnum.WORKORDER.getId(), id, czls,  id, workorder.getDprtcode(), operation);
		//工作内容附件
		Workorder woObj = workorderMapper.selectByPrimaryKey(id); //WO信息
		if(null != woObj.getGznrfjid() && !"".equals(woObj.getGznrfjid())){
			attachmentService.delFiles(woObj.getGznrfjid(), czls, workorder.getId(), LogOperationEnum.EDIT);
			if(workorder.getWorkContentAttachment() == null){
				//删除工卡附件
				workorder.setGznrfjid("");
			}else{
				//编辑工卡附件
				attachmentService.addAttachment(workorder.getWorkContentAttachment(), woObj.getGznrfjid(), czls, id,  workorder.getDprtcode(), operation);
			}
		}else{
			if(workorder.getWorkContentAttachment() != null){
				workorder.setGznrfjid(UUID.randomUUID().toString());
				attachmentService.addAttachment(workorder.getWorkContentAttachment(), workorder.getGznrfjid(), czls, id,  workorder.getDprtcode(), operation);
			}
		}
		//处理附件列表
		attachmentService.eidtAttachment(workorder.getAttachments(), id, czls, id, workorder.getDprtcode(), operation);
	}
	
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017年11月17日 下午2:30:55
	 * @CreateBy 岳彬彬
	 * @param id 新mainid
	 * @param mainid 旧mainid
	 * @param dprtcode
	 * @param user
	 * @param czls
	 */
	private void addWorkorder135Attachment(String id, String mainid, String dprtcode,User user ,String czls){
		attachmentService.delFiles(id, czls, id, LogOperationEnum.DELETE);
		Attachment attachment = new Attachment();
		attachment.setMainid(mainid);
		attachment.setId(id);
		attachment.setDprtcode(dprtcode);
		attachmentMapper.insertAttachemnt4Copy(attachment);
		commonRecService.writeByWhere(" b.mainid = '".concat(id).concat("' "), TableEnum.D_011, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,id);
	}
	/**
	 * 
	 * @Description 验证工单的的工包的是否是内部
	 * @CreateTime 2017年11月16日 下午3:28:30
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @return
	 */
	private boolean validation4Wbbs(String gbid){
		Workpackage145 wp145 = workpackage145Mapper.selectByPrimaryKey(gbid);
		Workpackage wp = workpackageMapper.selectByPrimaryKey(gbid);
		if(null != wp && null != wp145 && wp145.getWbbs() == 0){
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @Description 验证飞机注册号是否存在d_007
	 * @CreateTime 2018年4月19日 下午4:07:57
	 * @CreateBy 岳彬彬
	 * @param fjzch
	 * @param dprtcode
	 * @return
	 */
	private boolean doExistFj(String fjzch, String dprtcode){
		int count = aircraftinfoMapper.doValidation4FjzchExist(fjzch, dprtcode);
		if(count>0){
			return true;
		}
		return false;
	}
}
