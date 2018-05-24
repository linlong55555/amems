package com.eray.pbs.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
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

import com.eray.pbs.dao.DbTaskAccessPbsDao;
import com.eray.pbs.dao.DbTaskDao;
import com.eray.pbs.dao.DbTaskHoursPbsDao;
import com.eray.pbs.dao.DbTaskPbsDao;
import com.eray.pbs.dao.DbTaskZonePbsDao;
import com.eray.pbs.dao.TaskHoursPbsDao;
import com.eray.pbs.dao.TaskPbsDao;
import com.eray.pbs.dao.WorkHoursPbsDao;
import com.eray.pbs.dao.WorkPbsDao;
import com.eray.pbs.po.DbTask;
import com.eray.pbs.po.DbTaskAccessPbs;
import com.eray.pbs.po.DbTaskHoursPbs;
import com.eray.pbs.po.DbTaskPbs;
import com.eray.pbs.po.DbTaskZonePbs;
import com.eray.pbs.po.Group;
import com.eray.pbs.po.MIConfigBase;
import com.eray.pbs.po.TaskHoursPbs;
import com.eray.pbs.po.TaskPbs;
import com.eray.pbs.po.WorkCenter;
import com.eray.pbs.po.WorkHoursPbs;
import com.eray.pbs.po.WorkPbs;
import com.eray.pbs.util.StageMap;
import com.eray.pbs.vo.WorkPbsVO;
import com.eray.util.Global;
import com.eray.util.jpa.DynamicSpecifications;
import com.eray.util.jpa.SearchFilter;
import com.eray.util.poi.ExcelUtil;

@Component
@Transactional(readOnly = true)
public class DbTaskService {
	@Autowired
	private DbTaskDao dbTaskDao;
	@Autowired
	private DbTaskPbsDao dbTaskPbsDao;
	@Autowired
	private DbTaskAccessPbsDao dbTaskAccessPbsDao;
	@Autowired
	private DbTaskHoursPbsDao dbTaskHoursPbsDao;
	@Autowired
	private DbTaskZonePbsDao dbTaskZonePbsDao;
	@Autowired
	private TaskPbsDao taskPbsDao;
	@Autowired
	private TaskHoursPbsDao taskHoursPbsDao;
	@Autowired
	private WorkHoursPbsDao workHoursPbsDao;
	@Autowired
	private WorkPbsDao workPbsDao;
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	private StringBuilder sql;
	private Map<String, Object> searchParams = new HashMap<String, Object>();
	private Map<String, SearchFilter> filters;
	private Specification<DbTask> specDbTask;
	private List<DbTaskPbs> dbTaskPbsList;
	private PageRequest pageRequest;
	private Page<DbTask> page;

	private DbTaskPbs dbTaskPbs;
	private Specification<DbTaskPbs> specDbTaskPbs;

	private Specification<TaskPbs> specTaskPbs;

	private String[] rowArray;
	private String[] colArray;
	private String minStage = ""; //每行最小Stage

	private DbTaskAccessPbs dbTaskAccessPbs;

	private DbTaskZonePbs dbTaskZonePbs;

	private String access;
	private String zone;
	private WorkCenter workCenter;
	private Group group;

	private Specification<DbTaskHoursPbs> specDbTaskHoursPbs;
	private List<DbTaskHoursPbs> dbTaskHoursPbsList;

