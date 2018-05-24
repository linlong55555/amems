package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.AccessMhPbs;

public interface AccessMhPbsDao extends PagingAndSortingRepository<AccessMhPbs, Long>, JpaSpecificationExecutor<AccessMhPbs>
{
}
