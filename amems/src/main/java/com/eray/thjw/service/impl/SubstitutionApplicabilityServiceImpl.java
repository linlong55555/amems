package com.eray.thjw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.SubstitutionApplicabilityMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.SubstitutionApplicability;
import com.eray.thjw.service.SubstitutionApplicabilityService;
@Service
public class SubstitutionApplicabilityServiceImpl implements SubstitutionApplicabilityService{

	@Autowired
	private SubstitutionApplicabilityMapper substitutionApplicabilityMapper;
	/**
	 * @author sunji
	 * @description 根据mainid查询数据
	 * @param substitution
	 * @return List<Substitution>
	 */
	public List<SubstitutionApplicability> queryAllByMainid(String mainid)
			throws BusinessException {
		return substitutionApplicabilityMapper.queryAllByMainid(mainid);
	}

}
