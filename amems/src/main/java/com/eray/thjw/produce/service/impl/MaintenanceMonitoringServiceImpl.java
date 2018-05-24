package com.eray.thjw.produce.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.MonitoringCurrentMapper;
import com.eray.thjw.produce.dao.MonitoringObjectMapper;
import com.eray.thjw.produce.dao.MonitoringPlanMapper;
import com.eray.thjw.produce.dao.MonitoringWorkpackageMapper;
import com.eray.thjw.produce.dao.PlanUsageMapper;
import com.eray.thjw.produce.dao.ThresholdAirMapper;
import com.eray.thjw.produce.dao.WorkorderMapper;
import com.eray.thjw.produce.dao.WorkpackageMapper;
import com.eray.thjw.produce.po.MonitoringCurrent;
import com.eray.thjw.produce.po.MonitoringObject;
import com.eray.thjw.produce.po.MonitoringPlan;
import com.eray.thjw.produce.po.MonitoringWorkpackage;
import com.eray.thjw.produce.po.PlanUsage;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.po.Workpackage;
import com.eray.thjw.produce.service.MaintenanceMonitoringService;
import com.eray.thjw.produce.service.WorkpackageService;
import com.eray.thjw.project2.dao.CoverPlateMapper;
import com.eray.thjw.project2.dao.MaterialToolMapper;
import com.eray.thjw.project2.dao.ReferenceMapper;
import com.eray.thjw.project2.dao.WorkContentMapper;
import com.eray.thjw.project2.dao.WorkHourMapper;
import com.eray.thjw.project2.po.CoverPlate;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.po.MaterialTool;
import com.eray.thjw.project2.po.Reference;
import com.eray.thjw.project2.po.WorkContent;
import com.eray.thjw.project2.po.WorkHour;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.system.dao.SynRelMapper;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.WhetherEnum;
import enu.produce.FeedbackStatusEnum;
import enu.produce.WorkorderTypeEnum;
import enu.produce.WorkpackageStatusEnum;
import enu.project2.ExecutionEnum;
import enu.project2.MaintenanceProjectTypeEnum;
import enu.project2.MonitorProjectEnum;
import enu.project2.ProjectBusinessEnum;

/**
 * @Description 飞机维修监控服务层
 * @CreateTime 2017-9-28 上午11:04:27
 * @CreateBy 刘兵
 */
@Service
public class MaintenanceMonitoringServiceImpl implements MaintenanceMonitoringService{

	@Resource
	private MonitoringCurrentMapper monitoringCurrentMapper;
	
	@Resource
	private MonitoringObjectMapper monitoringObjectMapper;
	
	@Resource
	private MonitoringPlanMapper monitoringPlanMapper;
	
	@Resource
	private PlanUsageMapper planUsageMapper;
	
	@Resource
	private WorkorderMapper workorderMapper;
	
	@Resource
	private WorkpackageMapper workpackageMapper;
	
	@Resource
	private MonitoringWorkpackageMapper monitoringWorkpackageMapper;
	
	@Resource
	private AttachmentMapper attachmentMapper;
	
	@Resource
	private ReferenceMapper referenceMapper;
	
	@Resource
	private MaterialToolMapper materialToolMapper;
	
	@Resource
	private WorkContentMapper workContentMapper;
	
	@Resource
	private CoverPlateMapper coverPlateMapper;
	
	@Resource
	private WorkHourMapper workHourMapper;
	
	@Resource
	private SynRelMapper synRelMapper;
	
	@Resource
	private ThresholdAirMapper thresholdAirMapper;
	
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	@Resource
	private WorkpackageService workpackageService;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private CommonService commonService;
	
	/**
	 * @Description 保存监控
	 * @CreateTime 2017-10-12 上午10:42:37
	 * @CreateBy 刘兵
	 * @param monitoringObject 监控对象
	 * @return String
	 */
	@Override
	public String save(MonitoringObject monitoringObject){
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		//更新监控对象
		monitoringObject.setWhrid(user.getId());
		monitoringObject.setWhbmid(user.getBmdm());
		monitoringObjectMapper.updateByPrimaryKeySelective(monitoringObject);
		//保存历史记录信息
//		commonRecService.write(monitoringObject.getId(), TableEnum.B_S2_901, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, monitoringObject.getId());
		//更新当前监控数据
		MonitoringCurrent monitoringCurrent = new MonitoringCurrent();
		monitoringCurrent.setId(monitoringObject.getId());
		monitoringCurrent.setWhrid(user.getId());
		monitoringCurrent.setWhbmid(user.getBmdm());
		monitoringCurrentMapper.updateByPrimaryKeySelective(monitoringCurrent);
		//判断是否存在监控数据-(计划)执行数据
		if(monitoringObject.getMonitoringPlanList() != null && monitoringObject.getMonitoringPlanList().size() > 0){
			for (MonitoringPlan monitoringPlan : monitoringObject.getMonitoringPlanList()) {
				//更新监控数据-(计划)执行数据
				monitoringPlan.setWhrid(user.getId());
				monitoringPlan.setWhdwid(user.getBmdm());
				monitoringPlanMapper.updateByPrimaryKeySelective(monitoringPlan);
//				commonRecService.write(monitoringPlan.getId(), TableEnum.B_S2_904, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, monitoringObject.getId());
			}
		}
		return monitoringObject.getId();
	}
	
	/**
	 * @Description 保存选中的待组包的监控项目
	 * @CreateTime 2017-10-13 下午5:23:18
	 * @CreateBy 刘兵
	 * @param monitoringObject 监控对象
	 * @return int 已选中的数量
	 */
	@Override
	public int saveChecked(MonitoringObject monitoringObject){
		if(monitoringObject.getMonitoringWorkpackageList() != null && monitoringObject.getMonitoringWorkpackageList().size() > 0){
			User user = ThreadVarUtil.getUser();
			Map<String, Object> paramMap = new HashMap<String, Object>();//参数map,用于查询选中的监控数据id是否已经被选过
			List<String> jksjidList = new ArrayList<String>();//存放选中的监控数据id
			for(MonitoringWorkpackage workpackage : monitoringObject.getMonitoringWorkpackageList()){
				jksjidList.add(workpackage.getJksjgdid());
			}
			paramMap.put("fjzch", monitoringObject.getFjzch());
			paramMap.put("dprtcode", monitoringObject.getDprtcode());
			paramMap.put("jksjidList", jksjidList);
			//获取已经存在工单中的监控数据id集合
			List<String> existsWOid = workorderMapper.query4CheckExist(paramMap);
			//获取已经存在预组包中的监控数据id集合
			List<String> existsWPid = monitoringWorkpackageMapper.query4CheckExist(paramMap);
			for(MonitoringWorkpackage workpackage : monitoringObject.getMonitoringWorkpackageList()){
				//监控数据id在状态！=9的工单、待组包的监控项目表中都不存在，该监控数据写入b_s2_009表操作
				if(!existsWOid.contains(workpackage.getJksjgdid()) && !existsWPid.contains(workpackage.getJksjgdid())){
					workpackage.setId(UUID.randomUUID().toString());
					workpackage.setWhrid(user.getId());
					workpackage.setWhdwid(user.getBmdm());
					existsWPid.add(workpackage.getJksjgdid());
					//保存待组包的监控项目
					monitoringWorkpackageMapper.insertSelective(workpackage);
					//如果选中的是定检包,那么定检包下的监控数据都要写入到b_s2_009 待组包的监控项目表 （增量更新）
					if(workpackage.getParamsMap() != null && workpackage.getParamsMap().get("wxxmlx") != null){
						update4FixedMonitoringWorkpackage(workpackage, existsWOid, existsWPid);
					}
				}
			}
		}
		return monitoringWorkpackageMapper.getCheckedCount(monitoringObject.getFjzch(), monitoringObject.getDprtcode());
	}
	
