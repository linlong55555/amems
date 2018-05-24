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
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.aerialmaterial.service.OutFieldStockService;
import com.eray.thjw.aerialmaterial.service.StoreService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;

/**
 * 
 * 拆下件清单
 * @author xu.yong
 *
 */
@Controller
@RequestMapping("/aerialmaterial/removepart")
public class RemovePiecesController {
	
	@Resource
	private OutFieldStockService outFieldStockService;
	
	@Resource
	private StoreService storeService;
	/**
	 * 跳转至拆下件清单管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:removepart:main")
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
		return new ModelAndView("/material/removepart/removepart_main", model);
	}
	
	/**
	 * 根据仓库号查询库位号列表
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list/type", method = RequestMethod.POST)
	public List<Storage> senda1(String id) throws BusinessException{
		try {
			return	storeService.queryStorageListByStoreId(id);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 拆下件清单列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "removepartList", method = RequestMethod.POST)
	public Map<String, Object> removepartList(@RequestBody OutFieldStock outFieldStock,HttpServletRequest request,Model model) throws BusinessException{
		String id = outFieldStock.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		outFieldStock.setId(null);
		PageHelper.startPage(outFieldStock.getPagination());
		List<OutFieldStock> list = outFieldStockService.queryAllPageList(outFieldStock);
		if(((Page)list).getTotal() > 0){//当总记录数大于0执行分页查询
			//分页查询
			
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					OutFieldStock outFieldStock1 = new OutFieldStock();
					outFieldStock1.setId(id);
					List<OutFieldStock> newRecordList = outFieldStockService.queryAllPageList(outFieldStock1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, outFieldStock.getPagination());
		}else{
			List<OutFieldStock> newRecordList = new ArrayList<OutFieldStock>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				OutFieldStock outFieldStock1 = new OutFieldStock();
				outFieldStock1.setId(id);
				newRecordList = outFieldStockService.queryAllPageList(outFieldStock1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, outFieldStock.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, outFieldStock.getPagination());
		}
		
	}
	
	/**
	 * @author ll
	 * @description 送修
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:removepart:main:01")
	@ResponseBody
	@RequestMapping(value = "senda", method = RequestMethod.POST)
	public Map<String, Object> senda(@RequestBody OutFieldStock outFieldStock) throws BusinessException{
		try {
			return	outFieldStockService.senda(outFieldStock);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 检查借入单编辑权限
	 * @param request,id
	 * @return
	 * @develop date 2016.10.13
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "checkEdit", method = RequestMethod.POST)
	public void checkEdit(HttpServletRequest request,String id) throws BusinessException {
		try {
			outFieldStockService.checkEdit(id);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 报废
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:removepart:main:02")
	@ResponseBody
	@RequestMapping(value = "scrap", method = RequestMethod.POST)
	public Map<String, Object> scrap(@RequestBody OutFieldStock outFieldStock) throws BusinessException{
		try {
			return	outFieldStockService.remove(outFieldStock);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 入库
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:removepart:main:03")
	@ResponseBody
	@RequestMapping(value = "storage", method = RequestMethod.POST)
	public Map<String, Object> storage(@RequestBody OutFieldStock outFieldStock) throws BusinessException{
		try {
			return outFieldStockService.add(outFieldStock);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	
}
