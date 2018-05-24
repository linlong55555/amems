package com.eray.thjw.aerialmaterial.control;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.Outstock;
import com.eray.thjw.aerialmaterial.po.OutstockDetail;
import com.eray.thjw.aerialmaterial.service.MaterialRepairService;
import com.eray.thjw.aerialmaterial.service.OutstockDetailService;
import com.eray.thjw.aerialmaterial.service.OutstockService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author 林龙
 * @param <OutstockSerivce>
 * @description 出库历史控制器
 */
@Controller
@RequestMapping("/outboundhistory/history")
public class OutboundHistoryController extends BaseController {

	
	@Resource
	private MaterialRepairService materialRepairService;
	
	@Resource
	private OutstockService outstockService;
	
	@Resource
	private OutstockDetailService outstockDetailService;
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 出库单列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "historyList", method = RequestMethod.POST)
	public Map<String, Object> historyList(@RequestBody Outstock outstock,HttpServletRequest request,Model model) throws BusinessException{
		
		outstock.getParamsMap().put("userId", SessionUtil.getUserFromSession(request).getId());
		outstock.getParamsMap().put("userType",SessionUtil.getUserFromSession(request).getUserType());
		
		PageHelper.startPage(outstock.getPagination());
		List<Outstock> list = this.outstockService.queryAllPageList(outstock);
		return PageUtil.pack4PageHelper(list, outstock.getPagination());
	}
	
	/**
	 * @author ll
	 * @description 检查是否可以撤销
	 * @param model,ids
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdMt")
	public Map<String, Object> checkUpdMt(HttpServletRequest request,String ids
			) throws Exception {
		return outstockService.checkUpdMt(SessionUtil.getUserFromSession(request),ids);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 撤销
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@Privilege(code="aerialmaterial:outstock:main:08")
	@ResponseBody
	@RequestMapping(value = "backout/{id}", method = RequestMethod.GET)
	public Map<String, Object> backout(@PathVariable String id,HttpServletRequest request,Model model) throws BusinessException{
		try {
			return outstockService.backout(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("撤销失败",e);
		}
		
	}
	 
	
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 出库航材列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "aviationList/{id}", method = RequestMethod.GET)
	public Map<String, Object> aviationList(@PathVariable String id,HttpServletRequest request,Model model) throws BusinessException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows",outstockDetailService.queryKeyList(id));
		return resultMap;
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 退库
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@Privilege(code="aerialmaterial:outstock:main:09")
	@ResponseBody
	@RequestMapping(value = "cancellingStock", method = RequestMethod.POST)
	public Map<String, Object> cancellingStock(@RequestBody OutstockDetail outstockDetail,HttpServletRequest request,Model model) throws BusinessException{
		
		return outstockDetailService.cancellingStock(outstockDetail);
	}
	
	/**
	 * @author ll
	 * @description 检查是否可以退库
	 * @param model,ids
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpd")
	public Map<String, Object> checkUpd(HttpServletRequest request,String ids,BigDecimal tksl
			) throws Exception {
		return outstockDetailService.checkUpd(SessionUtil.getUserFromSession(request),ids,tksl);
	}

	
	/**
	 * 
	 * @Description 查看出库单
	 * @CreateTime 2017年8月26日 下午2:59:26
	 * @CreateBy 林龙
	 * @param model
	 * @param id 
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,@RequestParam String id)throws BusinessException {
		try {
			model.addAttribute("viewid", id);
			return new ModelAndView("material/outstock/outboundhistory_view");
		} catch (Exception e) {
			throw new BusinessException("查看出库单失败！", e);
		}
	}
}
