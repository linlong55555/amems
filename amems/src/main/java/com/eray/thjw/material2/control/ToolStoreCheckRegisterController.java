package com.eray.thjw.material2.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;

import enu.MaterialTypeEnum;
import enu.aerialmaterial.MaterialSrouceEnum;
/**
 * @author 裴秀
 * @description 盘点
 */
@Controller
@RequestMapping("material/tool/store/check")
public class ToolStoreCheckRegisterController {
	
	/**
	 * @Description 盘点
     * @CreateTime 2018年03月20日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:tool:store:check:register")
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public ModelAndView register(Model model){
		model.addAttribute("type", 2);
		model.addAttribute("materialSrouceEnum", MaterialSrouceEnum.enumToListMap());
		model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
	    return new ModelAndView("/material2/store/check/check_main");
	
	}
	
	/**
	 * @Description 盈亏历史
     * @CreateTime 2018年03月21日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:tool:store:check:list")
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView list(Model model){
		model.addAttribute("type", 2);
	    return new ModelAndView("/material2/store/list/list_main");
	}
	
}
