package com.eray.thjw.training.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.training.dao.CourseMapper;
import com.eray.thjw.training.dao.PersonnelRecentTrainingRecordMapper;
import com.eray.thjw.training.dao.PlanPersonMapper;
import com.eray.thjw.training.po.Course;
import com.eray.thjw.training.po.PersonnelRecentTrainingRecord;
import com.eray.thjw.training.po.PlanPerson;
import com.eray.thjw.training.service.PlanPersonService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/**
 * @author liub
 * @description 培训计划-培训课程人员service,用于业务逻辑处理
 */
@Service
public class PlanPersonServiceImpl implements PlanPersonService {
	
	  /**
  	 * @author liub
  	 * @description 附件service
  	 */
	@Resource
	private AttachmentService attachmentService;
	/**
	 * @author liub
	 * @description 课程service
	 */
	@Resource
	private CourseMapper courseMapper;
	
	@Resource
	private PlanPersonMapper planPersonMapper;
	@Resource
	private PersonnelRecentTrainingRecordMapper personnelRecentTrainingRecordMapper;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private AttachmentMapper attachmentMapper;
	
	@Override
	public List<PlanPerson> selectByPxjhid(String pxjhid){
		return planPersonMapper.queryByPxjhId(pxjhid);
	}
	@Override
	public List<PlanPerson> queryByPxjhscId(String pxjhid){
		return planPersonMapper.queryByPxjhscId(pxjhid);
	}
	/**
	 * @author sunji
	 * @description 查询维修人员培训跟踪
	 * @param PlanPerson
	 * @return List<PlanPerson>
	 */
	public Map<String, Object> qeryAllPageList(PlanPerson planPerson)throws BusinessException {
		PageHelper.startPage(planPerson.getPagination());
		List<PlanPerson> list = planPersonMapper.qeryAllPageList(planPerson);
		return PageUtil.pack4PageHelper(list, planPerson.getPagination());
	}

	@Override
	public void save(PlanPerson planPerson) throws BusinessException {
		String czls = UUID.randomUUID().toString();//操作流水
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		User user = ThreadVarUtil.getUser();
		planPerson.setId(uuid.toString());
		planPerson.setZt(1);
		planPerson.setWhrid(user.getId());
		planPerson.setWhsj(new Date());
		planPerson.setWhbmid(user.getBmdm());
		planPerson.setIsYc(1);
		if(planPerson.getSjJsrq()==null){
			planPerson.setSjJssj("");
		}
		planPerson.setIsSc(1);
		planPerson.setCybs(0);
		planPerson.setKq(1);
		planPerson.setJszt(0);
		planPerson.setDprtcode(user.getJgdm());
		planPersonMapper.insertSelective(planPerson);
		
		//附件
		attachmentService.eidtAttachment(planPerson.getAttachments(), planPerson.getId(), czls, planPerson.getId(), planPerson.getDprtcode(), LogOperationEnum.SAVE_WO);
		
		/**根据培训计划-培训课程人员表编辑最近人员数据**/
		addupdateRecentlyPlanPerson(planPerson.getKcid(),planPerson.getWxrydaid());
	}
	
	@Override
	public void update(PlanPerson planPerson) throws BusinessException {
		
		PlanPerson planPersonOld =planPersonMapper.selectByPrimaryKey(planPerson.getId());
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		planPerson.setWhsj(new Date());
		planPerson.setWhrid(user.getId());
		if(planPerson.getSjJsrq()==null){
			planPerson.setSjJssj("");
		}
		
		if(planPerson.getKcid() != null && !planPerson.getKcid().equals("")){
			/**根据课程id查询课程信息**/
			Course course=courseMapper.selectById(planPerson.getKcid());
			planPerson.setKcnr(course.getKcnr());
			planPerson.setKcbm(course.getKcbh());
			planPerson.setKcmc(course.getKcmc());
		}
		
		planPerson.setWhbmid(user.getBmdm());
		planPerson.setKq(1);
		planPerson.setIsSc(1);
		planPersonMapper.updateByPrimaryKeySelective(planPerson);

		//附件
		attachmentService.eidtAttachment(planPerson.getAttachments(), planPerson.getId(), czls, planPerson.getId(), planPerson.getDprtcode(), LogOperationEnum.EDIT);
		
		/**根据培训计划-培训课程人员表编辑最近人员数据**/
		addupdateRecentlyPlanPerson(planPerson.getKcid(),planPerson.getWxrydaid());
		if(StringUtils.isNotBlank(planPersonOld.getKcid()) && !planPersonOld.getKcid().equals(planPerson.getKcid())||!planPersonOld.getWxrydaid().equals(planPerson.getWxrydaid())){
			addupdateRecentlyPlanPerson(planPersonOld.getKcid(),planPersonOld.getWxrydaid());//
		}
	}

	
	
