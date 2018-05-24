package com.eray.thjw.project2.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.MonitoringLast;
import com.eray.thjw.produce.po.MonitoringObject;
import com.eray.thjw.produce.po.MonitoringPlan;
import com.eray.thjw.produce.service.MonitorDataService;
import com.eray.thjw.project2.dao.InstructionsourceMapper;
import com.eray.thjw.project2.dao.MaintenanceProjectMapper;
import com.eray.thjw.project2.dao.MaintenanceSchemeMapper;
import com.eray.thjw.project2.dao.ProjectEffectiveMapper;
import com.eray.thjw.project2.dao.ProjectRelationshipEffectiveMapper;
import com.eray.thjw.project2.po.Instructionsource;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.po.MaintenanceScheme;
import com.eray.thjw.project2.po.ProjectMonitor;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.service.InstructionsourceService;
import com.eray.thjw.project2.service.MaintenanceSchemeService;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.common.RevMarkEnum;
import enu.produce.MaintenanceTypeEnum;
import enu.produce.NodeEnum;
import enu.project2.MaintenanceSchemeStatusEnum;
import enu.project2.MonitorProjectEnum;
import enu.project2.ProjectBusinessEnum;
import enu.project2.SendOrderEnum;
import enu.project2.TodoEnum;
import enu.project2.TodoStatusEnum;

/**
 * @Description 维修方案service实现类
 * @CreateTime 2017年8月16日 下午2:36:11
 * @CreateBy 韩武
 */
@Service
public class MaintenanceSchemeServiceImpl implements MaintenanceSchemeService {

	@Resource
	private MaintenanceSchemeMapper maintenanceSchemeMapper;

	@Resource
	private SaibongUtilService saibongUtilService; 
	@Resource
	private TodoService todoService; 
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private TodorsService todorsService;
	
	@Resource
	private CommonService commonService;
	
	@Resource
	private InstructionsourceMapper instructionsourceMapper;
	
	@Resource
	private MaintenanceProjectMapper maintenanceProjectMapper;
	
	@Resource
	private ProjectEffectiveMapper projectEffectiveMapper;
	
	@Resource
	private ProjectRelationshipEffectiveMapper projectRelationshipEffectiveMapper;
	
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	@Resource
	private InstructionsourceService instructionsourceService;
	
	@Resource
	private MonitorDataService monitorDataService;
	
	/** 当前维修方案 */
	private final static String MP_TYPE_CURRENT = "current";
	
	/** 前一个维修方案 */
	private final static String MP_TYPE_PREVIOUS = "previous";

	/**
	 * @Description 根据飞机机型查询维修方案版本
	 * @CreateTime 2017年8月16日 下午2:35:31
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	@Override
	public List<MaintenanceScheme> queryByFjjx(MaintenanceScheme scheme) {
		return maintenanceSchemeMapper.queryByFjjx(scheme);
	}

	/**
	 * @Description 保存维修方案
	 * @CreateTime 2017年8月16日 下午2:35:37
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws SaibongException
	 * @throws BusinessException 
	 */
	@Override
	public void doSave(MaintenanceScheme scheme) throws SaibongException, BusinessException {
		
		// 验证机型权限
		validatePlaneModel(scheme);
		
		User user = ThreadVarUtil.getUser();
		scheme.setZdrid(user.getId());
		scheme.setZdsj(new Date());
		scheme.setZdbmid(user.getBmdm());
		scheme.setCzls(UUID.randomUUID().toString());
		
		if(StringUtils.isBlank(scheme.getId())){	// 新增
			scheme.setId(UUID.randomUUID().toString());
			scheme.setZxbs(RevMarkEnum.INITIAL.getId());
			scheme.setWxfabh(saibongUtilService.generate(user.getJgdm(), SaiBongEnum.WXFA.getName()));
			scheme.setZt(MaintenanceSchemeStatusEnum.SAVE.getId());
			scheme.setLogOperationEnum(LogOperationEnum.SAVE_WO);
			maintenanceSchemeMapper.insertSelective(scheme);
			// 写入日志
			commonRecService.write(scheme.getId(), TableEnum.B_G2_011, user.getId(), scheme.getCzls(),
					scheme.getLogOperationEnum(), UpdateTypeEnum.SAVE, scheme.getId());
		}else{	// 修改
			
			// 更新维修项目版本与维修方案版本一致
			updateMaintenanceProjectVersion(scheme);
						
			scheme.setLogOperationEnum(LogOperationEnum.EDIT);
			maintenanceSchemeMapper.updateByPrimaryKeySelective(scheme);	
			// 写入日志
			commonRecService.write(scheme.getId(), TableEnum.B_G2_011, user.getId(), scheme.getCzls(),
					scheme.getLogOperationEnum(), UpdateTypeEnum.UPDATE, scheme.getId());
			
		}
		
		// 保存下达指令来源
		saveInstructionsource(scheme);
		
		// 保存待办事项明细表
		saveTodo(scheme);
	}
	
	/**
	 * @Description 更新维修项目版本与维修方案版本一致
	 * @CreateTime 2017年12月21日 上午9:54:36
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws BusinessException 
	 */
	private void updateMaintenanceProjectVersion(MaintenanceScheme now) throws BusinessException{
		
		// 修改之前的数据
		MaintenanceScheme old = maintenanceSchemeMapper.selectByPrimaryKey(now.getId());
		// 改版前数据
		MaintenanceScheme lastVersion = maintenanceSchemeMapper.selectByPrimaryKey(old.getfBbid());
		
		if(lastVersion != null && lastVersion.getBb() != null && now.getBb() != null 
				&& lastVersion.getBb().compareTo(now.getBb()) >= 0){
			throw new BusinessException("版本号不能小于或等于改版前的版本号！");
		}
		if(old.getBb() != null && now.getBb() != null && now.getBb().compareTo(old.getBb()) != 0){
			
			now.setWxfabh(old.getWxfabh());
			now.setJx(old.getJx());
			now.getParamsMap().put("oldBb", old.getBb());
			maintenanceProjectMapper.updateVersion(now);
		}
	}

