package com.eray.thjw.system.control;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.system.po.ContactPerson;
import com.eray.thjw.system.service.ContactPersonService;

/**
 * @author 
 * @description 联系人维护
 * @develop 
 */
@Controller
@RequestMapping("/sys/contactperson")
public class ContactPersonController extends BaseController {
	@Autowired
	private ContactPersonService contactPersonService;
	
	@Resource(name="contactpersonexcelimporter")
	private BaseExcelImporter<ContactPerson> excelImporter;
	
	@Privilege(code="sys:contactperson:manage")
	@RequestMapping(value = "manage", method = RequestMethod.GET)
	public String main(Model model) throws BusinessException {
	
		return "sys/contactperson/contactperson_main";
	}
	
	/**
	 * 根据条件查询联系人
	 * @param baseMaintenance
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getListPage", method = RequestMethod.POST)
	public Map<String, Object> flightrecordList(@RequestBody ContactPerson contactPerson,
			HttpServletRequest request, Model model) throws Exception {
		try {		
			return contactPersonService.selectPageList(contactPerson);
		} catch (Exception e) {
			throw new BusinessException("查询联系人失败!",e);
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
	@Privilege(code="sys:contactperson:manage:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void save(@RequestBody ContactPerson contactPerson) throws BusinessException{
		try {
			
			contactPersonService.save(contactPerson);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
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
	@Privilege(code="sys:contactperson:manage:02")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public void edit(@RequestBody ContactPerson contactPerson) throws BusinessException{
		try {
			
			contactPersonService.update(contactPerson);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 作废
	 * @param request,id
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="sys:contactperson:manage:03")
	@ResponseBody
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public void cancel(@RequestBody ContactPerson contactPerson) throws BusinessException{
		try {
			contactPersonService.cancel(contactPerson);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * 获取该id对应的联系人信息
	 * @param baseMaintenance
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "editInfo", method = RequestMethod.POST)
	public ContactPerson editInfo(@RequestBody ContactPerson contactPerson,
			HttpServletRequest request, Model model) throws Exception {
		contactPerson=contactPersonService.selectContactPersonById(contactPerson.getId());
		return contactPerson;
	}
	
	/**
	 * @author liub
	 * @description 根据csid获取联系人信息
	 * @param csid
	 * @return List<ContactPerson>
	 */
	@ResponseBody
	@RequestMapping(value = "queryListByCsId",method={RequestMethod.POST,RequestMethod.GET})
	public List<ContactPerson> queryListByCsId(String csid) throws BusinessException {
		getLogger().info("前端参数:csid:{}", new Object[]{csid});
		List<ContactPerson> list = null;
		try {
			list = contactPersonService.queryListByCsId(csid);
		} catch (Exception e) {
			throw new BusinessException("获取联系人信息失败!",e);
		}
		return list;
	}

	/**
	 * 联系人导入
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@Privilege(code="sys:contactperson:manage:04")
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,  
		    HttpServletResponse response) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "联系人导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "联系人导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description 导出联系人
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "Contacts.xls")
	public String export(ContactPerson contactPerson, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			contactPerson.setDprtcode(new String (contactPerson.getDprtcode().getBytes("iso-8859-1"),"utf-8"));
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			contactPerson.setPagination(p);
			Map<String, Object> resultMap = contactPersonService.selectPageList(contactPerson);
			List<ContactPerson> list = (List<ContactPerson>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "lxr", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}

	}
}
