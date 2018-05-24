package com.eray.thjw.aerialmaterial.control;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.ReserveDetail;
import com.eray.thjw.aerialmaterial.po.ReserveMain;
import com.eray.thjw.aerialmaterial.po.ReserveWorkOrder;
import com.eray.thjw.aerialmaterial.service.ReserveMainService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.github.pagehelper.PageHelper;

import enu.EnquiryStatusEnum;
import enu.MaterialProgressEnum;
import enu.MaterialTypeEnum;
import enu.ReserveStatusEnum;
import enu.ThresholdEnum;
import enu.UrgencyEnum;

/**
 * @author liub
 * @description 航材提订管理控制层
 * @develop date 2016.10.10
 */
@Controller
@RequestMapping("/aerialmaterial/reserve")
public class ReserveController extends BaseController {
	
	/**
	 * @author liub
	 * @description 航材提订管理service
	 * @develop date 2016.10.12
	 */
	@Autowired
	private ReserveMainService reserveMainService;
	
	/**
	 * @author liub
	 * @description 系统阀值设置 Service
	 * @develop date 2016.12.22
	 */
	@Autowired
	private MonitorsettingsService monitorsettingsService;
	
	/**
	 * @author liub
	 * @description 跳转至航材提订申请界面
	 * @param model,request
	 * @return 页面视图
	 * @develop date 2016.10.10
	 *
	 */
	@Privilege(code="aerialmaterial:reserve:manage")
	@RequestMapping(value = "manage", method = RequestMethod.GET)
	public String manage(Model model,HttpServletRequest request,String bjh,String dprtcode) throws BusinessException {
		model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
		model.addAttribute("reserveStatusEnum", ReserveStatusEnum.enumToListMap());
		model.addAttribute("materialProgressEnum", MaterialProgressEnum.enumToListMap());
		User user = SessionUtil.getUserFromSession(request);
		model.addAttribute("username", user.getUsername());
		model.addAttribute("type", "manage");
		
		
		model.addAttribute("bjh", bjh);
		model.addAttribute("dprtcode", dprtcode);
		return "material/reserve/reserve_main";
	}
	
	/**
	 * @author liub
	 * @description 跳转至航材提订确认界面
	 * @param model,request
	 * @return 页面视图
	 * @develop date 2016.10.10
	 *
	 */
	@Privilege(code="aerialmaterial:reserve:approve")
	@RequestMapping(value = "approve", method = RequestMethod.GET)
	public String approve(Model model,HttpServletRequest request) throws BusinessException {
		model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
		model.addAttribute("reserveStatusEnum", ReserveStatusEnum.enumToListMap());
		model.addAttribute("materialProgressEnum", MaterialProgressEnum.enumToListMap());
		model.addAttribute("type", "approve");
		return "material/reserve/reserve_main";
	}
	