	/**
	 * @Description 提交维修方案
	 * @CreateTime 2017年8月16日 下午2:35:42
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws SaibongException
	 * @throws BusinessException 
	 */
	@Override
	public void doSubmit(MaintenanceScheme scheme) throws SaibongException, BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间		
		
		// 验证机型权限
		validatePlaneModel(scheme);
		
		// 验证必须有维修项目版本相同时才可提交
		validateExistProject(scheme);
				
		User user = ThreadVarUtil.getUser();
		scheme.setZdrid(user.getId());
		scheme.setZdsj(new Date());
		scheme.setZdbmid(user.getBmdm());
		scheme.setCzls(UUID.randomUUID().toString());
		scheme.setLogOperationEnum(LogOperationEnum.SUBMIT_WO);
		scheme.setZt(MaintenanceSchemeStatusEnum.SUBMIT.getId());
		
		if(StringUtils.isBlank(scheme.getId())){	// 新增
			scheme.setId(UUID.randomUUID().toString());
			scheme.setWxfabh(saibongUtilService.generate(user.getJgdm(), SaiBongEnum.WXFA.getName()));
			scheme.setZxbs(RevMarkEnum.INITIAL.getId());
			maintenanceSchemeMapper.insertSelective(scheme);
			// 写入日志
			commonRecService.write(scheme.getId(), TableEnum.B_G2_011, user.getId(), scheme.getCzls(),
					scheme.getLogOperationEnum(), UpdateTypeEnum.SAVE, scheme.getId());
		}else{	// 修改
			
			// 验证状态为1保存/5审核驳回/6审批驳回
			MaintenanceScheme dbData = maintenanceSchemeMapper.selectByPrimaryKey(scheme.getId());
			if(!MaintenanceSchemeStatusEnum.SAVE.getId().equals(dbData.getZt()) 
					&& !MaintenanceSchemeStatusEnum.AUDIT_REJEC.getId().equals(dbData.getZt()) 
					&& !MaintenanceSchemeStatusEnum.APPROVE_REJEC.getId().equals(dbData.getZt())){
				throw new BusinessException("只有状态为保存/审核驳回/审批驳回的维修方案才可提交");
			}
			
			// 更新维修项目版本与维修方案版本一致
			updateMaintenanceProjectVersion(scheme);
			
			maintenanceSchemeMapper.updateByPrimaryKeySelective(scheme);	
			// 写入日志
			commonRecService.write(scheme.getId(), TableEnum.B_G2_011, user.getId(), scheme.getCzls(),
					scheme.getLogOperationEnum(), UpdateTypeEnum.UPDATE, scheme.getId());
		}
		
		// 保存下达指令来源
		saveInstructionsource(scheme);
		
		// 保存待办事项明细表
		saveTodo(scheme);
		
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(scheme.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(5);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		
		MaintenanceScheme schemeNew=maintenanceSchemeMapper.selectByPrimaryKey(scheme.getId());
		//拼接说明
		StringBuffer strSm = new StringBuffer();
		strSm.append("请审核");
		strSm.append(schemeNew.getJx());
		strSm.append("机型的");
		strSm.append(schemeNew.getWxfabh());
		strSm.append(" R");
		strSm.append(scheme.getBb());
		strSm.append("维修方案。");
		todoService.insertSelectiveTechnical(schemeNew,strSm.toString(),"project2:maintenanceproject:audit:main",NodeEnum.NODE2.getId(),null,TodoEnum.MP.getId());
	}

	/**
	 * @Description 改版维修方案
	 * @CreateTime 2017年8月16日 下午2:35:48
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws SaibongException
	 * @throws BusinessException 
	 */
	@Override
	public void doRevision(MaintenanceScheme scheme) throws SaibongException, BusinessException {
		
		// 验证机型权限
		validatePlaneModel(scheme);
		
		String oldId = scheme.getId();
		User user = ThreadVarUtil.getUser();
		scheme.setZdrid(user.getId());
		scheme.setZdsj(new Date());
		scheme.setZdbmid(user.getBmdm());
		scheme.setCzls(UUID.randomUUID().toString());
		scheme.setLogOperationEnum(LogOperationEnum.REVISION);
		scheme.setId(UUID.randomUUID().toString());
		scheme.setZxbs(RevMarkEnum.INITIAL.getId());
		scheme.setZt(MaintenanceSchemeStatusEnum.SAVE.getId());
		
		// 修改前版本的后版本id
		MaintenanceScheme previous = maintenanceSchemeMapper.selectByPrimaryKey(oldId);
		if(previous != null){
			previous.setbBbid(scheme.getId());
			previous.setZdrid(user.getId());
			previous.setZdsj(new Date());
			previous.setZdbmid(user.getBmdm());
			maintenanceSchemeMapper.updateByPrimaryKeySelective(previous);	
			// 写入日志
			commonRecService.write(previous.getId(), TableEnum.B_G2_011, user.getId(), scheme.getCzls(),
					scheme.getLogOperationEnum(), UpdateTypeEnum.UPDATE, previous.getId());
			// 设置当前版本的前版本id
			scheme.setfBbid(previous.getId());
		}
		
		maintenanceSchemeMapper.insertSelective(scheme);	
		// 写入日志
		commonRecService.write(scheme.getId(), TableEnum.B_G2_011, user.getId(), scheme.getCzls(),
				scheme.getLogOperationEnum(), UpdateTypeEnum.SAVE, scheme.getId());
		
		// 保存下达指令来源
		saveInstructionsource(scheme);
		
		// 保存待办事项明细表
		saveTodo(scheme);
		
	}

	/**
	 * @Description 提交生产维修方案
	 * @CreateTime 2017年8月16日 下午2:35:55
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws BusinessException 
	 */
	@Override
	public void doSubmitProduction(MaintenanceScheme scheme) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间	
		
		// 验证机型权限
		validatePlaneModel(scheme);
		
