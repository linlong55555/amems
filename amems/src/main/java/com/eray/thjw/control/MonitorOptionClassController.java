package com.eray.thjw.control;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.MonitorOptionClass;
import com.eray.thjw.po.MonitorOptionItem;
import com.eray.thjw.service.MonitorOptionClassService;

/**
 * @author liub
 * @description 定检监控大类控制层
 * @develop date 2016.08.26
 */
@Controller
@RequestMapping("/component/monitorclass")
public class MonitorOptionClassController {

	/**
	 * @author liub
	 * @description 监控大类service
	 * @develop date 2016.08.22
	 */
	@Autowired
	private MonitorOptionClassService monitorClassService;
	
	/**
	 * 获取监控设置
	 * @param
	 * @return List<MonitorOptionClass>
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="queryMonitorList",method={RequestMethod.POST,RequestMethod.GET})
	public List<MonitorOptionClass> queryMonitorList() throws BusinessException{

		List<MonitorOptionClass> list ;
		
		try {
			list = monitorClassService.queryAll();
		} catch (Exception e) {
			throw new BusinessException("获取监控设置信息失败!");
		}finally{}

		return list;
	}
	
	/**
	 * 获取监控项设置
	 * @param
	 * @return List<MonitorOptionClass>
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="queryOptionList",method={RequestMethod.POST,RequestMethod.GET})
	public List<MonitorOptionItem> queryOptionList() throws BusinessException{

		List<MonitorOptionItem> list ;
		try {
			list = monitorClassService.findOptionAll();
		} catch (Exception e) {
			throw new BusinessException("获取监控项设置信息失败!");
		}finally{}
		return list;
	}

}
