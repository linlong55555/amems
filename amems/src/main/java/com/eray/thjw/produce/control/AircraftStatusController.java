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
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.AircraftinfoStatus;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.service.AircraftStatusService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.ThresholdEnum;

/**
 * @Description 飞机状态
 * @CreateTime 2017年9月11日 下午5:42:38
 * @CreateBy 韩武
 */
@Controller
@RequestMapping("/aircraftinfo/status")
public class AircraftStatusController extends BaseController {
	
	@Resource
	private DepartmentService departmentService;
	@Resource
	private AircraftStatusService aircraftStatusService;
	
	@Privilege(code = "aircraftinfo:status:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			responseParamMap.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			responseParamMap.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		responseParamMap.put("dprtcode", GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY));
		return new ModelAndView("produce/aircraftstatus/aircraftstatus_main", responseParamMap);
	}
	
	/**
	 * 
	 * @Description 查询所有数据（试航状态，监控项数据）
	 * @CreateTime 2017-10-23 下午2:14:04
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
			Map<String, Object> resultMap = new HashMap<String, Object>();
			AircraftinfoStatus aircraftinfoStatus = new AircraftinfoStatus();
			aircraftinfoStatus.setFjzch(aircraftinfo.getFjzch());
			aircraftinfoStatus.setDprtcode(aircraftinfo.getDprtcode());
			resultMap.put("row", aircraftStatusService.selectBystatus(aircraftinfo));
			resultMap.put("rows", aircraftStatusService.queryAll(aircraftinfoStatus));
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	/**
	 * 
	 * @Description 查询所有数据（试航状态，监控项数据）
	 * @CreateTime 2017-10-23 下午2:14:04
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllAir", method = RequestMethod.POST)
	public Map<String, Object> queryAllAir(@RequestBody Aircraftinfo aircraftinfo,HttpServletRequest request,Model model)throws BusinessException{
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("rows", aircraftStatusService.queryAllAir(aircraftinfo));
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	
	/**
	 * 
	 * @Description 加载执行历史
	 * @CreateTime 2017-10-27 下午2:45:11
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPerformHistory", method = RequestMethod.POST)
	public Map<String, Object> queryAllPerformHistory(@RequestBody Workorder workorder,HttpServletRequest request,Model model)throws BusinessException{
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("rows", aircraftStatusService.queryAllPerformHistory(workorder));
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	
}
