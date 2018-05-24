package com.eray.thjw.aerialmaterial.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.ContractDetail;
import com.eray.thjw.aerialmaterial.po.InstockDetailStock;
import com.eray.thjw.aerialmaterial.po.OutstockDetail;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.aerialmaterial.service.ContractDetailService;
import com.eray.thjw.aerialmaterial.service.InstockService;
import com.eray.thjw.aerialmaterial.service.OtherStockSerivce;
import com.eray.thjw.aerialmaterial.service.OutstockService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.aerialmaterial.service.StoreService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;
import enu.ThresholdEnum;

/**
 * 
 * @author 林龙
 * @description 库存控制器
 */
@Controller
@RequestMapping("/aerialmaterial/stock")
public class StockController extends BaseController {
	@Resource
	private StockSerivce stockSerivce;
	
	@Resource
	private StoreService storeService;
	
	@Resource
	private ContractDetailService contractDetailService;
	
	@Resource
	private OtherStockSerivce otherStockSerivce;
	
	@Resource
	private InstockService instockService;
	
	@Resource
	private OutstockService outstockService;
	
	@Resource(name="stockexcelimporter")
	private BaseExcelImporter<Stock> stockExcelImporter;
	
	@Resource
	private MonitorsettingsService monitorsettingsService;
	/**
	 * 跳转至库存管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:stock:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request,String bjh,String dprtcode) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		//获取登入user
		User user=ThreadVarUtil.getUser();
		Store store=new Store();
		store.setZt(1);
		store.setDprtcode(user.getJgdm());
		
		model.put("stockList", storeService.findAlltives(store));
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		model.put("bjh", bjh);
		model.put("dprtcode", dprtcode);
		return new ModelAndView("/material/stock/stock_main", model);
	}
	
	
	/**
	 * @author ll
	 * @description 根据dprtcode查询系统阀值
	 * @param dprtcode
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "getByKeyDprtcode", method = RequestMethod.POST)
	public Map<String, Object> getByKeyDprtcode(String dprtcode)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("monitorsettings", monitorsettingsService.getByKeyDprtcode(ThresholdEnum.HCSM.getName(),dprtcode));
		} catch (Exception e) {
			throw new BusinessException("查询系统阀值失败!",e);
		}
		return model;
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 在库航材列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "stockList", method = RequestMethod.POST)
	public Map<String, Object> stockList(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		paramsMap.put("userId", user.getId());
		stock.setParamsMap(paramsMap);
		
		PageHelper.startPage(stock.getPagination());
		List<Stock> list = this.stockSerivce.queryAllPageList(stock);
		return PageUtil.pack4PageHelper(list, stock.getPagination());
	
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "load", method = RequestMethod.POST)
	public Stock load(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		paramsMap.put("userId", user.getId());
		stock.setParamsMap(paramsMap);
		Stock result = this.stockSerivce.load(stock);
		return result;
	
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 外场航材列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "fieldmaterialstockList", method = RequestMethod.POST)
	public Map<String, Object> fieldmaterialstockList(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		
		PageHelper.startPage(stock.getPagination());
		List<Stock> list = this.stockSerivce.queryAllPageList3(stock);
		return PageUtil.pack4PageHelper(list, stock.getPagination());
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 在途航材列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "contractdetaillist", method = RequestMethod.POST)
	public Map<String, Object> contractdetaillist(@RequestBody ContractDetail contractDetail,HttpServletRequest request,Model model) throws BusinessException{
		PageHelper.startPage(contractDetail.getPagination());
		List<ContractDetail> list = this.contractDetailService.queryAllPageList(contractDetail);
		return PageUtil.pack4PageHelper(list, contractDetail.getPagination());
	}
	
	/**
	 * @author liub
	 * @description 根据条件分页查询送修航材列表
	 * @param request,model
	 * @return Map<String, Object>
	 * @throws Exception 
	 * @develop date 2016.10.27
	 */
	@ResponseBody
	@RequestMapping(value = "queryRepairStockList", method = RequestMethod.POST)
	public Map<String, Object> queryRepairStockList(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		PageHelper.startPage(stock.getPagination());
		List<Stock> list = this.stockSerivce.queryRepairStockList(stock);
		return PageUtil.pack4PageHelper(list, stock.getPagination());
	}
	
