package com.eray.thjw.aerialmaterial.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.Expatriate;
import com.eray.thjw.aerialmaterial.po.MaterialRequisition;
import com.eray.thjw.aerialmaterial.po.MaterialRequisitionDetail;
import com.eray.thjw.aerialmaterial.po.Outstock;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.aerialmaterial.service.ContractDetailService;
import com.eray.thjw.aerialmaterial.service.ExpatriateService;
import com.eray.thjw.aerialmaterial.service.MaterialRequisitionDetailService;
import com.eray.thjw.aerialmaterial.service.MaterialRequisitionService;
import com.eray.thjw.aerialmaterial.service.OutstockService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.aerialmaterial.service.StoreService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;
import enu.aerialmaterial.SecondmentTypeEnum;

/**
 * 
 * @author 林龙
 * @param <OutstockSerivce>
 * @description 出库控制器
 */
@Controller
@RequestMapping("/aerialmaterial/outstock")
public class SerialmaterialController extends BaseController {
	
	
	@Resource
	private StockSerivce stockSerivce;
	
	@Resource
	private ContractDetailService contractDetailService;
	
	
	@Resource
	private MaterialRequisitionService materialRequisitionService;
	
	@Resource
	private MaterialRequisitionDetailService materialRequisitionDetailService;
	
	@Resource
	private OutstockService outstockService;
	
	@Resource
	private StoreService storeService;
	
	@Resource
	private PlaneDataService planeDataService;
	
	@Resource
	private ExpatriateService expatriateService;
	
