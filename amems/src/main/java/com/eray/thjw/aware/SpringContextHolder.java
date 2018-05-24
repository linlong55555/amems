package com.eray.thjw.aware;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;

import com.eray.common.util.UserUtil;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.log.po.Field;
import com.eray.thjw.log.po.LogAccessRule;
import com.eray.thjw.log.po.Table;
import com.eray.thjw.service.TaskService;
import com.eray.thjw.system.dao.DictMapper;
import com.eray.thjw.system.po.Dict;
import com.eray.thjw.system.po.UpdateLog;
import com.eray.thjw.system.service.GlobleSystemConfigService;
import com.eray.thjw.util.Xml_Util;

import enu.ApprovalStatusEnum;
import enu.ApproveStatusEnum;
import enu.BinaryEnum;
import enu.ComponentOperationEnum;
import enu.EnquiryStatusEnum;
import enu.FirmTypeEnum;
import enu.FormTypeEnum;
import enu.FormulaTypeEnum;
import enu.JobCardStatusEnum;
import enu.LatestLogoTwoEnum;
import enu.LegacyTroubleStatusEnum;
import enu.LogOperationEnum;
import enu.MaterialPriceEnum;
import enu.MaterialSecondTypeEnum;
import enu.MaterialToolSecondTypeEnum;
import enu.MaterialType2Enum;
import enu.MaterialTypeEnum;
import enu.MaxWorkOrderTypeEnum;
import enu.MinWorkOrderTypeEnum;
import enu.MonitorItemEnum;
import enu.PartsPositionEnum;
import enu.PlanTaskDispalyState;
import enu.PlanTaskState;
import enu.PlanTaskSubType;
import enu.PlanTaskType;
import enu.PlaneComponentPositionEnum;
import enu.ReserveStatusEnum;
import enu.RevisionNoticeBookTypeEnum;
import enu.RevisionNoticeBookZtEnum;
import enu.ScheduledEnum;
import enu.StatusEnum;
import enu.Store2TypeEnum;
import enu.StoreTypeEnum;
import enu.SupervisoryLevelEnum;
import enu.SupplierTypeEnum;
import enu.TableType;
import enu.UnitEnum;
import enu.UpdateTypeEnum;
import enu.UrgencyEnum;
import enu.VisibleRangeEnum;
import enu.WorkOrderStateEnum;
import enu.aerialmaterial.ContractDeliveryStatusEnum;
import enu.aerialmaterial.ContractStatusEnum;
import enu.aerialmaterial.ContractTypeEnum;
import enu.aerialmaterial.FreezeBusinessTypeEnum;
import enu.aerialmaterial.InstockStatusEnum;
import enu.aerialmaterial.InstockTypeEnum;
import enu.aerialmaterial.MaterialCheckResultEnum;
import enu.aerialmaterial.MaterialEnum;
import enu.aerialmaterial.MaterialSrouceEnum;
import enu.aerialmaterial.PayTypeEnum;
import enu.aerialmaterial.ScrapStatusEnum;
import enu.aerialmaterial.SecondmentTypeEnum;
import enu.aerialmaterial.StockStatusEnum;
import enu.aerialmaterial.StockTypeEnum;
import enu.aerialmaterial.TakeStockStatusEnum;
import enu.common.ConclusionEnum;
import enu.common.DocumentTypeEnum;
import enu.common.EffectiveEnum;
import enu.common.EnableEnum;
import enu.common.WhetherEnum;
import enu.course.CycleEnum;
import enu.material2.ApplyStatusEnum;
import enu.material2.ContractMgntStatusEnum;
import enu.material2.ContractMgntTypeEnum;
import enu.material2.DemandDetailsLogoEnum;
import enu.material2.DemandSafeguardStatusEnum;
import enu.material2.DemandStatusEnum;
import enu.material2.DestroyEnum;
import enu.material2.DestroyStatusEnum;
import enu.material2.MaterialHistorySubtypeEnum;
import enu.material2.OutboundOrderStatusEnum;
import enu.material2.OutboundTypeEnum;
import enu.material2.ReceiptStatusEnum;
import enu.material2.ReceiptTypeEnum;
import enu.material2.SecurityStatusEnum;
import enu.material2.StockHistorySubtypeEnum;
import enu.material2.StockHistoryTypeEnum;
import enu.material2.ToolBorrowReturnTypeEnum;
import enu.mel.MelStatusEnum;
import enu.ordersource.OrderTypeEnum;
import enu.outfield.MeasurementMarkEnum;
import enu.produce.CareerCardTypeEnum;
import enu.produce.ComponenthistoryStatusEnum;
import enu.produce.FailureKeepEnum;
import enu.produce.FailureKeepStatusEnum;
import enu.produce.FailureKeepTypeEnum;
import enu.produce.FeedbackStatusEnum;
import enu.produce.FlbStatusEnum;
import enu.produce.InstalledPositionEnum;
import enu.produce.InstalledStatusEnum;
import enu.produce.NodeEnum;
import enu.produce.SourceTypeEnum;
import enu.produce.StorageTypeEnum;
import enu.produce.UndoEnum;
import enu.produce.UndoStatusEnum;
import enu.produce.WorkorderDXFBSEnum;
import enu.produce.WorkorderStatusEnum;
import enu.produce.WorkorderTypeEnum;
import enu.produce.Workpackage145StatusEnum;
import enu.produce.WorkpackageStatusEnum;
import enu.project2.AirworthinessMonitorStatusEnum;
import enu.project2.AirworthinessStatusEnum;
import enu.project2.BulletinStatusEnum;
import enu.project2.CompnentTypeEnum;
import enu.project2.ComputationalFormulaEnum;
import enu.project2.EngineeringOrderStatusEnum;
import enu.project2.ExecutionEnum;
import enu.project2.MaintenanceProjectTypeEnum;
import enu.project2.MaintenanceSchemeStatusEnum;
import enu.project2.MaterialToolEnum;
import enu.project2.ProductionOrderStatusEnum;
import enu.project2.ProjectBusinessEnum;
import enu.project2.SendOrderEnum;
import enu.project2.TechnicalEnum;
import enu.project2.TechnicalInstructionStatusEnum;
import enu.project2.TechnicalStatusEnum;
import enu.project2.TodoEnum;
import enu.project2.WorkCardStatusEnum;
import enu.quality.AnnualPlanStatusEnum;
import enu.quality.AuditDiscoverProblemTypeEnum;
import enu.quality.AuditDiscoverTypeEnum;
import enu.quality.AuditItemStatusEnum;
import enu.quality.AuditnoticeStatusEnum;
import enu.quality.AuditnoticeTyepEnum;
import enu.quality.InspectionStatusEnum;
import enu.quality.PostStatusEnum;
import enu.quality.ProblemStatusEnum;
import enu.task.TaskStatusEnum;
import enu.training.TrainingPlanStatusEnum;
import enu.training.TrainingPlanTypeEnum;

