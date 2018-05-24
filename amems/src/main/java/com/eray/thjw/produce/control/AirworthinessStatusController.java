package com.eray.thjw.produce.control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.service.AirworthinessStatusService;
import com.eray.thjw.production.po.AirworthinessDirective;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.util.SessionUtil;

/**
 * @Description 适航状态
 * @CreateTime 2017年9月11日 下午5:48:58
 * @CreateBy 朱超
 */
@Controller
@RequestMapping("/aircraftinfo/airworthiness")
public class AirworthinessStatusController extends BaseController {
	
	@Resource
	private PlaneDataService planeDataService; 
	
	@Resource
	private AirworthinessStatusService airworthinessStatusService; 
	
	
	@Privilege(code = "aircraftinfo:airworthiness:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("produce/airworthinessstatus/airworthinessstatus_main", responseParamMap);
	}
	
	@RequestMapping(value = "/plans")
	@ResponseBody
	public List<PlaneData> plans(HttpServletRequest request,String dprtcode)
			throws BusinessException {
		//查询飞机
		PlaneData planeData = new PlaneData();
		planeData.setDprtcode(dprtcode);
		List<PlaneData> planes = planeDataService.queryPlanes(planeData);
		return planes;
	}
	
	/**
	 * 查询适航指令（分页）
	 * @param request
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/queryList",method={RequestMethod.POST})
	public Map<String, Object> queryList(HttpServletRequest request, AirworthinessDirective record) throws BusinessException {
		try {
			record.getParamsMap().put("userId", SessionUtil.getUserFromSession(request).getId());
			record.getParamsMap().put("userType", SessionUtil.getUserFromSession(request).getUserType());
			Map<String, Object> result = airworthinessStatusService.queryList(record);
			return result;
		} catch (RuntimeException e) {
			throw new BusinessException("查询适航指令失败",e);
		}
	}
	
	/**
	 * 新增一条适航指令
	 * @param request
	 * @param record
	 * @throws BusinessException
	 */
	@Privilege(code="aircraftinfo:airworthiness:saveorupdate")
	@ResponseBody
	@RequestMapping(value="/saveOrUpdate",method={RequestMethod.POST})
	public AirworthinessDirective saveOrUpdate(HttpServletRequest request,  AirworthinessDirective record) throws BusinessException {
		airworthinessStatusService.saveOrUpdate(record);
		return record;
	}
	
	/**
	 * 查询一个适航指令
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/load",method={RequestMethod.GET})
	public AirworthinessDirective view(HttpServletRequest request,Model model,String id) throws BusinessException {
		AirworthinessDirective record = airworthinessStatusService.load(id);
		return record;
	}
	
	/**
	 * 删除一条适航指令
	 * @param request
	 * @param record
	 * @throws BusinessException
	 */
	@Privilege(code="aircraftinfo:airworthiness:cancel")
	@ResponseBody
	@RequestMapping(value="/cancel",method={RequestMethod.POST})
	public void cancel(HttpServletRequest request, AirworthinessDirective record) throws BusinessException {
		airworthinessStatusService.cancel(record);
	}
	
	/**
	 * 绑定日期处理格式
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
