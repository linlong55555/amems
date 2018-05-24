package com.eray.pbs.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.OperationDao;
import com.eray.pbs.dao.OperationPbsDao;
import com.eray.pbs.dao.PartnerDao;
import com.eray.pbs.dao.PartnerPbsDao;
import com.eray.pbs.dao.RevisionPbsDao;
import com.eray.pbs.dao.TaskHoursPbsDao;
import com.eray.pbs.dao.TaskPbsDao;
import com.eray.pbs.dao.WorkDao;
import com.eray.pbs.dao.WorkHourStatusLogDao;
import com.eray.pbs.dao.WorkHoursPbsDao;
import com.eray.pbs.dao.WorkPbsDao;
import com.eray.pbs.po.Operation;
import com.eray.pbs.po.OperationPbs;
import com.eray.pbs.po.Partner;
import com.eray.pbs.po.PartnerPbs;
import com.eray.pbs.po.RevisionPbs;
import com.eray.pbs.po.TaskHoursPbs;
import com.eray.pbs.po.TaskPbs;
import com.eray.pbs.po.Work;
import com.eray.pbs.po.WorkHourStatusLog;
import com.eray.pbs.po.WorkHoursPbs;
import com.eray.pbs.po.WorkPbs;
import com.eray.pbs.util.idoc.E2ORHDR_LTXT;
import com.eray.pbs.util.idoc.E2OROPR;
import com.eray.pbs.util.idoc.IDoc;
import com.eray.util.Global;
import com.eray.util.date.DateCalculation;
import com.eray.util.jpa.DynamicSpecifications;
import com.eray.util.jpa.SearchFilter;

/**
 * 同步工单
 *
 */
@Component
@Transactional(readOnly = true)
public class WorkService {
	private static final Logger logger = LoggerFactory
			.getLogger(WorkService.class);
	@Autowired
	private WorkDao workDao;
	@Autowired
	private WorkPbsDao workPbsDao;
	@Autowired
	private PartnerDao partnerDao;
	@Autowired
	private PartnerPbsDao partnerPbsDao;
	@Autowired
	private OperationDao operationDao;
	@Autowired
	private OperationPbsDao operationPbsDao;
	@Autowired
	private TaskPbsDao taskPbsDao;
	@Autowired
	private RevisionPbsDao revisionPbsDao;
	@Autowired
	private TaskHoursPbsDao taskHoursPbsDao;
	@Autowired
	private WorkHoursPbsDao workHoursPbsDao;
	//@Autowired
	//private DbTaskPbsDao dbTaskPbsDao;
	@Autowired
	private WorkHourStatusLogDao workHourStatusLogDao; //记录工时拷贝的标识位
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	private Map<String, Object> searchParams = new HashMap<String, Object>();
	private Map<String, SearchFilter> filters;

	private String workId;
	private String aircraftType;

	private Specification<WorkPbs> specWorkPbs;
	private List<WorkPbs> workPbsList;
	private WorkPbs workPbs;

	//private List<DbTaskPbs> dbTaskPbsList;
	//private Specification<DbTaskPbs> specDbTaskPbs;

	private Specification<RevisionPbs> specRevisionPbs;
	private List<RevisionPbs> revisionPbsList;

	private Partner partner;
	private String customerId;
	private Specification<PartnerPbs> specPartnerPbs;
	private List<PartnerPbs> partnerPbsList;
	private PartnerPbs partnerPbs;

	private Operation operation;
	private BigDecimal workhours;  //收费工时
	private BigDecimal duration;  //计划工时
	private int relativdays;
	private Specification<OperationPbs> specOperationPbs;
	private List<OperationPbs> operationPbsList;
	private OperationPbs operationPbs;

	private Specification<TaskPbs> specTaskPbs;
	private List<TaskPbs> taskPbsList;
	private TaskPbs taskPbs;

