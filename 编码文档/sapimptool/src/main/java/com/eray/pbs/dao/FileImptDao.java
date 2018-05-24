package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.FileImpt;

/**
 * 
 * 操作类：导入文件表
 * @since 1.0 19 Nov 2014
 * @author Hao.Z
 */
public interface FileImptDao extends PagingAndSortingRepository<FileImpt,Long>, JpaSpecificationExecutor<FileImpt>{
}
