package com.eray.thjw.sched.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.eray.thjw.sched.dao.ProductPlanSchedMapper;
import com.eray.thjw.sched.dao.ServiceInstructionMapper;
import com.eray.thjw.sched.po.ServiceInstruction;

public class ProductPlanSchedServiceImpl implements ProductPlanSchedService {

	@Resource
	private ProductPlanSchedMapper  productPlanSchedMapper;
	@Resource
	private ServiceInstructionMapper  serviceInstructionMapper;
	@Override
	public Map<String, Object> queryPage(ServiceInstruction serviceInstruction) {
		Map<String, Object> result = null;
		return result;
	}
	
	
}
