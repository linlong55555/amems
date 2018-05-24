package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.OperationPbs;

public interface OperationPbsDao extends
		PagingAndSortingRepository<OperationPbs, Long>,
		JpaSpecificationExecutor<OperationPbs> {
	
	OperationPbs findByWidAndOperationNumber(String wid, String operationNumber);
}
