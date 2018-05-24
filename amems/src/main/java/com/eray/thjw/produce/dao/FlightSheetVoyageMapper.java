package com.eray.thjw.produce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.produce.po.FlightSheet;
import com.eray.thjw.produce.po.FlightSheetVoyage;

/**
 * @Description b_s2_00601 飞行记录本-航次数据 dao
 * @CreateTime 2017年9月30日 上午11:08:08
 * @CreateBy 徐勇
 */
public interface FlightSheetVoyageMapper {
    int deleteByPrimaryKey(String id);

    int insert(FlightSheetVoyage record);

    int insertSelective(FlightSheetVoyage record);

    FlightSheetVoyage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FlightSheetVoyage record);

    int updateByPrimaryKey(FlightSheetVoyage record);
    /**
     * 
     * @Description 日期区间内发动机滑油监控
     * @CreateTime 2017年10月12日 下午1:47:36
     * @CreateBy 岳彬彬
     * @param baseEntity
     * @return
     */
    List<Map<String,Object>> select4MointorDate(BaseEntity baseEntity); 
    /**
     * 
     * @Description 飞机下所有发动机滑油数据
     * @CreateTime 2017年10月12日 下午1:49:17
     * @CreateBy 岳彬彬
     * @param baseEntity
     * @return
     */
    List<Map<String,Object>> select4MointorFj(BaseEntity baseEntity); 
    
	/**
	 * 
	 * @Description 条件查询飞行履历主列表
	 * @CreateTime 2017-10-12 下午2:35:42
	 * @CreateBy 孙霁
	 * @param flightSheet
	 * @return
	 */
	List<FlightSheetVoyage> queryAllFlighthistory(FlightSheetVoyage flightSheetVoyage);
	
	/**
	 * 
	 * @Description 查询小计数据
	 * @CreateTime 2017-10-13 上午10:58:12
	 * @CreateBy 孙霁
	 * @param flightSheet
	 * @return
	 */
	FlightSheetVoyage selectSubtotalByFlighthistory(FlightSheetVoyage flightSheetVoyage);
	
	/**
	 * 
	 * @Description 查询基本信息
	 * @CreateTime 2017-10-20 下午2:18:53
	 * @CreateBy 孙霁
	 * @param flightSheetVoyage
	 * @return
	 */
	FlightSheetVoyage queryBj(FlightSheetVoyage flightSheetVoyage);
	
	/**
	 * @Description 删除飞行数据
	 * @CreateTime 2017年10月25日 上午11:39:47
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @return
	 */
	int deleteNotExist(FlightSheet flightSheet);
	
	/**
	 * @Description 飞行记录本更新飞行数据
	 * @CreateTime 2017年10月28日 下午4:05:09
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	int updateByFLB(FlightSheetVoyage record);
	
	/**
	 * @Description 根据mainid查询飞行数据
	 * @CreateTime 2017年10月30日 上午9:33:02
	 * @CreateBy 韩武
	 * @param flightSheetVoyage
	 * @return
	 */
	List<FlightSheetVoyage> queryByMainid(String mainid);
	
	/**
	 * 
	 * @Description 查询任务类型小计
	 * @CreateTime 2017-11-28 上午10:48:15
	 * @CreateBy 孙霁
	 * @param flightSheetVoyage
	 * @return
	 */
	List<FlightSheetVoyage> selectRwlxByFlighthistory(FlightSheetVoyage flightSheetVoyage);

	/**
	 * 根据参数查询飞行记录本明细
	 * @Description 
	 * @CreateTime 2017-12-6 上午9:48:44
	 * @CreateBy 雷伟
	 * @param paramMap
	 * @return
	 */
	FlightSheetVoyage findFlightVoyageByParam(Map<String, String> paramMap);

	/**
	 * 批量新增导入
	 * @Description 
	 * @CreateTime 2017-12-6 下午2:08:06
	 * @CreateBy 雷伟
	 * @param addDetailDatas
	 */
	void insert4Batch(List<FlightSheetVoyage> list);
	/**
	 * 批量更新导入
	 * @Description 
	 * @CreateTime 2017-12-6 下午2:08:06
	 * @CreateBy 雷伟
	 * @param addDetailDatas
	 */
	void update4Batch(@Param("list")List<FlightSheetVoyage> list);

	/**
	 * 批量更新航次和航次描述：导入
	 * @Description 
	 * @CreateTime 2017-12-6 下午2:08:06
	 * @CreateBy 雷伟
	 * @param list
	 */
	void updateHcHcms4Batch(@Param("list")List<FlightSheetVoyage> list);

	/**
	 * @Description 查询物料基本信息
	 * @CreateTime 2018-3-26 下午4:05:54
	 * @CreateBy 雷伟
	 * @param flightSheetVoyage
	 * @return
	 */
	Object queryMaterialBj(FlightSheetVoyage flightSheetVoyage);
}