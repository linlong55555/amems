package com.eray.thjw.basic.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.basic.dao.ZoneStationMapper;
import com.eray.thjw.basic.po.ZoneStation;
import com.eray.thjw.basic.service.ZoneStationService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.ZoneStationEnum;
import enu.common.EffectiveEnum;

/**
 * @Description 区域和飞机站位业务逻辑实现类
 * @CreateTime 2017年8月18日 下午3:19:16
 * @CreateBy 徐勇
 */
@Service
public class ZoneStationServiceImpl implements ZoneStationService {

	@Resource
	ZoneStationMapper zoneStationMapper;

	@Resource
	private CommonRecService commonRecService;

	/**
	 * 
	 * @Description 区域和飞机站位共用的查询操作，获取列表，分页
	 * @CreateTime 2017年8月25日 下午5:12:24
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return Map<String, Object>
	 */
	private Map<String, Object> getZoneStationList(ZoneStation zoneStation) {
		PageHelper.startPage(zoneStation.getPagination());
		List<ZoneStation> list = zoneStationMapper.getZoneStationList(zoneStation);
		return PageUtil.pack4PageHelper(list, zoneStation.getPagination());
	}

	/**
	 * 
	 * @Description 根据机型和组织机构查询数据（弹框）
	 * @CreateTime 2017-8-22 上午10:33:06
	 * @CreateBy 孙霁
	 * @param position
	 * @return Map<String , Object>
	 */
	@Override
	public Map<String, Object> selectByJx(ZoneStation zoneStation) {
		PageHelper.startPage(zoneStation.getPagination());
		List<ZoneStation> list = zoneStationMapper.selectByJx(zoneStation);
		return PageUtil.pack4PageHelper(list, zoneStation.getPagination());
	}

	/**
	 * @Description 获取区域集合（无分页）
	 * @CreateTime 2017年8月24日 下午3:26:53
	 * @CreateBy 韩武
	 * @param zoneStation
	 * @return
	 */
	@Override
	public List<ZoneStation> getZoneList(ZoneStation zoneStation) {
		return zoneStationMapper.selectByJx(zoneStation);
	}

	/**
	 * 
	 * @Description 区域和飞机站位共用的添加方法
	 * @CreateTime 2017年8月26日 下午5:47:36
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return boolean
	 * @throws BusinessException
	 */
	private boolean add(ZoneStation zoneStation) {
		int count = zoneStationMapper.getCount(zoneStation);
		if (count == 0) {
			User user = ThreadVarUtil.getUser();
			String id = UUID.randomUUID().toString();
			String czls = UUID.randomUUID().toString();
			zoneStation.setId(id);
			zoneStation.setWhrid(user.getId());
			zoneStation.setWhdwid(user.getJgdm());
			zoneStation.setZt(EffectiveEnum.YES.getId());
			zoneStationMapper.insertSelective(zoneStation);
			commonRecService.write(id, TableEnum.D_023, user.getId(), czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE, id);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Description 区域和飞机站位共用的更新方法
	 * @CreateTime 2017年8月26日 下午5:47:36
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return boolean
	 * @throws BusinessException
	 */
	private void update(ZoneStation zoneStation)  {
			User user = ThreadVarUtil.getUser();
			String czls = UUID.randomUUID().toString();
			zoneStation.setWhrid(user.getId());
			zoneStation.setWhdwid(user.getJgdm());
			zoneStationMapper.updateByPrimaryKeySelective(zoneStation);
			commonRecService.write(zoneStation.getId(), TableEnum.D_023, user.getId(), czls, LogOperationEnum.EDIT,
					UpdateTypeEnum.UPDATE, zoneStation.getId());
	}

	/**
	 * 
	 * @Description 删除操作，逻辑删除，将状态改为无效
	 * @CreateTime 2017年8月25日 下午5:12:46
	 * @CreateBy 李高升
	 * @param id
	 */
	@Override
	public void deleteById(String id) {
		ZoneStation station = new ZoneStation();
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		station.setId(id);
		station.setWhrid(user.getId());
		station.setWhdwid(user.getJgdm());
		station.setZt(EffectiveEnum.NO.getId());
		zoneStationMapper.updateByPrimaryKeySelective(station);
		commonRecService.write(id, TableEnum.D_023, user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,
				id);

	}

	/**
	 * 
	 * @Description 添加区域
	 * @CreateTime 2017年8月26日 下午5:18:15
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @throws BusinessException
	 */
	@Override
	public void addZone(ZoneStation zoneStation) throws BusinessException {
		zoneStation.setLx(ZoneStationEnum.ZONE.getId());
		if (!add(zoneStation)) {
			throw new BusinessException("该区域已存在!");
		} 
	}

	/**
	 * 
	 * @Description 更新区域
	 * @CreateTime 2017年8月26日 下午5:17:27
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @throws BusinessException
	 */
	@Override
	public void updateZone(ZoneStation zoneStation) {
		zoneStation.setLx(ZoneStationEnum.ZONE.getId());
		update(zoneStation);
	}

	/**
	 * 
	 * @Description 添加飞机站位
	 * @CreateTime 2017年8月26日 下午5:34:39
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public void addStation(ZoneStation zoneStation) throws BusinessException {
		zoneStation.setLx(ZoneStationEnum.STATION.getId());
		if (!add(zoneStation)) {
			throw new BusinessException("该飞机站位已存在!");
		} 
	}

	/**
	 * 
	 * @Description 更新飞机站位
	 * @CreateTime 2017年8月26日 下午5:44:51
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @throws BusinessException
	 */
	@Override
	public void updateStation(ZoneStation zoneStation)  {
		zoneStation.setLx(ZoneStationEnum.STATION.getId());
		update(zoneStation);
	}
	
	/**
	 * 
	 * @Description 获取区域列表
	 * @CreateTime 2017年8月26日 下午6:30:54
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> getZones(ZoneStation zoneStation) {	
		zoneStation.setLx(ZoneStationEnum.ZONE.getId());
		return getZoneStationList(zoneStation);
	}
	
	
	/**
	 * 
	 * @Description 获取飞机站位列表
	 * @CreateTime 2017年8月26日 下午6:28:40
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> getStations(ZoneStation zoneStation) {	
		zoneStation.setLx(ZoneStationEnum.STATION.getId());
		return getZoneStationList(zoneStation);
	}

}
