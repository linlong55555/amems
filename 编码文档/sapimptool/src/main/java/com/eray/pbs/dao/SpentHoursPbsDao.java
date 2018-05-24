package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.SpentHoursPbs;

public interface SpentHoursPbsDao extends PagingAndSortingRepository<SpentHoursPbs, Long>, JpaSpecificationExecutor<SpentHoursPbs>
{
}