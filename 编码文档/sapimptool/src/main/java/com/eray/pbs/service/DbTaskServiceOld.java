package com.eray.pbs.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.DbTaskAccessPbsDao;
import com.eray.pbs.dao.DbTaskDao;
import com.eray.pbs.dao.DbTaskHoursPbsDao;
import com.eray.pbs.dao.DbTaskPbsDao;
import com.eray.pbs.dao.DbTaskZonePbsDao;
import com.eray.pbs.dao.GroupDao;
import com.eray.pbs.dao.TaskHoursPbsDao;
import com.eray.pbs.dao.TaskPbsDao;
import com.eray.pbs.dao.WorkCenterDao;
import com.eray.pbs.po.DbTask;
import com.eray.pbs.po.DbTaskAccessPbs;
import com.eray.pbs.po.DbTaskHoursPbs;
import com.eray.pbs.po.DbTaskPbs;
import com.eray.pbs.po.DbTaskZonePbs;
import com.eray.pbs.po.Group;
import com.eray.pbs.po.TaskHoursPbs;
import com.eray.pbs.po.TaskPbs;
import com.eray.pbs.po.WorkCenter;
import com.eray.pbs.util.StageMap;
import com.eray.util.jpa.DynamicSpecifications;
import com.eray.util.jpa.SearchFilter;
import com.eray.util.poi.ExcelUtil;

@Component
@Transactional(readOnly = true)
public class DbTaskServiceOld {
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
	private WorkCenterDao workCenterDao;
	@Autowired
	private GroupDao groupDao;
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
	private TaskPbs taskPbs;

	private String planHours;
	private BigDecimal bdPlanHours;
	private String[] rowArray;
	private String[] colArray;

	private String stage;

	private DbTaskAccessPbs dbTaskAccessPbs;

	private DbTaskZonePbs dbTaskZonePbs;

	private String workCenterString;
	private String mec;
	private String insp;

	private String access;
	private String zone;

	private BigDecimal zero = new BigDecimal("0");

	private DbTaskHoursPbs dbTaskHoursPbs;

	private Specification<DbTaskHoursPbs> specDbTaskHoursPbs;
	private List<DbTaskHoursPbs> dbTaskHoursPbsList;
	private WorkCenter workCenter;
	private Group group;
	private TaskHoursPbs tempTaskHours;
	private DbTaskHoursPbs tempDbTaskHours;

	private String remark;

