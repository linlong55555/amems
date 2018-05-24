package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.produce.service.MonitorDataService;
import com.eray.thjw.project2.dao.EngineeringOrderMapper;
import com.eray.thjw.project2.dao.TEApplicabilityMapper;
import com.eray.thjw.project2.po.TEApplicability;
import com.eray.thjw.project2.service.TEApplicabilityService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * 
 * @Description 技术评估单-适用性impl
 * @CreateTime 2018年3月29日 下午3:22:18
 * @CreateBy 林龙
 */
@Service
public class TEApplicabilityServiceImpl implements TEApplicabilityService {

	@Resource
	private TEApplicabilityMapper teapplicabilitymapper;
	@Resource
	private EngineeringOrderMapper engineeringOrderMapper;
	
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private MonitorDataService monitorDataService;
	
	@Resource
	private CommonService commonService;
	
	/**
	 * 新增或编辑
	 */
	public void savePulicationAffectedList(List<TEApplicability> syxszList,User user, String uuid, Date currentDate, String czls) {
		if(syxszList == null || syxszList.size() <= 0) {
			return;
		}
		
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		for (TEApplicability teapplicability : syxszList) {
			String id = UUID.randomUUID().toString();
			teapplicability.setId(id);
			teapplicability.setMainid(uuid); //业务ID
			teapplicability.setZt(EffectiveEnum.YES.getId());//状态
			teapplicability.setWhdwid(user.getBmdm());
			teapplicability.setWhrid(user.getId());
			teapplicability.setWhsj(currentDate);
			columnValueList.add(id);
		}
		teapplicabilitymapper.insert4Batch(syxszList);
		if(columnValueList.size() > 0){
			// 保存历史记录信息
			commonRecService.write("id", columnValueList, TableEnum.B_G2_00102, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, uuid);
		}
	}

	@Override
	public void deleteByMainid(String id,User user, String czls) {
		commonRecService.write(id, TableEnum.B_G2_00102, user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, id);
		teapplicabilitymapper.deleteByMainid(id);
	}


	
	
	
}
