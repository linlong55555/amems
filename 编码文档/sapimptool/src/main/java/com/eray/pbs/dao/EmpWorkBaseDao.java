package com.eray.pbs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.EmpWorkBase;

/**
 * 员工的book on & book off信息 2016.08.01
 * @author ganqing
 *
 */
public interface EmpWorkBaseDao extends
		PagingAndSortingRepository<EmpWorkBase, Long>,
		JpaSpecificationExecutor<EmpWorkBase> {
	
	/**
	 * 获得员工打卡的详细信息，并按员工编号排序
	 * @param backsapstatus 状态为N
	 * @return List<EmpWorkBase>
	 */
	List<EmpWorkBase> findByBacksapstatusAndWorkOverOrderByToEidAsc(String backsapstatus, String workOver);

}
