package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.OrderSource;
public interface OrderSourceMapper {
    int insert(OrderSource record);

    int insertSelective(OrderSource record);     //增加非例行工单的指令来源表
    
    public List<OrderSource> findOne(String pgdid);//根据评估ID查询所有指令编号
    
    List<OrderSource> selectByOrder(OrderSource record);     //查询出工单的评估单号
    
    int doByOrder(OrderSource record);                                        //修改工单的评估单号
    
    int deleteOrderSourceByZlid(String zlid);                                     //删除工单的评估单号
    
    int deleteOrderSourceByPgdid(String pgdid);                                     //删除工单的评估单号
    
    
    
    
    List<Map<String, Object>>  selectOrderSourceListByZlid(String zlbh);   
    
    int insertOrderSource(List<OrderSource> list);   
    
    
    int deleteOrderSourceByZlbhAndpgdid(OrderSource orderSource);

    List<OrderSource> selectOrderSourceList(Map<String, Object> param);
    
    List<OrderSource> queryOrderSourceListByZlxl(OrderSource orderSource);
    
    OrderSource selectByZlbhKey(String zlbh,String dprtcode);
    /**
	 * @author liub
	 * @description 根据评估单id查询下达指令数据
	 * @param 
	 * @return List<Map<String, Object>>
	 * @develop date 2017.03.14
	 */
    List<OrderSource> queryByPgdId(String pgdid);
    /**
	 * @author sunji
	 * @description 根据评估单id查询下达指令数据
	 * @param 
	 * @return List<OrderSource>
	 */
    List<OrderSource> queryByTechnicalfileIds(List<String> pgdids)throws BusinessException;
}