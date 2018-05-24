package com.eray.thjw.project2.control;

import java.util.HashMap;
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
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.service.AirworthinessService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.ThresholdEnum;
/**
 * 
 * @Description 待办事宜明细
 * @CreateTime 2017-8-18 下午8:13:21
 * @CreateBy 孙霁
 */
@Controller
@RequestMapping("/project2/todors")
public class TodorsController {
	
	@Resource
	private TodorsService todorsService;

	/**
	 * 
	 * @Description 
	 * @CreateTime 2017-8-18 下午8:15:08
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("selectTodorsBylyid")
	public Map<String, Object> selectTodorsBylyid(HttpServletRequest request,String lyid) throws BusinessException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("todorsList", todorsService.selectTodorsBylyid(lyid));
			return resultMap;
		} catch (BusinessException e) {
			throw new BusinessException("查询失败",e);
		}
		
	}
}
