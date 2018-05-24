package com.eray.thjw.aerialmaterial.control;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.aerialmaterial.po.Expatriate;
import com.eray.thjw.aerialmaterial.po.InstockDetail;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.aerialmaterial.service.ExpatriateService;
import com.eray.thjw.aerialmaterial.service.InstockService;
import com.eray.thjw.aerialmaterial.service.StoreService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;

import enu.MaterialTypeEnum;
import enu.SupervisoryLevelEnum;
import enu.aerialmaterial.InstockStatusEnum;
import enu.aerialmaterial.InstockTypeEnum;
import enu.aerialmaterial.SecondmentTypeEnum;

/**
 * 入库
 * 包含 采购、送修、借入、借出归还、其它
 * @author xu.yong
 */
@Controller
@RequestMapping("aerialmaterial/instock")
public class InstockController extends BaseController {

	@Resource
	private InstockService instockService;
	
	@Resource
	private StoreService storeService;
	
	@Resource
	private ExpatriateService expatriateService;
	
	@Resource
	private PlaneDataService planeDataService;
	
	/** 页面操作类型  */
	private static final String OPERATE_TYPE = "operateType";
	
	/** 采购 */
	private static final String PURCHASE = "purchase";
	/** 借出 */
	private static final String LEND = "lend";
	/** 送修 */
	private static final String REPAIR = "repair";
	
	/**
	 * 进入入库主界面
	 * @param model
	 */
	@Privilege(code="aerialmaterial:instock:main")
	@RequestMapping("/main")
	public String main(Model model){
		return "material/instock/instock_main";
	}
	
	/**
	 * 编辑视图跳转
	 * @return
	 */
	@RequestMapping(value="/view/{type}/{operateType}/{id}")
	public String view(@PathVariable String type, @PathVariable String operateType, @PathVariable String id, Model model){
		model.addAttribute(OPERATE_TYPE, operateType);
		if(PURCHASE.equals(type)){
			model.addAttribute("id", id);
			//管理级别
			model.addAttribute("gljb", SupervisoryLevelEnum.enumToListMap());
			return "material/instock/purchase_instock_view";
		}else if(REPAIR.equals(type)){
			model.addAttribute("id", id);
			//管理级别
			//model.addAttribute("gljb", SupervisoryLevelEnum.enumToListMap());
			return "material/instock/repair_instock_view";
		}else if(LEND.equals(type)){
			if(StringUtils.isBlank(id)){
				getLogger().error("参数错误");
				throw new RuntimeException("参数错误");
			}
			String[] params = StringUtils.split(id,"||");
			if(params.length != 3){
				getLogger().error("参数错误");
				throw new RuntimeException("参数错误");
			}
			
			//管理级别
			model.addAttribute("gljb", SupervisoryLevelEnum.enumToListMap());
			model.addAttribute("item", this.expatriateService.querySingleLend(params[0], params[1], params[2]));
			return "material/instock/lend_return_instock_view";
		}
		return "";
	}
	
	/**
	 * 分页查询 采购待入库航材
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value="/list/purchase", method=RequestMethod.POST)
	public Map<String, Object> listPurchase(@RequestBody InstockDetail instockDetail) throws BusinessException{
		try{
			Map<String, Object> map = this.instockService.queryPurchasePageList(instockDetail);
			PageUtil.addDict(map, "hclx", MaterialTypeEnum.enumToListMap());
			PageUtil.addDict(map, "gljb", SupervisoryLevelEnum.enumToListMap());
			return map;
		}catch (Exception e) {
			throw new BusinessException("查询采购待入库航材失败 ", e);
		}
	}
	
	/**
	 * 获取入库单信息
	 * @param id
	 */
	@RequestMapping(value="/view/purchase/{id}")
	public @ResponseBody Map<String, Object> queryPurchaseById(@PathVariable String id, Model model){
		
		Map<String, Object> resultMap = PageUtil.pack(this.instockService.queryPurchaseById(id));
		//获取登入user
//		User user=ThreadVarUtil.getUser();
//		Store store=new Store();
//		store.setZt(1);
//		store.setDprtcode(user.getJgdm());
//		PageUtil.addDict(resultMap, "store", storeService.findAlltives(store));
		return resultMap;
	}
	
	
	/**
	 * 保存采购入库
	 * @param id
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/save/purchase")
	public @ResponseBody String savePurchase(@RequestBody Map map) throws BusinessException{
		return this.instockService.savePurchase(map);
	}
	
	/**
	 * 提交采购入库
	 * @param id
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/submit/purchase")
	public @ResponseBody void submitPurchase(@RequestBody Map map) throws BusinessException{
		this.instockService.savePurchase(map);
		this.instockService.savePurchaseSubmit((String)map.get("id"));
	}
	
	/**
	 * 作废采购入库
	 * @param id
	 */
	@RequestMapping(value="/discard/purchase/{id}")
	public @ResponseBody void discardPurchase(@PathVariable String id){
		this.instockService.savePurchaseDiscard(id);;
	}
	
