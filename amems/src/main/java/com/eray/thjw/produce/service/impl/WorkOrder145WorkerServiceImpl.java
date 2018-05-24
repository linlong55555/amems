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
import com.eray.thjw.produce.dao.WorkOrder145WorkerMapper;
import com.eray.thjw.produce.po.WorkOrder145Worker;
import com.eray.thjw.produce.po.Workorder145;
import com.eray.thjw.produce.service.WorkOrder145WorkerService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * @Description 145工单信息-工作者service实现类
 * @CreateTime 2018年1月24日 上午10:05:36
 * @CreateBy 韩武
 */
@Service
public class WorkOrder145WorkerServiceImpl implements WorkOrder145WorkerService  {

	@Resource
	private WorkOrder145WorkerMapper workOrder145WorkerMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	/**
	 * @Description 保存145工单信息-工作者
	 * @CreateTime 2018年1月24日 上午10:04:51
	 * @CreateBy 韩武
	 * @param work
	 * @throws BusinessException
	 */
	@Override
	public void save(Workorder145 workorder, String czls, LogOperationEnum operation) throws BusinessException {
		
		if(workorder.getWorkers() == null){
			workorder.setWorkers(new ArrayList<WorkOrder145Worker>());
		}
		
		// 删除145工单信息-工作者
		deleteNotExist(workorder, czls, operation);
		
		User user = ThreadVarUtil.getUser();
		
		// 计算分摊工时
		BigDecimal ftgs = BigDecimal.ZERO;
		if(!workorder.getWorkers().isEmpty() && workorder.getSjGs() != null){
			ftgs = workorder.getSjGs().divide(new BigDecimal(workorder.getWorkers().size()), 2, BigDecimal.ROUND_HALF_UP);
		}
		for (WorkOrder145Worker worker : workorder.getWorkers()) {
			
			worker.setMainid(workorder.getId());
			worker.setWhrid(user.getId());
			worker.setWhsj(new Date());
			worker.setWhdwid(user.getBmdm());
			worker.setZt(EffectiveEnum.YES.getId());
			worker.setFtgs(ftgs);
			
			if (StringUtils.isBlank(worker.getId())){	// 新增145工单信息-工作者
				worker.setId(UUID.randomUUID().toString());
				workOrder145WorkerMapper.insertSelective(worker);
				// 保存历史记录信息
				commonRecService.write(worker.getId(), TableEnum.B_S2_01403, user.getId(), 
						czls , operation, UpdateTypeEnum.SAVE, workorder.getId());
				
			} else {	// 修改145工单信息-工作者
				workOrder145WorkerMapper.updateByPrimaryKeySelective(worker);
				// 保存历史记录信息
				commonRecService.write(worker.getId(), TableEnum.B_S2_01403, user.getId(), 
						czls , operation, UpdateTypeEnum.UPDATE, workorder.getId());
			}
		}
	}
	
	/**
	 * @Description 删除145工单信息-工作者
	 * @CreateTime 2018年1月24日 上午10:07:38
	 * @CreateBy 韩武
	 * @param record
	 */
	private void deleteNotExist(Workorder145 workorder, String czls, LogOperationEnum operation){
		
		User user = ThreadVarUtil.getUser();
		
		// 插入日志
		StringBuilder base = new StringBuilder("b.mainid = '"+workorder.getId()+"' ");
		StringBuilder param = new StringBuilder();
		boolean hasRecord = false;
		if(!workorder.getWorkers().isEmpty()){
			for (WorkOrder145Worker work : workorder.getWorkers()) {
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
		commonRecService.writeByWhere(base.toString(), TableEnum.B_S2_01403, user.getId(), czls,
				operation, UpdateTypeEnum.DELETE, workorder.getId());
		// 删除数据
		workOrder145WorkerMapper.deleteNotExist(workorder);
	}
	
}