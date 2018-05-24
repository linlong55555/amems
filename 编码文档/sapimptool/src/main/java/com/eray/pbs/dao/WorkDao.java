package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.Work;

public interface WorkDao extends PagingAndSortingRepository<Work, Long>,
		JpaSpecificationExecutor<Work> {
	
	
}
