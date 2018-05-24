package com.eray.thjw.produce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.AircraftFailure;
import com.eray.thjw.produce.po.FlightSheetFinishedWork;
import com.eray.thjw.produce.po.FlightSheetLeg;

/**
 * @Description 飞行记录本-完成工作mapper
 * @CreateTime 2017年10月24日 下午4:59:33
 * @CreateBy 韩武
 */
public interface FlightSheetFinishedWorkMapper {
    int deleteByPrimaryKey(String id);

    int insert(FlightSheetFinishedWork record);

    int insertSelective(FlightSheetFinishedWork record);

    FlightSheetFinishedWork selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FlightSheetFinishedWork record);

    int updateByPrimaryKey(FlightSheetFinishedWork record);
    
    /**
     * @Description 删除完成工作
     * @CreateTime 2017年10月25日 下午2:30:54
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int deleteNotExist(FlightSheetLeg record);
    
    /**
     * @Description 查询飞行记本完成工作，关联查询手工标识的装拆记录 
     * @CreateTime 2017年11月15日 下午2:22:49
     * @CreateBy 徐勇
     * @param flbId 飞行记录本ID
     * @return
     */
    List<FlightSheetFinishedWork> selectByMainId(@Param("flbId")String flbId);
    
    /**
     * @Description 查询飞机故障履
     * @CreateTime 2018年5月22日 上午10:18:47
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<AircraftFailure> queryAircraftFailure(AircraftFailure record);
}