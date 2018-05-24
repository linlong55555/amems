package com.eray.thjw.produce.dao;

import com.eray.thjw.produce.po.FlightSheet;
import com.eray.thjw.produce.po.FlightSheetLeg;

/**
 * @Description 飞行记录本-航段信息mapper
 * @CreateTime 2017年10月24日 下午4:59:43
 * @CreateBy 韩武
 */
public interface FlightSheetLegMapper {
    int deleteByPrimaryKey(String id);

    int insert(FlightSheetLeg record);

    int insertSelective(FlightSheetLeg record);

    FlightSheetLeg selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FlightSheetLeg record);

    int updateByPrimaryKey(FlightSheetLeg record);
    
    /**
	 * @Description 删除航段数据
	 * @CreateTime 2017年10月25日 上午11:39:47
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @return
	 */
	int deleteNotExist(FlightSheet flightSheet);
}