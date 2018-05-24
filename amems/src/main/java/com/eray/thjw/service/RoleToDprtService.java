package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.RoleToDprt;


/**
 * @author liub
 * @description 角色service,用于业务逻辑处理
 * @develop date 2016.07.29
 */
public interface RoleToDprtService {

	public List<RoleToDprt> queryAllPageList(RoleToDprt roleToDprt) throws Exception;
	
	public int queryCount(RoleToDprt roleToDprt) throws Exception;
	
	public void save(RoleToDprt roleToDprt) throws Exception;
	
	public void delete(RoleToDprt roleToDprt) throws Exception;
	
	public void modify(RoleToDprt roleToDprt) throws Exception;
	
	public RoleToDprt selectNum(RoleToDprt roleToDprt) throws Exception;
	
}
