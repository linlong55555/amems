package com.eray.thjw.aerialmaterial.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.service.HCMainDataService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Department;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 
 * 库存预警控制器
 * @author xu.yong
 *
 */
@Controller
@RequestMapping("/aerialmaterial/stockwarning")
public class StockwarningController  extends BaseController{
	
	@Resource
	private HCMainDataService hCMainDataService;
	
	/**
	 * 跳转至库存预警页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:stockwarning:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("/material/stockwarning/stockwarning_main", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 库存预警列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "stockwarningList", method = RequestMethod.POST)
	public Map<String, Object> stockwarningList(@RequestBody HCMainData hCMainData,HttpServletRequest request,Model model) throws BusinessException{
		try {
			PageHelper.startPage(hCMainData.getPagination());
			List<HCMainData> list = this.hCMainDataService.queryAllPageLists(hCMainData);
			return PageUtil.pack4PageHelper(list, hCMainData.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询失败",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "stockWaringOut.xls")
	public String export( HCMainData hCMainData, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			List<Department> dlist=(List<Department>) SessionUtil.getFromSession(request, "accessDepartment");
			for (Department department : dlist) {
				if(department.getId().equals(hCMainData.getDprtcode())){
					model.addAttribute("departname", department.getDprtname());
				}
			}
			List<HCMainData> list = this.hCMainDataService.queryAllPageLists(hCMainData);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "stockwarning", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}

	}
	
}
