package com.eray.thjw.material2.control;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.ToolBorrowRecord;
import com.eray.thjw.aerialmaterial.service.OutFieldStockService;
import com.eray.thjw.aerialmaterial.service.StoreInnerSearchServcie;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.ContractInfo;
import com.eray.thjw.material2.po.StockHistory;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.SupervisoryLevelEnum;

/** 
 * @Description 	场外查询
 * @CreateTime 2018-3-7 下午2:48:00
 * @CreateBy 孙霁
 */
@Controller
@RequestMapping("material/stock/material")
public class OutsideStockController  extends BaseController{

	@Resource
	private DepartmentService departmentService;
	@Resource
	private OutFieldStockService outFieldStockService;
	@Resource
	private StoreInnerSearchServcie storeInnerSearchService;
	
	/**
	 * 
	 * @Description 页面跳转
	 * @CreateTime 2018-3-7 下午2:50:33
	 * @CreateBy 孙霁
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="material:stock:material:outside")
	@RequestMapping(value = "outside", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
		User user = ThreadVarUtil.getUser();
		model.put("supervisoryLevelEnum", SupervisoryLevelEnum.enumToListMap());
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		model.put("isTool", false);
	    return new ModelAndView("/material2/stock/material/outsideStock_main",model);
	}
	
	/**
	 * 
	 * @Description 获取库外列表
	 * @CreateTime 2018-3-12 下午4:34:31
	 * @CreateBy 孙霁
	 * @param outFieldStock
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAll", method = RequestMethod.POST)
	public Map<String, Object> queryAll(@RequestBody OutFieldStock outFieldStock,HttpServletRequest request,Model model)throws BusinessException{
		return outFieldStockService.queryAll(outFieldStock);
	}
	/**
	 * 
	 * @Description 外场部件清单导出
	 * @CreateTime 2018-3-15 上午10:55:51
	 * @CreateBy 孙霁
	 * @param paramjson
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "outfield.xls", method = RequestMethod.GET)
	public String exportExcelOutfield(String paramjson, HttpServletRequest request, Model model) throws BusinessException {
		try {
		    paramjson = new String(paramjson.getBytes("iso-8859-1"),"utf-8");
		    OutFieldStock outFieldStock = JSON.parseObject(paramjson, OutFieldStock.class);
			List<OutFieldStock> list = outFieldStockService.outfieldExportExcel(outFieldStock);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "outfield", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	/**
	 * 
	 * @Description 在途部件清单导出
	 * @CreateTime 2018-3-15 上午10:55:51
	 * @CreateBy 孙霁
	 * @param paramjson
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "transit.xls", method = RequestMethod.GET)
	public String exportExcelTransit(String paramjson, HttpServletRequest request, Model model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"),"utf-8");
			 ContractInfo contractInfo = JSON.parseObject(paramjson, ContractInfo.class);
			List<ContractInfo> list = outFieldStockService.contractInfoExportExcel(contractInfo);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "transit", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	
	/**
	 * 
	 * @Description 跳转到查看界面
	 * @CreateTime 2017-8-15 下午6:43:19
	 * @CreateBy 孙霁
	 * @param id
	 * @return airworthiness_view
	 */
	@RequestMapping(value = "kc_view", method = RequestMethod.GET)
	public ModelAndView kc_view(Model model,@RequestParam String id)throws BusinessException {
		model.addAttribute("id", id);
		return new ModelAndView("/material2/stock/material/outsideStock_view");
	}
	/**
	 * 
	 * @Description 根据库存idh获取借用归还数据
	 * @CreateTime 2018-3-20 上午11:08:42
	 * @CreateBy 孙霁
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllJygh", method = RequestMethod.POST)
	public List<ToolBorrowRecord> queryAllJygh(String id,HttpServletRequest request,Model model)throws BusinessException{
		return storeInnerSearchService.getListBykcid(id);
	}
	/**
	 * 
	 * @Description 根据库存idh获取借用归还数据
	 * @CreateTime 2018-3-20 上午11:08:42
	 * @CreateBy 孙霁
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllKcll", method = RequestMethod.POST)
	public List<StockHistory> queryAllKcll(String id,HttpServletRequest request,Model model)throws BusinessException{
		return storeInnerSearchService.queryStoreHistoryByKcid(id);
	}
	
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2018-3-21 上午11:26:31
	 * @CreateBy 孙霁
	 * @param stock
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "updateByWc", method = RequestMethod.POST)
	public String updateByWc(@RequestBody OutFieldStock outFieldStock) throws BusinessException  {
			try {
				outFieldStockService.updateByWc(outFieldStock);
				return null;
			} catch (BusinessException e) {
				throw new BusinessException("库存保存失败!");
			}
	
	}
	
	
}
