package com.eray.thjw.material2.control;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.ToolBorrowReturn;
import com.eray.thjw.material2.service.ToolBorrowReturnService;
import com.eray.thjw.po.User;

/**
 * @author 裴秀
 * @description 工具/设备借归
 */
@Controller
@RequestMapping("material/toolborrowreturn")
public class ToolBorrowReturnController {
	
	@Resource
	private ToolBorrowReturnService toolBorrowReturnService;
	
	/**
	 * @Description 工具/设备借归
     * @CreateTime 2018年03月01日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:toolborrowreturn:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/toolborrowreturn/toolborrowreturn_main",model);
	
	}
	
	/**
	 * @Description 加载人员
	 * @CreateTime 2018年3月27日 下午3:25:54
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/loaduser", method = RequestMethod.POST)
	public User loadUser(@RequestBody Map<String, String> record) throws BusinessException{
		try {
			return toolBorrowReturnService.loadUser(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("加载人员失败!",e);
		}
	}
	
	/**
	 * @Description 加载工具
	 * @CreateTime 2018年3月27日 下午3:25:54
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/loadtool", method = RequestMethod.POST)
	public Stock loadTool(@RequestBody Map<String, String> record) throws BusinessException{
		try {
			return toolBorrowReturnService.loadTool(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("加载工具失败!",e);
		}
	}
	
	/**
	 * @Description 根据关键字查询人员/工具
	 * @CreateTime 2018年3月27日 下午3:25:54
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/loadbykeyword", method = RequestMethod.POST)
	public Map<String, Object> loadByKeyword(@RequestBody Map<String, String> record) throws BusinessException{
		try {
			return toolBorrowReturnService.loadByKeyword(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("根据关键字查询人员/工具失败!",e);
		}
	}
	
	/**
	 * @Description 工具/设备借归提交
	 * @CreateTime 2018年4月2日 下午3:20:30
	 * @CreateBy 韩武
	 * @param list
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public void doSubmit(@RequestBody List<ToolBorrowReturn> list) throws BusinessException{
		try {
			toolBorrowReturnService.doSubmit(list);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("工具/设备借归提交失败!",e);
		}
	}
}
