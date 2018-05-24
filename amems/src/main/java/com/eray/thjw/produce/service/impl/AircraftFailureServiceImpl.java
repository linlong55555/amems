package com.eray.thjw.produce.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.produce.dao.FlightSheetFinishedWorkMapper;
import com.eray.thjw.produce.po.AircraftFailure;
import com.eray.thjw.produce.service.AircraftFailureService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

/** 
 * @Description 飞机故障履历服务实现类
 * @CreateTime 2018年5月22日 上午10:13:39
 * @CreateBy 韩武	
 */
@Service
public class AircraftFailureServiceImpl implements AircraftFailureService {
	
	@Resource
	private FlightSheetFinishedWorkMapper flightSheetFinishedWorkMapper;

	/**
	 * @Description 查询飞机故障履历分页列表
	 * @CreateTime 2018年5月22日 下午2:00:50
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public Map<String, Object> queryPageableList(AircraftFailure record) {
		PageHelper.startPage(record.getPagination());
		List<AircraftFailure> list = flightSheetFinishedWorkMapper.queryAircraftFailure(record);
		return PageUtil.pack4PageHelper(list, record.getPagination());
	}

	/**
	 * @Description 查询飞机故障履历列表
	 * @CreateTime 2018年5月22日 上午10:12:52
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public List<AircraftFailure> queryList(AircraftFailure record) {
		return flightSheetFinishedWorkMapper.queryAircraftFailure(record);
	}

}
