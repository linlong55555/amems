package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.ShiftInOutHistory;
/**
 * 同步处理的最原始数据 2016.07.22
 * @author ganqing
 *
 */
public interface ShiftInOutHistoryDao extends
		PagingAndSortingRepository<ShiftInOutHistory, Long>,
		JpaSpecificationExecutor<ShiftInOutHistory> {

}
