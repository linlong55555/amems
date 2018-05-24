package com.eray.thjw.productionplan.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.productionplan.po.TimeControllWareItem;
import com.eray.thjw.productionplan.service.TimeControlOptionsService;
import com.eray.thjw.productionplan.service.TimeControllWareItemService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.SessionUtil;

/**
 * @author linlong
 * @description 时控件监控
 * @develop date 2016.07.25
 */
@Controller
@RequestMapping("/productionplan/timecontrollwareitem")
public class TimeControllWareItemController  extends BaseController{
	
	@Resource
	private TimeControllWareItemService timeControllWareItemService; //b_s_00303 生效区-定检件定检项目service
	
	@Resource
	private TimeControlOptionsService timeControlOptionsService; //b_s_00304 生效区-定检监控项目service
	
	@Resource
	private PlaneModelDataService planeModelDataService; //飞机数据service
	
	@Resource
	private MonitorsettingsService monitorsettingsService; 
	
	/**
	 * 时控件列表
	 * @param role
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "queryTimeControllWareItemList", method = RequestMethod.POST)
	public Map<String, Object> queryScheduledCheckItemList(@RequestBody TimeControllWareItem timeControllWareItem,HttpServletRequest request,Model model) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<TimeControllWareItem> list= timeControllWareItemService.queryAllPageList(timeControllWareItem); //查询  b_s_003 生效区-飞机装机清单集合
		
		PlaneModelData planeModelData=planeModelDataService.selectPlanes(timeControllWareItem.getFjzch(),timeControllWareItem.getDprtcode());//查询 d_004 所有部件日使用量
		if(planeModelData!=null){
		List<TimeControllWareItem> list1=timeControllWareItemService.geshihua(list,planeModelData,timeControllWareItem.getPagination()); //格式化 b_s_003 生效区-飞机装机清单集合
		resultMap.put("rows",list1);
		}
		return resultMap;
	}
	
	/**
	 * @author ll
	 * @description 检查时控件工单
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkdg")
	public Map<String, Object> checkdg(HttpServletRequest request,String id) throws Exception {

		return timeControllWareItemService.checkdg(SessionUtil.getUserFromSession(request),id);
	}
	
	/**
	 * 时控件监控：生成工单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@Privilege(code="productionplan:scheduledcheckitem:main:03")
	@ResponseBody
	@RequestMapping(value ="subgd", method = RequestMethod.POST)
	public Map<String, Object> subgd(@RequestBody TimeControllWareItem timeControllWareItem) throws Exception {
		
		return timeControllWareItemService.subgd(timeControllWareItem);
	}
	
	/**
	 * @author ll
	 * @description 检查时控件体积计划验证
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdMt")
	public Map<String, Object> checkUpdMt(HttpServletRequest request,String id) throws Exception {

		return timeControllWareItemService.checkUpdMt(SessionUtil.getUserFromSession(request),id);
	}
	
	/**
	 * 时控件监控：提交计划
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@Privilege(code="productionplan:scheduledcheckitem:main:04")
	@ResponseBody
	@RequestMapping(value ="subjh", method = RequestMethod.POST)
	public Map<String, Object> subjh(@RequestBody TimeControllWareItem timeControllWareItem) throws Exception {
		
		return timeControllWareItemService.subjh(timeControllWareItem);
	}
	
	/**
	 * @author ll
	 * @description 编辑监控备注
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	@Privilege(code="productionplan:scheduledcheckitem:main:05")
	@ResponseBody
	@RequestMapping(value = "saveJkbz", method = RequestMethod.POST)
	public Map<String, Object> saveJkbz(@RequestBody TimeControllWareItem timeControllWareItem) throws BusinessException{
		try {
			return	timeControllWareItemService.saveJkbz(timeControllWareItem);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
}
