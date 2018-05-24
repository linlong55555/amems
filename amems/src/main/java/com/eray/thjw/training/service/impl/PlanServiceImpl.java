package com.eray.thjw.training.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.training.dao.CourseMapper;
import com.eray.thjw.training.dao.PersonnelRecentTrainingRecordMapper;
import com.eray.thjw.training.dao.PlanPersonMapper;
import com.eray.thjw.training.dao.TrainingPlanMapper;
import com.eray.thjw.training.po.BusinessToMaintenancePersonnel;
import com.eray.thjw.training.po.Course;
import com.eray.thjw.training.po.PersonnelRecentTrainingRecord;
import com.eray.thjw.training.po.PlanPerson;
import com.eray.thjw.training.po.TrainingPlan;
import com.eray.thjw.training.service.PlanService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.DelStatus;
import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.WhetherEnum;
import enu.training.TrainingPlanStatusEnum;

/**
 * @author liub
 * @description 培训计划service,用于业务逻辑处理
 */
@Service
public class PlanServiceImpl implements PlanService {
	
	/**
	 * @author liub
	 * @description 培训计划mapper
	 */
	@Resource
	private TrainingPlanMapper trainingPlanMapper;
    
	/**
	 * @author liub
	 * @description 课程管理mapper
	 */
	@Resource
	private CourseMapper courseMapper;
	
	/**
	 * @author liub
	 * @description 培训计划-培训课程人员mapper
	 */
	@Resource
	private PlanPersonMapper planPersonMapper;
	
    /**
  	 * @author liub
  	 * @description 附件service
  	 */
	@Resource
	private AttachmentService attachmentService;
    
    /**
	 * @author liub
	 * @description 历史数据公共service
	 */
	@Autowired
	private CommonRecService commonRecService;
	/**
	 * @author 林龙
	 * @description 最近人员培训记录表 
	 */
	@Autowired
	private PersonnelRecentTrainingRecordMapper personnelRecentTrainingRecordMapper;
	
	
	/**
	 * @author liub
	 * @description 增加培训计划
	 * @param trainingPlan
	 * @throws BusinessException 
	 */
	@Override
	public String add(TrainingPlan trainingPlan) throws BusinessException{
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		trainingPlan.setId(id);
		trainingPlan.setZt(TrainingPlanStatusEnum.SAVE.getId());
		trainingPlan.setWhrid(user.getId());
		trainingPlan.setWhbmid(user.getBmdm());
		trainingPlan.setDprtcode(user.getJgdm());
		trainingPlanMapper.insertSelective(trainingPlan);
		commonRecService.write(id, TableEnum.B_P_002, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, trainingPlan.getId());//保存历史记录信息
		//新增培训计划-培训课程人员表
		if(null != trainingPlan.getPlanPersonList() && trainingPlan.getPlanPersonList().size() > 0){
			List<String> columnValueList = new ArrayList<String>();
			for (PlanPerson planPerson : trainingPlan.getPlanPersonList()) {
				planPerson.setId(UUID.randomUUID().toString());
				planPerson.setDprtcode(trainingPlan.getDprtcode());
				planPerson.setPxjhid(trainingPlan.getId());
				planPerson.setKcid(trainingPlan.getKcid());
				planPerson.setKcnr(trainingPlan.getKcnr());
				planPerson.setPxgh(trainingPlan.getPxjg());
				planPerson.setBz(trainingPlan.getBz());
				planPerson.setIsYc(WhetherEnum.YES.getId());
				planPerson.setPxlb(trainingPlan.getPxlb());
				planPerson.setFxbs(trainingPlan.getFxbs());
				planPerson.setPxxs(trainingPlan.getPxxs());
				planPerson.setKsxs(trainingPlan.getKsxs());
				planPerson.setKcdd(trainingPlan.getKcdd());
				planPerson.setJsxm(trainingPlan.getJsxm());
				planPerson.setZt(DelStatus.TAKE_EFFECT.getId());
				planPerson.setWhrid(user.getId());
				planPerson.setWhbmid(user.getBmdm());
				planPerson.setFjjx(trainingPlan.getFjjx());
				planPerson.setZy(trainingPlan.getZy());
				planPersonMapper.insertSelective(planPerson);
				columnValueList.add(planPerson.getId());
			}
			if(columnValueList.size() > 0){
				commonRecService.write("id", columnValueList, TableEnum.B_P_00201, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, id);//插入日志
			}
		}
		return id;
	}
	
