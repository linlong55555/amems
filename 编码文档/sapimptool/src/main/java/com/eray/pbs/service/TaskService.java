package com.eray.pbs.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.DbTaskHoursPbsDao;
import com.eray.pbs.dao.DbTaskPbsDao;
import com.eray.pbs.dao.StageDescriptionDao;
import com.eray.pbs.dao.TaskDao;
import com.eray.pbs.dao.TaskHoursPbsDao;
import com.eray.pbs.dao.TaskPbsDao;
import com.eray.pbs.dao.WorkHoursPbsDao;
import com.eray.pbs.dao.WorkPbsDao;
import com.eray.pbs.po.DbTaskHoursPbs;
import com.eray.pbs.po.DbTaskPbs;
import com.eray.pbs.po.StageDescription;
import com.eray.pbs.po.Task;
import com.eray.pbs.po.TaskHoursPbs;
import com.eray.pbs.po.TaskPbs;
import com.eray.pbs.po.WorkHoursPbs;
import com.eray.pbs.po.WorkPbs;
import com.eray.pbs.util.StageMap;
import com.eray.pbs.vo.WorkPbsVO;
import com.eray.util.Global;
import com.eray.util.date.DateCalculation;
import com.eray.util.jpa.DynamicSpecifications;
import com.eray.util.jpa.SearchFilter;

@Component
@Transactional(readOnly = true)
public class TaskService
{
	@Autowired
	private TaskDao taskDao;
	@Autowired
	private TaskPbsDao taskPbsDao;
	@Autowired
	private DbTaskPbsDao dbTaskPbsDao;
	@Autowired
	private DbTaskHoursPbsDao dbTaskHoursPbsDao;
	@Autowired
	private TaskHoursPbsDao taskHoursPbsDao;
	@Autowired
	private StageDescriptionDao stageDescriptionDao;
	@Autowired
	private WorkHoursPbsDao workHoursPbsDao;
	@Autowired
	private WorkPbsDao workPbsDao;
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	private Map<String, Object> searchParams = new HashMap<String, Object>();
	private Map<String, SearchFilter> filters;
	private Specification<Task> specTask;
	private PageRequest pageRequest;
	private Page<Task> page;

	private Specification<TaskPbs> specTaskPbs;
	private Specification<DbTaskPbs> specDbTaskPbs;
	private Specification<DbTaskHoursPbs> specDbTaskHoursPbs;
	private List<TaskPbs> taskPbsList;
	private List<DbTaskPbs> dbTaskPbsList;
	private TaskPbs taskPbs;
	private DbTaskPbs dbTaskPbs;
	private String aircraftModel;
	private List<DbTaskHoursPbs> dbTaskHoursPbsList;

	public List<Task> findAll(Map<String, Object> criteria)
	{
		return taskDao.findAll(buildTaskSpecification(criteria));
	}

	private Specification<Task> buildTaskSpecification(Map<String, Object> criteria)
	{
		filters = SearchFilter.parse(criteria);
		specTask = DynamicSpecifications.bySearchFilter(filters.values(), Task.class);
		return specTask;
	}

