package com.eray.thjw.productionplan.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.service.PlaneBaseService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author hanwu
 * @description 飞机基础数据控制器
 * @develop date 2016年8月31日
 */
@Controller
@RequestMapping("/productionplan/planeData")
public class PlaneDataController extends BaseController{
	
	@Resource
	private PlaneDataService planeDataService;
	
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	@Resource
	private PlaneBaseService planeBaseService;
	
	@Resource(name="planedatalistexcelimporter")
	private BaseExcelImporter<PlaneData> planeDataListExcelImporter;
	
	/**
	 * 跳转至飞机基本信息主页面
	 * @return 
	 */
	@Privilege(code="planeData:planeRegister:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main() throws BusinessException{
		return "productionplan/planeData/planeData_main";
	}
	
	/**
	 * 飞机基本信息分页查询
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value="/page",method={RequestMethod.POST})
	public Map<String, Object> queryByPage(HttpServletRequest request, @RequestBody PlaneData pd) throws BusinessException{
		try {
			//没有选择机构，可查当前用户授权的所有机构
			if (null == pd.getDprtcode() || StringUtils.isEmpty(pd.getDprtcode())) {
				List<String> dprtcodes = SessionUtil.getDprtcodeList(request);
				pd.setDprtcodes(dprtcodes);
			}
			return planeDataService.queryByPage(pd);
		} catch (Exception e) {
			 throw new BusinessException("飞机分页查询失败!", e);
		}
		
	}
	
	/**
	 * 跳转至新增飞机基本信息页面
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("baseList", planeBaseService.findBaseByDprtcode(ThreadVarUtil.getUser().getJgdm()));
		} catch (Exception e) {
			 throw new BusinessException("跳转页面失败!", e);
		}
		return new ModelAndView("productionplan/planeData/planeData_add", model);
	}
	
	/**
	 * 新增飞机基本信息
	 * @param pd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add",method={RequestMethod.POST})
	public String add(@RequestBody PlaneData pd) throws BusinessException{
		try {
			planeDataService.add(pd);
			return pd.getFjzch();
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("新增飞机失败！", e);
		}
	}
	
	/**
	 * 跳转至新增修改基本信息页面
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("fjzch") String fjzch,@RequestParam("dprtcode") String dprtcode) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("baseList", planeBaseService.findBaseByDprtcode(dprtcode));
			model.put("pd", planeDataService.queryByFjzch(new PlaneData(fjzch, dprtcode)));
			model.put("type", "edit");
		} catch (Exception e) {
			 throw new BusinessException("跳转页面失败!", e);
		}
		return new ModelAndView("productionplan/planeData/planeData_edit", model);
	}
	
	
	/**
	 * 修改飞机基本信息
	 * @param pd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/edit",method={RequestMethod.POST})
	public String edit(@RequestBody PlaneData pd) throws BusinessException{
		try {
			planeDataService.edit(pd);
			return pd.getFjzch();
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("修改飞机失败", e);
		}
	}
	
	/**
	 * 跳转至新增查看基本信息页面
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam("fjzch") String fjzch,@RequestParam("dprtcode") String dprtcode) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("baseList", planeBaseService.findBaseByDprtcode(dprtcode));
			model.put("pd", planeDataService.queryByFjzch((new PlaneData(fjzch, dprtcode))));
			model.put("type", "view");
		} catch (Exception e) {
			 throw new BusinessException("跳转页面失败!", e);
		}
		return new ModelAndView("productionplan/planeData/planeData_edit", model);
	}
	
	/**
	 * 查询飞机
	 * @param pd
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value="/queryList",method={RequestMethod.POST})
	public List<PlaneData> queryList(@RequestBody PlaneData pd) throws BusinessException{
		try {
			return planeDataService.queryList(pd);
		} catch (Exception e) {
			throw new BusinessException("查询飞机失败", e);
		}
	}
	
	/**
	 * 查询所有飞机数据并关联机型
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value="/findAllWithFjjx",method={RequestMethod.POST})
	public List<Map<String, Object>> findAllWithFjjx() throws BusinessException{
		try {
			return planeDataService.findAllWithFjjxInJson(new PlaneData());
		} catch (Exception e) {
			throw new BusinessException("查询飞机数据失败", e);
		}
	}
	
	/**
	 * 获取飞机以及初始化信息
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value="/queryPlaneData",method={RequestMethod.POST})
	public Map<String, Object> queryPlaneData(@RequestBody PlaneData pd) throws BusinessException{
		try {
			// 查询飞机初始化数据
			return planeDataService.queryByFjzch(pd);
		} catch (Exception e) {
			throw new BusinessException("查询飞机及初始化数据失败", e);
		}
	}
	/**
	 * 查询机型
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value="findJx",method={RequestMethod.POST})
	public PlaneData findJx(@RequestParam("fjzch") String fjzch,@RequestParam("dprtcode") String dprtcode) throws BusinessException{
		try {
			return planeDataService.selectByPrimaryKey((new PlaneData(fjzch, dprtcode)));
		} catch (Exception e) {
			throw new BusinessException("查询机型失败", e);
		}
	}	
	
	
	/**
	 * @author liub
	 * @description 根据条件分页查询飞机注册号列表
	 * @param request,model,planeData
	 * @return Map<String, Object>
	 * @throws Exception 
	 * @develop date 2017.01.05
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageWinList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody PlaneData planeData,HttpServletRequest request,Model model) throws BusinessException{
		try {
			PageHelper.startPage(planeData.getPagination());
			List<Map<String, Object>> list = this.planeDataService.queryAllPageWinList(planeData);
			return PageUtil.pack4PageHelper(list, planeData.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * 飞机信息导入
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@Privilege(code="planeData:planeRegister:main:03")
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,  
		    HttpServletResponse response) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			planeDataListExcelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "飞机信息导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "飞机信息导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除飞机基本信息
	 * @param pd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method={RequestMethod.POST})
	public void delete(@RequestBody PlaneData pd) throws BusinessException{
		try {
			planeDataService.delete(pd);
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage(), e);
		} catch (Exception e) {
			throw new BusinessException("删除飞机基本信息失败", e);
		}
	}
	/**
	 * 
	 * @Description EO适用性获取飞机数据列表
	 * @CreateTime 2017年8月24日 下午4:41:58
	 * @CreateBy 岳彬彬
	 * @param planeData
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getFj", method = RequestMethod.POST)
	public Map<String, Object> getBj(@RequestBody PlaneData planeData,HttpServletRequest request) throws BusinessException {
		try {
			return planeDataService.getplaneList(planeData);
		} catch (Exception e) {
			throw new BusinessException("获取飞机数据失败", e);
		}
	}
}
