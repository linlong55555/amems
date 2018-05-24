package com.eray.thjw.produce.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.MonitoringObject;

/** 
 * @Description 监控数据处理DAO
 * @CreateTime 2017年10月9日 下午1:53:46
 * @CreateBy 徐勇	
 */
public interface MonitorDataMapper {
	
	/**
	 * @Description 查询时控时寿项目需要监控的数据 
	 * @CreateTime 2017年10月9日 下午3:18:12
	 * @CreateBy 徐勇
	 * @param wxfaid 维修方案ID
	 * @return
	 */
	List<MonitoringObject> selectControlMPNeedMonitorDataList(@Param("wxfaid")String wxfaid);
	
	/**
	 * @Description 查询维修项目/定检包需要监控的数据
	 * @CreateTime 2017年10月9日 下午3:18:48
	 * @CreateBy 徐勇
	 * @param wxfaid 维修方案ID
	 * @return
	 */
	List<MonitoringObject> selectCommonMPNeedMonitorDataList(@Param("wxfaid")String wxfaid);
	
	/**
	 * @Description 批量从监控数据同步到当前监控数据中
	 * @CreateTime 2017年10月10日 上午10:46:13
	 * @CreateBy 徐勇
	 * @param list 监控数据
	 * @return
	 */
	int insertCurrMonitor4BatchSync(@Param("list")List<MonitoringObject> list);
	
	/**
	 * @Description 按机型查询飞机和飞机序列号件的初始化日期
	 * @CreateTime 2017年10月10日 下午4:26:50
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjjx 机型
	 * @return List<[{"ID","飞机注册号|装机清单ID"},{"CSZ","日历初始值"}]>
	 */
	List<Map<String, String>> selectAllCalInitByFjjx(@Param("dprtcode")String dprtcode, @Param("fjjx")String fjjx);

	/**
	 * @Description 根据EOID查询需要进行监控的对飞机监控数据
	 * @CreateTime 2017年10月12日 上午10:44:30
	 * @CreateBy 徐勇
	 * @param id EO ID
	 * @return
	 */
	List<MonitoringObject> selectEOPlaneNeedMonitorDataList(@Param("id")String id);
	
	/**
	 * @Description 根据EO查询需要进行监控的对部件监控数据 
	 * @CreateTime 2017年10月12日 上午10:47:26
	 * @CreateBy 徐勇
	 * @param id EO ID
	 * @param dprtcode EO所属组织机构
	 * @param fjjx EO的飞机机型
	 * @return
	 */
	List<MonitoringObject> selectEOPartNeedMonitorDataList(@Param("id")String id, @Param("dprtcode")String dprtcode, @Param("fjjx")String fjjx);
	
	/**
	 * @Description 查询单个飞机所需要的监控的维修项目和EO
	 * @CreateTime 2017年10月16日 下午2:03:28
	 * @CreateBy 徐勇
	 * @param dprtcode 飞机所属组织机构
	 * @param fjjx 飞机机型
	 * @param fjzch 飞机注册号
	 * @return
	 */
	List<MonitoringObject> selectPlaneNeedMonitorDataList(@Param("dprtcode")String dprtcode, @Param("fjjx")String fjjx, @Param("fjzch")String fjzch);

	/**
	 * @Description 查询装机件需要监控的数据
	 * @CreateTime 2017年10月25日 下午3:28:14
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjjx 飞机机型
	 * @param fjzch 飞机注册号
	 * @param bjh 部件号
	 * @param xlh 部件序列号
	 * @return
	 */
	List<MonitoringObject> selectInstallComponentNeedMonitorDataList(@Param("dprtcode")String dprtcode, @Param("fjjx")String fjjx, @Param("fjzch")String fjzch, @Param("bjh")String bjh, @Param("xlh")String xlh);

	/**
	 * @Description 生成对部件的初始值及累计值，写入到临时表-需要在装机清单编辑区同步到生效区前完成
	 * @CreateTime 2017年10月30日 下午4:11:58
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param pcbh 批次编号
	 * @return
	 */
	int insertTemp4Component(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch, @Param("pcbh")String pcbh);
	
	/**
	 * @Description 生成对飞机的发动机的初始值及累计值，写入到临时表-需要在装机清单编辑区同步到生效区前完成
	 * @CreateTime 2017年10月30日 下午4:11:58
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param pcbh 批次编号
	 * @return
	 */
	int insertTemp4PlaneEngine(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch, @Param("pcbh")String pcbh);
	
	/**
	 * @Description 生成对飞机的APU的初始值及累计值，写入到临时表-需要在装机清单编辑区同步到生效区前完成
	 * @CreateTime 2017年10月30日 下午4:11:58
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param pcbh 批次编号
	 * @return
	 */
	int insertTemp4PlaneAPU(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch, @Param("pcbh")String pcbh);
	
	/**
	 * @Description 从临时区更新计划执行数据 
	 * @CreateTime 2017年10月30日 下午5:24:05
	 * @CreateBy 徐勇
	 * @param pcbh 批次编号
	 * @return
	 */
	int update904FromTemp(@Param("pcbh")String pcbh);
	
