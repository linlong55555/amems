package com.eray.thjw.produce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.MonitoringLast;

/**
 * @Description 监控数据-上次执行数据Mapper
 * @CreateTime 2017-9-23 下午3:35:14
 * @CreateBy 刘兵
 */
public interface MonitoringLastMapper {
    int deleteByPrimaryKey(String id);

    int insert(MonitoringLast record);

    int insertSelective(MonitoringLast record);

    MonitoringLast selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MonitoringLast record);

    /**
     * @Description 更新监控数据-上次执行数据
     * @CreateTime 2017-9-30 下午1:33:10
     * @CreateBy 刘兵
     * @param record 监控数据-上次执行数据
     * @return int
     */
    int updateById(MonitoringLast record);
    
    /**
	 * @Description 根据监控数据id查询监控数据-上次执行数据
	 * @CreateTime 2017-9-28 上午11:06:29
	 * @CreateBy 刘兵
	 * @param jksjid 监控数据id
	 * @return List<MonitoringLast> 监控数据-上次执行数据集合
	 */
    List<MonitoringLast> queryByJksjid(String jksjid);
    
    /**
     * @Description 批量新增上次执行数据
     * @CreateTime 2017年11月21日 上午9:35:08
     * @CreateBy 徐勇
     * @param list
     * @return
     */
    int insert4Batch(List<MonitoringLast> list);
    
    /**
     * @Description 根据装机清单ID删除监控数据-上次执行数据 (监控数据未执行且不存在非指定结束的工单)
     * @CreateTime 2017年11月25日 上午9:54:39
     * @CreateBy 徐勇
     * @param zjqdid 装机清单id
     * @return
     */
    int deleteByZjqdid(@Param("zjqdid")String zjqdid);
    
    /**
     * @Description 批量修改上次执行数据
     * @CreateTime 2017年11月21日 上午9:35:08
     * @CreateBy 徐勇
     * @param list
     * @return
     */
    int update4Batch(List<MonitoringLast> list);
}