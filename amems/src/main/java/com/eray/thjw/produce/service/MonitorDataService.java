package com.eray.thjw.produce.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.MonitoringObject;
import com.eray.thjw.produce.po.MonitoringPlan;

/**
 * @Description 监控数据处理服务接口
 * @CreateTime 2017年10月9日 下午1:39:58
 * @CreateBy 徐勇
 */
public interface MonitorDataService {

	/**
	 * @Description 根据维修方案删除当前监控数据
	 * @CreateTime 2017年10月9日 下午1:47:29
	 * @CreateBy 徐勇
	 * @param wxfabh 维修方案编号
	 * @param dprtcode 组织机构
	 */
	public void removeByMaintenanceScheme(String wxfabh, String dprtcode);
	
	/**
	 * @Description 根据来源编号和来源类型删除当前监控数据
	 * @CreateTime 2018年5月2日 下午2:06:56
	 * @CreateBy 徐勇
	 * @param lybh 来源编号
	 * @param lylx 来源类型
	 * @param dprtcode 组织机构
	 */
	public void removeByLybhAndLx(String lybh, Integer lylx, String dprtcode);
	
	/**
	 * @Description 根据飞机注册号删除当前监控数据
	 * @CreateTime 2017年10月9日 下午1:47:29
	 * @CreateBy 徐勇
	 * @param fjzch 飞机注册号
	 * @param dprtcode 组织机构
	 */
	public void removeByFjzch(String fjzch, String dprtcode);
	
	/**
	 * @Description 根据装机清单id删除当前监控数据
	 * @CreateTime 2017年10月19日 下午3:27:43
	 * @CreateBy 徐勇
	 * @param zjqdid 装机清单ID
	 */
	public void removeByZjqdid(String zjqdid);
	
	/**
	 * @Description 查询时控时寿项目需要监控的数据 
	 * @CreateTime 2017年10月9日 下午3:18:12
	 * @CreateBy 徐勇
	 * @param wxfaid 维修方案ID
	 * @return
	 */
	public List<MonitoringObject> queryControlMPNeedMonitorDataList(String wxfaid);
	
	/**
	 * @Description 查询维修项目/定检包需要监控的数据
	 * @CreateTime 2017年10月9日 下午3:18:48
	 * @CreateBy 徐勇
	 * @param wxfaid 维修方案ID
	 * @return
	 */
	public List<MonitoringObject> queryCommonMPNeedMonitorDataList(String wxfaid);
	
	/**
	 * @Description 查询生产指令需要生成的监控数据
	 * @CreateTime 2018年5月2日 下午2:23:27
	 * @CreateBy 徐勇
	 * @param poId 生产指令id
	 * @return
	 */
	public List<MonitoringObject> queryPONeedMonitorDataList(String poId);
	
	/**
     * @Description 根据维修方案ID查询未执行的飞机监控数据（带计划、上次执行）
     * @CreateTime 2017年10月9日 下午8:02:58
     * @CreateBy 徐勇
     * @param wxfaid 维修方案ID
     * @return
     */
    public List<MonitoringObject> queryNotExeMonitorDataListByWxfaid(String wxfaid);
    
    /**
     * @Description 根据维修方案ID查询未执行的飞机监控数据（带计划、上次执行）
     * @CreateTime 2017年10月9日 下午8:02:58
     * @CreateBy 徐勇
     * @param wxfaid 维修方案ID
     * @return
     */
    public Map<String, MonitoringObject> queryNotExeMonitorDataMapByWxfaid(String wxfaid);
    
    /**
     * @Description 根据维修方案ID查询未执行的飞机监控数据（带计划、上次执行）
     * @CreateTime 2017年10月9日 下午8:02:58
     * @CreateBy 徐勇
     * @param wxfaid 维修方案ID
     * @return
     */
    public Map<String, MonitoringObject> queryNotExeMonitorDataMapByLyid(String lyid);
    
