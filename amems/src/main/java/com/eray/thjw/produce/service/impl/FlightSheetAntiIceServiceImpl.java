package com.eray.thjw.produce.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.FlightSheetAntiIceMapper;
import com.eray.thjw.produce.po.FlightSheetAntiIce;
import com.eray.thjw.produce.po.FlightSheetLeg;
import com.eray.thjw.produce.service.FlightSheetAntiIceService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * @Description 飞行记录本 - 防冰液信息service实现类
 * @CreateTime 2017年10月25日 下午1:46:28
 * @CreateBy 韩武
 */
@Service
public class FlightSheetAntiIceServiceImpl implements FlightSheetAntiIceService  {

	@Resource
	private FlightSheetAntiIceMapper flightSheetAntiIceMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private AttachmentService attachmentService;

	/**
	 * @Description 保存飞行记录单-防冰液信息
	 * @CreateTime 2017年10月25日 下午1:50:39
	 * @CreateBy 韩武
	 * @param record
	 */
	@Override
	public void save(FlightSheetLeg leg) {
		// 删除飞行记录本 - 防冰液信息
		deleteNotExist(leg);
		
		User user = ThreadVarUtil.getUser();
		for (FlightSheetAntiIce antiIce : leg.getAntiIces()) {
			antiIce.setMainid(leg.getMainid());
			antiIce.setHdid(leg.getId());
			antiIce.setCzls(leg.getCzls());
			antiIce.setLogOperationEnum(leg.getLogOperationEnum());
			antiIce.setWhrid(user.getId());
			antiIce.setWhsj(new Date());
			antiIce.setWhdwid(user.getBmdm());
			antiIce.setFxjldid(leg.getMainid());
			antiIce.setZt(EffectiveEnum.YES.getId());
			
			if (StringUtils.isBlank(antiIce.getId())){	// 新增防冰液信息
				antiIce.setId(UUID.randomUUID().toString());
				flightSheetAntiIceMapper.insertSelective(antiIce);
				// 保存历史记录信息
				commonRecService.write(antiIce.getId(), TableEnum.B_S2_00603, user.getId(), 
						leg.getCzls() , leg.getLogOperationEnum(), UpdateTypeEnum.SAVE, antiIce.getFxjldid());
				
			} else {	// 修改防冰液信息
				flightSheetAntiIceMapper.updateByPrimaryKeySelective(antiIce);
				// 保存历史记录信息
				commonRecService.write(antiIce.getId(), TableEnum.B_S2_00603, user.getId(), 
						leg.getCzls() , leg.getLogOperationEnum(), UpdateTypeEnum.UPDATE, antiIce.getFxjldid());
			}
		}
	}
	
	/**
	 * @Description 删除飞行记录本 - 防冰液信息
	 * @CreateTime 2017年10月25日 上午11:30:26
	 * @CreateBy 韩武
	 * @param record
	 */
	private void deleteNotExist(FlightSheetLeg record){
		
		User user = ThreadVarUtil.getUser();
		
		// 插入日志
		StringBuilder base = new StringBuilder("b.hdid = '"+record.getId()+"' ");
		StringBuilder param = new StringBuilder();
		boolean hasRecord = false;
		if(!record.getAntiIces().isEmpty()){
			for (FlightSheetAntiIce antiIce : record.getAntiIces()) {
				if(StringUtils.isNotBlank(antiIce.getId())){
					param.append("'").append(antiIce.getId()).append("',");
					hasRecord = true;
				}
			}
		}
		if(hasRecord){
			param.deleteCharAt(param.lastIndexOf(","));
			param.append(")");
			base.append("and b.id not in (").append(param);
		}
		commonRecService.writeByWhere(base.toString(), TableEnum.B_S2_00603, user.getId(), record.getCzls(),
				record.getLogOperationEnum(), UpdateTypeEnum.DELETE, record.getMainid());
		// 删除数据
		flightSheetAntiIceMapper.deleteNotExist(record);
	}
	
}