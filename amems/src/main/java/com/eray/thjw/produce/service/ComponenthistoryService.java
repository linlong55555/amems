package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ComponentCertificate;
import com.eray.thjw.produce.po.FlightSheetVoyage;
import com.eray.thjw.produce.po.InstallationListEffective;
import com.eray.thjw.produce.po.Workorder;

/** 
 * @Description 
 * @CreateTime 2017-10-18 下午2:13:46
 * @CreateBy 孙霁	
 */
public interface ComponenthistoryService {

	/**
	 * 
	 * @Description 根据条件查询部件履历
	 * @CreateTime 2017-10-18 下午2:29:07
	 * @CreateBy 孙霁
	 * @param installationListEffective
	 * @return Map<String, Object>
	 */
	public Map<String, Object> queryAll(InstallationListEffective installationListEffective);
	
	/**
	 * 
	 * @Description 根据条件查询证书信息
	 * @CreateTime 2017-10-19 上午11:12:17
	 * @CreateBy 孙霁
	 * @param componentCertificate
	 * @return
	 */
	public List<ComponentCertificate> queryCertificateList(ComponentCertificate componentCertificate);
	
	/**
	 * 
	 * @Description 查询基本信息
	 * @CreateTime 2017-10-20 下午2:17:18
	 * @CreateBy 孙霁
	 * @param flightSheetVoyage
	 * @return
	 */
	public Map<String, Object> queryBj(FlightSheetVoyage flightSheetVoyage);

	
	/**
	 * @Description  根据条件查询物料履历
	 * @CreateTime 2018-3-26 下午3:20:14
	 * @CreateBy 雷伟
	 * @param installationListEffective
	 * @return
	 */
	public Map<String, Object> queryMaterialAll(InstallationListEffective installationListEffective);

	/**
	 * @Description 查询物料基本信息
	 * @CreateTime 2018-3-26 下午4:02:23
	 * @CreateBy 雷伟
	 * @param flightSheetVoyage
	 * @return
	 */
	public Map<String, Object> queryMaterialBj(FlightSheetVoyage flightSheetVoyage);
}
