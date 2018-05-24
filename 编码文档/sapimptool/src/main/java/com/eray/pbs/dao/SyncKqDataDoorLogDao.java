package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.SyncKqDataDoorLog;

/**
 * 记录同步信息
 * @author ganqing
 *
 */
public interface SyncKqDataDoorLogDao extends
		PagingAndSortingRepository<SyncKqDataDoorLog, Long>,
		JpaSpecificationExecutor<SyncKqDataDoorLog> {
	
	@Query("select o.maxrecID from SyncKqDataDoorLog o where o.id = (select MAX(x.id) from SyncKqDataDoorLog x)")
	Long getMaxrecID();

}
