package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.produce.po.AircraftFailure;

/** 
 * @Description 飞机故障履历服务接口
 * @CreateTime 2018年5月22日 上午10:11:56
 * @CreateBy 韩武	
 */
public interface AircraftFailureService {
	
	/**
	 * @Description 查询飞机故障履历列表
	 * @CreateTime 2018年5月22日 上午10:12:52
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	List<AircraftFailure> queryList(AircraftFailure record);
	
	/**
	 * @Description 查询飞机故障履历分页列表
	 * @CreateTime 2018年5月22日 下午2:00:50
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	Map<String, Object> queryPageableList(AircraftFailure record);
}
