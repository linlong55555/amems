package com.eray.thjw.sched.service.impl;

import java.util.Map;

import com.eray.thjw.sched.po.ServiceInstruction;

public interface ProductPlanSchedService {
	
	public Map<String, Object> queryPage(ServiceInstruction serviceInstruction);

}
