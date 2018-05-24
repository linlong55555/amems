package com.eray.thjw.productionplan.dao;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.productionplan.po.TimeControllWareItem;


public interface TimeControllWareItemMapper {
	
	public List<TimeControllWareItem> queryAllPageList(TimeControllWareItem timeControllWareItem) throws BusinessException;
	
	public int queryCount(TimeControllWareItem timeControllWareItem) throws BusinessException;

	public List<TimeControllWareItem> queryAllPageList1(
			TimeControllWareItem timeControllWareItem)throws BusinessException;

	public void update(TimeControllWareItem timeControllWareItem)throws BusinessException;
	
	
}
