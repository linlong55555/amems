package com.eray.thjw.produce.control;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.FilgthResumeList;
import com.eray.thjw.produce.po.FlightSheetVoyage;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.service.FlighthistoryService;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;

/** 
 * @Description 
 * @CreateTime 2017-9-11 上午10:57:56
 * @CreateBy 孙霁	
 */
@Controller
@RequestMapping("/produce/flighthistory")
public class FlighthistoryController extends BaseController{

	@Resource
	private DepartmentService departmentService;
	@Resource
	private FlighthistoryService flighthistoryService;
	@Resource(name="flighthistoryExcelImporter")
	private BaseExcelImporter<LoadingList> excelImporter;
	@Resource(name="flightResumeExcelImporter")
	private BaseExcelImporter<FilgthResumeList> fxllExcelImporter;
	
	/**
	 * 
	 * @Description 跳转到飞行履历
	 * @CreateTime 2017-9-11 上午10:59:39
	 * @CreateBy 孙霁
	 * @return
	 */
	@Privilege(code="produce:flighthistory:main")
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
		return new ModelAndView("produce/flighthistory/flighthistory_main",model);
	}
	
	/**
	 * 
	 * @Description 主列表查询
	 * @CreateTime 2017-8-15 下午6:43:47
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAll", method = RequestMethod.POST)
	public Map<String, Object> queryAll(@RequestBody FlightSheetVoyage flightSheetVoyage,HttpServletRequest request,Model model)throws BusinessException{
		try {
			return flighthistoryService.queryAll(flightSheetVoyage);
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	
	/**
	 * 飞机装机清单导入
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,  
		    HttpServletResponse response,@RequestParam(value="fjzch" ,required=true) String fjzch
		    ) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.setParam("fjzch", fjzch);
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "飞行记录本导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "飞行记录本导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 飞行履历导入
	 * @Description 
	 * @CreateTime 2017-11-29 上午11:38:14
	 * @CreateBy 雷伟
	 * @param multipartRequest
	 * @param response
	 * @param dprtcode 组织机构码
	 * @param fjjx 飞机机型
	 * @param fjzch 飞机注册号
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:flighthistory:main:01")
	@ResponseBody
	@RequestMapping(value = "/fxll/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,HttpServletResponse response,
			String dprtcode,String fjjx,String fjzch) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			fxllExcelImporter.setParam("dprtcode", dprtcode);
			fxllExcelImporter.setParam("fjjx", fjjx);
			fxllExcelImporter.setParam("fjzch", fjzch);
			fxllExcelImporter.setParam(AbstractExcelImporter.STARTINDEX_NAME, 3); //修改起始行索引，如果不写默认是2(即从第3行开始读取excel数据)
			fxllExcelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "飞行履历导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "飞行履历导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2017-12-21 下午2:40:30
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:flighthistory:main:02")
	@RequestMapping(value = "flighthistory.xls")
	public String flighthistoryExcel(FlightSheetVoyage flightSheetVoyage,Model model)throws BusinessException {
		try {
			List<FlightSheetVoyage> list =flighthistoryService.getList(flightSheetVoyage);
			String wjmc="flighthistory";
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", wjmc, model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
}
