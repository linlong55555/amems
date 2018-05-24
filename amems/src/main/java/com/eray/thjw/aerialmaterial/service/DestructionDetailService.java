package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Destruction;
import com.eray.thjw.aerialmaterial.po.DestructionDetail;
import com.eray.thjw.exception.BusinessException;

public interface DestructionDetailService {

	/**
	 * @author sunji
	 * @description  根据条件分页销毁记录清单
	 * @param destructionDetail
	 * @return List<Destruction>
	 */
	public Map<String, Object> queryAll(DestructionDetail destructionDetail) throws BusinessException;
	/**
	 * @author sunji
	 * @description  根据条件不分页销毁记录清单
	 * @param destructionDetail
	 * @return List<Destruction>
	 */
	public List<DestructionDetail> queryAllPageList(DestructionDetail destructionDetail) throws BusinessException;
	
}
