package com.eray.thjw.aerialmaterial.control;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eray.thjw.aerialmaterial.po.Firm;
import com.eray.thjw.aerialmaterial.service.FirmService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.FirmTypeEnum;
import enu.ThresholdEnum;

/**
 * @author liub
 * @description 航材厂商控制层
 */
@Controller
@RequestMapping("/aerialmaterial/aerialmaterialfirm")
public class AerialmaterialFirmController extends BaseController {
	
	/**
	 * @author liub
	 * @description 供应商service
	 */
	@Autowired
	private FirmService firmService;
	
	/**
	 * @author liub
	 * @description 系统阀值设置 Service
	 */
	@Resource
	private MonitorsettingsService monitorsettingsService;
	
	@Resource(name="firmexcelimporter")
	private BaseExcelImporter<BaseEntity> excelImporter;
	
	/**
	 * @author liub
	 * @description 跳转至航材供应商界面
	 * @param
	 * @return 页面视图
	 *
	 */
	@Privilege(code="aerialmaterial:aerialmaterialfirm:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model) throws BusinessException {
		model.addAttribute("gyslb", FirmTypeEnum.MATERIAL_FIRM.getId());
		return "material/firm/aerialmaterial_firm_main";
	}
	
	/**
	 * @author liub
	 * @description 增加航材厂商
	 * @param firm
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:aerialmaterialfirm:main:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@RequestBody Firm firm) throws BusinessException{
		getLogger().info("增加供应商  前端参数:firm:{}", new Object[]{firm});
		try {
			return firmService.add(firm);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存航材厂商失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 修改供应商
	 * @param firm
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:aerialmaterialfirm:main:02")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(@RequestBody Firm firm) throws BusinessException{
		getLogger().info("修改供应商  前端参数:firm:{}", new Object[]{firm});
		try {
			firmService.edit(firm);
			return firm.getId();
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改航材厂商失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 作废
	 * @param request,id
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="aerialmaterial:aerialmaterialfirm:main:03")
	@ResponseBody
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request,String id) throws BusinessException{
		getLogger().info("作废操作  前端参数:id{}", new Object[]{id});
		try {
			firmService.cancel(id);
		} catch (Exception e) {
			 throw new BusinessException("作废供应商失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询厂商信息
	 * @param firm
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody Firm firm)throws BusinessException {
		String id = firm.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		firm.setId(null);
		try {
			PageHelper.startPage(firm.getPagination());
			List<Firm> list = firmService.queryAllPageList(firm);
			if(((Page)list).getTotal() > 0){
				//分页查询
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Firm newRecord = new Firm();
						newRecord.setId(id);
						List<Firm> newRecordList = firmService.queryAllPageList(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}
					}
				}
				return PageUtil.pack4PageHelper(list, firm.getPagination());
			}else{
				List<Firm> newRecordList = new ArrayList<Firm>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					Firm newRecord = new Firm();
					newRecord.setId(id);
					newRecordList = firmService.queryAllPageList(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, firm.getPagination());
					}
				}
				return PageUtil.pack(0, newRecordList, firm.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!",e);
		}
	}

	/**
	 * @author liub
	 * @description 根据供应商id查询供应商信息
	 * @param id
	 * @return Firm
	 */
	@ResponseBody
	@RequestMapping(value = "selectByPrimaryKey",method={RequestMethod.POST,RequestMethod.GET})
	public Firm selectByPrimaryKey(String id) throws BusinessException {
		try {
			return firmService.selectByPrimaryKey(id);
		} catch (Exception e) {
			throw new BusinessException("查询航材供应商失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询供应商信息(弹窗)
	 * @param firm
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "queryWinAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryWinAllPageList(@RequestBody Firm firm)throws BusinessException {
		getLogger().debug("查询供应商列表(弹窗)  前端参数:firm{}", new Object[]{firm});	
		try {
			PageHelper.startPage(firm.getPagination());
			List<Map<String, Object>> list = firmService.queryWinAllPageList(firm);
			return PageUtil.pack4PageHelper(list, firm.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据dprtcode查询系统阀值
	 * @param dprtcode
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "getByKeyDprtcode", method = RequestMethod.POST)
	public Map<String, Object> getByKeyDprtcode(String dprtcode)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("monitorsettings", monitorsettingsService.getByKeyDprtcode(ThresholdEnum.GYSYXQ.getName(),dprtcode));
		} catch (Exception e) {
			throw new BusinessException("查询系统阀值失败!",e);
		}
		return model;
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件查询供应商信息
	 * @param firm
	 * @return List<Firm>
	 */
	@ResponseBody
	@RequestMapping(value = "queryFirmList", method = RequestMethod.POST)
	public List<Firm> queryFirmList(@RequestBody Firm firm)throws BusinessException {
		getLogger().info("查询供应商列表  前端参数:firm{}", new Object[]{firm});	
		try {
			return firmService.queryFirmList(firm);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!",e);
		}
	}
	
	/**
	 * 供应商导入
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:aerialmaterialfirm:main:04")
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,  
		    HttpServletResponse response) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "航材供应商导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
			super.getLogger().error(e.getMessage(),e);
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "航材供应商导入失败！");
			super.getLogger().error("航材供应商导入失败！",e);
		}
		return result;
	}
	/**
	 * 
	 * @Description 获取外委供应商集合
	 * @CreateTime 2017年9月27日 下午2:27:07
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getFirmList", method = RequestMethod.POST)
	public List<Firm> getBmList(String dprtcode) throws BusinessException {
		try {
			return firmService.getwwFirmList(dprtcode);
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	
	/**
	 * 
	 * @Description 导出航材供应商
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "AirmaterialSupplier.xls")
	public String export(Firm firm,String sqksrqBegin, String sqksrqEnd,String sqjsrqBegin,String whrqBegin,String sqjsrqEnd,String whrqEnd,HttpServletRequest request,
			Model model) throws BusinessException {
		try {  
			firm.setDprtcode(new String (firm.getDprtcode().getBytes("iso-8859-1"),"utf-8"));
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			firm.setPagination(p);
			firm.getParamsMap().put("sqksrqBegin", sqksrqBegin);
			firm.getParamsMap().put("sqksrqEnd", sqksrqEnd);
			firm.getParamsMap().put("sqjsrqBegin", sqjsrqBegin);
			firm.getParamsMap().put("whrqBegin", whrqBegin);
			firm.getParamsMap().put("sqjsrqEnd", sqjsrqEnd);
			firm.getParamsMap().put("whrqEnd", whrqEnd);
			List<Firm> list =  firmService.queryAllPageList(firm);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "hcgys", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
}
