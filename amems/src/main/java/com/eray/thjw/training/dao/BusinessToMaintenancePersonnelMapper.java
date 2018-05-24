package com.eray.thjw.training.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.training.po.BusinessToMaintenancePersonnel;

public interface BusinessToMaintenancePersonnelMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusinessToMaintenancePersonnel record);

    int insertSelective(BusinessToMaintenancePersonnel record);

    BusinessToMaintenancePersonnel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusinessToMaintenancePersonnel record);

    int updateByPrimaryKey(BusinessToMaintenancePersonnel record);
    
    int updateByGwid(BusinessToMaintenancePersonnel record);
    
    int updateById(BusinessToMaintenancePersonnel record);

	List<BusinessToMaintenancePersonnel> queryAllPageList(
			BusinessToMaintenancePersonnel businessPer);

	List<BusinessToMaintenancePersonnel> queryByGwids(List<String> gwids);
	
	List<BusinessToMaintenancePersonnel> queryAllBywxrydaid(String wxrydaid);

	int deleteByGwid(String gwid);
	
	/**
	 * 根据维修人员档案id删除岗位
	 * @Description 
	 * @CreateTime 2017年8月11日 下午9:40:47
	 * @CreateBy 韩武
	 * @param wxrydaid
	 * @return
	 */
	int deleteByWxrydaid(String wxrydaid);

	int selectByPrimarygwwxId(BusinessToMaintenancePersonnel businessToMaintenancePersonnel);

	void deleteByPrimaryBt(
			BusinessToMaintenancePersonnel businessToMaintenancePersonnel);
	
	List<BusinessToMaintenancePersonnel> queryByGwidAndWxdaid(@Param("wxrydaid")String wxrydaid, @Param("gwid")String gwid);
	
	List<BusinessToMaintenancePersonnel> queryAll (BusinessToMaintenancePersonnel businessToMaintenancePersonnel);
	
}