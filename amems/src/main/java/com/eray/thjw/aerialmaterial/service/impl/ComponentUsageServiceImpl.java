package com.eray.thjw.aerialmaterial.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.ComponentUsageMapper;
import com.eray.thjw.aerialmaterial.po.ComponentUsage;
import com.eray.thjw.aerialmaterial.service.ComponentUsageService;
import com.eray.thjw.flightdata.po.FlightRecordSheet;


@Service
public class ComponentUsageServiceImpl implements ComponentUsageService {
    @Resource
    private ComponentUsageMapper componentUsageMapper;

    /**
     * 汇总部件使用情况 
     */
	@Override
	public ComponentUsage sumComponentUsage(ComponentUsage record)
			throws RuntimeException {
		return componentUsageMapper.sumComponentUsage(record);
	}

	/**
	 * 根据飞机注册号统计所有一级子部件的部件使用情况，截止至某次飞行记录单为止
	 */
	@Override
	public List<ComponentUsage> sumComponentUsageByPlane(FlightRecordSheet sheet) {
		return componentUsageMapper.sumComponentUsageByPlane(sheet);
	}

	/**
	 * 根据飞行记录单号统计这次拆解一级子部件的部件使用情况
	 */
	@Override
	public List<ComponentUsage> sumComponentUsageByFxjldh(String fxjldh) {
		return componentUsageMapper.sumComponentUsageByFxjldh(fxjldh);
	}

	@Override
	public int insertComponentUsage(String bjh, String sn, String dprtcode) {
		return componentUsageMapper.insertComponentUsage(bjh, sn, dprtcode);
	}

	@Override
	public int deleteComponentUsage(String bjh, String sn, String dprtcode) {
		return componentUsageMapper.deleteComponentUsage(bjh, sn, dprtcode);
	}

	/**
	 * 根据飞行记录单号统计航次一对应的一级子部件的部件使用情况
	 */
	@Override
	public List<ComponentUsage> sumComponentUsageByFxjldhHc(
			Map<String, Object> paramMap) {
		return componentUsageMapper.sumComponentUsageByFxjldhHc(paramMap);
	}

}
