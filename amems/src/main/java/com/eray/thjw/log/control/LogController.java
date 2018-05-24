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
import com.eray.thjw.log.po.Field;
import com.eray.thjw.log.po.LogAccessRule;
import com.eray.thjw.log.service.LogService;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.system.po.SysLog;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author zhuchao
 * @description 故障保留单控制器
 */
@Controller
@RequestMapping("/sys/log")
public class LogController extends BaseController {
	
	@Resource
	private LogService logService; 
	
	 
	/**
	 * 查询日志表头
	 * @param rule
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/queryFields",method={RequestMethod.POST})
	public List<Field> queryFields(@RequestBody LogAccessRule rule) throws BusinessException {
		try {
			List<Field> columns = logService.queryFields(rule);
			return columns;
		}catch (BusinessException e) {
			throw e;
		}catch (RuntimeException e) {
			throw new BusinessException("查询日志失败",e);
		} 
	}
	
	 /**
	 * 查询指定业务关键字下日志列表
	 * @param code 日志关键字（业务表名，参考TableEnum）
	 * @param id 日志关键字（业务表名，参考TableEnum）
	 * @param czls 日志关键字（业务表名，参考TableEnum）
	 * 
	 * @return
	 * @throws BusinessException
	 */
//	@Privilege(code="sys:log:list")
	@ResponseBody
	@RequestMapping(value="/queryList",method={RequestMethod.POST})
	public Map<String, Object> queryList(@RequestBody LogAccessRule rule) throws BusinessException {
		try {
			Map<String, Object> result = logService.queryList(rule);
			return result;
		}catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("查询日志失败",e);
		} 
	}
	
	/**
	 * 查询指定业务关键字下指定流水的日志以与上一版本的日志差异
	 * @param code 日志关键字（业务表名，参考TableEnum）
	 * @param czls 操作流水
	 * @param id 业务表主键 ID
	 * @return
	 * @throws BusinessException
	 */
//	@Privilege(code="sys:log:queryDifference")
	@ResponseBody
	@RequestMapping(value="/queryDifference",method={RequestMethod.POST})
	public LogAccessRule queryDifference( @RequestBody BaseEntity param) throws BusinessException {
		try {
			LogAccessRule result = logService.queryDifference(param);
			return result;
		}catch (BusinessException e) {
			throw e;
		}catch (RuntimeException e) {
			throw new BusinessException("查询日志失败",e);
		} 
	}
	/*public LogAccessRule queryDifference(String code,String czls,String id) throws BusinessException {
		try {
			LogAccessRule result = logService.queryDifference(code,czls,id);
			return result;
		}catch (BusinessException e) {
			throw e;
		}catch (RuntimeException e) {
			throw new BusinessException("查询日志失败",e);
		} 
	}*/
	
	/**
	 * 跳转至登陆日志页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="sys:log:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/sys/log/loginlog_main", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 登录日志列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "loginlogList", method = RequestMethod.POST)
	public Map<String, Object> loginlogList(@RequestBody SysLog sysLog,HttpServletRequest request,Model model) throws BusinessException{
		PageHelper.startPage(sysLog.getPagination());
		List<SysLog> list = this.logService.queryAllPageList(sysLog);
		return PageUtil.pack4PageHelper(list, sysLog.getPagination());
	}
	
	 
}
