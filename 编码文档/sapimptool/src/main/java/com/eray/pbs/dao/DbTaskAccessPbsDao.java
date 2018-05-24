package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.DbTaskAccessPbs;

public interface DbTaskAccessPbsDao extends PagingAndSortingRepository<DbTaskAccessPbs, Long>, JpaSpecificationExecutor<DbTaskAccessPbs>
{
	@Modifying
    @Query("delete from DbTaskAccessPbs o where o.taskId = ?1")
	void deleteByTaskId(Long taskId);
}