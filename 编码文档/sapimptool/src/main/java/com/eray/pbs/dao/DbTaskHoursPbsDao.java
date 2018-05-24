package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.DbTaskHoursPbs;

public interface DbTaskHoursPbsDao extends PagingAndSortingRepository<DbTaskHoursPbs, Long>, JpaSpecificationExecutor<DbTaskHoursPbs>
{
	@Modifying
    @Query("delete from DbTaskHoursPbs o where o.taskId = ?1")
	void deleteByTaskId(Long taskId);
}
