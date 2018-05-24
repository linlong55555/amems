package com.eray.thjw.material2.control;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.service.OutFieldStockService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.SupervisoryLevelEnum;

/** 
 * @Description 
 * @CreateTime 2018-3-23 下午4:50:16
 * @CreateBy 孙霁	
 */
@Controller
@RequestMapping("material/tool/store")
public class ToolTransferController {

	@Resource
	private DepartmentService departmentService;
	
	@Resource
	private OutFieldStockService outFieldStockService;
	@Resource
	private StockSerivce stockSerivce;
	
	/**
	 * @Description 移库
     * @CreateTime 2018年03月20日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:tool:store:transfer")
	@RequestMapping(value = "transfer", method = RequestMethod.GET)
	public ModelAndView transfer(Map<String, Object> model)throws BusinessException {
		User user = ThreadVarUtil.getUser();
		model.put("supervisoryLevelEnum", SupervisoryLevelEnum.enumToListMap());
		model.put("hclxType",2);
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
	    return new ModelAndView("/material2/store/transfer/transfer_main",model);
	}
}
