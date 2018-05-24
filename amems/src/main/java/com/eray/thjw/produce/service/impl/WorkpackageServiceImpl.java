package com.eray.thjw.produce.service.impl;

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

import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.dao.DepartmentMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.AircraftinfoMapper;
import com.eray.thjw.produce.dao.MonitoringWorkpackageMapper;
import com.eray.thjw.produce.dao.WorkOrderIORecordMapper;
import com.eray.thjw.produce.dao.Workorder145HoursMapper;
import com.eray.thjw.produce.dao.Workorder145IORecordMapper;
import com.eray.thjw.produce.dao.Workorder145Mapper;
import com.eray.thjw.produce.dao.WorkorderMapper;
import com.eray.thjw.produce.dao.Workpackage145Mapper;
import com.eray.thjw.produce.dao.WorkpackageMapper;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.po.Workorder145;
import com.eray.thjw.produce.po.Workorder145Hours;
import com.eray.thjw.produce.po.Workorder145IORecord;
import com.eray.thjw.produce.po.Workpackage;
import com.eray.thjw.produce.po.Workpackage145;
import com.eray.thjw.produce.service.WorkpackageService;
import com.eray.thjw.project2.dao.CoverPlateMapper;
import com.eray.thjw.project2.dao.InstructionsourceMapper;
import com.eray.thjw.project2.dao.MaterialToolMapper;
import com.eray.thjw.project2.dao.ReferenceMapper;
import com.eray.thjw.project2.dao.WorkCardMapper;
import com.eray.thjw.project2.dao.WorkContentMapper;
import com.eray.thjw.project2.dao.WorkHourMapper;
import com.eray.thjw.project2.po.CoverPlate;
import com.eray.thjw.project2.po.MaterialTool;
import com.eray.thjw.project2.po.Reference;
import com.eray.thjw.project2.po.WorkCard;
import com.eray.thjw.project2.po.WorkContent;
import com.eray.thjw.project2.po.WorkHour;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.system.dao.SynRelMapper;
import com.eray.thjw.system.dao.WorkGroupMapper;
import com.eray.thjw.system.po.SynRel;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.WhetherEnum;
import enu.produce.FeedbackStatusEnum;
import enu.produce.WorkorderStatusEnum;
import enu.produce.WorkpackageStatusEnum;

@Service
public class WorkpackageServiceImpl implements WorkpackageService {

	@Resource
	private WorkpackageMapper workpackageMapper;
	@Resource
	private MonitoringWorkpackageMapper monitoringWorkpackageMapper;
	@Resource
	private WorkorderMapper workorderMapper;
	@Resource
	private WorkOrderIORecordMapper workOrderIORecordMapper;
	@Resource
	private Workpackage145Mapper workpackage145Mapper;
	@Resource
	private Workorder145Mapper workorder145Mapper;
	@Resource
	private Workorder145HoursMapper workorder145HoursMapper;
	@Resource
	private Workorder145IORecordMapper workorder145IORecordMapper;
	@Resource
	private SynRelMapper synRelMapper;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private WorkHourMapper workHourMapper;
	@Resource
	private CoverPlateMapper coverPlateMapper;
	@Resource
	private MaterialToolMapper materialToolMapper;
	@Resource
	private ReferenceMapper referenceMapper;
	@Resource
	private WorkContentMapper workContentMapper;
	@Resource
	private AircraftinfoMapper aircraftinfoMapper;
	@Resource
	private WorkCardMapper workCardMapper;
	@Resource
	private AttachmentMapper attachmentMapper;
	@Resource
	private AttachmentService attachmentService;
	@Resource
	private PlaneModelDataService planeModelDataService;
	@Resource
	private InstructionsourceMapper instructionsourceMapper;
	@Resource
	private DepartmentMapper departmentMapper;
	
	@Resource
	private WorkGroupMapper workGroupMapper;

