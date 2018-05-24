package com.eray.thjw.produce.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.FlightSheetDisassemblyRecordMapper;
import com.eray.thjw.produce.po.FlightSheetDisassemblyRecord;
import com.eray.thjw.produce.po.FlightSheetFinishedWork;
import com.eray.thjw.produce.service.FlightSheetDisassemblyRecordService;
import com.eray.thjw.produce.service.InstallationListTempService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * @Description 飞行记录本 - 完成工作service实现类
 * @CreateTime 2017年10月25日 下午1:46:28
 * @CreateBy 韩武
 */
@Service
public class FlightSheetDisassemblyRecordServiceImpl implements FlightSheetDisassemblyRecordService  {

	@Resource
	private FlightSheetDisassemblyRecordMapper flightSheetDisassemblyRecordMapper;
	
	@Resource
	private InstallationListTempService installationListTempService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private AttachmentService attachmentService;
	
	/**
	 * @Description 保存飞行记录单-拆装记录
	 * @CreateTime 2017年10月25日 下午1:50:39
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException 
	 */
	@Override
	public void save(FlightSheetFinishedWork work) throws BusinessException {
		// 删除飞行记录本 - 拆装记录
		deleteNotExist(work);
		
		User user = ThreadVarUtil.getUser();
		for (FlightSheetDisassemblyRecord disassembly : work.getDisassemblies()) {
			
			// 保存装上件
			if(disassembly.getZsj() != null && StringUtils.isNotBlank(disassembly.getZsj().getBjh())){
				
				disassembly.getZsj().setFxjldid(work.getFxjldid());
				disassembly.getZsj().setAzbz(disassembly.getZsBz());
				disassembly.getZsj().setCzls(work.getCzls());
				disassembly.getZsj().setLogOperationEnum(work.getLogOperationEnum());
				String zsjid = installationListTempService.save(disassembly.getZsj(), disassembly.getZsZjqdid());
				disassembly.setZsZjqdid(zsjid);
			}else{
				FlightSheetDisassemblyRecord record = flightSheetDisassemblyRecordMapper.selectByPrimaryKey(disassembly.getId());
				if(record != null && StringUtils.isNotBlank(record.getZsZjqdid())){
					installationListTempService.delete(record.getZsZjqdid());
				}
			}
			disassembly.setMainid(work.getId());
			disassembly.setCzls(work.getCzls());
			disassembly.setLogOperationEnum(work.getLogOperationEnum());
			disassembly.setWhrid(user.getId());
			disassembly.setWhsj(new Date());
			disassembly.setWhdwid(user.getBmdm());
			disassembly.setZt(EffectiveEnum.YES.getId());
			disassembly.setFxjldid(work.getFxjldid());
			
			if (StringUtils.isBlank(disassembly.getId())){	// 新增拆装记录
				disassembly.setId(UUID.randomUUID().toString());
				if(StringUtils.isBlank(disassembly.getGdczjlid())){
					disassembly.setGdczjlid(UUID.randomUUID().toString());
				}
				flightSheetDisassemblyRecordMapper.insertSelective(disassembly);
				// 保存历史记录信息
				commonRecService.write(disassembly.getId(), TableEnum.B_S2_0060401, user.getId(), 
						work.getCzls() , work.getLogOperationEnum(), UpdateTypeEnum.SAVE, disassembly.getFxjldid());
				
			} else {	// 修改拆装记录
				flightSheetDisassemblyRecordMapper.updateByFlightSheet(disassembly);
				// 保存历史记录信息
				commonRecService.write(disassembly.getId(), TableEnum.B_S2_0060401, user.getId(), 
						work.getCzls() , work.getLogOperationEnum(), UpdateTypeEnum.UPDATE, disassembly.getFxjldid());
			}
		}
	}
	
	/**
	 * @Description 删除飞行记录本 - 拆装记录
	 * @CreateTime 2017年10月25日 上午11:30:26
	 * @CreateBy 韩武
	 * @param record
	 */
	private void deleteNotExist(FlightSheetFinishedWork record){
		
		User user = ThreadVarUtil.getUser();
		
		// 插入日志
		StringBuilder base = new StringBuilder("b.mainid = '"+record.getId()+"' ");
		StringBuilder param = new StringBuilder();
		boolean hasRecord = false;
		if(!record.getDisassemblies().isEmpty()){
			for (FlightSheetDisassemblyRecord disassembly : record.getDisassemblies()) {
				if(StringUtils.isNotBlank(disassembly.getId())){
					param.append("'").append(disassembly.getId()).append("',");
					hasRecord = true;
				}
			}
		}
		if(hasRecord){
			param.deleteCharAt(param.lastIndexOf(","));
			param.append(")");
			base.append("and b.id not in (").append(param);
		}
		commonRecService.writeByWhere(base.toString(), TableEnum.B_S2_0060401, user.getId(), record.getCzls(),
				record.getLogOperationEnum(), UpdateTypeEnum.DELETE, record.getFxjldid());
		// 删除数据
		flightSheetDisassemblyRecordMapper.deleteNotExist(record);
	}
	
}