package com.eray.pbs.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eray.pbs.po.Logon;

/**
 * 用户登录的Dao
 * @author ganqing
 *
 */
public interface LogonDao extends PagingAndSortingRepository<Logon, Long>,
		JpaSpecificationExecutor<Logon> {
	/**
	 * 根据员工编号查找员工的信息登录
	 * @param userid 员工编号
	 * @return 登录信息
	 */
	Logon findByUserid(String userid);

}
