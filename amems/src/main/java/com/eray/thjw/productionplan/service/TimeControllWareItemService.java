package com.eray.thjw.productionplan.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.productionplan.po.TimeControlOptions;
import com.eray.thjw.productionplan.po.TimeControllWareItem;


public interface TimeControllWareItemService {
	
	/**
	 * 查询  b_s_003 生效区-飞机装机清单集合
	 * @param timeControllWareItem
	 * @return
	 * @throws Exception
	 */
	public List<TimeControllWareItem> queryAllPageList(TimeControllWareItem timeControllWareItem) throws Exception;
	
	public int queryCount(TimeControllWareItem timeControllWareItem) throws Exception;
	
	
	String shengyu(TimeControlOptions timeControlOptions) throws Exception;//计算剩余
	
	public Map<String, Object> subgd(TimeControllWareItem timeControllWareItem) throws Exception;//生成工单
	
	public Map<String, Object> checkUpdMt(User user,String ids) throws Exception;//时控件监控提交计划的验证
	
	
	public Map<String, Object> subjh(TimeControllWareItem timeControllWareItem)throws Exception;//时控件监控提交计划

	public List<TimeControllWareItem> geshihua(List<TimeControllWareItem> list,PlaneModelData planeModelData, Pagination pagination)throws Exception;


	String shengyutianshu(PlaneModelData planeModelData,Map<String, String> resultMap1) throws Exception;

	public void calculationScheduledCheck(PlaneModelData planeModelData, Map<String, String> resultMap,ScheduledCheckItem item,Map<String, BigDecimal> zqzMap,Map<String, String> cursjMap,Map<String, String>curjhMap)
			throws Exception;

	public List<TimeControllWareItem> queryAllPageList1(TimeControllWareItem timeControllWareItem)throws Exception;

	public Map<String, Object> saveJkbz(TimeControllWareItem timeControllWareItem)throws Exception;

	public Map<String, Object> checkdg(User userFromSession, String id)throws Exception;
	
	
	
	
	
}
