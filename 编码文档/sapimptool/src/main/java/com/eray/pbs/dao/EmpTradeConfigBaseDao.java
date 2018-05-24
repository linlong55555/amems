package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.EmpTradeConfigBase;

/**
 * 员工与每天 的 available hrs 对应关系
 * @author ganqing
 *
 */
public interface EmpTradeConfigBaseDao extends
		PagingAndSortingRepository<EmpTradeConfigBase, Long>,
		JpaSpecificationExecutor<EmpTradeConfigBase> {

	/**
	 * 通过empid获得员工信息
	 * @param empId 员工编号
	 * @return EmpTradeConfigBase
	 */
	EmpTradeConfigBase findByEmpId(String empId);
}