	/**
	 * 借出待归 界面初始化
	 * @return
	 */
	@RequestMapping(value="/init/lend", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> initLend(){
		Map<String, Object> map = new HashMap<String, Object>();
		//借调对象类型
		PageUtil.addDict(map, "jddxlx", SecondmentTypeEnum.enumToListMap());
		PageUtil.addDict(map, "hclx", MaterialTypeEnum.enumToListMap());
		PageUtil.addDict(map, "gljb", SupervisoryLevelEnum.enumToListMap());
		return map;
	}
	
	/**
	 * 分页查询 借出待归还航材
	 * @return
	 */
	@RequestMapping(value="/list/lend", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> listLend(@RequestBody Expatriate expatriate){
		return this.expatriateService.queryLendStatisticsPage(expatriate);
	}
	
	/**
	 * 获取入库单信息
	 * @param id
	 */
	@RequestMapping(value="/view/lend")
	public @ResponseBody Map<String, Object> queryLend(@PathVariable String id, Model model){
		
		Map<String, Object> resultMap = PageUtil.pack(this.instockService.queryPurchaseById(id));
		//获取登入user
		User user=ThreadVarUtil.getUser();
//		Store store=new Store();
//		store.setZt(1);
//		store.setDprtcode(user.getJgdm());
//		PageUtil.addDict(resultMap, "store", storeService.findAlltives(store));
		return resultMap;
	}
	
	/**
	 * 获取仓库列表
	 * @return
	 */
	@RequestMapping(value="/store")
	public @ResponseBody Map<String, Object> store(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//获取登入user
		User user=ThreadVarUtil.getUser();
		Store store=new Store();
		store.setZt(1);
		store.setDprtcode(user.getJgdm());
		PageUtil.addDict(resultMap, "store", storeService.findAlltives(store));
		return resultMap;
	}
	
	/**
	 * 保存采购入库
	 * @param id
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/submit/lend")
	public @ResponseBody void submitLend(@RequestBody Map<String, Object> map) throws BusinessException{
		this.instockService.saveLend(map);
	}
	
	/**
	 * 获取默认库存成本
	 */
	@RequestMapping("/defaultCost")
	public @ResponseBody BigDecimal queryDefaultCost(@RequestParam(required=false) String bjid, @RequestParam(required=false) String bjh, @RequestParam(required=false) String instockDetailId){
		return this.instockService.queryDefaultCost(bjid, bjh, instockDetailId);
	}
	
	
	/**
	 * 分页查询 送修待入库航材
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value="/list/repair", method=RequestMethod.POST)
	public Map<String, Object> listRepair(BaseEntity entity) throws BusinessException{
		try{
			Map<String, Object> map = this.instockService.queryRepairPageList(entity);
			PageUtil.addDict(map, "hclx", MaterialTypeEnum.enumToListMap());
			return map;
		}catch (Exception e) {
			throw new BusinessException("查询送修待入库航材失败 ", e);
		}
	}
	
	/**
	 * 作废送修入库
	 * @param instockDetailId
	 */
	@RequestMapping(value="/discard/repair")
	public @ResponseBody void discardRepair(@RequestParam String instockDetailId){
		this.instockService.saveRepairDiscard(instockDetailId);;
	}
	
	/**
	 * 获取送修入库单信息
	 * @param instockDetailId
	 */
	@RequestMapping(value="/view/repair")
	public @ResponseBody Map<String, Object> queryRepairById(@RequestParam String instockDetailId){
		
		Map<String, Object> resultMap = PageUtil.pack(this.instockService.queryRepairById(instockDetailId));
		//获取登入user
//		User user=ThreadVarUtil.getUser();
//		Store store=new Store();
//		store.setZt(1);
//		store.setDprtcode(user.getJgdm());
//		PageUtil.addDict(resultMap, "store", storeService.findAlltives(store));
		return resultMap;
	}
	
	
	/**
	 * 保存送修入库
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/save/repair")
	public @ResponseBody void saveRepair(@RequestBody Map<String, Object> map) throws BusinessException{
		this.instockService.saveRepair(map);
	}
	
	/**
	 * 提交送修入库
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/submit/repair")
	public @ResponseBody void submitRepair(@RequestBody Map map) throws BusinessException{
		this.instockService.saveRepair(map);
		this.instockService.saveRepairSubmit(map);
	}
	
	/**
	 * 借入入库 界面初始化
	 * @return
	 */
	@RequestMapping(value="/init/borrow", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> initBorrow(){
		Map<String, Object> map = new HashMap<String, Object>();
		//借调对象类型
		PageUtil.addDict(map, "jddxlx", SecondmentTypeEnum.enumToListMap());
		return map;
	}
	
	/**
	 * 跳转借入入库单编辑页面
	 * @param id
	 */
	@RequestMapping(value="/edit/borrow/{borrowApplyId}")
	public String goEditBorrow(@PathVariable String borrowApplyId, Model model){
		model.addAttribute("borrowApplyId", borrowApplyId);
		model.addAttribute(OPERATE_TYPE, "edit");
		return "material/instock/borrow_instock_view";
	}
	
	/**
	 * 获取借入入库单信息
	 * @param instockId 入库单ID
	 * @param borrowApplyId 借入申请ID
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/view/borrow", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> queryBorrow(@RequestParam(required=false) String instockId, @RequestParam(required=false) String borrowApplyId) throws BusinessException{
		Map<String, Object> map = this.instockService.queryBorrow(instockId, borrowApplyId);
		PageUtil.addDict(map, "gljb", SupervisoryLevelEnum.enumToListMap());
		PageUtil.addDict(map, "jddxlx", SecondmentTypeEnum.enumToListMap());
		//获取登入user
//		User user=ThreadVarUtil.getUser();
//		Store store=new Store();
//		store.setZt(1);
//		store.setDprtcode(user.getJgdm());
//		PageUtil.addDict(map, "store", storeService.findAlltives(store));
		return map;
	}
	
	/**
	 * 提交借入入库
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/submit/borrow")
	public @ResponseBody void submitBorrow(@RequestBody Map map) throws BusinessException{
		this.instockService.saveBorrowSubmit(map);
	}
	/**
	 * 手工入库初始化数据
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/handwork/initdata")
	public @ResponseBody Map<String, Object> initdataHandWork() throws BusinessException{
		Map<String, Object> map = new HashMap<String, Object>();
		PageUtil.addDict(map, "gljb", SupervisoryLevelEnum.enumToListMap());
		//获取登入user
		User user=ThreadVarUtil.getUser();
		
		List<String> list = new ArrayList<String>(1);
		list.add(user.getJgdm());
		List planes = planeDataService.selectByDprtcodeList(list);
		PageUtil.addDict(map, "planes", planes);
		return map;
	}
	
	/**
	 * 提交手工入库
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/submit/handwork")
	public @ResponseBody void submitHandwork(@RequestBody Map map) throws BusinessException{
		this.instockService.saveHandworkSubmit(map);
	}
	
	/**
	 * 分页查询历史入库单
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> listPage(BaseEntity entity) throws BusinessException{
		Map<String, Object> map = this.instockService.queryPage(entity);
		PageUtil.addDict(map, "rklx", InstockTypeEnum.enumToListMap());
		PageUtil.addDict(map, "zt", InstockStatusEnum.enumToListMap());
		return map;
	}
	
	/**
	 * 撤消入库单
	 * @param id
	 * @throws BusinessException
	 */
	@RequestMapping(value="/cancel", method=RequestMethod.POST)
	public @ResponseBody void cancel(@RequestParam String id) throws BusinessException{
		try {
			this.instockService.saveCancel(id);
		} catch (BusinessException e) {
			getLogger().error(e.getMessage(), e);
			throw e;
		}
	}	
	
	@RequestMapping("/handworkborrow")
	public String goHandworkBorrow(Model model){
		//获取登入user
		User user=ThreadVarUtil.getUser();
		
		List<String> list = new ArrayList<String>(1);
		list.add(user.getJgdm());
		List planes = planeDataService.selectByDprtcodeList(list);
		model.addAttribute("planes", planes);
		return "material/instock/handwork_borrow_instock_view";
	}
	
	@RequestMapping("/view/detail/{id}")
	public String view(@PathVariable String id, Model model){
		model.addAttribute("instock", this.instockService.queryInstockById(id));
		model.addAttribute("details", this.instockService.queryInstockStockById(id));
		return "material/instock/instock_view";
	}
	
}
