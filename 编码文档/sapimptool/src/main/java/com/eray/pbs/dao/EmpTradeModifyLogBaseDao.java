package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.EmpTradeModifyLogBase;

/**
 * 修改员工与部门之间trade的log记录
 * @author ganqing
 *
 */
public interface EmpTradeModifyLogBaseDao extends
		PagingAndSortingRepository<EmpTradeModifyLogBase, Long>,
		JpaSpecificationExecutor<EmpTradeModifyLogBase> {

}
