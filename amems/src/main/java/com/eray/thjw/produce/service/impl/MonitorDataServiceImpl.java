package com.eray.thjw.produce.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.ComponentUseMapper;
import com.eray.thjw.produce.dao.InstallationListEffective2InitMapper;
import com.eray.thjw.produce.dao.InstallationListEffectiveMapper;
import com.eray.thjw.produce.dao.MonitorDataMapper;
import com.eray.thjw.produce.dao.MonitoringCurrentMapper;
import com.eray.thjw.produce.dao.MonitoringLastMapper;
import com.eray.thjw.produce.dao.MonitoringObjectMapper;
import com.eray.thjw.produce.dao.MonitoringPlanMapper;
import com.eray.thjw.produce.dao.PlanInitMapper;
import com.eray.thjw.produce.po.ComponentUse;
import com.eray.thjw.produce.po.InstallationListEffective;
import com.eray.thjw.produce.po.InstallationListEffective2Init;
import com.eray.thjw.produce.po.MonitoringLast;
import com.eray.thjw.produce.po.MonitoringObject;
import com.eray.thjw.produce.po.MonitoringPlan;
import com.eray.thjw.produce.po.PlanInit;
import com.eray.thjw.produce.service.MonitorDataService;
import com.eray.thjw.project2.dao.EOMonitorIemSetMapper;
import com.eray.thjw.project2.dao.EngineeringOrderMapper;
import com.eray.thjw.project2.dao.MaintenanceProjectMapper;
import com.eray.thjw.project2.dao.ProductionOrderMapper;
import com.eray.thjw.project2.dao.ProductionOrderMonitorMapper;
import com.eray.thjw.project2.po.EOMonitorIemSet;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.po.ProductionOrder;
import com.eray.thjw.project2.po.ProductionOrderMonitor;
import com.eray.thjw.project2.po.ProjectMonitor;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.ThreadVarUtil;

import enu.common.WhetherEnum;
import enu.produce.InstalledPositionEnum;
import enu.produce.InstalledStatusEnum;
import enu.produce.MaintenanceTypeEnum;
import enu.project2.ComputationalFormulaEnum;
import enu.project2.ExecutionEnum;
import enu.project2.MonitorProjectEnum;
import enu.project2.MonitorProjectUnitEnum;

/** 
 * @Description 监控数据处理服务类
 * @CreateTime 2017年10月9日 下午1:40:58
 * @CreateBy 徐勇	
 */
@Service
public class MonitorDataServiceImpl implements MonitorDataService{
	
	@Resource
	private MonitoringCurrentMapper monitoringCurrentMapper;
	
	@Resource
	private MonitoringObjectMapper monitoringObjectMapper;
	
	@Resource
	private MonitoringPlanMapper monitoringPlanMapper;
	
	@Resource
	private MonitorDataMapper monitorDataMapper;
	
	@Resource
	private MaintenanceProjectMapper maintenanceProjectMapper;
	
	@Resource
	private EOMonitorIemSetMapper eOMonitorIemSetMapper;
	
	@Resource
	private ComponentUseMapper componentUseMapper;
	
	@Resource
	private InstallationListEffective2InitMapper installationListEffective2InitMapper;
	
	@Resource
	private InstallationListEffectiveMapper installationListEffectiveMapper;
	
	@Resource
	private PlanInitMapper planInitMapper;
	
	@Resource
	private EngineeringOrderMapper engineeringOrderMapper;
	
	@Resource
	private MonitoringLastMapper monitoringLastMapper;
	
	@Resource
	private ProductionOrderMonitorMapper productionOrderMonitorMapper;
	
	@Resource
	private ProductionOrderMapper productionOrderMapper;
	
	/**
	 * @Description 根据维修方案删除当前监控数据
	 * @CreateTime 2017年10月9日 下午1:47:29
	 * @CreateBy 徐勇
	 * @param wxfabh 维修方案编号
	 * @param dprtcode 组织机构
	 */
	public void removeByMaintenanceScheme(String wxfabh, String dprtcode){
		this.monitoringCurrentMapper.deleteByMaintenanceScheme(wxfabh, dprtcode);
	}
	
	/**
	 * @Description 根据来源编号和来源类型删除当前监控数据
	 * @CreateTime 2018年5月2日 下午2:06:56
	 * @CreateBy 徐勇
	 * @param lybh 来源编号
	 * @param lylx 来源类型
	 * @param dprtcode 组织机构
	 */
	public void removeByLybhAndLx(String lybh, Integer lylx, String dprtcode){
		this.monitoringCurrentMapper.deleteByLybhAndLx(lybh, lylx, dprtcode);
	}
	
	/**
	 * @Description 根据飞机注册号删除当前监控数据
	 * @CreateTime 2017年10月9日 下午1:47:29
	 * @CreateBy 徐勇
	 * @param fjzch 飞机注册号
	 * @param dprtcode 组织机构
	 */
	public void removeByFjzch(String fjzch, String dprtcode){
		this.monitoringCurrentMapper.deleteByFjzch(fjzch, dprtcode);
	}
	
	/**
	 * @Description 根据装机清单id删除当前监控数据
	 * @CreateTime 2017年10月19日 下午3:27:43
	 * @CreateBy 徐勇
	 * @param zjqdid 装机清单ID
	 */
	public void removeByZjqdid(String zjqdid){
		this.monitoringCurrentMapper.deleteByZjqdid(zjqdid);
	}
	
	/**
	 * @Description 查询时控时寿项目需要监控的数据 
	 * @CreateTime 2017年10月9日 下午3:18:12
	 * @CreateBy 徐勇
	 * @param wxfaid 维修方案ID
	 * @return
	 */
	public List<MonitoringObject> queryControlMPNeedMonitorDataList(String wxfaid){
		return this.monitorDataMapper.selectControlMPNeedMonitorDataList(wxfaid);
	}
	
	/**
	 * @Description 查询维修项目/定检包需要监控的数据
	 * @CreateTime 2017年10月9日 下午3:18:48
	 * @CreateBy 徐勇
	 * @param wxfaid 维修方案ID
	 * @return
	 */
	public List<MonitoringObject> queryCommonMPNeedMonitorDataList(String wxfaid){
		return this.monitorDataMapper.selectCommonMPNeedMonitorDataList(wxfaid);
	}
	
	/**
	 * @Description 查询生产指令需要生成的监控数据
	 * @CreateTime 2018年5月2日 下午2:23:27
	 * @CreateBy 徐勇
	 * @param poId 生产指令id
	 * @return
	 */
	public List<MonitoringObject> queryPONeedMonitorDataList(String poId){
		return this.monitorDataMapper.selectPONeedMonitorDataList(poId);
	}
	
	/**
     * @Description 根据维修方案ID查询未执行的飞机监控数据（带计划、上次执行）
     * @CreateTime 2017年10月9日 下午8:02:58
     * @CreateBy 徐勇
     * @param wxfaid 维修方案ID
     * @return
     */
    public List<MonitoringObject> queryNotExeMonitorDataListByWxfaid(String wxfaid){
    	return this.monitoringObjectMapper.selectNotExeMonitorDataListByWxfaid(wxfaid);
    }
    
    /**
     * @Description 根据维修方案ID查询未执行的飞机监控数据（带计划、上次执行）
     * @CreateTime 2017年10月9日 下午8:02:58
     * @CreateBy 徐勇
     * @param wxfaid 维修方案ID
     * @return
     */
    public Map<String, MonitoringObject> queryNotExeMonitorDataMapByWxfaid(String wxfaid){
    	List<MonitoringObject> notExeList = this.queryNotExeMonitorDataListByWxfaid(wxfaid);
    	Map<String, MonitoringObject> notExeMap = new HashMap<String, MonitoringObject>(notExeList.size());
    	for (MonitoringObject monitoringObject : notExeList) {
    		notExeMap.put(getMonitorObjectKey(monitoringObject), monitoringObject);
		}
    	return notExeMap;
    }
    
	/**
     * @Description 根据来源ID查询未执行的飞机监控数据（带计划、上次执行）
     * @CreateTime 2018年5月2日 下午3:20:57
     * @CreateBy 徐勇
     * @param lyid 来源ID
     * @return
     */
    private List<MonitoringObject> queryNotExeMonitorDataListByLyid(String lyid){
    	return this.monitoringObjectMapper.selectNotExeMonitorDataListByLyid(lyid);
    }
    
    /**
     * @Description 根据来源ID查询未执行的飞机监控数据（带计划、上次执行）
     * @CreateTime 2018年5月2日 下午3:20:57
     * @CreateBy 徐勇
     * @param lyid 来源id
     * @return
     */
    public Map<String, MonitoringObject> queryNotExeMonitorDataMapByLyid(String lyid){
    	List<MonitoringObject> notExeList = this.queryNotExeMonitorDataListByLyid(lyid);
    	Map<String, MonitoringObject> notExeMap = new HashMap<String, MonitoringObject>(notExeList.size());
    	for (MonitoringObject monitoringObject : notExeList) {
    		notExeMap.put(getMonitorObjectKey(monitoringObject), monitoringObject);
		}
    	return notExeMap;
    }
    
    /**
     * @Description 获取监控数据唯一标识
     * @CreateTime 2017年10月9日 下午8:36:39
     * @CreateBy 徐勇
     * @param monitoringObject 监控数据对象
     * @return 来源编号_飞机注册号_装机清单ID_部件号_序列号_位置
     */
	public String getMonitorObjectKey(MonitoringObject monitoringObject){
		StringBuilder sb = new StringBuilder();
		if(monitoringObject.getLylx() == MaintenanceTypeEnum.PROJECT.getId()){
			return sb.append(monitoringObject.getLybh())
					.append("_").append(monitoringObject.getFjzch())
					.append("_").append(processNull(monitoringObject.getZjqdid()))
					.append("_").append(processNull(monitoringObject.getBjh()))
					.append("_").append(processNull(monitoringObject.getXlh()))
					.append("_").append(processNull(monitoringObject.getWz()))
					.toString();
		}if(monitoringObject.getLylx() == MaintenanceTypeEnum.PRODUCTION_ORDER.getId()){
			return sb.append(monitoringObject.getLybh())
					.append("_").append(monitoringObject.getFjzch())
					.append("_").append(processNull(monitoringObject.getZjqdid()))
					.append("_").append(processNull(monitoringObject.getBjh()))
					.append("_").append(processNull(monitoringObject.getXlh()))
					.append("_").append(processNull(monitoringObject.getWz()))
					.toString();
		}else{
			return sb.append(monitoringObject.getLyid())
					.append("_").append(processNull(monitoringObject.getEoxc()))
					.append("_").append(monitoringObject.getFjzch())
					.append("_").append(processNull(monitoringObject.getZjqdid()))
					.append("_").append(processNull(monitoringObject.getBjh()))
					.append("_").append(processNull(monitoringObject.getXlh()))
					.append("_").append(processNull(monitoringObject.getWz()))
					.toString();
			
		}
	}
	
	/**
	 * @Description 根据监控数据唯一标识获取监控对象
	 * @CreateTime 2017年10月10日 下午8:30:56
	 * @CreateBy 徐勇
	 * @param map 监控数据清单
	 * @param monitoringObject 需要匹配的监控数据
	 * @param fullMatch 是否全匹配（是否带位置匹配）
	 */
	public MonitoringObject getMatchedMonitorObject(Map<String, MonitoringObject> map, MonitoringObject monitoringObject, boolean fullMatch){
		if(map == null || map.size() == 0){
			return null;
		}
		MonitoringObject mo = map.get(this.getMonitorObjectKey(monitoringObject));
		if(mo != null || fullMatch){ //按全匹配查询得到结果 或 强制全匹配时直接返回数据
			return mo;
		}
		//非全匹配
		Integer wz = monitoringObject.getWz();//缓存需匹配数据自身的位置，return前需要还原
		if(wz != null){//当需匹配数据自身带位置时先以空位置进行匹配
			monitoringObject.setWz(null);
			mo = map.get(this.getMonitorObjectKey(monitoringObject));
			if(mo != null){
				monitoringObject.setWz(wz);
				return mo;
			}
		}
		//循环位置枚举进行匹配
		InstalledPositionEnum[] enums = InstalledPositionEnum.values();
		for (InstalledPositionEnum installedPositionEnum : enums) {
			monitoringObject.setWz(installedPositionEnum.getId());
			mo = map.get(this.getMonitorObjectKey(monitoringObject));
			if(mo != null){
				break;
			}
		}
		monitoringObject.setWz(wz);
		return mo;
	}
	
	/**
	 * @Description 处理空值为字符 "-"
	 * @CreateTime 2017年10月9日 下午8:32:03
	 * @CreateBy 徐勇
	 * @param str 待处理字符串
	 * @return
	 */
	private String processNull(Object str){
		if(str == null || "".equals(str.toString())){
			return "-";
		}
		return str.toString();
	}
    
	/**
	 * @Description 根据监控数据批量删除计划数据
	 * @CreateTime 2017年10月9日 下午10:45:07
	 * @CreateBy 徐勇
	 * @param list 监控数据
	 */
	public void removeMonitorPlanByMainIdBatch(List<MonitoringObject> list){
		for (int i = 0; i <= list.size()/SysContext.BATCH_PROCESS_SIZE; i++) {
			if(i*SysContext.BATCH_PROCESS_SIZE < list.size()){
				this.monitoringPlanMapper.deleteByMainIdBatch(list.subList(i*SysContext.BATCH_PROCESS_SIZE, 
						((i+1) * SysContext.BATCH_PROCESS_SIZE > list.size() ? list.size():(i+1) * SysContext.BATCH_PROCESS_SIZE)));
			}
		}
	}
	
	/**
	 * @Description 批量更新监控数据
	 * @CreateTime 2017年10月10日 上午9:40:23
	 * @CreateBy 徐勇
	 * @param list 监控数据
	 */
	public void updateMonitorObjectBatch(List<MonitoringObject> list){
		for (int i = 0; i <= list.size()/SysContext.BATCH_PROCESS_SIZE; i++) {
			if(i*SysContext.BATCH_PROCESS_SIZE < list.size()){
				this.monitoringObjectMapper.updateSource4Batch(list.subList(i*SysContext.BATCH_PROCESS_SIZE, 
						((i+1) * SysContext.BATCH_PROCESS_SIZE > list.size() ? list.size():(i+1) * SysContext.BATCH_PROCESS_SIZE)));
			}
		}
	}
	
	/**
	 * @Description 批量新增监控数据
	 * @CreateTime 2017年10月10日 上午10:13:41
	 * @CreateBy 徐勇
	 * @param list 监控数据
	 */
	public void saveMonitorObjectBatch(List<MonitoringObject> list){
		for (int i = 0; i <= list.size()/SysContext.BATCH_PROCESS_SIZE; i++) {
			if(i*SysContext.BATCH_PROCESS_SIZE < list.size()){
				this.monitoringObjectMapper.insert4Batch(list.subList(i*SysContext.BATCH_PROCESS_SIZE, 
						((i+1) * SysContext.BATCH_PROCESS_SIZE > list.size() ? list.size():(i+1) * SysContext.BATCH_PROCESS_SIZE)));
			}
		}
	}
	
