package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.MaterialShortagePbs;

public interface MaterialShortagePbsDao extends PagingAndSortingRepository<MaterialShortagePbs,Long>, JpaSpecificationExecutor<MaterialShortagePbs>
{
	@Modifying
    @Query("update MaterialShortagePbs  m set m.isLast =?1")
	int resetIsLast(int islast);
}
