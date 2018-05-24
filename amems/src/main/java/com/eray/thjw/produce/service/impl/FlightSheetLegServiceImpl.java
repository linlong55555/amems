package com.eray.thjw.produce.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.FlightSheetLegMapper;
import com.eray.thjw.produce.po.FlightSheet;
import com.eray.thjw.produce.po.FlightSheetLeg;
import com.eray.thjw.produce.service.FlightSheetAntiIceService;
import com.eray.thjw.produce.service.FlightSheetFinishedWorkService;
import com.eray.thjw.produce.service.FlightSheetLegService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * @Description 飞行记录本 - 航段数据service实现类
 * @CreateTime 2017年10月25日 下午2:10:16
 * @CreateBy 韩武
 */
@Service
public class FlightSheetLegServiceImpl implements FlightSheetLegService  {

	@Resource
	private FlightSheetLegMapper flightSheetLegMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private FlightSheetFinishedWorkService flightSheetFinishedWorkService;
	
	@Resource
	private FlightSheetAntiIceService flightSheetAntiIceService;
	
	@Resource
	private AttachmentService attachmentService;

	/**
	 * @Description 保存飞行记录本 - 航段数据
	 * @CreateTime 2017年10月25日 上午10:57:48
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException 
	 */
	@Override
	public void save(FlightSheet record) throws BusinessException {
		// 删除飞行记录本 - 航段数据
		deleteNotExist(record);
		
		User user = ThreadVarUtil.getUser();
		for (FlightSheetLeg leg : record.getLegs()) {
			leg.setMainid(record.getId());
			leg.setCzls(record.getCzls());
			leg.setFjzch(record.getFjzch());
			leg.setDprtcode(record.getDprtcode());
			leg.setLogOperationEnum(record.getLogOperationEnum());
			leg.setWhrid(user.getId());
			leg.setWhsj(new Date());
			leg.setWhdwid(user.getBmdm());
			leg.setZt(EffectiveEnum.YES.getId());
			
			if (StringUtils.isBlank(leg.getId())){	// 新增航段数据
				leg.setId(UUID.randomUUID().toString());
				flightSheetLegMapper.insertSelective(leg);
				// 保存历史记录信息
				commonRecService.write(leg.getId(), TableEnum.B_S2_00602, user.getId(), 
						record.getCzls() , record.getLogOperationEnum(), UpdateTypeEnum.SAVE, record.getId());
				
			} else {	// 修改航段数据
				flightSheetLegMapper.updateByPrimaryKeySelective(leg);
				// 保存历史记录信息
				commonRecService.write(leg.getId(), TableEnum.B_S2_00602, user.getId(), 
						record.getCzls() , record.getLogOperationEnum(), UpdateTypeEnum.UPDATE, record.getId());
			}
			
			// 保存完成工作
			flightSheetFinishedWorkService.save(leg);
			
			// 保存防冰液信息
			flightSheetAntiIceService.save(leg);
		}
	}
	
	/**
	 * @Description 删除飞行记录本 - 航段数据
	 * @CreateTime 2017年10月25日 上午11:30:26
	 * @CreateBy 韩武
	 * @param record
	 */
	private void deleteNotExist(FlightSheet record){
		
		User user = ThreadVarUtil.getUser();
		
		// 插入日志
		StringBuilder base = new StringBuilder("b.mainid = '"+record.getId()+"' ");
		StringBuilder param = new StringBuilder();
		boolean hasRecord = false;
		if(!record.getLegs().isEmpty()){
			for (FlightSheetLeg leg : record.getLegs()) {
				if(StringUtils.isNotBlank(leg.getId())){
					param.append("'").append(leg.getId()).append("',");
					hasRecord = true;
				}
			}
		}
		if(hasRecord){
			param.deleteCharAt(param.lastIndexOf(","));
			param.append(")");
			base.append("and b.id not in (").append(param);
		}
		commonRecService.writeByWhere(base.toString(), TableEnum.B_S2_00602, user.getId(), record.getCzls(),
				record.getLogOperationEnum(), UpdateTypeEnum.DELETE, record.getId());
		// 删除数据
		flightSheetLegMapper.deleteNotExist(record);
		
		//TODO 删除从表
	}
	
}