package com.eray.thjw.aerialmaterial.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.MaterialCostMapper;
import com.eray.thjw.aerialmaterial.po.MaterialCost;
import com.eray.thjw.aerialmaterial.service.MaterialCostService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;
import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class MaterialCostServiceImpl implements MaterialCostService {
    @Resource
    private MaterialCostMapper materialCostMapper;
	@Resource
	private CommonRecService commonRecService;                   //REC表服务
    
	@Override
	public List<MaterialCost> selectMaterialCostList(MaterialCost record) {
		return materialCostMapper.selectMaterialCostList(record);
	}

	@Override
	public int insertSelective(MaterialCost record) {
		int count=0;
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();
		String czls = UUID.randomUUID().toString();   //操作流水
		String id=uuid.toString();
		record.setId(id);
		record.setWhrid(user.getId());
		
		count = materialCostMapper.insertSelective(record);
		commonRecService.write(id, TableEnum.D_00803, user.getId(),czls, LogOperationEnum.EDIT, UpdateTypeEnum.SAVE,id);
		
		return count;
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		User user =ThreadVarUtil.getUser();
		int count=0;
		String czls = UUID.randomUUID().toString();   //操作流水
		commonRecService.write(id, TableEnum.D_00803, user.getId(),czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,id);
		count = materialCostMapper.deleteByPrimaryKey(id);
		return count;
	}
}
