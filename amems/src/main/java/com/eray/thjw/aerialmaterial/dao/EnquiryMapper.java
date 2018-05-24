package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Enquiry;

public interface EnquiryMapper {
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询询价列表信息
	 * @param enquiry
	 * @return Map<String, Object>
	 * @develop date 2016.10.18
	 */
	public List<Map<String, Object>> queryAllPageList(Enquiry record);
		
	
	/**
	 * @author liub
	 * @description  根据提订详情id查询询价信息
	 * @param mainid
	 * @return List<Enquiry>
	 * @develop date 2016.10.20
	 */
	public List<Enquiry> queryEnquiryListByMainId(String mainid);
	
    int deleteByPrimaryKey(String id);

    int insert(Enquiry record);

    int insertSelective(Enquiry record);

    Enquiry selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Enquiry record);

    int updateByPrimaryKey(Enquiry record);
}