	/**
	 * @Description 批量新增监控数据-计划执行数据
	 * @CreateTime 2017年10月10日 上午10:22:45
	 * @CreateBy 徐勇
	 * @param list 监控-计划执行数据
	 */
	public void saveMonitorPlanBatch(List<MonitoringPlan> list){
		for (int i = 0; i <= list.size()/SysContext.BATCH_PROCESS_SIZE; i++) {
			if(i*SysContext.BATCH_PROCESS_SIZE < list.size()){
				this.monitoringPlanMapper.insert4Batch(list.subList(i*SysContext.BATCH_PROCESS_SIZE, 
						((i+1) * SysContext.BATCH_PROCESS_SIZE > list.size() ? list.size():(i+1) * SysContext.BATCH_PROCESS_SIZE)));
			}
		}
	}
	
	/**
	 * @Description 批量新增监控数据-上次执行数据
	 * @CreateTime 2017年10月10日 上午10:22:45
	 * @CreateBy 徐勇
	 * @param list 监控-上次执行数据
	 */
	private void saveMonitorLastBatch(List<MonitoringLast> list){
		for (int i = 0; i <= list.size()/SysContext.BATCH_PROCESS_SIZE; i++) {
			if(i*SysContext.BATCH_PROCESS_SIZE < list.size()){
				this.monitoringLastMapper.insert4Batch(list.subList(i*SysContext.BATCH_PROCESS_SIZE, 
						((i+1) * SysContext.BATCH_PROCESS_SIZE > list.size() ? list.size():(i+1) * SysContext.BATCH_PROCESS_SIZE)));
			}
		}
	}
	
	/**
	 * @Description 批量同步监控数据到当前监控数据中去
	 * @CreateTime 2017年10月10日 上午10:22:45
	 * @CreateBy 徐勇
	 * @param list 监控数据
	 */
	public void saveCurrentMonitor4BatchSync(List<MonitoringObject> list){
		for (int i = 0; i <= list.size()/SysContext.BATCH_PROCESS_SIZE; i++) {
			if(i*SysContext.BATCH_PROCESS_SIZE < list.size()){
				this.monitorDataMapper.insertCurrMonitor4BatchSync(list.subList(i*SysContext.BATCH_PROCESS_SIZE, 
						((i+1) * SysContext.BATCH_PROCESS_SIZE > list.size() ? list.size():(i+1) * SysContext.BATCH_PROCESS_SIZE)));
			}
		}
	}
	
	/**
	 * @Description 按机型查询飞机和飞机序列号件的初始化日期
	 * @CreateTime 2017年10月10日 下午4:32:24
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjjx 飞机机型
	 * @return 飞机注册号|装机清单ID = 日历初始值 
	 */
	public Map<String, String> queryAllCalInitByFjjx(String dprtcode, String fjjx){
		List<Map<String, String>> list = this.monitorDataMapper.selectAllCalInitByFjjx(dprtcode, fjjx);
		Map<String, String> map = new HashMap<String, String>(list.size());
		for (Map<String, String> tempMap : list) {
			map.put(tempMap.get("ID"), tempMap.get("CSZ"));
		}
		return map;
	}
	
	/**
	 * @Description 根据EOID查询需要进行监控的对飞机监控数据
	 * @CreateTime 2017年10月12日 上午10:44:30
	 * @CreateBy 徐勇
	 * @param id EO ID
	 * @return
	 */
	public List<MonitoringObject> queryEOPlaneNeedMonitorDataList(String id){
		return this.monitorDataMapper.selectEOPlaneNeedMonitorDataList(id);
	} 
	
	/**
	 * @Description 根据EO查询需要进行监控的对部件监控数据 
	 * @CreateTime 2017年10月12日 上午10:47:26
	 * @CreateBy 徐勇
	 * @param id EO ID
	 * @param dprtcode EO所属组织机构
	 * @param fjjx EO的飞机机型
	 * @return
	 */
	public List<MonitoringObject> queryEOPartNeedMonitorDataList(String id, String dprtcode, String fjjx){
		return this.monitorDataMapper.selectEOPartNeedMonitorDataList(id, dprtcode, fjjx);
	}
	
	/**
	 * @Description 将监控项单位转换为日历单位
	 * @CreateTime 2017年10月10日 下午8:21:52
	 * @CreateBy 徐勇
	 * @param unit 监控项单位
	 * @return
	 * @throws ParseException 
	 */
	public int getOffsetUnit(int unit) throws ParseException{
		if(unit == MonitorProjectUnitEnum.DAY.getCode()){
			return Calendar.DATE;
		}else if(unit == MonitorProjectUnitEnum.MONTH.getCode()){
			return Calendar.MONTH;
		}
		throw new ParseException(String.valueOf(unit), 0);
	}
	
