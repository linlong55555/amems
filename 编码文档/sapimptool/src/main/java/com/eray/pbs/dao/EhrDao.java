package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.Ehr;

public interface EhrDao extends PagingAndSortingRepository<Ehr,Long>, JpaSpecificationExecutor<Ehr>{
}
