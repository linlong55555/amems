package com.eray.thjw.productionplan.control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.po.OtherWorkOrder;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.productionplan.po.TimeControllWareItem;
import com.eray.thjw.productionplan.service.OtherWorkOrderService;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.productionplan.service.ScheduledCheckItemService;
import com.eray.thjw.productionplan.service.TimeControlOptionsService;
import com.eray.thjw.productionplan.service.TimeControllWareItemService;
import com.eray.thjw.sched.po.ProductPlanSched;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.SessionUtil;
import com.google.gson.Gson;

import enu.ThresholdEnum;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author ll
 * @description 定检监控
 * @develop date 2016.07.25
 */
@Controller
@RequestMapping("/productionplan/scheduledcheckitem")
public class ScheduledCheckItemController  extends BaseController{
	
	@Resource
	private ScheduledCheckItemService scheduledCheckItemService; //b_s_00303 生效区-定检件定检项目service
	
	@Resource
	private PlaneDataService planeDataService; //飞行注册号
	
	@Resource
	private PlaneModelDataService planeModelDataService; //飞机数据service
	
	@Resource
	private TimeControllWareItemService timeControllWareItemService; //b_s_00303 生效区-定检件定检项目service
	
	@Resource
	private TimeControlOptionsService timeControlOptionsService; //b_s_00304 生效区-定检监控项目service
	
	@Resource
	private OtherWorkOrderService otherWorkOrderService; //其他工单service
	
	@Resource
	private MonitorsettingsService monitorsettingsService; 
	/**
	 * 初始化定检监控页面
	 * @return 页面视图
	 * @throws Exception 
	 */
	@Privilege(code="productionplan:scheduledcheckitem:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("planeData", new Gson().toJson(planeDataService.selectByDprtcodeList(SessionUtil.getDprtcodeList(request))));
		return new ModelAndView("/productionplan/scheduledcheckitem/scheduledcheckitem_list", model);
	}
	
