package com.eray.thjw.quality.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.DepartmentMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.dao.MaintenancePersonnelMapper;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.service.MaintenancePersonnelService;
import com.eray.thjw.quality.service.PersonnelToAchievementService;
import com.eray.thjw.quality.service.PersonnelToAuthorizationRecordService;
import com.eray.thjw.quality.service.PersonnelToBusinessAssessmentService;
import com.eray.thjw.quality.service.PersonnelToCreditRecordService;
import com.eray.thjw.quality.service.PersonnelToEducationExperienceService;
import com.eray.thjw.quality.service.PersonnelToForeignLanguageService;
import com.eray.thjw.quality.service.PersonnelToPostService;
import com.eray.thjw.quality.service.PersonnelToProfessionalTitleService;
import com.eray.thjw.quality.service.PersonnelToTechnicalExperienceService;
import com.eray.thjw.quality.service.PersonnelToTechnicalGradeRecordService;
import com.eray.thjw.quality.service.PersonnelToWorkExperienceService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.training.dao.BusinessMapper;
import com.eray.thjw.training.dao.BusinessToMaintenancePersonnelMapper;
import com.eray.thjw.training.dao.CourseMapper;
import com.eray.thjw.training.po.Business;
import com.eray.thjw.training.po.Course;
import com.eray.thjw.training.service.PlanPersonService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EnableEnum;

@Service
public class MaintenancePersonnelServiceImpl implements MaintenancePersonnelService {
	
	@Resource
	private MaintenancePersonnelMapper maintenancePersonnelMapper;
	
	@Autowired
	private DepartmentMapper departmentMapper;
	@Autowired
	private CourseMapper courseMapper;
	
	@Autowired
	private BusinessMapper businessMapper;

	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private BusinessToMaintenancePersonnelMapper businessToMaintenancePersonnelMapper;
	
	@Resource
	private PersonnelToEducationExperienceService personnelToEducationExperienceService;
	
	@Resource
	private PersonnelToForeignLanguageService personnelToForeignLanguageService;
	
	@Resource
	private PersonnelToProfessionalTitleService personnelToProfessionalTitleService;
	
	@Resource
	private PersonnelToWorkExperienceService personnelToWorkExperienceService;
	
	@Resource
	private PersonnelToPostService personnelToPostService;
	
	@Resource
	private PersonnelToTechnicalExperienceService personnelToTechnicalExperienceService;
	
	@Resource
	private PersonnelToAuthorizationRecordService personnelToAuthorizationRecordService;
	
	@Resource
	private PersonnelToTechnicalGradeRecordService personnelToTechnicalGradeRecordService;
	
	@Resource
	private PlanPersonService planPersonService;
	
	@Resource
	private PersonnelToBusinessAssessmentService personnelToBusinessAssessmentService;
	
	@Resource
	private PersonnelToAchievementService personnelToAchievementService;
	
	@Resource
	private PersonnelToCreditRecordService personnelToCreditRecordService;
	
	@Resource
	private AttachmentMapper attachmentMapper;
	
	private String parentIcon = "icon-folder-open-alt";
	private String childrenIcon = "icon-user";
	
	/**
	 * @author sunji
	 * @description 根据gwid查询数据
	 * @param id
	 * @return List<MaintenancePersonnel>
	 */
	public List<MaintenancePersonnel> queryAllBygwid(String gwid) {
		return maintenancePersonnelMapper.queryAllBygwid(gwid);
	}
	
