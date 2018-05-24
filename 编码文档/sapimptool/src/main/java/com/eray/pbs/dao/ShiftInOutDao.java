package com.eray.pbs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.ShiftInOut;

/**
 * 写入in & out 数据，该部分数据允许用户修改 2016.07.22
 * @author ganqing
 *
 */
public interface ShiftInOutDao extends
		PagingAndSortingRepository<ShiftInOut, Long>,
		JpaSpecificationExecutor<ShiftInOut> {
	/**
	 * 获得某天某个员工的在某一天的in & out打卡信息
	 * @param empid 员工编号
	 * @param markDate 实际的年月日yyyyMMdd
	 * @return List<ShiftInOut>
	 */
	@Query("from ShiftInOut o where o.empId =?1 and o.markDate = ?2 order by o.id asc")
	List<ShiftInOut> getShiftInOutByEmpIdAndMarkDateOrderByIdAsc(String empid,String markDate);
	
	/**
	 * 根据员工编号和日期获得考勤信息
	 * @param empid 员工编号
	 * @param markDate 日期
	 * @return  List<ShiftInOut>
	 */
	List<ShiftInOut> findByEmpIdAndMarkDate(String empid, String markDate);

}
