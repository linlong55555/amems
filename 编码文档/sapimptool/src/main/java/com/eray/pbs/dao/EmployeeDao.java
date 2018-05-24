package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.Employee;

public interface EmployeeDao extends PagingAndSortingRepository<Employee,Long>, JpaSpecificationExecutor<Employee>{
}
