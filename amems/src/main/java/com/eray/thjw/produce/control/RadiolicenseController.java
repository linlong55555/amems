package com.eray.thjw.produce.control;

import java.util.HashMap;
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
 * @Description 飞机三证监控
 * @CreateTime 2017年10月19日 下午2:53:03
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/quality/radiolicense")
public class RadiolicenseController  extends BaseController{
	
	@Resource
	private DepartmentService departmentService;
	
	@Resource
	private MonitorsettingsService monitorsettingsService;
	
	@Resource
	private AircraftinfoService aircraftinfoService;
	
	/**
	 * @Description 飞机三证监控
	 * @CreateTime 2017年10月19日 下午3:05:42
	 * @CreateBy 林龙
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:radiolicense:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			User user = ThreadVarUtil.getUser();
			if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
					//所有机构代码
				model.put("accessDepartments", departmentService.queryOrg());
			}else{
					//非超级机构获取当前用户机构代码
				model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
			}
			return new ModelAndView("produce/radiolicense/radiolicense_main",model);
		} catch (Exception e) {
			throw new BusinessException("飞机三证监控列表跳转失败!",e);
		}
	}
	
	/**
	 * @Description 飞机三证监控列表
	 * @CreateTime 2017年10月19日 下午3:08:21
	 * @CreateBy 林龙
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
			Map<String, Object> resultMap = aircraftinfoService.queryszAllPageList(aircraftinfo);
			//获取监控值
			Monitorsettings monitorsettings =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.DTZZ.getName(), aircraftinfo.getDprtcode());
			resultMap.put("monitorsettings", monitorsettings);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("飞机三证监控查询失败!", e);
		}
	}
	
	/**
	 * @Description 修改飞机三证监控
	 * @CreateTime 2017年10月20日 上午10:02:15
	 * @CreateBy 林龙
	 * @param aircraftinfo
	 * @param request
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "updatesz", method = RequestMethod.POST)
	@Privilege(code="quality:radiolicense:main:01")
	public String update(@RequestBody Aircraftinfo aircraftinfo,HttpServletRequest request) throws BusinessException{
		try { 
			return aircraftinfoService.updatesz(aircraftinfo);
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("修改飞机三证监控失败!",e);
		}
	}

}