	@Transactional(readOnly = false)
	public DbTask save(DbTask dbTask, Row row) {
		if (dbTask == null) {
			return dbTask;
		}
		searchParams.clear();
		searchParams.put("EQ_cardNumber", dbTask.getCardNumber());
		searchParams.put("EQ_aircraftType", dbTask.getAircraftType());
		filters = SearchFilter.parse(searchParams);
		specDbTaskPbs = DynamicSpecifications.bySearchFilter(filters.values(),
				DbTaskPbs.class);
		dbTaskPbsList = dbTaskPbsDao.findAll(specDbTaskPbs);
		if (dbTaskPbsList != null && dbTaskPbsList.size() > 0) {
			dbTaskPbs = dbTaskPbsList.get(0);
		} else {
			dbTaskPbs = new DbTaskPbs();
			dbTaskPbs.setCardNumber(dbTask.getCardNumber());
			dbTaskPbs.setAircraftType(dbTask.getAircraftType());
		}
		stage = ExcelUtil.getCellValue(row.getCell(3));
		if (stage.equals("无卡")) {
			return dbTask;
		}
		dbTaskPbs.setStage(stage.toUpperCase());
		planHours = ExcelUtil.getCellValue(row.getCell(24));
		if ("NO CARD".equals(planHours)) {
			return dbTask;
		}
		if (planHours != null && !planHours.equals("")) {
			try {
				bdPlanHours = new BigDecimal(planHours);
			} catch (Exception e) {
				return dbTask;
			}
			bdPlanHours.setScale(1, BigDecimal.ROUND_HALF_UP);
			dbTaskPbs.setPlanHours(bdPlanHours);
		} else {
			dbTaskPbs.setPlanHours(zero);
		}
		dbTaskPbs = dbTaskPbsDao.save(dbTaskPbs);

		access = ExcelUtil.getCellValue(row.getCell(2));
		if (access != null && !access.equals("") && !access.equals("无盖板")
				&& !access.equals("无卡")) {
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

		zone = ExcelUtil.getCellValue(row.getCell(1));
		if (zone != null && !zone.equals("") && !zone.equals("无盖板")
				&& !zone.equals("无卡")) {
			dbTaskZonePbsDao.deleteByTaskId(dbTaskPbs.getId());
			rowArray = zone.split("\n");
			for (int i = 0; i < rowArray.length; i++) {
				colArray = rowArray[i].split(" ");
				for (int j = 0; j < colArray.length; j++) {
					if (colArray[j].endsWith(".00")) {
						colArray[j] = colArray[j].substring(0,
								colArray[j].length() - 3);
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

		BigDecimal totalPlanHours = BigDecimal.ZERO;

		dbTaskHoursPbsDao.deleteByTaskId(dbTaskPbs.getId());
		for (int i = 4; i <= 20; i = i + 4) {
			workCenterString = ExcelUtil.getCellValue(row.getCell(i));
			if (workCenterString == null || workCenterString.equals("")) {
				break;
			}
			dbTaskHoursPbs = new DbTaskHoursPbs();
			dbTaskHoursPbs.setWorkCenter(workCenterString);
			dbTaskHoursPbs.setStage(stage);
			dbTaskHoursPbs.setTaskId(dbTaskPbs.getId());
			mec = ExcelUtil.getCellValue(row.getCell(i + 1));
			if (mec == null || mec.equals("") || mec.equals("NO CARD")) {
				dbTaskHoursPbs.setMechanicHours(zero);
			} else {
				try {
					dbTaskHoursPbs.setMechanicHours(new BigDecimal(mec));
					totalPlanHours = totalPlanHours.add(dbTaskHoursPbs
							.getMechanicHours());
				} catch (Exception e) {
					return dbTask;
				}
			}
			insp = ExcelUtil.getCellValue(row.getCell(i + 2));
			if (insp == null || insp.equals("") || insp.equals("NO CARD")) {
				dbTaskHoursPbs.setInspectionHours(zero);
			} else {
				try {
					dbTaskHoursPbs.setInspectionHours(new BigDecimal(insp));
					totalPlanHours = totalPlanHours.add(dbTaskHoursPbs
							.getInspectionHours());
				} catch (Exception e) {
					return dbTask;
				}
			}
			dbTaskHoursPbsDao.save(dbTaskHoursPbs);
		}

		dbTaskPbs.setPlanHours(totalPlanHours);
		dbTaskPbs = dbTaskPbsDao.save(dbTaskPbs);

		remark = ExcelUtil.getCellValue(row.getCell(25));
		//TODO check dbTaskPbs & remark is already exist;exist==1

		//搜索所有关联此数据库工卡的工卡
		searchParams.clear();
		searchParams.put("EQ_cardNumber", dbTask.getCardNumber());
		searchParams.put("EQ_aircraftType", dbTask.getAircraftType());
		filters = SearchFilter.parse(searchParams);
		specTaskPbs = DynamicSpecifications.bySearchFilter(filters.values(),TaskPbs.class);
		List<TaskPbs> taskPbsList = taskPbsDao.findAll(specTaskPbs);
		// 覆盖工卡工时
		for (int i = 0; i < taskPbsList.size(); i++) {
			taskPbs = taskPbsList.get(i);
			taskPbs.setStage(dbTaskPbs.getStage());
			taskPbs.setStageDescription(StageMap.getStageMap().getStageDescription(dbTaskPbs.getStage()));
			taskHoursPbsDao.deleteByTaskId(taskPbs.getId());
			
			searchParams.clear();
			searchParams.put("EQ_taskId", dbTaskPbs.getId());
			filters = SearchFilter.parse(searchParams);
			specDbTaskHoursPbs = DynamicSpecifications.bySearchFilter(filters.values(), DbTaskHoursPbs.class);
			dbTaskHoursPbsList = dbTaskHoursPbsDao.findAll(specDbTaskHoursPbs);
			if (dbTaskHoursPbsList != null && dbTaskHoursPbsList.size() > 0) {
				boolean status = true;
				Map<String, DbTaskHoursPbs> map = new HashMap<String, DbTaskHoursPbs>();
				for (int j = 0; j < dbTaskHoursPbsList.size(); j++) {
					dbTaskHoursPbs = dbTaskHoursPbsList.get(j);
					workCenter = workCenterDao.findByName(dbTaskHoursPbs.getWorkCenter());
					if (workCenter == null) {
						group = groupDao.findByName(dbTaskHoursPbs.getWorkCenter());
						if (group != null) {
							workCenter = workCenterDao.findOne(group.getWorkCenterId());
						}
					}
					if (workCenter == null || workCenter.getWorkCenter().equals("")) {
						taskPbs.setWorkhourNoReason("数据库工卡Workcenter不匹配");
						status = false;
						break;
					}
					if (map.get(workCenter.getWorkCenter()) != null) {
						tempDbTaskHours = map.get(workCenter.getWorkCenter());
						tempDbTaskHours.setMechanicHours(tempDbTaskHours.getMechanicHours().add(dbTaskHoursPbs.getMechanicHours()));
						tempDbTaskHours.setInspectionHours(tempDbTaskHours.getInspectionHours().add(dbTaskHoursPbs.getInspectionHours()));
						map.put(workCenter.getWorkCenter(), tempDbTaskHours);
					} else {
						map.put(workCenter.getWorkCenter(), dbTaskHoursPbs);
					}
				}
				if (status) {
					taskPbs.setLastModifier("DB工时导入");
					taskPbs.setLastModifyDate(new Timestamp(System
							.currentTimeMillis()));
					taskPbs.setDbWorkhours(dbTaskPbs.getPlanHours());
					for (Map.Entry<String, DbTaskHoursPbs> entry : map.entrySet()) {
						tempTaskHours = new TaskHoursPbs();
						tempTaskHours.setMechanicHours(entry.getValue().getMechanicHours());
						tempTaskHours.setInspectionHours(entry.getValue().getInspectionHours());
						tempTaskHours.setStage(entry.getValue().getStage());
						tempTaskHours.setWorkCenter(entry.getKey());
						tempTaskHours.setTaskId(taskPbs.getId());
						taskHoursPbsDao.save(tempTaskHours);
					}
					taskPbs.setWorkHourStatus(1);
				}
				taskPbs = taskPbsDao.save(taskPbs);
			}
		}
		return dbTaskDao.save(dbTask);
	}

	public DbTask findLast(String cardNumber, String aircraftType) {
		searchParams.clear();
		searchParams.put("EQ_cardNumber", cardNumber);
		searchParams.put("EQ_aircraftType", aircraftType);
		filters = SearchFilter.parse(searchParams);
		specDbTask = DynamicSpecifications.bySearchFilter(filters.values(),
				DbTask.class);
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
}