	/**
	 * 跳转至领用管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:outstock:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		//获取登入user
		User user=ThreadVarUtil.getUser();
		Store store=new Store();
		store.setZt(1);
		store.setDprtcode(user.getJgdm());
		
		model.put("stockList", storeService.findAlltives(store));
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("/material/outstock/outstock_main", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 申请单列表列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "stockList", method = RequestMethod.POST)
	public Map<String, Object> stockList(@RequestBody MaterialRequisition materialRequisition,HttpServletRequest request,Model model) throws BusinessException{
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		paramsMap.put("userId", user.getId());
		materialRequisition.setParamsMap(paramsMap);
		
		PageHelper.startPage(materialRequisition.getPagination());
		List<MaterialRequisition> list = this.materialRequisitionService.queryAllPageList(materialRequisition);
		return PageUtil.pack4PageHelper(list, materialRequisition.getPagination());
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 领用出库航材列表列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "aviationList", method = RequestMethod.POST)
	public Map<String, Object> aviationList(@RequestBody MaterialRequisitionDetail materialRequisitionDetail,HttpServletRequest request,Model model) throws BusinessException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("rows",materialRequisitionDetailService.queryAllPageList(materialRequisitionDetail));
			
			return resultMap;
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 领用库存列表列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "inventoryList", method = RequestMethod.POST)
	public Map<String, Object> inventoryList(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("rows",stockSerivce.queryAllList(stock));
			
			return resultMap;
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 领用出库
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@Privilege(code="aerialmaterial:outstock:main:01")
	@ResponseBody
	@RequestMapping(value = "stockRemoval", method = RequestMethod.POST)
	public Map<String, Object> stockRemoval(@RequestBody Outstock outstock,HttpServletRequest request,Model model) throws BusinessException{
		
		return	outstockService.save(outstock);
	
	}
	
	/**
	 * 初始化手动领用出库
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:outstock:main")
	@RequestMapping(value = "/warehousemain", method = RequestMethod.GET)
	public ModelAndView warehousemain( String id,HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Stock> stocklist=new ArrayList<Stock>();
	
		String[] item = id.split(",");
		for (int i = 0; i < item.length; i++) {
			Stock stock1=new Stock(); 
			stock1.setId(item[i]);
			Stock stock=stockSerivce.queryKey(stock1);
			stocklist.add(stock);
		}
		model.put("dprtcodeNum", stocklist.get(0).getDprtcode());
		model.put("stocklist", stocklist);
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("/material/outstock/warehouse_history", model);
	}
	
	/**
	 * 更换库存
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:outstock:main")
	@RequestMapping(value = "/changerepertory", method = RequestMethod.GET)
	public ModelAndView changerepertory( String id,String parameter,HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("changerepertoryid", id);
		model.put("parameter", parameter);
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("/material/outstock/outstock_main", model);
	}
	
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 手动领用出库
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@Privilege(code="aerialmaterial:outstock:main:03")
	@ResponseBody
	@RequestMapping(value = "manualstockRemoval", method = RequestMethod.POST)
	public Map<String, Object> manualstockRemoval(@RequestBody Outstock outstock,HttpServletRequest request,Model model) throws BusinessException{
		
		return	outstockService.manualsave(outstock);
	
	}
	
	/**
	 * 初始化借出出库
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:outstock:main")
	@RequestMapping(value = "/checkout/{id}", method = RequestMethod.GET)
	public ModelAndView checkout(@PathVariable("id") String id,HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Stock> stocklist=new ArrayList<Stock>();
	
		String[] item = id.split(",");
		for (int i = 0; i < item.length; i++) {
			Stock stock1=new Stock(); 
			stock1.setId(item[i]);
			Stock stock=stockSerivce.queryKey(stock1);
			stocklist.add(stock);
		}
		
		List<Map<String, Object>> map=	SecondmentTypeEnum.enumToListMap();
		model.put("dprtcodeNum", stocklist.get(0).getDprtcode());
		model.put("secondmenttype", map);
		model.put("stocklist", stocklist);
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("/material/outstock/checkout_history", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 借出出库
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@Privilege(code="aerialmaterial:outstock:main:04")
	@ResponseBody
	@RequestMapping(value = "checkoutstockRemoval", method = RequestMethod.POST)
	public Map<String, Object> checkoutstockRemoval(@RequestBody Outstock outstock,HttpServletRequest request,Model model) throws BusinessException{
		
		return	outstockService.checkoutsave(outstock);
	
	}
	
	/**
	 * 初始化归还出库
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:outstock:main")
	@RequestMapping(value = "/givebackout/{id}", method = RequestMethod.GET)
	public ModelAndView givebackout(@PathVariable("id") String id,HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Stock> stocklist=new ArrayList<Stock>();
	
		String[] item = id.split(",");
		for (int i = 0; i < item.length; i++) {
			Stock stock1=new Stock(); 
			stock1.setId(item[i]);
			Stock stock=stockSerivce.queryKey(stock1);
			stocklist.add(stock);
		}
		
		List<Map<String, Object>> map=	SecondmentTypeEnum.enumToListMap();
		model.put("dprtcodeNum", stocklist.get(0).getDprtcode());
		model.put("secondmenttype", map);
		model.put("stocklist", stocklist);
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("/material/outstock/givebackout_history", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 归还航材列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "givebackList", method = RequestMethod.POST)
	public Map<String, Object> givebackList(@RequestBody Expatriate expatriate,HttpServletRequest request,Model model) throws BusinessException{
		PageHelper.startPage(expatriate.getPagination());
		List<Expatriate> list = this.expatriateService.queryAllPageList(expatriate);
		return PageUtil.pack4PageHelper(list, expatriate.getPagination());
	}
	
	 /**
	  * 根据借调对象和部件id查询当前的待归还数量
	  * @param secondment
	  * @return
	  */
	@ResponseBody
	@RequestMapping(value = "querySelectCount", method = RequestMethod.POST)
	public Expatriate querySelectCount(@RequestBody Expatriate expatriate,HttpServletRequest request,Model model) throws BusinessException{
		
		return	expatriateService.querySelectCount(expatriate);
	
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 归还出库分配
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "checkoutstockassign", method = RequestMethod.POST)
	public Map<String, Object> checkoutstockassign(@RequestBody Outstock outstock,HttpServletRequest request,Model model) throws BusinessException{
		
		return	outstockService.checkoutstockassign(outstock);
	
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 归还出库
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@Privilege(code="aerialmaterial:outstock:main:05")
	@ResponseBody
	@RequestMapping(value = "returntheoutbounds", method = RequestMethod.POST)
	public Map<String, Object> returntheoutbounds(@RequestBody Outstock outstock,HttpServletRequest request,Model model) throws BusinessException{
		
		return	outstockService.returntheoutbound(outstock);
	}

	/**
	 * 初始化报废出库
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:outstock:main")
	@RequestMapping(value = "/dumping/{id}", method = RequestMethod.GET)
	public ModelAndView dumping(@PathVariable("id") String id,HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Stock> stocklist=new ArrayList<Stock>();
	
		String[] item = id.split(",");
		for (int i = 0; i < item.length; i++) {
			Stock stock1=new Stock(); 
			stock1.setId(item[i]);
			Stock stock=stockSerivce.queryKey(stock1);
			stocklist.add(stock);
		}
		
		List<Map<String, Object>> map=	SecondmentTypeEnum.enumToListMap();
		model.put("dprtcodeNum", stocklist.get(0).getDprtcode());
		model.put("secondmenttype", map);
		model.put("stocklist", stocklist);
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("/material/outstock/dumpingbackout_history", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 报废出库
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@Privilege(code="aerialmaterial:outstock:main:06")
	@ResponseBody
	@RequestMapping(value = "manualstockdumping", method = RequestMethod.POST)
	public Map<String, Object> manualstockdumping(@RequestBody Outstock outstock,HttpServletRequest request,Model model) throws BusinessException{
		try {
			return	outstockService.doManualstockdumping(outstock);
		} catch (BusinessException e) {
			throw e;
		}catch (Exception e) {
			throw new BusinessException("报废出库失败!",e);
		}
	}
	
	/**
	 * 初始化其它出库
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:outstock:main")
	@RequestMapping(value = "/otherbackout/{id}", method = RequestMethod.GET)
	public ModelAndView otherbackout(@PathVariable("id") String id,HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Stock> stocklist=new ArrayList<Stock>();
	
		String[] item = id.split(",");
		for (int i = 0; i < item.length; i++) {
			Stock stock1=new Stock(); 
			stock1.setId(item[i]);
			Stock stock=stockSerivce.queryKey(stock1);
			stocklist.add(stock);
		}
		
		List<Map<String, Object>> map=	SecondmentTypeEnum.enumToListMap();
		model.put("dprtcodeNum", stocklist.get(0).getDprtcode());
		model.put("secondmenttype", map);
		model.put("stocklist", stocklist);
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("/material/outstock/otherbackout_history", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 其它出库
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@Privilege(code="aerialmaterial:outstock:main:07")
	@ResponseBody
	@RequestMapping(value = "manualotherbackout", method = RequestMethod.POST)
	public Map<String, Object> manualotherbackout(@RequestBody Outstock outstock,HttpServletRequest request,Model model) throws BusinessException{
		
		return	outstockService.manualotherbackout(outstock);
	
	}
}
