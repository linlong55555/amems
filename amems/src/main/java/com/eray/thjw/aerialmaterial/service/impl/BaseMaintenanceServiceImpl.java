package com.eray.thjw.aerialmaterial.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.BaseMaintenanceMapper;
import com.eray.thjw.aerialmaterial.po.BaseMaintenance;
import com.eray.thjw.aerialmaterial.service.BaseMaintenanceService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;


@Service
public class BaseMaintenanceServiceImpl implements BaseMaintenanceService {
	@Autowired
	private BaseMaintenanceMapper baseMaintenanceMapper;
	@Resource
	private CommonRecService commonRecService;
	@Override
	public void insertBaseMaintenance(BaseMaintenance baseMaintenance) throws Exception {
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
//		String id = UUID.randomUUID().toString();
//		baseMaintenance.setId(id);
		baseMaintenanceMapper.insertBaseMaintenance(baseMaintenance);
		commonRecService.write(baseMaintenance.getId(), TableEnum.D_012, user.getId(), czls, LogOperationEnum.SAVE_WO,
				UpdateTypeEnum.SAVE,baseMaintenance.getId());
	}

	@Override
	public List<BaseMaintenance> selectBaseMaintenanceList(BaseMaintenance baseMaintenance) throws Exception {
		
		return baseMaintenanceMapper.selectBaseMaintenanceList(baseMaintenance);
	}

	@Override
	public BaseMaintenance selectBaseMaintenanceById(String id) throws Exception {
		
		return baseMaintenanceMapper.selectBaseMaintenanceById(id);
	}

	@Override
	public void updateBaseMaintenanceById(BaseMaintenance baseMaintenance) throws Exception {
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		baseMaintenanceMapper.updateBaseMaintenanceById(baseMaintenance);
		commonRecService.write(baseMaintenance.getId(), TableEnum.D_012, user.getId(), czls, LogOperationEnum.EDIT,
				UpdateTypeEnum.UPDATE,baseMaintenance.getId());
	}

	@Override
	public void deleteBaseMaintenanceById(String id) throws Exception {
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		baseMaintenanceMapper.deleteBaseMaintenanceById(id);
		commonRecService.write(id, TableEnum.D_012, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,id);
	}

	@Override
	public int selectByJdms(BaseMaintenance baseMaintenance) throws Exception {
		
		return baseMaintenanceMapper.selectByJdms(baseMaintenance);
	}

}
