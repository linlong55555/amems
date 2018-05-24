package com.eray.thjw.produce.control;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.WorkorderProcessHours;
import com.eray.thjw.produce.service.WorkorderProcessHoursService;

/** 
 * @Description 工单工序工时
 * @CreateTime 2017-10-23 下午7:51:34
 * @CreateBy 雷伟	
 */
@Controller
@RequestMapping("/produce/processHours145")
public class WorkorderProcessHoursController extends BaseController {

	@Resource
	private WorkorderProcessHoursService processHoursService;
	
	@ResponseBody
	@RequestMapping(value = "queryAllList", method = RequestMethod.POST)
	public List<WorkorderProcessHours> queryAllList(@RequestBody WorkorderProcessHours processHours)throws BusinessException {
		try {
			return processHoursService.queryListByParam(processHours);
		} catch (Exception e) {
			throw new BusinessException("工单列表加载失败!", e);
		}
	}
}