public class SpringContextHolder implements ApplicationContextAware, InitializingBean, DisposableBean {

	/** 日期类型错误 */
	protected final static Date DATE_TYPE_ERROR = new Date();
	
	public void destroy() throws Exception {
		SysContext.setApplicationContext(null);
	}

	public void setApplicationContext(ApplicationContext apContext) throws BeansException {
		SysContext.setApplicationContext(apContext);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initDic();
		initEnum();
		initLogConfig();
		initConfigProperties();//初始化加载 config.properties文件
		initUpdateLog();//初始化日志更新
		initGlobalConfig();// 依赖initConfigProperties()，必须放于其后
		initDoTask();
	}

	/**
	 * 初始化加载 config.properties文件
	 */
	private static void initConfigProperties() {
		// 加载配置文件信息
		Properties prop = new Properties();
		try {
			prop.load(UserUtil.class.getClassLoader().getResourceAsStream("conf/config.properties"));
			SysContext.APP_NAME = prop.getProperty("SYS.APP_NAME");
			SysContext.ASSESSMENT_TYPE = prop.getProperty("PROJECT.ASSESSMENT_TYPE");
			SysContext.CUSTOMERCODE = prop.getProperty("SYS.CUSTOMERCODE");
			SysContext.MEASUREMENT_LEVEL = prop.getProperty("TOOLCHECK.MEASUREMENT_LEVEL");
			SysContext.SALT_VALUE = prop.getProperty("SYS.SALT_VALUE");
			String osmsEnabled = prop.getProperty("SYS.OSMS_ENABLED");
			SysContext.OSMS_URL = prop.getProperty("SYS.OSMS_URL");
			SysContext.SCHEDULE_URL = prop.getProperty("SYS.SCHEDULE_URL");
			SysContext.SMS_URL = prop.getProperty("SYS.SMS_URL");
			SysContext.QYS = Integer.valueOf(prop.getProperty("SYS.QYS"));
			if ("false".equalsIgnoreCase(osmsEnabled)) {
				SysContext.OSMS_ENABLED = false;
				SysContext.WORKGROUP_MUST = false;
			}

			if (prop.getProperty("SYS.GETIP_STYLE") != null) {
				SysContext.GETIP_STYLE = prop.getProperty("SYS.GETIP_STYLE");
			}

			// 加载ftp设置
			if (prop.getProperty("SYS.FTP_ENABLED").equals("1")
					|| prop.getProperty("SYS.FTP_ENABLED").toUpperCase().equals("Y")
					|| prop.getProperty("SYS.FTP_ENABLED").toUpperCase().equals("TRUE")) {
				SysContext.FTP_ENABLED = Boolean.TRUE;
				SysContext.FTP_URL = prop.getProperty("SYS.FTP_URL").trim();
				SysContext.FTP_PORT = prop.getProperty("SYS.FTP_PORT").trim();
				SysContext.FTP_USERNAME = prop.getProperty("SYS.FTP_USERNAME").trim();
				SysContext.FTP_PASSWORD = prop.getProperty("SYS.FTP_PASSWORD").trim();
				SysContext.FTP_DOWN_USERNAME = prop.getProperty("SYS.FTP_DOWN_USERNAME").trim();
				SysContext.FTP_DOWN_PASSWORD = prop.getProperty("SYS.FTP_DOWN_PASSWORD").trim();
			}

			if (prop.getProperty("SYS.MODEL").equals("1") || prop.getProperty("SYS.MODEL").toUpperCase().equals("Y")
					|| prop.getProperty("SYS.MODEL").toUpperCase().equals("TRUE")) {
				SysContext.PRODUCT_MODEL = Boolean.TRUE;
			}
			
			String qrcodeEnabled = prop.getProperty("SYS.QRCODE_ENABLED");
			if ("TRUE".equalsIgnoreCase(qrcodeEnabled)) {
				SysContext.QRCODE_ENABLED = true;
			}
			
			String emailswitch = prop.getProperty("SYS.EMAILSWITCH");
			if ("TRUE".equalsIgnoreCase(emailswitch)) {
				SysContext.EMAILSWITCH = true;
				SysContext.SENDERADDRESS= prop.getProperty("SYS.SENDERADDRESS").trim();
				SysContext.SENDERACCOUNT= prop.getProperty("SYS.SENDERACCOUNT").trim();
				SysContext.SENDERPASSWORD= prop.getProperty("SYS.SENDERPASSWORD").trim();
				SysContext.SMTPADDRESS= prop.getProperty("SYS.SMTPADDRESS").trim();
			}
			
			SysContext.PROJECT_CUSTNAME = prop.getProperty("SYS.PROJECT_CUSTNAME").trim();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * 初始化日志更新
	 */
	public static void initUpdateLog() {
		try {
			// 获取文件路径
			ClassPathResource cpr = new ClassPathResource("/conf/updateLogs.xml");
			String xmlPath = cpr.getFile().getPath();
			Document doc = Xml_Util.getDocument(xmlPath);
			Element root = doc.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> updateLogs = root.elements();
			String temp = "";
			for (int i = 0; i < updateLogs.size(); i++) {
				//验证id判空
				if (updateLogs.get(i).attribute("id") == null) {
					throw new BusinessException("id未设置!");
				}
				
				temp = updateLogs.get(i).attribute("id").getText();
				 for (int j = i + 1; j < updateLogs.size(); j++)
		            {
						String oldid =updateLogs.get(j).attribute("id").getText();
						if(oldid != null && temp != null){
							if (temp.equals(oldid)){//验证id全局唯一
								throw new BusinessException("id:"+oldid+"必须全局唯一!");
							}
						}
		            }
				//加载数据
				SysContext.registUpdateLog(genUpdateLog(updateLogs.get(i)));
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (BusinessException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}
	
	@SuppressWarnings("unchecked")
	public static UpdateLog genUpdateLog(Element updateLog) throws BusinessException {
		List<Element> elements;
		UpdateLog updateL = new UpdateLog();
		Boolean falg=true;
		
		String id = updateLog.attribute("id").getText();
		updateL.setId(id);
		elements = updateLog.elements();
		if (elements != null && !elements.isEmpty()) {
			
			for (Element element : elements) {
				if(element.getName().equals("version")){// 版本
					//验证版本判空
					if(StringUtils.isBlank(element.getData().toString())){
						throw new BusinessException("版本未设置!");
					}
					updateL.setVersion(element.getData().toString());
				}
				if(element.getName().equals("pubdate")){//发布时间
					//验证发布时间判空
					if(StringUtils.isBlank(element.getData().toString())){
						throw new BusinessException("发布时间未设置!");
					}
					//验证发布时间格式
					Date date=convertToDate(element.getData().toString());
					
					if(date.compareTo(DATE_TYPE_ERROR) == 0){
						throw new BusinessException("发布时间格式不正确!");
					}
					updateL.setPubdate(date);
				}
				
				if(element.getName().equals("apply")){//适用
					if(StringUtils.isNotBlank(SysContext.CUSTOMERCODE)){
						if(!SysContext.CUSTOMERCODE.equalsIgnoreCase("all") && !SysContext.CUSTOMERCODE.equalsIgnoreCase("-")){
							String [] applys=element.getData().toString().split(",");
							for (String apply : applys) {
								if(!apply.trim().equalsIgnoreCase("all") && !apply.trim().equalsIgnoreCase("-")){
									if(!SysContext.CUSTOMERCODE.equalsIgnoreCase(apply.trim())){
										falg=false;
									}
								}
							}
						}
					}
					updateL.setApply(element.getData().toString());
				}
				
				if(element.getName().equals("title")){//标题
					updateL.setTitle(element.getData().toString());
				}
				
				if(element.getName().equals("describe")){//更新描述
					//更新描述判空
					if(StringUtils.isBlank(element.getData().toString())){
						throw new BusinessException("更新描述未设置!");
					}
					updateL.setDescribe(element.getData().toString());
				}
			}
		}
		if(falg){
			return updateL;
		}else{
			return null;
		}
	}
	
	/**
	 * String转Date
	 * @param str
	 * @param rowIndex
	 * @param des
	 * @return
	 */
	protected static Date convertToDate(String str){
		return convertToDate(str, null);
	}
	
	/**
	 * String转Date
	 * @param str
	 * @param rowNo
	 * @param des
	 * @param defaultValue
	 * @return
	 */
	protected static Date convertToDate(String str, Date defaultValue){
		if(StringUtils.isBlank(str)){
			return defaultValue;
		}
		String pattern;
		if(str.contains(".")){
			pattern = "yyyy.MM.dd";
		}else if(str.contains("-")){
			pattern = "yyyy-MM-dd";
		}else if(str.contains("/")){
			pattern = "yyyy/MM/dd";
		}else{
			pattern = "yyyyMMdd";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setLenient(false);//全匹配，强验证
		try {
			return format.parse(str);
		} catch (ParseException e) {
			return DATE_TYPE_ERROR;
		}
	}
	
	/**
	 * 初始化日志配置
	 */
	public static void initLogConfig() {
		try {
			// 获取文件路径
			ClassPathResource cpr = new ClassPathResource("/conf/logRule.xml");
			String xmlPath = cpr.getFile().getPath();
			Document doc = Xml_Util.getDocument(xmlPath);
			Element root = doc.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> rules = root.elements();
			for (Element rule : rules) {
				SysContext.registLogConfig(genLogRule(rule));
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (BusinessException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	@SuppressWarnings("unchecked")
	public static LogAccessRule genLogRule(Element rule) throws BusinessException {
		LogAccessRule logConfig = null;
		List<Element> elements;
		List<Table> list = null;
		if (rule.attribute("code") == null) {
			throw new BusinessException("日志规则名称未设置: ");
		}
		String code = rule.attribute("code").getText();
		/*
		 * if(code.indexOf("_REC")!=-1){ code = code.replaceAll("_REC", ""); }
		 */

		if (rule.attribute("space") == null) {
			throw new BusinessException("日志规则空间名称未设置: ");
		}

		elements = rule.elements();
		if (elements != null && !elements.isEmpty()) {
			logConfig = new LogAccessRule();
			list = new ArrayList<Table>();
			for (Element element : elements) {
				if (element.attribute("type") == null && TableType.notContain(element.attribute("type").getText())) {
					throw new BusinessException("日志表类型未设置: master 或者 slave ");
				}
				if (TableType.isMaster(element.attribute("type").getText())) {
					// 主表必须配置查询总数，查询记录sql对应id
					/*
					 * if (element.attribute("countSqlId")==null) { throw new
					 * BusinessException("日志表总数countSqlId未设置 "); }
					 */
					if (element.attribute("listSqlId") == null) {
						throw new BusinessException("日志表查询listSqlId未设置 ");
					}
				}

				if (element.attribute("tableName") == null) {
					throw new BusinessException("日志表未设置 ");
				}
				if (element.attribute("title") == null) {
					throw new BusinessException("日志表标题未设置 ");
				}
				if (element.attribute("etitle") == null) {
					throw new BusinessException("日志英文表标题未设置 ");
				}

				if (element.attribute("diffSqlId") == null) {
					throw new BusinessException("日志表差异查询sql未设置 ");
				}

				Table table = new Table();
				table.setStyle(element.attribute("style") != null ? element.attribute("style").getText()
						: "min-width: 1500px;");
				table.setTitle(element.attribute("title").getText());
				table.setEtitle(element.attribute("etitle").getText());

				table.setType(TableType.valueOf(element.attribute("type").getText().toUpperCase()));
				table.setTableName(element.attribute("tableName").getText());
				table.setCountSqlId(
						element.attribute("countSqlId") == null ? null : element.attribute("countSqlId").getText());
				table.setListSqlId(
						element.attribute("listSqlId") == null ? null : element.attribute("listSqlId").getText());
				table.setDiffSqlId(element.attribute("diffSqlId").getText());

				List<Field> fields = genFields(element);
				table.setFields(fields);
				list.add(table);

			}

			logConfig.setCode(code);
			logConfig.setTables(list);
			logConfig.setSpace(rule.attribute("space").getText());

		}

		return logConfig;
	}

	@SuppressWarnings("unchecked")
	private static List<Field> genFields(Element table) throws BusinessException {
		List<Element> elements = table.elements("column");
		List<Field> fields = null;
		if (elements != null && !elements.isEmpty()) {
			fields = new ArrayList<Field>();
			Boolean filledRequired = Boolean.FALSE;
			for (Element element : elements) {
				Field field = new Field();
				if (element.attribute("field") == null) {
					throw new BusinessException("必须为字段指定数据库字段");
				}
				if (element.attribute("fieldName") == null) {
					throw new BusinessException("必须为字段指定字段名称");
				}
				if ("REC_HD".equals(element.attribute("field").getText())) {
					filledRequired = Boolean.TRUE;
				}
				field.setField(element.attribute("field").getText());
				field.setFieldName(element.attribute("fieldName").getText());
				field.setShowInDiff(element.attribute("showInDiff") == null ? Boolean.FALSE
						: Boolean.valueOf(element.attribute("showInDiff").getText()));
				field.setShowInList(element.attribute("showInList") == null ? Boolean.FALSE
						: Boolean.valueOf(element.attribute("showInList").getText()));

				field.setHeadClass(element.attribute("headClass") != null ? element.attribute("headClass").getValue()
						: "colwidth-13");

				field.setDataClass(element.attribute("dataClass") != null ? element.attribute("dataClass").getValue()
						: "text-left");

				field.setFieldEname(
						element.attribute("fieldEname") != null ? element.attribute("fieldEname").getValue() : null);

				field.setOrder(element.attribute("order") != null
						? Integer.valueOf(element.attribute("order").getValue()) : 0);

				field.setEnumClass(
						element.attribute("enumClass") != null ? element.attribute("enumClass").getValue() : null);

				fields.add(field);
			}
			if (!filledRequired) {
				throw new BusinessException("必须指定业务活动字段名称:REC_HD");
			}
		}
		return fields;
	}

	/**
	 * 初始化枚举
	 */
	public static void initEnum() {
		Map<String, Object> enumMap = new HashMap<String, Object>();
		enumMap.put("revisionNoticeBookZtEnum", RevisionNoticeBookZtEnum.enumToListMap());
		enumMap.put("revisionNoticeBookTypeEnum", RevisionNoticeBookTypeEnum.enumToListMap());
		enumMap.put("planeComponentPositionEnum", PlaneComponentPositionEnum.enumToListMap());
		enumMap.put("componentOperationEnum", ComponentOperationEnum.enumToListMap());
		enumMap.put("storeTypeEnum", StoreTypeEnum.enumToListMap());
		enumMap.put("store2TypeEnum",Store2TypeEnum.enumToListMap());
		enumMap.put("supervisoryLevelEnum", SupervisoryLevelEnum.enumToListMap());
		enumMap.put("agingControlEnum", BinaryEnum.enumToListMap());
		enumMap.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		enumMap.put("materialSecondTypeEnum", MaterialSecondTypeEnum.enumToListMap());
		enumMap.put("materialToolSecondTypeEnum", MaterialToolSecondTypeEnum.enumToListMap());//工具设备二级类型
		enumMap.put("supplierTypeEnum", SupplierTypeEnum.enumToListMap());
		enumMap.put("approvalStatusEnum", ApprovalStatusEnum.enumToListMap());
		enumMap.put("statusEnum", StatusEnum.enumToListMap());
		enumMap.put("unitEnum", UnitEnum.enumToListMap());
		enumMap.put("approveStatusEnum", ApproveStatusEnum.enumToListMap());
		enumMap.put("latestLogoTwoEnum", LatestLogoTwoEnum.enumToListMap());
		enumMap.put("documentTypeEnum", DocumentTypeEnum.enumToListMap());

		enumMap.put("planTaskType", PlanTaskType.enumToListMap());
		enumMap.put("planTaskSubType", PlanTaskSubType.enumToListMap());
		enumMap.put("planTaskDispalyState", PlanTaskDispalyState.enumToListMap());
		enumMap.put("planTaskState", PlanTaskState.enumToListMap());
		enumMap.put("maxWorkOrderTypeEnum", MaxWorkOrderTypeEnum.enumToListMap());
		enumMap.put("minWorkOrderTypeEnum", MinWorkOrderTypeEnum.enumToListMap());
		enumMap.put("partsPositionEnum", PartsPositionEnum.enumToListMap());
		enumMap.put("orderTypeEnum", OrderTypeEnum.enumToListMap());

		enumMap.put("urgencyEnum", UrgencyEnum.enumToListMap());
		enumMap.put("reserveStatusEnum", ReserveStatusEnum.enumToListMap());
		enumMap.put("enquiryStatusEnum", EnquiryStatusEnum.enumToListMap());
		enumMap.put("formTypeEnum", FormTypeEnum.enumToListMap());
		enumMap.put("contractDeliveryStatusEnum", ContractDeliveryStatusEnum.enumToListMap());
		enumMap.put("contractStatusEnum", ContractStatusEnum.enumToListMap());
		enumMap.put("payTypeEnum", PayTypeEnum.enumToListMap());
		enumMap.put("contractTypeEnum", ContractTypeEnum.enumToListMap());

		enumMap.put("takeStockStatusEnum", TakeStockStatusEnum.enumToListMap());
		enumMap.put("measurementMarkEnum", MeasurementMarkEnum.enumToListMap());

		enumMap.put("formulaTypeEnum", FormulaTypeEnum.enumToListMap());
		enumMap.put("workOrderStateEnum", WorkOrderStateEnum.enumToListMap());
		enumMap.put("scheduledEnum", ScheduledEnum.enumToListMap());
		enumMap.put("legacyTroubleStatusEnum", LegacyTroubleStatusEnum.enumToListMap());
		enumMap.put("materialCheckResultEnum", MaterialCheckResultEnum.enumToListMap());
		enumMap.put("materialSrouceEnum", MaterialSrouceEnum.enumToListMap());
		enumMap.put("secondmentTypeEnum", SecondmentTypeEnum.enumToListMap());
		enumMap.put("monitorItemEnum", MonitorItemEnum.enumToListMap());
		enumMap.put("materialPriceEnum", MaterialPriceEnum.enumToListMap());
		enumMap.put("materialPriceEnum", MaterialPriceEnum.enumToListMap());
		enumMap.put("instockTypeEnum", InstockTypeEnum.enumToListMap());
		enumMap.put("instockStatusEnum", InstockStatusEnum.enumToListMap());
		enumMap.put("visibleRangeEnum", VisibleRangeEnum.enumToListMap());
		enumMap.put("enableEnum", EnableEnum.enumToListMap());
		enumMap.put("jobCardStatusEnum", JobCardStatusEnum.enumToListMap());

		enumMap.put("cycleEnum", CycleEnum.enumToListMap());
		enumMap.put("trainingPlanStatusEnum", TrainingPlanStatusEnum.enumToListMap());
		enumMap.put("trainingPlanTypeEnum", TrainingPlanTypeEnum.enumToListMap());
		enumMap.put("whetherEnum", WhetherEnum.enumToListMap());
		enumMap.put("workCardStatusEnum", WorkCardStatusEnum.enumToListMap());
		enumMap.put("materialEnum", MaterialEnum.enumToListMap());

		enumMap.put("freezeBusinessTypeEnum", FreezeBusinessTypeEnum.enumToListMap());
		enumMap.put("stockTypeEnum", StockTypeEnum.enumToListMap());
		enumMap.put("scrapStatusEnum", ScrapStatusEnum.enumToListMap());
		enumMap.put("airworthinessEnum", AirworthinessStatusEnum.enumToListMap());
		enumMap.put("technicalEnum", TechnicalEnum.enumToListMap());
		enumMap.put("computationalFormulaEnum", ComputationalFormulaEnum.enumToListMap());
		enumMap.put("bulletinStatusEnum", BulletinStatusEnum.enumToListMap()); // 技术通告
		enumMap.put("technicalInstructionStatusEnum", TechnicalInstructionStatusEnum.enumToListMap()); // 技术指令
		// 技术文件评估单
		enumMap.put("technicalStatusEnum", TechnicalStatusEnum.enumToListMap()); // 技术文件评估单状态枚举
		enumMap.put("projectBusinessEnum", ProjectBusinessEnum.enumToListMap()); // 工程模块业务类型枚举
		enumMap.put("compnentTypeEnum", CompnentTypeEnum.enumToListMap()); // 适用类别枚举
		enumMap.put("sendOrderEnum", SendOrderEnum.enumToListMap()); // 下达指令枚举
		enumMap.put("maintenanceSchemeStatusEnum", MaintenanceSchemeStatusEnum.enumToListMap()); // 维修方案状态枚举
		enumMap.put("maintenanceProjectTypeEnum", MaintenanceProjectTypeEnum.enumToListMap()); // 维修项目类型枚举

		enumMap.put("conclusionEnum", ConclusionEnum.enumToListMap()); // 流程信息结论枚举

		enumMap.put("engineeringOrderStatusEnum", EngineeringOrderStatusEnum.enumToListMap());// EO指令状态枚举

		enumMap.put("effectiveEnum", EffectiveEnum.enumToListMap());// 有效性枚举
		enumMap.put("logOperationEnum", LogOperationEnum.enumToListMap());// 操作日志操作说明
		enumMap.put("updateTypeEnum", UpdateTypeEnum.enumToListMap());// 数据库操作说明
		enumMap.put("melStatusEnum", MelStatusEnum.enumToListMap());// mel状态枚举
		enumMap.put("airworthinessMonitorStatusEnum", AirworthinessMonitorStatusEnum.enumToListMap()); // 适航性资料监控状态枚举

		enumMap.put("todoEnum", TodoEnum.enumToListMap());// 待办工作类型枚举
		enumMap.put("materialToolEnum", MaterialToolEnum.enumToListMap());// 器材工具部件枚举
		enumMap.put("executionEnum", ExecutionEnum.enumToListMap());// 执行方式枚举
		/*
		 * 生产计划
		 */
		enumMap.put("installedStatusEnum", InstalledStatusEnum.enumToListMap());// 装机状态枚举
		enumMap.put("installedPositionEnum", InstalledPositionEnum.enumToListMap());// 装机位置枚举
		enumMap.put("careercardtypeenum", CareerCardTypeEnum.enumToListMap());// 履历卡类型枚举
		enumMap.put("workpackageStatusEnum", WorkpackageStatusEnum.enumToListMap());// 工包枚举
		enumMap.put("workpackage145StatusEnum", Workpackage145StatusEnum.enumToListMap());// 工包145枚举
		enumMap.put("feedbackStatusEnum", FeedbackStatusEnum.enumToListMap());// 反馈枚举
		enumMap.put("failureKeepStatusEnum", FailureKeepStatusEnum.enumToListMap());// 故障保留状态枚举
		enumMap.put("failureKeepEnum", FailureKeepEnum.enumToListMap());// 故障保留来源枚举
		enumMap.put("failureKeepTypeEnum", FailureKeepTypeEnum.enumToListMap());// 故障保留类型枚举
		enumMap.put("flbStatusEnum", FlbStatusEnum.enumToListMap());// 飞行记录本状态枚举
		enumMap.put("sourceTypeEnum", SourceTypeEnum.enumToListMap());// 来源分类状态枚举
		enumMap.put("taskStatusEnum", TaskStatusEnum.enumToListMap());// 任务状态枚举
		/*
		 * 工单135
		 */
		enumMap.put("workorderStatusEnum", WorkorderStatusEnum.enumToListMap());// 工单状态枚举
		enumMap.put("workorderTypeEnum", WorkorderTypeEnum.enumToListMap());// 工单类型枚举
		enumMap.put("workorderDXFBSEnum", WorkorderDXFBSEnum.enumToListMap());//工单145待下发标识
		enumMap.put("componenthistoryStatusEnum", ComponenthistoryStatusEnum.enumToListMap());//部件履历当前状态
		enumMap.put("storageTypeEnum", StorageTypeEnum.enumToListMap());//入库操作类型
		
		enumMap.put("auditnoticeStatusEnum", AuditnoticeStatusEnum.enumToListMap());//质量审核通知单状态枚举
		enumMap.put("auditnoticeTyepEnum", AuditnoticeTyepEnum.enumToListMap());//质量审核通知单类别枚举
		enumMap.put("auditDiscoverTypeEnum", AuditDiscoverTypeEnum.enumToListMap());//审核问题单类型
		enumMap.put("auditDiscoverProblemTypeEnum", AuditDiscoverProblemTypeEnum.enumToListMap());//审核问题单问题类型
		enumMap.put("problemStatusEnum", ProblemStatusEnum.enumToListMap());//发现问题状态枚举

		/*
		 * 质量
		 */
		enumMap.put("postStatusEnum", PostStatusEnum.enumToListMap());//岗位授权状态枚举
		enumMap.put("annualPlanStatusEnum", AnnualPlanStatusEnum.enumToListMap());//年度计划状态枚举
		enumMap.put("auditItemStatusEnum", AuditItemStatusEnum.enumToListMap());//审核项目单状态枚举
		enumMap.put("inspectionStatusEnum", InspectionStatusEnum.enumToListMap());//检验单状态枚举
		
		/*
		 * 航材工具管理
		 */
		enumMap.put("demandStatusEnum", DemandStatusEnum.enumToListMap());//需求状态枚举
		enumMap.put("demandDetailsLogoEnum", DemandDetailsLogoEnum.enumToListMap());//需求明细标识枚举
		
		enumMap.put("contractMgntTypeEnum", ContractMgntTypeEnum.enumToListMap());//合同类型枚举
		enumMap.put("contractMgntStatusEnum", ContractMgntStatusEnum.enumToListMap());//合同状态枚举
		enumMap.put("stockStatusEnum", StockStatusEnum.enumToListMap());
		enumMap.put("outboundOrderStatusEnum", OutboundOrderStatusEnum.enumToListMap()); //出库单状态枚举
		enumMap.put("outboundTypeEnum", OutboundTypeEnum.enumToListMap()); //出库类型枚举
		
		enumMap.put("stockHistoryTypeEnum", StockHistoryTypeEnum.enumToListMap()); //操作类型枚举
		enumMap.put("stockHistorySubtypeEnum", StockHistorySubtypeEnum.enumToListMap()); //操作子类型枚举
		enumMap.put("applyStatusEnum", ApplyStatusEnum.enumToListMap()); //报废申请枚举
		
		/**
		 * 航材
		 */
		enumMap.put("demandSafeguardStatusEnum", DemandSafeguardStatusEnum.enumToListMap());//岗位授权状态枚举
		enumMap.put("receiptStatusEnum", ReceiptStatusEnum.enumToListMap());//收货状态枚举
		enumMap.put("receiptTypeEnum", ReceiptTypeEnum.enumToListMap());//收货类型枚举
		enumMap.put("destroyEnum", DestroyEnum.enumToListMap());//销毁枚举
		enumMap.put("destroyStatusEnum", DestroyStatusEnum.enumToListMap());//销毁状态枚举

		enumMap.put("firmTypeEnum", FirmTypeEnum.enumToListMap());//厂商类别枚举
		enumMap.put("materialType2Enum", MaterialType2Enum.enumToListMap());//航材二级类型枚举

		
		
		enumMap.put("materialHistorySubtypeEnum", MaterialHistorySubtypeEnum.enumToListMap()); //库存履历主信息-操作子类型
		enumMap.put("toolBorrowReturnTypeEnum", ToolBorrowReturnTypeEnum.enumToListMap());	//工具/设备借还状态枚举		
		enumMap.put("securityStatusEnum", SecurityStatusEnum.enumToListMap());	//保障状态
		/**
		 * 待办工作类型枚举
		 */
		enumMap.put("undoEnum", UndoEnum.enumToListMap());
		/**
		 * 节点枚举
		 */
		
		enumMap.put("nodeEnum",NodeEnum.enumToListMap());
		
		/**
		 * 待办工作状态枚举
		 */
		enumMap.put("undoStatusEnum",UndoStatusEnum.enumToListMap());
		
		/**
		 * 生产指令状态枚举
		 */
		enumMap.put("productionOrderStatusEnum",ProductionOrderStatusEnum.enumToListMap());
		
		SysContext.setEnumMap(enumMap);
	}

	public static void initDic() {
		DictMapper dicMapper = SysContext.getBean(DictMapper.class);
		List<Dict> dicList = dicMapper.selectDicAndItems();
		SysContext.getDprtDicMap().clear();
		if (dicList != null && !dicList.isEmpty()) {
			for (Dict dic : dicList) {
				SysContext.refreshDic(dic);
			}
		}
	}

	/**
	 * @description 初始化系统配置
	 * @author xu.yong
	 */
	public static void initGlobalConfig() {
		GlobleSystemConfigService globleSystemConfigService = SysContext.getBean(GlobleSystemConfigService.class);
		globleSystemConfigService.initGlobalSettings();
	}
	
	public static void initDoTask(){
		TaskService taskService = SysContext.getBean(TaskService.class);
		taskService.doTodoTaskList();
	}
}
