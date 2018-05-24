package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.ImptError;

public interface ImptErrorDao extends PagingAndSortingRepository<ImptError, Long>, JpaSpecificationExecutor<ImptError>
{
}
