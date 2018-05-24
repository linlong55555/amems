package com.eray.thjw.aerialmaterial.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.TakeStock;
import com.eray.thjw.aerialmaterial.po.TakeStockDetail;
import com.eray.thjw.aerialmaterial.po.TakeStockScope;
import com.eray.thjw.aerialmaterial.service.TakeStockService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;
import enu.aerialmaterial.MaterialSrouceEnum;
import enu.aerialmaterial.TakeStockStatusEnum;

/**
 * @author liub
 * @description 盘点控制层
 * @develop date 2016.11.18
 */
@Controller
@RequestMapping("/aerialmaterial/takestock")
public class TakeStockController extends BaseController {
	
	/**
	 * @author liub
	 * @description 盘点service
	 * @develop date 2016.11.21
	 */
	@Autowired
	private TakeStockService takeStockService;
	
	/**
	 * @author liub
	 * @description 盘点界面
	 * @param
	 * @return 页面视图
	 * @develop date 2016.11.21
	 *
	 */
	@Privilege(code="aerialmaterial:takestock:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model,HttpServletRequest request) throws BusinessException {
		model.addAttribute("materialSrouceEnum", MaterialSrouceEnum.enumToListMap());
		model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return "material/takestock/takeStock_main";
	}
	
	/**
	 * @author liub
	 * @description 盘点历史界面
	 * @param
	 * @return 页面视图
	 * @develop date 2016.11.21
	 *
	 */
	@Privilege(code="aerialmaterial:takestock:history")
	@RequestMapping(value = "history", method = RequestMethod.GET)
	public String history(Model model,HttpServletRequest request) throws BusinessException {
		model.addAttribute("takeStockStatusEnum", TakeStockStatusEnum.enumToListMap());
		return "material/takestock/takeStock_history";
	}
	
