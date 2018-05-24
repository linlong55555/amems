package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.EmployeePbs;

public interface EmployeePbsDao extends PagingAndSortingRepository<EmployeePbs,Long>, JpaSpecificationExecutor<EmployeePbs>{
}
