package com.eray.thjw.aerialmaterial.control;


import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;


/**
 * @author 裴秀
 * @description 工具快速借用/归还
 */
@Controller
@RequestMapping("/outfield/toolsquick")
public class ToolsQuickController extends BaseController {
	/**
	 * @Description 跳转到工具快速借用/归还
     * @CreateTime 2018年01月19日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="outfield:toolsquick:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material/fast_borrow_return/fast_borrow_return",model);
	
	}

}
