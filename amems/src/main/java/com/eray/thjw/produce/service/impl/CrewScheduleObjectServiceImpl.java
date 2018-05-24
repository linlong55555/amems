package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.CrewScheduleMapper;
import com.eray.thjw.produce.dao.CrewScheduleObjectMapper;
import com.eray.thjw.produce.po.CrewSchedule;
import com.eray.thjw.produce.po.CrewScheduleObject;
import com.eray.thjw.produce.service.CrewScheduleObjectService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class CrewScheduleObjectServiceImpl implements CrewScheduleObjectService {
	@Autowired
	private CrewScheduleObjectMapper crewScheduleObjectMapper;
	@Autowired
	private CrewScheduleMapper crewScheduleMapper;
	@Resource
	private CommonRecService commonRecService;

	@Override
	public List<String> selectIdByMainid(CrewScheduleObject crewScheduleObject) throws Exception {

		return crewScheduleObjectMapper.selectIdByMainid(crewScheduleObject);
	}

	@Override
	public void deleteByMainid(CrewScheduleObject crewScheduleObject) throws Exception {

		crewScheduleObjectMapper.deleteByMainid(crewScheduleObject);
	}

	/**
	 * 保存飞机排班对象
	 */
	@Override
	public void savePlaneScheduling(CrewScheduleObject crewScheduleObject) {
		crewScheduleObject.setId(UUID.randomUUID().toString());
		crewScheduleObject.setDxlx(10);
		crewScheduleObject.setZt(1);
		crewScheduleObjectMapper.savePlaneScheduling(crewScheduleObject);

		List<String> list = new ArrayList<String>();
		list.add(crewScheduleObject.getId());
		if(crewScheduleObject.getLogOperationEnum() == LogOperationEnum.SAVE_WO){
			commonRecService.write("id", list, TableEnum.B_S_01001, ThreadVarUtil.getUser().getId(),
					crewScheduleObject.getCzls(), LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,crewScheduleObject.getMainid());
		}else if(crewScheduleObject.getLogOperationEnum() == LogOperationEnum.SUBMIT_WO){
			commonRecService.write("id", list, TableEnum.B_S_01001, ThreadVarUtil.getUser().getId(),
					crewScheduleObject.getCzls(), LogOperationEnum.SUBMIT_WO, UpdateTypeEnum.UPDATE,crewScheduleObject.getMainid());
		}
	}

	/**
	 * 取消飞机排班对象
	 */
	@Override
	public void doCancelPlaneScheduling(List<String> ids) {
		crewScheduleObjectMapper.cancelPlaneScheduling(ids);
	}

	@Override
	public void updatecrewshecduleObject(CrewScheduleObject crewScheduleObject) throws Exception {

		crewScheduleObjectMapper.updatecrewshecduleObject(crewScheduleObject);
	}

	@Override
	public void updateZtByMainid(CrewScheduleObject crewScheduleObject) throws Exception {
		User user = ThreadVarUtil.getUser();
		String recId = UUID.randomUUID().toString();
		List<String> crewScheduleObjectId = crewScheduleObjectMapper.selectIdByMainid(crewScheduleObject);
		if(crewScheduleObjectId.size()>0){
			for (String id : crewScheduleObjectId) {
				commonRecService.write(id, TableEnum.B_S_01001, user.getId(), recId,
					LogOperationEnum.ZUOFEI, UpdateTypeEnum.UPDATE,crewScheduleObject.getMainid());
			}
		}
		commonRecService.write(crewScheduleObject.getMainid(), TableEnum.B_S_010, user.getId(), recId,
				LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,crewScheduleObject.getMainid());
		crewScheduleObjectMapper.updateZtByMainid(crewScheduleObject);
		crewScheduleMapper.deleteById(crewScheduleObject.getMainid());
	}

	/**
	 * 获取取消飞机排班对象
	 */
	@Override
	public List<String> getCancelPlaneScheduling(CrewSchedule crewSchedule) {
		return crewScheduleObjectMapper.getCancelPlaneScheduling(crewSchedule);
	}

	@Override
	public int getIdByMainid(CrewScheduleObject crewScheduleObject) {
		
		return crewScheduleObjectMapper.getIdByMainid(crewScheduleObject);
	}

	@Override
	public List<String> getIdByZt(CrewScheduleObject crewScheduleObject) {
		
		return crewScheduleObjectMapper.getIdByZt(crewScheduleObject);
	}

	@Override
	public void updateZtByCrewScheduleObject(CrewScheduleObject crewScheduleObject) {
		
		crewScheduleObjectMapper.updateZtByCrewScheduleObject(crewScheduleObject);
	}

	@Override
	public void deleteByZt(CrewScheduleObject crewScheduleObject) {
		crewScheduleObjectMapper.deleteByZt(crewScheduleObject);
	}

	@Override
	public void updateZt(CrewScheduleObject crewScheduleObject) {
		
		crewScheduleObjectMapper.updateZt(crewScheduleObject);
	}
	
}