		// 验证状态为4已批准
		MaintenanceScheme dbData = maintenanceSchemeMapper.selectByPrimaryKey(scheme.getId());
		if(!MaintenanceSchemeStatusEnum.APPROVED.getId().equals(dbData.getZt())){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
					
		User user = ThreadVarUtil.getUser();
		scheme.setZdrid(user.getId());
		scheme.setZdsj(new Date());
		scheme.setZdbmid(user.getBmdm());
		scheme.setCzls(UUID.randomUUID().toString());
		scheme.setLogOperationEnum(LogOperationEnum.SUBMIT_PRODUCTION_CONFIRM);
		scheme.setZt(MaintenanceSchemeStatusEnum.TO_BE_EFFECT.getId());
		
		maintenanceSchemeMapper.updateByPrimaryKeySelective(scheme);	
		// 写入日志
		commonRecService.write(scheme.getId(), TableEnum.B_G2_011, user.getId(), scheme.getCzls(),
				scheme.getLogOperationEnum(), UpdateTypeEnum.UPDATE, scheme.getId());
		
		// 更新下达指令业务单号状态
		instructionsourceMapper.updateYwdjztByZlid(scheme.getZt(), scheme.getId());
		// 更新待办事宜状态
		todorsService.updateYwdjztByYwid(scheme.getZt(),scheme.getId());
		
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(scheme.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(11);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		
		//拼接说明
		StringBuffer strSm = new StringBuffer();
		strSm.append("请确认");
		strSm.append(dbData.getJx());
		strSm.append("机型的");
		strSm.append(dbData.getWxfabh());
		strSm.append(" R");
		strSm.append(dbData.getBb());
		strSm.append("维修方案。");
		todoService.insertSelectiveTechnical(dbData,strSm.toString(),"project2:maintenanceproject:effect:main",NodeEnum.NODE12.getId(),null,TodoEnum.MP.getId());
	}
	
	
	/**
	 * @Description 保存下达指令来源
	 * @CreateTime 2017年8月21日 下午3:46:51
	 * @CreateBy 韩武
	 * @param record
	 */
	private void saveInstructionsource(MaintenanceScheme record) {
		
		List<Instructionsource> isList = record.getInstructionsourceList();
		
		record = maintenanceSchemeMapper.selectByPrimaryKey(record.getId());
		
		if(isList != null && !isList.isEmpty()){
			for (Instructionsource is : isList) {
				is.setZlbh(record.getWxfabh());
				is.setZlbb(record.getBb().toString());
				is.setYwdjzt(record.getZt());
			}
		}
		
		record.setInstructionsourceList(isList);
		
		// 新增下达指令
		instructionsourceService.updateInstructionSourceList(
				isList, ProjectBusinessEnum.MP.getId(), record.getId(), record.getDprtcode());
	}
	
	/**
	 * @Description 保存待办事项明细表
	 * @CreateTime 2017年8月21日 下午3:33:01
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException 
	 */
	private void saveTodo(MaintenanceScheme record) throws BusinessException{
		User user = ThreadVarUtil.getUser();//当前登陆人
		Date currentDate = commonService.getSysdate();//当前时间		
		// 获得评估单id集合
		List<String> lyidList = new ArrayList<String>();
		for (Instructionsource is : record.getInstructionsourceList()) {
			lyidList.add(is.getPgdid());
		}
		
		record = maintenanceSchemeMapper.selectByPrimaryKey(record.getId());
		
		// 删除待办事项明细表
		todorsService.deleteTodorsByYwid(record.getId());
		// 新增待办事项明细表
		todorsService.insertTodorsList(record.getJx(), lyidList, SendOrderEnum.XDWXFA.getId(), record.getZdrid(),
				record.getId(), record.getWxfabh(), record.getBb().toString(), null,record.getZt());
		
		
	}


	/**
	 * @Description 维修方案审核通过
	 * @CreateTime 2017年8月21日 下午5:06:41
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws SaibongException
	 * @throws BusinessException
	 */
	@Override
	public void doAuditAgree(MaintenanceScheme scheme) throws SaibongException,
			BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间	
		// 验证机型权限
		validatePlaneModel(scheme);
				
		// 验证状态为2提交
		MaintenanceScheme dbData = maintenanceSchemeMapper.selectByPrimaryKey(scheme.getId());
		if(!MaintenanceSchemeStatusEnum.SUBMIT.getId().equals(dbData.getZt())){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
					
		User user = ThreadVarUtil.getUser();
		scheme.setCzls(UUID.randomUUID().toString());
		scheme.setLogOperationEnum(LogOperationEnum.YISHENHE_WO);
		scheme.setZt(MaintenanceSchemeStatusEnum.AUDITED.getId());
		
		scheme.setShrid(user.getId());
		scheme.setShsj(new Date());
		scheme.setShbmid(user.getBmdm());
		scheme.setShjl(MaintenanceSchemeStatusEnum.AUDITED.getId());
		
		maintenanceSchemeMapper.updateByPrimaryKeySelective(scheme);	
		// 写入日志
		commonRecService.write(scheme.getId(), TableEnum.B_G2_011, user.getId(), scheme.getCzls(),
				scheme.getLogOperationEnum(), UpdateTypeEnum.UPDATE, scheme.getId());
		
		// 更新下达指令业务单号状态
		instructionsourceMapper.updateYwdjztByZlid(scheme.getZt(), scheme.getId());
		// 更新待办事宜状态
		todorsService.updateYwdjztByYwid(scheme.getZt(),scheme.getId());
		
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(scheme.getId());
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
		strSm.append(dbData.getJx());
		strSm.append("机型的");
		strSm.append(dbData.getWxfabh());
		strSm.append(" R");
		strSm.append(dbData.getBb());
		strSm.append("维修方案。");
		todoService.insertSelectiveTechnical(dbData,strSm.toString(),"project2:maintenanceproject:approval:main",NodeEnum.NODE3.getId(),null,TodoEnum.MP.getId());
		
	}

	/**
	 * @Description 维修方案审核驳回
	 * @CreateTime 2017年8月21日 下午5:06:41
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws SaibongException
	 * @throws BusinessException
	 */
	@Override
	public void doAuditReject(MaintenanceScheme scheme)
			throws SaibongException, BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		// 验证机型权限
		validatePlaneModel(scheme);
				
		// 验证状态为2提交
		MaintenanceScheme dbData = maintenanceSchemeMapper.selectByPrimaryKey(scheme.getId());
		if(!MaintenanceSchemeStatusEnum.SUBMIT.getId().equals(dbData.getZt())){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
					
		User user = ThreadVarUtil.getUser();
		scheme.setCzls(UUID.randomUUID().toString());
		scheme.setLogOperationEnum(LogOperationEnum.SHENHEBOHUI_WO);
		scheme.setZt(MaintenanceSchemeStatusEnum.AUDIT_REJEC.getId());
		
		scheme.setShrid(user.getId());
		scheme.setShsj(new Date());
		scheme.setShbmid(user.getBmdm());
		scheme.setShjl(MaintenanceSchemeStatusEnum.AUDIT_REJEC.getId());
		
		maintenanceSchemeMapper.updateByPrimaryKeySelective(scheme);	
		// 写入日志
		commonRecService.write(scheme.getId(), TableEnum.B_G2_011, user.getId(), scheme.getCzls(),
				scheme.getLogOperationEnum(), UpdateTypeEnum.UPDATE, scheme.getId());
		
		// 更新下达指令业务单号状态
		instructionsourceMapper.updateYwdjztByZlid(scheme.getZt(), scheme.getId());
		// 更新待办事宜状态
		todorsService.updateYwdjztByYwid(scheme.getZt(),scheme.getId());
		
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(scheme.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(2);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		
		//拼接说明
		StringBuffer strSm = new StringBuffer();
		strSm.append(dbData.getJx());
		strSm.append("机型的");
		strSm.append(dbData.getWxfabh());
		strSm.append(" R");
		strSm.append(dbData.getBb());
		strSm.append("维修方案已经驳回，请重新提交。");
		todoService.insertSelectiveTechnical(dbData,strSm.toString(),"project2:maintenanceproject:main:02",NodeEnum.NODE5.getId(),dbData.getZdrid(),TodoEnum.MP.getId());
	}

	/**
	 * @Description 维修方案审批通过
	 * @CreateTime 2017年8月21日 下午5:06:50
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws SaibongException
	 * @throws BusinessException
	 */
	@Override
	public void doApproveAgree(MaintenanceScheme scheme)throws SaibongException, BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间	
		// 验证机型权限
		validatePlaneModel(scheme);
				
		// 验证状态为3已审核
		MaintenanceScheme dbData = maintenanceSchemeMapper.selectByPrimaryKey(scheme.getId());
		if(!MaintenanceSchemeStatusEnum.AUDITED.getId().equals(dbData.getZt())){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
					
		User user = ThreadVarUtil.getUser();
		scheme.setCzls(UUID.randomUUID().toString());
		scheme.setLogOperationEnum(LogOperationEnum.YIPIZHUN_WO);
		scheme.setZt(MaintenanceSchemeStatusEnum.APPROVED.getId());
		
		scheme.setPfrid(user.getId());
		scheme.setPfsj(new Date());
		scheme.setPfbmid(user.getBmdm());
		scheme.setPfjl(MaintenanceSchemeStatusEnum.APPROVED.getId());
		
		maintenanceSchemeMapper.updateByPrimaryKeySelective(scheme);	
		// 写入日志
		commonRecService.write(scheme.getId(), TableEnum.B_G2_011, user.getId(), scheme.getCzls(),
				scheme.getLogOperationEnum(), UpdateTypeEnum.UPDATE, scheme.getId());
		
		// 更新下达指令业务单号状态
		instructionsourceMapper.updateYwdjztByZlid(scheme.getZt(), scheme.getId());
		// 更新待办事宜状态
		todorsService.updateYwdjztByYwid(scheme.getZt(),scheme.getId());
		
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(scheme.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(3);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		
		//拼接说明
		StringBuffer strSm = new StringBuffer();
		strSm.append(dbData.getJx());
		strSm.append("机型的");
		strSm.append(dbData.getWxfabh());
		strSm.append(" R");
		strSm.append(dbData.getBb());
		strSm.append("维修方案已经批准，请提交给生产确认。");
		todoService.insertSelectiveTechnical(dbData,strSm.toString(),"project2:maintenanceproject:main:05",NodeEnum.NODE11.getId(),dbData.getZdrid(),TodoEnum.MP.getId());
		
	}

	/**
	 * @Description 维修方案审批驳回
	 * @CreateTime 2017年8月21日 下午5:06:50
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws SaibongException
	 * @throws BusinessException
	 */
	@Override
	public void doApproveReject(MaintenanceScheme scheme)
			throws SaibongException, BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		// 验证机型权限
		validatePlaneModel(scheme);
				
		// 验证状态为3已审核
		MaintenanceScheme dbData = maintenanceSchemeMapper.selectByPrimaryKey(scheme.getId());
		if(!MaintenanceSchemeStatusEnum.AUDITED.getId().equals(dbData.getZt())){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
					
		User user = ThreadVarUtil.getUser();
		scheme.setCzls(UUID.randomUUID().toString());
		scheme.setLogOperationEnum(LogOperationEnum.SHENPIBOHUI_WO);
		scheme.setZt(MaintenanceSchemeStatusEnum.APPROVE_REJEC.getId());
		
		scheme.setPfrid(user.getId());
		scheme.setPfsj(new Date());
		scheme.setPfbmid(user.getBmdm());
		scheme.setPfjl(MaintenanceSchemeStatusEnum.APPROVE_REJEC.getId());
		
		maintenanceSchemeMapper.updateByPrimaryKeySelective(scheme);	
		// 写入日志
		commonRecService.write(scheme.getId(), TableEnum.B_G2_011, user.getId(), scheme.getCzls(),
				scheme.getLogOperationEnum(), UpdateTypeEnum.UPDATE, scheme.getId());
		
		// 更新下达指令业务单号状态
		instructionsourceMapper.updateYwdjztByZlid(scheme.getZt(), scheme.getId());
		// 更新待办事宜状态
		todorsService.updateYwdjztByYwid(scheme.getZt(),scheme.getId());
		
		
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(scheme.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(3);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		//拼接说明
		StringBuffer strSm = new StringBuffer();
		strSm.append(dbData.getJx());
		strSm.append("机型的");
		strSm.append(dbData.getWxfabh());
		strSm.append(" R");
		strSm.append(dbData.getBb());
		strSm.append("维修方案已经驳回，请重新提交。");
		todoService.insertSelectiveTechnical(dbData,strSm.toString(),"project2:maintenanceproject:main:02",NodeEnum.NODE5.getId(),dbData.getZdrid(),TodoEnum.MP.getId());
	}
	
	/**
	 * @Description 生产确认维修方案
	 * @CreateTime 2017年8月21日 下午5:07:03
	 * @CreateBy 韩武
	 * @UpdateBy 徐勇
	 * @UpdateTime 2017年10月9日 上午11:07:09
	 * @param scheme 维修方案对象
	 * @throws BusinessException
	 */
	@Override
	public void doConfirmProduction(MaintenanceScheme scheme)throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间	
		/*
		 * 验证
		 */
		//验证状态为7待生效
		MaintenanceScheme dbData = maintenanceSchemeMapper.selectByPrimaryKey(scheme.getId());
		if(!MaintenanceSchemeStatusEnum.TO_BE_EFFECT.getId().equals(dbData.getZt())){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
		// 验证机型权限
		validatePlaneModel(scheme);
		
		/*
		 * 更新b_g2_012中工卡编号
		 */
		maintenanceSchemeMapper.updateGkbh(dbData);
		
		/*
		 * 更新维修方案表状态=10生效，最新标识=2新版本
		 */
		User user = ThreadVarUtil.getUser();
		scheme.setCzls(UUID.randomUUID().toString());
		scheme.setSxsj(new Date());
		scheme.setSjSxrq(new Date());
		scheme.setZxbs(RevMarkEnum.LATEST_VERSION.getId());
		scheme.setLogOperationEnum(LogOperationEnum.COME_INTO_EFFECT);
		scheme.setZt(MaintenanceSchemeStatusEnum.EFFECTIVE.getId());
		maintenanceSchemeMapper.updateByPrimaryKeySelective(scheme);	
		// 写入日志
		commonRecService.write(scheme.getId(), TableEnum.B_G2_011, user.getId(), scheme.getCzls(),
				scheme.getLogOperationEnum(), UpdateTypeEnum.UPDATE, scheme.getId());
		
		
		/*
		 * 更新前版本的维修方案最新标识=3老版本
		 */
		MaintenanceScheme previousVersion = new MaintenanceScheme();
		previousVersion.setId(dbData.getfBbid());
		previousVersion.setZxbs(RevMarkEnum.OLD_VERSION.getId());
		previousVersion.setZdrid(user.getId());
		previousVersion.setZdsj(new Date());
		previousVersion.setZdbmid(user.getBmdm());
		maintenanceSchemeMapper.updateByPrimaryKeySelective(previousVersion);	
		
		
		/*
		 * 更新维修项目的状态=3生效
		 */
		dbData.setZdrid(user.getId());
		dbData.setZdsj(new Date());
		dbData.setZdbmid(user.getBmdm());
		maintenanceProjectMapper.effect(dbData);
		
		
		/*
		 * 写入到生效区
		 */
		// 写入维修方案生效区-维修项目关系表
		projectEffectiveMapper.synchronize(dbData);
		// 写入维修方案生效区-相关维修项目关系表
		projectRelationshipEffectiveMapper.synchronize(dbData);
		
		/*
		 * 处理其它相关业务
		 */
		// 更新装机清单控制标识（时控/时寿）
		this.updateInstallationListControlFlag(dbData);
		// 更新维修项目监控数据
		this.updateMonitorData(dbData);
		
		// 更新下达指令业务单号状态
		instructionsourceMapper.updateYwdjztByZlid(scheme.getZt(), scheme.getId());
		// 更新待办事宜状态
		todorsService.updateYwdjztByYwid(scheme.getZt(), scheme.getId());
		
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(scheme.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(12);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		
	}
	
	/**
	 * @Description 更新装机清单控制标识（时控/时寿）
	 * @CreateTime 2017年10月9日 上午11:05:28
	 * @CreateBy 徐勇
	 * @param scheme 维修方案对象
	 */
	private void updateInstallationListControlFlag(MaintenanceScheme scheme){
		//更新装机清单-临时区时控/时寿标识
		this.maintenanceSchemeMapper.updateInstallationListControlFlag(MaintenanceSchemeMapper.U_INS_LIST_TEMP, scheme.getId(), scheme.getDprtcode(), scheme.getJx());
		//更新装机清单-编辑区时控/时寿标识
		this.maintenanceSchemeMapper.updateInstallationListControlFlag(MaintenanceSchemeMapper.U_INS_LIST_EDIT, scheme.getId(), scheme.getDprtcode(), scheme.getJx());
		//更新装机清单-生效区时控/时寿标识
		this.maintenanceSchemeMapper.updateInstallationListControlFlag(MaintenanceSchemeMapper.U_INS_LIST_EFF, scheme.getId(), scheme.getDprtcode(), scheme.getJx());
	}
	
	/**
	 * 
	 * @Description 更新维修项目监控数据
	 * @CreateTime 2017年10月9日 上午11:08:30
	 * @CreateBy 徐勇
	 * @param scheme 维修方案对象
	 */
	private void updateMonitorData(MaintenanceScheme scheme){
		//删除该维修方案的当前监控数据
		this.monitorDataService.removeByMaintenanceScheme(scheme.getWxfabh(), scheme.getDprtcode());
		
		/* 生成最新的监控数据 */
		
		//查询时控时寿项目需要监控的数据
		List<MonitoringObject> cotrolMPMDList = this.monitorDataService.queryControlMPNeedMonitorDataList(scheme.getId());
		//查询一般项目/定检包需要监控的数据
		List<MonitoringObject> commonMPMDList = this.monitorDataService.queryCommonMPNeedMonitorDataList(scheme.getId());

		if(cotrolMPMDList.isEmpty() && commonMPMDList.isEmpty()){
			return;
		}
		
		//查询当前维修方案所有有效维修项目及监控项；将List转换为HashMap<维修项目编号，维修项目对象>
		List<MaintenanceProject> mpList = this.maintenanceProjectMapper.selectEffectMPListWithMonitorBySId(scheme.getId());
		Map<String, MaintenanceProject> mpMap = new HashMap<String, MaintenanceProject>(mpList.size());//当前维修方案的维修项目HashMap<维修项目编号，维修项目对象>
		for (MaintenanceProject maintenanceProject : mpList) {
			mpMap.put(maintenanceProject.getRwh(), maintenanceProject);
		}
		mpList = null;
		
		//当维修方案有前版本时 查询前版本维修方案未执行数据(带计划、上次执行)
		Map<String, MonitoringObject> notExeMap = null;//前版本维修方案未执行数据(带计划、上次执行)
		if(StringUtils.isNotBlank(scheme.getfBbid())){
			notExeMap = this.monitorDataService.queryNotExeMonitorDataMapByWxfaid(scheme.getfBbid());
		}
		//查询 机型下飞机/在机序列件的 日历初始值，map中key值为 飞机注册号/装机清单ID
		Map<String, String> calInitMap = this.monitorDataService.queryAllCalInitByFjjx(scheme.getDprtcode(), scheme.getJx());
		
		List<MonitoringObject> cMOList = new ArrayList<MonitoringObject>();//待新增的监控数据
		List<MonitoringObject> uMOList = new ArrayList<MonitoringObject>();//待修改的监控数据
		List<MonitoringPlan> cMPlanList = new ArrayList<MonitoringPlan>();//待新增的计划数据（待新增及待修改的监控数据的计划数据）
		
		//组装当前监控数据 及 计划、上次执行数据
		for (MonitoringObject monitoringObject : cotrolMPMDList) {
			monitoringObject.setLylx(MaintenanceTypeEnum.PROJECT.getId());
			//获取原监控数据
			MonitoringObject notExeMO = this.monitorDataService.getMatchedMonitorObject(notExeMap, monitoringObject, false);//原监控数据
			this.buildMonitorData(cMOList, uMOList, cMPlanList, monitoringObject, notExeMO, mpMap, calInitMap, scheme, ThreadVarUtil.getUser());
		}
		for (MonitoringObject monitoringObject : commonMPMDList) {
			monitoringObject.setLylx(MaintenanceTypeEnum.PROJECT.getId());
			//获取原监控数据
			MonitoringObject notExeMO = this.monitorDataService.getMatchedMonitorObject(notExeMap, monitoringObject, false);//原监控数据
			this.buildMonitorData(cMOList, uMOList, cMPlanList, monitoringObject, notExeMO, mpMap, calInitMap, scheme, ThreadVarUtil.getUser());
		}
		
		//删除待修改监控数据的计划数据
		this.monitorDataService.removeMonitorPlanByMainIdBatch(uMOList);
		//批量修改监控数据（处理待修改监控数据）
		this.monitorDataService.updateMonitorObjectBatch(uMOList);
		//批量新增监控数据（处理待新增监控数据）
		this.monitorDataService.saveMonitorObjectBatch(cMOList);
		//批量新增计划数据（处理待新增计划数据）
		this.monitorDataService.saveMonitorPlanBatch(cMPlanList);
		//根据待修改、待新增监控数据 同步到 当前监控数据中
		uMOList.addAll(cMOList);
		this.monitorDataService.saveCurrentMonitor4BatchSync(uMOList);
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
	private void buildMonitorData(List<MonitoringObject> cMOList, List<MonitoringObject> uMOList, List<MonitoringPlan> cMPlanList, 
			MonitoringObject monitoringObject, MonitoringObject notExeMO, Map<String, MaintenanceProject> mpMap,
			Map<String, String> calInitMap, MaintenanceScheme scheme, User user){
		
		MaintenanceProject mp = mpMap.get(monitoringObject.getLybh());
		//取不到维修项目，忽略此记录
		if(mp == null){
			return;
		}
		if(notExeMO == null){//不存在原监控数据，按维修项目生成监控数据
			String jksjid = UUID.randomUUID().toString();//监控数据ID
			//生成监控对象
			monitoringObject.setId(jksjid);
			monitoringObject.setWhbmid(user.getBmdm());
			monitoringObject.setWhrid(user.getId());
			monitoringObject.setLylx(MaintenanceTypeEnum.PROJECT.getId());
			monitoringObject.setWxfaid(scheme.getId());
			monitoringObject.setWxfabh(scheme.getWxfabh());
			cMOList.add(monitoringObject);
			
			//生成计划数据
			List<ProjectMonitor> projectMonitorList = this.getProjectMonitor(mp, monitoringObject.getBjh());
			MonitoringPlan monitoringPlan = null;
			for (ProjectMonitor projectMonitor : projectMonitorList) {
				monitoringPlan = new MonitoringPlan(projectMonitor);
				monitoringPlan.setId(UUID.randomUUID().toString());
				monitoringPlan.setJksjid(jksjid);
				monitoringPlan.setWhdwid(user.getBmdm());
				monitoringPlan.setWhrid(user.getId());
				monitoringPlan.setWz(monitoringObject.getWz());
				if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
					//日历计划值为初始+首次; 装机清单ID不为空时按装机清单取日历初始值，否则按飞机取
					String strCalInit = null;
					if(StringUtils.isNotBlank(monitoringObject.getZjqdid())){
						strCalInit = calInitMap.get(monitoringObject.getZjqdid());
					}else{
						strCalInit = calInitMap.get(monitoringObject.getFjzch());
					}
					//异常处理，当日历初始值或首次值为空 则 计划值为空
					if(StringUtils.isNotBlank(strCalInit) && StringUtils.isNotBlank(projectMonitor.getScz())){
						try {
							monitoringPlan.setJhz(DateUtil.getOffsetDate(strCalInit, Integer.parseInt(monitoringPlan.getScz()), this.monitorDataService.getOffsetUnit(monitoringPlan.getDwScz())));
						} catch (ParseException e) {}
					}
				}else{//非日历
					monitoringPlan.setJhz(projectMonitor.getScz());
				}
				cMPlanList.add(monitoringPlan);
			}
			
		}else{//存在原监控数据，更新原监控数据，并按维修项目生成新的计划
			
			//修改原监控对象
			notExeMO.setWhbmid(user.getBmdm());
			notExeMO.setWhrid(user.getId());
			notExeMO.setLyid(monitoringObject.getLyid());
			notExeMO.setLybh(monitoringObject.getLybh());
			notExeMO.setWxfaid(scheme.getId());
			notExeMO.setWxfabh(scheme.getWxfabh());
			notExeMO.setWz(monitoringObject.getWz());
			notExeMO.setJsgs(monitoringObject.getJsgs());
			notExeMO.setHdwz(monitoringObject.getHdwz());
			uMOList.add(notExeMO);
			//生成计划数据
			List<MonitoringPlan> notExeMOPlanList = notExeMO.getMonitoringPlanList();//原监控数据计划数据
			List<MonitoringLast> notExeMOLastList = notExeMO.getMonitoringLastList();//原监控数据上次执行数据
			List<ProjectMonitor> projectMonitorList = this.getProjectMonitor(mp, monitoringObject.getBjh());//维修项目监控数据
			MonitoringPlan monitoringPlan = null;
			for (ProjectMonitor projectMonitor : projectMonitorList) {
				monitoringPlan = new MonitoringPlan(projectMonitor);
				monitoringPlan.setId(UUID.randomUUID().toString());
				monitoringPlan.setJksjid(notExeMO.getId());
				monitoringPlan.setWhdwid(user.getBmdm());
				monitoringPlan.setWhrid(user.getId());
				monitoringPlan.setWz(monitoringObject.getWz());
				
				//判断原监控计划是否有该监控项，有该监控项使用该监控项的T0值进行计算
				for (MonitoringPlan notExeMOPlan : notExeMOPlanList) {
					if(notExeMOPlan.getJklbh().equals(monitoringPlan.getJklbh())){
						monitoringPlan.setJhqsz(notExeMOPlan.getJhqsz());
						break;
					}
				}
				
				//计算间隔 = 首次or周期
				String interval = null;//间隔
				int intervalUnit = 0;//间隔单位
				
				//T0无值，判断是否有上次执行数据，有上次执行数据以周期计算，否则以首次计算
				if((notExeMOLastList != null && notExeMOLastList.size() > 0) || StringUtils.isNotBlank(notExeMO.getfJksjid())){
					//以周期进行计算
					interval = monitoringPlan.getZqz();
					intervalUnit = monitoringPlan.getDwZqz();
				}else{
					//以首次进行计算
					interval = monitoringPlan.getScz();
					intervalUnit = monitoringPlan.getDwScz();
				}
				
				//间隔不为空时计算计划值，间隔为空则计划值为空
				if(StringUtils.isNotBlank(interval)){
					if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
						//日历：为 T0为空时 使用初始+间隔，否则使用T0+间隔
						String t0 = monitoringPlan.getJhqsz();
						if(StringUtils.isBlank(t0)){
							//t0为空 后面以初始值进行计算
							if(StringUtils.isNotBlank(monitoringObject.getZjqdid())){
								t0 = calInitMap.get(monitoringObject.getZjqdid());
							}else{
								t0 = calInitMap.get(monitoringObject.getFjzch());
							}
						}
						//异常处理，当日历初始值或首次值为空 则 计划值为空
						if(StringUtils.isNotBlank(t0)){
							try {
								monitoringPlan.setJhz(DateUtil.getOffsetDate(t0, Integer.parseInt(interval), this.monitorDataService.getOffsetUnit(intervalUnit)));
							} catch (ParseException e) {}
						}
						
					}else{
						//非日历 T0+间隔
						monitoringPlan.setJhz(String.valueOf(Integer.parseInt(interval) + Integer.parseInt(StringUtils.isNotBlank(monitoringPlan.getJhqsz())?monitoringPlan.getJhqsz():"0")));
					}
				}else{
					monitoringPlan.setJhqsz(null);
				}
				cMPlanList.add(monitoringPlan);
			}
		}
	}
	
	/**
	 * @Description 根据部件号取得维修项目中对应的维修项目
	 * @CreateTime 2017年10月10日 下午2:22:36
	 * @CreateBy 徐勇
	 * @param mp 维修项目（带监控数据）
	 * @param bjh 部件号
	 * @return
	 */
	private List<ProjectMonitor> getProjectMonitor(MaintenanceProject mp, String bjh){
		if(StringUtils.isBlank(bjh)){//部件号为空表示该维修项目为非控制项目
			return mp.getProjectMonitors();
		}else{
			List<ProjectMonitor> resultList = new ArrayList<ProjectMonitor>();//待返回的监控项目
			List<ProjectMonitor> projectMonitorList = mp.getProjectMonitors();//维修项目中的监控项目
			//根据部件与取得对应的监控项目
			for (ProjectMonitor projectMonitor : projectMonitorList) {
				if(bjh.equals(projectMonitor.getBjh())){
					resultList.add(projectMonitor);
				}
			}
			return resultList;
		}
	}
		
	/**
	 * @Description 查询待审核维修方案
	 * @CreateTime 2017年8月22日 上午10:58:17
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	@Override
	public List<MaintenanceScheme> queryAuditList(MaintenanceScheme scheme) {
		setUserId(scheme);
		return maintenanceSchemeMapper.queryAuditList(scheme);
	}
	
	/**
	 * @Description 验证机型权限
	 * @CreateTime 2017年8月25日 下午1:57:39
	 * @CreateBy 韩武
	 * @param maintenanceProject
	 * @throws BusinessException 
	 */
	private void validatePlaneModel(MaintenanceScheme scheme) throws BusinessException{
		User user = ThreadVarUtil.getUser();
		List<String> list = new ArrayList<String>();
		list.add(scheme.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), scheme.getDprtcode(), list);
	}
	
	/**
	 * @Description 验证必须有维修项目版本相同时才可提交
	 * @CreateTime 2017年8月28日 下午1:39:50
	 * @CreateBy 韩武
	 * @param scheme
	 * @throws BusinessException
	 */
	private void validateExistProject(MaintenanceScheme scheme) throws BusinessException{
		if(maintenanceSchemeMapper.existProjectCount(scheme) == 0){
			throw new BusinessException("请至少添加或改版一个维修项目！");
		}
	}

	/**
	 * @Description 查询待批准维修方案
	 * @CreateTime 2017年8月22日 上午10:58:29
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	@Override
	public List<MaintenanceScheme> queryApproveList(MaintenanceScheme scheme) {
		setUserId(scheme);
		return maintenanceSchemeMapper.queryApproveList(scheme);
	}

	/**
	 * @Description 查询待生效维修方案
	 * @CreateTime 2017年8月22日 上午10:58:37
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	@Override
	public List<MaintenanceScheme> queryEffectList(MaintenanceScheme scheme) {
		setUserId(scheme);
		return maintenanceSchemeMapper.queryEffectList(scheme);
	}

	/**
	 * 
	 * @Description 根据id查询维修方案
	 * @CreateTime 2017年8月25日 上午10:29:03
	 * @CreateBy 林龙
	 * @param zlid 维修方案id
	 * @return 维修方案
	 * @throws BusinessException
	 */
	@Override
	public MaintenanceScheme selectByPrimaryKey(String zlid)
			throws BusinessException {
		return maintenanceSchemeMapper.selectByPrimaryKey(zlid);
	}
	
	/**
	 * @Description 设置userid（用于sql机型权限判断）
	 * @CreateTime 2017年8月26日 上午9:43:00
	 * @CreateBy 韩武
	 * @param scheme
	 */
	private void setUserId(MaintenanceScheme scheme){
		User user = new User();
		if(StringUtils.isBlank(user.getUserType())){
			scheme.getParamsMap().put("userId", ThreadVarUtil.getUser().getId());
		}
	}

	/**
	 * @Description 查询差异数据
	 * @CreateTime 2017年8月28日 下午3:31:49
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	@Override
	public Map<String, MaintenanceScheme> queryDifferenceData(MaintenanceScheme scheme) {
		Map<String, MaintenanceScheme> map = new HashMap<String, MaintenanceScheme>();
		
		// 当前维修方案
		MaintenanceScheme current = maintenanceSchemeMapper.selectByPrimaryKey(scheme.getId());
		if(MaintenanceSchemeStatusEnum.EFFECTIVE.getId().equals(current.getZt())){
			// 查询生效版本的维修方案对应数据
			current = maintenanceSchemeMapper.queryEffective(current);
		}else{
			// 查未询生效版本的维修方案对应数据
			current = maintenanceSchemeMapper.queryIneffective(current);
		}
		// 根据ATA分组
		groupByATA(current);
		
		// 前一个维修方案
		String fBbid = current.getfBbid() == null ? current.getId() : current.getfBbid();
		MaintenanceScheme previous = maintenanceSchemeMapper.selectByPrimaryKey(fBbid);
		previous = maintenanceSchemeMapper.queryEffective(previous);
		
		map.put(MP_TYPE_CURRENT, current);
		map.put(MP_TYPE_PREVIOUS, previous);
		return map;
	}
	
	/**
	 * @Description 根据ATA分组
	 * @CreateTime 2017年8月28日 下午4:57:12
	 * @CreateBy 韩武
	 * @param scheme
	 */
	private void groupByATA(MaintenanceScheme scheme){
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, List<MaintenanceProject>> tempMap = new LinkedHashMap<String, List<MaintenanceProject>>();
		for (MaintenanceProject project : scheme.getProjectList()) {
			// 维修项目版本 = 维修方案版本 或 （维修项目版本 < 维修方案版本 且 有效标识 = 有效）
			if(project.getBb().compareTo(scheme.getBb()) == 0 ||
					project.getBb().compareTo(scheme.getBb()) < 0 
						&& EffectiveEnum.YES.getId().equals(project.getYxbs())){
				
				String key = project.getZjh();
				if(tempMap.containsKey(key)){
					tempMap.get(key).add(project);
				}else{
					List<MaintenanceProject> list = new ArrayList<MaintenanceProject>();
					list.add(project);
					tempMap.put(key, list);
				}
			}
		}
		for (String zjh : tempMap.keySet()) {
			Map<String, Object> map = new HashMap<String, Object>();
			FixChapter fixChapter = tempMap.get(zjh).get(0).getFixChapter();
			if(fixChapter != null){
				map.put("id", UUID.randomUUID().toString());
				map.put("bh", fixChapter.getZjh());
				map.put("zwmc", fixChapter.getZwms());
				map.put("ywmc", fixChapter.getYwms());
				map.put("children", tempMap.get(zjh));
				resultList.add(map);
			}
		}
		scheme.setATAList(resultList);
	}

	/**
	 * @Description 查询维修方案版本历史版本
	 * @CreateTime 2017年8月30日 上午9:18:56
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	@Override
	public List<MaintenanceScheme> queryVersionList(MaintenanceScheme scheme) {
		return maintenanceSchemeMapper.queryVersionList(scheme);
	}

	/**
	 * @Description 查询维修方案详情
	 * @CreateTime 2017年9月12日 下午5:07:35
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	@Override
	public MaintenanceScheme queryDetail(String id) {
		return maintenanceSchemeMapper.queryDetail(id);
	}
	
}
