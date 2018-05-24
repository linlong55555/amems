package com.eray.pbs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.WorkCenter;

public interface WorkCenterDao extends PagingAndSortingRepository<WorkCenter, Long>, JpaSpecificationExecutor<WorkCenter>
{
	@Query("select o from WorkCenter o where o.workCenter = ?1")
	public WorkCenter findByName(String workCenter);

	@Query("select o from WorkCenter o")
	public List<WorkCenter> getAllWorkcenter();
}
