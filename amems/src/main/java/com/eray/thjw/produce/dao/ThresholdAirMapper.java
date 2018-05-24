package com.eray.thjw.produce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.ThresholdAir;

/**
 * 
 * @Description 系统阀值设置-飞机mapper
 * @CreateTime 2017年9月19日 下午3:17:45
 * @CreateBy 林龙
 */
public interface ThresholdAirMapper {
	
	void insertSelective(ThresholdAir thresholdAir);

	ThresholdAir selectByPrimaryKey(ThresholdAir thresholdAir);

    void updateByPrimaryKeySelective(ThresholdAir thresholdAir);
	/**
	 * 
	 * @Description 根据查询系统阀值设置-飞机数据
	 * @CreateTime 2017年9月19日 下午3:20:35
	 * @CreateBy 林龙
	 * @param thresholdAir 
	 * @return  系统阀值设置-飞机数据list集合
	 * @throws BusinessException
	 */
	 List<ThresholdAir> queryMonitorSettingByfjzch(ThresholdAir thresholdAir);
	 
	 /**
     * @Description 查询系统阀值设置-飞机数据
     * @CreateTime 2017-11-1 下午3:31:16
     * @CreateBy 刘兵
     * @param fjzch 飞机注册号
     * @param dprtcode 机构代码
     * @return List<PlanUsage> 日使用量集合
     */
    List<ThresholdAir> queryByFjzch(@Param("fjzch")String fjzch, @Param("dprtcode")String dprtcode);
	
}
