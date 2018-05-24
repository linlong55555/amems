package com.eray.thjw.flightdata.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.flightdata.dao.MountFixedMonitorMapper;
import com.eray.thjw.flightdata.po.MountFixedMonitor;
import com.eray.thjw.flightdata.service.MountFixedMonitorService;
import com.eray.thjw.flightdata.service.MountMonitorItemService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * 装上件-定检件定检项目
 * @author hanwu
 *
 */
@Service
public class MountFixedMonitorServiceImpl implements MountFixedMonitorService {

	@Resource
	private MountFixedMonitorMapper mountFixedMonitorMapper;
	
	@Resource
	private MountMonitorItemService mountMonitorItemService;
	
	@Resource
	private CommonRecService commonRecService;


	/**
	 * 保存装上件-定检件定检项目
	 */
	@Override
	public void save(List<MountFixedMonitor> list, String czls, 
			LogOperationEnum logOperationEnum, String fxjldid, String dprtcode) {
		
		for (MountFixedMonitor data : list) {
			// 当前操作   1：新增   2.修改 
			UpdateTypeEnum operation = UpdateTypeEnum.UPDATE;
			// 生成id
			if(StringUtils.isBlank(data.getId())){
				data.setId(UUID.randomUUID().toString());
				operation = UpdateTypeEnum.SAVE;
			}
			data.setDprtcode(dprtcode);
			// 设置默认值
			setDefaultValue(data);
			// 保存记录
			mountFixedMonitorMapper.save(data);
			// 插入日志
			commonRecService.write(data.getId(), TableEnum.B_S_00602010103, ThreadVarUtil.getUser().getId(), czls,
					logOperationEnum, operation, data.getFxjldid());
			// 保存定检监控项目
			mountMonitorItemService.save(data.getMonitorItemList(), data.getId(), czls, logOperationEnum, data.getFxjldid(), data.getDprtcode());
		}
	}
	
	/**
	 * 设置默认值
	 * @param data
	 */
	private void setDefaultValue(MountFixedMonitor data){
		User user = ThreadVarUtil.getUser();
		data.setWhrid(user.getId());
		data.setWhdwid(user.getBmdm());
		data.setWhsj(new Date());
		data.setZt(EffectiveEnum.YES.getId());
		data.setTbbs(SynchronzeEnum.TO_DO.getCode());
	}

	/**
	 * 根据装上件id查询对应的定检件定检项目
	 */
	@Override
	public List<MountFixedMonitor> findByMainid(String mainid) {
		return mountFixedMonitorMapper.findByMainid(mainid);
	}
	
}
