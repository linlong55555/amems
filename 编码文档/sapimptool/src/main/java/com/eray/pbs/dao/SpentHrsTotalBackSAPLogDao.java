package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.SpentHrsTotalBackSAPLog;

public interface SpentHrsTotalBackSAPLogDao extends
		PagingAndSortingRepository<SpentHrsTotalBackSAPLog, Long>,
		JpaSpecificationExecutor<SpentHrsTotalBackSAPLog> {

}
