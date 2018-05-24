package com.eray.thjw.flightdata.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.flightdata.dao.MountTimeMonitorMapper;
import com.eray.thjw.flightdata.po.MountTimeMonitor;
import com.eray.thjw.flightdata.service.MountTimeMonitorService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * 装上件-时控件设置
 * @author hanwu
 *
 */
@Service
public class MountTimeMonitorServiceImpl implements MountTimeMonitorService {

	@Resource
	private MountTimeMonitorMapper mountTimeMonitorMapper;
	
	@Resource
	private CommonRecService commonRecService;


	/**
	 * 保存装上件-时控件设置
	 */
	@Override
	public void save(List<MountTimeMonitor> settings, String mainid, String czls, 
			LogOperationEnum logOperationEnum, String fxjldid, String dprtcode) {
		// 删除原有时控件设置
		delete(mainid, czls, logOperationEnum, fxjldid);
		// 新增时控件设置
		if(settings != null){
			for (MountTimeMonitor setting : settings) {
				setting.setId(UUID.randomUUID().toString());
				setting.setZt(1);
				setting.setDprtcode(dprtcode);
				setDefaultValue(setting);
				mountTimeMonitorMapper.insertSelective(setting);
				// 插入日志
				commonRecService.write(setting.getId(), TableEnum.B_S_00602010101, ThreadVarUtil.getUser().getId(), czls,
						logOperationEnum, UpdateTypeEnum.SAVE, fxjldid);
			}
		}
	}
	
	/**
	 * 删除时控件设置-编辑区
	 */
	private void delete(String mainid, String czls, LogOperationEnum logOperationEnum, String fxjldid) {
		MountTimeMonitor param = new MountTimeMonitor();
		param.setMainid(mainid);
		mountTimeMonitorMapper.deleteByParam(param);
		// 插入日志
		List<String> keys = new ArrayList<String>();
		keys.add(mainid);
		commonRecService.write("mainid", keys, TableEnum.B_S_00602010101, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.DELETE, fxjldid);
	}

	/**
	 * 设置默认值
	 * @param data
	 */
	private void setDefaultValue(MountTimeMonitor data){
		User user = ThreadVarUtil.getUser();
		data.setWhrid(user.getId());
		data.setWhdwid(user.getBmdm());
		data.setWhsj(new Date());
		data.setZt(EffectiveEnum.YES.getId());
		data.setTbbs(SynchronzeEnum.TO_DO.getCode());
	}

	/**
	 * 根据装上件id查找对应的时控件设置
	 */
	@Override
	public List<MountTimeMonitor> findByMainid(String mainid) {
		return mountTimeMonitorMapper.findByMainid(mainid);
	}
	
}
