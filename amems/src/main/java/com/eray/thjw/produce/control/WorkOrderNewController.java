package com.eray.thjw.produce.control;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.DeptInfo;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.MonitoringCurrent;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.po.Workpackage;
import com.eray.thjw.produce.service.WorkOrderNewService;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.project2.service.WorkCardService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.service.DeptInfoService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;
import com.github.pagehelper.PageHelper;

import enu.produce.FeedbackStatusEnum;
import enu.produce.WorkorderStatusEnum;
import enu.produce.WorkorderTypeEnum;
import enu.project2.MonitorProjectEnum;

/**
 * @Description 工包控制器
 * @CreateTime 2017-9-15 上午9:31:43
 * @CreateBy 雷伟
 */
@Controller
@RequestMapping("/produce/workorder")
public class WorkOrderNewController extends BaseController{
	@Resource
	private WorkOrderNewService workOrderNewService;
	@Resource
	private WorkCardService workCardService;
	@Resource
	private DeptInfoService deptInfoService;

	/**
	 * @Description 135工单管理
	 * @CreateTime 2017-9-15 上午9:32:08
	 * @CreateBy 雷伟
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workorder:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main135(Model model) throws BusinessException {
		try {
			model.addAttribute("workorderStatusEnum", WorkorderStatusEnum.enumToListMap());//工单状态枚举
			model.addAttribute("workorderTypeEnum", WorkorderTypeEnum.enumToListMap());//工单类型枚举
			model.addAttribute("feedbackStatusEnum", FeedbackStatusEnum.enumToListMap());//反馈枚举
			return "produce/workorder/135/workorder_main";
		} catch (Exception e) {
			throw new BusinessException("工单管理列表跳转失败!", e);
		}
	}
	

	/**
	 * @Description 工单135列表查询
	 * @CreateTime 2017-9-30 下午1:34:06
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody Workorder workorder)
			throws BusinessException {
		try {
			return workOrderNewService.queryAllPageList(workorder);
		} catch (Exception e) {
			throw new BusinessException("工单列表加载失败!", e);
		}
	}
	
	/**
	 * @Description 工单135信息查询 (弹窗)
	 * @CreateTime 2017-12-28 上午11:32:04
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageListWin", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageListWin(@RequestBody Workorder workorder)
			throws BusinessException {
		try {
			return workOrderNewService.queryAllPageListWin(workorder);
		} catch (Exception e) {
			throw new BusinessException("工单列表加载失败!", e);
		}
	}
	
	/**
	 * @Description 新增工单
	 * @CreateTime 2017-10-10 下午8:50:14
	 * @CreateBy 雷伟
	 * @param workorder 工单135
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workorder:main:01,produce:workpackage:main:09")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody Workorder workorder) throws BusinessException {
		try {
			return workOrderNewService.save(workorder);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("工单保存失败!", e);
		}
	}
	
	/**
	 * @Description 编辑工单
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param workorder 工单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workorder:main:02")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@RequestBody Workorder workorder) throws BusinessException {
		try {
			return workOrderNewService.update(workorder);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("工单编辑失败!", e);
		}
	}
	
	/**
	 * @Description 删除工单
	 * @CreateTime 2017-10-12 下午2:54:11
	 * @CreateBy 雷伟
	 * @param woId 工单ID
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workorder:main:03")
	@ResponseBody
	@RequestMapping(value = "doDelete", method = RequestMethod.POST)
	public void doDelete(String woId) throws BusinessException{
		try {
			workOrderNewService.doDelete(woId);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除失败!",e);
		}
	}
	
	/**
	 * @Description 工单反馈
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param workorder 工单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workorder:main:04")
	@ResponseBody
	@RequestMapping(value = "doFeedback", method = RequestMethod.POST)
	public String doFeedback(@RequestBody Workorder workorder) throws BusinessException {
		try {
			return workOrderNewService.doFeedback(workorder);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("工单反馈失败!", e);
		}
	}
	
	/**
	 * @Description 工单关闭
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param workorder 工单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workorder:main:05")
	@ResponseBody
	@RequestMapping(value = "doWGClose", method = RequestMethod.POST)
	public String doWGClose(@RequestBody Workorder workorder) throws BusinessException {
		try {
			return workOrderNewService.doWGClose(workorder);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("工单关闭失败!", e);
		}
	}
	
	/**
	 * @Description 工单关闭,指定结束
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param workorder 工单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workorder:main:06")
	@ResponseBody
	@RequestMapping(value = "doZDClose", method = RequestMethod.POST)
	public String doZDClose(@RequestBody Workorder workorder) throws BusinessException {
		try {
			return workOrderNewService.doZDClose(workorder);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("工单关闭失败!", e);
		}
	}
	/**
	 * @Description 工单修订
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 孙霁
	 * @param workorder 工单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:bulletin:08,produce:workorder:main:07")
	@ResponseBody
	@RequestMapping(value = "doWGRevision", method = RequestMethod.POST)
	public String doWGRevision10(@RequestBody Workorder workorder) throws BusinessException {
		try {
			return workOrderNewService.doWGRevision(workorder);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("工单修订失败!", e);
		}
	}
	
	/**
	 * @Description 根据工单Id查询工单信息
	 * @CreateTime 2017-8-24 上午10:59:42
	 * @CreateBy 雷伟
	 * @param gdid 工单id
	 * @return WorkOrder
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectWOById", method = { RequestMethod.POST })
	public Workorder selectWOById(String gdid) throws BusinessException {
		try {
			Workorder wo = workOrderNewService.selectWOById(gdid);
			workOrderNewService.getCompleteLimit(wo); //设置完成时限
		    return wo;
		} catch (Exception e) {
			throw new BusinessException("查询工单失败!", e);
		}
	}

	/**
	 * 
	 * @Description 工单列表加载
	 * @CreateTime 2017年10月9日 下午9:10:27
	 * @CreateBy 林龙
	 * @param workorder
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllList", method = RequestMethod.POST)
	public Map<String, Object> queryAllList(@RequestBody Workorder workorder)throws BusinessException {
		try {
			return workOrderNewService.queryAllList(workorder);
		} catch (Exception e) {
			throw new BusinessException("工单列表加载失败!", e);
		}
	}

	/**
	 * 
	 * @Description 选择不在工包和预组包中的工单列表弹窗
	 * @CreateTime 2017年9月29日 下午2:27:13
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getWorkorderList", method = RequestMethod.POST)
	public Map<String, Object> getWorkorderList(@RequestBody Workorder record, HttpServletRequest request)
			throws BusinessException {
		try {
			return workOrderNewService.getWorkorderList(record);
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}

	/**
	 * 
	 * @Description 更改工单的工包id
	 * @CreateTime 2017年9月30日 上午9:22:46
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "updateGbid", method = RequestMethod.POST)
	public void updateGbid(@RequestBody Workorder record, HttpServletRequest request) throws BusinessException {
		try {
			workOrderNewService.updateGbid(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("添加工单失败", e);
		}
	}
	/**
	 * 
	 * @Description 工单添加附件
	 * @CreateTime 2017年10月20日 上午10:49:55
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "doAttachment", method = RequestMethod.POST)
	public void doAttachment(@RequestBody Workorder record, HttpServletRequest request) throws BusinessException {
		try {
			workOrderNewService.doAttachment(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("添加附件失败", e);
		}
	}
	
	/**
	 * @Description 根据条件查询维修历史清单列表
	 * @CreateTime 2017-10-09上午11:06:29
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Workorder> 工单数据集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllTaskhistory", method = RequestMethod.POST)
	public List<Workorder> queryAllTaskhistory(@RequestBody Workorder workorder) throws BusinessException {
		try {
			return workOrderNewService.queryAllTaskhistory(workorder);
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	
	/**
	 * @Description 根据条件查询维修历史清单列表
	 * @CreateTime 2017-10-09上午11:06:29 
	 * @CreateBy 孙霁
	 * @param workorder 工单
	 * @return List<Workorder> 工单数据集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryTaskhistoryByfjzch", method = RequestMethod.POST)
	public Map<String, Object> queryTaskhistoryByfjzch(@RequestBody Workorder workorder) throws BusinessException {
		try {
			PageHelper.startPage(workorder.getPagination());
			List<Workorder> list = workOrderNewService.queryAllTaskhistory(workorder);
			return PageUtil.pack4PageHelper(list, workorder.getPagination());
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	
	/**
	 * @Description 根据条件查询NRC工单列表
	 * @CreateTime 2017-10-13 上午9:58:19
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Workorder> 工单数据集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryNRCWorkOrderList", method = RequestMethod.POST)
	public List<Workorder> queryNRCWorkOrderList(@RequestBody Workorder workorder) throws BusinessException {
		try {
			return workOrderNewService.queryNRCWorkOrderList(workorder);
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	
	/**
	 * @Description 根据条件查询NRC工单列表(返回map)
	 * @CreateTime 2018-4-11 下午4:19:21
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Map<String, Object>> 工单数据集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryNRCMapList", method = RequestMethod.POST)
	public List<Map<String, Object>> queryNRCMapList(@RequestBody Workorder workorder) throws BusinessException {
		try {
			List<Workorder> wList = workOrderNewService.queryNRCWorkOrderList(workorder);
			return workOrderToMap(wList);
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	
	/**
	 * @Description 工单对象转map
	 * @CreateTime 2018-4-11 上午9:58:54
	 * @CreateBy 刘兵
	 * @param list
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> workOrderToMap(List<Workorder> list){
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (Workorder workorder : list) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", workorder.getId());
			resultMap.put("dprtcode", workorder.getDprtcode());
			resultMap.put("jksjid", workorder.getJksjid());
			resultMap.put("djbgdid", workorder.getDjbgdid());
			resultMap.put("zt", workorder.getZt());
			resultMap.put("gbid", workorder.getGbid());
			resultMap.put("gdbh", workorder.getGdbh());
			resultMap.put("gdbt", workorder.getGdbt());
			resultMap.put("gkid", workorder.getGkid());
			resultMap.put("jhKsrq", workorder.getJhKsrq());
			resultMap.put("jhJsrq", workorder.getJhJsrq());
			resultMap.put("zjh", workorder.getZjh());
			resultMap.put("gdlx", workorder.getGdlx());
			resultMap.put("whsj", workorder.getWhsj());
			resultMap.put("gzlb", workorder.getGzlb());
			resultMap.put("bgr", workorder.getBgr());
			resultMap.put("gkbh", workorder.getGkbh());
			resultMap.put("gznrfjid", workorder.getGznrfjid());
			resultMap.put("paramsMap", workorder.getParamsMap());
			resultList.add(resultMap);
		}
		return resultList;
	}
	
	/**
	 * @Description 根据条件查询预组包工包明细列表
	 * @CreateTime 2018-4-11 下午4:19:21
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Map<String, Object>> 工单数据集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryPackageDetailMapList", method = RequestMethod.POST)
	public List<Map<String, Object>> queryPackageDetailMapList(@RequestBody Workorder workorder) throws BusinessException {
		try {
			List<Workorder> wList = workOrderNewService.queryWorkOrderList(workorder);
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			for (Workorder w : wList) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("id", w.getId());
				resultMap.put("dprtcode", w.getDprtcode());
				resultMap.put("djbgdid", w.getDjbgdid());
				resultMap.put("zt", w.getZt());
				resultMap.put("gbid", w.getGbid());
				resultMap.put("gdbh", w.getGdbh());
				resultMap.put("lyrwid", w.getLyrwid());
				resultMap.put("lyrwh", w.getLyrwh());
				resultMap.put("wgbs", w.getWgbs());
				resultMap.put("gdlx", w.getGdlx());
				resultMap.put("gkid", w.getGkid());
				resultMap.put("gznrfjid", w.getGznrfjid());
				resultMap.put("paramsMap", w.getParamsMap());
				resultList.add(resultMap);
			}
			return resultList;
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	
	/**
	 * @Description 工单航材明细
	 * @CreateTime 2017年9月29日 下午2:21:37
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getGDHCToolDetail", method = RequestMethod.POST)
	public Map<String, Object> getGDHCToolDetail(@RequestBody Workorder workorder) throws BusinessException {
		try {
			return workOrderNewService.getGDHCToolDetail(workorder);
		} catch (Exception e) {
			throw new BusinessException("获取工单航材数据失败", e);
		}
		
	}
	/**
	 * @Description 工单执行历史
	 * @CreateTime 2017年9月29日 下午2:21:37
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getGDZxhistoryInfo", method = RequestMethod.POST)
	public Map<String, Object> getGDZxhistoryInfo(@RequestBody Workorder workorder) throws BusinessException {
		try {
			return workOrderNewService.getGDZxhistoryInfo(workorder);
		} catch (Exception e) {
			throw new BusinessException("获取工单执行历史数据失败", e);
		}
	}
	
	/**
	 * @Description 工单查看页面
	 * @CreateTime 2017-10-16 上午8:59:02
	 * @CreateBy 雷伟
	 * @param gdid
	 * @return
	 */
	@RequestMapping(value = "woView", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView woView(String gdid) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("gdid", gdid);
		Workorder wo = workOrderNewService.selectWOById(gdid);
		if(wo==null){
			return new ModelAndView("/produce/workorder/145/workorder_view", model);
		}
		return new ModelAndView("/produce/workorder/135/workorder_view", model);
	}
	
