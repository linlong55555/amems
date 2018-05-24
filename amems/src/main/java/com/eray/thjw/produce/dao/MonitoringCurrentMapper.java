package com.eray.thjw.produce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.MonitoringCurrent;

/**
 * @Description 当前监控数据Mapper
 * @CreateTime 2017-9-23 下午3:35:14
 * @CreateBy 刘兵
 */
public interface MonitoringCurrentMapper {
    int deleteByPrimaryKey(String id);

    int insert(MonitoringCurrent record);

    int insertSelective(MonitoringCurrent record);

    MonitoringCurrent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MonitoringCurrent record);
    
    /**
	 * @Description 根据监控数据id查询任务信息(维修项目)
	 * @CreateTime 2017-9-28 上午11:06:29
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return MonitoringCurrent 监控数据
	 */
    MonitoringCurrent selectRelProjectById(String id);
    
    /**
	 * @Description 根据监控数据id查询任务信息(EO)
	 * @CreateTime 2017-9-28 上午11:06:29
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return MonitoringCurrent 监控数据
	 */
    MonitoringCurrent selectRelEOById(String id);
    
    /**
   	 * @Description 根据监控数据id查询任务信息(PO)
   	 * @CreateTime 2018-5-7 下午5:02:39
   	 * @CreateBy 刘兵
   	 * @param id 监控数据id
   	 * @return MonitoringCurrent 监控数据
   	 */
    MonitoringCurrent selectRelPoById(String id);
    
    /**
	 * @Description 根据维修方案编号删除当前监控数据（对飞机/装机件）
	 * @CreateTime 2017年10月9日 下午1:57:41
	 * @CreateBy 徐勇
	 * @param wxfabh 维修方案编号
	 * @param dprtcode 组织机构
	 * @return
	 */
	int deleteByMaintenanceScheme(@Param("wxfabh")String wxfabh, @Param("dprtcode")String dprtcode);
	
	/**
	 * @Description 根据来源编号和来源类型删除当前监控数据
	 * @CreateTime 2018年5月2日 下午2:10:51
	 * @CreateBy 徐勇
	 * @param lybh 来源编号
	 * @param lylx 来源类型
	 * @param dprtcode 组织机构
	 * @return
	 */
	int deleteByLybhAndLx(@Param("lybh")String lybh, @Param("lylx")Integer lylx, @Param("dprtcode")String dprtcode);
	
	/**
	 * @Description 根据飞机注册号删除当前监控数据
	 * @CreateTime 2017年10月9日 下午1:57:41
	 * @CreateBy 徐勇
	 * @param fjzch 飞机注册号
	 * @param dprtcode 组织机构
	 * @return
	 */
	int deleteByFjzch(@Param("fjzch")String fjzch, @Param("dprtcode")String dprtcode);
	
	/**
	 * @Description 根据查询条件分页查询当前监控数据(飞机维修项目监控信息)
	 * @CreateTime 2017-9-25 下午3:19:22
	 * @CreateBy 刘兵
	 * @param record 当前监控数据
	 * @return List<MonitoringCurrent> 当前监控数据集合
	 */
    List<MonitoringCurrent> queryAllPageMaintenanceList(MonitoringCurrent record);
    
    /**
	 * @Description 根据查询条件分页查询当前监控数据(EO监控信息)
	 * @CreateTime 2017-9-27 下午4:55:05
	 * @CreateBy 刘兵
	 * @param record 当前监控数据
	 * @return List<MonitoringCurrent> 当前监控数据集合
	 */
    List<MonitoringCurrent> queryAllPageEOList(MonitoringCurrent record);
    
    /**
   	 * @Description 根据查询条件分页查询当前监控数据(生产指令监控信息)
   	 * @CreateTime 2018-5-7 下午3:15:41
   	 * @CreateBy 刘兵
   	 * @param record 当前监控数据
   	 * @return List<MonitoringCurrent> 当前监控数据集合
   	 */
     List<MonitoringCurrent> queryAllPagePOList(MonitoringCurrent record);
    
    /**
	 * @Description 根据查询条件分页查询当前监控数据(飞机维修项目监控信息)(预组包专用)
	 * @CreateTime 2017-9-25 下午3:19:22
	 * @CreateBy 刘兵
	 * @param record 当前监控数据
	 * @return List<MonitoringCurrent> 当前监控数据集合
	 */
    List<MonitoringCurrent> queryPackageMaintenanceList(MonitoringCurrent record);
    
    /**
	 * @Description 根据查询条件查询维修清单
	 * @CreateTime 2017-9-25 下午3:19:22
	 * @CreateBy 刘兵
	 * @param record 当前监控数据
	 * @return List<MonitoringCurrent> 当前监控数据集合
	 */
    List<MonitoringCurrent> queryPackageMaintenanceDetailList(MonitoringCurrent record);
    
    /**
	 * @Description 根据查询条件分页查询当前监控数据(EO监控信息)(预组包专用)
	 * @CreateTime 2017-9-27 下午4:55:05
	 * @CreateBy 刘兵
	 * @param record 当前监控数据
	 * @return List<MonitoringCurrent> 当前监控数据集合
	 */
    List<MonitoringCurrent> queryPackageEOList(MonitoringCurrent record);
    
    /**
	 * @Description 根据查询条件分页查询当前监控数据(生产指令监控信息)(预组包专用)
	 * @CreateTime 2018-5-8 下午2:57:21
	 * @CreateBy 刘兵
	 * @param record 当前监控数据
	 * @return List<MonitoringCurrent> 当前监控数据集合
	 */
    List<MonitoringCurrent> queryPackagePOList(MonitoringCurrent record);

	/**
	 * 
	 * @Description 查询当前监控数据列表
	 * @CreateTime 2017年10月12日 下午4:31:17
	 * @CreateBy 朱超
	 * @param record
	 * @return
	 */
	List<Map<String, Object>> selectAll4Mp(MonitoringCurrent record);

	/**
	 * 
	 * @Description 查询当前监控数据列表
	 * @CreateTime 2017年10月12日 下午4:40:27
	 * @CreateBy 朱超
	 * @param record
	 * @return
	 */
	List<Map<String, Object>> selectAll4Eo(MonitoringCurrent record);
	
	/**
	 * @Description 查询当前监控数据列表
	 * @CreateTime 2018-5-10 下午1:50:50
	 * @CreateBy 刘兵
	 * @param record
	 * @return
	 */
	List<Map<String, Object>> selectAll4Po(MonitoringCurrent record);
	
	/**
	 * @Description 获取定检包下的监控数据id集合
	 * @CreateTime 2017-10-14 上午10:56:32
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @param fjzch 飞机注册号
	 * @return List<String> 监控数据id集合
	 */
	List<String> query4FixedPackage(@Param("jksjid")String jksjid, @Param("fjzch")String fjzch);
	
	/**
	 * @Description 根据EO执行对象删除当前监控数据 
	 * @CreateTime 2017年10月18日 下午2:39:28
	 * @CreateBy 徐勇
	 * @param eoId EOID
	 * @param fjzch 飞机注册号 执行对象为飞机时传此参数
	 * @param bjh 部件号 执行对象为部件时需要传此参数
	 * @param xlh 部件序列号 执行对象为部件时需要传此参数
	 */
	int deleteByEOExecObject(@Param("lyid")String lyid, @Param("fjzch")String fjzch, @Param("bjh")String bjh, @Param("xlh")String xlh);

	/**
	 * @Description 根据装机清单ID删除当前监控数据
	 * @CreateTime 2017年10月19日 下午3:30:46
	 * @CreateBy 徐勇
	 * @param zjqdid 装机清单ID
	 * @return
	 */
	int deleteByZjqdid(@Param("zjqdid")String zjqdid);
	
    /**
     * @Description 根据装机清单id更新序列号
     * @CreateTime 2017年12月6日 下午3:13:06
     * @CreateBy 徐勇
     * @param zjqdid 装机清单id
     * @param xlh 序列号
     * @return
     */
    int updateXlhByZjqdid(@Param("zjqdid")String zjqdid, @Param("xlh")String xlh);
}