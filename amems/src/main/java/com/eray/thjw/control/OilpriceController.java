package com.eray.thjw.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Oilprice;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.service.OilpriceService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/airportensure/oilprice")
public class OilpriceController extends BaseController{

	
	@Autowired
	private OilpriceService oilpriceService;
	@Autowired
	private DepartmentService departmentService;
	/**
	 * 跳转至油品价格
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="airportensure:oilprice:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(Oilprice oilprice) {
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
		return new ModelAndView("/airportensure/oilprice/oilprice_main", model);
	}
	/**
	 * 根据组织机构查询油品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectByOil", method = RequestMethod.POST)
	public  Map<String, Object> selectByOil(@RequestParam String dprtcode,HttpServletRequest request,HttpServletResponse response)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<Oilprice> OilList=oilpriceService.selectByOil(dprtcode);
			model.put("OilList", OilList);
		} catch (Exception e) {
			throw new BusinessException("查询失败",e);
		}
		return model;
	}
	/**
	 * 根据油品规格查询查询油品价
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectByYpgg", method = RequestMethod.POST)
	public  Map<String, Object> selectByYpgg(@RequestBody Oilprice oilprice,HttpServletRequest request,HttpServletResponse response)throws BusinessException {
		try {
			PageHelper.startPage(oilprice.getPagination());
			List<Oilprice> list = this.oilpriceService.selectByYpgg(oilprice);
			return PageUtil.pack4PageHelper(list, oilprice.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 新增
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="airportensure:oilprice:main:01")
	@ResponseBody
	@RequestMapping(value = "saveOilprice", method = RequestMethod.POST)
	public Map<String, Object> saveOilprice(@RequestBody Oilprice oilprice) throws BusinessException{
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			oilpriceService.save(oilprice);
			return resultMap;
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	/**
	 * @author sunji
	 * @description 修改
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="airportensure:oilprice:main:02")
	@ResponseBody
	@RequestMapping(value = "updateOilprice", method = RequestMethod.POST)
	public void updateOilprice(@RequestBody Oilprice oilprice) throws BusinessException{
		try {
			oilpriceService.update(oilprice);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 作废
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="airportensure:oilprice:main:03")
	@ResponseBody
	@RequestMapping(value = "invalidOilprice", method = RequestMethod.POST)
	public void invalidOilprice(@RequestBody Oilprice oilprice) throws BusinessException{
		try {
			oilpriceService.invalid(oilprice);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
	}
	
}
