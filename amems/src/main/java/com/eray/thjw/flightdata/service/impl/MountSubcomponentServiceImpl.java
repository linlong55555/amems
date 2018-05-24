package com.eray.thjw.flightdata.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.flightdata.dao.MountSubcomponentMapper;
import com.eray.thjw.flightdata.po.MountSubcomponent;
import com.eray.thjw.flightdata.service.MountSubcomponentService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * 子部件关系
 * @author hanwu
 *
 */
@Service
public class MountSubcomponentServiceImpl implements MountSubcomponentService {

	@Resource
	private MountSubcomponentMapper mountSubcomponentMapper;
	
	@Resource
	private CommonRecService commonRecService;


	/**
	 * 保存子部件关系
	 */
	@Override
	public void save(List<MountSubcomponent> children, String mainid, String czls, 
			LogOperationEnum logOperationEnum, String fxjldid, String dprtcode) {
		// 删除原有子部件关系
		MountSubcomponent param = new MountSubcomponent();
		param.setMainid(mainid);
		mountSubcomponentMapper.deleteByParam(param);
		List<String> keys = new ArrayList<String>();
		keys.add(mainid);
		commonRecService.write("mainid", keys, TableEnum.B_S_00602010104, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.DELETE, fxjldid);
		if(children != null){
			// 新增子部件关系
			for (MountSubcomponent child : children) {
				child.setId(UUID.randomUUID().toString());
				child.setZt(1);
				child.setDprtcode(dprtcode);
				setDefaultValue(child);
				mountSubcomponentMapper.insertSelective(child);
				// 插入日志
				commonRecService.write(child.getId(), TableEnum.B_S_00602010104, ThreadVarUtil.getUser().getId(), czls,
						logOperationEnum, UpdateTypeEnum.SAVE, fxjldid);
			}
		}
	}

	
	/**
	 * 设置默认值
	 * @param data
	 */
	private void setDefaultValue(MountSubcomponent data){
		User user = ThreadVarUtil.getUser();
		data.setWhrid(user.getId());
		data.setWhdwid(user.getBmdm());
		data.setWhsj(new Date());
		data.setZt(EffectiveEnum.YES.getId());
		data.setTbbs(SynchronzeEnum.TO_DO.getCode());
	}


	/**
	 * 查找父节点下的所有子节点
	 */
	@Override
	public List<MountSubcomponent> findByMainid(String mainid) {
		return mountSubcomponentMapper.findByMainid(mainid);
	}
	
}
