package com.eray.thjw.aerialmaterial.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.ComponentCertificate;
import com.eray.thjw.produce.po.InstallationListEditable;

/**
 * @Description 部件证书service
 * @CreateTime 2017年9月28日 上午10:00:36
 * @CreateBy 韩武
 */
public interface ComponentCertificateService {
	
	
	/**
	 * @Description 查询部件证书集合
	 * @CreateTime 2017年9月28日 上午10:01:00
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	List<ComponentCertificate> queryList(ComponentCertificate record);
	
	/**
	 * @Description 保存部件证书
	 * @CreateTime 2017年9月28日 下午5:03:51
	 * @CreateBy 韩武
	 * @param record
	 */
	void save(InstallationListEditable record);
	
	/**
	 * @Description 删除部件证书
	 * @CreateTime 2017年9月28日 下午5:04:34
	 * @CreateBy 韩武
	 * @param record
	 */
	void delete(InstallationListEditable record);
	
	/**
	 * 
	 * @Descriptiont 添加证书
	 * @CreateTime 2017-11-14 上午9:57:56
	 * @CreateBy 孙霁
	 * @param record
	 */
	void add(InstallationListEditable record);
	

	/**
	 * 
	 * @Description 根据条件查询数据
	 * @CreateTime 2018-3-19 下午4:57:32
	 * @CreateBy 孙霁
	 * @param record
	 */
	List<ComponentCertificate> selectByParams(ComponentCertificate record);
	
}