	/**
	 * @author liub
	 * @description 查看提订单
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.09.19
	 *
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,String id) throws BusinessException{
		getLogger().info("初始化查看提订单  前端参数:id:{}", new Object[]{id});
		try {
			model.addAttribute("reserveMain", reserveMainService.selectById(id));
			model.addAttribute("reserveStatusEnum", ReserveStatusEnum.enumToListMap());
			model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
			model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		} catch (Exception e) {
			throw new BusinessException("查看查看提订单失败!",e);
		}
		return new ModelAndView("material/reserve/reserve_view");
	}
	
	/**
	 * @author liub
	 * @description 初始化增加提订单
	 * @param request,response
	 * @return 页面视图
	 * @develop date 2016.10.11
	 *
	 */
	@Privilege(code="aerialmaterial:reserve:manage:01")
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("urgencyEnum", UrgencyEnum.enumToListMap());
			model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		} catch (Exception e) {
			throw new BusinessException("初始化增加提订单失败!",e);
		}
		return new ModelAndView("material/reserve/reserve_add", model);
	}
	
	/**
	 * @author liub
	 * @description 保存提订单
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:reserve:manage:03")
	@ResponseBody
	@RequestMapping(value = "addSave", method = RequestMethod.POST)
	public String add(@RequestBody ReserveMain reserve) throws BusinessException{
		getLogger().info("增加提订单  前端参数:reserve:{}", new Object[]{reserve});
		try {
			reserve.setZt(ReserveStatusEnum.SAVE.getId());
			return reserveMainService.add(reserve);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("增加提订单失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 提交提订单
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:reserve:manage:04")
	@ResponseBody
	@RequestMapping(value = "addSubmit", method = RequestMethod.POST)
	public String addSubmit(@RequestBody ReserveMain reserve) throws BusinessException{
		getLogger().info("提交提订单  前端参数:reserve:{}", new Object[]{reserve});
		try {
			reserve.setZt(ReserveStatusEnum.SUBMIT.getId());
			return reserveMainService.add(reserve);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("提交提订单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 检查提订单编辑权限
	 * @param request,id
	 * @develop date 2016.10.13
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkEdit", method = RequestMethod.POST)
	public void checkEdit(HttpServletRequest request,String id) throws BusinessException {
		getLogger().info("检查提订单编辑权限 前端参数:id:{}", new Object[]{id});
		try {
			reserveMainService.checkEdit(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("查询提订单失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 初始化编辑提订单
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.10.13
	 *
	 */
	@Privilege(code="aerialmaterial:reserve:manage:02")
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(Model model,String id) throws BusinessException{
		getLogger().info("初始化编辑提订单  前端参数:id:{}", new Object[]{id});
		try {
			model.addAttribute("reserveMain", reserveMainService.selectById(id));
			model.addAttribute("reserveStatusEnum", ReserveStatusEnum.enumToListMap());
			model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
			model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		} catch (Exception e) {
			throw new BusinessException("初始化编辑提订单失败!",e);
		}
		return new ModelAndView("material/reserve/reserve_edit");
	}
	

	/**
	 * @author liub
	 * @description 编辑后保存提订单
	 * @param reserve
	 * @develop date 2016.10.14
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:reserve:manage:02")
	@ResponseBody
	@RequestMapping(value = "editSave", method = RequestMethod.POST)
	public void editSave(@RequestBody ReserveMain reserve) throws BusinessException{
		getLogger().info("编辑后保存提订单  前端参数:reserve:{}", new Object[]{reserve});
		try {
			reserveMainService.edit(reserve);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存提订单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 编辑后提交提订单
	 * @param reserve
	 * @develop date 2016.10.14
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:reserve:manage:02")
	@ResponseBody
	@RequestMapping(value = "editSubmit", method = RequestMethod.POST)
	public void editSubmit(@RequestBody ReserveMain reserve) throws BusinessException{
		getLogger().info("编辑后提交提订单  前端参数:reserve:{}", new Object[]{reserve});
		try {
			reserve.setZt(ReserveStatusEnum.SUBMIT.getId());
			reserveMainService.edit(reserve);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("提交提订单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 审核通过,修改询价状态为待询价
	 * @param reserve
	 * @develop date 2016.12.16
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:reserve:approve:01")
	@ResponseBody
	@RequestMapping(value = "updateEquiryStatus", method = RequestMethod.POST)
	public void updateEquiryStatus(@RequestBody ReserveDetail reserveDetail) throws BusinessException{
		getLogger().info("修改询价状态  前端参数:reserveDetail:{}", new Object[]{reserveDetail});
		try {
			reserveDetail.setXjzt(EnquiryStatusEnum.WAIT_ENQUIRY.getId());
			reserveMainService.updateReserveDetail(reserveDetail);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("审核失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 检查提订单审核权限
	 * @param request,id
	 * @return
	 * @develop date 2016.10.14
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkAudit", method = RequestMethod.POST)
	public void checkAudit(HttpServletRequest request,String id) throws BusinessException {
		getLogger().info("检查提订单审核权限 前端参数:id:{}", new Object[]{id});
		try {
			reserveMainService.checkAudit(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("查询提订单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 初始化审核提订单
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.10.13
	 *
	 */
	@Privilege(code="aerialmaterial:reserve:approve:01")
	@RequestMapping(value = "audit", method = RequestMethod.GET)
	public ModelAndView audit(Model model,String id) throws BusinessException{
		getLogger().info("初始化审核提订单  前端参数:id:{}", new Object[]{id});
		try {
			model.addAttribute("reserveMain", reserveMainService.selectById(id));
			model.addAttribute("reserveStatusEnum", ReserveStatusEnum.enumToListMap());
			model.addAttribute("urgencyEnum", UrgencyEnum.enumToListMap());
			model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		} catch (Exception e) {
			throw new BusinessException("初始化审核提订单失败!",e);
		}
		return new ModelAndView("material/reserve/reserve_audit");
	}
	
	/**
	 * @author liub
	 * @description 审核后保存提订单
	 * @param reserve
	 * @develop date 2016.10.14
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:reserve:approve:01")
	@ResponseBody
	@RequestMapping(value = "auditSave", method = RequestMethod.POST)
	public void auditSave(@RequestBody ReserveMain reserve) throws BusinessException{
		getLogger().info("审核后保存提订单  前端参数:reserve:{}", new Object[]{reserve});
		try {
			reserveMainService.editAudit(reserve);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("审核提订单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 审核后提交提订单
	 * @param reserve
	 * @develop date 2016.10.14
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:reserve:approve:01")
	@ResponseBody
	@RequestMapping(value = "auditSubmit", method = RequestMethod.POST)
	public void auditSubmit(@RequestBody ReserveMain reserve) throws BusinessException{
		getLogger().info("审核后提交提订单  前端参数:reserve:{}", new Object[]{reserve});
		try {
			reserve.setZt(ReserveStatusEnum.AUDIT_PASS.getId());
			reserveMainService.editAudit(reserve);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("审核提订单失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 作废
	 * @param request,id
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="aerialmaterial:reserve:manage:05")
	@ResponseBody
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request,String id) throws BusinessException{
		getLogger().info("作废操作  前端参数:id{}", new Object[]{id});
		try {
			reserveMainService.cancel(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("作废失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 指定结束
	 * @param reserve
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="aerialmaterial:reserve:approve:02")
	@ResponseBody
	@RequestMapping(value = "shutDown", method = RequestMethod.POST)
	public void shutDown(@RequestBody ReserveMain reserve) throws BusinessException{
		getLogger().info("指定结束  前端参数:id{}", new Object[]{reserve});
		try {
			reserve.setZt(ReserveStatusEnum.CLOSE.getId());
			reserveMainService.updateShutDown(reserve);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("指定结束失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询航材提订单信息
	 * @param reserve
	 * @return Map<String, Object>
	 * @develop date 2016.10.12
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody ReserveMain reserve)throws BusinessException {
		try {
			return reserveMainService.queryAllPageList(reserve);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询审核航材提订单信息
	 * @param reserve
	 * @return Map<String, Object>
	 * @develop date 2016.10.14
	 */
	@ResponseBody
	@RequestMapping(value = "queryApprovePageList", method = RequestMethod.POST)
	public Map<String, Object> queryApprovePageList(@RequestBody ReserveMain reserve)throws BusinessException {
		try {
			return reserveMainService.queryApprovePageList(reserve);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据提订单id查询提订详情信息
	 * @param request,mainid
	 * @return List<ReserveDetail>
	 * @develop date 2016.10.13
	 */
	@ResponseBody
	@RequestMapping(value = "queryReserveDetailListByMainId", method = RequestMethod.POST)
	public List<ReserveDetail> queryReserveDetailListByMainId(String mainid,HttpServletRequest request)throws BusinessException {
		try {
			return reserveMainService.queryReserveDetailListByMainId(mainid);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据提订单id集合查询提订详情信息
	 * @param request,mainidList
	 * @return List<Map<String, Object>>
	 * @develop date 2016.10.13
	 */
	@ResponseBody
	@RequestMapping(value = "queryReserveDetailListByIds", method = RequestMethod.POST)
	public List<Map<String, Object>> queryReserveDetailListByIds(@RequestParam("mainidList[]")List<String> mainidList,HttpServletRequest request)throws BusinessException {
		try {
			return reserveMainService.queryReserveDetailListByIds(mainidList);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询航材提订详情信息(弹窗)
	 * @param reserveDetail
	 * @return Map<String, Object>
	 * @develop date 2016.11.01
	 */
	@ResponseBody
	@RequestMapping(value = "queryReserveDetailPageList", method = RequestMethod.POST)
	public Map<String, Object> queryReserveDetailPageList(@RequestBody ReserveDetail reserveDetail)throws BusinessException {
		try {
			PageHelper.startPage(reserveDetail.getPagination());
			List<Map<String, Object>> list = reserveMainService.queryReserveDetailPageList(reserveDetail);
			return PageUtil.pack4PageHelper(list, reserveDetail.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据mainid查询相关工单信息
	 * @param request,mainid
	 * @return List<ReserveDetail>
	 * @develop date 2016.11.18
	 */
	@ResponseBody
	@RequestMapping(value = "queryWorkOrderListByMainId", method = RequestMethod.POST)
	public List<ReserveWorkOrder> queryWorkOrderListByMainId(String mainid,HttpServletRequest request)throws BusinessException {
		try {
			return reserveMainService.queryWorkOrderListByMainId(mainid);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据dprtcode查询系统阀值
	 * @param dprtcode
	 * @return Map<String, Object>
	 * @develop date 2017.04.17
	 */
	@ResponseBody
	@RequestMapping(value = "getByKeyDprtcode", method = RequestMethod.POST)
	public Map<String, Object> getByKeyDprtcode(String dprtcode)throws BusinessException {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("monitorsettings", monitorsettingsService.getByKeyDprtcode(ThresholdEnum.TDYQ.getName(),dprtcode));
			return model;
		} catch (Exception e) {
			throw new BusinessException("查询系统阀值失败!",e);
		}
	}
	
}
