package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.Status;

public interface StatusDao extends PagingAndSortingRepository<Status, Long>, JpaSpecificationExecutor<Status>
{
}
