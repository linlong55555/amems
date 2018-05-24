package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Substitution;
import com.eray.thjw.po.SubstitutionApplicability;

public interface SubstitutionApplicabilityService {

	
	/**
	 * @author sunji
	 * @description 根据mainid查询数据
	 * @param substitution
	 * @return List<Substitution>
	 */
	public List<SubstitutionApplicability> queryAllByMainid(String mainid)throws BusinessException;
}