	/**
	 * @author ll
	 * @description 根据dprtcode查询系统阀值
	 * @param dprtcode
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "getByKeyDprtcode", method = RequestMethod.POST)
	public Map<String, Object> getByKeyDprtcode(String dprtcode)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("monitorsettings", monitorsettingsService.getByKeyDprtcode(ThresholdEnum.SCJK.getName(),dprtcode));
		} catch (Exception e) {
			throw new BusinessException("查询系统阀值失败!",e);
		}
		return model;
	}
	
	/**
	 * 定检监控列表
	 * @param role
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "queryScheduledCheckItemList", method = RequestMethod.POST)
	public Map<String, Object> queryScheduledCheckItemList(@RequestBody ScheduledCheckItem scheduledCheckItem,HttpServletRequest request,Model model) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String sort=null;
			String order=null;	
			//剩余天数排序
			if("syts".equals(scheduledCheckItem.getPagination().getSort())){
				Pagination pagination=new Pagination();
				sort=scheduledCheckItem.getPagination().getSort();
				order=scheduledCheckItem.getPagination().getOrder();
				pagination.setSort("auto");
				pagination.setOrder("desc");
				pagination.setPage(scheduledCheckItem.getPagination().getPage());
				pagination.setRows(scheduledCheckItem.getPagination().getRows());
				scheduledCheckItem.setPagination(pagination);
			}else if("auto".equals(scheduledCheckItem.getPagination().getSort())){
				sort=scheduledCheckItem.getPagination().getSort();
				order=scheduledCheckItem.getPagination().getOrder();
			}
			
			List<ScheduledCheckItem> list= scheduledCheckItemService.queryAllPageList(scheduledCheckItem); //查询定检监控主数据
			
			PlaneModelData planeModelData=planeModelDataService.selectPlanes(scheduledCheckItem.getFjzch(),scheduledCheckItem.getDprtcode());//查询 d_004 所有部件日使用量
			if(planeModelData!=null){
				Pagination pagination=new Pagination();
				pagination.setSort(sort);
				pagination.setOrder(order);
				pagination.setPage(scheduledCheckItem.getPagination().getPage());
				pagination.setRows(scheduledCheckItem.getPagination().getRows());
				scheduledCheckItem.setPagination(pagination);
				List<ScheduledCheckItem> list1=scheduledCheckItemService.geshihua(list,planeModelData,scheduledCheckItem); //格式化 计划，剩余，剩余天数
				resultMap.put("rows",list1);
			}
		} catch (Exception e) {
			throw new BusinessException("定检监控模式查询失败",e);
		}
		return resultMap;
	}
	
	/**
	 * 定检监控列表-预排模式
	 * @param role
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "recursivelyScheduledCheckItems", method = RequestMethod.POST)
	public Map<String, Object> recursivelyScheduledCheckItems(@RequestBody ScheduledCheckItem scheduledCheckItem,HttpServletRequest request,Model model) throws BusinessException {
		try {
			User user  = SessionUtil.getUserFromSession(request);
			Map<String, Object> resultMap = scheduledCheckItemService.doQuerySched(scheduledCheckItem,user); //格式化 计划，剩余，剩余天数
			return resultMap;
		} catch (BusinessException e) {
	        e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("定检预排模式查询失败");
		}
	}
	
	/**
	 * 批量生成预排数据（由ischedule定时调度）
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "batchUpdateSched")
	public Map<String, Object> batchUpdateSched(HttpServletRequest request,Model model) throws BusinessException {
		Map<String, Object> resultMap = null;
		try {
			resultMap = new HashMap<String, Object>();
			resultMap.put("status", "success");
			resultMap.put("info", "操作成功");
			scheduledCheckItemService.batchUpdateSched(); 
			return resultMap;
		} catch (BusinessException e) {
	        e.printStackTrace();
	        resultMap.put("status", "error");
			resultMap.put("info", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", "error");
			resultMap.put("info", "批量生成预排数据失败");
		}
		return resultMap; 
	}
	
	/**
	 * @author ll
	 * @description 检查定检是否可以提交计划
	 * @param model,ids
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdMt")
	public Map<String, Object> checkUpdMt(HttpServletRequest request,String ids) throws Exception {
		return scheduledCheckItemService.checkUpdMt(SessionUtil.getUserFromSession(request),ids);
	}
	
	/**
	 * 定检监控：提交计划
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@Privilege(code="productionplan:scheduledcheckitem:main:01")
	@ResponseBody
	@RequestMapping(value ="subScheduledcheckitem", method = RequestMethod.POST)
	public Map<String, Object> subScheduledcheckitem(String  ids) throws Exception {
		
		return scheduledCheckItemService.saveScheduledcheckitem(ids);
	}
	
	/**
	 * @author 梅志亮
	 * @description 根据飞机注册号取得件号和序列号
	 */
	@ResponseBody
	@RequestMapping("getXlh")
	public ScheduledCheckItem getLeftXlh(HttpServletRequest request,@Param (value="fjzch") String fjzch,@Param (value="dprtcode") String dprtcode) throws BusinessException {
		
		ScheduledCheckItem sc2=new ScheduledCheckItem();
		sc2.setFjzch(fjzch);
		sc2.setDprtcode(dprtcode);
		ScheduledCheckItem sc =null;
		try {
			sc=scheduledCheckItemService.selectXlh(sc2);
		} catch (Exception e) {
			throw new BusinessException("注册号加载失败",e);
		}
		return sc;
	}
	
	/**
	 * @author ll
	 * @description 编辑监控备注
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	@Privilege(code="productionplan:scheduledcheckitem:main:02")
	@ResponseBody
	@RequestMapping(value = "saveJkbz", method = RequestMethod.POST)
	public Map<String, Object> saveJkbz(@RequestBody ScheduledCheckItem scheduledCheckItem) throws BusinessException{
		try {
			return	scheduledCheckItemService.saveJkbz(scheduledCheckItem);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}


	
	/**
	 * 定检监控导出
	 * @param keyword
	 * @param dprtcode
	 * @param fjzch
	 * @param dprtname
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="scheduledcheckitem.xls")
	public String ypoutExcel(String keyword,String dprtcode,String fjzch,String jzrq,String lx, HttpServletRequest request,Model  model) throws BusinessException {
		try { 
			keyword = new String((request.getParameter("keyword")).getBytes("iso-8859-1"), "utf-8");
			//预排导出
			if(lx.equals("2")){
				ScheduledCheckItem scheduledCheckItem=new ScheduledCheckItem();
				scheduledCheckItem.setFjzch(fjzch);
				scheduledCheckItem.setDprtcode(dprtcode);
				if(!keyword.equals("")){
					scheduledCheckItem.setKeyword(keyword);
				}			
				Pagination p=new Pagination();
				p.setSort("auto");
				p.setRows(Integer.MAX_VALUE);
				scheduledCheckItem.setPagination(p);
				DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				scheduledCheckItem.setJzrq(df.parse(jzrq));		
				User user = SessionUtil.getUserFromSession(request);
				/*List<ScheduledCheckItem> list1=scheduledCheckItemService.installRecursivelyScheduledCheck(scheduledCheckItem,user); //格式化 计划，剩余，剩余天数
				JRDataSource jrDataSource = new JRBeanCollectionDataSource(list1);  */
				
