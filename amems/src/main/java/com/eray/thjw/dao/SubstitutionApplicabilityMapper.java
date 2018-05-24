package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.Substitution;
import com.eray.thjw.po.SubstitutionApplicability;

public interface SubstitutionApplicabilityMapper {
    int deleteByPrimaryKey(String id);

    int insert(SubstitutionApplicability record);

    int insertSelective(SubstitutionApplicability record);

    SubstitutionApplicability selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SubstitutionApplicability record);

    int updateByPrimaryKey(SubstitutionApplicability record);
    
    List<SubstitutionApplicability> queryAllByMainid(String mainid);
    
	/**
	 * @author sunji
	 * @description 根据mainid集合、机构代码查询等效替代部件适用性
	 * @param map
	 * @return List<Substitution>
	 */
	public List<SubstitutionApplicability> queryByMainidList(Map<String, Object> map);
}