package com.eray.thjw.produce.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.FlightSheet;

/**
 * @Description b_s2_006 飞行记录本 dao
 * @CreateTime 2017年9月30日 上午11:07:41
 * @CreateBy 徐勇
 */
public interface FlightSheetMapper {
    int deleteByPrimaryKey(String id);

    int insert(FlightSheet record);

    int insertSelective(FlightSheet record);

    FlightSheet selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FlightSheet record);

    int updateByPrimaryKey(FlightSheet record);
    
    List<FlightSheet> queryFlightSheetList(FlightSheet record);

	List<FlightSheet> queryAllList(FlightSheet flightSheet);
	
	/**
	 * @Description 查询飞行记录本详情
	 * @CreateTime 2017年10月26日 下午5:11:41
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @return
	 */
	FlightSheet queryDetail(FlightSheet flightSheet);
	
	/**
	 * @Description 获取同一飞机的同一飞行记录本页码数量
	 * @CreateTime 2017年10月28日 下午2:48:18
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @return
	 */
	int getJlbymCount(FlightSheet flightSheet);
	
	/**
	 * @Description 获取同一飞机的大于指定飞行日期的数量
	 * @CreateTime 2017年10月28日 下午2:48:18
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @return
	 */
	int getFxrqCount(FlightSheet flightSheet);
	
	/**
	 * @Description 获取同一飞机的大于指定飞行时间的数量
	 * @CreateTime 2017年10月28日 下午2:48:18
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @return
	 */
	int getFxsjCount(FlightSheet flightSheet);
	
	/**
	 * @Description 更新修订提醒标识为提醒（修改参数 以后的飞行记录本）
	 * @CreateTime 2017年11月23日 下午3:03:54
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @param kcsj 开车时间
	 * @param jlbym 记录本页面
	 * @return
	 */
	int updateReviseFlag(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch, @Param("kcsj")Date kcsj, @Param("jlbym")String jlbym, @Param("id")String id);

	List<FlightSheet> getHbhFxrqFlightRecord(FlightSheet sheet);

	/**
	 * @Description 根据参数查询飞行记录本
	 * @CreateTime 2017-12-6 上午9:25:13
	 * @CreateBy 雷伟
	 * @param paramMap
	 * @return
	 */
	FlightSheet findFlightSheetByParam(Map<String, String> paramMap);
	
	/**
	 * @Description 查询上一页下一页
	 * @CreateTime 2017年12月5日 下午4:24:27
	 * @CreateBy 韩武
	 * @param sheet
	 * @return
	 */
	FlightSheet queryClosePage(FlightSheet sheet);

	/**
	 * @Description 批量导入
	 * @CreateTime 2017-12-6 下午1:53:17
	 * @CreateBy 雷伟
	 * @param addMainDatas
	 */
	void insert4Batch(List<FlightSheet> addMainDatas);

	/**
	 * 批量更新开车时间：导入
	 * @Description 
	 * @CreateTime 2017-12-6 下午2:08:06
	 * @CreateBy 雷伟
	 * @param list maind集合
	 */
	void updateKcsj4Batch(@Param("list")List<String> list);
	
}