	/**
	 * 
	 * @Description 新增
	 * @CreateTime 2017年9月25日 下午3:59:53
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String addRecord(Workpackage record) throws BusinessException {
		String id = UUID.randomUUID().toString();
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		record.setId(id);
		
		
		record.setDprtcode(user.getJgdm());
		record.setWhrid(user.getId());
		record.setWhdwid(user.getBmdm());
		record.setZdrid(user.getId());
		record.setZdbmid(user.getBmdm());
		record.setZt(WorkpackageStatusEnum.SUBMIT.getId());
		record.setDtbs(WhetherEnum.NO.getId());
		record.setWgbs(FeedbackStatusEnum.NO.getId());
		
		//当页面没有填写编号时
		if(record.getGbbh() == null || "".equals(record.getGbbh())){ 
			String gbbh=setGbbh(record); //根据评估单对象获取最新编号
			record.setGbbh(gbbh);
		}else{
			// 验证编号唯一
			this.validationGbbh(record);
		}
		
		// 验证飞机权限
		validationFjzch(record, user);
		
		// 新增工包
		workpackageMapper.insertSelective(record);
		// 保存附件信息
		attachmentService.eidtAttachment(record.getAttachments(), id, czls, id, record.getDprtcode(),
				LogOperationEnum.SUBMIT_WO);
		// 工包日志
		commonRecService.write(record.getId(), TableEnum.B_S2_007, user.getId(), czls, LogOperationEnum.SAVE_WO,
				UpdateTypeEnum.SAVE, record.getId());
		return id;
	}
	
	/**
	 * 
	 * @Description  自动生成编号
	 * @CreateTime 2017年8月17日 下午4:32:44
	 * @CreateBy 林龙
	 * @param technical
	 * @return pgdbhNew 编号
	 * @throws com.eray.framework.exception.SaibongException 
	 */
	private String setGbbh(Workpackage record) throws BusinessException{
		User user = ThreadVarUtil.getUser();//当前登陆人
		Workpackage tec = new Workpackage(); //new 对象
		boolean b = true;
		String dh = null;
		while(b){
			try {
				dh = SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.GB135.getName(),record);
			} catch (SaibongException e) {
				throw new BusinessException(e);
			} 
			tec.setGbbh(dh);
			tec.setDprtcode(user.getJgdm());
			//根据技术文件对象查询技术文件数量
			int i = workpackageMapper.queryCount(tec); 
			if(i <= 0){
				b = false;
			}
		}
		return dh;
	}

	/**
	 * 
	 * @Description 验证飞机权限
	 * @CreateTime 2017年9月25日 下午3:04:16
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param user
	 * @throws BusinessException
	 */
	private void validationFjzch(Workpackage record, User user) throws BusinessException {
		if(StringUtils.isNotBlank(record.getFjzch())){
			List<String> fjzchList = new ArrayList<String>();
			fjzchList.add(record.getFjzch());
			planeModelDataService.existsAircraft4Expt(user.getId(), user.getUserType(), record.getDprtcode(), fjzchList);
		}	
	}

	/**
	 * 
	 * @Description 验证工包编号唯一
	 * @CreateTime 2017年9月25日 下午3:04:31
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private void validationGbbh(Workpackage record) throws BusinessException {
		Integer count = workpackageMapper.getCountByGbbhAndDprtcode(record.getGbbh(), record.getDprtcode());
		if (count != null && count > 0) {
			throw new BusinessException("该工包编号已存在!");
		}
	}

	/**
	 * 
	 * @Description 获取工包列表
	 * @CreateTime 2017年9月25日 下午3:58:43
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 */
	@Override
	public Map<String, Object> getWorkpackageList(Workpackage record) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = record.getId();
		record.setId("");
		User user = ThreadVarUtil.getUser();
		record.getParamsMap().put("userId", user.getId());
		record.getParamsMap().put("userType", user.getUserType());
		try {
			PageHelper.startPage(record.getPagination());
			List<Workpackage> recordList = workpackageMapper.getWorkpackageList(record);
			if (((Page) recordList).getTotal() > 0) {
				// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if (StringUtils.isNotBlank(id)) {
					// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					if (!PageUtil.hasRecord(recordList, id)) {
						// 在查询条件中增加ID
						Workpackage newRecord = new Workpackage();
						newRecord.setId(id);
						List<Workpackage> newRecordList = workpackageMapper.getWorkpackageList(newRecord);
						if (newRecordList != null && newRecordList.size() == 1) {
							recordList.add(0, newRecordList.get(0));
						}
					}
				}
				resultMap = PageUtil.pack4PageHelper(recordList, record.getPagination());
				return resultMap;

			} else {
				List<Workpackage> newRecordList = new ArrayList<Workpackage>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					Workpackage newRecord = new Workpackage();
					newRecord.setId(id);
					newRecordList = workpackageMapper.getWorkpackageList(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						resultMap = PageUtil.pack(1, newRecordList, record.getPagination());
						return resultMap;
					}

				}
				resultMap = PageUtil.pack(0, newRecordList, record.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败", e);
		}
	}
	
	/**
	 * @Description 查询航材工具需求清单工包信息 
	 * @CreateTime 2018-4-12 上午11:33:44
	 * @CreateBy 刘兵
	 * @param record
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryMToolDetailList(Workpackage record) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		record.getParamsMap().put("userId", user.getId());
		record.getParamsMap().put("userType", user.getUserType());
		List<Workpackage> recordList = workpackageMapper.getWorkpackageList(record);
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (Workpackage workpackage : recordList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", workpackage.getId());
			map.put("fjzch", workpackage.getFjzch());
			map.put("xlh", workpackage.getAircrafInfo()!=null?workpackage.getAircrafInfo().getXlh():"");
			map.put("gbbh", workpackage.getGbbh());
			map.put("gbmc", workpackage.getGbmc());
			map.put("wxlx", workpackage.getWxlx());
			map.put("zdrq", workpackage.getZdrq());
			map.put("jhKsrq", workpackage.getJhKsrq());
			map.put("jhJsrq", workpackage.getJhJsrq());
			map.put("xfdwDprtname", workpackage.getXfdwDepartment()!=null?workpackage.getXfdwDepartment().getDprtname():"");
			map.put("gzyq", workpackage.getGzyq());
			resultList.add(map);
		}
		resultMap.put("total", resultList.size());
		resultMap.put("rows", resultList);
		return resultMap;

	}
	
	/**
	 * @Description 查询预组包工包信息
	 * @CreateTime 2018-4-12 上午11:33:44
	 * @CreateBy 刘兵
	 * @param record
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryBurstificationWPList(Workpackage record) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = record.getId();
		record.setId("");
		User user = ThreadVarUtil.getUser();
		record.getParamsMap().put("userId", user.getId());
		record.getParamsMap().put("userType", user.getUserType());
		try {
			PageHelper.startPage(record.getPagination());
			List<Workpackage> recordList = workpackageMapper.getWorkpackageList(record);
			if (((Page) recordList).getTotal() > 0) {
				// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if (StringUtils.isNotBlank(id)) {
					// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					if (!PageUtil.hasRecord(recordList, id)) {
						// 在查询条件中增加ID
						Workpackage newRecord = new Workpackage();
						newRecord.setId(id);
						List<Workpackage> newRecordList = workpackageMapper.getWorkpackageList(newRecord);
						if (newRecordList != null && newRecordList.size() == 1) {
							recordList.add(0, newRecordList.get(0));
						}
					}
				}
				resultMap = PageUtil.pack4PageHelper(recordList, record.getPagination());
				resultMap.put("rows", workpackageToMap(recordList));
				return resultMap;

			} else {
				List<Workpackage> newRecordList = new ArrayList<Workpackage>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					Workpackage newRecord = new Workpackage();
					newRecord.setId(id);
					newRecordList = workpackageMapper.getWorkpackageList(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						resultMap = PageUtil.pack(1, newRecordList, record.getPagination());
						resultMap.put("rows", workpackageToMap(newRecordList));
						return resultMap;
					}

				}
				resultMap = PageUtil.pack(0, recordList, record.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败", e);
		}
	}
	
	/**
	 * @Description 工包对象转map
	 * @CreateTime 2018-4-11 上午9:58:54
	 * @CreateBy 刘兵
	 * @param list
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> workpackageToMap(List<Workpackage> list){
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (Workpackage workpackage : list) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", workpackage.getId());
			resultMap.put("dprtcode", workpackage.getDprtcode());
			resultMap.put("zt", workpackage.getZt());
			resultMap.put("gbbh", workpackage.getGbbh());
			resultMap.put("gbmc", workpackage.getGbmc());
			resultMap.put("wxlx", workpackage.getWxlx());
			resultMap.put("jhKsrq", workpackage.getJhKsrq());
			resultMap.put("jhJsrq", workpackage.getJhJsrq());
			resultMap.put("zdrq", workpackage.getZdrq());
			resultMap.put("jhZxdw", workpackage.getJhZxdw());
			resultMap.put("gzyq", workpackage.getGzyq());
			resultMap.put("whsj", workpackage.getWhsj());
			
			
			resultMap.put("xfdwDprtname", workpackage.getXfdwDepartment()!=null?workpackage.getXfdwDepartment().getDprtname():"");
			resultMap.put("username", workpackage.getWhr()!=null?workpackage.getWhr().getUsername():"");
			resultMap.put("realname", workpackage.getWhsj()!=null?workpackage.getWhr().getRealname():"");
			resultMap.put("dprtname", workpackage.getDepartment()!=null?workpackage.getDepartment().getDprtname():"");
			
			resultList.add(resultMap);
		}
		return resultList;
	}

	/**
	 * 
	 * @Description 根据id获取工包数据
	 * @CreateTime 2017年9月27日 下午3:19:31
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 * @throws BusinessException 
	 */
	@Override
	public Workpackage getRecord(Workpackage record) throws BusinessException {
		if("close".equals(record.getParamsMap().get("option"))){
			//验证工单是否都关闭或完成
			validation4Workorder(record.getId());
			// 验证预组包中是否有工包
			validation(record.getId());
		}else if("end".equals(record.getParamsMap().get("option"))){
			//验证工单是否都关闭或完成
			validation4Workorder(record.getId());
		}
		Workpackage wp = workpackageMapper.selectByPrimaryKey(record.getId());
		return wp;
	}

	/**
	 * 
	 * @Description 更新工包
	 * @CreateTime 2017年9月27日 下午3:20:32
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public void updateRecord(Workpackage record) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		record.setWhrid(user.getId());
		record.setWhdwid(user.getBmdm());
		// 验证飞机权限
		validationFjzch(record, user);
		// 验证工包状态
		validation4zt(record);
		// 更新工包
		workpackageMapper.updateByPrimaryKey(record);
		// 保存附件信息
		attachmentService.eidtAttachment(record.getAttachments(), record.getId(), czls, record.getId(),
				record.getDprtcode(), LogOperationEnum.EDIT);
		// 工包日志
		commonRecService.write(record.getId(), TableEnum.B_S2_007, user.getId(), czls, LogOperationEnum.EDIT,
				UpdateTypeEnum.UPDATE, record.getId());
	}

	/**
	 * 
	 * @Description 验证工包状态是否发生变化
	 * @CreateTime 2017年9月27日 下午3:39:52
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private void validation4zt(Workpackage record) throws BusinessException {
		Integer currentZt = workpackageMapper.getCurrentById(record.getId());
		if (currentZt == null || record.getZt() != currentZt) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		} else if (currentZt != WorkpackageStatusEnum.SUBMIT.getId() && currentZt != WorkpackageStatusEnum.SAVE.getId()) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}

	/**
	 * 
	 * @Description 删除工包
	 * @CreateTime 2017年9月27日 下午5:09:55
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public void deleteRecord(Workpackage record) throws BusinessException {
		// 验证工包状态
		validation4zt(record);
		if (record.getZt() == WorkpackageStatusEnum.SUBMIT.getId()) {
			// 验证工包为提交状态下下面没有工单
			validation4WorkOrder(record);
		}
		// 清除预组包关系
		monitoringWorkpackageMapper.updateByGbid(record.getId());
		// 工包日志
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		commonRecService.write(record.getId(), TableEnum.B_S2_007, user.getId(), czls, LogOperationEnum.DELETE,
				UpdateTypeEnum.DELETE, record.getId());
		// 物理删除工包
		workpackageMapper.deleteByPrimaryKey(record.getId());
		//清除附件
		attachmentService.delFiles(record.getId(), czls, record.getId(), LogOperationEnum.DELETE);
	}

	/**
	 * 
	 * @Description 验证工包为提交状态下下面没有工单
	 * @CreateTime 2017年9月28日 上午10:38:11
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private void validation4WorkOrder(Workpackage record) throws BusinessException {
		// 查询工包下面的工单数量
		int count = workorderMapper.getCountByGbid(record.getId());
		if (count > 0) {
			throw new BusinessException("该工包下面有工单，不能删除该工包!");
		}
	}

	/**
	 * 
	 * @Description 指定结束
	 * @CreateTime 2017年9月27日 下午6:20:19
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public void update4EndClose(Workpackage record) throws BusinessException {
		// 验证工包状态
		Integer current = workpackageMapper.getCurrentById(record.getId());
		if (current != WorkpackageStatusEnum.SUBMIT.getId() && current != WorkpackageStatusEnum.TAKEEFFECT.getId()) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		// 验证工单是否都关闭或完成
		validation4Workorder(record.getId());
		// 设置参数
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		record.setWhrid(user.getId());
		record.setWhdwid(user.getBmdm());
		record.setGbrid(user.getId());
		record.setZt(WorkpackageStatusEnum.CLOSETOEND.getId());
		// 清除预组包关系
		monitoringWorkpackageMapper.updateByGbid(record.getId());
		// 指定结束工包
		workpackageMapper.update4EndClose(record);
		// 日志
		commonRecService.write(record.getId(), TableEnum.B_S2_007, user.getId(), czls, LogOperationEnum.GUANBI,
				UpdateTypeEnum.CLOSE, record.getId());
	}

	/**
	 * 
	 * @Description 验证工单是否都关闭或完成
	 * @CreateTime 2017年9月28日 上午10:35:24
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @throws BusinessException
	 */
	private void validation4Workorder(String gbid) throws BusinessException {
		Integer count = workorderMapper.getCountByGbid4endClsoe(gbid);
		if (count > 0) {
			throw new BusinessException("该工包下有未指定结束或未完工关闭的工单，不能关闭!");
		}
	}

	/**
	 * 
	 * @Description 下发
	 * @CreateTime 2017年9月28日 上午10:40:43
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public void update4Issued(Workpackage record) throws BusinessException {
		//验证工包状态
		Integer current = workpackageMapper.getCurrentById(record.getId());
		if (current != WorkpackageStatusEnum.SUBMIT.getId() ) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		// 验证工包下是否有提交状态的工单
		validation4WorkorderSubmitCount(record.getId());
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		// 处理1：修改工包数据
		doWorkpackageIssued(record, user, czls);
		// 处理3：同步145工包和工单数据
		doWorkpackage145AndWorkoder145(record.getId(), user,czls);
		// 处理2：根据工包id修改135工单表数据
		doWorkorderIssued(record.getId(), user ,czls);

	}

	/**
	 * 
	 * @Description 验证工包下是否有提交状态的工单
	 * @CreateTime 2017年9月28日 上午10:53:13
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @throws BusinessException
	 */
	private void validation4WorkorderSubmitCount(String gbid) throws BusinessException {
		Integer count = workorderMapper.getSubmitCountByGbid(gbid);
		if (count == 0) {
			throw new BusinessException("该工包下没有提交状态的工单，不能下发!");
		}
	}

	/**
	 * 
	 * @Description 工包下发
	 * @CreateTime 2017年9月28日 上午11:07:01
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param user
	 */
	private void doWorkpackageIssued(Workpackage record, User user, String czls) {
		record.setWhrid(user.getId());
		record.setWhdwid(user.getBmdm());
		record.setXfrdwid(user.getBmdm());
		record.setXfrid(user.getId());
		record.setZt(WorkpackageStatusEnum.TAKEEFFECT.getId());
		workpackageMapper.update4Issued(record);// 工包下发
		// 工包日志
		commonRecService.write(record.getId(), TableEnum.B_S2_007, user.getId(), czls, LogOperationEnum.ISSUED,
				UpdateTypeEnum.UPDATE, record.getId());
	}

	/**
	 * 
	 * @Description 工单下发
	 * @CreateTime 2017年9月28日 上午11:08:18
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @param user
	 */
	private void doWorkorderIssued(String gbid, User user ,String czls) {
		Workorder workorder = new Workorder();
		workorder.setWhdwid(user.getBmdm());
		workorder.setWhrid(user.getId());
		workorder.setXfrdwid(user.getBmdm());
		workorder.setXfrid(user.getId());
		workorder.setZt(WorkorderStatusEnum.TAKEEFFECT.getId());
		workorder.setGbid(gbid);
		workorderMapper.updateByGbid4Issued(workorder);// 工单135下发
		// 工单日志
		commonRecService.writeByWhere(" b.gbid = '".concat(gbid).concat("' ").concat("and b.zt=7"), TableEnum.B_S2_008, user.getId(),czls,LogOperationEnum.ISSUED, UpdateTypeEnum.UPDATE,null);
	}

	/**
	 * 
	 * @Description 同步145工包和工单数据
	 * @CreateTime 2017年9月28日 上午11:10:41
	 * @CreateBy 岳彬彬
	 * @param id
	 * @param user
	 */
	private void doWorkpackage145AndWorkoder145(String id, User user,String czls) {
		Workpackage wp = workpackageMapper.selectByPrimaryKey(id);
		if (validation4ExistSync(wp)) {
			// 新增145工包
			Workpackage145 wp145 = addWorkpackage145(wp,user,czls);
			// 批量新增145工单数据
			addWorkorder145(id, wp145, wp,user,czls);
		}
	}

	/**
	 * 
	 * @Description 验证是否需要同步145工包和145工单
	 * @CreateTime 2017年9月28日 上午11:23:29
	 * @CreateBy 岳彬彬
	 * @param wp
	 * @return
	 */
	private Boolean validation4ExistSync(Workpackage wp) {
		// 工包是内部执行单位
		if (wp.getJhWwbs() == 0) {
			return true;
		} 
/**		目前只考虑内部执行单位，外部的不需要下发到145
  		else {
			SynRel record = new SynRel();
			record.setDprtcode(wp.getDprtcode());
			record.setDxid(wp.getJhZxdwid());
			record.setGxlx(1);
			int count = synRelMapper.getCount4Workpackage(record);
			if (count > 0) {
				return true;
			}
		}
*/
		return false;
	}

	/**
	 * 
	 * @Description 新增145工包
	 * @CreateTime 2017年10月9日 下午6:18:03
	 * @CreateBy 岳彬彬
	 * @param wp
	 */
	private Workpackage145 addWorkpackage145(Workpackage wp,User user,String czls) {
		Workpackage145 wp145 = new Workpackage145();
		wp145.setId(wp.getId());
		// 内部
		if (wp.getJhWwbs() == 0) {
			wp145.setDprtcode(wp.getDprtcode());
			wp145.setWhdwid(user.getBmdm());
			wp145.setWhrid(user.getId());
			wp145.setZdbmid(user.getBmdm());
			wp145.setZdrid(user.getId());
			wp145.setXfdwid(wp.getGbxfdwid());
			Department dprt = departmentMapper.selectByPrimaryKey(wp.getGbxfdwid());
			if(null != dprt){
				wp145.setXfdw(dprt.getDprtname());
			}
			wp145.setZxdwid(wp.getJhZxdwid());
			wp145.setZxdw(wp.getJhZxdw());
			wp145.setJhKsrq(wp.getJhKsrq());
			wp145.setJhJsrq(wp.getJhJsrq());
		} else {
			// 外部
			SynRel record = synRelMapper.getSyrelByDxidAndDprtcode(wp.getJhZxdwid(), wp.getDprtcode());
			wp145.setDprtcode(record.getGljgdm());
			List<SynRel> list = synRelMapper.getSyrelByParams(wp.getDprtcode(), record.getGljgdm());
			if (list.size() > 0) {
				wp145.setXfdwid(list.get(0).getDxid());
				wp145.setXfdw(list.get(0).getObjText());
			}
		}
		Aircraftinfo aircraftinfo = new Aircraftinfo();
		aircraftinfo.setFjzch(wp.getFjzch());
		aircraftinfo.setDprtcode(wp.getDprtcode());
		aircraftinfo = aircraftinfoMapper.getFjByFjzchAndDprtcode(aircraftinfo);
		if(aircraftinfo!=null){
			wp145.setFjjx(aircraftinfo.getFjjx());
			wp145.setFjxlh(aircraftinfo.getXlh());
		}	
		wp145.setWhsj(new Date());
		wp145.setZdrq(new Date());
		wp145.setZt(WorkpackageStatusEnum.SUBMIT.getId());
		wp145.setWgbs(FeedbackStatusEnum.NO.getId());
		wp145.setGbbh(wp.getGbbh());
		wp145.setGbmc(wp.getGbmc());
		wp145.setFjzch(wp.getFjzch());
		wp145.setWxlx(wp.getWxlx());
		wp145.setWbbs(wp.getJhWwbs());
		wp145.setGzyq(wp.getGzyq());
		workpackage145Mapper.insertSelective(wp145);
		commonRecService.write(wp145.getId(), TableEnum.B_S2_010, user.getId(), czls, LogOperationEnum.SAVE_WO,
				UpdateTypeEnum.SAVE, wp145.getId());
		return wp145;
	}

	/**
	 * 
	 * @Description 批量新增145工单
	 * @CreateTime 2017年10月9日 下午6:18:43
	 * @CreateBy 岳彬彬
	 * @param gbid
	 */
	private void addWorkorder145(String gbid, Workpackage145 wp145, Workpackage wp,User user,String czls) {
		List<Workorder> woList = workorderMapper.getSubmitWorkorderByGbid(gbid);
		// 处理145工单表数据
		doWorkorder145(woList, wp145, wp,user,czls);
	}

	/**
	 * 
	 * @Description 新增145工单
	 * @CreateTime 2017年10月10日 上午9:59:53
	 * @CreateBy 岳彬彬
	 * @param woList
	 * @param wp145
	 */
	private void doWorkorder145(List<Workorder> woList, Workpackage145 wp145, Workpackage wp,User user,String czls) {
		String gzzId = workGroupMapper.getId4Mrbs(wp145.getDprtcode());
		for (Workorder workorder : woList) {
			String id = UUID.randomUUID().toString();
			// 拆装记录明细
			addWorkorder145IORecord(id, workorder.getId(), wp145,user ,czls);
			// 新增b_g2_993工种工时
			addWorkHours(id, workorder.getId(), wp145,user,czls);
			// 新增b_g2_995接近（盖板）/区域/站位
			addCoverPlate(id, workorder.getId(), wp145,user,czls);
			// 新增b_g2_996工作内容
			addWorkContent(id, workorder.getId(), wp145,user,czls);
			// 新增b_g2_997 器材/工具
			addMaterialTool(id, workorder.getId(), wp145,user,czls);
			// 新增b_g2_999参考文件
			addReference(id, workorder.getId(), wp145,user,czls);
			// 新增工单145数据
			String gzzid = addWorkOrder145(workorder, id, wp145, wp, user, czls, gzzId);
			//新增工单145附件
			addWorkorder145Attachment(id, workorder.getId(), wp145,user,czls);
			//新增工时工序
			addWorkorder145Gsgx(gzzid,id,workorder,wp145,user,czls);
			//更改下达指令状态
			instructionsourceMapper.updateYwdjztByZlid(WorkorderStatusEnum.TAKEEFFECT.getId(), workorder.getId());
		}

	}

	/**
	 * 
	 * @Description 新增工单145拆装记录明细
	 * @CreateTime 2017年10月9日 下午7:08:33
	 * @CreateBy 岳彬彬
	 * @param id
	 * @param mainid
	 * @param wp145
	 */
	private void addWorkorder145IORecord(String id, String mainid, Workpackage145 wp145,User user,String czls) {
		Workorder145IORecord record = new Workorder145IORecord();
		record.setId(id);
		record.setMainid(mainid);
		record.setWhdwid(wp145.getWhdwid());
		record.setWhrid(wp145.getWhrid());
		workorder145IORecordMapper.insertWorkorder145IORecordList(record);
	}

	/**
	 * 
	 * @Description 新增工单145工种工时
	 * @CreateTime 2017年10月10日 上午10:07:20
	 * @CreateBy 岳彬彬
	 * @param id
	 * @param mainid
	 * @param wp145
	 */
	private void addWorkHours(String id, String mainid, Workpackage145 wp145,User user,String czls) {
		WorkHour wh = new WorkHour();
		wh.setId(id);
		wh.setYwid(mainid);
		wh.setDprtcode(wp145.getDprtcode());
		wh.setWhdwid(wp145.getWhdwid());
		wh.setWhrid(wp145.getWhrid());
		workHourMapper.insertByCopyWorkhours(wh);
		commonRecService.writeByWhere(" b.ywid = '".concat(id).concat("' ").concat("and b.zt=1"), TableEnum.B_G2_993, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,id);
	}

	/**
	 * 
	 * @Description 新增145工单下接近（盖板）/区域/站位
	 * @CreateTime 2017年10月10日 上午10:42:13
	 * @CreateBy 岳彬彬
	 * @param id
	 * @param mainid
	 * @param wp145
	 */
	private void addCoverPlate(String id, String mainid, Workpackage145 wp145,User user ,String czls) {
		CoverPlate record = new CoverPlate();
		record.setId(id);
		record.setYwid(mainid);
		record.setDprtcode(wp145.getDprtcode());
		record.setWhdwid(wp145.getWhdwid());
		record.setWhrid(wp145.getWhrid());
		coverPlateMapper.insertByCopyCoverPlate(record);
		commonRecService.writeByWhere(" b.ywid = '".concat(id).concat("' "), TableEnum.B_G2_995, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,id);
	}

	/**
	 * 
	 * @Description 新增工单145工作内容
	 * @CreateTime 2017年10月10日 上午11:01:40
	 * @CreateBy 岳彬彬
	 * @param id 新ywid
	 * @param mainid 老ywid
	 * @param wp145
	 */
	private void addWorkContent(String id, String mainid, Workpackage145 wp145,User user,String czls) {
		WorkContent record = new WorkContent();
		record.setId(id);
		record.setYwid(mainid);
		record.setDprtcode(wp145.getDprtcode());
		record.setWhdwid(wp145.getWhdwid());
		record.setWhrid(wp145.getWhrid());
		workContentMapper.insertByCopyWorkContent(record);
		commonRecService.writeByWhere(" b.ywid = '".concat(id).concat("' "), TableEnum.B_G2_996, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,id);
	
	}

	/**
	 * 
	 * @Description 新增145工单 器材/工具
	 * @CreateTime 2017年10月10日 上午10:47:11
	 * @CreateBy 岳彬彬
	 * @param id
	 * @param mainid
	 * @param wp145
	 */
	private void addMaterialTool(String id, String mainid, Workpackage145 wp145,User user,String czls) {
		MaterialTool record = new MaterialTool();
		record.setId(id);
		record.setYwid(mainid);
		record.setDprtcode(wp145.getDprtcode());
		record.setWhdwid(wp145.getWhdwid());
		record.setWhrid(wp145.getWhrid());
		materialToolMapper.insertByCopyMaterialTool(record);
		commonRecService.writeByWhere(" b.ywid = '".concat(id).concat("' "), TableEnum.B_G2_997, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,id);
	}

	/**
	 * 
	 * @Description 新增145工单参考文件
	 * @CreateTime 2017年10月10日 上午10:53:42
	 * @CreateBy 岳彬彬
	 * @param id
	 * @param mainid
	 * @param wp145
	 */
	private void addReference(String id, String mainid, Workpackage145 wp145,User user ,String czls) {
		Reference record = new Reference();
		record.setId(id);
		record.setYwid(mainid);
		record.setDprtcode(wp145.getDprtcode());
		record.setWhdwid(wp145.getWhdwid());
		record.setWhrid(wp145.getWhrid());
		referenceMapper.insertByCopyReference(record);
		commonRecService.writeByWhere(" b.ywid = '".concat(id).concat("' "), TableEnum.B_G2_999, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,id);
	}

	/**
	 * 
	 * @Description 新增145工单
	 * @CreateTime 2017年10月10日 上午11:35:04
	 * @CreateBy 岳彬彬
	 * @param workorder 135工单
	 * @param id 145工单id
	 * @param wp145 145工包
	 * @param wp 135工包
	 */
	private String addWorkOrder145(Workorder workorder, String id, Workpackage145 wp145, Workpackage wp,User user,String czls, String gzzId) {
		Workorder145 record = new Workorder145();
		record.setId(id);
		record.setGdsbid(workorder.getGdsbid());
		record.setDprtcode(wp145.getDprtcode());
		record.setWhdwid(wp145.getWhdwid());
		record.setWhrid(wp145.getWhrid());
		record.setWhsj(new Date());
		record.setZdbmid(wp145.getZdbmid());
		record.setZdrid(wp145.getZdrid());
		record.setZdrq(new Date());
		record.setZt(2);
		record.setXmblbs(0);
		record.setWgbs(FeedbackStatusEnum.NO.getId());
		record.setJksjid(workorder.getJksjid());
		record.setGbid(wp145.getId());
		// 处理工卡id、工卡编号、报告人、工作内容附件
		doGk(workorder, record, wp,user,czls);
		//获取工卡的工作组
		if(null != record.getGkid() && !"".equals(record.getGkid())){
			WorkCard wc = workCardMapper.selectByPrimaryKey(record.getGkid());
			if(null != wc && StringUtils.isNotBlank(wc.getGzzid()) && !"-".equals(wc.getGzzid())){
				gzzId = wc.getGzzid();
			}	
		}
		record.setGzzid(gzzId);
		record.setDjbgdid(workorder.getDjbgdid());
		record.setGdbh(workorder.getGdbh());
		record.setGdlx(workorder.getGdlx());
		record.setGdbt(workorder.getGdbt());
		record.setLyrwh(workorder.getLyrwh());
		record.setLyrwid(workorder.getLyrwid());
		Aircraftinfo aircraftinfo = new Aircraftinfo();
		aircraftinfo.setFjzch(workorder.getFjzch());
		aircraftinfo.setDprtcode(workorder.getDprtcode());
		aircraftinfo = aircraftinfoMapper.getFjByFjzchAndDprtcode(aircraftinfo);
		if(aircraftinfo!=null){
			record.setFjjx(aircraftinfo.getFjjx());
			record.setFjxlh(aircraftinfo.getXlh());
		}		
		record.setFjzch(workorder.getFjzch());
		record.setZjh(workorder.getZjh());
		record.setBjbs(workorder.getBjbs());
		record.setKdrq(workorder.getKdrq());
		record.setJhKsrq(workorder.getJhKsrq());
		record.setJhJsrq(workorder.getJhJsrq());
		record.setZy(workorder.getZy());
		record.setGzlb(workorder.getGzlb());
		record.setJhgsRs(workorder.getJhgsRs());
		record.setJhgsXss(workorder.getJhgsXss());
		record.setJhZd(workorder.getJhZd());
		record.setYbgs(workorder.getYbgs());
		record.setQxms(workorder.getQxms());
		record.setJyclfa(workorder.getJyclfa());
		record.setDxfbs(10);
		workorder145Mapper.insertSelective(record);
		// 工包日志
		commonRecService.write(record.getId(), TableEnum.B_S2_014, user.getId(), czls, LogOperationEnum.SUBMIT_WO,
				UpdateTypeEnum.SAVE, record.getId());
		return gzzId;
	}
	
	 /**
	  * 
	  * @Description 处理工卡id、工卡编号、报告人、工作内容附件
	  * @CreateTime 2017年10月10日 下午1:56:59
	  * @CreateBy 岳彬彬
	  * @param workorder 135工单
	  * @param record 145工单
	  * @param wp  135工包
	  */
	private void doGk(Workorder workorder, Workorder145 record, Workpackage wp ,User user,String czls) {
		if (wp.getJhWwbs() == 0) {
			record.setGkid(workorder.getGkid());
			record.setGkbh(workorder.getGkbh());
			record.setBgrid(workorder.getBgrid());
			record.setBgr(workorder.getBgr());
			record.setGznrfjid(workorder.getGznrfjid());
		} else {
			if (workorder.getGkbh() != null && !"".equals(workorder.getGkbh())) {
				Aircraftinfo aircraftinfo = new Aircraftinfo();
				aircraftinfo.setFjzch(wp.getFjzch());
				aircraftinfo.setDprtcode(record.getDprtcode());
				aircraftinfo = aircraftinfoMapper.getFjByFjzchAndDprtcode(aircraftinfo);
				if(aircraftinfo!=null){
					WorkCard wc = workCardMapper.getWorkCardByjxAndGkbh(aircraftinfo.getFjjx(), workorder.getGkbh(),
							record.getDprtcode());
					if (wc != null && wc.getGkh() != null && !"".equals(wc.getGkh())) {
						record.setGkbh(wc.getGkh());
						record.setGkid(wc.getId());
					}
				}
				
			} else {
				if (workorder.getGdlx() == 1) {
					Aircraftinfo aircraftinfo = new Aircraftinfo();
					aircraftinfo.setFjzch(wp.getFjzch());
					aircraftinfo.setDprtcode(wp.getDprtcode());
					aircraftinfo = aircraftinfoMapper.getFjByFjzchAndDprtcode(aircraftinfo);
					if(aircraftinfo!=null){
						WorkCard wc = workCardMapper.getWorkCardByjxAndWxxmbh(aircraftinfo.getFjjx(), workorder.getLyrwh(),
								record.getDprtcode());
						if (wc != null && wc.getGkh() != null && !"".equals(wc.getGkh())) {
							record.setGkbh(wc.getGkh());
							record.setGkid(wc.getId());
						}	
					}				
				}
			}
			//处理工作内容附件
			if(record.getGznrfjid()!=null&&!"".equals(record.getGznrfjid())){
				String gznrfjid = UUID.randomUUID().toString();
				record.setGznrfjid(gznrfjid);
				Attachment attachment = new Attachment();
				attachment.setMainid(workorder.getGznrfjid());
				attachment.setId(gznrfjid);
				attachmentMapper.insertAttachemnt4Copy(attachment);
				// 保存附件信息
				commonRecService.writeByWhere(" b.mainid = '".concat(gznrfjid).concat("' "), TableEnum.D_011, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,record.getId());
			}
		}
	}
	/**
	 * 
	 * @Description 新增145工单附件
	 * @CreateTime 2017年10月20日 下午1:54:42
	 * @CreateBy 岳彬彬
	 * @param id
	 * @param mainid
	 * @param wp145
	 * @param user
	 * @param czls
	 */
	private void addWorkorder145Attachment(String id, String mainid, Workpackage145 wp145,User user ,String czls){
		Attachment attachment = new Attachment();
		attachment.setMainid(mainid);
		attachment.setId(id);
		attachment.setDprtcode(wp145.getDprtcode());
		attachmentMapper.insertAttachemnt4Copy(attachment);
		commonRecService.writeByWhere(" b.mainid = '".concat(id).concat("' "), TableEnum.D_011, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,id);
	}
	/**
	 * 
	 * @Description 新增145工单下工序工时
	 * @CreateTime 2017年10月9日 下午7:02:28
	 * @CreateBy 岳彬彬
	 * @param woList
	 * @param wp145
	 */
	private void addWorkorder145Gsgx(String gzzid, String id, Workorder workorder,Workpackage145 wp145,User user,String czls ) {
			Workorder145Hours woh = null;
			woh = new Workorder145Hours();
			woh.setId(UUID.randomUUID().toString());
			woh.setMainid(id);
			woh.setZt(1);
			woh.setGzzid(gzzid);
			woh.setWhdwid(wp145.getWhdwid());
			woh.setWhrid(wp145.getWhrid());
			woh.setWhsj(new Date());
			woh.setGxbh("0010");
			if(workorder.getJhgsXss()!=null && workorder.getJhgsRs()!=null){
				woh.setJhgs(workorder.getJhgsXss().multiply(new BigDecimal(workorder.getJhgsRs())));				
			}
			workorder145HoursMapper.insertSelective(woh);
			commonRecService.writeByWhere(" b.mainid = '".concat(id).concat("' "), TableEnum.B_S2_01402, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,id);
	}

	/**
	 * 
	 * @Description 完工反馈
	 * @CreateTime 2017年9月28日 上午11:37:52
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public void update4Feedback(Workpackage record) throws BusinessException {
		// 验证工包状态为7下发状态
		validation4Issued(record);
		// 实际日期、单位的处理
		doData(record);
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		record.setWhrid(user.getId());
		record.setWhdwid(user.getBmdm());
		record.setWgbs(FeedbackStatusEnum.YES.getId());
		workpackageMapper.update4Feedback(record);// 完工反馈
		// 完工反馈附件
		attachmentService.eidtAttachment(record.getAttachments(), record.getId(), czls, record.getId(),
				record.getDprtcode(), LogOperationEnum.FEEDBACK);
		// 工包日志
		commonRecService.write(record.getId(), TableEnum.B_S2_007, user.getId(), czls, LogOperationEnum.FEEDBACK,
				UpdateTypeEnum.UPDATE, record.getId());
	}

	/**
	 * 
	 * @Description 验证工包状态为7下发状态
	 * @CreateTime 2017年9月28日 上午11:44:08
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private void validation4Issued(Workpackage record) throws BusinessException {
		Workpackage wp = workpackageMapper.selectByPrimaryKey(record.getId());
		if (wp.getZt() != WorkpackageStatusEnum.TAKEEFFECT.getId()) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}

	/**
	 * 
	 * @Description 实际日期、单位的处理
	 * @CreateTime 2017年9月28日 下午3:25:14
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param wp
	 * @throws BusinessException
	 */
	@SuppressWarnings("deprecation")
	private void doData(Workpackage record) throws BusinessException {
		if (record.getSjJsrq() != null ) {
			String sjjssj = (String) record.getParamsMap().get("sjJssj");
			Date jhjsrq = record.getSjJsrq();
			if (sjjssj != null && !"".equals(sjjssj)) {
				String[] sj = sjjssj.split(":");
				jhjsrq.setHours(Integer.valueOf(sj[0]));
				jhjsrq.setMinutes(Integer.valueOf(sj[1]));
			}else{
				jhjsrq.setHours(Integer.valueOf(0));
				jhjsrq.setMinutes(Integer.valueOf(0));
			}
			jhjsrq.setSeconds(0);
			record.setSjJsrq(jhjsrq);
		}
	}

	/**
	 * 
	 * @Description 完工关闭
	 * @CreateTime 2017年9月28日 下午1:37:24
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public void update4Close(Workpackage record) throws BusinessException {
		// 验证工包状态为7下发状态
		validation4Issued(record);
		// 验证工包下工单状态是否都关闭或完成
		validation4Workorder(record.getId());
		// 验证预组包中是否有工包
		validation(record.getId());
		// 实际日期、单位的处理
		doData(record);
		// 设置参数
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		record.setWhrid(user.getId());
		record.setWhdwid(user.getBmdm());
		record.setGbrid(user.getId());
		record.setZt(WorkpackageStatusEnum.CLOSETOFINISH.getId());
		record.setWgbs(FeedbackStatusEnum.YES.getId());
		workpackageMapper.update4Close(record);// 完工关闭
		// 完工关闭附件
		attachmentService.eidtAttachment(record.getAttachments(), record.getId(), czls, record.getId(),
				record.getDprtcode(), LogOperationEnum.WANCHEN);
		// 工包日志
		commonRecService.write(record.getId(), TableEnum.B_S2_007, user.getId(), czls, LogOperationEnum.WANGONG,
				UpdateTypeEnum.UPDATE, record.getId());
	}

	/**
	 * 
	 * @Description 验证预组包中是否有工包
	 * @CreateTime 2017年10月9日 下午4:48:45
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @throws BusinessException
	 */
	private void validation(String gbid) throws BusinessException {
		int count = monitoringWorkpackageMapper.getCountByGbid(gbid);
		if (count > 0) {
			throw new BusinessException("预组包中存在该工包不能关闭!");
		}
	}

	/**
	 * 
	 * @Description 获取工包明细
	 * @CreateTime 2017年9月30日 上午10:22:24
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @param dprtcode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getWorkpackageDetail(Workpackage record) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		List<String> lx = (List<String>) record.getParamsMap().get("lx");
		if (lx != null && !lx.isEmpty() && lx.size() > 0) {
			List<Integer> gdzlx = new ArrayList<Integer>();
			List<Integer> gdlx = new ArrayList<Integer>();
			for (String i : lx) {
				if (!"5".equals(i) && !"6".equals(i) && !"7".equals(i) && !"8".equals(i)) {
					gdzlx.add(Integer.valueOf(i));
				}
				if ("5".equals(i)) {
					gdlx.add(2);
				}
				if ("6".equals(i)) {
					gdlx.add(3);
				}
				if ("7".equals(i)) {
					gdlx.add(4);
				}
				if ("8".equals(i)) {
					gdlx.add(5);
				}
			}
			if (gdzlx.size() > 0) {
				searchMap.put("gdzlx", gdzlx);
			}
			if (gdlx.size() > 0) {
				searchMap.put("gdlx", gdlx);
			}
		}
		searchMap.put("gbid", record.getParamsMap().get("gbid"));
		if (record.getParamsMap().get("keyword") != null && !"".equals(record.getParamsMap().get("keyword"))) {
			searchMap.put("keyword", record.getParamsMap().get("keyword"));
		}

		List<Map<String, Object>> workorderList = workorderMapper.getWorkorderListByGbid(searchMap);
//		List<Map<String, Object>> hcList = workorderMapper.getHcToolDetail((String) record.getParamsMap().get("gbid"),
//				(String) record.getParamsMap().get("dprtcode"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workorderList", workorderList);
//		map.put("hcList", hcList);
		return map;
	}

	/**
	 * @Description 飞行记录本查询工包数据
	 * @CreateTime 2017年10月17日 下午3:04:16
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public List<Workpackage> query4FLB(Workpackage record) {
		return workpackageMapper.query4FLB(record);
	}
	
	/**
	 * @Description 同步145工包和工单数据 
	 * @CreateTime 2017-10-17 下午6:54:30
	 * @CreateBy 刘兵
	 * @param workpackage 工包
	 * @param user 用户
	 * @param workorderList 工单集合
	 * @param czls 操作流水
	 */
	@Override
	public void doWorkpackageAndWorkoder145(Workpackage wp, List<Workorder> workorderList, User user, String czls) {
		if (validation4ExistSync(wp)) {
			// 获取145工包
			Workpackage145 wp145 = workpackage145Mapper.selectByPrimaryKey(wp.getId());
			List<Workorder> woList = workorderMapper.getSubmitWorkorderByGbid(wp.getId());
			woList.addAll(workorderList);
			// 处理145工单表数据
			doWorkorder145(woList, wp145 , wp, user, czls);
		}
	}
	
	/**
	 * @Description 根据条件查询工包信息
	 * @CreateTime 2017-10-19 下午2:37:19
	 * @CreateBy 刘兵
	 * @param record 工包
	 * @return List<Workpackage>工包集合
	 */
	@Override
	public List<Workpackage> queryWorkpackageList(Workpackage record){
		return workpackageMapper.queryWorkpackageList(record);
	}
	
	/**
	 * 
	 * @Description 自动下发工单
	 * @CreateTime 2017年10月26日 上午10:02:05
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @param idList
	 */
	@Override
	public void doAutoIssuedWorkorder(String gbid, List<String> list) {
		Workpackage wp = workpackageMapper.selectByPrimaryKey(gbid);
		//工包是下发状态的工单，就要处理工包下工单
		if(wp!=null && wp.getZt()==WorkpackageStatusEnum.TAKEEFFECT.getId()){		
			List<Workorder> woList = workorderMapper.selectByIdList(list);
			List<Workorder> workorderList = new ArrayList<Workorder>();
			if(woList!=null && woList.size()>0){
				List<String> idList = new ArrayList<String>();
				for (Workorder workorder : woList) {
					if(workorder.getZt()==WorkorderStatusEnum.SUBMIT.getId()){
						idList.add(workorder.getId());
						workorderList.add(workorder);
					}
				}
				if(idList.size()>0){
					User user = ThreadVarUtil.getUser();
					String czls = UUID.randomUUID().toString();
					Workorder wo = new Workorder();
					wo.setGbid(gbid);
					wo.setWhrid(user.getId());
					wo.setWhdwid(user.getBmdm());
					wo.setXfrid(user.getId());
					wo.setXfrdwid(user.getBmdm());
					wo.setZt(WorkorderStatusEnum.TAKEEFFECT.getId());
					wo.getParamsMap().put("idList", idList);
					//修改工单
					workorderMapper.update4Issued(wo);
					commonRecService.write("id", idList, TableEnum.B_S2_008, user.getId(), czls, LogOperationEnum.ISSUED, UpdateTypeEnum.UPDATE, null);
					Workpackage145 wp145 = workpackage145Mapper.selectByPrimaryKey(wp.getId());
					//验证工包是否下发到145,存在标识工包已下发过去,不存在工包可能下发下去但是被删除，也有可能不满足下发到145
					if(wp145!=null){
						// 处理145工单表数据
						doWorkorder145(workorderList, wp145, wp,user,czls);
					}
				}
			}		
		}
	}
	
	/**
	 * 
	 * @Description 修订
	 * @CreateTime 2017年11月27日 上午10:17:31
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException 
	 */
	@Override
	public void update4Revision(Workpackage record) throws BusinessException {
		Workpackage wp = workpackageMapper.selectByPrimaryKey(record.getId());
		if (wp.getZt() != WorkpackageStatusEnum.CLOSETOEND.getId() && wp.getZt() != WorkpackageStatusEnum.CLOSETOFINISH.getId()) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		record.setWhrid(user.getId());
		record.setWhdwid(user.getBmdm());
		record.setGbrid(user.getId());
		if(wp.getZt() == WorkpackageStatusEnum.CLOSETOEND.getId()){
			record.setZt(WorkpackageStatusEnum.CLOSETOEND.getId());
			// 指定结束工包
			workpackageMapper.update4EndClose(record);
			// 日志
			commonRecService.write(record.getId(), TableEnum.B_S2_007, user.getId(), czls, LogOperationEnum.REVISE,
					UpdateTypeEnum.UPDATE, record.getId());
		}
		if(wp.getZt() == WorkpackageStatusEnum.CLOSETOFINISH.getId()){
			record.setZt(WorkpackageStatusEnum.CLOSETOFINISH.getId());
			record.setWgbs(FeedbackStatusEnum.YES.getId());
			// 实际日期、单位的处理
			doData(record);
			workpackageMapper.update4Close(record);// 完工关闭
			// 完工关闭附件
			attachmentService.eidtAttachment(record.getAttachments(), record.getId(), czls, record.getId(),
					record.getDprtcode(), LogOperationEnum.WANCHEN);
			// 工包日志
			commonRecService.write(record.getId(), TableEnum.B_S2_007, user.getId(), czls, LogOperationEnum.REVISE,
					UpdateTypeEnum.UPDATE, record.getId());
		}
		
	}
	/**
	 * 
	 * @Description 获取下发状态下的工包
	 * @CreateTime 2017年12月12日 下午4:55:26
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 */
	@Override
	public List<Workpackage> getIssuedWp(Workpackage record) {
		
		return workpackageMapper.getIssuedWp(record);
	}
}