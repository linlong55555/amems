package com.eray.thjw.aerialmaterial.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.po.MaterialToStore;
import com.eray.thjw.aerialmaterial.service.HCMainDataService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.BinaryEnum;
import enu.MaterialPriceEnum;
import enu.MaterialSecondTypeEnum;
import enu.MaterialTypeEnum;
import enu.SupervisoryLevelEnum;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author liub
 * @description 航材控制层
 * @develop date 2016.09.10
 */
@Controller
@RequestMapping("/material/material")
public class MaterialController extends BaseController {
	
	/**
	 * @author liub
	 * @description 航材service
	 * @develop date 2016.09.12
	 */
	@Autowired
	private HCMainDataService hcMainDataService;
	
	@Resource(name="materialexcelimporter")
	private BaseExcelImporter<HCMainData> excelImporter;
	
	private static final Logger log = LoggerFactory.getLogger(MaterialController.class);
	
	/**
	 * @author liub
	 * @description 跳转至航材界面
	 * @param
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code="material:material:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(HttpServletRequest request,Model model) {
		model.addAttribute("supervisoryLevelEnum", SupervisoryLevelEnum.enumToListMap());
		model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		model.addAttribute("agingControlEnum", BinaryEnum.enumToListMap());
		model.addAttribute("materialPriceEnum", MaterialPriceEnum.enumToListMap());
		model.addAttribute("supervisoryLevelEnum", SupervisoryLevelEnum.enumToListMap());
		model.addAttribute("materialSecondTypeEnum", MaterialSecondTypeEnum.enumToListMap()) ;
		return "material/config/material_main";
	}
	
	/**
	 * @author liub
	 * @description 增加航材
	 * @param hcMainData
	 * @develop date 2016.09.18
	 * @throws BusinessException 
	 */
	@Privilege(code="material:material:main:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@RequestBody HCMainData hcMainData) throws BusinessException{
		try {
			return hcMainDataService.add(hcMainData);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存航材失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 修改航材
	 * @param hcMainData
	 * @develop date 2016.09.19
	 * @throws BusinessException 
	 */
	@Privilege(code="material:material:main:02,material:material:main:05")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(@RequestBody HCMainData hcMainData) throws BusinessException{
		try {
			hcMainDataService.edit(hcMainData);
			return hcMainData.getId();
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改航材失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 作废
	 * @param request,ids
	 * @return
	 * @develop date 2016.09.19
	 * @throws BusinessException
	 */
	@Privilege(code="material:material:main:03")
	@ResponseBody
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request,String id) throws BusinessException{
		try {
			hcMainDataService.cancel(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("作废失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询航材信息
	 * @param material
	 * @return Map<String, Object>
	 * @develop date 2016.07.25
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody HCMainData hcMainData)throws BusinessException {
		String id = hcMainData.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		hcMainData.setId(null);
		PageHelper.startPage(hcMainData.getPagination());
		List<HCMainData> list = hcMainDataService.queryAllPageList(hcMainData);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					HCMainData newRecord = new HCMainData();
					newRecord.setId(id);
					List<HCMainData> newRecordList = hcMainDataService.queryAllPageList(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, hcMainData.getPagination());
		}else{
			List<HCMainData> newRecordList = new ArrayList<HCMainData>();
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				HCMainData newRecord = new HCMainData();
				newRecord.setId(id);
				newRecordList = hcMainDataService.queryAllPageList(newRecord);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, hcMainData.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, hcMainData.getPagination());
		}
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询航材信息(弹窗需要的数据)
	 * @param material
	 * @return Map<String, Object>
	 * @develop date 2016.10.11
	 */
	@ResponseBody
	@RequestMapping(value = "queryWinAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryWinAllPageList(@RequestBody HCMainData hcMainData)throws BusinessException {
		log.debug("查询航材列表  前端参数:hcMainData{}", new Object[]{hcMainData});	
		
		PageHelper.startPage(hcMainData.getPagination());
		List<HCMainData> list = hcMainDataService.queryWinAllPageList(hcMainData);
		return PageUtil.pack4PageHelper(list, hcMainData.getPagination());
		
	}
	
	/**
	 * @author liub
	 * @description 检查航材类型是否可以修改(如果存在则不能修改)
	 * @param id
	 * @return List<HCMainData>
	 * @develop date 2016.11.24
	 */
	@ResponseBody
	@RequestMapping(value = "checkUptById",method={RequestMethod.POST,RequestMethod.GET})
	public HCMainData checkUptById(String id) throws BusinessException {
		try {
			return hcMainDataService.checkUptById(id);
		} catch (Exception e) {
			throw new BusinessException("查询航材失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description  根据航材id查询航材仓库关系
	 * @param mainId
	 * @return MaterialToStore
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "getMaterialStoreByMainId", method = RequestMethod.POST)
	public MaterialToStore getMaterialStoreByMainId(HttpServletRequest request,String mainId) throws  Exception {
		log.info("查询航材仓库关系  前端参数:mainId{}", new Object[]{mainId});	
		MaterialToStore materialToStore = null;
		try {
			materialToStore = hcMainDataService.getMaterialStoreByMainId(mainId);
		} catch (Exception e) {
			throw new BusinessException("查询航材仓库关系失败!",e);
		}
		return materialToStore;
	}
	
	/**
	 * @author liub
	 * @description 根据盘点id、关键字查询航材信息
	 * @param pdid,keyword,model
	 * @return List<HCMainData>
	 * @develop date 2016.11.24
	 */
	@ResponseBody
	@RequestMapping(value = "queryMaterialListByPdid",method={RequestMethod.POST,RequestMethod.GET})
	public List<HCMainData> queryMaterialListByPdid(Model model,String pdid,String keyword) throws BusinessException {
		List<HCMainData> list = null;
		try {
			list = hcMainDataService.queryMaterialListByPdid(pdid,keyword);
		} catch (Exception e) {
			throw new BusinessException("查询航材失败!",e);
		}
		return list;
	}
	
	/**
	 * @author liub
	 * @description 根据航材id查询航材及对应的机型、仓库信息
	 * @param id
	 * @return HCMainData
	 * @develop date 2016.12.14
	 */
	@ResponseBody
	@RequestMapping(value = "selectById",method={RequestMethod.POST,RequestMethod.GET})
	public HCMainData selectById(Model model,String id) throws BusinessException {
		HCMainData list = null;
		try {
			list = hcMainDataService.selectById(id);
		} catch (Exception e) {
			throw new BusinessException("查询航材失败!",e);
		}
		return list;
	}
	
	/**
	 * 导入
	 * @param multipartRequest
	 * @param response
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="material:material:main:04")
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,String dprtcode,
		    HttpServletResponse response) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.setParam("dprtcode", dprtcode);
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "部件主数据导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "部件主数据导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据件号和组织机构查询
	 * @param bjh
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectByBjhAndDprt",method={RequestMethod.POST,RequestMethod.GET})
	public HCMainData selectByBjhAndDprt(@RequestBody HCMainData data) throws BusinessException {
		try {
			return hcMainDataService.selectByBjhAndDprt(data);
		} catch (Exception e) {
			throw new BusinessException("根据件号和组织机构查询航材主数据失败!", e);
		}
	} 
	
	/**
	 * @author liubing
	 * @description  根据条件查询航材信息(弹窗)含库存数量
	 * @param hcmaindata
	 * @return Map<String, Object>
	 * @develop date 2017.08.09
	 */
	@ResponseBody
	@RequestMapping(value = "selectWinList", method = RequestMethod.POST)
	public Map<String, Object> selectWinList(@RequestBody HCMainData hcMainData) throws BusinessException {
	    try{
			PageHelper.startPage(hcMainData.getPagination());
			List<HCMainData> list = hcMainDataService.selectWinList(hcMainData);
			return PageUtil.pack4PageHelper(list, hcMainData.getPagination());
		}catch (RuntimeException e) {
			throw new BusinessException("航材信息加载失败",e);
		}
	}
	/**
	 * 
	 * @Description 获取部件
	 * @CreateTime 2017年8月24日 上午10:46:28
	 * @CreateBy 岳彬彬
	 * @param hcMainData
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectPartList", method = RequestMethod.POST)
	public Map<String, Object> selectPartList(@RequestBody HCMainData hcMainData) throws BusinessException {
	    try{
			PageHelper.startPage(hcMainData.getPagination());
			List<HCMainData> list = hcMainDataService.selectPartList(hcMainData);
			return PageUtil.pack4PageHelper(list, hcMainData.getPagination());
		}catch (RuntimeException e) {
			throw new BusinessException("航材信息加载失败",e);
		}
	}
	
	/**
	 * @Description 根据组织机构获取部件前十条数据
	 * @CreateTime 2017年8月31日 下午1:46:36
	 * @CreateBy 韩武
	 * @param hcMainData
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "limitTen", method = RequestMethod.POST)
	public List<HCMainData> limitTen(@RequestBody HCMainData hcMainData) throws BusinessException {
	    try{
			return hcMainDataService.queryLimitTen(hcMainData);
		}catch (RuntimeException e) {
			throw new BusinessException("根据组织机构获取部件前十条数据失败",e);
		}
	}
	/**
	 * 
	 * @Description 根据bjh 和 dprtcode 查询数据
	 * @CreateTime 2017-9-27 上午11:38:42
	 * @CreateBy 孙霁
	 * @param hcmaindata
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectByBjhAndDprcode", method = RequestMethod.POST)
	public  Map<String, Object> selectByBjhAndDprcode(@RequestBody HCMainData hcMainData) throws BusinessException {
	    try{
	    	Map<String, Object> result = new HashMap<String, Object>();
	    	result.put("hCMainData", hcMainDataService.selectByBjhAndDprcode(hcMainData));
	    	return result;
		}catch (RuntimeException e) {
			throw new BusinessException("航材信息查询失败",e);
		}
	}
	
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,@RequestParam String id){
			model.addAttribute("id", id);
			model.addAttribute("supervisoryLevelEnum", SupervisoryLevelEnum.enumToListMap());
			model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
			model.addAttribute("agingControlEnum", BinaryEnum.enumToListMap());
			model.addAttribute("materialPriceEnum", MaterialPriceEnum.enumToListMap());
			model.addAttribute("supervisoryLevelEnum", SupervisoryLevelEnum.enumToListMap());
			model.addAttribute("materialSecondTypeEnum", MaterialSecondTypeEnum.enumToListMap()) ;
			return new ModelAndView("material/config/material_view");
	}
	
	@RequestMapping(value = "viewByBjhAnddprtcode", method = RequestMethod.GET)
	public ModelAndView viewByBjhAnddprtcode(Model model,@RequestParam String bjh,@RequestParam String dprtcode)throws BusinessException {
			HCMainData hcMainData = new HCMainData();
			hcMainData.setBjh(bjh);
			hcMainData.setDprtcode(dprtcode);
			HCMainData hd = hcMainDataService.selectByBjhAndDprcode(hcMainData);
			if(hd == null){
				throw new BusinessException("查询失败");
			}
			model.addAttribute("id", hd.getId());
			model.addAttribute("supervisoryLevelEnum", SupervisoryLevelEnum.enumToListMap());
			model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
			model.addAttribute("agingControlEnum", BinaryEnum.enumToListMap());
			model.addAttribute("materialPriceEnum", MaterialPriceEnum.enumToListMap());
			model.addAttribute("supervisoryLevelEnum", SupervisoryLevelEnum.enumToListMap());
			model.addAttribute("materialSecondTypeEnum", MaterialSecondTypeEnum.enumToListMap()) ;
			return new ModelAndView("material/config/material_view");
	}
	/**
	 * 
	 * @Description 部件主数据导出
	 * @CreateTime 2017年12月5日 上午11:36:02
	 * @CreateBy 岳彬彬
	 * @param hcMainData
	 * @param whrq
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="material:material:main:06")
	@RequestMapping(value = "partMainData.xls")
	public String export(HCMainData hcMainData, String whrq, HttpServletRequest request,Model model) throws BusinessException {
		try {
			List<HCMainData> list = hcMainDataService.doHcExportList(hcMainData,whrq);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "partMainData", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}

	}
}
