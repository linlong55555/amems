package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.AccessMh;

public interface AccessMhDao extends PagingAndSortingRepository<AccessMh, Long>, JpaSpecificationExecutor<AccessMh>
{
}