	/**
	 * @author liub
	 * @description 查看盘点详情
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.11.28
	 *
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,String id) throws BusinessException{
		model.addAttribute("id", id);
		return new ModelAndView("material/takestock/takeStock_view");
	}
	
	/**
	 * @author liub
	 * @description 保存盘点单
	 * @param takeStock
	 * @develop date 2016.11.22
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:takestock:main:01")
	@ResponseBody
	@RequestMapping(value = "addTakeStock", method = RequestMethod.POST)
	public String addTakeStock(@RequestBody TakeStock takeStock) throws BusinessException{
		String pdid = null;
		try {
			pdid = takeStockService.addTakeStock(takeStock);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存盘点单失败!",e);
		}
		return pdid;
	}
	
	/**
	 * @author liub
	 * @description 修改盘点单
	 * @param takeStock
	 * @develop date 2016.11.22
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:takestock:main:02")
	@ResponseBody
	@RequestMapping(value = "editTakeStock", method = RequestMethod.POST)
	public String editTakeStock(@RequestBody TakeStock takeStock) throws BusinessException{
		try {
			takeStockService.editTakeStock(takeStock);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改盘点单失败!",e);
		}
		return takeStock.getId();
	}
	
	/**
	 * @author liub
	 * @description 提交盘点单
	 * @param takeStock
	 * @develop date 2016.11.28
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:takestock:main:05")
	@ResponseBody
	@RequestMapping(value = "submitTakeStock", method = RequestMethod.POST)
	public String submitTakeStock(@RequestBody TakeStock takeStock) throws BusinessException{
		try {
			takeStock.setZt(TakeStockStatusEnum.SUBMIT.getId());
			takeStockService.editTakeStock(takeStock);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("提交盘点单失败!",e);
		}
		return takeStock.getId();
	}
	
	/**
	 * @author liub
	 * @description 保存盘点范围
	 * @param takeStock
	 * @develop date 2016.11.22
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:takestock:main:02")
	@ResponseBody
	@RequestMapping(value = "addTakeScope", method = RequestMethod.POST)
	public void addTakeScope(@RequestBody TakeStock takeStock) throws BusinessException{
		try {
			takeStockService.addTakeScope(takeStock);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存盘点范围失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 保存盘点详情根据库存
	 * @param takeStockDetail
	 * @develop date 2016.11.24
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:takestock:main:03")
	@ResponseBody
	@RequestMapping(value = "addDetailFromStock", method = RequestMethod.POST)
	public void addDetailFromStock(@RequestBody TakeStockDetail takeStockDetail) throws BusinessException{
		try {
			takeStockService.addDetailFromStock(takeStockDetail);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存盘点详情失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 保存盘点详情根据航材
	 * @param takeStockDetail
	 * @develop date 2016.11.24
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:takestock:main:03")
	@ResponseBody
	@RequestMapping(value = "addDetailFromMaterial", method = RequestMethod.POST)
	public void addDetailFromMaterial(@RequestBody TakeStockDetail takeStockDetail) throws BusinessException{
		try {
			takeStockService.addDetailFromMaterial(takeStockDetail);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存盘点详情失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 修改盘点详情,及部件履历
	 * @param takeStockDetail
	 * @develop date 2016.11.25
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:takestock:main:03")
	@ResponseBody
	@RequestMapping(value = "eidtTakeStockDetail", method = RequestMethod.POST)
	public void eidtTakeStockDetail(@RequestBody TakeStockDetail takeStockDetail) throws BusinessException{
		try {
			takeStockService.eidtTakeStockDetail(takeStockDetail);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改盘点详情失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 删除盘点范围
	 * @param request,id
	 * @return
	 * @develop date 2016.11.23
	 * @throws BusinessException
	 */
	@Privilege(code="aerialmaterial:takestock:main:02")
	@ResponseBody
	@RequestMapping(value = "delTakeScope", method = RequestMethod.POST)
	public void delTakeScope(HttpServletRequest request,String takeScopeId,String mainId) throws BusinessException{
		try {
			takeStockService.delTakeScope(takeScopeId,mainId);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("删除盘点范围失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 审核
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.11.28
	 *
	 */
	@Privilege(code="aerialmaterial:takestock:history:01")
	@RequestMapping(value = "audit", method = RequestMethod.GET)
	public ModelAndView audit(Model model,String id) throws BusinessException{
		model.addAttribute("id", id);
		return new ModelAndView("material/takestock/takeStock_audit");
	}
	
	/**
	 * @author liub
	 * @description 审核
	 * @param takeStock
	 * @develop date 2016.11.29
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:takestock:history:03")
	@ResponseBody
	@RequestMapping(value = "audit", method = RequestMethod.POST)
	public void audit(@RequestBody TakeStock takeStock) throws BusinessException{
		try {
			takeStockService.editAudit(takeStock);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("审核失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 撤销
	 * @param takeStock
	 * @develop date 2016.11.29
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:takestock:history:02")
	@ResponseBody
	@RequestMapping(value = "recall", method = RequestMethod.POST)
	public void recall(@RequestBody TakeStock takeStock) throws BusinessException{
		try {
			takeStockService.editRecall(takeStock);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("撤销失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据id查询盘点单
	 * @param request,id
	 * @return TakeStock
	 * @develop date 2016.11.22
	 */
	@ResponseBody
	@RequestMapping(value = "getById", method = RequestMethod.POST)
	public TakeStock getById(String id,HttpServletRequest request)throws BusinessException {
		TakeStock obj = null;
		try {
			obj = takeStockService.getById(id);
		} catch (Exception e) {
			throw new BusinessException("根据id查询盘点单失败",e);
		}
		return obj;
	}
	
	/**
	 * @author liub
	 * @description 根据仓库id查询盘点单列表
	 * @param request,ckid
	 * @return List<TakeStock>
	 * @develop date 2016.11.22
	 */
	@ResponseBody
	@RequestMapping(value = "queryListByCkid", method = RequestMethod.POST)
	public List<TakeStock> queryListByCkid(String ckid,HttpServletRequest request)throws BusinessException {
		List<TakeStock> list = null;
		try {
			list = takeStockService.queryListByCkid(ckid);
		} catch (Exception e) {
			throw new BusinessException("根据仓库id查询盘点单列表失败",e);
		}
		return list;
	}
	
	/**
	 * @author liub
	 * @description 根据盘点id查询盘点范围列表
	 * @param request,mainid
	 * @return List<TakeStock>
	 * @develop date 2016.11.22
	 */
	@ResponseBody
	@RequestMapping(value = "queryTakeScopeListByMainId", method = RequestMethod.POST)
	public List<TakeStockScope> queryTakeScopeListByMainId(String mainid,HttpServletRequest request)throws BusinessException {
		List<TakeStockScope> list = null;
		try {
			list = takeStockService.queryTakeScopeListByMainId(mainid);
		} catch (Exception e) {
			throw new BusinessException("根据盘点id查询盘点范围列表失败",e);
		}
		return list;
	}

	/**
	 * @author liub
	 * @description  根据查询条件分页查询盘点单列表
	 * @param takeStock
	 * @return Map<String, Object>
	 * @develop date 2016.11.21
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody TakeStock takeStock)throws BusinessException {
		String id = takeStock.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		takeStock.setId(null);
		try {
			PageHelper.startPage(takeStock.getPagination());
			List<TakeStock> list = takeStockService.queryAllPageList(takeStock);
			if(((Page)list).getTotal() > 0){
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						TakeStock newRecord = new TakeStock();
						newRecord.setId(id);
						List<TakeStock> newRecordList = takeStockService.queryAllPageList(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}
					}
				}
				return PageUtil.pack4PageHelper(list, takeStock.getPagination());
			}else{
				List<TakeStock> newRecordList = new ArrayList<TakeStock>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					TakeStock newRecord = new TakeStock();
					newRecord.setId(id);
					newRecordList = takeStockService.queryAllPageList(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, takeStock.getPagination());
					}
				}
				return PageUtil.pack(0, newRecordList, takeStock.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询盘点盈亏详情列表
	 * @param takeStockDetail
	 * @return Map<String, Object>
	 * @develop date 2016.11.23
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllDetailPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllDetailPageList(@RequestBody TakeStockDetail takeStockDetail) throws BusinessException{
		try {
			PageHelper.startPage(takeStockDetail.getPagination());
			List<TakeStockDetail> list = takeStockService.queryAllDetailPageList(takeStockDetail);
			return PageUtil.pack4PageHelper(list, takeStockDetail.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询库存及盘点盈亏详情列表
	 * @param takeStockDetail
	 * @return Map<String, Object>
	 * @develop date 2016.11.23
	 */
	@ResponseBody
	@RequestMapping(value = "queryStockDetailPageList", method = RequestMethod.POST)
	public Map<String, Object> queryStockDetailPageList(@RequestBody TakeStockDetail takeStockDetail) throws BusinessException{
		try {
			PageHelper.startPage(takeStockDetail.getPagination());
			List<TakeStockDetail> list = takeStockService.queryStockDetailPageList(takeStockDetail);
			return PageUtil.pack4PageHelper(list, takeStockDetail.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据盘点差异id查询盘点差异及履历信息
	 * @param id,model
	 * @return Stock
	 * @develop date 2016.11.25
	 */
	@ResponseBody
	@RequestMapping(value = "queryByDetailId",method={RequestMethod.POST,RequestMethod.GET})
	public TakeStockDetail queryByDetailId(Model model,String id) throws BusinessException {
		TakeStockDetail takeStockDetail = null;
		try {
			takeStockDetail = takeStockService.queryByDetailId(id);
		} catch (Exception e) {
			throw new BusinessException("查询盘点差异失败!",e);
		}
		return takeStockDetail;
	}
	

}
