package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Oilprice;

public interface OilpriceService {

	/**
	 * @author sunji
	 * @description 油品查询
	 * @develop date 2016.08.15
	 */
	public List<Oilprice> selectByOil(String dprtcode);
	/**
	 * @author sunji
	 * @description 油品价格查询
	 * @develop date 2016.08.15
	 */
	public List<Oilprice> selectByYpgg(Oilprice oilprice);

	/**
	 * @author sunji
	 * @description 添加油品价格
	 * @develop date 2016.08.15
	 */
	public void save(Oilprice oilprice);
	/**
	 * @author sunji
	 * @description 修改油品价格
	 * @develop date 2016.08.15
	 */
	public void update(Oilprice oilprice)throws BusinessException;
	/**
	 * @author sunji
	 * @description 油品价格作废
	 * @develop date 2016.08.15
	 */
	 public void invalid(Oilprice oilprice)throws BusinessException;
}