	/**
	 * @Description 从临时区更新已执行数据
	 * @CreateTime 2017年10月30日 下午5:24:05
	 * @CreateBy 徐勇
	 * @param pcbh 批次编号
	 * @return
	 */
	int update903FromTemp(@Param("pcbh")String pcbh);
	
	/**
	 * @Description 根据批次编号清除临时表数据
	 * @CreateTime 2017年10月31日 上午9:35:20
	 * @CreateBy 徐勇
	 * @param pcbh 批次编号
	 * @return
	 */
	int deleteTempByPcbh(@Param("pcbh")String pcbh);
	
	/**
	 * @Description 修改b_s2_904 监控数据-(计划)执行数据存在飞行时间/飞行循环监控项目的初始化值
	 * @CreateTime 2017年11月7日 下午3:26:37
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param jklbh 监控类编号
	 * @param csz 初始值
	 * @return
	 */
	int update904Init4Plane(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch, @Param("jklbh")String jklbh, @Param("csz")String csz);
	
	/**
	 * @Description 修改b_s2_903 监控数据-上次执行数据飞行时间/飞行循环监控项目的上次实际值
	 * @CreateTime 2017年11月7日 下午3:35:20
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param jklbh 监控类编号
	 * @return
	 */
	int update903sjz4Plane(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch, @Param("jklbh")String jklbh);
	
	/**
	 * @Description 生成对飞机的FH/FC累计值到临时表（包含本飞行记录本会影响的所有已执行任务）
	 * @CreateTime 2017年12月22日 上午11:37:58
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param pcbh 批次编号
	 * @return
	 */
	int insertTemp4FJByFLB(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch, @Param("pcbh")String pcbh, @Param("minKcsj")Date minKcsj);
	
	/**
	 * @Description 生成对部件的累计值到临时表（包含本飞行记录本会影响的所有已执行任务）
	 * @CreateTime 2017年11月14日 下午4:40:28
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param pcbh 批次号
	 * @param minKcsj 最小开车时间（即航次一的开车时间）
	 * @return
	 */
	int insertTemp4ComponentByFLB(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch, @Param("pcbh")String pcbh, @Param("minKcsj")Date minKcsj);
	
	/**
	 * @Description 生成对飞机的发动机监控项的累计值，写入到临时表 （包含本飞行记录本会影响的所有已执行任务）
	 * @CreateTime 2017年11月14日 下午4:40:28
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param pcbh 批次号
	 * @param minKcsj 最小开车时间（即航次一的开车时间）
	 * @return
	 */
	int insertTemp4PlaneEngineByFLB(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch, @Param("pcbh")String pcbh, @Param("minKcsj")Date minKcsj);
	
	/**
	 * @Description 生成对飞机的APU监控项的累计值，写入到临时表（包含本飞行记录本会影响的所有已执行任务）
	 * @CreateTime 2017年11月14日 下午4:40:28
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param pcbh 批次号
	 * @param minKcsj 最小开车时间（即航次一的开车时间）
	 * @return
	 */
	int insertTemp4PlaneAPUByFLB(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch, @Param("pcbh")String pcbh, @Param("minKcsj")Date minKcsj);

	/**
	 * @Description 从临时区更新计划执行数据实际值
	 * @CreateTime 2017年10月30日 下午5:24:05
	 * @CreateBy 徐勇
	 * @param pcbh 批次编号
	 * @return
	 */
	int update904SJZFromTemp(@Param("pcbh")String pcbh);
	
	/**
	 * @Description 从临时区和904更新已执行数据
	 * @CreateTime 2017年10月30日 下午5:24:05
	 * @CreateBy 徐勇
	 * @param pcbh 批次编号
	 * @return
	 */
	int update903FromTempAnd904(@Param("pcbh")String pcbh);
	
	/**
	 * @Description 从临时区更新计划起算点和计划值
	 * @CreateTime 2017年12月25日 下午3:53:26
	 * @CreateBy 徐勇
	 * @param pcbh
	 * @return
	 */
	int update904JHFromTemp(@Param("pcbh")String pcbh);
	
	/**
	 * @Description 查询飞机装机清单要监控的维修项目和EO
	 * @CreateTime 2018年4月10日 上午10:15:41
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjjx	飞机机型
	 * @param fjzch	飞机注册号
	 * @return
	 */
	List<MonitoringObject> selectInstallNeedMonitorDataList(@Param("dprtcode")String dprtcode, @Param("fjjx")String fjjx, @Param("fjzch")String fjzch);

	
	/**
	 * @Description 查询飞机和飞机序列号件的初始化日期
	 * @CreateTime 2018年4月10日 下午2:36:11
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjjx 机型
	 * @return List<[{"ID","飞机注册号|装机清单ID"},{"CSZ","日历初始值"}]>
	 */
	List<Map<String, String>> selectAllCalInitByFjzch(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);
	
	/**
	 * @Description 查询生产指令应监控数据
	 * @CreateTime 2018年5月2日 下午2:59:09
	 * @CreateBy 徐勇
	 * @param id 生产指令id
	 * @return
	 */
	List<MonitoringObject> selectPONeedMonitorDataList(@Param("id")String id);
}
