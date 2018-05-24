package com.eray.pbs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.WorkHoursPbs;

public interface WorkHoursPbsDao extends PagingAndSortingRepository<WorkHoursPbs, Long>, JpaSpecificationExecutor<WorkHoursPbs>
{
	 @Query("select o from WorkHoursPbs o where o.workId = ?1")
	 List<WorkHoursPbs> findByWorkId(Long workId);
	 /**
	  * 根据工单编号删除工单对应的上传工时信息
	  * @param workId
	  */
	 @Modifying
	 @Query("delete from WorkHoursPbs o where o.workId = ?1")
	 void deleteByWorkId(Long workId);
}
