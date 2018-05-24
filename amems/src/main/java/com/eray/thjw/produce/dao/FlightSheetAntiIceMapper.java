package com.eray.thjw.produce.dao;

import com.eray.thjw.produce.po.FlightSheetAntiIce;
import com.eray.thjw.produce.po.FlightSheetLeg;

/**
 * @Description 飞行记录本-防冰液信息mapper
 * @CreateTime 2017年10月24日 下午4:58:52
 * @CreateBy 韩武
 */
public interface FlightSheetAntiIceMapper {
    int deleteByPrimaryKey(String id);

    int insert(FlightSheetAntiIce record);

    int insertSelective(FlightSheetAntiIce record);

    FlightSheetAntiIce selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FlightSheetAntiIce record);

    int updateByPrimaryKey(FlightSheetAntiIce record);
    
    /**
     * @Description 删除防冰液信息
     * @CreateTime 2017年10月25日 下午2:30:54
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int deleteNotExist(FlightSheetLeg record);
}