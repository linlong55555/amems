package com.eray.thjw.material2.control;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.dao.StoreInnerSearchMapper;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.SupervisoryLevelEnum;

/**
 * @author 裴秀
 * @description 库内查询
 */
@Controller
@RequestMapping("material/stock/tool")
public class ToolInsideController {
	
	@Resource
	private DepartmentService departmentService;
	
	
	@Resource
	private StoreInnerSearchMapper storeInnerSearchMapper;
	/**
	 * @Description 库内查询
     * @CreateTime 2018年03月08日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:stock:tool:inside")
	@RequestMapping(value = "inside", method = RequestMethod.GET)
	public ModelAndView inside(Map<String, Object> model)throws BusinessException {
		  model.put("isTool", true);
	    return new ModelAndView("/material2/stockmaterial/inside/inside_main",model);
	
	}
	
	/**
	 * @Description 库外查询
     * @CreateTime 2018年03月08日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:stock:tool:outside")
	@RequestMapping(value = "outside", method = RequestMethod.GET)
	public ModelAndView outside(Map<String, Object> model)throws BusinessException {
		User user = ThreadVarUtil.getUser();
		model.put("supervisoryLevelEnum", SupervisoryLevelEnum.enumToListMap());
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		model.put("isTool", true);
	    return new ModelAndView("/material2/stocktool/outside/outside_main",model);
	}
	
	/**
	 * @Description 库存明细账
     * @CreateTime 2018年03月08日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:stock:tool:detail")
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public ModelAndView detail(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/stocktool/detail/detail_main",model);
	    
	
	}
	
	
	/**
	 * 工具库存查询
	 * @param model
	 * @CreateBy 刘邓
	 * @return
	 */
	@Privilege(code="material:stock:tool:insideut")
	@RequestMapping(value = "insideut", method = RequestMethod.GET)
	public ModelAndView insideut(Map<String, Object> model){
		 model.put("isTool", true);
		return new 	ModelAndView("/material2/stocktool/insideut/insideut_main",model);	
	}
	
	
	/**
	 * 批量修改状态
	 * @param stock
	 * @CreateBy 刘邓
	 */
	@ResponseBody
	@RequestMapping(value="updateStatus",method=RequestMethod.POST)
	public int updateStatus(@RequestBody Stock stock){
		  return storeInnerSearchMapper.updateStatus(stock);
	}
	
}
