package com.eray.thjw.project2.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.InstallationListEditableMapper;
import com.eray.thjw.produce.dao.InstallationListEffectiveMapper;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.po.InstallationListEffective;
import com.eray.thjw.produce.service.AircraftinfoService;
import com.eray.thjw.project2.dao.MaintenanceProjectMapper;
import com.eray.thjw.project2.dao.MaintenanceSchemeMapper;
import com.eray.thjw.project2.dao.ProjectModelMapper;
import com.eray.thjw.project2.po.MaintenanceClass;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.po.MaintenanceScheme;
import com.eray.thjw.project2.po.ProjectMaterial;
import com.eray.thjw.project2.po.ProjectModel;
import com.eray.thjw.project2.po.ProjectMonitor;
import com.eray.thjw.project2.service.CoverPlateService;
import com.eray.thjw.project2.service.MaintenanceProjectService;
import com.eray.thjw.project2.service.ProjectMaterialService;
import com.eray.thjw.project2.service.ProjectModelService;
import com.eray.thjw.project2.service.ProjectMonitorService;
import com.eray.thjw.project2.service.ProjectRelationshipService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.project2.MaintenanceProjectEffectiveEnum;
import enu.project2.MaintenanceProjectTypeEnum;
import enu.project2.MaintenanceSchemeStatusEnum;
import enu.project2.MonitorProjectEnum;

/**
 * @Description 维修项目service
 * @CreateTime 2017年8月16日 上午10:56:34
 * @CreateBy 韩武
 */
@Service
public class MaintenanceProjectServiceImpl implements MaintenanceProjectService{
	
	@Resource
	private ProjectRelationshipService projectRelationshipService;
	
	@Resource
	private MaintenanceProjectMapper maintenanceProjectMapper;
	
	@Resource
	private MaintenanceSchemeMapper maintenanceSchemeMapper;
	
	@Resource
	private ProjectModelMapper projectModelMapper;
	
	@Resource
	private CoverPlateService coverPlateService;
	
	@Resource
	private ProjectModelService projectModelService;
	
	@Resource
	private ProjectMaterialService projectMaterialService;
	
	@Resource
	private ProjectMonitorService projectMonitorService;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	@Resource
	private AircraftinfoService aircraftinfoService;
	
	@Resource
	private InstallationListEditableMapper installationListEditableMapper;
	
	@Resource
	private InstallationListEffectiveMapper installationListEffectiveMapper;
	
	/** 当前维修项目 */
	private final static String PROJECT_TYPE_CURRENT = "current";
	
	/** 前一个维修项目 */
	private final static String PROJECT_TYPE_PREVIOUS = "previous";

	
	/**
	 * @Description 保存维修项目（所有）
	 * @CreateTime 2017年8月16日 上午9:33:40
	 * @CreateBy 韩武
	 * @param maintenanceProject
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public MaintenanceProject doSave(MaintenanceProject maintenanceProject) throws BusinessException{
		
		// 验证维修项目
		validate(maintenanceProject);
		
		// 曾今添加过该维修项目
		if(hasHistoryData(maintenanceProject)){
			// 进入改版操作
			return doRevision(maintenanceProject);
		} else {
			// 进入新增/修改操作
			return save(maintenanceProject);
		}
		
	}
	
	/**
	 * @Description 保存维修项目（所有）
	 * @CreateTime 2017年8月16日 上午9:33:40
	 * @CreateBy 韩武
	 * @param maintenanceProject
	 * @return
	 * @throws BusinessException
	 */
	private MaintenanceProject save(MaintenanceProject maintenanceProject) throws BusinessException{
		
		// 保存维修项目
		saveMaintenanceProject(maintenanceProject);
		
		// 保存附件
		saveAttachments(maintenanceProject);
		
		// 保存关联维修项目
		projectRelationshipService.save(maintenanceProject);
		
		// 保存接近/盖板
		coverPlateService.save(maintenanceProject);
		
		// 保存维修项目对应飞机关系
		projectModelService.save(maintenanceProject);
		
		// 保存维修项目-监控项设置
		projectMonitorService.save(maintenanceProject);
		
		// 保存维修项目-关联部件关系
		projectMaterialService.save(maintenanceProject);
		
		// 查询获取维修项目的所有信息
		return queryEditData(maintenanceProject, maintenanceProject.getId());
	}
	