	private Specification<TaskHoursPbs> specTaskHoursPbs;
	private List<TaskHoursPbs> taskHoursPbsList;
	private TaskHoursPbs taskHoursPbs;

	private WorkHoursPbs workHoursPbs;

	private RevisionPbs revisionPbs;

	private PageRequest pageRequest;
	private Page<WorkPbs> page;

	private DateFormat sapDF = new SimpleDateFormat("yyyyMMddHHmmss");
	private BigDecimal pointOne = new BigDecimal("0.1");

	private String rid;

	@Transactional(readOnly = false)
	public Work save(Work work) {
		return workDao.save(work);
	}

	@Transactional(readOnly = false)
	public boolean saveIDocWork(Work work, IDoc iDoc) {
		if (work == null || iDoc == null) {
			return false;
		}
		work.setWorkId(iDoc.getE2ORHDR().getAUFNR());
		work.setTaskId(iDoc.getZZCARDNO().getZZCARDNO());
		work.setControlNo(iDoc.getZZCARDNO().getZZCONTRNO());
		work.setFuncLocation(iDoc.getE2ORHDR().getSTRNO());
		work.setOrderType(iDoc.getE2ORHDR().getAUART());
		work.setPlant(iDoc.getE2ORHDR().getWERKS());
		work.setWorkCenter(iDoc.getE2ORHDR().getARBPL());
		work.setRevisionId(iDoc.getZZREVISON().getREVNR());
		work.setCompanyCode(iDoc.getE2NTHDR().getBUKRS());
		work.setScheduledStartDate(iDoc.getE2ORHDR().getGSTRS());
		work.setScheduledStartTime(iDoc.getE2ORHDR().getGSUZS());
		work.setScheduledFinishDate(iDoc.getE2ORHDR().getGLTRS());
		work.setScheduledFinishTime(iDoc.getE2ORHDR().getGLUZS());
		work.setEarliestStartDate(iDoc.getE2ORHDR().getGSTRP());
		work.setEarliestStartTime(iDoc.getE2ORHDR().getGSUZP());
		work.setLatestFinishDate(iDoc.getE2ORHDR().getGLTRP());
		work.setLatestFinishTime(iDoc.getE2ORHDR().getGLUZP());
		work.setFuncLocationStructureIndicator(iDoc.getZE1ORHDR_EXT()
				.getTPLKZ());
		String description = "";
		for (E2ORHDR_LTXT e2ORHDR_LTXT : iDoc.getE2ORHDR_LTXTList()) {
			if (e2ORHDR_LTXT.getTDLINE() != null) {
				if ("".equals(description)) {
					description = description + e2ORHDR_LTXT.getTDLINE();
				} else {
					description = description + " " + e2ORHDR_LTXT.getTDLINE();
				}
			}
		}
		if (description.equals("")) {
			work.setDescription(iDoc.getE2ORHDR().getKTEXT());
		} else {
			work.setDescription(description);
		}
		logger.info("work description:{}", work.getDescription());
		if (work.getWorkId() == null || work.getWorkId().equals("")) {
			return false;
		}
		searchParams.clear();
		workId = work.getWorkId();
		workId = Global.removeZeroBefore(workId);
		searchParams.put("EQ_wid", workId);
		filters = SearchFilter.parse(searchParams);
		specWorkPbs = DynamicSpecifications.bySearchFilter(filters.values(),
				WorkPbs.class);
		workPbsList = workPbsDao.findAll(specWorkPbs);
		if (workPbsList != null && workPbsList.size() > 0) {
			workPbs = workPbsList.get(0);
			if (!work.getWorkId().endsWith(workPbs.getWid())) {
				logger.error("Search Error:Import File Work Order:"
						+ work.getWorkId() + ";search ID:" + workId
						+ ";findPbsWorkOrder:" + workPbs.toString());
				return false;
			}
			
		} else {
			workPbs = new WorkPbs();
			workPbs.setWid(workId);
			workPbs.setActualHourChanged("0"); //spent hour工时默认写入0,0表示为发生变化,1表示有变化
		}
		workPbs.setCardNumber(work.getTaskId());
		workPbs.setDescription(work.getDescription());
		workPbs.setType(work.getOrderType());
		workPbs.setPlant(work.getPlant());
		workPbs.setMainWorkCenter(work.getWorkCenter());
		try {
			workPbs.setBasicStartDate(new Timestamp((sapDF.parse(work
					.getScheduledStartDate() + work.getScheduledStartTime()))
					.getTime()));
			workPbs.setBasicFinishDate(new Timestamp((sapDF.parse(work
					.getScheduledFinishDate() + work.getScheduledFinishTime()))
					.getTime()));
			workPbs.setScheduledStartDate(workPbs.getBasicStartDate());
			workPbs.setScheduledFinishDate(workPbs.getBasicFinishDate());
			if (workPbs.getBasicStartDate() == null) {
				workPbs.setActualStartDate(workPbs.getBasicStartDate());
			}
			if (workPbs.getBasicFinishDate() == null) {
				workPbs.setActualFinishDate(workPbs.getBasicFinishDate());
			}
		} catch (Exception e) {
		}
		rid = work.getRevisionId();
		rid = Global.removeZeroBefore(rid);

		if (rid != null && !rid.trim().equals("") && workPbs.getRid() != null
				&& !workPbs.getRid().trim().equals("")
				&& !rid.equals(workPbs.getRid())) {
			logger.error("workorder's rid is not allowed to be changed.orignal rid:"
					+ rid + ";current rid:" + workPbs.getRid());
			return false;
		} else {
			workPbs.setRid(rid);
		}
		// workPbs.setRid(rid);

		workPbs.setControlNumber(work.getControlNo());
		if (workPbs.getRid() == null || "".equals(workPbs.getRid())) {
			logger.info(" 239 -->> rid is null:{}",  workPbs.toString());
			workPbs = workPbsDao.save(workPbs);
			workDao.save(work);
			return true;
		}

		if (StringUtils.isEmpty(workPbs.getStage()) && StringUtils.isNotEmpty(work.getControlNo())
				&& work.getControlNo().startsWith("N")) {
			workPbs.setStage("ST13");
			workPbs.setStageDescription("Servicing/Replacement/Repair");
		}
		if (iDoc.getE2ORTOB_PARTNR().getPARNR() != null) {
			partner = new Partner();
			partner.setName(iDoc.getE2ORTOB_PARTNR_ADR().getNAME1());
			partner.setStreet(iDoc.getE2ORTOB_PARTNR_ADR().getSTREET());
			partner.setCity(iDoc.getE2ORTOB_PARTNR_ADR().getCITY1());
			partner.setPostCode(iDoc.getE2ORTOB_PARTNR_ADR().getPOST_CODE1());
			partner.setSearchTerm(iDoc.getE2ORTOB_PARTNR_ADR().getSORT1());
			partner.setCountryKey(iDoc.getE2ORTOB_PARTNR_ADR().getCOUNTRY());
			partner.setFirstTelephone(iDoc.getE2ORTOB_PARTNR_ADR()
					.getTEL_NUMBER());
			partner.setPartnerFunction(iDoc.getE2ORTOB_PARTNR().getPARVW());
			partner.setPartnerId(iDoc.getE2ORTOB_PARTNR().getPARNR());
			partner.setDescription(iDoc.getE2ORTOB_PARTNR().getVTEXT());
			partner.setWorkId(iDoc.getE2ORHDR().getAUFNR());
			partner.setImptFilename(work.getImptFilename());
			partnerDao.save(partner);

			work.setCustomerId(partner.getPartnerId());

			customerId = Global.removeZeroBefore(partner.getPartnerId());
			workPbs.setCustomerId(customerId);
			if (customerId != null && !customerId.equals("")) {
				searchParams.clear();
				searchParams.put("EQ_partnerId", customerId);
				filters = SearchFilter.parse(searchParams);
				specPartnerPbs = DynamicSpecifications.bySearchFilter(
						filters.values(), PartnerPbs.class);
				partnerPbsList = partnerPbsDao.findAll(specPartnerPbs);
				if (partnerPbsList != null && partnerPbsList.size() > 0) {
					partnerPbs = partnerPbsList.get(0);
				} else {
					partnerPbs = new PartnerPbs();
					partnerPbs.setPartnerId(customerId);
				}
				//有SAP错误，用户要求编号为205的客户信息固定为United Airline,而不是The Boeing Company 2017-01-06GQ 该硬代码后期可能会去掉
				if (customerId.equals("205")) {
					if (!partner.getName().trim().equals("The Boeing Company")) {
						partnerPbs.setName(partner.getName());
					}
				} else {
					partnerPbs.setName(partner.getName());
				}
				partnerPbs.setStreet(partner.getStreet());
				partnerPbs.setCity(partner.getCity());
				partnerPbs.setPostCode(partner.getPostCode());
				partnerPbs.setCountryKey(partner.getCountryKey());
				partnerPbs.setFirstTelephone(partner.getFirstTelephone());
				partnerPbs.setSearchTerm(partner.getSearchTerm());
				partnerPbs.setPartnerFunction(partner.getPartnerFunction());
				partnerPbs.setDescription(partner.getDescription());
				partnerPbsDao.save(partnerPbs);
			}
		}
		workhours = new BigDecimal("0");
		duration = new BigDecimal("0");
		relativdays = 0;
		for (E2OROPR e2OROPR : iDoc.getE2OROPRList()) {
			operation = new Operation();
			operation.setOperationId(e2OROPR.getVORNR());
			operation.setShortText(e2OROPR.getLTXA1());
			operation.setWorkCenter(e2OROPR.getARBPL());
			operation.setControlKey(e2OROPR.getSTEUS());
			operation.setActivityType(e2OROPR.getLARNT());
			operation.setWorkActivity(e2OROPR.getARBEI());
			operation.setNormalDuration(e2OROPR.getDAUNO());
			operation.setUnitForWork(e2OROPR.getARBEH());
			operation.setWorkId(iDoc.getE2ORHDR().getAUFNR());
			operation.setScheduledStartDate(e2OROPR.getFSAVD());
			operation.setScheduledStartTime(e2OROPR.getFSAVZ());
			operation.setScheduledFinishDate(e2OROPR.getFSEDD());
			operation.setScheduledFinishTime(e2OROPR.getFSEDZ());
			operation.setImptFilename(work.getImptFilename());
			operationDao.save(operation);

			if ("0010".equals(operation.getOperationId())
			// && (workPbs.getActualStartDate().getTime() >
			// System.currentTimeMillis()||workPbs.getCurrentWorkCenter()==null))
					&& workPbs.getCurrentWorkCenter() == null) {
				workPbs.setCurrentWorkCenter(operation.getWorkCenter());
			}

			if (operation.getWorkId() != null
					&& !operation.getWorkId().equals("")
					&& operation.getOperationId() != null
					&& !operation.getOperationId().equals("")) {
				workId = Global.removeZeroBefore(operation.getWorkId());
				searchParams.clear();
				searchParams.put("EQ_wid", workId);
				searchParams.put("EQ_operationNumber",
						operation.getOperationId());
				filters = SearchFilter.parse(searchParams);
				specOperationPbs = DynamicSpecifications.bySearchFilter(
						filters.values(), OperationPbs.class);
				operationPbsList = operationPbsDao.findAll(specOperationPbs);
				if (operationPbsList != null && operationPbsList.size() > 0) {
					operationPbs = operationPbsList.get(0);
				} else {
					operationPbs = new OperationPbs();
					operationPbs.setWid(workId);
					operationPbs.setOperationNumber(operation.getOperationId());
				}
				operationPbs.setPlant(work.getPlant());
				operationPbs.setWorkCenter(operation.getWorkCenter());
				operationPbs.setOperationDescription(operation.getShortText());
				if (operation.getNormalDuration() != null
						&& !operation.getNormalDuration().equals("")) {
					try {
						operationPbs.setWorkhours(new BigDecimal(operation
								.getNormalDuration()));
					} catch (Exception e) {
						operationPbs.setWorkhours(new BigDecimal("0"));
					}
				}
				if (operation.getWorkActivity() != null
						&& !operation.getWorkActivity().equals("")) {
					try {
						operationPbs.setWorkActivity(new BigDecimal(operation
								.getWorkActivity()));
					} catch (Exception e) {
						operationPbs.setWorkActivity(new BigDecimal("0"));
					}
				}
				if (workId != null && !workId.equals("")
						&& workId.startsWith("2")) {
					if (operationPbs.getWorkActivity().compareTo(pointOne) > 0) {
						workhours = workhours.add(operationPbs
								.getWorkActivity());
					}
					// if(operationPbs.getWorkhours().compareTo(pointOne)>0){
					duration = duration.add(operationPbs.getWorkhours());
					// }
				} else {
					workhours = workhours.add(operationPbs.getWorkActivity());
					duration = duration.add(operationPbs.getWorkhours());
				}
				operationPbs.setTimeUnit(operation.getUnitForWork());
				operationPbs.setActivityType(operation.getActivityType());
				try {
					operationPbs.setBasicStartDate(new Timestamp((sapDF
							.parse(operation.getScheduledStartDate()
									+ operation.getScheduledStartTime()))
							.getTime()));
					operationPbs.setBasicFinishDate(new Timestamp((sapDF
							.parse(operation.getScheduledFinishDate()
									+ operation.getScheduledFinishTime()))
							.getTime()));
				} catch (Exception e) {
				}
				operationPbs.setScheduledStartDate(operationPbs
						.getBasicStartDate());
				operationPbs.setScheduledFinishDate(operationPbs
						.getBasicFinishDate());
				try {
					operationPbs.setDuration(differ(
							sapDF.parse(operation.getScheduledStartDate()
									+ "000000"),
							sapDF.parse(operation.getScheduledFinishDate()
									+ "000000")));
				} catch (Exception e) {
				}
				relativdays += operationPbs.getDuration();
				operationPbsDao.save(operationPbs);
			}
		}
		workPbs.setWorkhours(workhours);
		workPbs.setRelativeDays(relativdays);
		if (workPbs.getDuration() == null
				|| (!workPbs.getDuration().equals(duration))) {
			workPbs.setDurationChanged("1");
		}
		logger.info("set durtion:{}",duration);
		workPbs.setDuration(duration);
		if (workPbs.getWorkHourStatus() != null
				&& workPbs.getWorkHourStatus() == 1) {
			//logger.info("417 --> before save work info:{}" , workPbs.toString());
			workPbs = workPbsDao.save(workPbs);
			logger.info("419 --> after save work info:{}" , workPbs.toString());
			workDao.save(work);
			return true;
		}
		workPbs.setWorkHourStatus(0); ////是否有工时的记录标示，1标示有，0标示无

		// 根据revision获取机型
		searchParams.clear();
		searchParams.put("EQ_rid", workPbs.getRid());
		filters = SearchFilter.parse(searchParams);
		specRevisionPbs = DynamicSpecifications.bySearchFilter(
				filters.values(), RevisionPbs.class);
		revisionPbsList = revisionPbsDao.findAll(specRevisionPbs);
		if (revisionPbsList == null || revisionPbsList.size() < 1) {
			logger.info("433-->workPbs info :{}" , workPbs.toString());
			workPbs = workPbsDao.save(workPbs);
			workDao.save(work);
			return true;
		}
		revisionPbs = revisionPbsList.get(0);
		/*if (revisionPbs.getCustomer() == null
				|| "".equals(revisionPbs.getCustomer())) {
			revisionPbs.setCustomer(customerId);
			logger.info("442---> revision:" + revisionPbs);
			revisionPbsDao.save(revisionPbs);
		}*/ //之前逻辑：revision客户号不存在，才更新 modify 20161118
		
		if(customerId != null && !customerId.equals("")) {
			revisionPbs.setCustomer(customerId);
			logger.info("strat save revision:" + revisionPbs);
			revisionPbsDao.save(revisionPbs);
		}//目前逻辑：只要客户号发生变更，就更新  modify 20161118
		
		// NRC不用导入工卡工时
		if (workId != null && !workId.equals("") && workId.startsWith("2")) {
			workPbs.setWorkHourStatus(1);
			logger.info("start save Nrc work:{}",workPbs);
			workPbs = workPbsDao.save(workPbs);
			//更新NRC工单的开始结束时间
			if (workPbs.getActualStartDate() != null && workPbs.getActualFinishDate() != null) {
				String currntDateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
				Date currentDate = DateCalculation.StringToDate(currntDateStr, "yyyyMMdd");
				Date date = this.getStage13StartDate(workPbs.getRid());
				BigDecimal durtion = workPbs.getDuration();
				if (durtion == null) {
					durtion = new BigDecimal(0);
				}
				if (date == null) { //没有配置时间
					workPbs.setActualStartDate(new Timestamp(currentDate.getTime()));
					workPbs.setActualFinishDate(new Timestamp(DateCalculation.dateAdd(currentDate, 
							new Float(durtion.toString())).getTime()));
				} else {
					//比较stage的开始时间与当前时间
					String stageStartStr = new SimpleDateFormat("yyyyMMdd").format(date);
					if (Integer.valueOf(currntDateStr).compareTo(Integer.valueOf(stageStartStr)) > 0) { //当前时间 > stage开始
						workPbs.setActualStartDate(new Timestamp(currentDate.getTime()));
						workPbs.setActualFinishDate(new Timestamp(DateCalculation.dateAdd(currentDate, 
								new Float(durtion.toString())).getTime()));
					} else {
						workPbs.setActualStartDate(new Timestamp(date.getTime()));
						workPbs.setActualFinishDate(new Timestamp(DateCalculation.dateAdd(date, 
								new Float(durtion.toString())).getTime()));
					}
				}
			}
			logger.info("NRC set start & end Date:{}", workPbs);
			workDao.save(work);
			return true;
		}
		if (work.getTaskId() == null || workPbs.getRid() == null
				|| work.getTaskId().equals("") || workPbs.getRid().equals("")) {
			logger.info("workPbs info 455:{}" , workPbs.toString());
			workPbs = workPbsDao.save(workPbs);
			workDao.save(work);
			return true;
		}
		try {
			aircraftType = "B" + revisionPbs.getAircraftType();
		} catch (Exception e) {
			aircraftType = "";
		}
		if (aircraftType.equals("")) {
			logger.info("the aircraftType is null:{}", workPbs.toString());
			workPbs = workPbsDao.save(workPbs);
			workDao.save(work);
			return true;
		}
		logger.info("get aircraftType:{}",aircraftType);
		// 查找工卡
		searchParams.clear();
		searchParams.put("EQ_cardNumber", work.getTaskId());
		searchParams.put("EQ_aircraftType", Global.getTypeByType(aircraftType));
		filters = SearchFilter.parse(searchParams);
		specTaskPbs = DynamicSpecifications.bySearchFilter(filters.values(),
				TaskPbs.class);
		taskPbsList = taskPbsDao.findAll(specTaskPbs);
		if (taskPbsList == null || taskPbsList.size() < 1) {
			logger.info("481 -->taskPbsList is null:{}",workPbs.toString());
			workPbs = workPbsDao.save(workPbs);
			workDao.save(work);
			return true;
		}
		taskPbs = taskPbsList.get(0);
		// rtn工卡不需要默认的stage 修改时间 2017.02.20
		/*if (workPbs.getStage() == null) {
			searchParams.clear();
			searchParams.put("EQ_cardNumber", work.getTaskId());
			searchParams.put("EQ_aircraftType",
					Global.getModelByType(aircraftType));
			filters = SearchFilter.parse(searchParams);
			specDbTaskPbs = DynamicSpecifications.bySearchFilter(
					filters.values(), DbTaskPbs.class);
			dbTaskPbsList = dbTaskPbsDao.findAll(specDbTaskPbs);
			if (dbTaskPbsList != null && dbTaskPbsList.size() > 0) {
				workPbs.setStage(dbTaskPbsList.get(0).getStage());
			}
		}*/
		workPbs.setZone(taskPbs.getZone());
		workPbs.setSourceOfJobCard(taskPbs.getSourceOfJobCard());
		if (taskPbs.getWorkHourStatus() == 1) {
			// 拷贝工时
			searchParams.clear();
			searchParams.put("EQ_taskId", taskPbs.getId());
			filters = SearchFilter.parse(searchParams);
			specTaskHoursPbs = DynamicSpecifications.bySearchFilter(
					filters.values(), TaskHoursPbs.class);
			taskHoursPbsList = taskHoursPbsDao.findAll(specTaskHoursPbs);
			if (taskHoursPbsList != null && taskHoursPbsList.size() > 0) {
				WorkHourStatusLog stLog = new WorkHourStatusLog(workPbs.getWid(), workPbs.getRid(),
						"WorkService.saveIDocWork", workPbs.toString());
				stLog.setRemark("success");     //再次更新标示位
				workPbs.setWorkHourStatus(1);
				workHourStatusLogDao.save(stLog);
				logger.info("512 -->The workPbs info:{}", workPbs);
				workPbs = workPbsDao.save(workPbs);
				if (workPbs.getId() != null) {
					for (int i = 0; i < taskHoursPbsList.size(); i++) {
						taskHoursPbs = taskHoursPbsList.get(i);
						workHoursPbs = new WorkHoursPbs();
						workHoursPbs.setWorkId(workPbs.getId());
						workHoursPbs.setStage(taskHoursPbs.getStage());
						workHoursPbs.setWorkCenter(taskHoursPbs.getWorkCenter());
						workHoursPbs.setMechanicHours(taskHoursPbs.getMechanicHours());
						workHoursPbs.setInspectionHours(taskHoursPbs.getInspectionHours());
						workHoursPbs.setModifyFlag("y");
						workHoursPbs.setWid(workPbs.getWid());
						logger.info("The workHour INFO:{}", workHoursPbs);
						workHoursPbsDao.save(workHoursPbs);
					}
				}
				logger.info("After save workPbs:{}", workPbs);
			} else {
				workPbs.setWorkHourStatus(0);
				logger.info("528--> pbsWork info :{}", workPbs.toString());
				workPbs = workPbsDao.save(workPbs);
			}
		} else {
			logger.info("532--> pbsWork info :{}", workPbs.toString());
			workPbs = workPbsDao.save(workPbs);
		}
		workDao.save(work);
		return true;
	}

