package com.eray.thjw.flightdata.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.flightdata.dao.FlightRecordMapper;
import com.eray.thjw.flightdata.po.FlightRecord;
import com.eray.thjw.flightdata.po.FlightRecordSheet;
import com.eray.thjw.flightdata.service.FlightRecordService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * 飞行履历实现
 * @author ll
 *
 */
@Service
public class FlightRecordServiceImpl implements FlightRecordService {

	@Resource
	private FlightRecordMapper flightRecordMapper;
	
	@Resource
	private CommonRecService commonRecService;

	@Override
	public List<FlightRecord> queryAllPageList(FlightRecord flightRecord)
			throws RuntimeException {
		return flightRecordMapper.queryAllPageList(flightRecord);
	}

	/**
	 * 保存飞行履历
	 */
	@Override
	public void save(FlightRecord flightRecord) throws RuntimeException {
		// 当前操作   1：新增   2.修改 
		UpdateTypeEnum operation = UpdateTypeEnum.UPDATE;
		// 生成id
		if(StringUtils.isBlank(flightRecord.getId())){
			flightRecord.setId(UUID.randomUUID().toString());
			operation = UpdateTypeEnum.SAVE;
		}
		// 设置默认值
		setDefaultValue(flightRecord);
		// 保存记录
		flightRecordMapper.save(flightRecord);
		// 插入日志
		commonRecService.write(flightRecord.getId(), TableEnum.B_S_00601, ThreadVarUtil.getUser().getId(), flightRecord.getCzls(),
				flightRecord.getLogOperationEnum(), operation, flightRecord.getFxjldid());
	}

	/**
	 * 删除飞行履历
	 */
	@Override
	public void deleteNotExist(FlightRecordSheet flightRecordSheet)
			throws RuntimeException {
		if(!StringUtils.isBlank(flightRecordSheet.getId())){
			FlightRecord flightRecord = new FlightRecord();
			flightRecord.setFxjldid(flightRecordSheet.getId());
			flightRecordMapper.deleteAdjust(flightRecord);
			// 插入日志
			commonRecService.writeByWhere("b.fxjldid = '"+flightRecordSheet.getId()+"' and b.xdbs = 1", 
					TableEnum.B_S_00601, ThreadVarUtil.getUser().getId(), flightRecordSheet.getCzls(),
					flightRecordSheet.getLogOperationEnum(), UpdateTypeEnum.DELETE, flightRecordSheet.getId());
		}
	}
	
	/**
	 * 设置默认值
	 * @param data
	 */
	private void setDefaultValue(FlightRecord flightRecord){
		User user = ThreadVarUtil.getUser();
		flightRecord.setWhrid(user.getId());
		flightRecord.setWhdwid(user.getBmdm());
		flightRecord.setWhsj(new Date());
		flightRecord.setTbbs(SynchronzeEnum.TO_DO.getCode());
		flightRecord.setZt(EffectiveEnum.YES.getId());
	}

	
	
}
