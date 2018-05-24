package com.eray.thjw.productionplan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.PlaneModelDataMapper;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.PlaneDailyUsageMapper;
import com.eray.thjw.productionplan.dao.PlaneDataMapper;
import com.eray.thjw.productionplan.po.PlaneDailyUsage;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.service.PlaneDailyUsageService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;


@Service
public class PlaneDailyUsageServiceImpl implements PlaneDailyUsageService {
	
	@Resource
	private PlaneModelDataMapper planeModelDataMapper;
	
	@Resource
	private PlaneDailyUsageMapper planeDailyUsageMapper;
	
	@Resource
	private PlaneDataMapper planeDataMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	/**
	 * 新增飞机日使用量
	 */
	@Override
	public void add(PlaneData pd, String czls, LogOperationEnum logOperationEnum) throws RuntimeException {
		// 查询机型
		PlaneModelData param = new PlaneModelData();
		pd = planeDataMapper.selectByPrimaryKey(pd);
		param.setFjjx(pd.getFjjx());
		param.setDprtcode(pd.getDprtcode());
		PlaneModelData modelData = planeModelDataMapper.selectPlaneModelData(param);
		// 保存飞机日使用量数据
		PlaneDailyUsage dailyUsage = new PlaneDailyUsage();
		dailyUsage.setFjzch(pd.getFjzch());
		dailyUsage.setDprtcode(ThreadVarUtil.getUser().getJgdm());
		dailyUsage.setrJsfxsj(modelData.getrJsfxsj());
		dailyUsage.setrSsdsj(modelData.getrSsdsj());
		dailyUsage.setrJcsj(modelData.getrJcsj());
		dailyUsage.setrQljxh(modelData.getrQljxh());
		dailyUsage.setrJcxh(modelData.getrJcxh());
		dailyUsage.setrWdgxh(modelData.getrWdgxh());
		dailyUsage.setrSsdxh(modelData.getrSsdxh());
		dailyUsage.setrN1(modelData.getrN1());
		dailyUsage.setrN2(modelData.getrN2());
		dailyUsage.setrN3(modelData.getrN3());
		dailyUsage.setrN4(modelData.getrN4());
		dailyUsage.setrN5(modelData.getrN5());
		dailyUsage.setrN6(modelData.getrN6());
		dailyUsage.setrTsjk1(modelData.getrTsjk1());
		dailyUsage.setrTsjk2(modelData.getrTsjk2());
		dailyUsage.setWhrid(ThreadVarUtil.getUser().getId());
		dailyUsage.setDprtcode(pd.getDprtcode());
		dailyUsage.setWhsj(new Date());
		planeDailyUsageMapper.insertSelective(dailyUsage);
		// 插入日志
		commonRecService.writeByWhere(" b.fjzch = '"+pd.getFjzch().replaceAll("'", "''")+"' and b.dprtcode = '"+pd.getDprtcode()+"'"
				, TableEnum.D_00702, ThreadVarUtil.getUser().getId(), czls, logOperationEnum, UpdateTypeEnum.SAVE
				, pd.getFjzch().concat(",").concat(pd.getDprtcode()));
	}


	/**
	 * 修改飞机日使用量
	 */
	@Override
	public void edit(PlaneData pd, String czls, LogOperationEnum logOperationEnum) throws RuntimeException {
		User user = ThreadVarUtil.getUser();
		// 查询机型
		PlaneModelData param = new PlaneModelData();
//		pd.setDprtcode(ThreadVarUtil.getUser().getJgdm());
		pd = planeDataMapper.selectByPrimaryKey(pd);
		param.setFjjx(pd.getFjjx());
		param.setDprtcode(pd.getDprtcode());
		PlaneModelData modelData = planeModelDataMapper.selectPlaneModelData(param);
		// 保存飞机日使用量数据
		PlaneDailyUsage dailyUsage = new PlaneDailyUsage();
		dailyUsage.setFjzch(pd.getFjzch());
		dailyUsage.setrJsfxsj(modelData.getrJsfxsj());
		dailyUsage.setrSsdsj(modelData.getrSsdsj());
		dailyUsage.setrJcsj(modelData.getrJcsj());
		dailyUsage.setrQljxh(modelData.getrQljxh());
		dailyUsage.setrJcxh(modelData.getrJcxh());
		dailyUsage.setrWdgxh(modelData.getrWdgxh());
		dailyUsage.setrSsdxh(modelData.getrSsdxh());
		dailyUsage.setrN1(modelData.getrN1());
		dailyUsage.setrN2(modelData.getrN2());
		dailyUsage.setrN3(modelData.getrN3());
		dailyUsage.setrN4(modelData.getrN4());
		dailyUsage.setrN5(modelData.getrN5());
		dailyUsage.setrN6(modelData.getrN6());
		dailyUsage.setrTsjk1(modelData.getrTsjk1());
		dailyUsage.setrTsjk2(modelData.getrTsjk2());
		dailyUsage.setWhrid(ThreadVarUtil.getUser().getId());
		dailyUsage.setWhsj(new Date());
		dailyUsage.setDprtcode(pd.getDprtcode());
		planeDailyUsageMapper.updateByPrimaryKeySelective(dailyUsage);
		//插入日志
		List<String> keys = new ArrayList<String>();
		keys.add(pd.getFjzch());
		commonRecService.write("fjzch", keys, TableEnum.D_00702, user.getId(), 
				czls, logOperationEnum, UpdateTypeEnum.UPDATE, pd.getFjzch().concat(",").concat(pd.getDprtcode()));
		// 插入日志
		commonRecService.writeByWhere(" b.fjzch = '"+pd.getFjzch().replaceAll("'", "''")+"' and b.dprtcode = '"+pd.getDprtcode()+"'"
				, TableEnum.D_00702, ThreadVarUtil.getUser().getId(), czls, logOperationEnum, UpdateTypeEnum.UPDATE
				, pd.getFjzch().concat(",").concat(pd.getDprtcode()));
	}


	/**
	 * 删除飞机日使用量
	 */
	@Override
	public void delete(PlaneData pd, String czls, LogOperationEnum logOperationEnum) {
		// 插入日志
		commonRecService.writeByWhere(" b.fjzch = '"+pd.getFjzch().replaceAll("'", "''")+"' and b.dprtcode = '"+pd.getDprtcode()+"'"
				, TableEnum.D_00702, ThreadVarUtil.getUser().getId(), czls, logOperationEnum, UpdateTypeEnum.DELETE
				, pd.getFjzch().concat(",").concat(pd.getDprtcode()));
		// 删除
		planeDailyUsageMapper.deleteByPrimaryKey(pd);
	}

}
