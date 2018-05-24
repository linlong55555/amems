package com.eray.thjw.produce.dao;

import java.util.List;

import com.eray.thjw.produce.po.Workorder145Hours;
/**
 * 
 * @Description 145工单工序工时mapper
 * @CreateTime 2017年10月9日 下午6:58:26
 * @CreateBy 岳彬彬
 */
public interface Workorder145HoursMapper {
	int deleteByPrimaryKey(String id);

	int insert(Workorder145Hours record);

	int insertSelective(Workorder145Hours record);

	Workorder145Hours selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Workorder145Hours record);

	int updateByPrimaryKey(Workorder145Hours record);
	/**
	 * 
	 * @Description 批量新增145工单下工序工时
	 * @CreateTime 2017年10月9日 下午6:58:06
	 * @CreateBy 岳彬彬
	 * @param list
	 */
	void insertWorkorder145HoursList(List<Workorder145Hours> list);
}