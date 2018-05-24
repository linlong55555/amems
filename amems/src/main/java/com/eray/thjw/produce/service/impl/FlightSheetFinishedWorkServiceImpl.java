package com.eray.thjw.produce.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.FlightSheetFinishedWorkMapper;
import com.eray.thjw.produce.dao.FlightSheetWorkerMapper;
import com.eray.thjw.produce.po.FlightSheetFinishedWork;
import com.eray.thjw.produce.po.FlightSheetLeg;
import com.eray.thjw.produce.service.FlightSheetDisassemblyRecordService;
import com.eray.thjw.produce.service.FlightSheetFinishedWorkService;
import com.eray.thjw.produce.service.FlightSheetWorkerService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * @Description 飞行记录本 - 完成工作service实现类
 * @CreateTime 2017年10月25日 下午1:46:28
 * @CreateBy 韩武
 */
@Service
public class FlightSheetFinishedWorkServiceImpl implements FlightSheetFinishedWorkService  {

	@Resource
	private FlightSheetFinishedWorkMapper flightSheetFinishedWorkMapper;
	
	@Resource
	private FlightSheetWorkerMapper flightSheetWorkerMapper;
	
	@Resource
	private FlightSheetDisassemblyRecordService flightSheetDisassemblyRecordService;
	
	@Resource
	private FlightSheetWorkerService flightSheetWorkerService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private SaibongUtilService saibongUtilService;
	
	/** 工单编号前缀 */
	private final String GDBH_PREFIX = "SG-";

	/**
	 * @Description 保存飞行记录单-完成工作
	 * @CreateTime 2017年10月25日 下午1:50:39
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException 
	 */
	@Override
	public void save(FlightSheetLeg leg) throws BusinessException {
		// 删除飞行记录本 - 完成工作
		deleteNotExist(leg);
		
		User user = ThreadVarUtil.getUser();
		for (FlightSheetFinishedWork work : leg.getFinishedWorks()) {
			work.setMainid(leg.getMainid());
			work.setHdid(leg.getId());
			work.setCzls(leg.getCzls());
			work.setLogOperationEnum(leg.getLogOperationEnum());
			work.setWhrid(user.getId());
			work.setWhsj(new Date());
			work.setWhdwid(user.getBmdm());
			work.setZt(EffectiveEnum.YES.getId());
			work.setFxjldid(leg.getMainid());
			
			if(StringUtils.isBlank(work.getGdid())){
				work.setGdid(UUID.randomUUID().toString());
				work.setGdbh(generateGdbh(leg));
			}
			
			if (StringUtils.isBlank(work.getId())){	// 新增完成工作
				work.setId(UUID.randomUUID().toString());
				flightSheetFinishedWorkMapper.insertSelective(work);
				// 保存历史记录信息
				commonRecService.write(work.getId(), TableEnum.B_S2_00604, user.getId(), 
						leg.getCzls() , leg.getLogOperationEnum(), UpdateTypeEnum.SAVE, work.getFxjldid());
				
			} else {	// 修改完成工作
				flightSheetFinishedWorkMapper.updateByPrimaryKeySelective(work);
				// 保存历史记录信息
				commonRecService.write(work.getId(), TableEnum.B_S2_00604, user.getId(), 
						leg.getCzls() , leg.getLogOperationEnum(), UpdateTypeEnum.UPDATE, work.getFxjldid());
			}
			
			// 保存附件
			attachmentService.delFiles(work.getGdid(), work.getCzls(), 
					work.getFxjldid(), work.getLogOperationEnum());
			attachmentService.eidtAttachment(work.getAttachments(), work.getGdid(), 
					work.getCzls(), work.getFxjldid(), leg.getDprtcode(), work.getLogOperationEnum());
			
			// 保存工作者
			flightSheetWorkerService.save(work);
			
			// 保存拆装记录
			flightSheetDisassemblyRecordService.save(work);
		}
	}
	
	/**
	 * @Description 生成工单编号
	 * @CreateTime 2017年12月1日 上午9:43:20
	 * @CreateBy 韩武
	 * @param leg
	 * @return
	 */
	private String generateGdbh(FlightSheetLeg leg){
		String fjzch = leg.getFjzch();
		String last4 = fjzch.substring(fjzch.length() - 4 <= 0 ? 0 : fjzch.length()-4, fjzch.length());
		User user = ThreadVarUtil.getUser();
		try {
			return saibongUtilService.generate(user.getJgdm(), SaiBongEnum.ZBGD.getName(), GDBH_PREFIX + last4);
		} catch (SaibongException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @Description 删除飞行记录本 - 完成工作
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
		if(!record.getFinishedWorks().isEmpty()){
			for (FlightSheetFinishedWork work : record.getFinishedWorks()) {
				if(StringUtils.isNotBlank(work.getId())){
					param.append("'").append(work.getId()).append("',");
					hasRecord = true;
				}
			}
		}
		if(hasRecord){
			param.deleteCharAt(param.lastIndexOf(","));
			param.append(")");
			base.append("and b.id not in (").append(param);
		}
		commonRecService.writeByWhere(base.toString(), TableEnum.B_S2_00604, user.getId(), record.getCzls(),
				record.getLogOperationEnum(), UpdateTypeEnum.DELETE, record.getMainid());
		
		// 删除无效的工作者数据
		flightSheetWorkerMapper.deleteByFinishedWork(record);
		
		// 删除数据
		flightSheetFinishedWorkMapper.deleteNotExist(record);
	}
	
}