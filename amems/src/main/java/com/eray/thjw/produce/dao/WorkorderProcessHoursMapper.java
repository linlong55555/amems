package com.eray.thjw.produce.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.produce.po.WorkorderProcessHours;

public interface WorkorderProcessHoursMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkorderProcessHours record);

    int insertSelective(WorkorderProcessHours record);

    WorkorderProcessHours selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkorderProcessHours record);

    int updateByPrimaryKey(WorkorderProcessHours record);

    /**
     * @Description 批量插入工序工时
     * @CreateTime 2017-10-23 下午3:29:48
     * @CreateBy 雷伟
     * @param processList
     */
	void insert4Batch(List<WorkorderProcessHours> processList);

	/**
	 * @Description 根据mainId删除记录
	 * @CreateTime 2017-10-23 下午5:03:31
	 * @CreateBy 雷伟
	 * @param mainId
	 */
	void deleteByMainid(String mainid);

	List<WorkorderProcessHours> queryListByParam(WorkorderProcessHours processHours);
}