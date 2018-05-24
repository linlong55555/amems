package com.eray.thjw.basic.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.basic.dao.ZoneStationMapper;
import com.eray.thjw.basic.po.Propertyright;
import com.eray.thjw.basic.po.ZoneStation;
import com.eray.thjw.basic.service.PropertyrightService;
import com.eray.thjw.basic.service.ZoneStationService;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.training.po.TrainingPlan;
import com.eray.thjw.training.po.TrainingPlanExportExcel;
import com.eray.thjw.util.ThreadVarUtil;

import enu.ThresholdEnum;
import enu.course.CycleEnum;
import enu.training.TrainingPlanStatusEnum;
import enu.training.TrainingPlanTypeEnum;



/**
 * 
 * @Description 产权数据管理控制层
 * @CreateTime 2018-2-5 上午10:42:41
 * @CreateBy 孙霁
 */
@Controller
@RequestMapping("/basic/propertyright")
public class PropertyrightController extends BaseController {

	@Resource
	private PropertyrightService propertyrightService;
	@Resource
	private DepartmentService departmentService;

	/**
	 * 
	 * @Description 跳转到产权数据管理页面
	 * @CreateTime 2018-2-5 上午10:44:24
	 * @CreateBy 孙霁
	 * @return
	 */
	@Privilege(code="basic:propertyright:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
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
		return new ModelAndView("basic/propertyright/propertyright_main",model);
	}
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2018-2-5 上午10:45:31
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAll", method = RequestMethod.POST)
	public Map<String, Object> queryAll(@RequestBody Propertyright propertyright,HttpServletRequest request,Model model)throws BusinessException{
		try {
			return propertyrightService.queryAll(propertyright);
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	/**
	 * 
	 * @Description 新增
	 * @CreateTime 2018-2-5 上午11:30:24
	 * @CreateBy 孙霁
	 * @param propertyright
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	/*@Privilege(code="basic:propertyright:main:01")*/
	public String add(@RequestBody Propertyright propertyright,HttpServletRequest request) throws BusinessException{
		try {
			return propertyrightService.insert(propertyright);
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("数据添加失败！",e);
		}
	}
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2018-2-5 上午11:32:07
	 * @CreateBy 孙霁
	 * @param propertyright
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	/*@Privilege(code="basic:propertyright:main:02")*/
	public String edit(@RequestBody Propertyright propertyright,HttpServletRequest request) throws BusinessException{
		try {
			return propertyrightService.update(propertyright);
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("数据修改失败！",e);
		}
	}
	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2018-2-5 上午11:32:01
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("invalid")
	/*@Privilege(code="basic:propertyright:main:03")*/
	public String invalid(HttpServletRequest request,String id) throws BusinessException {
		try {
			propertyrightService.delete(id);
			return null;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("作废失败！",e);
		}
	}
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017-9-14 下午5:27:23
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("selectById")
	public Map<String, Object> selectById(HttpServletRequest request,String id) throws BusinessException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("propertyright",  propertyrightService.selectById(id));
			return resultMap;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询失败！",e);
		}
		
	}
}



