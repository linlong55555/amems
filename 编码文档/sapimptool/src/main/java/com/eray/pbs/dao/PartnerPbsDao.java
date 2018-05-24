package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.PartnerPbs;

public interface PartnerPbsDao extends PagingAndSortingRepository<PartnerPbs,Long>, JpaSpecificationExecutor<PartnerPbs>
{

}
