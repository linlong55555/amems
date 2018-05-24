package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Send;

public interface SendService {

	public List<Send> querySendAll(Send record);
	
	public int insert(Send record);
	
	public int insertSelective(Send record);
	
	public int deleteSend(String mainid);
	
	public int delete(Send send);
	
	/**
	 * @author sunji
	 * @description 根据条件查询
	 * @param request,model
	 * @return list
	 * @develop date 2016.08.05
	 */
	public List<Send> queryAll(Send send)throws BusinessException;
	/**
	 * @author sunji
	 * @description 查询
	 * @param request,model
	 * @return list
	 * @develop date 2016.08.05
	 */
	public List<Send> selectByPrimaryKey(Send send);
	/**
	 * @author sunji
	 * @description 修改
	 * @param request,model
	 * @return void
	 * @develop date 2016.08.05
	 */
	public void updateByPrimaryKeySelective(Send send);
	/**
	 * @author sunji
	 * @description 修改该通告下的传阅为未读
	 * @param request,model
	 * @return void
	 * @develop date 2016.08.05
	 */
	public void updateByMainid(Send send);
	
	/**
	 * @author sunji
	 * @description 根据mainid和jsrid修改接收状态
	 * @param request,model
	 * @return void
	 * @develop date 2016.08.05
	 */
	public void updateJszt(Send send);
}