	/**
	 * @Description  定检包下的监控数据都要写入到b_s2_009 待组包的监控项目表 （增量更新）
	 * @CreateTime 2017-10-14 上午11:38:24
	 * @CreateBy 刘兵
	 * @param param 待组包的监控项目
	 * @param existsWOid 已经存在工单中的监控数据id集合
	 * @param existsWPid 已经存在预组包中的监控数据id集合
	 */
	private void update4FixedMonitoringWorkpackage(MonitoringWorkpackage param, List<String> existsWOid, List<String> existsWPid){
		//获取定检包下的监控数据id集合
		List<String> jksjidList = monitoringCurrentMapper.query4FixedPackage(param.getJksjgdid(), param.getFjzch());
		if(jksjidList.size() > 0){
			Map<String, Object> paramMap = new HashMap<String, Object>();//参数map,用于查询选中的监控数据id是否已经被选过
			paramMap.put("fjzch", param.getFjzch());
			paramMap.put("dprtcode", param.getDprtcode());
			//获取已经存在预组包中的监控数据id集合
			paramMap.put("jksjidList", jksjidList);
			List<String> existsWPidList = monitoringWorkpackageMapper.query4CheckExist(paramMap);
			//加入已经存在预组包中的监控数据id集合
			existsWPid.addAll(existsWPidList);
			for (String jksjid : jksjidList) {
				//如果监控数据id在工单中不存在,则需要写入到b_s2_009 待组包的监控项目表 （增量更新）
//				if(!existsWOid.contains(jksjid)){
					MonitoringWorkpackage workpackage = new MonitoringWorkpackage();
					workpackage.setWhrid(param.getWhrid());
					workpackage.setWhdwid(param.getWhdwid());
					workpackage.setJksjgdid(jksjid);
					workpackage.setDjbjksjid(param.getJksjgdid());
					workpackage.setXsbs(WhetherEnum.NO.getId());
					workpackage.setGbid(null);
					//如果监控数据id在待组包中不存在则新增,否则编辑
					if(!existsWPid.contains(jksjid)){
						workpackage.setId(UUID.randomUUID().toString());
						workpackage.setDprtcode(param.getDprtcode());
						workpackage.setFjzch(param.getFjzch());
						workpackage.setLx(WhetherEnum.YES.getId());
						existsWPid.add(jksjid);
						//保存待组包的监控项目
						monitoringWorkpackageMapper.insertSelective(workpackage);
					}else{
						monitoringWorkpackageMapper.updateByJksjId(workpackage);
					}
//				}
			}
		}
	}
	
	/**
	 * @Description 移除选中的待组包的监控项目
	 * @CreateTime 2017-10-13 下午5:23:18
	 * @CreateBy 刘兵
	 * @param monitoringObject 监控对象
	 */
	@Override
	public void deleteChecked(MonitoringObject monitoringObject){
		List<String> idList = new ArrayList<String>();
		if(monitoringObject.getMonitoringWorkpackageList() != null && monitoringObject.getMonitoringWorkpackageList().size() > 0){
			for(MonitoringWorkpackage workpackage : monitoringObject.getMonitoringWorkpackageList()){
				idList.add(workpackage.getId());
			}
		}
		if(idList.size() > 0){
			monitoringWorkpackageMapper.delete4Batch(idList);
		}
	}
	
