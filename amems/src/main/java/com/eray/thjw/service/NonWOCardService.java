package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.NonWOCard;
import com.eray.thjw.po.WorkOrder;

public interface NonWOCardService {
	int insertSelective(WorkOrder workorder) throws RuntimeException;        //增加非例行工单 相关工卡的数据
   
	List<NonWOCard> selectByNonWOCardList(NonWOCard record);        //查询工单的关联工单集合
	/**
	 * @author sunji
	 * @description 根据基础id查询相关工单（卡）
	 * @develop date 2016.08.15
	 */
    public List<NonWOCard> selectedXggkList(NonWOCard nonWOCard)throws RuntimeException;  
	
    /**
	 * @author meizhiliang
	 * @description 更新相关工单
	 * @develop date 2016.08.15
	 */
    int doByWOCard(WorkOrder workorder);                 //更新工单的相关工单
    
    /**
   	 * @author meizhiliang
   	 * @description 删除工单的相关工单
   	 * @develop date 2016.08.15
   	 */
    int deleteById(String  id);                               //删除工单的相关工单
}
