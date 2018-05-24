package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.EmpAvailable;

/**
 * 某个员工某天的available hrs信息
 * @author ganqing
 *
 */
public interface EmpAvailableDao extends
		PagingAndSortingRepository<EmpAvailable, Long>,
		JpaSpecificationExecutor<EmpAvailable> {
	
	/**
	 * 根据员工编号 & yyyyMMdd查找员工每天的available hrs信息
	 * @param empId 员工编号
	 * @param markDate 对应的日期yyyyMMdd
	 * @return EmpAvailable
	 */
	EmpAvailable findByEmpIdAndMarkDate(String empId, String markDate);

}
