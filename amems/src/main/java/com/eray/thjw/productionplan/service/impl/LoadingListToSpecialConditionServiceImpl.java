package com.eray.thjw.productionplan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.LoadingListToSpecialConditionEditableMapper;
import com.eray.thjw.productionplan.po.LoadingListToSpecialCondition;
import com.eray.thjw.productionplan.service.LoadingListToSpecialConditionService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;


@Service
public class LoadingListToSpecialConditionServiceImpl implements LoadingListToSpecialConditionService{
	
	@Resource
	private LoadingListToSpecialConditionEditableMapper loadingListToSpecialConditionEditableMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * 根据条件查询
	 */
	@Override
	public List<LoadingListToSpecialCondition> select(
			LoadingListToSpecialCondition con) throws RuntimeException {
		return loadingListToSpecialConditionEditableMapper.select(con);
	}

	/**
	 * 保存时控件特殊飞行情况
	 */
	@Override
	public void saveEditable(List<LoadingListToSpecialCondition> conditions, String zjqdid, 
			 String czls, LogOperationEnum logOperationEnum) throws RuntimeException {
		User user = ThreadVarUtil.getUser();
		// 删除原有时控件设置
		LoadingListToSpecialCondition param = new LoadingListToSpecialCondition();
		param.setZjqdid(zjqdid);
		loadingListToSpecialConditionEditableMapper.deleteByParam(param);
		List<String> keys = new ArrayList<String>();
		keys.add(zjqdid);
		commonRecService.write("zjqdid", keys, TableEnum.B_S_00102, user.getId(), 
				czls, logOperationEnum, UpdateTypeEnum.DELETE, zjqdid);
		// 新增时控件设置
		for (LoadingListToSpecialCondition condition : conditions) {
			condition.setId(UUID.randomUUID().toString());
			condition.setZt(1);
			setDefaultValue(condition);
			loadingListToSpecialConditionEditableMapper.insertSelective(condition);
			commonRecService.write(condition.getId(), TableEnum.B_S_00102, user.getId(), 
					czls, logOperationEnum, UpdateTypeEnum.SAVE, zjqdid);
		}
	}
	
	/**
	 * 设置默认值
	 * @param ll
	 */
	private void setDefaultValue(LoadingListToSpecialCondition condition){
		User user = ThreadVarUtil.getUser();
		// 待同步
		condition.setTbbs(SynchronzeEnum.TO_DO.getCode());
		condition.setWhrid(user.getId());
		condition.setWhdwid(user.getBmdm());
		condition.setWhsj(new Date());
		condition.setDprtcode(user.getJgdm());
	}

}
