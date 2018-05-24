
package com.eray.thjw.project2.control;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.util.SessionUtil;

import enu.SaiBongEnum;

/**
 * 待办事宜
 * 
 * @author ll
 *
 */
@Controller
@RequestMapping("/project2/todo")
public class ToDoControl {

	@Resource
	private TodoService todoService;

	@Resource
	private SaibongUtilService saibongUtilService;
	
	/**
	 * 
	 * @Description 返回页面
	 * @CreateTime 2017年8月30日 下午6:36:31
	 * @CreateBy 李高升
	 * @param annunciate
	 * @return
	 */
	@Privilege(code = "project2:todo:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(Annunciate annunciate) {
		
		try {
			
			String val = saibongUtilService.generate("00009", SaiBongEnum.CJJL.getName(), "zc");
			System.out.println(val);
		} catch (SaibongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ModelAndView("project2/todo/todo_main");
	}

	/**
	 * 
	 * @Description
	 * @CreateTime 2017年8月28日 下午7:57:41
	 * @CreateBy 李高升
	 * @param todo
	 * @return
	 */
	@Privilege(code = "project2:todo:main")
	@ResponseBody
	@RequestMapping(value = "getToDOList", method = RequestMethod.POST)
	public Map<String, Object> getToDOList(HttpServletRequest request , @RequestBody Todo todo) {
		User user = SessionUtil.getUserFromSession(request);
		todo.setBlrid(user.getId());
		todo.setDprtcode(user.getJgdm());
		return todoService.getToDOList(todo);
	}

	/**
	 * 
	 * @Description 反馈操作
	 * @CreateTime 2017年8月30日 上午10:38:39
	 * @CreateBy 李高升
	 * @param todo
	 * @throws BusinessException
	 */
	
	@ResponseBody
	@RequestMapping(value = "doFeedBack", method = RequestMethod.POST)
	public void doFeedBack(@RequestBody Todo todo) throws BusinessException {
		try {
			todoService.doFeedBack(todo);
		} catch (Exception e) {
			throw new BusinessException("反馈失败!", e);
		}
	}
	
	/**
	 * @Description 根据id获取数据
	 * @CreateTime 2018-4-23 下午4:51:08
	 * @CreateBy 刘兵
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectById",method={RequestMethod.POST,RequestMethod.GET})
	public Todo selectById(String id){
		return todoService.selectByPrimaryKey(id);
	}
	
}
