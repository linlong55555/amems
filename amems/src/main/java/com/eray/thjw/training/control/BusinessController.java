package com.eray.thjw.training.control;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.training.po.Business;
import com.eray.thjw.training.po.BusinessToMaintenancePersonnel;
import com.eray.thjw.training.service.BusinessService;
import com.eray.thjw.training.service.BusinessToMaintenancePersonnelService;

/**
 * 
 * 岗位控制器
 * @author ll
 *
 */
@Controller
@RequestMapping("/training/business")
public class BusinessController  extends BaseController{
	
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessToMaintenancePersonnelService businessToMaintenancePersonnelService;
	@Resource(name="businessExcelImporter")
	private BaseExcelImporter<LoadingList> excelImporter;
	
	/**
	 * 跳转至岗位界面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="training:business:manage")
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("/training/business/business_main", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 岗位列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "businessList", method = RequestMethod.POST)
	public Map<String, Object> businessList(@RequestBody Business business,HttpServletRequest request,Model model) throws BusinessException{
		try {		
			return businessService.queryAllPageList(business);
		} catch (Exception e) {
			throw new BusinessException("查询岗位失败!",e);
		}
		
	}
	
	/**
	 * @Description 查询课程岗位关系
	 * @CreateTime 2018-2-1 上午10:33:20
	 * @CreateBy 刘兵
	 * @param business 岗位
	 * @return List<Business>
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryByKc", method = RequestMethod.POST)
	public List<Business> queryByKc(@RequestBody Business business) throws BusinessException{
		try {		
			return businessService.queryByKc(business);
		} catch (Exception e) {
			throw new BusinessException("查询岗位失败!",e);
		}
		
	}
	
	/**
	 * @author ll
	 * @description 新增
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="training:business:manage:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void save(@RequestBody Business business) throws BusinessException{
		try {
			
			businessService.insertSelective(business);
		}catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			 throw new BusinessException();
		}
	}
	
	/**
	 * @author ll
	 * @description 修改
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="training:business:manage:02")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public void edit(@RequestBody Business business) throws BusinessException{
		try {
			
			businessService.updateByPrimaryKeySelective(business);
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
		}catch (Exception e) {
			throw new BusinessException();
		}
	}
	/**
	 * @author sunji
	 * @description 作废
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="training:business:manage:03")
	@ResponseBody
	@RequestMapping(value = "invalid", method = RequestMethod.POST)
	public void invalid(@RequestBody Business business) throws BusinessException{
		try {
			businessService.invalid(business);
		} catch (BusinessException e) {
			 throw new BusinessException("操作失败",e);
		}
	}
	
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 岗位人员列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "getInfoList", method = RequestMethod.POST)
	public Map<String, Object> getInfoList(@RequestBody BusinessToMaintenancePersonnel businessPer,HttpServletRequest request,Model model) throws BusinessException{
	
		try {		
			return businessToMaintenancePersonnelService.queryAllPageList(businessPer);
		} catch (Exception e) {
			throw new BusinessException("查询岗位人员失败!",e);
		}
		
		
	}
	/**
	 * 保存提交技术文件
	 * 
	 * @return 
	 * @throws BusinessException 
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	@Privilege(code="training:business:manage:04")
	public void saveUser(@RequestBody BusinessToMaintenancePersonnel businessToMaintenancePersonnel,HttpServletRequest request) throws BusinessException{
		try {
				businessToMaintenancePersonnelService.batchInsert(businessToMaintenancePersonnel);
		} catch (BusinessException e) {
			throw new BusinessException("数据添加失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 作废
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 *
	 */
	@ResponseBody
	@RequestMapping("deleteInfo")
	@Privilege(code="training:business:manage:05")
	public void deleteInfo(HttpServletRequest request,String id) throws BusinessException {
		businessToMaintenancePersonnelService.deleteInfo(id);
	}
	/**
	 * 飞机装机清单导入
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@Privilege(code="training:business:manage:06")
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,  
		    HttpServletResponse response
		    ) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "岗位管理导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "岗位管理导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description 岗位导出
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "Post.xls")
	public String export(Business business, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			business.setPagination(p);
			Map<String, Object> resultMap = businessService.queryAllPageList(business);
			List<Business> list = (List<Business>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "gw", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
	
}