	public int differ(Date date1, Date date2) {
		return (int) ((date2.getTime() - date1.getTime()) / 86400000);
	}

	public WorkPbs findPbs(Map<String, Object> criteriaMap) {
		filters = SearchFilter.parse(criteriaMap);
		specWorkPbs = DynamicSpecifications.bySearchFilter(filters.values(),
				WorkPbs.class);
		pageRequest = new PageRequest(0, 1, new Sort(Direction.DESC, "id"));
		page = workPbsDao.findAll(specWorkPbs, pageRequest);
		if (page.getContent() != null && page.getContent().size() > 0) {
			return page.getContent().get(0);
		}
		return null;
	}

	public List<WorkPbs> findNrcWork() {
		searchParams.clear();
		searchParams.put("LIKE_controlNumber", "N");
		filters = SearchFilter.parse(searchParams);
		specWorkPbs = DynamicSpecifications.bySearchFilter(filters.values(),
				WorkPbs.class);
		return workPbsDao.findAll(specWorkPbs);
	}

	public List<OperationPbs> findOperationByWorkId(String workid) {
		searchParams.clear();
		searchParams.put("EQ_wid", workid);
		filters = SearchFilter.parse(searchParams);
		specOperationPbs = DynamicSpecifications.bySearchFilter(
				filters.values(), OperationPbs.class);
		return operationPbsDao.findAll(specOperationPbs);
	}