    /**
     * @Description 获取监控数据唯一标识
     * @CreateTime 2017年10月9日 下午8:36:39
     * @CreateBy 徐勇
     * @param monitoringObject 监控数据对象
     * @return 来源编号_飞机注册号_装机清单ID_部件号_序列号_位置
     */
	public String getMonitorObjectKey(MonitoringObject monitoringObject);
	
	/**
	 * @Description 根据监控数据唯一标识获取监控对象
	 * @CreateTime 2017年10月10日 下午8:30:56
	 * @CreateBy 徐勇
	 * @param map 监控数据清单
	 * @param monitoringObject 需要匹配的监控数据
	 * @param fullMatch 是否全匹配（是否带位置匹配）
	 */
	public MonitoringObject getMatchedMonitorObject(Map<String, MonitoringObject> map, MonitoringObject monitoringObject, boolean fullMatch);
	
	/**
	 * @Description 根据监控数据批量删除计划数据
	 * @CreateTime 2017年10月9日 下午10:45:07
	 * @CreateBy 徐勇
	 * @param list 监控数据
	 */
	public void removeMonitorPlanByMainIdBatch(List<MonitoringObject> list);
	
	/**
	 * @Description 批量更新监控数据
	 * @CreateTime 2017年10月10日 上午9:40:23
	 * @CreateBy 徐勇
	 * @param list 监控数据
	 */
	public void updateMonitorObjectBatch(List<MonitoringObject> list);
	
	/**
	 * @Description 批量新增监控数据
	 * @CreateTime 2017年10月10日 上午10:13:41
	 * @CreateBy 徐勇
	 * @param list 监控数据
	 */
	public void saveMonitorObjectBatch(List<MonitoringObject> list);
	
	/**
	 * @Description 批量新增监控数据-计划执行数据
	 * @CreateTime 2017年10月10日 上午10:22:45
	 * @CreateBy 徐勇
	 * @param list 监控-计划执行数据
	 */
	public void saveMonitorPlanBatch(List<MonitoringPlan> list);
	
	/**
	 * @Description 批量同步监控数据到当前监控数据中去
	 * @CreateTime 2017年10月10日 上午10:22:45
	 * @CreateBy 徐勇
	 * @param list 监控数据
	 */
	public void saveCurrentMonitor4BatchSync(List<MonitoringObject> list);
	
	/**
	 * @Description 按机型查询飞机和飞机序列号件的初始化日期
	 * @CreateTime 2017年10月10日 下午4:32:24
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjjx 飞机机型
	 * @return 飞机注册号|装机清单ID = 日历初始值 
	 */
	public Map<String, String> queryAllCalInitByFjjx(String dprtcode, String fjjx);
	
	/**
	 * @Description 根据EOID查询需要进行监控的对飞机监控数据
	 * @CreateTime 2017年10月12日 上午10:44:30
	 * @CreateBy 徐勇
	 * @param id EO ID
	 * @return
	 */
	List<MonitoringObject> queryEOPlaneNeedMonitorDataList(String id);
	
	/**
	 * @Description 根据EO查询需要进行监控的对部件监控数据 
	 * @CreateTime 2017年10月12日 上午10:47:26
	 * @CreateBy 徐勇
	 * @param id EO ID
	 * @param dprtcode EO所属组织机构
	 * @param fjjx EO的飞机机型
	 * @return
	 */
	List<MonitoringObject> queryEOPartNeedMonitorDataList(String id, String dprtcode, String fjjx);
	
	/**
	 * @Description 将监控项单位转换为日历单位
	 * @CreateTime 2017年10月10日 下午8:21:52
	 * @CreateBy 徐勇
	 * @param unit 监控项单位
	 * @return
	 * @throws ParseException 
	 */
	int getOffsetUnit(int unit) throws ParseException;
	
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
	public void add4AddPlane(String dprtcode, String fjjx, String fjzch, String xlh, String strCalInit);
	
