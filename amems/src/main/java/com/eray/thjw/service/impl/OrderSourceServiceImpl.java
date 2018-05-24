package com.eray.thjw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.OrderSourceMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.OrderSource;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.OrderSourceService;
import com.eray.thjw.util.ThreadVarUtil;


/**
 * @author liub
 * @description 用户service,用于业务逻辑处理
 * @develop date 2016.07.25
 */
@Service
public class OrderSourceServiceImpl implements OrderSourceService {
	
	@Autowired
	private OrderSourceMapper orderSourceMapper;
 
    @Resource
    private CommonRecService commonRecService;
    
	public List<OrderSource> findOne(String pgdid) {
		
		return orderSourceMapper.findOne(pgdid);
	}

	@Override
	public int insert(OrderSource orderSource) {
		return orderSourceMapper.insert(orderSource);
	}
	
	/**
	 * 根据指令id查询下达指令来源信息列表的实现方法
	 */
	@Override
	public List<Map<String, Object>> queryOrderSourceListByZlid(String zlid) {
		return orderSourceMapper.selectOrderSourceListByZlid(zlid);
	}

	/**
	 * @author sunji
	 * @description 根据指令编号，评估单id进行删除操作
	 * @param zlid 指令id
	 * @develop date 2016.08.18
	 * @return OrderSource 下达指令来源信息列表
	 */
	public Map<String, Object> deleteOrderSource(OrderSource orderSource) {
		Map<String , Object> map=new HashMap<String , Object>();
		int i =orderSourceMapper.deleteOrderSourceByZlbhAndpgdid(orderSource);
		if(i!=1){
			map.put("state", "error");
			map.put("massage", "系统异常");
			return map;
		}
		map.put("state", "success");
		map.put("massage", "删除成功");
		return map;
	}
	/**
	 * @author sunji
	 * @description 根据指令id，删除所有评估单
	 * @param zlid 指令id
	 * @develop date 2016.08.18
	 * @return map
	 */
	public int deleteOrderSourceByZlid(String id) {
		return orderSourceMapper.deleteOrderSourceByZlid(id);
	}

	/**
	 * @author meizhiliang
	 * @description 增加下达指令来源
	 * @develop date 2016.08.18
	 * @return map
	 */

	public int insertOrderSource(WorkOrder workorder)throws RuntimeException {
		int count=0;
		List<OrderSource> list=workorder.getBaseWorkOrder().getOrderSourceList();
		if (list!=null && !list.isEmpty()) {
			User user = ThreadVarUtil.getUser();
			for (OrderSource orderSource : list) {
					orderSource.setDprtcode(user.getJgdm());
					orderSource.setZlid(workorder.getId());
					orderSource.setZlbh(workorder.getGdbh());
					count=orderSourceMapper.insertSelective(orderSource);
			}
		}
		return count;
	}
	@Override
	public int doByOrder(WorkOrder wrokorder)throws RuntimeException {
		
		int count=0;
		List <OrderSource> list=wrokorder.getBaseWorkOrder().getOrderSourceList();
		count=orderSourceMapper.deleteOrderSourceByZlid(wrokorder.getId());
		User user = ThreadVarUtil.getUser();
		if (list!=null && !list.isEmpty()) {
			for (OrderSource orderSource : list) {
				orderSource.setDprtcode(user.getJgdm());
				orderSource.setZlxl(4);
				orderSource.setZlid(wrokorder.getId());
				orderSource.setZlbh(wrokorder.getGdbh());
			    count=orderSourceMapper.insertSelective(orderSource);
			}
		}
		return count;
	}

	@Override
	public List<OrderSource> selectByOrder(OrderSource orderSource) {
		return orderSourceMapper.selectByOrder(orderSource);
	}

	@Override
	public List<OrderSource> queryOrderSourceListByZlxl(OrderSource orderSource) {
		return orderSourceMapper.queryOrderSourceListByZlxl(orderSource);
	}

	/**
	 * @description 根据指令编号查询数据
	 * @author sunji
	 * @param zlbh
	 * @return ordersource
	 * @throws RuntimeException
	 */
	public OrderSource selectByZlbhKey(String zlbh,String dprtcode) {
		return orderSourceMapper.selectByZlbhKey(zlbh,dprtcode);
	}

	/**
	 * @author liub
	 * @description 根据评估单id查询下达指令数据
	 * @param 
	 * @return List<Map<String, Object>>
	 * @develop date 2017.03.14
	 */
	@Override
	public List<OrderSource> queryByPgdId(String pgdid) throws RuntimeException{
		return orderSourceMapper.queryByPgdId(pgdid);
	}
	/**
	 * @author sunji
	 * @description 根据评估单id查询下达指令数据
	 * @param 
	 * @return List<OrderSource>
	 */
	public List<OrderSource> queryByTechnicalfileIds(List<String> pgdids)
			throws BusinessException {
		return orderSourceMapper.queryByTechnicalfileIds(pgdids);
	}
}