	@Transactional(readOnly = false)
	public WorkPbs saveWorkPbs(WorkPbs work) {
		logger.info("576 --> saveWorkPbs:{}",work.toString());
		return workPbsDao.save(work);
	}
	
	@Transactional(readOnly = false)
	public void saveWorkPbs(List<WorkPbs> works) {
		//logger.info(works.toString());
		workPbsDao.save(works);
	}
	
	/**
	 * 获得某个工包stage 13的开始时间
	 * @param rid 工包编号
	 * @return 时间
	 */
	private Date getStage13StartDate(String rid) {
		StringBuilder sql = new StringBuilder();
		sql.append("select orignaldate_ from pbs_stagetransaction where rid_='").append(rid).append("'");
		Date start = jdbcTemplate.queryForObject(sql.toString(), Date.class);
		return start == null ? null : start;
	}
	
	/**
	 * 更新工单航材的状态信息
	 * @param workMaterialwids 缺失航材的工单编号列表
	 */
	@Transactional(readOnly = false)
	public void updateWorkPbsMaterialStatus(List<String> workMaterialwids) {
		//全部置1
		jdbcTemplate.execute(" update pbs_work set msstatus_ = 1 where msstatus_ = 2 ");
		//重置缺失航材的标识
		StringBuffer bf = new StringBuffer();
		int nums = 0;
		for(String wid : workMaterialwids) 
		{
			nums++;
			bf.append(",'").append(wid).append("'");
			
			//分批次更新，一次900条
			if(nums == 900)
			{
				jdbcTemplate.execute(" update pbs_work set msstatus_ = 2 where wid_ in("+bf.toString().replaceFirst(",", "")+")");
				logger.info("628->updateWorkPbsMaterialStatus:{}", bf.toString());
				bf.setLength(0);
				nums = 0;
			}
		}

		//将剩余的全部更新
		if(bf != null && bf.toString().length() > 0)
		{
			jdbcTemplate.execute(" update pbs_work set msstatus_ = 2 where wid_ in("+bf.toString().replaceFirst(",", "")+")");
			logger.info("638->updateWorkPbsMaterialStatus:{}", bf.toString());
			bf = null;
			nums = 0;
		}
	}

	@Transactional(readOnly = false)
	public int resetMsStatus(int aftStatus, int befStatus) {
		return workPbsDao.resetMsStatus(aftStatus, befStatus);
	}

	public WorkPbs findPbsByWorkOrder(String workOrder) {
		searchParams.clear();
		searchParams.put("EQ_wid", workOrder);
		filters = SearchFilter.parse(searchParams);
		specWorkPbs = DynamicSpecifications.bySearchFilter(filters.values(),
				WorkPbs.class);
		workPbsList = workPbsDao.findAll(specWorkPbs);
		if (workPbsList != null && workPbsList.size() > 0) {
			return workPbsList.get(0);
		} else {
			return null;
		}
	}
}
