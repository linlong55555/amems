package com.eray.thjw.productionplan.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.po.OtherWorkOrder;
import com.eray.thjw.productionplan.po.TimeControllWareItem;


/**
 * 虚拟字段:其他工单
 * @author linlong
 *
 */
public interface OtherWorkOrderMapper {
	
	public List<OtherWorkOrder> queryAllList(OtherWorkOrder otherWorkOrder) throws Exception;
	
	 public Map<String, Object> checkUpdMt(User userFromSession, String ids); //验证

	public void saveskj(TimeControllWareItem timeControllWareItem);

	public List<OtherWorkOrder> queryAllList1(OtherWorkOrder otherWorkOrder);

	public void update(OtherWorkOrder otherWorkOrder)throws Exception;
}