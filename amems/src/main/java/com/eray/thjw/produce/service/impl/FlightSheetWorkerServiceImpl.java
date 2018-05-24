package com.eray.thjw.produce.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.FlightSheetWorkerMapper;
import com.eray.thjw.produce.po.FlightSheetFinishedWork;
import com.eray.thjw.produce.po.FlightSheetWorker;
import com.eray.thjw.produce.service.FlightSheetWorkerService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * @Description 航段信息-工作者service实现类
 * @CreateTime 2018年1月24日 上午10:05:36
 * @CreateBy 韩武
 */
@Service
public class FlightSheetWorkerServiceImpl implements FlightSheetWorkerService  {

	@Resource
	private FlightSheetWorkerMapper flightSheetWorkerMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	/**
	 * @Description 保存航段信息-工作者
	 * @CreateTime 2018年1月24日 上午10:04:51
	 * @CreateBy 韩武
	 * @param work
	 * @throws BusinessException
	 */
	@Override
	public void save(FlightSheetFinishedWork work) throws BusinessException {
		
		if(work.getWorkers() == null){
			work.setWorkers(new ArrayList<FlightSheetWorker>());
		}
		
		// 删除航段信息-工作者
		deleteNotExist(work);
		
		User user = ThreadVarUtil.getUser();
		
		// 计算分摊工时
		BigDecimal ftgs = BigDecimal.ZERO;
		if(!work.getWorkers().isEmpty() && work.getSjgs() != null){
			ftgs = work.getSjgs().divide(new BigDecimal(work.getWorkers().size()), 2, BigDecimal.ROUND_HALF_UP);
		}
		for (FlightSheetWorker worker : work.getWorkers()) {
			
			worker.setMainid(work.getId());
			worker.setCzls(work.getCzls());
			worker.setLogOperationEnum(work.getLogOperationEnum());
			worker.setWhrid(user.getId());
			worker.setWhsj(new Date());
			worker.setWhdwid(user.getBmdm());
			worker.setZt(EffectiveEnum.YES.getId());
			worker.setFtgs(ftgs);
			
			if (StringUtils.isBlank(worker.getId())){	// 新增航段信息-工作者
				worker.setId(UUID.randomUUID().toString());
				flightSheetWorkerMapper.insertSelective(worker);
				// 保存历史记录信息
				commonRecService.write(worker.getId(), TableEnum.B_S2_0060402, user.getId(), 
						work.getCzls() , work.getLogOperationEnum(), UpdateTypeEnum.SAVE, work.getFxjldid());
				
			} else {	// 修改航段信息-工作者
				flightSheetWorkerMapper.updateByPrimaryKeySelective(worker);
				// 保存历史记录信息
				commonRecService.write(worker.getId(), TableEnum.B_S2_0060402, user.getId(), 
						work.getCzls() , work.getLogOperationEnum(), UpdateTypeEnum.UPDATE, work.getFxjldid());
			}
		}
	}
	
	/**
	 * @Description 删除航段信息-工作者
	 * @CreateTime 2018年1月24日 上午10:07:38
	 * @CreateBy 韩武
	 * @param record
	 */
	private void deleteNotExist(FlightSheetFinishedWork record){
		
		User user = ThreadVarUtil.getUser();
		
		// 插入日志
		StringBuilder base = new StringBuilder("b.mainid = '"+record.getId()+"' ");
		StringBuilder param = new StringBuilder();
		boolean hasRecord = false;
		if(!record.getWorkers().isEmpty()){
			for (FlightSheetWorker work : record.getWorkers()) {
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
		commonRecService.writeByWhere(base.toString(), TableEnum.B_S2_0060402, user.getId(), record.getCzls(),
				record.getLogOperationEnum(), UpdateTypeEnum.DELETE, record.getFxjldid());
		// 删除数据
		flightSheetWorkerMapper.deleteNotExist(record);
	}
	
}