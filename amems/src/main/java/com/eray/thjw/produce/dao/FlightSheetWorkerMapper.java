package com.eray.thjw.produce.dao;

import com.eray.thjw.produce.po.FlightSheetFinishedWork;
import com.eray.thjw.produce.po.FlightSheetLeg;
import com.eray.thjw.produce.po.FlightSheetWorker;

/**
 * @Description 航段信息-工作者mapper
 * @CreateTime 2018年1月24日 上午9:55:12
 * @CreateBy 韩武
 */
public interface FlightSheetWorkerMapper {
    int deleteByPrimaryKey(String id);

    int insert(FlightSheetWorker record);

    int insertSelective(FlightSheetWorker record);

    FlightSheetWorker selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FlightSheetWorker record);

    int updateByPrimaryKey(FlightSheetWorker record);
    
    /**
     * @Description 删除工作者
     * @CreateTime 2018年1月24日 上午10:23:12
     * @CreateBy 韩武
     * @param flightSheet
     * @return
     */
	int deleteNotExist(FlightSheetFinishedWork work);
	
	/**
	 * @Description 删除完成工作时，同步删除工作者
	 * @CreateTime 2018年1月24日 下午2:59:50
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	int deleteByFinishedWork(FlightSheetLeg record);
}