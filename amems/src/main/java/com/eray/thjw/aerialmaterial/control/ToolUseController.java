package com.eray.thjw.aerialmaterial.control;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.ToolsUse;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.aerialmaterial.service.ToolsUseService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author zhuchao
 * @description 库存工具设备控制器
 */


@Controller
@RequestMapping("/outfield/toolsuse")
public class ToolUseController extends BaseController {
	@Resource
	private StockSerivce stockSerivce;
	@Resource
	private ToolsUseService toolsUseService;
	
	
	/**
	 * 跳转至工具借用管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="outfield:toolsuse:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/outfield/toolsuse/toolsuse_main", model);
	}
	
	 /**
	  * 查询工具,设备列表
	  * @param stock
	  * @param request
	  * @param model
	  * @return
	  * @throws BusinessException
	  */
	@Privilege(code="outfield:toolsuse:main")
	@ResponseBody
	@RequestMapping(value = "/queryList4Tool", method = RequestMethod.POST)
	public Map<String, Object> queryList4Tool(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		String id = stock.getId();
		stock.setId(null);
		
		PageHelper.startPage(stock.getPagination());
		List<Stock> list = this.stockSerivce.queryList4Tool(stock);
		if(((Page)list).getTotal() > 0){
			queryOne4Tool(id, list);
			return PageUtil.pack4PageHelper(list, stock.getPagination());
		} else {
			list = new ArrayList<Stock>();
			queryOne4Tool(id, list);
			return PageUtil.pack( list.size(), list, stock.getPagination());
		}
		 
	}

	public void queryOne4Tool(String id, List<Stock> list) {
		if (StringUtils.isNotBlank(id)) {
			Stock stockParam = new Stock();
			stockParam.setId(id);
			List<Stock> stocklist = this.stockSerivce.queryList4Tool(stockParam);
			if (stocklist!=null && !stocklist.isEmpty()) {
				list.add(0,stocklist.get(0));
			}
		}
	}
	
	/**
	 * 查询工具,设备借用列表
	 * @param record
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="outfield:toolsuse:main")
	@ResponseBody
	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	public Map<String, Object> stockList(@RequestBody ToolsUse record,HttpServletRequest request,Model model) throws BusinessException{
		PageHelper.startPage(record.getPagination());
		List<ToolsUse> list = this.toolsUseService.queryList(record);
		return PageUtil.pack4PageHelper(list, record.getPagination());
	}
	
	/**
	 * 借出申请
	 * @param record
	 * @param request
	 * @param model
	 * @throws BusinessException
	 */
	@Privilege(code="outfield:toolsuse:main:01")
	@ResponseBody
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	public Map<String, Object> apply(@RequestBody ToolsUse record,HttpServletRequest request,Model model) throws BusinessException{
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			record.setParamsMap(paramsMap);
			Map<String, Object> result = this.toolsUseService.doApply(record);
			return result;
		} catch (Exception e) {
			throw new BusinessException("借出申请失败",e);
		}
	}
	
	/**
	 * 借出确认
	 * @param record
	 * @param request
	 * @param model
	 * @throws BusinessException
	 */
	@Privilege(code="outfield:toolsuse:main:02")
	@ResponseBody
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public Map<String, Object> confirm(@RequestBody ToolsUse record,HttpServletRequest request,Model model) throws BusinessException{
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			record.setParamsMap(paramsMap);
			Map<String, Object> result = this.toolsUseService.doConfirm(record);
			return result;
		} catch (Exception e) {
			throw new BusinessException("借出确认失败",e);
		}
	}
	
	/**
	 * 快速借出确认
	 * @param record
	 * @param request
	 * @param model
	 * @throws BusinessException
	 */
	@Privilege(code="outfield:toolsuse:main:04")
	@ResponseBody
	@RequestMapping(value = "/confirmFast", method = RequestMethod.POST)
	public Map<String, Object> confirmFast(@RequestBody ToolsUse record,HttpServletRequest request,Model model) throws BusinessException{
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			record.setParamsMap(paramsMap);
			Map<String, Object> result = this.toolsUseService.doConfirmFast(record);
			return result;
		} catch (Exception e) {
			throw new BusinessException("借出确认失败",e);
		}
	}
	
	
	/**
	 * 借出归还
	 * @param record
	 * @param request
	 * @param model
	 * @throws BusinessException
	 */
	@Privilege(code="outfield:toolsuse:main:03")
	@ResponseBody
	@RequestMapping(value = "/revert", method = RequestMethod.POST)
	public Map<String, Object> revert(@RequestBody ToolsUse record,HttpServletRequest request,Model model) throws BusinessException{
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			record.setParamsMap(paramsMap);
			Map<String, Object> result = this.toolsUseService.doReturn(record);
			return result;
		} catch (Exception e) {
			throw new BusinessException("借出归还失败",e);
		}
	}
	
	/**
	 * 指定结束
	 * @param record
	 * @param request
	 * @param model
	 * @throws BusinessException
	 */
	@Privilege(code="outfield:toolsuse:main:05")
	@ResponseBody
	@RequestMapping(value = "/end", method = RequestMethod.POST)
	public Map<String, Object> end(@RequestBody ToolsUse record,HttpServletRequest request,Model model) throws BusinessException{
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			record.setParamsMap(paramsMap);
			Map<String, Object> result = this.toolsUseService.doEnd(record);
			return result;
		} catch (Exception e) {
			throw new BusinessException("指定结束失败",e);
		}
	}
	
}
