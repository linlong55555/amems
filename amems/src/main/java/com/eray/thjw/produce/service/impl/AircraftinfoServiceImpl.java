package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.dao.DeptInfoMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.DeptInfo;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.AircraftinfoMapper;
import com.eray.thjw.produce.dao.AircraftinfoStatusMapper;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.po.PlanInit;
import com.eray.thjw.produce.po.PlaneBasic;
import com.eray.thjw.produce.service.AircraftinfoService;
import com.eray.thjw.produce.service.InstallationListEditableService;
import com.eray.thjw.produce.service.MonitorDataService;
import com.eray.thjw.produce.service.PlanInitService;
import com.eray.thjw.produce.service.PlanUsageService;
import com.eray.thjw.produce.service.PlaneBasicService;
import com.eray.thjw.project2.service.EOApplicabilityService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.common.PartSnValidationEnum;
import enu.produce.InstalledPositionEnum;
import enu.produce.InstalledStatusEnum;
import enu.project2.MonitorProjectEnum;

/**
 * 
 * @Description 飞机基本信息impl
 * @CreateTime 2017年9月19日 上午11:15:50
 * @CreateBy 林龙
 */
@Service("aircraftinfoService")
public class AircraftinfoServiceImpl implements AircraftinfoService {

	@Resource
	private AircraftinfoMapper aircraftinfoMapper;
	@Resource
	private PlaneModelDataService planeModelDataService;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private PlanInitService planInitService;
	@Resource
	private PlanUsageService planUsageService;
	@Resource
	private AttachmentMapper attachmentMapper;
	@Resource
	private DeptInfoMapper deptInfoMapper;
	@Resource
	private InstallationListEditableService installationListEditableService;
	@Resource
	private EOApplicabilityService eOApplicabilityService;
	@Resource
	private MonitorDataService monitorDataService;
	@Resource
	private AircraftinfoStatusMapper aircraftinfoStatusMapper;
	@Resource
	private StockSerivce stockSerivce;
	@Resource
	private PlaneBasicService planeBasicService;

