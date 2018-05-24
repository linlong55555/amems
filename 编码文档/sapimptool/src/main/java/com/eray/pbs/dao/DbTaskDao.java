package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.DbTask;

public interface DbTaskDao extends PagingAndSortingRepository<DbTask, Long>, JpaSpecificationExecutor<DbTask>
{
}
