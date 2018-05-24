package com.eray.thjw.productionplan.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;



/**
 * 生效区-定检件定检项目 dao
 * @author zhuchao
 *
 */
@Component("scheduledCheckItemMapper")
public interface ScheduledCheckItemMapper {
    
    public List<ScheduledCheckItem> queryAllPageList(ScheduledCheckItem scheduledCheckItem) throws Exception;
    
    int queryCount(ScheduledCheckItem scheduledCheckItem) throws Exception;
     
 	public Map<String, Object> checkUpdMt(User user, String ids)throws Exception;
 		
    public Map<String, Object> subScheduledcheckitem(ScheduledCheckItem scheduledCheckItem) throws Exception;
    
    public void updatePlaneEditable(ScheduledCheckItem scheduledCheckItem1) ;

	public List<ScheduledCheckItem> queryAllPageList1(
			ScheduledCheckItem scheduledCheckItem)throws Exception;
}