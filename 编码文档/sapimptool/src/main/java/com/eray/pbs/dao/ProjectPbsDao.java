package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.ProjectPbs;

public interface ProjectPbsDao extends PagingAndSortingRepository<ProjectPbs, Long>, JpaSpecificationExecutor<ProjectPbs>
{
}
