package com.eray.thjw.project2.control;

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
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.ProductionOrder;
import com.eray.thjw.project2.service.ProductionOrderService;

/**
 * @Description 生产指令控制器
 * @CreateTime 2018年5月2日 上午9:40:28
 * @CreateBy 韩武
 */
@Controller
@RequestMapping("/project2/production")
public class ProductionOrderController extends BaseController {
	
	@Resource
	private ProductionOrderService productionOrderService;
	
	/**
	 * @Description 跳转到生产指令主页面
	 * @CreateTime 2018年5月2日 上午9:41:24
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@Privilege(code = "project2:production:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("project2/production/production_main", responseParamMap);
	}
	
	/**
	 * @Description 查询生产指令分页列表
	 * @CreateTime 2018年5月4日 上午11:36:38
	 * @CreateBy 韩武
	 * @param order
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/pagelist", method = RequestMethod.POST)
	public Map<String, Object> queryList(@RequestBody ProductionOrder order) throws BusinessException{
		try {
			return productionOrderService.queryPageableList(order);
		} catch (Exception e) {
			throw new BusinessException("查询生产指令分页列表失败!",e);
		}
	}
	
	/**
	 * @Description 查询生产指令详情
	 * @CreateTime 2018年5月7日 上午9:44:31
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ProductionOrder queryDetail(String id) throws BusinessException{
		try {
			return productionOrderService.queryDetail(id);
		} catch (Exception e) {
			throw new BusinessException("查询生产指令详情失败!",e);
		}
	}
	
	/**
	 * @Description 保存生产指令
	 * @CreateTime 2018年5月3日 下午3:57:46
	 * @CreateBy 韩武
	 * @param productionOrder
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:production:main:01,project2:production:main:02")
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestBody ProductionOrder order) throws BusinessException{
		try {
			return productionOrderService.doSave(order);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存生产指令失败!",e);
		}
	}
	
	/**
	 * @Description 提交生产指令
	 * @CreateTime 2018年5月7日 下午2:36:44
	 * @CreateBy 韩武
	 * @param order
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:production:main:01,project2:production:main:02")
	@ResponseBody
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(@RequestBody ProductionOrder order) throws BusinessException{
		try {
			return productionOrderService.doSubmit(order);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("提交生产指令失败!",e);
		}
	}
	
	/**
	 * @Description 生产指令审核通过
	 * @CreateTime 2018年5月7日 下午3:42:38
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:production:main:06")
	@ResponseBody
	@RequestMapping(value = "/auditagree", method = RequestMethod.POST)
	public void auditAgree(@RequestBody ProductionOrder order) throws BusinessException{
		try {
			productionOrderService.doAuditAgree(order);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("生产指令审核通过失败!", e);
		}
	}
	
	/**
	 * @Description 生产指令审核驳回
	 * @CreateTime 2018年5月7日 下午3:42:57
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:production:main:06")
	@ResponseBody
	@RequestMapping(value = "/auditreject", method = RequestMethod.POST)
	public void auditReject(@RequestBody ProductionOrder order) throws BusinessException{
		try {
			productionOrderService.doAuditReject(order);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("生产指令审核驳回失败!", e);
		}
	}
	
	/**
	 * @Description 生产指令审批通过
	 * @CreateTime 2018年5月7日 下午3:44:44
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:production:main:07")
	@ResponseBody
	@RequestMapping(value = "/approveagree", method = RequestMethod.POST)
	public void approveAgree(@RequestBody ProductionOrder order) throws BusinessException{
		try {
			productionOrderService.doApproveAgree(order);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("生产指令审批通过失败!", e);
		}
	}
	
	/**
	 * @Description 生产指令审批驳回
	 * @CreateTime 2018年5月7日 下午3:44:58
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:production:main:07")
	@ResponseBody
	@RequestMapping(value = "/approvereject", method = RequestMethod.POST)
	public void approveReject(@RequestBody ProductionOrder order) throws BusinessException{
		try {
			productionOrderService.doApproveReject(order);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("生产指令审批驳回失败!", e);
		}
	}
	
	/**
	 * @Description 生产指令改版保存
	 * @CreateTime 2018年5月8日 上午9:58:19
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:production:main:04")
	@ResponseBody
	@RequestMapping(value = "/revisionsave", method = RequestMethod.POST)
	public void revisionSave(@RequestBody ProductionOrder order) throws BusinessException{
		try {
			productionOrderService.doRevisionSave(order);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("生产指令改版保存失败!", e);
		}
	}
	
	/**
	 * @Description 生产指令改版提交
	 * @CreateTime 2018年5月8日 上午9:58:19
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:production:main:04")
	@ResponseBody
	@RequestMapping(value = "/revisionsubmit", method = RequestMethod.POST)
	public void revisionSubmit(@RequestBody ProductionOrder order) throws BusinessException{
		try {
			productionOrderService.doRevisionSubmit(order);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("生产指令改版提交失败!", e);
		}
	}
	
	/**
	 * @Description 查询生产指令版本历史
	 * @CreateTime 2018年5月8日 上午11:44:46
	 * @CreateBy 韩武
	 * @param order
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/versionlist",method={RequestMethod.POST,RequestMethod.GET})
	public List<ProductionOrder> queryVersionList(@RequestBody ProductionOrder order) throws BusinessException {
		try {
			return productionOrderService.queryVersionList(order);
		} catch (Exception e) {
			throw new BusinessException("查询生产指令版本历史失败!",e);
		}
	}
	
	/**
	 * @Description 查询生产指令历史版本
	 * @CreateTime 2018年5月8日 下午1:24:29
	 * @CreateBy 韩武
	 * @param order
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/historylist",method={RequestMethod.POST,RequestMethod.GET})
	public List<ProductionOrder> queryHistoryList(@RequestBody ProductionOrder order) throws BusinessException {
		try {
			return productionOrderService.queryHistoryList(order);
		} catch (Exception e) {
			throw new BusinessException("查询生产指令历史版本失败!",e);
		}
	}
	
	/**
	 * @Description 生产指令查看
	 * @CreateTime 2018年5月8日 下午2:16:04
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("project2/production/production_view", responseParamMap);
	}
	
	/**
	 * @Description 删除生产指令
	 * @CreateTime 2018年5月8日 下午3:24:33
	 * @CreateBy 韩武
	 * @param id
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:production:main:03")
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(String id) throws BusinessException{
		try {
			productionOrderService.doDelete(id);
		} catch (Exception e) {
			throw new BusinessException("删除生产指令失败!",e);
		}
	}
	
	/**
	 * @Description 关闭生产指令
	 * @CreateTime 2018年5月8日 下午3:27:37
	 * @CreateBy 韩武
	 * @param id
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:production:main:05")
	@ResponseBody
	@RequestMapping(value = "/close", method = RequestMethod.POST)
	public void close(String id) throws BusinessException{
		try {
			productionOrderService.doClose(id);
		} catch (Exception e) {
			throw new BusinessException("关闭生产指令失败!",e);
		}
	}
	
	/**
	 * @Description 启用生产指令
	 * @CreateTime 2018年5月8日 下午3:28:07
	 * @CreateBy 韩武
	 * @param id
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:production:main:08")
	@ResponseBody
	@RequestMapping(value = "/open", method = RequestMethod.POST)
	public void open(String id) throws BusinessException{
		try {
			productionOrderService.doOpen(id);
		} catch (Exception e) {
			throw new BusinessException("启用生产指令失败!",e);
		}
	}
}