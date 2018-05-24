package com.eray.thjw.material2.control;


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

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.DemandSafeguardDetail;
import com.eray.thjw.material2.service.DemandApplyService;
import com.eray.thjw.material2.service.DemandSafeguardDetailService;
import com.eray.thjw.material2.service.DemandSafeguardService;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.service.ProcessRecordService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;


/**
 * @author 裴秀
 * @description 	航材需求清单
 */
@Controller
@RequestMapping("/material/demand")
public class DemandMaterialListController extends BaseController {
	
	@Resource
	private DepartmentService departmentService;
	@Resource
	private DemandSafeguardDetailService demandSafeguardDetailService;
	@Resource
	private DemandSafeguardService demandSafeguardService;
	@Resource
	private ProcessRecordService processRecordService;
	@Resource
	private DemandApplyService demandApplyService;
	/**
	 * @Description 	航材需求清单
     * @CreateTime 2018年01月31日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:demand:materiallist")
	@RequestMapping(value = "materiallist", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
	    return new ModelAndView("/material2/demand/materiallist/materiallist_main",model);
	
	}
	/**
	 * @Description 工具需求清单
     * @CreateTime 2018年01月31日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:demand:toollist")
	@RequestMapping(value = "toollist", method = RequestMethod.GET)
	public ModelAndView toollist(Map<String, Object> model)throws BusinessException {
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		return new ModelAndView("/material2/demand/toollist/toollist_main",model);
	
	}
	/**
	 * 
	 * @Description 查询航材需求清单
	 * @CreateTime 2018-2-27 下午3:18:28
	 * @CreateBy 孙霁
	 * @param demandSafeguardDetail
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAll", method = RequestMethod.POST)
	public Map<String, Object> queryAll(@RequestBody DemandSafeguardDetail demandSafeguardDetail,HttpServletRequest request,Model model)throws BusinessException{
		return demandSafeguardDetailService.queryAll(demandSafeguardDetail);
}
	/**
	 * @Description 查询需求统计分析
	 * @CreateTime 2018-4-3 下午5:09:08
	 * @CreateBy 刘兵
	 * @param demandSafeguardDetail
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAnalysisList", method = RequestMethod.POST)
	public List<DemandSafeguardDetail> queryAnalysisList(@RequestBody DemandSafeguardDetail demandSafeguardDetail,HttpServletRequest request,Model model)throws BusinessException{
		return demandSafeguardDetailService.queryAnalysisList(demandSafeguardDetail);
}
	
	/**
	 * 
	 * @Description 查询需求详情和流程信息
	 * @CreateTime 2018-2-28 上午11:19:06
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("selectDetailAndProcess")
	public Map<String, Object> invalid(HttpServletRequest request,String id,String mainid) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//获取需求详情
		resultMap.put("demandSafeguard", demandSafeguardService.selectDetail(mainid));
		resultMap.put("processRecordList", processRecordService.selectByMainid(id));
		return resultMap;
	}
	
	
	
	@RequestMapping(value = "materiallist/view", method = RequestMethod.GET)
	public ModelAndView view(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/demand/materiallist/analysis_view",model);
	
	}
	/**
	 * 
	 * @Description 批量操作
	 * @CreateTime 2018-3-1 上午10:36:23
	 * @CreateBy 孙霁
	 * @param demandSafeguardDetail
	 * @throws BusinessException
	 */
	/*@Privilege(code="project2:workcard:main:03")*/
	@ResponseBody
	@RequestMapping(value = "updateBatch", method = RequestMethod.POST)
	public void doAudit(@RequestBody DemandSafeguardDetail demandSafeguardDetail) throws BusinessException{
		try {
			demandSafeguardDetailService.updateBatch(demandSafeguardDetail);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("操作失败!",e);
		}
	}
	/**
	 * 
	 * @Description 航材导出
	 * @CreateTime 2018-3-1 上午10:37:11
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:airworthiness:main:04")
	@RequestMapping(value = "materiallist.xls")
	public String materiallistExcel(DemandSafeguardDetail demandSafeguardDetail,Model model)throws BusinessException {
		try {
			List<DemandSafeguardDetail> list =demandSafeguardDetailService.getDemandSafeguardDetailList(demandSafeguardDetail);
			String wjmc="materiallist";
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", wjmc, model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	/**
	 * 
	 * @Description 工具导出
	 * @CreateTime 2018-3-1 上午10:37:11
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:airworthiness:main:04")
	@RequestMapping(value = "toollist.xls")
	public String toollistExcel(DemandSafeguardDetail demandSafeguardDetail,Model model)throws BusinessException {
		try {
			List<DemandSafeguardDetail> list =demandSafeguardDetailService.getDemandSafeguardDetailList(demandSafeguardDetail);
			String wjmc="toollist";
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", wjmc, model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
}
