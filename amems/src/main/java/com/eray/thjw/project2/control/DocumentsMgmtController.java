package com.eray.thjw.project2.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;

import enu.common.DocumentEnum;

/**
 * @author hanwu
 * @description 工程文件管理控制层
 * @develop date 2017.08.08
 */
@Controller
@RequestMapping("/project2/document")
public class DocumentsMgmtController extends BaseController{
	
	/**
	 * 跳转至工程文件管理页面
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="project2:document:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Model model) {
		model.addAttribute("mkdm", DocumentEnum.FD_GCWD.getCode());
		return new ModelAndView("project2/document/document_main");
	}
	
}
