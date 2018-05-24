package com.eray.thjw.produce.control;

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
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.service.WorkOrderNewService;
import com.eray.thjw.project2.service.WorkCardService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

import enu.produce.FeedbackStatusEnum;
import enu.produce.WorkorderStatusEnum;
import enu.produce.WorkorderTypeEnum;

/**
 * 
 * @Description NRC135
 * @CreateTime 2017年12月9日 下午2:37:31
 * @CreateBy 岳彬彬
 */
@Controller
@RequestMapping("/produce/nrc135")
public class Nrc135Controller {
	@Resource
	private WorkOrderNewService workOrderNewService;
	@Resource
	private WorkCardService workCardService;

	/**
	 * @Description 135工单管理
	 * @CreateTime 2017-9-15 上午9:32:08
	 * @CreateBy 雷伟
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:nrc135:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main135(Model model) throws BusinessException {
		try {
			model.addAttribute("workorderStatusEnum", WorkorderStatusEnum.enumToListMap());//工单状态枚举
			model.addAttribute("workorderTypeEnum", WorkorderTypeEnum.enumToListMap());//工单类型枚举
			model.addAttribute("feedbackStatusEnum", FeedbackStatusEnum.enumToListMap());//反馈枚举
			return "produce/nrc/135/nrc_main";
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
	 * @Description 新增工单
	 * @CreateTime 2017-10-10 下午8:50:14
	 * @CreateBy 雷伟
	 * @param workorder 工单135
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:nrc135:main:01,produce:workpackage:main:10")
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
	@Privilege(code = "produce:nrc135:main:02")
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
	@Privilege(code = "produce:nrc135:main:03")
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
	@Privilege(code = "produce:nrc135:main:04")
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
	@Privilege(code = "produce:nrc135:main:05")
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
	@Privilege(code = "produce:nrc135:main:06")
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
	@Privilege(code = "project2:bulletin:08,produce:nrc135:main:07")
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
	public List<Workorder> queryByGdsbid(@RequestBody Workorder workorder) throws BusinessException {
		try {
			return workOrderNewService.queryByGdsbid(workorder);
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
}
