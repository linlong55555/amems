package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.EOPulicationAffectedMapper;
import com.eray.thjw.project2.po.EOPulicationAffected;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.project2.service.EOPulicationAffectedService;
import com.eray.thjw.service.CommonRecService;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/** 
 * @Description EO受影响出版物  业务实现
 * @CreateTime 2017-8-23 上午9:32:09
 * @CreateBy 雷伟	
 */
@Service
public class EOPulicationAffectedServiceImpl implements EOPulicationAffectedService {

	@Resource
	private EOPulicationAffectedMapper eoPulicationAffectedMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	/**
	 * @Description 新增受影响出版物
	 * @CreateTime 2017-8-23 上午9:36:02
	 * @CreateBy 雷伟
	 * @param syxcbwList 数据集合
	 * @param user 当前用户
	 * @param eoId 业务ID
	 * @param currentDate 当前时间
	 * @param czls 操作流水号
	 * @param operation 操作类型
	 */
	@Override
	public void savePulicationAffectedList(List<EOPulicationAffected> syxcbwList, User user, String eoId,Date currentDate,String czls,LogOperationEnum operation) {
		if(syxcbwList == null || syxcbwList.size() <= 0) {
			return;
		}
		
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		for (EOPulicationAffected eoPulicationAffected : syxcbwList) {
			String id = UUID.randomUUID().toString();
			eoPulicationAffected.setId(id);
			eoPulicationAffected.setMainid(eoId); //业务ID
			eoPulicationAffected.setZt(EffectiveEnum.YES.getId());//状态
			eoPulicationAffected.setWhdwid(user.getBmdm());
			eoPulicationAffected.setWhrid(user.getId());
			eoPulicationAffected.setWhsj(currentDate);
			columnValueList.add(id);
		}
		eoPulicationAffectedMapper.insert4Batch(syxcbwList);
		if(columnValueList.size() > 0){
			// 保存历史记录信息
			commonRecService.write("id", columnValueList, TableEnum.B_G2_01003, user.getId(), czls, operation, UpdateTypeEnum.SAVE, eoId);
		}
	}

	/**
	 * @Description 查询EO受影响出版物
	 * @CreateTime 2017-8-24 下午3:25:16
	 * @CreateBy 雷伟
	 * @param eoPulicationAffected EO受影响出版物
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<EOPulicationAffected> queryAllList(EOPulicationAffected eoPulicationAffected) {
		return eoPulicationAffectedMapper.queryAllList(eoPulicationAffected);
	}

	@Override
	public void deleteByMainid(EngineeringOrder engineeringOrder,User user, Date currentDate, String czls, LogOperationEnum operation) {
		commonRecService.write(engineeringOrder.getId(), TableEnum.B_G2_01004, user.getId(), czls, operation, UpdateTypeEnum.DELETE, engineeringOrder.getId());
		eoPulicationAffectedMapper.deleteByMainid(engineeringOrder.getId());
	}
	
}
