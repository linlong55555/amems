package com.eray.thjw.material2.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;

/**
 * @author 裴秀
 * @description 工具销毁下架
 */
@Controller
@RequestMapping("material/destroy/tool")
public class MaterialDestroyToolController {
	/**
	 * @Description 工具销毁下架
     * @CreateTime 2018年03月22日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:destroy:tool:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Model model)throws BusinessException {
	    return new ModelAndView("/material2/destory/tool/tool_main");
	
	}
}
