package com.eray.thjw.aerialmaterial.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.Secondment;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.SecondmentService;
import com.eray.thjw.aerialmaterial.service.StoreService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.otheraerocade.service.OtherAerocadeStockService;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;
import enu.ThresholdEnum;
/**
 * 
 * @author sunji
 * @description 其他飞行队航材库存
 */
@Controller
@RequestMapping("/otheraerocade/stock")
public class OtherAerocadeStockController {

	@Resource
	private OtherAerocadeStockService otherAerocadeStockService;
	@Resource
	private StoreService storeService;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private SecondmentService secondmentService;
	@Resource
	private MonitorsettingsService monitorsettingsService;
	/**
	 * 跳转至借入申请管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="otheraerocade:stock:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<Secondment> newRecordList= secondmentService.queryOtherOrg(ThreadVarUtil.getUser().getJgdm());
		
		model.put("newRecordList", newRecordList);
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("/material/otheraerocade/otheraerocadestock_main", model);
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 其他飞行队在库航材列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "otheraerocadeStockList", method = RequestMethod.POST)
	public Map<String, Object> stockList(@RequestBody Stock stock,HttpServletRequest request,Model model) throws BusinessException{
		 Map<String, Object> responseMap=new HashMap<String, Object>();
		 Monitorsettings monitorsettings =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.HCSM.getName(), stock.getDprtcode());
		try {
			PageHelper.startPage(stock.getPagination());
			List<Stock> list = this.otherAerocadeStockService.queryAllPageList(stock);
			responseMap=PageUtil.pack4PageHelper(list, stock.getPagination());
			responseMap.put("monitorsettings", monitorsettings);
			return responseMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("查询失败");
		}
	}
}
