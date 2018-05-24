package com.eray.thjw.sched.dao;

import java.util.List;

import com.eray.thjw.sched.po.ProductPlanSched;

public interface ProductPlanSchedMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProductPlanSched record);

    int insertSelective(ProductPlanSched record);

    ProductPlanSched selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProductPlanSched record);

    int updateByPrimaryKey(ProductPlanSched record);

	List<ProductPlanSched> selectList(ProductPlanSched productPlanSched);

	void insertList(List<ProductPlanSched> plist);

	/**
	 * 删除所有预排信息
	 */
	void deleteAll();
}