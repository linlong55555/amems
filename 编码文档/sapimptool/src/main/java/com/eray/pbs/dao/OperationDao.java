package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.Operation;

public interface OperationDao extends PagingAndSortingRepository<Operation,Long>, JpaSpecificationExecutor<Operation>
{

}
