package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.OrderAttachment;

public interface OrderAttachmentService {
	
	/**
	 * @author sunji
	 * @description 添加附件
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public  int insertSelective(OrderAttachment record);
	/**
	 * @author sunji
	 * @description 修改附件
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public int updateByPrimaryKeySelective(OrderAttachment record);
	/**
	 * @author sunji
	 * @description 根据id查询对象
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public OrderAttachment selectByPrimaryKey(String id);
	/**
	 * @author sunji
	 * @description 根据mainid查询List
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public List<OrderAttachment> queryAll(String id);
	/**
	 * @author sunji
	 * @description 根据id删除数据
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public int deleteByPrimaryKey(String id);
}
