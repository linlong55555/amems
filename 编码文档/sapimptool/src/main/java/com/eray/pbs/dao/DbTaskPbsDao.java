package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.DbTaskPbs;

public interface DbTaskPbsDao extends PagingAndSortingRepository<DbTaskPbs, Long>, JpaSpecificationExecutor<DbTaskPbs>
{
}