	/**
	 * @author liub
	 * @description 根据盘点id、关键字查询库存信息
	 * @param pdid,keyword,model
	 * @return List<Stock>
	 * @develop date 2016.11.22
	 */
	@ResponseBody
	@RequestMapping(value = "queryStockListByPdid",method={RequestMethod.POST,RequestMethod.GET})
	public List<Stock> queryStockListByPdid(Model model,String pdid,String keyword) throws BusinessException {

		List<Stock> list = null;
		try {
			list = stockSerivce.queryStockListByPdid(pdid,keyword);
		} catch (Exception e) {
			throw new BusinessException("查询库存失败!");
		}finally{}
		return list;
	}
	
	/**
	 * @author liub
	 * @description 根据id查询库存信息
	 * @param id,model
	 * @return Stock
	 * @develop date 2016.11.25
	 */
	@ResponseBody
	@RequestMapping(value = "queryById",method={RequestMethod.POST,RequestMethod.GET})
	public Stock queryById(Model model,String id) throws BusinessException {
		Stock stock = null;
		try {
			stock = stockSerivce.queryById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("查询库存失败!");
		}finally{}
		return stock;
	}
	 
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 其它飞行队库存列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "otherstockList", method = RequestMethod.POST)
	public Map<String, Object> otherstockList(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		PageHelper.startPage(stock.getPagination());
		List<Stock> list = this.otherStockSerivce.queryAllPageList(stock);
		return PageUtil.pack4PageHelper(list, stock.getPagination());
	}
	
	
	/**
	 * @return 可领用航材列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "requisitionStockList", method = RequestMethod.POST)
	public Map<String, Object> requisitionStockList(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		
		User user = ThreadVarUtil.getUser();
		
		stock.getParamsMap().put("userId", user.getId());
		
		PageHelper.startPage(stock.getPagination());
		List<Stock> list = this.stockSerivce.query4RequisitionPage(stock);
		return PageUtil.pack4PageHelper(list, stock.getPagination());
	}
	/**
	 * @return 可移库航材列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "transferStockList", method = RequestMethod.POST)
	public Map<String, Object> transferStockList(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		
		User user = ThreadVarUtil.getUser();
		stock.getParamsMap().put("userId", user.getId());
		
		PageHelper.startPage(stock.getPagination());
		List<Stock> list = this.stockSerivce.queryTransferPage(stock);
		return PageUtil.pack4PageHelper(list, stock.getPagination());

	}
	
	/**
	 * 跳转至工具借用管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:stock:iolist")
	@RequestMapping(value = "/iolist", method = RequestMethod.GET)
	public ModelAndView iolistMain(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("startDate",DateUtil.getFirst4Month());
		model.put("endDate",DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE));
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("/material/stock/stock_iolist", model);
	}
	
	/**
	 * 查询入库明细
	 * @param instockDetail
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="aerialmaterial:stock:iolist")
	@ResponseBody
	@RequestMapping(value = "/querylist4In", method = {RequestMethod.GET,RequestMethod.POST})
	public Map<String, Object> query4Ilist(@RequestBody InstockDetailStock record) throws BusinessException {
		Map<String,Object> result = this.instockService.queryDetailPage(record);
		return result;
	}
	
	/**
	 * 查询出库明细
	 * @param outstockDetail
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="aerialmaterial:stock:iolist")
	@ResponseBody
	@RequestMapping(value = "/querylist4Out", method = {RequestMethod.GET,RequestMethod.POST})
	public Map<String, Object> query4Olist(@RequestBody OutstockDetail record ) throws BusinessException {
		Map<String,Object> result = this.outstockService.queryDetailPage(record);
		return result;
	}
	/**
	 * 预览导出PDF
	 * @param id
	 * @param dprtcode
	 * @param keyword
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "stockOutPDF")
	public String export(String[] id,String dprtcode, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			StringBuffer sbf=new StringBuffer("");
			sbf.append("where t1.id in (");
			for(int i=0;i<id.length;i++){
				if(i==id.length-1){
					sbf.append("'"+id[i]+"'");
				}else{
				sbf.append("'"+id[i]+"',");
			}
			}
			sbf.append(" )");
			model.addAttribute("id", sbf.toString());
//			return "redirect:/report/".concat("pdf").concat("/").concat(dprtcode).concat("/StockAvailableCard");
			return outReport("pdf", dprtcode, "StockAvailableCard", model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("预览或导出失败");
		}

	}
	
	/**
	 * 库存档案导入
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,  
		    HttpServletResponse response) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			stockExcelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "库存数据导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "库存数据导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 跳转至库存信息修改列表页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:stock:editmain")
	@RequestMapping(value = "/editmain", method = RequestMethod.GET)
	public ModelAndView editmain(HttpServletRequest request) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("/material/stock/stock_editmain", model);
	}
	
	/**
	 * @description 
	 * @param request,model
	 * @return 在库和外场航材列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@Privilege(code="aerialmaterial:stock:editmain")
	@ResponseBody
	@RequestMapping(value = "editList", method = RequestMethod.POST)
	public Map<String, Object> editList(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		paramsMap.put("userId", user.getId());
		stock.setParamsMap(paramsMap);
		
		PageHelper.startPage(stock.getPagination());
		List<Stock> list = this.stockSerivce.queryPage4Edit(stock);
		return PageUtil.pack4PageHelper(list, stock.getPagination());
	
	}
	
	/**
	 * @description 
	 * @param request,model
	 * @return 在库和外场航材列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@Privilege(code="aerialmaterial:stock:editmain:01")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public void edit(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		paramsMap.put("userId", user.getId());
		stock.setParamsMap(paramsMap);
		this.stockSerivce.edit(stock);
	
	}
	
	/**
	 * @author liub
	 * @description 根据部件号、机构代码查询库存
	 * @param request,stock
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "selectBjhAndDprt", method = RequestMethod.POST)
	public Map<String, Object> selectBjhAndDprt(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		PageHelper.startPage(stock.getPagination());
		List<Stock> list = this.stockSerivce.selectBjhAndDprt(stock);
		return PageUtil.pack4PageHelper(list, stock.getPagination());
	}
	
	/**
	 * @author liub
	 * @description 在库航材列表(弹窗)
	 * @param request,model
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageListWin", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageListWin(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		paramsMap.put("userId", user.getId());
		stock.setParamsMap(paramsMap);
		
		PageHelper.startPage(stock.getPagination());
		List<Stock> list = this.stockSerivce.queryAllPageListWin(stock);
		return PageUtil.pack4PageHelper(list, stock.getPagination());
	
	}
	
	/**
	 * 查询可报废的库存
	 * @param stock
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectScrapableStock", method = RequestMethod.POST)
	public Map<String, Object> selectScrapableStock(@RequestBody Stock stock) throws BusinessException{
		PageHelper.startPage(stock.getPagination());
		List<Stock> list = this.stockSerivce.selectScrapableStock(stock);
		return PageUtil.pack4PageHelper(list, stock.getPagination());
	}
	
	/**
	 * @Description 查询库存分布详情
	 * @CreateTime 2017-10-30 下午2:40:48
	 * @CreateBy 刘兵
	 * @param stock 库存
	 * @return List<Stock> 库存集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryInventoryDetailList", method = RequestMethod.POST)
	public List<Stock> queryInventoryDetailList(@RequestBody Stock stock) throws BusinessException{
		return stockSerivce.queryInventoryDetailList(stock);
	}
	
	/**
	 * 
	 * @Description 查询工具设备（计量工具校验使用）查询条件库存 b_h_001 部件类型为工具设备（2,3）序列号管理的库存数据
	 * @CreateTime 2018年2月8日 下午5:38:44
	 * @CreateBy 林龙
	 * @param stock
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageToolsList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageToolsList(@RequestBody Stock stock,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = stockSerivce.queryAllPageToolsList(stock);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("库存列表加载失败!",e);
		}
	}
	/**
	 * 
	 * @Description 
	 * @CreateTime 2018-3-22 下午4:15:35
	 * @CreateBy 孙霁
	 * @param stock
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectByIds", method = RequestMethod.POST)
	public List<Stock> selectByIds(@RequestBody List<String> ids,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			return stockSerivce.selectByIds(ids);
		} catch (Exception e) {
			throw new BusinessException("库存列表加载失败!",e);
		}
	}
	
}
