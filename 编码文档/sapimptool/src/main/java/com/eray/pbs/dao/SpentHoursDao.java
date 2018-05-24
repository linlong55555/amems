package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.SpentHours;

public interface SpentHoursDao extends PagingAndSortingRepository<SpentHours, Long>, JpaSpecificationExecutor<SpentHours>
{
}
