package com.eray.thjw.material2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.material2.po.MeasurementToolsDetails;

public interface MeasurementToolsDetailsMapper {
    int deleteByPrimaryKey(String id);

    int insert(MeasurementToolsDetails record);

    int insertSelective(MeasurementToolsDetails record);

    MeasurementToolsDetails selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MeasurementToolsDetails record);

    int updateByPrimaryKey(MeasurementToolsDetails record);

	List<MeasurementToolsDetails> queryAllPageList(MeasurementToolsDetails measurementToolsDetails);

	int queryCount(MeasurementToolsDetails measurementToolsDetails);

	MeasurementToolsDetails getInfoById(
			MeasurementToolsDetails measurementToolsDetails);

	int getCurrentZt4Validation(String id);

	int selectMainidList(MeasurementToolsDetails mea);

	List<MeasurementToolsDetails> queryAllLogList(
			MeasurementToolsDetails measurementToolsDetails);
	
	/**
	 * @Description 根据件号、序列号查询计量工具监控明细
	 * @CreateTime 2018年3月28日 上午10:58:27
	 * @CreateBy 韩武
	 * @param bjh
	 * @param xlh
	 * @param dprtcode
	 * @return
	 */
	MeasurementToolsDetails queryByJhAndXlh(@Param("bjh")String bjh, @Param("xlh")String xlh, @Param("dprtcode")String dprtcode);

}