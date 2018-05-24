package com.eray.thjw.flightdata.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.flightdata.dao.FlightRecordSheetToDisassemblyMapper;
import com.eray.thjw.flightdata.po.FlightRecordSheetToDisassembly;
import com.eray.thjw.flightdata.po.FlightRecordSheetToPlan;
import com.eray.thjw.flightdata.po.MountLoadingList;
import com.eray.thjw.flightdata.po.MountTimeMonitorTotal;
import com.eray.thjw.flightdata.service.FlightRecordSheetToDisassemblyService;
import com.eray.thjw.flightdata.service.MountLoadingListService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.SaiBongEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * 拆解记录实现
 * @author hanwu
 *
 */
@Service
public class FlightRecordSheetToDisassemblyServiceImpl implements FlightRecordSheetToDisassemblyService {

	@Resource
	private FlightRecordSheetToDisassemblyMapper flightRecordSheetToDisassemblyMapper;
	
	@Resource
	MountLoadingListService mountLoadingListService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private SaibongUtilService saibongUtilService;     


	/**
	 * 保存拆解记录
	 */
	@Override
	public void save(FlightRecordSheetToDisassembly data) {
		// 当前操作   1：新增   2.修改 
		UpdateTypeEnum operation = UpdateTypeEnum.UPDATE;
		// 生成id
		if(StringUtils.isBlank(data.getId())){
			data.setId(UUID.randomUUID().toString());
			// 生成飞行记录单号
			String fjzch = data.getFjzch();
			String last4=fjzch.substring(fjzch.length()-4 <= 0 ? 0 : fjzch.length()-4,fjzch.length());
			User user = ThreadVarUtil.getUser();
			try {
				data.setCjjldh(saibongUtilService.generate(user.getJgdm(), SaiBongEnum.CJJL.getName(), last4));
			} catch (SaibongException e) {
				throw new RuntimeException(e);
			}
			operation = UpdateTypeEnum.SAVE;
		}
		// 如果装上件为手输，且在外场库存中存在，则设置外场库存id为对应
		MountLoadingList ll = data.getOn();
		if(ll != null && StringUtils.isBlank(data.getZsWckcid()) 
				&& !StringUtils.isBlank(ll.getJh()) && !StringUtils.isBlank(ll.getXlh())){
			OutFieldStock out = flightRecordSheetToDisassemblyMapper.queryOutFieldData(ll);
			if(out != null){
				// 填写外场库存id，并装机数量继承库存数量
				data.setZsWckcid(out.getId());
				ll.setZjsl(out.getKcsl().intValue());
			}
		}
		
		if(ll != null && StringUtils.isNotBlank(ll.getBz())){
			data.setZsBz(ll.getBz());
		}
		// 设置默认值
		setDefaultValue(data);
		// 保存记录
		flightRecordSheetToDisassemblyMapper.save(data);
		// 插入日志
		commonRecService.write(data.getId(), TableEnum.B_S_0060201, ThreadVarUtil.getUser().getId(), data.getCzls(),
				data.getLogOperationEnum(), operation, data.getFxjldid());
		// 保存装上件-装机清单数据
		if(data.getOn() != null){
			saveMountLoadingList(data);
		}
	}


	@Override
	public void deleteNotExist(FlightRecordSheetToPlan plan) {
		FlightRecordSheetToDisassembly param = new FlightRecordSheetToDisassembly();
		setDefaultValue(param);
		param.setFxjlgljlid(plan.getId());
		param.setZt(EffectiveEnum.NO.getId());
		List<String> ids = new ArrayList<String>();
		for (FlightRecordSheetToDisassembly temp : plan.getDismountRecord()) {
			if(StringUtils.isNotBlank(temp.getId())){
				ids.add(temp.getId());
			}
		}
		param.setIds(ids);
		flightRecordSheetToDisassemblyMapper.deleteNotExist(param);
		// 插入日志
		StringBuilder sql = new StringBuilder("b.fxjlgljlid = '"+plan.getId()+"' ");
		if(!ids.isEmpty()){
			sql.append("and b.id not in (");
			for (String id : ids) {
				sql.append("'").append(id).append("',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(")");
		}
		commonRecService.writeByWhere(sql.toString(), TableEnum.B_S_0060201, ThreadVarUtil.getUser().getId(), plan.getCzls(),
				plan.getLogOperationEnum(), UpdateTypeEnum.DELETE, plan.getFxjldid());
	}
	
	/**
	 * 保存装上件-装机清单数据
	 * @param data
	 */
	private void saveMountLoadingList(FlightRecordSheetToDisassembly data){
		MountLoadingList ll = data.getOn();
		MountTimeMonitorTotal total = data.getTimeMonitor();
		if(total != null){
			ll.setTsn(total.getTsn());
			ll.setTso(total.getTso());
		}
		ll.setMainid(data.getId());
		ll.setCzls(data.getCzls());
		ll.setLogOperationEnum(data.getLogOperationEnum());
		ll.setDprtcode(data.getDprtcode());
		mountLoadingListService.save(data);
	}
	
	/**
	 * 设置默认值
	 * @param data
	 */
	private void setDefaultValue(FlightRecordSheetToDisassembly data){
		if(data.getZsIs() == null){
			data.setZsIs(0);
		}
		User user = ThreadVarUtil.getUser();
		data.setWhrid(user.getId());
		data.setWhdwid(user.getBmdm());
		data.setWhsj(new Date());
		data.setZt(EffectiveEnum.YES.getId());
		data.setTbbs(SynchronzeEnum.TO_DO.getCode());
	}
	
}