	/**
	 * @author liub
	 * @description 修改培训计划
	 * @param trainingPlan
	 * @throws BusinessException 
	 */
	@Override
	public void edit(TrainingPlan trainingPlan) throws BusinessException{
		TrainingPlan oldPlan = trainingPlanMapper.selectByPrimaryKey(trainingPlan.getId());
		/* 验证重复提交 begin */
		verification(new Integer[]{TrainingPlanStatusEnum.SAVE.getId(),TrainingPlanStatusEnum.LSSUED.getId()},oldPlan.getZt(),"该培训计划已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		trainingPlan.setWhrid(user.getId());
		trainingPlan.setWhbmid(user.getBmdm());
		trainingPlanMapper.updatePlanById(trainingPlan);
		commonRecService.write(trainingPlan.getId(), TableEnum.B_P_002, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, trainingPlan.getId());//保存历史记录信息
		List<String> pList = new ArrayList<String>();
		Map<String,PlanPerson> oldMap = new HashMap<String, PlanPerson>();
		List<PlanPerson> planPersonList = planPersonMapper.queryByPxjhId(trainingPlan.getId());
		for (PlanPerson planPerson : planPersonList) {
			oldMap.put(planPerson.getWxrydaid(), planPerson);
		}
		//新增培训计划-培训课程人员表
		if(null != trainingPlan.getPlanPersonList() && trainingPlan.getPlanPersonList().size() > 0){
			for (PlanPerson planPerson : trainingPlan.getPlanPersonList()) {
				pList.add(planPerson.getWxrydaid());
				planPerson.setPxjhid(trainingPlan.getId());
				planPerson.setKcid(trainingPlan.getKcid());
				planPerson.setKcnr(trainingPlan.getKcnr());
				planPerson.setPxgh(trainingPlan.getPxjg());
				planPerson.setBz(trainingPlan.getBz());
				planPerson.setIsYc(WhetherEnum.YES.getId());
				planPerson.setPxlb(trainingPlan.getPxlb());
				planPerson.setFxbs(trainingPlan.getFxbs());
				planPerson.setPxxs(trainingPlan.getPxxs());
				planPerson.setKsxs(trainingPlan.getKsxs());
				planPerson.setKcdd(trainingPlan.getKcdd());
				planPerson.setJsxm(trainingPlan.getJsxm());
				planPerson.setWhrid(user.getId());
				planPerson.setWhbmid(user.getBmdm());
				planPerson.setFjjx(trainingPlan.getFjjx());
				planPerson.setZy(trainingPlan.getZy());
				if(oldMap.containsKey(planPerson.getWxrydaid())){
					planPerson.setId(oldMap.get(planPerson.getWxrydaid()).getId());
					planPersonMapper.updateByPrimaryKeySelective(planPerson);
					commonRecService.write(planPerson.getId(), TableEnum.B_P_00201, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, trainingPlan.getId());//保存历史记录信息
				}else{
					planPerson.setId(UUID.randomUUID().toString());
					planPerson.setZt(DelStatus.TAKE_EFFECT.getId());
					planPerson.setDprtcode(trainingPlan.getDprtcode());
					planPersonMapper.insertSelective(planPerson);
					commonRecService.write(planPerson.getId(), TableEnum.B_P_00201, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, trainingPlan.getId());//保存历史记录信息
				}
			}
		}
		for(Map.Entry<String,PlanPerson> entry : oldMap.entrySet()){
			if(!pList.contains(entry.getKey())){
				planPersonMapper.deleteById(entry.getValue().getId());
				commonRecService.write(entry.getValue().getId(), TableEnum.B_P_00201, user.getId(), czls ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, trainingPlan.getId());//保存历史记录信息
			}
		}
	}
	
	/**
	 * @author liub
	 * @description 作废
	 * @param id
	 */
	@Override
	public void cancel(String id) throws BusinessException{
		TrainingPlan oldPlan = trainingPlanMapper.selectByPrimaryKey(id);
		/* 验证重复提交 begin */
		verification(new Integer[]{TrainingPlanStatusEnum.SAVE.getId()},oldPlan.getZt(),"该培训计划已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		TrainingPlan trainingPlan = new TrainingPlan();
		trainingPlan.setId(id);
		trainingPlan.setZt(TrainingPlanStatusEnum.CANCEL.getId());
		trainingPlan.setWhrid(user.getId());
		trainingPlan.setWhbmid(user.getBmdm());
		trainingPlanMapper.updateStatus(trainingPlan);
		commonRecService.write(id, TableEnum.B_P_002, user.getId(), czls ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, trainingPlan.getId());//保存历史记录信息
	}
	
	/**
	 * @author liub
	 * @description 下发
	 * @param id
	 */
	@Override
	public void updateIssued(String id) throws BusinessException{
		TrainingPlan oldPlan = trainingPlanMapper.selectByPrimaryKey(id);
		if(null == oldPlan.getJsxm()){
			throw new BusinessException("讲师不能为空!");
		}
		/* 验证重复提交 begin */
		verification(new Integer[]{TrainingPlanStatusEnum.SAVE.getId()},oldPlan.getZt(),"该培训计划已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		TrainingPlan trainingPlan = new TrainingPlan();
		trainingPlan.setId(id);
		trainingPlan.setZt(TrainingPlanStatusEnum.LSSUED.getId());
		trainingPlan.setWhrid(user.getId());
		trainingPlan.setWhbmid(user.getBmdm());
		trainingPlanMapper.updateStatus(trainingPlan);
		commonRecService.write(id, TableEnum.B_P_002, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, trainingPlan.getId());//保存历史记录信息
	}
	
	/**
	 * @author liub
	 * @description 指定结束
	 * @param trainingPlan
	 */
	@Override
	public void updateShutDown(TrainingPlan trainingPlan) throws BusinessException{
		TrainingPlan oldPlan = trainingPlanMapper.selectByPrimaryKey(trainingPlan.getId());
		/* 验证重复提交 begin */
		verification(new Integer[]{TrainingPlanStatusEnum.LSSUED.getId()},oldPlan.getZt(),"该培训计划已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		trainingPlanMapper.updateByPrimaryKeySelective(trainingPlan);
	}
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询培训计划信息
	 * @param TrainingPlan
	 * @return List<TrainingPlan>
	 */
	@Override
	public List<TrainingPlan> queryAllPageList(TrainingPlan trainingPlan){
		return trainingPlanMapper.queryAllPageList(trainingPlan);
	}
	/**
	 * @author 孙霁
	 * @description 根据查询条件分页查询培训计划信息
	 * @param TrainingPlan
	 * @return List<TrainingPlan>
	 */
	@Override
	public List<TrainingPlan> queryAllPageListToPerson(TrainingPlan trainingPlan){
		
		List<TrainingPlan> trainingPlanList=trainingPlanMapper.queryAllPageListToPerson(trainingPlan);
		if(trainingPlanList != null && trainingPlanList.size() > 0){
			
			for (TrainingPlan trp : trainingPlanList) {
				if(trp.getPxys() != null ){
					trp.setPxysBz(trp.getPxys()+" "+trp.getPxysBz());
				}
				
				if(trp.getPlanPersonList() != null && trp.getPlanPersonList().size() > 0){
					StringBuffer strXm=new StringBuffer();
					List<PlanPerson> planPersonList=trp.getPlanPersonList();
					for (PlanPerson planPerson : planPersonList) {
						strXm.append(planPerson.getMaintenancePersonnel() != null ? planPerson.getMaintenancePersonnel().getRybh()+" "+planPerson.getMaintenancePersonnel().getXm()+"," :"");
					}
					if(strXm !=null && strXm.length() > 0){
						trp.getParamsMap().put("ryxx", strXm.toString().subSequence(0, strXm.length()-1));
					}
				}
			}
		}
		return trainingPlanList;
	}
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询年度视图信息
	 * @param trainingPlan
	 * @return List<TrainingPlan>
	 */
	@Override
	public Map<String, Object> queryYearPageList(TrainingPlan trainingPlan){
		PageHelper.startPage(trainingPlan.getPagination());
		List<TrainingPlan> resultList = trainingPlanMapper.queryYearPageList(trainingPlan);
		for (TrainingPlan result : resultList) {
			if(result.getJhKsrq() != null){
				result.getParamsMap().put("jh", getMonthAndWeekValue(result.getJhKsrq()));
			}
			if(result.getSjKsrq() != null){
				result.getParamsMap().put("sj", getMonthAndWeekValue(result.getSjKsrq()));
			}
		}
		Map<String, Object> resultMap = PageUtil.pack4PageHelper(resultList, trainingPlan.getPagination());
		resultMap.put("thStyleMap", DateUtil.getMonthAndWeek(Integer.parseInt(String.valueOf(trainingPlan.getParamsMap().get("year")))));
		return resultMap;
	}
	
	/**
	 * @author liub
	 * @description 根据日期获取月份及每月的周数
	 * @param date
	 * @return Map<String, Integer>
	 */
	public static Map<String, Integer> getMonthAndWeekValue(Date date){
		Map<String, Integer> map = new HashMap<String, Integer>();
		Calendar c = Calendar.getInstance(); 
		c.setTime(date);
        map.put("month", c.get(Calendar.MONTH)+1);
        map.put("week", c.get(Calendar.WEEK_OF_MONTH));
		return map;
	}
	
	/**
	 * @author liub
	 * @description 根据id查询培训计划、课程及用户信息
	 * @param id
	 * @return TrainingPlan
	 */
	@Override
	public TrainingPlan selectById(String id){
		
		
		TrainingPlan trainingPlan=trainingPlanMapper.selectById(id);
		
		List<PlanPerson> trainingPlanList= trainingPlan.getPlanPersonList();
		
		Map<String, List<Attachment>> map = new HashMap<String, List<Attachment>>();
		for (PlanPerson trainingPlan1 : trainingPlanList) {
			//查询出所有培训大纲下的课程附件
			List<Attachment> filelist=attachmentService.selectListAttachments(trainingPlan1.getId());
			
			if (filelist!=null && !filelist.isEmpty()) {
				for (Attachment attachment : filelist) {
					if(map!=null&&map.containsKey(attachment.getMainid())){
						map.get(attachment.getMainid()).add(attachment);
					}else{
						List<Attachment> templist= new ArrayList<Attachment>();
						templist.add(attachment);
						map.put(attachment.getMainid(), templist);
					}
				}
			}
		}
		
		if (trainingPlanList!=null && !trainingPlanList.isEmpty()) {
			for (PlanPerson tcp : trainingPlanList) {
				if(map.containsKey(tcp.getId())){
					tcp.setAttachments(map.get(tcp.getId()));
				}
			}
		}	
		
		return trainingPlan;
	}

	@Override
	public List<TrainingPlan> queryAllPagetrainingnoticeList(
			TrainingPlan trainingPlan) {
		return trainingPlanMapper.queryAllPagetrainingnoticeList(trainingPlan);
	}
	
	/**
	 * @author liub
	 * @description 验证重复提交
	 * @param i,j,message
	 */
	private void verification(Integer[] i, Integer j,String message) throws BusinessException{
		if(j != null && !ArrayUtils.contains(i,j)){
			throw new BusinessException(message);
		}
	}
	/**
	 * @author sunji
	 * @description 获取年度数据
	 * @param i,j,message
	 */
	public List<TrainingPlan> queryAllTrainingPlan(TrainingPlan trainingPlan){
		List<TrainingPlan> resultList = trainingPlanMapper.queryYearPageList(trainingPlan);
		Map<Integer,Integer> thStyleMap=DateUtil.getMonthAndWeek(Integer.parseInt(String.valueOf(trainingPlan.getParamsMap().get("year"))));
		List<Map<String , String>> bt=new ArrayList<Map<String , String>>();
		for(int i=0;i<12;i++){
			Integer week=thStyleMap.get(0);
			for(int j=0;j<week;j++){
				Map<String , String> btmap=new HashMap<String, String>();
				btmap.put("bt", (i+1)+"月"+"-"+(j+1)+"周");
				bt.add(btmap);
			}
			
		}
		for (TrainingPlan result : resultList) {
			
			Map<String,Object> paramsMap=new HashMap<String, Object>();
			if(result.getJhKsrq() != null && result.getJhKsrq().getYear()+1900==Integer.parseInt(trainingPlan.getParamsMap().get("year").toString())){
				paramsMap.put("jh", getMonthAndWeekValue(result.getJhKsrq()));
			}
			if(result.getSjKsrq() != null && result.getSjKsrq().getYear()+1900==Integer.parseInt(trainingPlan.getParamsMap().get("year").toString())){
				paramsMap.put("sj", getMonthAndWeekValue(result.getSjKsrq()));
			}
			
			List<Map<String,String>> logo=new ArrayList<Map<String,String>>(bt.size());
			int count=0;
			for (Map<String ,String> btmap : bt) {
				int month=0;
				int week=0;
				if(btmap.get("bt").length()>5){
					month=Integer.parseInt(btmap.get("bt").substring(0,2));
					week=Integer.parseInt(btmap.get("bt").substring(4,5));
				}else{
					month=Integer.parseInt(btmap.get("bt").substring(0,1));
					week=Integer.parseInt(btmap.get("bt").substring(3,4));
				}
				Map<String,String> lg=new HashMap<String, String>();
				lg.put("lg", "");
				logo.add(lg);
				if(paramsMap.get("jh") !=null ){
					Map<String,Integer> map=(Map<String, Integer>) paramsMap.get("jh");
					if(map.get("month")==month && map.get("week")==week){
						lg.put("lg", "○");
						logo.set(count,lg);
					}
				}
				if(paramsMap.get("jh") !=null && paramsMap.get("sj") !=null){
					Map<String,Integer> jhmap=(Map<String, Integer>) paramsMap.get("jh");
					Map<String,Integer> sjmap=(Map<String, Integer>) paramsMap.get("sj");
					if(sjmap.get("month")==month && sjmap.get("week")==week){
						lg.put("lg", "●");
						logo.set(count,lg);
						if(jhmap.get("month")==sjmap.get("month") && jhmap.get("week")==sjmap.get("week")){
							lg.put("lg", "▲");
							logo.set(count,lg);
						}
					}
					
					
				}
				count++;
			}
			result.setLogo(logo);
			result.setBt(bt);
			
		}
		
		
		return resultList;
	}

	@Override
	public List<TrainingPlan> queryAllPagerecordsList(TrainingPlan trainingPlan) {
		return trainingPlanMapper.queryAllPagerecordsList(trainingPlan);
	}

	@Override
	public TrainingPlan selectByPrimaryKey(String id) {
		return trainingPlanMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TrainingPlan> queryAllPageKcList(TrainingPlan trainingPlan) {
		return trainingPlanMapper.queryAllPageKcList(trainingPlan);
	}

	@Override
	public void save(TrainingPlan trainingPlan) {
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		List<String> fjidlist=trainingPlan.getFjidlist(); //取得需要逻辑删除的附件
		List<String> planPersonIdList=new ArrayList<String>();
		
		if(trainingPlan.getSjJsrq()==null){
			trainingPlan.setSjJssj(null);
		}
		/**修改培训计划**/
		trainingPlan.setWhsj(new Date());
		int i=0;
		for (PlanPerson planPerson1 : trainingPlan.getPlanPersonList()) {
			if(planPerson1.getIsSc()==1){
				i=i+1;
			}
		}
		trainingPlan.setScrs(i);
		trainingPlan.setWhrid(user.getId());
		trainingPlan.setZt(10);
		trainingPlanMapper.updateByRecordResult(trainingPlan);
		/**日志**/
		commonRecService.write(trainingPlan.getId(), TableEnum.B_P_002, user.getId(),czls, LogOperationEnum.SUBMIT_WO, UpdateTypeEnum.SAVE,null);
		
		/**新增或者删除附件**/
		attachmentService.eidtAttachment(trainingPlan.getAttachments(), trainingPlan.getId(), czls, trainingPlan.getId(), trainingPlan.getDprtcode(), LogOperationEnum.SUBMIT_WO);
		
		/**逻辑删除人员附件**/
		for (String fjid : fjidlist) {
			attachmentService.updateByPrimaryKey(fjid);
		}

		/**根据课程id查询课程信息**/
		Course course=courseMapper.selectById(trainingPlan.getParamsMap().get("kcid").toString());
		
		for (PlanPerson planPerson : trainingPlan.getPlanPersonList()) {//页面获取人员信息
			if((null == planPerson.getId()) || ("".equals( planPerson.getId()))){
				
			}else{
				PlanPerson planPersonOld =planPersonMapper.selectByPrimaryKey(planPerson.getId());
				//把已存在的培训人员id存起来
				planPersonIdList.add(planPerson.getId());
				
				//已知id做修改
				planPerson.setSjKsrq(trainingPlan.getSjKsrq());
				planPerson.setSjKssj(trainingPlan.getSjKssj());
				planPerson.setSjJsrq(trainingPlan.getSjJsrq());
				if(trainingPlan.getSjJsrq()!=null){
					planPerson.setSjJssj(trainingPlan.getSjJssj());
				}else{
					planPerson.setSjJssj(null);
				}
				planPerson.setWhsj(new Date());
				planPerson.setDprtcode(user.getJgdm());
				
				planPerson.setPxjhid(trainingPlan.getId());
				planPerson.setKcid(trainingPlan.getParamsMap().get("kcid").toString());
				planPerson.setKcdd(trainingPlan.getKcdd());
				planPerson.setJsxm(trainingPlan.getJsxm());
				planPerson.setSjks(trainingPlan.getSjks());
				planPerson.setFxbs(trainingPlan.getFxbs());
				planPerson.setKq(planPerson.getIsSc());
				planPerson.setJszt(0);
				planPerson.setPxlb(trainingPlan.getPxlb());
				planPerson.setFjjx(trainingPlan.getFjjx());
				planPerson.setZy(trainingPlan.getZy());
				planPerson.setPxgh(trainingPlan.getPxjg());
				planPerson.setPxxs(trainingPlan.getPxxs());
				planPerson.setKsxs(trainingPlan.getKsxs());
				planPerson.setKcbm(course.getKcbh());
				planPerson.setKcmc(course.getKcmc());
				planPerson.setKcnr(course.getKcnr());
				planPersonMapper.updateByPrimaryKeySelective(planPerson);
				
				/**日志**/
				commonRecService.write(trainingPlan.getId(), TableEnum.B_P_00201, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,trainingPlan.getId());
				
				/**根据培训计划-培训课程人员表编辑最近人员数据-修改**/
				addupdateRecentlyPlanPerson(planPerson.getKcid(),planPerson.getWxrydaid());
				if(!planPerson.getKcid().equals(planPersonOld.getKcid())||!planPersonOld.getWxrydaid().equals(planPerson.getWxrydaid())){
					/**根据培训计划-培训课程人员表编辑最近人员数据-修改**/
					addupdateRecentlyPlanPerson(planPersonOld.getKcid(),planPersonOld.getWxrydaid());
				}
				
				/**跟新附件**/
				attachmentService.eidtAttachment(planPerson.getAttachments(), planPerson.getId(), czls, planPerson.getId(), course.getDprtcode(), LogOperationEnum.EDIT);
			}
		}
		
		/**根据计划id查询所有计划人员信息，状态为1,实参为1**/
		List<PlanPerson> planPersonList = planPersonMapper.queryByPxjhsc(trainingPlan.getId());
		 
			if(planPersonList != null && planPersonList.size() > 0){
				
				for (PlanPerson planPerson : planPersonList) {//数据库信息
					
					if(!planPersonIdList.contains(planPerson.getId())){//已存在的数据和 数据库的里的数据对比 少就删  多 重复就修改
						
						String kcid = planPerson.getKcid();//保存课程id
						String wxrydaid = planPerson.getWxrydaid();//保存维修人员id
						
						/**删除人员**/
						planPerson.setZt(0);
						planPersonMapper.updateByPrimaryKeySelective(planPerson);
						
						/**日志**/
						commonRecService.write(trainingPlan.getId(), TableEnum.B_P_00201, user.getId(),czls,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,trainingPlan.getId());
						
						/**根据培训计划-培训课程人员表编辑最近人员数据-删除**/
						PlanPerson planPersondel=new PlanPerson();
						planPersondel.setKcid(kcid);
						planPersondel.setWxrydaid(wxrydaid);
						/**根据培训计划-培训课程人员表编辑最近人员数据**/
						addupdateRecentlyPlanPerson(planPerson.getKcid(),planPerson.getWxrydaid());
					}else{//找得到就做修改
			
					}
				}
		}
		
		
		/**查询页面数据 id为null做新增**/
		for (PlanPerson planPerson : trainingPlan.getPlanPersonList()) {//页面获取人员信息
			if((null == planPerson.getId()) || ("".equals( planPerson.getId()))){
				UUID uuid = UUID.randomUUID();//获取随机的uuid
				planPerson.setId(uuid.toString());
				planPerson.setPxjhid(trainingPlan.getId());
				planPerson.setZt(1);
				planPerson.setWhrid(user.getId());
				planPerson.setCybs(0);
				planPerson.setJszt(0);
				planPerson.setWhbmid(user.getBmdm());
				planPerson.setDprtcode(user.getJgdm());
				planPerson.setSjKsrq(trainingPlan.getSjKsrq());
				planPerson.setSjKssj(trainingPlan.getSjKssj());
				planPerson.setSjJsrq(trainingPlan.getSjJsrq());
				if(trainingPlan.getSjJsrq()!=null){
					planPerson.setSjJssj(trainingPlan.getSjJssj());
				}else{
					planPerson.setSjJssj(null);
				}
				planPerson.setKq(planPerson.getIsSc());
				planPerson.setPxjhid(trainingPlan.getId());
				planPerson.setPxjhid(trainingPlan.getId());
				planPerson.setKcid(trainingPlan.getParamsMap().get("kcid").toString());
				planPerson.setKcdd(trainingPlan.getKcdd());
				planPerson.setJsxm(trainingPlan.getJsxm());
				planPerson.setSjks(trainingPlan.getSjks());
				planPerson.setFxbs(trainingPlan.getFxbs());
				planPerson.setPxlb(trainingPlan.getPxlb());
				planPerson.setFjjx(trainingPlan.getFjjx());
				planPerson.setZy(trainingPlan.getZy());
				planPerson.setPxgh(trainingPlan.getPxjg());
				planPerson.setKcbm(course.getKcbh());
				planPerson.setKcmc(course.getKcmc());
				planPerson.setKcnr(course.getKcnr());
				planPerson.setPxxs(trainingPlan.getPxxs());
				planPerson.setKsxs(trainingPlan.getKsxs());
				planPersonMapper.insertSelective(planPerson);
				
				/**根据培训计划-培训课程人员表编辑最近人员数据**/
				addupdateRecentlyPlanPerson(planPerson.getKcid(),planPerson.getWxrydaid());
				
				/**跟新附件**/
				attachmentService.eidtAttachment(planPerson.getAttachments(), uuid.toString(), czls, uuid.toString(), user.getBmdm(), LogOperationEnum.EDIT);
			}
		}
	}
	
	/**
	 * 根据培训计划-培训课程人员表编辑最近人员数据-修改
	 * @param planPerson
	 */
	private void addupdateRecentlyPlanPerson(String kcid,String wxrydaid){
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


	/**
	 * 
	 * @Description 根据课程编号和人员id查询数据
	 * @CreateTime 2018-3-28 下午3:31:15
	 * @CreateBy 孙霁
	 * @param trainingPlan
	 * @return
	 */
	@Override
	public Map<String, Object> queryAllByBhAndRy(PlanPerson planPerson) {
		PageHelper.startPage(planPerson.getPagination());
		List<PlanPerson> list = planPersonMapper.queryAllByBhAndRy(planPerson);
		return PageUtil.pack4PageHelper(list, planPerson.getPagination());
	}
	
}
