package com.eray.thjw.project2.dao;

import java.util.HashMap;

import com.eray.thjw.project2.po.EngineeringOrderSub;


/**
 * @Description EOSub Mapper
 * @CreateTime 2017-8-22 上午9:39:16
 * @CreateBy 雷伟
 */
public interface EngineeringOrderSubMapper {
	
	/**
	 * @Description 新增EO从表数据
	 * @CreateTime 2017-8-22 上午10:12:13
	 * @CreateBy 雷伟
	 * @param engineeringOrderSub EOSub
	 * @return
	 */
	int insert(EngineeringOrderSub engineeringOrderSub);
	
	/**
	 * @Description 修改EO从表数据
	 * @CreateTime 2017-8-22 上午10:12:17
	 * @CreateBy 雷伟
	 * @param engineeringOrderSub EOSub
	 * @return
	 */
	int updateByPrimaryKeySelective(EngineeringOrderSub engineeringOrderSub);

	/**
	 * @Description 获取EO从表信息
	 * @CreateTime 2017-8-25 上午8:42:07
	 * @CreateBy 雷伟
	 * @param paramMap 参数
	 * @return
	 */
	EngineeringOrderSub selectById(HashMap<String, String> paramMap);
	
	/**
	 * @Description 删除
	 * @CreateTime 2017-9-13 下午5:33:11
	 * @CreateBy 雷伟
	 * @param id
	 * @return
	 */
	void deleteByMainId(String id);
}