package com.eray.thjw.system.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.component.saibong.po.SaibongGz;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.system.service.SaiBongGzService;
import com.eray.thjw.util.ThreadVarUtil;

/**
 * 
 * @author ll
 * @description 采番规则
 */
@Controller
@RequestMapping("/sys/saibong")
public class SaiBongController extends BaseController {
	
	@Resource
	private SaiBongGzService saiBongGzService; 
	
	
	@Autowired
	private DepartmentService departmentService;
	/**
	 * 跳转至采番规则页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="sys:saibong:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		model.put("dprtcode", GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY));
		return new ModelAndView("/sys/saibong/saibong_main", model);
	}
	
	/**
	 *采番列表
	 * @param role
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "saibongList", method = RequestMethod.POST)
	public Map<String, Object> saibongList(@RequestBody SaibongGz saibongGz,HttpServletRequest request,Model model) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
				List<SaibongGz> list = saiBongGzService.queryAllList(saibongGz); 
				resultMap.put("rows",list);
		} catch (Exception e) {
			throw new BusinessException("采番列表查询失败",e);
		}
		
		return resultMap;
	}
	
	
	/**
	 * @author ll
	 * @description 修改
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="sys:saibong:main:01")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public void edit(@RequestBody SaibongGz saibongGz) throws BusinessException{
		try {
			
			saiBongGzService.update(saibongGz);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 预览
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="sys:saibong:main:01")
	@ResponseBody
	@RequestMapping(value = "previewSaibong", method = RequestMethod.POST)
	public String previewSaibong(@RequestBody SaibongGz saibongGz) throws BusinessException{
		String	previewStr=null;
		try {
			previewStr=saiBongGzService.previewSaibong(saibongGz);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return previewStr;
	}
	 
	
}
