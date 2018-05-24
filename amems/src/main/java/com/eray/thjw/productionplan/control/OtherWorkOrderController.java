package com.eray.thjw.productionplan.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.productionplan.po.OtherWorkOrder;
import com.eray.thjw.productionplan.service.OtherWorkOrderService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.SessionUtil;

import enu.ThresholdEnum;

/**
 * @author linlong
 * @description 其他工单监控
 * @develop date 2016.07.25
 */
@Controller
@RequestMapping("/productionplan/otherworkorder")
public class OtherWorkOrderController extends BaseController {
	
	@Resource
	private OtherWorkOrderService otherWorkOrderService; //其他工单service
	
	@Resource
	private PlaneModelDataService planeModelDataService; //飞机数据service
	@Resource
	private MonitorsettingsService monitorsettingsService; //飞机数据service
	
	/**
	 * 其他工单查询集合
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "queryOtherWorkOrdertemList", method = RequestMethod.POST)
	public Map<String, Object> queryScheduledCheckItemList(@RequestBody OtherWorkOrder otherWorkOrder,HttpServletRequest request,Model model) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		otherWorkOrder.getParamsMap().put("userId", SessionUtil.getUserFromSession(request).getId());
		otherWorkOrder.getParamsMap().put("userType",SessionUtil.getUserFromSession(request).getUserType());
		
		List<OtherWorkOrder> list= otherWorkOrderService.queryAllList(otherWorkOrder);
		
		List<OtherWorkOrder> list1=new ArrayList<OtherWorkOrder>();
		PlaneModelData planeModelData=planeModelDataService.selectPlanes(otherWorkOrder.getFjzch(),otherWorkOrder.getDprtcode());//查询 d_004 所有部件日使用量
			
			for (OtherWorkOrder otherWorkOrder2 : list) {
				list1.add(otherWorkOrderService.canshu(otherWorkOrder2,planeModelData));//格式化计划，剩余，剩余天数
			}
			
			List<OtherWorkOrder> list2=new ArrayList<OtherWorkOrder>();
			if(otherWorkOrder.getPagination().getSort().equals("auto")){
				list2=sytspaixu(list);//剩余天数排序
			}else{
				list2=list;
			}
			
			resultMap.put("rows",list2);
	
		return resultMap;
	}
	
	/**
	 * 剩余天数排序
	 * @param list
	 * @return
	 */
	public List<OtherWorkOrder> sytspaixu(List<OtherWorkOrder> list){
		
		Collections.sort(list, new Comparator<OtherWorkOrder>() {
			@Override
			public int compare(OtherWorkOrder o1, OtherWorkOrder o2) {
				
				if(StringUtils.isNotBlank(o1.getRwid())){
					return 1;
				}
				if(StringUtils.isNotBlank(o2.getRwid())){
					return -1;
				}
				
				if(StringUtils.isNotBlank(o1.getShengyut()) && StringUtils.isNotBlank(o2.getShengyut())){
					return Integer.valueOf(o1.getShengyut()).compareTo( Integer.valueOf(o2.getShengyut()));
				}else{
					return StringUtils.isNotBlank(o1.getShengyut())?-1:(StringUtils.isNotBlank(o1.getShengyut())?1:0);
				}
			}
			
		});
		
		return list ;
	}
	
	/**
	 * @author ll
	 * @description 其他工单是否可以提交计划
	 * @param model,ids
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdMt")
	public Map<String, Object> checkUpdMt(HttpServletRequest request,String id
			) throws Exception {

		return otherWorkOrderService.checkUpdMt(SessionUtil.getUserFromSession(request),id);
	}
	
	/**
	 * 其他工单：提交计划
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@Privilege(code="productionplan:scheduledcheckitem:main:06")
	@ResponseBody
	@RequestMapping(value ="subjh", method = RequestMethod.POST)
	public Map<String, Object> subjh(@RequestBody OtherWorkOrder otherWorkOrder) throws BusinessException  {
		
		return otherWorkOrderService.subjh(otherWorkOrder);
	}

	/**
	 * @author ll
	 * @description 编辑监控备注
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	@Privilege(code="productionplan:scheduledcheckitem:main:07")
	@ResponseBody
	@RequestMapping(value = "saveJkbz", method = RequestMethod.POST)
	public Map<String, Object> saveJkbz(@RequestBody OtherWorkOrder otherWorkOrder) throws BusinessException{
		try {
			return	otherWorkOrderService.saveJkbz(otherWorkOrder);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
}
