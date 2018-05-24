package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.DbTaskZonePbs;

public interface DbTaskZonePbsDao extends PagingAndSortingRepository<DbTaskZonePbs, Long>, JpaSpecificationExecutor<DbTaskZonePbs>
{
	@Modifying
    @Query("delete from DbTaskZonePbs o where o.taskId = ?1")
	void deleteByTaskId(Long taskId);
}