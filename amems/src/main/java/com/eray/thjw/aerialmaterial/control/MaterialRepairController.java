package com.eray.thjw.aerialmaterial.control;

import java.util.ArrayList;
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

import com.eray.thjw.aerialmaterial.po.MaterialRepair;
import com.eray.thjw.aerialmaterial.service.ContractDetailService;
import com.eray.thjw.aerialmaterial.service.MaterialRepairService;
import com.eray.thjw.aerialmaterial.service.MaterialRequisitionDetailService;
import com.eray.thjw.aerialmaterial.service.MaterialRequisitionService;
import com.eray.thjw.aerialmaterial.service.OutstockService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.aerialmaterial.service.StoreService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author 林龙
 * @param <OutstockSerivce>
 * @description 送修出库控制器
 */
@Controller
@RequestMapping("/aerialmaterial/materialrepair")
public class MaterialRepairController extends BaseController {
	
	@Resource
	private MaterialRepairService materialRepairService;
	
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
	
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 送修出库列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "repairList", method = RequestMethod.POST)
	public Map<String, Object> repairList(@RequestBody MaterialRepair materialRepair,HttpServletRequest request,Model model) throws BusinessException{
		String id = materialRepair.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		materialRepair.setId(null);
		
		PageHelper.startPage(materialRepair.getPagination());
		List<MaterialRepair> list = materialRepairService.queryAllPageList(materialRepair);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					MaterialRepair materialRepair1 = new MaterialRepair();
					materialRepair1.setId(id);
					List<MaterialRepair> newRecordList = materialRepairService.queryAllPageList(materialRepair1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, materialRepair.getPagination());
		}else{
			List<MaterialRepair> newRecordList = new ArrayList<MaterialRepair>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				MaterialRepair materialRepair1 = new MaterialRepair();
				materialRepair1.setId(id);
				newRecordList = materialRepairService.queryAllPageList(materialRepair1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, materialRepair.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, materialRepair.getPagination());
		}
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 送修出库
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@Privilege(code="aerialmaterial:outstock:main:02")
	@ResponseBody
	@RequestMapping(value = "sendOutbound", method = RequestMethod.POST)
	public Map<String, Object> stockRemoval(@RequestBody MaterialRepair materialRepair,HttpServletRequest request,Model model) throws BusinessException{
		
		return	outstockService.saveOutbound(materialRepair);
	
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询航材送修单信息
	 * @param reserve
	 * @return Map<String, Object>
	 * @develop date 2016.11.01
	 */
	@ResponseBody
	@RequestMapping(value = "queryRepairPageList", method = RequestMethod.POST)
	public Map<String, Object> queryRepairPageList(@RequestBody MaterialRepair materialRepair)throws BusinessException {
		try {
			return materialRepairService.queryRepairPageList(materialRepair);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}

	/**
	 * @author liub
	 * @description  根据查询条件分页查询审核航材送修单信息
	 * @param reserve
	 * @return Map<String, Object>
	 * @develop date 2016.11.01
	 */
	@ResponseBody
	@RequestMapping(value = "queryApproveRepairPageList", method = RequestMethod.POST)
	public Map<String, Object> queryApproveRepairPageList(@RequestBody MaterialRepair materialRepair)throws BusinessException {
		try {
			return materialRepairService.queryApproveRepairPageList(materialRepair);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询送修航材信息(弹窗)
	 * @param reserve
	 * @return Map<String, Object>
	 * @develop date 2016.11.01
	 */
	@ResponseBody
	@RequestMapping(value = "queryRepairMaterialPageList", method = RequestMethod.POST)
	public Map<String, Object> queryRepairMaterialPageList(@RequestBody MaterialRepair materialRepair,HttpServletRequest request,Model model) throws BusinessException{
		PageHelper.startPage(materialRepair.getPagination());
		List<Map<String, Object>> list = this.materialRepairService.queryRepairMaterialPageList(materialRepair);
		return PageUtil.pack4PageHelper(list, materialRepair.getPagination());
	}
	 
}