	/**
	 * @Description 查询工包明细(工单清单列表)
	 * @CreateTime 2017-10-17 下午9:10:03
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Workorder> 工单集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryWorkOrderList", method = RequestMethod.POST)
	public List<Workorder> queryWorkOrderList(@RequestBody Workorder workorder)
			throws BusinessException {
		try {
			return workOrderNewService.queryWorkOrderList(workorder);
		} catch (Exception e) {
			throw new BusinessException("查询工单清单列表失败!", e);
		}
	}
	
	/**
	 * @Description 据查询条件分页查询工包明细(工单清单列表)
	 * @CreateTime 2017-10-20 上午11:36:49
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return Map<String, Object> 页面数据
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageWorkOrderList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageWorkOrderList(@RequestBody Workorder workorder)throws BusinessException {
		try {
			return workOrderNewService.queryAllPageWorkOrderList(workorder);
		} catch (Exception e) {
			throw new BusinessException("查询工包明细失败!",e);
		}
	}
	/**
	 * 
	 * @Description 移除工包中的工单
	 * @CreateTime 2017年11月15日 上午9:37:13
	 * @CreateBy 岳彬彬
	 * @param id
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "update4RemoveWO", method = RequestMethod.POST)
	public void update4RemoveWO(String id)throws BusinessException {
		try {
			workOrderNewService.update4RemoveWO(id);
		} catch (Exception e) {
			throw new BusinessException("查询工包明细失败!",e);
		}
	}
	
	/**
	 * @Description 根据工单识别id查询工单列表
	 * @CreateTime 2017-11-25 下午2:03:39
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Workorder> 工单集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryByGdsbid", method = RequestMethod.POST)
	public List<Map<String, Object>> queryByGdsbid(@RequestBody Workorder param) throws BusinessException {
		try {
			List<Workorder> list = workOrderNewService.queryByGdsbid(param);
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			for (Workorder workorder : list) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("id", workorder.getId());
				resultMap.put("dprtcode", workorder.getDprtcode());
				resultMap.put("zt", workorder.getZt());
				resultMap.put("wgbs", workorder.getWgbs());
				resultMap.put("jksjid", workorder.getJksjid());
				resultMap.put("fjzch", workorder.getFjzch());
				resultMap.put("gdbh", workorder.getGdbh());
				resultMap.put("gdlx", workorder.getGdlx());
				resultMap.put("gdsbid", workorder.getGdsbid());
				resultMap.put("zjh", workorder.getZjh());
				resultMap.put("zy", workorder.getZy());
				resultMap.put("gzlb", workorder.getGzlb());
				resultMap.put("lyrwh", workorder.getLyrwh());
				resultMap.put("lyrwid", workorder.getLyrwid());
				resultMap.put("gdbt", workorder.getGdbt());
				resultMap.put("gkid", workorder.getGkid());
				resultMap.put("jhZd", workorder.getJhZd());
				resultMap.put("gkbh", workorder.getGkbh());
				resultMap.put("gznrfjid", workorder.getGznrfjid());
				resultMap.put("whsj", workorder.getWhsj());
				resultMap.put("gznrfjid", workorder.getGznrfjid());
				resultMap.put("paramsMap", workorder.getParamsMap());
				resultList.add(resultMap);
			}
			return resultList;
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	
	/**
	 * @Description 查询工包明细维修计划135(工单数，已反馈，已关闭)数量
	 * @CreateTime 2017-11-27 下午3:01:33
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return Map<String, Object> 工单数量集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryCount4WorkOrder", method = RequestMethod.POST)
	public Map<String, Object> queryCount4WorkOrder(@RequestBody Workorder workorder)throws BusinessException {
		try {
			return workOrderNewService.queryCount4WorkOrder(workorder);
		} catch (Exception e) {
			throw new BusinessException("查询工单数量失败!",e);
		}
	}
	/**
	 * 
	 * @Description 工单打印
	 * @CreateTime 2017年12月20日 下午5:26:07
	 * @CreateBy 岳彬彬
	 * @param wo
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workorder:main:08,produce:nrc135:main:08")
	@RequestMapping(value = "workorderPDF")
	public String export(Workorder wo, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			model.addAttribute("id", wo.getId());
			User user = ThreadVarUtil.getUser();
			DeptInfo DeptInfo = deptInfoService.selectById(user.getJgdm());
			String imagePath = "zx.jpg";
			String wcImage = "zx.jpg";
			String type = "135";
			if(null != DeptInfo){
				if("145".equals(DeptInfo.getDeptType())){
					imagePath = "hx.jpg";
					wcImage = "hxwc.jpg";
					type = "145";
				}
			}
			String path = request.getSession().getServletContext().getRealPath("/static/images/report");
			String subreport_dir = request.getSession().getServletContext().getRealPath("/WEB-INF/views/reports/common");
			model.addAttribute("images_path", path.concat(File.separator).concat(imagePath));
			model.addAttribute("wcImage", path.concat(File.separator).concat(wcImage));
			model.addAttribute("subreport_dir", subreport_dir);
			model.addAttribute("type", type);
			if(WorkorderTypeEnum.RTN.getId() == wo.getGdlx()){
				return outReport("pdf", wo.getDprtcode(), "mo", model);
			}else if(WorkorderTypeEnum.NRC.getId() == wo.getGdlx()){								
				return outReport("pdf", wo.getDprtcode(), "workOrderPrint", model);
			}			
			return outReport("pdf", wo.getDprtcode(), "mcc", model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("预览或导出失败");
		}

	}
	
	/**
	 * 
	 * @Description 根据id查询该工单能否下发
	 * @CreateTime 2017年12月13日 上午10:31:11
	 * @CreateBy 岳彬彬
	 * @param gdid
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectById", method = { RequestMethod.POST })
	public void selectById(String gdid) throws BusinessException {
		try {
			workOrderNewService.doValidation4Exist(gdid);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询工单失败!", e);
		}
	}
	
	/**
	 * 
	 * @Description 下发MCC/其他工单
	 * @CreateTime 2017年12月13日 上午10:36:02
	 * @CreateBy 岳彬彬
	 * @param workorder
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "doIssued", method = { RequestMethod.POST })
	public void doIssued(@RequestBody Workorder workorder) throws BusinessException {
		try {
			workOrderNewService.doIssued(workorder);
		} catch (Exception e) {
			throw new BusinessException("下发工单失败!", e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "doWoIssued", method = { RequestMethod.POST })
	public void doWoIssued(@RequestBody Workpackage workpackage) throws BusinessException {
		try {
			workOrderNewService.doWoIssued(workpackage);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("下发工单失败!", e);
		}
	}
	/**
	 * 
	 * @Description 工单列表导出
	 * @CreateTime 2017年12月20日 下午5:26:21
	 * @CreateBy 岳彬彬
	 * @param paramjson
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@Privilege(code = "produce:workorder:main:09,produce:nrc135:main:09")
	@RequestMapping(value = "workorder.xls" ,method={RequestMethod.GET})
	public String exportExcel(String paramjson, HttpServletRequest request,Model model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"), "utf-8");
			Workorder workorder = Utils.Json.toObject(paramjson, Workorder.class);
			Map<String, Object> resultMap = workOrderNewService.queryAllPageList(workorder);
			List<Workorder> list = (List<Workorder>) resultMap.get("rows");
			for (Workorder workorder2 : list) {
				StringBuffer sbf = new StringBuffer();
				if(null != workorder2.getMonitoringPlan() && null != workorder2.getMonitoringPlan().getParamsMap().get("jhsjsj") && !"".equals(workorder2.getMonitoringPlan().getParamsMap().get("jhsjsj"))){
					String limtStr = (String) workorder2.getMonitoringPlan().getParamsMap().get("jhsjsj");
					String[] limitArr = limtStr.split(",");
					for (int i=0;null != limitArr && i< limitArr.length;i++){
						String jklbh = limitArr[i].split("#_#")[0];
						String val =  limitArr[i].split("#_#")[1];
						if(null != jklbh && MonitorProjectEnum.isTime(jklbh)){
							val = StringAndDate_Util.convertToHour(val);
						}
						if(null != jklbh && !"".equals(jklbh)){
							jklbh =MonitorProjectEnum.getUnit(jklbh);
						}
						sbf.append(val).append(jklbh).append(",");
					}
						workorder2.getParamsMap().put("jkl", sbf.substring(0, sbf.length()-1).toString());		
				}
			}
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "workorder", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
	
	/**
	 * @Description 根据条件查询其它指令工单列表(导出)
	 * @CreateTime 2017-10-13 上午9:58:19
	 * @CreateBy 刘兵
	 * @param paramjson 工单参数
	 * @return String
	 * @throws BusinessException
	 */
	@RequestMapping(value = "workorderMonitoring.xls", method = RequestMethod.GET)
	public String exportExcelWorkorderzl(String paramjson, HttpServletRequest request,Model model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"),"utf-8");
			Workorder workorder = Utils.Json.toObject(paramjson, Workorder.class);
			List<Workorder> list = workOrderNewService.queryNRCWorkOrderList(workorder);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "workorderzl", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	
	/**
	 * @Description 据查询条件分页查询工包明细(工单清单列表)(导出)
	 * @CreateTime 2017-12-22 下午1:09:33
	 * @CreateBy 刘兵
	 * @param paramjson 当前工单参数
	 * @return String 页面数据
	 * @throws BusinessException
	 */
	@RequestMapping(value = "workorder135.xls", method = RequestMethod.GET)
	public String exportExcelMt(String paramjson, HttpServletRequest request, Model model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"),"utf-8");
			Workorder workorder = JSON.parseObject(paramjson, Workorder.class);
			List<Workorder> list = workOrderNewService.exportExcelWO(workorder);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "wodetail", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	
	/**
	 * @Description 据查询条件分页查询工包明细(预组包工单清单列表)(导出)
	 * @CreateTime 2017-12-22 下午1:09:33
	 * @CreateBy 刘兵
	 * @param paramjson 当前工单参数
	 * @return String 页面数据
	 * @throws BusinessException
	 */
	@RequestMapping(value = "workorderBurstification.xls", method = RequestMethod.GET)
	public String exportExcelYzbWOdetail(String paramjson, HttpServletRequest request, Model model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"),"utf-8");
			Workorder workorder = JSON.parseObject(paramjson, Workorder.class);
			List<Workorder> list = workOrderNewService.exportExcelYzbWO(workorder);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "yzbwodetail", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	
}