	@Transactional(readOnly = false)
	public Task save(Task task, String[] para)
	{
		if (task == null || para == null || para.length < 29)
		{
			return task;
		}
		searchParams.clear();
		searchParams.put("EQ_cardNumber", task.getCardNumber());
		searchParams.put("EQ_aircraftType", task.getAircraftType());
		filters = SearchFilter.parse(searchParams);
		specTaskPbs = DynamicSpecifications.bySearchFilter(filters.values(), TaskPbs.class);
		taskPbsList = taskPbsDao.findAll(specTaskPbs);
		if (taskPbsList != null && taskPbsList.size() > 0)
		{
			taskPbs = taskPbsList.get(0);
			if (DateCalculation.StringToDate(taskPbs.getKeyDate()).getTime() > DateCalculation.StringToDate(
			        task.getKeyDate()).getTime())
			{
				return taskDao.save(task);
			}
		} else
		{
			taskPbs = new TaskPbs();
			taskPbs.setCardNumber(task.getCardNumber());
			taskPbs.setAircraftType(task.getAircraftType());
		}
		taskPbs.setGroupId(task.getGroupId());
		taskPbs.setGroupCounter(para[2]);
		taskPbs.setKeyDate(task.getKeyDate());
		taskPbs.setDescription(para[4]);
		taskPbs.setPlanningPlant(para[5]);
		taskPbs.setWorkCenter(para[6]);
		taskPbs.setStage(para[7]);
		taskPbs.setSourceOfJobCard(para[9]);
		taskPbs.setSourceReference(para[10]);
		taskPbs.setZone(para[11]);
		taskPbs.setAtaChapter(para[12]);
		taskPbs.setCheck(para[13]);
		taskPbs.setOperationNumber(para[14]);
		taskPbs.setOperationDescription(para[16]);
		taskPbs.setOperationWorkCenter(para[17]);
		try
		{
			taskPbs.setWorkHours(new BigDecimal(para[19]));
		} catch (Exception e)
		{
			return task;
		}
		taskPbs.setActivityType(para[20]);
		taskPbs.setOperationNumberRelation(para[21]);
		taskPbs.setRelationType(para[22]);
		taskPbs.setCustomer(para[25]);
		taskPbs.setOwner(para[26]);
		try
		{
			taskPbs.setStandardManhour(new BigDecimal(para[27]));
		} catch (Exception e)
		{
			return task;
		}
		taskPbs.setElapsedTime(para[28]);
		if(para.length>29){
			taskPbs.setStageDescription(para[29]);
			if(!taskPbs.getStageDescription().equals("")&&!taskPbs.getStage().equals("")&&!taskPbs.getStageDescription().equals(StageMap.getStageMap().getStageDescription(taskPbs.getStage()))){
				StageDescription stageDescription=null;
				if(StageMap.getStageMap().getStageDescription(taskPbs.getStage())!=null){
					List<StageDescription> stageDescriptionList=stageDescriptionDao.findByStage(taskPbs.getStage());
					if(stageDescriptionList!=null && stageDescriptionList.size()>0){
						stageDescription = stageDescriptionList.get(0);
					}
				}else{
					stageDescription = new StageDescription();
					stageDescription.setStage(taskPbs.getStage());
				}
				stageDescription.setStageDescription(taskPbs.getStageDescription());
				stageDescriptionDao.save(stageDescription);
				StageMap.getStageMap().putStage(taskPbs.getStage(), taskPbs.getStageDescription());
			}
		}
		if (taskPbs.getWorkHourStatus() != null && taskPbs.getWorkHourStatus() == 1)
		{
			taskPbs = taskPbsDao.save(taskPbs);
			return taskDao.save(task);
		}
		// 查找徐勇的工时数据库，获取ST，比较计划工时
		aircraftModel = taskPbs.getAircraftType();
		taskPbs.setWorkHourStatus(0);
		if (aircraftModel == null)
		{
			taskPbs.setWorkhourNoReason("没有找到飞机机型");
			taskPbs = taskPbsDao.save(taskPbs);
			return taskDao.save(task);
		}
		searchParams.clear();
		searchParams.put("EQ_cardNumber", task.getCardNumber());
		searchParams.put("EQ_aircraftType", aircraftModel);
		specDbTaskPbs = DynamicSpecifications.bySearchFilter(filters.values(), DbTaskPbs.class);
		dbTaskPbsList = dbTaskPbsDao.findAll(specDbTaskPbs);
		if (dbTaskPbsList == null || dbTaskPbsList.size() < 1)
		{
			taskPbs.setWorkhourNoReason("没有找到数据库工卡");
			taskPbs = taskPbsDao.save(taskPbs);
			return taskDao.save(task);
		}
		dbTaskPbs = dbTaskPbsList.get(0);
		if (dbTaskPbs.getStage() != null && !dbTaskPbs.getStage().equals(""))
		{
			taskPbs.setStage(dbTaskPbs.getStage());
			taskPbs.setStageDescription(StageMap.getStageMap().getStageDescription(dbTaskPbs.getStage()));
		}
//		if (dbTaskPbs.getPlanHours().compareTo(taskPbs.getWorkHours()) > 0)
//		{
//			taskPbs.setWorkhourNoReason("数据库工卡工时大于SAP工卡工时");
//			taskPbs = taskPbsDao.save(taskPbs);
//			return taskDao.save(task);
//		}
		
		// 拷贝工时
		searchParams.clear();
		searchParams.put("EQ_taskId", dbTaskPbs.getId());
		filters = SearchFilter.parse(searchParams);
		specDbTaskHoursPbs = DynamicSpecifications.bySearchFilter(filters.values(), DbTaskHoursPbs.class);
		dbTaskHoursPbsList = dbTaskHoursPbsDao.findAll(specDbTaskHoursPbs);
		
		if(dbTaskHoursPbsList != null && dbTaskHoursPbsList.size() > 0)
		{
			taskPbs = taskPbsDao.save(taskPbs);
			taskPbs.setLastModifier(dbTaskPbs.getLastModifier());//之前都是DB工时导入，要求记录修改人，modify by 2016-11-23
			taskPbs.setWorkhourNoReason("");
			taskPbs.setLastModifyDate(new Timestamp(System.currentTimeMillis()));
			taskPbs.setDbWorkhours(dbTaskPbs.getPlanHours());
			
			//先删除原来的工卡工时
			taskHoursPbsDao.deleteByTaskId(taskPbs.getId());

			for (int j = 0; dbTaskHoursPbsList != null && j < dbTaskHoursPbsList.size(); j++) {
				DbTaskHoursPbs dbTaskHours = dbTaskHoursPbsList.get(j);
				TaskHoursPbs taskHours = new TaskHoursPbs();
				taskHours.setMechanicHours(dbTaskHours.getMechanicHours());
				taskHours.setInspectionHours(dbTaskHours.getInspectionHours());
				taskHours.setStage(dbTaskHours.getStage());
				taskHours.setWorkCenter(dbTaskHours.getWorkCenter());
				taskHours.setTaskId(taskPbs.getId());
				taskHoursPbsDao.save(taskHours);
			}

			taskPbs.setWorkHourStatus(1); //1：DB工时已导入
			taskPbs = taskPbsDao.save(taskPbs);
			
			/**工卡工时更新：工卡工时有变动更新工单工时 (即pbs_taskhours变动了，检查pbs_workhours)**/
			//changeTaskhoursUpdateWorkhours(taskPbs);
		} 
		else
		{
			taskPbs.setWorkhourNoReason("没有数据库工时数据");
			taskPbs = taskPbsDao.save(taskPbs);
		}
		return taskDao.save(task);
	}

