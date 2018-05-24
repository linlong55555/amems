package com.eray.thjw.system.control;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.system.po.Login;
import com.eray.thjw.system.service.AccountService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.common.EnableEnum;

/**
 * 账号管理
 * @author xu.yong
 *
 */
@Controller
@RequestMapping("/sys/account")
public class AccountController extends BaseController {

	@Autowired
	private DepartmentService departmentService;
	
	@Resource
	private AccountService accountService;
	
	/**
	 * 进入账号管理列表界面
	 * @return
	 */
	@Privilege(code="sys:account:main")
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest req, HttpServletResponse resp){
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		
		return new ModelAndView("sys/account/account_main", model);
	}
	
	/**
	 * 账号管理列表查询
	 * @param entity
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> list(BaseEntity entity) throws BusinessException{
		return this.accountService.queryPageList(entity);
	}
	
	/**
	 * 账号管理列表查询
	 * @param entity
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/unbound",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> unboundList(BaseEntity entity) throws BusinessException{
		return this.accountService.queryUnboundPageList(entity);
	}
	
	/**
	 * 保存账号
	 * @param login
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public @ResponseBody String save(Login login) throws BusinessException{
		this.accountService.saveAccount(login);
		return login.getId();
	}
	
	/**
	 * 启用账号
	 */
	@RequestMapping(value="/enable/{id}",method=RequestMethod.POST)
	public @ResponseBody void enable(@PathVariable String id){
		this.accountService.updateAccountState(id, EnableEnum.ENABLED.getId());
	}
	
	/**
	 * 禁用账号
	 */
	@RequestMapping(value="/disable/{id}",method=RequestMethod.POST)
	public @ResponseBody void disable(@PathVariable String id){
		this.accountService.updateAccountState(id, EnableEnum.DISABLED.getId());
	}
	
	/**
	 * 重置账号密码
	 */

	@Privilege(code="sys:user:main:04")
	@RequestMapping(value="/reset/password/{id}",method=RequestMethod.POST)
	public @ResponseBody void resetPassword(@PathVariable String id){
		this.accountService.updateAccountPassword4Reset(id);
	}
	
	/**
	 * 删除账号
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	public @ResponseBody void delete(@PathVariable String id) throws BusinessException{
		this.accountService.deleteAccount(id);
	}
	
}
