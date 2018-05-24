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

import com.alibaba.fastjson.JSON;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.StoreCheck;
import com.eray.thjw.material2.service.StoreCheckService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;
import enu.aerialmaterial.MaterialSrouceEnum;
/**
 * @author 裴秀
 * @description 盘点
 */
@Controller
@RequestMapping("material/store/check")
public class StoreCheckRegisterController extends BaseController{
	
	@Resource
	private StoreCheckService storeCheckService;
	
	/**
	 * @Description 盘点
     * @CreateTime 2018年03月20日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:store:check:register")
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public ModelAndView register(Model model){
		model.addAttribute("type", 1);
		model.addAttribute("materialSrouceEnum", MaterialSrouceEnum.enumToListMap());
		model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
	    return new ModelAndView("/material2/store/check/check_main");
	
	}
	
	/**
	 * @Description 盈亏历史
     * @CreateTime 2018年03月21日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:store:check:list")
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView list(Model model){
		model.addAttribute("type", 1);
	    return new ModelAndView("/material2/store/list/list_main");
	}
	
	/**
	 * @Description 新增库存
	 * @CreateTime 2018-3-21 下午5:52:46
	 * @CreateBy 刘兵
	 * @param stock 库存
	 * @throws BusinessException
	 */
	@Privilege(code="material:store:check:register")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody Stock stock) throws BusinessException{
		try {
			return storeCheckService.save(stock);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("新增库存失败!",e);
		}
	}
	
	/**
	 * @Description 修改库存
	 * @CreateTime 2018-3-21 下午5:52:46
	 * @CreateBy 刘兵
	 * @param stock 库存
	 * @throws BusinessException
	 */
	@Privilege(code="material:store:check:register")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@RequestBody Stock stock) throws BusinessException{
		try {
			return storeCheckService.update(stock);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改库存失败!",e);
		}
	}
	
	/**
	 * @Description 盈亏历史分页列表查询
	 * @CreateTime 2018-3-22 上午11:54:02
	 * @CreateBy 刘兵
	 * @param storeCheck 盘点记录
	 * @return Map<String, Object> 
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody StoreCheck storeCheck)throws BusinessException{
		try {
			PageHelper.startPage(storeCheck.getPagination());
			List<StoreCheck> list = storeCheckService.queryAllPageList(storeCheck);
			return PageUtil.pack4PageHelper(list, storeCheck.getPagination());
		} catch (Exception e) {
			throw new BusinessException("盈亏历史列表加载失败!",e);
		}	
	}
	
	/**
	 * @Description 获取库存数据
	 * @CreateTime 2018-3-23 上午11:11:38
	 * @CreateBy 刘兵
	 * @param stock 库存
	 * @return Stock 库存
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getByStock", method = RequestMethod.POST)
	public Stock getByStock(@RequestBody Stock stock)throws BusinessException{
		try {
			return storeCheckService.getByStock(stock);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("获取库存数据失败!",e);
		}
	}
	
	/**
	 * @Description 导出
	 * @CreateTime 2018-3-26 上午10:10:29
	 * @CreateBy 刘兵
	 * @param paramjson 当前参数
	 * @return String 页面数据
	 * @throws BusinessException
	 */
	@Privilege(code="material:store:check:register")
	@RequestMapping(value = "storecheck.xls", method = RequestMethod.GET)
	public String exportExcelMt(String paramjson, HttpServletRequest request, Model model) throws BusinessException {
		try {
		    paramjson = new String(paramjson.getBytes("iso-8859-1"),"utf-8");
		    StoreCheck storeCheck = JSON.parseObject(paramjson, StoreCheck.class);
			List<StoreCheck> list = storeCheckService.doExportExcel(storeCheck);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "storecheck", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	
	
}
