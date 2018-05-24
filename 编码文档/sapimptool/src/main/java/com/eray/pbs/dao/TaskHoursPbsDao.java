package com.eray.pbs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.TaskHoursPbs;

public interface TaskHoursPbsDao extends PagingAndSortingRepository<TaskHoursPbs, Long>, JpaSpecificationExecutor<TaskHoursPbs>
{
	@Modifying
    @Query("delete from TaskHoursPbs o where o.taskId = ?1")
	void deleteByTaskId(Long taskId);
	
    @Query("select o from TaskHoursPbs o where o.taskId = ?1")
	List<TaskHoursPbs> findByTaskId(Long taskId);
}
