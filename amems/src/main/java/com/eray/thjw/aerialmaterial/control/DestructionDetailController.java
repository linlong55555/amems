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

import com.eray.thjw.aerialmaterial.po.Destruction;
import com.eray.thjw.aerialmaterial.po.DestructionDetail;
import com.eray.thjw.aerialmaterial.service.DestructionDetailService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.MaterialTypeEnum;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 
 * @author sunji
 * @description 销毁清单控制器
 */
@Controller
@RequestMapping("/aerialmaterial/destructionlist")
public class DestructionDetailController extends BaseController{
	
	@Resource
	private DepartmentService departmentService;
	@Resource
	private DestructionDetailService destructionDetailService;
	/**
	 * 跳转至销毁页面
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="aerialmaterial:destructionlist:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Destruction destruction) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		model.put("dprtcode", GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY));
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("/material/destructionDetail/destructionDetail_main",model);
	}
	

	@ResponseBody
	@RequestMapping(value = "main", method = RequestMethod.POST)
	public Map<String, Object> main(@RequestBody DestructionDetail destructionDetail,HttpServletRequest request,Model model)throws BusinessException{
		return destructionDetailService.queryAll(destructionDetail);
	}
	/**
	 * @author sunji 导出
	 * @description 
	 * @param destructionDetail
	 */
	@Privilege(code="aerialmaterial:destructionlist:main:01")
	@RequestMapping(value = "DestructionList.xls")
	public String export( DestructionDetail destructionDetail, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			Pagination p=new Pagination();
			p.setSort("auto");
			p.setRows(Integer.MAX_VALUE);
			destructionDetail.setPagination(p);
			List <DestructionDetail> list=destructionDetailService.queryAllPageList(destructionDetail);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "DestructionList", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}

	}
}
