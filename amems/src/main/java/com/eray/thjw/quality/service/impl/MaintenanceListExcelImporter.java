package com.eray.thjw.quality.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.UserMapper;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.dao.MaintenancePersonnelMapper;
import com.eray.thjw.quality.dao.PersonnelToAchievementMapper;
import com.eray.thjw.quality.dao.PersonnelToAuthorizationRecordMapper;
import com.eray.thjw.quality.dao.PersonnelToBusinessAssessmentMapper;
import com.eray.thjw.quality.dao.PersonnelToCreditRecordMapper;
import com.eray.thjw.quality.dao.PersonnelToEducationExperienceMapper;
import com.eray.thjw.quality.dao.PersonnelToForeignLanguageMapper;
import com.eray.thjw.quality.dao.PersonnelToPostMapper;
import com.eray.thjw.quality.dao.PersonnelToProfessionalTitleMapper;
import com.eray.thjw.quality.dao.PersonnelToTechnicalExperienceMapper;
import com.eray.thjw.quality.dao.PersonnelToTechnicalGradeRecordMapper;
import com.eray.thjw.quality.dao.PersonnelToWorkExperienceMapper;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToAchievement;
import com.eray.thjw.quality.po.PersonnelToAuthorizationRecord;
import com.eray.thjw.quality.po.PersonnelToBusinessAssessment;
import com.eray.thjw.quality.po.PersonnelToCreditRecord;
import com.eray.thjw.quality.po.PersonnelToEducationExperience;
import com.eray.thjw.quality.po.PersonnelToForeignLanguage;
import com.eray.thjw.quality.po.PersonnelToPost;
import com.eray.thjw.quality.po.PersonnelToProfessionalTitle;
import com.eray.thjw.quality.po.PersonnelToTechnicalExperience;
import com.eray.thjw.quality.po.PersonnelToTechnicalGradeRecord;
import com.eray.thjw.quality.po.PersonnelToWorkExperience;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.system.dao.LoginMapper;
import com.eray.thjw.training.dao.CourseMapper;
import com.eray.thjw.training.dao.PersonnelRecentTrainingRecordMapper;
import com.eray.thjw.training.dao.PlanPersonMapper;
import com.eray.thjw.training.po.Course;
import com.eray.thjw.training.po.PersonnelRecentTrainingRecord;
import com.eray.thjw.training.po.PlanPerson;
import com.eray.thjw.training.service.CourseService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service("maintenancelistexcelimporter")
public class MaintenanceListExcelImporter extends AbstractExcelImporter<Object>{
	
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private CourseService courseService;
	
	@Resource
	private LoginMapper loginMapper;
	
	/**维修档案人员*/
	@Resource
	private MaintenancePersonnelMapper maintenancePersonnelMapper;
	/** 教育经历 */
	@Resource
	private PersonnelToEducationExperienceMapper personnelToEducationExperienceMapper;
	/** 外语水平 */
	@Resource
	private PersonnelToForeignLanguageMapper personnelToForeignLanguageMapper;
	/** 聘任职称 */
	@Resource
	private PersonnelToProfessionalTitleMapper personnelToProfessionalTitleMapper;
	/** 工作履历 */
	@Resource
	private PersonnelToWorkExperienceMapper personnelToWorkExperienceMapper;
	/** 岗位职务 */
	@Resource
	private PersonnelToPostMapper personnelToPostMapper;
	/** 技术履历 */
	@Resource
	private PersonnelToTechnicalExperienceMapper personnelToTechnicalExperienceMapper;
	/** 维修执照 *//** 机型执照 */
	@Resource
	private PersonnelToAuthorizationRecordMapper personnelToAuthorizationRecordMapper;
	/** 维修技术等级升级记录 */
	@Resource
	private PersonnelToTechnicalGradeRecordMapper personnelToTechnicalGradeRecordMapper;
	/** 培训记录 */
	@Resource
	private PlanPersonMapper planPersonMapper;
	/** 业务考核记录 */
	@Resource
	private PersonnelToBusinessAssessmentMapper personnelToBusinessAssessmentMapper;
	/** 学术成就 */ /** 嘉奖记录 */
	@Resource
	private PersonnelToAchievementMapper personnelToAchievementMapper;
	/** 事故征候情况 *//** 负有责任的不安全事件 *//** 不诚信行为 */
	@Resource
	private PersonnelToCreditRecordMapper personnelToCreditRecordMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private CourseMapper courseMapper;
	@Resource
	private PersonnelRecentTrainingRecordMapper personnelRecentTrainingRecordMapper;
	