	/**
	 * 根据培训计划-培训课程人员表编辑最近人员数据-修改
	 * @param planPerson
	 */
	private void addupdateRecentlyPlanPerson(String kcid,String wxrydaid){
		if(StringUtils.isBlank(kcid) || StringUtils.isBlank(wxrydaid)){
			return;
		}
		//根据程id，计划id，维修人员id查询培训计划-培训课程人员表数据  状态为1 实参与标识为1 获取最大实际开始日期的那条数据
 		PlanPerson maxPlanPerson = planPersonMapper.selectMaxDate(kcid,wxrydaid);
			
		if(maxPlanPerson != null){
			//比较当前计划id与计划实际开始日期最大id是否一致
			
				//根据课程id和维修档案人员id查询bp004表的数据
				PersonnelRecentTrainingRecord personnelRecentTrainingRecord = personnelRecentTrainingRecordMapper.selectBykcwxr(kcid,wxrydaid);
				
				if(personnelRecentTrainingRecord==null){
					  UUID uuid = UUID.randomUUID();//获取随机的uuid
					  PersonnelRecentTrainingRecord newPersonnelRecentTrainingRecord=new PersonnelRecentTrainingRecord();
					  newPersonnelRecentTrainingRecord.setId(uuid.toString());
					  newPersonnelRecentTrainingRecord.setDprtcode(maxPlanPerson.getDprtcode());
					  newPersonnelRecentTrainingRecord.setKcid(maxPlanPerson.getKcid());
					  newPersonnelRecentTrainingRecord.setWxrydaid(maxPlanPerson.getWxrydaid());
					  newPersonnelRecentTrainingRecord.setPxjlid(maxPlanPerson.getId());
					  personnelRecentTrainingRecordMapper.insertSelective(newPersonnelRecentTrainingRecord);	
				}else{
						personnelRecentTrainingRecord.setPxjlid(maxPlanPerson.getId());
						personnelRecentTrainingRecordMapper.updateByPrimaryKeySelective(personnelRecentTrainingRecord);
				}
		} else{
			personnelRecentTrainingRecordMapper.delete(kcid,wxrydaid);
		}
	}
	
	
	
