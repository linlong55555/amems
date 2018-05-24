package com.eray.thjw.produce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.MonitoringWorkpackage;
/**
 * 
 * @Description 待组包的监控项目Mapper
 * @CreateTime 2017年9月27日 下午5:24:19
 * @CreateBy 岳彬彬
 */
public interface MonitoringWorkpackageMapper {
    int deleteByPrimaryKey(String id);

    int insert(MonitoringWorkpackage record);

    int insertSelective(MonitoringWorkpackage record);

    MonitoringWorkpackage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MonitoringWorkpackage record);

    int updateByPrimaryKey(MonitoringWorkpackage record);
    /**
     * 
     * @Description 清除预组包关闭
     * @CreateTime 2017年9月28日 下午1:52:22
     * @CreateBy 岳彬彬
     * @param id
     */
    void updateByGbid(String id);
    
    /**
     * @Description 修改待组包的监控项目工包id字段
     * @CreateTime 2017-10-16 下午2:43:19
     * @CreateBy 刘兵
     * @param gbid 工包id
     * @param id 预组包id
     */
    void updateGbidById(@Param("gbid")String gbid, @Param("id")String id);
    
    /**
     * @Description 批量清空待组包的监控项目工包id
     * @CreateTime 2017-10-18 上午10:41:34
     * @CreateBy 刘兵
     * @param idList 监控数据id集合
     * @return int
     */
    int updateGbidByJksjgdid4Batch(List<String> idList);
    
    /**
     * @Description 根据定检包监控数据id清空待组包的监控项目工包id
     * @CreateTime 2017-10-18 上午10:41:34
     * @CreateBy 刘兵
     * @param djbjksjid 定检包监控数据id
     * @return int
     */
    int updateGbidByDjbjksjgdid(String djbjksjid);
    
    /**
     * @Description 根据工包id删除待组包的监控项目
     * @CreateTime 2017-10-24 下午5:01:56
     * @CreateBy 刘兵
     * @param gbid 工包id
     * @return int
     */
    int deleteByGbid(String gbid);
    
    /**
     * @Description 批量删除待组包的监控项目
     * @CreateTime 2017-10-14 下午2:21:19
     * @CreateBy 刘兵
     * @param idList 待组包id集合
     * @return int
     */
    int delete4Batch(List<String> idList);
    
    /**
     * @Description 修改监控数据显示标识为不显示
     * @CreateTime 2017-10-14 下午1:48:00
     * @CreateBy 刘兵
     * @param jksjid 监控数据id
     */
    void updateByXsbs(String jksjid);
    
    /**
     * 
     * @Description 获取预组包中是否存在工包
     * @CreateTime 2017年9月28日 下午1:52:56
     * @CreateBy 岳彬彬
     * @param gbid
     * @return
     */
    int getCountByGbid(String gbid);
    
    /**
     * @Description 获取预组包中已选中的数量
     * @CreateTime 2017-10-14 下午2:21:19
     * @CreateBy 刘兵
     * @param fjzch 飞机注册号
     * @param dprtcode 机构代码
     * @return int 已选中数量
     */
    int getCheckedCount(@Param("fjzch")String fjzch, @Param("dprtcode")String dprtcode);
    
    /**
	 * @Description 查询监控数据id集合,检查监控数据id是否存在
	 * @CreateTime 2017-10-13 下午5:43:57
	 * @CreateBy 刘兵
	 * @param map 参数
	 * @return List<String> 监控数据id集合
	 */
	List<String> query4CheckExist(Map<String, Object> map);
	
	/**
	 * @Description 根据条件查询已选中的监控数据列表
	 * @CreateTime 2017-10-14 下午4:05:52
	 * @CreateBy 刘兵
	 * @param record 待组包的监控项目
	 * @return List<MonitoringWorkpackage> 已选中的监控数据集合
	 */
	List<MonitoringWorkpackage> queryCheckedList(MonitoringWorkpackage record);
	
	/**
	 * @Description 根据监控数据id修改待组包的监控项目
	 * @CreateTime 2017-11-29 上午10:16:53
	 * @CreateBy 刘兵
	 * @param record 待组包的监控项目
	 */
    void updateByJksjId(MonitoringWorkpackage record);
    /**
     * 
     * @Description 查询工单是否存在预组包中
     * @CreateTime 2017年12月12日 下午2:51:35
     * @CreateBy 岳彬彬
     * @param jksjgdid
     * @return
     */
    int getCountByJksjgdid(String jksjgdid);
}