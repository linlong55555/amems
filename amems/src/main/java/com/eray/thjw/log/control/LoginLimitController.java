package com.eray.thjw.log.control;

import java.util.HashMap;
import java.util.List;
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
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.log.po.LoginLimit;
import com.eray.thjw.log.service.LoginLimitService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author ll
 * @description ipmac
 */
@Controller
@RequestMapping("/sys/loginlimit")
public class LoginLimitController extends BaseController {
	
	@Resource
	private LoginLimitService loginLimitService; 
	
	/**
	 * 跳转至登陆限制页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="sys:loginlimit:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("/sys/loginlimit/loginlimit_main", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 登录限制列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "loginlimitList", method = RequestMethod.POST)
	public Map<String, Object> loginlimitList(@RequestBody LoginLimit loginLimit,HttpServletRequest request,Model model) throws BusinessException{
		try {
			
			PageHelper.startPage(loginLimit.getPagination());
			List<LoginLimit> list = this.loginLimitService.queryAllPageList(loginLimit);
			return PageUtil.pack4PageHelper(list, loginLimit.getPagination());

		} catch (Exception e) {
			throw new BusinessException("查询失败",e);
		}
	}
	
	/**
	 * @author ll
	 * @description 新增
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="sys:loginlimit:main:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void save(@RequestBody LoginLimit loginLimit) throws BusinessException{
		try {
			
			loginLimitService.save(loginLimit);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 修改
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="sys:loginlimit:main:02")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public void edit(@RequestBody LoginLimit loginLimit) throws BusinessException{
		try {
			
			loginLimitService.update(loginLimit);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}
	 
	/**
	 * @author ll
	 * @description 作废
	 * @param request,id
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="sys:loginlimit:main:03")
	@ResponseBody
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request,String id) throws BusinessException{
		try {
			loginLimitService.cancel(id);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
}
