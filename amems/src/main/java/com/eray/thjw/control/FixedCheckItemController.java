package com.eray.thjw.control;



import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.FixedCheckItem;
import com.eray.thjw.service.FixedCheckItemService;
import com.eray.thjw.service.MaintenanceService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.ApproveStatusEnum;
import enu.SignificantCoefficientEnum;
import enu.common.EffectiveEnum;

/**
 * @author liub
 * @description 定检项目控制层
 * @develop date 2016.08.15
 */
@Controller
@RequestMapping("/project/fixedcheckitem")
public class FixedCheckItemController extends BaseController {
	
	/**
	 * @author liub
	 * @description 定检项目service
	 * @develop date 2016.08.15
	 */
	@Autowired
	private FixedCheckItemService fixedCheckItemService;
	
	/**
	 * @author liub
	 * @description 维修方案service
	 * @develop date 2016.08.23
	 */
	@Autowired
	private MaintenanceService maintenanceService;
	
	/**
	 * @author liub
	 * @description 跳转至定检项目界面
	 * @param model
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code="project:fixedcheckitem:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model) throws BusinessException{
		try {
			model.addAttribute("approveStatusEnum", ApproveStatusEnum.enumToListMap());
			model.addAttribute("effectiveEnum", EffectiveEnum.enumToListMap());
		} catch (Exception e) {
			throw new BusinessException("获取维修方案信息失败!",e);
		}
		return "project/maintenance/fixedcheckitem_main";
	}
	
	/**
	 * @author liub
	 * @description 查看定检项目
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.25
	 *
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,String id) throws BusinessException{
		try {
			FixedCheckItem fixedCheckItem = fixedCheckItemService.selectById(id);
			model.addAttribute("significantCoefficientEnum", SignificantCoefficientEnum.enumToListMap());
			model.addAttribute("effectiveEnum", EffectiveEnum.enumToListMap());
			model.addAttribute("approveStatusEnum", ApproveStatusEnum.enumToListMap());
			model.addAttribute("fixedCheckItem", fixedCheckItem);
			model.addAttribute("newbb", maintenanceService.getMaxBbByWxfabh(fixedCheckItem.getWxfabh(),fixedCheckItem.getDprtcode()));
		} catch (Exception e) {
			throw new BusinessException("获取监控项目信息失败!");
		}
		return new ModelAndView("project/maintenance/fixedcheckitem_view");
	}
	
	/**
	 * @author liub
	 * @description 初始化增加定检项目
	 * @param model,wxfabh,bb
	 * @return 页面视图
	 * @develop date 2016.08.23
	 *
	 */
	@Privilege(code="project:fixedcheckitem:main:01")
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(Model model,String wxfabh,BigDecimal bb) throws BusinessException{
		try {
			int maxPxh = fixedCheckItemService.getMaxPxh();
			model.addAttribute("significantCoefficientEnum", SignificantCoefficientEnum.enumToListMap());
			model.addAttribute("effectiveEnum", EffectiveEnum.enumToListMap());
			model.addAttribute("maintenance", maintenanceService.selectByWxfabhBb(wxfabh, bb.toString(),ThreadVarUtil.getUser().getJgdm()));
			model.addAttribute("pxh", maxPxh);
			model.addAttribute("rwxfabb", bb);
		} catch (Exception e) {
			throw new BusinessException("初始化增加定检项目失败!",e);
		}
		return new ModelAndView("project/maintenance/fixedcheckitem_add");
	}
	
