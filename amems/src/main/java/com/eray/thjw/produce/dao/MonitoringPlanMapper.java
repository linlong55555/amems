package com.eray.thjw.produce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.MonitoringObject;
import com.eray.thjw.produce.po.MonitoringPlan;
/**
 * @Description 监控数据-(计划)执行数据Mapper
 * @CreateTime 2017-9-23 下午3:35:14
 * @CreateBy 刘兵
 */
public interface MonitoringPlanMapper {
    int deleteByPrimaryKey(String id);

    int insert(MonitoringPlan record);

    int insertSelective(MonitoringPlan record);

    MonitoringPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MonitoringPlan record);
    
    /**
	 * @Description 根据监控数据id查询监控数据-(计划)执行数据
	 * @CreateTime 2017-9-29 上午11:41:22
	 * @CreateBy 刘兵
	 * @param jksjid 监控数据id
	 * @return List<MonitoringPlan> 监控数据-(计划)执行数据
	 */
    List<MonitoringPlan> queryByJksjid(String jksjid);

    /**
     * @Description 根据监控数据批量删除计划数据
     * @CreateTime 2017年10月9日 下午10:43:35
     * @CreateBy 徐勇
     * @param list 监控数据
     * @return
     */
    int deleteByMainIdBatch(@Param("list")List<MonitoringObject> list);
    
    /**
     * @Description 批量新增监控数据-计划
     * @CreateTime 2017年10月10日 上午10:21:00
     * @CreateBy 徐勇
     * @param list 监控数据-计划
     * @return
     */
    int insert4Batch(@Param("list")List<MonitoringPlan> list);

    /**
     * @Description 根据工单的监控数据id匹配出完成时限
     * @CreateTime 2017-10-11 下午4:42:45
     * @CreateBy 雷伟
     * @param jksjid 监控数据id
     * @return
     */
    MonitoringPlan getCompleteLimit(String jksjid);
    
    /**
     * @Description 查询飞机对日历监控项目的计划执行数据（计划起算值为空，计划值不空）
     * @CreateTime 2017年11月7日 下午4:12:49
     * @CreateBy 徐勇
     * @param dprtcode 组织机构
     * @param fjzch 飞机注册号
     * @return
     */
    List<MonitoringPlan> selectCalPlanListByPlane(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);
    
    /**
     * @Description 批量更新计划值
     * @CreateTime 2017年11月7日 下午4:34:29
     * @CreateBy 徐勇
     * @param list
     * @return
     */
    int updateJhz4Batch(@Param("list")List<MonitoringPlan> list);
    
    /**
     * @Description 批量更新初始值及实际值 
     * @CreateTime 2017年11月20日 下午4:36:02
     * @CreateBy 徐勇
     * @param list
     * @return
     */
    int updateSjz4Batch(@Param("list")List<MonitoringPlan> list);
    
    /**
     * @Description 根据装机清单ID删除监控数据-计划执行数据 (监控数据未执行且不存在非指定结束的工单)
     * @CreateTime 2017年11月25日 上午9:54:39
     * @CreateBy 徐勇
     * @param zjqdid 装机清单id
     * @return
     */
    int deleteByZjqdid(@Param("zjqdid")String zjqdid);
    
}