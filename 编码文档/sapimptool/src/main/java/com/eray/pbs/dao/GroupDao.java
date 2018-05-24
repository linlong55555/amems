package com.eray.pbs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.Group;

public interface GroupDao extends PagingAndSortingRepository<Group, Long>, JpaSpecificationExecutor<Group>
{
    @Query("select o from Group o where o.name = ?1 and o.status='1'")
	public Group findByName(String workCenter);
    
    @Query("select o from Group o where o.status='1'")
  	public List<Group> getAllGroup();
}