	@Override
	public void validateParam(Map<Integer, List<Object>> datasMap)
			throws ExcelImportException {
		List<String> resultList = new ArrayList<String>();
		// 循环工作表
		for (Integer sheetIndex : datasMap.keySet()) {
			// 工作表对应的的装机清单数据
			List<Object> datas = datasMap.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
		
			String temp = "";
			String userName = "";
			String tempName = "";
			if(sheetIndex==0){
				for (int i = 0; i < datas.size(); i++) {
					//人员档案验证
					MaintenancePersonnel maintenancePersonnel =(MaintenancePersonnel) datas.get(i);
					
					resultList.add(maintenancePersonnel.getRybh());
					
					//判空处理
					if(StringUtils.isBlank(maintenancePersonnel.getRybh())){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					
					if(StringUtils.isBlank(maintenancePersonnel.getXm())){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，姓名不能为空");
					}
					if(maintenancePersonnel.getWbbs()==null){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，外部标识不能为空");
					}
//					if(maintenancePersonnel.getCjgzrq()==null){
//						addErrorMessage("人员档案工作表，第"+(i+3)+"行，参加工作时间不能为空");
//					}
//					if(maintenancePersonnel.getRzrq()==null){
//						addErrorMessage("人员档案工作表，第"+(i+3)+"行，入职日期不能为空");
//					}
			
					// 长度验证
					if(Utils.Str.getLength(maintenancePersonnel.getRybh()) > 50){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(maintenancePersonnel.getDabh()) > 50){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，档案编号的最大长度为30");
					}
					if(Utils.Str.getLength(maintenancePersonnel.getXm()) > 100){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，姓名的最大长度为100");
					}
					if(Utils.Str.getLength(maintenancePersonnel.getXl()) > 15){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，学历的最大长度为15");
					}
					if(Utils.Str.getLength(maintenancePersonnel.getJg()) > 100){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，籍贯的最大长度为100");
					}
					if(Utils.Str.getLength(maintenancePersonnel.getZzmm()) > 15){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，政治面貌的最大长度为15");
					}
					if(Utils.Str.getLength(maintenancePersonnel.getLxdh()) > 20){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，联系电话的最大长度为20");
					}
					if(Utils.Str.getLength(maintenancePersonnel.getRzqxx()) > 100){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，入职前信息的最大长度为100");
					}
					if(Utils.Str.getLength(maintenancePersonnel.getDz()) > 100){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，地址的最大长度为100");
					}
					if(Utils.Str.getLength(maintenancePersonnel.getSfz()) > 50){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，身份证号的最大长度为50");
					}
					if(Utils.Str.getLength(maintenancePersonnel.getJtcy()) > 100){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，家庭成员的最大长度为100");
					}
					if(Utils.Str.getLength(maintenancePersonnel.getBz()) > 300){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，备注的最大长度为300");
					}
					
					temp = maintenancePersonnel.getRybh();
					userName = maintenancePersonnel.getUserName();
					tempName = maintenancePersonnel.getXm();
					
				   for (int j = i + 1; j < datas.size(); j++)
		            {
						MaintenancePersonnel maintenancePersonnel1 =(MaintenancePersonnel) datas.get(j);
						if(maintenancePersonnel1 != null && temp != null){
							if (temp.equals(maintenancePersonnel1.getRybh())){
								addErrorMessage("人员档案工作表，第"+(i+3)+"行，人员编号重复");
							}
						}
		            }
				   
				   if (StringUtils.isNotEmpty(userName)) { //不为时校验
					   for (int j = i + 1; j < datas.size(); j++) {
						   MaintenancePersonnel maintenancePersonnel1 =(MaintenancePersonnel) datas.get(j);
						   if(maintenancePersonnel1 != null && temp != null){
							   if (StringUtils.isNotEmpty(maintenancePersonnel1.getUserName()) && 
									   userName.equals(maintenancePersonnel1.getUserName())){
								   addErrorMessage("人员档案工作表，第"+(i+3)+"行，登陆账号重复");
							   }
						   }
					   }
				   }
					
					// 格式验证
					if(!StringUtils.isBlank(maintenancePersonnel.getRybh()) && Utils.Str.isChinese(maintenancePersonnel.getRybh())){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(!StringUtils.isBlank(maintenancePersonnel.getUserName()) && Utils.Str.isChinese(maintenancePersonnel.getUserName())){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，登录账号不能含有中文");
					}
					if(!StringUtils.isBlank(maintenancePersonnel.getDabh()) && Utils.Str.isChinese(maintenancePersonnel.getDabh())){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，档案编号不能含有中文");
					}
					if(maintenancePersonnel.getXb()!=null){
						if(maintenancePersonnel.getXb() != 1 && maintenancePersonnel.getXb() != 2){
							addErrorMessage("人员档案工作表，第"+(i+3)+"行，性别只能为1、2");
						}
					}
					if(maintenancePersonnel.getWbbs()!=null &&maintenancePersonnel.getWbbs() != 1 && maintenancePersonnel.getWbbs() != 2){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，外部标识只能为1、2");
					}
					if(maintenancePersonnel.getSr() != null && maintenancePersonnel.getSr().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，出生日期格式不正确");
					}
					if(maintenancePersonnel.getCjgzrq() != null && maintenancePersonnel.getCjgzrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，参加工作时间格式不正确");
					}
					if(maintenancePersonnel.getRzrq() != null && maintenancePersonnel.getRzrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，入职日期格式不正确");
					}
					if(!StringUtils.isBlank(maintenancePersonnel.getYxdz()) && Utils.Str.isChinese(maintenancePersonnel.getYxdz())){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，邮箱地址不能含有中文");
					}
					if(!StringUtils.isBlank(maintenancePersonnel.getSfz()) && Utils.Str.isChinese(maintenancePersonnel.getSfz())){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，邮箱地址不能含有中文");
					}
					if(maintenancePersonnel.getIsJh()!=null &&maintenancePersonnel.getIsJh() != 1 && maintenancePersonnel.getIsJh() != 0){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，婚姻状况只能为1、0");
					}
		/*			if(maintenancePersonnel.getCxfz()!=null){
						try {
							   BigDecimal bd1=new BigDecimal("100000000");
						       BigDecimal bd2=new BigDecimal("-100000000");
								//类型验证
						       
								if(maintenancePersonnel.getCxfz().compareTo(bd1)==1||maintenancePersonnel.getCxfz().compareTo(bd2)==-1){
									addErrorMessage("人员档案工作表，第"+(i+3)+"行，诚信分值的范围为-9999999.99~9999999.99");
								}
						} catch (Exception e) {
							addErrorMessage("人员档案工作表，第"+(i+3)+"行，诚信分值格式不正确");
						}
					}*/
				}
			}
			if(sheetIndex==1){
				for (int i = 0; i < datas.size(); i++) {
					 /** 教育经历 */
					PersonnelToEducationExperience personnelToEducationExperience =(PersonnelToEducationExperience) datas.get(i);
				
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToEducationExperience.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel MaintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
				
					if(MaintenancePersonnel1==null && resultList.contains(personnelToEducationExperience.getMainid())==false){
						addErrorMessage("教育经历工作表，第"+(i+3)+"行，人员编号不存在");
					}
					
					//判空   
					if(StringUtils.isBlank(personnelToEducationExperience.getMainid())){
						addErrorMessage("教育经历工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					if(personnelToEducationExperience.getKsrq()==null){
						addErrorMessage("教育经历工作表，第"+(i+3)+"行，开始日期不能为空");
					}
					if(StringUtils.isBlank(personnelToEducationExperience.getByxx())){
						addErrorMessage("教育经历工作表，第"+(i+3)+"行，毕业学校不能为空");
					}
					if(StringUtils.isBlank(personnelToEducationExperience.getJyzy())){
						addErrorMessage("教育经历工作表，第"+(i+3)+"行，专业不能为空");
					}
					
					// 长度验证
					if(Utils.Str.getLength(personnelToEducationExperience.getMainid()) > 50){
						addErrorMessage("教育经历工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToEducationExperience.getByxx()) > 100){
						addErrorMessage("教育经历工作表，第"+(i+3)+"行，毕业学校的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToEducationExperience.getJyzy()) > 100){
						addErrorMessage("教育经历工作表，第"+(i+3)+"行，专业的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToEducationExperience.getXz()) > 15){
						addErrorMessage("教育经历工作表，第"+(i+3)+"行，学制的最大长度为15");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(personnelToEducationExperience.getMainid()) && Utils.Str.isChinese(personnelToEducationExperience.getMainid())){
						addErrorMessage("教育经历工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(personnelToEducationExperience.getKsrq() != null && personnelToEducationExperience.getKsrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("教育经历工作表，第"+(i+3)+"行，开始日期格式不正确");
					}
					if(personnelToEducationExperience.getJsrq() != null && personnelToEducationExperience.getJsrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("教育经历工作表，第"+(i+3)+"行，结束日期格式不正确");
					}
				}
			}
			
			if(sheetIndex==2){
				for (int i = 0; i < datas.size(); i++) {
					/** 外语水平 */
					PersonnelToForeignLanguage personnelToForeignLanguage =(PersonnelToForeignLanguage) datas.get(i);
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToForeignLanguage.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					if(maintenancePersonnel1==null && resultList.contains(personnelToForeignLanguage.getMainid())==false){
						addErrorMessage("外语水平工作表，第"+(i+3)+"行，人员编号不存在");
					}
					   
					//判空验证   
					if(StringUtils.isBlank(personnelToForeignLanguage.getMainid())){
						addErrorMessage("外语水平工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					if(personnelToForeignLanguage.getYz()==null){
						addErrorMessage("第外语水平工作表第"+(i+3)+"行，语种不能为空");
					}
					if(personnelToForeignLanguage.getWysp()==null){
						addErrorMessage("外语水平工作表，第"+(i+3)+"行，外语水平不能为空");
					}
					// 长度验证
					if(Utils.Str.getLength(personnelToForeignLanguage.getMainid()) >50){
						addErrorMessage("外语水平工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToForeignLanguage.getYz()) > 15){
						addErrorMessage("外语水平工作表，第"+(i+3)+"行，语种的最大长度为15");
					}
					if(Utils.Str.getLength(personnelToForeignLanguage.getWysp()) > 15){
						addErrorMessage("外语水平工作表，第"+(i+3)+"行，外语水平的最大长度为15");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(personnelToForeignLanguage.getMainid()) && Utils.Str.isChinese(personnelToForeignLanguage.getMainid())){
						addErrorMessage("外语水平工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
				}
			}
			if(sheetIndex==3){
				
				for (int i = 0; i < datas.size(); i++) {
					/**聘任职称*/
					PersonnelToProfessionalTitle personnelToProfessionalTitle =(PersonnelToProfessionalTitle) datas.get(i);
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToProfessionalTitle.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					if(maintenancePersonnel1==null && resultList.contains(personnelToProfessionalTitle.getMainid())==false){
						addErrorMessage("聘任职称工作表，第"+(i+3)+"行，人员编号不存在");
					}
					//判空验证   
					if(StringUtils.isBlank(personnelToProfessionalTitle.getMainid())){
						addErrorMessage("聘任职称工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					if(StringUtils.isBlank(personnelToProfessionalTitle.getZc())){
						addErrorMessage("聘任职称工作表，第"+(i+3)+"行，职称不能为空");
					}
					if(StringUtils.isBlank(personnelToProfessionalTitle.getSqdw())){
						addErrorMessage("聘任职称工作表，第"+(i+3)+"行，授权单位不能为空");
					}
					if(personnelToProfessionalTitle.getPrrq()==null){
						addErrorMessage("聘任职称工作表，第"+(i+3)+"行，聘任日期不能为空");
					}
					
					// 长度验证
					if(Utils.Str.getLength(personnelToProfessionalTitle.getMainid()) > 50){
						addErrorMessage("聘任职称工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToProfessionalTitle.getZc()) > 100){
						addErrorMessage("聘任职称工作表，第"+(i+3)+"行，职称的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToProfessionalTitle.getSqdw()) > 100){
						addErrorMessage("聘任职称工作表，第"+(i+3)+"行，授权的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToProfessionalTitle.getBz()) > 300){
						addErrorMessage("聘任职称工作表，第"+(i+3)+"行，备注的最大长度为300");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(personnelToProfessionalTitle.getMainid()) && Utils.Str.isChinese(personnelToProfessionalTitle.getMainid())){
						addErrorMessage("聘任职称工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(personnelToProfessionalTitle.getPrrq() != null && personnelToProfessionalTitle.getPrrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("聘任职称工作表，第"+(i+3)+"行，聘任日期格式不正确");
					}
					if(personnelToProfessionalTitle.getPrqx() != null && personnelToProfessionalTitle.getPrqx().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("聘任职称工作表，第"+(i+3)+"行，聘任期限格式不正确");
					}
				}
			}
			if(sheetIndex==4){
				for (int i = 0; i < datas.size(); i++) {
				    /** 工作履历 */
					PersonnelToWorkExperience personnelToWorkExperience =(PersonnelToWorkExperience) datas.get(i);
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToWorkExperience.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					if(maintenancePersonnel1==null && resultList.contains(personnelToWorkExperience.getMainid())==false){
						addErrorMessage("工作履历工作表，第"+(i+3)+"行，人员编号不存在");
					}
					
					//判空验证   
					if(StringUtils.isBlank(personnelToWorkExperience.getMainid())){
						addErrorMessage("工作履历工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					if(personnelToWorkExperience.getKsrq()==null){
						addErrorMessage("工作履历工作表，第"+(i+3)+"行，开始日期不能为空");
					}
					if(StringUtils.isBlank(personnelToWorkExperience.getSzdw())){
						addErrorMessage("工作履历工作表，第"+(i+3)+"行，工作单位/部门不能为空");
					}
				
					// 长度验证
					if(Utils.Str.getLength(personnelToWorkExperience.getMainid()) > 50){
						addErrorMessage("工作履历工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToWorkExperience.getSzdw()) > 100){
						addErrorMessage("工作履历工作表，第"+(i+3)+"行，工作单位/部门的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToWorkExperience.getZw()) > 100){
						addErrorMessage("工作履历工作表，第"+(i+3)+"行，职位的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToWorkExperience.getGznr()) > 1000){
						addErrorMessage("工作履历工作表，第"+(i+3)+"行，工作内容的最大长度为1000");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(personnelToWorkExperience.getMainid()) && Utils.Str.isChinese(personnelToWorkExperience.getMainid())){
						addErrorMessage("工作履历工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(personnelToWorkExperience.getKsrq() != null && personnelToWorkExperience.getKsrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("工作履历工作表，第"+(i+3)+"行，开始日期格式不正确");
					}
					if(personnelToWorkExperience.getJsrq() != null && personnelToWorkExperience.getJsrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("工作履历工作表，第"+(i+3)+"行，结束日期限格式不正确");
					}
				}
			}
			if(sheetIndex==5){
				for (int i = 0; i < datas.size(); i++) {
					/** 岗位职务 */
					PersonnelToPost personnelToPost =(PersonnelToPost) datas.get(i);
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToPost.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					if(maintenancePersonnel1==null && resultList.contains(personnelToPost.getMainid())==false){
						addErrorMessage("岗位职务工作表，第"+(i+3)+"行，人员编号不存在");
					}
					//判空验证   
					if(StringUtils.isBlank(personnelToPost.getMainid())){
						addErrorMessage("岗位职务工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					if(StringUtils.isBlank(personnelToPost.getGwzw())){
						addErrorMessage("岗位职务工作表，第"+(i+3)+"行，岗位/职务不能为空");
					}
					if(StringUtils.isBlank(personnelToPost.getDwbm())){
						addErrorMessage("岗位职务工作表，第"+(i+3)+"行，工作单位/部门不能为空");
					}
					if(personnelToPost.getKsrq()==null){
						addErrorMessage("岗位职务工作表，第"+(i+3)+"行，开始日期不能为空");
					}
					
					// 长度验证
					if(Utils.Str.getLength(personnelToPost.getMainid()) > 50){
						addErrorMessage("岗位职务工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToPost.getGwzw()) > 100){
						addErrorMessage("岗位职务工作表，第"+(i+3)+"行，岗位/职务的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToPost.getDwbm()) > 100){
						addErrorMessage("岗位职务工作表，第"+(i+3)+"行，工作单位/部门的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToPost.getGzfw()) > 600){
						addErrorMessage("岗位职务工作表，第"+(i+3)+"行，工作范围的最大长度为600");
					}
					if(Utils.Str.getLength(personnelToPost.getBz()) > 300){
						addErrorMessage("岗位职务工作表，第"+(i+3)+"行，备注的最大长度为300");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(personnelToPost.getMainid()) && Utils.Str.isChinese(personnelToPost.getMainid())){
						addErrorMessage("岗位职务工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(personnelToPost.getKsrq() != null && personnelToPost.getKsrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("岗位职务工作表，第"+(i+3)+"行，开始日期格式不正确");
					}
					if(personnelToPost.getJsrq() != null && personnelToPost.getJsrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("岗位职务工作表，第"+(i+3)+"行，结束日期限格式不正确");
					}
				}
			}
			if(sheetIndex==6){
				for (int i = 0; i < datas.size(); i++) {
					 /** 技术履历 */
					PersonnelToTechnicalExperience personnelToTechnicalExperience =(PersonnelToTechnicalExperience) datas.get(i);
				
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToTechnicalExperience.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					if(maintenancePersonnel1==null && resultList.contains(personnelToTechnicalExperience.getMainid())==false){
						addErrorMessage("技术履历 工作表，第"+(i+3)+"行，人员编号不存在");
					}
					
					//判空验证   
					if(StringUtils.isBlank(personnelToTechnicalExperience.getMainid())){
						addErrorMessage("技术履历工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					if(personnelToTechnicalExperience.getRq()==null){
						addErrorMessage("技术履历工作表，第"+(i+3)+"行，日期不能为空");
					}
					if(StringUtils.isBlank(personnelToTechnicalExperience.getDwbm())){
						addErrorMessage("技术履历工作表，第"+(i+3)+"行，工作单位/部门不能为空");
					}
					
					// 长度验证
					if(Utils.Str.getLength(personnelToTechnicalExperience.getMainid()) > 50){
						addErrorMessage("技术履历工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToTechnicalExperience.getJx()) > 50){
						addErrorMessage("技术履历工作表，第"+(i+3)+"行，机型的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToTechnicalExperience.getDwbm()) > 100){
						addErrorMessage("技术履历工作表，第"+(i+3)+"行，工作单位/部门的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToTechnicalExperience.getZw()) > 100){
						addErrorMessage("技术履历工作表，第"+(i+3)+"行，职务的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToTechnicalExperience.getGzfw()) > 600){
						addErrorMessage("技术履历工作表，第"+(i+3)+"行，工作范围的最大长度为600");
					}
					if(Utils.Str.getLength(personnelToTechnicalExperience.getBz()) > 300){
						addErrorMessage("技术履历工作表，第"+(i+3)+"行，备注的最大长度为300");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(personnelToTechnicalExperience.getMainid()) && Utils.Str.isChinese(personnelToTechnicalExperience.getMainid())){
						addErrorMessage("技术履历工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(personnelToTechnicalExperience.getRq() != null && personnelToTechnicalExperience.getRq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("技术履历工作表，第"+(i+3)+"行，日期格式不正确");
					}
					if(!StringUtils.isBlank(personnelToTechnicalExperience.getJx()) && Utils.Str.isChinese(personnelToTechnicalExperience.getJx())){
						addErrorMessage("技术履历工作表，第"+(i+3)+"行，机型不能含有中文");
					}
				}
			}
			if(sheetIndex==7){
				for (int i = 0; i < datas.size(); i++) {
					/** 维修执照 */
					PersonnelToAuthorizationRecord personnelToAuthorizationRecord =(PersonnelToAuthorizationRecord) datas.get(i);
				
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToAuthorizationRecord.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					if(maintenancePersonnel1==null && resultList.contains(personnelToAuthorizationRecord.getMainid())==false){
						addErrorMessage("维修执照工作表，第"+(i+3)+"行，人员编号不存在");
					}   
					//判空验证   
					if(StringUtils.isBlank(personnelToAuthorizationRecord.getMainid())){
						addErrorMessage("维修执照工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					if(StringUtils.isBlank(personnelToAuthorizationRecord.getBfdw())){
						addErrorMessage("维修执照工作表，第"+(i+3)+"行，颁发单位不能为空");
					}
//					if(StringUtils.isBlank(personnelToAuthorizationRecord.getZy())){
//						addErrorMessage("维修执照工作表，第"+(i+3)+"行，专业不能为空");
//					}
					if(StringUtils.isBlank(personnelToAuthorizationRecord.getZjbh())){
						addErrorMessage("维修执照工作表，第"+(i+3)+"行，执照号不能为空");
					}
					if(personnelToAuthorizationRecord.getBfrq()==null){
						addErrorMessage("维修执照工作表，第"+(i+3)+"行，颁发日期不能为空");
					}
					if(personnelToAuthorizationRecord.getYxqKs()==null){
						addErrorMessage("维修执照工作表，第"+(i+3)+"行，有效期不能为空");
					}
					
					// 长度验证
					if(Utils.Str.getLength(personnelToAuthorizationRecord.getMainid()) > 50){
						addErrorMessage("维修执照工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToAuthorizationRecord.getBfdw()) > 100){
						addErrorMessage("维修执照工作表，第"+(i+3)+"行，颁发单位的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToAuthorizationRecord.getZy()) > 15){
						addErrorMessage("维修执照工作表，第"+(i+3)+"行，专业的最大长度为15");
					}
					if(Utils.Str.getLength(personnelToAuthorizationRecord.getZjbh()) > 50){
						addErrorMessage("维修执照工作表，第"+(i+3)+"行，执照号的最大长度为50");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(personnelToAuthorizationRecord.getMainid()) && Utils.Str.isChinese(personnelToAuthorizationRecord.getMainid())){
						addErrorMessage("维修执照工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(!StringUtils.isBlank(personnelToAuthorizationRecord.getZjbh()) && Utils.Str.isChinese(personnelToAuthorizationRecord.getZjbh())){
						addErrorMessage("维修执照工作表，第"+(i+3)+"行，执照号不能含有中文");
					}
					if(personnelToAuthorizationRecord.getBfrq() != null && personnelToAuthorizationRecord.getBfrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("维修执照工作表，第"+(i+3)+"行，颁发日期格式不正确");
					}
					if(personnelToAuthorizationRecord.getYxqKs() != null && personnelToAuthorizationRecord.getYxqKs().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("维修执照工作表，第"+(i+3)+"行，有效期格式不正确");
					}
				}
			}
			if(sheetIndex==8){
				for (int i = 0; i < datas.size(); i++) {
					 /** 机型信息 */
					PersonnelToAuthorizationRecord personnelToAuthorizationRecord =(PersonnelToAuthorizationRecord) datas.get(i);
				
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToAuthorizationRecord.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					if(maintenancePersonnel1==null && resultList.contains(personnelToAuthorizationRecord.getMainid())==false){
						addErrorMessage("机型执照工作表，第"+(i+3)+"行，人员编号不存在");
					}   
					//判空验证   
					if(StringUtils.isBlank(personnelToAuthorizationRecord.getMainid())){
						addErrorMessage("机型执照工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					if(StringUtils.isBlank(personnelToAuthorizationRecord.getBfdw())){
						addErrorMessage("机型执照工作表，第"+(i+3)+"行，颁发单位不能为空");
					}
					//判空验证   
//					if(StringUtils.isBlank(personnelToAuthorizationRecord.getZy())){
//						addErrorMessage("机型执照工作表，第"+(i+3)+"行，专业不能为空");
//					}
					if(StringUtils.isBlank(personnelToAuthorizationRecord.getFjjx())){
						addErrorMessage("机型执照工作表，第"+(i+3)+"行，机型不能为空");
					}
//					if(personnelToAuthorizationRecord.getBfrq()==null){
//						addErrorMessage("机型执照工作表，第"+(i+3)+"行，颁发日期不能为空");
//					}
//					if(personnelToAuthorizationRecord.getYxqKs()==null){
//						addErrorMessage("机型执照工作表，第"+(i+3)+"行，有效期不能为空");
//					}
					
					// 长度验证
					if(Utils.Str.getLength(personnelToAuthorizationRecord.getMainid()) > 50){
						addErrorMessage("机型执照工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToAuthorizationRecord.getBfdw()) > 100){
						addErrorMessage("机型执照工作表，第"+(i+3)+"行，颁发单位的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToAuthorizationRecord.getFjjx()) > 50){
						addErrorMessage("机型执照工作表，第"+(i+3)+"行，机型的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToAuthorizationRecord.getZy()) > 15){
						addErrorMessage("机型执照工作表，第"+(i+3)+"行，专业的最大长度为15");
					}
					if(Utils.Str.getLength(personnelToAuthorizationRecord.getSqdj()) > 15){
						addErrorMessage("机型执照工作表，第"+(i+3)+"行，维护级别的最大长度为15");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(personnelToAuthorizationRecord.getMainid()) && Utils.Str.isChinese(personnelToAuthorizationRecord.getMainid())){
						addErrorMessage("机型执照工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(!StringUtils.isBlank(personnelToAuthorizationRecord.getFjjx()) && Utils.Str.isChinese(personnelToAuthorizationRecord.getFjjx())){
						addErrorMessage("机型执照工作表，第"+(i+3)+"行，机型不能含有中文");
					}
					
				/*	if(maintenancePersonnel.getRzrq() != null && maintenancePersonnel.getRzrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("人员档案工作表，第"+(i+3)+"行，入职日期格式不正确");
					}*/
					
					if(personnelToAuthorizationRecord.getBfrq() != null && personnelToAuthorizationRecord.getBfrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("机型执照工作表，第"+(i+3)+"行，颁发日期格式不正确");
					}
					if(personnelToAuthorizationRecord.getYxqKs() != null && personnelToAuthorizationRecord.getYxqKs().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("机型执照工作表，第"+(i+3)+"行，有效期格式不正确");
					}
				}
			}
			if(sheetIndex==9){
				for (int i = 0; i < datas.size(); i++) {
					/** 维修技术等级升级记录 */
					PersonnelToTechnicalGradeRecord personnelToTechnicalGradeRecord =(PersonnelToTechnicalGradeRecord) datas.get(i);
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToTechnicalGradeRecord.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					if(maintenancePersonnel1==null && resultList.contains(personnelToTechnicalGradeRecord.getMainid())==false){
						addErrorMessage("维修技术等级工作表，第"+(i+3)+"行，人员编号不存在");
					}
					   
					//判空验证   
					if(StringUtils.isBlank(personnelToTechnicalGradeRecord.getMainid())){
						addErrorMessage("维修技术等级工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					if(StringUtils.isBlank(personnelToTechnicalGradeRecord.getDj())){
						addErrorMessage("维修技术等级工作表，第"+(i+3)+"行，级别不能为空");
					}
					if(personnelToTechnicalGradeRecord.getRq()==null){
						addErrorMessage("维修技术等级工作表，第"+(i+3)+"行，日期不能为空");
					}
					
					// 长度验证
					if(Utils.Str.getLength(personnelToTechnicalGradeRecord.getMainid()) > 50){
						addErrorMessage("维修技术等级工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToTechnicalGradeRecord.getDj()) > 100){
						addErrorMessage("维修技术等级工作表，第"+(i+3)+"行，级别的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToTechnicalGradeRecord.getZy()) > 15){
						addErrorMessage("维修技术等级工作表，第"+(i+3)+"行，专业的最大长度为15");
					}
					if(Utils.Str.getLength(personnelToTechnicalGradeRecord.getJx()) > 50){
						addErrorMessage("维修技术等级工作表，第"+(i+3)+"行，机型的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToTechnicalGradeRecord.getKhcj()) > 15){
						addErrorMessage("维修技术等级工作表，第"+(i+3)+"行，考核成绩的最大长度为15");
					}
					if(Utils.Str.getLength(personnelToTechnicalGradeRecord.getPzr()) > 100){
						addErrorMessage("维修技术等级工作表，第"+(i+3)+"行，批准人的最大长度为100");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(personnelToTechnicalGradeRecord.getMainid()) && Utils.Str.isChinese(personnelToTechnicalGradeRecord.getMainid())){
						addErrorMessage("维修技术等级工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(personnelToTechnicalGradeRecord.getRq() != null && personnelToTechnicalGradeRecord.getRq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("维修技术等级工作表，第"+(i+3)+"行，日期格式不正确");
					}
					if(!StringUtils.isBlank(personnelToTechnicalGradeRecord.getJx()) && Utils.Str.isChinese(personnelToTechnicalGradeRecord.getJx())){
						addErrorMessage("维修技术等级工作表，第"+(i+3)+"行，机型不能含有中文");
					}
				}
			}
			if(sheetIndex==10){
				Map<String, Course> courses = getAllCourse();
				for (int i = 0; i < datas.size(); i++) {
					/** 培训记录 */
					PlanPerson planPerson =(PlanPerson) datas.get(i);
			
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(planPerson.getWxrydaid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel MaintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					if(MaintenancePersonnel1==null && resultList.contains(planPerson.getWxrydaid())==false){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，人员编号不存在");
					}   
					//判空验证   
					if(StringUtils.isBlank(planPerson.getWxrydaid())){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，人员编号不能为空");
					}
//					if(planPerson.getSjKsrq()==null){
//						addErrorMessage("培训记录工作表，第"+(i+3)+"行，课程开始时间不能为空");
//					}
//					if(StringUtils.isBlank(planPerson.getPxgh())){
//						addErrorMessage("培训记录工作表，第"+(i+3)+"行，培训机构不能为空");
//					}
					if(StringUtils.isBlank(planPerson.getKcbm())){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，课程代码不能为空");
					}
					if(StringUtils.isBlank(planPerson.getKcmc()) && StringUtils.isNotBlank(planPerson.getKcbm())){
						if(!courses.containsKey(new StringBuffer().append(planPerson.getKcbm()).append("_").append(StringUtils.isBlank(planPerson.getFjjx())?"-":planPerson.getFjjx()).toString())){
							addErrorMessage("培训记录工作表，第"+(i+3)+"行，课程编号无效，课程名称不能为空");
						}
					}
					if(StringUtils.isBlank(planPerson.getKcmc()) && StringUtils.isBlank(planPerson.getKcbm())){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，课程名称不能为空");
					}
					// 长度验证
					if(Utils.Str.getLength(planPerson.getWxrydaid()) > 50){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(planPerson.getPxlb()) > 15){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，培训类别的最大长度为15");
					}
					if(Utils.Str.getLength(planPerson.getFjjx()) > 50){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，机型的最大长度为50");
					}
					if(Utils.Str.getLength(planPerson.getZy()) > 15){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，专业的最大长度为15");
					}
					if(Utils.Str.getLength(planPerson.getPxgh()) > 100){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，培训机构的最大长度为100");
					}
					if(Utils.Str.getLength(planPerson.getKcbm()) > 50){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，课程编号的最大长度为50");
					}
					if(Utils.Str.getLength(planPerson.getKcmc()) > 100){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，课程名称的最大长度为100");
					}
					if(Utils.Str.getLength(planPerson.getJsxm()) > 100){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，讲师的最大长度为100");
					}
					if(Utils.Str.getLength(planPerson.getSjks()) > 30){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，课时的最大长度为30");
					}
					if(Utils.Str.getLength(planPerson.getPxxs()) > 15){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，培训地点的最大长度为15");
					}
					if(Utils.Str.getLength(planPerson.getCj()) > 15){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，成绩的最大长度为15");
					}
					if(Utils.Str.getLength(planPerson.getZs()) > 100){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，证书编号的最大长度为100");
					}
					if(Utils.Str.getLength(planPerson.getBz()) > 300){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，备注的最大长度为300");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(planPerson.getWxrydaid()) && Utils.Str.isChinese(planPerson.getWxrydaid())){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(!StringUtils.isBlank(planPerson.getFjjx()) && Utils.Str.isChinese(planPerson.getFjjx())){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，机型不能含有中文");
					}
					if(planPerson.getSjKsrq() != null && planPerson.getSjKsrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，课程开始时间格式不正确");
					}
					if(planPerson.getSjJsrq() != null && planPerson.getSjJsrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，课程结束格式不正确");
					}
					if(!StringUtils.isBlank(planPerson.getKcbm()) && Utils.Str.isChinese(planPerson.getKcbm())){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，课程编号不能含有中文");
					}
					if(planPerson.getKhjg()!=null &&planPerson.getKhjg() != 1 && planPerson.getKhjg() != 0){
						addErrorMessage("培训记录工作表，第"+(i+3)+"行，考核结果只能为1、0");
					}
//					if(!StringUtils.isBlank(planPerson.getZs()) && Utils.Str.isChinese(planPerson.getZs())){
//						addErrorMessage("培训记录工作表，第"+(i+3)+"行，证书编号不能含有中文");
//					}
				}
			}
			if(sheetIndex==11){
				for (int i = 0; i < datas.size(); i++) {
					/** 业务考核记录 */
					PersonnelToBusinessAssessment personnelToBusinessAssessment =(PersonnelToBusinessAssessment) datas.get(i);
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToBusinessAssessment.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					if(maintenancePersonnel1==null && resultList.contains(personnelToBusinessAssessment.getMainid())==false){
						addErrorMessage("业务考核工作表，第"+(i+3)+"行，人员编号不存在");
					}
					   
					//判空验证   
					if(StringUtils.isBlank(personnelToBusinessAssessment.getMainid())){
						addErrorMessage("业务考核工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					if(personnelToBusinessAssessment.getRq()==null){
						addErrorMessage("业务考核工作表，第"+(i+3)+"行，日期不能为空");
					}
					if(StringUtils.isBlank(personnelToBusinessAssessment.getJx())){
						addErrorMessage("业务考核工作表，第"+(i+3)+"行，机型不能为空");
					}
					if(StringUtils.isBlank(personnelToBusinessAssessment.getCj())){
						addErrorMessage("业务考核工作表，第"+(i+3)+"行，成绩不能为空");
					}
					if(StringUtils.isBlank(personnelToBusinessAssessment.getKhr())){
						addErrorMessage("业务考核工作表，第"+(i+3)+"行，考核人不能为空");
					}
					
					// 长度验证
					if(Utils.Str.getLength(personnelToBusinessAssessment.getMainid()) > 50){
						addErrorMessage("业务考核工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToBusinessAssessment.getJx()) > 50){
						addErrorMessage("业务考核工作表，第"+(i+3)+"行，机型的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToBusinessAssessment.getZy()) > 15){
						addErrorMessage("业务考核工作表，第"+(i+3)+"行，专业的最大长度为15");
					}
					if(Utils.Str.getLength(personnelToBusinessAssessment.getCj()) > 100){
						addErrorMessage("业务考核工作表，第"+(i+3)+"行，成绩的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToBusinessAssessment.getKhr()) > 100){
						addErrorMessage("业务考核工作表，第"+(i+3)+"行，考核成绩的最大长度为10");
					}
					if(Utils.Str.getLength(personnelToBusinessAssessment.getBz()) > 1000){
						addErrorMessage("业务考核工作表，第"+(i+3)+"行，备注的最大长度为1000");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(personnelToBusinessAssessment.getMainid()) && Utils.Str.isChinese(personnelToBusinessAssessment.getMainid())){
						addErrorMessage("业务考核工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(personnelToBusinessAssessment.getRq() != null && personnelToBusinessAssessment.getRq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("业务考核工作表，第"+(i+3)+"行，日期格式不正确");
					}
					if(!StringUtils.isBlank(personnelToBusinessAssessment.getJx()) && Utils.Str.isChinese(personnelToBusinessAssessment.getJx())){
						addErrorMessage("业务考核工作表，第"+(i+3)+"行，机型不能含有中文");
					}
				}
			}
			if(sheetIndex==12){
				for (int i = 0; i < datas.size(); i++) {
				    /** 学术成就 */
					PersonnelToAchievement personnelToAchievement =(PersonnelToAchievement) datas.get(i);
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToAchievement.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					if(maintenancePersonnel1==null && resultList.contains(personnelToAchievement.getMainid())==false){
						addErrorMessage("学术成就工作表，第"+(i+3)+"行，人员编号不存在");
					}
					//判空验证   
					if(StringUtils.isBlank(personnelToAchievement.getMainid())){
						addErrorMessage("学术成就工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					if(StringUtils.isBlank(personnelToAchievement.getCljg())){
						addErrorMessage("学术成就工作表，第"+(i+3)+"行，成就项目不能为空");
					}
					if(personnelToAchievement.getJcrq()==null){
						addErrorMessage("学术成就工作表，第"+(i+3)+"行，日期不能为空");
					}
					
					// 长度验证
					if(Utils.Str.getLength(personnelToAchievement.getMainid()) > 50){
						addErrorMessage("学术成就工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToAchievement.getCljg()) > 1000){
						addErrorMessage("学术成就工作表，第"+(i+3)+"行，成就项目的最大长度为1000");
					}
					if(Utils.Str.getLength(personnelToAchievement.getSm()) > 1000){
						addErrorMessage("学术成就工作表，第"+(i+3)+"行，证明的最大长度为1000");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(personnelToAchievement.getMainid()) && Utils.Str.isChinese(personnelToAchievement.getMainid())){
						addErrorMessage("学术成就工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(personnelToAchievement.getJcrq() != null && personnelToAchievement.getJcrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("学术成就工作表，第"+(i+3)+"行，日期格式不正确");
					}
				}
			}
			if(sheetIndex==13){
				for (int i = 0; i < datas.size(); i++) {
				    /** 嘉奖记录 */
					PersonnelToAchievement personnelToAchievement =(PersonnelToAchievement) datas.get(i);
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToAchievement.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					if(maintenancePersonnel1==null && resultList.contains(personnelToAchievement.getMainid())==false){
						addErrorMessage("嘉奖记录工作表，第"+(i+3)+"行，人员编号不存在");
					}   
					//判空验证   
					if(StringUtils.isBlank(personnelToAchievement.getMainid())){
						addErrorMessage("嘉奖记录工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					if(StringUtils.isBlank(personnelToAchievement.getCljg())){
						addErrorMessage("嘉奖记录工作表，第"+(i+3)+"行，事件不能为空");
					}
					if(personnelToAchievement.getJcrq()==null){
						addErrorMessage("嘉奖记录工作表，第"+(i+3)+"行，日期不能为空");
					}
					
					// 长度验证
					if(Utils.Str.getLength(personnelToAchievement.getMainid()) > 50){
						addErrorMessage("嘉奖记录工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToAchievement.getCljg()) > 1000){
						addErrorMessage("嘉奖记录工作表，第"+(i+3)+"行，事件的最大长度为1000");
					}
					if(Utils.Str.getLength(personnelToAchievement.getSm()) > 1000){
						addErrorMessage("嘉奖记录工作表，第"+(i+3)+"行，奖励情况的最大长度为1000");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(personnelToAchievement.getMainid()) && Utils.Str.isChinese(personnelToAchievement.getMainid())){
						addErrorMessage("嘉奖记录工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(personnelToAchievement.getJcrq() != null && personnelToAchievement.getJcrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("嘉奖记录工作表，第"+(i+3)+"行，日期格式不正确");
					}
				}
			}
			if(sheetIndex==14){
				for (int i = 0; i < datas.size(); i++) {
				    /** 事故征候 */
					PersonnelToCreditRecord personnelToCreditRecord =(PersonnelToCreditRecord) datas.get(i);
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToCreditRecord.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					if(maintenancePersonnel1==null && resultList.contains(personnelToCreditRecord.getMainid())==false){
						addErrorMessage("事故征候工作表，第"+(i+3)+"行，人员编号不存在");
					}  
					//判空验证   
					if(StringUtils.isBlank(personnelToCreditRecord.getMainid())){
						addErrorMessage("事故征候工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					if(personnelToCreditRecord.getRq()==null){
						addErrorMessage("事故征候工作表，第"+(i+3)+"行，日期不能为空");
					}
					if(StringUtils.isBlank(personnelToCreditRecord.getSjjg())){
						addErrorMessage("事故征候工作表，第"+(i+3)+"行，事故征候不能为空");
					}
					if(StringUtils.isBlank(personnelToCreditRecord.getDcjl())){
						addErrorMessage("事故征候工作表，第"+(i+3)+"行，处分情况不能为空");
					}
					
					// 长度验证
					if(Utils.Str.getLength(personnelToCreditRecord.getMainid()) > 50){
						addErrorMessage("事故征候工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getDd()) > 100){
						addErrorMessage("事故征候工作表，第"+(i+3)+"行，地点的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getSjjg()) > 1000){
						addErrorMessage("事故征候工作表，第"+(i+3)+"行，事故征候的最大长度为1000");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getHg()) > 1000){
						addErrorMessage("事故征候工作表，第"+(i+3)+"行，造成后果的最大长度为1000");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getDcjl()) > 1000){
						addErrorMessage("事故征候工作表，第"+(i+3)+"行，处分情况的最大长度为1000");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(personnelToCreditRecord.getMainid()) && Utils.Str.isChinese(personnelToCreditRecord.getMainid())){
						addErrorMessage("事故征候工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(personnelToCreditRecord.getRq() != null && personnelToCreditRecord.getRq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("事故征候工作表，第"+(i+3)+"行，日期格式不正确");
					}
				}
			}
			if(sheetIndex==15){
				for (int i = 0; i < datas.size(); i++) {
					/** 不安全事件 */
					PersonnelToCreditRecord personnelToCreditRecord =(PersonnelToCreditRecord) datas.get(i);
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToCreditRecord.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					if(maintenancePersonnel1==null && resultList.contains(personnelToCreditRecord.getMainid())==false){
						addErrorMessage("不安全事件工作表，第"+(i+3)+"行，人员编号不存在");
					}  
					//判空验证   
					if(StringUtils.isBlank(personnelToCreditRecord.getMainid())){
						addErrorMessage("不安全事件工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					if(personnelToCreditRecord.getRq()==null){
						addErrorMessage("不安全事件工作表，第"+(i+3)+"行，日期不能为空");
					}
					if(StringUtils.isBlank(personnelToCreditRecord.getDcjl())){
						addErrorMessage("不安全事件工作表，第"+(i+3)+"行，调查结论不能为空");
					}
					
					// 长度验证
					if(Utils.Str.getLength(personnelToCreditRecord.getMainid()) > 50){
						addErrorMessage("不安全事件工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getDd()) > 100){
						addErrorMessage("不安全事件工作表，第"+(i+3)+"行，地点的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getSjry()) > 100){
						addErrorMessage("不安全事件工作表，第"+(i+3)+"行，涉及人员的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getSjjg()) > 1000){
						addErrorMessage("不安全事件工作表，第"+(i+3)+"行，事件经过的最大长度为1000");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getHg()) > 1000){
						addErrorMessage("不安全事件工作表，第"+(i+3)+"行，后果的最大长度为1000");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getDcjl()) > 1000){
						addErrorMessage("不安全事件工作表，第"+(i+3)+"行，调查结论的最大长度为1000");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getBz()) > 300){
						addErrorMessage("不安全事件工作表，第"+(i+3)+"行，备注的最大长度为300");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(personnelToCreditRecord.getMainid()) && Utils.Str.isChinese(personnelToCreditRecord.getMainid())){
						addErrorMessage("不安全事件工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(personnelToCreditRecord.getRq() != null && personnelToCreditRecord.getRq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("不安全事件工作表，第"+(i+3)+"行，日期格式不正确");
					}
					if(personnelToCreditRecord.getKf()!=null){
						try {
						        BigDecimal bd1=new BigDecimal("100000000");
						        BigDecimal bd2=new BigDecimal("0");
								//类型验证
								if(personnelToCreditRecord.getKf().compareTo(bd1)==1||personnelToCreditRecord.getKf().compareTo(bd2)==-1){
									addErrorMessage("不安全事件工作表，第"+(i+3)+"行，扣分的范围为0~9999999.99");
								}
						} catch (Exception e) {
							addErrorMessage("不安全事件工作表，第"+(i+3)+"行，扣分格式不正确");
						}
					}
				}
			}
			if(sheetIndex==16){
				for (int i = 0; i < datas.size(); i++) {
					/** 不诚信行为 */
					PersonnelToCreditRecord personnelToCreditRecord =(PersonnelToCreditRecord) datas.get(i);
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToCreditRecord.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					if(maintenancePersonnel1==null && resultList.contains(personnelToCreditRecord.getMainid())==false){
						addErrorMessage("不诚信行为工作表，第"+(i+3)+"行，人员编号不存在");
					} 
					//判空验证   
					if(StringUtils.isBlank(personnelToCreditRecord.getMainid())){
						addErrorMessage("不诚信行为工作表，第"+(i+3)+"行，人员编号不能为空");
					}
					if(personnelToCreditRecord.getRq()==null){
						addErrorMessage("不诚信行为工作表，第"+(i+3)+"行，日期不能为空");
					}
					if(StringUtils.isBlank(personnelToCreditRecord.getDcjl())){
						addErrorMessage("不诚信行为工作表，第"+(i+3)+"行，调查结论不能为空");
					}
					
					// 长度验证
					if(Utils.Str.getLength(personnelToCreditRecord.getMainid()) > 50){
						addErrorMessage("不诚信行为工作表，第"+(i+3)+"行，人员编号的最大长度为50");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getDd()) > 100){
						addErrorMessage("不诚信行为工作表，第"+(i+3)+"行，地点的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getSjry()) > 100){
						addErrorMessage("不诚信行为工作表，第"+(i+3)+"行，涉及人员的最大长度为100");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getSjjg()) > 1000){
						addErrorMessage("不诚信行为工作表，第"+(i+3)+"行，事件经过的最大长度为1000");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getHg()) > 1000){
						addErrorMessage("不诚信行为工作表，第"+(i+3)+"行，后果的最大长度为1000");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getDcjl()) > 1000){
						addErrorMessage("不诚信行为工作表，第"+(i+3)+"行，调查结论的最大长度为1000");
					}
					if(Utils.Str.getLength(personnelToCreditRecord.getBz()) > 300){
						addErrorMessage("不诚信行为工作表，第"+(i+3)+"行，备注的最大长度为300");
					}
					
					// 格式验证
					if(!StringUtils.isBlank(personnelToCreditRecord.getMainid()) && Utils.Str.isChinese(personnelToCreditRecord.getMainid())){
						addErrorMessage("不诚信行为工作表，第"+(i+3)+"行，人员编号不能含有中文");
					}
					if(personnelToCreditRecord.getRq() != null && personnelToCreditRecord.getRq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("不诚信行为工作表，第"+(i+3)+"行，日期格式不正确");
					}
					if(personnelToCreditRecord.getKf()!=null){
						try {
							   BigDecimal bd1=new BigDecimal("100000000");
						       BigDecimal bd2=new BigDecimal("0");
								//类型验证
								if(personnelToCreditRecord.getKf().compareTo(bd1)==1||personnelToCreditRecord.getKf().compareTo(bd2)==-1){
									addErrorMessage("不诚信行为工作表，第"+(i+3)+"行，扣分的范围为0~9999999.99");
								}
						} catch (Exception e) {
							addErrorMessage("不诚信行为工作表，第"+(i+3)+"行，扣分格式不正确");
						}
					}
				}
			}
		}
	}
	
	
	@Override
	public int writeToDB(Map<Integer, List<Object>> datasMap)
			throws ExcelImportException {
		for (Integer sheetIndex : datasMap.keySet()) {
			// 工作表对应的的装机清单数据
			List<Object> datas = datasMap.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			//人员档案
			if(sheetIndex==0){
				Map<String, User> userMap = getAllUser();
				for (int i = 0; i < datas.size(); i++) {
					MaintenancePersonnel maintenancePersonnel =(MaintenancePersonnel) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					if(StringUtils.isNotBlank(maintenancePersonnel.getUserName())){
						
						if(userMap.containsKey(maintenancePersonnel.getUserName())){	// 当用户存在
							User user = userMap.get(maintenancePersonnel.getUserName());
							maintenancePersonnel.setWxryid(user.getId());
							maintenancePersonnel.setXm(user.getRealname());
							maintenancePersonnel.setWxrbmid(user.getBmdm());
							maintenancePersonnel.setSzdw(String.valueOf(user.getParamsMap().get("dprtname")));
							if(maintenancePersonnel.getSzdw() == null){
								maintenancePersonnel.setSzdw("");
							}
						}
					}
					maintenancePersonnel.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						maintenancePersonnel.setWhbmid("");
					}else{
						maintenancePersonnel.setWhbmid(ThreadVarUtil.getUser().getBmdm());
					}
					maintenancePersonnel.setWhrid(ThreadVarUtil.getUser().getId());
					maintenancePersonnel.setWhsj(new Date());
					int num=maintenancePersonnelMapper.queryCount(maintenancePersonnel);
					if(num>0){
						maintenancePersonnelMapper.updateByPrimaryKeySelectiveStr(maintenancePersonnel);
						commonRecService.write(maintenancePersonnel.getId(), TableEnum.B_Z_001, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,maintenancePersonnel.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						maintenancePersonnel.setId(uuid);
						maintenancePersonnel.setZzzt(1);
						maintenancePersonnel.setIsJh(0);
						if(maintenancePersonnel.getSzdw() == null){
							maintenancePersonnel.setSzdw("");
						}
						maintenancePersonnelMapper.insertSelective(maintenancePersonnel);
						commonRecService.write(maintenancePersonnel.getId(), TableEnum.B_Z_001, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,maintenancePersonnel.getId());
					}
				}
			}
			 /** 教育经历 */
			if(sheetIndex==1){
				Map<String, PersonnelToEducationExperience> edus = getAllEducationExperience();
				for (int i = 0; i < datas.size(); i++) {
					PersonnelToEducationExperience personnelToEducationExperience =(PersonnelToEducationExperience) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToEducationExperience.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel MaintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
				
					personnelToEducationExperience.setMainid(MaintenancePersonnel1.getId());
					personnelToEducationExperience.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						personnelToEducationExperience.setWhbmid("");
					}else{
						personnelToEducationExperience.setWhbmid(ThreadVarUtil.getUser().getBmdm());
					}
					personnelToEducationExperience.setWhrid(ThreadVarUtil.getUser().getId());
					personnelToEducationExperience.setWhsj(new Date());
					String key = maintenancePersonnel.getRybh() + "_" + personnelToEducationExperience.getByxx() + "_" + personnelToEducationExperience.getJyzy();
					if(edus.containsKey(key)){
						personnelToEducationExperience.setId(edus.get(key).getId());
						personnelToEducationExperienceMapper.updateByPrimaryKey(personnelToEducationExperience);
						commonRecService.write(personnelToEducationExperience.getId(), TableEnum.B_Z_00105, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,MaintenancePersonnel1.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						personnelToEducationExperience.setId(uuid);
						personnelToEducationExperienceMapper.insertSelective(personnelToEducationExperience);
						commonRecService.write(personnelToEducationExperience.getId(), TableEnum.B_Z_00105, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,MaintenancePersonnel1.getId());
					}
				}
			}
			/** 外语水平 */
			if(sheetIndex==2){
				Map<String, PersonnelToForeignLanguage> fors = getAllForeignLanguage();
				for (int i = 0; i < datas.size(); i++) {
					PersonnelToForeignLanguage personnelToForeignLanguage =(PersonnelToForeignLanguage) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToForeignLanguage.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					personnelToForeignLanguage.setMainid(maintenancePersonnel1.getId());
					personnelToForeignLanguage.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						personnelToForeignLanguage.setWhbmid("");
					}else{
						personnelToForeignLanguage.setWhbmid(ThreadVarUtil.getUser().getBmdm());
					}
					personnelToForeignLanguage.setWhrid(ThreadVarUtil.getUser().getId());
					personnelToForeignLanguage.setWhsj(new Date());
					
					String key = maintenancePersonnel.getRybh() + "_" + personnelToForeignLanguage.getYz();
					if(fors.containsKey(key)){
						personnelToForeignLanguage.setId(fors.get(key).getId());
						personnelToForeignLanguageMapper.updateByPrimaryKeySelective(personnelToForeignLanguage);
						commonRecService.write(personnelToForeignLanguage.getId(), TableEnum.B_Z_00106, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,maintenancePersonnel1.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						personnelToForeignLanguage.setId(uuid);
						personnelToForeignLanguageMapper.insertSelective(personnelToForeignLanguage);
						commonRecService.write(personnelToForeignLanguage.getId(), TableEnum.B_Z_00106, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,maintenancePersonnel1.getId());
					}
				}
			}
			/** 聘任职称 */
			if(sheetIndex==3){
				Map<String, PersonnelToProfessionalTitle> titles = getAllProfessionalTitle();
				for (int i = 0; i < datas.size(); i++) {
					PersonnelToProfessionalTitle personnelToProfessionalTitle =(PersonnelToProfessionalTitle) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToProfessionalTitle.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					personnelToProfessionalTitle.setMainid(maintenancePersonnel1.getId());
					personnelToProfessionalTitle.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						personnelToProfessionalTitle.setWhbmid("");
					}else{
						personnelToProfessionalTitle.setWhbmid(ThreadVarUtil.getUser().getBmdm());
						
					}
					personnelToProfessionalTitle.setWhrid(ThreadVarUtil.getUser().getId());
					personnelToProfessionalTitle.setWhsj(new Date());
					
					String key = maintenancePersonnel.getRybh() + "_" + personnelToProfessionalTitle.getZc() + "_" + personnelToProfessionalTitle.getSqdw();
					if(titles.containsKey(key)){
						personnelToProfessionalTitle.setId(titles.get(key).getId());
						personnelToProfessionalTitleMapper.updateByPrimaryKeySelective(personnelToProfessionalTitle);
						commonRecService.write(personnelToProfessionalTitle.getId(), TableEnum.B_Z_00108, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,maintenancePersonnel1.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						personnelToProfessionalTitle.setId(uuid);
						personnelToProfessionalTitleMapper.insertSelective(personnelToProfessionalTitle);
						commonRecService.write(personnelToProfessionalTitle.getId(), TableEnum.B_Z_00108, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,maintenancePersonnel1.getId());
					}
				}
			}
			/** 工作履历 */
			if(sheetIndex==4){
				Map<String, PersonnelToWorkExperience> works = getAllWorkExperience();
				for (int i = 0; i < datas.size(); i++) {
					PersonnelToWorkExperience personnelToWorkExperience =(PersonnelToWorkExperience) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToWorkExperience.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					personnelToWorkExperience.setMainid(maintenancePersonnel1.getId());
					personnelToWorkExperience.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						personnelToWorkExperience.setWhbmid("");
					}else{
						personnelToWorkExperience.setWhbmid(ThreadVarUtil.getUser().getBmdm());
						
					}
					personnelToWorkExperience.setWhrid(ThreadVarUtil.getUser().getId());
					personnelToWorkExperience.setWhsj(new Date());
					
					String key = maintenancePersonnel.getRybh() + "_" + personnelToWorkExperience.getSzdw();
					if(works.containsKey(key)){
						personnelToWorkExperience.setId(works.get(key).getId());
						personnelToWorkExperienceMapper.updateByPrimaryKey(personnelToWorkExperience);
						commonRecService.write(personnelToWorkExperience.getId(), TableEnum.B_Z_00103, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,maintenancePersonnel1.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						personnelToWorkExperience.setId(uuid);
						personnelToWorkExperienceMapper.insertSelective(personnelToWorkExperience);
						commonRecService.write(personnelToWorkExperience.getId(), TableEnum.B_Z_00103, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,maintenancePersonnel1.getId());
					}
				}
			}
			/** 岗位职务 */
			if(sheetIndex==5){
				Map<String, PersonnelToPost> posts = getAllPost();
				for (int i = 0; i < datas.size(); i++) {
					PersonnelToPost personnelToPost =(PersonnelToPost) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToPost.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					personnelToPost.setMainid(maintenancePersonnel1.getId());
					personnelToPost.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						personnelToPost.setWhbmid("");
					}else{
						personnelToPost.setWhbmid(ThreadVarUtil.getUser().getBmdm());
						
					}
					personnelToPost.setWhrid(ThreadVarUtil.getUser().getId());
					personnelToPost.setWhsj(new Date());
					
					String key = maintenancePersonnel.getRybh() + "_" + personnelToPost.getGwzw() + "_" + personnelToPost.getDwbm();
					if(posts.containsKey(key)){
						personnelToPost.setId(posts.get(key).getId());
						personnelToPostMapper.updateByPrimaryKey(personnelToPost);
						commonRecService.write(personnelToPost.getId(), TableEnum.B_Z_00109, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,maintenancePersonnel1.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						personnelToPost.setId(uuid);
						personnelToPostMapper.insertSelective(personnelToPost);
						commonRecService.write(personnelToPost.getId(), TableEnum.B_Z_00109, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,maintenancePersonnel1.getId());
					}
				}
			}
			/** 技术履历 */
			if(sheetIndex==6){
				Map<String, PersonnelToTechnicalExperience> tecs = getAllTechnicalExperience();
				for (int i = 0; i < datas.size(); i++) {
					PersonnelToTechnicalExperience personnelToTechnicalExperience =(PersonnelToTechnicalExperience) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToTechnicalExperience.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					personnelToTechnicalExperience.setMainid(maintenancePersonnel1.getId());
					personnelToTechnicalExperience.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						personnelToTechnicalExperience.setWhbmid("");
					}else{
						personnelToTechnicalExperience.setWhbmid(ThreadVarUtil.getUser().getBmdm());
						
					}
					personnelToTechnicalExperience.setWhrid(ThreadVarUtil.getUser().getId());
					personnelToTechnicalExperience.setWhsj(new Date());
					String key = maintenancePersonnel.getRybh() + "_" + 
							DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, personnelToTechnicalExperience.getRq()) + "_" + 
							personnelToTechnicalExperience.getJx() + "_" + 
							personnelToTechnicalExperience.getDwbm() + "_" + 
							personnelToTechnicalExperience.getZw();
					if(tecs.containsKey(key)){
						personnelToTechnicalExperience.setId(tecs.get(key).getId());
						personnelToTechnicalExperienceMapper.updateByPrimaryKeySelective(personnelToTechnicalExperience);
						commonRecService.write(personnelToTechnicalExperience.getId(), TableEnum.B_Z_00110, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,maintenancePersonnel1.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						personnelToTechnicalExperience.setId(uuid);
						personnelToTechnicalExperienceMapper.insertSelective(personnelToTechnicalExperience);
						commonRecService.write(personnelToTechnicalExperience.getId(), TableEnum.B_Z_00110, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,maintenancePersonnel1.getId());
					}
				}
			}
			/** 维修执照 */
			if(sheetIndex==7){
				Map<String, PersonnelToAuthorizationRecord> lics = getAllMaintenanceLicense();
				for (int i = 0; i < datas.size(); i++) {
					PersonnelToAuthorizationRecord personnelToAuthorizationRecord =(PersonnelToAuthorizationRecord) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToAuthorizationRecord.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					personnelToAuthorizationRecord.setMainid(maintenancePersonnel1.getId());
					personnelToAuthorizationRecord.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						personnelToAuthorizationRecord.setWhbmid("");
					}else{
						personnelToAuthorizationRecord.setWhbmid(ThreadVarUtil.getUser().getBmdm());
						
					}
					personnelToAuthorizationRecord.setWhrid(ThreadVarUtil.getUser().getId());
					personnelToAuthorizationRecord.setWhsj(new Date());
					
					String key = maintenancePersonnel.getRybh() + "_" + personnelToAuthorizationRecord.getZy()  + "_" + personnelToAuthorizationRecord.getZjbh();
					if(lics.containsKey(key)){
						personnelToAuthorizationRecord.setId(lics.get(key).getId());
						personnelToAuthorizationRecordMapper.updateByPrimaryKey(personnelToAuthorizationRecord);
						commonRecService.write(personnelToAuthorizationRecord.getId(), TableEnum.B_Z_00101, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,maintenancePersonnel1.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						personnelToAuthorizationRecord.setId(uuid);
						personnelToAuthorizationRecordMapper.insertSelective(personnelToAuthorizationRecord);
						commonRecService.write(personnelToAuthorizationRecord.getId(), TableEnum.B_Z_00101, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,maintenancePersonnel1.getId());
					}
				}
			}
			/** 机型执照 */
			if(sheetIndex==8){
				Map<String, PersonnelToAuthorizationRecord> lics = getAllTypeLicense();
				for (int i = 0; i < datas.size(); i++) {
					PersonnelToAuthorizationRecord personnelToAuthorizationRecord =(PersonnelToAuthorizationRecord) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToAuthorizationRecord.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					personnelToAuthorizationRecord.setMainid(maintenancePersonnel1.getId());
					personnelToAuthorizationRecord.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						personnelToAuthorizationRecord.setWhbmid("");
					}else{
						personnelToAuthorizationRecord.setWhbmid(ThreadVarUtil.getUser().getBmdm());
						
					}
					personnelToAuthorizationRecord.setWhrid(ThreadVarUtil.getUser().getId());
					personnelToAuthorizationRecord.setWhsj(new Date());
					
					String key = maintenancePersonnel.getRybh() + "_" + 
							personnelToAuthorizationRecord.getZy() + "_" + 
							personnelToAuthorizationRecord.getBfdw() + "_" + 
							personnelToAuthorizationRecord.getFjjx() + "_" + 
							personnelToAuthorizationRecord.getZy();
					if(lics.containsKey(key)){
						personnelToAuthorizationRecord.setId(lics.get(key).getId());
						personnelToAuthorizationRecordMapper.updateByPrimaryKey(personnelToAuthorizationRecord);
						commonRecService.write(personnelToAuthorizationRecord.getId(), TableEnum.B_Z_00101, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,maintenancePersonnel1.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						personnelToAuthorizationRecord.setId(uuid);
						personnelToAuthorizationRecordMapper.insertSelective(personnelToAuthorizationRecord);
						commonRecService.write(personnelToAuthorizationRecord.getId(), TableEnum.B_Z_00101, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,maintenancePersonnel1.getId());
					}
				}
			}
			/** 维修技术等级升级记录 */
			if(sheetIndex==9){
				Map<String, PersonnelToTechnicalGradeRecord> grades = getAllTechnicalGradeRecord();
				for (int i = 0; i < datas.size(); i++) {
					PersonnelToTechnicalGradeRecord personnelToTechnicalGradeRecord =(PersonnelToTechnicalGradeRecord) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToTechnicalGradeRecord.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					personnelToTechnicalGradeRecord.setMainid(maintenancePersonnel1.getId());
					personnelToTechnicalGradeRecord.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						personnelToTechnicalGradeRecord.setWhbmid("");
					}else{
						personnelToTechnicalGradeRecord.setWhbmid(ThreadVarUtil.getUser().getBmdm());
						
					}
					personnelToTechnicalGradeRecord.setWhrid(ThreadVarUtil.getUser().getId());
					personnelToTechnicalGradeRecord.setWhsj(new Date());
					
					String key = maintenancePersonnel.getRybh() + "_" + 
								 personnelToTechnicalGradeRecord.getDj() + "_" + 
								 DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, personnelToTechnicalGradeRecord.getRq());
					if(grades.containsKey(key)){
						personnelToTechnicalGradeRecord.setId(grades.get(key).getId());
						personnelToTechnicalGradeRecordMapper.updateByPrimaryKeySelective(personnelToTechnicalGradeRecord);
						commonRecService.write(personnelToTechnicalGradeRecord.getId(), TableEnum.B_Z_00111, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,maintenancePersonnel1.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						personnelToTechnicalGradeRecord.setId(uuid);
						personnelToTechnicalGradeRecordMapper.insertSelective(personnelToTechnicalGradeRecord);
						commonRecService.write(personnelToTechnicalGradeRecord.getId(), TableEnum.B_Z_00111, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,maintenancePersonnel1.getId());
					}
				}
			}
			/** 培训记录 */
			if(sheetIndex==10){
				Map<String, PlanPerson> records = getAllPlanPerson();
				Map<String, Course> courses = getAllCourse();
				for (int i = 0; i < datas.size(); i++) {
					PlanPerson planPerson =(PlanPerson) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(planPerson.getWxrydaid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel MaintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					planPerson.setWxrydaid(MaintenancePersonnel1.getId());
					planPerson.setXm(MaintenancePersonnel1.getXm());
					planPerson.setGzdw(MaintenancePersonnel1.getSzdw());
					planPerson.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						planPerson.setWhbmid("");
					}else{
						planPerson.setWhbmid(ThreadVarUtil.getUser().getBmdm());
					}
					planPerson.setWhrid(ThreadVarUtil.getUser().getId());
					planPerson.setWhsj(new Date());
					if(StringUtils.isNotBlank(planPerson.getKcbm())){
						String kcKey = new StringBuffer().append(planPerson.getKcbm()).append("_").append(StringUtils.isBlank(planPerson.getFjjx())?"-":planPerson.getFjjx()).toString();
						if(courses.containsKey(kcKey)){
							Course course = courses.get(kcKey);
							planPerson.setKcid(course.getId());
							planPerson.setKcmc(course.getKcmc());
							planPerson.setKcnr(course.getKcnr());
							planPerson.setPxxs(course.getPxxs());
							planPerson.setKsxs(course.getKsxs());
							
							// 计算下次培训开始时间
							calcNextTrainingDate(planPerson, course);
						}
					}
					
					String key = maintenancePersonnel.getRybh() + "_" + 
								(planPerson.getSjKsrq() != null ? DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, planPerson.getSjKsrq()) : "") + "_" + 
								 planPerson.getSjKssj() + "_" + 
								 planPerson.getKcbm() + "_" + 
								 planPerson.getFjjx();
					if(records.containsKey(key)){
						planPerson.setId(records.get(key).getId());
						
						
						planPersonMapper.updateByWxryda(planPerson);
						commonRecService.write(planPerson.getId(), TableEnum.B_P_00201, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,planPerson.getWxrydaid());
						
						/**根据培训计划-培训课程人员表编辑最近人员数据-修改**/
						addupdateRecentlyPlanPerson(planPerson);
					}else{
						String uuid = UUID.randomUUID().toString();
						planPerson.setId(uuid);
						planPersonMapper.insertSelective(planPerson);
						commonRecService.write(planPerson.getId(), TableEnum.B_P_00201, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,planPerson.getWxrydaid());
						/**根据培训计划-培训课程人员表编辑最近人员数据-修改**/
						addupdateRecentlyPlanPerson(planPerson);
					}
				}
			}
			/** 业务考核记录 */
			if(sheetIndex==11){
				Map<String, PersonnelToBusinessAssessment> assessments = getAllBusinessAssessment();
				for (int i = 0; i < datas.size(); i++) {
					PersonnelToBusinessAssessment personnelToBusinessAssessment =(PersonnelToBusinessAssessment) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToBusinessAssessment.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					personnelToBusinessAssessment.setMainid(maintenancePersonnel1.getId());
					personnelToBusinessAssessment.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						personnelToBusinessAssessment.setWhbmid("");
					}else{
						personnelToBusinessAssessment.setWhbmid(ThreadVarUtil.getUser().getBmdm());
						
					}
					personnelToBusinessAssessment.setWhrid(ThreadVarUtil.getUser().getId());
					personnelToBusinessAssessment.setWhsj(new Date());
					
					String key = maintenancePersonnel.getRybh() + "_" + 
								 DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, personnelToBusinessAssessment.getRq()) + "_" + 
								 personnelToBusinessAssessment.getJx() + "_" + 
								 personnelToBusinessAssessment.getZy();
					if(assessments.containsKey(key)){
						personnelToBusinessAssessment.setId(assessments.get(key).getId());
						personnelToBusinessAssessmentMapper.updateByPrimaryKeySelective(personnelToBusinessAssessment);
						commonRecService.write(personnelToBusinessAssessment.getId(), TableEnum.B_Z_00107, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,maintenancePersonnel1.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						personnelToBusinessAssessment.setId(uuid);
						personnelToBusinessAssessmentMapper.insertSelective(personnelToBusinessAssessment);
						commonRecService.write(personnelToBusinessAssessment.getId(), TableEnum.B_Z_00107, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,maintenancePersonnel1.getId());
					}
				}
			}
			/** 学术成就 */
			if(sheetIndex==12){
				Map<String, PersonnelToAchievement> ships = getAllScholarship();
				for (int i = 0; i < datas.size(); i++) {
					PersonnelToAchievement personnelToAchievement =(PersonnelToAchievement) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToAchievement.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					personnelToAchievement.setMainid(maintenancePersonnel1.getId());
					personnelToAchievement.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						personnelToAchievement.setWhbmid("");
					}else{
						personnelToAchievement.setWhbmid(ThreadVarUtil.getUser().getBmdm());
						
					}
					personnelToAchievement.setWhrid(ThreadVarUtil.getUser().getId());
					personnelToAchievement.setWhsj(new Date());
					
					String key = maintenancePersonnel.getRybh() + "_" + personnelToAchievement.getCljg() + "_" + DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, personnelToAchievement.getJcrq());
					if(ships.containsKey(key)){
						personnelToAchievement.setId(ships.get(key).getId());
						personnelToAchievementMapper.updateByPrimaryKeySelective(personnelToAchievement);
						commonRecService.write(personnelToAchievement.getId(), TableEnum.B_Z_00102, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,maintenancePersonnel1.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						personnelToAchievement.setId(uuid);
						personnelToAchievementMapper.insertSelective(personnelToAchievement);
						commonRecService.write(personnelToAchievement.getId(), TableEnum.B_Z_00102, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,maintenancePersonnel1.getId());
					}
				}
			}
			/** 嘉奖记录 */
			if(sheetIndex==13){
				Map<String, PersonnelToAchievement> citations = getAllCitation();
				for (int i = 0; i < datas.size(); i++) {
					PersonnelToAchievement personnelToAchievement =(PersonnelToAchievement) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToAchievement.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					personnelToAchievement.setMainid(maintenancePersonnel1.getId());
					personnelToAchievement.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						personnelToAchievement.setWhbmid("");
					}else{
						personnelToAchievement.setWhbmid(ThreadVarUtil.getUser().getBmdm());
						
					}
					personnelToAchievement.setWhrid(ThreadVarUtil.getUser().getId());
					personnelToAchievement.setWhsj(new Date());
					
					String key = maintenancePersonnel.getRybh() + "_" + personnelToAchievement.getCljg() + "_" + DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, personnelToAchievement.getJcrq());
					if(citations.containsKey(key)){
						personnelToAchievement.setId(citations.get(key).getId());
						personnelToAchievementMapper.updateByPrimaryKeySelective(personnelToAchievement);
						commonRecService.write(personnelToAchievement.getId(), TableEnum.B_Z_00102, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,maintenancePersonnel1.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						personnelToAchievement.setId(uuid);
						personnelToAchievementMapper.insertSelective(personnelToAchievement);
						commonRecService.write(personnelToAchievement.getId(), TableEnum.B_Z_00102, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,maintenancePersonnel1.getId());
					}
				}
			}
			/** 事故征候情况 */
			if(sheetIndex==14){
				Map<String, PersonnelToCreditRecord> credits = getAllIncidentSituation();
				for (int i = 0; i < datas.size(); i++) {
					PersonnelToCreditRecord personnelToCreditRecord =(PersonnelToCreditRecord) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToCreditRecord.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					personnelToCreditRecord.setMainid(maintenancePersonnel1.getId());
					personnelToCreditRecord.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						personnelToCreditRecord.setWhbmid("");
					}else{
						personnelToCreditRecord.setWhbmid(ThreadVarUtil.getUser().getBmdm());
						
					}
					personnelToCreditRecord.setWhrid(ThreadVarUtil.getUser().getId());
					personnelToCreditRecord.setWhsj(new Date());
					
					String key = maintenancePersonnel.getRybh() + "_" + DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, personnelToCreditRecord.getRq()) + "_" + personnelToCreditRecord.getSjjg();
					if(credits.containsKey(key)){
						personnelToCreditRecord.setId(credits.get(key).getId());
						personnelToCreditRecordMapper.updateByPrimaryKeySelective(personnelToCreditRecord);
						commonRecService.write(personnelToCreditRecord.getId(), TableEnum.B_Z_00104, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,maintenancePersonnel1.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						personnelToCreditRecord.setId(uuid);
						personnelToCreditRecordMapper.insertSelective(personnelToCreditRecord);
						commonRecService.write(personnelToCreditRecord.getId(), TableEnum.B_Z_00104, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,maintenancePersonnel1.getId());
					}
				}
			}
			/** 负有责任的不安全事件 */
			if(sheetIndex==15){
				Map<String, PersonnelToCreditRecord> incidents = getAllUnsafeIncident();
				for (int i = 0; i < datas.size(); i++) {
					PersonnelToCreditRecord personnelToCreditRecord =(PersonnelToCreditRecord) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToCreditRecord.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					personnelToCreditRecord.setMainid(maintenancePersonnel1.getId());
					personnelToCreditRecord.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						personnelToCreditRecord.setWhbmid("");
					}else{
						personnelToCreditRecord.setWhbmid(ThreadVarUtil.getUser().getBmdm());
						
					}
					personnelToCreditRecord.setWhrid(ThreadVarUtil.getUser().getId());
					personnelToCreditRecord.setWhsj(new Date());
					
					String key = maintenancePersonnel.getRybh() + "_" + DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, personnelToCreditRecord.getRq()) + "_" + personnelToCreditRecord.getDcjl();
					if(incidents.containsKey(key)){
						personnelToCreditRecord.setId(incidents.get(key).getId());
						personnelToCreditRecordMapper.updateByPrimaryKeySelective(personnelToCreditRecord);
						commonRecService.write(personnelToCreditRecord.getId(), TableEnum.B_Z_00104, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,maintenancePersonnel1.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						personnelToCreditRecord.setId(uuid);
						personnelToCreditRecordMapper.insertSelective(personnelToCreditRecord);
						commonRecService.write(personnelToCreditRecord.getId(), TableEnum.B_Z_00104, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,maintenancePersonnel1.getId());
					}
				}
			}
			/** 不诚信行为 */
			if(sheetIndex==16){
				Map<String, PersonnelToCreditRecord> records = getAllDishonestBehaviors();
				for (int i = 0; i < datas.size(); i++) {
					PersonnelToCreditRecord personnelToCreditRecord =(PersonnelToCreditRecord) datas.get(i);
					// 写入日志
					String czls = UUID.randomUUID().toString();
					
					MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
					maintenancePersonnel.setRybh(personnelToCreditRecord.getMainid());
					maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					MaintenancePersonnel maintenancePersonnel1=maintenancePersonnelMapper.queryAllRybh(maintenancePersonnel);
					
					personnelToCreditRecord.setMainid(maintenancePersonnel1.getId());
					personnelToCreditRecord.setZt(1);
					if(ThreadVarUtil.getUser().getBmdm()==null){
						personnelToCreditRecord.setWhbmid("");
					}else{
						personnelToCreditRecord.setWhbmid(ThreadVarUtil.getUser().getBmdm());
						
					}
					personnelToCreditRecord.setWhrid(ThreadVarUtil.getUser().getId());
					personnelToCreditRecord.setWhsj(new Date());
					
					String key = maintenancePersonnel.getRybh() + "_" + DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, personnelToCreditRecord.getRq()) + "_" + personnelToCreditRecord.getDcjl();
					if(records.containsKey(key)){
						personnelToCreditRecord.setId(records.get(key).getId());
						personnelToCreditRecordMapper.updateByPrimaryKeySelective(personnelToCreditRecord);
						commonRecService.write(personnelToCreditRecord.getId(), TableEnum.B_Z_00104, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,maintenancePersonnel1.getId());
					}else{
						String uuid = UUID.randomUUID().toString();
						personnelToCreditRecord.setId(uuid);
						personnelToCreditRecordMapper.insertSelective(personnelToCreditRecord);
						commonRecService.write(personnelToCreditRecord.getId(), TableEnum.B_Z_00104, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,maintenancePersonnel1.getId());
					}
				}
			}
		}
		return 1;
	}
	@Override
	public Map<Integer, List<Object>> convertBean(Map<Integer, List<Map<Integer, String>>> totalMapList) throws ExcelImportException {
				// 结果集
				Map<Integer, List<Object>> resultMap = new TreeMap<Integer, List<Object>>();
				// 循环sheet
				for (Integer sheetIndex : totalMapList.keySet()) {
					// sheet对应装机清单数据
					List<Map<Integer, String>> mapList = totalMapList.get(sheetIndex);
					List<Object> list = new ArrayList<Object>();
					Map<Integer, String> bean;
					
					if(sheetIndex==0){
						for (int i = 0; i < mapList.size(); i++) {
							MaintenancePersonnel maintenancePersonnel = new MaintenancePersonnel();
							bean = mapList.get(i);
							/** 维修档案人员主表 */
							maintenancePersonnel.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							maintenancePersonnel.setRybh(bean.get(0)); 
							maintenancePersonnel.setUserName(bean.get(1));
							maintenancePersonnel.setDabh(bean.get(2));
							maintenancePersonnel.setXm(bean.get(3));
							maintenancePersonnel.setXb(convertToInteger(bean.get(4),1));
							maintenancePersonnel.setXl(bean.get(5));
							maintenancePersonnel.setWbbs(convertToInteger(bean.get(6), 1));
							maintenancePersonnel.setSr(convertToDate(dealTime(bean.get(7))));
							maintenancePersonnel.setJg(bean.get(8));
							maintenancePersonnel.setZzmm(bean.get(9));
							maintenancePersonnel.setLxdh(bean.get(10));
							maintenancePersonnel.setCjgzrq(convertToDate(dealTime(bean.get(11))));
							maintenancePersonnel.setRzrq(convertToDate(dealTime(bean.get(12))));
							maintenancePersonnel.setRzqxx(bean.get(13));
							maintenancePersonnel.setYxdz(bean.get(14));
							maintenancePersonnel.setDz(bean.get(15));
							maintenancePersonnel.setSfz(bean.get(16));
							maintenancePersonnel.setIsJh(convertToInteger(bean.get(17)));
							maintenancePersonnel.setJtcy(bean.get(18));
							/*if (bean.get(19) != null && !bean.get(19).equals("")) {
								maintenancePersonnel.setCxfz(convertToBigDecimal(bean.get(19)));
							}*/
							maintenancePersonnel.setBz(bean.get(19));
							list.add(maintenancePersonnel);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==1){
						for (int i = 0; i < mapList.size(); i++) {
							PersonnelToEducationExperience personnelToEducationExperience = new PersonnelToEducationExperience();
							bean = mapList.get(i);
							/** 教育经历 */
							personnelToEducationExperience.setMainid(bean.get(0));
							personnelToEducationExperience.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							personnelToEducationExperience.setKsrq(convertToDate(bean.get(1)));
							personnelToEducationExperience.setJsrq(convertToDate(bean.get(2)));
							personnelToEducationExperience.setByxx(bean.get(3));
							personnelToEducationExperience.setJyzy(bean.get(4));
							personnelToEducationExperience.setXz(bean.get(5));
							list.add(personnelToEducationExperience);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==2){
						for (int i = 0; i < mapList.size(); i++) {
							PersonnelToForeignLanguage personnelToForeignLanguage = new PersonnelToForeignLanguage();
							bean = mapList.get(i);
							/** 档案-外语水平 */
							personnelToForeignLanguage.setMainid(bean.get(0));
							personnelToForeignLanguage.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							personnelToForeignLanguage.setYz(bean.get(1));
							personnelToForeignLanguage.setWysp(bean.get(2));
							list.add(personnelToForeignLanguage);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==3){
						for (int i = 0; i < mapList.size(); i++) {
							PersonnelToProfessionalTitle personnelToProfessionalTitle = new PersonnelToProfessionalTitle();
							bean = mapList.get(i);
							/** 档案-聘任职称 */
							personnelToProfessionalTitle.setMainid(bean.get(0));
							personnelToProfessionalTitle.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							personnelToProfessionalTitle.setZc(bean.get(1));
							personnelToProfessionalTitle.setSqdw(bean.get(2));
							personnelToProfessionalTitle.setPrrq(convertToDate(bean.get(3)));
							personnelToProfessionalTitle.setPrqx(convertToDate(bean.get(4)));
							personnelToProfessionalTitle.setBz(bean.get(5));
							list.add(personnelToProfessionalTitle);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==4){
						for (int i = 0; i < mapList.size(); i++) {
							PersonnelToWorkExperience personnelToWorkExperience = new PersonnelToWorkExperience();
							bean = mapList.get(i);
							/** 档案-工作履历 */
							personnelToWorkExperience.setMainid(bean.get(0));
							personnelToWorkExperience.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							personnelToWorkExperience.setKsrq(convertToDate(bean.get(1)));
							personnelToWorkExperience.setJsrq(convertToDate(bean.get(2)));
							personnelToWorkExperience.setSzdw(bean.get(3));
							personnelToWorkExperience.setZw(bean.get(4));
							personnelToWorkExperience.setGznr(bean.get(5));
							list.add(personnelToWorkExperience);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==5){
						for (int i = 0; i < mapList.size(); i++) {
							PersonnelToPost personnelToPost = new PersonnelToPost();
							bean = mapList.get(i);
							/** 档案-岗位职务 */
							personnelToPost.setMainid(bean.get(0));
							personnelToPost.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							personnelToPost.setGwzw(bean.get(1));
							personnelToPost.setDwbm(bean.get(2));
							personnelToPost.setKsrq(convertToDate(bean.get(3)));
							personnelToPost.setJsrq(convertToDate(bean.get(4)));
							personnelToPost.setGzfw(bean.get(5));
							personnelToPost.setBz(bean.get(6));
							
							list.add(personnelToPost);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==6){
						for (int i = 0; i < mapList.size(); i++) {
							PersonnelToTechnicalExperience personnelToTechnicalExperience = new PersonnelToTechnicalExperience();
							bean = mapList.get(i);
							/** 技术履历 */
							personnelToTechnicalExperience.setMainid(bean.get(0));
							personnelToTechnicalExperience.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							personnelToTechnicalExperience.setRq(convertToDate(bean.get(1)));
							personnelToTechnicalExperience.setJx(bean.get(2));
							personnelToTechnicalExperience.setDwbm(bean.get(3));
							personnelToTechnicalExperience.setZw(bean.get(4));
							personnelToTechnicalExperience.setGzfw(bean.get(5));
							personnelToTechnicalExperience.setBz(bean.get(6));
							list.add(personnelToTechnicalExperience);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==7){
						for (int i = 0; i < mapList.size(); i++) {
							PersonnelToAuthorizationRecord personnelToAuthorizationRecord = new PersonnelToAuthorizationRecord();
							bean = mapList.get(i);
							/** 维修执照 */
							personnelToAuthorizationRecord.setMainid(bean.get(0));
							personnelToAuthorizationRecord.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							personnelToAuthorizationRecord.setXxlx(21);
							personnelToAuthorizationRecord.setBfdw(bean.get(1));
							personnelToAuthorizationRecord.setZy(bean.get(2));
							personnelToAuthorizationRecord.setZjbh(bean.get(3));
							personnelToAuthorizationRecord.setBfrq(convertToDate(bean.get(4)));
							personnelToAuthorizationRecord.setYxqKs(convertToDate(bean.get(4)));
							personnelToAuthorizationRecord.setYxqJs(convertToDate(bean.get(5)));
							list.add(personnelToAuthorizationRecord);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==8){
						for (int i = 0; i < mapList.size(); i++) {
							PersonnelToAuthorizationRecord personnelToAuthorizationRecord = new PersonnelToAuthorizationRecord();
							bean = mapList.get(i);
							/** 机型信息 */
							personnelToAuthorizationRecord.setMainid(bean.get(0));
							personnelToAuthorizationRecord.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							personnelToAuthorizationRecord.setXxlx(22);
							personnelToAuthorizationRecord.setBfdw(bean.get(1));
							personnelToAuthorizationRecord.setFjjx(bean.get(2));
							personnelToAuthorizationRecord.setZy(bean.get(3));
							personnelToAuthorizationRecord.setZjbh(bean.get(4));
							personnelToAuthorizationRecord.setBfrq(convertToDate(bean.get(5)));
							personnelToAuthorizationRecord.setYxqKs(convertToDate(bean.get(5)));
							personnelToAuthorizationRecord.setYxqJs(convertToDate(bean.get(6)));
							list.add(personnelToAuthorizationRecord);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==9){
						for (int i = 0; i < mapList.size(); i++) {
							PersonnelToTechnicalGradeRecord personnelToTechnicalGradeRecord = new PersonnelToTechnicalGradeRecord();
							bean = mapList.get(i);
							/** 维修技术等级升级记录 */
							personnelToTechnicalGradeRecord.setMainid(bean.get(0));
							personnelToTechnicalGradeRecord.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							personnelToTechnicalGradeRecord.setDj(bean.get(1));
							personnelToTechnicalGradeRecord.setRq(convertToDate(bean.get(2)));
							personnelToTechnicalGradeRecord.setZy(bean.get(3));
							personnelToTechnicalGradeRecord.setJx(bean.get(4));
							personnelToTechnicalGradeRecord.setKhcj(bean.get(5));
							personnelToTechnicalGradeRecord.setPzr(bean.get(6));
							list.add(personnelToTechnicalGradeRecord);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==10){
						for (int i = 0; i < mapList.size(); i++) {
							PlanPerson planPerson = new PlanPerson();
							bean = mapList.get(i);
							/** 培训记录 */
							planPerson.setWxrydaid(bean.get(0));
							planPerson.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							planPerson.setPxlb(bean.get(1));
							planPerson.setFjjx(bean.get(2));
							planPerson.setZy(bean.get(3));
							
							SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
							if(bean.get(4).trim().contains(" ")){
								String dateString = formatter.format(convertToDates(bean.get(4)));
								planPerson.setSjKssj(dateString);
							}
							
							planPerson.setSjKsrq(convertToDate(bean.get(4)));
							
							if(convertToDates(bean.get(5))!=null){
								planPerson.setSjJsrq(convertToDate(bean.get(5)));
								if(convertToDates(bean.get(5))!=null){
									String dateString5 = formatter.format(convertToDates(bean.get(5)));
									planPerson.setSjJssj(dateString5);
								}
							}
							
							planPerson.setPxgh(bean.get(6));
							planPerson.setKcdd(bean.get(7));
							planPerson.setKcbm(bean.get(8));
							planPerson.setKcmc(bean.get(9));
							if (bean.get(10) != null ) {
								planPerson.setFxbs(convertToInteger(bean.get(10)));
							}else{
								planPerson.setFxbs(1);
							}
							planPerson.setJsxm(bean.get(11));
							planPerson.setSjks(bean.get(12));
							planPerson.setPxxs(bean.get(13));
							planPerson.setCj(bean.get(14));
							
							if (bean.get(15) != null ) {
								planPerson.setKhjg(convertToInteger(bean.get(15)));
							}else{
								planPerson.setKhjg(1);
							}
							
							planPerson.setZs(bean.get(16));
							planPerson.setBz(bean.get(17));
							planPerson.setIsSc(1);
							planPerson.setIsYc(1);
							planPerson.setCybs(0);
							planPerson.setKq(1);
							planPerson.setJszt(1);
							list.add(planPerson);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==11){
						for (int i = 0; i < mapList.size(); i++) {
							PersonnelToBusinessAssessment personnelToBusinessAssessment = new PersonnelToBusinessAssessment();
							bean = mapList.get(i);
							/** 业务考核记录 */
							personnelToBusinessAssessment.setMainid(bean.get(0));
							personnelToBusinessAssessment.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							personnelToBusinessAssessment.setRq(convertToDate(bean.get(1)));
							personnelToBusinessAssessment.setJx(bean.get(2));
							personnelToBusinessAssessment.setZy(bean.get(3));
							personnelToBusinessAssessment.setCj(bean.get(4));
							personnelToBusinessAssessment.setKhr(bean.get(5));
							personnelToBusinessAssessment.setBz(bean.get(6));
							list.add(personnelToBusinessAssessment);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==12){
						for (int i = 0; i < mapList.size(); i++) {
							PersonnelToAchievement personnelToAchievement = new PersonnelToAchievement();
							bean = mapList.get(i);
							/** 学术成就 */
							personnelToAchievement.setMainid(bean.get(0));
							personnelToAchievement.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							personnelToAchievement.setLx(1);
							personnelToAchievement.setCljg(bean.get(1));
							personnelToAchievement.setJcrq(convertToDate(bean.get(2)));
							personnelToAchievement.setSm(bean.get(3));
							list.add(personnelToAchievement);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==13){
						for (int i = 0; i < mapList.size(); i++) {
							PersonnelToAchievement personnelToAchievement = new PersonnelToAchievement();
							bean = mapList.get(i);
							/** 嘉奖记录 */
							personnelToAchievement.setMainid(bean.get(0));
							personnelToAchievement.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							personnelToAchievement.setLx(2);
							personnelToAchievement.setCljg(bean.get(1));
							personnelToAchievement.setJcrq(convertToDate(bean.get(2)));
							personnelToAchievement.setSm(bean.get(3));
							list.add(personnelToAchievement);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==14){
						for (int i = 0; i < mapList.size(); i++) {
							PersonnelToCreditRecord personnelToCreditRecord = new PersonnelToCreditRecord();
							bean = mapList.get(i);
							/** 事故征候情况 */
							personnelToCreditRecord.setMainid(bean.get(0));
							personnelToCreditRecord.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							personnelToCreditRecord.setLx(3);
							personnelToCreditRecord.setRq(convertToDate(bean.get(1)));
							personnelToCreditRecord.setDd(bean.get(2));
							personnelToCreditRecord.setSjjg(bean.get(3));
							personnelToCreditRecord.setHg(bean.get(4));
							personnelToCreditRecord.setDcjl(bean.get(5));
							list.add(personnelToCreditRecord);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==15){
						for (int i = 0; i < mapList.size(); i++) {
							PersonnelToCreditRecord personnelToCreditRecord = new PersonnelToCreditRecord();
							bean = mapList.get(i);
							/** 负有责任的不安全事件 */
							personnelToCreditRecord.setMainid(bean.get(0));
							personnelToCreditRecord.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							personnelToCreditRecord.setLx(1);
							personnelToCreditRecord.setRq(convertToDate(bean.get(1)));
							personnelToCreditRecord.setDd(bean.get(2));
							personnelToCreditRecord.setSjry(bean.get(3));
							personnelToCreditRecord.setSjjg(bean.get(4));
							personnelToCreditRecord.setHg(bean.get(5));
							personnelToCreditRecord.setDcjl(bean.get(6));
							if (bean.get(7) != null ) {
								personnelToCreditRecord.setKf(convertToBigDecimal(bean.get(7)));
							}
							personnelToCreditRecord.setBz(bean.get(8));
							list.add(personnelToCreditRecord);
							resultMap.put(sheetIndex, list);
						}
					}
					if(sheetIndex==16){
						for (int i = 0; i < mapList.size(); i++) {
							PersonnelToCreditRecord personnelToCreditRecord = new PersonnelToCreditRecord();
							bean = mapList.get(i);
							/** 不诚信行为 */
							personnelToCreditRecord.setMainid(bean.get(0));
							personnelToCreditRecord.setDprtcode(ThreadVarUtil.getUser().getJgdm());
							personnelToCreditRecord.setLx(2);
							personnelToCreditRecord.setRq(convertToDate(bean.get(1)));
							personnelToCreditRecord.setDd(bean.get(2));
							personnelToCreditRecord.setSjry(bean.get(3));
							personnelToCreditRecord.setSjjg(bean.get(4));
							personnelToCreditRecord.setHg(bean.get(5));
							personnelToCreditRecord.setDcjl(bean.get(6));
							if (bean.get(7) != null ) {
								personnelToCreditRecord.setKf(convertToBigDecimal(bean.get(7)));
							}
							personnelToCreditRecord.setBz(bean.get(8));
							list.add(personnelToCreditRecord);
							resultMap.put(sheetIndex, list);
						}
					}
				}
				return resultMap;
	}
	
	private String dealTime(String str) {
		if(StringUtils.isBlank(str))
			return null;
		if(str.contains(".")&&str.length()==7){
			str+=".01";
		}else if(str.contains("-")&&str.length()==7){
			str+="-01";
		}else if(str.length()==6){
			str+="01";
		}
		return str;
	}


	/**
	 * @Description 计算下次培训开始时间
	 * @CreateTime 2018年1月12日 上午9:47:52
	 * @CreateBy 韩武
	 * @param planPerson
	 */
	private void calcNextTrainingDate(PlanPerson planPerson, Course course){
		
		Date ksrq = planPerson.getSjKsrq();
		
		String num = null;
		
		try {
			if(new Integer(1).equals(course.getZqdw())){//天
				num = DateUtil.getOffsetDate(ksrq, course.getZqz(),Calendar.DATE);
			}else if(new Integer(2).equals(course.getZqdw())){//月
				num = DateUtil.getOffsetDate(ksrq, course.getZqz(),Calendar.MONTH);
			}else if(new Integer(3).equals(course.getZqdw())){//年
				num = DateUtil.getOffsetDate(ksrq, course.getZqz()*12,Calendar.MONTH);
			} 
		
			if(num != null){
				planPerson.setXcpxrq(DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, num));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description 获取所有用户
	 * @CreateTime 2018年1月11日 下午3:14:38
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, User> getAllUser(){
		User param = new User();
		param.setJgdm(ThreadVarUtil.getUser().getJgdm());
		List<User> list = userMapper.queryUserAllByDprtcode(param);
		
		Map<String, User> resultMap = new HashMap<String, User>();
		for (User user : list) {
			resultMap.put(user.getUsername(), user);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有课程
	 * @CreateTime 2018年1月12日 上午9:42:14
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, Course> getAllCourse(){
		List<Course> list = courseMapper.selectALLCourse(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, Course> resultMap = new HashMap<String, Course>();
		for (Course course : list) {
			
			resultMap.put(new StringBuffer().append(course.getKcbh()).append("_").append(StringUtils.isBlank(course.getFjjx())?"-":course.getFjjx()).toString(), course);
		}
		return resultMap;
	}
	
	
	/**
	 * @Description 获取所有教育经历
	 * @CreateTime 2018年1月12日 上午10:37:47
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PersonnelToEducationExperience> getAllEducationExperience(){
		List<PersonnelToEducationExperience> list = 
				personnelToEducationExperienceMapper.queryByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PersonnelToEducationExperience> resultMap = new HashMap<String, PersonnelToEducationExperience>();
		for (PersonnelToEducationExperience record : list) {
			String key = record.getRybh() + "_" + record.getByxx() + "_" + record.getJyzy();
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有外语水平
	 * @CreateTime 2018年1月12日 上午10:38:01
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PersonnelToForeignLanguage> getAllForeignLanguage(){
		List<PersonnelToForeignLanguage> list = 
				personnelToForeignLanguageMapper.queryByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PersonnelToForeignLanguage> resultMap = new HashMap<String, PersonnelToForeignLanguage>();
		for (PersonnelToForeignLanguage record : list) {
			String key = record.getRybh() + "_" + record.getYz();
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有聘任职称
	 * @CreateTime 2018年1月12日 上午10:38:20
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PersonnelToProfessionalTitle> getAllProfessionalTitle(){
		List<PersonnelToProfessionalTitle> list = 
				personnelToProfessionalTitleMapper.queryByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PersonnelToProfessionalTitle> resultMap = new HashMap<String, PersonnelToProfessionalTitle>();
		for (PersonnelToProfessionalTitle record : list) {
			String key = record.getRybh() + "_" + record.getZc() + "_" + record.getSqdw();
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有工作经验
	 * @CreateTime 2018年1月12日 上午10:38:31
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PersonnelToWorkExperience> getAllWorkExperience(){
		List<PersonnelToWorkExperience> list = 
				personnelToWorkExperienceMapper.queryByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PersonnelToWorkExperience> resultMap = new HashMap<String, PersonnelToWorkExperience>();
		for (PersonnelToWorkExperience record : list) {
			String key = record.getRybh() + "_" + record.getSzdw();
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有岗位/职务
	 * @CreateTime 2018年1月12日 上午10:38:40
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PersonnelToPost> getAllPost(){
		List<PersonnelToPost> list = 
				personnelToPostMapper.queryByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PersonnelToPost> resultMap = new HashMap<String, PersonnelToPost>();
		for (PersonnelToPost record : list) {
			String key = record.getRybh() + "_" + record.getGwzw() + "_" + record.getDwbm();
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有技术履历
	 * @CreateTime 2018年1月12日 上午10:38:58
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PersonnelToTechnicalExperience> getAllTechnicalExperience(){
		List<PersonnelToTechnicalExperience> list = 
				personnelToTechnicalExperienceMapper.queryByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PersonnelToTechnicalExperience> resultMap = new HashMap<String, PersonnelToTechnicalExperience>();
		for (PersonnelToTechnicalExperience record : list) {
			String key = record.getRybh() + "_" + 
						 DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, record.getRq()) + "_" + 
						 record.getJx() + "_" + 
						 record.getDwbm() + "_" + 
						 record.getZw();
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有维修执照
	 * @CreateTime 2018年1月12日 上午10:39:15
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PersonnelToAuthorizationRecord> getAllMaintenanceLicense(){
		List<PersonnelToAuthorizationRecord> list = 
				personnelToAuthorizationRecordMapper.queryMaintenanceLicensesByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PersonnelToAuthorizationRecord> resultMap = new HashMap<String, PersonnelToAuthorizationRecord>();
		for (PersonnelToAuthorizationRecord record : list) {
			String key = record.getRybh() + "_" + record.getZy() + "_" + record.getZjbh();
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有机型执照
	 * @CreateTime 2018年1月12日 上午10:39:29
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PersonnelToAuthorizationRecord> getAllTypeLicense(){
		List<PersonnelToAuthorizationRecord> list = 
				personnelToAuthorizationRecordMapper.queryTypeLicensesByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PersonnelToAuthorizationRecord> resultMap = new HashMap<String, PersonnelToAuthorizationRecord>();
		for (PersonnelToAuthorizationRecord record : list) {
			String key = record.getRybh() + "_" + record.getZy() + "_" + record.getBfdw() + "_" + record.getFjjx() + "_" + record.getZy();
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有维修技术等级升级记录
	 * @CreateTime 2018年1月12日 上午10:39:43
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PersonnelToTechnicalGradeRecord> getAllTechnicalGradeRecord(){
		List<PersonnelToTechnicalGradeRecord> list = 
				personnelToTechnicalGradeRecordMapper.queryByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PersonnelToTechnicalGradeRecord> resultMap = new HashMap<String, PersonnelToTechnicalGradeRecord>();
		for (PersonnelToTechnicalGradeRecord record : list) {
			String key = record.getRybh() + "_" + 
						 record.getDj() + "_" + 
						 DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, record.getRq());
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有培训记录
	 * @CreateTime 2018年1月12日 上午10:40:07
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PlanPerson> getAllPlanPerson(){
		List<PlanPerson> list = 
				planPersonMapper.queryByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PlanPerson> resultMap = new HashMap<String, PlanPerson>();
		for (PlanPerson record : list) {
			String key = record.getParamsMap().get("rybh") + "_" + 
						 (record.getSjKsrq() != null ? DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, record.getSjKsrq()) : "") + "_" + 
						 record.getSjKssj() + "_" + 
						 record.getKcbm() + "_" + 
						 record.getFjjx();
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有业务考核记录
	 * @CreateTime 2018年1月12日 上午10:40:16
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PersonnelToBusinessAssessment> getAllBusinessAssessment(){
		List<PersonnelToBusinessAssessment> list = 
				personnelToBusinessAssessmentMapper.queryByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PersonnelToBusinessAssessment> resultMap = new HashMap<String, PersonnelToBusinessAssessment>();
		for (PersonnelToBusinessAssessment record : list) {
			String key = record.getRybh() + "_" + 
						 DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, record.getRq()) + "_" + 
						 record.getJx() + "_" + 
						 record.getZy();
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有学术成就
	 * @CreateTime 2018年1月12日 上午10:40:32
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PersonnelToAchievement> getAllScholarship(){
		List<PersonnelToAchievement> list = 
				personnelToAchievementMapper.queryScholarshipsByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PersonnelToAchievement> resultMap = new HashMap<String, PersonnelToAchievement>();
		for (PersonnelToAchievement record : list) {
			String key = record.getRybh() + "_" + record.getCljg() + "_" + DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, record.getJcrq());
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有嘉奖记录
	 * @CreateTime 2018年1月12日 上午10:40:46
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PersonnelToAchievement> getAllCitation(){
		List<PersonnelToAchievement> list = 
				personnelToAchievementMapper.queryCitationsByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PersonnelToAchievement> resultMap = new HashMap<String, PersonnelToAchievement>();
		for (PersonnelToAchievement record : list) {
			String key = record.getRybh() + "_" + record.getCljg() + "_" + DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, record.getJcrq());
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有事故征候情况
	 * @CreateTime 2018年1月12日 上午10:41:00
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PersonnelToCreditRecord> getAllIncidentSituation(){
		List<PersonnelToCreditRecord> list = 
				personnelToCreditRecordMapper.queryIncidentSituationsByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PersonnelToCreditRecord> resultMap = new HashMap<String, PersonnelToCreditRecord>();
		for (PersonnelToCreditRecord record : list) {
			String key = record.getRybh() + "_" + DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, record.getRq()) + "_" + record.getSjjg();
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有负有责任的不安全事件
	 * @CreateTime 2018年1月12日 上午10:41:14
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PersonnelToCreditRecord> getAllUnsafeIncident(){
		List<PersonnelToCreditRecord> list = 
				personnelToCreditRecordMapper.queryUnsafeIncidentsByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PersonnelToCreditRecord> resultMap = new HashMap<String, PersonnelToCreditRecord>();
		for (PersonnelToCreditRecord record : list) {
			String key = record.getRybh() + "_" + DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, record.getRq()) + "_" + record.getDcjl();
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取所有不诚信行为
	 * @CreateTime 2018年1月12日 上午10:41:31
	 * @CreateBy 韩武
	 * @return
	 */
	private Map<String, PersonnelToCreditRecord> getAllDishonestBehaviors(){
		List<PersonnelToCreditRecord> list = 
				personnelToCreditRecordMapper.queryDishonestBehaviorsByDprtcode(ThreadVarUtil.getUser().getJgdm());
		
		Map<String, PersonnelToCreditRecord> resultMap = new HashMap<String, PersonnelToCreditRecord>();
		for (PersonnelToCreditRecord record : list) {
			String key = record.getRybh() + "_" + DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, record.getRq()) + "_" + record.getDcjl();
			resultMap.put(key, record);
		}
		return resultMap;
	}
	
	/**
	 * 根据培训计划-培训课程人员表编辑最近人员数据-修改
	 * @param planPerson
	 */
	private void addupdateRecentlyPlanPerson(PlanPerson planPerson){
		if(StringUtils.isBlank(planPerson.getKcid()) || StringUtils.isBlank(planPerson.getWxrydaid())){
			return;
		}
		//根据程id，计划id，维修人员id查询培训计划-培训课程人员表数据  状态为1 实参与标识为1 获取最大实际开始日期的那条数据
		PlanPerson maxPlanPerson = planPersonMapper.selectMaxDate(planPerson.getKcid(),planPerson.getWxrydaid());
			
		if(maxPlanPerson != null){
			//比较当前计划id与计划实际开始日期最大id是否一致
			
				//根据课程id和维修档案人员id查询bp004表的数据
				PersonnelRecentTrainingRecord personnelRecentTrainingRecord = personnelRecentTrainingRecordMapper.selectBykcwxr(planPerson.getKcid(),planPerson.getWxrydaid());
				
				if(personnelRecentTrainingRecord==null){
					  UUID uuid = UUID.randomUUID();//获取随机的uuid
					  PersonnelRecentTrainingRecord newPersonnelRecentTrainingRecord=new PersonnelRecentTrainingRecord();
					  newPersonnelRecentTrainingRecord.setId(uuid.toString());
					  newPersonnelRecentTrainingRecord.setDprtcode(maxPlanPerson.getDprtcode());
					  newPersonnelRecentTrainingRecord.setKcid(planPerson.getKcid());
					  newPersonnelRecentTrainingRecord.setWxrydaid(planPerson.getWxrydaid());
					  newPersonnelRecentTrainingRecord.setPxjlid(planPerson.getId());
					  personnelRecentTrainingRecordMapper.insertSelective(newPersonnelRecentTrainingRecord);	
				}else{
					  personnelRecentTrainingRecord.setPxjlid(maxPlanPerson.getId());
					  personnelRecentTrainingRecordMapper.updateByPrimaryKeySelective(personnelRecentTrainingRecord);
				}
		} else{
			 personnelRecentTrainingRecordMapper.delete(planPerson.getKcid(),planPerson.getWxrydaid());
		}
	}
}
