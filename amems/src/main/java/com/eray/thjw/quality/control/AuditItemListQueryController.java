package com.eray.thjw.quality.control;

import java.util.HashMap;
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
import com.eray.thjw.quality.po.AuditItem;
import com.eray.thjw.quality.service.AuditItemListService;
/**
 * 
 * @Description 审核项目单
 * @CreateTime 2017年12月26日 
 * @CreateBy 裴秀
 */
@Controller
@RequestMapping("/quality/audititemlistquery")
public class AuditItemListQueryController {
	
	

	@Resource
	private AuditItemListService auditItemListService;
	
	/**
	 * @Description 审核项目单
     * @CreateTime 2017年12月26日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="quality:audititemlistquery:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
	 	model = new HashMap<String, Object>();
	    return new ModelAndView("/quality/audititemlistquery/audit_item_list_query",model);
	
	}
	
	/**
	 * 
	 * @Description 查询审核单数据
	 * @CreateTime 2018-1-5 上午10:42:28
	 * @CreateBy 孙霁
	 * @param audititem
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAll", method = RequestMethod.POST)
	public Map<String, Object> queryAll(@RequestBody AuditItem audititem,HttpServletRequest request,Model model)throws BusinessException{
		try {
			return auditItemListService.queryAll(audititem);
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	


}
