package com.eray.thjw.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.OrderAttachmentMapper;
import com.eray.thjw.po.OrderAttachment;
import com.eray.thjw.service.OrderAttachmentService;
@Service
public class OrderAttachmentServiceImpl implements OrderAttachmentService{

	@Resource
	private OrderAttachmentMapper orderAttachmentMapper;
	
	/**
	 * @author sunji
	 * @description 添加附件
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public int insertSelective(OrderAttachment record) {
		return orderAttachmentMapper.insertSelective(record);
	}
	/**
	 * @author sunji
	 * @description 修改附件
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public int updateByPrimaryKeySelective(OrderAttachment record) {
		return orderAttachmentMapper.updateByPrimaryKeySelective(record);
	}
	/**
	 * @author sunji
	 * @description 根据id查询对象
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public OrderAttachment selectByPrimaryKey(String id) {
		return orderAttachmentMapper.selectByPrimaryKey(id);
	}
	/**
	 * @author sunji
	 * @description 根据mainid查询List
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public List<OrderAttachment> queryAll(String id) {
		return orderAttachmentMapper.queryAll(id);
	}
	/**
	 * @author sunji
	 * @description 根据id删除数据
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public int deleteByPrimaryKey(String id) {
		return orderAttachmentMapper.deleteByPrimaryKey(id);
	}

	

}
