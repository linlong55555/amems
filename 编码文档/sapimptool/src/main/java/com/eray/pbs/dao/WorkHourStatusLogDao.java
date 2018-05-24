package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.WorkHourStatusLog;
/**
 * 记录工单是否拷贝工时的标识位log
 * @author ganqing
 *
 */
public interface WorkHourStatusLogDao extends
		JpaSpecificationExecutor<WorkHourStatusLog>,
		PagingAndSortingRepository<WorkHourStatusLog, Long> {

}
