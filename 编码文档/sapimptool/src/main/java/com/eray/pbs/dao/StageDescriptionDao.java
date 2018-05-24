package com.eray.pbs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.StageDescription;

public interface StageDescriptionDao extends PagingAndSortingRepository<StageDescription, Long>, JpaSpecificationExecutor<StageDescription>
{
	@Query("select o from StageDescription o where o.stage = ?1")
	List<StageDescription> findByStage(String stage);
}