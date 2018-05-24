package com.eray.thjw.control;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.util.SessionUtil;

import enu.PrivilegeTypeEnum;


/**
 * @author liub
 * @description  用户权限控制层
 * @develop date 2016.08.11
 */
@Controller
@RequestMapping("/sys/privilege")
public class PrivilegeController {
	
	private static final Logger LOG = LoggerFactory.getLogger(PrivilegeController.class);
	
	/**
	 * @author liub
	 * @description 获取所有的模块id(用户拥有的权限)
	 * @param request,model
	 * @return String
	 * @develop date 2016.08.11
	 */
	@ResponseBody
	@RequestMapping(value="queryMenuIds", method = RequestMethod.POST)
	public List<Integer> queryMenuIds(HttpServletRequest request,Model model){
		
		List<Integer> menuIds = SessionUtil.getMenuIdsFromSession(request);
		
		LOG.info("结果:menuIds:{}", new Object[]{menuIds});
		
		return menuIds;
	}
	
	/**
	 * @author liub
	 * @description 根据模块查询菜单权限(用户有用的权限)
	 * @param request,model
	 * @return String
	 * @develop date 2016.08.11
	 */
	@ResponseBody
	@RequestMapping(value="queryMenuPri", method = RequestMethod.GET)
	public List<Map<String, Object>> queryMenuPri(HttpServletRequest request,Model model,
			@RequestParam(value = "id",required = true) String id
			){
		LOG.info("前端参数:id:{}", new Object[]{id});
		
		List<Map<String, Object>> menuTree = SessionUtil.getMenuTreeFromSession(request, id);
		
		LOG.info("结果:menuTree:{}", new Object[]{menuTree});
		
		return menuTree;
	}
	
	/**
	 * @author liub
	 * @description 查询菜单按钮权限
	 * @param request,model
	 * @return String
	 * @develop date 2016.08.11
	 */
	@ResponseBody
	@RequestMapping(value="queryMenuButton", method = RequestMethod.POST)
	public List<String> queryMenuButton(HttpServletRequest request,Model model) {
	
		List<String> buttonCodeList = SessionUtil.getCodeFromSession(request, PrivilegeTypeEnum.BUTTON.toString());
		
		LOG.info("结果:buttonCodeList:{}", new Object[]{buttonCodeList});
		
		return buttonCodeList;
	}
}
