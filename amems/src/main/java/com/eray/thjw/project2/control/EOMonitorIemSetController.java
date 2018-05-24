package com.eray.thjw.project2.control;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.EOMonitorIemSet;
import com.eray.thjw.project2.service.EOMonitorIemSetService;

/** 
 * @Description EO监控项设置  控制层
 * @CreateTime 2017-8-24 下午8:03:34
 * @CreateBy 雷伟	
 */
@Controller
@RequestMapping("/project2/eomonitoriemset")
public class EOMonitorIemSetController extends BaseController {
	@Resource
	private EOMonitorIemSetService eoMonitorIemSetService;
	
	/**
	 * @Description 查询EO监控项设置 
	 * @CreateTime 2017-8-24 下午3:25:16
	 * @CreateBy 雷伟
	 * @param eoMonitorIemSet EO监控项设置
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllList", method = RequestMethod.POST)
	public List<EOMonitorIemSet> queryAllList(@RequestBody EOMonitorIemSet eoMonitorIemSet)throws BusinessException{
		try {
			return eoMonitorIemSetService.queryAllList(eoMonitorIemSet);
		} catch (Exception e) {
			throw new BusinessException("查询EO监控项设置失败!",e);
		}
	}
}
