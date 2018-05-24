package com.eray.thjw.oil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Oil;
import com.eray.thjw.po.Oilprice;

public interface OilService  {

	/**
	 * 根据条件查询油品规格
	 */
	
	public List<Oil> queryAll(Oil oil) throws BusinessException;
	/**
	 * @author sunji
	 * @description 添加油品规格
	 */
	public void save(Oil oil);
	/**
	 * @author sunji
	 * @description 修改油品规格
	 */
	public void update(Oil oil)throws BusinessException;
	/**
	 * @author sunji
	 * @description 油品规格作废
	 */
	 public void invalid(Oil oil)throws BusinessException;
	 /**
	  * @author sunji
	  * @description 验证唯一
	  */
	 public int validationYpgg(Oil oil)throws BusinessException;
	 /**
	  * @author sunji
	  * @description 根据组织机构查询所油品规格
	  */
	 public List<Oil> queryByDprtcode(String dprtcode)throws BusinessException;
	 
	
}
