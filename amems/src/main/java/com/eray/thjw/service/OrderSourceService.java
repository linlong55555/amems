package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.OrderSource;
import com.eray.thjw.po.WorkOrder;


public interface OrderSourceService {
	
	
	public List<OrderSource> findOne(String pgdid);//根据评估ID查询所有指令编号
	
	List<OrderSource> selectByOrder(OrderSource orderSource);     //查询出工单的评估单号
	
	public int insert(OrderSource orderSource);
	
	/**
	 * @author meizhiliang
	 * @description 根据指令id查询下达指令来源信息列表
	 * @param zlid 指令id
	 * @develop date 2016.08.18
	 * @return Map<String, Object>下达指令来源信息列表
	 */
	public List<Map<String, Object>> queryOrderSourceListByZlid(String zlid);
	/**
	 * @author sunji
	 * @description 根据指令序号查询下达指令来源信息列表
	 * @param zlid 指令id
	 * @develop date 2016.08.18
	 * @return Map<String, Object>下达指令来源信息列表
	 */
	public List<OrderSource> queryOrderSourceListByZlxl(OrderSource orderSource);
	/**
	 * @author sunji
	 * @description 根据指令编号，评估单id进行删除操作
	 * @param zlid 指令id
	 * @develop date 2016.08.18
	 * @return map
	 */
	public Map<String, Object> deleteOrderSource(OrderSource orderSource);
	/**
	 * @author sunji
	 * @description 根据指令id，删除所有评估单
	 * @param zlid 指令id
	 * @develop date 2016.08.18
	 * @return map
	 */
	public int deleteOrderSourceByZlid(String id);

	/**
	 * @description 增加指令来源表的数据
	 * @author meizhiliang
	 * @param list
	 * @param gdlx 
	 * @return
	 * @throws RuntimeException
	 */
	public int insertOrderSource(WorkOrder workorder)throws RuntimeException;   //增加非例行工单的指令来源表
	
	public int doByOrder(WorkOrder wrokorder)throws RuntimeException;                                    //操作工单的评估单号
	/**
	 * @description 根据指令编号查询数据
	 * @author sunji
	 * @param zlbh
	 * @return ordersource
	 * @throws RuntimeException
	 */
	public OrderSource selectByZlbhKey(String zlbh,String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据评估单id查询下达指令数据
	 * @param 
	 * @return List<Map<String, Object>>
	 * @develop date 2017.03.14
	 */
	public List<OrderSource> queryByPgdId(String pgdid) throws RuntimeException;
	/**
	 * @author sunji
	 * @description 根据评估单id查询下达指令数据
	 * @param 
	 * @return List<OrderSource>
	 */
	public List<OrderSource> queryByTechnicalfileIds(List<String> pgdids) throws BusinessException;
	
}
