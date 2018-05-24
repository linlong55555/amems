package com.eray.thjw.produce.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;

import enu.common.DocumentEnum;

/**
 * 
 * @Description 生产文档
 * @CreateTime 2018年2月5日 下午1:56:55
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/produce/file")
public class ProductionDocumentsController extends BaseController{
	
	/**
	 * 跳转至生产文档管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="produce:file:scmain")
	@RequestMapping(value = "/scmain", method = RequestMethod.GET)
	public ModelAndView scmain(HttpServletRequest request) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("mkdm", DocumentEnum.WDGL_SC.getCode());
		return new ModelAndView("project2/document/document_main", model);
	}
	
	

}
