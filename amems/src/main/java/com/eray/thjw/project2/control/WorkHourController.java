package com.eray.thjw.project2.control;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.WorkHour;
import com.eray.thjw.project2.service.WorkHourService;

/**
 * @Description 工种工时控制层
 * @CreateTime 2017-8-19 下午3:42:43
 * @CreateBy 刘兵
 */
@Controller
@RequestMapping("/project2/workhour")
public class WorkHourController {

	@Resource
	private WorkHourService workHourService;
	
	/**
	 * @Description 根据条件查询工种工时列表
	 * @CreateTime 2017-8-19 下午3:49:49
	 * @CreateBy 刘兵
	 * @param workHour 工种工时
	 * @return List<WorkHour> 工种工时集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllList", method = RequestMethod.POST)
	public List<WorkHour> queryAllList(@RequestBody WorkHour workHour)throws BusinessException{
		try {
			return workHourService.queryAllList(workHour);
		} catch (Exception e) {
			throw new BusinessException("查询工种工时失败!",e);
		}
	}
}
