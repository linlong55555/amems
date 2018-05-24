package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.Partner;

public interface PartnerDao extends PagingAndSortingRepository<Partner,Long>, JpaSpecificationExecutor<Partner>
{

}