				List<ProductPlanSched> list = scheduledCheckItemService.querySchedAll(scheduledCheckItem);
				JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
				model.addAttribute("jrMainDataSource", jrDataSource); 
			    //报表文件重写
			    
				return outReport("xls", "common", "scheduledcheckitemYP", model);
			}
			//时控件导出
		    if(lx.equals("3")){
		    	TimeControllWareItem timeControllWareItem=new TimeControllWareItem();
				timeControllWareItem.setFjzch(fjzch);
				timeControllWareItem.setDprtcode(dprtcode);
				if(!keyword.equals("")){
					timeControllWareItem.setKeyword(keyword);
				}			
				Pagination p=new Pagination();
				p.setSort("auto");
				p.setRows(Integer.MAX_VALUE);
				timeControllWareItem.setPagination(p);
				List<TimeControllWareItem> list= timeControllWareItemService.queryAllPageList(timeControllWareItem); //查询  b_s_003 生效区-飞机装机清单集合		
				PlaneModelData planeModelData=planeModelDataService.selectPlanes(timeControllWareItem.getFjzch(),timeControllWareItem.getDprtcode());//查询 d_004 所有部件日使用量			
				List<TimeControllWareItem> list1=timeControllWareItemService.geshihua(list,planeModelData,timeControllWareItem.getPagination()); //格式化 b_s_003 生效区-飞机装机清单集合
				JRDataSource jrDataSource = new JRBeanCollectionDataSource(list1);  
			    model.addAttribute("jrMainDataSource", jrDataSource);     
				return outReport("xls", "common", "ScheduledcheckitemSKJ", model);
		    }
		    //监控导出
		    if(lx.equals("1")){
		    	ScheduledCheckItem scheduledCheckItem=new ScheduledCheckItem();
				scheduledCheckItem.setFjzch(fjzch);
				scheduledCheckItem.setDprtcode(dprtcode);
				if(!keyword.equals("")){
					scheduledCheckItem.setKeyword(keyword);
				}			
				Pagination p=new Pagination();
				p.setSort("auto");
				p.setRows(Integer.MAX_VALUE);
				scheduledCheckItem.setPagination(p);
				List<ScheduledCheckItem> list= scheduledCheckItemService.queryAllPageList(scheduledCheckItem); //查询定检监控主数据
				PlaneModelData planeModelData=planeModelDataService.selectPlanes(scheduledCheckItem.getFjzch(),scheduledCheckItem.getDprtcode());//查询 d_004 所有部件日使用量
				List<ScheduledCheckItem> list1=scheduledCheckItemService.geshihua(list,planeModelData,scheduledCheckItem); //格式化 计划，剩余，剩余天数
				JRDataSource jrDataSource = new JRBeanCollectionDataSource(list1);
				model.addAttribute("jrMainDataSource", jrDataSource); 
				return outReport("xls", "common", "ScheduledcheckitemJk", model);
		    }
		    //其他工单监控导出
		    if(lx.equals("4")){
		    	OtherWorkOrder otherWorkOrder=new OtherWorkOrder();
		    	otherWorkOrder.getParamsMap().put("userId", SessionUtil.getUserFromSession(request).getId());
				otherWorkOrder.getParamsMap().put("userType",SessionUtil.getUserFromSession(request).getUserType());
				otherWorkOrder.setFjzch(fjzch);
				otherWorkOrder.setDprtcode(dprtcode);
				if(!keyword.equals("")){
					otherWorkOrder.setKeyword(keyword);
				}			
				Pagination p=new Pagination();
				p.setSort("auto");
				p.setRows(Integer.MAX_VALUE);
				otherWorkOrder.setPagination(p);
				List<OtherWorkOrder> list= otherWorkOrderService.queryAllList(otherWorkOrder);			
				List<OtherWorkOrder> list1=new ArrayList<OtherWorkOrder>();
				PlaneModelData planeModelData=planeModelDataService.selectPlanes(otherWorkOrder.getFjzch(),otherWorkOrder.getDprtcode());//查询 d_004 所有部件日使用量
				for (OtherWorkOrder otherWorkOrder2 : list) {
					list1.add(otherWorkOrderService.canshu(otherWorkOrder2,planeModelData));//格式化计划，剩余，剩余天数
				}
				JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);  
			    model.addAttribute("jrMainDataSource", jrDataSource);     
				return outReport("xls", "common", "scheduledcheckitemQT", model);
		    }
		    return "";
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("报表预览或导出失败");
		}
	}
	
}