	@Transactional(readOnly = false)
	public DbTask save(DbTask dbTask, Row row,Map<String, 
			Group> groupMap,Map<String, WorkCenter> workcenterNameMap, 
			Map<Long, WorkCenter> workcenterIdMap,Map<String, MIConfigBase> miConfigMap,
			Map<String, WorkCenter> workcenterMap,String handleMan,Date handleDate) {
		if (dbTask == null) {
			return dbTask;
		}
		
		BigDecimal planHours = new BigDecimal(0); //总工时
				
		//判断数据库工卡是否已经存在
		searchParams.clear();
		searchParams.put("EQ_cardNumber", dbTask.getCardNumber());
		searchParams.put("EQ_aircraftType", dbTask.getAircraftType());
		filters = SearchFilter.parse(searchParams);
		specDbTaskPbs = DynamicSpecifications.bySearchFilter(filters.values(),DbTaskPbs.class);
		dbTaskPbsList = dbTaskPbsDao.findAll(specDbTaskPbs);
		if (dbTaskPbsList != null && dbTaskPbsList.size() > 0) {
			dbTaskPbs = dbTaskPbsList.get(0);
		}else {
			dbTaskPbs = new DbTaskPbs();
			dbTaskPbs.setCardNumber(dbTask.getCardNumber());
			dbTaskPbs.setAircraftType(dbTask.getAircraftType());
		}
		
		int coloumNum= row.getPhysicalNumberOfCells();//获得总列数
		/*if((coloumNum-4) % 3 != 0){
			return dbTask; //excel模板错误
		}*/
	
		minStage = ""; //没遍历一行,都要清空上次最小stage
		//判断阶段、工作组
		for(int i = 4; i < coloumNum; i=i+3) {
			if (ExcelUtil.getCellValue(row.getCell(i)) == null
					|| ExcelUtil.getCellValue(row.getCell(i)).equals("无卡") 
					|| ExcelUtil.getCellValue(row.getCell(i)).equals("")) {
				
				if(i==4) {
					dbTask.setErrorMsg("第"+row.getRowNum()+"行,第一个Stage没有填写"); //判断第一个stage是否存在
					return dbTask;
				}else{
					row.getCell(i).setCellValue(ExcelUtil.getCellValue(row.getCell(i-3))); //如果第二个stage为空则参照前一个,第三个参照第二个，以此类推
				}
			}
			
			setMinStage(ExcelUtil.getCellValue(row.getCell(i)).toUpperCase()); //设置最小stage
			
			if (ExcelUtil.getCellValue(row.getCell(i+1)) != null 
					&& !ExcelUtil.getCellValue(row.getCell(i+1)).equals("")) {
				//校验WorkCenter是否有效
				workCenter = workcenterNameMap.get(ExcelUtil.getCellValue(row.getCell(i+1)));
				if (workCenter == null) {
					group = groupMap.get(ExcelUtil.getCellValue(row.getCell(i+1)));
					if (group != null) {
						workCenter = workcenterIdMap.get(group.getWorkCenterId());
					}
				}
				if (workCenter == null || workCenter.getWorkCenter().equals("")) {
					
					dbTask.setErrorMsg("第"+row.getRowNum()+"行,第"+i+"列,组不存在");
					return dbTask;  //没有填写组
				}
				
				workcenterMap.put(ExcelUtil.getCellValue(row.getCell(i+1)), workCenter); //全称
			}
			
			//如果mh为空则按0处理
			String mh = ExcelUtil.getCellValue(row.getCell(i+2));
			if(mh == null || mh.equals("") || mh.equals("NO CARD")){
				mh = "0";
			}
			planHours = planHours.add(new BigDecimal(mh));//总工时
		}
		dbTaskPbs.setPlanHours(planHours);
		dbTaskPbs.setStage(minStage.toUpperCase());  //取最小Stage最为工单的Stage
		
		dbTaskPbs.setLastModifier(handleMan);//记录操作人 add 2016-11-24
		dbTaskPbs.setLastModifyDate(new Timestamp(handleDate.getTime()));//记录操时间 add 2016-11-24
		
		//保存数据库工卡pbs_dbtask
		dbTaskPbs = dbTaskPbsDao.save(dbTaskPbs);
		
		//pbs_dbtaskzone保存区域
		zone = ExcelUtil.getCellValue(row.getCell(1));
		if (zone != null && !zone.equals("") && !zone.equals("无盖板") && !zone.equals("无卡")) {
			dbTaskZonePbsDao.deleteByTaskId(dbTaskPbs.getId());
			rowArray = zone.split("\n");
			for (int i = 0; i < rowArray.length; i++) {
				colArray = rowArray[i].split(" ");
				for (int j = 0; j < colArray.length; j++) {
					if (colArray[j].endsWith(".00")) {
						colArray[j] = colArray[j].substring(0,colArray[j].length() - 3);
					}
					if (colArray[j].equals("") || colArray[j].equals("无卡")) {
						continue;
					}
					dbTaskZonePbs = new DbTaskZonePbs();
					dbTaskZonePbs.setZone(colArray[j]);
					dbTaskZonePbs.setTaskId(dbTaskPbs.getId());
					dbTaskZonePbsDao.save(dbTaskZonePbs);
				}
			}
		}
		
		//pbs_dbtaskaccess保存盖板
		access = ExcelUtil.getCellValue(row.getCell(2));
		if (access != null && !access.equals("") && !access.equals("无盖板") && !access.equals("无卡")) {
			dbTaskAccessPbsDao.deleteByTaskId(dbTaskPbs.getId());
			rowArray = access.split("\n");
			for (int i = 0; i < rowArray.length; i++) {
				colArray = rowArray[i].split(" ");
				for (int j = 0; j < colArray.length; j++) {
					if (colArray[j].equals("") || colArray[j].equals("无盖板")) {
						continue;
					}
					dbTaskAccessPbs = new DbTaskAccessPbs();
					dbTaskAccessPbs.setAccessNo(colArray[j]);
					dbTaskAccessPbs.setTaskId(dbTaskPbs.getId());
					dbTaskAccessPbsDao.save(dbTaskAccessPbs);
				}
			}
		}
		
		Map<String, DbTaskHoursPbs> dbTaskHoursMap = new HashMap<String, DbTaskHoursPbs>();
		//pbs_dbtaskhours保存数据库工卡工时, 先删除,再建立
		dbTaskHoursPbsDao.deleteByTaskId(dbTaskPbs.getId()); 
		for(int k = 4; k < coloumNum; k=k+3) {
			//建立新的工卡工时关系(必须stage、WorkCenter、MH都存在), 阶段和工作组相同合并MI值
			if(StringUtils.isNotEmpty(ExcelUtil.getCellValue(row.getCell(k)))
					&& StringUtils.isNotEmpty(ExcelUtil.getCellValue(row.getCell(k+1)))
					&& StringUtils.isNotEmpty(ExcelUtil.getCellValue(row.getCell(k+2)))){
				
				String key = ExcelUtil.getCellValue(row.getCell(k)).toUpperCase() + ""+ ExcelUtil.getCellValue(row.getCell(k+1)).toUpperCase(); //阶段和组
				Long taskId = dbTaskPbs.getId();
				String stage = ExcelUtil.getCellValue(row.getCell(k)).toUpperCase();
				String workCenter = ExcelUtil.getCellValue(row.getCell(k+1)).toUpperCase();
				BigDecimal MH = new BigDecimal(ExcelUtil.getCellValue(row.getCell(k+2)));
				
				if(dbTaskHoursMap.get(key) != null){
					DbTaskHoursPbs dbTaskHours = dbTaskHoursMap.get(key);
					dbTaskHours.setMechanicHours(dbTaskHours.getMechanicHours().add(MH));
					dbTaskHours.setInspectionHours(dbTaskHours.getInspectionHours().add(MH));
					dbTaskHoursMap.put(key, dbTaskHours);
				}else{
					DbTaskHoursPbs dbTaskHours = new DbTaskHoursPbs();
					dbTaskHours.setTaskId(taskId);
					dbTaskHours.setStage(stage);
					dbTaskHours.setWorkCenter(workcenterMap.get(workCenter).getWorkCenter()); //通过简称找全称
					dbTaskHours.setMechanicHours(MH);
					dbTaskHours.setInspectionHours(MH);
					dbTaskHoursMap.put(key, dbTaskHours);
				}
			}
		}
		
		if(dbTaskHoursMap != null && dbTaskHoursMap.size() > 0){
			for (Iterator iter = dbTaskHoursMap.entrySet().iterator(); iter.hasNext();)  
	        {  
	            Entry entry = (Entry) iter.next();  
	            DbTaskHoursPbs taskHourObj = (DbTaskHoursPbs) entry.getValue();  
	            
	            //获取MI配置比率默认M:0.9 I:0.1
	            double MConfig = miConfigMap.get(taskHourObj.getWorkCenter()) == null?0.9:miConfigMap.get(taskHourObj.getWorkCenter()).getMechanichours();
	            double IConfig = miConfigMap.get(taskHourObj.getWorkCenter()) == null?0.1:miConfigMap.get(taskHourObj.getWorkCenter()).getInspectionhours();
	            
	            //根据MI比率,算出M/I的值
	            BigDecimal M = taskHourObj.getMechanicHours().multiply(new BigDecimal(MConfig)).setScale(3, BigDecimal.ROUND_HALF_UP);
				BigDecimal I = taskHourObj.getInspectionHours().multiply(new BigDecimal(IConfig)).setScale(3, BigDecimal.ROUND_HALF_UP);
				
				taskHourObj.setMechanicHours(M);
				taskHourObj.setInspectionHours(I);
				
				dbTaskHoursPbsDao.save(taskHourObj); 
	        }  
		}

		//搜索所有关联此数据库工卡的工卡
		searchParams.clear();
		searchParams.put("EQ_cardNumber", dbTask.getCardNumber());
		searchParams.put("EQ_aircraftType", dbTask.getAircraftType());
		filters = SearchFilter.parse(searchParams);
		specTaskPbs = DynamicSpecifications.bySearchFilter(filters.values(),TaskPbs.class);
		List<TaskPbs> taskPbsList = taskPbsDao.findAll(specTaskPbs);
		
		// 覆盖工卡工时
		for (int i = 0; taskPbsList != null && i < taskPbsList.size(); i++) {
			TaskPbs taskPbs = taskPbsList.get(i);
			taskPbs.setStage(dbTaskPbs.getStage()); //最小Stage
			taskPbs.setStageDescription(StageMap.getStageMap().getStageDescription(dbTaskPbs.getStage()));
			taskPbs.setLastModifier(handleMan); //之前都是DB工时导入，要求记录修改人，modify by 2016-11-23
			taskPbs.setWorkhourNoReason("");
			taskPbs.setLastModifyDate(new Timestamp(System.currentTimeMillis()));
			taskPbs.setDbWorkhours(dbTaskPbs.getPlanHours());
			
			
			//先删除原来的工卡工时
			taskHoursPbsDao.deleteByTaskId(taskPbs.getId());
			
			searchParams.clear();
			searchParams.put("EQ_taskId", dbTaskPbs.getId());
			filters = SearchFilter.parse(searchParams);
			specDbTaskHoursPbs = DynamicSpecifications.bySearchFilter(filters.values(), DbTaskHoursPbs.class);
			dbTaskHoursPbsList = dbTaskHoursPbsDao.findAll(specDbTaskHoursPbs);

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
		
		return dbTaskDao.save(dbTask);
	}
	
	//取每行最小Stage
	private void setMinStage(String stageNameNow) {
		
		if("".equals(minStage)){
			minStage = stageNameNow;
		}else{
			int minStageOld = Integer.valueOf(minStage.replace("ST", ""));
			int minStageNow = Integer.valueOf(stageNameNow.replace("ST", ""));
			
			if(minStageNow < minStageOld){
				minStage = stageNameNow;
			}
		}
	}

	public DbTask findLast(String cardNumber, String aircraftType) {
		searchParams.clear();
		searchParams.put("EQ_cardNumber", cardNumber);
		searchParams.put("EQ_aircraftType", aircraftType);
		filters = SearchFilter.parse(searchParams);
		specDbTask = DynamicSpecifications.bySearchFilter(filters.values(),DbTask.class);
		pageRequest = new PageRequest(0, 1, new Sort(Direction.DESC, "id"));
		page = dbTaskDao.findAll(specDbTask, pageRequest);
		if (page.getContent() != null && page.getContent().size() > 0) {
			return page.getContent().get(0);
		}
		return null;
	}

	@Transactional(readOnly = false)
	public void refreshNoStageRoutingWork() {
		// 刷新所有Stage为空的NRC工单
		sql = new StringBuilder();
		sql.append("update pbs_work set pbs_work.stage_ = pbs_dbtask.stage_ ");
		sql.append("from pbs_work,pbs_dbtask,pbs_revision where pbs_work.rid_=pbs_revision.rid_ ");
		sql.append("and pbs_work.cardnumber_=pbs_dbtask.cardnumber_ and pbs_work.controlnumber_ like 'R%'");
		sql.append("and pbs_dbtask.aircrafttype_=SUBSTRING('B'+pbs_revision.aircrafttype_,1,4) and (pbs_work.stage_ = '' or pbs_work.stage_ is null) and pbs_dbtask.aircrafttype_<>'B737'");
		jdbcTemplate.execute(sql.toString());
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