	/**
	 * @Description 飞机基本信息分页
	 * @CreateTime 2017年9月19日 上午11:17:10
	 * @CreateBy 林龙
	 * @param aircraftinfo
	 *            飞机基本信息
	 * @return 飞机基本信息map集合
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAllPageList(Aircraftinfo aircraftinfo) throws BusinessException {
		// 用户刚编辑过的记录 飞机注册号，组织机构
		String fjzch = aircraftinfo.getFjzch();
		String dprtcode = aircraftinfo.getDprtcode();
		// 清除查询条件中的飞机注册号，组织机构，保证列表查询结果集的正确性
		aircraftinfo.setFjzch(null);
		PageHelper.startPage(aircraftinfo.getPagination());
		List<Aircraftinfo> list = aircraftinfoMapper.queryAllPageList(aircraftinfo);
		if (((Page) list).getTotal() > 0) {
			if (StringUtils.isNotBlank(fjzch)) { // 判断飞机注册号是否为空，不为空则表示需要查询最近编辑的业务记录
				if (!PageUtil.hasRecord(list, "fjzch", fjzch)) {
					Aircraftinfo param = new Aircraftinfo();
					param.setFjzch(fjzch);
					param.setDprtcode(dprtcode);
					List<Aircraftinfo> newRecordList = aircraftinfoMapper.queryAllPageList(param);
					if (newRecordList != null && newRecordList.size() == 1) {
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, aircraftinfo.getPagination());
		} else {
			List<Aircraftinfo> newRecordList = new ArrayList<Aircraftinfo>(1);
			if (StringUtils.isNotBlank(fjzch)) { // 判断飞机注册号是否为空，不为空则表示需要查询最近编辑的业务记录
				Aircraftinfo param = new Aircraftinfo();
				param.setFjzch(fjzch);
				param.setDprtcode(dprtcode);
				newRecordList = aircraftinfoMapper.queryAllPageList(param);
				if (newRecordList != null && newRecordList.size() == 1) {
					return PageUtil.pack(1, newRecordList, aircraftinfo.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, aircraftinfo.getPagination());
		}
	}

	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2017-9-21 上午11:09:46
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String insert(Aircraftinfo aircraftinfo) throws BusinessException {
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		// 操作指令
		LogOperationEnum cz = LogOperationEnum.SAVE_WO;
		aircraftinfo.setDprtcode(user.getJgdm());
		// 附件id
		aircraftinfo.setGjdjzfjid(UUID.randomUUID().toString());
		aircraftinfo.setShzfjid(UUID.randomUUID().toString());
		aircraftinfo.setWxdtzzfjid(UUID.randomUUID().toString());
		
		// 验证账号注册数量
		int fjCount = aircraftinfoMapper.queryCountfj(ThreadVarUtil.getUser().getJgdm());

		// 查询账号注册最大数量
		DeptInfo accounts = deptInfoMapper.selectCounts(ThreadVarUtil.getUser().getJgdm());
		if (accounts != null) {
			if (accounts.getZcfj_max() != null && fjCount >= accounts.getZcfj_max() && accounts.getZcfj_max() != 0) {
				throw new BusinessException("飞机注册号不能超过组织机构的最大飞机注册数量！");
			}
		}

		// 验证飞机注册号唯一性
		int countFjzch = aircraftinfoMapper.findByFjzch(aircraftinfo);
		// 验证MSN唯一性
		int countXlh = aircraftinfoMapper.findByXlh(aircraftinfo);
		if (countFjzch > 0) {
			throw new BusinessException("飞机注册号出现重复,请修改后再进行操作！");
		}
		if (countXlh > 0) {
			throw new BusinessException("MSN出现重复,请修改后再进行操作！");
		}
		// 验证是否拥有机型权限
		List<String> list = new ArrayList<String>();
		list.add(aircraftinfo.getFjjx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), user.getJgdm(), list);
		
		// 验证部件号，序列号数据唯一
//		this.validata(aircraftinfo.getPlanInitList(),aircraftinfo.getDprtcode());
				
		// 1.新增飞机基本信息
		this.addPlane(aircraftinfo, czls, cz);
		
		// 添加装机清单数据，将飞机初始化数据中填入装机清单ID
		this.installationListEditableService.saveInstall4PlaneAdd(aircraftinfo.getFjjx(), aircraftinfo.getFjzch(), user.getJgdm(), aircraftinfo.getPlanInitList(), czls);
		
		// 2.新增飞机初始化数据
		planInitService.add(aircraftinfo.getPlanInitList(), aircraftinfo.getFjzch(), user.getJgdm(),
				UpdateTypeEnum.SAVE, czls, cz);
		// 3.新增飞机日使用量监控指标
		planUsageService.add(aircraftinfo.getPlanUsageList(), aircraftinfo.getFjzch(), user.getJgdm(),
				UpdateTypeEnum.SAVE, czls, cz);
		// 4.新增附件
		this.addDtzzfjs(aircraftinfo.getAttachmentList(), aircraftinfo.getFjzch(), czls, cz, aircraftinfo);
		
		// 5.添加飞机基础数据（D_010）
		planeBasicService.insertUpdate(aircraftinfo);
		// 新增监控数据
		// 新增EO执行对象
		this.eOApplicabilityService.addExecObject(aircraftinfo.getDprtcode(), aircraftinfo.getFjjx(), aircraftinfo.getFjzch(), aircraftinfo.getXlh());
		List<PlanInit> planeInitList = aircraftinfo.getPlanInitList();
		String strCalInit = null;//飞机日历初始值
		for(PlanInit planeInit : planeInitList){
			if(planeInit.getWz().intValue() == InstalledPositionEnum.BODY.getId().intValue() 
					&& MonitorProjectEnum.isCalendar(planeInit.getJklbh())){
				strCalInit = planeInit.getCsz();
				break;
			}
		}
		this.monitorDataService.add4AddPlane(aircraftinfo.getDprtcode(), aircraftinfo.getFjjx(), aircraftinfo.getFjzch(), aircraftinfo.getXlh(), strCalInit);
		return aircraftinfo.getFjzch();
	}
	// 验证部件号，序列号数据唯一
/**	private void validata(List<PlanInit> planInitList ,String drptcode) throws BusinessException{
		List<Stock> stockList = new ArrayList<Stock>();
		for (PlanInit planInit : planInitList) {
			if(planInit.getJh() != null && planInit.getXlh() != null){
				Boolean b = true;
				if(stockList != null && stockList.size() > 0 ){
					for (Stock stock : stockList) {
						if(stock.getSn().equals(planInit.getXlh()) && stock.getBjh().equals(planInit.getJh())){
							b = false;
						}
					}
				}
				if(b){
					Stock stock = new Stock();
					stock.setBjh(planInit.getJh());
					stock.setSn(planInit.getXlh());
					stock.setDprtcode(drptcode);
					stockList.add(stock);
				}
			}
		}
		stockSerivce.getCount4ValidationBjAndXlh(stockList, PartSnValidationEnum.ACREG.getId());
	}
*/
	// 添加飞机基本信息
	private void addPlane(Aircraftinfo aircraftinfo, String czls, LogOperationEnum logOperationEnum) {
		User user = ThreadVarUtil.getUser();
		aircraftinfo.setZt(EffectiveEnum.YES.getId());
		aircraftinfo.setWhrid(user.getId());
		aircraftinfo.setWhbmid(user.getBmdm());
		aircraftinfo.setWhsj(new Date());
		aircraftinfoMapper.insertSelective(aircraftinfo);
		// 3.插入日志
		commonRecService.writeByWhere(
				" b.fjzch = '" + aircraftinfo.getFjzch().replaceAll("'", "''") + "' and b.dprtcode = '" + user.getJgdm()
						+ "'",
				TableEnum.D_007, user.getId(), czls, logOperationEnum, UpdateTypeEnum.SAVE,
				aircraftinfo.getFjzch().concat(",").concat(user.getJgdm()));
	}

