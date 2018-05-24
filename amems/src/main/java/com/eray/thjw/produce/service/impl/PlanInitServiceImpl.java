package com.eray.thjw.produce.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.AircraftinfoStatusMapper;
import com.eray.thjw.produce.dao.PlanInitMapper;
import com.eray.thjw.produce.po.AircraftinfoStatus;
import com.eray.thjw.produce.po.PlanInit;
import com.eray.thjw.produce.service.PlanInitService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.produce.InstalledPositionEnum;
import enu.project2.MonitorProjectEnum;

/** 
 * @Description 
 * @CreateTime 2017-9-21 下午12:03:11
 * @CreateBy 孙霁	
 */
@Service("PlanInitService")
public class PlanInitServiceImpl implements PlanInitService{

	@Resource
	private CommonRecService commonRecService;
	@Resource
	private PlanInitMapper planInitMapper;
	@Resource
	private AircraftinfoStatusMapper aircraftinfoStatusMapper;
	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2017-9-21 下午12:01:59
	 * @CreateBy 孙霁
	 * @param planInitList
	 * @param fjzch
	 * @param jgdm
	 * @param type
	 * @param czls
	 * @param logOperationEnum
	 */
	@Override
	public void add(List<PlanInit> planInitList, String fjzch, String dprtcode,
			UpdateTypeEnum type, String czls, LogOperationEnum logOperationEnum) {
		if (planInitList != null && !planInitList.isEmpty()) {
			// 根据项目编号填充飞机初始化项目描述
			this.fillXmms(planInitList, fjzch);
			// 新增飞机初始化数据
			planInitMapper.insertAll(planInitList);
			
			//新增飞机状态数据  b_s2_911 ，仅新增 机身非日历的数据
			AircraftinfoStatus aircraftinfoStatus = null;
			for (PlanInit planInit : planInitList) {
				if(InstalledPositionEnum.BODY.getId().intValue() == planInit.getWz().intValue()
						&& !MonitorProjectEnum.isCalendar(planInit.getJklbh())){
					aircraftinfoStatus = new AircraftinfoStatus();
					aircraftinfoStatus.setId(UUID.randomUUID().toString());
					aircraftinfoStatus.setFjzch(fjzch);
					aircraftinfoStatus.setDprtcode(dprtcode);
					aircraftinfoStatus.setWz(InstalledPositionEnum.BODY.getId());
					aircraftinfoStatus.setZjqdid(planInit.getZjqdid());
					aircraftinfoStatus.setJh(planInit.getJh());
					aircraftinfoStatus.setXlh(planInit.getXlh());
					aircraftinfoStatus.setJkflbh(planInit.getJkflbh());
					aircraftinfoStatus.setJklbh(planInit.getJklbh());
					aircraftinfoStatus.setCsz(Integer.parseInt(planInit.getCsz()));
					this.aircraftinfoStatusMapper.insertSelective(aircraftinfoStatus);
				}
			}
			
		}
		// 插入日志
		commonRecService.writeByWhere(" b.fjzch = '"+fjzch.replaceAll("'", "''")+"' and b.dprtcode = '"+dprtcode+"'"
				, TableEnum.D_00701, ThreadVarUtil.getUser().getId(), czls, logOperationEnum, type
				, fjzch.concat(",").concat(dprtcode));
	}
	
	private void fillXmms(List<PlanInit> planInitList, String fjzch){
		//获取登入user
		User user = ThreadVarUtil.getUser();
		for (PlanInit planInit : planInitList) {
			planInit.setId(UUID.randomUUID().toString());
			planInit.setFjzch(fjzch);
			planInit.setWhrid(user.getId());
			planInit.setWhbmid(user.getBmdm());
			planInit.setDprtcode(user.getJgdm());
			
		}
	}
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2017-9-21 下午12:01:59
	 * @CreateBy 孙霁
	 * @param planInitList
	 * @param fjzch
	 * @param jgdm
	 * @param type
	 * @param czls
	 * @param logOperationEnum
	 */
	@Override
	public void edit(List<PlanInit> planInitList, String fjzch, String dprtcode,
			UpdateTypeEnum type, String czls, LogOperationEnum logOperationEnum) {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		if (planInitList != null && !planInitList.isEmpty()) {
			for (PlanInit planInit : planInitList) {
				planInit.setFjzch(fjzch);
				planInit.setWhrid(user.getId());
				planInit.setWhbmid(user.getBmdm());
			}
			// 新增飞机初始化数据
			planInitMapper.updateAll(planInitList);
		}
		// 插入日志
		commonRecService.writeByWhere(" b.fjzch = '"+fjzch.replaceAll("'", "''")+"' and b.dprtcode = '"+dprtcode+"'"
				, TableEnum.D_00701, ThreadVarUtil.getUser().getId(), czls, logOperationEnum, type
				, fjzch.concat(",").concat(dprtcode));
		
	}

}
