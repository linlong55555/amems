package com.eray.thjw.baseinfo.servcice.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.framework.saibong.dao.SaibongSnMapper;
import com.eray.framework.saibong.po.SaibongSn;
import com.eray.thjw.baseinfo.dao.CustomerMapper;
import com.eray.thjw.baseinfo.dao.ProjectAircraftMapper;
import com.eray.thjw.baseinfo.dao.ProjectMainAreaMapper;
import com.eray.thjw.baseinfo.dao.ProjectMapper;
import com.eray.thjw.baseinfo.po.Customer;
import com.eray.thjw.baseinfo.po.Project;
import com.eray.thjw.baseinfo.po.ProjectAircraft;
import com.eray.thjw.baseinfo.po.ProjectMainArea;
import com.eray.thjw.baseinfo.rule.ProjectCodeRuleable;
import com.eray.thjw.baseinfo.servcice.ProjectService;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.dao.DepartmentMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.Workpackage145Mapper;
import com.eray.thjw.produce.po.Workpackage145;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.project2.CustProjectEnum;

/** 
 * @Description 项目信息实现类
 * @CreateTime 2017-9-20 上午10:41:35
 * @CreateBy 甘清	
 */
@Service
public class ProjectServiceImpl implements ProjectService {

	@Resource
	private ProjectMapper projectMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private ProjectMainAreaMapper projectMainAreaMapper; //项目信息 关键部位
	
	@Resource
	private CustomerMapper customerMapper;  //客户信息
	
	@Resource
	private AttachmentMapper attachmentMapper; //附件信息
	
	@Resource
	private DepartmentMapper departmentMapper; //部门
	
	@Resource
	private CommonService commonService;  //日志通用
	
	@Resource
	private Workpackage145Mapper workpackage145Mapper; //145工包信息
	
	
	@Resource
	private ProjectAircraftMapper projectAircraftMapper; //飞机基本信息
	
	@Resource(name="projectCodeHXtor")
	private ProjectCodeRuleable projectCodeRuleableHX; //华夏生成项目编号工具类
	
	@Resource(name="projectCodeJStor")
	private ProjectCodeRuleable projectCodeRuleableJS; //江苏公务机生成项目编号工具类
	
	@Resource
	private SaibongSnMapper saibongSnMapper; //采番数据 
	
	@Override
	public Map<String, Object> getProjectList(Project project) {
		String id = project.getId();
		project.setId("");
		PageHelper.startPage(project.getPagination());
		List<Project> list = projectMapper.getProjectList(project);
		if (((Page)list).getTotal() > 0) {
			if(StringUtils.isNotBlank(id)) {
				if (!PageUtil.hasRecord(list, id)) {
					Project s = new Project();
					s.setId(id);
					Project firtProject = projectMapper.getProjectById(s);
					if (firtProject != null) {
						list.add(0, firtProject);
					}
				}
			}
			return PageUtil.pack4PageHelper(list, project.getPagination());
		} else {
			List<Project> projectlist = new ArrayList<Project>();
			if(StringUtils.isNotBlank(id)) {
				Project s = new Project();
				s.setId(id);
				Project pro = projectMapper.getProjectById(s);
				if (pro != null) {
					projectlist.add(pro);
				}
			}
			return PageUtil.pack(1, projectlist, project.getPagination());
		}
	}

