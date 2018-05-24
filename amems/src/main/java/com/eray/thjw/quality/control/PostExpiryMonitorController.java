package com.eray.thjw.quality.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.service.MaintenancePersonnelService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.ThresholdEnum;

/** 
 * @Description 
 * @CreateTime 2017-11-15 上午10:28:50
 * @CreateBy 孙霁	
 */
@Controller
@RequestMapping("/quality/post/expiry")
public class PostExpiryMonitorController extends BaseController {

	@Resource
	private DepartmentService departmentService;
	@Resource
	private MonitorsettingsService monitorsettingsService;
	@Resource
	private MaintenancePersonnelService maintenancePersonnelService;
	/**
	 * 
	 * @Description 跳转到岗位到期监控
	 * @CreateTime 2017-11-15 上午10:28:50
	 * @CreateBy 孙霁
	 * @return
	 */
	@Privilege(code="quality:post:expiry:monitor")
	@RequestMapping(value = "monitor", method = RequestMethod.GET)
	public ModelAndView CommonalityPage() {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		model.put("dprtcode", GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY));
		return new ModelAndView("quality/postexpirymonitoring/postexpirymonitoring_main",model);
	}
	
	/**
	 * 
	 * @Description 主列表查询
	 * @CreateTime 2017-8-15 下午6:43:47
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAll", method = RequestMethod.POST)
	public Map<String, Object> queryAll(@RequestBody MaintenancePersonnel maintenancePersonnel,HttpServletRequest request,Model model)throws BusinessException{
		try {
			Map<String, Object> resultMap = maintenancePersonnelService.queryAllMonitor(maintenancePersonnel);
			//获取监控值
			Monitorsettings monitorsettings =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.GWDQJK.getName(), maintenancePersonnel.getDprtcode());
			resultMap.put("monitorsettings", monitorsettings);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	
	/**
	 * @Description 导出
	 * @CreateTime 2017-12-22 下午1:09:33
	 * @CreateBy 刘兵
	 * @param paramjson 当前参数
	 * @return String 页面数据
	 * @throws BusinessException
	 */
	@RequestMapping(value = "postmonitor.xls", method = RequestMethod.GET)
	public String exportExcelMt(String paramjson, HttpServletRequest request, Model model) throws BusinessException {
		try {
		    paramjson = new String(paramjson.getBytes("iso-8859-1"),"utf-8");
		    MaintenancePersonnel obj = JSON.parseObject(paramjson, MaintenancePersonnel.class);
			List<MaintenancePersonnel> list = maintenancePersonnelService.doExportExcel(obj);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "postmonitor", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	
}
