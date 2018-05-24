package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.MaterialShortage;

public interface MaterialShortageDao extends PagingAndSortingRepository<MaterialShortage,Long>, JpaSpecificationExecutor<MaterialShortage>
{

}