	@Override
	public Project addProject(Project project) throws BusinessException {
		try {
			return this.addProjectRealize(project);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Description 添加项目信息
	 * @CreateTime 2017-9-20 上午10:56:53
	 * @CreateBy 甘清
	 * @param project 添加项目信息
	 * @return boolean
	 * @throws ParseException 
	 */
	@SuppressWarnings("static-access")
	private Project addProjectRealize(Project project) throws ParseException, BusinessException { 
		User user = ThreadVarUtil.getUser();
		String id = UUID.randomUUID().toString();
		String czls = UUID.randomUUID().toString(); //操作流水号
		project.setWhsj(new Date());
		project.setId(id);
		project.setDprtcode(user.getJgdm());
		if (StringUtils.isNotEmpty(project.getXmbm())) { //项目编号不为空时，才校验是否重名
			List<Project> checkPros = projectMapper.checkProject(project);
			if (checkPros != null && checkPros.size() > 0) {
				throw new BusinessException("项目编号:" + project.getXmbm() + "已存在!");
			}
		}
		project.setWhrid(user.getId());
		project.setWhbmid(user.getBmdm());
		project.setZt(CustProjectEnum.ST_2.getId());
		project.setKzxbs(EffectiveEnum.YES.getId());
		if (StringUtils.isNotEmpty(project.getFxsjstr()) && !":".equals(project.getFxsjstr().trim())) {
			project.setFxsj(StringAndDate_Util.convertToMinute(project.getFxsjstr())); //飞机飞行循环
		}
		if (StringUtils.isNotEmpty(project.getJhksrqstr())) {
			project.setJhksrq(DateUtil.getStr2Date("yyyy-MM-dd", project.getJhksrqstr()));
		}
		if (StringUtils.isNotEmpty(project.getJhjsrqstr())) {
			project.setJhjsrq(DateUtil.getStr2Date("yyyy-MM-dd", project.getJhjsrqstr()));
		}
		if (StringUtils.isNotEmpty(project.getSjksrqstr())) {
			project.setSjksrq(DateUtil.getStr2Date("yyyy-MM-dd", project.getSjksrqstr()));
		}
		if (StringUtils.isNotEmpty(project.getSjjsrqstr())) {
			project.setSjjsrq(DateUtil.getStr2Date("yyyy-MM-dd", project.getSjjsrqstr()));
		}
		if (StringUtils.isNotEmpty(project.getFjzch())) { //保存项目的飞机基本信息：2017.12.7
			ProjectAircraft airdraft = new ProjectAircraft();
			airdraft.setDprtcode(project.getDprtcode());
			airdraft.setFjzch(project.getFjzch());
			List<ProjectAircraft> airs = projectAircraftMapper.findProjectAircraft(airdraft);
			airdraft.setFjms(project.getFjbzm());
			airdraft.setFjjx(project.getFjjx());
			airdraft.setIpcyxxh(project.getIpcyxxh());
			airdraft.setRhyzph(project.getRhyzph());
			airdraft.setWhbmid(user.getBmdm());
			airdraft.setWhrid(user.getId());
			airdraft.setWhsj(new Date());
			airdraft.setXlh(project.getFjxlh()); //序列号
			airdraft.setYyyph(project.getYyyph());
			airdraft.setZt(EffectiveEnum.YES.getId());
			airdraft.setBzm(project.getFjbzm()); //备注名
			if (airs != null && airs.size() > 0) { //更新操作
				projectAircraftMapper.updateProjectAircraft(airdraft);
			} else { //新增操作
				projectAircraftMapper.addProjectAircraft(airdraft);
			}
		}
		if (StringUtils.isEmpty(project.getXmbm())) { //设置项目编号
			String cust = SysContext.PROJECT_CUSTNAME;
			if (cust.toUpperCase().equals("HX")) {
				project.setXmbm(projectCodeRuleableHX.createProjectCode(project));
			} else { //默认为江苏公务机的命名规范
				project.setXmbm(projectCodeRuleableJS.createProjectCode(project));
				SaibongSn sn = saibongSnMapper.getSaibongSn4ProjectCode(project.getDprtcode(), projectCodeRuleableJS.CFKEY);
				if (sn == null) {
					sn = new SaibongSn();
					sn.setCfkey(projectCodeRuleableJS.CFKEY);
					sn.setDprtcode(project.getDprtcode());
					sn.setDjh(project.getXmbm());
					saibongSnMapper.insertSelective(sn);
				} else {
					sn.setDjh(project.getXmbm());
					saibongSnMapper.updateSaibongSn4ProjectCode(sn);
				}
			}
		}
		projectMapper.addProject(project); //保存项目
		commonRecService.write(project.getId(), TableEnum.D_020, user.getId(), czls, LogOperationEnum.SAVE_WO,
				UpdateTypeEnum.SAVE, project.getId());
		List<ProjectMainArea> areas  = project.getProjectMainAreas(); //获得apu & engine
		if (areas != null && areas.size() > 0) {
			for (ProjectMainArea ares : areas) {
				id = UUID.randomUUID().toString();
				ares.setId(id);
				ares.setMainid(project.getId());
				ares.setWhrid(user.getId());
				ares.setWhsj(new Date());
				if (StringUtils.isNotEmpty(ares.getSjzstr())) {
					if (ares.getJkflbh().equals("2T")) {
						ares.setSjz(StringAndDate_Util.convertToMinute(ares.getSjzstr()));
					} else {
						ares.setSjz(Integer.valueOf(ares.getSjzstr()));
					}
				}
				ares.setWhbmid(user.getBmdm());
				ares.setZt(EffectiveEnum.YES.getId());
				projectMainAreaMapper.addProjectMainArea(ares);
				commonRecService.write(id, TableEnum.D_02001, user.getId(), czls, LogOperationEnum.SAVE_WO,
						UpdateTypeEnum.SAVE, id);
			}
		}
		//保存附件信息
		if (project.getAttachments() != null && project.getAttachments().size() > 0) {
			for (Attachment att : project.getAttachments()) {
				UUID uuid = UUID.randomUUID();      //获取随机的uuid
	    		id = uuid.toString();
	    		att.setId(id);
	    		att.setMainid(project.getId());
	    		att.setDprtcode(user.getJgdm());
	    		att.setYxzt(EffectiveEnum.YES.getId());
	    		att.setCzsj(new Date());
	    		att.setCzbmid(user.getBmdm()); //部门
	    		att.setCzrid(user.getId());
	    		attachmentMapper.addFile(att);
	    		commonRecService.write(att.getId(), TableEnum.D_011, user.getId(), czls, LogOperationEnum.SAVE_WO, 
	    				UpdateTypeEnum.SAVE, project.getId());
			}
		}
		return project;
	}

	@Override
	public Project getProjectById(Project project) {
		Project backProject = projectMapper.getProjectById(project); //获得项目主信息
		ProjectMainArea area = new ProjectMainArea();
		area.setMainid(project.getId());
		List<ProjectMainArea> areas = projectMainAreaMapper.getProjectMainArea(area); //获得主部件数据
		if (areas != null && areas.size() > 0) { //对主部件的时间进行转换
			List<ProjectMainArea> newAreas = new ArrayList<ProjectMainArea>();
			for (ProjectMainArea a : areas) {
				if (a.getJkflbh().equals("2T")) {
					if (a.getSjz() != null) {
						a.setSjzstr(StringAndDate_Util.convertToHour(String.valueOf(a.getSjz())));
					}
				}
				newAreas.add(a);
			}
			backProject.setProjectMainAreas(newAreas);
		}
		//获得附件信息
		Attachment paramAttachment = new Attachment();
		paramAttachment.setMainid(project.getId());
		List<Attachment> attachments = attachmentMapper.queryListAttachments(paramAttachment);
		if (attachments != null && attachments.size() > 0) {
			List<Attachment> newAttachments = new ArrayList<Attachment>();
			for (Attachment att : attachments) {
				att.setWjdxStr(Utils.FileUtil.bytes2unitG(att.getWjdx() == null ? 
						0 : att.getWjdx().multiply(BigDecimal.valueOf(1024)).intValue()));
				newAttachments.add(att);
			}
			backProject.setAttachments(newAttachments);
		}
		if (backProject.getWbbs().equals(CustProjectEnum.CUST_TYPE_1.getId())) { //默认为外部客户
			//获得客户信息
			Customer customer = new Customer();
			customer.setId(backProject.getKhid());
			customer = customerMapper.getCustomerById(customer);
			if (customer != null) {
				backProject.setKhbm(customer.getKhbm());
				backProject.setKhmc(customer.getKhmc());
			}
		} else {
			Department dept = departmentMapper.selectByPrimaryKey(backProject.getKhid());
			if (dept != null) {
				backProject.setKhbm(dept.getDprtcode());
				backProject.setKhmc(dept.getDprtname());
			}
		}
		if(backProject.getFxsj() != null) {
			backProject.setFxsjstr(StringAndDate_Util.convertToHour(String.valueOf(backProject.getFxsj())));//分钟转小时
		}
		return backProject;
	}
	
	
	@Override
	public void updateProject(Project project) throws BusinessException {
		try {
			this.updateProjectRealize(project);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Description  更新项目实现方法
	 * @CreateTime 2017-9-27 下午11:27:56
	 * @CreateBy 甘清
	 * @param project 项目信息
	 * @return 
	 * @throws ParseException 时间转换异常
	 * @throws BusinessException
	 */
	private boolean updateProjectRealize(Project project) throws ParseException, BusinessException{
		Project backProject = projectMapper.getProjectById(project); //获得项目主信息
		if (backProject != null) {
			String czls = UUID.randomUUID().toString();  //操作流水号
			User user = ThreadVarUtil.getUser();
			//当前时间
			Date currentDate = commonService.getSysdate();
			project.setWhsj(currentDate);
			project.setWhrid(user.getId());
			project.setWhbmid(user.getBmdm());
			project.setZt(CustProjectEnum.ST_2.getId());
			if (StringUtils.isNotEmpty(project.getFxsjstr()) && !project.getFxsjstr().trim().equals(":")) {
				project.setFxsj(StringAndDate_Util.convertToMinute(project.getFxsjstr()));
			}
			if (StringUtils.isNotEmpty(project.getJhksrqstr())) {
				project.setJhksrq(DateUtil.getStr2Date("yyyy-MM-dd", project.getJhksrqstr()));
			}
			if (StringUtils.isNotEmpty(project.getJhjsrqstr())) {
				project.setJhjsrq(DateUtil.getStr2Date("yyyy-MM-dd", project.getJhjsrqstr()));
			}
			if (StringUtils.isNotEmpty(project.getSjksrqstr())) {
				project.setSjksrq(DateUtil.getStr2Date("yyyy-MM-dd", project.getSjksrqstr()));
			}
			if (StringUtils.isNotEmpty(project.getSjjsrqstr())) {
				project.setSjjsrq(DateUtil.getStr2Date("yyyy-MM-dd", project.getSjjsrqstr()));
			}
			if (StringUtils.isNotEmpty(project.getFjzch())) { //保存项目的飞机基本信息：2017.12.13
				ProjectAircraft airdraft = new ProjectAircraft();
				airdraft.setDprtcode(project.getDprtcode());
				airdraft.setFjzch(project.getFjzch());
				List<ProjectAircraft> airs = projectAircraftMapper.findProjectAircraft(airdraft);
				airdraft.setFjms(project.getFjbzm());
				airdraft.setFjjx(project.getFjjx());
				airdraft.setIpcyxxh(project.getIpcyxxh());
				airdraft.setRhyzph(project.getRhyzph());
				airdraft.setWhbmid(user.getBmdm());
				airdraft.setWhrid(user.getId());
				airdraft.setWhsj(new Date());
				airdraft.setXlh(project.getFjxlh()); //序列号
				airdraft.setYyyph(project.getYyyph());
				airdraft.setZt(EffectiveEnum.YES.getId());
				airdraft.setBzm(project.getFjbzm()); //备注名
				if (airs != null && airs.size() > 0) { //更新操作
					projectAircraftMapper.updateProjectAircraft(airdraft);
				} else { //新增操作
					projectAircraftMapper.addProjectAircraft(airdraft);
				}
			}
			projectMapper.updateProject(project);
			commonRecService.write(project.getId(), TableEnum.D_020, user.getId(), czls, LogOperationEnum.EDIT,
					UpdateTypeEnum.UPDATE, project.getId());
			//保存主要部件信息
			List<ProjectMainArea> areas  = project.getProjectMainAreas(); //获得apu & engine
			if (areas != null && areas.size() > 0) {
				for (ProjectMainArea ares : areas) {
					ProjectMainArea backArea = new ProjectMainArea();
					backArea.setId(backArea.getId());
					List<ProjectMainArea> backAreas = projectMainAreaMapper.getProjectMainArea(backArea);
					if (backAreas == null || backAreas.size() < 1) {
						continue;
					}
					ares.setMainid(project.getId());
					ares.setWhrid(user.getId());
					ares.setWhsj(currentDate);
					if (StringUtils.isNotEmpty(ares.getSjzstr())) {
						if (ares.getJkflbh().equals("2T")) {
							ares.setSjz(StringAndDate_Util.convertToMinute(ares.getSjzstr()));
						} else {
							ares.setSjz(Integer.valueOf(ares.getSjzstr()));
						}
					}
					ares.setWhbmid(user.getBmdm());
					ares.setZt(EffectiveEnum.YES.getId());
					projectMainAreaMapper.updateProjectMainArea(ares);
					commonRecService.write(ares.getId(), TableEnum.D_02001, user.getId(), czls, LogOperationEnum.EDIT,
							UpdateTypeEnum.UPDATE, ares.getId());
				}
			}
			//保存附件信息
			if (project.getAttachments() != null && project.getAttachments().size() > 0) {
				Attachment paramAttachment = new Attachment();
				paramAttachment.setMainid(project.getId());
				List<Attachment> oldAttachments = attachmentMapper.queryListAttachments(paramAttachment); //原始附件
				List<Attachment> oldUIAttachments = new ArrayList<Attachment>(); //前端传过来的原始文件
				for (Attachment att : project.getAttachments()) {
					if (att.getBiaoshi().equals("11")) { //原始的文件
						oldUIAttachments.add(att);
					} else if (att.getBiaoshi().equals("00")){ //新增的文件
						UUID uuid = UUID.randomUUID();      //获取随机的uuid
			    		String id = uuid.toString();
			    		att.setId(id);
			    		att.setMainid(project.getId());
			    		att.setDprtcode(project.getDprtcode());
			    		att.setYxzt(EffectiveEnum.YES.getId());
			    		att.setCzsj(currentDate);
			    		att.setCzbmid(user.getBmdm()); //部门
			    		att.setCzrid(user.getId());
			    		attachmentMapper.addFile(att);
			    		commonRecService.write(att.getId(), TableEnum.D_011, user.getId(), czls, 
			    				LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, project.getId());
					}
				}
				List<Attachment> oldDelAttachments = this.getOldDelAttachments(oldAttachments, oldUIAttachments); //获得差集
				if (oldDelAttachments.size() > 0) {
					for (Attachment delAtt: oldDelAttachments) {
						commonRecService.write(delAtt.getId(), TableEnum.D_011, user.getId(), czls,
								LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, project.getId());
						attachmentMapper.deleteByPrimaryKey(delAtt.getId());
					}
				}
			} else { //删除所有合同
				Attachment paramAttachment = new Attachment();
				paramAttachment.setMainid(project.getId());
				List<Attachment> dels = attachmentMapper.queryListAttachments(paramAttachment);
				if (dels != null && dels.size() > 0) {
					for (Attachment delAtt: dels) {
						commonRecService.write(delAtt.getId(), TableEnum.D_011, user.getId(), czls,
								LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, project.getId());
						attachmentMapper.deleteByPrimaryKey(delAtt.getId());
					}
				}
			}
		} else {
			throw new BusinessException("该项目信息已删除，请刷新后再进行操作!");
		}
		return false;
	}
	
	/**
	 * @Description 获得oldAttachments 中不在oldUIAttachments的内容
	 * @CreateTime 2017-9-28 下午4:55:01
	 * @CreateBy 甘清
	 * @param oldAttachments 全集
	 * @param oldUIAttachments 子集
	 * @return
	 */
	private List<Attachment> getOldDelAttachments(List<Attachment> oldAttachments, List<Attachment> oldUIAttachments) {
		List<Attachment> list = new ArrayList<Attachment>();
		if (oldAttachments != null && oldAttachments.size() > 0) {
			if (oldUIAttachments != null && oldUIAttachments.size() > 0) {
				for (Attachment ba : oldAttachments) {
					boolean flag = true;
					for (Attachment sa : oldUIAttachments) {
						if (ba.getId().equals(sa.getId())) {
							flag = false;
							break;
						}
					}
					if (flag) {
						list.add(ba);
					}
				}
			}
		}
		return list;
	}

	@Override
	public List<Project> getProjectByDprt(String dprtcode) {
		return projectMapper.selectProjectByDprt(dprtcode);
	}

	@Override
	public Map<String, Object> getProjectsList(Project project) {
		PageHelper.startPage(project.getPagination());
		List<Project> list = projectMapper.selectProjectByDprtAndKhid(project);
		return PageUtil.pack4PageHelper(list, project.getPagination());
	}

	@Override
	public Map<String, Object> deleteProject(String id) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Workpackage145 record = new Workpackage145();
		record.setXmid(id);
		List<Workpackage145>  revs = workpackage145Mapper.getRevisonByProjectId(record);
		if (revs != null && revs.size() > 0) {
			resultMap.put("revs", revs);
			return resultMap;
		}
		Project project = new Project();
		project.setId(id);
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		commonRecService.write(id, TableEnum.D_020, user.getId(), czls, LogOperationEnum.DELETE,
				UpdateTypeEnum.DELETE, id);
		//删除合同 & 项目部件信息
		ProjectMainArea proArea = new ProjectMainArea();
		proArea.setMainid(id);
		projectMainAreaMapper.deleteProjectMainAreaByProjectId(proArea);
		projectMapper.deleteProject(project);
		attachmentMapper.deleteByMaind(id);
		resultMap.put("revs", "");
		return resultMap;
	}

	@Override
	public Map<String, Object> getProjectAircraft(ProjectAircraft projectAircraft) {
		Map<String, Object> resultMap = new HashMap<String, Object>(); 
		List<ProjectAircraft> airs = projectAircraftMapper.findProjectAircraft(projectAircraft);
		if (airs != null && airs.size() > 0) {
		    resultMap.put("airs", airs.get(0));
		} else {
			resultMap.put("airs", "");
		}
		return resultMap;
	}
}