	// 新增附件
	private void addDtzzfjs(List<Attachment> dtzzfjs, String fjzch, String czls, LogOperationEnum logOperationEnum,
			Aircraftinfo aircraftinfo) {
		if (dtzzfjs != null && !dtzzfjs.isEmpty()) {
			User user = ThreadVarUtil.getUser();
			List<String> columnValueList = new ArrayList<String>();
			for (Attachment attachment : dtzzfjs) {
				if (attachment.getType() == 1) {
					attachment.setMainid(aircraftinfo.getGjdjzfjid());
				} else if (attachment.getType() == 2) {
					attachment.setMainid(aircraftinfo.getShzfjid());
				} else {
					attachment.setMainid(aircraftinfo.getWxdtzzfjid());
				}

				/* attachment.setMainid(fjzch); */
				attachment.setDprtcode(user.getJgdm());
				attachment.setId(UUID.randomUUID().toString());
				attachment.setCzrid(user.getId());
				if (user.getBmdm() == null) {
					attachment.setCzbmid("");
				} else {
					attachment.setCzbmid(user.getBmdm());
				}
				attachmentMapper.addFile(attachment);
				columnValueList.add(attachment.getId());
			}
			commonRecService.write("id", columnValueList, TableEnum.D_011, user.getId(), czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE, fjzch.concat(",").concat(user.getJgdm()));
		}
	}

	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2017-9-22 上午10:05:44
	 * @CreateBy 孙霁
	 * @param id
	 * @return Aircraftinfo
	 * @throws BusinessException
	 */
	@Override
	public Aircraftinfo selectByfjzchAndDprtcode(Aircraftinfo aircraftinfo) throws BusinessException {
		return aircraftinfoMapper.selectByfjzchAndDprtcode(aircraftinfo);
	}

	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2017-9-21 上午11:09:46
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String update(Aircraftinfo aircraftinfo) throws BusinessException {
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		// 操作指令
		LogOperationEnum cz = LogOperationEnum.EDIT;
		// 验证飞机注册号唯一性
		int countFjzch = aircraftinfoMapper.findByFjzch(aircraftinfo);
		// 验证MSN唯一性
		int countXlh = aircraftinfoMapper.findByXlh(aircraftinfo);
		if (countFjzch > 0) {
			throw new BusinessException("飞机注册号出现重复,请修改后再进行操作！");
		}
		if (countXlh > 0) {
			throw new BusinessException("MSN出现重复,请修改后再进行操作！");
		}
		// 验证是否拥有机型权限
		List<String> list = new ArrayList<String>();
		list.add(aircraftinfo.getFjjx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), aircraftinfo.getDprtcode(), list);
		// 检查机型是否可以操作
		Aircraftinfo oldAircraftinfo = aircraftinfoMapper.selectByfjzchAndDprtcode(aircraftinfo);
		if (oldAircraftinfo.getZt() == EffectiveEnum.YES.getId()) {
			aircraftinfoMapper.updateByPrimaryKeySelective(aircraftinfo);
		} else {
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
		// 验证部件号，序列号数据唯一
//		this.validata(aircraftinfo.getPlanInitList(),aircraftinfo.getDprtcode());
		// 1.修改飞机基本信息
		this.editPlane(aircraftinfo, czls, cz);
		
		// 2.修改飞机初始化数据
		planInitService.edit(aircraftinfo.getPlanInitList(), aircraftinfo.getFjzch(), aircraftinfo.getDprtcode(),
				UpdateTypeEnum.UPDATE, czls, cz);
		
		// 3.修改飞机日使用量监控指标
		planUsageService.edit(aircraftinfo.getPlanUsageList(), aircraftinfo.getFjzch(), aircraftinfo.getDprtcode(),
				UpdateTypeEnum.UPDATE, czls, cz);
		// 4.修改附件
		this.addDtzzfjs(aircraftinfo.getAttachmentList(), aircraftinfo.getFjzch(), czls, cz, aircraftinfo);
		// 5.删除附件
		if (aircraftinfo.getDelAttachements() != null && aircraftinfo.getDelAttachements().size() > 0) {
			// 插入日志
			commonRecService.write("id", aircraftinfo.getDelAttachements(), TableEnum.D_011, user.getId(), czls, cz,
					UpdateTypeEnum.DELETE, aircraftinfo.getFjzch().concat(",").concat(aircraftinfo.getDprtcode()));
			attachmentMapper.delFileByids(aircraftinfo.getDelAttachements());
		}
		// 5.添加或者修改飞机基础数据（D_010）
		planeBasicService.insertUpdate(aircraftinfo);
		//更新飞机状态数据
		this.aircraftinfoStatusMapper.updateByPlaneEdit(aircraftinfo.getDprtcode(), aircraftinfo.getFjzch());
		
		String calInit = null;//飞机日历初始值
		String fhInit = null;//飞行时间初始值
		String fcInit = null;//飞行循环初始值
		
		List<PlanInit> planInitList = aircraftinfo.getPlanInitList();
		for (PlanInit planInit : planInitList) {
			if(planInit.getWz().intValue() == InstalledPositionEnum.BODY.getId().intValue()){
				if(MonitorProjectEnum.isCalendar(planInit.getJklbh())){
					calInit = planInit.getCsz();
				}else if(MonitorProjectEnum.isTime(planInit.getJklbh())){
					fhInit = planInit.getCsz();
				}else{
					fcInit = planInit.getCsz();
				}
			}
		}
		//更新对飞机已经执行的任务的初始值
		this.monitorDataService.updateTaskInit4Plane(aircraftinfo.getDprtcode(), aircraftinfo.getFjzch(), calInit, fhInit, fcInit);
		
		return aircraftinfo.getFjzch();
	}

	
	/**
	 * 
	 * @Description 修改飞机三证
	 * @CreateTime 2017年11月22日 上午10:58:30
	 * @CreateBy 林龙
	 * @param aircraftinfo
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String updatesz(Aircraftinfo aircraftinfo) throws BusinessException {
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		// 操作指令
		LogOperationEnum cz = LogOperationEnum.EDIT;
		// 验证飞机注册号唯一性
		int countFjzch = aircraftinfoMapper.findByFjzch(aircraftinfo);
		// 验证MSN唯一性
		int countXlh = aircraftinfoMapper.findByXlh(aircraftinfo);
		if (countFjzch > 0) {
			throw new BusinessException("飞机注册号出现重复,请修改后再进行操作！");
		}
		if (countXlh > 0) {
			throw new BusinessException("MSN出现重复,请修改后再进行操作！");
		}
		// 验证是否拥有机型权限
		List<String> list = new ArrayList<String>();
		list.add(aircraftinfo.getFjjx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), aircraftinfo.getDprtcode(), list);
		// 检查机型是否可以操作
		Aircraftinfo oldAircraftinfo = aircraftinfoMapper.selectByfjzchAndDprtcode(aircraftinfo);
		if (oldAircraftinfo.getZt() == EffectiveEnum.YES.getId()) {
			aircraftinfoMapper.updateByPrimaryKeySelective(aircraftinfo);
		} else {
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
		// 1.修改飞机基本信息
		this.editPlane(aircraftinfo, czls, cz);
		
		// 2.修改飞机初始化数据
		planInitService.edit(aircraftinfo.getPlanInitList(), aircraftinfo.getFjzch(), aircraftinfo.getDprtcode(),
				UpdateTypeEnum.UPDATE, czls, cz);
		
		// 3.修改飞机日使用量监控指标
		planUsageService.edit(aircraftinfo.getPlanUsageList(), aircraftinfo.getFjzch(), aircraftinfo.getDprtcode(),
				UpdateTypeEnum.UPDATE, czls, cz);
		// 4.修改附件
		this.addDtzzfjs(aircraftinfo.getAttachmentList(), aircraftinfo.getFjzch(), czls, cz, aircraftinfo);
		// 5.删除附件
		if (aircraftinfo.getDelAttachements() != null && aircraftinfo.getDelAttachements().size() > 0) {
			// 插入日志
			commonRecService.write("id", aircraftinfo.getDelAttachements(), TableEnum.D_011, user.getId(), czls, cz,
					UpdateTypeEnum.DELETE, aircraftinfo.getFjzch().concat(",").concat(aircraftinfo.getDprtcode()));
			attachmentMapper.delFileByids(aircraftinfo.getDelAttachements());
		}
		
		//更新飞机状态数据
		this.aircraftinfoStatusMapper.updateByPlaneEdit(aircraftinfo.getDprtcode(), aircraftinfo.getFjzch());
		
		return aircraftinfo.getFjzch();
	}
	
	// 修改飞机基本信息
	private void editPlane(Aircraftinfo aircraftinfo, String czls, LogOperationEnum logOperationEnum) {
		User user = ThreadVarUtil.getUser();
		aircraftinfo.setWhrid(user.getId());
		aircraftinfo.setWhbmid(user.getBmdm());
		aircraftinfo.setWhsj(new Date());
		aircraftinfoMapper.updateByPrimaryKeySelective(aircraftinfo);
		// 3.插入日志
		commonRecService.writeByWhere(
				" b.fjzch = '" + aircraftinfo.getFjzch().replaceAll("'", "''") + "' and b.dprtcode = '" + user.getJgdm()
						+ "'",
				TableEnum.D_007, user.getId(), czls, logOperationEnum, UpdateTypeEnum.UPDATE,
				aircraftinfo.getFjzch().concat(",").concat(user.getJgdm()));
	}

	/**
	 * 
	 * @Description 修改状态
	 * @CreateTime 2017-9-21 上午11:09:46
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public void updateByPrimaryKeySelective(Aircraftinfo aircraftinfo) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		// 检查机型权限
		List<String> list = new ArrayList<String>();
		list.add(aircraftinfo.getFjjx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), aircraftinfo.getDprtcode(), list);

		// 检查机型是否可以操作
		Aircraftinfo oldAircraftinfo = aircraftinfoMapper.selectByfjzchAndDprtcode(aircraftinfo);
		if (oldAircraftinfo.getZt() == aircraftinfo.getZt()) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
		// 根据状态判断是启用还是注销，调用不同的装机清单方法
		if (aircraftinfo.getZt() == EffectiveEnum.YES.getId()) {

			// 1.验证账号注册数量
			int fjCount = aircraftinfoMapper.queryCountfj(ThreadVarUtil.getUser().getJgdm());

			// 查询账号注册最大数量
			DeptInfo accounts = deptInfoMapper.selectCounts(ThreadVarUtil.getUser().getJgdm());
			if (accounts != null) {
				if (accounts.getZcfj_max() != null && fjCount >= accounts.getZcfj_max()
						&& accounts.getZcfj_max() != 0) {
					throw new BusinessException("飞机注册号不能超过组织机构的最大飞机注册数量！");
				}
			}
			// 2.修改状态
			aircraftinfoMapper.updateByPrimaryKeySelective(aircraftinfo);
			// 3.插入日志
			commonRecService.writeByWhere(
					" b.fjzch = '" + aircraftinfo.getFjzch().replaceAll("'", "''") + "' and b.dprtcode = '"
							+ user.getJgdm() + "'",
					TableEnum.D_007, user.getId(), czls, LogOperationEnum.ENABLED, UpdateTypeEnum.UPDATE,
					aircraftinfo.getFjzch().concat(",").concat(user.getJgdm()));
			// 4.添加或者修改飞机基础数据（D_010）
			planeBasicService.insertUpdate(aircraftinfo);
			// 增加飞机的当前监控数据
			this.eOApplicabilityService.addExecObject(oldAircraftinfo.getDprtcode(), oldAircraftinfo.getFjjx(), oldAircraftinfo.getFjzch(), oldAircraftinfo.getXlh());
			List<PlanInit> planeInitList = oldAircraftinfo.getPlanInitList();
			String strCalInit = null;//飞机日历初始值
			for(PlanInit planeInit : planeInitList){
				if(planeInit.getWz().intValue() == InstalledPositionEnum.BODY.getId().intValue() 
						&& MonitorProjectEnum.isCalendar(planeInit.getJklbh())){
					strCalInit = planeInit.getCsz();
					break;
				}
			}
			this.monitorDataService.add4EffectPlane(oldAircraftinfo.getDprtcode(), oldAircraftinfo.getFjjx(), oldAircraftinfo.getFjzch(), oldAircraftinfo.getXlh());
			
		} else {
			// 1.修改状态
			aircraftinfoMapper.updateByPrimaryKeySelective(aircraftinfo);
			// 2.插入日志
			commonRecService.writeByWhere(
					" b.fjzch = '" + aircraftinfo.getFjzch().replaceAll("'", "''") + "' and b.dprtcode = '"
							+ user.getJgdm() + "'",
					TableEnum.D_007, user.getId(), czls, LogOperationEnum.WRITEOFF, UpdateTypeEnum.UPDATE,
					aircraftinfo.getFjzch().concat(",").concat(user.getJgdm()));
			//清除飞机的当前监控数据
			this.monitorDataService.removeByFjzch(aircraftinfo.getFjzch(), aircraftinfo.getDprtcode());
		}
	}

	/**
	 * 
	 * @Description 获取飞机信息
	 * @CreateTime 2017年9月27日 下午2:32:03
	 * @CreateBy 岳彬彬
	 * @param aircraftinfo
	 * @return
	 */
	@Override
	public Aircraftinfo getAircraftinfo(Aircraftinfo aircraftinfo) {

		return aircraftinfoMapper.getFjByFjzchAndDprtcode(aircraftinfo);
	}

