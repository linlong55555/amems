package com.eray.thjw.material2.control;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.MaterialHistoryService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.ContractInfo;
import com.eray.thjw.material2.po.ContractMgnt;
import com.eray.thjw.material2.po.OutinReceipt;
import com.eray.thjw.material2.po.OutinReceiptSource;
import com.eray.thjw.material2.po.StockInShelf;
import com.eray.thjw.material2.service.ContractInfoService;
import com.eray.thjw.material2.service.MaterialStockInService;
import com.eray.thjw.material2.service.OutinReceiptService;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.util.Utils;

import enu.MaterialTypeEnum;
import enu.material2.StockHistorySubtypeEnum;
import enu.material2.StockHistoryTypeEnum;

/**
 * @author 裴秀
 * @description 收货
 */
@Controller
@RequestMapping("material/outin")
public class OutinReceiptController extends BaseController {
	
	@Resource
	private OutinReceiptService outinReceiptService;
	
	@Resource
	private ContractInfoService contractInfoService;
	@Resource
	private MaterialHistoryService materialHistoryService;
	
	@Resource
	private MaterialStockInService materialStockInService;
	
	/**
	 * @Description 收货
     * @CreateTime 2018年02月28日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:outin:receipt")
	@RequestMapping(value = "receipt", method = RequestMethod.GET)
	public ModelAndView receipt(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/outin/receipt/receipt_main",model);
	}
	
	/**
	 * @Description 查询收货来源-合同列表
	 * @CreateTime 2018年3月5日 下午2:53:40
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/contractlist", method = RequestMethod.POST)
	public Map<String, Object> queryContractList(@RequestBody ContractMgnt record) throws BusinessException{
		try {
			return outinReceiptService.queryContractList(record);
		} catch (Exception e) {
			throw new BusinessException("查询收货来源-合同列表失败!",e);
		}
	}
	
	/**
	 * @Description 保存收货单
	 * @CreateTime 2018年3月9日 下午4:25:05
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public OutinReceipt save(@RequestBody OutinReceipt record) throws BusinessException{
		try {
			return outinReceiptService.doSave(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存收货单失败!",e);
		}
	}
	
	/**
	 * @Description 提交收货单
	 * @CreateTime 2018年3月13日 上午10:11:28
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public OutinReceipt submit(@RequestBody OutinReceipt record) throws BusinessException{
		try {
			return outinReceiptService.doSubmit(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("提交收货单失败!",e);
		}
	}
	
	/**
	 * @Description 查询收货来源详细-合同详细/退料详细列表
	 * @CreateTime 2018年3月7日 上午9:51:37
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/originlist", method = RequestMethod.POST)
	public Map<String, Object> queryOriginList(@RequestBody OutinReceipt record) throws BusinessException{
		try {
			return outinReceiptService.queryOriginList(record);
		} catch (Exception e) {
			throw new BusinessException("查询收货来源详细失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 查询退料数据
	 * @CreateTime 2018年4月20日 上午11:12:13
	 * @CreateBy 林龙
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/returnDatalist", method = RequestMethod.POST)
	public  List<OutinReceiptSource>  queryReturnDatalist(@RequestBody OutinReceipt record) throws BusinessException{
		try {
			return outinReceiptService.queryReturnMaterialList(record);
		} catch (Exception e) {
			throw new BusinessException("查询退料数据失败!",e);
		}
	}
	
	/**
	 * @Description 删除收货单
	 * @CreateTime 2018年3月19日 下午5:21:17
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(String id) throws BusinessException{
		try {
			outinReceiptService.doDelete(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("删除收货单失败!",e);
		}
	}
	
	/**
	 * @Description 撤销收货单
	 * @CreateTime 2018年3月19日 下午5:21:17
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/revoke", method = RequestMethod.POST)
	public void revoke(String id) throws BusinessException{
		try {
			outinReceiptService.doRevoke(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("撤销收货单失败!",e);
		}
	}
	
	/**
	 * @Description 查询当前人的收货单列表
	 * @CreateTime 2018年3月19日 下午2:46:02
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/selflist", method = RequestMethod.POST)
	public Map<String, Object> querySelfList(@RequestBody OutinReceipt record) throws BusinessException{
		try {
			return outinReceiptService.querySelfList(record);
		} catch (Exception e) {
			throw new BusinessException("查询当前人的收货单列表失败!",e);
		}
	}
	
	/**
	 * @Description 查询收货单详情
	 * @CreateTime 2018年3月20日 上午9:38:51
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public OutinReceipt queryDetail(String id) throws BusinessException{
		try {
			return outinReceiptService.queryDetail(id);
		} catch (Exception e) {
			throw new BusinessException("查询收货单详情失败!",e);
		}
	}
	
	/**
	 * @Description 航材入库上架
     * @CreateTime 2018年03月01日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:outin:materialstockin")
	@RequestMapping(value = "materialstockin", method = RequestMethod.GET)
	public ModelAndView materialstockin(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/outin/materialstockin/materialstockin_main",model);
	}
	
	/**
	 * @Description 查询航材入库上架列表
	 * @CreateTime 2018年3月21日 上午9:42:52
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/materialstockinlist", method = RequestMethod.POST)
	public Map<String, Object> queryMaterialStockInList(@RequestBody Stock record) throws BusinessException{
		try {
			return materialStockInService.queryMaterialStockInList(record);
		} catch (Exception e) {
			throw new BusinessException("查询航材入库上架列表失败!",e);
		}
	}
	
	/**
	 * @Description 查询上架详情
	 * @CreateTime 2018年3月22日 下午5:02:14
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/shelfdetail", method = RequestMethod.POST)
	public Stock queryShelfDetail(String id) throws BusinessException{
		try {
			return materialStockInService.queryShelfDetail(id);
		} catch (Exception e) {
			throw new BusinessException("查询上架详情失败!",e);
		}
	}
	
	/**
	 * @Description 入库上架
	 * @CreateTime 2018年3月23日 上午11:43:29
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/putonshelf", method = RequestMethod.POST)
	public void doPutOnShelf(@RequestBody StockInShelf record) throws BusinessException{
		try {
			materialStockInService.doPutOnShelf(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("入库上架失败!",e);
		}
	}
	
	
	/**
	 * @Description 工具入库上架
     * @CreateTime 2018年03月01日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:outin:toolstockin")
	@RequestMapping(value = "toolstockin", method = RequestMethod.GET)
	public ModelAndView toolstockin(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/outin/toolstockin/toolstockin_main",model);
	
	}
	
	/**
	 * @Description 库存履历
     * @CreateTime 2018年03月01日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:outin:stockinoutlist")
	@RequestMapping(value = "stockinoutlist", method = RequestMethod.GET)
	public ModelAndView stockinoutlist(Map<String, Object> model)throws BusinessException {
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());//航材类型
		model.put("stockHistoryTypeEnum", StockHistoryTypeEnum.enumToListMap());//操作类型
		model.put("stockHistorySubtypeEnum", StockHistorySubtypeEnum.enumToListMap());//操作子类型
	    return new ModelAndView("/material2/outin/stockinoutlist/stockinoutlist_main",model);
	
	}
	
	/**
	 * 
	 * @Description 库存履历列表
	 * @CreateTime 2018年3月21日 下午3:49:37
	 * @CreateBy 林龙
	 * @param materialHistory
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "materialHistoryList", method = RequestMethod.POST)
	public Map<String, Object> materialHistoryList(@RequestBody MaterialHistory materialHistory,HttpServletRequest request,Model model) throws BusinessException{
		try {
			return materialHistoryService.queryAllPageList(materialHistory);
		} catch (Exception e) {
			throw new BusinessException("库存履历列表加载失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 查询合同明细
	 * @CreateTime 2018年3月13日 下午2:13:23
	 * @CreateBy 林龙
	 * @param demandDetails
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryContractdetailsList", method = RequestMethod.POST)
	public List<ContractInfo> queryContractdetailsList(@RequestBody ContractInfo contractInfo,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			return contractInfoService.queryContractdetailsList(contractInfo);
		} catch (Exception e) {
			throw new BusinessException("查询合同明细加载失败!",e);
		}
	}
	
	/**
	 * 	
	 * @Description 库存履历导出
	 * @CreateTime 2018年3月21日 下午5:27:47
	 * @CreateBy 林龙
	 * @param json
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "MaterialHistoryInfo.xls")
	public String export(String json, Model model) throws BusinessException {
		try {
			json=new String (json.getBytes("iso-8859-1"),"utf-8");
			MaterialHistory materialHistory = Utils.Json.toObject(json, MaterialHistory.class);
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			materialHistory.setPagination(p);
			Map<String, Object> resultMap = materialHistoryService.queryAllPageList(materialHistory);
			List<MaterialHistory> list = (List<MaterialHistory>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "materialHistoryInfo", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}
	}
}
