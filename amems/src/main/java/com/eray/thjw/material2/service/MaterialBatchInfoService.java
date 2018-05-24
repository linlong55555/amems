package com.eray.thjw.material2.service;

import java.math.BigDecimal;


/**
 * @Description 物料批次信息service
 * @CreateTime 2018年3月23日 下午3:30:19
 * @CreateBy 韩武
 */
public interface MaterialBatchInfoService {
	
	/**
	 * @Description 插入或更新物料批次信息
	 * @CreateTime 2018年3月23日 下午3:32:44
	 * @CreateBy 韩武
	 * @param dprtcode	组织机构
	 * @param bjh		部件号
	 * @param xlh		序列号
	 * @param pch		批次号
	 * @param cb		成本
	 * @param cbbz		成本币种
	 * @param jz		价值
	 * @param jzbz		价值币种
	 * @return	更新的数量
	 */
	int insertOrUpdate(String dprtcode, String bjh, String xlh, String pch,
			BigDecimal cb, String cbbz, BigDecimal jz, String jzbz);
}