	/**
	 * @author liub
	 * @description 保存定检项目
	 * @param fixedCheckItem
	 * @develop date 2016.08.24
	 */
	@Privilege(code="project:fixedcheckitem:main:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@RequestBody FixedCheckItem fixedCheckItem) throws BusinessException{
		try {
			return fixedCheckItemService.add(fixedCheckItem);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("定检项目保存失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 检查定检项目修改权限
	 * @param id
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkEdit", method = RequestMethod.POST)
	public void checkEdit(String id) throws BusinessException {
		try {
			fixedCheckItemService.checkEdit(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("查询定检项目失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 初始化修改定检项目
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.23
	 *
	 */
	@Privilege(code="project:fixedcheckitem:main:02")
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(Model model,String id) throws BusinessException{
		try {
			FixedCheckItem fixedCheckItem = fixedCheckItemService.selectById(id);
			model.addAttribute("significantCoefficientEnum", SignificantCoefficientEnum.enumToListMap());
			model.addAttribute("effectiveEnum", EffectiveEnum.enumToListMap());
			model.addAttribute("approveStatusEnum", ApproveStatusEnum.enumToListMap());
			model.addAttribute("fixedCheckItem", fixedCheckItem);
			model.addAttribute("newbb", maintenanceService.getMaxBbByWxfabh(fixedCheckItem.getWxfabh(),fixedCheckItem.getDprtcode()));
		} catch (Exception e) {
			throw new BusinessException("初始化修改定检项目失败!",e);
		}
		return new ModelAndView("project/maintenance/fixedcheckitem_edit");
	}
	
	/**
	 * @author liub
	 * @description 修改定检项目
	 * @param fixedCheckItem
	 * @develop date 2016.08.24
	 */
	@Privilege(code="project:fixedcheckitem:main:02")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public void edit(@RequestBody FixedCheckItem fixedCheckItem) throws BusinessException{
		try {
			fixedCheckItem.setSpzt(ApproveStatusEnum.NOT_AUDITED.getId());
			fixedCheckItemService.edit(fixedCheckItem);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("修改定检项目失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 检查定检项目改版权限
	 * @param id
	 * @develop date 2016.08.26
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkModify", method = RequestMethod.POST)
	public void checkModify(String id) throws BusinessException{
		try {
			fixedCheckItemService.checkModify(id);
		} catch (BusinessException e) {
			 throw e;
		}catch (Exception e) {
			 throw new BusinessException("查询定检项目失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 初始化改版定检项目
	 * @param model,id,bb
	 * @return 页面视图
	 * @develop date 2016.08.26
	 *
	 */
	@Privilege(code="project:fixedcheckitem:main:03")
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public ModelAndView modify(Model model,String id,BigDecimal bb) throws BusinessException{
		try {
			FixedCheckItem fixedCheckItem = fixedCheckItemService.selectById(id);
			model.addAttribute("significantCoefficientEnum", SignificantCoefficientEnum.enumToListMap());
			model.addAttribute("effectiveEnum", EffectiveEnum.enumToListMap());
			model.addAttribute("approveStatusEnum", ApproveStatusEnum.enumToListMap());
			model.addAttribute("fixedCheckItem", fixedCheckItem);
			model.addAttribute("newbb", maintenanceService.getMaxBbByWxfabh(fixedCheckItem.getWxfabh(),fixedCheckItem.getDprtcode()));
		} catch (Exception e) {
			throw new BusinessException("初始化改版定检项目失败!",e);
		}
		return new ModelAndView("project/maintenance/fixedcheckitem_modify");
	}
	
	/**
	 * @author liub
	 * @description 改版定检项目
	 * @param fixedCheckItem
	 * @develop date 2016.08.24
	 */
	@Privilege(code="project:fixedcheckitem:main:03")
	@ResponseBody
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String modify(@RequestBody FixedCheckItem fixedCheckItem) throws BusinessException{
		try {
			return fixedCheckItemService.modify(fixedCheckItem);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("改版定检项目失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 检查定检项目审核权限
	 * @param id
	 * @return 页面视图
	 * @develop date 2016.09.28
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkAudit", method = RequestMethod.POST)
	public void checkAudit(String id) throws BusinessException{
		try {
			fixedCheckItemService.checkAudit(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("查询定检项目失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 进入定检项目审核页面
	 * @param model,id,bb
	 * @return 页面视图
	 * @develop date 2016.09.28
	 *
	 */
	@Privilege(code="project:fixedcheckitem:main:05")
	@RequestMapping(value = "audit", method = RequestMethod.GET)
	public ModelAndView audit(Model model,String id,BigDecimal bb) throws BusinessException{
		try {
			FixedCheckItem fixedCheckItem = fixedCheckItemService.selectById(id);
			model.addAttribute("significantCoefficientEnum", SignificantCoefficientEnum.enumToListMap());
			model.addAttribute("effectiveEnum", EffectiveEnum.enumToListMap());
			model.addAttribute("approveStatusEnum", ApproveStatusEnum.enumToListMap());
			model.addAttribute("fixedCheckItem", fixedCheckItem);
			model.addAttribute("newbb", maintenanceService.getMaxBbByWxfabh(fixedCheckItem.getWxfabh(),fixedCheckItem.getDprtcode()));
		} catch (Exception e) {
			throw new BusinessException("进入定检项目审核页面失败!",e);
		}
		return new ModelAndView("project/maintenance/fixedcheckitem_audit");
	}
	
	/**
	 * @author liub
	 * @description 审核通过
	 * @param fixedCheckItem
	 * @develop date 2016.09.29
	 */
	@Privilege(code="project:fixedcheckitem:main:05")
	@ResponseBody
	@RequestMapping(value = "agreedAudit", method = RequestMethod.POST)
	public void agreedAudit(@RequestBody FixedCheckItem fixedCheckItem) throws BusinessException{
		try {
			fixedCheckItem.setSpzt(ApproveStatusEnum.AUDITED.getId());
			fixedCheckItemService.audit(fixedCheckItem);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("审核定检项目失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 审核驳回
	 * @param fixedCheckItem
	 * @develop date 2016.09.29
	 */
	@Privilege(code="project:fixedcheckitem:main:05")
	@ResponseBody
	@RequestMapping(value = "rejectedAudit", method = RequestMethod.POST)
	public void rejectedAudit(@RequestBody FixedCheckItem fixedCheckItem) throws BusinessException{
		try {
			fixedCheckItem.setSpzt(ApproveStatusEnum.AUDIT_DISMISSED.getId());
			fixedCheckItemService.audit(fixedCheckItem);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("审核定检项目失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 检查定检项目审批权限
	 * @param id
	 * @develop date 2016.09.28
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkApprove", method = RequestMethod.POST)
	public void checkApprove(String id) throws BusinessException{
		try {
			fixedCheckItemService.checkApprove(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("查询定检项目失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 进入定检项目批准页面
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.09.28
	 *
	 */
	@Privilege(code="project:fixedcheckitem:main:06")
	@RequestMapping(value = "approve", method = RequestMethod.GET)
	public ModelAndView approve(Model model,String id) throws BusinessException{
		try {
			FixedCheckItem fixedCheckItem = fixedCheckItemService.selectById(id);
			model.addAttribute("significantCoefficientEnum", SignificantCoefficientEnum.enumToListMap());
			model.addAttribute("effectiveEnum", EffectiveEnum.enumToListMap());
			model.addAttribute("approveStatusEnum", ApproveStatusEnum.enumToListMap());
			model.addAttribute("fixedCheckItem", fixedCheckItem);
			model.addAttribute("newbb", maintenanceService.getMaxBbByWxfabh(fixedCheckItem.getWxfabh(),fixedCheckItem.getDprtcode()));
		} catch (Exception e) {
			throw new BusinessException("进入定检项目批准页面失败!",e);
		}
		return new ModelAndView("project/maintenance/fixedcheckitem_approve");
	}
	
	/**
	 * @author liub
	 * @description 批准通过
	 * @param fixedCheckItem
	 * @develop date 2016.09.29
	 */
	@Privilege(code="project:fixedcheckitem:main:06")
	@ResponseBody
	@RequestMapping(value = "agreedApprove", method = RequestMethod.POST)
	public void agreedApprove(@RequestBody FixedCheckItem fixedCheckItem) throws BusinessException{
		try {
			fixedCheckItem.setSpzt(ApproveStatusEnum.APPROVED.getId());
			fixedCheckItemService.approve(fixedCheckItem);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("批准定检项目失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 批量审核
	 * @param idList,yj
	 * @return List<Map<String, Object>>
	 * @develop date 2016.10.13
	 */
	@Privilege(code="project:fixedcheckitem:main:05")
	@ResponseBody
	@RequestMapping(value = "updateBatchAudit", method = RequestMethod.POST)
	public String updateBatchAudit(@RequestParam("idList[]")List<String> idList,String yj)throws BusinessException {
		try {
			return fixedCheckItemService.updateBatchAudit(idList,yj);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("批量审核失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 批量批准
	 * @param idList,yj
	 * @return String
	 * @develop date 2016.10.13
	 */
	@Privilege(code="project:fixedcheckitem:main:06")
	@ResponseBody
	@RequestMapping(value = "updateBatchApprove", method = RequestMethod.POST)
	public String updateBatchApprove(@RequestParam("idList[]")List<String> idList,String yj)throws BusinessException {
		try {
			return fixedCheckItemService.updateBatchApprove(idList,yj);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("批量批准失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 中止
	 * @param fixedCheckItem
	 * @develop date 2016.09.29
	 */
	@Privilege(code="project:fixedcheckitem:main:06")
	@ResponseBody
	@RequestMapping(value = "stopApprove", method = RequestMethod.POST)
	public void stopApprove(@RequestBody FixedCheckItem fixedCheckItem) throws BusinessException{
		try {
			fixedCheckItem.setSpzt(ApproveStatusEnum.STOPPED.getId());
			fixedCheckItemService.approve(fixedCheckItem);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("定检项目中止失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 批准驳回
	 * @param fixedCheckItem
	 * @develop date 2016.09.29
	 * @throws BusinessException 
	 */
	@Privilege(code="project:fixedcheckitem:main:06")
	@ResponseBody
	@RequestMapping(value = "rejectedApprove", method = RequestMethod.POST)
	public void rejectedApprove(@RequestBody FixedCheckItem fixedCheckItem) throws BusinessException{
		try {
			fixedCheckItem.setSpzt(ApproveStatusEnum.APPROVED_DISMISSED.getId());
			fixedCheckItemService.approve(fixedCheckItem);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("批准驳回失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 初始化调整排序
	 * @return 页面视图
	 * @develop date 2016.08.25
	 *
	 */
	@Privilege(code="project:fixedcheckitem:main:07")
	@RequestMapping(value = "order", method = RequestMethod.GET)
	public ModelAndView order(){
		return new ModelAndView("project/maintenance/fixedcheckitem_order");
	}
	
	/**
	 * @author liub
	 * @description 调整排序
	 * @param fixedCheckItemList
	 * @develop date 2016.08.25
	 */
	@Privilege(code="project:fixedcheckitem:main:07")
	@ResponseBody
	@RequestMapping(value = "order", method = RequestMethod.POST)
	public void order(@RequestBody List<FixedCheckItem> fixedCheckItemList) throws BusinessException{
		try {
			fixedCheckItemService.order(fixedCheckItemList);
		} catch (Exception e) {
			throw new BusinessException("调整排序失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 作废
	 * @param id
	 * @develop date 2016.08.18
	 */
	@Privilege(code="project:fixedcheckitem:main:04")
	@ResponseBody
	@RequestMapping("cancel")
	public void cancel(String id) throws Exception{
		try {
			fixedCheckItemService.cancel(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("作废失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据维修方案编号、机构代码查询需要排序的定检项目
	 * @param wxfabh,dprtcode
	 * @return List<Map<String, Object>>
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "queryOrderList", method = RequestMethod.POST)
	public List<Map<String, Object>> queryOrderList(String wxfabh,String dprtcode) throws BusinessException {
		try {
			return fixedCheckItemService.queryOrderList(wxfabh,dprtcode);
		} catch (Exception e) {
			throw new BusinessException("获取定检项目失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 
	 * @param model
	 * @return Map<String, Object>
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody FixedCheckItem fixedCheckItem)throws BusinessException {
		try {
			return fixedCheckItemService.queryAllPageList(fixedCheckItem);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!",e);
		}
	}
	
	
	/**
	 * @author liub
	 * @description 根据定检项目id查询定检项目信息
	 * @param id
	 * @return FixedCheckItem
	 * @develop date 2016.09.26
	 */
	@ResponseBody
	@RequestMapping(value = "selectByPrimaryKey", method = RequestMethod.POST)
	public FixedCheckItem selectByPrimaryKey(String id)throws BusinessException {
		try {
			return fixedCheckItemService.selectByPrimaryKey(id);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据id查询定检项目,包含制单人,审核人,批准人
	 * @param id
	 * @return FixedCheckItem
	 * @develop date 2016.09.26
	 */
	@ResponseBody
	@RequestMapping(value = "selectById", method = RequestMethod.POST)
	public FixedCheckItem selectById(String id)throws BusinessException {
		try {
			return fixedCheckItemService.selectById(id);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据维修方案编号查询定检项目信息,字段排序F_BBID desc,ZWMS desc
	 * @param wxfabh，dprtcode
	 * @return List<FixedCheckItem>
	 * @develop date 2016.09.28
	 */
	@ResponseBody
	@RequestMapping(value = "queryByWxfabh", method = RequestMethod.POST)
	public List<FixedCheckItem> queryByWxfabh(String wxfabh,String dprtcode)throws BusinessException {
		try {
			return fixedCheckItemService.queryByWxfabh(wxfabh,dprtcode);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据条件查询有差异维修方案的定检项目信息,字段排序djbh desc,ZWMS desc
	 * @param map
	 * @return List<FixedCheckItem>
	 * @develop date 2016.09.28
	 */
	@ResponseBody
	@RequestMapping(value = "queryByMap", method = RequestMethod.POST)
	public List<FixedCheckItem> queryByMap(@RequestParam Map<String, Object> map)throws BusinessException {
		try {
			return fixedCheckItemService.queryByMap(map);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 根据条件查询有差异维修方案的定检项目信息,字段排序djbh 
	 * @param model,djbh,dprtCode
	 * @return ModelAndView
	 * @develop date 2016.09.28
	 */
	@RequestMapping(value = "selectByZxbsAndBh", method = RequestMethod.GET)
	public ModelAndView selectByZxbsAndBh(Model model,String djbh,String dprtCode) throws BusinessException{
			FixedCheckItem fixedCheckItem=new FixedCheckItem();
			fixedCheckItem.setDjbh(djbh);
			fixedCheckItem.setZxbs(1);
			fixedCheckItem.setDprtcode(dprtCode);
			FixedCheckItem newFixedCheckItem = fixedCheckItemService.selectByZxbsAndBh(fixedCheckItem);
			if(newFixedCheckItem==null){
				throw new BusinessException("该定检项目不存在!");
			}
			model.addAttribute("significantCoefficientEnum", SignificantCoefficientEnum.enumToListMap());
			model.addAttribute("effectiveEnum", EffectiveEnum.enumToListMap());
			model.addAttribute("approveStatusEnum", ApproveStatusEnum.enumToListMap());
			model.addAttribute("fixedCheckItem", newFixedCheckItem);
			
			model.addAttribute("newbb", maintenanceService.getMaxBbByWxfabh(newFixedCheckItem.getWxfabh(),dprtCode));
			
		return new ModelAndView("project/maintenance/fixedcheckitem_view");
	}
	/**
	 * @author sunji
	 * @description 根据条件查询有差异维修方案的定检项目信息,字段排序djbh desc,ZWMS desc
	 * @param model,djbh,dprtCode
	 * @return ModelAndView
	 * @develop date 2016.09.28
	 */
	@ResponseBody
	@RequestMapping("djxmCount")
	public Map<String,Object> djxmCount(@RequestParam String djbh,@RequestParam String dprtCode) throws BusinessException{
		Map<String,Object> map=new HashMap<String, Object>();
			FixedCheckItem fixedCheckItem=new FixedCheckItem();
			fixedCheckItem.setDjbh(djbh);
			fixedCheckItem.setZxbs(1);
			fixedCheckItem.setDprtcode(dprtCode);
			FixedCheckItem newFixedCheckItem = fixedCheckItemService.selectByZxbsAndBh(fixedCheckItem);
			if(newFixedCheckItem==null){
				map.put("state", "error");
			}else{
				map.put("state", "success");
			}
		return map;
	}
}
