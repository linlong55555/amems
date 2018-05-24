package com.eray.thjw.produce.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.PlaneBase;
import com.eray.thjw.produce.po.CrewSchedule;
import com.eray.thjw.produce.po.CrewScheduleObject;
import com.eray.thjw.produce.service.CrewScheduleObjectService;
import com.eray.thjw.produce.service.CrewScheduleService;
import com.eray.thjw.service.PlaneBaseService;
import com.eray.thjw.util.PageUtil;

/**
 * 
 * @Description 人员排班 
 * @CreateTime 2017年10月27日 下午4:02:41
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/produce/scheduling/person")
public class CrewScheduleController {
	@Autowired
	private CrewScheduleService crewScheduleService;
	@Autowired
	private CrewScheduleObjectService crewScheduleObjectService;
	@Resource
	private PlaneBaseService planeBaseService;

	@Privilege(code = "produce:scheduling:person:main")
	@RequestMapping("main")
	public ModelAndView main(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			return new ModelAndView("produce/schedule/CrewScheduling",model);
		} catch (Exception e) {
			throw new BusinessException("人员排班置列表跳转失败!",e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "crewScheduleList", method = RequestMethod.POST)
	public Map<String, Object> flightrecordList(@RequestBody CrewSchedule crewSchedule, HttpServletRequest request,
			Model model) throws Exception {
		List<CrewSchedule> crewScheduleList = crewScheduleService.queryAllPageList(crewSchedule);
		return PageUtil.pack(crewScheduleList, crewSchedule.getId());

	}
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestBody CrewSchedule crewSchedule) throws BusinessException {
		try {
			CrewScheduleObject crewScheduleObject = new CrewScheduleObject();
			crewScheduleObject.setMainid(crewSchedule.getId());
			crewScheduleObjectService.updateZtByMainid(crewScheduleObject);
			return "";
		} catch (Exception e) {
			throw new BusinessException("跳转至人员排班修改页面失败!", e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public CrewSchedule edit(@RequestBody CrewSchedule crewSchedule) throws BusinessException {
		try {
			crewSchedule = crewScheduleService.queryCrewScheduleById(crewSchedule.getId());
			return crewSchedule;
		} catch (Exception e) {
			throw new BusinessException("跳转至人员排班修改页面失败!", e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@RequestBody CrewSchedule crewSchedule ) throws BusinessException {
		try {			
			crewSchedule.getPbrq().setHours(8);
			crewScheduleService.updateCrewScheduleById(crewSchedule);
			return crewSchedule.getId();
		} catch (Exception e) {
			throw new BusinessException("修改失败!", e);
		}
	}
	/**
	 * 根据组织机构查询对应的基地
	 * @param planBase
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/findJd", method = RequestMethod.POST)
	public List<PlaneBase>  findJd(@RequestBody PlaneBase planBase ) throws BusinessException {
		try {
			List<PlaneBase> planBaseList=planeBaseService.findBaseByDprtcode(planBase.getDprtcode());
			return planBaseList;
		} catch (Exception e) {
			throw new BusinessException("跳转至人员排班修改页面失败!", e);
		}
	}
}
