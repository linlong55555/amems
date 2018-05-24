package com.eray.thjw.produce.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.ComponentCertificateMapper;
import com.eray.thjw.aerialmaterial.po.ComponentCertificate;
import com.eray.thjw.produce.dao.FlightSheetVoyageMapper;
import com.eray.thjw.produce.dao.InstallationListEffectiveMapper;
import com.eray.thjw.produce.po.FlightSheetVoyage;
import com.eray.thjw.produce.po.InstallationListEffective;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.service.ComponenthistoryService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

/** 
 * @Description 
 * @CreateTime 2017-10-18 下午2:14:00
 * @CreateBy 孙霁	
 */
@Service
public class ComponenthistoryServiceImpl implements ComponenthistoryService{

	@Resource
	private InstallationListEffectiveMapper installationListEffectiveMapper;
	@Resource
	private ComponentCertificateMapper componentCertificateMapper;
	@Resource
	private FlightSheetVoyageMapper flightSheetVoyageMapper;
	/**
	 * @Description 根据条件查询部件履历
	 * @CreateTime 2017-10-18 下午2:29:07
	 * @CreateBy 孙霁
	 * @param installationListEffective
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryAll(
			InstallationListEffective installationListEffective) {
		PageHelper.startPage(installationListEffective.getPagination());
		List<InstallationListEffective> list = installationListEffectiveMapper.queryComponenthistoryList(installationListEffective);
		return PageUtil.pack4PageHelper(list, installationListEffective.getPagination());
	}

	/**
	 * 
	 * @Description 根据条件查询证书信息
	 * @CreateTime 2017-10-19 上午11:12:17
	 * @CreateBy 孙霁
	 * @param componentCertificate
	 * @return
	 */
	@Override
	public List<ComponentCertificate> queryCertificateList(
			ComponentCertificate componentCertificate) {
		return componentCertificateMapper.queryList(componentCertificate);
	}

	/**
	 * 
	 * @Description 查询基本信息
	 * @CreateTime 2017-10-20 下午2:17:18
	 * @CreateBy 孙霁
	 * @param flightSheetVoyage
	 * @return
	 */
	@Override
	public Map<String, Object> queryBj(FlightSheetVoyage flightSheetVoyage) {
		Map<String, Object> map = new HashMap<String, Object>();
		//查询基本数据
		map.put("row", flightSheetVoyageMapper.queryBj(flightSheetVoyage));
		//查询数据列表
		InstallationListEffective installationListEffective = new InstallationListEffective();
		installationListEffective.setParamsMap(flightSheetVoyage.getParamsMap());
		map.put("rows", installationListEffectiveMapper.queryAllBj(installationListEffective));
		return map;
	}

	/**
	 * @Description 根据条件查询物料履历
	 * @CreateTime 2018-3-26 下午3:22:15
	 * @CreateBy 雷伟
	 * @param installationListEffective
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryMaterialAll(
			InstallationListEffective installationListEffective) {
		PageHelper.startPage(installationListEffective.getPagination());
		List<InstallationListEffective> list = installationListEffectiveMapper.queryMaterialhistoryList(installationListEffective);
		return PageUtil.pack4PageHelper(list, installationListEffective.getPagination());
	}

	/**
     * @Description 查询物料履历
     * @CreateTime 2018-3-26 下午3:23:01
     * @CreateBy 雷伟
     * @param record
     */
	@Override
	public Map<String, Object> queryMaterialBj(FlightSheetVoyage flightSheetVoyage) {
		Map<String, Object> map = new HashMap<String, Object>();
		//查询基本数据
		map.put("row", flightSheetVoyageMapper.queryMaterialBj(flightSheetVoyage));
		//查询数据列表
		InstallationListEffective installationListEffective = new InstallationListEffective();
		installationListEffective.setParamsMap(flightSheetVoyage.getParamsMap());
		map.put("rows", installationListEffectiveMapper.queryMaterialAllBj(installationListEffective));
		return map;
	}
}
