package com.eray.thjw.produce.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.PlanUsageMapper;
import com.eray.thjw.produce.po.PlanInit;
import com.eray.thjw.produce.po.PlanUsage;
import com.eray.thjw.produce.service.PlanUsageService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/** 
 * @Description 
 * @CreateTime 2017-9-21 下午2:01:07
 * @CreateBy 孙霁	
 */
@Service("PlanUsageService")
public class PlanUsageServiceImpl implements PlanUsageService{

	@Resource
	private PlanUsageMapper planUsageMapper;
	@Resource
	private CommonRecService commonRecService;
	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2017-9-21 下午2:54:21
	 * @CreateBy 孙霁
	 * @param PlanUsageList
	 * @param fjzch
	 * @param dprtcode
	 * @param type
	 * @param czls
	 * @param logOperationEnum
	 * @throws BusinessException
	 */
	@Override
	public void add(List<PlanUsage> planUsageList, String fjzch,
			String dprtcode, UpdateTypeEnum type, String czls,
			LogOperationEnum logOperationEnum) throws BusinessException {
		if (planUsageList != null && !planUsageList.isEmpty()) {
			// 根据项目编号填充飞机初始化项目描述
			this.fillXmms(planUsageList, fjzch);
			// 新增飞机初始化数据
			planUsageMapper.insertAll(planUsageList);
		}
		// 插入日志
		commonRecService.writeByWhere(" b.fjzch = '"+fjzch.replaceAll("'", "''")+"' and b.dprtcode = '"+dprtcode+"'"
				, TableEnum.D_00702, ThreadVarUtil.getUser().getId(), czls, logOperationEnum, type
				, fjzch.concat(",").concat(dprtcode));
		
	}
	
	private void fillXmms(List<PlanUsage> planUsageList, String fjzch){
		//获取登入user
		User user = ThreadVarUtil.getUser();
		for (PlanUsage planUsage : planUsageList) {
			planUsage.setId(UUID.randomUUID().toString());
			planUsage.setFjzch(fjzch);
			planUsage.setWhrid(user.getId());
			planUsage.setWhbmid(user.getBmdm());
			planUsage.setDprtcode(user.getJgdm());
		}
	}
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2017-9-21 下午2:54:21
	 * @CreateBy 孙霁
	 * @param PlanUsageList
	 * @param fjzch
	 * @param dprtcode
	 * @param type
	 * @param czls
	 * @param logOperationEnum
	 * @throws BusinessException
	 */
	@Override
	public void edit(List<PlanUsage> planUsageList, String fjzch,
			String dprtcode, UpdateTypeEnum type, String czls,
			LogOperationEnum logOperationEnum) throws BusinessException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		if (planUsageList != null && !planUsageList.isEmpty()) {
			for (PlanUsage planUsage : planUsageList) {
				planUsage.setFjzch(fjzch);
				planUsage.setWhrid(user.getId());
				planUsage.setWhbmid(user.getBmdm());
				// 新增飞机初始化数据
				planUsageMapper.updateAll(planUsageList);
			}
		}
		// 插入日志
		commonRecService.writeByWhere(" b.fjzch = '"+fjzch.replaceAll("'", "''")+"' and b.dprtcode = '"+dprtcode+"'"
				, TableEnum.D_00702, ThreadVarUtil.getUser().getId(), czls, logOperationEnum, type
				, fjzch.concat(",").concat(dprtcode));
	}

}
