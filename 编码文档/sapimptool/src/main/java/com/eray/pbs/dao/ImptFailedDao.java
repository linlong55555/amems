package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.ImptFailed;

/**
 * 
 * 操作类：导入记录统计
 * @since 1.0 15 Aug 2014
 * @author Hao.Z
 */
public interface ImptFailedDao extends PagingAndSortingRepository<ImptFailed,Long>, JpaSpecificationExecutor<ImptFailed>{
}
