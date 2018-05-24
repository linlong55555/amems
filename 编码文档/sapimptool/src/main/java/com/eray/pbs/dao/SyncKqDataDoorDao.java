package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.SyncKqDataDoor;

/**
 * 保存in&out原始数据DAO 2016.07.21
 * @author ganqing
 *
 */
public interface SyncKqDataDoorDao extends
		PagingAndSortingRepository<SyncKqDataDoor, Long>,
		JpaSpecificationExecutor<SyncKqDataDoor> {
	

}
