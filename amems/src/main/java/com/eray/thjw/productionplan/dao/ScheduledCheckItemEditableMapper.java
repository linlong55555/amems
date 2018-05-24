package com.eray.thjw.productionplan.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.eray.thjw.productionplan.po.ScheduledCheckItem;



/**
 * 编辑区-定检件定检项目 dao
 * @author hanwu
 *
 */
@Component("ScheduledCheckItemEditableMapper")
public interface ScheduledCheckItemEditableMapper {
	
	int insertSelective(ScheduledCheckItem record);
	
	List<ScheduledCheckItem> queryByZjqdid(String zjqdid);
	
	int updateByPrimaryKeySelective(ScheduledCheckItem record);
	
	int deleteByZjqdid(ScheduledCheckItem record);
    
	int cascadeDeleteByZjqdid(ScheduledCheckItem record);
	
    ScheduledCheckItem selectXlh(ScheduledCheckItem  sc)throws RuntimeException;            //工单查询飞机序号和左发
    
    /**
     * 找到维修方案改版影响的定检项目
     * @param param
     * @return
     */
    List<ScheduledCheckItem> selectInfluencedByWxfaData(Map<String, Object> param);	
    
    /**
     * 更新同步标识为待同步
     * @param ids
     * @return
     */
    int updateTbbs(Map<String, Object> param);
}