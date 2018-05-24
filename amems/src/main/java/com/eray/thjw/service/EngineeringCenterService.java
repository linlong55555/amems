package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;

public interface EngineeringCenterService {
	 
	/**
	 * @author liub
	 * @description 根据评估单id查询下达指令数据
	 * @param id
	 * @return List<Map<String, Object>>
	 * @develop date 2017.03.14
	 */
	public List<Map<String, Object>> queryByPgdId(String id) throws BusinessException;
}
