package com.eray.thjw.system.service;

import java.util.Map;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.system.po.SynRel;

/** 
 * @Description 系统同步关系服务
 * @CreateTime 2017年9月25日 上午11:27:53
 * @CreateBy 朱超	
 */
public interface SynRelService {

	/**
	 * 
	 * @Description 查询一页系统同步关系
	 * @CreateTime 2017年9月25日 上午11:31:15
	 * @CreateBy 朱超
	 * @param entity
	 * @return
	 */
	Map<String, Object> queryPage(SynRel entity);
	/**
	 * 
	 * @Description 保存系统同步关系
	 * @CreateTime 2017年9月25日 上午11:41:06
	 * @CreateBy 朱超
	 * @param entity
	 */
	void save(SynRel entity);

	/**
	 * 
	 * @Description 加载一个同步关系
	 * @CreateTime 2017年9月25日 上午11:49:09
	 * @CreateBy 朱超
	 */
	SynRel load(String id);

	/**
	 * 
	 * @Description 批量删除同步关系
	 * @CreateTime 2017年9月25日 上午11:42:02
	 * @CreateBy 朱超
	 * @param baseEntity
	 */
	void dels(BaseEntity baseEntity);

}
