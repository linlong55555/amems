package com.eray.thjw.aerialmaterial.control;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.aerialmaterial.service.StoreService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.User;
import com.eray.thjw.service.PlaneBaseService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.Store2TypeEnum;
import enu.StoreTypeEnum;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author liub
 * @description 仓库控制层
 * @develop date 2016.09.12
 */
@Controller
@RequestMapping("/material/store")
public class StoreController extends BaseController {
	
	/**
	 * @author liub
	 * @description 仓库service
	 * @develop date 2016.09.12
	 */
	@Autowired
	private StoreService storeService;
	
	/**
	 * @author liub
	 * @description 用户service
	 * @develop date 2016.09.13
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * @author liub
	 * @description 基地service
	 * @develop date 2016.12.27
	 */
	@Resource
	private PlaneBaseService planeBaseService;
	
	@Resource(name="storageexcelimporter")
	private BaseExcelImporter<Storage> excelImporter;
		
	/**
	 * @author liub
	 * @description 跳转至仓库主界面
	 * @param
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code="material:store:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model) {
		model.addAttribute("storeTypeEnum", StoreTypeEnum.enumToListMap());
		model.addAttribute("isStoreData", false);
		return "material/config/store_main";
	}
	
	/**
	 * @author liub
	 * @description 查看仓库详情
	 * @param request,response
	 * @return 页面视图
	 * @develop date 2016.09.14
	 *
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,String id,String dprtcode,String isStoreData) throws BusinessException{
		try {
			model.addAttribute("store", storeService.selectJoinUserById(id));
			model.addAttribute("isStoreData", isStoreData);
			if("true".equals(isStoreData)){//仓库数据管理
				model.addAttribute("storeType2Enum", Store2TypeEnum.enumToListMap());
			}else if("false".equals(isStoreData)){//仓库主数据
				model.addAttribute("storeTypeEnum", StoreTypeEnum.enumToListMap());
			}
		} catch (Exception e) {
			throw new BusinessException("查看仓库详情失败!",e);
		}
		return new ModelAndView("material/config/store_view");
	}
	
	/**
	 * @author liub
	 * @description 初始化增加仓库
	 * @param request,response
	 * @return 页面视图
	 * @develop date 2016.09.13
	 *
	 */
	@Privilege(code="material:store:main:01,material:store2:main:01")
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("userList", userService.queryUserAll(new User()));
			model.put("storeTypeEnum", StoreTypeEnum.enumToListMap());
			model.put("baseList", planeBaseService.findBaseByDprtcode(ThreadVarUtil.getUser().getJgdm()));
		} catch (Exception e) {
			throw new BusinessException("初始化增加仓库失败!",e);
		}
		return new ModelAndView("material/config/store_add", model);
	}
	
	/**
	 * @author liub
	 * @description 增加仓库
	 * @param store
	 * @develop date 2016.09.14
	 * @throws BusinessException 
	 */
	@Privilege(code="material:store:main:01,material:store2:main:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@RequestBody Store store) throws BusinessException{
		try {
			return storeService.add(store);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存仓库失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 初始化修改仓库
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.23
	 *
	 */
	@ResponseBody
	@Privilege(code="material:store:main:02,material:store2:main:02")
	@RequestMapping("selectById")
	public Map<String, Object> selectById(@RequestParam String id) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("row",  storeService.selectJoinUserById(id));
			model.put("rows",storeService.queryStorageListByStoreId(id));
		} catch (Exception e) {
			throw new BusinessException("初始化修改仓库失败!",e);
		}
		return model;
	}
	
	/**
	 * @author liub
	 * @description 修改仓库
	 * @param store
	 * @develop date 2016.09.18
	 * @throws BusinessException 
	 */
	@Privilege(code="material:store:main:02,material:store2:main:02")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public void edit(@RequestBody Store store) throws BusinessException{
		try {
			storeService.edit(store);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改仓库失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 作废
	 * @param request,response,ids
	 * @return
	 * @develop date 2016.09.18
	 * @throws @throws Exception 
	 */
	@Privilege(code="material:store:main:03,material:store2:main:03")
	@ResponseBody
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request,String ids) throws BusinessException{
		try {
			storeService.cancel(ids);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("作废仓库失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询仓库信息
	 * @param store
	 * @return Map<String, Object>
	 * @develop date 2016.09.12
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody Store store)throws BusinessException {
		String id = store.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		store.setId(null);
		try {
			//查询总记录数
			PageHelper.startPage(store.getPagination());
			List<Store> list = storeService.queryAllPageList(store);
			if(((Page)list).getTotal() > 0){
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Store newStore = new Store();
						newStore.setId(id);
						List<Store> newRecordList = storeService.queryAllPageList(newStore);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}
					}
				}
				return PageUtil.pack4PageHelper(list, store.getPagination());
			}else{
				List<Store> newRecordList = new ArrayList<Store>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					Store newStore = new Store();
					newStore.setId(id);
					newRecordList = storeService.queryAllPageList(newStore);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, store.getPagination());
					}
				}
				return PageUtil.pack(0, newRecordList, store.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据机构代码查询仓库
	 * @param dprtcode,model
	 * @return List<Store>
	 * @develop date 2017.03.23
	 */
	@ResponseBody
	@RequestMapping(value = "selectByDprtcode",method={RequestMethod.POST,RequestMethod.GET})
	public List<Store> selectByDprtcode(Model model,String dprtcode) throws BusinessException {
		List<Store> list = null;
		try {
			list = storeService.selectByDprtcode(dprtcode);
		} catch (Exception e) {
			throw new BusinessException("查询仓库失败!",e);
		}
		return list;
	}
	
	/**
	 * @author liub
	 * @description 根据仓库id查询仓库库位
	 * @param id,model
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.29
	 */
	@ResponseBody
	@RequestMapping(value = "queryStorageListByStoreId",method={RequestMethod.POST,RequestMethod.GET})
	public List<Storage> queryStorageListByStoreId(Model model,String storeId) throws BusinessException {
		List<Storage> list = null;
		try {
			list = storeService.queryStorageListByStoreId(storeId);
		} catch (Exception e) {
			throw new BusinessException("查询仓库库位失败!",e);
		}
		return list;
	}
	
	/**
	 * @author liub
	 * @description 根据盘点id查询仓库库位
	 * @param pdid,model
	 * @return List<Storage>
	 * @develop date 2016.11.22
	 */
	@ResponseBody
	@RequestMapping(value = "queryStorageListByPdid",method={RequestMethod.POST,RequestMethod.GET})
	public List<Storage> queryStorageListByPdid(Model model,String pdid) throws BusinessException {
		List<Storage> list = null;
		try {
			list = storeService.queryStorageListByPdid(pdid);
		} catch (Exception e) {
			throw new BusinessException("查询仓库库位失败!",e);
		}
		return list;
	}

	/**
	 * @author liub
	 * @description 根据盘点id查询仓库库位下拉框(新增盘点差异)
	 * @param pdid,model
	 * @return List<Storage>
	 * @develop date 2016.11.24
	 */
	@ResponseBody
	@RequestMapping(value = "queryStorageSelectByPdid",method={RequestMethod.POST,RequestMethod.GET})
	public List<Storage> queryStorageSelectByPdid(Model model,String pdid) throws BusinessException {
		List<Storage> list = null;
		try {
			list = storeService.queryStorageSelectByPdid(pdid);
		} catch (Exception e) {
			throw new BusinessException("查询仓库库位失败!",e);
		}
		return list;
	}
	/**
	 * 导入
	 * @param multipartRequest
	 * @param id
	 * @param response
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="material:store:main:04,material:store2:main:04")
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest, String id, HttpServletResponse response) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.setParam("id", id);
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "仓库库位导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "仓库库位导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2017年12月6日 上午11:05:47
	 * @CreateBy 岳彬彬
	 * @param store
	 * @param whrq
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="material:store:main:05,material:store2:main:05")
	@RequestMapping(value = "WarehouseMainData.xls")
	public String export(Store store, String whrq,String isStoreData,HttpServletRequest request,Model model) throws BusinessException {
		try {
			store.getParamsMap().put("isStoreData", "true".equals(isStoreData)?true:false);
			List<Store> list = storeService.doExport(store,whrq);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "store", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
}
