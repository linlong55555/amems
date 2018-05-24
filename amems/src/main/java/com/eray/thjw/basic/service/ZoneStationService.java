package com.eray.thjw.basic.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.basic.po.ZoneStation;
import com.eray.thjw.exception.BusinessException;

/**
 * @Description 区域和飞机站位业务逻辑接口
 * @CreateTime 2017年8月18日 下午3:19:16
 * @CreateBy 徐勇
 */
public interface ZoneStationService {


	/**
	 * 
	 * @Description 根据机型和组织机构查询数据
	 * @CreateTime 2017-8-22 上午10:27:32
	 * @CreateBy 孙霁
	 * @param record
	 * @return Map<String , Object>
	 */
	Map<String, Object> selectByJx(ZoneStation record);

	/**
	 * @Description 获取区域集合（无分页）
	 * @CreateTime 2017年8月24日 下午3:26:53
	 * @CreateBy 韩武
	 * @param zoneStation
	 * @return
	 */
	List<ZoneStation> getZoneList(ZoneStation zoneStation);

	/**
	 * 
	 * @Description 删除操作
	 * @CreateTime 2017年8月25日 下午5:12:46
	 * @CreateBy 李高升
	 * @param id
	 */
	void deleteById(String id);

	/**
	 * 
	 * @Description 添加区域
	 * @CreateTime 2017年8月26日 下午5:18:15
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @throws BusinessException
	 */
	void addZone(ZoneStation zoneStation) throws BusinessException;

	/**
	 * 
	 * @Description 更新区域
	 * @CreateTime 2017年8月26日 下午5:17:27
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @throws BusinessException
	 */
	void updateZone(ZoneStation zoneStation);

	/**
	 * 
	 * @Description 添加飞机站位
	 * @CreateTime 2017年8月26日 下午5:34:39
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @throws BusinessException
	 */
	void addStation(ZoneStation zoneStation) throws BusinessException;

	/**
	 * 
	 * @Description 更新飞机站位
	 * @CreateTime 2017年8月26日 下午5:44:51
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @throws BusinessException
	 */
	void updateStation(ZoneStation zoneStation);

	/**
	 * 
	 * @Description 获取区域列表
	 * @CreateTime 2017年8月26日 下午6:28:40
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return
	 */
	Map<String, Object> getZones(ZoneStation zoneStation);

	/**
	 * 
	 * @Description 获取飞机站位列表
	 * @CreateTime 2017年8月26日 下午6:30:54
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return
	 */
	Map<String, Object> getStations(ZoneStation zoneStation);

}
