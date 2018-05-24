package com.eray.thjw.produce.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.ThresholdAir;




/**
 * @Description 系统阀值设置-飞机service
 * @CreateTime 2017年9月19日 下午3:17:45
 * @CreateBy 林龙
 */
public interface ThresholdAirService {

	/**
	 * @Description 根据查询系统阀值设置-飞机数据
	 * @CreateTime 2017年9月19日 下午3:20:35
	 * @CreateBy 林龙
	 * @param thresholdAir 
	 * @return  系统阀值设置-飞机数据list集合
	 * @throws BusinessException
	 */
	public List<ThresholdAir> queryMonitorSettingByfjzch(ThresholdAir thresholdAir)throws BusinessException;

	/**
	 * @Description 批量保存监控设置
	 * @CreateTime 2017年9月19日 下午5:09:51
	 * @CreateBy 林龙
	 * @param thresholdAir
	 * @return
	 * @throws BusinessException
	 */
	public String save(ThresholdAir thresholdAir)throws BusinessException;
}
