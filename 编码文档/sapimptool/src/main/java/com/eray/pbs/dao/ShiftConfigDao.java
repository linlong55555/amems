package com.eray.pbs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.ShiftConfig;

/**
 * 排班配置 2016.07.22
 * @author ganqing
 *
 */
public interface ShiftConfigDao extends
		PagingAndSortingRepository<ShiftConfig, Long>,
		JpaSpecificationExecutor<ShiftConfig> {
	
	@Query("select o from ShiftConfig o")
	List<ShiftConfig> getAllShifts();

}