	/**
	 * @author liub
	 * @description 根据机构代码查询维修人员档案树(按部门)
	 * @param id
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryDrptTreeByDprtcode(String dprtcode,String str) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dprtcode", dprtcode);
		List<Department> departments = departmentMapper.getChildrentList(param);
		Map<String, Object> stateMap = new HashMap<String, Object>();//树形菜单展开或收缩
		stateMap.put("opened", true);
		List<Map<String, Object>> treeList = new ArrayList<Map<String,Object>>();
		Map<String, Department> drptMap = new HashMap<String, Department>();//部门map
		for (Department department : departments) {
			String parentId = department.getParentid();
			if(department.getId().equals(dprtcode)){
				parentId = "#";
			}
			addDrptTree(treeList, department.getId(), department.getDprtname(), stateMap, this.parentIcon, parentId, getParamMap(null, 0));
			drptMap.put(department.getId(), department);
		}
		List<MaintenancePersonnel> personnels = maintenancePersonnelMapper.queryDrptTreeByDprtcode(dprtcode,str);
		for (MaintenancePersonnel maintenancePersonnel : personnels) {
			StringBuffer s = new StringBuffer();
			String parentId = dprtcode;
			if(maintenancePersonnel.getZdr() != null && drptMap.containsKey(maintenancePersonnel.getZdr().getBmdm())){
				parentId = maintenancePersonnel.getZdr().getBmdm();
			}
			addDrptTree(treeList, maintenancePersonnel.getId(), s.append(maintenancePersonnel.getRybh()).append(" ").append(maintenancePersonnel.getXm()), stateMap, this.childrenIcon, parentId, getParamMap(maintenancePersonnel, 1));
		}
		return treeList;
	}
	
	/**
	 * @author liub
	 * @description 根据机构代码查询维修人员档案树(按岗位)
	 * @param id
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryBusinessTreeByDprtcode(String dprtcode,String str) {
		List<Business> businesses = businessMapper.queryBusinessByDprtcode(dprtcode);
		Map<String, Object> stateMap = new HashMap<String, Object>();//树形菜单展开或收缩
		stateMap.put("opened", true);
		List<Map<String, Object>> treeList = new ArrayList<Map<String,Object>>();
		Map<Object, Business> drptMap = new HashMap<Object, Business>();//部门map
		for (Business business : businesses) {
			addDrptTree(treeList, business.getId(), business.getDgmc(), stateMap, this.parentIcon, "#", getParamMap(null, 0));
			drptMap.put(business.getId(), business);
		}
		List<MaintenancePersonnel> personnels = maintenancePersonnelMapper.queryBusinessTreeByDprtcode(dprtcode,str);
//		int maintenancePersonnelTreeId = 0;
		for (MaintenancePersonnel maintenancePersonnel : personnels) {
			StringBuffer s = new StringBuffer();
			Object parentId = "bzg";
			if(maintenancePersonnel.getParamsMap().get("gwid") != null && drptMap.containsKey(maintenancePersonnel.getParamsMap().get("gwid"))){
				parentId = maintenancePersonnel.getParamsMap().get("gwid");
			}
//			addDrptTree(treeList, maintenancePersonnel.getId(), s.append(maintenancePersonnel.getRybh()).append(" ").append(maintenancePersonnel.getXm()), stateMap,this.childrenIcon, parentId, getParamMap(maintenancePersonnel, 1));
//			addDrptTree(treeList, maintenancePersonnelTreeId++, s.append(maintenancePersonnel.getRybh()).append(" ").append(maintenancePersonnel.getXm()), stateMap,this.childrenIcon, parentId, getParamMap(maintenancePersonnel, 1));
			addDrptTree(treeList, UUID.randomUUID().toString(), s.append(maintenancePersonnel.getRybh()).append(" ").append(maintenancePersonnel.getXm()), stateMap,this.childrenIcon, parentId, getParamMap(maintenancePersonnel, 1));
		}
		addDrptTree(treeList, "bzg", "不在岗人员", stateMap,this.parentIcon, "#", getParamMap(null, 0));
		return treeList;
	}
	
	/**
	 * @author liub
	 * @description 获取属性参数
	 * @param resultList,type
	 */
	private Map<String, Object> getParamMap(MaintenancePersonnel personnels,int type){
		Map<String, Object> attrMap = new HashMap<String, Object>();
		attrMap.put("type", type);
		if(null != personnels){
			StringBuffer s = new StringBuffer();
			attrMap.put("id", personnels.getId());
			attrMap.put("rybh", personnels.getRybh());
			attrMap.put("xm", personnels.getXm());
			attrMap.put("szdw", personnels.getSzdw());
			attrMap.put("displayName", s.append(personnels.getRybh()).append(" ").append(personnels.getXm()));
		}
		return attrMap;
	}
	
	/**
	 * @author liub
	 * @description 新增部门树
	 * @param resultList,typeMap,type
	 * @develop date 2017.03.17
	 */
	private void addDrptTree(List<Map<String, Object>> resultList,Object id,Object text,Object stateMap,String icon , Object parentId,Map<String, Object> paramMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("id", id);
		resultMap.put("text", text);
		resultMap.put("state", stateMap);
		resultMap.put("icon", icon);
		resultMap.put("parent", parentId);
		resultMap.put("li_attr", paramMap);
		resultList.add(resultMap);
	}

