package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.RevisionPbs;

public interface RevisionPbsDao extends PagingAndSortingRepository<RevisionPbs, Long>, JpaSpecificationExecutor<RevisionPbs>
{
}
