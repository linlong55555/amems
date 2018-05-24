package com.eray.thjw.produce.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.SystemException;
import com.eray.thjw.po.PlaneBase;
import com.eray.thjw.produce.po.CrewSchedule;
import com.eray.thjw.produce.service.CrewScheduleService;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.service.PlaneBaseService;

/**
 * 
 * @Description 飞机排班控制器
 * @CreateTime 2017年10月27日 下午4:03:04
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/produce/scheduling/aircraft")
public class AircraftSchedulingController extends BaseController {
	
	@Resource
	private PlaneBaseService planeBaseService;
	
	@Resource
	private CrewScheduleService crewScheduleService;
	
	
	/**
	 * 跳转至飞机排班页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="produce:scheduling:aircraft:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main() throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("produce/aircraftscheduling/aircraftscheduling_main", model);
	}
	 
	/**
	 * 飞机排班数据查询
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value="queryPlaneScheduling",method={RequestMethod.POST})
	public List<PlaneData> queryPlaneScheduling(@RequestBody PlaneData pd) throws BusinessException{
		try {
			return crewScheduleService.queryPlaneScheduling(pd);
		} catch (Exception e) {
			throw new BusinessException("飞机排班数据查询失败!",e);
		}
	}
	
	/**
	 * 保存飞机排班数据
	 * @param crewScheduleList
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "savePlaneScheduling", method = { RequestMethod.POST })
	public void savePlaneScheduling(@RequestBody List<CrewSchedule> crewScheduleList) throws BusinessException {
		try {
			crewScheduleService.savePlaneScheduling(crewScheduleList);
		} catch (SystemException e) {
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("保存飞机排班数据失败！",e);
		}
	}
	
	/**
	 * 取消飞机排班数据
	 * @param crewScheduleList
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "cancelPlaneScheduling", method = { RequestMethod.POST })
	public void cancelPlaneScheduling(@RequestBody CrewSchedule crewSchedule) throws BusinessException {
		try {
			crewScheduleService.doCancelPlaneScheduling(crewSchedule, true);
		} catch (SystemException e) {
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("取消飞机排班数据查询失败！",e);
		}
	}
	
	/**
	 * 加载飞机排班数据
	 * @param crewScheduleList
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "loadPlaneScheduling", method = { RequestMethod.POST })
	public List<CrewSchedule> loadPlaneScheduling(@RequestBody CrewSchedule crewSchedule) throws BusinessException {
		try {
			return crewScheduleService.loadPlaneScheduling(crewSchedule);
		} catch (Exception e) {
			throw new BusinessException("加载飞机排班数据查询失败！",e);
		}
	}
	
	/**
	 * 飞机排班数据查询-飞机视图
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value="queryPlaneSchedulingInPlaneView",method={RequestMethod.POST})
	public PlaneData queryPlaneSchedulingInPlaneView(@RequestBody Map<String, Object> paramMap) throws BusinessException{
		try {
			return crewScheduleService.queryPlaneSchedulingInPlaneView(paramMap);
		} catch (Exception e) {
			throw new BusinessException("飞机排班数据-飞机视图查询失败!",e);
		}
	}
	
	/**
	 * 查询基地
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value="queryBase",method={RequestMethod.POST})
	public List<PlaneBase> queryBase(@RequestBody CrewSchedule crewSchedule) throws BusinessException{
		try {
			return planeBaseService.findBaseByDprtcode(crewSchedule.getDprtcode());
		} catch (Exception e) {
			throw new BusinessException("查询基地失败!",e);
		}
	}
}
