package com.eray.thjw.aerialmaterial.control;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.eray.thjw.aerialmaterial.po.Enquiry;
import com.eray.thjw.aerialmaterial.po.ProcurementCatalog;
import com.eray.thjw.aerialmaterial.service.EnquiryService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.EnquiryStatusEnum;
import enu.FormTypeEnum;

/**
 * @author liub
 * @description 询价管理控制层
 * @develop date 2016.10.17
 */
@Controller
@RequestMapping("/aerialmaterial/enquiry")
public class EnquiryController extends BaseController {
	
	/**
	 * @author liub
	 * @description 询价管理service
	 * @develop date 2016.10.18
	 */
	@Autowired
	private EnquiryService enquiryService ;
	
	/**
	 * @author liub
	 * @description 跳转至询价管理界面
	 * @param
	 * @return 页面视图
	 * @develop date 2016.10.17
	 *
	 */
	@Privilege(code="aerialmaterial:enquiry:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String manage(Model model,HttpServletRequest request){
		model.addAttribute("formTypeEnum", FormTypeEnum.enumToListMap());
		model.addAttribute("enquiryStatusEnum", EnquiryStatusEnum.enumToListMap());
		return "material/enquiry/enquiry_main";
	}
	
	/**
	 * @author liub
	 * @description 保存询价
	 * @param enquiry
	 * @develop date 2016.10.20
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:enquiry:main:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void save(@RequestBody Enquiry enquiry) throws BusinessException{
		try {
			enquiryService.save(enquiry);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询询价列表信息
	 * @param enquiry
	 * @return Map<String, Object>
	 * @develop date 2016.10.18
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody Enquiry enquiry)throws BusinessException {
		String id = enquiry.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		enquiry.setId(null);
		try {
			PageHelper.startPage(enquiry.getPagination());
			List<Map<String, Object>> list = enquiryService.queryAllPageList(enquiry);
			if(((Page)list).getTotal() > 0){
				//分页查询
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecordMap(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Enquiry newRecord = new Enquiry();
						newRecord.setId(id);
						List<Map<String, Object>> newRecordList = enquiryService.queryAllPageList(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}
					}
				}
				return PageUtil.pack4PageHelper(list, enquiry.getPagination());
			}else{
				List<Map<String, Object>> newRecordList = new ArrayList<Map<String, Object>>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					Enquiry newRecord = new Enquiry();
					newRecord.setId(id);
					newRecordList = enquiryService.queryAllPageList(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, enquiry.getPagination());
					}
				}
				return PageUtil.pack(0, newRecordList, enquiry.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据提订详情id查询询价信息
	 * @param request,mainid
	 * @return List<Enquiry>
	 * @develop date 2016.10.20
	 */
	@ResponseBody
	@RequestMapping(value = "queryEnquiryListByMainId", method = RequestMethod.POST)
	public List<Enquiry> queryEnquiryListByMainId(String mainid,HttpServletRequest request)throws BusinessException {
		List<Enquiry> list = new ArrayList<Enquiry>();
		try {
			list = enquiryService.queryEnquiryListByMainId(mainid);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!",e);
		}
		return list;
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件采购目录信息
	 * @param enquiry
	 * @return List<ProcurementCatalog>
	 * @develop date 2016.11.07
	 */
	@ResponseBody
	@RequestMapping(value = "queryProcurementCatalogList", method = RequestMethod.POST)
	public List<ProcurementCatalog> queryProcurementCatalogList(@RequestBody ProcurementCatalog procurementCatalog)throws BusinessException {
		List<ProcurementCatalog> list = null;
		try {
			list = enquiryService.queryProcurementCatalogList(procurementCatalog);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!",e);
		}
		return list;
	}
	@RequestMapping(value = "enquiryOutExcel")
	public String export(String djlx,  String id,String dprtcode,Integer sl, HttpServletRequest request, RedirectAttributesModelMap model) throws BusinessException {
		try {
			if(!djlx.equals("")){
				model.addFlashAttribute("djlx", Integer.valueOf(djlx));
			}
			model.addFlashAttribute("id", id);
			model.addFlashAttribute("sl", sl);
			return "redirect:/report/".concat("xls").concat("/").concat(dprtcode).concat("/enquiry.xls");
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败!",e);
		}
	}
}
