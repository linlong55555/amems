package com.eray.thjw.aerialmaterial.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.Transfer;
import com.eray.thjw.aerialmaterial.service.SecondmentService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.aerialmaterial.service.TransferService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;
import enu.aerialmaterial.SecondmentTypeEnum;
/**
 * 航材移库
 * @author meizhiliang
 */
@Controller
@RequestMapping("/aerialmaterial/transfer")
public class TransferController extends BaseController {
	
	@Resource
	private SecondmentService secondmentService;
	@Resource
	private StockSerivce stockSerivce;
	@Resource
	private CommonRecService commonRecService;                   //REC表服务
	@Resource
	private TransferService transferService;
	/**
	 * @author 梅志亮  库存列表
	 * @time 2016-10-31
	 */
	@Privilege(code = "aerialmaterial:transfer:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String getIndex(Model model) {
		model.addAttribute("secondmentTypeEnum", SecondmentTypeEnum.enumToListMap());
		model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return "material/transfer/transferlist";
	}
	
	/**
	 * @author meizhiliangg
	 * @description 
	 * @param request,model
	 * @return 进入移库页面初始化 获取选择移库的航材
	 * @throws Exception 
	 * @develop date 2016.11.19
	 */
	@Privilege(code = "aerialmaterial:transfer:main:01")
	@RequestMapping(value = "checkList")
	public ModelAndView stockList(@Param (value="ids") String []  ids) throws BusinessException{
		Map<String, Object> model =new HashMap<String, Object>();
		List<String> list =new ArrayList<String>();
		for (int i = 0; i < ids.length; i++) {
			list.add(ids[i]);
		}
		model.put("stock", stockSerivce.queryChoseList(list));
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("material/transfer/add_transfer", model);
	}
	/**
	 * @author 梅志亮
	 * @description 增加移库操作
	 * @develop date 2016.08.24
	 * @throws BusinessException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Privilege(code = "aerialmaterial:transfer:main:01")
	@ResponseBody
	@RequestMapping(value = "addTransfer", method = RequestMethod.POST)
	public int add(@RequestBody Map map) throws BusinessException {
		return transferService.insertSelective(map);
	}  
	/**
	 * @author 梅志亮  移库清单列表
	 * @time 2016-11-22
	 */
	@Privilege(code = "aerialmaterial:transfer:main")
	@ResponseBody
	@RequestMapping(value = "queryTransferList", method = RequestMethod.POST)
	public Map<String, Object> queryQualityCheckList(
		@RequestBody Transfer transfer, HttpServletRequest request,
		Model model) {
		
		PageHelper.startPage(transfer.getPagination());
		List<Transfer> list = transferService.selectTransferList(transfer);
		return PageUtil.pack4PageHelper(list, transfer.getPagination());
		
	}
	/**
	 * @author 梅志亮  撤销移库
	 * @time 2016-11-22
	 */
	@Privilege(code = "aerialmaterial:transfer:main:02")
	@ResponseBody
	@RequestMapping(value = "Revoke", method = RequestMethod.POST)
	public int Revoke(String id) {
		return transferService.deleteByPrimaryKey(id);
	}
	
	/**
     * @author 梅志亮  查看航材移库
	 * @time 2016-10-31 
	 */
	@RequestMapping(value="view",method=RequestMethod.GET)
	public ModelAndView  View(String id){
		Map<String, Object> model =new HashMap<String, Object>();
		model.put("transferlist", transferService.selectByKey(id));
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return new ModelAndView("material/transfer/view_transfer",model);
	}
}
