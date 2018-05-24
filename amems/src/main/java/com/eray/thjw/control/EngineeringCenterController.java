package com.eray.thjw.control;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.EngineeringCenterService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.ThreadVarUtil;

/**
 * @author liub
 * @description 工程中心
 * @develop date 2017.03.14
 */
@Controller
@RequestMapping("/engineering/center")
public class EngineeringCenterController {
	
	/**
	 * @author liub
	 * @description 工程中心service
	 * @develop date 2017.03.14
	 */
	@Autowired
	private EngineeringCenterService engineeringCenterService;
	
	@Resource
	private UserService userService;

	/**
	 * @author liub
	 * @description 跳转至工程中心页面
	 * @param model
	 * @return 页面视图
	 * @develop date 2017.03.09
	 *
	 */
	@Privilege(code = "engineering:center:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model) {
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		List<User> users = userService.getByDprtcode(user.getJgdm());
		model.addAttribute("userToRole", users);
		model.addAttribute("monitorType", "");
		return "project/engineeringcenter/engineering_center_main";
	}
	
	/**
	 * @author liub
	 * @description 跳转至CAD监控页面
	 * @param model
	 * @return 页面视图
	 *
	 */
	@Privilege(code = "engineering:center:cad")
	@RequestMapping(value = "cad", method = RequestMethod.GET)
	public String cad(Model model) {
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		List<User> users = userService.getByDprtcode(user.getJgdm());
		model.addAttribute("userToRole", users);
		model.addAttribute("monitorType", "CAD");
		return "project/engineeringcenter/engineering_center_main";
	}
	
	/**
	 * @author liub
	 * @description 跳转至SB监控页面
	 * @param model
	 * @return 页面视图
	 *
	 */
	@Privilege(code = "engineering:center:sb")
	@RequestMapping(value = "sb", method = RequestMethod.GET)
	public String sb(Model model) {
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		List<User> users = userService.getByDprtcode(user.getJgdm());
		model.addAttribute("userToRole", users);
		model.addAttribute("monitorType", "SB");
		return "project/engineeringcenter/engineering_center_main";
	}
	
	/**
	 * @author liub
	 * @description 根据评估单id查询下达指令数据
	 * @param id
	 * @return List<Map<String, Object>>
	 * @develop date 2017.03.15
	 */
	@ResponseBody
	@RequestMapping(value = "queryByPgdId", method = RequestMethod.POST)
	public List<Map<String, Object>> queryByMap(String id)throws BusinessException {
		try {
			return engineeringCenterService.queryByPgdId(id);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
}
