package com.eray.thjw.produce.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.WorkOrderWorkerMapper;
import com.eray.thjw.produce.dao.WorkorderMapper;
import com.eray.thjw.produce.po.WorkOrder145Worker;
import com.eray.thjw.produce.po.WorkOrderWorker;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.po.Workorder145;
import com.eray.thjw.produce.service.WorkOrderWorkerService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * @Description 135工单信息-工作者service实现类
 * @CreateTime 2018年1月24日 上午10:05:36
 * @CreateBy 韩武
 */
@Service
public class WorkOrderWorkerServiceImpl implements WorkOrderWorkerService  {

	@Resource
	private WorkOrderWorkerMapper workOrderWorkerMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private WorkorderMapper workorderMapper;
	
	/**
	 * @Description 保存135工单信息-工作者
	 * @CreateTime 2018年1月24日 上午10:04:51
	 * @CreateBy 韩武
	 * @param work
	 * @throws BusinessException
	 */
	@Override
	public void save(Workorder workorder, String czls, LogOperationEnum operation){
		
		if(workorder.getWorkers() == null){
			workorder.setWorkers(new ArrayList<WorkOrderWorker>());
		}
		
		// 删除航段信息-工作者
		deleteNotExist(workorder, czls, operation);
		
		User user = ThreadVarUtil.getUser();
		
		// 计算分摊工时
		BigDecimal ftgs = BigDecimal.ZERO;
		if(!workorder.getWorkers().isEmpty() && workorder.getSjGs() != null){
			ftgs = workorder.getSjGs().divide(new BigDecimal(workorder.getWorkers().size()), 2, BigDecimal.ROUND_HALF_UP);
		}
		for (WorkOrderWorker worker : workorder.getWorkers()) {
			
			worker.setMainid(workorder.getId());
			worker.setWhrid(user.getId());
			worker.setWhsj(new Date());
			worker.setWhdwid(user.getBmdm());
			worker.setZt(EffectiveEnum.YES.getId());
			worker.setFtgs(ftgs);
			
			if (StringUtils.isBlank(worker.getId())){	// 新增135工单信息-工作者
				worker.setId(UUID.randomUUID().toString());
				workOrderWorkerMapper.insertSelective(worker);
				// 保存历史记录信息
				commonRecService.write(worker.getId(), TableEnum.B_S2_00802, user.getId(), 
						czls , operation, UpdateTypeEnum.SAVE, workorder.getId());
				
			} else {	// 修改135工单信息-工作者
				workOrderWorkerMapper.updateByPrimaryKeySelective(worker);
				// 保存历史记录信息
				commonRecService.write(worker.getId(), TableEnum.B_S2_00802, user.getId(), 
						czls , operation, UpdateTypeEnum.UPDATE, workorder.getId());
			}
		}
	}
	
	/**
	 * @Description 删除135工单信息-工作者
	 * @CreateTime 2018年1月24日 上午10:07:38
	 * @CreateBy 韩武
	 * @param record
	 */
	private void deleteNotExist(Workorder workorder, String czls, LogOperationEnum operation){
		
		User user = ThreadVarUtil.getUser();
		
		// 插入日志
		StringBuilder base = new StringBuilder("b.mainid = '"+workorder.getId()+"' ");
		StringBuilder param = new StringBuilder();
		boolean hasRecord = false;
		if(!workorder.getWorkers().isEmpty()){
			for (WorkOrderWorker work : workorder.getWorkers()) {
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
		commonRecService.writeByWhere(base.toString(), TableEnum.B_S2_00802, user.getId(), czls,
				operation, UpdateTypeEnum.DELETE, workorder.getId());
		// 删除数据
		workOrderWorkerMapper.deleteNotExist(workorder);
	}

	/**
	 * @Description 145工单工作者-->135工单工作者
	 * @CreateTime 2018年1月25日 下午3:44:27
	 * @CreateBy 韩武
	 * @param workorder
	 * @param czls
	 * @param operation
	 * @throws BusinessException
	 */
	@Override
	public void saveWorkorder145(Workorder145 workorder145, String czls,
			LogOperationEnum operation){
		Workorder workorder = new Workorder();
		workorder.setId(workorder.getId());
		workorder.setSjGs(workorder145.getSjGs());
		List<WorkOrderWorker> workers = new ArrayList<WorkOrderWorker>();
		if(workorder145.getWorkers() != null){
			for (WorkOrder145Worker worker145 : workorder145.getWorkers()) {
				WorkOrderWorker worker = new WorkOrderWorker();
				worker.setGzz(worker145.getGzz());
				worker.setGzzid(worker145.getGzzid());
				workers.add(worker);
			}
			workorder.setWorkers(workers);
			save(workorder, czls, operation);
		}
	}
	
	/**
	 * @Description 145工单工作者-->135工单工作者
	 * @CreateTime 2018年1月25日 下午3:44:27
	 * @CreateBy 韩武
	 * @param workorder
	 * @param czls
	 * @param operation
	 * @throws BusinessException
	 */
	@Override
	public void updateWorkorder145(Workorder145 workorder145, String czls,
			LogOperationEnum operation){
		Workorder workorder = new Workorder();
		Workorder param = workorderMapper.getIdByGdsbid(workorder145.getGdsbid());
		if(param != null && workorder145.getWorkers() != null){
			workorder.setId(param.getId());
			workorder.setSjGs(workorder145.getSjGs());
			List<WorkOrderWorker> workers = new ArrayList<WorkOrderWorker>();
			for (WorkOrder145Worker worker145 : workorder145.getWorkers()) {
				WorkOrderWorker worker = new WorkOrderWorker();
				worker.setGzz(worker145.getGzz());
				worker.setGzzid(worker145.getGzzid());
				workers.add(worker);
			}
			workorder.setWorkers(workers);
			save(workorder, czls, operation);
		}
	}

	/**
	 * @Description 135工单-->FLB（工单信息修改后 工作者同步更新FLB信息）
	 * @CreateTime 2018年1月31日 上午10:02:55
	 * @CreateBy 韩武
	 * @param gdid
	 */
	@Override
	public void updateFlbWorkerByWorkorderWorker(String gdid) {
		// 删除FLB的工单的工作者
		workOrderWorkerMapper.deleteFlbWorkerByGdid(gdid);
		// 复制135工单的工作者到FLB中
		workOrderWorkerMapper.copyWorkOrderWorkerToFlb(gdid);
	}
	
}