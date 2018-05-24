package com.eray.thjw.produce.control;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.service.AircraftinfoService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.ThresholdEnum;

/** 
 * @Description 
 * @CreateTime 2017-9-11 上午10:57:56
 * @CreateBy 孙霁	
 */
@Controller
@RequestMapping("/aircraftinfo")
public class AircraftinfoController extends BaseController{

	@Resource
	private DepartmentService departmentService;
	@Resource
	private MonitorsettingsService monitorsettingsService;
	@Resource
	private AircraftinfoService aircraftinfoService;
	
	/**
	 * 
	 * @Description 跳转到飞机基础信息
	 * @CreateTime 2017-9-11 上午10:59:39
	 * @CreateBy 孙霁
	 * @return
	 */
	@Privilege(code="aircraftinfo:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage() {
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
		return new ModelAndView("produce/aircraftinfo/aircraftinfo_main",model);
	}
	
	/**
	 * 
	 * @Description 主列表查询
	 * @CreateTime 2017-8-15 下午6:43:47
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAll", method = RequestMethod.POST)
	public Map<String, Object> queryAll(@RequestBody Aircraftinfo aircraftinfo,HttpServletRequest request,Model model)throws BusinessException{
		try {
			Map<String, Object> resultMap = aircraftinfoService.queryAllPageList(aircraftinfo);
			//获取监控值
			Monitorsettings monitorsettings =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.DTZZ.getName(), aircraftinfo.getDprtcode());
			resultMap.put("monitorsettings", monitorsettings);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2017-9-23 下午4:21:50
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@Privilege(code="aircraftinfo:main:01")
	public String save(@RequestBody Aircraftinfo aircraftinfo,HttpServletRequest request) throws BusinessException{
		try {
			return aircraftinfoService.insert(aircraftinfo);
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("数据添加失败！",e);
		}
	}
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2017-9-23 下午4:22:05
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@Privilege(code="aircraftinfo:main:02")
	public void update(@RequestBody Aircraftinfo aircraftinfo,HttpServletRequest request) throws BusinessException{
		try { 
			aircraftinfoService.update(aircraftinfo);
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("数据添加失败！",e);
		}
	}

	
	
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017-9-22 上午10:04:46
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("selectById")
	public Map<String, Object> selectById(HttpServletRequest request,@RequestBody Aircraftinfo aircraftinfo) throws BusinessException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("aircraftinfo",  aircraftinfoService.selectByfjzchAndDprtcode(aircraftinfo));
			return resultMap;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询失败！",e);
		}
		
	}
	/**
	 * 
	 * @Description 启动或注销
	 * @CreateTime 2017-9-25 下午5:00:01
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "startOrcancel", method = RequestMethod.POST)
	@Privilege(code="aircraftinfo:main:03,aircraftinfo:main:04")
	public void startOrcancel(@RequestBody Aircraftinfo aircraftinfo,HttpServletRequest request) throws BusinessException{
		try { 
			aircraftinfoService.updateByPrimaryKeySelective(aircraftinfo);
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("操作失败！",e);
		}
	}
	/**
	 * 
	 * @Description 跳转到查看界面
	 * @CreateTime 2017-8-15 下午6:43:19
	 * @CreateBy 孙霁
	 * @param id
	 * @return aircraftinfo_view
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,@RequestParam String fjzch, @RequestParam String dprtcode)throws BusinessException {
			Aircraftinfo aircraftinfo = new Aircraftinfo();
			aircraftinfo.setFjzch(fjzch);
			aircraftinfo.setDprtcode(dprtcode);
			model.addAttribute("aircraftinfo", aircraftinfo);
			return new ModelAndView("produce/aircraftinfo/aircraftinfo_view");
	}
	
	/**
	 * @Description 根据机型查询飞机信息
	 * @CreateTime 2017年9月27日 上午10:48:16
	 * @CreateBy 韩武
	 * @param aircraftinfo
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectByFjjx", method = RequestMethod.POST)
	public List<Aircraftinfo> selectByFjjx(@RequestBody Aircraftinfo aircraftinfo) throws BusinessException {
		try {
			return aircraftinfoService.selectByFjjx(aircraftinfo);
		} catch (Exception e) {
			throw new BusinessException("根据机型查询飞机信息失败！",e);
		}
	}
	/**
	 * 
	 * @Description 获取飞机信息
	 * @CreateTime 2017年9月27日 下午2:32:03
	 * @CreateBy 岳彬彬
	 * @param aircraftinfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getFj")
	public  Aircraftinfo getFj(HttpServletRequest request,@RequestBody Aircraftinfo aircraftinfo) throws BusinessException {
		try {			
			return aircraftinfoService.getAircraftinfo(aircraftinfo);
		}  catch (Exception e) {
			throw new BusinessException("查询失败！",e);
		}
		
	}
	/**
	 * 
	 * @Description 根据组织机构获取飞机
	 * @CreateTime 2017年10月14日 上午11:16:00
	 * @CreateBy 岳彬彬
	 * @param request
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("getByDprtcode")
	public  List<Aircraftinfo> getByDprtcode(HttpServletRequest request, String dprtcode) throws BusinessException {
		try {			
			return aircraftinfoService.getFjByDprtcode(dprtcode);
		}  catch (Exception e) {
			throw new BusinessException("查询失败！",e);
		}
		
	}
}
