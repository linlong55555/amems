package com.eray.thjw.project2.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.project2.service.TechnicalService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.UserService;

/**
 * 
 * @Description 技术评估单查看
 * @CreateTime 2017年8月23日 下午5:42:15
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/project2/assessment/view")
public class AssessmentViewController {

	@Autowired
	private MonitorsettingsService monitorsettingsService;
	@Autowired
	private TechnicalService technicalService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PlaneModelDataService planeModelDataService;
	@Autowired
	private UserService userService;
	
	
	/**
	 * @Description 技术评估单列表跳转 
	 * @CreateTime 2017年8月14日 上午9:37:54
	 * @CreateBy 林龙
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:view:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			//初始化数据
			model = technicalService.initData();
			return new ModelAndView("project2/assessment/view/assessment_view_main",model);
		} catch (Exception e) {
			 throw new BusinessException("技术文件评估单列表跳转失败!",e);
		}
	}
}
