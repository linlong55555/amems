package com.eray.thjw.flightdata.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.flightdata.dao.InspectionRecordMapper;
import com.eray.thjw.flightdata.po.FlightRecordSheet;
import com.eray.thjw.flightdata.po.InspectionRecord;
import com.eray.thjw.flightdata.service.InspectionRecordService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * 航间检查记录实现
 * @author hanwu
 *
 */
@Service
public class InspectionRecordServiceImpl implements InspectionRecordService {

	@Resource
	private InspectionRecordMapper inspectionRecordMapper;
	
	@Resource
	private CommonRecService commonRecService;


	/**
	 * 保存航间检查记录
	 */
	@Override
	public void save(InspectionRecord data) {
		// 当前操作   1：新增   2.修改 
		UpdateTypeEnum operation = UpdateTypeEnum.UPDATE;
		// 生成id
		if(StringUtils.isBlank(data.getId())){
			data.setId(UUID.randomUUID().toString());
			operation = UpdateTypeEnum.SAVE;
		}
		// 设置默认值
		setDefaultValue(data);
		// 保存记录
		inspectionRecordMapper.save(data);
		// 插入日志
		commonRecService.write(data.getId(), TableEnum.B_S_00603, ThreadVarUtil.getUser().getId(), data.getCzls(),
				data.getLogOperationEnum(), operation, data.getFxjldid());
		
	}


	@Override
	public void deleteNotExist(FlightRecordSheet flightRecordSheet) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 设置默认值
	 * @param data
	 */
	private void setDefaultValue(InspectionRecord data){
		User user = ThreadVarUtil.getUser();
		data.setWhrid(user.getId());
		data.setWhdwid(user.getBmdm());
		data.setWhsj(new Date());
		data.setZt(EffectiveEnum.YES.getId());
	}


	/**
	 * 根据飞行记录单查找
	 */
	@Override
	public List<InspectionRecord> findByFxjldid(String fxjldid) {
		return inspectionRecordMapper.findByFxjldid(fxjldid);
	}
	
}
