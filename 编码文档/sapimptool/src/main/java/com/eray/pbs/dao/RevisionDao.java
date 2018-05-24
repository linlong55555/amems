package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.Revision;

public interface RevisionDao extends PagingAndSortingRepository<Revision, Long>, JpaSpecificationExecutor<Revision>
{
}
