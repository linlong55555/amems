package com.eray.thjw.project2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.project2.po.EOMonitorIemSet;


/**
 * @Description EO-监控项设置 Mapper
 * @CreateTime 2017-8-22 上午9:39:16
 * @CreateBy 雷伟
 */
public interface EOMonitorIemSetMapper {
	
	/**
	 * @Description 新增EO监控项设置
	 * @CreateTime 2017-8-22 上午10:12:13
	 * @CreateBy 雷伟
	 * @param eoMonitorIemSet 监控项设置
	 * @return
	 */
	int insert(EOMonitorIemSet eoMonitorIemSet);
	
	/**
	 * @Description 修改EO监控项设置
	 * @CreateTime 2017-8-22 上午10:12:17
	 * @CreateBy 雷伟
	 * @param eoMonitorIemSet 监控项设置
	 * @return
	 */
	int updateByPrimaryKeySelective(EOMonitorIemSet eoMonitorIemSet);

	/**
	 * @Description 批量新增EO监控项设置
	 * @CreateTime 2017-8-23 上午10:32:20
	 * @CreateBy 雷伟
	 * @param jkxszList
	 */
	void insert4Batch(List<EOMonitorIemSet> jkxszList);

	/**
	 * @Description 查询EO监控项设置 
	 * @CreateTime 2017-8-24 下午8:07:12
	 * @CreateBy 雷伟
	 * @param eoMonitorIemSet
	 * @return
	 */
	List<EOMonitorIemSet> queryAllList(EOMonitorIemSet eoMonitorIemSet);

	/**
	 * @Description 根据EO主表ID删记录
	 * @CreateTime 2017-8-25 下午6:15:42
	 * @CreateBy 雷伟
	 * @param eoId EO主表ID
	 */
	void deleteByMainid(String eoId);
	
	/**
	 * @Description 根据多个EOid查询监控项目，以mainid,XC,JKLBH进行排序
	 * @CreateTime 2017年10月16日 下午9:02:00
	 * @CreateBy 徐勇
	 * @param list EOID
	 * @return
	 */
	List<EOMonitorIemSet> selectMonitorByMainIds(@Param("list")List<String> list);
	/**
	 * 
	 * @Description 根据mainid获取监控项
	 * @CreateTime 2018年3月15日 下午3:52:55
	 * @CreateBy 岳彬彬
	 * @param mainid
	 * @return
	 */
	List<EOMonitorIemSet> queryByMainid(String mainid);
}