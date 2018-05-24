package com.eray.thjw.material2.control;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.BackRegister;
import com.eray.thjw.material2.po.RejectedMaterial;
import com.eray.thjw.material2.service.BackRegisterService;
import com.eray.thjw.material2.service.RejectedMaterialService;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

/**
 * @author 裴秀
 * @description 退料申请
 */
@Controller
@RequestMapping("material/return")
public class ReturnApplyController extends BaseController {
	

	@Resource
	private BackRegisterService backRegisterService;
	
	
	@Resource
	private DepartmentService departmentService;
	
	@Resource
	private RejectedMaterialService rejectedMaterialService;
	
	/**
	 * @Description 退料申请
     * @CreateTime 2018年02月26日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:return:apply")
	@RequestMapping(value = "apply", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
	    return new ModelAndView("/material2/return/apply/apply_main",model);
	
	}
	
	/**
	 * @Description 退料申请列表
     * @CreateTime 2018年01月31日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAll", method = RequestMethod.POST)
	public Map<String, Object> queryAll(@RequestBody RejectedMaterial rejectedMaterial,HttpServletRequest request,Model model)throws BusinessException{
		return rejectedMaterialService.queryAll(rejectedMaterial);
	}
	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2018-3-6 上午11:30:59
	 * @CreateBy 孙霁
	 * @param rejectedMaterial
	 * @param request
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@RequestBody RejectedMaterial rejectedMaterial,HttpServletRequest request) throws BusinessException{
		try {
			return rejectedMaterialService.insert(rejectedMaterial);
		} catch (BusinessException e) {
			throw new BusinessException("数据添加失败",e);
		}
	}
	/**
	 * 
	 * @Description 修改（修订）
	 * @CreateTime 2018-3-6 上午11:30:59
	 * @CreateBy 孙霁
	 * @param rejectedMaterial
	 * @param request
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(@RequestBody RejectedMaterial rejectedMaterial,HttpServletRequest request) throws BusinessException{
		try {
			return rejectedMaterialService.update(rejectedMaterial);
		} catch (BusinessException e) {
			throw new BusinessException("数据修改失败",e);
		}
	}
	/**
	 * 
	 * @Description 关闭
	 * @CreateTime 2018-3-6 下午2:21:06
	 * @CreateBy 孙霁
	 * @param rejectedMaterial
	 * @param request
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "close", method = RequestMethod.POST)
	public void close(@RequestBody RejectedMaterial rejectedMaterial,HttpServletRequest request) throws BusinessException{
		try {
			rejectedMaterialService.update(rejectedMaterial);
		} catch (BusinessException e) {
			throw new BusinessException("数据关闭失败",e);
		}
	}
	
	@ResponseBody
	@RequestMapping("selectById")
	public RejectedMaterial selectById(HttpServletRequest request,String id) throws Exception {
		return rejectedMaterialService.selectById(id);
	}
	/**
	 * @Description 航材退料清单
     * @CreateTime 2018年02月26日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:return:materiallist")
	@RequestMapping(value = "materiallist", method = RequestMethod.GET)
	public ModelAndView materialist(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/return/materialList/materialList_main",model);
	
	}
	
	/**
	 * @Description 工具退料清单
     * @CreateTime 2018年02月26日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:return:toollist")
	@RequestMapping(value = "toollist", method = RequestMethod.GET)
	public ModelAndView toollist(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/return/toolList/toolList_main",model);
	
	}
	
	/**
	 * 
	 * @Description 航材退料清单分页
	 * @CreateTime 2018年3月5日 上午9:48:50
	 * @CreateBy 林龙
	 * @param backRegister
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody BackRegister backRegister,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			return backRegisterService.queryAllPageList(backRegister);
		} catch (Exception e) {
			throw new BusinessException("航材退料清单分页列表加载失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description  航材退料清单查看页面跳转
	 * @CreateTime 2017年9月29日 下午4:17:56
	 * @CreateBy 林龙
	 * @param id
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("find/{id}")
	public ModelAndView find(@PathVariable("id") String id,Model model)throws BusinessException {
		try {
			 model.addAttribute("viewid", id);
				return new ModelAndView("/material2/return/materialList/materialList_find");
		} catch (RuntimeException e) {
			throw new BusinessException("航材退料清单查看页面跳转失败!",e);
		}
	}
	
	/**
	 * @Description 根据id查询航材退料清单信息
	 * @CreateTime 2017年8月15日 下午8:15:37
	 * @CreateBy 林龙
	 * @param defectKeep 计量工具
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public BackRegister getById(@RequestBody BackRegister backRegister)throws BusinessException {
		try {
			return backRegisterService.getInfoById(backRegister);
		} catch (Exception e) {
			throw new BusinessException("查询航材退料清单失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 航材退料清单导出
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "backRegister.xls")
	public String export(String paramjson,HttpServletRequest request,Model model) throws BusinessException {
		try {
			paramjson=new String (paramjson.getBytes("iso-8859-1"),"utf-8");
			BackRegister backRegister = Utils.Json.toObject(paramjson, BackRegister.class);
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			backRegister.setPagination(p);
			Map<String, Object> resultMap = backRegisterService.queryAllPageList(backRegister);
			List<BackRegister> list = (List<BackRegister>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "backRegister", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}

	}
	
	/**
	 * 
	 * @Description 工具退料清单导出
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "backTools.xls")
	public String exportbackTools(String paramjson,HttpServletRequest request,Model model) throws BusinessException {
		try {
			paramjson=new String (paramjson.getBytes("iso-8859-1"),"utf-8");
			BackRegister backRegister = Utils.Json.toObject(paramjson, BackRegister.class);
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			backRegister.setPagination(p);
			Map<String, Object> resultMap = backRegisterService.queryAllPageList(backRegister);
			List<BackRegister> list = (List<BackRegister>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "backTools", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}
		
	}
}