	/**
	 * @Description 飞机启用时增加监控数据
	 * @CreateTime 2018年4月8日 下午2:14:02
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjjx	飞机机型
	 * @param fjzch	飞机注册号
	 * @param fjxlh 飞机MSN
	 */
	public void add4EffectPlane(String dprtcode, String fjjx, String fjzch, String fjxlh);
	
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
	public void removeByEOExecObject(String eoId, String fjzch, String bjh, String xlh) throws BusinessException;
	
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
	public void add4installComponent(String dprtcode, String fjjx, String fjzch, String fjxlh, String zjqdid, String bjh, String xlh, String strCalInit);

	
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
	public void add4RemoveComponentUndo(String dprtcode, String fjjx, String fjzch, String fjxlh, String zjqdid, String bjh, String xlh, String strCalInit);
	/**
	 * @Description 生成对部件的初始值及累计值，写入到临时表-需要在装机清单编辑区同步到生效区前完成
	 * @CreateTime 2017年10月30日 下午4:11:58
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param pcbh 批次编号
	 * @return
	 */
	public void addTemp4Component(String dprtcode, String fjzch, String pcbh);
	
	/**
	 * @Description 生成对飞机的发动机的初始值及累计值，写入到临时表-需要在装机清单编辑区同步到生效区前完成
	 * @CreateTime 2017年10月30日 下午4:11:58
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param pcbh 批次编号
	 * @return
	 */
	public void addTemp4PlaneEngine(String dprtcode, String fjzch, String pcbh);
	
	/**
	 * @Description 生成对飞机的APU的初始值及累计值，写入到临时表-需要在装机清单编辑区同步到生效区前完成
	 * @CreateTime 2017年10月30日 下午4:11:58
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param pcbh 批次编号
	 * @return
	 */
	public void addTemp4PlaneAPU(String dprtcode, String fjzch, String pcbh);
	
	/**
	 * @Description 从临时表更新已执行任务实际值
	 * @CreateTime 2017年10月30日 下午5:26:32
	 * @CreateBy 徐勇
	 * @param pcbh 批次编号
	 */
	public void updateFromTemp(String pcbh);
	
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
	public void updateTaskInit4Plane(String dprtcode, String fjzch, String calInit, String fhInit, String fcInit);
	
	/**
	 * @Description 飞行记录本（提交/修订/作废）更新已执行任务实际值
	 * @CreateTime 2017年11月14日 下午5:09:58
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param minKcsj 最小开车时间
	 */
	public void updateTask4FLB(String dprtcode, String fjzch, Date minKcsj);
	
	/**
	 * @Description 工单完成，处理监控数据
	 * @CreateTime 2017年11月21日 上午9:27:41
	 * @CreateBy 徐勇
	 * @param workOrderId 工单ID
	 */
	public void doWorkOrderFinish(String workOrderId);
	
	/**
	 * @Description 根据装机清单ID删除监控数据 (监控数据未执行且不存在非指定结束的工单)
	 * @CreateTime 2017年11月25日 上午9:45:19
	 * @CreateBy 徐勇
	 * @param zjqdid 装机清单id
	 */
	public void removeMonitoringObjectByZjqdid(String zjqdid);
	
	/**
	 * @Description 计算时间修改 监控数据进行修订
	 * @CreateTime 2017年11月28日 下午3:27:40
	 * @CreateBy 徐勇
	 * @param jksjid 监控数据ID
	 * @param jssj 新的计算时间
	 */
	public void doMOModifyJssj(String jksjid, Date jssj);
	
	/**
	 * @Description 根据装机清单id更新序列号
	 * @CreateTime 2017年12月6日 下午3:09:50
	 * @CreateBy 徐勇
	 * @param zjqdid 装机清单id
	 * @param xlh 部件序列号
	 */
	public void updateXlhByZjqdid(String zjqdid, String xlh);
}