	/**
	 * @Description 根据机型查询飞机信息
	 * @CreateTime 2017年9月27日 上午10:49:09
	 * @CreateBy 韩武
	 * @param aircraftinfo
	 * @return
	 */
	@Override
	public List<Aircraftinfo> selectByFjjx(Aircraftinfo aircraftinfo) {
		return aircraftinfoMapper.selectByFjjx(aircraftinfo);
	}
	/**
	 * 
	 * @Description 获取组织机构下所有有效飞机
	 * @CreateTime 2017年10月14日 上午10:39:10
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	@Override
	public List<Aircraftinfo> getFjByDprtcode(String dprtcode) {
		return aircraftinfoMapper.getFjByDprtcode(dprtcode);
	}

	/**
	 * 
	 * @Description 飞机三证监控分页查询
	 * @CreateTime 2017年10月20日 上午10:09:54
	 * @CreateBy 林龙
	 * @param aircraftinfo
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryszAllPageList(Aircraftinfo aircraftinfo)
			throws BusinessException {
		// 用户刚编辑过的记录 飞机注册号，组织机构
				String fjzch = aircraftinfo.getFjzch();
				String dprtcode = aircraftinfo.getDprtcode();
				// 清除查询条件中的飞机注册号，组织机构，保证列表查询结果集的正确性
				aircraftinfo.setFjzch(null);
				PageHelper.startPage(aircraftinfo.getPagination());
				List<Aircraftinfo> list = aircraftinfoMapper.queryszAllPageList(aircraftinfo);
				if (((Page) list).getTotal() > 0) {
					if (StringUtils.isNotBlank(fjzch)) { // 判断飞机注册号是否为空，不为空则表示需要查询最近编辑的业务记录
						if (!PageUtil.hasRecord(list, "fjzch", fjzch)) {
							Aircraftinfo param = new Aircraftinfo();
							param.setFjzch(fjzch);
							param.setDprtcode(dprtcode);
							List<Aircraftinfo> newRecordList = aircraftinfoMapper.queryszAllPageList(param);
							if (newRecordList != null && newRecordList.size() == 1) {
								list.add(0, newRecordList.get(0));
							}
						}
					}
					return PageUtil.pack4PageHelper(list, aircraftinfo.getPagination());
				} else {
					List<Aircraftinfo> newRecordList = new ArrayList<Aircraftinfo>(1);
					if (StringUtils.isNotBlank(fjzch)) { // 判断飞机注册号是否为空，不为空则表示需要查询最近编辑的业务记录
						Aircraftinfo param = new Aircraftinfo();
						param.setFjzch(fjzch);
						param.setDprtcode(dprtcode);
						newRecordList = aircraftinfoMapper.queryszAllPageList(param);
						if (newRecordList != null && newRecordList.size() == 1) {
							return PageUtil.pack(1, newRecordList, aircraftinfo.getPagination());
						}
					}
					return PageUtil.pack(0, newRecordList, aircraftinfo.getPagination());
				}
	}

}