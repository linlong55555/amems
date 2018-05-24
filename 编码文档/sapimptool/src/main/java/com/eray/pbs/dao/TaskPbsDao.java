package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.TaskPbs;

public interface TaskPbsDao extends PagingAndSortingRepository<TaskPbs, Long>, JpaSpecificationExecutor<TaskPbs>
{
}
