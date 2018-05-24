package com.eray.thjw.aerialmaterial.control;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.aerialmaterial.service.OutFieldStockService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.Airworthiness;

/**
 * 外场库存
 * @author hanwu
 *
 */
@Controller
@RequestMapping("aerialmaterial/outfieldstock")
public class OutFieldStockController {

	@Resource
	private OutFieldStockService outFieldStockService;
	
	/**
	 * 查询待选择的装上件
	 * @param outFieldStock
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value="/queryMountComponent",method={RequestMethod.POST})
	public Map<String, Object> queryMountComponent(@RequestBody OutFieldStock outFieldStock)
			throws BusinessException{
		try {
			return outFieldStockService.queryPageByParam(outFieldStock);
		} catch (Exception e) {
			throw new BusinessException("查询外场库存失败!",e);
		}
	}
	/**
	 * 
	 * @Description 
	 * @CreateTime 2018-3-19 上午10:42:50
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("selectById")
	public Map<String, Object> selectById(HttpServletRequest request,String id) throws BusinessException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("outFieldStock",  outFieldStockService.selectById(id));
			return resultMap;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询失败！",e);
		}
		
	}
	/**
	 * 
	 * @Description 根据id查询库存信息
	 * @CreateTime 2018-3-19 上午10:42:50
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("selectKcxqById")
	public Map<String, Object> selectKcxqById(HttpServletRequest request,String id) throws BusinessException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			return  outFieldStockService.selectKcxqById(id);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询失败！",e);
		}
		
	}
}