	/**
	 * @Description 是否曾今添加过该维修项目
	 * @CreateTime 2017年12月20日 下午2:27:24
	 * @CreateBy 韩武
	 * @param maintenanceProject
	 * @return
	 */
	private boolean hasHistoryData(MaintenanceProject maintenanceProject){
		
		if(StringUtils.isBlank(maintenanceProject.getId())){
			MaintenanceProject latest = maintenanceProjectMapper.getLatestWxxm(maintenanceProject);
			if(latest != null){
				maintenanceProject.setId(latest.getId());
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @Description 查询修改的行数据
	 * @CreateTime 2017年11月16日 下午3:31:20
	 * @CreateBy 韩武
	 * @param data
	 * @param queryId
	 * @return
	 */
	private MaintenanceProject queryEditData(MaintenanceProject data, String queryId){
		data.getParamsMap().put("queryId", queryId);
		List<MaintenanceProject> list = queryMaintenanceProject(data);
		return list.isEmpty() ? null : list.get(0);
	}
	
	/**
	 * @Description 验证机型权限
	 * @CreateTime 2017年8月25日 下午1:57:39
	 * @CreateBy 韩武
	 * @param maintenanceProject
	 * @throws BusinessException 
	 */
	private void validatePlaneModel(MaintenanceProject maintenanceProject) throws BusinessException{
		User user = ThreadVarUtil.getUser();
		List<String> list = new ArrayList<String>();
		list.add(maintenanceProject.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), maintenanceProject.getDprtcode(), list);
	}
	
	/**
	 * @Description 验证关联维修项目没有被其他维修项目关联
	 * @CreateTime 2017年9月6日 下午1:34:50
	 * @CreateBy 韩武
	 * @param maintenanceProject
	 * @throws BusinessException
	 */
	private void validateNoOtherRelation(MaintenanceProject maintenanceProject) throws BusinessException{
		List<MaintenanceProject> existList = maintenanceProjectMapper.queryOtherRelation(maintenanceProject);
		if(existList != null && !existList.isEmpty()){
			StringBuilder str = new StringBuilder();
			for (MaintenanceProject pro : existList) {
				str.append(pro.getRwh()).append("、");
			}
			str.deleteCharAt(str.lastIndexOf("、"));
			throw new BusinessException("关联维修项目" + str.toString() + "已被其他的维修项目关联，无法撤销！");
		}
	}
	
	/**
	 * @Description 保存维修项目（单表）
	 * @CreateTime 2017年8月16日 上午9:41:14
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 * @throws BusinessException
	 */
	private String saveMaintenanceProject(MaintenanceProject project) throws BusinessException{
		
		User user = ThreadVarUtil.getUser();
		project.setZdrid(user.getId());
		project.setZdsj(new Date());
		project.setZdbmid(user.getBmdm());
		if(StringUtils.isBlank(project.getCzls())){
			project.setCzls(UUID.randomUUID().toString());//操作流水
		}
		
		if (StringUtils.isBlank(project.getId())){	// 新增维修项目信息
			project.setId(UUID.randomUUID().toString());
			if(project.getLogOperationEnum() == null){
				project.setLogOperationEnum(LogOperationEnum.SAVE_WO);
			}
			project.setZt(MaintenanceProjectEffectiveEnum.INEFFECTIVE.getId());
			maintenanceProjectMapper.insertSelective(project);
			// 保存历史记录信息
			commonRecService.write(project.getId(), TableEnum.B_G2_012, user.getId(), 
					project.getCzls() ,project.getLogOperationEnum(), UpdateTypeEnum.SAVE, project.getScheme().getId());
			
		} else {	// 修改维修项目信息
			if(project.getLogOperationEnum() == null){
				project.setLogOperationEnum(LogOperationEnum.EDIT);
			}
			maintenanceProjectMapper.updateByMaintenanceProject(project);
			// 保存历史记录信息
			commonRecService.write(project.getId(), TableEnum.B_G2_012, user.getId(), 
					project.getCzls() ,project.getLogOperationEnum(), UpdateTypeEnum.UPDATE, project.getScheme().getId());
		}
		
		// 写入日志
		commonRecService.write(project.getScheme().getId(), TableEnum.B_G2_011, user.getId(), project.getCzls(),
				LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, project.getScheme().getId());
		
		return project.getId();
	}
	
	/**
	 * @Description 保存附件
	 * @CreateTime 2017年8月16日 上午10:08:33
	 * @CreateBy 韩武
	 * @param project
	 */
	private void saveAttachments(MaintenanceProject project){
		if(StringUtils.isBlank(project.getfBbid())){	// 新增/修改
			attachmentService.eidtAttachment(project.getAttachments(), project.getId(), 
					project.getCzls(), project.getScheme().getId(), project.getDprtcode(), project.getLogOperationEnum());
		}else{	// 改版
			attachmentService.saveAttachment4Modify(project.getAttachments(), project.getfBbid(), project.getId(),
					project.getCzls(), project.getScheme().getId(), project.getDprtcode(), project.getLogOperationEnum());
		}
	}
	
	/**
	 * @Description 删除附件
	 * @CreateTime 2017年9月5日 下午4:16:20
	 * @CreateBy 韩武
	 * @param project
	 */
	private void deleteAttachments(MaintenanceProject project){
		Attachment attachment = new Attachment();
		attachment.setMainid(project.getId());
		attachmentService.delFiles(project.getId(), project.getCzls(), project.getScheme().getId(), project.getLogOperationEnum());
	}
	
	
	/**
	 * @Description 根据维修方案查询对应的维修项目
	 * @CreateTime 2017年8月16日 下午2:32:51
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 */
	@Override
	public List<Map<String, Object>> groupByATA(MaintenanceProject maintenanceProject) {
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, List<MaintenanceProject>> tempMap = new LinkedHashMap<String, List<MaintenanceProject>>();
		for (MaintenanceProject project : queryMaintenanceProject(maintenanceProject)) {
			// 维修项目版本 = 维修方案版本 或 （维修项目版本 < 维修方案版本 且 有效标识 = 有效）
			if(project.getBb().compareTo(maintenanceProject.getBb()) == 0 ||
					project.getBb().compareTo(maintenanceProject.getBb()) < 0 
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
		return resultList;
	}

	/**
	 * @Description 根据维修方案查询对应的维修项目（按照目录分组）
	 * @CreateTime 2017年8月16日 下午2:33:01
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 */
	@Override
	public List<Map<String, Object>> groupByCatalog(MaintenanceProject maintenanceProject) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, List<MaintenanceProject>> tempMap = new LinkedHashMap<String, List<MaintenanceProject>>();
		for (MaintenanceProject project : queryMaintenanceProject(maintenanceProject)) {
			// 维修项目版本 = 维修方案版本 或 （维修项目版本 < 维修方案版本 且 有效标识 = 有效）
			if(project.getBb().compareTo(maintenanceProject.getBb()) == 0 ||
					project.getBb().compareTo(maintenanceProject.getBb()) < 0 
						&& EffectiveEnum.YES.getId().equals(project.getYxbs())){
				
				String key = project.getMaintenanceClass().getDlbh();
				if(tempMap.containsKey(key)){
					tempMap.get(key).add(project);
				}else{
					List<MaintenanceProject> list = new ArrayList<MaintenanceProject>();
					list.add(project);
					tempMap.put(key, list);
				}
			}
		}
		for (String dlbh : tempMap.keySet()) {
			Map<String, Object> map = new HashMap<String, Object>();
			MaintenanceClass maintenanceClass = tempMap.get(dlbh).get(0).getMaintenanceClass();
			if(maintenanceClass != null){
				map.put("id", UUID.randomUUID().toString());
				map.put("bh", maintenanceClass.getDlbh());
				map.put("zwmc", maintenanceClass.getDlzwms());
				map.put("ywmc", maintenanceClass.getDlywms());
				map.put("children", tempMap.get(dlbh));
				resultList.add(map);
			}
		}
		return resultList;
	}
	
	/**
	 * @Description 查询
	 * @CreateTime 2017年8月19日 下午2:44:22
	 * @CreateBy 韩武
	 * @param maintenanceProject
	 * @return
	 */
	private List<MaintenanceProject> queryMaintenanceProject(MaintenanceProject maintenanceProject){
		
		List<MaintenanceProject> list;
		if(MaintenanceSchemeStatusEnum.EFFECTIVE.getId().equals(maintenanceProject.getZt())){
			// 查询生效版本的维修方案的维修项目数据
			list = maintenanceProjectMapper.queryEffective(maintenanceProject);
		}else{	
			// 查询未生效版本的维修方案的维修项目数据
			list = maintenanceProjectMapper.queryIneffective(maintenanceProject);
		}
		// 分钟转换为小时
		for (MaintenanceProject pro : list) {
			switchMinuteToHour(pro);
		}
		return list;
	}
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询维修项目信息(弹窗需要的数据)
	 * @param maintenanceProject
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryWinAllPageList(MaintenanceProject maintenanceProject) {
		PageHelper.startPage(maintenanceProject.getPagination());
		List<MaintenanceProject> list = maintenanceProjectMapper.queryWinAllPageList(maintenanceProject);
		setProjectModelList(list);
		return PageUtil.pack4PageHelper(list, maintenanceProject.getPagination());
	}
	
	/**
	 * @author liub
	 * @description 根据查询条件查询定检包信息
	 * @param maintenanceProject
	 * @return List<MaintenanceProject>
	 */
	@Override
	public List<MaintenanceProject> queryFixedPackageByWxfn(MaintenanceProject maintenanceProject) {
		return maintenanceProjectMapper.queryFixedPackageByWxfn(maintenanceProject);
	}
	
	/**
	 * 验证维修项目
	 * @Description 
	 * @CreateTime 2017年8月16日 上午9:47:23
	 * @CreateBy 韩武
	 * @param maintenanceProject
	 * @throws BusinessException
	 */
	private void validate(MaintenanceProject maintenanceProject) throws BusinessException{
		
		// 验证维修项目唯一（机型+组织机构+任务号+版本）
		if(StringUtils.isBlank(maintenanceProject.getId()) 
				&& maintenanceProjectMapper.checkExistWithVersion(maintenanceProject) > 0){
			throw new BusinessException("维修项目"+maintenanceProject.getRwh()+"已存在!");
		}
		
		// 验证机型权限
		validatePlaneModel(maintenanceProject);
	}

	/**
	 * @Description 查询相关维修项目
	 * @CreateTime 2017年8月16日 下午2:33:10
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 */
	@Override
	public List<MaintenanceProject> queryRelatedMaintenanceProject(
			MaintenanceProject project) {
		if(MaintenanceSchemeStatusEnum.EFFECTIVE.getId().equals(project.getScheme().getZt())){
			// 查询生效的维修项目的相关维修项目
			return maintenanceProjectMapper.queryqueryEffectiveRelated(project);
		}else{	
			// 查询未生效的维修项目的相关维修项目
			return maintenanceProjectMapper.queryqueryIneffectiveRelated(project);
		}
	}


	/**
	 * @Description 查询监控项目版本
	 * @CreateTime 2017年8月16日 下午2:33:16
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 */
	@Override
	public List<MaintenanceProject> queryMonitorItemVersion(
			MaintenanceProject project) {
		List<MaintenanceProject> projectList = maintenanceProjectMapper.queryMonitorItemVersion(project);
		
		for (MaintenanceProject pro : projectList) {
			// 分钟转化为小时
			switchMinuteToHour(pro);
			// 时控/时寿项目
			if(MaintenanceProjectTypeEnum.TIMECONTROL.getId().equals(project.getWxxmlx()) 
					|| MaintenanceProjectTypeEnum.WHENLIFE.getId().equals(project.getWxxmlx())){
				// 将监控项放到对应的部件下
				assembleMaterialMonitor(pro);
			}
		}
		
		return projectList;
	}
	
	/**
	 * @Description 组装部件监控项
	 * @CreateTime 2017年8月26日 下午7:31:03
	 * @CreateBy 韩武
	 * @param pro
	 */
	private void assembleMaterialMonitor(MaintenanceProject pro){
		List<ProjectMaterial> materialList = new ArrayList<ProjectMaterial>();
		for (ProjectMonitor mon : pro.getProjectMonitors()) {
			if(StringUtils.isNotBlank(mon.getBjh())){
				
				boolean flag = false;
				for (ProjectMaterial material : materialList) {
					if(mon.getBjh().equals(material.getBjh())){
						material.getProjectMonitors().add(mon);
						flag = true;
					}
				}
				if(!flag){
					ProjectMaterial material = new ProjectMaterial();
					material.setId(mon.getId());
					material.setBjh(mon.getBjh());
					material.setCj(mon.getCj());
					material.setProjectMonitors(new ArrayList<ProjectMonitor>());
					material.getProjectMonitors().add(mon);
					materialList.add(material);
				}
				
			}
		}
		pro.setProjectMaterialList(materialList);
	}


	/**
	 * @Description 撤销维修项目
	 * @CreateTime 2017年8月16日 下午2:33:23
	 * @CreateBy 韩武
	 * @param project
	 * @throws BusinessException 
	 */
	@Override
	public MaintenanceProject doRevoke(MaintenanceProject project) throws BusinessException {
		
		// 验证机型权限
		validatePlaneModel(project);
		
		// 验证关联维修项目有没有被其他的其他维修项目关联
		validateNoOtherRelation(project);
		
		User user = ThreadVarUtil.getUser();
		project.setCzls(UUID.randomUUID().toString());
		project.setLogOperationEnum(LogOperationEnum.REVOKE);
		// 当前维修项目
		MaintenanceProject current = maintenanceProjectMapper.selectByPrimaryKey(project.getId());
		if(current == null){
			throw new BusinessException("数据已更新,请刷新页面！");
		}
		// 修改前版本的后版本id为null
		if(StringUtils.isNotBlank(current.getfBbid())){
			MaintenanceProject previous = new MaintenanceProject();
			previous.setId(current.getfBbid());
			previous.setbBbid(null);
			previous.setZdrid(user.getId());
			previous.setZdbmid(user.getBmdm());
			previous.setZdsj(new Date());
			maintenanceProjectMapper.updateBbbid(previous);
			// 保存历史记录信息
			commonRecService.write(previous.getId(), TableEnum.B_G2_012, user.getId(), 
					project.getCzls() , project.getLogOperationEnum(), UpdateTypeEnum.UPDATE, previous.getId());
		}
		
		// 删除附件
		deleteAttachments(project);
				
		// 删除关联维修项目
		projectRelationshipService.delete(project);
		
		// 删除接近/盖板
		coverPlateService.save(project);
		
		// 删除维修项目对应飞机关系
		projectModelService.delete(project);
		
		// 删除维修项目-监控项设置
		projectMonitorService.delete(project);
		
		// 删除维修项目-关联部件关系
		projectMaterialService.delete(project);
				
		// 写入维修项目日志
		commonRecService.write(project.getId(), TableEnum.B_G2_012, user.getId(), 
				project.getCzls() , project.getLogOperationEnum(), UpdateTypeEnum.DELETE, project.getScheme().getId());
		// 写入维修方案日志
		commonRecService.write(project.getScheme().getId(), TableEnum.B_G2_011, user.getId(), project.getCzls(),
				LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, project.getScheme().getId());
		// 删除当前维修项目
		maintenanceProjectMapper.deleteByPrimaryKey(project.getId());
		
		// 查询获取维修项目的所有信息
		if(StringUtils.isNotBlank(current.getfBbid())){
			return queryEditData(current, current.getfBbid());
		}
		
		return null;
	}
	
	/**
	 * @Description 根据查询条件分页查询维修项目信息(工卡弹窗需要的数据)
	 * @CreateTime 2017-8-21 下午3:47:29
	 * @CreateBy 刘兵
	 * @param maintenanceProject 维修项目
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryWinAllPageList4WorkCard(MaintenanceProject maintenanceProject) {
		PageHelper.startPage(maintenanceProject.getPagination());
		List<MaintenanceProject> list = maintenanceProjectMapper.queryWinAllPageList4WorkCard(maintenanceProject);
		setProjectModelList(list);
		return PageUtil.pack4PageHelper(list, maintenanceProject.getPagination());
	}

	/**
	 * @Description 查询维修项目详情
	 * @CreateTime 2017年8月24日 下午6:30:06
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 */
	@Override
	public MaintenanceProject queryDetail(MaintenanceProject project) {
		Integer wxfazt = project.getZt();
		MaintenanceProject detail = maintenanceProjectMapper.queryDetail(project);
		if(wxfazt != null){
			detail.getScheme().setZt(wxfazt);
		}
		// 查询维修方案
		if(project.getParamsMap().get("wxfaid") != null){
			String wxfaid = String.valueOf(project.getParamsMap().get("wxfaid"));
			MaintenanceScheme scheme = maintenanceSchemeMapper.selectByPrimaryKey(wxfaid);
			detail.setScheme(scheme);
		}
		// 查询相关维修项目
		detail.setProjectList(queryRelatedMaintenanceProject(detail));
		// 分钟转化为小时
		switchMinuteToHour(detail);
		// 组装部件监控项
		assembleMaterialMonitor(detail);
		return detail;
	}

	/**
	 * @Description 查询维修项目版本历史版本
	 * @CreateTime 2017年8月25日 上午10:39:04
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<MaintenanceProject> queryVersionList(MaintenanceProject project) {
		return maintenanceProjectMapper.queryVersionList(project);
	}

	/**
	 * @Description 改版维修项目
	 * @CreateTime 2017年8月26日 下午4:56:39
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 * @throws BusinessException
	 */
	public MaintenanceProject doRevision(MaintenanceProject project)
			throws BusinessException {
		
		// 验证维修项目
		validate(project);
		
		// 当前用户
		User user = ThreadVarUtil.getUser();
		// 前版本id
		String previousId = project.getId();
		// 前版本维修项目
		MaintenanceProject previous = maintenanceProjectMapper.selectByPrimaryKey(previousId);
		// 改版后id
		String currentId;
		MaintenanceProject current;
		
		project.setCzls(UUID.randomUUID().toString());
		project.setLogOperationEnum(LogOperationEnum.REVISION);
		
		/*
		 * 保存改版后的维修项目
		 */
		project.setId(null);
		project.setfBbid(previousId);
		project.setEobh(previous.getEobh());
		project.setGkbh(previous.getGkbh());
		current = save(project);
		currentId = current.getId();
		
		/*
		 * 修改上一版本的后版本id
		 */
		previous.setbBbid(currentId);
		maintenanceProjectMapper.updateBbbid(previous);
		// 保存历史记录信息
		commonRecService.write(previous.getId(), TableEnum.B_G2_012, user.getId(), 
				project.getCzls() , project.getLogOperationEnum(), UpdateTypeEnum.UPDATE, project.getScheme().getId());
		
		return current;
	}
	
	/**
     * @Description 查询监控项目适用性
     * @CreateTime 2017-8-26 下午3:04:46
     * @CreateBy 刘兵
     * @param id 维修项目id
     * @return MaintenanceProject 维修项目
     */
	@Override
	public MaintenanceProject queryMonitorItemModel(String id) {
		MaintenanceProject pro = maintenanceProjectMapper.queryMonitorItemModel(id);
		// 分钟转化为小时
		switchMinuteToHour(pro);
		// 时控/时寿项目
		if(MaintenanceProjectTypeEnum.TIMECONTROL.getId().equals(pro.getWxxmlx()) 
				|| MaintenanceProjectTypeEnum.WHENLIFE.getId().equals(pro.getWxxmlx())){
			// 将监控项放到对应的部件下
			assembleMaterialMonitor(pro);
		}
		return pro;
	}
	
	/**
	 * @Description 根据条件查询监控项目适用性
	 * @CreateTime 2017-9-20 下午3:20:23
	 * @CreateBy 刘兵
	 * @param maintenanceProject 维修项目
	 * @return maintenanceProject 维修项目
	 */
	@Override
	public MaintenanceProject queryMonitorItemModelByWxxm(MaintenanceProject maintenanceProject) {
		MaintenanceProject pro = maintenanceProjectMapper.queryMonitorItemModelByWxxm(maintenanceProject);
		if(null != pro){
			// 分钟转化为小时
			switchMinuteToHour(pro);
			// 时控/时寿项目
			if(MaintenanceProjectTypeEnum.TIMECONTROL.getId().equals(pro.getWxxmlx()) 
					|| MaintenanceProjectTypeEnum.WHENLIFE.getId().equals(pro.getWxxmlx())){
				// 将监控项放到对应的部件下
				assembleMaterialMonitor(pro);
			}
		}
		return pro;
	}
	
	/**
	 * @Description 设置获取维修项目对应飞机关系集合,并赋值
	 * @CreateTime 2017-8-28 上午11:32:32
	 * @CreateBy 刘兵
	 * @param maintenanceProjectList 维修项目
	 */
	private void setProjectModelList(List<MaintenanceProject> maintenanceProjectList){
		List<String> mainidList = new ArrayList<String>();
		for (MaintenanceProject mp : maintenanceProjectList) {
			mainidList.add(mp.getId());
		}
		if(mainidList.size() > 0){
			List<ProjectModel> projectModelAllList = projectModelMapper.queryByMainidList(mainidList);
			Map<String, List<ProjectModel>> map = new HashMap<String, List<ProjectModel>>();
			for (ProjectModel pm : projectModelAllList){
				List<ProjectModel> projectModelList = map.get(pm.getMainid());
				if(null == projectModelList){
					projectModelList = new ArrayList<ProjectModel>();
					map.put(pm.getMainid(), projectModelList);
				}
				projectModelList.add(pm);
			}
			for (MaintenanceProject mp : maintenanceProjectList) {
				mp.setProjectModelList(map.get(mp.getId()));
			}
		}
	}

	/**
	 * @Description 查询差异数据
	 * @CreateTime 2017年8月28日 下午5:37:02
	 * @CreateBy 韩武
	 * @param project
	 * @return
	 */
	@Override
	public Map<String, MaintenanceProject> queryDifferenceData(
			MaintenanceProject project) {
		
		Map<String, MaintenanceProject> map = new HashMap<String, MaintenanceProject>();
		
		// 当前维修项目
		MaintenanceProject current = maintenanceProjectMapper.selectByPrimaryKey(project.getId());
		current.setParamsMap(project.getParamsMap());
		current.setZt(null);
		current = queryDetail(current);
		
		// 前一个维修项目
		String fBbid = project.getfBbid();
		if(StringUtils.isBlank(fBbid)){
			fBbid = current.getfBbid() == null ? current.getId() : current.getfBbid();
		}
		MaintenanceProject previous = maintenanceProjectMapper.selectByPrimaryKey(fBbid);
		previous.setParamsMap(project.getParamsMap());
		previous.setZt(null);
		previous = queryDetail(previous);
		
		map.put(PROJECT_TYPE_CURRENT, current);
		map.put(PROJECT_TYPE_PREVIOUS, previous);
		return map;
	}
	
	/**
	 * @Description 分钟转化为小时
	 * @CreateTime 2017年8月31日 下午4:58:49
	 * @CreateBy 韩武
	 * @param list
	 */
	private MaintenanceProject switchMinuteToHour(MaintenanceProject project){
		
		if(project == null){
			return project;
		}
		
		List<ProjectMonitor> list = project.getProjectMonitors();
		
		if(list != null && !list.isEmpty()){
			
			for (ProjectMonitor monitor : list) {
				
				// 是时间类型的监控项目
				if(MonitorProjectEnum.isTime(monitor.getJklbh())){
					
					monitor.setScz(StringAndDate_Util.convertToHour(monitor.getScz()));
					
					monitor.setZqz(StringAndDate_Util.convertToHour(monitor.getZqz()));
					
					monitor.setYdzQ(StringAndDate_Util.convertToHour(monitor.getYdzQ()));
					
					monitor.setYdzH(StringAndDate_Util.convertToHour(monitor.getYdzH()));
					
				}
			}
		}
		
		return project;
		
	}
	
	/**
	 * @Description 查询适用维修项目
	 * @CreateTime 2017年9月27日 下午2:07:03
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException 
	 */
	@Override
	public List<MaintenanceProject> queryApplyList(InstallationListEditable record) throws BusinessException {
		
		if(StringUtils.isNotBlank(record.getId())){
			InstallationListEditable temp = installationListEditableMapper.selectByPrimaryKey(record.getId());
			Aircraftinfo info = new Aircraftinfo();
			if(temp != null){
				info.setFjzch(temp.getFjzch());
				info.setDprtcode(temp.getDprtcode());
			}else{
				InstallationListEffective temp2 = installationListEffectiveMapper.selectByPrimaryKey(record.getId());
				info.setFjzch(temp2.getFjzch());
				info.setDprtcode(temp2.getDprtcode());
			}
			
			info = aircraftinfoService.selectByfjzchAndDprtcode(info);
			record.setFjzch(info.getFjzch());
			record.setJx(info.getFjjx());
		}
		List<MaintenanceProject> list = maintenanceProjectMapper.queryApplyList(record);
		// 分钟转换为小时
		for (MaintenanceProject pro : list) {
			switchMinuteToHour(pro);
		}
		return list;
	}
	
	/**
     * @Description 查询适用维修项目(航材/工具检验)
     * @CreateTime 2018-5-2 上午10:45:01
     * @CreateBy 刘兵
     * @param dprtcode 机构代码
     * @param bjh 部件号
     * @return
     */
	@Override
	public List<MaintenanceProject> queryApplyListByBjh(String bjh, String dprtcode){
		List<MaintenanceProject> list = maintenanceProjectMapper.queryApplyListByBjh(dprtcode, bjh);
		// 分钟转换为小时
		for (MaintenanceProject pro : list) {
			switchMinuteToHour(pro);
		}
		return list;
	}

	/**
	 * @Description 获取导出数据
	 * @CreateTime 2017年12月19日 上午10:35:39
	 * @CreateBy 韩武
	 * @param pro
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getExportList(MaintenanceProject param) {
		param.getParamsMap().put("relatePart", 1);
		List<Map<String, Object>> list;
		if(new Integer(1).equals(param.getParamsMap().get("type"))){
			list = groupByATA(param);
		}else{
			list = groupByCatalog(param);
		}
		
		for (Map<String, Object> map : list) {
			List<MaintenanceProject> projects = (List<MaintenanceProject>) map.get("children");
			for (MaintenanceProject project : projects) {
				
				// 拼接适用性
				StringBuilder syxstr = new StringBuilder();
				if("00000".equals(project.getSyx())){
					syxstr.append("ALL");
				}
				if(project.getSyx() == null || "null".equals(project.getSyx())){
					syxstr.append("N/A");
				}
				if("-".equals(project.getSyx())){
					for (ProjectModel model : project.getProjectModelList()) {
						syxstr.append(model.getFjzch()).append(",");
					}
					if(syxstr.length() > 0){
						syxstr.deleteCharAt(syxstr.lastIndexOf(","));
					}
				}
				project.getParamsMap().put("syxstr", syxstr);
				
				// 计算工时
				BigDecimal jhgsRs = project.getJhgsRs() != null ? project.getJhgsRs() : BigDecimal.ZERO;
				BigDecimal jhgsXss = project.getJhgsXss() != null ? project.getJhgsXss() : BigDecimal.ZERO;
				BigDecimal total = jhgsRs.multiply(jhgsXss).setScale(2);
				project.getParamsMap().put("total", total);
				
				// 拼接监控项
				for (ProjectMonitor mon : project.getProjectMonitors()) {
					String jkmsstr = MonitorProjectEnum.getName(mon.getJklbh());
					mon.getParamsMap().put("jkmsstr", jkmsstr);
					
					if(StringUtils.isNotBlank(mon.getScz())){
						String scstr = mon.getScz() + MonitorProjectEnum.getUnit(mon.getJklbh(), String.valueOf(mon.getDwScz()));
						mon.getParamsMap().put("scstr", scstr);
					}
					if(StringUtils.isNotBlank(mon.getZqz())){
						String zqstr = mon.getZqz() + MonitorProjectEnum.getUnit(mon.getJklbh(), String.valueOf(mon.getDwZqz()));
						mon.getParamsMap().put("zqstr", zqstr);
					}
					if(StringUtils.isNotBlank(mon.getYdzQ())){
						String ydstr = "-" + mon.getYdzQ() + "/+" + mon.getYdzH() + 
								MonitorProjectEnum.getUnit(mon.getJklbh(), String.valueOf(mon.getYdzQdw()));
						mon.getParamsMap().put("ydstr", ydstr);
					}
				}
				
				Collections.sort(project.getProjectMonitors(), new Comparator<ProjectMonitor>() {
					public int compare(ProjectMonitor o1, ProjectMonitor o2) {
						if(StringUtils.isNotBlank(o1.getBjh()) 
								&& StringUtils.isNotBlank(o2.getBjh()) 
								&& !o1.getBjh().equals(o2.getBjh())){
							return o1.getBjh().compareTo(o2.getBjh());
						}
						return o1.getJklbh().compareTo(o2.getJklbh());
					}
				});
			}
		}
		return list;
	}
	
	/**
	 * @Description 导出
	 * @CreateTime 2018-1-29 下午3:17:36
	 * @CreateBy 刘兵
	 * @param paramObj 当前参数
	 * @return List<MaintenanceProject>
	 */
	@Override
	public List<MaintenanceProject> doExportExcel(MaintenanceProject param) {
		List<MaintenanceProject> mList = maintenanceProjectMapper.queryExportExcelList(param);
		formatMtList(mList);
		//对比
		for (int i = 0; i < mList.size(); i++) {
			MaintenanceProject project = mList.get(i);
			if(StringUtils.isNotBlank(project.getfBbid()) && EffectiveEnum.YES.getId() == project.getYxbs() && i != mList.size() - 1){
				project.compareColumn(project, mList.get(i + 1));
			}
		}
		return mList;
	}
	
	/**
	 * @Description 格式化维修项目
	 * @CreateTime 2018-1-30 下午3:37:53
	 * @CreateBy 刘兵
	 * @param mList
	 */
	private void formatMtList(List<MaintenanceProject> mList){
		for (MaintenanceProject project : mList) {
			switchMinuteToHour(project);
			// 拼接适用性
			StringBuilder syxstr = new StringBuilder();
			if("00000".equals(project.getSyx())){
				syxstr.append("ALL");
			}
			if(project.getSyx() == null || "null".equals(project.getSyx())){
				syxstr.append("N/A");
			}
			if("-".equals(project.getSyx())){
				for (ProjectModel model : project.getProjectModelList()) {
					syxstr.append(model.getFjzch()).append(",");
				}
				if(syxstr.length() > 0){
					syxstr.deleteCharAt(syxstr.lastIndexOf(","));
				}
			}
			project.getParamsMap().put("syxstr", syxstr);
			
			// 计算工时
			BigDecimal jhgsRs = project.getJhgsRs() != null ? project.getJhgsRs() : BigDecimal.ZERO;
			BigDecimal jhgsXss = project.getJhgsXss() != null ? project.getJhgsXss() : BigDecimal.ZERO;
			BigDecimal total = jhgsRs.multiply(jhgsXss).setScale(2);
			project.getParamsMap().put("total", total);
			
			// 拼接监控项
			for (ProjectMonitor mon : project.getProjectMonitors()) {
				String jkmsstr = MonitorProjectEnum.getName(mon.getJklbh());
				mon.getParamsMap().put("jkmsstr", jkmsstr);
				
				if(StringUtils.isNotBlank(mon.getScz())){
					String scstr = mon.getScz() + MonitorProjectEnum.getUnit(mon.getJklbh(), String.valueOf(mon.getDwScz()));
					mon.getParamsMap().put("scstr", scstr);
				}
				if(StringUtils.isNotBlank(mon.getZqz())){
					String zqstr = mon.getZqz() + MonitorProjectEnum.getUnit(mon.getJklbh(), String.valueOf(mon.getDwZqz()));
					mon.getParamsMap().put("zqstr", zqstr);
				}
				if(StringUtils.isNotBlank(mon.getYdzQ())){
					String ydstr = "-" + mon.getYdzQ() + "/+" + mon.getYdzH() + 
							MonitorProjectEnum.getUnit(mon.getJklbh(), String.valueOf(mon.getYdzQdw()));
					mon.getParamsMap().put("ydstr", ydstr);
				}
			}
			
			Collections.sort(project.getProjectMonitors(), new Comparator<ProjectMonitor>() {
				public int compare(ProjectMonitor o1, ProjectMonitor o2) {
					if(StringUtils.isNotBlank(o1.getBjh()) 
							&& StringUtils.isNotBlank(o2.getBjh()) 
							&& !o1.getBjh().equals(o2.getBjh())){
						return o1.getBjh().compareTo(o2.getBjh());
					}
					return o1.getJklbh().compareTo(o2.getJklbh());
				}
			});
			
			if(null == project.getProjectMonitors() || project.getProjectMonitors().size() == 0){
				List<ProjectMonitor> pmList = new ArrayList<ProjectMonitor>();
				pmList.add(new ProjectMonitor());
				project.setProjectMonitors(pmList);
			}
		}
	}
	
}
