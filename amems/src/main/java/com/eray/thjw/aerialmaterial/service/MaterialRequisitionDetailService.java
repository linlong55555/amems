package com.eray.thjw.aerialmaterial.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.MaterialRequisitionDetail;


/**
 * 航材领用明细DAO
 * @author xu.yong
 *
 */
public interface MaterialRequisitionDetailService {
	
	/**
	 * 按条件查询一页领用申请明细
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<MaterialRequisitionDetail> queryAllPageList(MaterialRequisitionDetail materialRequisitionDetail)  throws RuntimeException;
	
 	/**
	 * 按条件查询领用申请明细总数
	 * @param param
	 * @param pagination
	 * @return
	 */
	 int queryCount(MaterialRequisitionDetail materialRequisitionDetail) throws RuntimeException;

	void updateByPrimaryKey(MaterialRequisitionDetail materialRequisitionDetail);

	MaterialRequisitionDetail selectByPrimaryKey(String lydmxid) throws RuntimeException;

	void updateByPrimaryKeySelectives(
			MaterialRequisitionDetail materialRequisitionDetail)throws RuntimeException;
	
    
}