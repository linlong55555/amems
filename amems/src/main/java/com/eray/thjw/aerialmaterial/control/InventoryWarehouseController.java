package com.eray.thjw.aerialmaterial.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.aerialmaterial.po.ContractDetail;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.ContractDetailService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.aerialmaterial.service.StoreService;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author 林龙
 * @description 库存出库控制器
 */
@Controller
@RequestMapping("/aerialmaterial/warehouse")
public class InventoryWarehouseController extends BaseController {
	
	
	@Resource
	private StockSerivce stockSerivce;
	
	@Resource
	private StoreService storeService;
	
	@Resource
	private ContractDetailService contractDetailService;
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 在库航材列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "warehouseList", method = RequestMethod.POST)
	public Map<String, Object> warehouseList(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		paramsMap.put("userId", user.getId());
		stock.setParamsMap(paramsMap);
		String id = stock.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		stock.setId(null);
		
		PageHelper.startPage(stock.getPagination());
		List<Stock> list = stockSerivce.queryAllPageNormalListkc(stock);
		if(((Page)list).getTotal() > 0){
			
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					Stock stock1 = new Stock();
					stock1.setId(id);
					List<Stock> newRecordList = stockSerivce.queryAllPageNormalListkc(stock1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, stock.getPagination());
		}else{
			List<Stock> newRecordList = new ArrayList<Stock>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				Stock stock1 = new Stock();
				stock1.setId(id);
				newRecordList = stockSerivce.queryAllPageNormalListkc(stock);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, stock.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, stock.getPagination());
		}
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
	 
	
}
