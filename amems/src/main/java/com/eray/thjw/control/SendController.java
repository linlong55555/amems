package com.eray.thjw.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Send;
import com.eray.thjw.po.User;
import com.eray.thjw.service.SendService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/project/send")
public class SendController extends BaseController{

	@Autowired
	private SendService sendService;
	
	/**
	 * 跳转至通告传阅界面
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="project:send:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String CommonalityPage(Model model) {
		return "project/send/sendMain";
	}
	
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 页面视图system/user/userMain.jsp
	 * @throws BusinessException 
	 * @develop date 2016.08.05
	 */
	@ResponseBody
	@RequestMapping(value = "querySendList", method = RequestMethod.POST)
	public Object querySendList(@RequestBody Send send,HttpServletRequest request,Model model) throws BusinessException{
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		//给对象查询条件
		send.setJsrid(user.getId());
		send.setDprtcode(user.getJgdm());
		try {
			PageHelper.startPage(send.getPagination());
			List<Send> list = this.sendService.queryAll(send);
			return PageUtil.pack4PageHelper(list, send.getPagination());
		} catch (Exception e) {
			 throw new BusinessException("查询失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 页面视图system/user/userMain.jsp
	 * @throws BusinessException 
	 * @develop date 2016.08.05
	 */
	@ResponseBody
	@RequestMapping(value = "updateSend", method = RequestMethod.POST)
	public Object updateSend(@RequestBody Send send,HttpServletRequest request,Model model) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
		sendService.updateByPrimaryKeySelective(send);
		} catch (Exception e) {
		 throw new BusinessException("查询失败",e);
	}
		return resultMap;
	}
	
	
}



