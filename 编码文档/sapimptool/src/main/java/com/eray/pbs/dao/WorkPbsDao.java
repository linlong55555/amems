package com.eray.pbs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.WorkPbs;

public interface WorkPbsDao extends PagingAndSortingRepository<WorkPbs, Long>, JpaSpecificationExecutor<WorkPbs>
{
	@Modifying
    @Query("update WorkPbs w set w.msStatus = ?1 where w.msStatus = ?2")
	int resetMsStatus(int aftStatus,int befStatus);

	@Query("select w from WorkPbs w where w.id = ?1")
	List<WorkPbs> findById(Long id);
	
	/**
	 * 根据工单编号，工包编号获得工单信息
	 * @param wid 工单编号
	 * @param rid 工包编号
	 * @return Work 工包信息
	 */
	WorkPbs findByWidAndRid(String wid, String rid);
	
	/**
	 * 根据工单编号获得工包信息
	 * @param wid 工单编号
	 * @return WorkPbs
	 */
	WorkPbs findByWid(String wid);
}
