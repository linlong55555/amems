package com.eray.pbs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.ActivityTypeReWorkCenter;

/**
 * Activity Type接口文件下载 2016.08.18
 * @author ganqing
 *
 */
public interface ActivityTypeReWorkCenterDao extends
		PagingAndSortingRepository<ActivityTypeReWorkCenter, Long>,
		JpaSpecificationExecutor<ActivityTypeReWorkCenter> {
	/**
	 * 获得当前时间的 Activity Type的配对情况
	 * @param markDate 备注时间
	 * @return 数量
	 */
	@Query("select count(1) from ActivityTypeReWorkCenter o where o.remarkDate = ?1")
	Long getCueerntActivityCount(String markDate);
	
	/**
	 * 通过workCenter获得activity Type
	 * @param workCenter 工作组
	 * @return List<ActivityTypeReWorkCenter>
	 */
	List<ActivityTypeReWorkCenter> findByWorkCenter(String workCenter);
	
	
	

}
