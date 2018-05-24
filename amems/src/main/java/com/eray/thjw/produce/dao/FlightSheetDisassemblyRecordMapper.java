package com.eray.thjw.produce.dao;

import com.eray.thjw.produce.po.FlightSheetDisassemblyRecord;
import com.eray.thjw.produce.po.FlightSheetFinishedWork;

/**
 * @Description 飞行记录本-拆解记录mapper
 * @CreateTime 2017年10月24日 下午4:59:17
 * @CreateBy 韩武
 */
public interface FlightSheetDisassemblyRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(FlightSheetDisassemblyRecord record);

    int insertSelective(FlightSheetDisassemblyRecord record);

    FlightSheetDisassemblyRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FlightSheetDisassemblyRecord record);

    int updateByPrimaryKey(FlightSheetDisassemblyRecord record);
    
    /**
     * @Description 飞行记录本保存更新拆解记录
     * @CreateTime 2018年5月9日 下午4:14:15
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int updateByFlightSheet(FlightSheetDisassemblyRecord record);
    
    /**
     * @Description 删除拆解记录
     * @CreateTime 2017年10月25日 下午2:30:54
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int deleteNotExist(FlightSheetFinishedWork work);
}