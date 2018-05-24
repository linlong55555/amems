package com.eray.thjw.flightdata.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.flightdata.dao.MountSpecialConditionMapper;
import com.eray.thjw.flightdata.po.MountSpecialCondition;
import com.eray.thjw.flightdata.service.MountSpecialConditionService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * 特殊情况设置
 * @author hanwu
 *
 */
@Service
public class MountSpecialConditionServiceImpl implements MountSpecialConditionService {

	@Resource
	private MountSpecialConditionMapper mountSpecialConditionMapper;
	
	@Resource
	private CommonRecService commonRecService;


	/**
	 * 保存特殊情况设置
	 */
	@Override
	public void save(List<MountSpecialCondition> conditions, String mainid, String czls, 
			LogOperationEnum logOperationEnum, String fxjldid, String dprtcode) {
		// 删除原有时控件设置
		MountSpecialCondition param = new MountSpecialCondition();
		param.setMainid(mainid);
		mountSpecialConditionMapper.deleteByParam(param);
		List<String> keys = new ArrayList<String>();
		keys.add(mainid);
		commonRecService.write("mainid", keys, TableEnum.B_S_00602010102, ThreadVarUtil.getUser().getId(), czls,
				logOperationEnum, UpdateTypeEnum.DELETE, fxjldid);
		if(conditions != null){
			// 新增时控件设置
			for (MountSpecialCondition condition : conditions) {
				condition.setId(UUID.randomUUID().toString());
				condition.setZt(1);
				condition.setDprtcode(dprtcode);
				setDefaultValue(condition);
				mountSpecialConditionMapper.insertSelective(condition);
				// 插入日志
				commonRecService.write(condition.getId(), TableEnum.B_S_00602010102, ThreadVarUtil.getUser().getId(), czls,
						logOperationEnum, UpdateTypeEnum.SAVE, fxjldid);
			}
		}
	}

	
	/**
	 * 设置默认值
	 * @param data
	 */
	private void setDefaultValue(MountSpecialCondition data){
		User user = ThreadVarUtil.getUser();
		data.setWhrid(user.getId());
		data.setWhdwid(user.getBmdm());
		data.setWhsj(new Date());
		data.setZt(EffectiveEnum.YES.getId());
		data.setTbbs(SynchronzeEnum.TO_DO.getCode());
	}

	/**
	 * 根据装上件id查询对应的时控件特殊飞行情况
	 */
	@Override
	public List<MountSpecialCondition> findByMainid(String mainid) {
		return mountSpecialConditionMapper.findByMainid(mainid);
	}
	
}
