package com.eray.thjw.training.control;

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
 * @Description 培训文档
 * @CreateTime 2018年2月5日 下午1:56:55
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/training/file")
public class TrainingDocumentsController extends BaseController{
	
	/**
	 * 跳转至培训文档管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="training:file:zlmain")
	@RequestMapping(value = "/zlmain", method = RequestMethod.GET)
	public ModelAndView zlmain(HttpServletRequest request) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("mkdm", DocumentEnum.WDGL_PX.getCode());
		return new ModelAndView("project2/document/document_main", model);
	}
	
	

}