	/**
	 * @Description 飞机注册时 增加监控数据
	 * @CreateTime 2017年10月16日 上午11:11:49
	 * @CreateBy 徐勇
	 * @param dprtcode 飞机所属组织机构
	 * @param fjjx 飞机机型
	 * @param fjzch 飞机注册号
	 * @param xlh 飞机序列号
	 * @param strCalInit 飞机的日历 初始值 
	 */
	public void add4AddPlane(String dprtcode, String fjjx, String fjzch, String xlh, String strCalInit){
		//获取该飞机的监控数据（包含维修项目和EO）
		List<MonitoringObject> monitorObjectList = this.monitorDataMapper.selectPlaneNeedMonitorDataList(dprtcode, fjjx, fjzch);
		if(monitorObjectList.isEmpty()){
			return;
		}
		
		List<String> mpIdList = new ArrayList<String>();//涉及到的维修项目ID
		List<String> eoIdList = new ArrayList<String>();//涉及到的EO ID
		List<String> poIdList = new ArrayList<String>();//涉及到的生产指令 ID
		for (MonitoringObject monitoringObject : monitorObjectList) {
			if(MaintenanceTypeEnum.PROJECT.getId() == monitoringObject.getLylx()){
				mpIdList.add(monitoringObject.getLyid());
			}else if (MaintenanceTypeEnum.EO.getId() == monitoringObject.getLylx()){
				eoIdList.add(monitoringObject.getLyid());
			}else if (MaintenanceTypeEnum.PRODUCTION_ORDER.getId() == monitoringObject.getLylx()){
				poIdList.add(monitoringObject.getLyid());
			}
		}
		//获取涉及的维修项目监控项目，并放入到map<维修项目ID,监控项目list>中
		Map<String, List<ProjectMonitor>> mpMonitorItemMap = this.queryMPMonitor(mpIdList);
		//获取涉及的EO监控项目，并放入到map<EOID_EOXC,监控项目list>中
		Map<String, List<EOMonitorIemSet>> eOMonitorIemSetMap = this.queryEOMonitor(eoIdList);
		//获取涉及的生产指令监控项目，并放入到map<生产指令ID,监控项目list>中
		Map<String, List<ProductionOrderMonitor>> poMonitorItemMap = this.queryPOMonitor(poIdList);
		
		List<MonitoringPlan> monitorPlanList = new ArrayList<MonitoringPlan>();//监控对象-预计执行数据
		MonitoringPlan monitoringPlan = null;
		List<ProjectMonitor> projectMonitorList = null;
		List<EOMonitorIemSet> eOMonitorIemSetList = null;
		List<ProductionOrderMonitor> productionOrderMonitorList = null;
		User user = ThreadVarUtil.getUser();
		//循环处理 监控对象、生成预计执行数据
		for (MonitoringObject monitoringObject : monitorObjectList) {
			monitoringObject.setFjxlh(xlh);
			monitoringObject.setWhbmid(user.getBmdm());
			monitoringObject.setWhrid(user.getId());
			if(MaintenanceTypeEnum.PROJECT.getId() == monitoringObject.getLylx()){ //处理维修项目的 监控项目
				projectMonitorList = mpMonitorItemMap.get(monitoringObject.getLyid());
				if(projectMonitorList != null){
					for (ProjectMonitor projectMonitor : projectMonitorList) {
						monitoringPlan = new MonitoringPlan(projectMonitor);
						monitoringPlan.setId(UUID.randomUUID().toString());
						monitoringPlan.setWhdwid(user.getBmdm());
						monitoringPlan.setWhrid(user.getId());
						monitoringPlan.setJksjid(monitoringObject.getId());
						monitoringPlan.setWz(monitoringObject.getWz());
						if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
							//日历计划值为初始+首次;
							//异常处理，当日历初始值或首次值为空 则 计划值为空
							if(StringUtils.isNotBlank(strCalInit) && StringUtils.isNotBlank(projectMonitor.getScz())){
								try {
									monitoringPlan.setJhz(DateUtil.getOffsetDate(strCalInit, Integer.parseInt(monitoringPlan.getScz()), this.getOffsetUnit(monitoringPlan.getDwScz())));
								} catch (ParseException e) {}
							}
						}else{//非日历
							monitoringPlan.setJhz(projectMonitor.getScz());
						}
						monitorPlanList.add(monitoringPlan);
					}
				}
			}else if(MaintenanceTypeEnum.EO.getId() == monitoringObject.getLylx()) {//处理EO的监控项目
				eOMonitorIemSetList = eOMonitorIemSetMap.get(monitoringObject.getLyid() +"_"+ monitoringObject.getEoxc());
				if(eOMonitorIemSetList != null){
					for (EOMonitorIemSet eOMonitorIemSet : eOMonitorIemSetList) {
						monitoringPlan = new MonitoringPlan(eOMonitorIemSet);
						monitoringPlan.setId(UUID.randomUUID().toString());
						monitoringPlan.setWhdwid(user.getBmdm());
						monitoringPlan.setWhrid(user.getId());
						monitoringPlan.setJksjid(monitoringObject.getId());
						monitoringPlan.setWz(monitoringObject.getWz());
						
						//EO重复执行的日历监控项，计划值 = 初始+首次
						if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
							//异常处理，当日历初始值或首次值为空 则 计划值为空
							monitoringPlan.setJhz(eOMonitorIemSet.getScz());
							if(StringUtils.isNotBlank(eOMonitorIemSet.getScz())){
//								Integer scz = null;
//								try{
//									scz = Integer.parseInt(monitoringPlan.getScz());
//									if(StringUtils.isNotBlank(strCalInit)){
//										try {
//											monitoringPlan.setJhz(DateUtil.getOffsetDate(strCalInit, scz, this.getOffsetUnit(monitoringPlan.getDwScz())));
//										} catch (ParseException e) {}
//									}
//								}catch(NumberFormatException e){
//									//当日历首次值无法转换为整型时，表示 EO为单次或分段
//									monitoringPlan.setJhz(eOMonitorIemSet.getScz());
//								}
								monitoringPlan.setScz("0");
								monitoringPlan.setDwScz(MonitorProjectUnitEnum.DAY.getCode());
							}else{
								monitoringPlan.setScz(null);
								monitoringPlan.setDwScz(null);
							}
						}else{//非日历
							monitoringPlan.setJhz(eOMonitorIemSet.getScz());
						}
						monitorPlanList.add(monitoringPlan);
					}
				}
			}else{ //处理生产指令
				productionOrderMonitorList = poMonitorItemMap.get(monitoringObject.getLyid());
				if(productionOrderMonitorList != null){
					for (ProductionOrderMonitor productionOrderMonitor : productionOrderMonitorList) {
						monitoringPlan = new MonitoringPlan(productionOrderMonitor);
						monitoringPlan.setId(UUID.randomUUID().toString());
						monitoringPlan.setWhdwid(user.getBmdm());
						monitoringPlan.setWhrid(user.getId());
						monitoringPlan.setJksjid(monitoringObject.getId());
						monitoringPlan.setWz(monitoringObject.getWz());
						if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
							//日历计划值为初始+首次;
							//异常处理，当日历初始值或首次值为空 则 计划值为空
//							if(StringUtils.isNotBlank(strCalInit) && StringUtils.isNotBlank(productionOrderMonitor.getScz())){
//								try {
//									monitoringPlan.setJhz(DateUtil.getOffsetDate(strCalInit, Integer.parseInt(monitoringPlan.getScz()), this.getOffsetUnit(monitoringPlan.getDwScz())));
//								} catch (ParseException e) {}
//							}
//							
							monitoringPlan.setJhz(monitoringPlan.getScz());
							if(StringUtils.isNotBlank(monitoringPlan.getScz())){
//								Integer scz = null;
//								try{
//									scz = Integer.parseInt(monitoringPlan.getScz());
//									if(StringUtils.isNotBlank(strCalInit)){
//										try {
//											monitoringPlan.setJhz(DateUtil.getOffsetDate(strCalInit, scz, this.getOffsetUnit(monitoringPlan.getDwScz())));
//										} catch (ParseException e) {}
//									}
//								}catch(NumberFormatException e){
//									//当日历首次值无法转换为整型时，表示 EO为单次或分段
//									monitoringPlan.setJhz(eOMonitorIemSet.getScz());
//								}
								monitoringPlan.setScz("0");
								monitoringPlan.setDwScz(MonitorProjectUnitEnum.DAY.getCode());
							}else{
								monitoringPlan.setScz(null);
								monitoringPlan.setDwScz(null);
							}
							
						}else{//非日历
							monitoringPlan.setJhz(productionOrderMonitor.getScz());
						}
						monitorPlanList.add(monitoringPlan);
					}
				}
			}
		}
		//保存监控数据
		this.saveMonitorObjectBatch(monitorObjectList);
		//按监控数据生成当前监控数据
		this.saveCurrentMonitor4BatchSync(monitorObjectList);
		//保存监控数据-计划执行数据
		this.saveMonitorPlanBatch(monitorPlanList);
	}
	
	/**
	 * @Description 飞机启用时增加监控数据
	 * @CreateTime 2018年4月8日 下午2:14:02
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjjx	飞机机型
	 * @param fjzch	飞机注册号
	 * @param fjxlh 飞机MSN
	 */
	public void add4EffectPlane(String dprtcode, String fjjx, String fjzch, String fjxlh){
		//获取飞机应监控数据
		//获取该飞机的监控数据（包含维修项目、生产指令和EO）
		List<MonitoringObject> planeMonitorObjectList = this.monitorDataMapper.selectPlaneNeedMonitorDataList(dprtcode, fjjx, fjzch);
		//获取飞机部件应监控数据
		List<MonitoringObject> partMonitorObjectList = this.monitorDataMapper.selectInstallNeedMonitorDataList(dprtcode, fjjx, fjzch);
		if(planeMonitorObjectList.isEmpty() && partMonitorObjectList.isEmpty()){
			return;
		}
		planeMonitorObjectList.addAll(partMonitorObjectList);
		List<String> mpIdList = new ArrayList<String>();//涉及到的维修项目ID
		List<String> eoIdList = new ArrayList<String>();//涉及到的EO ID
		List<String> poIdList = new ArrayList<String>();//涉及到的生产指令 ID
		for (MonitoringObject monitoringObject : planeMonitorObjectList) {
			if(MaintenanceTypeEnum.PROJECT.getId() == monitoringObject.getLylx()){
				mpIdList.add(monitoringObject.getLyid());
			}else if(MaintenanceTypeEnum.EO.getId() == monitoringObject.getLylx()){
				eoIdList.add(monitoringObject.getLyid());
			}else if (MaintenanceTypeEnum.PRODUCTION_ORDER.getId() == monitoringObject.getLylx()){
				poIdList.add(monitoringObject.getLyid());
			}
		}
		//获取涉及的维修项目监控项目，并放入到map<维修项目ID,监控项目list>中
		Map<String, List<ProjectMonitor>> mpMonitorItemMap = this.queryMPMonitor(mpIdList);
		//获取涉及的EO监控项目，并放入到map<EOID_EOXC,监控项目list>中
		Map<String, List<EOMonitorIemSet>> eOMonitorIemSetMap = this.queryEOMonitor(eoIdList);
		//获取涉及的生产指令监控项目，并放入到map<生产指令ID,监控项目list>中
			Map<String, List<ProductionOrderMonitor>> poMonitorItemMap = this.queryPOMonitor(poIdList);
				
		//获取飞机原监控数据，
		//当维修方案有前版本时 查询前版本维修方案未执行数据(带计划、上次执行)
		List<MonitoringObject> notExeList = this.monitoringObjectMapper.selectNotExeMonitorDataListByFjzch(dprtcode, fjzch);
		Map<String, MonitoringObject> notExeMap = new HashMap<String, MonitoringObject>();
		for (MonitoringObject monitoringObject : notExeList) {
    		notExeMap.put(getMonitorObjectKey(monitoringObject), monitoringObject);
		}
		notExeList = null;
		
		User user = ThreadVarUtil.getUser();
		List<MonitoringObject> cMOList = new ArrayList<MonitoringObject>();//待新增的监控数据
		List<MonitoringObject> uMOList = new ArrayList<MonitoringObject>();//待修改的监控数据
		List<MonitoringPlan> cMPlanList = new ArrayList<MonitoringPlan>();//待新增的计划数据（待新增及待修改的监控数据的计划数据）
		
		//查询 机型下飞机/在机序列件的 日历初始值，map中key值为 飞机注册号/装机清单ID
		List<Map<String, String>> calInitList = this.monitorDataMapper.selectAllCalInitByFjzch(dprtcode, fjzch);
		Map<String, String> calInitMap = new HashMap<String, String>(calInitList.size());
		for (Map<String, String> tempMap : calInitList) {
			calInitMap.put(tempMap.get("ID"), tempMap.get("CSZ"));
		}
		calInitList = null;
		
		//组装当前监控数据 及 计划、上次执行数据
		for (MonitoringObject monitoringObject : planeMonitorObjectList) {
			monitoringObject.setFjzch(fjzch);
			monitoringObject.setFjxlh(fjxlh);
			//获取原监控数据
			MonitoringObject notExeMO = this.getMatchedMonitorObject(notExeMap, monitoringObject, false);//原监控数据
			String strCalInit = null;
			if(StringUtils.isNotBlank(monitoringObject.getZjqdid())){
				strCalInit = calInitMap.get(monitoringObject.getZjqdid());
			}else{
				strCalInit = calInitMap.get(fjzch);
			}
			if(MaintenanceTypeEnum.PROJECT.getId() == monitoringObject.getLylx()){
				List<ProjectMonitor> projectMonitorList = mpMonitorItemMap.get(monitoringObject.getLyid());;
				if(StringUtils.isNotBlank(monitoringObject.getBjh())){
					for (Iterator<ProjectMonitor> iterator = projectMonitorList.iterator(); iterator.hasNext();) {
						ProjectMonitor projectMonitor = (ProjectMonitor) iterator.next();
						if(!monitoringObject.getBjh().equals(projectMonitor.getBjh())){
							iterator.remove();
						}
					}
					
				}
				this.buildMonitorData(cMOList, uMOList, cMPlanList, monitoringObject, notExeMO, strCalInit, user, projectMonitorList);
			}else if(MaintenanceTypeEnum.PRODUCTION_ORDER.getId() == monitoringObject.getLylx()){
				List<ProductionOrderMonitor> productionOrderMonitorList = poMonitorItemMap.get(monitoringObject.getLyid());;
				this.buildPOMonitorData(cMOList, uMOList, cMPlanList, monitoringObject, notExeMO, strCalInit, user, productionOrderMonitorList);
			} else{
				List<EOMonitorIemSet> monitorItemList = eOMonitorIemSetMap.get(monitoringObject.getLyid()+"_"+monitoringObject.getEoxc());
				EngineeringOrder engineeringOrder = engineeringOrderMapper.selectByPrimaryKey(monitoringObject.getLyid());
				int eoZxfs = engineeringOrder.getZxfs().intValue();
				this.buildEOMonitorData(cMOList, uMOList, cMPlanList, monitoringObject, notExeMO, strCalInit, user, eoZxfs, monitorItemList);
			}
		}
		
		//删除待修改监控数据的计划数据
		this.removeMonitorPlanByMainIdBatch(uMOList);
		//批量修改监控数据（处理待修改监控数据）
		this.updateMonitorObjectBatch(uMOList);
		//批量新增监控数据（处理待新增监控数据）
		this.saveMonitorObjectBatch(cMOList);
		//批量新增计划数据（处理待新增计划数据）
		this.saveMonitorPlanBatch(cMPlanList);
		//根据待修改、待新增监控数据 同步到 当前监控数据中
		uMOList.addAll(cMOList);
		this.saveCurrentMonitor4BatchSync(uMOList);
	}
	
	private void buildMonitorData(List<MonitoringObject> cMOList, List<MonitoringObject> uMOList, List<MonitoringPlan> cMPlanList, 
			MonitoringObject monitoringObject, MonitoringObject notExeMO,
			String strCalInit, User user,  List<ProjectMonitor> projectMonitorList){
		
		if(notExeMO == null){//不存在原监控数据，按维修项目生成监控数据
			//生成监控对象
			monitoringObject.setWhbmid(user.getBmdm());
			monitoringObject.setWhrid(user.getId());
			cMOList.add(monitoringObject);
			
			if(projectMonitorList == null){
				return;
			}
			//生成计划数据
			MonitoringPlan monitoringPlan = null;
			for (ProjectMonitor projectMonitor : projectMonitorList) {
				monitoringPlan = new MonitoringPlan(projectMonitor);
				monitoringPlan.setId(UUID.randomUUID().toString());
				monitoringPlan.setJksjid(monitoringObject.getId());
				monitoringPlan.setWhdwid(user.getBmdm());
				monitoringPlan.setWhrid(user.getId()); 
				monitoringPlan.setWz(monitoringObject.getWz());
				if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
					//异常处理，当日历初始值或首次值为空 则 计划值为空
					if(StringUtils.isNotBlank(strCalInit) && StringUtils.isNotBlank(projectMonitor.getScz())){
						try {
							monitoringPlan.setJhz(DateUtil.getOffsetDate(strCalInit, Integer.parseInt(monitoringPlan.getScz()), this.getOffsetUnit(monitoringPlan.getDwScz())));
						} catch (ParseException e) {}
					}
				}else{//非日历
					monitoringPlan.setJhz(projectMonitor.getScz());
				}
				cMPlanList.add(monitoringPlan);
			}
			
		}else{//存在原监控数据，更新原监控数据，并按维修项目生成新的计划
			
			//修改原监控对象
			notExeMO.setWhbmid(user.getBmdm());
			notExeMO.setWhrid(user.getId());
			notExeMO.setLyid(monitoringObject.getLyid());
			notExeMO.setLybh(monitoringObject.getLybh());
			notExeMO.setWz(monitoringObject.getWz());
			notExeMO.setJsgs(monitoringObject.getJsgs());
			notExeMO.setHdwz(monitoringObject.getHdwz());
			uMOList.add(notExeMO);
			if(projectMonitorList == null){
				return;
			}
			//生成计划数据
			List<MonitoringPlan> notExeMOPlanList = notExeMO.getMonitoringPlanList();//原监控数据计划数据
			List<MonitoringLast> notExeMOLastList = notExeMO.getMonitoringLastList();//原监控数据上次执行数据
			MonitoringPlan monitoringPlan = null;
			for (ProjectMonitor projectMonitor : projectMonitorList) {
				monitoringPlan = new MonitoringPlan(projectMonitor);
				monitoringPlan.setId(UUID.randomUUID().toString());
				monitoringPlan.setJksjid(notExeMO.getId());
				monitoringPlan.setWhdwid(user.getBmdm());
				monitoringPlan.setWhrid(user.getId());
				monitoringPlan.setWz(monitoringObject.getWz());
				
				//判断原监控计划是否有该监控项，有该监控项使用该监控项的T0值进行计算
				for (MonitoringPlan notExeMOPlan : notExeMOPlanList) {
					if(notExeMOPlan.getJklbh().equals(monitoringPlan.getJklbh())){
						monitoringPlan.setJhqsz(notExeMOPlan.getJhqsz());
						break;
					}
				}
				
				//计算间隔 = 首次or周期
				String interval = null;//间隔
				int intervalUnit = 0;//间隔单位
				
				//T0无值，判断是否有上次执行数据，有上次执行数据以周期计算，否则以首次计算
				if((notExeMOLastList != null && notExeMOLastList.size() > 0) || StringUtils.isNotBlank(notExeMO.getfJksjid())){
					//以周期进行计算
					interval = monitoringPlan.getZqz();
					intervalUnit = monitoringPlan.getDwZqz();
				}else{
					//以首次进行计算
					interval = monitoringPlan.getScz();
					intervalUnit = monitoringPlan.getDwScz();
				}
				
				//间隔不为空时计算计划值，间隔为空则计划值为空
				if(StringUtils.isNotBlank(interval)){
					if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
						//日历：为 T0为空时 使用初始+间隔，否则使用T0+间隔
						String t0 = monitoringPlan.getJhqsz();
						if(StringUtils.isBlank(t0)){
							//t0为空 后面以初始值进行计算
							t0 = strCalInit;
						}
						//异常处理，当日历初始值或首次值为空 则 计划值为空
						if(StringUtils.isNotBlank(t0)){
							try {
								monitoringPlan.setJhz(DateUtil.getOffsetDate(t0, Integer.parseInt(interval), this.getOffsetUnit(intervalUnit)));
							} catch (ParseException e) {}
						}
						
					}else{
						//非日历 T0+间隔
						monitoringPlan.setJhz(String.valueOf(Integer.parseInt(interval) + Integer.parseInt(StringUtils.isNotBlank(monitoringPlan.getJhqsz())?monitoringPlan.getJhqsz():"0")));
					}
				}else{
					monitoringPlan.setJhqsz(null);
				}
				cMPlanList.add(monitoringPlan);
			}
		}
	}
	
	private void buildPOMonitorData(List<MonitoringObject> cMOList, List<MonitoringObject> uMOList, List<MonitoringPlan> cMPlanList, 
			MonitoringObject monitoringObject, MonitoringObject notExeMO,
			String strCalInit, User user,  List<ProductionOrderMonitor> productionOrderMonitorList){
		
		if(notExeMO == null){//不存在原监控数据，按维修项目生成监控数据
			//生成监控对象
			monitoringObject.setWhbmid(user.getBmdm());
			monitoringObject.setWhrid(user.getId());
			cMOList.add(monitoringObject);
			
			if(productionOrderMonitorList == null){
				return;
			}
			//生成计划数据
			MonitoringPlan monitoringPlan = null;
			for (ProductionOrderMonitor productionOrderMonitor : productionOrderMonitorList) {
				monitoringPlan = new MonitoringPlan(productionOrderMonitor);
				monitoringPlan.setId(UUID.randomUUID().toString());
				monitoringPlan.setJksjid(monitoringObject.getId());
				monitoringPlan.setWhdwid(user.getBmdm());
				monitoringPlan.setWhrid(user.getId()); 
				monitoringPlan.setWz(monitoringObject.getWz());
				if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
					//异常处理，当日历初始值或首次值为空 则 计划值为空
//					if(StringUtils.isNotBlank(strCalInit) && StringUtils.isNotBlank(productionOrderMonitor.getScz())){
//						try {
//							monitoringPlan.setJhz(DateUtil.getOffsetDate(strCalInit, Integer.parseInt(monitoringPlan.getScz()), this.getOffsetUnit(monitoringPlan.getDwScz())));
//						} catch (ParseException e) {}
//					}
					monitoringPlan.setJhz(monitoringPlan.getScz());
					if(StringUtils.isNotBlank(monitoringPlan.getScz())){
						monitoringPlan.setScz("0");
						monitoringPlan.setDwScz(MonitorProjectUnitEnum.DAY.getCode());
					}else{
						monitoringPlan.setScz(null);
						monitoringPlan.setDwScz(null);
					}
				}else{//非日历
					monitoringPlan.setJhz(productionOrderMonitor.getScz());
				}
				cMPlanList.add(monitoringPlan);
			}
			
		}else{//存在原监控数据，更新原监控数据，并生成新的计划
			
			//修改原监控对象
			notExeMO.setWhbmid(user.getBmdm());
			notExeMO.setWhrid(user.getId());
			notExeMO.setLyid(monitoringObject.getLyid());
			notExeMO.setLybh(monitoringObject.getLybh());
			notExeMO.setWz(monitoringObject.getWz());
			notExeMO.setJsgs(monitoringObject.getJsgs());
			notExeMO.setHdwz(monitoringObject.getHdwz());
			uMOList.add(notExeMO);
			if(productionOrderMonitorList == null){
				return;
			}
			//生成计划数据
			List<MonitoringPlan> notExeMOPlanList = notExeMO.getMonitoringPlanList();//原监控数据计划数据
			List<MonitoringLast> notExeMOLastList = notExeMO.getMonitoringLastList();//原监控数据上次执行数据
			MonitoringPlan monitoringPlan = null;
			for (ProductionOrderMonitor productionOrderMonitor : productionOrderMonitorList) {
				monitoringPlan = new MonitoringPlan(productionOrderMonitor);
				monitoringPlan.setId(UUID.randomUUID().toString());
				monitoringPlan.setJksjid(notExeMO.getId());
				monitoringPlan.setWhdwid(user.getBmdm());
				monitoringPlan.setWhrid(user.getId());
				monitoringPlan.setWz(monitoringObject.getWz());
				
				//判断原监控计划是否有该监控项，有该监控项使用该监控项的T0值进行计算
				for (MonitoringPlan notExeMOPlan : notExeMOPlanList) {
					if(notExeMOPlan.getJklbh().equals(monitoringPlan.getJklbh())){
						monitoringPlan.setJhqsz(notExeMOPlan.getJhqsz());
						break;
					}
				}
				
				//计算间隔 = 首次or周期
				String interval = null;//间隔
				int intervalUnit = 0;//间隔单位
				boolean isSc = false;//是否首次

				//T0无值，判断是否有上次执行数据，有上次执行数据以周期计算，否则以首次计算
				if((notExeMOLastList != null && notExeMOLastList.size() > 0) || StringUtils.isNotBlank(notExeMO.getfJksjid())){
					//以周期进行计算
					interval = monitoringPlan.getZqz();
					intervalUnit = monitoringPlan.getDwZqz();
				}else{
					//以首次进行计算
					interval = monitoringPlan.getScz();
					intervalUnit = monitoringPlan.getDwScz();
					isSc = true;
				}
				
				//间隔不为空时计算计划值，间隔为空则计划值为空
				if(StringUtils.isNotBlank(interval)){
					if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
						//日历：为 T0为空时 使用初始+间隔，否则使用T0+间隔
//						String t0 = monitoringPlan.getJhqsz();
//						if(StringUtils.isBlank(t0)){
//							//t0为空 后面以初始值进行计算
//							t0 = strCalInit;
//						}
//						//异常处理，当日历初始值或首次值为空 则 计划值为空
//						if(StringUtils.isNotBlank(t0)){
//							try {
//								monitoringPlan.setJhz(DateUtil.getOffsetDate(t0, Integer.parseInt(interval), this.getOffsetUnit(intervalUnit)));
//							} catch (ParseException e) {}
//						}
						
						String t0 = monitoringPlan.getJhqsz();
						if(StringUtils.isBlank(t0)){
							//t0为空 后面以初始值进行计算
							//t0 = calInitMap.get(monitoringObject.getFjzch());
							t0 = monitoringPlan.getScz();
						}
						//异常处理，当日历初始值或首次值为空 则 计划值为空
						if(StringUtils.isNotBlank(t0)){
							if(isSc){
								monitoringPlan.setJhz(t0);
							}else{
								try {
									monitoringPlan.setJhz(DateUtil.getOffsetDate(t0, Integer.parseInt(interval), this.getOffsetUnit(intervalUnit)));
								} catch (ParseException e) {}
							}
						}
						
						if(StringUtils.isNotBlank(monitoringPlan.getScz())){
							monitoringPlan.setScz("0");
							monitoringPlan.setDwScz(MonitorProjectUnitEnum.DAY.getCode());
						}else{
							monitoringPlan.setScz(null);
							monitoringPlan.setDwScz(null);
						}
						
					}else{
						//非日历 T0+间隔
						monitoringPlan.setJhz(String.valueOf(Integer.parseInt(interval) + Integer.parseInt(StringUtils.isNotBlank(monitoringPlan.getJhqsz())?monitoringPlan.getJhqsz():"0")));
					}
				}else{
					monitoringPlan.setJhqsz(null);
				}
				cMPlanList.add(monitoringPlan);
			}
		}
	}
	
	private void buildEOMonitorData(List<MonitoringObject> cMOList, List<MonitoringObject> uMOList, List<MonitoringPlan> cMPlanList, 
			MonitoringObject monitoringObject, MonitoringObject notExeMO,
			String strCalInit, User user, int eoZxfs,  List<EOMonitorIemSet> monitorItemList) {
		
		if(notExeMO == null){//不存在原监控数据，按维修项目生成监控数据
			//生成监控对象
			monitoringObject.setWhbmid(user.getBmdm());
			monitoringObject.setWhrid(user.getId());
			cMOList.add(monitoringObject);
			
			//生成计划数据
			//根据项次获取监控项
			if(monitorItemList == null){
				return;
			}
			MonitoringPlan monitoringPlan = null;
			for (EOMonitorIemSet eoMonitorIemSet : monitorItemList) {
				monitoringPlan = new MonitoringPlan(eoMonitorIemSet);
				monitoringPlan.setId(UUID.randomUUID().toString());
				monitoringPlan.setJksjid(monitoringObject.getId());
				monitoringPlan.setWhdwid(user.getBmdm());
				monitoringPlan.setWhrid(user.getId());
				//EO执行方式=单次或分段 计划值为EO监控项中的首次值
				if(eoZxfs != ExecutionEnum.REPEAT.getId().intValue()){
					monitoringPlan.setJhz(eoMonitorIemSet.getScz());
				}else{//EO执行方式=重复
					if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
						//日历计划值为初始+首次; 当监控对象为飞机或在机件（飞机注册号不为空）时，取日历初始值+首次， 库存及外场件（飞机注册和装机清单ID均为空） 时计划值为空
						//异常处理，当日历初始值或首次值为空 则 计划值为空
//						if(StringUtils.isNotBlank(strCalInit) && StringUtils.isNotBlank(eoMonitorIemSet.getScz())){
//							try {
//								monitoringPlan.setJhz(DateUtil.getOffsetDate(strCalInit, Integer.parseInt(monitoringPlan.getScz()), this.getOffsetUnit(monitoringPlan.getDwScz())));
//							} catch (ParseException e) {}
//						}
						monitoringPlan.setJhz(monitoringPlan.getScz());
						if(StringUtils.isNotBlank(monitoringPlan.getScz())){
							monitoringPlan.setScz("0");
							monitoringPlan.setDwScz(MonitorProjectUnitEnum.DAY.getCode());
						}else{
							monitoringPlan.setScz(null);
							monitoringPlan.setDwScz(null);
						}
					}else{//非日历
						monitoringPlan.setJhz(eoMonitorIemSet.getScz());
					}
				}
				
				cMPlanList.add(monitoringPlan);
			}
		}else{
			//修改原监控对象
			notExeMO.setWhbmid(user.getBmdm());
			notExeMO.setWhrid(user.getId());
			notExeMO.setLyid(monitoringObject.getLyid());
			notExeMO.setLybh(monitoringObject.getLybh());
			notExeMO.setWz(monitoringObject.getWz());
			notExeMO.setJsgs(monitoringObject.getJsgs());
			notExeMO.setHdwz(monitoringObject.getHdwz());
			uMOList.add(notExeMO);
			if(monitorItemList == null){
				return;
			}
			//生成计划数据
			List<MonitoringPlan> notExeMOPlanList = notExeMO.getMonitoringPlanList();//原监控数据计划数据
			List<MonitoringLast> notExeMOLastList = notExeMO.getMonitoringLastList();//原监控数据上次执行数据
			MonitoringPlan monitoringPlan = null;
			for (EOMonitorIemSet eoMonitorIemSet : monitorItemList) {
				monitoringPlan = new MonitoringPlan(eoMonitorIemSet);
				monitoringPlan.setId(UUID.randomUUID().toString());
				monitoringPlan.setJksjid(notExeMO.getId());
				monitoringPlan.setWhdwid(user.getBmdm());
				monitoringPlan.setWhrid(user.getId());
				monitoringPlan.setWz(monitoringObject.getWz());
				//EO执行方式=单次或分段 计划值为EO监控项中的首次值
				if(eoZxfs != ExecutionEnum.REPEAT.getId().intValue()){
					monitoringPlan.setJhz(eoMonitorIemSet.getScz());
				}else{
					//判断原监控计划是否有该监控项，有该监控项使用该监控项的T0值进行计算
					for (MonitoringPlan notExeMOPlan : notExeMOPlanList) {
						if(notExeMOPlan.getJklbh().equals(monitoringPlan.getJklbh())){
							monitoringPlan.setJhqsz(notExeMOPlan.getJhqsz());
							break;
						}
					}
					
					//计算间隔 = 首次or周期
					String interval = null;//间隔
					int intervalUnit = 0;//间隔单位
					boolean isSc = false;//是否首次
					
					//T0无值，判断是否有上次执行数据，有上次执行数据以周期计算，否则以首次计算
					if((notExeMOLastList != null && notExeMOLastList.size() > 0) || StringUtils.isNotBlank(notExeMO.getfJksjid())){
						//以周期进行计算
						interval = monitoringPlan.getZqz();
						intervalUnit = monitoringPlan.getDwZqz();
					}else{
						//以首次进行计算
						interval = monitoringPlan.getScz();
						intervalUnit = monitoringPlan.getDwScz();
						isSc = true;
					}
					
					//间隔不为空时计算计划值，间隔为空则计划值为空
					if(StringUtils.isNotBlank(interval)){
						if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
							//日历：为 T0为空时 使用初始+间隔，否则使用T0+间隔
//							String t0 = monitoringPlan.getJhqsz();
//							if(StringUtils.isBlank(t0)){
//								//t0为空 后面以初始值进行计算
//								t0 = strCalInit;
//							}
//							//异常处理，当日历初始值或首次值为空 则 计划值为空
//							if(StringUtils.isNotBlank(t0)){
//								try {
//									monitoringPlan.setJhz(DateUtil.getOffsetDate(t0, Integer.parseInt(interval), this.getOffsetUnit(intervalUnit)));
//								} catch (ParseException e) {}
//							}
							String t0 = monitoringPlan.getJhqsz();
							if(StringUtils.isBlank(t0)){
								//t0为空 后面以初始值进行计算
								//t0 = calInitMap.get(monitoringObject.getFjzch());
								t0 = monitoringPlan.getScz();
							}
							//异常处理，当日历初始值或首次值为空 则 计划值为空
							if(StringUtils.isNotBlank(t0)){
								if(isSc){
									monitoringPlan.setJhz(t0);
								}else{
									try {
										monitoringPlan.setJhz(DateUtil.getOffsetDate(t0, Integer.parseInt(interval), this.getOffsetUnit(intervalUnit)));
									} catch (ParseException e) {}
								}
							}
							
							if(StringUtils.isNotBlank(monitoringPlan.getScz())){
								monitoringPlan.setScz("0");
								monitoringPlan.setDwScz(MonitorProjectUnitEnum.DAY.getCode());
							}else{
								monitoringPlan.setScz(null);
								monitoringPlan.setDwScz(null);
							}
						}else{
							//非日历 T0+间隔
							monitoringPlan.setJhz(String.valueOf(Integer.parseInt(interval) + Integer.parseInt(StringUtils.isNotBlank(monitoringPlan.getJhqsz())?monitoringPlan.getJhqsz():"0")));
						}
					}else{
						monitoringPlan.setJhqsz(null);
					}
				}
				cMPlanList.add(monitoringPlan);
			}
		}

		
	}
	
	/**
	 * @Description 查询维修项目监控项目
	 * @CreateTime 2017年10月25日 下午2:59:32
	 * @CreateBy 徐勇
	 * @param mpIdList 维修项目ID
	 * @return
	 */
	private Map<String, List<ProjectMonitor>> queryMPMonitor(List<String> mpIdList){
		Map<String, List<ProjectMonitor>> mpMonitorItemMap = new HashMap<String, List<ProjectMonitor>>();
		if(mpIdList.size() > 0){
			
			/* HCHU-001:modify by hchu 20171027 start : 解决mpIdList超过1000size的问题 */
			List<ProjectMonitor> mpMonitorItemList = new ArrayList<ProjectMonitor>();
			for (int i = 0; i < Math.ceil((double) mpIdList.size() / 1000); i++) {
				mpMonitorItemList.addAll(this.maintenanceProjectMapper.selectMonitorByMainIds(mpIdList.subList(i * 1000,
						mpIdList.size() < ((i + 1) * 1000) ? mpIdList.size() : ((i + 1) * 1000))));
			}
			//屏蔽了原先的代码，紧接着的一行
			//List<ProjectMonitor> mpMonitorItemList = this.maintenanceProjectMapper.selectMonitorByMainIds(mpIdList);
			/* HCHU-001:modify by hchu 20171027 end */
			
			if(mpMonitorItemList.size() > 0){
				String mpId = mpMonitorItemList.get(0).getMainid();
				List<ProjectMonitor> tempList = new ArrayList<ProjectMonitor>();
				for (ProjectMonitor projectMonitor : mpMonitorItemList) {
					if(mpId.equals(projectMonitor.getMainid())){
						tempList.add(projectMonitor);
					}else{
						mpMonitorItemMap.put(mpId, tempList);
						mpId = projectMonitor.getMainid();
						tempList = new ArrayList<ProjectMonitor>();
						tempList.add(projectMonitor);
					}
				}
				mpMonitorItemMap.put(mpId, tempList);
			}
		}
		return mpMonitorItemMap;
	}
	
	/**
	 * @Description 查询生产指令监控项目
	 * @CreateTime 2017年10月25日 下午2:59:32
	 * @CreateBy 徐勇
	 * @param poIdList 生产指令ID
	 * @return
	 */
	private Map<String, List<ProductionOrderMonitor>> queryPOMonitor(List<String> poIdList){
		Map<String, List<ProductionOrderMonitor>> poMonitorItemMap = new HashMap<String, List<ProductionOrderMonitor>>();
		if(poIdList.size() > 0){
			
			List<ProductionOrderMonitor> poMonitorItemList = new ArrayList<ProductionOrderMonitor>();
			for (int i = 0; i < Math.ceil((double) poIdList.size() / 1000); i++) {
				poMonitorItemList.addAll(this.productionOrderMonitorMapper.selectMonitorByMainIds(poIdList.subList(i * 1000,
						poIdList.size() < ((i + 1) * 1000) ? poIdList.size() : ((i + 1) * 1000))));
			}
			
			if(poMonitorItemList.size() > 0){
				String mpId = poMonitorItemList.get(0).getMainid();
				List<ProductionOrderMonitor> tempList = new ArrayList<ProductionOrderMonitor>();
				for (ProductionOrderMonitor poMonitor : poMonitorItemList) {
					if(mpId.equals(poMonitor.getMainid())){
						tempList.add(poMonitor);
					}else{
						poMonitorItemMap.put(mpId, tempList);
						mpId = poMonitor.getMainid();
						tempList = new ArrayList<ProductionOrderMonitor>();
						tempList.add(poMonitor);
					}
				}
				poMonitorItemMap.put(mpId, tempList);
			}
		}
		return poMonitorItemMap;
	}
	
	/**
	 * @Description 查询维修项目监控项目
	 * @CreateTime 2017年10月25日 下午2:59:32
	 * @CreateBy 徐勇
	 * @param mpIdList 维修项目ID
	 * @param bjh 部件号
	 * @return
	 */
	private Map<String, List<ProjectMonitor>> queryMPMonitor(List<String> mpIdList, String bjh){
		Map<String, List<ProjectMonitor>> mpMonitorItemMap = new HashMap<String, List<ProjectMonitor>>();
		if(mpIdList.size() > 0){
			List<ProjectMonitor> mpMonitorItemList = this.maintenanceProjectMapper.selectMonitorByMainIdsAndBjh(bjh, mpIdList);
			if(mpMonitorItemList.size() > 0){
				String mpId = mpMonitorItemList.get(0).getMainid();
				List<ProjectMonitor> tempList = new ArrayList<ProjectMonitor>();
				for (ProjectMonitor projectMonitor : mpMonitorItemList) {
					if(mpId.equals(projectMonitor.getMainid())){
						tempList.add(projectMonitor);
					}else{
						mpMonitorItemMap.put(mpId, tempList);
						mpId = projectMonitor.getMainid();
						tempList = new ArrayList<ProjectMonitor>();
						tempList.add(projectMonitor);
					}
				}
				mpMonitorItemMap.put(mpId, tempList);
			}
		}
		return mpMonitorItemMap;
	}
	
	/**
	 * @Description 查询EO监控项目
	 * @CreateTime 2017年10月25日 下午3:02:35
	 * @CreateBy 徐勇
	 * @param eoIdList EO ID
	 * @return
	 */
	private Map<String, List<EOMonitorIemSet>> queryEOMonitor(List<String> eoIdList){
		Map<String, List<EOMonitorIemSet>> eOMonitorIemSetMap = new HashMap<String, List<EOMonitorIemSet>>();
		if(eoIdList.size() > 0){
			
			/* HCHU-003:modify by hchu 20171027 start : 解决mpIdList超过1000size的问题 */
			List<EOMonitorIemSet> eOMonitorIemSetList = new ArrayList<EOMonitorIemSet>();
			for (int i = 0; i < Math.ceil((double) eoIdList.size() / 1000); i++) {
				eOMonitorIemSetList.addAll(this.eOMonitorIemSetMapper.selectMonitorByMainIds(eoIdList.subList(i * 1000,
						eoIdList.size() < ((i + 1) * 1000) ? eoIdList.size() : ((i + 1) * 1000))));
			}
			//屏蔽了原先的代码，紧接着的一行
			//List<EOMonitorIemSet> eOMonitorIemSetList = this.eOMonitorIemSetMapper.selectMonitorByMainIds(eoIdList);
			/* HCHU-003:modify by hchu 20171027 end */
			
			if(eOMonitorIemSetList.size() > 0){
				String eoId = eOMonitorIemSetList.get(0).getMainid();
				int xc = eOMonitorIemSetList.get(0).getXc().intValue();
				List<EOMonitorIemSet> tempList = new ArrayList<EOMonitorIemSet>();
				for (EOMonitorIemSet eOMonitorIemSet : eOMonitorIemSetList) {
					if(eoId.equals(eOMonitorIemSet.getMainid()) && xc == eOMonitorIemSet.getXc().intValue()){
						tempList.add(eOMonitorIemSet);
					}else{
						eOMonitorIemSetMap.put(eoId+"_"+xc, tempList);
						eoId = eOMonitorIemSet.getMainid();
						xc = eOMonitorIemSet.getXc().intValue();
						tempList = new ArrayList<EOMonitorIemSet>();
						tempList.add(eOMonitorIemSet);
					}
				}
				eOMonitorIemSetMap.put(eoId+"_"+xc, tempList);
			}
		}
		return eOMonitorIemSetMap;
	}

	
	/**
	 * @Description EO执行对象关闭 删除当前监控数据 
	 * @CreateTime 2017年10月18日 下午2:39:28
	 * @CreateBy 徐勇
	 * @param eoId EOID
	 * @param fjzch 飞机注册号 执行对象为飞机时传此参数
	 * @param bjh 部件号 执行对象为部件时需要传此参数
	 * @param xlh 部件序列号 执行对象为部件时需要传此参数
	 * @throws BusinessException 
	 */
	public void removeByEOExecObject(String eoId, String fjzch, String bjh, String xlh) throws BusinessException{
		if(StringUtils.isNotBlank(fjzch)){
			this.monitoringCurrentMapper.deleteByEOExecObject(eoId, fjzch, null, null);
		}else if(StringUtils.isNotBlank(bjh) && StringUtils.isNotBlank(xlh)){
			this.monitoringCurrentMapper.deleteByEOExecObject(eoId, null, bjh, xlh);
		}else{
			throw new BusinessException("执行对象不存在！");
		}
	}
	
	/**
	 * @Description 装机清单生效时-装上件生成监控数据
	 * @CreateTime 2017年10月25日 下午4:03:27
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjjx 飞机机型
	 * @param fjzch 飞机注册号
	 * @param fjxlh 飞机序列号
	 * @param zjqdid 装机清单ID
	 * @param bjh 部件号
	 * @param xlh 序列号
	 * @param strCalInit 部件日历初始值
	 */
	public void add4installComponent(String dprtcode, String fjjx, String fjzch, String fjxlh, String zjqdid, String bjh, String xlh, String strCalInit){
		//获取装机件 监控对象数据（包括维修项目和EO）
		List<MonitoringObject> monitorObjectList = this.monitorDataMapper.selectInstallComponentNeedMonitorDataList(dprtcode, fjjx, fjzch, bjh, xlh);
		if(monitorObjectList.isEmpty()){
			return;
		}
		
		List<String> mpIdList = new ArrayList<String>();//涉及到的维修项目ID
		List<String> eoIdList = new ArrayList<String>();//涉及到的EO ID
		for (MonitoringObject monitoringObject : monitorObjectList) {
			if(MaintenanceTypeEnum.PROJECT.getId() == monitoringObject.getLylx()){
				mpIdList.add(monitoringObject.getLyid());
			}else{
				eoIdList.add(monitoringObject.getLyid());
			}
		}
		//获取涉及的维修项目监控项目，并放入到map<维修项目ID,监控项目list>中
		Map<String, List<ProjectMonitor>> mpMonitorItemMap = this.queryMPMonitor(mpIdList, bjh);
		//获取涉及的EO监控项目，并放入到map<EOID_EOXC,监控项目list>中
		Map<String, List<EOMonitorIemSet>> eOMonitorIemSetMap = this.queryEOMonitor(eoIdList);
		
		List<MonitoringPlan> monitorPlanList = new ArrayList<MonitoringPlan>();//监控对象-预计执行数据
		MonitoringPlan monitoringPlan = null;
		List<ProjectMonitor> projectMonitorList = null;
		List<EOMonitorIemSet> eOMonitorIemSetList = null;
		User user = ThreadVarUtil.getUser();
		//循环处理 监控对象、生成预计执行数据
		for (MonitoringObject monitoringObject : monitorObjectList) {
			monitoringObject.setFjxlh(fjxlh);
			monitoringObject.setZjqdid(zjqdid);
			monitoringObject.setBjh(bjh);
			monitoringObject.setXlh(xlh);
			monitoringObject.setWhbmid(user.getBmdm());
			monitoringObject.setWhrid(user.getId());
			if(MaintenanceTypeEnum.PROJECT.getId() == monitoringObject.getLylx()){ //处理维修项目的 监控项目
				projectMonitorList = mpMonitorItemMap.get(monitoringObject.getLyid());
				if(projectMonitorList != null){
					for (ProjectMonitor projectMonitor : projectMonitorList) {
						monitoringPlan = new MonitoringPlan(projectMonitor);
						monitoringPlan.setId(UUID.randomUUID().toString());
						monitoringPlan.setWhdwid(user.getBmdm());
						monitoringPlan.setWhrid(user.getId());
						monitoringPlan.setJksjid(monitoringObject.getId());
						monitoringPlan.setWz(monitoringObject.getWz());
						if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
							//日历计划值为初始+首次;
							//异常处理，当日历初始值或首次值为空 则 计划值为空
							if(StringUtils.isNotBlank(strCalInit) && StringUtils.isNotBlank(projectMonitor.getScz())){
								try {
									monitoringPlan.setJhz(DateUtil.getOffsetDate(strCalInit, Integer.parseInt(monitoringPlan.getScz()), this.getOffsetUnit(monitoringPlan.getDwScz())));
								} catch (ParseException e) {}
							}
						}else{//非日历
							monitoringPlan.setJhz(projectMonitor.getScz());
						}
						monitorPlanList.add(monitoringPlan);
					}
				}
			}else{//处理EO的监控项目
				eOMonitorIemSetList = eOMonitorIemSetMap.get(monitoringObject.getLyid() +"_"+ monitoringObject.getEoxc());
				if(eOMonitorIemSetList != null){
					for (EOMonitorIemSet eOMonitorIemSet : eOMonitorIemSetList) {
						monitoringPlan = new MonitoringPlan(eOMonitorIemSet);
						monitoringPlan.setId(UUID.randomUUID().toString());
						monitoringPlan.setWhdwid(user.getBmdm());
						monitoringPlan.setWhrid(user.getId());
						monitoringPlan.setJksjid(monitoringObject.getId());
						monitoringPlan.setWz(monitoringObject.getWz());
						
						//EO重复执行的日历监控项，计划值 = 初始+首次
						if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
							//异常处理，当日历初始值或首次值为空 则 计划值为空
//							if(StringUtils.isNotBlank(eOMonitorIemSet.getScz())){
//								Integer scz = null;
//								try{
//									scz = Integer.parseInt(monitoringPlan.getScz());
//									if(StringUtils.isNotBlank(strCalInit)){
//										try {
//											monitoringPlan.setJhz(DateUtil.getOffsetDate(strCalInit, scz, this.getOffsetUnit(monitoringPlan.getDwScz())));
//										} catch (ParseException e) {}
//									}
//								}catch(NumberFormatException e){
//									//当日历首次值无法转换为整型时，表示 EO为单次或分段
//									monitoringPlan.setJhz(eOMonitorIemSet.getScz());
//								}
//							}
							//日历计划值就是日历
							monitoringPlan.setJhz(monitoringPlan.getScz());
							if(StringUtils.isNotBlank(monitoringPlan.getScz())){
								monitoringPlan.setScz("0");
								monitoringPlan.setDwScz(MonitorProjectUnitEnum.DAY.getCode());
							}else{
								monitoringPlan.setScz(null);
								monitoringPlan.setDwScz(null);
							}
						}else{//非日历
							monitoringPlan.setJhz(eOMonitorIemSet.getScz());
						}
						monitorPlanList.add(monitoringPlan);
					}
				}
			}
		}
		
		if (null != monitorObjectList && monitorObjectList.size() > 0) {
			// 保存监控数据
			this.saveMonitorObjectBatch(monitorObjectList);
			// 按监控数据生成当前监控数据
			this.saveCurrentMonitor4BatchSync(monitorObjectList);
			//保存监控数据-计划执行数据
			if (null != monitorPlanList && monitorPlanList.size() > 0) {
				this.saveMonitorPlanBatch(monitorPlanList);
			}
		}
	}
	
	/**
	 * @Description 拆下件撤销时-生成监控数据，需考虑之前拆下时的数据
	 * @CreateTime 2017年10月25日 下午4:03:27
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjjx 飞机机型
	 * @param fjzch 飞机注册号
	 * @param fjxlh 飞机序列号
	 * @param zjqdid 装机清单ID
	 * @param bjh 部件号
	 * @param xlh 序列号
	 * @param strCalInit 部件日历初始值
	 */
	public void add4RemoveComponentUndo(String dprtcode, String fjjx, String fjzch, String fjxlh, String zjqdid, String bjh, String xlh, String strCalInit){
		//获取装机件 监控对象数据（包括维修项目和EO）
		List<MonitoringObject> monitorObjectList = this.monitorDataMapper.selectInstallComponentNeedMonitorDataList(dprtcode, fjjx, fjzch, bjh, xlh);
		if(monitorObjectList.isEmpty()){
			return;
		}
		
		List<String> mpIdList = new ArrayList<String>();//涉及到的维修项目ID
		List<String> eoIdList = new ArrayList<String>();//涉及到的EO ID
		for (MonitoringObject monitoringObject : monitorObjectList) {
			if(MaintenanceTypeEnum.PROJECT.getId() == monitoringObject.getLylx()){
				mpIdList.add(monitoringObject.getLyid());
			}else{
				eoIdList.add(monitoringObject.getLyid());
			}
		}
		//获取涉及的维修项目监控项目，并放入到map<维修项目ID,监控项目list>中
		Map<String, List<ProjectMonitor>> mpMonitorItemMap = this.queryMPMonitor(mpIdList, bjh);
		//获取涉及的EO监控项目，并放入到map<EOID_EOXC,监控项目list>中
		Map<String, List<EOMonitorIemSet>> eOMonitorIemSetMap = this.queryEOMonitor(eoIdList);
		
		//查询之前最后一次的监控数据(可能是执行过，也可能是未执行的)
		List<MonitoringObject> lastMOList = this.monitoringObjectMapper.selectLastMonitorByZjqdid(zjqdid);
		
		
		Map<String, MonitoringObject> lastMOMap = new HashMap<String, MonitoringObject>(lastMOList.size());
    	for (MonitoringObject monitoringObject : lastMOList) {
    		lastMOMap.put(getMonitorObjectKey(monitoringObject), monitoringObject);
		}
		
    	List<MonitoringObject> cMOList = new ArrayList<MonitoringObject>();//待新增的监控数据
    	List<MonitoringObject> uMOList = new ArrayList<MonitoringObject>();//待修改的监控数据
    	List<MonitoringLast> monitorLastList = new ArrayList<MonitoringLast>();//监控对象-上次执行数据
		List<MonitoringPlan> monitorPlanList = new ArrayList<MonitoringPlan>();//监控对象-预计执行数据
		MonitoringPlan monitoringPlan = null;
		List<ProjectMonitor> projectMonitorList = null;
		List<EOMonitorIemSet> eOMonitorIemSetList = null;
		User user = ThreadVarUtil.getUser();
		//循环处理 监控对象、生成预计执行数据
		for (MonitoringObject monitoringObject : monitorObjectList) {
			monitoringObject.setFjxlh(fjxlh);
			monitoringObject.setZjqdid(zjqdid);
			monitoringObject.setBjh(bjh);
			monitoringObject.setXlh(xlh);
			monitoringObject.setWhbmid(user.getBmdm());
			monitoringObject.setWhrid(user.getId());
			MonitoringObject lastMO = this.getMatchedMonitorObject(lastMOMap, monitoringObject, false);//原监控数据
			if(lastMO == null){//没有之前的记录，新增监控数据
				cMOList.add(monitoringObject);
				if(MaintenanceTypeEnum.PROJECT.getId() == monitoringObject.getLylx()){ //处理维修项目的 监控项目
					projectMonitorList = mpMonitorItemMap.get(monitoringObject.getLyid());
					if(projectMonitorList != null){
						for (ProjectMonitor projectMonitor : projectMonitorList) {
							monitoringPlan = new MonitoringPlan(projectMonitor);
							monitoringPlan.setId(UUID.randomUUID().toString());
							monitoringPlan.setWhdwid(user.getBmdm());
							monitoringPlan.setWhrid(user.getId());
							monitoringPlan.setJksjid(monitoringObject.getId());
							monitoringPlan.setWz(monitoringObject.getWz());
							if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
								//日历计划值为初始+首次;
								//异常处理，当日历初始值或首次值为空 则 计划值为空
								if(StringUtils.isNotBlank(strCalInit) && StringUtils.isNotBlank(projectMonitor.getScz())){
									try {
										monitoringPlan.setJhz(DateUtil.getOffsetDate(strCalInit, Integer.parseInt(monitoringPlan.getScz()), this.getOffsetUnit(monitoringPlan.getDwScz())));
									} catch (ParseException e) {}
								}
							}else{//非日历
								monitoringPlan.setJhz(projectMonitor.getScz());
							}
							monitorPlanList.add(monitoringPlan);
						}
					}
				}else{//处理EO的监控项目
					eOMonitorIemSetList = eOMonitorIemSetMap.get(monitoringObject.getLyid() +"_"+ monitoringObject.getEoxc());
					if(eOMonitorIemSetList != null){
						for (EOMonitorIemSet eOMonitorIemSet : eOMonitorIemSetList) {
							monitoringPlan = new MonitoringPlan(eOMonitorIemSet);
							monitoringPlan.setId(UUID.randomUUID().toString());
							monitoringPlan.setWhdwid(user.getBmdm());
							monitoringPlan.setWhrid(user.getId());
							monitoringPlan.setJksjid(monitoringObject.getId());
							monitoringPlan.setWz(monitoringObject.getWz());
							
							//EO重复执行的日历监控项，计划值 = 初始+首次
							if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
								//异常处理，当日历初始值或首次值为空 则 计划值为空
								if(StringUtils.isNotBlank(eOMonitorIemSet.getScz())){
//									Integer scz = null;
//									try{
//										scz = Integer.parseInt(monitoringPlan.getScz());
//										if(StringUtils.isNotBlank(strCalInit)){
//											try {
//												monitoringPlan.setJhz(DateUtil.getOffsetDate(strCalInit, scz, this.getOffsetUnit(monitoringPlan.getDwScz())));
//											} catch (ParseException e) {}
//										}
//									}catch(NumberFormatException e){
//										//当日历首次值无法转换为整型时，表示 EO为单次或分段
//										monitoringPlan.setJhz(eOMonitorIemSet.getScz());
//									}
//									
									//日历计划值就是日历
									monitoringPlan.setJhz(monitoringPlan.getScz());
									if(StringUtils.isNotBlank(monitoringPlan.getScz())){
										monitoringPlan.setScz("0");
										monitoringPlan.setDwScz(MonitorProjectUnitEnum.DAY.getCode());
									}else{
										monitoringPlan.setScz(null);
										monitoringPlan.setDwScz(null);
									}
								}
							}else{//非日历
								monitoringPlan.setJhz(eOMonitorIemSet.getScz());
							}
							monitorPlanList.add(monitoringPlan);
						}
					}
				}
			}else{//之前有监控数据
				if(lastMO.getJssj() == null){//计算时间为空，直接修改该数据
					lastMO.setWhbmid(user.getBmdm());
					lastMO.setWhrid(user.getId());
					lastMO.setLyid(monitoringObject.getLyid());
					lastMO.setLybh(monitoringObject.getLybh());
					lastMO.setWxfaid(monitoringObject.getWxfaid());
					lastMO.setWxfabh(monitoringObject.getWxfabh());
					lastMO.setWz(monitoringObject.getWz());
					lastMO.setJsgs(monitoringObject.getJsgs());
					lastMO.setHdwz(monitoringObject.getHdwz());
					uMOList.add(lastMO);
					List<MonitoringPlan> notExeMOPlanList = lastMO.getMonitoringPlanList();//原监控数据计划数据
					List<MonitoringLast> notExeMOLastList = lastMO.getMonitoringLastList();//原监控数据上次执行数据
					if(MaintenanceTypeEnum.PROJECT.getId() == monitoringObject.getLylx()){//处理维修方案
						//生成计划数据
						projectMonitorList = mpMonitorItemMap.get(monitoringObject.getLyid());//维修项目监控数据
						for (ProjectMonitor projectMonitor : projectMonitorList) {
							monitoringPlan = new MonitoringPlan(projectMonitor);
							monitoringPlan.setId(UUID.randomUUID().toString());
							monitoringPlan.setJksjid(lastMO.getId());
							monitoringPlan.setWhdwid(user.getBmdm());
							monitoringPlan.setWhrid(user.getId());
							monitoringPlan.setWz(monitoringObject.getWz());
							
							//判断原监控计划是否有该监控项，有该监控项使用该监控项的T0值进行计算
							for (MonitoringPlan notExeMOPlan : notExeMOPlanList) {
								if(notExeMOPlan.getJklbh().equals(monitoringPlan.getJklbh())){
									monitoringPlan.setJhqsz(notExeMOPlan.getJhqsz());
									break;
								}
							}
							
							//计算间隔 = 首次or周期
							String interval = null;//间隔
							int intervalUnit = 0;//间隔单位

							//T0无值，判断是否有上次执行数据，有上次执行数据以周期计算，否则以首次计算
							if((notExeMOLastList != null && notExeMOLastList.size() > 0) || StringUtils.isNotBlank(lastMO.getfJksjid())){
								//以周期进行计算
								interval = monitoringPlan.getZqz();
								intervalUnit = monitoringPlan.getDwZqz();
							}else{
								//以首次进行计算
								interval = monitoringPlan.getScz();
								intervalUnit = monitoringPlan.getDwScz();
							}
							
							//间隔不为空时计算计划值，间隔为空则计划值为空
							if(StringUtils.isNotBlank(interval)){
								if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
									//日历：为 T0为空时 使用初始+间隔，否则使用T0+间隔
									String t0 = monitoringPlan.getJhqsz();
									if(StringUtils.isBlank(t0)){
										//t0为空 后面以初始值进行计算
										t0 = strCalInit;
									}
									//异常处理，当日历初始值或首次值为空 则 计划值为空
									if(StringUtils.isNotBlank(t0)){
										try {
											monitoringPlan.setJhz(DateUtil.getOffsetDate(t0, Integer.parseInt(interval), this.getOffsetUnit(intervalUnit)));
										} catch (ParseException e) {}
									}
								}else{
									//非日历 T0+间隔
									monitoringPlan.setJhz(String.valueOf(Integer.parseInt(interval) + Integer.parseInt(StringUtils.isNotBlank(monitoringPlan.getJhqsz())?monitoringPlan.getJhqsz():"0")));
								}
							}else{
								monitoringPlan.setJhqsz(null);
							}
							monitorPlanList.add(monitoringPlan);
						}
					}else{//处理EO
						for (MonitoringPlan tempMonitoringPlan : notExeMOPlanList) {
							monitorPlanList.add(tempMonitoringPlan);
						}
					}
				}else{//计算时间不为空，生成新的数据，使用前后版本与之关联。
						
					List<MonitoringPlan> tempPlanList = new ArrayList<MonitoringPlan>();
					if(MaintenanceTypeEnum.PROJECT.getId() == monitoringObject.getLylx()){//处理维修方案
						projectMonitorList = mpMonitorItemMap.get(monitoringObject.getLyid());//维修项目监控数据
						for (ProjectMonitor projectMonitor : projectMonitorList) {
							monitoringPlan = new MonitoringPlan(projectMonitor);
							monitoringPlan.setId(UUID.randomUUID().toString());
							monitoringPlan.setWhdwid(user.getBmdm());
							monitoringPlan.setWhrid(user.getId());
							monitoringPlan.setJksjid(monitoringObject.getId());
							monitoringPlan.setWz(monitoringObject.getWz());
							monitoringPlan.setZt(WhetherEnum.YES.getId());
							tempPlanList.add(monitoringPlan);
						}
					}else{
						eOMonitorIemSetList = eOMonitorIemSetMap.get(monitoringObject.getLyid() +"_"+ monitoringObject.getEoxc());
						if(eOMonitorIemSetList != null){
							for (EOMonitorIemSet eOMonitorIemSet : eOMonitorIemSetList) {
								monitoringPlan = new MonitoringPlan(eOMonitorIemSet);
								monitoringPlan.setId(UUID.randomUUID().toString());
								monitoringPlan.setWhdwid(user.getBmdm());
								monitoringPlan.setWhrid(user.getId());
								monitoringPlan.setJksjid(monitoringObject.getId());
								monitoringPlan.setWz(monitoringObject.getWz());
								monitoringPlan.setZt(WhetherEnum.YES.getId());
								tempPlanList.add(monitoringPlan);
							}
						}
					}
					
					//当监控项目均没有周期值（不算没有监控项目的） 时不开启下一次监控
					if(!this.validatePlanHasZqz(tempPlanList)){
						break;
					}
					//获取计划值 计算公式，此方法返回null表示 不需要开启下一次监控
					Integer jsgs = this.getJsgs(monitoringObject.getLyid(), monitoringObject.getLylx());
					if(jsgs == null){
						break;
					}
					
					lastMO.setWhbmid(user.getBmdm());
					lastMO.setWhrid(user.getId());
					lastMO.setbJksjid(monitoringObject.getId());
					this.monitoringObjectMapper.updateByPrimaryKeySelective(lastMO);
					
					monitoringObject.setfJksjid(lastMO.getId());
					cMOList.add(monitoringObject);
					
					//更新监控数据
					List<MonitoringPlan> oldPlanList = lastMO.getMonitoringPlanList();
					MonitoringLast monitoringLast = null;
					List<MonitoringLast> tempLastList = new ArrayList<MonitoringLast>();
					for (MonitoringPlan tempMonitoringPlan : oldPlanList) {
						if(StringUtils.isNotBlank(tempMonitoringPlan.getJhz())){
							monitoringLast = new MonitoringLast();
							monitoringLast.setId(tempMonitoringPlan.getId());
							monitoringLast.setJksjid(monitoringObject.getId());
							monitoringLast.setWhdwid(user.getBmdm());
							monitoringLast.setWhrid(user.getId());
							monitoringLast.setZt(WhetherEnum.YES.getId());
							monitoringLast.setJklbh(tempMonitoringPlan.getJklbh());
							monitoringLast.setJkflbh(tempMonitoringPlan.getJkflbh());
							monitoringLast.setScjhz(tempMonitoringPlan.getJhz());
							monitoringLast.setScsjz(this.getSjz(tempMonitoringPlan.getJklbh(), tempMonitoringPlan.getCsz(), tempMonitoringPlan.getSjz()));
							tempLastList.add(monitoringLast);
						}
					}
					monitorLastList.addAll(tempLastList);
					for (MonitoringPlan tempPlan : tempPlanList) {
						if(StringUtils.isNotBlank(tempPlan.getZqz())){
							String jhqsz = "";
							
							for (MonitoringLast tempLast : tempLastList) {
								if(tempPlan.getJklbh().equals(tempLast.getJklbh())){
									if(jsgs == ComputationalFormulaEnum.FORMULA_1.getId()){
										//计划与实际取小
										if(MonitorProjectEnum.isCalendar(tempPlan.getJklbh())){
											if(tempLast.getScjhz() == null || tempLast.getScsjz() == null){
												jhqsz = tempLast.getScjhz()==null?tempLast.getScsjz():tempLast.getScjhz();
											}else{
												try {
													jhqsz = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, tempLast.getScsjz()).getTime()<DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, tempLast.getScjhz()).getTime()?tempLast.getScsjz():tempLast.getScjhz();
												} catch (ParseException e) {
												}
											}
										}else{
											Integer sj = formatStrToInt(tempLast.getScsjz());
											Integer jh = formatStrToInt(tempLast.getScjhz());
											jhqsz = formatIntToStr(sj.compareTo(jh)<0?sj:jh);
										}
									}else if(jsgs == ComputationalFormulaEnum.FORMULA_2.getId()){
										jhqsz = tempLast.getScsjz();
									}else{
										//计划
										jhqsz = tempLast.getScjhz();
									}
								}
							}
							if(MonitorProjectEnum.isCalendar(tempPlan.getJklbh())){
								if(StringUtils.isBlank(jhqsz)){
									jhqsz = DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, lastMO.getJssj());
								}
								try {
									tempPlan.setJhz(DateUtil.getOffsetDate(jhqsz, this.formatStrToInt(tempPlan.getZqz()), this.getOffsetUnit(tempPlan.getDwZqz())));
								} catch (NumberFormatException e) {
									tempPlan.setJhz(jhqsz);
								} catch (ParseException e) {
									tempPlan.setJhz(jhqsz);
								}
							}else{
								tempPlan.setJhz(formatIntToStr(this.formatStrToInt(jhqsz) + this.formatStrToInt(tempPlan.getZqz())));
							}
							tempPlan.setJhqsz(jhqsz);
						}else{
							tempPlan.setJhqsz(null);
							tempPlan.setJhz(null);
						}
					}
					monitorPlanList.addAll(tempPlanList);
				}
			}
		}
		
		//删除待修改监控数据的计划数据
		this.removeMonitorPlanByMainIdBatch(uMOList);
		//批量修改监控数据（处理待修改监控数据）
		this.updateMonitorObjectBatch(uMOList);
		//批量新增监控数据（处理待新增监控数据）
		this.saveMonitorObjectBatch(cMOList);
		//批量新增计划数据（处理待新增计划数据）
		this.saveMonitorPlanBatch(monitorPlanList);
		this.saveMonitorLastBatch(monitorLastList);
		//根据待修改、待新增监控数据 同步到 当前监控数据中
		uMOList.addAll(cMOList);
		this.saveCurrentMonitor4BatchSync(uMOList);
	}
	
	
	
	
	/**
	 * @Description 生成对部件的初始值及累计值，写入到临时表-需要在装机清单编辑区同步到生效区前完成
	 * @CreateTime 2017年10月30日 下午4:11:58
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param pcbh 批次编号
	 * @return
	 */
	public void addTemp4Component(String dprtcode, String fjzch, String pcbh){
		this.monitorDataMapper.insertTemp4Component(dprtcode, fjzch, pcbh);
	}
	
	/**
	 * @Description 生成对飞机的发动机的初始值及累计值，写入到临时表-需要在装机清单编辑区同步到生效区前完成
	 * @CreateTime 2017年10月30日 下午4:11:58
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param pcbh 批次编号
	 * @return
	 */
	public void addTemp4PlaneEngine(String dprtcode, String fjzch, String pcbh){
		this.monitorDataMapper.insertTemp4PlaneEngine(dprtcode, fjzch, pcbh);
	}
	
	/**
	 * @Description 生成对飞机的APU的初始值及累计值，写入到临时表-需要在装机清单编辑区同步到生效区前完成
	 * @CreateTime 2017年10月30日 下午4:11:58
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param pcbh 批次编号
	 * @return
	 */
	public void addTemp4PlaneAPU(String dprtcode, String fjzch, String pcbh){
		this.monitorDataMapper.insertTemp4PlaneAPU(dprtcode, fjzch, pcbh);
	}
	
	/**
	 * @Description 从临时表更新已执行任务实际值
	 * @CreateTime 2017年10月30日 下午5:26:32
	 * @CreateBy 徐勇
	 * @param pcbh 批次编号
	 */
	public void updateFromTemp(String pcbh){
		this.monitorDataMapper.update904FromTemp(pcbh);
		this.monitorDataMapper.update903FromTemp(pcbh);
		
		//更新当前监控数据的计划起算点和计划值，通过该方式更新均不是第一次执行,计划值以周期进行计算
		this.monitorDataMapper.update904JHFromTemp(pcbh);
		//清除临时表数据
		this.monitorDataMapper.deleteTempByPcbh(pcbh);
	}
	
	/**
	 * @Description 更新飞机已经执行任务的实际值
	 * @CreateTime 2017年11月7日 下午2:43:45
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param calInit 日历初始值
	 * @param fhInit 飞行小时初始值
	 * @param fcInit 飞行循环初始值
	 */
	public void updateTaskInit4Plane(String dprtcode, String fjzch, String calInit, String fhInit, String fcInit){
		//修改b_s2_904 监控数据-(计划)执行数据存在飞行时间监控项目的初始化值
		this.monitorDataMapper.update904Init4Plane(dprtcode, fjzch, MonitorProjectEnum.FH.getCode(), fhInit);
		//修改b_s2_904 监控数据-(计划)执行数据存在飞行循环监控项目的初始化值
		this.monitorDataMapper.update904Init4Plane(dprtcode, fjzch, MonitorProjectEnum.FC.getCode(), fcInit);
		//修改b_s2_903 监控数据-上次执行数据的上次实际值-飞行时间
		this.monitorDataMapper.update903sjz4Plane(dprtcode, fjzch, MonitorProjectEnum.FH.getCode());
		//修改b_s2_903 监控数据-上次执行数据的上次实际值-飞机循环
		this.monitorDataMapper.update903sjz4Plane(dprtcode, fjzch, MonitorProjectEnum.FC.getCode());
		//更新b_s2_904 监控数据-(计划)执行数据 日历日计划值修改
		List<MonitoringPlan> monitoringPlanList = this.monitoringPlanMapper.selectCalPlanListByPlane(dprtcode, fjzch);
		List<MonitoringPlan> toUpdateList = new ArrayList<MonitoringPlan>(monitoringPlanList.size());
		for (MonitoringPlan monitoringPlan : monitoringPlanList) {
				
			Integer scz = null;
			try{
				scz = Integer.parseInt(monitoringPlan.getScz());
				try {
					monitoringPlan.setJhz(DateUtil.getOffsetDate(calInit, scz, this.getOffsetUnit(monitoringPlan.getDwScz())));
					toUpdateList.add(monitoringPlan);
				} catch (ParseException e) {}
			}catch(NumberFormatException e){
				//当日历首次值无法转换为整型时，表示 EO为单次或分段的日期型格式
			}	
		}
		this.saveMonitorPlanJhzBatch(toUpdateList);
	}
	
	/**
	 * @Description 批量更新监控数据-计划执行数据计划值
	 * @CreateTime 2017年10月10日 上午10:22:45
	 * @CreateBy 徐勇
	 * @param list 监控-计划执行数据
	 */
	private void saveMonitorPlanJhzBatch(List<MonitoringPlan> list){
		for (int i = 0; i <= list.size()/SysContext.BATCH_PROCESS_SIZE; i++) {
			if(i*SysContext.BATCH_PROCESS_SIZE < list.size()){
				this.monitoringPlanMapper.updateJhz4Batch(list.subList(i*SysContext.BATCH_PROCESS_SIZE, 
						((i+1) * SysContext.BATCH_PROCESS_SIZE > list.size() ? list.size():(i+1) * SysContext.BATCH_PROCESS_SIZE)));
			}
		}
	}
	
	/**
	 * @Description 飞行记录本（提交/修订/作废）更新已执行任务实际值
	 * @CreateTime 2017年11月14日 下午5:09:58
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param minKcsj 最小开车时间
	 */
	public void updateTask4FLB(String dprtcode, String fjzch, Date minKcsj){
		String pcbh = UUID.randomUUID().toString();
		//生成对飞机的FH/FC累计值写入临时表
		this.monitorDataMapper.insertTemp4FJByFLB(dprtcode, fjzch, pcbh, minKcsj);
		//生成对部件的累计值写入临时表
		this.monitorDataMapper.insertTemp4ComponentByFLB(dprtcode, fjzch, pcbh, minKcsj);
		//生成对飞机发动机的累计值写入临时表
		this.monitorDataMapper.insertTemp4PlaneEngineByFLB(dprtcode, fjzch, pcbh, minKcsj);
		//生成对APU的累计值写入临时表
		this.monitorDataMapper.insertTemp4PlaneAPUByFLB(dprtcode, fjzch, pcbh, minKcsj);
		//按临时表数据更新904累计值
		this.monitorDataMapper.update904SJZFromTemp(pcbh);
		//按临时表数据更新903上次实际值
		this.monitorDataMapper.update903FromTempAnd904(pcbh);
		//更新当前监控数据的计划起算点和计划值，通过该方式更新均不是第一次执行,计划值以周期进行计算
		this.monitorDataMapper.update904JHFromTemp(pcbh);
		//按批次号清除临时表数据
		this.monitorDataMapper.deleteTempByPcbh(pcbh);
	}
	
	/**
	 * @Description 工单完成，处理监控数据
	 * @CreateTime 2017年11月21日 上午9:27:41
	 * @CreateBy 徐勇
	 * @param workOrderId 工单ID
	 */
	public void doWorkOrderFinish(String workOrderId){
		
		//查询工单对应的监控数据
		MonitoringObject monitoringObject = this.monitoringObjectMapper.selectByWorkOrder(workOrderId);
		this.doMOFinish(monitoringObject, true);
	}
	
	/**
	 * @Description 
	 * @CreateTime 2017年11月21日 上午10:51:55
	 * @CreateBy 徐勇
	 * @param monitoringObject
	 * @flag 是否处理取代关系
	 */
	private void doMOFinish(MonitoringObject monitoringObject, boolean flag){
		//工单没有对应监控数据，不需要处理监控逻辑
		if(monitoringObject == null){
			return;
		}
		
		Date jssj = monitoringObject.getJssj();
		String byxjksjid = monitoringObject.getByxjksjid();
		if(flag){
			monitoringObject.setByxjksjid(null);
		}
		//当被影响监控数据有值，表示该数据无需进行完成动作
		if(!flag || StringUtils.isBlank(byxjksjid)){
			//删除当前监控数据
			int iCount = this.monitoringCurrentMapper.deleteByPrimaryKey(monitoringObject.getId());
			//当执行删除当前监控数据时操作结果数为0表示之前不存在当前监控数据，不需要开启新的监控，只需要处理原监控数据
			if(iCount == 0){
				//处理
				this.doMOFinish(monitoringObject, null);
				return;
			}
			this.doMOFinish(monitoringObject, UUID.randomUUID().toString());
		}
		//处理取代
		if(flag){
			monitoringObject.setJssj(jssj);
			this.doReplace(monitoringObject);
		}
	}
	
	/**
	 * @Description 监控数据关闭
	 * @CreateTime 2017年11月21日 上午10:04:59
	 * @CreateBy 徐勇
	 * @param monitoringObject 监控数据
	 * @param nextId 下一次监控项ID
	 * 	 * @flag 是否处理取代关系

	 */
	private void doMOFinish(MonitoringObject monitoringObject, String nextId){
		Integer jsgs = null;//计划值 计算公式
		if(StringUtils.isNotBlank(nextId)){
			//验证装机清单是否有效且为装上状态，不通过则不用开启下一次监控
			if(StringUtils.isNotBlank(monitoringObject.getZjqdid())){
				nextId = validateInstallStatus(monitoringObject.getZjqdid())?nextId:null;
			}
			//当监控项目均没有周期值（不算没有监控项目的） 时不开启下一次监控
			if(StringUtils.isNotBlank(nextId) && !this.validatePlanHasZqz(monitoringObject.getMonitoringPlanList())){
				nextId = null;
			}
			if(StringUtils.isNotBlank(nextId)){
				//获取计划值 计算公式，此方法返回null表示 不需要开启下一次监控
				jsgs = this.getJsgs(monitoringObject.getLyid(), monitoringObject.getLylx());
				if(jsgs == null){
					nextId = null;
				}
			}
		}
		
		//更新监控数据
		monitoringObject.setbJksjid(nextId);
		this.monitoringObjectMapper.updateByPrimaryKeySelective(monitoringObject);
		
		//更新监控数据-计划执行
		update904Sjz(monitoringObject);
		
		//开启下一次监控
		if(StringUtils.isNotBlank(nextId)){
			this.doStartNextMonitor(monitoringObject, nextId, jsgs);
		}
	}
	
	private void update904Sjz(MonitoringObject monitoringObject){
		List<MonitoringPlan> monitoringPlanList = monitoringObject.getMonitoringPlanList();
		
		if(monitoringPlanList != null && monitoringPlanList.size() > 0){
			//获取初始值和实际值
			if(StringUtils.isNotBlank(monitoringObject.getZjqdid())){
				//根据装机清单获取实际值
				ComponentUse componentUse = this.componentUseMapper.selectByZjqdid(monitoringObject.getZjqdid(), monitoringObject.getJssj());
				List<InstallationListEffective2Init> initList = this.installationListEffective2InitMapper.selectByMainid(monitoringObject.getZjqdid());
				for (MonitoringPlan monitoringPlan : monitoringPlanList) {
					if(StringUtils.isNotBlank(monitoringPlan.getJhz())){
						if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
							//日历实际值为计算时间
							monitoringPlan.setSjz(DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, monitoringObject.getJssj()));
						}else{
							if(componentUse != null){
								monitoringPlan.setSjz(formatIntToStr(componentUse.getByJklbh(monitoringPlan.getJklbh(), componentUse.getWz())));
							}
							if(StringUtils.isBlank(monitoringPlan.getSjz())){
								monitoringPlan.setSjz("0");
							}
						}
						for (InstallationListEffective2Init installationListEffective2Init : initList) {
							if(installationListEffective2Init.getJklbh().equals(monitoringPlan.getJklbh())){
								monitoringPlan.setCsz(installationListEffective2Init.getZssyy());
								break;
							}
						}
						if(!MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh()) && StringUtils.isBlank(monitoringPlan.getCsz())){
							monitoringPlan.setCsz("0");
						}
					}
				}
			}else{
				//获取飞机和关键部件的时间/循环实际值
				List<ComponentUse> componentUseList = this.componentUseMapper.selectByFjzch(monitoringObject.getDprtcode(), monitoringObject.getFjzch(), monitoringObject.getJssj());
				//查询飞机初始值
				List<PlanInit> planeInitList = this.planInitMapper.selectBodyInit(monitoringObject.getFjzch(), monitoringObject.getDprtcode());
				//查询部件初始值
				List<InstallationListEffective2Init> initList = null;
				if(monitoringObject.getWz() != null){
					initList = this.installationListEffective2InitMapper.selectTopByWzAndSj(monitoringObject.getDprtcode(), monitoringObject.getFjzch(), monitoringObject.getWz(), monitoringObject.getJssj());
				}
				
				List<InstallationListEffective2Init> apuInitList = null;
				for (MonitoringPlan monitoringPlan : monitoringPlanList) {
					if(MonitorProjectEnum.isApu(monitoringPlan.getJklbh()) && StringUtils.isNotBlank(monitoringPlan.getJhz())){
						apuInitList = this.installationListEffective2InitMapper.selectTopByWzAndSj(monitoringObject.getDprtcode(), monitoringObject.getFjzch(), InstalledPositionEnum.APU.getId(), monitoringObject.getJssj());
						break;
					}
				}
				
				for (MonitoringPlan monitoringPlan : monitoringPlanList) {
					if(StringUtils.isNotBlank(monitoringPlan.getJhz())){
						if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
							//日历实际值为计算时间
							monitoringPlan.setSjz(DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, monitoringObject.getJssj()));
							for (PlanInit planInit : planeInitList) {
								if(monitoringPlan.getJklbh().equals(planInit.getJklbh())){
									monitoringPlan.setCsz(planInit.getCsz());
									break;
								}
							}
						}else{
							if(MonitorProjectEnum.isEngine(monitoringPlan.getJklbh())){
								if(monitoringObject.getWz() != null){
									if(MonitorProjectEnum.isEngine(monitoringPlan.getJklbh())){
										for (ComponentUse componentUse : componentUseList) {
											if(componentUse.getWz() == monitoringObject.getWz()){
												if(MonitorProjectEnum.isTime(monitoringPlan.getJklbh())){
													monitoringPlan.setSjz(formatIntToStr(componentUse.getSj()));
												}else{
													monitoringPlan.setSjz(formatIntToStr(componentUse.getXh()));
												}
												break;
											}
										}
										for (InstallationListEffective2Init installationListEffective2Init : initList) {
											if(monitoringPlan.getJklbh().equals(installationListEffective2Init.getJklbh())){
												monitoringPlan.setCsz(installationListEffective2Init.getZssyy());
												break;
											}
										}
									}
								}
							}else if(MonitorProjectEnum.isApu(monitoringPlan.getJklbh())){
									for (ComponentUse componentUse : componentUseList) {
										if(componentUse.getWz() == InstalledPositionEnum.APU.getId()){
											if(MonitorProjectEnum.isTime(monitoringPlan.getJklbh())){
												monitoringPlan.setSjz(formatIntToStr(componentUse.getSj()));
											}else{
												monitoringPlan.setSjz(formatIntToStr(componentUse.getXh()));
											}
											break;
										}
									}
									for (InstallationListEffective2Init installationListEffective2Init : apuInitList) {
										if(monitoringPlan.getJklbh().equals(installationListEffective2Init.getJklbh())){
											monitoringPlan.setCsz(installationListEffective2Init.getZssyy());
											break;
										}
									}
							}else{
								for (ComponentUse componentUse : componentUseList) {
									if(componentUse.getWz() == InstalledPositionEnum.BODY.getId()){
										if(MonitorProjectEnum.isTime(monitoringPlan.getJklbh())){
											monitoringPlan.setSjz(formatIntToStr(componentUse.getSj()));
										}else{
											monitoringPlan.setSjz(formatIntToStr(componentUse.getXh()));
										}
										break;
									}
								}
								for (PlanInit planInit : planeInitList) {
									if(monitoringPlan.getJklbh().equals(planInit.getJklbh())){
										monitoringPlan.setCsz(planInit.getCsz());
										break;
									}
								}
							}
						}
						
						if(StringUtils.isBlank(monitoringPlan.getCsz())){
							monitoringPlan.setCsz("0");
						}
						if(StringUtils.isBlank(monitoringPlan.getSjz())){
							monitoringPlan.setSjz("0");
						}
					}
				}
			}
			//更新 计划-执行数据
			this.monitoringPlanMapper.updateSjz4Batch(monitoringPlanList);
		}
	}
	
	/**
	 * @Description 验证所有监控项目中是否含有周期不为空的数据，没有监控项直接返回true
	 * @CreateTime 2017年11月24日 下午1:55:43
	 * @CreateBy 徐勇
	 * @param list
	 * @return
	 */
	private boolean validatePlanHasZqz(List<MonitoringPlan> list){
		if(list == null || list.size() == 0){
			return true;
		}
		for (MonitoringPlan monitoringPlan : list) {
			if(StringUtils.isNotBlank(monitoringPlan.getZqz())){
				return true;
			}
		}
		return false;
	}
	
	private Integer getJsgs(String lyid, Integer lylx){
		Integer jsgs = null;//计算公式
		//判断是否EO， EO非循环类型不需要开启下一次监控
		if(lylx == MaintenanceTypeEnum.EO.getId()){
			EngineeringOrder engineeringOrder = engineeringOrderMapper.selectByPrimaryKey(lyid);
			if(engineeringOrder.getZxfs().intValue() != ExecutionEnum.REPEAT.getId().intValue()){
				return null;
			}
			jsgs = engineeringOrder.getJsgs();
		}else if(lylx == MaintenanceTypeEnum.PROJECT.getId()){
			MaintenanceProject maintenanceProject = maintenanceProjectMapper.selectByPrimaryKey(lyid);
			jsgs = maintenanceProject.getJsgs();
		}else if(lylx == MaintenanceTypeEnum.PRODUCTION_ORDER.getId()){
			ProductionOrder productionOrder = this.productionOrderMapper.selectByPrimaryKey(lyid);
			jsgs = productionOrder.getJsgs();
		}
		if(jsgs == null){
			jsgs = ComputationalFormulaEnum.FORMULA_1.getId();
		}
		return jsgs;
	}
	
	/**
	 * @Description 验证生效区装机清单是否装上且有效状态
	 * @CreateTime 2017年11月23日 下午4:09:27
	 * @CreateBy 徐勇
	 * @param zjqdid 装机清单ID
	 * @return
	 */
	private boolean validateInstallStatus(String zjqdid){
		InstallationListEffective installationListEffective = this.installationListEffectiveMapper.selectByPrimaryKey(zjqdid);
		if(installationListEffective.getYxbs()==WhetherEnum.YES.getId() && installationListEffective.getZjzt() == InstalledStatusEnum.INSTALLED.getId()){
			return true;
		}
		return false;
	}
	
	/**
	 * @Description 开启下一次监控
	 * @CreateTime 2017年11月21日 上午9:30:35
	 * @CreateBy 徐勇
	 * @param monitoringObject 原监控数据
	 * @param newId 新监控数据ID
	 * @param jsgs 计算公式
	 */
	private void doStartNextMonitor(MonitoringObject monitoringObject, String newId, Integer jsgs){
		
		Date jssj = monitoringObject.getJssj();//计算时间
		
		String fid = monitoringObject.getId();//前监控数据ID
		monitoringObject.setId(newId);
		monitoringObject.setfJksjid(fid);
		monitoringObject.setbJksjid(null);
		monitoringObject.setJssj(null);
		monitoringObject.setByxjksjid(null);
		User user = ThreadVarUtil.getUser();
		monitoringObject.setWhbmid(user.getBmdm());
		monitoringObject.setWhrid(user.getId());
		this.monitoringObjectMapper.insert(monitoringObject);
		
		//新增当前监控数据
		List<MonitoringObject> list = new ArrayList<MonitoringObject>(1);
		list.add(monitoringObject);
		this.saveCurrentMonitor4BatchSync(list);
		
		//处理上次执行数据 和 计划执行数据
		List<MonitoringPlan> monitoringPlanList = monitoringObject.getMonitoringPlanList();
		List<MonitoringLast> monitoringLastList = new ArrayList<MonitoringLast>();
		List<MonitoringPlan> newMonitoringPlanList = new ArrayList<MonitoringPlan>();
		MonitoringLast monitoringLast = null;
		
		for (MonitoringPlan monitoringPlan : monitoringPlanList) {
			if(StringUtils.isNotBlank(monitoringPlan.getJhz())){
				monitoringLast = new MonitoringLast();
				monitoringLast.setId(monitoringPlan.getId());
				monitoringLast.setJksjid(newId);
				monitoringLast.setWhdwid(user.getBmdm());
				monitoringLast.setWhrid(user.getId());
				monitoringLast.setZt(WhetherEnum.YES.getId());
				monitoringLast.setJklbh(monitoringPlan.getJklbh());
				monitoringLast.setJkflbh(monitoringPlan.getJkflbh());
				monitoringLast.setScjhz(monitoringPlan.getJhz());
				monitoringLast.setScsjz(this.getSjz(monitoringPlan.getJklbh(), monitoringPlan.getCsz(), monitoringPlan.getSjz()));
				monitoringLastList.add(monitoringLast);
			}
			monitoringPlan.setId(UUID.randomUUID().toString());
			monitoringPlan.setJksjid(newId);
			monitoringPlan.setWhdwid(user.getBmdm());
			monitoringPlan.setWhrid(user.getId());
			monitoringPlan.setZt(WhetherEnum.YES.getId());
			if(StringUtils.isNotBlank(monitoringPlan.getZqz())){
				String jhqsz = "";
				if(jsgs == ComputationalFormulaEnum.FORMULA_1.getId()){
					//计划与实际取小
					if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
						if(monitoringPlan.getSjz() == null || monitoringPlan.getJhz() == null){
							jhqsz = monitoringPlan.getSjz()==null?monitoringPlan.getJhz():monitoringPlan.getSjz();
						}else{
							try {
								jhqsz = DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, monitoringPlan.getSjz()).getTime()<DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, monitoringPlan.getJhz()).getTime()?monitoringPlan.getSjz():monitoringPlan.getJhz();
							} catch (ParseException e) {
							}
						}
					}else{
						Integer sj = formatStrToInt(monitoringPlan.getSjz()) + formatStrToInt(monitoringPlan.getCsz());
						Integer jh = formatStrToInt(monitoringPlan.getJhz());
						jhqsz = formatIntToStr(sj.compareTo(jh)<0?sj:jh);
					}
				}else if(jsgs == ComputationalFormulaEnum.FORMULA_2.getId()){
					//实际
					if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
						jhqsz = monitoringPlan.getSjz();
					}else{
						jhqsz = formatIntToStr(formatStrToInt(monitoringPlan.getSjz()) + formatStrToInt(monitoringPlan.getCsz()));
					}
				}else{
					//计划
					jhqsz = monitoringPlan.getJhz();
				}
				if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
					if(StringUtils.isBlank(jhqsz)){
						jhqsz = DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, jssj);
					}
					try {
						monitoringPlan.setJhz(DateUtil.getOffsetDate(jhqsz, this.formatStrToInt(monitoringPlan.getZqz()), this.getOffsetUnit(monitoringPlan.getDwZqz())));
					} catch (NumberFormatException e) {
						monitoringPlan.setJhz(jhqsz);
					} catch (ParseException e) {
						monitoringPlan.setJhz(jhqsz);
					}
				}else{
					monitoringPlan.setJhz(formatIntToStr(this.formatStrToInt(jhqsz) + this.formatStrToInt(monitoringPlan.getZqz())));
				}
				monitoringPlan.setJhqsz(jhqsz);
			}else{
				monitoringPlan.setJhqsz(null);
				monitoringPlan.setJhz(null);
			}
			monitoringPlan.setCsz(null);
			monitoringPlan.setSjz(null);
			newMonitoringPlanList.add(monitoringPlan);
		}
		//新增903和904
		this.saveMonitorPlanBatch(newMonitoringPlanList);
		this.saveMonitorLastBatch(monitoringLastList);
	}
	
	/**
	 * @Description 处理取代关系
	 * @CreateTime 2017年11月21日 上午10:15:13
	 * @CreateBy 徐勇
	 * @param monitoringObject
	 */
	private void doReplace(MonitoringObject monitoringObject){
		//只有维修项目有取代关系，EO和生产指令都没有取代关系
		if(monitoringObject.getLylx() != MaintenanceTypeEnum.PROJECT.getId()){
			return;
		}
		//查询取代关系
		List<MonitoringObject> moList = this.monitoringObjectMapper.selectReplaceByLyid(monitoringObject.getLyid(), monitoringObject.getDprtcode(), monitoringObject.getFjzch());
		
		for (MonitoringObject mo : moList) {
			
			//当监控数据前监控ID有数据，表示执行过开启下一次监控，此时应使用前监控数据id为被影响监控数据id
			if(StringUtils.isNotBlank(monitoringObject.getfJksjid())){
				mo.setByxjksjid(monitoringObject.getfJksjid());
			}else{
				mo.setByxjksjid(monitoringObject.getId());
			}
			
			mo.setJssj(monitoringObject.getJssj());
			this.doMOFinish(mo, false);
		}
		
	}
	
	/**
	 * @Description 根据初始值+累计值计算实际值
	 * @CreateTime 2017年11月20日 下午5:19:43
	 * @CreateBy 徐勇
	 * @param jklbh 监控类编号
	 * @param csz 初始值
	 * @param ljz 累计值
	 * @return
	 */
	private String getSjz(String jklbh, String csz, String ljz){
		if(MonitorProjectEnum.isCalendar(jklbh)){
			return ljz;
		}else{
			return formatIntToStr(formatStrToInt(csz) + formatStrToInt(ljz));
		}
	}
	
	/**
	 * @Description 监控值 由 string 转 int， 为空则返回0
	 * @CreateTime 2017年11月20日 下午5:20:17
	 * @CreateBy 徐勇
	 * @param str
	 * @return
	 */
	private int formatStrToInt(String str){
		if(StringUtils.isNotBlank(str)){
			try {
				return Integer.parseInt(str);
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}
	
	/**
	 * @Description 监控值 由 int 转 String， 为空则返回0
	 * @CreateTime 2017年11月20日 下午5:20:17
	 * @CreateBy 徐勇
	 * @param i
	 * @return
	 */
	private String formatIntToStr(Integer i){
		if(i == null){
			i = 0;
		}
		return i.toString();
	}
	
	/**
	 * @Description 根据装机清单ID删除监控数据 (监控数据未执行且不存在非指定结束的工单)
	 * @CreateTime 2017年11月25日 上午9:45:19
	 * @CreateBy 徐勇
	 * @param zjqdid 装机清单id
	 */
	public void removeMonitoringObjectByZjqdid(String zjqdid){
		//删除监控数据上次执行数据、计划执行数据(监控数据未执行且不存在非指定结束的工单)
		this.monitoringLastMapper.deleteByZjqdid(zjqdid);
		this.monitoringPlanMapper.deleteByZjqdid(zjqdid);
		
		//删除监控数据 (监控数据未执行且不存在非指定结束的工单)
		this.monitoringObjectMapper.deleteByZjqdid(zjqdid);
	}
	
	/**
	 * @Description 计算时间修改 监控数据进行修订
	 * @CreateTime 2017年11月28日 下午3:27:40
	 * @CreateBy 徐勇
	 * @param jksjid 监控数据ID
	 * @param jssj 新的计算时间
	 */
	public void doMOModifyJssj(String jksjid, Date jssj){
		MonitoringObject mo = this.monitoringObjectMapper.selecyById(jksjid);
		this.doMOModifyJssj(mo, jssj, true);
	}
	
	/**
	 * @Description 处理监控数据计算时间发生变化的逻辑
	 * @CreateTime 2017年11月28日 下午3:27:05
	 * @CreateBy 徐勇
	 * @param mo 监控数据
	 * @param jssj 计算时间
	 * @param flag 是否处理取代关系
	 */
	private void doMOModifyJssj(MonitoringObject mo, Date jssj, boolean flag){
		mo.setJssj(jssj);
		this.monitoringObjectMapper.updateByPrimaryKeySelective(mo);
		//更新904
		this.update904Sjz(mo);
		//更新后版本数据的903
		if(StringUtils.isNotBlank(mo.getbJksjid())){
			MonitoringLast monitoringLast = null;
			List<MonitoringLast> monitoringLastList = new ArrayList<MonitoringLast>();
			for (MonitoringPlan monitoringPlan : mo.getMonitoringPlanList()) {
				if(StringUtils.isNotBlank(monitoringPlan.getJhz())){
					monitoringLast = new MonitoringLast();
					monitoringLast.setId(monitoringPlan.getId());
					monitoringLast.setScjhz(monitoringPlan.getJhz());
					monitoringLast.setScsjz(this.getSjz(monitoringPlan.getJklbh(), monitoringPlan.getCsz(), monitoringPlan.getSjz()));
					monitoringLastList.add(monitoringLast);
				}
			}
			if(monitoringLastList.size() > 0){
				this.monitoringLastMapper.update4Batch(monitoringLastList);
			}
		}
		if(flag){
			this.doReplaceMOModifyJssj(mo.getId(), jssj);
		}
	}
	
	/**
	 * @Description 处理取代监控数据在计算时间变化时操作
	 * @CreateTime 2017年11月28日 下午3:26:09
	 * @CreateBy 徐勇
	 * @param jksjid 取代其它监控数据的监控数据ID
	 * @param jssj 新的计算时间
	 */
	private void doReplaceMOModifyJssj(String jksjid, Date jssj){
		List<MonitoringObject> moList = this.monitoringObjectMapper.selecyByByxjksjid(jksjid);
		for (MonitoringObject monitoringObject : moList) {
			this.doMOModifyJssj(monitoringObject, jssj, false);
		}
	}
	
	/**
	 * @Description 根据装机清单id更新序列号
	 * @CreateTime 2017年12月6日 下午3:09:50
	 * @CreateBy 徐勇
	 * @param zjqdid 装机清单id
	 * @param xlh 部件序列号
	 */
	public void updateXlhByZjqdid(String zjqdid, String xlh){
		this.monitoringCurrentMapper.updateXlhByZjqdid(zjqdid, xlh);
		this.monitoringObjectMapper.updateXlhByZjqdid(zjqdid, xlh);
	}
	
}
