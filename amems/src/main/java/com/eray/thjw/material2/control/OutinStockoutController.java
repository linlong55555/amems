package com.eray.thjw.material2.control;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.OutboundOrder;
import com.eray.thjw.material2.service.OutboundOrderService;

import enu.MaterialTypeEnum;
import enu.project2.TechnicalSysEnum;

/**
 * 
 * @Description 出库
 * @CreateTime 2018年3月8日 上午9:50:52
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("material/outin")
public class OutinStockoutController extends BaseController{
	
	@Resource
	private StockSerivce stockSerivce;
	@Resource
	private OutboundOrderService outboundOrderService;
	
	/**
	 * 
	 * @Description 出库
	 * @CreateTime 2018年3月8日 上午9:51:09
	 * @CreateBy 林龙
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="material:outin:stockout")
	@RequestMapping(value = "stockout", method = RequestMethod.GET)
	public ModelAndView stockout(Map<String, Object> model)throws BusinessException {
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
	    return new ModelAndView("/material2/outin/stockout/stockout_main",model);
	
	}
	
	/**
	 * 
	 * @Description 出库-选择出库物料-库存信息
	 * @CreateTime 2018年3月14日 下午2:10:40
	 * @CreateBy 林龙
	 * @param stock
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "stockList", method = RequestMethod.POST)
	public Map<String, Object> stockList(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		try {
			return stockSerivce.queryOutinAllPageList(stock);
		} catch (Exception e) {
			throw new BusinessException("出库-选择出库物料-库存信息加载失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 出库分页列表
	 * @CreateTime 2018年3月14日 下午2:10:40
	 * @CreateBy 林龙
	 * @param stock
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody OutboundOrder outboundOrder,HttpServletRequest request,Model model) throws BusinessException{
		try {
			return outboundOrderService.queryAllPageList(outboundOrder);
		} catch (Exception e) {
			throw new BusinessException("出库列表加载失败!",e);
		}
	}
	
	/**
	 * @Description 保存出库单
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
//	@Privilege(code="project2:assessment:main:01")
	@ResponseBody
	@RequestMapping(value = "saveOutboundOrder", method = RequestMethod.POST)
	public String saveOutboundOrder(@RequestBody OutboundOrder outboundOrder) throws BusinessException{
		try {
			return outboundOrderService.save(outboundOrder);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存出库单失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 查询出库单数据
	 * @CreateTime 2018年3月16日 下午2:51:09
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getByStockoutId", method = RequestMethod.POST)
	public OutboundOrder getByStockoutId(@RequestBody OutboundOrder outboundOrder)throws BusinessException {
		try {
			return outboundOrderService.getByStockoutId(outboundOrder);
		} catch (Exception e) {
			throw new BusinessException("查询出库单数据失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 修改出库单
	 * @CreateTime 2018年3月15日 下午3:21:09
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
//	@Privilege(code="project2:assessment:main:02")
	@ResponseBody
	@RequestMapping(value = "updateOutboundOrder", method = RequestMethod.POST)
	public String update(@RequestBody OutboundOrder outboundOrder) throws BusinessException{
		try {
			return outboundOrderService.update(outboundOrder);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改出库单失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 新增提交出库单
	 * @CreateTime 2018年3月15日 下午3:22:32
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
//	@Privilege(code="project2:assessment:main:01")
	@ResponseBody
	@RequestMapping(value = "saveSubmit", method = RequestMethod.POST)
	public String saveSubmit(@RequestBody OutboundOrder outboundOrder) throws BusinessException{
		try {
			return outboundOrderService.saveSubmit(outboundOrder);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("提交出库单失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 修改提交出库单
	 * @CreateTime 2018年3月15日 下午3:23:22
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
//	@Privilege(code="project2:assessment:main:02")
	@ResponseBody
	@RequestMapping(value = "updateSubmit", method = RequestMethod.POST)
	public String updateSubmit(@RequestBody OutboundOrder outboundOrder) throws BusinessException{
		try {
			return outboundOrderService.updateSubmit(outboundOrder);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("提交出库单失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 删除出库单
	 * @CreateTime 2018年3月15日 下午3:24:24
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @throws BusinessException
	 */
//	@Privilege(code="project2:assessment:main:03")
	@ResponseBody
	@RequestMapping(value = "deleteOut", method = RequestMethod.POST)
	public void delete(@RequestBody OutboundOrder outboundOrder) throws BusinessException{
		try {
			outboundOrderService.delete(outboundOrder);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除出库单失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 撤销出库单
	 * @CreateTime 2018年3月15日 下午3:24:24
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @throws BusinessException
	 */
//	@Privilege(code="project2:assessment:main:03")
	@ResponseBody
	@RequestMapping(value = "revokeOut", method = RequestMethod.POST)
	public String revoke(@RequestBody OutboundOrder outboundOrder) throws BusinessException{
		try {
			return outboundOrderService.updaterevoke(outboundOrder);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("撤销出库单失败!",e);
		}
	}
	

	
	/**
	 * 
	 * @Description 查看出库单信息
	 * @CreateTime 2017年8月26日 下午2:59:26
	 * @CreateBy 林龙
	 * @param model
	 * @param id 
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "viewOut", method = RequestMethod.GET)
	public ModelAndView viewOut(Model model,@RequestParam String id)throws BusinessException {
		try {
			model.addAttribute("viewid", id);
			return new ModelAndView("/material2/outin/stockout/stockout_find");
		} catch (Exception e) {
			throw new BusinessException("查看出库单信息！", e);
		}
	}

}
