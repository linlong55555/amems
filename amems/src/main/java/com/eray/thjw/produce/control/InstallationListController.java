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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.InstallationList;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.po.InstallationListEffective;
import com.eray.thjw.produce.service.InstallationListEditableService;
import com.eray.thjw.produce.service.InstallationListEffectService;
import com.eray.thjw.produce.service.InstallationListEffectiveService;

/**
 * @Description 装机清单Controller
 * @CreateTime 2017年9月11日 上午11:14:45
 * @CreateBy 韩武
 */
@Controller
@RequestMapping("/aircraftinfo/installationlist")
public class InstallationListController extends BaseController {
	
	@Resource
	private InstallationListEditableService installationListEditableService;
	
	@Resource
	private InstallationListEffectiveService installationListEffectiveService;
	
	@Resource
	private InstallationListEffectService installationListEffectService;
	
	@Resource(name="installationlistexcelimporter")
	private BaseExcelImporter<InstallationList> excelImporter;
	
	/**
	 * @Description 跳转到装机清单主页面
	 * @CreateTime 2017年9月14日 下午4:12:41
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@Privilege(code = "aircraftinfo:installationlist:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("produce/installationlist/installationlist_main", responseParamMap);
	}
	
	/**
	 * @Description 查询装机清单-编辑区的分页数据
	 * @CreateTime 2017年9月23日 上午9:58:11
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/editpagelist", method = RequestMethod.POST)
	public Map<String, Object> queryEditPageableList(@RequestBody InstallationListEditable record) throws BusinessException{
		try {
			return installationListEditableService.queryPageableList(record);
		} catch (Exception e) {
			throw new BusinessException("查询装机清单-编辑区的分页数据失败!",e);
		}
	}
	
	/**
	 * @Description 查询装机清单-生效区的分页数据
	 * @CreateTime 2017年10月11日 下午2:30:55
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/effectpagelist", method = RequestMethod.POST)
	public Map<String, Object> queryEffectPageableList(@RequestBody InstallationListEffective record) throws BusinessException{
		try {
			return installationListEffectiveService.queryPageableList(record);
		} catch (Exception e) {
			throw new BusinessException("查询装机清单-生效区的分页数据失败!",e);
		}
	}
	
	/**
	 * @Description 查询部件集合
	 * @CreateTime 2017年9月25日 下午4:57:14
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public List<InstallationListEditable> queryList(@RequestBody InstallationListEditable record) throws BusinessException{
		try {
			return installationListEditableService.queryList(record);
		} catch (Exception e) {
			throw new BusinessException(" 查询部件集合失败!",e);
		}
	}
	
	/**
	 * @Description 查询装机清单-生效区的数据集合
	 * @CreateTime 2017年10月27日 上午10:45:26
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/effectlist", method = RequestMethod.POST)
	public List<InstallationListEffective> queryEffectlist(@RequestBody InstallationListEffective record) throws BusinessException{
		try {
			return installationListEffectiveService.queryList(record);
		} catch (Exception e) {
			throw new BusinessException("查询装机清单-生效区的数据集合失败!",e);
		}
	}
	
	/**
	 * @Description 跳转到装机清单查看页面
	 * @CreateTime 2017年9月14日 下午4:13:40
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("produce/installationlist/installationlist_view", responseParamMap);
	}
	
	/**
	 * @Description 保存装机清单
	 * @CreateTime 2017年9月28日 下午3:54:55
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "aircraftinfo:installationlist:main:01,aircraftinfo:installationlist:main:02")
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestBody InstallationListEditable record) throws BusinessException{
		try {
			return installationListEditableService.doSave(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存装机清单失败!",e);
		}
	}
	
	/**
	 * @Description 删除装机清单
	 * @CreateTime 2017年10月10日 上午10:03:42
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException
	 */
	@Privilege(code = "aircraftinfo:installationlist:main:03")
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(@RequestBody InstallationListEditable record) throws BusinessException{
		try {
			installationListEditableService.doDelete(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("删除装机清单失败!",e);
		}
	}
	
	/**
	 * @Description 查询装机清单详情（编辑区）
	 * @CreateTime 2017年10月9日 上午11:10:59
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public InstallationListEditable queryDetail(@RequestBody InstallationListEditable record) throws BusinessException{
		try {
			return installationListEditableService.queryDetail(record);
		} catch (Exception e) {
			throw new BusinessException("查询装机清单详情（编辑区）失败!",e);
		}
	}
	
	/**
	 * @Description 查询装机清单详情（生效区）
	 * @CreateTime 2017年11月27日 下午5:07:54
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/effectdetail", method = RequestMethod.POST)
	public InstallationListEffective queryEffectiveDetail(@RequestBody InstallationListEffective record) throws BusinessException{
		try {
			return installationListEffectiveService.queryDetail(record);
		} catch (Exception e) {
			throw new BusinessException("查询装机清单详情（生效区）失败!",e);
		}
	}
	
	/**
	 * @Description 装机清单生效
	 * @CreateTime 2017年10月10日 下午4:13:22
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException
	 */
	@Privilege(code = "aircraftinfo:installationlist:main:04")
	@ResponseBody
	@RequestMapping(value = "/effect", method = RequestMethod.POST)
	public void effect(@RequestBody Aircraftinfo record) throws BusinessException{
		try {
			installationListEffectService.doEffect(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("装机清单生效失败!",e);
		}
	}
	
	/**
	 * 飞机装机清单导入
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
	@Privilege(code = "aircraftinfo:installationlist:main:05")
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,HttpServletResponse response,
			String dprtcode,String fjjx,String fjzch) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.setParam("dprtcode", dprtcode);
			excelImporter.setParam("fjjx", fjjx);
			excelImporter.setParam("fjzch", fjzch);
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "飞机装机清单导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "飞机装机清单导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @Description 装机清单导出
	 * @CreateTime 2017年12月18日 下午1:44:17
	 * @CreateBy 韩武
	 * @param record
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="aircraftinfo:installationlist:main:06")
	@RequestMapping(value = "zjqd.xls")
	public String export(String json, Model model) throws BusinessException {
		try {
			InstallationListEditable record = JSON.parseObject(json, InstallationListEditable.class);
			List<InstallationListEditable> list;
			if("history".equals(record.getParamsMap().get("historyStatus"))){
				list = installationListEffectiveService.getExportList(record);
			}else{
				list = installationListEditableService.getExportList(record);
			}
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "installationlist", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
}
