package com.eray.thjw.control;



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

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.service.FixedCheckWorkContentService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

/**
 * @author liub
 * @description 定检工作内容控制层
 * @develop date 2016.08.25
 */
@Controller
@RequestMapping("/project/fixedcheckworkcontent")
public class FixedCheckWorkContentController extends BaseController {
	
	/**
	 * @author liub
	 * @description service
	 * @develop date 2016.08.25
	 */
	@Autowired
	private FixedCheckWorkContentService fixedCheckWorkContentService;
	/**
	 * @author liub
	 * @description 
	 * @param djxmid,model
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.29
	 */
	@ResponseBody
	@RequestMapping(value = "queryListByDjxmbhid",method={RequestMethod.POST,RequestMethod.GET})
	public List<Map<String, Object>> queryListByDjxmid(Model model,String djxmid) throws BusinessException {
		getLogger().info("  前端参数:djxmid:{}", new Object[]{djxmid});
		List<Map<String, Object>> list = null;
		try {
			list = fixedCheckWorkContentService.queryListByDjxmid(djxmid);
		} catch (Exception e) {
			throw new BusinessException("获取定检工作内容失败!",e);
		}
		return list;
	}
	
	/**
	 * @author liub
	 * @description 根据条件分页查询差异列表
	 * @param request,model,baseEntity
	 * @return Map<String, Object>
	 * @throws Exception 
	 * @develop date 2017.02.09
	 */
	@ResponseBody
	@RequestMapping(value = "queryDiffPageList", method = RequestMethod.POST)
	public Map<String, Object> queryDiffPageList(@RequestBody BaseEntity baseEntity)throws BusinessException {
		try {
			PageHelper.startPage(baseEntity.getPagination());
			List<Map<String, Object>> list = fixedCheckWorkContentService.queryDiffPageList(baseEntity);
			return PageUtil.pack4PageHelper(list, baseEntity.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	/*
	 * 导出excel
	 */
	@RequestMapping(value="maintenanceDerictory")
	public String ypoutExcel(String wxfabh,String wxfazwms,String bb,String wxfabb,String dprtcode,HttpServletRequest request, RedirectAttributesModelMap model) throws BusinessException {
		try {
			wxfazwms = new String(wxfazwms.getBytes("iso-8859-1"), "utf-8");
			model.addFlashAttribute("wxfabh", wxfabh);
			model.addFlashAttribute("wxfazwms", wxfazwms);
			model.addFlashAttribute("bb", bb);
			model.addFlashAttribute("dprtcode", dprtcode);
			model.addFlashAttribute("wxfabb", wxfabb);
			return "redirect:/report/".concat("xls").concat("/").concat(dprtcode).concat("/maintenanceDerictory.xls");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("报表预览或导出失败");
		}
		
	}
}
