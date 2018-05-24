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
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.po.MaterialCost;
import com.eray.thjw.aerialmaterial.po.MaterialToPlaneModel;
import com.eray.thjw.aerialmaterial.service.HCMainDataService;
import com.eray.thjw.aerialmaterial.service.MaterialCostService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import enu.BinaryEnum;
import enu.MaterialPriceEnum;
import enu.MaterialSecondTypeEnum;
import enu.MaterialTypeEnum;
import enu.SupervisoryLevelEnum;

 
@Controller
@RequestMapping("/aerialmaterial/cost/")
public class MaterialCostController {
	
	@Resource
	private UserService userService;
	@Resource
	private MaterialCostService  materialCostService;
	@Resource
	private HCMainDataService hCMainDataService;
	@Resource
	private PlaneModelDataService planeModelDataService;

	
	/**
	 * @author 梅志亮  航材成本
	 * @time 2016-10-31
	 */
	@Privilege(code = "aerialmaterial:cost:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String getIndex(Model model) {
		model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		model.addAttribute("supervisoryLevelEnum",SupervisoryLevelEnum.enumToListMap());
		model.addAttribute("agingControlEnum", BinaryEnum.enumToListMap());
		return "material/cost/materialcostlist";
	}
	/**
	 * @author 梅志亮  航材成本列表
	 * @time 2016-10-31
	 */
	@SuppressWarnings("rawtypes")
	@Privilege(code = "aerialmaterial:cost:main")
	@ResponseBody
	@RequestMapping(value = "queryCostList", method = RequestMethod.POST)
	public Map<String, Object> queryQualityCheckList(
		@RequestBody HCMainData hcmainData, HttpServletRequest request,
		Model model)throws BusinessException {
		
		String id = "";//用户刚编辑过的记录 ID
		if(hcmainData.getId() != null){
			id = hcmainData.getId().toString();
		}
		//清除查询条件中的ID，保证列表查询结果集的正确性
		hcmainData.setId(null);
		//查询总记录数
		PageHelper.startPage(hcmainData.getPagination());
		List<HCMainData> list = hCMainDataService.selectMaterialCostList(hcmainData);
		if(((Page)list).getTotal() > 0){
			//分页查询
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					HCMainData hcmainData1 = new HCMainData();
					hcmainData1.setId(id);
					List<HCMainData> newList = hCMainDataService.selectMaterialCostList(hcmainData1);
					if(newList != null && newList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, hcmainData.getPagination());
		}else{
			List<HCMainData> newRecordList = new ArrayList<HCMainData>(1);
			if(StringUtils.isNotBlank(String.valueOf(id))){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				HCMainData hcmainData1 = new HCMainData();
				hcmainData1.setId(id);
				newRecordList = hCMainDataService.selectMaterialCostList(hcmainData1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, hcmainData.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, hcmainData.getPagination());
		}
	}
	
	/**
     * @author 梅志亮  查看航材成本
	 * @time 2016-10-31 
	 */
	@RequestMapping(value="view",method=RequestMethod.GET)
	public ModelAndView  View(String id){
		Map<String, Object> model =initData(id);
		model.put("typeList", getMaterialPlane(((HCMainData) model.get("hcmainData")).getMaterialToPlaneModelList()));
		return new ModelAndView("material/cost/view_materialcost",model);
	}
	/**
     * @author 梅志亮  查询航材成本的维护记录
	 * @time 2016-10-31 
	 */
	@ResponseBody
	@RequestMapping(value = "queryCostListByPage", method = RequestMethod.POST)
	public Map<String, Object> queryCostListByPage(@RequestBody MaterialCost materialCost) {
		PageHelper.startPage(materialCost.getPagination());
		List<MaterialCost> list = materialCostService.selectMaterialCostList(materialCost);
		return PageUtil.pack4PageHelper(list, materialCost.getPagination());
	}
	
	/**
     * @author 梅志亮  修改航材成本
	 * @time 2016-10-31 
	 */
	@Privilege(code = "aerialmaterial:cost:main:01")
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public ModelAndView  edit(String id){
		return new ModelAndView("material/cost/edit_materialcost",initData(id));
	}
	/**
	 * @author meizhiliang
	 * @description 新增航材成本维护
	 * @develop date 2016.08.24
	 * @throws BusinessException
	 */
	@Privilege(code = "aerialmaterial:cost:main:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public int add(@RequestBody MaterialCost materialCost) throws BusinessException {
		try {
			return materialCostService.insertSelective(materialCost);
		} catch (RuntimeException e) {
			throw new BusinessException("新增航材报价失败",e);
		}
	}
	/**
	 * @author meizhiliang
	 * @description 删除航材成本维护
	 * @develop date 2016.08.24
	 * @throws BusinessException
	 */
	@Privilege(code = "aerialmaterial:cost:main:02")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public int delete(String id) throws BusinessException {
		try {
			return materialCostService.deleteByPrimaryKey(id);
		} catch (RuntimeException e) {
			throw new BusinessException("删除报价失败",e);
		}
	}
	/**
	 * @author meizhiliang
	 * @param materialToPlaneModelList 航材机型关系表
	 * @return List<Map<String, String>>
	 * @develop date 2016.09.20
	 */
	private List<Map<String, String>> getMaterialPlane(List<MaterialToPlaneModel> materialToPlaneModelList){
		List<String> checkList = new ArrayList<String>();
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		for (MaterialToPlaneModel materialToPlaneModel : materialToPlaneModelList) {
			checkList.add(materialToPlaneModel.getFjjx());
		}
		return resultList;
	}
	/**
     * @author 梅志亮  
     * 修改、查看航材成本绑定数据
	 */
	private Map<String, Object> initData(String id){
		Map<String, Object> model =new HashMap<String, Object>();
		HCMainData hcMainData = hCMainDataService.selectById(id);
		model.put("hcmainData",hcMainData);
		model.put("materialPriceEnum", MaterialPriceEnum.enumToListMap());
		model.put("supervisoryLevelEnum", SupervisoryLevelEnum.enumToListMap());
		model.put("agingControlEnum", BinaryEnum.enumToListMap());
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		model.put("materialSecondTypeEnum", MaterialSecondTypeEnum.enumToListMap()) ;
	    return model;
	}
}
