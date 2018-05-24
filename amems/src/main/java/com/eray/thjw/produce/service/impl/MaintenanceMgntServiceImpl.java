package com.eray.thjw.produce.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.DepartmentMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.MaintenanceMonthlyReportMapper;
import com.eray.thjw.produce.dao.MaintenanceReportConfigMapper;
import com.eray.thjw.produce.po.MaintenanceReportConfig;
import com.eray.thjw.produce.service.MaintenanceMgntService;
import com.eray.thjw.util.File_Util;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.TimeSeparatorEnum;
import enu.produce.InstalledPositionEnum;
import enu.project2.MonitorProjectEnum;

/**
 * @Description 维修管理service实现类
 * @CreateTime 2018年4月24日 下午4:05:40
 * @CreateBy 韩武
 */
@Service
public class MaintenanceMgntServiceImpl implements MaintenanceMgntService  {

	@Resource
	private MaintenanceReportConfigMapper maintenanceReportConfigMapper;
	
	@Resource
	private MaintenanceMonthlyReportMapper maintenanceMonthlyReportMapper;
	
	@Resource
	private DepartmentMapper departmentMapper;

	/**
	 * @Description 查询维修执管月报
	 * @CreateTime 2018年4月24日 下午4:03:00
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	@Override
	public Map<String, Object> queryMonthlyReport(MaintenanceReportConfig config) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("yftext", config.getYftext());
		resultMap.put("fjzch", config.getFjzch());
		Department department = departmentMapper.selectByPrimaryKey(config.getDprtcode());
		resultMap.put("dprtname", department == null ? null : department.getDprtname());
		// 查询工时费用设置
		resultMap.put("config", queryLatestConfig(config));
		// 查询机身及发动机小时数
		resultMap.put("hoursStatistics", queryHoursStatistics(config));
		// 查询航线例行维修工作
		resultMap.put("routineWork", maintenanceMonthlyReportMapper.queryRoutineWork(config));
		// 查询航线非例行维修工作
		resultMap.put("nonRoutineWork", maintenanceMonthlyReportMapper.queryNonRoutineWork(config));
		// 查询EO/MAO执行情况
		resultMap.put("eoMaoImplementation", queryEoMaoImplementation(config));
		// 查询适航指令,厂家服务通告等评估情况
		resultMap.put("evaluationSituations", queryEvaluationSituations(config));
		// 查询飞机故障/缺陷监控
		resultMap.put("faultMonitor", queryFaultMonitor(config));
		// 查询非例行维修工作费用
		resultMap.put("nonRoutinePayment", maintenanceMonthlyReportMapper.queryNonRoutinePayment(config));
		// 计算费用
		calcPayment(resultMap);
		// 图片
		resultMap.put("image", File_Util.getImageBase(File_Util.getFilePath("static/images/report/zx.jpg")));
		return resultMap;
	}
	
	/**
	 * @Description 查询工时费用设置
	 * @CreateTime 2018年4月27日 下午4:32:39
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	private MaintenanceReportConfig queryLatestConfig(MaintenanceReportConfig config){
		MaintenanceReportConfig configDb = maintenanceReportConfigMapper.queryLatestConfig(config);
		if(configDb == null){
			return config;
		}
		return configDb;
	}
	
	/**
	 * @Description 查询机身及发动机小时数
	 * @CreateTime 2018年4月24日 下午4:27:20
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	private List<Map<String, Object>> queryHoursStatistics(MaintenanceReportConfig config){
		List<Map<String, Object>> list = maintenanceMonthlyReportMapper.queryHoursStatistics(config);
		
		for (Map<String, Object> bean : list) {
			
			// null-->0
			if(bean.get("YDSJ") == null){
				bean.put("YDSJ", BigDecimal.ZERO);
			}
			if(bean.get("YDXH") == null){
				bean.put("YDXH", BigDecimal.ZERO);
			}
			if(bean.get("SJ") == null){
				bean.put("SJ", BigDecimal.ZERO);
			}
			if(bean.get("XH") == null){
				bean.put("XH", BigDecimal.ZERO);
			}
			if(bean.get("SJ_CSZ") == null){
				bean.put("SJ_CSZ", BigDecimal.ZERO);
			}
			if(bean.get("XH_CSZ") == null){
				bean.put("XH_CSZ", BigDecimal.ZERO);
			}
			
			// 转型
			BigDecimal ydsj = (BigDecimal) bean.get("YDSJ");
			BigDecimal sj = (BigDecimal) bean.get("SJ");
			BigDecimal xh = (BigDecimal) bean.get("XH");
			BigDecimal sjCsz = new BigDecimal(bean.get("SJ_CSZ").toString());
			BigDecimal xhCsz = new BigDecimal(bean.get("XH_CSZ").toString());
			
			// 时间循环加上飞机初始数据
			if(sj != null && sjCsz != null){
				sj = sj.add(sjCsz);
				bean.put("SJ", sj);
			}
			if(xh != null && xhCsz != null){
				xh = xh.add(xhCsz);
				bean.put("XH", xh);
			}
			
			// 时间分钟转小时
			if(ydsj != null){
				bean.put("YDSJ", StringAndDate_Util.convertToHour(ydsj.intValue(), TimeSeparatorEnum.COLON));
			}
			if(sj != null){
				bean.put("SJ", StringAndDate_Util.convertToHour(sj.intValue(), TimeSeparatorEnum.COLON));
			}
			
			// 位置
			if(bean.get("WZ") != null){
				bean.put("WZ", InstalledPositionEnum.getName(Integer.parseInt(bean.get("WZ").toString())));
			}
		}
		
		return list;
	}

	/**
	 * @Description 查询EO/MAO执行情况
	 * @CreateTime 2018年4月25日 下午2:32:56
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	private List<Map<String, Object>> queryEoMaoImplementation(MaintenanceReportConfig config){
		List<Map<String, Object>> list = maintenanceMonthlyReportMapper.queryEoMaoImplementation(config);
		if(list.isEmpty()){
			Map<String, Object> bean = new HashMap<String, Object>();
			bean.put("XH", "N/A");
			bean.put("EOBH", "N/A");
			bean.put("EOZT", "N/A");
			bean.put("WCZT", "N/A");
			list.add(bean);
		}else{
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> bean = list.get(i);
				bean.put("XH", i + 1);
				bean.put("WCZT", "1".equals(bean.get("WCZT").toString()) ? "完成" : "未完成");
			}
		}
		return list;
	}
	
	/**
	 * @Description 查询适航指令,厂家服务通告等评估情况
	 * @CreateTime 2018年4月25日 下午3:42:16
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	private List<Map<String, Object>> queryEvaluationSituations(MaintenanceReportConfig config){
		List<Map<String, Object>> list = maintenanceMonthlyReportMapper.queryEvaluationSituations(config);
		for (Map<String, Object> bean : list) {
			bean.put("ZDSJ", Utils.DT.formatDate((Date) bean.get("ZDSJ")));
		}
		return list;
	}
	
	/**
	 * @Description 查询飞机故障/缺陷监控
	 * @CreateTime 2018年4月25日 下午3:54:22
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	private List<Map<String, Object>> queryFaultMonitor(MaintenanceReportConfig config){
		List<Map<String, Object>> list = maintenanceMonthlyReportMapper.queryFaultMonitor(config);
		for (Map<String, Object> bean : list) {
			String detail = (String) bean.get("DETAIL");
			if(detail != null){
				String[] arr = detail.split("#__#");
				bean.put("BLRQ", arr[0]);
				bean.put("BLYY", arr[1]);
				
				StringBuilder dqrq = new StringBuilder();
				for (String jkxm : arr[2].split(",")) {
					String jkx = jkxm.split("#_#")[0];
					String jkz = jkxm.split("#_#")[1];
					// 监控项目为时间，分钟-->小时
					if(MonitorProjectEnum.isTime(jkx)){
						jkz = StringAndDate_Util.convertToHour(jkz);
					}
					dqrq.append(MonitorProjectEnum.getName(jkx)).append("：").append(jkz).append("\n");
				}
				bean.put("DQRQ", dqrq.toString());
			}
		}
		return list;
	}
	
	/**
	 * @Description 计算非例行维修工作费用
	 * @CreateTime 2018年4月26日 下午4:00:16
	 * @CreateBy 韩武
	 * @param resultMap
	 */
	@SuppressWarnings("unchecked")
	private void calcPayment(Map<String, Object> resultMap){
		// 维修执管月报配置
		MaintenanceReportConfig config = (MaintenanceReportConfig) resultMap.get("config");
		
		// 非例行维修工作
		List<Map<String, Object>> flxList = (List<Map<String, Object>>) resultMap.get("nonRoutinePayment");
		// 非例行累计工时
		BigDecimal flxljgs = BigDecimal.ZERO;
		for (Map<String, Object> bean : flxList) {
			BigDecimal sjgs = (BigDecimal) bean.get("SJGS");
			if(sjgs != null){
				flxljgs = flxljgs.add(sjgs);
			}
		}
		flxljgs = flxljgs.setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
		// 非例行工时单价
		BigDecimal flxgsdj = config == null ? BigDecimal.ZERO : 
			(config.getFlxgsdj() == null ? BigDecimal.ZERO : config.getFlxgsdj());
		// 非例行累计费用
		BigDecimal flxljfy = flxljgs.multiply(flxgsdj).setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
		// 非例行工时共计： 工时求和
		resultMap.put("flxgs", flxljgs.toPlainString() + "工时");
		// 非例行工时费共计： 非例行工时共计  *  非例行工时费
		resultMap.put("flxfy", flxljgs.toPlainString() + " X " + flxgsdj.toPlainString() + " = " + flxljfy.toPlainString() + "￥");
		
		
		
		
		// 航线例行维修工作
		List<Map<String, Object>> lxList = (List<Map<String, Object>>) resultMap.get("routineWork");
		// 例行累计工时
		BigDecimal lxljgs = BigDecimal.ZERO;
		for (Map<String, Object> bean : lxList) {
			BigDecimal sjgs = (BigDecimal) bean.get("GS");
			if(sjgs != null){
				lxljgs = lxljgs.add(sjgs);
			}
		}
		lxljgs = lxljgs.setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
		// 例行工时单价
		BigDecimal lxgsdj = config == null ? BigDecimal.ZERO : 
			(config.getLxgsdj() == null ? BigDecimal.ZERO : config.getLxgsdj());
		// 例行累计费用
		BigDecimal lxljfy = lxljgs.multiply(lxgsdj).setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
		resultMap.put("lxgs", lxljgs.toPlainString() + "工时");
		
		
		
		// 常用耗材费用比例
		BigDecimal cyhcfybl = config == null ? BigDecimal.ZERO : 
			(config.getCyhcfybl() == null ? BigDecimal.ZERO : config.getCyhcfybl());
		// 常用耗材费 = （例行工时费 + 非例行 工时费）* （常用耗材费用比例 / 100）
		BigDecimal cyhcfy = (flxljfy.add(lxljfy)).multiply(cyhcfybl.divide(new BigDecimal(100)))
				.setScale(2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
		resultMap.put("cyhcfy", "人民币：" + cyhcfy.toPlainString());
		resultMap.put("cyhcfywb", "(" + flxljfy.toPlainString() + " + " + 
				lxljfy.toPlainString() + ") * " + cyhcfybl.toPlainString() + "% = " + cyhcfy.toPlainString() + "￥");
		
	}
	
	/**
	 * @Description 保存工时费用设置
	 * @CreateTime 2018年4月26日 上午11:05:55
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	@Override
	public MaintenanceReportConfig doSave(MaintenanceReportConfig config) throws BusinessException {
		
		User user  = ThreadVarUtil.getUser();
		config.setWhbmid(user.getBmdm());
		config.setWhrid(user.getId());
		config.setWhsj(new Date());
		int count = maintenanceReportConfigMapper.updateByBusinessKey(config);
		if(count == 0){
			config.setId(UUID.randomUUID().toString());
			maintenanceReportConfigMapper.insertSelective(config);
		}
		return config;
	}

	/**
	 * @Description 查询工时费用详情
	 * @CreateTime 2018年4月26日 下午1:50:02
	 * @CreateBy 韩武
	 * @param config
	 * @return
	 */
	@Override
	public MaintenanceReportConfig queryDetail(MaintenanceReportConfig config) {
		return maintenanceReportConfigMapper.queryLatestConfig(config);
	}
}