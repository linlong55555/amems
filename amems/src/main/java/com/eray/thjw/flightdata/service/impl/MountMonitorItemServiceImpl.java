package com.eray.thjw.flightdata.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.flightdata.dao.MountMonitorItemMapper;
import com.eray.thjw.flightdata.po.MountMonitorItem;
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
 * 装上件-定检件监控项目
 * @author hanwu
 *
 */
@Service
public class MountMonitorItemServiceImpl implements MountMonitorItemService {

	@Resource
	private MountMonitorItemMapper mountMonitorItemMapper;
	
	@Resource
	private CommonRecService commonRecService;


	/**
	 * 保存装上件-定检件监控项目
	 */
	@Override
	public void save(List<MountMonitorItem> list, String mainid, String czls, 
			LogOperationEnum logOperationEnum, String fxjldid, String dprtcode) {
		// 删除原有时控件设置
		MountMonitorItem param = new MountMonitorItem();
		param.setMainid(mainid);
		mountMonitorItemMapper.deleteByParam(param);
		List<String> keys = new ArrayList<String>();
		keys.add(mainid);
		commonRecService.write("mainid", keys, TableEnum.B_S_0060201010301, ThreadVarUtil.getUser().getId(),czls,
				logOperationEnum, UpdateTypeEnum.DELETE, fxjldid);
		if(list != null){
			// 新增时控件设置
			for (MountMonitorItem item : list) {
				item.setId(UUID.randomUUID().toString());
				item.setMainid(mainid);
				item.setZt(1);
				item.setDprtcode(dprtcode);
				setDefaultValue(item);
				mountMonitorItemMapper.insertSelective(item);
				// 插入日志
				commonRecService.write(item.getId(), TableEnum.B_S_0060201010301, ThreadVarUtil.getUser().getId(), czls,
						logOperationEnum, UpdateTypeEnum.SAVE, fxjldid);
			}
		}
	}

	
	/**
	 * 设置默认值
	 * @param data
	 */
	private void setDefaultValue(MountMonitorItem data){
		User user = ThreadVarUtil.getUser();
		data.setWhrid(user.getId());
		data.setWhdwid(user.getBmdm());
		data.setWhsj(new Date());
		data.setZt(EffectiveEnum.YES.getId());
		data.setTbbs(SynchronzeEnum.TO_DO.getCode());
	}
	
}
