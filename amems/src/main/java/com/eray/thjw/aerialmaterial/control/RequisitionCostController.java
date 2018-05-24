package com.eray.thjw.aerialmaterial.control;



import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.eray.thjw.aerialmaterial.service.RequisitionCostService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

import enu.MaterialSecondTypeEnum;

/**
 * @author liub
 * @description 单击领用成本控制层
 * @develop date 2016.12.06
 */
@Controller
@RequestMapping("/aerialmaterial/requisitioncost")
public class RequisitionCostController extends BaseController {
	
	/**
	 * @author liub
	 * @description 单击领用成本service
	 * @develop date 2016.12.06
	 */
	@Autowired
	private RequisitionCostService requisitionCostService ;
	
	/**
	 * @author liub
	 * @description 单击领用成本管理界面
	 * @param
	 * @return 页面视图
	 * @develop date 2016.12.06
	 *
	 */
	@Privilege(code="aerialmaterial:requisitioncost:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model,HttpServletRequest request) throws BusinessException {
		try {
			model.addAttribute("materialSecondTypeEnum", MaterialSecondTypeEnum.enumToListMap()) ;
			return "material/statistical/requisitioncost_main";
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据条件分页查询单击领用成本列表
	 * @param request,model,baseEntity
	 * @return Map<String, Object>
	 * @throws Exception 
	 * @develop date 2016.12.06
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody BaseEntity baseEntity,HttpServletRequest request,Model model) throws BusinessException{
		try {
			PageHelper.startPage(baseEntity.getPagination());
			List<Map<String, Object>> list = this.requisitionCostService.queryAllPageList(baseEntity);
			return PageUtil.pack4PageHelper(list, baseEntity.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	/**
	 * 单机领用成本导出
	 * @param fjzch
	 * @param dprtcode
	 * @param hclx
	 * @param hclx_ej
	 * @param lyrq
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "outRequisitioncostExcel")
	public String export(String fjzch, String dprtcode, String hclx, String hclx_ej,String lyrq, HttpServletRequest request, RedirectAttributesModelMap model) throws BusinessException {
		try {			
			String param="";
			StringBuffer sbf = new StringBuffer("");
			if (!fjzch.equals("")) {
				if(fjzch.contains("'")){
					fjzch=fjzch.replace("'", "'|| chr(39) ||'");
				}
				param=" and (b1.fjzch ='"+fjzch+ "'  or b1.fjzch = '00000')";
			}
			if (hclx.equals("")) {
				sbf.append(" and b4.hclx in (1, 4, 5) ");
			}
			if (!hclx.equals("")) {
				sbf.append(" and b4.hclx in (2, 3) ");
			}
			if(!hclx_ej.equals("")){
				sbf.append(" and b4.hclx_ej='" + hclx_ej + "'");
			}
			String first=lyrq.substring(0, 10).concat(" 00:00:00");
			String end=lyrq.substring(13,23).concat(" 23:59:59");
			model.addFlashAttribute("sql", sbf.toString());
			model.addFlashAttribute("param", param);
			model.addFlashAttribute("star",  " and b1.cksj >=to_Date('"+first+"','YYYY-MM-DD HH24:MI:SS') ");
			model.addFlashAttribute("last", " and b1.cksj <=to_Date('"+end+"','YYYY-MM-DD HH24:MI:SS') ");
			return "redirect:/report/".concat("xls").concat("/").concat(dprtcode).concat("/Requisitioncost.xls");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("报表预览或导出失败");
		}

	}
}