	@Override
	public List<PlanPerson> queryAllPageplanPersonList(PlanPerson planPerson)
			throws BusinessException {
		return planPersonMapper.queryAllPageplanPersonList(planPerson);
	}
	@Override
	public void delete(PlanPerson planPerson){
		PlanPerson newPlanPerson = planPersonMapper.selectByPrimaryKey(planPerson.getId());
		
		String kcid = newPlanPerson.getKcid();//保存课程id
		String id = newPlanPerson.getId();//保存计划id
		String wxrydaid = newPlanPerson.getWxrydaid();//保存维修人员id
		
		planPerson.setZt(0);
		planPersonMapper.updateByPrimaryKeySelective(planPerson);
		
		PlanPerson planPersondel=new PlanPerson();
		planPersondel.setKcid(kcid);
		planPersondel.setWxrydaid(wxrydaid);
		//当课程id不为空时，如果当前的开始日期大于查询到的开始日期那么就跟新
		/**根据培训计划-培训课程人员表编辑最近人员数据**/
		addupdateRecentlyPlanPerson(planPersondel.getKcid(),planPersondel.getWxrydaid());
	}
	

	
	@Override
	public PlanPerson selectByPrimaryKey(String id) {
		return planPersonMapper.selectByPrimaryKey(id);
	}
	@Override
	public List<PlanPerson> queryAllPagerecordsList(PlanPerson planPerson) {
		return planPersonMapper.queryAllPagerecordsList(planPerson);
	}
	
	
	/**
	 * 保存
	 */
	@Override
	public void save(MaintenancePersonnel personnel) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		for (PlanPerson training : personnel.getTrainings()) {
			training.setDprtcode(personnel.getDprtcode());
			training.setWxrydaid(personnel.getId());
			training.setXm(personnel.getXm());
			training.setGzdw(personnel.getSzdw());
			training.setZt(1);
			
			training.setWhbmid(user.getBmdm());
			training.setWhrid(user.getId());
			training.setWhsj(new Date());
			training.setCzls(personnel.getCzls());
			training.setLogOperationEnum(personnel.getLogOperationEnum());
			if(StringUtils.isBlank(training.getId())){	// 新增
				training.setId(UUID.randomUUID().toString());
				training.setIsYc(1);
				training.setIsSc(1);
				training.setCybs(0);
				training.setJszt(0);
				planPersonMapper.insertSelective(training);
				// 写入日志
				commonRecService.write(training.getId(), TableEnum.B_P_00201, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.SAVE, personnel.getId());
				
				/**根据培训计划-培训课程人员表编辑最近人员数据-修改**/
				addupdateRecentlyPlanPerson(training.getKcid(),training.getWxrydaid());
			}else{	// 修改
				PlanPerson planPerson =planPersonMapper.selectByPrimaryKey(training.getId());
				
				planPersonMapper.updateByWxryda(training);	
				// 写入日志
				commonRecService.write(training.getId(), TableEnum.B_P_00201, user.getId(), personnel.getCzls(),
						personnel.getLogOperationEnum(), UpdateTypeEnum.UPDATE, personnel.getId());
				/**根据培训计划-培训课程人员表编辑最近人员数据-修改**/
				addupdateRecentlyPlanPerson(training.getKcid(),training.getWxrydaid());
				if(StringUtils.isNotBlank(planPerson.getKcid()) && !planPerson.getKcid().equals(training.getKcid())||!training.getWxrydaid().equals(planPerson.getWxrydaid())){
					addupdateRecentlyPlanPerson(planPerson.getKcid(),planPerson.getWxrydaid());//
				}
			}
			// 保存附件
			saveAttachments(training);
		}
		// 插入日志
		StringBuilder sql = new StringBuilder("b.wxrydaid = '"+personnel.getId()+"' ");
		if(!personnel.getTrainings().isEmpty()){
			sql.append("and b.id not in (");
			for (PlanPerson training : personnel.getTrainings()) {
				sql.append("'").append(training.getId()).append("',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(")");
		}
		if(personnel.getTrainings().size()==0){
			PersonnelRecentTrainingRecord personnelRecentTrainingRecord=new PersonnelRecentTrainingRecord();
			personnelRecentTrainingRecord.setWxrydaid(personnel.getId());
			personnelRecentTrainingRecordMapper.deletel(personnelRecentTrainingRecord);
		}
		    
	
		commonRecService.writeByWhere(sql.toString(), TableEnum.B_P_00201, user.getId(), personnel.getCzls(),
				personnel.getLogOperationEnum(), UpdateTypeEnum.DELETE, personnel.getId());
		planPersonMapper.deleteNotExist(personnel);
		
		
		//1.根据已有的数据查询培训计划的课程id
		List<PlanPerson> traininglist=planPersonMapper.selectMaxDateList(personnel.getId(),personnel.getDprtcode());
		//2.根据维修档案人员查询人员最近培训记录数据
		List<PersonnelRecentTrainingRecord> personnelRecentTrainingRecordlist=personnelRecentTrainingRecordMapper.selectList(personnel.getId(),personnel.getDprtcode());
		
		List<String> str1=new ArrayList<String>();//删除后的培训记录
		for (PersonnelRecentTrainingRecord personnelRecentTrainingRecord : personnelRecentTrainingRecordlist) {
			str1.add(personnelRecentTrainingRecord.getKcid());
		}
		List<String> str2=new ArrayList<String>();//最近人员
		for (PlanPerson planPerson : traininglist) {
			str2.add(planPerson.getKcid());
		}
		
		for (String str : str1) {
			if(!str2.contains(str) ){
				PersonnelRecentTrainingRecord personnelRecentTrainingRecord= new PersonnelRecentTrainingRecord();
				personnelRecentTrainingRecord.setKcid(str);
				personnelRecentTrainingRecord.setWxrydaid(personnel.getId());
				personnelRecentTrainingRecordMapper.deletel(personnelRecentTrainingRecord);
			}
		}
		    	 
		

	}
	
	/**
	 * 保存附件
	 * @param data
	 */
	private void saveAttachments(PlanPerson data){
		List<String> ids = new ArrayList<String>();
		List<Attachment> attachments = data.getAttachments();
		if (attachments != null && !attachments.isEmpty()) {
			User user = ThreadVarUtil.getUser();
			for (Attachment attachment : attachments) {
				if(StringUtils.isBlank(attachment.getId())){
					// 后缀名截取10位
					if(StringUtils.isNotBlank(attachment.getHzm()) && attachment.getHzm().length() > 10){
						attachment.setHzm(attachment.getHzm().substring(0, 10));
					}
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
					data.getLogOperationEnum(), UpdateTypeEnum.SAVE, data.getWxrydaid());
		}
	}
	
	/**
	 * 根据维修人员档案查询对应的培训记录
	 */
	@Override
	public List<PlanPerson> queryByWxrydaid(String id) {
		List<PlanPerson> list = planPersonMapper.queryByWxrydaid(id);
		for (PlanPerson p : list) {
			Attachment param = new Attachment();
			param.setMainid(p.getId());
			p.setAttachments(attachmentMapper.queryListAttachments(param));
		}
		return list;
	}
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	
}