	/**
	 * @Description 组包 
	 * @CreateTime 2017-10-16 下午2:01:22
	 * @CreateBy 刘兵
	 * @param workpackage 工包135
	 * @return int 预组包数量
	 * @throws BusinessException
	 */
	@Override
	public int addWorkpackage(Workpackage workpackage) throws BusinessException{
		String id = UUID.randomUUID().toString();
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		workpackage.setId(id);
		
		//当页面没有填写编号时
		if(workpackage.getGbbh() == null || "".equals(workpackage.getGbbh())){ 
			String gbbh=setGbbh(workpackage); //根据评估单对象获取最新编号
			workpackage.setGbbh(gbbh);
		}else{
			// 验证编号唯一
			validationGbbh(workpackage);
		}
		
		workpackage.setWhrid(user.getId());
		workpackage.setWhdwid(user.getBmdm());
		workpackage.setZdrid(user.getId());
		workpackage.setZdbmid(user.getBmdm());
		workpackage.setZt(WorkpackageStatusEnum.SAVE.getId());
		workpackage.setDtbs(WhetherEnum.YES.getId());
		workpackage.setWgbs(FeedbackStatusEnum.NO.getId());
		// 验证飞机权限
		validationFjzch(workpackage, user);
	
		// 新增工包
		workpackageMapper.insertSelective(workpackage);
		// 保存附件信息
		attachmentService.eidtAttachment(workpackage.getAttachments(), id, czls, id, workpackage.getDprtcode(), LogOperationEnum.SAVE_WO);
		// 工包日志
		commonRecService.write(workpackage.getId(), TableEnum.B_S2_007, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, workpackage.getId());
		//修改b_s2_009 待组包的监控项目表工包id字段
		updateGbid(workpackage);
		return workpackageMapper.getBurstificationCount(workpackage.getFjzch(), workpackage.getDprtcode());
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
		String dh =null;
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
	 * @Description 添加到已有工包
	 * @CreateTime 2017-10-16 下午2:01:22
	 * @CreateBy 刘兵
	 * @param workpackage 工包135
	 * @return int 预组包数量
	 * @throws BusinessException
	 */
	@Override
	public int add2WorkPackage(Workpackage workpackage) throws BusinessException{
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		workpackage.setDtbs(WhetherEnum.YES.getId());
		// 验证飞机权限
		validationFjzch(workpackage, user);
		// 编辑工包
		workpackageMapper.updateDtbsById(workpackage);
		// 工包日志
		commonRecService.write(workpackage.getId(), TableEnum.B_S2_007, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, workpackage.getId());
		//修改b_s2_009 待组包的监控项目表工包id字段
		updateGbid(workpackage);
		return workpackageMapper.getBurstificationCount(workpackage.getFjzch(), workpackage.getDprtcode());
	}
	
	/**
	 * @Description 修改b_s2_009 待组包的监控项目表工包id字段
	 * @CreateTime 2017-10-16 下午2:47:32
	 * @CreateBy 刘兵
	 * @param workpackage 工包
	 */
	private void updateGbid(Workpackage workpackage){
		if(workpackage.getMonitoringWorkpackageList() != null && workpackage.getMonitoringWorkpackageList().size() > 0){
			Map<String, Object> paramMap = new HashMap<String, Object>();//参数map,用于查询选中的监控数据id是否已经被选过
			List<String> jksjidList = new ArrayList<String>();//存放选中的监控数据id
			for(MonitoringWorkpackage mwp : workpackage.getMonitoringWorkpackageList()){
				jksjidList.add(mwp.getJksjgdid());
			}
			paramMap.put("fjzch", workpackage.getFjzch());
			paramMap.put("dprtcode", workpackage.getDprtcode());
			paramMap.put("jksjidList", jksjidList);
			//获取已经存在工单中的监控数据id集合
			List<String> existsWOid = workorderMapper.query4CheckExist(paramMap);
			for(MonitoringWorkpackage mwp : workpackage.getMonitoringWorkpackageList()){
				//监控数据id在状态！=9的工单中不存在，修改b_s2_009表工包id字段
				if(!existsWOid.contains(mwp.getJksjgdid())){
					monitoringWorkpackageMapper.updateGbidById(workpackage.getId(), mwp.getId());
				}
			}
		}
	}
	
	/**
	 * @Description 删除预组包
	 * @CreateTime 2017-10-17 上午11:16:44
	 * @CreateBy 刘兵
	 * @param id 工包id
	 * @throws BusinessException
	 */
	@Override
	public void deletePackage(String id) throws BusinessException{
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		Workpackage wp = workpackageMapper.selectByPrimaryKey(id);
		if(null == wp){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		// 验证飞机权限
		validationFjzch(wp, user);
		/* 验证重复提交 begin */
		validation4CurrentZt(new Integer[]{WorkpackageStatusEnum.SAVE.getId()}, wp.getZt(), "该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		// 清除预组包关系
		monitoringWorkpackageMapper.updateByGbid(id);
		// 工包日志
		commonRecService.write(id, TableEnum.B_S2_007, user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, id);
		// 物理删除工包
		workpackageMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * @Description 提交预组包
	 * @CreateTime 2017-10-17 上午11:16:44
	 * @CreateBy 刘兵
	 * @param id 工包id
	 * @return int 预组包数量
	 * @throws BusinessException
	 */
	@Override
	public int doSubmitPackage(String id) throws BusinessException{
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		Workpackage wp = workpackageMapper.selectByPrimaryKey(id);
		if(null == wp){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		// 验证飞机权限
		validationFjzch(wp, user);
		/* 验证重复提交 begin */
		validation4CurrentZt(new Integer[]{
				WorkpackageStatusEnum.SAVE.getId()
				,WorkpackageStatusEnum.SUBMIT.getId()
				,WorkpackageStatusEnum.TAKEEFFECT.getId()
				}, wp.getZt(), "该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		//如果工包状态=1预组包，则修改工包状态=2提交,否则不修改工包状态,待提标识修改成0否
		wp.setDtbs(WhetherEnum.NO.getId());
		if(wp.getZt() == WorkpackageStatusEnum.SAVE.getId()){
			wp.setZt(WorkpackageStatusEnum.SUBMIT.getId());
			workpackageMapper.updateDtbsAndZtById(wp);
		}else{
			workpackageMapper.updateDtbsById(wp);
		}
		// 工包日志
		commonRecService.write(wp.getId(), TableEnum.B_S2_007, user.getId(), czls, LogOperationEnum.SUBMIT_WO, UpdateTypeEnum.UPDATE, wp.getId());
		Date currentDate = commonService.getSysdate();//当前时间
		//获取需要生成工单的维修项目和EO
		List<Workorder> workorderList = workorderMapper.queryProduceList(wp.getFjzch(), wp.getDprtcode(), id);
		Map<String, Workorder> map = new HashMap<String, Workorder>();//用于存放监控数据id对应的工单
		for (Workorder workorder : workorderList) {
			map.put(workorder.getJksjid(), workorder);
		}
		
		Workpackage workPage = workpackageMapper.selectByPrimaryKey(id); //获得135的工包
			
		for (Workorder workorder : workorderList) {
			workorder.setGbbh(workPage.getGbbh());
			
//			String gdbh = getGdbh(wp.getDprtcode(), wp.getGbbh());
			
			
			//当页面没有填写工单编号时
			if(workorder.getGdbh() == null || "".equals(workorder.getGdbh())){ 
				String gddh=this.settoGdbh(workorder,user); //根据缺陷获取最新缺陷保留单号
				workorder.setGdbh(gddh);
			}else{
				//验证验证工单编号 唯一
				this.validateUniqueness(workorder);
			}
			
			
			
			workorder.setDprtcode(wp.getDprtcode());//组织编码
			workorder.setWhdwid(user.getBmdm());//维护人单位ID
			workorder.setWhrid(user.getId());//维护人ID
			workorder.setWhsj(currentDate);//维护时间
			if(WorkpackageStatusEnum.TAKEEFFECT.getId() == wp.getZt()){
				workorder.setXfrdwid(user.getBmdm());
				workorder.setXfrid(user.getId());
				workorder.setXfsj(currentDate);
			}
			workorder.setZdbmid(user.getBmdm());//制单部门ID
			workorder.setZdrid(user.getId());//制单人id
			workorder.setZdrq(currentDate);//制单日期
			workorder.setZt(wp.getZt());
			workorder.setXmblbs(WhetherEnum.NO.getId());//项目保留标识标识：0否、1是
			workorder.setWgbs(WhetherEnum.NO.getId());//完工标识：0未完工、1已完工
			workorder.setGbid(id);
//			workorder.setGdbh(gdbh);
			workorder.setFjzch(wp.getFjzch());
			workorder.setKdrq(currentDate);
			workorder.setJhKsrq(wp.getJhKsrq());
			workorder.setJhJsrq(wp.getJhJsrq());
			if(workorder.getParamsMap().get("djbjksjid") != null){
				workorder.setDjbgdid(map.get((String)workorder.getParamsMap().get("djbjksjid")).getGdsbid());
			}
			if(StringUtils.isNotBlank(workorder.getGznrfjid())){
				String gznrfjid = UUID.randomUUID().toString();
				attachmentMapper.insert4Copy(gznrfjid, workorder.getGznrfjid());
				workorder.setGznrfjid(gznrfjid);
			}
			//新增工单
			workorderMapper.insert(workorder);
			//保存历史记录信息
			commonRecService.write(workorder.getId(), TableEnum.B_S2_008, user.getId(), czls, LogOperationEnum.SUBMIT_WO, UpdateTypeEnum.SAVE, wp.getId());
			//如果是EO,则复制EO关联信息
			if(WorkorderTypeEnum.EO.getId() == workorder.getGdlx()){
				insertOthers4Copy(workorder.getId(), workorder.getLyrwid(), user, czls, LogOperationEnum.SUBMIT_WO);
			}else{
				//如果工卡不存在,则复制维修项目关联信息,否则复制工卡关联信息
				if (StringUtils.isBlank(workorder.getGkid())){
					if(WorkorderTypeEnum.RTN.getId() == workorder.getGdlx()){
						insertOthers4Copy(workorder.getId(), workorder.getLyrwid(), user, czls, LogOperationEnum.SUBMIT_WO);
					}
				}else{
					insertOthers4Copy(workorder.getId(), workorder.getGkid(), user, czls, LogOperationEnum.SUBMIT_WO);
				}
			}
		}
		
		
		//修改NRC工单上工包id
		workorderMapper.updateNRCGbid(id);
		//工包状态为7下发,验证是否需要同步145工包和145工单
		if(WorkpackageStatusEnum.TAKEEFFECT.getId() == wp.getZt()){
			//同步145工包和工单数据
			workpackageService.doWorkpackageAndWorkoder145(wp, workorderList ,user, czls);
		}
		//删除预组包数据
		monitoringWorkpackageMapper.deleteByGbid(id);
		return monitoringWorkpackageMapper.getCheckedCount(wp.getFjzch(), wp.getDprtcode());
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
	private String settoGdbh(Workorder workorder,User user) throws BusinessException {
		StringBuffer bldhNew = new StringBuffer();	//编号
		Workorder fai = new Workorder(); 		  	//new 编号
		boolean b = true;
		while(b){ 
			String gddh;
			try {
				gddh = SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.GD135.getName(),workorder);
			} catch (SaibongException e) {
				throw new BusinessException(e);
			}
			bldhNew.append(gddh);
			fai.setGdbh(gddh);
			fai.setDprtcode(user.getJgdm());
			//根据技术文件对象查询数量
			int i = workorderMapper.queryCount(fai); 
			if(i <= 0){
				b = false;
			}
		}
		return bldhNew.toString();
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
	
/*	
	*//**
	 * @Description 获取工单编号
	 * @CreateTime 2017-11-15 下午3:48:17
	 * @CreateBy 刘兵
	 * @param dprtcode 机构代码
	 * @param gbbh 工包编号
	 * @return String 工单编号
	 * @throws BusinessException
	 *//*
	private String getGdbh(String dprtcode, String gbbh) throws BusinessException{
		String gdbh = null;
		try {
			gdbh = saibongUtilService.generate(dprtcode, SaiBongEnum.ZBGD.getName(), gbbh);
			//机构代码+工单编号唯一
			int iCount = workorderMapper.getCount4CheckExist(gdbh, dprtcode);
			if (iCount > 0) {
				getGdbh(dprtcode, gbbh);
			}
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}
		return gdbh;
	}*/
	
	/**
	 * @Description 删除工包下工单
	 * @CreateTime 2017-10-18 上午10:35:33
	 * @CreateBy 刘兵
	 * @param workpackage 工包135
	 * @throws BusinessException
	 */
	@Override
	public void deleteWorkOrder4WorkPackage(Workpackage workpackage) throws BusinessException{
		User user = ThreadVarUtil.getUser();
		Workpackage wp = workpackageMapper.selectByPrimaryKey(workpackage.getId());
		if(null == wp){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		// 验证飞机权限
		validationFjzch(wp, user);
		/* 验证重复提交 begin */
		validation4CurrentZt(new Integer[]{WorkpackageStatusEnum.SAVE.getId()
				,WorkpackageStatusEnum.SUBMIT.getId()
				,WorkpackageStatusEnum.TAKEEFFECT.getId()
			}, wp.getZt(), "该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		
		List<String> jksjgdidList = new ArrayList<String>();//监控数据id集合
		for(MonitoringWorkpackage monitoringWorkpackage : workpackage.getMonitoringWorkpackageList()){
			jksjgdidList.add(monitoringWorkpackage.getJksjgdid());
			//如果选中的是定检包,那么定检包下的监控数据都要清空待组包工包id
			if(monitoringWorkpackage.getParamsMap() != null && monitoringWorkpackage.getParamsMap().get("gdzlx") != null){
				monitoringWorkpackageMapper.updateGbidByDjbjksjgdid(monitoringWorkpackage.getJksjgdid());
			}
		}
		//批量清空待组包的监控项目工包id
		monitoringWorkpackageMapper.updateGbidByJksjgdid4Batch(jksjgdidList);
	}
	
	/**
	 * @Description 复制工卡关联信息
	 * @CreateTime 2017-10-17 下午6:27:09
	 * @CreateBy 刘兵
	 * @param id 工单id
	 * @param ywid 父表id
	 * @param user 用户
	 * @param czls 操作流水
	 * @param logopration 操作
	 */
	private void insertOthers4Copy(String id, String ywid, User user, String czls, LogOperationEnum logopration) {
		// 新增b_g2_999参考文件
		addReference(id, ywid, user, czls, logopration);
		// 新增b_g2_997 器材/工具
		addMaterialTool(id, ywid, user, czls, logopration);
		// 新增b_g2_996工作内容
		addWorkContent(id, ywid, user, czls, logopration);
		// 新增b_g2_995接近（盖板）/区域/站位
		addCoverPlate(id, ywid, user, czls, logopration);
		// 新增b_g2_993工种工时
		addWorkHours(id, ywid, user, czls, logopration);
	}
	
	/**
	 * @Description 新增135工单参考文件
	 * @CreateTime 2017-10-17 下午5:59:20
	 * @CreateBy 刘兵
	 * @param id 工单id
	 * @param ywid 父表id
	 * @param user 用户
	 * @param czls 操作流水
	 * @param logopration 操作
	 */
	private void addReference(String id, String ywid, User user ,String czls, LogOperationEnum logopration) {
		Reference record = new Reference();
		record.setId(id);
		record.setYwid(ywid);
		record.setYwlx(ProjectBusinessEnum.WORKORDER.getId());
		record.setWhdwid(user.getBmdm());
		record.setWhrid(user.getId());
		referenceMapper.insertByCopy(record);
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		columnValueList.add(id);
		// 保存历史记录信息
		commonRecService.write("ywid", columnValueList, TableEnum.B_G2_999, user.getId(), czls, logopration, UpdateTypeEnum.SAVE, id);
	}
	
	/**
	 * @Description 新增135工单器材/工具
	 * @CreateTime 2017-10-17 下午5:59:20
	 * @CreateBy 刘兵
	 * @param id 工单id
	 * @param ywid 父表id
	 * @param user 用户
	 * @param czls 操作流水
	 * @param logopration 操作
	 */
	private void addMaterialTool(String id, String ywid, User user ,String czls, LogOperationEnum logopration) {
		MaterialTool record = new MaterialTool();
		record.setId(id);
		record.setYwid(ywid);
		record.setYwlx(ProjectBusinessEnum.WORKORDER.getId());
		record.setWhdwid(user.getBmdm());
		record.setWhrid(user.getId());
		materialToolMapper.insertByCopy(record);
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		columnValueList.add(id);
		// 保存历史记录信息
		commonRecService.write("ywid", columnValueList, TableEnum.B_G2_997, user.getId(), czls, logopration, UpdateTypeEnum.SAVE, id);
	}
	
	/**
	 * @Description 新增135工单工作内容
	 * @CreateTime 2017-10-17 下午5:59:20
	 * @CreateBy 刘兵
	 * @param id 工单id
	 * @param ywid 父表id
	 * @param user 用户
	 * @param czls 操作流水
	 * @param logopration 操作
	 */
	private void addWorkContent(String id, String ywid, User user ,String czls, LogOperationEnum logopration) {
		WorkContent record = new WorkContent();
		record.setId(id);
		record.setYwid(ywid);
		record.setYwlx(ProjectBusinessEnum.WORKORDER.getId());
		record.setWhdwid(user.getBmdm());
		record.setWhrid(user.getId());
		workContentMapper.insertByCopy(record);
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		columnValueList.add(id);
		// 保存历史记录信息
		commonRecService.write("ywid", columnValueList, TableEnum.B_G2_996, user.getId(), czls, logopration, UpdateTypeEnum.SAVE, id);
	}
	
	/**
	 * @Description 新增135工单接近（盖板）/区域/站位
	 * @CreateTime 2017-10-17 下午5:59:20
	 * @CreateBy 刘兵
	 * @param id 工单id
	 * @param ywid 父表id
	 * @param user 用户
	 * @param czls 操作流水
	 * @param logopration 操作
	 */
	private void addCoverPlate(String id, String ywid, User user ,String czls, LogOperationEnum logopration) {
		CoverPlate record = new CoverPlate();
		record.setId(id);
		record.setYwid(ywid);
		record.setYwlx(ProjectBusinessEnum.WORKORDER.getId());
		record.setWhdwid(user.getBmdm());
		record.setWhrid(user.getId());
		coverPlateMapper.insertByCopy(record);
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		columnValueList.add(id);
		// 保存历史记录信息
		commonRecService.write("ywid", columnValueList, TableEnum.B_G2_995, user.getId(), czls, logopration, UpdateTypeEnum.SAVE, id);
	}
	
	/**
	 * @Description 新增135工单工种工时
	 * @CreateTime 2017-10-17 下午5:59:20
	 * @CreateBy 刘兵
	 * @param id 工单id
	 * @param ywid 父表id
	 * @param user 用户
	 * @param czls 操作流水
	 * @param logopration 操作
	 */
	private void addWorkHours(String id, String ywid, User user ,String czls, LogOperationEnum logopration) {
		WorkHour record = new WorkHour();
		record.setId(id);
		record.setYwid(ywid);
		record.setYwlx(ProjectBusinessEnum.WORKORDER.getId());
		record.setWhdwid(user.getBmdm());
		record.setWhrid(user.getId());
		workHourMapper.insertByCopy(record);
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		columnValueList.add(id);
		// 保存历史记录信息
		commonRecService.write("ywid", columnValueList, TableEnum.B_G2_993, user.getId(), czls, logopration, UpdateTypeEnum.SAVE, id);
	}
	
	/**
	 * @Description 根据飞机注册号、机构代码获取已选中的数量
	 * @CreateTime 2017-10-14 下午2:48:55
	 * @CreateBy 刘兵
	 * @param fjzch飞机注册号
	 * @param dprtcode 机构代码
	 * @return int 已选中数量
	 */
	@Override
	public int getCheckCount(String fjzch, String dprtcode){
		return monitoringWorkpackageMapper.getCheckedCount(fjzch, dprtcode);
	}
	
	/**
	 * @Description 根据飞机注册号、机构代码获取预组包数量
	 * @CreateTime 2017-10-16 下午3:18:21
	 * @CreateBy 刘兵
	 * @param fjzch飞机注册号
	 * @param dprtcode 机构代码
	 * @return int 预组包数量
	 */
	@Override
	public int getBurstificationCount(String fjzch, String dprtcode){
		return workpackageMapper.getBurstificationCount(fjzch, dprtcode);
	}
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(飞机维修项目监控信息)
	 * @CreateTime 2017-9-25 下午3:19:22
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryAllPageMaintenanceList(MonitoringCurrent monitoringCurrent) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = monitoringCurrent.getId();
		monitoringCurrent.setId(null);
		List<MonitoringCurrent> list = monitoringCurrentMapper.queryPackageMaintenanceList(monitoringCurrent);
		if (StringUtils.isNotBlank(id)) {
			// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
			if (!PageUtil.hasRecord(list, id)) {
				// 在查询条件中增加ID
				MonitoringCurrent newRecord = new MonitoringCurrent();
				newRecord.setId(id);
				newRecord.setDprtcode(monitoringCurrent.getDprtcode());
				newRecord.getParamsMap().put("fjzch", monitoringCurrent.getParamsMap().get("fjzch"));
				List<MonitoringCurrent> newRecordList = monitoringCurrentMapper.queryPackageMaintenanceList(monitoringCurrent);
				if (newRecordList != null && newRecordList.size() == 1) {
					list.add(0, newRecordList.get(0));
				}
			}
		}
		List<PlanUsage> planUsageList = planUsageMapper.queryByFjzch((String)monitoringCurrent.getParamsMap().get("fjzch"), monitoringCurrent.getDprtcode());
		Date currentDate = commonService.getSysdate();//当前时间
		//设置下次计划日期
		for (MonitoringCurrent monitoringCurrent2 : list) {
			setNextDate(monitoringCurrent2, planUsageList, currentDate);
		}
		//设置默认排序排序
		if("auto".equals(monitoringCurrent.getPagination().getSort())){
			Collections.sort(list, new DefaultOrderCompare());//默认排序调用方法
		}
		PageUtil.hasRecord(list, id);
		List<Map<String, Object>> resultList = monitoringCurrentmaintenanceToMap(list);
		map.put("rows", resultList);
		map.put("total", resultList.size());
		//获取日使用量
		map.put("planUsageList", planUsageToMap(planUsageList));
		//获取阈值
//		map.put("thresholdAirList", thresholdAirMapper.queryByFjzch((String)monitoringCurrent.getParamsMap().get("fjzch"), monitoringCurrent.getDprtcode()));
		return map;
	}
	
	/**
	 * @Description 维修监控对象转map
	 * @CreateTime 2018-4-11 上午9:58:54
	 * @CreateBy 刘兵
	 * @param list
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> monitoringCurrentmaintenanceToMap(List<MonitoringCurrent> list){
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (MonitoringCurrent monitoringCurrent : list) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			MaintenanceProject maintenanceProject = monitoringCurrent.getMaintenanceProject();
			if(null != maintenanceProject){
				monitoringCurrent.getParamsMap().put("id", maintenanceProject.getId());
				monitoringCurrent.getParamsMap().put("wxxmlx", maintenanceProject.getWxxmlx());
				monitoringCurrent.getParamsMap().put("rwh", maintenanceProject.getRwh());
				monitoringCurrent.getParamsMap().put("bb", maintenanceProject.getBb());
				monitoringCurrent.getParamsMap().put("ckh", maintenanceProject.getCkh());
				monitoringCurrent.getParamsMap().put("rwms", maintenanceProject.getRwms());
				monitoringCurrent.getParamsMap().put("gzlx", maintenanceProject.getGzlx());
				
			}
			resultMap.put("id", monitoringCurrent.getId());
			resultMap.put("dprtcode", monitoringCurrent.getDprtcode());
			resultMap.put("zjqdid", monitoringCurrent.getZjqdid());
			resultMap.put("bjh", monitoringCurrent.getBjh());
			resultMap.put("xlh", monitoringCurrent.getXlh());
			resultMap.put("hdwz", monitoringCurrent.getHdwz());
			resultMap.put("paramsMap", monitoringCurrent.getParamsMap());
			resultList.add(resultMap);
		}
		return resultList;
	}
	
	/**
	 * @Description 飞机日使用量监控指标对象转map
	 * @CreateTime 2018-4-11 下午2:07:26
	 * @CreateBy 刘兵
	 * @param planUsageList
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> planUsageToMap(List<PlanUsage> planUsageList){
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (PlanUsage planUsage : planUsageList) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("jklbh", planUsage.getJklbh());
			resultMap.put("rsyl", planUsage.getRsyl());
			resultList.add(resultMap);
		}
		return resultList;
	}
	
	/**
	 * @Description 根据查询条件查询维修清单
	 * @CreateTime 2017-10-28 下午3:38:39
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryPackageMaintenanceDetailList(MonitoringCurrent monitoringCurrent) {
		PageHelper.startPage(monitoringCurrent.getPagination());
		List<MonitoringCurrent> list = monitoringCurrentMapper.queryPackageMaintenanceDetailList(monitoringCurrent);
		List<PlanUsage> planUsageList = planUsageMapper.queryByFjzch((String)monitoringCurrent.getParamsMap().get("fjzch"), monitoringCurrent.getDprtcode());
		Date currentDate = commonService.getSysdate();//当前时间
		for (MonitoringCurrent monitoringCurrent2 : list) {
			setNextDate(monitoringCurrent2, planUsageList, currentDate);
		}
		//设置默认排序排序
		if("auto".equals(monitoringCurrent.getPagination().getSort())){
			Collections.sort(list, new DefaultOrderCompare());//默认排序调用方法
		}
		Map<String, Object> pack4PageHelper = PageUtil.pack4PageHelper(list, monitoringCurrent.getPagination());
		pack4PageHelper.put("planUsageList", planUsageMapper.queryByFjzch((String)monitoringCurrent.getParamsMap().get("fjzch"), monitoringCurrent.getDprtcode()));
		return pack4PageHelper;
	}
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(EO监控信息)
	 * @CreateTime 2017-9-25 下午3:19:22
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryAllPageEOList(MonitoringCurrent monitoringCurrent) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = monitoringCurrent.getId();
		monitoringCurrent.setId(null);
		List<MonitoringCurrent> list = monitoringCurrentMapper.queryPackageEOList(monitoringCurrent);
		if (StringUtils.isNotBlank(id)) {
			// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
			if (!PageUtil.hasRecord(list, id)) {
				// 在查询条件中增加ID
				MonitoringCurrent newRecord = new MonitoringCurrent();
				newRecord.setId(id);
				newRecord.setDprtcode(monitoringCurrent.getDprtcode());
				newRecord.getParamsMap().put("fjzch", monitoringCurrent.getParamsMap().get("fjzch"));
				List<MonitoringCurrent> newRecordList = monitoringCurrentMapper.queryPackageEOList(monitoringCurrent);
				if (newRecordList != null && newRecordList.size() == 1) {
					list.add(0, newRecordList.get(0));
				}
			}
		}
		List<PlanUsage> planUsageList = planUsageMapper.queryByFjzch((String)monitoringCurrent.getParamsMap().get("fjzch"), monitoringCurrent.getDprtcode());
		Date currentDate = commonService.getSysdate();//当前时间
		for (MonitoringCurrent monitoringCurrent2 : list) {
			setNextDate(monitoringCurrent2, planUsageList, currentDate);
		}
		//设置默认排序排序
		if("auto".equals(monitoringCurrent.getPagination().getSort())){
			Collections.sort(list, new DefaultOrderCompare());//默认排序调用方法
		}
		PageUtil.hasRecord(list, id);
		List<Map<String, Object>> resultList = monitoringCurrentEOToMap(list);
		map.put("rows", resultList);
		map.put("total", resultList.size());
		map.put("planUsageList", planUsageToMap(planUsageList));
		//获取阈值
//		map.put("thresholdAirList", thresholdAirMapper.queryByFjzch((String)monitoringCurrent.getParamsMap().get("fjzch"), monitoringCurrent.getDprtcode()));
		return map;
	}
	
	/**
	 * @Description EO对象转map
	 * @CreateTime 2018-4-11 上午9:58:54
	 * @CreateBy 刘兵
	 * @param list
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> monitoringCurrentEOToMap(List<MonitoringCurrent> list){
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (MonitoringCurrent monitoringCurrent : list) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			EngineeringOrder engineeringOrder = monitoringCurrent.getEngineeringOrder();
			if(null != engineeringOrder){
				monitoringCurrent.getParamsMap().put("id", engineeringOrder.getId());
				monitoringCurrent.getParamsMap().put("eobh", engineeringOrder.getEobh());
				monitoringCurrent.getParamsMap().put("bb", engineeringOrder.getBb());
				monitoringCurrent.getParamsMap().put("eozt", engineeringOrder.getEozt());
				monitoringCurrent.getParamsMap().put("gzlx", engineeringOrder.getGzlx());
				monitoringCurrent.getParamsMap().put("zxfs", engineeringOrder.getZxfs());
			}
			resultMap.put("id", monitoringCurrent.getId());
			resultMap.put("dprtcode", monitoringCurrent.getDprtcode());
			resultMap.put("zjqdid", monitoringCurrent.getZjqdid());
			resultMap.put("bjh", monitoringCurrent.getBjh());
			resultMap.put("xlh", monitoringCurrent.getXlh());
			resultMap.put("hdwz", monitoringCurrent.getHdwz());
			resultMap.put("eoxc", monitoringCurrent.getEoxc());
			resultMap.put("paramsMap", monitoringCurrent.getParamsMap());
			resultList.add(resultMap);
		}
		return resultList;
	}
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(生产指令监控信息)
	 * @CreateTime 2018-5-8 下午2:49:06
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 当前监控数据
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryAllPagePOList(MonitoringCurrent monitoringCurrent) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = monitoringCurrent.getId();
		monitoringCurrent.setId(null);
		List<MonitoringCurrent> list = monitoringCurrentMapper.queryPackagePOList(monitoringCurrent);
		if (StringUtils.isNotBlank(id)) {
			// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
			if (!PageUtil.hasRecord(list, id)) {
				// 在查询条件中增加ID
				MonitoringCurrent newRecord = new MonitoringCurrent();
				newRecord.setId(id);
				newRecord.setDprtcode(monitoringCurrent.getDprtcode());
				newRecord.getParamsMap().put("fjzch", monitoringCurrent.getParamsMap().get("fjzch"));
				List<MonitoringCurrent> newRecordList = monitoringCurrentMapper.queryPackagePOList(monitoringCurrent);
				if (newRecordList != null && newRecordList.size() == 1) {
					list.add(0, newRecordList.get(0));
				}
			}
		}
		List<PlanUsage> planUsageList = planUsageMapper.queryByFjzch((String)monitoringCurrent.getParamsMap().get("fjzch"), monitoringCurrent.getDprtcode());
		Date currentDate = commonService.getSysdate();//当前时间
		for (MonitoringCurrent monitoringCurrent2 : list) {
			setNextDate(monitoringCurrent2, planUsageList, currentDate);
		}
		//设置默认排序排序
		if("auto".equals(monitoringCurrent.getPagination().getSort())){
			Collections.sort(list, new DefaultOrderCompare());//默认排序调用方法
		}
		PageUtil.hasRecord(list, id);
		List<Map<String, Object>> resultList = monitoringCurrentPOToMap(list);
		map.put("rows", resultList);
		map.put("total", resultList.size());
		map.put("planUsageList", planUsageToMap(planUsageList));
		//获取阈值
//		map.put("thresholdAirList", thresholdAirMapper.queryByFjzch((String)monitoringCurrent.getParamsMap().get("fjzch"), monitoringCurrent.getDprtcode()));
		return map;
	}
	
	/**
	 * @Description 生产指令对象转map
	 * @CreateTime 2018-5-8 下午3:28:39
	 * @CreateBy 刘兵
	 * @param list
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> monitoringCurrentPOToMap(List<MonitoringCurrent> list){
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (MonitoringCurrent monitoringCurrent : list) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", monitoringCurrent.getId());
			resultMap.put("dprtcode", monitoringCurrent.getDprtcode());
			resultMap.put("zjqdid", monitoringCurrent.getZjqdid());
			resultMap.put("hdwz", monitoringCurrent.getHdwz());
			resultMap.put("paramsMap", monitoringCurrent.getParamsMap());
			resultList.add(resultMap);
		}
		return resultList;
	}
	
	/**
	 * @Description 根据条件查询已选中的监控数据列表
	 * @CreateTime 2017-10-14 下午4:05:52
	 * @CreateBy 刘兵
	 * @param monitoringWorkpackage 待组包的监控项目
	 * @return List<MonitoringWorkpackage> 已选中的监控数据集合
	 */
	@Override
	public List<MonitoringWorkpackage> queryCheckedList(MonitoringWorkpackage monitoringWorkpackage){
		return monitoringWorkpackageMapper.queryCheckedList(monitoringWorkpackage);
	}
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(飞机维修项目监控信息)(导出)
	 * @CreateTime 2017-9-25 下午3:19:22
	 * @CreateBy 刘兵
	 * @param monitoringCurrentParam 当前监控数据参数
	 * @return List<MonitoringCurrent>
	 */
	@Override
	public List<MonitoringCurrent> exportExcelMt(MonitoringCurrent monitoringCurrentParam) {
		List<MonitoringCurrent> list = monitoringCurrentMapper.queryPackageMaintenanceList(monitoringCurrentParam);
		Date currentDate = commonService.getSysdate();//当前时间
		List<PlanUsage> planUsageList = planUsageMapper.queryByFjzch((String)monitoringCurrentParam.getParamsMap().get("fjzch"), monitoringCurrentParam.getDprtcode());
		for (MonitoringCurrent monitoringCurrent : list) {
			MaintenanceProject maintenanceProject = monitoringCurrent.getMaintenanceProject();
			if(null != maintenanceProject){
				monitoringCurrent.getParamsMap().put("wxxmlx", MaintenanceProjectTypeEnum.getName(maintenanceProject.getWxxmlx()));
				monitoringCurrent.getParamsMap().put("rwh", maintenanceProject.getRwh());
				monitoringCurrent.getParamsMap().put("bb", maintenanceProject.getBb());
				monitoringCurrent.getParamsMap().put("ckh", maintenanceProject.getCkh());
				monitoringCurrent.getParamsMap().put("rwms", maintenanceProject.getRwms());
				monitoringCurrent.getParamsMap().put("gzlx", maintenanceProject.getGzlx());
				formatWorkOrderPackage(monitoringCurrent);
				formatMonitorData(monitoringCurrent , 0);
				setNextDate(monitoringCurrent, planUsageList, currentDate);
			}
			formatLastData(monitoringCurrent);
		}
		//设置默认排序排序
		if("auto".equals(monitoringCurrentParam.getPagination().getSort())){
			Collections.sort(list, new DefaultOrderCompare());//默认排序调用方法
		}
		return list;
	}
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(EO监控信息)(导出)
	 * @CreateTime 2017-9-25 下午3:19:22
	 * @CreateBy 刘兵
	 * @param monitoringCurrentParam 当前监控数据参数
	 * @return List<MonitoringCurrent>
	 */
	@Override
	public List<MonitoringCurrent> doExportExcelEO(MonitoringCurrent monitoringCurrentParam) {
		StringBuffer buffer = null;
		List<MonitoringCurrent> list = monitoringCurrentMapper.queryPackageEOList(monitoringCurrentParam);
		Date currentDate = commonService.getSysdate();//当前时间
		List<PlanUsage> planUsageList = planUsageMapper.queryByFjzch((String)monitoringCurrentParam.getParamsMap().get("fjzch"), monitoringCurrentParam.getDprtcode());
		for (MonitoringCurrent monitoringCurrent : list) {
			EngineeringOrder engineeringOrder = monitoringCurrent.getEngineeringOrder();
			if(null != engineeringOrder){
				buffer = new StringBuffer();
				String zxfs = ExecutionEnum.getName(Integer.valueOf(engineeringOrder.getZxfs().toString()));
				if(null != monitoringCurrent.getEoxc() && new BigDecimal("3").compareTo(engineeringOrder.getZxfs()) == 0){
					zxfs = buffer.append("分段：第").append(monitoringCurrent.getEoxc()).append("次").toString();
				}
				monitoringCurrent.getParamsMap().put("eobh", engineeringOrder.getEobh());
				monitoringCurrent.getParamsMap().put("bb", engineeringOrder.getBb());
				monitoringCurrent.getParamsMap().put("eozt", engineeringOrder.getEozt());
				monitoringCurrent.getParamsMap().put("gzlx", engineeringOrder.getGzlx());
				monitoringCurrent.getParamsMap().put("zxfs", zxfs);
				formatMonitorData(monitoringCurrent, Integer.valueOf(engineeringOrder.getZxfs().toString()));
				setNextDate(monitoringCurrent, planUsageList, currentDate);
			}
			formatLastData(monitoringCurrent);
		}
		//设置默认排序
		if("auto".equals(monitoringCurrentParam.getPagination().getSort())){
			Collections.sort(list, new DefaultOrderCompare());//默认排序调用方法
		}
		return list;
	}
	
	/**
	 * @Description 格式化上次数据
	 * @CreateTime 2017-12-19 下午4:13:28
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 监控数据
	 * @throws BusinessException
	 */
	private void formatLastData(MonitoringCurrent monitoringCurrent){
		if(null != monitoringCurrent.getParamsMap().get("scsj")){
			StringBuffer scjh = new StringBuffer();
			StringBuffer scsj =  new StringBuffer();
			String[] arr1 = String.valueOf(monitoringCurrent.getParamsMap().get("scsj")).split(",");
			for (int i  = 0 ; i < arr1.length ; i++) {
				String[] arr2 = arr1[i].split("#_#", -1);
				scjh.append(formatJkz(arr2[0], arr2[1], " ", "", true));
				scsj.append(formatJkz(arr2[0], arr2[2], " ", "", true));
				if(i != arr1.length - 1){
					scjh.append("\n");
					scsj.append("\n");
				}
			}
			monitoringCurrent.getParamsMap().put("scjh", scjh);
			monitoringCurrent.getParamsMap().put("scsj", scsj);
		}
	}
	
	/**
	 * @Description 格式化监控数据
	 * @CreateTime 2017-12-19 下午4:13:28
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 监控数据
	 * @param zxfs 执行方式,0:没有执行方式,1:单次,2:重复,3:多段
	 * @throws BusinessException
	 */
	private void formatMonitorData(MonitoringCurrent monitoringCurrent, int zxfs){
		if(null != monitoringCurrent.getParamsMap().get("jhsjsj")){
			StringBuffer xcjhqsd = new StringBuffer();
			StringBuffer zq =  new StringBuffer();
			StringBuffer rc =  new StringBuffer();
			StringBuffer xcjh =  new StringBuffer();
			StringBuffer sj =  new StringBuffer();
			StringBuffer sy =  new StringBuffer();
			String[] arr1 = String.valueOf(monitoringCurrent.getParamsMap().get("jhsjsj")).split(",");
			for (int i  = 0 ; i < arr1.length ; i++) {
				String[] arr2 = arr1[i].split("#_#", -1);
				xcjhqsd.append(formatJkz(arr2[0], arr2[1], " ", "", true));
				xcjh.append(formatJkz(arr2[0], arr2[2], "0", "", true));
				sj.append(formatJkz(arr2[0], arr2[3], "0", "", true));
				sy.append(formatJkz(arr2[0], arr2[4], "0", "11", true));
				if(0 == zxfs || 2 == zxfs){
					zq.append(formatJkz(arr2[0], arr2[5], " ", arr2[6], true));
					rc.append("-").append(formatJkz(arr2[0], arr2[7], "0", arr2[8], false));
					rc.append("/+").append(formatJkz(arr2[0], arr2[9], "0", arr2[8], true));
				}
				if(i != arr1.length - 1){
					xcjhqsd.append("\n");
					xcjh.append("\n");
					sj.append("\n");
					sy.append("\n");
					if(0 == zxfs || 2 == zxfs){
						zq.append("\n");
						rc.append("\n");
					}
				}
			}
			monitoringCurrent.getParamsMap().put("xcjhqsd", xcjhqsd);
			monitoringCurrent.getParamsMap().put("zq", zq);
			monitoringCurrent.getParamsMap().put("rc", rc);
			monitoringCurrent.getParamsMap().put("xcjh", xcjh);
			monitoringCurrent.getParamsMap().put("sj", sj);
			monitoringCurrent.getParamsMap().put("sy", sy);
		}
	}
	
	/**
	 * @Description 设置下次计划日期
	 * @CreateTime 2017-12-19 下午4:13:28
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 监控数据
	 * @param planUsageList 日使用量
	 * @param currentDate 当前时间
	 * @throws BusinessException
	 */
	private void setNextDate(MonitoringCurrent monitoringCurrent, List<PlanUsage> planUsageList, Date currentDate){
		if(null != monitoringCurrent.getParamsMap().get("jhsjsj")){
			int syts = 0;
			int hdwz = monitoringCurrent.getHdwz() != null? monitoringCurrent.getHdwz():0;
			String[] arr1 = String.valueOf(monitoringCurrent.getParamsMap().get("jhsjsj")).split(",");
			for (int i  = 0 ; i < arr1.length ; i++) {
				String[] arr2 = arr1[i].split("#_#", -1);
				int temp = formatSyts(planUsageList, arr2[0], arr2[4]);
				if(i == 0){
					syts = temp;
				}
				if((hdwz == 1 && syts < temp) || (hdwz == 0 && syts > temp)){
					syts = temp;
				}
			}
			//获取下次计划日期
			String xcjhrq = DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, currentDate);
			if(syts != 0){
				try {
					xcjhrq = DateUtil.getOffsetDate(xcjhrq, syts,  Calendar.DATE);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			monitoringCurrent.getParamsMap().put("xcjhrq", xcjhrq);
		}
	}
	
	/**
	 * @Description 格式化剩余天数
	 * @CreateTime 2017-12-20 下午3:38:05
	 * @CreateBy 刘兵
	 * @param planUsageList 日使用量
	 * @param jklbh 监控类编号
	 * @param sy 剩余
	 * @return 剩余天数
	 */
	private int formatSyts(List<PlanUsage> planUsageList, String jklbh, String sy){
		Double syts = 0.0;
		if(StringUtils.isNotBlank(sy)){
			syts = Double.valueOf(sy);
			for (PlanUsage planUsage : planUsageList) {
				if(jklbh.equals(planUsage.getJklbh())){
					if(StringUtils.isNotBlank(planUsage.getRsyl()) && !"0".equals(planUsage.getRsyl())){
						syts = Double.valueOf(sy)/Double.valueOf(planUsage.getRsyl());
					}else{
						//判断是否是时间格式
						if(MonitorProjectEnum.isTime(jklbh)){
							syts = Double.valueOf(sy)/60;
						}
					}
					break;
				}
			}
			syts = Math.floor(syts);
		}
		return syts.intValue();
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
	
	/**
	 * @Description 格式化工单工包
	 * @CreateTime 2017-12-19 下午4:13:28
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 监控数据
	 * @throws BusinessException
	 */
	private void formatWorkOrderPackage(MonitoringCurrent monitoringCurrent){
		if(null != monitoringCurrent.getParamsMap().get("gdgbstr")){
			StringBuffer gdbh = new StringBuffer();
			StringBuffer gbbh =  new StringBuffer();
			String[] arr1 = String.valueOf(monitoringCurrent.getParamsMap().get("gdgbstr")).split(",");
			for (int i  = 0 ; i < arr1.length ; i++) {
				String[] arr2 = arr1[i].split("#_#", -1);
				gdbh.append(arr2[1]);
				if(gbbh.indexOf(arr2[3]) == -1){
					gbbh.append(arr2[3]);
				}
				if(i != arr1.length - 1){
					gdbh.append("\n");
					gbbh.append("\n");
				}
			}
			monitoringCurrent.getParamsMap().put("gdbh", gdbh);
			monitoringCurrent.getParamsMap().put("gbbh", gbbh);
		}
	}
	
	/**
	 * @Description 验证飞机权限
	 * @CreateTime 2017年9月25日 下午3:04:16
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param user
	 * @throws BusinessException
	 */
	private void validationFjzch(Workpackage record, User user) throws BusinessException {
		List<String> fjzchList = new ArrayList<String>();
		fjzchList.add(record.getFjzch());
		planeModelDataService.existsAircraft4Expt(user.getId(), user.getUserType(), record.getDprtcode(), fjzchList);
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
	 * @Description 验证表单重复提交,当前状态是否存在于数组中,不存在:抛出异常message
	 * @CreateTime 2017-10-17 上午11:18:54
	 * @CreateBy 刘兵
	 * @param ztArr 可操作状态数组
	 * @param currentZt 当前状态
	 * @param message 异常信息
	 * @throws BusinessException
	 */
	private void validation4CurrentZt(Integer[] ztArr, Integer currentZt,String message) throws BusinessException{
		if(currentZt != null && !ArrayUtils.contains(ztArr,currentZt)){
			throw new BusinessException(message);
		}
	}
	
	/**
	 * @Description 默认排序
	 * @CreateTime 2017-12-21 下午3:41:49
	 * @CreateBy 刘兵
	 */
	class DefaultOrderCompare implements Comparator<MonitoringCurrent> {

		@Override
		public int compare(MonitoringCurrent o1, MonitoringCurrent o2) {
			int yjbs1 = o1.getParamsMap().get("isWarning")==null?0:Integer.valueOf(o1.getParamsMap().get("isWarning").toString());
			int yjbs2 = o2.getParamsMap().get("isWarning")==null?0:Integer.valueOf(o2.getParamsMap().get("isWarning").toString());
			if(yjbs1 != yjbs2){
				return yjbs2 - yjbs1;
			}else{
				if(null == o1.getParamsMap().get("xcjhrq")){
					if(null == o2.getParamsMap().get("xcjhrq")){
						return 0;
					}else{
						return 1;
					}
				}else{
					if(null == o2.getParamsMap().get("xcjhrq")){
						return -1;
					}else{
						try {
							Date xcjhrq1 = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, o1.getParamsMap().get("xcjhrq").toString());
							Date xcjhrq2 = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, o2.getParamsMap().get("xcjhrq").toString());
							return xcjhrq1.compareTo(xcjhrq2);
						} catch (ParseException e) {
							return 0;
						}
					}
				} 
			}
		}
	}

}