	/**
	 * 保存维修人员档案
	 */
	@Override
	public String save(MaintenancePersonnel personnel) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		personnel.setZt(1);
		personnel.setWhbmid(user.getBmdm());
		personnel.setWhrid(user.getId());
		personnel.setWhsj(new Date());
		personnel.setCzls(UUID.randomUUID().toString());
		int count=maintenancePersonnelMapper.selectCount4VlidationRybh(personnel.getRybh(), personnel.getDprtcode());
		// 维修人员档案主表
		if(StringUtils.isBlank(personnel.getId())){	// 新增维修人员档案主表
			if(count>0){
				throw new BusinessException("人员编号不能重复!");
			}
			personnel.setId(UUID.randomUUID().toString());
			personnel.setLogOperationEnum(LogOperationEnum.SAVE_WO);
			maintenancePersonnelMapper.insertSelective(personnel);
			// 写入日志
			commonRecService.write(personnel.getId(), TableEnum.B_Z_001, user.getId(), personnel.getCzls(),
					personnel.getLogOperationEnum(), UpdateTypeEnum.SAVE, personnel.getId());
		}else{	// 修改维修人员档案主表
			if(count>1){
				throw new BusinessException("人员编号不能重复!");
			}
			personnel.setLogOperationEnum(LogOperationEnum.SUBMIT_WO);
			maintenancePersonnelMapper.updateByMaintenance(personnel);	
			// 写入日志
			commonRecService.write(personnel.getId(), TableEnum.B_Z_001, user.getId(), personnel.getCzls(),
					personnel.getLogOperationEnum(), UpdateTypeEnum.UPDATE, personnel.getId());
		}
		// 删除岗位信息
		if(EnableEnum.DISABLED.getId().equals(personnel.getZzzt())){
			deletePositionInfo(personnel);
		}
		// 保存教育经历
		personnelToEducationExperienceService.save(personnel);
		// 保存外语水平
		personnelToForeignLanguageService.save(personnel);
		// 保存聘任职称
		personnelToProfessionalTitleService.save(personnel);
		// 保存工作履历
		personnelToWorkExperienceService.save(personnel);
		// 保存岗位/职务
		personnelToPostService.save(personnel);
		// 保存技术履历
		personnelToTechnicalExperienceService.save(personnel);
		// 保存维修执照
		personnelToAuthorizationRecordService.saveMaintenanceLicense(personnel);
		// 保存机型执照
		personnelToAuthorizationRecordService.saveTypeLicense(personnel);
		// 保存维修技术等级升级记录
		personnelToTechnicalGradeRecordService.save(personnel);
		// 保存培训记录
		planPersonService.save(personnel);
		// 保存业务考核记录
		personnelToBusinessAssessmentService.save(personnel);
		// 保存学术成就
		personnelToAchievementService.saveScholarship(personnel);
		// 保存嘉奖记录
		personnelToAchievementService.saveCitation(personnel);
		// 保存事故征候情况
		personnelToCreditRecordService.saveIncidentSituation(personnel);
		// 保存负有责任的不安全事件
		personnelToCreditRecordService.saveUnsafeIncident(personnel);
		// 保存不诚信行为
		personnelToCreditRecordService.saveDishonestBehaviors(personnel);
		// 保存附件
		saveAttachments(personnel);
		// 删除附件
		delteAttachments(personnel);
		return personnel.getId();
	}
	
	/**
	 * 保存附件
	 * @param data
	 */
	private void saveAttachments(MaintenancePersonnel data){
		List<Attachment> attachments = data.getAttachments();
		List<String> ids = new ArrayList<String>();
		if (attachments != null && !attachments.isEmpty()) {
			User user = ThreadVarUtil.getUser();
			for (Attachment attachment : attachments) {
				if(StringUtils.isBlank(attachment.getId())){
					attachment.setMainid(data.getId());
					attachment.setDprtcode(data.getDprtcode());
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					attachment.setCzbmid(user.getBmdm());
					attachmentMapper.addFile(attachment);
					ids.add(attachment.getId());
				}
			}
		}
		if(!ids.isEmpty()){
			commonRecService.write("id", ids, TableEnum.D_011, ThreadVarUtil.getUser().getId(), data.getCzls(),
					data.getLogOperationEnum(), UpdateTypeEnum.SAVE, data.getId());
		}
	}
	
	/**
	 * 删除附件
	 * @param data
	 */
	private void delteAttachments(MaintenancePersonnel data){
		List<String> delList = data.getDelAttachments();
		if (delList != null && !delList.isEmpty()) {
			for (String delId : delList) {
				attachmentMapper.updateByPrimaryKey(delId);
			}
		}
		if(!delList.isEmpty()){
			commonRecService.write("id", delList, TableEnum.D_011, ThreadVarUtil.getUser().getId(), data.getCzls(),
					data.getLogOperationEnum(), UpdateTypeEnum.DELETE, data.getId());
		}
	}

	/**
	 * 维修人员档案分页查询
	 */
	@Override
	public Map<String, Object> queryPage(MaintenancePersonnel personnel) {
		String id = personnel.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		personnel.setId(null);
		PageHelper.startPage(personnel.getPagination());
		List<MaintenancePersonnel> list = maintenancePersonnelMapper.queryPage(personnel);
		if(((Page)list).getTotal() > 0){//当总记录数大于0执行分页查询
			//分页查询
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					MaintenancePersonnel param = new MaintenancePersonnel();
					param.setId(id);
					List<MaintenancePersonnel> newRecordList = maintenancePersonnelMapper.queryPage(param);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			// 设置岗位
			setPost(personnel, list);
			return PageUtil.pack4PageHelper(list, personnel.getPagination());
		}else{
			List<MaintenancePersonnel> newRecordList = new ArrayList<MaintenancePersonnel>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				MaintenancePersonnel param = new MaintenancePersonnel();
				param.setId(id);
				newRecordList = maintenancePersonnelMapper.queryPage(param);
				if(newRecordList != null && newRecordList.size() == 1){
					// 设置岗位
					setPost(personnel, list);
					return PageUtil.pack(1, newRecordList, personnel.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, personnel.getPagination());
		}
	}
	
	/**
	 * 设置岗位
	 * @param personnel
	 */
	private void setPost(MaintenancePersonnel personnel, List<MaintenancePersonnel> list){
		List<MaintenancePersonnel> postList = maintenancePersonnelMapper.queryWithPost(personnel);
		if(list.size()>0){
			for (MaintenancePersonnel i : list) {
				for (MaintenancePersonnel post : postList) {
					if(i.getId().equals(post.getId())){
						i.setBusinesses(post.getBusinesses());
					}
				}
			}
		}
	}

	/**
	 * 删除维修人员档案
	 */
	@Override
	public void delete(MaintenancePersonnel personnel) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		personnel.setWhbmid(user.getBmdm());
		personnel.setWhrid(user.getId());
		personnel.setWhsj(new Date());
		personnel.setCzls(UUID.randomUUID().toString());
		personnel.setLogOperationEnum(LogOperationEnum.ZUOFEI);
		personnel.setZt(0);
		
		// 删除岗位信息
		deletePositionInfo(personnel);
		
		maintenancePersonnelMapper.updateByPrimaryKeySelective(personnel);
		// 写入日志
		commonRecService.write(personnel.getId(), TableEnum.B_Z_001, user.getId(), personnel.getCzls(),
				personnel.getLogOperationEnum(), UpdateTypeEnum.DELETE, personnel.getId());
	}
	
	/**
	 * 删除岗位信息
	 * @Description 
	 * @CreateTime 2017年8月11日 下午9:38:37
	 * @CreateBy 韩武
	 * @param id
	 */
	private void deletePositionInfo(MaintenancePersonnel personnel){
		// 插入日志
		String sql = "b.wxrydaid = '"+personnel.getId()+"' ";
		commonRecService.writeByWhere(sql.toString(), TableEnum.B_P_006, ThreadVarUtil.getUser().getId(), personnel.getCzls(),
				personnel.getLogOperationEnum(), UpdateTypeEnum.DELETE, personnel.getId());
		businessToMaintenancePersonnelMapper.deleteByWxrydaid(personnel.getId());
	}

	/**
	 * 加载维修人员档案详情
	 */
	@Override
	public MaintenancePersonnel loadDetail(MaintenancePersonnel personnel) {
		String id = personnel.getId();
		personnel = maintenancePersonnelMapper.selectByPrimaryKey(id);
		personnel.setEducations(personnelToEducationExperienceService.query(id));
		personnel.setLanguages(personnelToForeignLanguageService.query(id));
		personnel.setTitles(personnelToProfessionalTitleService.query(id));
		personnel.setWorkExperiences(personnelToWorkExperienceService.query(id));
		personnel.setPosts(personnelToPostService.query(id));
		personnel.setTechnicalExperiences(personnelToTechnicalExperienceService.query(id));
		personnel.setMaintenanceLicenses(personnelToAuthorizationRecordService.queryMaintenanceLicenses(id));
		personnel.setTypeLicenses(personnelToAuthorizationRecordService.queryTypeLicenses(id));
		personnel.setGrades(personnelToTechnicalGradeRecordService.query(id));
		personnel.setTrainings(planPersonService.queryByWxrydaid(id));
		personnel.setBusinessAssessments(personnelToBusinessAssessmentService.query(id));
		personnel.setScholarships(personnelToAchievementService.queryScholarships(id));
		personnel.setCitationRecords(personnelToAchievementService.queryCitations(id));
		personnel.setIncidentSituations(personnelToCreditRecordService.queryIncidentSituations(id));
		personnel.setUnsafeIncidents(personnelToCreditRecordService.queryUnsafeIncidents(id));
		personnel.setDishonestBehaviors(personnelToCreditRecordService.queryDishonestBehaviors(id));
		
		Attachment param = new Attachment();
		param.setMainid(id);
		personnel.setAttachments(attachmentMapper.queryListAttachments(param));
		return personnel;
	}

	/**
	 * id查询
	 */
	@Override
	public MaintenancePersonnel selectByPrimaryKey(String id) {
		return maintenancePersonnelMapper.selectByPrimaryKey(id);
	}

	@Override
	public int validtion4RyidExist(MaintenancePersonnel personnel) {
		
		return maintenancePersonnelMapper.validtion4RyidExist(personnel);
	}

	@Override
	public MaintenancePersonnel findRyidExist(MaintenancePersonnel personnel) {
		return maintenancePersonnelMapper.findRyidExist(personnel);
	}

	/**
	 * 
	 * @Description 查询所有岗位监控
	 * @CreateTime 2017-11-16 上午10:01:45
	 * @CreateBy 孙霁
	 * @param maintenancePersonnel
	 */
	@Override
	public Map<String, Object> queryAllMonitor(
			MaintenancePersonnel maintenancePersonnel) {
		PageHelper.startPage(maintenancePersonnel.getPagination());
		List<MaintenancePersonnel> list = maintenancePersonnelMapper.queryAllMonitor(maintenancePersonnel);
		return PageUtil.pack4PageHelper(list, maintenancePersonnel.getPagination());
	}

	@Override
	public List<Map<String, Object>> loadTrainingcourse(MaintenancePersonnel personnel) {
		return maintenancePersonnelMapper.loadTrainingcourse(personnel);
	}
	/**
	 * @Description 导出
	 * @CreateTime 2018-1-29 下午5:28:03
	 * @CreateBy 刘兵
	 * @param maintenancePersonnel 维修人员档案
	 * @return List<MaintenancePersonnel
	 */
	@Override
	public List<MaintenancePersonnel> doExportExcel(MaintenancePersonnel personnel) {
		List<MaintenancePersonnel> mList = maintenancePersonnelMapper.queryAllMonitor(personnel);
		for (MaintenancePersonnel maintenancePersonnel : mList) {
			StringBuffer yxq = new StringBuffer();
			if(null != maintenancePersonnel.getParamsMap().get("ksrq")){
				yxq.append(maintenancePersonnel.getParamsMap().get("ksrq").toString().substring(0, 10));
			}
			if(null != maintenancePersonnel.getParamsMap().get("jzrq")){
				yxq.append("~").append(maintenancePersonnel.getParamsMap().get("jzrq").toString().substring(0, 10));
			}
			maintenancePersonnel.getParamsMap().put("yxq", yxq);
		}
		return mList;
	}

	/**
	 * wxryid查询
	 * @param id
	 * @return
	 */
	@Override
	public MaintenancePersonnel selectByWxryid(String wxryid) {
		return maintenancePersonnelMapper.selectByWxryid(wxryid);
	}

	@Override
	public List<Map<String, Object>> loadTrainingcourseFjjx(MaintenancePersonnel personnel) {
		return maintenancePersonnelMapper.loadTrainingcourseFjjx(personnel);
	}
		
}