	public Task findLast(String cardNumber, String group, String keyDate, String aircraftType)
	{
		if (cardNumber == null || aircraftType == null || group == null || keyDate == null || group.equals("")
		        || keyDate.equals("") || cardNumber.equals("") || aircraftType.equals(""))
		{
			return null;
		}
		searchParams.clear();
		searchParams.put("EQ_cardNumber", cardNumber);
		searchParams.put("EQ_groupId", group);
		searchParams.put("EQ_keyDate", keyDate);
		searchParams.put("EQ_aircraftType", aircraftType);
		filters = SearchFilter.parse(searchParams);
		specTask = DynamicSpecifications.bySearchFilter(filters.values(), Task.class);
		pageRequest = new PageRequest(0, 1, new Sort(Direction.DESC, "id"));
		page = taskDao.findAll(specTask, pageRequest);
		if (page.getContent() != null && page.getContent().size() > 0)
		{
			return page.getContent().get(0);
		}
		return null;
	}
	
	/**工卡工时更新：工卡工时有变动更新工单工时 (即pbs_taskhours变动了，检查pbs_workhours)**/
	private void changeTaskhoursUpdateWorkhours(TaskPbs taskPbs) {
		//通过工卡找工单(可能存在多个)
		RowMapper<WorkPbsVO> rowMapper =  ParameterizedBeanPropertyRowMapper.newInstance(WorkPbsVO.class);
		String sql = " select w.id_ as workId," +
				     "        w.wid_ as wid," +
				     "        r.rid_ as rid," +
				     "        r.aircrafttype_ as aircraftType," +
				     "        w.cardnumber_ as cardNumber" +
				     " from pbs_work w,pbs_revision r " +
				     " where r.rid_ = w.rid_ " +
				     "       and r.status_ != 'X' and r.topflag_ = '1' " +
				     "       and w.status_ != 'Complete' " +
				     "       and w.cardnumber_ = '"+taskPbs.getCardNumber()+"' "+ Global.getTypeByModel(taskPbs.getAircraftType());
		List<WorkPbsVO> workList = jdbcTemplate.query(sql, rowMapper);
		
		if(workList == null){
			return;
		}
		
		//最新工卡工时
		Map<String, TaskHoursPbs> newTaskHoursMap = new HashMap<String, TaskHoursPbs>();
		List<TaskHoursPbs> taskhourList = taskHoursPbsDao.findByTaskId(taskPbs.getId());
		for (int i = 0; taskhourList != null && i < taskhourList.size(); i++) {
			TaskHoursPbs taskhour = taskhourList.get(i);
			newTaskHoursMap.put((taskhour.getStage()+taskhour.getWorkCenter()).toLowerCase(), taskhour);
		}
		
		//原来的工单工时
		Map<Long, Map<String, WorkHoursPbs>> oldworkhoursMap = new HashMap<Long, Map<String, WorkHoursPbs>>();
		for(WorkPbsVO work : workList) {
			Map<String, WorkHoursPbs> workhhousMap = new HashMap<String, WorkHoursPbs>();
		    List<WorkHoursPbs> workhourList = workHoursPbsDao.findByWorkId(work.getWorkId());
		    for (int i = 0; workhourList != null && i < workhourList.size(); i++) {
		    	WorkHoursPbs workHoursPbs = workhourList.get(i);
		    	if(workhhousMap.get((workHoursPbs.getStage()+workHoursPbs.getWorkCenter()).toLowerCase()) == null){
		    		String key = (workHoursPbs.getStage()+workHoursPbs.getWorkCenter()).toLowerCase();
		    		workhhousMap.put(key, workHoursPbs);
		    	}
		    }
		    oldworkhoursMap.put(work.getWorkId(), workhhousMap);
		}
		
		List<WorkHoursPbs> workhoursList = new ArrayList<WorkHoursPbs>();
		
		for (Iterator iter = oldworkhoursMap.entrySet().iterator(); iter.hasNext();)  
        {  
            Entry entry = (Entry) iter.next();  
            Long workId = (Long) entry.getKey();  
            Map<String, List<WorkHoursPbs>> oldworkHoursMap = ( Map<String, List<WorkHoursPbs>>) entry.getValue(); //原始工单工时BytradeBystage
            
            //找Y：新增的 (在老的里面,找新的，找不到说明是Y)
            for (Iterator iternew = newTaskHoursMap.entrySet().iterator(); iternew.hasNext();)  
            {  
            	Entry entryNew = (Entry) iternew.next();
            	TaskHoursPbs tasHoursPbs = (TaskHoursPbs)entryNew.getValue();  
            	if(oldworkHoursMap.get(entryNew.getKey()+"") == null){
            		WorkHoursPbs obj = new WorkHoursPbs();
            		obj.setWorkId(workId);
            		obj.setStage(tasHoursPbs.getStage());
            		obj.setWorkCenter(tasHoursPbs.getWorkCenter());
            		obj.setMechanicHours(tasHoursPbs.getMechanicHours());
            		obj.setInspectionHours(tasHoursPbs.getInspectionHours());
            		obj.setModifyFlag("Y");
            		workhoursList.add(obj);
            	}
            }
            
            //找X：删除的 (在新的里面,找老的，找不到说明是X) (找到了,如果MI有变化也是Y)
            for (Iterator iterold = oldworkHoursMap.entrySet().iterator(); iterold.hasNext();)  
            {  
            	Entry entryOld = (Entry) iterold.next();
            	WorkHoursPbs workHoursPbs = (WorkHoursPbs)entryOld.getValue();  
            	if(newTaskHoursMap.get(entryOld.getKey()+"") == null){
            		workHoursPbs.setModifyFlag("X");
            		workhoursList.add(workHoursPbs);
            	}else{
            		TaskHoursPbs taskHoursPbs = newTaskHoursMap.get(entryOld.getKey()+"");
            		if(taskHoursPbs.getMechanicHours() != workHoursPbs.getMechanicHours() || 
            				taskHoursPbs.getInspectionHours() != workHoursPbs.getInspectionHours()){
            			workHoursPbs.setModifyFlag("Y");
            			workHoursPbs.setMechanicHours(taskHoursPbs.getMechanicHours());
            			workHoursPbs.setInspectionHours(taskHoursPbs.getInspectionHours());
                		workhoursList.add(workHoursPbs);
            		}
            	}
            }
        }
		
		//保存
		for (int i = 0; workhoursList != null && i < workhoursList.size(); i++) {
			workHoursPbsDao.save(workhoursList.get(i));
			
			List<WorkPbs> workPbs = workPbsDao.findById(workhoursList.get(i).getWorkId());
			if(workPbs != null && workPbs.size() > 0){
				WorkPbs work = workPbs.get(0);
				work.setWorkHourStatus(1);
				workPbsDao.save(work);
			}
		}
	}
}
