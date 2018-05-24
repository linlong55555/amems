package com.eray.thjw.produce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.MonitoringObject;
/**
 * @Description 监控对象Mapper
 * @CreateTime 2017-9-23 下午3:35:14
 * @CreateBy 刘兵
 */
public interface MonitoringObjectMapper {
    int deleteByPrimaryKey(String id);

    int insert(MonitoringObject record);

    int insertSelective(MonitoringObject record);

    MonitoringObject selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MonitoringObject record);
    
    /**
	 * @Description 根据监控数据id查询监控对象及监控数据-上次执行数据
	 * @CreateTime 2017-9-30 上午9:42:15
	 * @CreateBy 刘兵
	 * @param id 监控数据id
	 * @return MonitoringObject 监控对象
	 */
    MonitoringObject selecyById(String id);
    
    /**
     * @Description 根据维修方案ID查询未执行的飞机监控数据（带计划、上次执行）
     * @CreateTime 2017年10月9日 下午8:02:58
     * @CreateBy 徐勇
     * @param wxfaid 维修方案ID
     * @return
     */
    List<MonitoringObject> selectNotExeMonitorDataListByWxfaid(@Param("wxfaid")String wxfaid);
    
    /**
     * @Description 批量更新监控数据来源信息
     * @CreateTime 2017年10月10日 上午9:57:56
     * @CreateBy 徐勇
     * @param list 监控数据
     * @return
     */
    int updateSource4Batch(@Param("list")List<MonitoringObject> list);

    /**
     * @Description 批量新增监控数据
     * @CreateTime 2017年10月10日 上午10:12:00
     * @CreateBy 徐勇
     * @param list 监控数据
     * @return
     */
    int insert4Batch(@Param("list")List<MonitoringObject> list);
    
    /**
     * @Description 根据工单查询其监控数据
     * @CreateTime 2017年11月20日 上午10:41:05
     * @CreateBy 徐勇
     * @param workOrderId 工单ID
     * @return
     */
    MonitoringObject selectByWorkOrder(@Param("workOrderId")String workOrderId);
    
    /**
     * @Description 根据维修项目id查询可取代的监控数据
     * @CreateTime 2017年11月21日 上午10:48:17
     * @CreateBy 徐勇
     * @param lyid 来源id
     * @param dprtcode 组织机构
     * @param fjzch 飞机注册号
     * @return
     */
    List<MonitoringObject> selectReplaceByLyid(@Param("lyid")String lyid, @Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);

    /**
     * @Description 根据装机清单ID删除监控数据 (监控数据未执行且不存在非指定结束的工单)
     * @CreateTime 2017年11月25日 上午9:54:39
     * @CreateBy 徐勇
     * @param zjqdid 装机清单id
     * @return
     */
    int deleteByZjqdid(@Param("zjqdid")String zjqdid);
    
    /**
     * @Description 根据被影响监控数据id查询 
     * @CreateTime 2017年11月28日 下午3:24:27
     * @CreateBy 徐勇
     * @param byxjksjid 被影响监控数据id
     * @return
     */
    List<MonitoringObject> selecyByByxjksjid(@Param("byxjksjid")String byxjksjid);
    
    /**
     * @Description 根据装机清单id更新序列号
     * @CreateTime 2017年12月6日 下午3:13:06
     * @CreateBy 徐勇
     * @param zjqdid 装机清单id
     * @param xlh 序列号
     * @return
     */
    int updateXlhByZjqdid(@Param("zjqdid")String zjqdid, @Param("xlh")String xlh);
    
    /**
     * @Description 根据装机清单查询每个来源最后的一次监控数据
     * @CreateTime 2017年12月15日 上午10:41:05
     * @CreateBy 徐勇
     * @param zjqdid 装机清单id
     */
    List<MonitoringObject> selectLastMonitorByZjqdid(@Param("zjqdid")String zjqdid);
    
    /**
     * @Description 根据飞机查询未执行的监控数据 
     * @CreateTime 2018年4月10日 下午1:52:45
     * @CreateBy 徐勇
     * @param dprtcode 组织机构
     * @param fjzch 飞机注册号
     * @return
     */
    List<MonitoringObject> selectNotExeMonitorDataListByFjzch(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);

    /**
     * @Description 根据来源ID查询未执行的飞机监控数据（带计划、上次执行）
     * @CreateTime 2018年5月2日 下午3:13:06
     * @CreateBy 徐勇
     * @param lyid 来源id
     * @return
     */
    List<MonitoringObject> selectNotExeMonitorDataListByLyid(@Param("lyid")String lyid);
}