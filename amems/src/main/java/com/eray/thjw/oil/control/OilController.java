package com.eray.thjw.oil.control;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.oil.service.OilService;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.Oil;
import com.eray.thjw.po.Oilprice;
import com.eray.thjw.po.Routinginspectionrecord;
import com.eray.thjw.po.TechnicalFile;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author sunji
 * @description  Oil
 */
@Controller 
@RequestMapping("/basedata/oil")
public class OilController {
	@Autowired
	private OilService oilService;
	@Autowired
	private DepartmentService departmentService;
	
	
	
	@Privilege(code = "basedata:oil:main")
	@RequestMapping("main")
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
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
		return new ModelAndView("sys/oil/oil_main", model);
	}
	
	/**
	 *油品规格列表
	 * @param role
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "queryOilList", method = RequestMethod.POST)
	public Map<String, Object> queryOilList(@RequestBody Oil oil,HttpServletRequest request,Model model) throws BusinessException {
		String id=oil.getId();
		oil.setId("");
		try {
			PageHelper.startPage(oil.getPagination());
			List<Oil> list = this.oilService.queryAll(oil);
			if(((Page)list).getTotal() > 0){
				
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Oil newRecord = new Oil();
						newRecord.setId(id);
						List<Oil> newRecordList =this.oilService.queryAll(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}
					}
				}
				return PageUtil.pack4PageHelper(list, oil.getPagination());
				
			}else{
				List<Oil> newRecordList = new ArrayList<Oil>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					Oil newRecord = new Oil();
					newRecord.setId(id);
					newRecordList = this.oilService.queryAll(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, oil.getPagination());
					}
				}
				return PageUtil.pack(0, newRecordList, oil.getPagination());
			}
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
	@Privilege(code="basedata:oil:add:01")
	@ResponseBody
	@RequestMapping(value = "saveOil", method = RequestMethod.POST)
	public Map<String, Object> saveOilprice(@RequestBody Oil oil) throws BusinessException{
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			oilService.save(oil);
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
	@Privilege(code="basedata:oil:edit:02")
	@ResponseBody
	@RequestMapping(value = "updateOil", method = RequestMethod.POST)
	public void updateOil(@RequestBody Oil oil) throws BusinessException{
		try {
			oilService.update(oil);
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
	@Privilege(code="basedata:oil:invalid:03")
	@ResponseBody
	@RequestMapping(value = "invalidOil", method = RequestMethod.POST)
	public void invalidOil(@RequestBody Oil oil) throws BusinessException{
		try {
			oilService.invalid(oil);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 验证唯一
	 * @param reserve
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "validationYpgg", method = RequestMethod.POST)
	public Object validationYpgg(@RequestParam String ypgg, @RequestParam String oldypgg, @RequestParam String dprtcode,
			HttpServletRequest request) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Oil oil = new Oil();
			oil.setYpgg(ypgg);
			oil.setOldypgg(oldypgg);
			// 获取当前登入人对象
			oil.setDprtcode(dprtcode);
			int i = oilService.validationYpgg(oil);
			resultMap.put("total", i);
		} catch (Exception e) {
			throw new BusinessException("查询失败");
		}
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 根据组织机构获取当前的所有油品规格
	 * @param reserve
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryByDprtcode", method = RequestMethod.POST)
	public Object queryByDprtcode(@RequestParam String dprtcode,HttpServletRequest request) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Oil> oilList = oilService.queryByDprtcode(dprtcode);
			resultMap.put("oilList", oilList);
		} catch (Exception e) {
			throw new BusinessException("查询失败");
		}
		return resultMap;
	}
	
	
}
